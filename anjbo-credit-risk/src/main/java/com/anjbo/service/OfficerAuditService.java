package com.anjbo.service;

import com.anjbo.bean.risk.OfficerAuditDto;

public interface OfficerAuditService {

	OfficerAuditDto detail(String orderNo);
	
	int update(OfficerAuditDto obj);
	
	int insert(OfficerAuditDto obj);
}
