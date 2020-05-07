/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller.impl.process;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.anjbo.bean.UserDto;
import com.anjbo.bean.order.BaseListDto;
import com.anjbo.bean.order.FlowDto;
import com.anjbo.bean.process.AuditManagerDto;
import com.anjbo.bean.process.SignInsuranceDto;
import com.anjbo.bean.risk.AuditFinalDto;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.OrderBaseController;
import com.anjbo.controller.api.UserApi;
import com.anjbo.controller.process.ISignInsuranceController;
import com.anjbo.service.process.SignInsuranceService;
import com.anjbo.service.risk.AuditFinalService;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 17:23:34
 * @version 1.0
 */
@RestController
public class SignInsuranceController extends OrderBaseController implements ISignInsuranceController{

	@Resource private SignInsuranceService signInsuranceService;
	
	@Resource private UserApi userApi;
	
	@Resource private AuditFinalService auditFinalService;
	
	

	/**
	 * 提交
	 * @author liuf
	 */
	@Override
	public RespStatus processSubmit(@RequestBody SignInsuranceDto dto) {
		RespStatus resp = new RespStatus();
		try {
			UserDto userDto = userApi.getUserDto();
			dto.setCreateUid(userDto.getUid());
			dto.setUpdateUid(userDto.getUid());
			
			SignInsuranceDto tempDto = new SignInsuranceDto();
			tempDto.setOrderNo(dto.getOrderNo());
			tempDto = signInsuranceService.find(tempDto);
			if(tempDto == null) {
				signInsuranceService.insert(dto);
			}else {
				dto.setId(tempDto.getId());
				signInsuranceService.update(dto);
			}
			
			FlowDto flowDto = new FlowDto();
			flowDto.setOrderNo(dto.getOrderNo());
			flowDto.setCurrentProcessId("signInsurancePolicy");
			flowDto.setCurrentProcessName("签订投保单");
			flowDto.setNextProcessId("allocationFund");
			flowDto.setNextProcessName("推送金融机构");
			flowDto.setUpdateUid(userDto.getUid());
			flowDto.setHandleUid(userDto.getUid());  //当前处理人
			flowDto.setHandleName(userDto.getName());
			
			AuditFinalDto finalDto = new AuditFinalDto();
			finalDto.setOrderNo(dto.getOrderNo());
			finalDto = auditFinalService.find(finalDto);
			BaseListDto baseListDto = new BaseListDto();
			baseListDto.setOrderNo(dto.getOrderNo());
			if(finalDto.getAllocationFundUid()!=null) {
				UserDto handlerUserDto = userApi.findUserDtoByUid(finalDto.getAllocationFundUid());
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
	 * @author liuf 
	 */
	@Override
	public RespDataObject<SignInsuranceDto> processDetails(@RequestBody SignInsuranceDto dto) {
		RespDataObject<SignInsuranceDto> resp = new RespDataObject<SignInsuranceDto>();
		try {
			SignInsuranceDto signDto = signInsuranceService.find(dto);
			return RespHelper.setSuccessDataObject(resp,signDto);
		}catch (Exception e) {
			logger.error("详情异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, null, RespStatusEnum.FAIL.getMsg());
		}
	}
		
}