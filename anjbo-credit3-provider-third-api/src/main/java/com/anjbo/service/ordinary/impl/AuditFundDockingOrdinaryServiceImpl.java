package com.anjbo.service.ordinary.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;

import com.anjbo.bean.order.BaseBorrowDto;
import com.anjbo.bean.ordinary.AuditFundDockingOrdinaryDto;
import com.anjbo.dao.ordinary.AuditFundDockingOrdinaryMapper;
import com.anjbo.service.ordinary.AuditFundDockingOrdinaryService;

@Service
public class AuditFundDockingOrdinaryServiceImpl implements AuditFundDockingOrdinaryService{
    
	@Resource private AuditFundDockingOrdinaryMapper auditFundDockingOrdinaryMapper;
	
	@Override
	public int insert(Map<String, Object> parament) {
		String  orderNo = MapUtils.getString(parament,"orderNo");
		if(!"null".equals(orderNo)&&!"".equals(orderNo)) {
			auditFundDockingOrdinaryMapper.delete(parament);
		}
		return auditFundDockingOrdinaryMapper.insert(parament);
	}

	@Override
	public int delete(Map<String, Object> parament) {
		// TODO Auto-generated method stub
		return auditFundDockingOrdinaryMapper.delete(parament);
	}

	@Override
	public AuditFundDockingOrdinaryDto findByOrdinary(Map<String, Object> parament) {
		return auditFundDockingOrdinaryMapper.findByOrdinary(parament);
	}

}
