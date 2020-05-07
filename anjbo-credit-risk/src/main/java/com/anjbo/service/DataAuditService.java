package com.anjbo.service;

import java.util.Map;

import com.anjbo.bean.risk.DataAuditDto;

public interface DataAuditService {

	public int insert(DataAuditDto auditDto);
	
	public DataAuditDto detail(String orderNo);
	
}
