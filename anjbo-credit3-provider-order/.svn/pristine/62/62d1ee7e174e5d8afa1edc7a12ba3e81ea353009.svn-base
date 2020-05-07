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
import com.anjbo.bean.risk.AuditFirstDto;
import com.anjbo.bean.risk.AuditReviewDto;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.OrderBaseController;
import com.anjbo.controller.api.UserApi;
import com.anjbo.controller.risk.IAuditReviewController;
import com.anjbo.service.order.BaseBorrowService;
import com.anjbo.service.order.BaseListService;
import com.anjbo.service.risk.AuditFinalService;
import com.anjbo.service.risk.AuditFirstService;
import com.anjbo.service.risk.AuditReviewService;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 19:44:33
 * @version 1.0
 */
@RestController
public class AuditReviewController extends OrderBaseController implements IAuditReviewController{

	@Resource private AuditReviewService auditReviewService;
	
	@Resource private UserApi userApi;
	
	@Resource private BaseBorrowService baseBorrowService;
	
	@Resource private AuditFirstService auditFirstService;
	
	@Resource private AuditFinalService auditFinalService;
	
	@Resource private BaseListService baseListService;

	/**
	 * 提交（复核审批通过）
	 * @author lic 
	 */
	@Override
	public RespStatus processSubmit(@RequestBody AuditReviewDto reviewAuditDto) {
		RespStatus resp = new RespStatus();
		try {
			UserDto userDto = userApi.getUserDto();
			reviewAuditDto.setCreateUid(userDto.getUid());
			reviewAuditDto.setUpdateUid(userDto.getUid());
			
			AuditFinalDto finalDto = new AuditFinalDto();
			finalDto.setOrderNo(reviewAuditDto.getOrderNo());
			AuditFinalDto finalAuditDto=auditFinalService.find(finalDto);
			//判断建议费率只能改大，不能改小
			String orderNo = reviewAuditDto.getOrderNo();
			AuditReviewDto reviewAudit = new AuditReviewDto();
			BaseBorrowDto dto=new BaseBorrowDto();
			dto.setOrderNo(reviewAuditDto.getOrderNo());
			BaseBorrowDto baseBorrowDto = baseBorrowService.find(dto);
			if("01".equals(baseBorrowDto.getProductCode()) || "02".equals(baseBorrowDto.getProductCode())
					|| "05".equals(baseBorrowDto.getProductCode())|| "06".equals(baseBorrowDto.getProductCode())
					|| "07".equals(baseBorrowDto.getProductCode())){
				AuditFirstDto firstAuditDto=auditFirstService.detail(orderNo);
				reviewAudit.setRate(firstAuditDto.getRate());
				reviewAudit.setOverdueRate(firstAuditDto.getOverdueRate());
			}else{
				reviewAudit.setRate(baseBorrowDto.getRate());
				reviewAudit.setOverdueRate(baseBorrowDto.getOverdueRate());
			}
			if(!"06".equals(baseBorrowDto.getProductCode())
					&& !"07".equals(baseBorrowDto.getProductCode())) {
				reviewAuditDto.setNextHandleUid(finalAuditDto.getAllocationFundUid());
			}
			//选择的资金分配员
			reviewAuditDto.setCreateUid(userDto.getUid());
			reviewAuditDto.setAuditTime(new Date());
			reviewAuditDto.setHandleUid(userDto.getUid());
			reviewAuditDto.setAuditStatus(1);
			
			AuditReviewDto temp = new AuditReviewDto();
			temp.setOrderNo(reviewAuditDto.getOrderNo());
			temp = auditReviewService.find(temp);
			if(temp==null){
				auditReviewService.insert(reviewAuditDto);
			}else{
				auditReviewService.update(reviewAuditDto);
			}
			FlowDto next = new FlowDto();
			next.setOrderNo(reviewAuditDto.getOrderNo());
			next.setCurrentProcessId("auditReview");
			next.setCurrentProcessName("复核审批");
			next.setHandleUid(userDto.getUid());
			next.setHandleName(userDto.getName());
			next.setUpdateUid(userDto.getUid());
			
			BaseListDto baseListDto = new BaseListDto();
			baseListDto.setOrderNo(reviewAuditDto.getOrderNo());
			baseListDto = baseListService.find(baseListDto);
			if("05".equals(baseListDto.getProductCode())) {//提放，提交流程到签订投保单(受理员操作)
				next.setNextProcessId("signInsurancePolicy");
				next.setNextProcessName("签订投保单");
				BaseListDto orderListDto = new BaseListDto();
				orderListDto.setOrderNo(reviewAuditDto.getOrderNo());
				orderListDto.setCurrentHandler(baseListDto.getAcceptMemberName());
				orderListDto.setCurrentHandlerUid(baseListDto.getAcceptMemberUid());	
				goNextNode(next,orderListDto);
			}else {
				next.setNextProcessId("allocationFund");
				next.setNextProcessName("推送金融机构");
				BaseListDto orderListDto = new BaseListDto();
				orderListDto.setOrderNo(reviewAuditDto.getOrderNo());
				if(StringUtils.isNotEmpty(reviewAuditDto.getNextHandleUid())) {
					UserDto nextUser = userApi.findUserDtoByUid(reviewAuditDto.getNextHandleUid());
					orderListDto.setCurrentHandler(nextUser.getName());
					orderListDto.setCurrentHandlerUid(nextUser.getUid());	
				}
				goNextNode(next,orderListDto);//进入下一个节点
			}
			return RespHelper.setSuccessRespStatus(resp);
		}catch (Exception e) {
			logger.error("提交异常,参数："+reviewAuditDto.toString(), e);
			return RespHelper.failRespStatus();
		}
	}
	
