/**
 * 
 */
package com.anjbo.bean.tools;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


/**
 * @author Kevin Chang
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoanConditionDto {
	//贷款类别(1为商业，2为公积金，3为组合贷)
	public int loanType;
	//计算方式(1为根据贷款总额计算，2为根据面积单价计算)
	public int calcType;
	//面积
	public double area;
	//单价
	public double unitPrice;
	//按揭成数
	public int percentage;
	// 商业贷款总额（为公积金贷款时为空）
	public double syTotal;
	//公积金贷款总额（为商业贷款时为空）
	public double gjjTotal;
	//商业贷款利率（6.15%传参为6.15）
	public double syRates;
	//公积金贷款利率（4.25%传参为4.25）
	public double gjjRates;
	// 还款期数（1年为12期，2年为24期，以此类推）
	public int months;
	// 首次还款时间(如：2015-01-05)
	public String firstDate;
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("loanType=").append(loanType).append(";");
		sb.append("calcType=").append(calcType).append(";");
		sb.append("area=").append(area).append(";");
		sb.append("unitPrice=").append(unitPrice).append(";");
		sb.append("percentage=").append(percentage).append(";");
		sb.append("syTotal=").append(syTotal).append(";");
		sb.append("gjjTotal=").append(gjjTotal).append(";");
		sb.append("syRates=").append(syRates).append(";");
		sb.append("gjjRates=").append(gjjRates).append(";");
		sb.append("months=").append(months).append(";");
		sb.append("firstDate=").append(firstDate).append(";");
		return sb.toString();
	}
	public int getLoanType() {
		return loanType;
	}
	
	public int getMonths() {
		return months;
	}
	public void setMonths(int months) {
		this.months = months;
	}
	public String getFirstDate() {
		return firstDate;
	}
	public void setFirstDate(String firstDate) {
		this.firstDate = firstDate;
	}

	public double getSyTotal() {
		return syTotal;
	}

	public void setSyTotal(double syTotal) {
		this.syTotal = syTotal;
	}

	public double getGjjTotal() {
		return gjjTotal;
	}

	public void setGjjTotal(double gjjTotal) {
		this.gjjTotal = gjjTotal;
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

	public void setLoanType(int loanType) {
		this.loanType = loanType;
	}

	public int getCalcType() {
		return calcType;
	}

	public void setCalcType(int calcType) {
		this.calcType = calcType;
	}

	public double getArea() {
		return area;
	}

	public void setArea(double area) {
		this.area = area;
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public int getPercentage() {
		return percentage;
	}

	public void setPercentage(int percentage) {
		this.percentage = percentage;
	}
	
}
