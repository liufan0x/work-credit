package com.anjbo.service.ordinary.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.aspectj.weaver.AjAttribute.PrivilegedAttribute;
import org.springframework.stereotype.Service;

import com.anjbo.bean.ordinary.AuditFundDockingOrdinaryAuditDto;
import com.anjbo.dao.ordinary.AuditFundDockingOrdinaryAuditMapper;
import com.anjbo.service.ordinary.AuditFundDockingOrdinaryAuditService;

@Service
public class AuditFundDockingOrdinaryAuditServiceImpl implements AuditFundDockingOrdinaryAuditService{
    
	@Resource  private AuditFundDockingOrdinaryAuditMapper auditFundDockingOrdinaryAuditMapper;
	
	@Override
	public AuditFundDockingOrdinaryAuditDto findByOrdinaryAudit(Map<String, Object> parament) {
		return auditFundDockingOrdinaryAuditMapper.findByOrdinaryAudit(parament);
	}

	@Override
	public int insert(Map<String, Object> parament) {
		String  orderNo =MapUtils.getString(parament,"orderNo");
		if(!"null".equals(orderNo)&&!"".equals(orderNo)) {
			auditFundDockingOrdinaryAuditMapper.delete(parament);
		}
		return auditFundDockingOrdinaryAuditMapper.insert(parament);
	}

	@Override
	public int delete(Map<String, Object> parament) {
		// TODO Auto-generated method stub
		return auditFundDockingOrdinaryAuditMapper.delete(parament);
	}

}
