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
 * @Date 2018-07-03 18:34:33
 * @version 1.0
 */
@ApiModel(value = "待付利息的订单")
public class LendingPayDto extends BaseDto{
	
	private static final long serialVersionUID = 1L;
		

	/** 待付利息的订单 */
	@ApiModelProperty(value = "待付利息的订单")
	private java.lang.Integer id;
	
	/** 最后更新时间 */
	@ApiModelProperty(value = "最后更新时间")
	private java.util.Date lastUpdateTime;
	
	/** 订单编号 */
	@ApiModelProperty(value = "订单编号")
	private java.lang.String orderNo;
	
	/** 利息支付时间 */
	@ApiModelProperty(value = "利息支付时间")
	private java.util.Date payTime;
	
	/** 上传截图 */
	@ApiModelProperty(value = "上传截图")
	private java.lang.String payImg;
	
	/** 备注 */
	@ApiModelProperty(value = "备注")
	private java.lang.String remark;
	

	public void setId(java.lang.Integer value) {
		this.id = value;
	}	
	public java.lang.Integer getId() {
		return this.id;
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
	public void setPayTime(java.util.Date value) {
		this.payTime = value;
	}	
	public java.util.Date getPayTime() {
		return this.payTime;
	}
	public void setPayImg(java.lang.String value) {
		this.payImg = value;
	}	
	public java.lang.String getPayImg() {
		return this.payImg;
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
			.append("payTime",getPayTime())
			.append("payImg",getPayImg())
			.append("remark",getRemark())
			.toString();
	}
}

