package com.anjbo.bean.risk;

import com.anjbo.bean.BaseDto;

/**
 * 初审-回款
 * @author Administrator
 *
 */
public class FirstPaymentAuditDto extends BaseDto{

	
   /**
	 * 
	 */
   private static final long serialVersionUID = 1L;
   
   private Integer id;
   private String orderNo;
   private String paymentAccountType;  //账户类型:公司,个人(产权人/债务人)
   private String  paymentName  ;///回款银行开户名
   private String  paymentAccount  ;  //银行账户
   private Integer  paymentBankId  ;
   private String  paymentBankName  ;
   private Integer  paymentBankSubId  ;
   private String  paymentBankSubName  ;
   
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
	public String getPaymentAccountType() {
		return paymentAccountType;
	}
	public void setPaymentAccountType(String paymentAccountType) {
		this.paymentAccountType = paymentAccountType;
	}
	public String getPaymentName() {
		return paymentName;
	}
	public void setPaymentName(String paymentName) {
		this.paymentName = paymentName;
	}
	public String getPaymentAccount() {
		return paymentAccount;
	}
	public void setPaymentAccount(String paymentAccount) {
		this.paymentAccount = paymentAccount;
	}
	public Integer getPaymentBankId() {
		return paymentBankId;
	}
	public void setPaymentBankId(Integer paymentBankId) {
		this.paymentBankId = paymentBankId;
	}
	public String getPaymentBankName() {
		return paymentBankName;
	}
	public void setPaymentBankName(String paymentBankName) {
		this.paymentBankName = paymentBankName;
	}
	public Integer getPaymentBankSubId() {
		return paymentBankSubId;
	}
	public void setPaymentBankSubId(Integer paymentBankSubId) {
		this.paymentBankSubId = paymentBankSubId;
	}
	public String getPaymentBankSubName() {
		return paymentBankSubName;
	}
	public void setPaymentBankSubName(String paymentBankSubName) {
		this.paymentBankSubName = paymentBankSubName;
	}

}
