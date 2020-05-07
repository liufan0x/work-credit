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
 * 贷款类别:1.商业贷款<br>
 * 每月月供递减额=每月应还本金×月利率=贷款本金÷还款月数×月利率
 * 
 * @author Kevin Chang
 */
public class SameBjSyLoanServiceImpl extends LoanService {
	private static final Log log = LogFactory
			.getLog(SameBjSyLoanServiceImpl.class);
	private Map<String, Double> cacheMap = new HashMap<String, Double>();
	private LoanConditionDto loanConditionDto;
	// 月利率
	private double syMonthlyRates = 0;
	// 贷款总额
	private double syTotal = 0;
	// 期数
	private int months;

	public SameBjSyLoanServiceImpl(LoanConditionDto loanConditionDto) {
		this.loanConditionDto = loanConditionDto;
		this.syMonthlyRates = loanConditionDto.getSyRates() / 100 / 12;
		this.syTotal = loanConditionDto.getSyTotal();
		this.months = loanConditionDto.getMonths();
	}

	/**
	 * 总还款额
	 * 
	 * @return
	 */
	@Override
	public String calcTotalRefund() {
		return NumberUtil.formatDecimalToStr(getTotalInterest() + syTotal);
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
			totalInterest = ((syTotal / months + syTotal * syMonthlyRates) + syTotal
					/ months * (1 + syMonthlyRates))
					/ 2 * months - syTotal;
			totalInterest = NumberUtil.formatDecimal(totalInterest);
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
		double monthlyPayments = (syTotal / months)
				+ (syTotal - returnPrincipal) * syMonthlyRates;
		String monthlyPaymentsStr = NumberUtil
				.formatDecimalToStr(monthlyPayments);
		return monthlyPaymentsStr;
	}

	@Override
	public List<MonthlyItemInfo> calcTotalInfos() {
		double remainder = syTotal;
		List<MonthlyItemInfo> itemInfos = new ArrayList<MonthlyItemInfo>();
		double monthlyPrincipal = getMonthlyPrincipal();
		String fmt_monthlyPrincipal = NumberUtil
				.formatDecimalToStr(monthlyPrincipal);
		for (int i = 1; i <= loanConditionDto.getMonths(); i++) {
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
		double remainderPrincipal = syTotal - (index - 1)
				* getMonthlyPrincipal();
		String monthlyInterest = NumberUtil
				.formatDecimalToStr(remainderPrincipal * syMonthlyRates);
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
			monthlyPrincipal = syTotal / months;
			cacheMap.put("monthlyPrincipal", monthlyPrincipal);
		}
		return monthlyPrincipal;
	}

	@Override
	public String calcTotal() {
		return NumberUtil.formatDecimalToStr(syTotal);
	}
}
