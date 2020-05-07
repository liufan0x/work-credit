package com.anjbo.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anjbo.bean.finance.LendingInstructionsDto;
import com.anjbo.bean.finance.LendingPayDto;
import com.anjbo.dao.LendingHarvestMapper;
import com.anjbo.dao.LendingInstructionMapper;
import com.anjbo.dao.LendingPayMapper;
import com.anjbo.service.LendingPayService;

@Service
public class LendingPayServiceImpl implements LendingPayService {
	@Resource LendingPayMapper lendingPayMapper;
	@Resource LendingInstructionMapper lendingInstructionMapper;
	
	/**
	 * 详情
	 */
	@Override
	public LendingPayDto findByPay(LendingPayDto lendingPayDto) {
		return lendingPayMapper.findByPay(lendingPayDto);
	}
	
	/**
	 * 完善信息
	 */
	@Override
	public int updatePay(LendingPayDto lendingPayDto) {
		return lendingPayMapper.updatePay(lendingPayDto);
	}


	@Override
	public int addLendingPay(LendingPayDto dto) {
		// TODO Auto-generated method stub
		return lendingPayMapper.addLendingPay(dto);
	}

	

}
