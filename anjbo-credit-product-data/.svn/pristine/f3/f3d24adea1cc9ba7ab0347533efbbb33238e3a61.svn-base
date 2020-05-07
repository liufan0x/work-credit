package com.anjbo.service.cm.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anjbo.bean.cm.LoanAuditResultDto;
import com.anjbo.dao.cm.FeedbackResultMapper;
import com.anjbo.service.cm.FeedbackResultService;



/**
 * 审批结果反馈
 * @author chenzm  
 * @date 2017-08-29 下午05:29:08
 */

@Service
public class FeedbackResultServiceImpl implements FeedbackResultService {
	
	@Resource private FeedbackResultMapper loanAuditResultMapper;



	@Override
	public int addLoanFeedResult(LoanAuditResultDto loanAuditResultDto) {
		return loanAuditResultMapper.addLoanFeedResult(loanAuditResultDto);
	}

	
}
