package com.anjbo.bean.finance;

import java.math.BigDecimal;
import java.util.Date;

import com.anjbo.bean.BaseDto;

public class ReceivableHasDto extends BaseDto{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

	/** 订单编号 */
	private String orderNo;

	/** 状态（0初始状态 1审批中   2审核通过 3审核未通过） */
	private Integer status;

	/** 类型（0准时回款 1提前回款 2逾期回款  ） */
	private Integer type;

	/** 退费(0 否    1是)  当类型1提前回款时 */
	private Integer refund;

	/**支出金额（应付罚息 和提前退费) */
	private BigDecimal penaltyPayable;

	/**回款情况（提前5天或者逾期）*/
	private Integer datediff;
	
	/** 机构ID */
	private int agencyId;
	/** 产品代码 */
	private String productCode;

	private String remark;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getRefund() {
		return refund;
	}

	public void setRefund(Integer refund) {
		this.refund = refund;
	}


	public BigDecimal getPenaltyPayable() {
		return penaltyPayable;
	}

	public void setPenaltyPayable(BigDecimal penaltyPayable) {
		this.penaltyPayable = penaltyPayable;
	}

	public Integer getDatediff() {
		return datediff;
	}

	public void setDatediff(Integer datediff) {
		this.datediff = datediff;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(int agencyId) {
		this.agencyId = agencyId;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	
	
	
}
