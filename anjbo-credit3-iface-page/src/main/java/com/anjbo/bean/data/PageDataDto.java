/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.bean.data;

import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import com.anjbo.bean.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-06-28 20:20:05
 * @version 1.0
 */
@ApiModel(value = "页面配置数据")
public class PageDataDto extends BaseDto{
	
	private static final long serialVersionUID = 1L;
		

	/** 云按揭数据 */
	@ApiModelProperty(value = "云按揭数据")
	private java.lang.Integer id;
	
	/** 表名（原意义上的表名） */
	@ApiModelProperty(value = "表名（原意义上的表名）")
	private java.lang.String tblName;
	
	/** 订单号 */
	@ApiModelProperty(value = "订单号")
	private java.lang.String orderNo;
	
	/** 城市Code */
	@ApiModelProperty(value = "城市Code")
	private String cityCode;

	/** 产品Code */
	@ApiModelProperty(value = "产品Code")
	private String productCode;

	/** 流程Id */
	@ApiModelProperty(value = "流程Id")
	private String processId;
	
	/** 表下的数据 */
	@ApiModelProperty(value = "表下的数据")
	private java.lang.String data;
	
	/** 表下的数据Map */
	@ApiModelProperty(value = "表下的数据Map")
	private Map<String, Object> dataMap;
	
	/** 其他表数据Map */
	@ApiModelProperty(value = "其他表数据Map")
	private Map<String, Object> otherData;

	/** 无表数据时展示的一句话 */
	@ApiModelProperty(value = "无表数据时展示的一句话")
	private java.lang.String showText;
	

	public void setId(java.lang.Integer value) {
		this.id = value;
	}	
	public java.lang.Integer getId() {
		return this.id;
	}
	public void setTblName(java.lang.String value) {
		this.tblName = value;
	}	
	public java.lang.String getTblName() {
		return this.tblName;
	}
	public void setOrderNo(java.lang.String value) {
		this.orderNo = value;
	}	
	public java.lang.String getOrderNo() {
		return this.orderNo;
	}
	public void setData(java.lang.String value) {
		this.data = value;
	}	
	public java.lang.String getData() {
		return this.data;
	}
	public void setShowText(java.lang.String value) {
		this.showText = value;
	}	
	public java.lang.String getShowText() {
		return this.showText;
	}
	public Map<String, Object> getDataMap() {
		return dataMap;
	}
	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}
	public Map<String, Object> getOtherData() {
		return otherData;
	}
	public void setOtherData(Map<String, Object> otherData) {
		this.otherData = otherData;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getProcessId() {
		return processId;
	}
	public void setProcessId(String processId) {
		this.processId = processId;
	}
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("tblName",getTblName())
			.append("orderNo",getOrderNo())
			.append("data",getData())
			.append("showText",getShowText())
			.toString();
	}
}

