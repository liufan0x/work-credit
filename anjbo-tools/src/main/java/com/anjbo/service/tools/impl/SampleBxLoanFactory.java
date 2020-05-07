/**
 * 
 */
package com.anjbo.service.tools.impl;


import com.anjbo.bean.tools.LoanConditionDto;
import com.anjbo.service.tools.LoanService;

/**
 * @author Kevin Chang
 * 
 */
public class SampleBxLoanFactory {
	public static LoanService getLoanCalc(LoanConditionDto loanConditionDto) {
		// 贷款类型
		int loanType = loanConditionDto.getLoanType();
		// 计算方式
		int calcType = loanConditionDto.getCalcType();
		double total = 0;
		if (calcType == 2) {
			// 计算贷款总额
			total = loanConditionDto.getArea()
					* loanConditionDto.getUnitPrice()
					* loanConditionDto.getPercentage() / 10;
		}
		if (loanType == 1) {
			// 商业
			if (calcType == 2) {
				loanConditionDto.setSyTotal(total);
			}
			return new SameBxSyLoanServiceImpl(loanConditionDto);
		} else if (loanType == 2) {
			// 公积金
			if (calcType == 2) {
				loanConditionDto.setGjjTotal(total);
			}
			return new SameBxGjjLoanServiceImpl(loanConditionDto);
		} else if (loanType == 3) {
			// 组合
			return new SameBxZonghLoanServiceImpl(loanConditionDto);
		}
		return null;
	}
}
