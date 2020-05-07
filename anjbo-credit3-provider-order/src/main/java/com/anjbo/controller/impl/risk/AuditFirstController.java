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

import com.anjbo.bean.BankDto;
import com.anjbo.bean.BankSubDto;
import com.anjbo.bean.UserDto;
import com.anjbo.bean.order.BaseBorrowDto;
import com.anjbo.bean.order.BaseListDto;
import com.anjbo.bean.order.FlowDto;
import com.anjbo.bean.risk.AuditFirstDto;
import com.anjbo.bean.risk.AuditFirstForeclosureDto;
import com.anjbo.bean.risk.AuditFirstPaymentDto;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.OrderBaseController;
import com.anjbo.controller.api.DataApi;
import com.anjbo.controller.api.UserApi;
import com.anjbo.controller.risk.IAuditFirstController;
import com.anjbo.service.order.BaseBorrowService;
import com.anjbo.service.risk.AuditFirstService;
import com.anjbo.utils.StringUtil;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 19:44:32
 * @version 1.0
 */
@RestController
public class AuditFirstController extends OrderBaseController implements IAuditFirstController{

	@Resource private AuditFirstService auditFirstService;
	
	@Resource private UserApi userApi;
	
	@Resource private DataApi dataApi;
	
	@Resource private BaseBorrowService baseBorrowService;
	
	
	/**
	 * 保存
	 * @author lic 
	 */
	@Override
	public RespStatus processSave(@RequestBody AuditFirstDto dto) {
		RespStatus resp = new RespStatus();
		try {
			UserDto userDto = userApi.getUserDto();
			dto.setCreateUid(userDto.getUid());
			dto.setUpdateUid(userDto.getUid());
			
			return RespHelper.setSuccessRespStatus(resp);
		}catch (Exception e) {
			logger.error("提交异常,参数："+dto.toString(), e);
			return RespHelper.failRespStatus();
		}
	}
	
	/**
	 * 提交(上报终审审批)
	 * @author lic 
	 */
	@Override
	public RespStatus processSubmit(@RequestBody AuditFirstDto firstAudit) {
		RespStatus result = new RespStatus();
		try {
			UserDto userDto = userApi.getUserDto();
			firstAudit.setCreateUid(userDto.getUid());
			firstAudit.setUpdateUid(userDto.getUid());
				
			if(null !=firstAudit.getForeclosureAuditList() &&firstAudit.getForeclosureAuditList().size()>0){
				for(AuditFirstForeclosureDto auditDto:firstAudit.getForeclosureAuditList()){
					BankDto bank = new BankDto();
					bank.setId(auditDto.getLoanBankId());
					RespDataObject<BankDto> bankDto =dataApi.getBankNameById(bank);
					auditDto.setLoanBankName(bankDto==null ? "":bankDto.getData().getName());
					BankSubDto sub = new BankSubDto();
					sub.setId(auditDto.getLoanBankSubId());
					RespDataObject<BankSubDto> subBankDto =dataApi.getSubBankNameById(sub);
					auditDto.setLoanBankSubName(subBankDto == null ?"":subBankDto.getData().getName());
				}
			}
			if(null!=firstAudit.getFirstPaymentAuditList() && firstAudit.getFirstPaymentAuditList().size()>0){
				for(AuditFirstPaymentDto auditDto:firstAudit.getFirstPaymentAuditList()){
					BankDto bank = new BankDto();
					bank.setId(auditDto.getPaymentBankId());
					RespDataObject<BankDto> bankDto =dataApi.getBankNameById(bank);
					auditDto.setPaymentBankName(bankDto==null ? "":bankDto.getData().getName());
					BankSubDto sub = new BankSubDto();
					sub.setId(auditDto.getPaymentBankSubId());
					RespDataObject<BankSubDto> subBankDto =dataApi.getSubBankNameById(sub);
					auditDto.setPaymentBankSubName(subBankDto == null ?"":subBankDto.getData().getName());
				}
			}
			BaseBorrowDto dto=new BaseBorrowDto();
			dto.setOrderNo(firstAudit.getOrderNo());
			BaseBorrowDto baseBorrowDto = baseBorrowService.find(dto);
			if(null!=firstAudit.getOther() && StringUtil.isEmpty(firstAudit.getProductName())){
				if(baseBorrowDto!=null){
					firstAudit.setProductName(baseBorrowDto.getProductName());
				}
			}
			firstAudit.setCreateUid(userDto.getUid());
			firstAudit.setAuditTime(new Date());
			firstAudit.setHandleUid(userDto.getUid());
			firstAudit.setAuditStatus(3);
			AuditFirstDto temp = new AuditFirstDto();
			temp.setOrderNo(firstAudit.getOrderNo());
			temp = auditFirstService.find(temp);
			if(temp==null){
				auditFirstService.insert(firstAudit);
			}else{
				auditFirstService.update(firstAudit);
			}
			if(null!=firstAudit.getForeclosureAuditList()&&firstAudit.getForeclosureAuditList().size()>0){
				auditFirstService.delFirstForeclosure(firstAudit.getOrderNo());
				for(AuditFirstForeclosureDto  auditDto:firstAudit.getForeclosureAuditList()){
					auditDto.setOrderNo(firstAudit.getOrderNo());
					auditDto.setCreateUid(userDto.getUid());
					auditDto.setUpdateUid(userDto.getUid());
					auditFirstService.addFirstForeclosure(auditDto);
				}
			}
			if(null!=firstAudit.getFirstPaymentAuditList()&&firstAudit.getFirstPaymentAuditList().size()>0){
				auditFirstService.delFirstPayment(firstAudit.getOrderNo());
				for(AuditFirstPaymentDto auditDto:firstAudit.getFirstPaymentAuditList()){
					auditDto.setOrderNo(firstAudit.getOrderNo());
					auditDto.setCreateUid(userDto.getUid());
					auditDto.setUpdateUid(userDto.getUid());
					auditFirstService.addFirstPayment(auditDto);
				}
			}
			
			
			FlowDto next = new FlowDto();
			next.setOrderNo(firstAudit.getOrderNo());
			next.setCurrentProcessId("auditFirst");
			next.setCurrentProcessName("风控初审");
			if("06".equals(baseBorrowDto.getProductCode())||"07".equals(baseBorrowDto.getProductCode())) {
				next.setNextProcessId("auditReview");
				next.setNextProcessName("复核审批");
			}else {
				next.setNextProcessId("auditFinal");
				next.setNextProcessName("风控终审");
			}
			next.setHandleUid(userDto.getUid());
			next.setHandleName(userDto.getName());
			next.setUpdateUid(userDto.getUid());
			
			BaseListDto orderListDto = new BaseListDto();
			orderListDto.setOrderNo(firstAudit.getOrderNo());
			if(StringUtils.isNotEmpty(firstAudit.getNextHandleUid())) {
				UserDto nextUser = userApi.findUserDtoByUid(firstAudit.getNextHandleUid());
				orderListDto.setCurrentHandlerUid(nextUser.getUid());
				orderListDto.setCurrentHandler(nextUser.getName());
			}
			goNextNode(next,orderListDto);//进入下一个节点
			return RespHelper.setSuccessRespStatus(result);
		}catch (Exception e) {
			logger.error("提交异常,参数："+firstAudit.toString(), e);
			return RespHelper.failRespStatus();
		}
	}
	
