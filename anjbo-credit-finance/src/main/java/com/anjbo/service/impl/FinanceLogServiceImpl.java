package com.anjbo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anjbo.bean.finance.FinanceLogDto;
import com.anjbo.dao.FinanceLogMapper;
import com.anjbo.service.FinanceLogService;

@Service
public class FinanceLogServiceImpl implements FinanceLogService {

	@Resource
	FinanceLogMapper financeLogMapper;
	
	@Override
	public List<FinanceLogDto> findByAll(FinanceLogDto logDto) {
		// TODO Auto-generated method stub
		return financeLogMapper.findByAll(logDto);
	}

	@Override
	public int delete(FinanceLogDto logDto) {
		// TODO Auto-generated method stub
		return financeLogMapper.delete(logDto);
	}

	@Override
	public int insert(FinanceLogDto financeLogDto) {
		// TODO Auto-generated method stub
		return financeLogMapper.insert(financeLogDto);
	}

}
