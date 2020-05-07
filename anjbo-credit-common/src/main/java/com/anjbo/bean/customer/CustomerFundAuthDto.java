/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.bean.customer;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.anjbo.bean.BaseNewDto;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-03-01 16:12:53
 * @version 1.0
 */
public class CustomerFundAuthDto extends BaseNewDto{
	private static final long serialVersionUID = 1L;
		
	//columns START
	/** 资方code*/
	private String fundCode;
	/** 权限数组 */	
	private java.lang.String auths;
	//columns END
	
	public CustomerFundAuthDto(){}
	public CustomerFundAuthDto(long id, String fundCode, String auths){
		this.setId(id);
		this.fundCode = fundCode;
		this.auths = auths;
	}

	public void setAuths(java.lang.String value) {
		this.auths = value;
	}	
	public java.lang.String getAuths() {
		return this.auths;
	}	

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("ID",getId())
			.append("AUTHS",getAuths())
			.append("CREATE_UID",getCreateUid())
			.append("CREATE_TIME",getCreateTime())
			.append("UPDATE_UID",getUpdateUid())
			.append("UPDATE_TIME",getUpdateTime())
			.toString();
	}
	public String getFundCode() {
		return fundCode;
	}
	public void setFundCode(String fundCode) {
		this.fundCode = fundCode;
	}
}

