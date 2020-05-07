package com.anjbo.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anjbo.dao.FinancialMapper;
import com.anjbo.service.FinancialService;

 @Service
public class FinancialServiceImpl implements FinancialService{
	 
	@Resource
    FinancialMapper financialMapper;
	
	@Override
	public List<Map<String, Object>> financialList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return financialMapper.financialList(map);
	}

	@Override
	public int financialCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return financialMapper.financialCount(map);
	}

}
