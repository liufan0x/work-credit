package com.anjbo.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anjbo.bean.risk.OfficerAuditDto;
import com.anjbo.dao.OfficerAuditMapper;
import com.anjbo.service.OfficerAuditService;
/**
 * 首席风险官
 * @author huanglj
 *
 */
@Transactional
@Service
public class OfficerAuditServiceImpl implements OfficerAuditService{

	@Resource
	private OfficerAuditMapper officerAuditMapper;
	
	public OfficerAuditDto detail(String orderNo){
		OfficerAuditDto obj = officerAuditMapper.detail(orderNo);
		return  null==obj?new OfficerAuditDto():obj;
	}
	
	public int update(OfficerAuditDto obj){
		return officerAuditMapper.update(obj);
	}
	
	public int insert(OfficerAuditDto obj){
		int success = 0;
		OfficerAuditDto tmp = officerAuditMapper.detail(obj.getOrderNo());
		if(null==tmp){
			success = officerAuditMapper.insert(obj);
		} else {
			success = officerAuditMapper.update(obj);
		}
		return success;
	}
}