	/**
	 * 提交（风控复核审批提交到首席风险官）
	 * @author lic 
	 */
	@Override
	public RespStatus processReportOfficer(@RequestBody AuditReviewDto reviewAuditDto) {
		RespStatus resp = new RespStatus();
		try {
			UserDto userDto = userApi.getUserDto();
			reviewAuditDto.setCreateUid(userDto.getUid());
			reviewAuditDto.setUpdateUid(userDto.getUid());
			
			AuditFinalDto finalDto = new AuditFinalDto();
			finalDto.setOrderNo(reviewAuditDto.getOrderNo());
			AuditFinalDto finalAuditDto=auditFinalService.find(finalDto);
			//判断建议费率只能改大，不能改小
			String orderNo = reviewAuditDto.getOrderNo();
			AuditReviewDto reviewAudit = new AuditReviewDto();
			BaseBorrowDto dto=new BaseBorrowDto();
			dto.setOrderNo(reviewAuditDto.getOrderNo());
			BaseBorrowDto baseBorrowDto = baseBorrowService.find(dto);
			if("01".equals(baseBorrowDto.getProductCode()) || "02".equals(baseBorrowDto.getProductCode())
					|| "05".equals(baseBorrowDto.getProductCode())){
				AuditFirstDto firstAuditDto=auditFirstService.detail(orderNo);
				reviewAudit.setRate(firstAuditDto.getRate());
				reviewAudit.setOverdueRate(firstAuditDto.getOverdueRate());
			}else{
				reviewAudit.setRate(baseBorrowDto.getRate());
				reviewAudit.setOverdueRate(baseBorrowDto.getOverdueRate());
			}
			
			reviewAuditDto.setNextHandleUid(finalAuditDto.getAllocationFundUid());
			//选择的资金分配员
			reviewAuditDto.setCreateUid(userDto.getUid());
			reviewAuditDto.setAuditTime(new Date());
			reviewAuditDto.setHandleUid(userDto.getUid());
			reviewAuditDto.setAuditStatus(1);
			
			AuditReviewDto temp = new AuditReviewDto();
			temp.setOrderNo(reviewAuditDto.getOrderNo());
			temp = auditReviewService.find(temp);
			if(temp==null){
				auditReviewService.insert(reviewAuditDto);
			}else{
				auditReviewService.update(reviewAuditDto);
			}
			FlowDto next = new FlowDto();
			next.setOrderNo(reviewAuditDto.getOrderNo());
			next.setCurrentProcessId("auditReview");
			next.setCurrentProcessName("复核审批");
			next.setNextProcessId("auditOfficer");
			next.setNextProcessName("首席风险官审批");
			next.setHandleUid(userDto.getUid());
			next.setHandleName(userDto.getName());
			next.setUpdateUid(userDto.getUid());
			
			BaseListDto orderListDto = new BaseListDto();
			orderListDto.setOrderNo(reviewAuditDto.getOrderNo());
			if(StringUtils.isNotEmpty(finalAuditDto.getOfficerUid())) {
				UserDto nextUser = userApi.findUserDtoByUid(finalAuditDto.getOfficerUid());
				orderListDto.setCurrentHandler(nextUser.getName());
				orderListDto.setCurrentHandlerUid(nextUser.getUid());	
			}
			goNextNode(next,orderListDto);//进入下一个节点
			return RespHelper.setSuccessRespStatus(resp);
		}catch (Exception e) {
			logger.error("提交异常,参数："+reviewAuditDto.toString(), e);
			return RespHelper.failRespStatus();
		}
	}
	
	/**
	 * 详情
	 * @author lic 
	 */
	@Override
	public RespDataObject<AuditReviewDto> processDetails(@RequestBody AuditReviewDto reviewAuditDto) {
		RespDataObject<AuditReviewDto> result = new RespDataObject<AuditReviewDto>();
		try {
			AuditReviewDto reviewAudit = auditReviewService.find(reviewAuditDto);
			BaseBorrowDto dto=new BaseBorrowDto();
			dto.setOrderNo(reviewAuditDto.getOrderNo());
			BaseBorrowDto baseBorrowDto=  baseBorrowService.find(dto);
			if(baseBorrowDto!=null){
				reviewAudit.setLoanAmont(baseBorrowDto.getLoanAmount());
				reviewAudit.setBorrowingDays(baseBorrowDto.getBorrowingDays());
			}
			//初审出款金额
			AuditFirstDto temp = new AuditFirstDto();
			temp.setOrderNo(reviewAuditDto.getOrderNo());
			temp = auditFirstService.find(temp);
			if(temp!=null&&temp.getSunMoney()!=null&&temp.getSunMoney()>0) {
				reviewAudit.setLoanAmont(temp.getSunMoney());
			}
			UserDto user = null;
			if(StringUtils.isNotBlank(reviewAudit.getHandleUid())){
				user = userApi.findUserDtoByUid(reviewAudit.getHandleUid());
				if(null!=user&&null!=reviewAudit){
					reviewAudit.setHandleName(user.getName());
				}
			}
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
			return RespHelper.setSuccessDataObject(result,reviewAudit);
		}catch (Exception e) {
			logger.error("详情异常,参数："+reviewAuditDto.toString(), e);
			return RespHelper.setFailDataObject(result, null, RespStatusEnum.FAIL.getMsg());
		}
	}
		
}