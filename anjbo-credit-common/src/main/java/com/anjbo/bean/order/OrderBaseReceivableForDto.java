package com.anjbo.bean.order;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.anjbo.bean.BaseDto;
/**
 * 计划回款
 * @author liuf
 *
 */
public class OrderBaseReceivableForDto extends BaseDto{

	private static final long serialVersionUID = 1L;
	/**计划回款信息*/
	private Integer id;
	/** 订单编号 */
	private String orderNo;
	/**计划回款时间*/
	private Date payMentAmountDate;
	/**回款金额*/
	private Double payMentAmount;
	/** 回款时间字符串 */
	private String payMentAmountDateStr;
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
	public Date getPayMentAmountDate() {
		return payMentAmountDate;
	}
	public void setPayMentAmountDate(Date payMentAmountDate) {
		this.payMentAmountDate = payMentAmountDate;
	}
	public Double getPayMentAmount() {
		return payMentAmount;
	}
	public void setPayMentAmount(Double payMentAmount) {
		this.payMentAmount = payMentAmount;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public OrderBaseReceivableForDto() {
		super();
	}
	public String getPayMentAmountDateStr() {
		if(null!=this.payMentAmountDate){
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			this.payMentAmountDateStr = format.format(this.payMentAmountDate);
		}
		return payMentAmountDateStr;
	}
	public void setPayMentAmountDateStr(String payMentAmountDateStr) {
		this.payMentAmountDateStr = payMentAmountDateStr;
	}

}