	/**
	 * 详情
	 * @author lic 
	 */
	@Override
	public RespDataObject<AuditFirstDto> processDetails(@RequestBody AuditFirstDto firstAudit) {
		RespDataObject<AuditFirstDto> result = new RespDataObject<AuditFirstDto>();
		try {
//			if(StringUtils.isBlank(firstAudit.getOrderNo())){
//				result.setMsg("查询初审详情参数缺失,查询失败!");
//				return result;
//			}
			AuditFirstDto obj = auditFirstService.detail(firstAudit.getOrderNo());
			if(null!=obj.getOther() && StringUtil.isEmpty(obj.getProductName())){
				BaseBorrowDto dto=new BaseBorrowDto();
				dto.setOrderNo(firstAudit.getOrderNo());
				BaseBorrowDto baseBorrowDto = baseBorrowService.find(dto);
				if(baseBorrowDto!=null){
					obj.setProductName(baseBorrowDto.getProductName());
				}
			}
			UserDto user = null;
			if(StringUtils.isNotBlank(obj.getHandleUid())){
				user = userApi.findUserDtoByUid(obj.getHandleUid());
			}
			if(null!=user&&null!=obj){
				obj.setHandleName(user.getName());
			}
			if(null!=obj){
				//new 出/回款添加多行  2018-3-22
				List<AuditFirstForeclosureDto> foreclosureAuditList=auditFirstService.findforeclosureList(firstAudit.getOrderNo());
				obj.setForeclosureAuditList(foreclosureAuditList);
				List<AuditFirstPaymentDto> PaymentAuditList = auditFirstService.findPaymentList(firstAudit.getOrderNo());
				obj.setFirstPaymentAuditList(PaymentAuditList);
			}
			if(null !=firstAudit.getForeclosureAuditList() &&firstAudit.getForeclosureAuditList().size()>0){
				for(AuditFirstForeclosureDto auditDto:firstAudit.getForeclosureAuditList()){
					BankDto bank = new BankDto();
					bank.setId(auditDto.getLoanBankId());
					RespDataObject<BankDto> bankDto =dataApi.getBankNameById(bank);
					auditDto.setLoanBankName(bankDto==null ? "":bankDto.getData().getName());
					BankSubDto sub = new BankSubDto();
					sub.setId(auditDto.getLoanBankSubId());
					RespDataObject<BankSubDto> subBankDto =dataApi.getSubBankNameById(sub);
					auditDto.setLoanBankSubName(subBankDto == null ?"":subBankDto.getData().getName());
				}
			}
			if(null!=firstAudit.getFirstPaymentAuditList() && firstAudit.getFirstPaymentAuditList().size()>0){
				for(AuditFirstPaymentDto auditDto:firstAudit.getFirstPaymentAuditList()){
					BankDto bank = new BankDto();
					bank.setId(auditDto.getPaymentBankId());
					RespDataObject<BankDto> bankDto =dataApi.getBankNameById(bank);
					auditDto.setPaymentBankName(bankDto==null ? "":bankDto.getData().getName());
					BankSubDto sub = new BankSubDto();
					sub.setId(auditDto.getPaymentBankSubId());
					RespDataObject<BankSubDto> subBankDto =dataApi.getSubBankNameById(sub);
					auditDto.setPaymentBankSubName(subBankDto == null ?"":subBankDto.getData().getName());
				}
			}
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
			return RespHelper.setSuccessDataObject(result,obj);
		}catch (Exception e) {
			logger.error("详情异常,参数："+firstAudit.toString(), e);
			return RespHelper.setFailDataObject(result, null, RespStatusEnum.FAIL.getMsg());
		}
	}
		
}