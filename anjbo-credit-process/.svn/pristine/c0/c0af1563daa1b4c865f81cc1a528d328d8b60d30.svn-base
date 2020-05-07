package com.anjbo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anjbo.bean.product.ManagerAuditDto;
import com.anjbo.dao.ManagerAuditMapper;
import com.anjbo.service.ManagerAuditService;

@Service
public class ManagerAuditServiceImpl implements ManagerAuditService {

	@Resource  ManagerAuditMapper auditMapper;
	@Override
	public ManagerAuditDto selectManagerAudit(ManagerAuditDto dto) {
		// TODO Auto-generated method stub
		return auditMapper.selectManagerAudit(dto);
	}

	@Override
	public int addManagerAudit(ManagerAuditDto dto) {
		// TODO Auto-generated method stub
		return auditMapper.addManagerAudit(dto);
	}

	@Override
	public int updateManagerAudit(ManagerAuditDto dto) {
		// TODO Auto-generated method stub
		return auditMapper.updateManagerAudit(dto);
	}

	@Override
	public int updateStatus(ManagerAuditDto dto) {
		// TODO Auto-generated method stub
		return auditMapper.updateStatus(dto);
	}

	@Override
	public List<ManagerAuditDto> selectManagerAll(ManagerAuditDto dto) {
		// TODO Auto-generated method stub
		return auditMapper.selectManagerAll(dto);
	}

	@Override
	public int selectManagerCount() {
		// TODO Auto-generated method stub
		return auditMapper.selectManagerCount();
	}


}
