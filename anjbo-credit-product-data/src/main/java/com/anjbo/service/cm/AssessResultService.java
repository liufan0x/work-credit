package com.anjbo.service.cm;

import com.anjbo.bean.cm.AssessDto;
import com.anjbo.bean.cm.AssessResultDto;
import com.anjbo.bean.cm.LoanAuditResultDto;





/**
 * 评估反馈接口
 * @author chenzm   
 * @date 2017-8-23 下午05:26:05
 */
public interface AssessResultService {
	/**
	 * 新增评估反馈结果
	 * @param assessResultDto
	 * @return
	 */
    int addAssessResult(AssessResultDto assessResultDto);
    
    AssessDto selectOrderNoByAssessOrderNo(String orderNo);
    
    /**
     * 根据评估申请编号查询订单编号，用于校验申请编号是否有效
     * @param appNo
     * @return
     */
    AssessDto selectOrderNoByAssessAppNo(String appNo);
    /**
     * 添加意见反馈
     * @return
     */
    int addResult(LoanAuditResultDto auditResultDto);
}
