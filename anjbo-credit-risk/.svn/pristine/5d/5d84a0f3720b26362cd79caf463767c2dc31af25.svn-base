package com.anjbo.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anjbo.bean.risk.FinalAuditDto;
import com.anjbo.dao.FinalAuditMapper;
import com.anjbo.service.FinalAuditService;

/**
 * 终审
 * @author huanglj
 *
 */
@Transactional
@Service
public class FinalAuditServiceImpl implements FinalAuditService{

	@Resource
	private FinalAuditMapper finalAuditMapper;
	
	public FinalAuditDto detail(String orderNo){
		FinalAuditDto obj = finalAuditMapper.detail(orderNo);
		return null==obj?new FinalAuditDto():obj;
	}
	
	public int update(FinalAuditDto obj){
		return finalAuditMapper.update(obj);
	}
	
	public int insert(FinalAuditDto obj){
		int success = 0;
		FinalAuditDto tmp = finalAuditMapper.detail(obj.getOrderNo());
		if(null==tmp){
//			int count=finalAuditMapper.delete(obj.getOrderNo());
			success = finalAuditMapper.insert(obj);
		} else {
			success = finalAuditMapper.update(obj);
		}
		return success;
	}
}
