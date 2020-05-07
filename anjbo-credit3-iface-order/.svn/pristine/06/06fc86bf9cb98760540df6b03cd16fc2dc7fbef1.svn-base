/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.bean.finance;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import com.anjbo.bean.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 18:34:35
 * @version 1.0
 */
@ApiModel(value = "财务制单")
public class StatementDto extends BaseDto{
	
	private static final long serialVersionUID = 1L;
		

	/** 财务制单 */
	@ApiModelProperty(value = "财务制单")
	private java.lang.Integer id;
	
	/** 最后更新时间 */
	@ApiModelProperty(value = "最后更新时间")
	private java.util.Date lastUpdateTime;
	
	/** 订单编号 */
	@ApiModelProperty(value = "订单编号")
	private java.lang.String orderNo;
	
	/** 备注 */
	@ApiModelProperty(value = "备注")
	private java.lang.String remark;
	
	/** 备注 */
	@ApiModelProperty(value = "陕国投放款批次号")
	private java.lang.String sgtLendingBatNo;
	

	public void setId(java.lang.Integer value) {
		this.id = value;
	}	
	public java.lang.Integer getId() {
		return this.id;
	}
	public java.lang.String getSgtLendingBatNo() {
		return sgtLendingBatNo;
	}
	public void setSgtLendingBatNo(java.lang.String sgtLendingBatNo) {
		this.sgtLendingBatNo = sgtLendingBatNo;
	}
	public void setLastUpdateTime(java.util.Date value) {
		this.lastUpdateTime = value;
	}	
	public java.util.Date getLastUpdateTime() {
		return this.lastUpdateTime;
	}
	public void setOrderNo(java.lang.String value) {
		this.orderNo = value;
	}	
	public java.lang.String getOrderNo() {
		return this.orderNo;
	}
	public void setRemark(java.lang.String value) {
		this.remark = value;
	}	
	public java.lang.String getRemark() {
		return this.remark;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("lastUpdateTime",getLastUpdateTime())
			.append("orderNo",getOrderNo())
			.append("remark",getRemark())
			.toString();
	}
}

