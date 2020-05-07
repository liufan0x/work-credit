package com.anjbo.job;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.anjbo.bean.element.AuditBaseDto;
import com.anjbo.bean.element.ElementSystemMessageDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.Constants;
import com.anjbo.common.DateUtil;
import com.anjbo.service.AuditBaseService;
import com.anjbo.service.ElementFileService;
import com.anjbo.service.ElementSystemMessageService;
import com.anjbo.utils.AmsUtil;
import com.anjbo.utils.CommonDataUtil;
import com.anjbo.utils.ConfigUtil;

/**
 * 
 * @ClassName: BorrowedOverTimeJob 
 * @Description: 超时未还提醒定时任务
 * @author A18ccms a18ccms_gmail_com 
 * @date 2018年1月3日 上午9:23:14 
 *
 */
@Component
public class BorrowedOverTimeJob {
	
	private Log log = LogFactory.getLog(BorrowedOverTimeJob.class);
	@Resource
	private ElementSystemMessageService sysMsgSevice;
	@Resource
	private AuditBaseService auditBaseService;
	@Resource
	private ElementFileService elementFileService;
	/**
	 * 
	 * @Title: addNotice 
	 * @Description: 超时未还检查,添加系统消息和标记订单超时
	 * @return void    返回类型 
	 * @throws
	 */
	@Scheduled(cron="0 0/3 * * * ? ")
	public void addNotice(){
		try {
			log.info("执行超时未还检查");
			checkAuditOverTime();
			checkOperationOverTime();
		} catch (Exception e) {
			log.error("超时未还检查发生异常!",e);
		}
	}
	/**
	 * 
	 * @Title: sendOverTimeSms 
	 * @Description: 超时未还检查,发送短信任务
	 * @return void    返回类型 
	 * @throws
	 */
	@Scheduled(cron="30 0/3 9-20 * * ? ")
	public void sendOverTimeSms(){
		try {
			log.info("执行超时未还短信通知检查!");
			sendSmsForOverTimeOperation();
			sendSmsForAuditOverTime();
		} catch (Exception e) {
			log.error("超时未还发送通知短信发生异常!",e);
		}
	}
	//发送审批借用超时未还提醒短信
	private void sendSmsForAuditOverTime() {
		/*------------------------超时后通知借用者----------------------------*/
		List<AuditBaseDto> auditList = auditBaseService.selectBorrowedOverTimeAudits(2);//未发送短信给借用者的超时记录
		for (AuditBaseDto auditBaseDto : auditList) {
			log.info(auditBaseDto.getTitle()+"超时未还!");
			String eleDescrib = getEleDescribFromSysmsgTitle(auditBaseDto);
			if(eleDescrib!=null){
				UserDto applier = CommonDataUtil.getUserDtoByUidAndMobile(auditBaseDto.getCreateUid());
				String timeStr = DateUtil.getDateByFmt(auditBaseDto.getEndTime(), "yyyy-MM-dd HH:mm");
				//借要件的人提醒短信
				String smsIpWhite = ConfigUtil.getStringValue(Constants.BASE_AMS_IPWHITE,ConfigUtil.CONFIG_BASE);	
				AmsUtil.smsSend2(applier.getMobile(),smsIpWhite, Constants.SMS_ELEMENT_AUDIT_BORROWWARN,"hyt", eleDescrib,timeStr);
				auditBaseService.updateBorrowedOverTimeMsgState(auditBaseDto.getId(),1,1);//标记已短信提醒借用者
			}
		}
		
		/*-------------------超时24小时通知要件管理员和审批人员--------------------*/
		List<AuditBaseDto> auditList2 = auditBaseService.selectBorrowedOverTimeAudits(3);//已送短信给借用者,但未发送短信通知要件管理员和审批人员的超时记录
		for (AuditBaseDto auditBaseDto : auditList2) {
			log.info(auditBaseDto.getTitle()+"超时未还24小时!");
			String eleDescrib = getEleDescribFromSysmsgTitle(auditBaseDto);
			if(eleDescrib!=null){
				UserDto applier = CommonDataUtil.getUserDtoByUidAndMobile(auditBaseDto.getCreateUid());
				String timeStr = DateUtil.getDateByFmt(auditBaseDto.getEndTime(), "yyyy-MM-dd HH:mm");
				List<String> uids = auditBaseService.selectBorrowedOverTimeReceiverUid(auditBaseDto.getId());
				uids.add(auditBaseService.selectBorrowOperatorUid(auditBaseDto.getId(),1));
				Set<String> uidSet = new HashSet<String>();
				uidSet.addAll(uids);
				for (String uid : uidSet) {
					UserDto userDto = CommonDataUtil.getUserDtoByUidAndMobile(uid);
					if(userDto!=null){
						String phone = userDto.getMobile();
						if(StringUtils.isNotBlank(phone)){
							//审批人,要件管理员提醒短信
							String smsIpWhite = ConfigUtil.getStringValue(Constants.BASE_AMS_IPWHITE,ConfigUtil.CONFIG_BASE);
							AmsUtil.smsSend2(phone,smsIpWhite, Constants.SMS_ELEMENT_AUDIT_BORROWNOTICE, "hyt",
									auditBaseDto.getApplierName(),eleDescrib,timeStr,applier.getMobile());
						}
					}
				}
				auditBaseService.updateBorrowedOverTimeMsgState(auditBaseDto.getId(),1,2);//标记已短信提醒要件管理员和审批人
			}
		}
	}
	//发送开箱超时未还提醒短信
	private void sendSmsForOverTimeOperation() {
		List<Map<String,Object>> OperationList = auditBaseService.selectBorrowedOverTimeOperation(2);//开箱超时未还
		for (Map<String, Object> map : OperationList) {
			Integer elementOperationId = MapUtils.getInteger(map, "id");
			String uid = MapUtils.getString(map, "currentHandlerUid");
			UserDto user = CommonDataUtil.getUserDtoByUidAndMobile(uid);
			log.info(user.getName()+"开箱操作超时未还!");
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("elementOperationId", elementOperationId);
			params.put("uid", uid);
			String title = sysMsgSevice.selectTitle(params);
			if(StringUtils.isNotBlank(title)){
				String elsDescrib = title.substring(title.indexOf("("), title.indexOf(")")+1);
				String timeStr = DateUtil.getDateByFmt((Date)map.get("endBorrowElementTime"), "yyyy-MM-dd HH:mm");
				//借要件的人提醒短信
				String smsIpWhite = ConfigUtil.getStringValue(Constants.BASE_AMS_IPWHITE,ConfigUtil.CONFIG_BASE);
				AmsUtil.smsSend2(user.getMobile(),smsIpWhite, Constants.SMS_ELEMENT_AUDIT_BORROWWARN, "hyt",elsDescrib,timeStr);
				auditBaseService.updateBorrowedOverTimeMsgState(elementOperationId,2,2);//标记超时未还已进行提醒借用人和要件管理员
			}
		}
	}
	
