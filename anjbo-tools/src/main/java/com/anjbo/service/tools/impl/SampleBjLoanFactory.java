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
public class SampleBjLoanFactory {
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
			if (calcType == 2) {
				loanConditionDto.setSyTotal(total);
			}
			return new SameBjSyLoanServiceImpl(loanConditionDto);
		} else if (loanType == 2) {
			if (calcType == 2) {
				loanConditionDto.setGjjTotal(total);
			}
			return new SameBjGjjLoanServiceImpl(loanConditionDto);
		} else if (loanType == 3) {
			return new SameBjZonghLoanServiceImpl(loanConditionDto);
		}
		return null;
	}
}
