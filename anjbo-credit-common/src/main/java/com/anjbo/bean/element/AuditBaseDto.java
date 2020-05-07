package com.anjbo.bean.element;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;


/**
  * 审批信息表 [实体类]
  * @Description: tbl_element_audit_base
  * @author 
  * @date 2017-12-20 18:04:58
  * @version V3.0
 */
public class AuditBaseDto implements Serializable {
	private static final long serialVersionUID = -426530698740426496L;
	/**
	*字段名：id:
	*名称：审批信息表
	*/
	private Integer id;
	
	private int extendId;
	
	/**
	*字段名：title:
	*名称：标题
	*/
	private String title;
	
	/**
	 *字段名：fileToSeal:
	 *名称：用印文件名称
	 */
	private String fileToSeal;
	
	/**
	 *字段名：sealFileCount:
	 *名称：用印文件份数
	 */
	private String sealFileCount;
	
	/**
	 *字段名：fileType:
	 *名称：用印文件类别
	 */
	private String fileType;
	
	/**
	 *字段名：fileImgUrl:
	 *名称：用印文件图片路径
	 */
	private String fileImgUrl;
	
	/**
	*字段名：createTime:
	*名称：发起时间
	*/
	private Date createTime;
	
	/**
	*字段名：modifyTime:
	*名称：修改时间
	*/
	private Date modifyTime;
	
	/**
	*字段名：orderNo:
	*名称：订单编号
	*/
	private String orderNo;
	
	/**
	*字段名：type:
	*名称：审批类型
	*/
	private Integer type;
	
	/**
	 *字段名：borrowDay:
	 *名称：借要件时长(单位小时)
	 */
	private Double borrowDay;
	
	/**
	*字段名：createUid:
	*名称：发起人uid
	*/
	private String createUid;
	/**
	 *字段名：applierName:
	 *名称：发起人姓名
	 */
	private String applierName;
	/**
	 *字段名：customerName:
	 *名称：客户名称
	 */
	private String customerName;
	/**
	 *字段名：currentAuditName:
	 *名称：当前审批人姓名
	 */
	private String currentAuditName;
	/**
	 *字段名：currentAuditUid:
	 *名称：当前审批人uid
	 */
	private String currentAuditUid;
	
	/**
	*字段名：elementIds:
	*名称：申请使用的物品id集合
	*/
	private String elementIds;
	
	/**
	*字段名：beginTime:
	*名称：开始时间
	*/
	private Date beginTime;
	
	/**
	*字段名：endTime:
	*名称：结束时间
	*/
	private Date endTime;
	
	/**
	 * 延长后结束时间
	 */
	private Date newEndTime;
	/**
	*字段名：reason:
	*名称：原因
	*/
	private String reason;
	
	/**延长原因*/
	private String extendReason;
	/**
	 *字段名：riskElement:
	 *名称：借用的风控要件
	 */
	private String riskElement;
	/**
	 *字段名：receivableElement:
	 *名称：借用的回款要件
	 */
	private String receivableElement;
	/**
	 *字段名：publicSeal:
	 *名称：借用的公章
	 */
	private String publicSeal;
	/**
	 *字段名：sealDepartment:
	 *名称：公章所属部门
	 */
	private String sealDepartment;
	
	/** 是否已取出借用的要件或公章(0未取出,1已取出) */
	private Integer hasTake;
	
	/**
	*字段名：state:
	*名称：状态(0待审批,1审批通过,2审批拒绝)
	*/
	private Integer state;
	
	/**
	*字段名：elementOperation:
	*名称：申请操作动作(1存2取3借4还5退)
	*/
	private Integer elementOperation;
	
	/**
	*字段名：copyTo:
	*名称：抄送人uid列表
	*/
	private String copyTo;
	
	
	/**id: 审批信息表*/
	public Integer getId() {
		return id;
	}

	/**id: 审批信息表*/
	public void setId(Integer id) {
		this.id = id;
	}
	
	/**title: 标题*/
	public String getTitle() {
		return title;
	}

	/**title: 标题*/
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**createTime: 发起时间*/
	public Date getCreateTime() {
		return createTime;
	}

	/**createTime: 发起时间*/
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	/**modifyTime: 修改时间*/
	public Date getModifyTime() {
		return modifyTime;
	}

	/**modifyTime: 修改时间*/
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	
	/**orderNo: 订单编号*/
	public String getOrderNo() {
		return orderNo;
	}

	/**orderNo: 订单编号*/
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	/**type: 审批类型*/
	public Integer getType() {
		return type;
	}

