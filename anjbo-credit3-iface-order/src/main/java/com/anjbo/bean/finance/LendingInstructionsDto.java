package com.anjbo.bean.finance;
import java.util.List;

import com.anjbo.bean.order.BaseBorrowDto;

/**
 * 待发放款指令
 * @author yis
 *
 */
public class LendingInstructionsDto  extends BaseBorrowDto{
	private static final long serialVersionUID = 1L;
	
	
	  private String orderNo ;// 订单编号
	  private int status ;// 状态（0初始状态 1确认放款指令）
	  private String lendingBank;//  放款银行
	  private String lendingBankId;
	  private String openingBank;// 支行
	  private String openingBankId;
	  private String bankName ;//   银行卡户名
	  private String bankAccount; //银行卡账号
	  private String chargesReceivedImg;//  已收取费用图
	  private String chargesReceivedImgName;
	  private String remark; //  备注
	
	  private String fundCode; //资金方代号
 	  public List<Integer> fids;
	  private int fid;  //资金方id
	
	  private String borrowerName;   //客户姓名
	  /**费率*/
	   private Double rate;
	  /**逾期费率*/
	  private Double overdueRate;
	  /**收费金额*/
	  private Double chargeMoney;
	  /**风控等级*/
	  private Integer riskGradeId;
	  
	  private int type; //是否修改 0:否 1：是
	  
	  
	  
	public String getLendingBankId() {
		return lendingBankId;
	}
	public void setLendingBankId(String lendingBankId) {
		this.lendingBankId = lendingBankId;
	}
	public String getOpeningBankId() {
		return openingBankId;
	}
	public void setOpeningBankId(String openingBankId) {
		this.openingBankId = openingBankId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Integer getRiskGradeId() {
		return riskGradeId;
	}
	public void setRiskGradeId(Integer riskGradeId) {
		this.riskGradeId = riskGradeId;
	}
	
	public Double getRate() {
		return rate;
	}
	public void setRate(Double rate) {
		this.rate = rate;
	}
	public Double getOverdueRate() {
		return overdueRate;
	}
	public void setOverdueRate(Double overdueRate) {
		this.overdueRate = overdueRate;
	}
	public Double getChargeMoney() {
		return chargeMoney;
	}
	public void setChargeMoney(Double chargeMoney) {
		this.chargeMoney = chargeMoney;
	}
	
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getLendingBank() {
		return lendingBank;
	}
	public void setLendingBank(String lendingBank) {
		this.lendingBank = lendingBank;
	}
	public String getOpeningBank() {
		return openingBank;
	}
	public void setOpeningBank(String openingBank) {
		this.openingBank = openingBank;
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
	public String getFundCode() {
		return fundCode;
	}
	public void setFundCode(String fundCode) {
		this.fundCode = fundCode;
	}
	public List<Integer> getFids() {
		return fids;
	}
	public void setFids(List<Integer> fids) {
		this.fids = fids;
	}
	public int getFid() {
		return fid;
	}
	public void setFid(int fid) {
		this.fid = fid;
	}
	public String getBorrowerName() {
		return borrowerName;
	}
	public void setBorrowerName(String borrowerName) {
		this.borrowerName = borrowerName;
	}
	
	
	
}