	/**
	 * 更新订单状态（为超时未还）
	 * @param eleIds
	 */
	public void updateElementFile(List<Integer> eleIds) {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		for (int id : eleIds) {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id", id);
			map.put("status", 9);
			list.add(map);
		}
		elementFileService.updateElementFile(list);
	}
	
	//开箱操作超时未还检查,更新超时未还按钮为显示并添加系统消息
	private void checkOperationOverTime() {
		List<Map<String,Object>> OperationList = auditBaseService.selectBorrowedOverTimeOperation(1);//开箱超时未还
		Date time = new Date();
		for (Map<String, Object> map : OperationList) {
			Integer elementOperationId = MapUtils.getInteger(map, "id");
			Integer orderType = MapUtils.getInteger(map, "orderType");
			String orderNo = MapUtils.getString(map, "orderNo");
			String customerName = MapUtils.getString(map, "customerName");
			String uid = MapUtils.getString(map, "currentHandlerUid");
			List<Integer> eleIds = auditBaseService.selectBorrowedOverTimeEle(elementOperationId,2);
			ElementSystemMessageDto msgDto = new ElementSystemMessageDto();
			msgDto.setMessageType(1);
			msgDto.setElementOperationId(elementOperationId);
			msgDto.setOrderNo(orderNo);
			msgDto.setCustomerName(customerName);
			msgDto.setBeginBorrowElementTime((Date) map.get("beginBorrowElementTime"));
			msgDto.setEndBorrowElementTime((Date) map.get("endBorrowElementTime"));
			msgDto.setCreateTime(time);
			String elsDescrib = "";
			if(orderType==3){
				msgDto.setPublicSeal(auditBaseService.buildElementsDescrib(eleIds,3));//公章
				elsDescrib="公章("+msgDto.getPublicSeal()+")";
			}else{
				Set<String> describSet = new HashSet<String>();
				msgDto.setReceivableElements(auditBaseService.buildElementsDescrib(eleIds,1));//回款要件
				msgDto.setRiskElement(auditBaseService.buildElementsDescrib(eleIds,2));//风控要件
				if(msgDto.getReceivableElements()!=null){
					String[] eles = msgDto.getReceivableElements().split("/");
					describSet.addAll(Arrays.asList(eles));
				}
				if(msgDto.getRiskElement()!=null){
					String[] eles = msgDto.getRiskElement().split("/");
					describSet.addAll(Arrays.asList(eles));
				}
				elsDescrib="要件("+StringUtils.join(describSet, "、")+")";
			}
			String title = String.format("您借用的%s超期未还,请知晓", elsDescrib);
			msgDto.setTitle(title);
			msgDto.setReceiverUid(uid);
			sysMsgSevice.addMsg(msgDto);//添加超时未还系统消息
			//更新要件状态为超时未还
			updateElementFile(eleIds);
			auditBaseService.updateBtnOnState(2,msgDto.getOrderNo());//标记订单超时未还按钮状态为显示
			auditBaseService.updateSysmsgState(elementOperationId,2);//标记该开箱操作已添加系统消息提醒和标记订单超时未还
			log.info("开箱操作超时未还,未还"+elsDescrib+"应还日期:"+msgDto.getEndBorrowElementTime()+";用户uid"+uid);
		}
	}
	//审批借用超时未还检查,更新超时未还按钮并添加系统消息
	private void checkAuditOverTime() {
		List<AuditBaseDto> auditList = auditBaseService.selectBorrowedOverTimeAudits(1);
		Date time = new Date();
		if(auditList.size()>0){
			for (AuditBaseDto auditBaseDto : auditList) {
				ElementSystemMessageDto msgDto = new ElementSystemMessageDto();
				msgDto.setCreateTime(time);
				msgDto.setApplicantName(auditBaseDto.getApplierName());
				msgDto.setApplicantUid(auditBaseDto.getCreateUid());
				msgDto.setBeginBorrowElementTime(auditBaseDto.getBeginTime());
				msgDto.setEndBorrowElementTime(auditBaseDto.getEndTime());
				msgDto.setDbId(auditBaseDto.getId());
				msgDto.setOrderNo(auditBaseDto.getOrderNo());
				msgDto.setMessageType(1);
				List<Integer> eleIds = auditBaseService.selectBorrowedOverTimeEle(auditBaseDto.getId(),1);
				//已取消存入的不检测超时未还
				String elsDescrib = "";
				if(auditBaseDto.getType()==3){
					msgDto.setPublicSeal(auditBaseService.buildElementsDescrib(eleIds,3));//公章
					elsDescrib="公章("+msgDto.getPublicSeal()+")";
				}else{
					Set<String> describSet = new HashSet<String>();
					msgDto.setReceivableElements(auditBaseService.buildElementsDescrib(eleIds,1));//回款要件
					msgDto.setRiskElement(auditBaseService.buildElementsDescrib(eleIds,2));//风控要件
					if(msgDto.getReceivableElements()!=null){
						String[] eles = msgDto.getReceivableElements().split("/");
						describSet.addAll(Arrays.asList(eles));
					}
					if(msgDto.getRiskElement()!=null){
						String[] eles = msgDto.getRiskElement().split("/");
						describSet.addAll(Arrays.asList(eles));
					}
					elsDescrib="要件("+StringUtils.join(describSet, "、")+")";
				}
				msgDto.setTitle(auditBaseDto.getApplierName()+"借用的"+elsDescrib+"超期未还,请知晓");
				List<String> uids = auditBaseService.selectBorrowedOverTimeReceiverUid(auditBaseDto.getId());
				uids.add(auditBaseService.selectBorrowOperatorUid(auditBaseDto.getId(),1));
				Set<String> uidSet = new HashSet<String>();
				uidSet.addAll(uids);
				for (String uid : uidSet) {
					msgDto.setReceiverUid(uid);
					sysMsgSevice.addMsg(msgDto);
				}
				msgDto.setReceiverUid(auditBaseDto.getCreateUid());
				msgDto.setTitle("您借用的"+elsDescrib+"超期未还,请知晓");
				sysMsgSevice.addMsg(msgDto);
				//更新要件状态为超时未还
				updateElementFile(eleIds);
				auditBaseService.updateBtnOnState(2,auditBaseDto.getOrderNo());//标记订单超时未还按钮状态为显示
				auditBaseService.updateSysmsgState(auditBaseDto.getId(),1);//标记该审批借用超时已添加系统消息提醒和标记订单超时未还
				log.info(auditBaseDto.getTitle()+"超时未还,未还"+elsDescrib+"应还日期:"+auditBaseDto.getEndTime());
			}
		}
	}
	
	private String getEleDescribFromSysmsgTitle(AuditBaseDto auditBaseDto){
		String applierUid = auditBaseDto.getCreateUid();
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("dbId", auditBaseDto.getId());
		params.put("uid", applierUid);
		params.put("messageType", 1);
		String title = sysMsgSevice.selectTitle(params);
		if(StringUtils.isNotBlank(title)){
			return title.substring(title.indexOf("("), title.indexOf(")")+1);
		}
		return null;
	}
}
