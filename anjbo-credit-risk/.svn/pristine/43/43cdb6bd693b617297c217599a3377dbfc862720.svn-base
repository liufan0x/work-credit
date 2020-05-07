package com.anjbo.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anjbo.bean.risk.ReviewAuditDto;
import com.anjbo.dao.ReviewAuditMapper;
import com.anjbo.service.ReviewAuditService;

@Service
public class ReviewAuditServiceImpl implements ReviewAuditService {

	@Resource ReviewAuditMapper reviewAuditMapper;

	public ReviewAuditDto detail(String orderNo){
		return reviewAuditMapper.detail(orderNo);
	}
	
	public int update(ReviewAuditDto obj){
		return reviewAuditMapper.update(obj);
	}
	
	public int insert(ReviewAuditDto obj){
		int count=reviewAuditMapper.delete(obj.getOrderNo());
		return reviewAuditMapper.insert(obj);
	}
	
}
