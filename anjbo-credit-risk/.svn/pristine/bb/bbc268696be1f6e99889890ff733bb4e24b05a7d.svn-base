package com.anjbo.service;

import java.util.List;
import java.util.Map;

import com.anjbo.bean.report.StatisticsDto;
import com.anjbo.bean.risk.CreditDto;
import com.anjbo.bean.risk.FirstAuditDto;
import com.anjbo.bean.risk.FirstForeclosureAuditDto;
import com.anjbo.bean.risk.FirstPaymentAuditDto;

public interface FirstAuditService {

	FirstAuditDto detail(String orderNo);
	
	int update(FirstAuditDto obj);
	
	int insert(FirstAuditDto obj);
	
	Map<String, Boolean> getCreditFlagMap(String orderNo);
	/**
	 * 查询初审审批时间区间内的审批信息
	 * @param map key:{startTime=开始时间,endTime=结束时间,pageStart=分页开始,pageSize=分页结束}
	 * @return
	 */
	List<StatisticsDto> listByTime(Map<String,Object> map);
	/**
	 * 查询初审审批时间区间内的审批信息数量
	 * @param map key:{startTime=开始时间,endTime=结束时间,pageStart=分页开始,pageSize=分页结束}
	 * @return
	 */
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
