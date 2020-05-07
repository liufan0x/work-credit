package com.anjbo.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anjbo.bean.product.UploadInsuranceDto;
import com.anjbo.dao.UploadInsuranceMapper;
import com.anjbo.service.UploadInsuranceService;
@Service
public class UploadInsuranceServiceImpl implements UploadInsuranceService{

	@Resource
	private UploadInsuranceMapper uploadInsuranceMapper;
	
	@Override
	public UploadInsuranceDto select(UploadInsuranceDto dto) {
		return uploadInsuranceMapper.select(dto);
	}

}
