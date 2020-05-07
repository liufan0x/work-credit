package com.anjbo.service;

import com.anjbo.bean.finance.StatementDto;

public interface StatementService {

	int addStatement(StatementDto statementDto);
	int deleteStatement(String orderNo);
	StatementDto selectStatement(String orderNo);
}
