package com.anjbo.bean.tools;

import java.util.List;

public class LoanInfo {
	// 贷款总额
	private String total;
	// 总还款金额
	private String totalRefund;
	// 总利息
	private String totalInterest;
	// 每月月供(针对等额本息)
	private String monthlyPayments;
	// 每月本金(针对等额本金)
	private String monthlyPrincipal;
	// 每期详情列表
	private List<MonthlyItemInfo> totalInfos;
	// 商业贷款利率（6.15%传参为6.15）
	public double syRates;
	// 公积金贷款利率（4.25%传参为4.25）
	public double gjjRates;
	// 还款期数（1年为12期，2年为24期，以此类推）
	public int months;

	public String getTotalRefund() {
		return totalRefund;
	}

	public void setTotalRefund(String totalRefund) {
		this.totalRefund = totalRefund;
	}

	public String getTotalInterest() {
		return totalInterest;
	}

	public void setTotalInterest(String totalInterest) {
		this.totalInterest = totalInterest;
	}

	public String getMonthlyPayments() {
		return monthlyPayments;
	}

	public void setMonthlyPayments(String monthlyPayments) {
		this.monthlyPayments = monthlyPayments;
	}

	public String getMonthlyPrincipal() {
		return monthlyPrincipal;
	}

	public void setMonthlyPrincipal(String monthlyPrincipal) {
		this.monthlyPrincipal = monthlyPrincipal;
	}

	public List<MonthlyItemInfo> getTotalInfos() {
		return totalInfos;
	}

	public void setTotalInfos(List<MonthlyItemInfo> totalInfos) {
		this.totalInfos = totalInfos;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public double getSyRates() {
		return syRates;
	}

	public void setSyRates(double syRates) {
		this.syRates = syRates;
	}

	public double getGjjRates() {
		return gjjRates;
	}

	public void setGjjRates(double gjjRates) {
		this.gjjRates = gjjRates;
	}

	public int getMonths() {
		return months;
	}

	public void setMonths(int months) {
		this.months = months;
	}

	@Override
	public String toString() {
		return "\nLoanInfo [总还款金额=" + totalRefund + ", 总利息=" + totalInterest
				+ ", 每月月供=" + monthlyPayments + ", 每月本金=" + monthlyPrincipal
				+ ", 每期详情列表=" + totalInfos + "]";
	}

}
