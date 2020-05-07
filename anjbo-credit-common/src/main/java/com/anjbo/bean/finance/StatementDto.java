package com.anjbo.bean.finance;

import java.util.Date;

import com.anjbo.bean.BaseDto;

/**
 * 财务制单
 * @author admin
 *
 */
public class StatementDto extends  BaseDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String orderNo;
	private String remark;
	private Date lastUpdateTime;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	
	
}
