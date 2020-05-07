package com.anjbo.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anjbo.bean.finance.AuditDto;
import com.anjbo.dao.AuditMapper;
import com.anjbo.service.AuditService;
@Service
public class AuditServiceImpl implements AuditService {

	@Resource
	AuditMapper auditMapper;
	
	@Override
	public int addAudit(AuditDto auditDto) {
		// TODO Auto-generated method stub
		auditMapper.deleteAudit(auditDto.getOrderNo());
		return auditMapper.addAudit(auditDto);
	}

	@Override
	public int deleteAudit(String orderNO) {
		// TODO Auto-generated method stub
		return auditMapper.deleteAudit(orderNO);
	}

	@Override
	public AuditDto selectAudit(String orderNo) {
		// TODO Auto-generated method stub
		return auditMapper.selectAudit(orderNo);
	}

}
