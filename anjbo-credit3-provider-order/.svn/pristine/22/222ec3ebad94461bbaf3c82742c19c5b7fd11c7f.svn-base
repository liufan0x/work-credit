/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller.impl.risk;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.anjbo.bean.UserDto;
import com.anjbo.bean.order.BaseBorrowDto;
import com.anjbo.bean.order.BaseListDto;
import com.anjbo.bean.order.FlowDto;
import com.anjbo.bean.process.DistributionMemberDto;
import com.anjbo.bean.risk.AuditDataAuditDto;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.OrderBaseController;
import com.anjbo.controller.api.UserApi;
import com.anjbo.controller.risk.IAuditDataAuditController;
import com.anjbo.service.order.BaseBorrowService;
import com.anjbo.service.order.BaseListService;
import com.anjbo.service.process.DistributionMemberService;
import com.anjbo.service.risk.AuditDataAuditService;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 19:44:31
 * @version 1.0
 */
@RestController
public class AuditDataAuditController extends OrderBaseController implements IAuditDataAuditController{

	@Resource private AuditDataAuditService auditDataAuditService;
	
	@Resource private UserApi userApi;
	
	@Resource private BaseBorrowService baseBorrowService;
	
	@Resource private DistributionMemberService distributionMemberService;
	
	@Resource private BaseListService baseListService;

	/**
	 * 提交
	 * @author lic 
	 */
	@Override
	public RespStatus processSubmit(@RequestBody AuditDataAuditDto auditDto) {
		RespStatus resp = new RespStatus();
		try {
			UserDto user = userApi.getUserDto();
			auditDto.setCreateUid(user.getUid());
			auditDto.setUpdateUid(user.getUid());
			
			AuditDataAuditDto temp = new AuditDataAuditDto();
			temp.setOrderNo(auditDto.getOrderNo());
			temp = auditDataAuditService.find(temp);
			if(temp==null){
				auditDataAuditService.insert(auditDto);
			}else{
				auditDataAuditService.update(auditDto);
			}
			
			FlowDto next = new FlowDto();
			BaseListDto orderListDto = new BaseListDto();
			next.setOrderNo(auditDto.getOrderNo());
			next.setCurrentProcessId("dataAudit");
			next.setCurrentProcessName("资料审核");
			next.setHandleUid(user.getUid());
			next.setHandleName(user.getName());
			next.setUpdateUid(user.getUid());
			//获取当前受理员uid
			BaseBorrowDto orderBaseBorrowDto=new BaseBorrowDto();
			orderBaseBorrowDto.setOrderNo(auditDto.getOrderNo());
			BaseBorrowDto baseBorrowDto=  baseBorrowService.find(orderBaseBorrowDto);
			if("04".equals(baseBorrowDto.getProductCode())){ //房抵贷 V3.4.0
				next.setNextProcessId("fddMortgage");
				next.setNextProcessName("抵押");
				UserDto nextUser = userApi.findUserDtoByUid(baseBorrowDto.getAcceptMemberUid());
				orderListDto.setCurrentHandlerUid(nextUser.getUid());
				orderListDto.setCurrentHandler(nextUser.getName());
			}else{
				//获取下一处理人 还款专员  ---V3.0.1版本
				DistributionMemberDto memberDto = new DistributionMemberDto();
				memberDto.setOrderNo(auditDto.getOrderNo());
				DistributionMemberDto member=  distributionMemberService.find(memberDto);
				UserDto nextUser = null;
				if(member!=null && member.getForeclosureMemberUid()!=null){
					logger.info("还款专员uid:"+member.getForeclosureMemberUid());
					nextUser = userApi.findUserDtoByUid(member.getForeclosureMemberUid());
				}
				next.setNextProcessId("applyLoan");
				next.setNextProcessName("申请放款");
				if(nextUser==null){
					nextUser = userApi.findUserDtoByUid(baseBorrowDto.getAcceptMemberUid());
				}
				orderListDto.setCurrentHandlerUid(nextUser.getUid());
				orderListDto.setCurrentHandler(nextUser.getName());
			}
			
			orderListDto.setOrderNo(auditDto.getOrderNo());
			goNextNode(next,orderListDto);
			
			return RespHelper.setSuccessRespStatus(resp);
		}catch (Exception e) {
			logger.error("提交异常,参数："+auditDto.toString(), e);
			return RespHelper.failRespStatus();
		}
	}
	
	/**
	 * 详情
	 * @author lic 
	 */
	@Override
	public RespDataObject<AuditDataAuditDto> processDetails(@RequestBody AuditDataAuditDto auditDto) {
		RespDataObject<AuditDataAuditDto> result = new RespDataObject<AuditDataAuditDto>();
		try {
//			if(StringUtils.isBlank(auditDto.getOrderNo())){
//				result.setMsg("加载审核信息异常,缺少订单编号");
//				return result;
//			}
			BaseListDto listDto=new BaseListDto();
			listDto.setOrderNo(auditDto.getOrderNo());
			listDto = baseListService.find(listDto);
			if(StringUtils.isNotEmpty(listDto.getRelationOrderNo())){
				auditDto.setOrderNo(listDto.getRelationOrderNo());
			}
			auditDto=auditDataAuditService.find(auditDto);
			if(auditDto!=null){
				UserDto userDto = userApi.findUserDtoByUid(auditDto.getCreateUid());
				auditDto.setName(userDto.getName());
			}
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
			return RespHelper.setSuccessDataObject(result,auditDto);
		}catch (Exception e) {
			logger.error("详情异常,参数："+auditDto.toString(), e);
			return RespHelper.setFailDataObject(result, null, RespStatusEnum.FAIL.getMsg());
		}
	}
	
		
}