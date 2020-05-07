package com.anjbo.service.huarong.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.anjbo.dao.huarong.BorrowLendMapper;
import com.anjbo.dao.huarong.BorrowRepaymentMapper;
import com.anjbo.dao.huarong.FileApplyMapper;
import com.anjbo.dao.huarong.KgAppointMapper;
import com.anjbo.dao.huarong.KgApprovalMapper;
import com.anjbo.dao.huarong.KgHouseMapper;
import com.anjbo.dao.huarong.KgIndivMapper;
import com.anjbo.dao.huarong.LcApplApptMapper;
import com.anjbo.dao.huarong.LcAppointMapper;
import com.anjbo.dao.huarong.LcApptIndivMapper;
import com.anjbo.dao.huarong.RepaymentRegisterMapper;
import com.anjbo.service.huarong.QueryHrSendDataService;
@Service
public class QueryHrSendDataServiceImpl implements QueryHrSendDataService{
	
	@Resource
	private LcAppointMapper lcAppointMapper;
	
	@Resource
	private LcApptIndivMapper lcApptIndivMapper;
	
	@Resource
	private LcApplApptMapper lcApplApptMapper;
	
	@Resource
	private KgAppointMapper kgAppointMapper;
	
	@Resource
	private KgApprovalMapper kgApprovalMapper;
	
	@Resource
	private KgHouseMapper kgHouseMapper;
	
	@Resource
	private KgIndivMapper kgIndivMapper;
	
	@Resource
	private BorrowLendMapper borrowLendMapper;
	
	@Resource
	private BorrowRepaymentMapper borrowRepaymentMapper;//应还款
	
	@Resource
	private RepaymentRegisterMapper repaymentRegisterMapper;//回款
	
	@Resource
	private FileApplyMapper fileApplyMapper;
	
	Logger log = Logger.getLogger(QueryHrSendDataServiceImpl.class);

	@Override
	public Map<String, Object> getLcAppoint(String orderNo) {
		  Map map=lcAppointMapper.getLcAppoint(orderNo);
		  return map;
	}

	@Override
	public Map<String, Object> getLcApplAppt(String orderNo) {
		 Map map=lcApplApptMapper.getLcApplAppt(orderNo);
		  return map;
	}

	@Override
	public Map<String, Object> getLcApptIndiv(String orderNo) {
		Map map=lcApptIndivMapper.getLcApptIndiv(orderNo);
		  return map;
	}

	@Override
	public Map<String, Object> getKgAppoint(String orderNo) {
		Map map=kgAppointMapper.getKgAppoint(orderNo);
		  return map;
	}
	
	@Override
	public Map<String, Object> getKgApproval(String orderNo) {
		Map map=kgApprovalMapper.getKgApproval(orderNo);
		  return map;
	}
	
	@Override
	public Map<String, Object> getKgHouse(String orderNo) {
		Map map=kgHouseMapper.getKgHouse(orderNo);
		  return map;
	}
	
	@Override
	public Map<String, Object> getKgIndiv(String orderNo) {
		Map map=kgIndivMapper.getKgIndiv(orderNo);
		  return map;
	}
	
	
	@Override
	public List<Map<String, Object>> getFileApply(String orderNo) {
		List<Map<String, Object>> list=fileApplyMapper.getFileApply(orderNo);
		return list;
	}

	@Override
	public Map<String, Object> getBorrowLend(String orderNo) {
		Map map=borrowLendMapper.getBorrowLend(orderNo);
		return map;
	}

	@Override
	public Map<String, Object> getBorrowRepayment(String orderNo) {
		Map map=borrowRepaymentMapper.getBorrowRepayment(orderNo);
		return map;
	}

	@Override
	public Map<String, Object> getRepaymentRegister(String orderNo) {
		Map map=repaymentRegisterMapper.getRepaymentRegister(orderNo);
		return map;
	}
	

}
