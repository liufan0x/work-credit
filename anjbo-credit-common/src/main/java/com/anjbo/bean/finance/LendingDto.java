package com.anjbo.bean.finance;
import java.util.Date;

/**
 * 待放款的订单
 * @author YS
 *
 */
public class LendingDto extends LendingInstructionsDto{
	private static final long serialVersionUID = 1L;
	
	
	private Date lendingTime ;//放款时间
	private String lendingImg ;//上传截屏
	private Date customerPaymentTime;  //预计回款时间
	 
	private String lendingTimeStr;
	private String customerPaymentTimeStr;
	/** 是否一次性付款（0否  1是） */
	private int oneTimePay;
	private String receivableForUid;  //回款处理人uid  //扣回后置费用UId
	private int isPaymentMethod;  //费用前置后置
	
	
	public int getIsPaymentMethod() {
		return isPaymentMethod;
	}

	public void setIsPaymentMethod(int isPaymentMethod) {
		this.isPaymentMethod = isPaymentMethod;
	}

	public String getReceivableForUid() {
		return receivableForUid;
	}

	public void setReceivableForUid(String receivableForUid) {
		this.receivableForUid = receivableForUid;
	}

	public int getOneTimePay() {
		return oneTimePay;
	}

	public void setOneTimePay(int oneTimePay) {
		this.oneTimePay = oneTimePay;
	}

	public String getCustomerPaymentTimeStr() {
		return customerPaymentTimeStr;
	}

	public void setCustomerPaymentTimeStr(String customerPaymentTimeStr) {
		this.customerPaymentTimeStr = customerPaymentTimeStr;
	}

	public String getLendingTimeStr() {
		return lendingTimeStr;
	}

	public void setLendingTimeStr(String lendingTimeStr) {
		this.lendingTimeStr = lendingTimeStr;
	}

	public Date getCustomerPaymentTime() {
		return customerPaymentTime;
	}

	public void setCustomerPaymentTime(Date customerPaymentTime) {
		this.customerPaymentTime = customerPaymentTime;
	}

	public Date getLendingTime() {
		return lendingTime;
	}

	public void setLendingTime(Date lendingTime) {
		this.lendingTime = lendingTime;
	}

	public String getLendingImg() {
		return lendingImg;
	}

	public void setLendingImg(String lendingImg) {
		this.lendingImg = lendingImg;
	}
}
