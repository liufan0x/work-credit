/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.bean.data;

import java.util.List;
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
@ApiModel(value = "业务资料分类")
public class PageBusinfoTypeDto extends BaseDto{
	
	private static final long serialVersionUID = 1L;
		

	/** 业务资料分类 */
	@ApiModelProperty(value = "业务资料分类")
	private java.lang.Integer id;
	
	/** 资料名称 */
	@ApiModelProperty(value = "资料名称")
	private java.lang.String name;
	
	/** 材料来源 */
	@ApiModelProperty(value = "材料来源")
	private java.lang.String source;
	
	/** 上级分类ID */
	@ApiModelProperty(value = "上级分类ID")
	private java.lang.Integer pid;
	
	/** 是否必备（1：是2：否） */
	@ApiModelProperty(value = "是否必备（1：是2：否）")
	private java.lang.Integer isMust;
	
	/** 01：债务置换贷款交易类02：债务置换贷款非交易类0103：交易类畅贷0203：非交易类畅贷 */
	@ApiModelProperty(value = "01：债务置换贷款交易类02：债务置换贷款非交易类0103：交易类畅贷0203：非交易类畅贷")
	private java.lang.String productCode;
	
	/** 是否是面签资料(面签节点的业务资料上传)。1是，2不是(每个节点的照片). */
	@ApiModelProperty(value = "是否是面签资料(面签节点的业务资料上传)。1是，2不是(每个节点的照片).")
	private java.lang.Integer isFaceInfo;
	
	/** 是否pc的业务类型(1是,2否) */
	@ApiModelProperty(value = "是否pc的业务类型(1是,2否)")
	private java.lang.Integer pcPid;
	
	/** 备注 */
	@ApiModelProperty(value = "备注")
	private java.lang.String remark;

	/** 子集合 */
	@ApiModelProperty(value = "子集合")
	private List<PageBusinfoTypeDto> sonTypes;
	
	/** 数据 */
	@ApiModelProperty(value = "数据")
	private List<PageBusinfoDto> listMap;
	

	public void setId(java.lang.Integer value) {
		this.id = value;
	}	
	public java.lang.Integer getId() {
		return this.id;
	}
	public void setName(java.lang.String value) {
		this.name = value;
	}	
	public java.lang.String getName() {
		return this.name;
	}
	public void setPid(java.lang.Integer value) {
		this.pid = value;
	}	
	public java.lang.Integer getPid() {
		return this.pid;
	}
	public void setIsMust(java.lang.Integer value) {
		this.isMust = value;
	}	
	public java.lang.Integer getIsMust() {
		return this.isMust;
	}
	public void setProductCode(java.lang.String value) {
		this.productCode = value;
	}	
	public java.lang.String getProductCode() {
		return this.productCode;
	}
	public void setIsFaceInfo(java.lang.Integer value) {
		this.isFaceInfo = value;
	}	
	public java.lang.Integer getIsFaceInfo() {
		return this.isFaceInfo;
	}
	public void setPcPid(java.lang.Integer value) {
		this.pcPid = value;
	}	
	public java.lang.Integer getPcPid() {
		return this.pcPid;
	}
	public void setRemark(java.lang.String value) {
		this.remark = value;
	}	
	public java.lang.String getRemark() {
		return this.remark;
	}
	public List<PageBusinfoTypeDto> getSonTypes() {
		return sonTypes;
	}
	public void setSonTypes(List<PageBusinfoTypeDto> sonTypes) {
		this.sonTypes = sonTypes;
	}
	public List<PageBusinfoDto> getListMap() {
		return listMap;
	}
	public void setListMap(List<PageBusinfoDto> listMap) {
		this.listMap = listMap;
	}
	public java.lang.String getSource() {
		return source;
	}
	public void setSource(java.lang.String source) {
		this.source = source;
	}
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("name",getName())
			.append("pid",getPid())
			.append("isMust",getIsMust())
			.append("productCode",getProductCode())
			.append("isFaceInfo",getIsFaceInfo())
			.append("pcPid",getPcPid())
			.append("remark",getRemark())
			.toString();
	}
}

