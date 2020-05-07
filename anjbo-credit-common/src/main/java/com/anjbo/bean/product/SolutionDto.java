package com.anjbo.bean.product;

import java.util.Date;

import com.anjbo.bean.BaseDto;
/**
 * 解压
 * @author admin
 *
 */
public class SolutionDto extends BaseDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String orderNo;
	private Date solutionTime;
	private String landBureauUserName;
	private String landBureauName;
	private String remark;
	
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public Date getSolutionTime() {
		return solutionTime;
	}
	public void setSolutionTime(Date solutionTime) {
		this.solutionTime = solutionTime;
	}
	public String getLandBureauUserName() {
		return landBureauUserName;
	}
	public void setLandBureauUserName(String landBureauUserName) {
		this.landBureauUserName = landBureauUserName;
	}
	public String getLandBureauName() {
		return landBureauName;
	}
	public void setLandBureauName(String landBureauName) {
		this.landBureauName = landBureauName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
}
