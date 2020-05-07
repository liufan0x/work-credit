/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.bean.data;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import com.anjbo.bean.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-05-11 10:57:22
 * @version 1.0
 */
@ApiModel(value = "业务资料")
public class PageBusinfoDto extends BaseDto{
	
	private static final long serialVersionUID = 1L;
		

	/** 业务资料 */
	@ApiModelProperty(value = "业务资料")
	private java.lang.Integer id;
	
	/** 订单编号 */
	@ApiModelProperty(value = "订单编号")
	private java.lang.String orderNo;
	
	/** 分类，关联业务资料分类表 tbl_order_businfo_type */
	@ApiModelProperty(value = "分类，关联业务资料分类表 tbl_order_businfo_type")
	private java.lang.Integer typeId;
	
	/** 图片顺序 */
	@ApiModelProperty(value = "图片顺序")
	private Integer index;
	
	/** 资料地址 */
	@ApiModelProperty(value = "资料地址")
	private java.lang.String url;
	
	/** 是否提单（1：提单； 2：提单后 ） */
	@ApiModelProperty(value = "是否提单（1：提单； 2：提单后 ）")
	private java.lang.Integer isOrder;
	
	/** 图片是否是ps（0:未修改,1:已修改） */
	@ApiModelProperty(value = "图片是否是ps（0:未修改,1:已修改）")
	private java.lang.Integer isPs;
	
	/** ids */
	@ApiModelProperty(value = "ids")
	private String ids;

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
	public void setTypeId(java.lang.Integer value) {
		this.typeId = value;
	}	
	public java.lang.Integer getTypeId() {
		return this.typeId;
	}
	public void setIndex(Integer value) {
		this.index = value;
	}	
	public Integer getIndex() {
		return this.index;
	}
	public void setUrl(java.lang.String value) {
		this.url = value;
	}	
	public java.lang.String getUrl() {
		return this.url;
	}
	public void setIsOrder(java.lang.Integer value) {
		this.isOrder = value;
	}	
	public java.lang.Integer getIsOrder() {
		return this.isOrder;
	}
	public void setIsPs(java.lang.Integer value) {
		this.isPs = value;
	}	
	public java.lang.Integer getIsPs() {
		return this.isPs;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("orderNo",getOrderNo())
			.append("typeId",getTypeId())
			.append("index",getIndex())
			.append("url",getUrl())
			.append("isOrder",getIsOrder())
			.append("isPs",getIsPs())
			.toString();
	}
}

