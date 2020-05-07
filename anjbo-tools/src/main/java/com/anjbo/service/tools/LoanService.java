package com.anjbo.service.tools;



import com.anjbo.bean.tools.LoanInfo;
import com.anjbo.bean.tools.MonthlyItemInfo;

import java.util.List;

public abstract class LoanService {
	public abstract String calcTotal();
	
	public abstract String calcTotalRefund();

	public abstract String calcTotalInterest();

	public abstract String calcMonthlyPayments(int index);

	public abstract String calcMonthlyInterest(int index);

	public abstract String calcMonthlyPrincipal(int index);

	public abstract List<MonthlyItemInfo> calcTotalInfos();

	public final LoanInfo calcLoanInfo() throws Exception{
		LoanInfo loanInfo = new LoanInfo();
		loanInfo.setTotal(calcTotal());
		loanInfo.setTotalRefund(calcTotalRefund());
		loanInfo.setTotalInterest(calcTotalInterest());
		loanInfo.setTotalInfos(calcTotalInfos());
		loanInfo.setMonthlyPrincipal(calcMonthlyPrincipal(0));
		loanInfo.setMonthlyPayments(calcMonthlyPayments(0));
		return loanInfo;
	}
}
