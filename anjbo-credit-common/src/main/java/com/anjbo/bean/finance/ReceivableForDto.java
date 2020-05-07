package com.anjbo.bean.finance;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 待回款
 * @author yis
 *
 */
public class ReceivableForDto extends ReceivableHasDto{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/**是否一次性付款（1是2否  ） */
	private int oneTimePay;
	
	/** 回款时间(允许有多个) */
	private String payMentAmountDate;
	private String payMentAmountDateStart;
	private String payMentAmountDateEnd;
	private String payMentAmountDateFmt;

	/** 回款金额(允许有多个) */
	private BigDecimal payMentAmount;

	/** 回款照片证明url(允许有多个) */
	private String payMentPic;

	private String remark;
	private List<ReceivableForDto> forList;
	private List<ReceivableForDto> newForList;
	
	private Date lendingTime; //放款时间
	private Date customerPaymentTime;  //预计回款时间
	private Double loanAmount;  //放款金额
	private int isMortgage; //是否抵押 
	
	
	private String customerPaymentTimeStr;
	
	private Integer isFrist;  //是否是首期（1:首期  2:尾期）
	
	private String forName;  //节点名称
	private int isRongAn;  //是否推送融安0 否  1：是 
	private String fundCode; //资金方
	private  ReceivableForToHuaRongDto huaRongDto;
	
	/** 报表:总额*/
	private double reportSum;
	/** 报表:总数*/
	private int reportCount;
	

	public ReceivableForToHuaRongDto getHuaRongDto() {
		return huaRongDto;
	}

	public void setHuaRongDto(ReceivableForToHuaRongDto huaRongDto) {
		this.huaRongDto = huaRongDto;
	}

	public String getFundCode() {
		return fundCode;
	}

	public void setFundCode(String fundCode) {
		this.fundCode = fundCode;
	}

	public int getIsRongAn() {
		return isRongAn;
	}

	public void setIsRongAn(int isRongAn) {
		this.isRongAn = isRongAn;
	}

	public String getForName() {
		return forName;
	}

	public void setForName(String forName) {
		this.forName = forName;
	}

	public Integer getIsFrist() {
		return isFrist;
	}

	public void setIsFrist(Integer isFrist) {
		this.isFrist = isFrist;
	}
	
	public List<ReceivableForDto> getNewForList() {
		return newForList;
	}

	public void setNewForList(List<ReceivableForDto> newForList) {
		this.newForList = newForList;
	}

	public String getCustomerPaymentTimeStr() {
		return customerPaymentTimeStr;
	}

	public void setCustomerPaymentTimeStr(String customerPaymentTimeStr) {
		this.customerPaymentTimeStr = customerPaymentTimeStr;
	}

	public List<ReceivableForDto> getForList() {
		return forList;
	}

	public void setForList(List<ReceivableForDto> forList) {
		this.forList = forList;
	}

	public int getIsMortgage() {
		return isMortgage;
	}

	public void setIsMortgage(int isMortgage) {
		this.isMortgage = isMortgage;
	}

	public Double getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(Double loanAmount) {
		this.loanAmount = loanAmount;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getOneTimePay() {
		return oneTimePay;
	}

	public void setOneTimePay(int oneTimePay) {
		this.oneTimePay = oneTimePay;
	}

	public String getPayMentAmountDate() {
		return payMentAmountDate;
	}


	public void setPayMentAmountDate(String payMentAmountDate) {
		this.payMentAmountDate = payMentAmountDate;
	}


	public BigDecimal getPayMentAmount() {
		return payMentAmount;
	}


	public void setPayMentAmount(BigDecimal payMentAmount) {
		this.payMentAmount = payMentAmount;
	}


	public String getPayMentPic() {
		return payMentPic;
	}


	public void setPayMentPic(String payMentPic) {
		this.payMentPic = payMentPic;
	}

	public String getPayMentAmountDateFmt() {
		return payMentAmountDateFmt;
	}

	public void setPayMentAmountDateFmt(String payMentAmountDateFmt) {
		this.payMentAmountDateFmt = payMentAmountDateFmt;
	}

	public double getReportSum() {
		return reportSum;
	}

	public void setReportSum(double reportSum) {
		this.reportSum = reportSum;
	}

	public int getReportCount() {
		return reportCount;
	}

	public void setReportCount(int reportCount) {
		this.reportCount = reportCount;
	}

	public String getPayMentAmountDateStart() {
		return payMentAmountDateStart;
	}

	public void setPayMentAmountDateStart(String payMentAmountDateStart) {
		this.payMentAmountDateStart = payMentAmountDateStart;
	}

	public String getPayMentAmountDateEnd() {
		return payMentAmountDateEnd;
	}

	public void setPayMentAmountDateEnd(String payMentAmountDateEnd) {
		this.payMentAmountDateEnd = payMentAmountDateEnd;
	}

	
}
