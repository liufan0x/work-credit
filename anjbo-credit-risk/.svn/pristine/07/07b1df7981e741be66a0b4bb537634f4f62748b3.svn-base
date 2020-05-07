package com.anjbo.service;

import java.util.Map;

import com.anjbo.bean.order.OrderBaseBorrowDto;
import com.anjbo.bean.risk.DataAuditDto;
import com.anjbo.bean.risk.FirstAuditDto;

public interface FundDockingService {

	public DataAuditDto fund(String orderNo);
	public int add(DataAuditDto auditDto);
	public int delete(String orderNo);
	
	public int addOrdinary(Map<String, Object> parament);
	public int addOrdinaryAudit(Map<String, Object> parament);
	public OrderBaseBorrowDto findByOrdinary(String orderNo);
	public FirstAuditDto findByOrdinaryAudit(String orderNo);
}
