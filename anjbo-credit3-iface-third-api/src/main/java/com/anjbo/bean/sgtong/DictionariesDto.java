package com.anjbo.bean.sgtong;

import com.anjbo.bean.BaseDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(value = "字典")
public class DictionariesDto extends BaseDto{
	private static final long serialVersionUID = 1L;
	
	/** 业务资料 */
	@ApiModelProperty(value = "id")
	private java.lang.Integer id;
	
	/** 订单编号 */
	@ApiModelProperty(value = "编号")
	private java.lang.String code;
	
	/** 分类，关联业务资料分类表 tbl_fc_order_businfo_type */
	@ApiModelProperty(value = "名称")
	private java.lang.String name;

	public java.lang.Integer getId() {
		return id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	public java.lang.String getCode() {
		return code;
	}

	public void setCode(java.lang.String code) {
		this.code = code;
	}

	public java.lang.String getName() {
		return name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}
}
