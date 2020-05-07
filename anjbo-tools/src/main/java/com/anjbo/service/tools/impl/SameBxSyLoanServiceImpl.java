package com.anjbo.service.tools.impl;


import com.anjbo.bean.tools.LoanConditionDto;
import com.anjbo.bean.tools.MonthlyItemInfo;
import com.anjbo.common.SimpleDateUtils;
import com.anjbo.service.tools.LoanService;
import com.anjbo.utils.NumberUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 还款方式：等额本息<br>
 * 贷款类别:1.商业贷款<br>
 * 
 * @author Kevin Chang
 * 
 */
public class SameBxSyLoanServiceImpl extends LoanService {
	private static final Log log = LogFactory
			.getLog(SameBxSyLoanServiceImpl.class);
	private Map<String, Double> cacheMap = new HashMap<String, Double>();
	private LoanConditionDto loanConditionDto;
	// 月利率
	private double monthlyRates = 0;
	// 贷款总额
	private double syTotal = 0;
	//
	private int months;

	public SameBxSyLoanServiceImpl(LoanConditionDto loanConditionDto) {
		this.loanConditionDto = loanConditionDto;
		monthlyRates = loanConditionDto.getSyRates() / 100 / 12;
		syTotal = loanConditionDto.getSyTotal();
		months = loanConditionDto.getMonths();
	}

	/**
	 * 总还款金额=本金+总利息
	 */
	@Override
	public String calcTotalRefund() {
		// 总利息
		Double totalInterest = getTotalInterest();
		// 总还款金额
		String totalRefund = NumberUtil.formatDecimalToStr(syTotal
				+ totalInterest);
		return totalRefund;
	}

	/**
	 * 总利息=还款月数×每月月供额-贷款本金
	 */
	@Override
	public String calcTotalInterest() {
		return NumberUtil.formatDecimalToStr(getTotalInterest());
	}

	/**
	 * 每月月供额=〔贷款本金×月利率×(1＋月利率)＾还款月数〕÷〔(1＋月利率)＾还款月数-1〕
	 */
	@Override
	public String calcMonthlyPayments(int index) {
		return NumberUtil.formatDecimalToStr(getMonthlyPayments());
	}

	/**
	 * 所有期还款详情
	 */
	@Override
	public List<MonthlyItemInfo> calcTotalInfos() {
		double remainder = syTotal;
		List<MonthlyItemInfo> itemInfos = new ArrayList<MonthlyItemInfo>();
		for (int i = 1; i <= loanConditionDto.getMonths(); i++) {
			MonthlyItemInfo itemInfo = new MonthlyItemInfo();
			itemInfo.setIndex(i);
			itemInfo.setIndexDetail(SimpleDateUtils.calcDateStr(
					loanConditionDto.getFirstDate(), i));
			itemInfo.setMonthlyPayments(calcMonthlyPayments(0));
			double monthlyPrincipal = getMonthlyPrincipal(i);
			itemInfo.setMonthlyPrincipal(NumberUtil
					.formatDecimalToStr(monthlyPrincipal));
			itemInfo.setMonthlyInterest(NumberUtil
					.formatDecimalToStr(getMonthlyInterest(i)));
			remainder -= monthlyPrincipal;
			if (remainder < 1) {
				remainder = 0;
			}
			itemInfo.setRemainder(NumberUtil.formatDecimalToStr(remainder));
			itemInfos.add(itemInfo);
		}
		return itemInfos;
	}

	/**
	 * 每月应还利息=贷款本金×月利率×〔(1+月利率)^还款月数-(1+月利率)^(还款月序号-1)〕÷〔(1+月利率)^还款月数-1〕
	 */
	@Override
	public String calcMonthlyInterest(int index) {
		return NumberUtil.formatDecimalToStr(getMonthlyInterest(index));
	}

	/**
	 * 每月应还本金=贷款本金×月利率×(1+月利率)^(还款月序号-1)÷〔(1+月利率)^还款月数-1〕
	 */
	@Override
	public String calcMonthlyPrincipal(int index) {
		return NumberUtil.formatDecimalToStr(getMonthlyPrincipal(index));
	}

	/**
	 * 计算每月月供额
	 * 
	 * @return
	 */
	private double getMonthlyPayments() {
		Double monthlyPayments = cacheMap.get("monthlyPayments");
		if (monthlyPayments == null) {
			monthlyPayments = (syTotal * monthlyRates * Math.pow(
					(1 + monthlyRates), months))
					/ (Math.pow(1 + monthlyRates, months) - 1);
			cacheMap.put("monthlyPayments", monthlyPayments);
		}
		return monthlyPayments;
	}

	/**
	 * 计算总利息
	 * 
	 * @return
	 */
	private double getTotalInterest() {
		// 总利息
		Double totalInterest = cacheMap.get("totalInterest");
		if (totalInterest == null) {
			// 每月月供额
			double monthlyPayments = getMonthlyPayments();
			totalInterest = months * monthlyPayments - syTotal;
			cacheMap.put("totalInterest", totalInterest);
		}
		return totalInterest;
	}

	/**
	 * 计算每月应还本金
	 * 
	 * @return
	 */
	private double getMonthlyPrincipal(int index) {
		if (index == 0) {
			return 0;
		}
		double monthlyPrincipal = syTotal * monthlyRates
				* Math.pow(1 + monthlyRates, index - 1)
				/ (Math.pow(1 + monthlyRates, months) - 1);
		return monthlyPrincipal;
	}

	/**
	 * 计算每月应还利息
	 * 
	 * @param index
	 * @return
	 */
	private double getMonthlyInterest(int index) {
		double monthlyInterest = syTotal
				* monthlyRates
				* (Math.pow((1 + monthlyRates), months) - Math.pow(
						1 + monthlyRates, index - 1))
				/ (Math.pow(1 + monthlyRates, months) - 1);
		return monthlyInterest;
	}

	@Override
	public String calcTotal() {
		return NumberUtil.formatDecimalToStr(syTotal);
	}
}
