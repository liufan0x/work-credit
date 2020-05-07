package com.anjbo.dao;

import com.anjbo.bean.risk.ReviewAuditDto;

public interface ReviewAuditMapper {
    
	ReviewAuditDto detail(String orderNo);
	
	int update(ReviewAuditDto obj);
	
	int insert(ReviewAuditDto obj);
	int delete(String orderNo);
}
