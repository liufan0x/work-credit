package com.anjbo.service;

import com.anjbo.bean.finance.AuditDto;

public interface AuditService {

	
	public int addAudit(AuditDto auditDto);
	public int deleteAudit(String orderNO);
	public AuditDto selectAudit(String orderNo);
}
