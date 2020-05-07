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
 * 贷款类别:3.组合贷款<br>
 * 每月月供递减额=每月应还本金×月利率=贷款本金÷还款月数×月利率
 * 
 * @author Kevin Chang
 */
public class SameBjZonghLoanServiceImpl extends LoanService {
	private static final Log log = LogFactory
			.getLog(SameBjZonghLoanServiceImpl.class);
	private Map<String, Double> cacheMap = new HashMap<String, Double>();
	private LoanConditionDto loanConditionDto;
	// 商业月利率
	private double syMonthlyRates = 0;
	// 公积金月利率
	private double gjjMonthlyRates = 0;
	// 商业贷款总额
	private double syTotal = 0;
	// 公积金贷款总额
	private double gjjTotal = 0;
	// 总额
	private double total = 0;
	// 期数
	private int months = 0;

	public SameBjZonghLoanServiceImpl(LoanConditionDto loanConditionDto) {
		this.loanConditionDto = loanConditionDto;
		this.syTotal = loanConditionDto.getSyTotal();
		this.syMonthlyRates = loanConditionDto.getSyRates() / 100 / 12;
		this.gjjTotal = loanConditionDto.getGjjTotal();
		this.gjjMonthlyRates = loanConditionDto.getGjjRates() / 100 / 12;
		this.total = syTotal + gjjTotal;
		this.months = loanConditionDto.getMonths();
	}

	/**
	 * 总还款额
	 * 
	 * @return
	 */
	@Override
	public String calcTotalRefund() {
		return NumberUtil.formatDecimalToStr(getTotalInterest() + total);
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
			// 商业
			double syTotalInterest = ((syTotal / months + syTotal
					* syMonthlyRates) + syTotal / months * (1 + syMonthlyRates))
					/ 2 * months - syTotal;
			// 公积金
			double gjjTotalInterest = ((gjjTotal / months + gjjTotal
					* gjjMonthlyRates) + gjjTotal / months
					* (1 + gjjMonthlyRates))
					/ 2 * months - gjjTotal;
			totalInterest = NumberUtil.formatDecimal(syTotalInterest
					+ gjjTotalInterest);
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
		// 商业
		double syReturnPrincipal = (index - 1) * getSyMonthlyPrincipal();
		double syMonthlyPayments = (syTotal / months)
				+ (syTotal - syReturnPrincipal) * syMonthlyRates;
		// 公积金
		double gjjReturnPrincipal = (index - 1) * getGjjMonthlyPrincipal();
		double gjjMonthlyPayments = (gjjTotal / months)
				+ (gjjTotal - gjjReturnPrincipal) * gjjMonthlyRates;
		String monthlyPayments = NumberUtil
				.formatDecimalToStr(syMonthlyPayments + gjjMonthlyPayments);
		return monthlyPayments;
	}

	@Override
	public List<MonthlyItemInfo> calcTotalInfos() {
		double remainder = total;
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
		// 商业
		double syRemainderPrincipal = syTotal - (index - 1)
				* getSyMonthlyPrincipal();
		double syMonthlyInterest = NumberUtil
				.formatDecimal(syRemainderPrincipal * syMonthlyRates);
		// 公积金
		double gjjRemainderPrincipal = gjjTotal - (index - 1)
				* getGjjMonthlyPrincipal();
		double gjjMonthlyInterest = NumberUtil
				.formatDecimal(gjjRemainderPrincipal * gjjMonthlyRates);
		String monthlyInterest = NumberUtil
				.formatDecimalToStr(syMonthlyInterest + gjjMonthlyInterest);
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
			// 商业
			double syMonthlyPrincipal = getSyMonthlyPrincipal();
			// 公积金
			double gjjMonthlyPrincipal = getGjjMonthlyPrincipal();
			monthlyPrincipal = syMonthlyPrincipal + gjjMonthlyPrincipal;
			cacheMap.put("monthlyPrincipal", monthlyPrincipal);
		}
		return monthlyPrincipal;
	}

	/**
	 * 商业每月应还本金
	 */
	private double getSyMonthlyPrincipal() {
		double syMonthlyPrincipal = syTotal / months;
		return syMonthlyPrincipal;
	}

	/**
	 * 公积金每月应还本金
	 * 
	 * @return
	 */
	private double getGjjMonthlyPrincipal() {
		double gjjMonthlyPrincipal = gjjTotal / months;
		return gjjMonthlyPrincipal;
	}

	@Override
	public String calcTotal() {
		return NumberUtil.formatDecimalToStr(total);
	}
}
