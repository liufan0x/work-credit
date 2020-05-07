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
 * @Date 2018-07-03 17:23:34
 * @version 1.0
 */
@ApiModel(value = "解压")
public class AppFddReleaseDto extends BaseDto{
	
	private static final long serialVersionUID = 1L;
		

	/** 解压 */
	@ApiModelProperty(value = "解压")
	private java.lang.Integer id;
	
	/** 订单编号 */
	@ApiModelProperty(value = "订单编号")
	private java.lang.String orderNo;
	
	/** 解压时间 */
	@ApiModelProperty(value = "解压时间")
	private java.util.Date releaseTime;
	
	/** 解压操作人 */
	@ApiModelProperty(value = "解压操作人")
	private java.lang.String releaseUid;
	
	/** 国土局名称 */
	@ApiModelProperty(value = "国土局名称")
	private java.lang.String rlandBureauName;
	
	/** 备注 */
	@ApiModelProperty(value = "备注")
	private java.lang.String remark;
	

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
	public void setReleaseTime(java.util.Date value) {
		this.releaseTime = value;
	}	
	public java.util.Date getReleaseTime() {
		return this.releaseTime;
	}
	public void setReleaseUid(java.lang.String value) {
		this.releaseUid = value;
	}	
	public java.lang.String getReleaseUid() {
		return this.releaseUid;
	}
	public void setRlandBureauName(java.lang.String value) {
		this.rlandBureauName = value;
	}	
	public java.lang.String getRlandBureauName() {
		return this.rlandBureauName;
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
			.append("orderNo",getOrderNo())
			.append("releaseTime",getReleaseTime())
			.append("releaseUid",getReleaseUid())
			.append("rlandBureauName",getRlandBureauName())
			.append("remark",getRemark())
			.toString();
	}
}

