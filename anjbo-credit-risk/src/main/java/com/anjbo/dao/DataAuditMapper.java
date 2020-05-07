package com.anjbo.dao;

import com.anjbo.bean.risk.DataAuditDto;

public interface DataAuditMapper {
	
	public int insert(DataAuditDto auditDto);
	public int update(DataAuditDto auditDto);
	public DataAuditDto detail(String orderNo);
}
