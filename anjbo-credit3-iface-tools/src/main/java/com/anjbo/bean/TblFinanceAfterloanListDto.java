/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.bean;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import com.anjbo.bean.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-12-04 15:49:31
 * @version 1.0
 */
@ApiModel(value = "id")
public class TblFinanceAfterloanListDto extends BaseDto{
	
	private static final long serialVersionUID = 1L;
		

	/** id */
	@ApiModelProperty(value = "id")
	private java.lang.Integer id;
	
	/** 城市代码 */
	@ApiModelProperty(value = "城市代码")
	private java.lang.String cityCode;
	
	/** 城市名称 */
	@ApiModelProperty(value = "城市名称")
	private java.lang.String cityName;
	
	/** 产品代码 */
	@ApiModelProperty(value = "产品代码")
	private java.lang.String productCode;
	
	/** 产品名称 */
	@ApiModelProperty(value = "产品名称")
	private java.lang.String productName;
	
	/** 合同编号 */
	@ApiModelProperty(value = "合同编号")
	private java.lang.String contractNo;
	
	/** 客户姓名 */
	@ApiModelProperty(value = "客户姓名")
	private java.lang.String borrowerName;
	
	/** 借款金额 */
	@ApiModelProperty(value = "借款金额")
	private Long loanAmount;
	
	/** 借款期限(期) */
	@ApiModelProperty(value = "借款期限(期)")
	private java.lang.Integer borrowingPeriods;
	
	/** 放款时间 */
	@ApiModelProperty(value = "放款时间")
	private java.util.Date lendingTime;
	
	/** 渠道经理uid */
	@ApiModelProperty(value = "渠道经理uid")
	private java.lang.String channelManagerUid;
	
	/** 渠道经理名称 */
	@ApiModelProperty(value = "渠道经理名称")
	private java.lang.String channelManagerName;
	
	/** 受理员uid */
	@ApiModelProperty(value = "受理员uid")
	private java.lang.String acceptMemberUid;
	
	/** 受理员名称 */
	@ApiModelProperty(value = "受理员名称")
	private java.lang.String acceptMemberName;
	
	/** 逾期天数 */
	@ApiModelProperty(value = "逾期天数")
	private java.lang.Integer overdueDay;
	
	/** 最新还款日 */
	@ApiModelProperty(value = "最新还款日")
	private java.util.Date newRepayment;
	
	/** 还款状态(1:待还款,2:部分还款,3:已还款,4:提前结清,5:已展期,6:已逾期,7:已结清) */
	@ApiModelProperty(value = "还款状态(1:待还款,2:部分还款,3:已还款,4:提前结清,5:已展期,6:已逾期,7:已结清)")
	private java.lang.Integer repaymentStatus;
	
	/** 状态名称 */
	@ApiModelProperty(value = "状态名称")
	private java.lang.String repaymentStatusName;
	
	/** 订单号 */
	@ApiModelProperty(value = "订单号")
	private java.lang.String orderNo;
	
	/** 费率 */
	@ApiModelProperty(value = "费率")
	private Long rate;
	
	/** 逾期费率 */
	@ApiModelProperty(value = "逾期费率")
	private Long overdueRate;
	
	/** 还款方式(1:先息后本,2:等额本息) */
	@ApiModelProperty(value = "还款方式(1:先息后本,2:等额本息)")
	private java.lang.Integer repaymentType;
	
	/** 还款方式名称 */
	@ApiModelProperty(value = "还款方式名称")
	private java.lang.String repaymentName;
	
	/** 客户手机号码 */
	@ApiModelProperty(value = "客户手机号码")
	private java.lang.String phoneNumber;
	
	/** 渠道经理手机号 */
	@ApiModelProperty(value = "渠道经理手机号")
	private java.lang.String channelManagerPhone;
	
	/** 溢出 */
	@ApiModelProperty(value = "溢出")
	private Long overflow;
	
	/** 营业部 */
	@ApiModelProperty(value = "营业部")
	private java.lang.String branchCompany;
	

