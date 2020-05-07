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
 * 等额本金<br>
 * 贷款类别:2.公积金贷款<br>
 * 每月月供递减额=每月应还本金×月利率=贷款本金÷还款月数×月利率
 * 
 * @author Kevin Chang
 */
public class SameBjGjjLoanServiceImpl extends LoanService {
	private static final Log log = LogFactory
			.getLog(SameBjGjjLoanServiceImpl.class);
	private Map<String, Double> cacheMap = new HashMap<String, Double>();
	private LoanConditionDto loanConditionDto;
	// 月利率
	private double monthlyRates = 0;
	// 贷款总额
	private double gjjTotal = 0;
	// 期数
	private int months = 0;

	public SameBjGjjLoanServiceImpl(LoanConditionDto loanConditionDto) {
		this.loanConditionDto = loanConditionDto;
		this.monthlyRates = loanConditionDto.getGjjRates() / 100 / 12;
		this.gjjTotal = loanConditionDto.getGjjTotal();
		this.months = loanConditionDto.getMonths();
	}

	/**
	 * 总还款额
	 * 
	 * @return
	 */
	@Override
	public String calcTotalRefund() {
		return NumberUtil.formatDecimalToStr(getTotalInterest() + gjjTotal);
	}

	/**
	 * 总利息=〔(总贷款额÷还款月数+总贷款额×月利率)+总贷款额÷还款月数×(1+月利率)〕÷2×还款月数-总贷款额
	 * 
	 * @return
	 */
	@Override
	public String calcTotalInterest() {
		return NumberUtil.formatDecimalToStr(getTotalInterest());
	}

	private double getTotalInterest() {
		Double totalInterest = cacheMap.get("totalInterest");
		if (totalInterest == null) {
			totalInterest = ((gjjTotal / months + gjjTotal * monthlyRates) + gjjTotal
					/ months * (1 + monthlyRates))
					/ 2 * months - gjjTotal;
			cacheMap.put("totalInterest", totalInterest);
		}
		return totalInterest;
	}

	/**
	 * 每月月供额=(贷款本金÷还款月数)+(贷款本金-已归还本金累计额)×月利率
	 */
	@Override
	public String calcMonthlyPayments(int index) {
		if (index == 0) {
			return "0";
		}
		double returnPrincipal = (index - 1) * getMonthlyPrincipal();
		double monthlyPayments = (gjjTotal / months)
				+ (gjjTotal - returnPrincipal) * monthlyRates;
		String monthlyPaymentsStr = NumberUtil.formatDecimalToStr(monthlyPayments);
		return monthlyPaymentsStr;
	}

	@Override
	public List<MonthlyItemInfo> calcTotalInfos() {
		double remainder = gjjTotal;
		List<MonthlyItemInfo> itemInfos = new ArrayList<MonthlyItemInfo>();
		double monthlyPrincipal = getMonthlyPrincipal();
		String fmt_monthlyPrincipal = NumberUtil
				.formatDecimalToStr(monthlyPrincipal);
		for (int i = 1; i <= months; i++) {
			MonthlyItemInfo itemInfo = new MonthlyItemInfo();
			itemInfo.setIndex(i);
			itemInfo.setIndexDetail(SimpleDateUtils.calcDateStr(
					loanConditionDto.getFirstDate(), i));
			// 还款额
			itemInfo.setMonthlyPayments(calcMonthlyPayments(i));
			itemInfo.setMonthlyPrincipal(fmt_monthlyPrincipal);
			itemInfo.setMonthlyInterest(calcMonthlyInterest(i));
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
	 * 每月应还利息=剩余本金×月利率=(贷款本金-已归还本金累计额)×月利率
	 * 
	 * @param index
	 * @return
	 */
	@Override
	public String calcMonthlyInterest(int index) {
		// 剩余本金
		double remainderPrincipal = gjjTotal - (index - 1)
				* getMonthlyPrincipal();
		String monthlyInterest = NumberUtil.formatDecimalToStr(remainderPrincipal
				* monthlyRates);
		return monthlyInterest;
	}

	/**
	 * 每月应还本金=贷款本金÷还款月数
	 */
	@Override
	public String calcMonthlyPrincipal(int index) {
		return NumberUtil.formatDecimalToStr(getMonthlyPrincipal());
	}

	private double getMonthlyPrincipal() {
		Double monthlyPrincipal = cacheMap.get("monthlyPrincipal");
		if (monthlyPrincipal == null) {
			monthlyPrincipal = gjjTotal / months;
			cacheMap.put("monthlyPrincipal", monthlyPrincipal);
		}
		return monthlyPrincipal;
	}

	@Override
	public String calcTotal() {
		return NumberUtil.formatDecimalToStr(gjjTotal);
	}
}
