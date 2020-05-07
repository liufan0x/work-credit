package com.anjbo.bean.element;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;


/**
  * 审批流水表 [实体类]
  * @Description: tbl_element_audit_flow
  * @author 
  * @date 2017-12-20 18:04:58
  * @version V3.0
 */
public class AuditFlowDto implements Serializable {
	private static final long serialVersionUID = 5440826160207150300L;
	/**
	*字段名：id:
	*名称：审批流水表
	*/
	private Integer id;
	
	/**
	*字段名：dbId:
	*名称：审批信息表关联id
	*/
	private Integer dbId;
	
	/**
	*字段名：auditorUid:
	*名称：审批人uid
	*/
	private String auditorUid;
	
	/**
	 *字段名：auditorName:
	 *名称：审批人姓名
	 */
	private String auditorName;
	
	/**
	 *字段名：orderNo:
	 *名称：订单编号
	 */
	private String orderNo;
	
	/**
	*字段名：auditTime:
	*名称：审批时间
	*/
	private Date auditTime;
	
	/**
	*字段名：remark:
	*名称：审批备注
	*/
	private String remark;
	
	/**
	 *字段名：deliverTo:
	 *名称：转交的审批人姓名
	 */
	private String deliverTo;
	
	/**
	*字段名：auditLevel:
	*名称：审批层级
	*/
	private Integer auditLevel;
	
	/**
	*字段名：hasNext:
	*名称：是否有下一级审批(0无,1有)
	*/
	private Boolean hasNext;
	
	/**
	*字段名：state:
	*名称：状态(0未就绪,1待审批,2已审批)
	*/
	private Integer state;
	/**
	 * 是否延长借用时间1：否2：是
	 */
	private int isExtend;
	
	/**
	 * 关联对象,审批信息对象
	 */
	private AuditBaseDto auditBase;
	
	/**id: 审批流水表*/
	public Integer getId() {
		return id;
	}

	/**id: 审批流水表*/
	public void setId(Integer id) {
		this.id = id;
	}
	
	/**dbId: 审批信息表关联id*/
	public Integer getDbId() {
		return dbId;
	}

	/**dbId: 审批信息表关联id*/
	public void setDbId(Integer dbId) {
		this.dbId = dbId;
	}
	
	/**auditorUid: 审批人uid*/
	public String getAuditorUid() {
		return auditorUid;
	}

	/**auditorUid: 审批人uid*/
	public void setAuditorUid(String auditorUid) {
		this.auditorUid = auditorUid;
	}
	
	/**auditTime: 审批时间*/
	public Date getAuditTime() {
		return auditTime;
	}

	/**auditTime: 审批时间*/
	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}
	
	/**remark: 审批备注*/
	public String getRemark() {
		return remark;
	}

	/**remark: 审批备注*/
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	/**auditLevel: 审批层级*/
	public Integer getAuditLevel() {
		return auditLevel;
	}

	/**auditLevel: 审批层级*/
	public void setAuditLevel(Integer auditLevel) {
		this.auditLevel = auditLevel;
	}
	
	/**state: 状态(0未就绪,1待审批,2已审批)*/
	public Integer getState() {
		return state;
	}

	/**state: 状态(0未就绪,1待审批,2已审批)*/
	public void setState(Integer state) {
		this.state = state;
	}
	
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Boolean isHasNext() {
		return hasNext;
	}

	public void setHasNext(Boolean hasNext) {
		this.hasNext = hasNext;
	}

	public AuditBaseDto getAuditBase() {
		return auditBase;
	}

	public void setAuditBase(AuditBaseDto auditBase) {
		this.auditBase = auditBase;
	}

	public String getAuditorName() {
		return auditorName;
	}

	public void setAuditorName(String auditorName) {
		this.auditorName = auditorName;
	}
	
	public AuditFlowDto(){
		
	}
	
	public String getDeliverTo() {
		return deliverTo;
	}

	public void setDeliverTo(String deliverTo) {
		this.deliverTo = deliverTo;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public int getIsExtend() {
		return isExtend;
	}

	public void setIsExtend(int isExtend) {
		this.isExtend = isExtend;
	}
	
}