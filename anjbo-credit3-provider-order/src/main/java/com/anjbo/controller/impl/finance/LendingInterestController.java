/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller.impl.finance;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.anjbo.controller.api.UserApi;
import com.anjbo.controller.OrderBaseController;
import com.anjbo.bean.UserDto;
import com.anjbo.bean.finance.LendingInterestDto;
import com.anjbo.bean.order.BaseListDto;
import com.anjbo.bean.order.FlowDto;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.finance.ILendingInterestController;
import com.anjbo.service.finance.LendingInterestService;
import com.anjbo.utils.StringUtil;

/**
 * 收利息/扣回后置的订单
 * @Author ANJBO Generator 
 * @Date 2018-07-03 18:34:33
 * @version 1.0
 */
@RestController
public class LendingInterestController extends OrderBaseController implements ILendingInterestController{

	@Resource private LendingInterestService lendingInterestService;
	
	@Resource private UserApi userApi;

	/**
	 * 提交
	 * @author lic 
	 */
	@Override
	public RespStatus processSubmit(@RequestBody LendingInterestDto dto) {
		RespStatus resp = new RespStatus();
		try {
			UserDto userDto = userApi.getUserDto();
			dto.setCreateUid(userDto.getUid());
			dto.setUpdateUid(userDto.getUid());
			
			LendingInterestDto temp = new LendingInterestDto();
			temp.setOrderNo(dto.getOrderNo());
			temp = lendingInterestService.find(temp);
			if(temp == null) {
				lendingInterestService.insert(dto);
			}else {
				lendingInterestService.update(dto);
			}
			
			FlowDto flowDto = new FlowDto();
			flowDto.setOrderNo(dto.getOrderNo());
			flowDto.setUpdateUid(userDto.getUid());
			flowDto.setHandleUid(userDto.getUid()); // 当前处理人
			flowDto.setHandleName(userDto.getName());
			
			if(dto.getType()==2){
				flowDto.setCurrentProcessId("isBackExpenses");
				flowDto.setCurrentProcessName("扣回后置费用");
				flowDto.setNextProcessId("backExpenses");
				flowDto.setNextProcessName("核实后置费用");
			}else {
				flowDto.setCurrentProcessId("isLendingHarvest");
				flowDto.setCurrentProcessName("收利息");
				flowDto.setNextProcessId("lendingHarvest");
				flowDto.setNextProcessName("核实利息");
			}
			String handlerUid = dto.getUid();//下一处理人
			BaseListDto baseListDto = new BaseListDto();
			baseListDto.setOrderNo(dto.getOrderNo());
			if(StringUtil.isNotBlank(handlerUid)) {
				UserDto handlerUserDto = userApi.findUserDtoByUid(handlerUid);
				baseListDto.setCurrentHandler(handlerUserDto.getName());
				baseListDto.setCurrentHandlerUid(handlerUserDto.getUid());
			}
			goNextNode(flowDto, baseListDto);
			
			return RespHelper.setSuccessRespStatus(resp);
		}catch (Exception e) {
			logger.error("提交异常,参数："+dto.toString(), e);
			return RespHelper.failRespStatus();
		}
	}
	
	/**
	 * 详情
	 * @author lic 
	 */
	@Override
	public RespDataObject<Map<String, Object>> processDetails(@RequestBody LendingInterestDto dto) {
		RespDataObject<Map<String, Object>> resp = new RespDataObject<Map<String, Object>>();
		Map<String, Object> map=new HashMap<String, Object>();
		try {
			dto = lendingInterestService.find(dto);
			map.put("interest", dto);
			return RespHelper.setSuccessDataObject(resp,map);
		}catch (Exception e) {
			logger.error("详情异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, null, RespStatusEnum.FAIL.getMsg());
		}
	}
		
}