package com.anjbo.service.cm.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anjbo.bean.cm.LoanLendingResultDto;
import com.anjbo.dao.cm.LoanLendingResultMapper;
import com.anjbo.service.cm.LoanLendingResultService;

/**
 * 贷款反馈实现
 * @author limh limh@anjbo.com   
 * @date 2016-12-28 下午05:29:08
 */

@Service
public class LoanLendingResultServiceImpl implements LoanLendingResultService {
	
	@Resource private LoanLendingResultMapper loanLendingResultMapper;

	@Override
	public int addLoanLendingResult(LoanLendingResultDto loanLendingResultDto) {
		return loanLendingResultMapper.addLoanLendingResult(loanLendingResultDto);
	}

	@Override
	public LoanLendingResultDto selectLoanLendingResult(String orderNo) {
		return loanLendingResultMapper.selectLoanLendingResult(orderNo);
	}
}
