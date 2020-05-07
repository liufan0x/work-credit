/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller.impl.risk;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.anjbo.bean.UserDto;
import com.anjbo.bean.order.BaseBorrowDto;
import com.anjbo.bean.order.BaseListDto;
import com.anjbo.bean.order.FlowDto;
import com.anjbo.bean.risk.AuditFinalDto;
import com.anjbo.common.Constants;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.common.SMSConstants;
import com.anjbo.controller.OrderBaseController;
import com.anjbo.controller.api.UserApi;
import com.anjbo.controller.risk.IAuditFinalController;
import com.anjbo.service.order.BaseBorrowService;
import com.anjbo.service.order.BaseListService;
import com.anjbo.service.risk.AuditFinalService;
import com.anjbo.utils.AmsUtil;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 19:44:31
 * @version 1.0
 */
@RestController
public class AuditFinalController extends OrderBaseController implements IAuditFinalController{

	@Resource private AuditFinalService auditFinalService;
	
	@Resource private UserApi userApi;
	
	@Resource private BaseBorrowService baseBorrowService;
	
	@Resource private BaseListService baseListService;

	/**
	 * 提交(风控终审)
	 * @author lic 
	 */
	@Override
	public RespStatus processSubmit(@RequestBody AuditFinalDto finalAudit) {
		RespStatus resp = new RespStatus();
		try {
			UserDto userDto = userApi.getUserDto();
			finalAudit.setCreateUid(userDto.getUid());
			finalAudit.setUpdateUid(userDto.getUid());
			
			//选择的资金分配员
			finalAudit.setAuditTime(new Date());
			finalAudit.setHandleUid(userDto.getUid());
			finalAudit.setAuditStatus(1);
			AuditFinalDto temp = new AuditFinalDto();
			temp.setOrderNo(finalAudit.getOrderNo());
			temp = auditFinalService.find(temp);
			//录入推送金融机构uid
			finalAudit.setAllocationFundUid(finalAudit.getNextHandleUid());
			if(temp==null){
				auditFinalService.insert(finalAudit);
			}else{
				auditFinalService.update(finalAudit);
			}
			FlowDto next = new FlowDto();
			next.setOrderNo(finalAudit.getOrderNo());
			next.setCurrentProcessId("auditFinal");
			next.setCurrentProcessName("风控终审");
			next.setHandleUid(userDto.getUid());
			next.setHandleName(userDto.getName());
			next.setUpdateUid(userDto.getUid());
			
			BaseListDto baseListDto = new BaseListDto();
			baseListDto.setOrderNo(finalAudit.getOrderNo());
			baseListDto = baseListService.find(baseListDto);
			if("05".equals(baseListDto.getProductCode())) {//提放，提交流程到签订投保单(受理员操作)
				next.setNextProcessId("signInsurancePolicy");
				next.setNextProcessName("签订投保单");
				BaseListDto orderListDto = new BaseListDto();
				orderListDto.setOrderNo(finalAudit.getOrderNo());
				orderListDto.setCurrentHandler(baseListDto.getAcceptMemberName());
				orderListDto.setCurrentHandlerUid(baseListDto.getAcceptMemberUid());	
				goNextNode(next,orderListDto);
			}else {
				next.setNextProcessId("allocationFund");
				next.setNextProcessName("推送金融机构");
				BaseListDto orderListDto = new BaseListDto();
				orderListDto.setOrderNo(finalAudit.getOrderNo());
				if(StringUtils.isNotEmpty(finalAudit.getNextHandleUid())) {
					UserDto nextUser = userApi.findUserDtoByUid(finalAudit.getNextHandleUid());
					orderListDto.setCurrentHandler(nextUser.getName());
					orderListDto.setCurrentHandlerUid(nextUser.getUid());	
				}
				goNextNode(next,orderListDto);
			}
			
			return RespHelper.setSuccessRespStatus(resp);
		}catch (Exception e) {
			logger.error("提交异常,参数："+finalAudit.toString(), e);
			return RespHelper.failRespStatus();
		}
	}
	
