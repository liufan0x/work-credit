package com.anjbo.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anjbo.bean.finance.RebateDto;
import com.anjbo.dao.RebateMapper;
import com.anjbo.service.RebateService;

@Service
public class RebateServiceImpl implements RebateService{

	@Resource
	RebateMapper rebateMapper;
	@Override
	public RebateDto findByAll(String orderNO) {
		// TODO Auto-generated method stub
		return rebateMapper.findByAll(orderNO);
	}

	@Override
	public int insert(RebateDto rebateDto) {
		// TODO Auto-generated method stub
		rebateMapper.delete(rebateDto.getOrderNo());
		return rebateMapper.insert(rebateDto);
	}

	@Override
	public int delete(String orderNo) {
		// TODO Auto-generated method stub
		return rebateMapper.delete(orderNo);
	}

}
