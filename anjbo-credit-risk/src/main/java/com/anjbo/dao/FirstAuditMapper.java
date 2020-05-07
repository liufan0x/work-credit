package com.anjbo.dao;

import java.util.List;
import java.util.Map;

import com.anjbo.bean.report.StatisticsDto;
import com.anjbo.bean.risk.FirstAuditDto;
import com.anjbo.bean.risk.FirstForeclosureAuditDto;
import com.anjbo.bean.risk.FirstPaymentAuditDto;


public interface FirstAuditMapper {
    
	FirstAuditDto detail(String orderNo);
	
	int update(FirstAuditDto obj);
	
	int insert(FirstAuditDto obj);
	
	List<StatisticsDto> listByTime(Map<String,Object> map);
	
	int listByTimeCount(Map<String,Object> map);
	
	
	/**
	 * 查询初审出款
	 * @param orderNo
	 * @return
	 */
	public List<FirstForeclosureAuditDto> findforeclosureList(String orderNo);
	public int addFirstForeclosure(FirstForeclosureAuditDto foreclosureAuditDto);
	public int delFirstForeclosure(String orderNo);
	/**
	 * 查询初审回款
	 * @param orderNo
	 * @return
	 */
	public List<FirstPaymentAuditDto> findPaymentList(String orderNo);
	public int addFirstPayment(FirstPaymentAuditDto firstPaymentAuditDto);
	public int delFirstPayment(String orderNo);
	
}