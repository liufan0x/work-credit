package com.anjbo.service;

import java.util.List;

import com.anjbo.bean.finance.FinanceLogDto;

public interface FinanceLogService {

	public List<FinanceLogDto> findByAll(FinanceLogDto logDto);
	public int delete(FinanceLogDto financeLogDto);
	public int insert(FinanceLogDto financeLogDto);
}
