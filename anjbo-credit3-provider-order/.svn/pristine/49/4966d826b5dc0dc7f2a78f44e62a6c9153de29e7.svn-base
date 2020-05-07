/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service.impl.risk;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anjbo.bean.risk.AuditFirstDto;
import com.anjbo.bean.risk.AuditFirstForeclosureDto;
import com.anjbo.bean.risk.AuditFirstPaymentDto;
import com.anjbo.dao.risk.AuditFirstMapper;
import com.anjbo.service.risk.AuditFirstService;
import com.anjbo.service.impl.BaseServiceImpl;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 19:44:32
 * @version 1.0
 */
@Service
public class AuditFirstServiceImpl extends BaseServiceImpl<AuditFirstDto>  implements AuditFirstService {
	@Autowired private AuditFirstMapper auditFirstMapper;

	@Override
	public AuditFirstDto detail(String orderNo) {
		// TODO Auto-generated method stub
		AuditFirstDto obj=auditFirstMapper.detail(orderNo);
		return null==obj?new AuditFirstDto():obj;
	}

	@Override
	public int delFirstForeclosure(String orderNo) {
		return auditFirstMapper.delFirstForeclosure(orderNo);
	}

	@Override
	public int addFirstForeclosure(AuditFirstForeclosureDto auditFirstForeclosureDto) {
		return auditFirstMapper.addFirstForeclosure(auditFirstForeclosureDto);
	}

	@Override
	public int addFirstPayment(AuditFirstPaymentDto auditFirstPaymentDto) {
		return auditFirstMapper.addFirstPayment(auditFirstPaymentDto);
	}

	@Override
	public int delFirstPayment(String orderNo) {
		return auditFirstMapper.delFirstPayment(orderNo);
	}

	@Override
	public List<AuditFirstForeclosureDto> findforeclosureList(String orderNo) {
		// TODO Auto-generated method stub
		return auditFirstMapper.findforeclosureList(orderNo);
	}

	@Override
	public List<AuditFirstPaymentDto> findPaymentList(String orderNo) {
		// TODO Auto-generated method stub
		return auditFirstMapper.findPaymentList(orderNo);
	}
	
}
