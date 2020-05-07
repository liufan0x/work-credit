package com.anjbo.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anjbo.bean.finance.LendingInterestDto;
import com.anjbo.dao.LendingInterestMapper;
import com.anjbo.service.LendingInterestService;
@Service
public class LendingInterestServoceImpl implements LendingInterestService {

	@Resource LendingInterestMapper lendingInterestMapper;
	
	@Override
	public LendingInterestDto findByInterest(LendingInterestDto InterestDto) {
		// TODO Auto-generated method stub
		return lendingInterestMapper.findByInterest(InterestDto);
	}

	@Override
	public int addLendingInterest(LendingInterestDto InterestDto) {
		// TODO Auto-generated method stub
		return lendingInterestMapper.addLendingInterest(InterestDto);
	}

	@Override
	public int updateInterest(LendingInterestDto InterestDto) {
		// TODO Auto-generated method stub
		return lendingInterestMapper.updateInterest(InterestDto);
	}

	@Override
	public int delectInterest(LendingInterestDto InterestDto) {
		// TODO Auto-generated method stub
		return lendingInterestMapper.delectInterest(InterestDto);
	}

}
