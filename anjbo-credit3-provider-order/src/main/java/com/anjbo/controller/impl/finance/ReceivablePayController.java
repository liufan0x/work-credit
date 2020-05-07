/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller.impl.finance;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.anjbo.controller.api.UserApi;
import com.anjbo.controller.OrderBaseController;
import com.anjbo.bean.UserDto;
import com.anjbo.bean.finance.ReceivablePayDto;
import com.anjbo.bean.order.BaseBorrowDto;
import com.anjbo.bean.order.BaseListDto;
import com.anjbo.bean.order.FlowDto;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.finance.IReceivablePayController;
import com.anjbo.service.finance.ReceivablePayService;
import com.anjbo.service.order.BaseBorrowService;
import com.anjbo.service.order.BaseListService;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 18:34:34
 * @version 1.0
 */
@RestController
public class ReceivablePayController extends OrderBaseController implements IReceivablePayController{

	@Resource private ReceivablePayService receivablePayService;
	
	@Resource private UserApi userApi;
	
	@Resource private BaseBorrowService baseBorrowService;
	
	@Resource private BaseListService baseListService;

	/**
	 * 提交
	 * @author lic 
	 */
	@Override
	public RespStatus processSubmit(@RequestBody ReceivablePayDto dto) {
		RespStatus resp = new RespStatus();
		try {
			UserDto userDto = userApi.getUserDto();
			dto.setCreateUid(userDto.getUid());
			dto.setUpdateUid(userDto.getUid());
			
			ReceivablePayDto temp = new ReceivablePayDto();
			temp.setOrderNo(dto.getOrderNo());
			temp = receivablePayService.find(temp);
			if(temp == null) {
				receivablePayService.insert(dto);
			}else {
				receivablePayService.update(dto);
			}
			
			FlowDto flowDto = new FlowDto();
			flowDto.setOrderNo(dto.getOrderNo());
			flowDto.setCurrentProcessId("pay");
			flowDto.setCurrentProcessName("收罚息");
			flowDto.setUpdateUid(userDto.getUid());
			flowDto.setHandleUid(userDto.getUid()); // 当前处理人
			flowDto.setHandleName(userDto.getName());
			String handlerUid = "";
			
			BaseListDto baseListDto = new BaseListDto();
			baseListDto.setOrderNo(dto.getOrderNo());
			baseListDto = baseListService.find(baseListDto);
			
			if("03".equals(baseListDto.getProductCode()) && StringUtils.isNotEmpty(baseListDto.getRelationOrderNo())) {
				if(StringUtils.isNotEmpty(dto.getNextHandleUid())) {
					flowDto.setNextProcessId("rebate");
					flowDto.setNextProcessName("返佣");
					handlerUid = dto.getNextHandleUid();
				}else {
					flowDto.setNextProcessId("wanjie");
					flowDto.setNextProcessName("已完结");
				}
			}else {
				BaseBorrowDto baseBorrowDto = new BaseBorrowDto();
				baseBorrowDto.setOrderNo(dto.getOrderNo());
				baseBorrowDto = baseBorrowService.find(baseBorrowDto);
				flowDto.setNextProcessId("elementReturn");  //要件退还
				flowDto.setNextProcessName("要件退还");
				handlerUid = baseBorrowDto.getElementUid();
			}
			
			UserDto handlerUserDto = userApi.findUserDtoByUid(handlerUid);
			baseListDto = new BaseListDto();
			baseListDto.setOrderNo(dto.getOrderNo());
			baseListDto.setCurrentHandler(handlerUserDto.getName());
			baseListDto.setCurrentHandlerUid(handlerUserDto.getUid());
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
	public RespDataObject<ReceivablePayDto> processDetails(@RequestBody ReceivablePayDto dto) {
		RespDataObject<ReceivablePayDto> resp = new RespDataObject<ReceivablePayDto>();
		try {
			dto = receivablePayService.find(dto);
			return RespHelper.setSuccessDataObject(resp,dto);
		}catch (Exception e) {
			logger.error("详情异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, null, RespStatusEnum.FAIL.getMsg());
		}
	}
		
}