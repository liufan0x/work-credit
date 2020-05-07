/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller.impl.finance;

import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.anjbo.controller.api.UserApi;
import com.anjbo.controller.OrderBaseController;
import com.anjbo.bean.UserDto;
import com.anjbo.bean.finance.LendingHarvestDto;
import com.anjbo.bean.finance.RebateDto;
import com.anjbo.bean.finance.ReceivablePayDto;
import com.anjbo.bean.order.BaseListDto;
import com.anjbo.bean.order.FlowDto;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.finance.IRebateController;
import com.anjbo.service.finance.LendingHarvestService;
import com.anjbo.service.finance.RebateService;
import com.anjbo.service.finance.ReceivablePayService;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 18:34:34
 * @version 1.0
 */
@RestController
public class RebateController extends OrderBaseController implements IRebateController{

	@Resource private RebateService rebateService;
	
	@Resource private UserApi userApi;
	
	@Resource private LendingHarvestService lendingHarvestService;
	
	@Resource private ReceivablePayService receivablePayService;
	
	/**
	 * 提交
	 * @author lic 
	 */
	@Override
	public RespStatus processSubmit(@RequestBody RebateDto dto) {
		RespStatus resp = new RespStatus();
		try {
			UserDto userDto = userApi.getUserDto();
			dto.setCreateUid(userDto.getUid());
			dto.setUpdateUid(userDto.getUid());
			
			RebateDto temp= new RebateDto();
			temp.setOrderNo(dto.getOrderNo());
			temp = rebateService.find(temp);
			if(temp == null) {
				rebateService.insert(dto);
			}else {
				rebateService.update(dto);
			}
			
			FlowDto flowDto = new FlowDto();
			flowDto.setOrderNo(dto.getOrderNo());
			flowDto.setCurrentProcessId("rebate");
			flowDto.setCurrentProcessName("返佣");
			flowDto.setNextProcessId("wanjie");
			flowDto.setNextProcessName("已完结");
			flowDto.setUpdateUid(userDto.getUid());
			flowDto.setHandleUid(userDto.getUid());
			flowDto.setHandleName(userDto.getName());
			
			BaseListDto baseListDto = new BaseListDto();
			baseListDto.setOrderNo(dto.getOrderNo());
			baseListDto.setCurrentHandler("-");
			baseListDto.setCurrentHandlerUid("-");
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
	public RespDataObject<RebateDto> processDetails(@RequestBody RebateDto dto) {
		RespDataObject<RebateDto> resp = new RespDataObject<RebateDto>();
		try {
			
			LendingHarvestDto lendingHarvestDto = new LendingHarvestDto();
			lendingHarvestDto.setOrderNo(dto.getOrderNo());
			lendingHarvestDto = lendingHarvestService.find(lendingHarvestDto);
			
			ReceivablePayDto receivablePayDto = new ReceivablePayDto();
			receivablePayDto.setOrderNo(dto.getOrderNo());
			receivablePayDto = receivablePayService.find(receivablePayDto);
			
			double rebateMoney = 0.0;
			if(receivablePayDto!=null && receivablePayDto.getRebateMoney() != null) {
				rebateMoney += receivablePayDto.getRebateMoney();
			}
			if(lendingHarvestDto!=null && lendingHarvestDto.getReturnMoney() != null){
				rebateMoney += lendingHarvestDto.getReturnMoney();
			}
			String orderNo = dto.getOrderNo();
			dto = rebateService.find(dto);
			if(dto == null) {
				dto = new RebateDto();
				dto.setOrderNo(orderNo);
			}
			dto.setRebateMoney(rebateMoney);
			return RespHelper.setSuccessDataObject(resp,dto);
		}catch (Exception e) {
			logger.error("详情异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, null, RespStatusEnum.FAIL.getMsg());
		}
	}
		
}