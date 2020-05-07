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
 * @Date 2018-05-09 17:56:47
 * @version 1.0
 */
@ApiModel(value = "资方ID")
public class FundAuthDto extends BaseDto{
	
	private static final long serialVersionUID = 1L;
		

	/** 资方ID */
	@ApiModelProperty(value = "资方ID")
	private Integer id;
	
	/** 资金方代号 */
	@ApiModelProperty(value = "资金方代号")
	private java.lang.String fundCode;
	
	/** 权限数组：风控查看(1500置换/1501畅贷)，其它影像资料类型ID */
	@ApiModelProperty(value = "权限数组：风控查看(1500置换/1501畅贷)，其它影像资料类型ID")
	private java.lang.String auths;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setFundCode(java.lang.String value) {
		this.fundCode = value;
	}	
	public java.lang.String getFundCode() {
		return this.fundCode;
	}
	public void setAuths(java.lang.String value) {
		this.auths = value;
	}	
	public java.lang.String getAuths() {
		return this.auths;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("fundCode",getFundCode())
			.append("auths",getAuths())
			.toString();
	}
}

