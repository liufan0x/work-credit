package com.anjbo.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anjbo.bean.element.AuditBaseDto;
import com.anjbo.bean.element.AuditFlowDto;
import com.anjbo.bean.element.ElementSystemMessageDto;
import com.anjbo.bean.element.vo.SystemMsgVO;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.Constants;
import com.anjbo.dao.ElementSystemMessageMapper;
import com.anjbo.service.ElementSystemMessageService;
import com.anjbo.util.DingtalkUtil;
import com.anjbo.utils.AmsUtil;
import com.anjbo.utils.CommonDataUtil;
import com.anjbo.utils.ConfigUtil;

/**
  * 系统消息表 [Service实现类]
  * @ClassName: ElementSystemMessageServiceImpl
  * @Description: 系统消息表业务服务
  * @author 
  * @date 2017-12-21 15:01:44
  * @version V3.0
 */
@Service
public class ElementSystemMessageServiceImpl  implements ElementSystemMessageService
{
	
	@Resource
	private ElementSystemMessageMapper elementSystemMessageMapper;
	
	
	@Override
	public void addToAuditMsg(AuditFlowDto dto, UserDto nextAuditor) {
		AuditBaseDto auditBaseDto = dto.getAuditBase();
		ElementSystemMessageDto msgDto = new ElementSystemMessageDto();
		msgDto.setDbId(dto.getDbId());
		msgDto.setMessageType(2);
		msgDto.setCreateTime(new Date());
		msgDto.setApplicantName(auditBaseDto.getApplierName());
		msgDto.setApplicantUid(auditBaseDto.getCreateUid());
		msgDto.setOrderNo(auditBaseDto.getOrderNo());
		msgDto.setBeginBorrowElementTime(auditBaseDto.getBeginTime());
		if(auditBaseDto.getNewEndTime()!=null) {
			msgDto.setEndBorrowElementTime(auditBaseDto.getNewEndTime());
		}else {
			msgDto.setEndBorrowElementTime(auditBaseDto.getEndTime());
		}
		msgDto.setAuditState(1);
		msgDto.setCustomerName(auditBaseDto.getCustomerName());
		msgDto.setHasRead(0);
		msgDto.setReceiverUid(dto.getAuditorUid());
		String title = String.format("%s的借%s审批申请需要您的审批,请知晓", auditBaseDto.getApplierName(), auditBaseDto.getType()==3?"公章":"要件");
		msgDto.setTitle(title);
		msgDto.setRiskElement(auditBaseDto.getRiskElement());
		msgDto.setReceivableElements(auditBaseDto.getReceivableElement());
		msgDto.setPublicSeal(auditBaseDto.getPublicSeal());
		elementSystemMessageMapper.addElementSystemMessage(msgDto);
		//推送钉钉消息及发送短信给审批人
		String smsIpWhite = ConfigUtil.getStringValue(Constants.BASE_AMS_IPWHITE,ConfigUtil.CONFIG_BASE);
		AmsUtil.smsSend2(nextAuditor.getMobile(),smsIpWhite, Constants.SMS_ELEMENT_AUDIT_AUDITNOTICE,"hyt", msgDto.getApplicantName(),auditBaseDto.getType()==3?"公章":"要件");
		pushDingtalkMsg(msgDto, Arrays.asList(nextAuditor.getMobile()));
	}

	@Override
	public void addAuditResultMsg(AuditBaseDto baseDto, boolean pass) {
		ElementSystemMessageDto msgDto = new ElementSystemMessageDto();
		Date time = new Date();
		msgDto.setCreateTime(time);
		msgDto.setDbId(baseDto.getId());
		msgDto.setMessageType(4);
		msgDto.setApplicantName(baseDto.getApplierName());
		msgDto.setApplicantUid(baseDto.getCreateUid());
		msgDto.setOrderNo(baseDto.getOrderNo());
		msgDto.setBeginBorrowElementTime(baseDto.getBeginTime());
		msgDto.setEndBorrowElementTime(baseDto.getEndTime());
		msgDto.setReceiverUid(baseDto.getCreateUid());
		msgDto.setCustomerName(baseDto.getCustomerName());
		msgDto.setAuditState(pass?2:3);
		String title = String.format("您的借%s审批申请已%s,请知晓",baseDto.getType()==3?"公章":"要件", pass?"同意":"拒绝");
		msgDto.setTitle(title);
		msgDto.setRiskElement(baseDto.getRiskElement());
		msgDto.setReceivableElements(baseDto.getReceivableElement());
		msgDto.setPublicSeal(baseDto.getPublicSeal());
		UserDto applier = CommonDataUtil.getUserDtoByUidAndMobile(baseDto.getCreateUid());
		elementSystemMessageMapper.addElementSystemMessage(msgDto);
		pushDingtalkMsg(msgDto, Arrays.asList(applier.getMobile()));
		if(pass){//审批通过,添加抄送
			String[] uids = baseDto.getCopyTo().split(",");
			title = String.format("%s提交的借%s审批申请流程已通过,抄送给你,请知晓",baseDto.getApplierName(),baseDto.getType()==3?"公章":"要件");
			List<String> mobileList = new ArrayList<String>();
			ElementSystemMessageDto cpMsgDto = new ElementSystemMessageDto();
			cpMsgDto.setDbId(baseDto.getId());
			cpMsgDto.setMessageType(3);
			cpMsgDto.setTitle(title);
			cpMsgDto.setCreateTime(time);
			cpMsgDto.setCustomerName(msgDto.getCustomerName());
			cpMsgDto.setApplicantName(baseDto.getApplierName());
			cpMsgDto.setApplicantUid(baseDto.getCreateUid());
			cpMsgDto.setOrderNo(baseDto.getOrderNo());
			cpMsgDto.setRiskElement(msgDto.getRiskElement());
			cpMsgDto.setReceivableElements(msgDto.getReceivableElements());
			cpMsgDto.setPublicSeal(baseDto.getPublicSeal());
			cpMsgDto.setBeginBorrowElementTime(baseDto.getBeginTime());
			cpMsgDto.setEndBorrowElementTime(baseDto.getEndTime());
			cpMsgDto.setAuditState(2);
			for (String uid : uids) {
				UserDto userDto = CommonDataUtil.getUserDtoByUidAndMobile(uid);
				if(userDto!=null){
					cpMsgDto.setReceiverUid(userDto.getUid());
					mobileList.add(userDto.getMobile());
					elementSystemMessageMapper.addElementSystemMessage(cpMsgDto);
				}
			}
			pushDingtalkMsg(cpMsgDto, mobileList);
		}
	}

