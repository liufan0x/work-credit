package com.anjbo.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anjbo.bean.finance.StatementDto;
import com.anjbo.dao.StatementMapper;
import com.anjbo.service.StatementService;

@Service
public class StatementServiceImpl implements StatementService{

	@Resource  StatementMapper statementMapper;
	
	@Override
	public int addStatement(StatementDto statementDto) {
		// TODO Auto-generated method stub
		statementMapper.deleteStatement(statementDto.getOrderNo());
		return statementMapper.addStatement(statementDto);
	}

	@Override
	public int deleteStatement(String orderNo) {
		// TODO Auto-generated method stub
		return statementMapper.deleteStatement(orderNo);
	}

	@Override
	public StatementDto selectStatement(String orderNo) {
		// TODO Auto-generated method stub
		return statementMapper.selectStatement(orderNo);
	}

	
	
}
