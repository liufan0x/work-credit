/**
 * 
 */
package com.anjbo.bean.tools;

import com.anjbo.bean.BaseDto;

/**
 * @author Kevin Chang
 * 
 */
public class OrderResultDto extends BaseDto{
	private String orderid;
	private double amount;

	private String enquriyId; // 评估id
	private String archiveId;// 查档id

	public String getEnquriyId() {
		return enquriyId;
	}

	public void setEnquriyId(String enquriyId) {
		this.enquriyId = enquriyId;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getArchiveId() {
		return archiveId;
	}

	public void setArchiveId(String archiveId) {
		this.archiveId = archiveId;
	}

}
