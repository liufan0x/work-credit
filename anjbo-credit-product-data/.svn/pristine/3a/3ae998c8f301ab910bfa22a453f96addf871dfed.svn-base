package com.anjbo.service.cm.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anjbo.bean.cm.AssessDto;
import com.anjbo.bean.cm.AssessResultDto;
import com.anjbo.bean.cm.LoanAuditResultDto;
import com.anjbo.dao.cm.AssessMapper;
import com.anjbo.dao.cm.AssessResultMapper;

import com.anjbo.service.cm.AssessResultService;

/**
 * 评估反馈实现
 * @author chenzm   
 * @date 2017-8-23 下午05:29:08
 */

@Service
public class AssessResultServiceImpl implements AssessResultService {
	@Resource private AssessMapper assessMapper;
	@Resource private AssessResultMapper assessResultMapper;

	@Override
	public int addAssessResult(AssessResultDto assessResultDto) {
		return assessResultMapper.addAssessResult(assessResultDto);
	}

	//根据评估申请编号查询订单编号，用于校验申请编号是否有效
	@Override
	public AssessDto selectOrderNoByAssessAppNo(String appNo) {
		return assessMapper.selectOrderNoByAssessAppNo(appNo);
	}

	@Override
	public AssessDto selectOrderNoByAssessOrderNo(String orderNo) {
		// TODO Auto-generated method stub
		return assessMapper.selectOrderNoByAssessOrderNo(orderNo);
	}

	@Override
	public int addResult(LoanAuditResultDto auditResultDto) {
		// TODO Auto-generated method stub
		return assessMapper.addResult(auditResultDto);
	}


}
