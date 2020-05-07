package com.anjbo.bean.finance;

import java.math.BigDecimal;

import com.anjbo.bean.BaseDto;

/**
 * 返佣
 * @author yis
 *
 */
public class RebateDto extends BaseDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

	/** 订单编号 */
	private String orderNo;
	/** 返佣时间*/
	private String rebateTime;
	private String rebateTimeStr;
	/** 返佣照片*/
	private String rebateImg;
	/**返佣金额 */
	private BigDecimal rebateMoney;
	private String remark;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getRebateTime() {
		return rebateTime;
	}
	public void setRebateTime(String rebateTime) {
		this.rebateTime = rebateTime;
	}
	public String getRebateTimeStr() {
		return rebateTimeStr;
	}
	public void setRebateTimeStr(String rebateTimeStr) {
		this.rebateTimeStr = rebateTimeStr;
	}
	public String getRebateImg() {
		return rebateImg;
	}
	public void setRebateImg(String rebateImg) {
		this.rebateImg = rebateImg;
	}
	public BigDecimal getRebateMoney() {
		return rebateMoney;
	}
	public void setRebateMoney(BigDecimal rebateMoney) {
		this.rebateMoney = rebateMoney;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
