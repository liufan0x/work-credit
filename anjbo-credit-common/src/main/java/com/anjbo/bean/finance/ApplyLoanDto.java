package com.anjbo.bean.finance;

import com.anjbo.bean.BaseDto;

public class ApplyLoanDto extends BaseDto{
	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String orderNo ;// 订单编号
	  private String lendingBank;//  放款银行
	  private Integer lendingBankId;
	  private String lendingBankSub;// 支行
	  private Integer lendingBankSubId;
	  private String bankName ;//   银行卡户名
	  private String bankAccount; //银行卡账号
	  private String chargesReceivedImg;//  已收取费用图
	  private String chargesReceivedImgName;
	  private String remark; //  备注
	  private String borrowerName;  //客户姓名
	  private int borrowingDays;   //借款金额
	  private Double loanAmount;  //借款天数
	  private String payAccountImg;  //出款账号
	  private String mortgageImg; //抵押回执照片/抵押状态截图
	  private int mortgageImgType;  //1:抵押回执照片 2:抵押状态截图
	  
	  
	public int getMortgageImgType() {
		return mortgageImgType;
	}
	public void setMortgageImgType(int mortgageImgType) {
		this.mortgageImgType = mortgageImgType;
	}
	public String getMortgageImg() {
		return mortgageImg;
	}
	public void setMortgageImg(String mortgageImg) {
		this.mortgageImg = mortgageImg;
	}
	public String getPayAccountImg() {
		return payAccountImg;
	}
	public void setPayAccountImg(String payAccountImg) {
		this.payAccountImg = payAccountImg;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getLendingBank() {
		return lendingBank;
	}
	public void setLendingBank(String lendingBank) {
		this.lendingBank = lendingBank;
	}
	public Integer getLendingBankId() {
		return lendingBankId;
	}
	public void setLendingBankId(Integer lendingBankId) {
		this.lendingBankId = lendingBankId;
	}
	
	public String getLendingBankSub() {
		return lendingBankSub;
	}
	public void setLendingBankSub(String lendingBankSub) {
		this.lendingBankSub = lendingBankSub;
	}
	public Integer getLendingBankSubId() {
		return lendingBankSubId;
	}
	public void setLendingBankSubId(Integer lendingBankSubId) {
		this.lendingBankSubId = lendingBankSubId;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	public String getChargesReceivedImg() {
		return chargesReceivedImg;
	}
	public void setChargesReceivedImg(String chargesReceivedImg) {
		this.chargesReceivedImg = chargesReceivedImg;
	}
	public String getChargesReceivedImgName() {
		return chargesReceivedImgName;
	}
	public void setChargesReceivedImgName(String chargesReceivedImgName) {
		this.chargesReceivedImgName = chargesReceivedImgName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getBorrowerName() {
		return borrowerName;
	}
	public void setBorrowerName(String borrowerName) {
		this.borrowerName = borrowerName;
	}
	public int getBorrowingDays() {
		return borrowingDays;
	}
	public void setBorrowingDays(int borrowingDays) {
		this.borrowingDays = borrowingDays;
	}
	public Double getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(Double loanAmount) {
		this.loanAmount = loanAmount;
	}
	  
	  
}
