package com.anjbo.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anjbo.bean.product.SignInsuranceDto;
import com.anjbo.dao.SignInsuranceMapper;
import com.anjbo.service.SignInsuranceService;
@Service
public class SignInsuranceServiceImpl implements SignInsuranceService{

	@Resource
	private SignInsuranceMapper signInsuranceMapper;
	
	@Override
	public SignInsuranceDto select(SignInsuranceDto dto) {
		return signInsuranceMapper.select(dto);
	}

}
