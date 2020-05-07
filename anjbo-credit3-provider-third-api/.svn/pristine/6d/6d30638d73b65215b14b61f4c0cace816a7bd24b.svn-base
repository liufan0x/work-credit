package com.anjbo.service.hrtrust.impl;

import com.anjbo.controller.api.OrderApi;
import com.anjbo.dao.hrtrust.*;
import com.anjbo.service.hrtrust.QueryHrSendDataService;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class QueryHrSendDataServiceImpl implements QueryHrSendDataService{
	
	@Resource
	private HrtrustLcAppointMapper lcAppointMapper;
	
	@Resource
	private HrtrustLcApptIndivMapper lcApptIndivMapper;
	
	@Resource
	private HrtrustLcApplApptMapper lcApplApptMapper;
	
	@Resource
	private HrtrustKgAppointMapper kgAppointMapper;
	
	@Resource
	private HrtrustKgApprovalMapper kgApprovalMapper;
	
	@Resource
	private HrtrustKgHouseMapper kgHouseMapper;
	
	@Resource
	private HrtrustKgIndivMapper kgIndivMapper;
	
	@Resource
	private HrtrustLoanMapper hrtrustLoanMapper;
	
	@Resource
	private HrtrustRepaymentPlanMapper borrowRepaymentMapper;//应还款
	
	@Resource
	private HrtrustRepaymentInfoMapper repaymentRegisterMapper;//回款
	
	@Resource
	private HrtrustBusinfoMapper fileApplyMapper;
	
	@Resource
	private HrtrustLoanStatusMapper loanStatusMapper;
	
	@Resource
	private OrderApi orderApi;
	
	Logger log = Logger.getLogger(QueryHrSendDataServiceImpl.class);

	@Override
	public Map<String, Object> getLcAppoint(String orderNo) {
		return lcAppointMapper.getLcAppoint(orderNo);
	}

	@Override
	public Map<String, Object> getLcApplAppt(String orderNo) {
		return lcApplApptMapper.getLcApplAppt(orderNo);
	}

	@Override
	public Map<String, Object> getLcApptIndiv(String orderNo) {
		return lcApptIndivMapper.getLcApptIndiv(orderNo);
	}

	@Override
	public Map<String, Object> getKgAppoint(String orderNo) {
		return kgAppointMapper.getKgAppoint(orderNo);
	}
	
	@Override
	public Map<String, Object> getKgApproval(String orderNo) {
		return kgApprovalMapper.getKgApproval(orderNo);
	}
	
	@Override
	public Map<String, Object> getKgHouse(String orderNo) {
		return kgHouseMapper.getKgHouse(orderNo);
	}
	
	@Override
	public Map<String, Object> getKgIndiv(String orderNo) {
		return kgIndivMapper.getKgIndiv(orderNo);
	}
	
	
	@Override
	public List<Map<String, Object>> getFileApply(String orderNo) {
		return fileApplyMapper.getFileApply(orderNo);
	}
	
	@Override
	public Map<String, Object> selectHrtrustLoan(String orderNo) {
		return hrtrustLoanMapper.selectHrtrustLoan(orderNo);
	}

	@Override
	public Map<String, Object> getBorrowRepayment(String orderNo) {
		return borrowRepaymentMapper.getBorrowRepayment(orderNo);
	}

	@Override
	public Map<String, Object> getRepaymentRegister(String orderNo) {
		return repaymentRegisterMapper.getRepaymentRegister(orderNo);
	}

	@Override
	public Map<String, Object> getLoanStatus(String orderNo) {
		return loanStatusMapper.getLoanStatus(orderNo);
	}
	

	
}
