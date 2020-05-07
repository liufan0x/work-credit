/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller.impl.process;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.anjbo.bean.UserDto;
import com.anjbo.bean.order.BaseCustomerDto;
import com.anjbo.bean.order.BaseListDto;
import com.anjbo.bean.order.FlowDto;
import com.anjbo.bean.process.UploadInsuranceDto;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.OrderBaseController;
import com.anjbo.controller.api.ThirdApi;
import com.anjbo.controller.api.UserApi;
import com.anjbo.controller.process.IUploadInsuranceController;
import com.anjbo.service.order.BaseCustomerService;
import com.anjbo.service.order.BaseListService;
import com.anjbo.service.process.UploadInsuranceService;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 17:23:34
 * @version 1.0
 */
@RestController
public class UploadInsuranceController extends OrderBaseController implements IUploadInsuranceController{

	@Resource private UploadInsuranceService uploadInsuranceService;
	
	@Resource private UserApi userApi;
	
	@Resource private BaseListService baseListService;
	
	@Resource private ThirdApi thirdApi;
	
	@Resource private BaseCustomerService baseCustomerService;
	
	Logger log = Logger.getLogger(UploadInsuranceController.class);
	
	

	/**
	 * 提交
	 * @author liuf
	 */
	@Override
	public RespStatus processSubmit(@RequestBody UploadInsuranceDto dto) {
		RespStatus resp = new RespStatus();
		try {
			UserDto userDto = userApi.getUserDto();
			dto.setCreateUid(userDto.getUid());
			dto.setUpdateUid(userDto.getUid());
			//调用api上传电子保单
			log.info("上传电子保单开始---");
			BaseCustomerDto customerDto = new BaseCustomerDto();
			customerDto.setOrderNo(dto.getOrderNo());
			customerDto = baseCustomerService.find(customerDto);
			Map<String,Object> map = new HashMap<String,Object>();
			System.out.println("客户姓名："+customerDto.getCustomerName());
			log.info("客户姓名："+customerDto.getCustomerName());
			map.put("customerName", customerDto.getCustomerName());
			map.put("idCardType", customerDto.getCustomerCardType());
			map.put("idCardNumber", customerDto.getCustomerCardNumber());
			map.put("insuranceFile", dto.getUploadInsurancePdf());
			RespStatus respStatus = thirdApi.getInsertFile(map);
			log.info("api返回："+respStatus.getMsg());
			if(!"SUCCESS".equals(respStatus.getCode())) {
				RespHelper.setFailRespStatus(resp, respStatus.getMsg());
				return resp;
			}
			UploadInsuranceDto tempDto = new UploadInsuranceDto();
			tempDto.setOrderNo(dto.getOrderNo());
			tempDto = uploadInsuranceService.find(tempDto);
			if(tempDto == null) {
				uploadInsuranceService.insert(dto);
			}else {
				dto.setId(tempDto.getId());
				uploadInsuranceService.update(dto);
			}
			
			FlowDto flowDto = new FlowDto();
			flowDto.setOrderNo(dto.getOrderNo());
			flowDto.setCurrentProcessId("uploadInsurancePolicy");
			flowDto.setCurrentProcessName("上传电子保单");
			flowDto.setUpdateUid(userDto.getUid());
			flowDto.setHandleUid(userDto.getUid());  //当前处理人
			flowDto.setHandleName(userDto.getName());
			
			BaseListDto baseListDto = new BaseListDto();
			baseListDto.setOrderNo(dto.getOrderNo());
			baseListDto = baseListService.find(baseListDto);
			flowDto.setNextProcessId("foreclosure");
			flowDto.setNextProcessName("结清原贷款");
			BaseListDto orderListDto = new BaseListDto();
			orderListDto.setOrderNo(dto.getOrderNo());
			orderListDto.setCurrentHandler(baseListDto.getAcceptMemberName());
			orderListDto.setCurrentHandlerUid(baseListDto.getAcceptMemberUid());	
			goNextNode(flowDto, orderListDto);
			
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
	public RespDataObject<UploadInsuranceDto> processDetails(@RequestBody UploadInsuranceDto dto) {
		RespDataObject<UploadInsuranceDto> resp = new RespDataObject<UploadInsuranceDto>();
		try {
			UploadInsuranceDto uploadDto = uploadInsuranceService.find(dto);
			return RespHelper.setSuccessDataObject(resp,uploadDto);
		}catch (Exception e) {
			logger.error("详情异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, null, RespStatusEnum.FAIL.getMsg());
		}
	}
		
}