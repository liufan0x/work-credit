package com.anjbo.service.cm.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anjbo.bean.cm.LoanDto;
import com.anjbo.dao.cm.LoanMapper;
import com.anjbo.service.cm.LoanService;

/**
 * 实现
 * @author limh limh@anjbo.com   
 * @date 2016-12-28 下午05:29:08
 */

@Service
public class LoanServiceImpl implements LoanService {
	
	@Resource private LoanMapper loanMapper;

	@Override
	public int addLoan(LoanDto loanDto) {
		return loanMapper.addLoan(loanDto);
	}

	@Override
	public List<LoanDto> selectLoanList(Map<String, Object> params) {
		return loanMapper.selectLoanList(params);
	}

	@Override
	public int selectLoanCount(Map<String, Object> params) {
		return loanMapper.selectLoanCount(params);
	}

	@Override
	public int updateLoan(LoanDto loanDto) {
		return loanMapper.updateLoan(loanDto);
	}

	@Override
	public int updateLoanStatus(LoanDto loanDto) {
		return loanMapper.updateLoanStatus(loanDto);
	}

	@Override
	public LoanDto selectLoanByOrderNo(String orderNo) {
		return loanMapper.selectLoanByOrderNo(orderNo);
	}

	@Override
	public LoanDto selectOrderNoByLoanAppNo(String appNo) {
		return loanMapper.selectOrderNoByLoanAppNo(appNo);
	}

	@Override
	public List<Map<String, Object>> selectLoanProgressFlow(String orderNo) {
		return loanMapper.selectLoanProgressFlow(orderNo);
	}

	@Override
	public List<LoanDto> selectLoanListByStatus(String status) {
		return loanMapper.selectLoanListByStatus(status);
	}

	@Override
	public int updateLoanProgressNo(Map<String, Object> param) {
		return loanMapper.updateLoanProgressNo(param);
	}

	@Override
	public int addLoanProgressFlow(Map<String, Object> param) {
		return loanMapper.addLoanProgressFlow(param);
	}

	@Override
	public int addOrderClose(LoanDto loanDto) {
		return loanMapper.addOrderClose(loanDto);
	}

	@Override
	public Map<String, Object> selectOrderCloseReason(String orderNo) {
		return loanMapper.selectOrderCloseReason(orderNo);
	}
}
