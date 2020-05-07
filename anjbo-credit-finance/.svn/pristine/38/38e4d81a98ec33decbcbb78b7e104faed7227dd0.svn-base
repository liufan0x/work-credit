package com.anjbo.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anjbo.bean.finance.ReceivablePayDto;
import com.anjbo.dao.ReceivablePayMapper;
import com.anjbo.service.ReceivablePayService;

@Service
public class ReceivablePayServiceImpl implements ReceivablePayService {

	@Resource ReceivablePayMapper receivablePayMapper;
	
	@Override
	public ReceivablePayDto findByReceivablePay(ReceivablePayDto dto) {
		// TODO Auto-generated method stub
		return receivablePayMapper.findByReceivablePay(dto);
	}

	@Override
	public int addReceivablePay(ReceivablePayDto dto) {
		// TODO Auto-generated method stub
		return receivablePayMapper.addReceivablePay(dto);
	}

	@Override
	public int updateReceivablePay(ReceivablePayDto dto) {
		// TODO Auto-generated method stub
		return receivablePayMapper.updateReceivablePay(dto);
	}

}
