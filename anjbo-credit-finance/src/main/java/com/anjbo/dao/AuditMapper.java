package com.anjbo.dao;

import com.anjbo.bean.finance.AuditDto;

public interface AuditMapper {
	public int addAudit(AuditDto auditDto);
	public int deleteAudit(String orderNO);
	public AuditDto selectAudit(String orderNo);
}
