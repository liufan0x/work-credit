/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.bean.process;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import com.anjbo.bean.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 17:23:35
 * @version 1.0
 */
@ApiModel(value = "指派还款专员")
public class DistributionMemberDto extends BaseDto{
	
	private static final long serialVersionUID = 1L;
		

	/** 指派还款专员 */
	@ApiModelProperty(value = "指派还款专员")
	private java.lang.Integer id;
	
	/** 订单编号 */
	@ApiModelProperty(value = "订单编号")
	private java.lang.String orderNo;
	
	/** 还款专员(赎楼员)uid */
	@ApiModelProperty(value = "还款专员(赎楼员)uid")
	private java.lang.String foreclosureMemberUid;

	/** 还款专员(赎楼员)name */
	@ApiModelProperty(value = "还款专员(赎楼员)name")
	private java.lang.String foreclosureMemberName;
	
	/** 备注 */
	@ApiModelProperty(value = "备注")
	private java.lang.String remark;
	
	/** 指派时间 */
	@ApiModelProperty(value = "指派时间")
	private java.util.Date distributionTime;
	

	public void setId(java.lang.Integer value) {
		this.id = value;
	}	
	public java.lang.Integer getId() {
		return this.id;
	}
	public void setOrderNo(java.lang.String value) {
		this.orderNo = value;
	}	
	public java.lang.String getOrderNo() {
		return this.orderNo;
	}
	public void setForeclosureMemberUid(java.lang.String value) {
		this.foreclosureMemberUid = value;
	}	
	public java.lang.String getForeclosureMemberUid() {
		return this.foreclosureMemberUid;
	}
	public void setRemark(java.lang.String value) {
		this.remark = value;
	}	
	public java.lang.String getRemark() {
		return this.remark;
	}
	public void setDistributionTime(java.util.Date value) {
		this.distributionTime = value;
	}	
	public java.util.Date getDistributionTime() {
		return this.distributionTime;
	}
	public java.lang.String getForeclosureMemberName() {
		return foreclosureMemberName;
	}
	public void setForeclosureMemberName(java.lang.String foreclosureMemberName) {
		this.foreclosureMemberName = foreclosureMemberName;
	}
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("orderNo",getOrderNo())
			.append("foreclosureMemberUid",getForeclosureMemberUid())
			.append("remark",getRemark())
			.append("distributionTime",getDistributionTime())
			.toString();
	}
}

