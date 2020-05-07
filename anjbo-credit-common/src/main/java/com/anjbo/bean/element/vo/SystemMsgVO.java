package com.anjbo.bean.element.vo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.anjbo.common.DateUtil;

public class SystemMsgVO  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	private Integer id;
	private String title;
	private Integer messageType;//1:超时提醒消息2:待我审批提示消息3:审批抄送4:申请审批返回结果
	private int auditType;//1借要件普通审批2借要件财务审批3借公章
	private String customerName;
	private Integer dbId;
	private Integer elementOperationId;
	private String orderNo;
	private String applicantName;
	private String riskElement;
	private String receivableElements;
	private String publicSeal;
	private Date beginBorrowElementTime;
	private Date endBorrowElementTime;
	private Date createTime;
	private Integer auditState;
	private boolean hasRead;
	
	public Integer getType(){
		if(messageType!=null&&messageType==1){
			if(StringUtils.isNotBlank(publicSeal)){
				return 1;//公章超时未还消息
			}
			return 0;//要件超时未还消息
		}
		if(auditType==3){
			return 3;//公章审批消息
		}
		return 2;//要件审批消息
	}
	public Integer getId() {
		return id;
	}
	public String getTitle() {
		return title;
	}
	public String getTypeStr() {
		String str = null;
		if(messageType!=null){
			switch (messageType) {
				case 1:str = "要件超时未还";break;
				case 2:;
				case 3:;
				case 4:str = "审批";break;
				default:break;
			}
		}
		return str;
	}
	public List<String> getDetail(){
		List<String> list = new ArrayList<String>();
		if(applicantName!=null){
			list.add("申请人:"+applicantName);
		}
		if(customerName!=null){
			list.add("客户:"+customerName);
		}
		if(riskElement!=null){
			list.add("申请借用的风控要件:"+riskElement);
		}
		if(receivableElements!=null){
			list.add("申请借用的回款要件:"+receivableElements);
		}
		if(publicSeal!=null){
			list.add("申请借用的公章:"+publicSeal);
		}
		list.add(String.format("%s借用时间:%s至%s", publicSeal==null?"要件":"公章",
				beginBorrowElementTime==null?"--":fmt.format(beginBorrowElementTime),
				endBorrowElementTime==null?"--":fmt.format(endBorrowElementTime)));
		return list;
	}
	public String getCreateTime() {
		if(createTime!=null){
			return DateUtil.getDateByFmt(createTime, "MM月dd日 HH:mm");
		}
		return null;
	}
	public Integer getState() {
		return auditState;
	}
	public String getStateStr() {
		String str = null;
		if(auditState!=null){
			switch (auditState) {
				case 1:str="待审批";break;
				case 2:str="已同意";break;
				case 3:str="已拒绝";break;
				default:break;
			}
		}
		return str;
	}
	public Integer getElementOperationId() {
		return elementOperationId;
	}
	
	public boolean isHasRead() {
		return hasRead;
	}
	

	
	public void setHasRead(boolean hasRead) {
		this.hasRead = hasRead;
	}
	public void setElementOperationId(Integer elementOperationId) {
		this.elementOperationId = elementOperationId;
	}
	public void setAuditType(int auditType) {
		this.auditType = auditType;
	}
	public Integer getDbId() {
		return dbId;
	}
	public void setDbId(Integer dbId) {
		this.dbId = dbId;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}
	public void setBeginBorrowElementTime(Date beginBorrowElementTime) {
		this.beginBorrowElementTime = beginBorrowElementTime;
	}
	public void setEndBorrowElementTime(Date endBorrowElementTime) {
		this.endBorrowElementTime = endBorrowElementTime;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setMessageType(Integer messageType) {
		this.messageType = messageType;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public void setRiskElement(String riskElement) {
		this.riskElement = riskElement;
	}
	public void setReceivableElements(String receivableElements) {
		this.receivableElements = receivableElements;
	}
	public void setPublicSeal(String publicSeal) {
		this.publicSeal = publicSeal;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public void setAuditState(Integer auditState) {
		this.auditState = auditState;
	}
	
}