	@Override
	public Integer selectUnreadCount(String uid) {
		return elementSystemMessageMapper.selectUnreadCount(uid);
	}

	@Override
	public Integer selectUnreadCopyAuditCount(String uid) {
		return elementSystemMessageMapper.selectUnreadCopyAuditCount(uid);
	}

	@Override
	public void updateAuditMsgState(boolean pass, AuditBaseDto auditBase,UserDto auditor) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("state", pass?2:3);
		params.put("id", auditBase.getId());
		params.put("uid", auditor.getUid());
		elementSystemMessageMapper.updateAuditMsgState(params);
		//添加审批结果推送消息
		pushDingtalkResultMsg(auditBase, auditor.getMobile(), pass);
	}

	@Override
	public void updateCopyMsgState(Integer id, String uid) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("id", id);
		params.put("uid", uid);
		elementSystemMessageMapper.updateCopyMsgState(params);
	}

	@Override
	public ElementSystemMessageDto getById(Integer id) {
		return elementSystemMessageMapper.selectElementSystemMessageById(id);
	}

	@Override
	public List<SystemMsgVO> listByUid(Map<String, Object> params) {
		return elementSystemMessageMapper.listByUid(params);
	}
	
	/**
	 * 
	 * @Title: pushDingtalkMsg 
	 * @Description: 推送审批消息到钉钉
	 * @param @param msgDto
	 * @param @param mobile    设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	private void pushDingtalkMsg(ElementSystemMessageDto msgDto, List<String> mobileList) {
		Map<String,String> contentMap = new LinkedHashMap<String,String>();
		contentMap.put("申请人", msgDto.getApplicantName());
		contentMap.put("客户", msgDto.getCustomerName());
		if(msgDto.getRiskElement()!=null){
			contentMap.put("申请提取的风控要件", msgDto.getRiskElement());
		}
		if(msgDto.getReceivableElements()!=null){
			contentMap.put("申请借用的回款要件", msgDto.getReceivableElements());
		}
		if(msgDto.getPublicSeal()!=null){
			contentMap.put("申请借用的公章", msgDto.getPublicSeal());
		}
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		contentMap.put("开始时间", fmt.format(msgDto.getBeginBorrowElementTime()));
		contentMap.put("结束时间", fmt.format(msgDto.getEndBorrowElementTime()));
		DingtalkUtil.pushMsg(contentMap, msgDto.getTitle(), mobileList,msgDto.getDbId(),msgDto.getId());
	}
	/**
	 * 
	 * @Title: pushDingtalkResultMsg 
	 * @Description: 推送审批审批人审批操作结果到钉钉
	 * @param @param msgDto
	 * @param @param mobile    设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	private void pushDingtalkResultMsg(AuditBaseDto auditBase, String mobile, boolean pass){
		String title = String.format("您已%s%s", pass?"同意":"拒绝",auditBase.getTitle());
		String content = "点击查看"+auditBase.getTitle()+"详情";
		DingtalkUtil.pushResult(content, title, Arrays.asList(mobile), auditBase.getId());
	}

	@Override
	public void addMsg(ElementSystemMessageDto msgDto) {
		elementSystemMessageMapper.addElementSystemMessage(msgDto);
		//TODO
	}

	@Override
	public void updateElementSystemMessage(ElementSystemMessageDto msgdto) {
		elementSystemMessageMapper.updateElementSystemMessage(msgdto);
		
	}

	@Override
	public Integer unreadApplayCount(String uid) {
		return elementSystemMessageMapper.unreadApplayCount(uid);
	}

	@Override
	public void updateMessageHasRead(Integer msgId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("msgId", msgId);
		elementSystemMessageMapper.updateMessageHasRead(map);
	}

	@Override
	public String selectTitle(Map<String, Object> params) {
		return elementSystemMessageMapper.selectTitle(params);
	}
}