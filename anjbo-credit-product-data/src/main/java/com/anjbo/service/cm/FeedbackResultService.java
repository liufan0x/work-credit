package com.anjbo.service.cm;

import com.anjbo.bean.cm.LoanAuditResultDto;



/**
 * 审批结果反馈
 * @author limh limh@anjbo.com   
 * @date 2016-12-28 下午05:26:05
 */
public interface FeedbackResultService {

    /**
     * 新增意见反馈结果
     * @param loanAuditResultDto
     * @return
     */
    int addLoanFeedResult(LoanAuditResultDto loanAuditResultDto);
}
