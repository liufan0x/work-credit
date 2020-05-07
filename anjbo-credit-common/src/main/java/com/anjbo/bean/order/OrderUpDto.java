/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.bean.order;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.data.annotation.Id;

import com.anjbo.bean.BaseNewDto;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-03-07 14:16:11
 * @version 1.0
 */
public class OrderUpDto extends BaseNewDto{
	private static final long serialVersionUID = 1L;
		
	//columns START
	/** 订单号 */	
	private java.lang.String orderNo;
	//columns END

	public void setOrderNo(java.lang.String value) {
		this.orderNo = value;
	}	
	public java.lang.String getOrderNo() {
		return this.orderNo;
	}	

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("ID",getId())
			.append("ORDER_NO",getOrderNo())
			.append("CREATE_UID",getCreateUid())
			.append("CREATE_TIME",getCreateTime())
			.append("UPDATE_UID",getUpdateUid())
			.append("UPDATE_TIME",getUpdateTime())
			.toString();
	}
}