	/**type: 审批类型*/
	public void setType(Integer type) {
		this.type = type;
	}
	
	/**createUid: 发起人uid*/
	public String getCreateUid() {
		return createUid;
	}

	/**createUid: 发起人uid*/
	public void setCreateUid(String createUid) {
		this.createUid = createUid;
	}
	
	/**elementIds: 申请使用的物品id集合*/
	public String getElementIds() {
		return elementIds;
	}

	/**elementIds: 申请使用的物品id集合*/
	public void setElementIds(String elementIds) {
		this.elementIds = elementIds;
	}
	
	/**beginTime: 开始时间*/
	public Date getBeginTime() {
		return beginTime;
	}

	/**beginTime: 开始时间*/
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	
	/**endTime: 结束时间*/
	public Date getEndTime() {
		return endTime;
	}

	/**endTime: 结束时间*/
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	/**reason: 原因*/
	public String getReason() {
		return reason;
	}

	/**reason: 原因*/
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	/**state: 状态(0待审批,1审批通过,2审批拒绝)*/
	public Integer getState() {
		return state;
	}

	/**state: 状态(0待审批,1审批通过,2审批拒绝)*/
	public void setState(Integer state) {
		this.state = state;
	}
	
	/**elementOperation: 申请操作动作(1存2取3借4还5退)*/
	public Integer getElementOperation() {
		return elementOperation;
	}

	/**elementOperation: 申请操作动作(1存2取3借4还5退)*/
	public void setElementOperation(Integer elementOperation) {
		this.elementOperation = elementOperation;
	}
	
	/**copyTo: 抄送人uid列表*/
	public String getCopyTo() {
		return copyTo;
	}

	/**copyTo: 抄送人uid列表*/
	public void setCopyTo(String copyTo) {
		this.copyTo = copyTo;
	}

	public String getApplierName() {
		return applierName;
	}

	public void setApplierName(String applierName) {
		this.applierName = applierName;
	}
	
	public String getCurrentAuditName() {
		return currentAuditName;
	}

	public void setCurrentAuditName(String currentAuditName) {
		this.currentAuditName = currentAuditName;
	}
	
	public String getCurrentAuditUid() {
		return currentAuditUid;
	}

	public void setCurrentAuditUid(String currentAuditUid) {
		this.currentAuditUid = currentAuditUid;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public String getRiskElement() {
		return riskElement;
	}

	public void setRiskElement(String riskElement) {
		this.riskElement = riskElement;
	}

	public String getReceivableElement() {
		return receivableElement;
	}

	public void setReceivableElement(String receivableElement) {
		this.receivableElement = receivableElement;
	}
	
	public String getPublicSeal() {
		return publicSeal;
	}

	public void setPublicSeal(String publicSeal) {
		this.publicSeal = publicSeal;
	}
	
	public String getSealDepartment() {
		return sealDepartment;
	}

	public void setSealDepartment(String sealDepartment) {
		this.sealDepartment = sealDepartment;
	}
	
	public Double getBorrowDay() {
		if(beginTime!=null&&endTime!=null){
			long begin = beginTime.getTime();
			long end = endTime.getTime();
			long ret = end-begin;
			return ret*1.0/(3600*1000);
		}
		return borrowDay;
	}

	public void setBorrowDay(Double borrowDay) {
		this.borrowDay = borrowDay;
	}
	
	public String getFileToSeal() {
		return fileToSeal;
	}

	public void setFileToSeal(String fileToSeal) {
		this.fileToSeal = fileToSeal;
	}

	public String getSealFileCount() {
		return sealFileCount;
	}

	public void setSealFileCount(String sealFileCount) {
		this.sealFileCount = sealFileCount;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFileImgUrl() {
		return fileImgUrl;
	}

	public void setFileImgUrl(String fileImgUrl) {
		this.fileImgUrl = fileImgUrl;
	}

	public AuditBaseDto(){
	
	}
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public Integer getHasTake() {
		return hasTake;
	}

	public void setHasTake(Integer hasTake) {
		this.hasTake = hasTake;
	}

	public String getExtendReason() {
		return extendReason;
	}

	public void setExtendReason(String extendReason) {
		this.extendReason = extendReason;
	}

	public Date getNewEndTime() {
		return newEndTime;
	}

	public void setNewEndTime(Date newEndTime) {
		this.newEndTime = newEndTime;
	}

	public int getExtendId() {
		return extendId;
	}

	public void setExtendId(int extendId) {
		this.extendId = extendId;
	}
	
}