	public void setId(java.lang.Integer value) {
		this.id = value;
	}	
	public java.lang.Integer getId() {
		return this.id;
	}
	public void setCityCode(java.lang.String value) {
		this.cityCode = value;
	}	
	public java.lang.String getCityCode() {
		return this.cityCode;
	}
	public void setCityName(java.lang.String value) {
		this.cityName = value;
	}	
	public java.lang.String getCityName() {
		return this.cityName;
	}
	public void setProductCode(java.lang.String value) {
		this.productCode = value;
	}	
	public java.lang.String getProductCode() {
		return this.productCode;
	}
	public void setProductName(java.lang.String value) {
		this.productName = value;
	}	
	public java.lang.String getProductName() {
		return this.productName;
	}
	public void setContractNo(java.lang.String value) {
		this.contractNo = value;
	}	
	public java.lang.String getContractNo() {
		return this.contractNo;
	}
	public void setBorrowerName(java.lang.String value) {
		this.borrowerName = value;
	}	
	public java.lang.String getBorrowerName() {
		return this.borrowerName;
	}
	public void setLoanAmount(Long value) {
		this.loanAmount = value;
	}	
	public Long getLoanAmount() {
		return this.loanAmount;
	}
	public void setBorrowingPeriods(java.lang.Integer value) {
		this.borrowingPeriods = value;
	}	
	public java.lang.Integer getBorrowingPeriods() {
		return this.borrowingPeriods;
	}
	public void setLendingTime(java.util.Date value) {
		this.lendingTime = value;
	}	
	public java.util.Date getLendingTime() {
		return this.lendingTime;
	}
	public void setChannelManagerUid(java.lang.String value) {
		this.channelManagerUid = value;
	}	
	public java.lang.String getChannelManagerUid() {
		return this.channelManagerUid;
	}
	public void setChannelManagerName(java.lang.String value) {
		this.channelManagerName = value;
	}	
	public java.lang.String getChannelManagerName() {
		return this.channelManagerName;
	}
	public void setAcceptMemberUid(java.lang.String value) {
		this.acceptMemberUid = value;
	}	
	public java.lang.String getAcceptMemberUid() {
		return this.acceptMemberUid;
	}
	public void setAcceptMemberName(java.lang.String value) {
		this.acceptMemberName = value;
	}	
	public java.lang.String getAcceptMemberName() {
		return this.acceptMemberName;
	}
	public void setOverdueDay(java.lang.Integer value) {
		this.overdueDay = value;
	}	
	public java.lang.Integer getOverdueDay() {
		return this.overdueDay;
	}
	public void setNewRepayment(java.util.Date value) {
		this.newRepayment = value;
	}	
	public java.util.Date getNewRepayment() {
		return this.newRepayment;
	}
	public void setRepaymentStatus(java.lang.Integer value) {
		this.repaymentStatus = value;
	}	
	public java.lang.Integer getRepaymentStatus() {
		return this.repaymentStatus;
	}
	public void setRepaymentStatusName(java.lang.String value) {
		this.repaymentStatusName = value;
	}	
	public java.lang.String getRepaymentStatusName() {
		return this.repaymentStatusName;
	}
	public void setOrderNo(java.lang.String value) {
		this.orderNo = value;
	}	
	public java.lang.String getOrderNo() {
		return this.orderNo;
	}
	public void setRate(Long value) {
		this.rate = value;
	}	
	public Long getRate() {
		return this.rate;
	}
	public void setOverdueRate(Long value) {
		this.overdueRate = value;
	}	
	public Long getOverdueRate() {
		return this.overdueRate;
	}
	public void setRepaymentType(java.lang.Integer value) {
		this.repaymentType = value;
	}	
	public java.lang.Integer getRepaymentType() {
		return this.repaymentType;
	}
	public void setRepaymentName(java.lang.String value) {
		this.repaymentName = value;
	}	
	public java.lang.String getRepaymentName() {
		return this.repaymentName;
	}
	public void setPhoneNumber(java.lang.String value) {
		this.phoneNumber = value;
	}	
	public java.lang.String getPhoneNumber() {
		return this.phoneNumber;
	}
	public void setChannelManagerPhone(java.lang.String value) {
		this.channelManagerPhone = value;
	}	
	public java.lang.String getChannelManagerPhone() {
		return this.channelManagerPhone;
	}
	public void setOverflow(Long value) {
		this.overflow = value;
	}	
	public Long getOverflow() {
		return this.overflow;
	}
	public void setBranchCompany(java.lang.String value) {
		this.branchCompany = value;
	}	
	public java.lang.String getBranchCompany() {
		return this.branchCompany;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("cityCode",getCityCode())
			.append("cityName",getCityName())
			.append("productCode",getProductCode())
			.append("productName",getProductName())
			.append("contractNo",getContractNo())
			.append("borrowerName",getBorrowerName())
			.append("loanAmount",getLoanAmount())
			.append("borrowingPeriods",getBorrowingPeriods())
			.append("lendingTime",getLendingTime())
			.append("channelManagerUid",getChannelManagerUid())
			.append("channelManagerName",getChannelManagerName())
			.append("acceptMemberUid",getAcceptMemberUid())
			.append("acceptMemberName",getAcceptMemberName())
			.append("overdueDay",getOverdueDay())
			.append("newRepayment",getNewRepayment())
			.append("repaymentStatus",getRepaymentStatus())
			.append("repaymentStatusName",getRepaymentStatusName())
			.append("orderNo",getOrderNo())
			.append("rate",getRate())
			.append("overdueRate",getOverdueRate())
			.append("repaymentType",getRepaymentType())
			.append("repaymentName",getRepaymentName())
			.append("phoneNumber",getPhoneNumber())
			.append("channelManagerPhone",getChannelManagerPhone())
			.append("overflow",getOverflow())
			.append("branchCompany",getBranchCompany())
			.toString();
	}
}

