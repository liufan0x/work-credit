/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.dao.risk;

import java.util.List;

import com.anjbo.bean.risk.AuditFirstDto;
import com.anjbo.bean.risk.AuditFirstForeclosureDto;
import com.anjbo.bean.risk.AuditFirstPaymentDto;
import com.anjbo.dao.BaseMapper;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 19:44:32
 * @version 1.0
 */
public interface AuditFirstMapper extends BaseMapper<AuditFirstDto>{
	AuditFirstDto detail(String orderNo);
	public int delFirstForeclosure(String orderNo);
	public int addFirstForeclosure(AuditFirstForeclosureDto auditFirstForeclosureDto);
	public int delFirstPayment(String orderNo);
	public int addFirstPayment(AuditFirstPaymentDto auditFirstPaymentDto);
	/**
	 * 查询初审出款
	 * @param orderNo
	 * @return
	 */
	public List<AuditFirstForeclosureDto> findforeclosureList(String orderNo);
	/**
	 * 查询初审回款
	 * @param orderNo
	 * @return
	 */
	public List<AuditFirstPaymentDto> findPaymentList(String orderNo);
	
}
