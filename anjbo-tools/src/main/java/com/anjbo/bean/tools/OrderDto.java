package com.anjbo.bean.tools;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * 订单
 * 
 * @author zhuzj
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderDto {
	private String orderId;// 虚拟商品订单编号
	private String uid;// 创建人uid
	private String goodId;// 虚拟商品Id
	private int goodType;// 商品类型(1询价，2查档，3打合同，4过户）
	private double amount;// 金额
	private String payType;// 支付方式(alipay/wxpay)
	private int payStatus;// 支付状态
	private Date createTime;// 订单创建时间
	private String token;
	
	/**
	 * 
	 */
	public OrderDto() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	/**
	 * @param orderId
	 * @param payType
	 * @param payStatus
	 */
	public OrderDto(String orderId, String payType, int payStatus) {
		this.orderId = orderId;
		this.payType = payType;
		this.payStatus = payStatus;
	}



	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getGoodId() {
		return goodId;
	}

	public void setGoodId(String goodId) {
		this.goodId = goodId;
	}

	public int getGoodType() {
		return goodType;
	}

	public void setGoodType(int goodType) {
		this.goodType = goodType;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public int getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(int payStatus) {
		this.payStatus = payStatus;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
