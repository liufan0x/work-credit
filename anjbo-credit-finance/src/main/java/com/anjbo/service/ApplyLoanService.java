package com.anjbo.service;

import com.anjbo.bean.finance.ApplyLoanDto;

public interface ApplyLoanService {

	
	public ApplyLoanDto findByApplyLoan(String orderNo);
	public int deleteApplyLoan(String orderNo);
	public int addApplyLoan(ApplyLoanDto applyLoanDto);
	
}
