package com.anjbo.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anjbo.bean.product.MortgageDto;
import com.anjbo.dao.MortgageMapper;
import com.anjbo.service.MortgageService;

@Service
public class MortgageServiceImpl implements MortgageService {

	@Resource MortgageMapper mortgageMapper;
	@Override
	public MortgageDto selectMortgage(MortgageDto dto) {
		// TODO Auto-generated method stub
		return mortgageMapper.selectMortgage(dto);
	}

	@Override
	public int addMortgage(MortgageDto dto) {
		// TODO Auto-generated method stub
		return mortgageMapper.addMortgage(dto);
	}

	@Override
	public int updateMortgage(MortgageDto dto) {
		// TODO Auto-generated method stub
		return mortgageMapper.updateMortgage(dto);
	}

}
