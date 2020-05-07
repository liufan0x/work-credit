/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller.impl.risk;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.anjbo.bean.UserDto;
import com.anjbo.bean.order.BaseBorrowDto;
import com.anjbo.bean.order.BaseListDto;
import com.anjbo.bean.order.FlowDto;
import com.anjbo.bean.risk.AllocationFundDto;
import com.anjbo.bean.risk.AuditJusticeDto;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.OrderBaseController;
import com.anjbo.controller.api.UserApi;
import com.anjbo.controller.risk.IAuditJusticeController;
import com.anjbo.service.order.BaseBorrowService;
import com.anjbo.service.risk.AllocationFundService;
import com.anjbo.service.risk.AuditJusticeService;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 19:44:33
 * @version 1.0
 */
@RestController
public class AuditJusticeController extends OrderBaseController implements IAuditJusticeController{

	@Resource private AuditJusticeService auditJusticeService;
	
	@Resource private UserApi userApi;
	
	@Resource private AllocationFundService allocationFundService;
	
	@Resource private BaseBorrowService baseBorrowService;

	/**
	 * 提交
	 * @author lic 
	 */
	@Override
	public RespStatus processSubmit(@RequestBody AuditJusticeDto obj) {
		RespStatus resp = new RespStatus();
		try {
			UserDto userDto = userApi.getUserDto();
			obj.setCreateUid(userDto.getUid());
			obj.setUpdateUid(userDto.getUid());
			obj.setAuditTime(new Date());
			obj.setAuditStatus(1);
			
			AuditJusticeDto temp = new AuditJusticeDto();
			temp.setOrderNo(obj.getOrderNo());
			temp = auditJusticeService.find(temp);
			if(temp==null){
				auditJusticeService.insert(obj);
			}else{
				auditJusticeService.update(obj);
			}
			
			FlowDto next = new FlowDto();
			next.setOrderNo(obj.getOrderNo());
			next.setCurrentProcessId("auditJustice");
			next.setCurrentProcessName("法务审批");
			next.setHandleUid(userDto.getUid());
			next.setHandleName(userDto.getName());
			next.setUpdateUid(userDto.getUid());
			
			BaseListDto orderListDto = new BaseListDto();
			orderListDto.setOrderNo(obj.getOrderNo());
			
			//提放业务法务审批到要件校验
			BaseBorrowDto baseBorrowDto = new BaseBorrowDto();
			baseBorrowDto.setOrderNo(obj.getOrderNo());
			baseBorrowDto = baseBorrowService.find(baseBorrowDto);
			if("05".equals(baseBorrowDto.getProductCode())) {
				String nextUid = baseBorrowDto.getElementUid();
				next.setNextProcessId("element");
				next.setNextProcessName("要件校验");
				UserDto nextUser = userApi.findUserDtoByUid(nextUid);
				orderListDto.setCurrentHandlerUid(nextUser.getUid());
				orderListDto.setCurrentHandler(nextUser.getName());
			}else {
				next.setNextProcessId("fundDocking");
				next.setNextProcessName("资料推送");
				AllocationFundDto allocationFundDto =new AllocationFundDto();
				allocationFundDto.setOrderNo(obj.getOrderNo());
				List<AllocationFundDto> allocationFundList = allocationFundService.search(allocationFundDto);
				String nextUid = allocationFundList.get(0).getCreateUid();
				UserDto nextUser = userApi.findUserDtoByUid(nextUid);
				orderListDto.setCurrentHandlerUid(nextUser.getUid());
				orderListDto.setCurrentHandler(nextUser.getName());
			}
			goNextNode(next,orderListDto);
			return RespHelper.setSuccessRespStatus(resp);
		}catch (Exception e) {
			logger.error("提交异常,参数："+obj.toString(), e);
			return RespHelper.failRespStatus();
		}
	}
	
	/**
	 * 详情
	 * @author lic 
	 */
	@Override
	public RespDataObject<AuditJusticeDto> processDetails(@RequestBody AuditJusticeDto obj) {
		RespDataObject<AuditJusticeDto> resp = new RespDataObject<AuditJusticeDto>();
		try {
//			if(StringUtils.isBlank(obj.getOrderNo())){
//				result.setMsg("查询法务审批详情异常,缺少订单编号!");
//				return result;
//			}
			obj = auditJusticeService.find(obj);
			UserDto user = null;
			if(StringUtils.isNotBlank(obj.getHandleUid())){
				user = userApi.findUserDtoByUid(obj.getHandleUid());
			}
			if(null!=user&&null!=obj){
				obj.setHandleName(user.getName());
			}
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
			return RespHelper.setSuccessDataObject(resp,obj);
		}catch (Exception e) {
			logger.error("详情异常,参数："+obj.toString(), e);
			return RespHelper.setFailDataObject(resp, null, RespStatusEnum.FAIL.getMsg());
		}
	}
		
}