	/**
	 * 上报首席风险官审批
	 * @param request
	 * @param finalAudit
	 * @return
	 */
	public RespStatus processReportOfficer(@RequestBody AuditFinalDto finalAudit){
		RespStatus result = new RespStatus();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			//获取登录人信息
			UserDto userDto = userApi.getUserDto();
			finalAudit.setCreateUid(userDto.getUid());
			finalAudit.setAuditTime(new Date());
			finalAudit.setAuditStatus(3);
			finalAudit.setHandleUid(userDto.getUid());
			AuditFinalDto temp = new AuditFinalDto();
			temp.setOrderNo(finalAudit.getOrderNo());
			temp = auditFinalService.find(temp);
			if(temp==null){
				auditFinalService.insert(finalAudit);
			}else{
				auditFinalService.update(finalAudit);
			}
			
			
			FlowDto next = new FlowDto();
			next.setOrderNo(finalAudit.getOrderNo());
			next.setCurrentProcessId("auditFinal");
			next.setCurrentProcessName("风控终审");
			next.setNextProcessId("auditOfficer");
			next.setNextProcessName("首席风险官审批");
			next.setHandleUid(userDto.getUid());
			next.setHandleName(userDto.getName());
			next.setUpdateUid(userDto.getUid());
			
			BaseListDto orderListDto = new BaseListDto();
			orderListDto.setOrderNo(finalAudit.getOrderNo());
			if(StringUtils.isNotEmpty(finalAudit.getNextHandleUid())) {
				UserDto nextUser = userApi.findUserDtoByUid(finalAudit.getNextHandleUid());
				orderListDto.setCurrentHandler(nextUser.getName());
				orderListDto.setCurrentHandlerUid(nextUser.getUid());	
			}
			goNextNode(next,orderListDto);
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e){
			logger.error("上报首席风险官审批异常,异常信息为==>"+e.getMessage());
		}
		return result;
	}
	/**
	 * 上报复核审批
	 * @param request
	 * @param finalAudit
	 * @return
	 */
	public RespStatus processReportReview(@RequestBody AuditFinalDto finalAudit){
		RespStatus result = new RespStatus();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			if(finalAudit.getOfficerUid()!=null && finalAudit.getOfficerUidType()==0){//兼容app
			    finalAudit.setOfficerUidType(2);	
			}
			//获取登录人信息
			UserDto userDto = userApi.getUserDto();
			finalAudit.setCreateUid(userDto.getUid());
			finalAudit.setAuditTime(new Date());
			finalAudit.setAuditStatus(4);
			finalAudit.setHandleUid(userDto.getUid());
			finalAudit.setUpdateTime(new Date());
			AuditFinalDto temp = new AuditFinalDto();
			temp.setOrderNo(finalAudit.getOrderNo());
			temp = auditFinalService.find(temp);
			if(temp==null){
				auditFinalService.insert(finalAudit);
			}else{
				auditFinalService.update(finalAudit);
			}
			
			FlowDto next = new FlowDto();
			next.setHandleUid(userDto.getUid());
			next.setHandleName(userDto.getName());
			next.setUpdateUid(userDto.getUid());
			BaseListDto orderListDto = new BaseListDto();
			next.setOrderNo(finalAudit.getOrderNo());
			next.setCurrentProcessId("auditFinal");
			next.setCurrentProcessName("风控终审");
			next.setNextProcessId("auditReview");
			next.setNextProcessName("复核审批");
			if(StringUtils.isNotEmpty(finalAudit.getNextHandleUid())) {
				UserDto nextUser = userApi.findUserDtoByUid(finalAudit.getNextHandleUid());
				orderListDto.setCurrentHandler(nextUser.getName());
				orderListDto.setCurrentHandlerUid(nextUser.getUid());
			}
			orderListDto.setOrderNo(finalAudit.getOrderNo());
			goNextNode(next,orderListDto);
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e){
			logger.error("上报复核审批异常,异常信息为==>"+e.getMessage());
		}
		return result;
	}
	/**
	 * 详情
	 * @author lic 
	 */
	@Override
	public RespDataObject<AuditFinalDto> processDetails(@RequestBody AuditFinalDto finalAudit) {
		RespDataObject<AuditFinalDto> result = new RespDataObject<AuditFinalDto>();
		try {
			if(StringUtils.isBlank(finalAudit.getOrderNo())){
				result.setMsg("查询终审详情参失败,缺少订单编号!");
				return result;
			}
			finalAudit = auditFinalService.find(finalAudit);
			UserDto user = null;
			if(StringUtils.isNotBlank(finalAudit.getHandleUid())){
				user = userApi.findUserDtoByUid(finalAudit.getHandleUid());
			}
			if(null!=user&&null!=finalAudit){
				finalAudit.setHandleName(user.getName());
			}
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
			return RespHelper.setSuccessDataObject(result,finalAudit);
		}catch (Exception e) {
			logger.error("详情异常,参数："+finalAudit.toString(), e);
			return RespHelper.setFailDataObject(result, null, RespStatusEnum.FAIL.getMsg());
		}
	}
		
}