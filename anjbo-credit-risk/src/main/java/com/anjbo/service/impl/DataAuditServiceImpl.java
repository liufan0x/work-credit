package com.anjbo.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anjbo.bean.risk.DataAuditDto;
import com.anjbo.dao.DataAuditMapper;
import com.anjbo.service.DataAuditService;

@Service
public class DataAuditServiceImpl implements DataAuditService {

	@Resource
	private DataAuditMapper auditMapper;
	@Override
	public int insert(DataAuditDto auditDto) {
		// TODO Auto-generated method stub
		DataAuditDto dto=auditMapper.detail(auditDto.getOrderNo());
		if(dto==null){
			return auditMapper.insert(auditDto);
		}else{
			return auditMapper.update(auditDto);
		}
	}

	@Override
	public DataAuditDto detail(String orderNo) {
		// TODO Auto-generated method stub
		return auditMapper.detail(orderNo);
	}

}
