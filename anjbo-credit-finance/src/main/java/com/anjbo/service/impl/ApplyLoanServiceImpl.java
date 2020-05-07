package com.anjbo.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anjbo.bean.finance.ApplyLoanDto;
import com.anjbo.dao.ApplyLoanMapper;
import com.anjbo.service.ApplyLoanService;

@Service
public class ApplyLoanServiceImpl implements ApplyLoanService {

	@Resource 
	private ApplyLoanMapper applyLoanMapper;
	
	@Override
	public ApplyLoanDto findByApplyLoan(String orderNo) {
		// TODO Auto-generated method stub
		return applyLoanMapper.findByApplyLoan(orderNo);
	}

	@Override
	public int addApplyLoan(ApplyLoanDto applyLoanDto) {
		// TODO Auto-generated method stub
//		applyLoanMapper.deleteApplyLoan(applyLoanDto.getOrderNo());
		ApplyLoanDto dto=applyLoanMapper.findByApplyLoan(applyLoanDto.getOrderNo());
		if(dto!=null){
			return applyLoanMapper.updateApplyLoan(applyLoanDto);
		}else{
		    return applyLoanMapper.addApplyLoan(applyLoanDto);
		}
	}

	@Override
	public int deleteApplyLoan(String orderNo) {
		// TODO Auto-generated method stub
		return applyLoanMapper.deleteApplyLoan(orderNo);
	}

}
