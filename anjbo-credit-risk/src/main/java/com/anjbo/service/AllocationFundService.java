package com.anjbo.service;

import com.anjbo.bean.risk.AllocationFundDto;
import com.anjbo.bean.risk.HuaanDto;

import java.util.List;
import java.util.Map;

public interface AllocationFundService {

	List<AllocationFundDto> listDetail(String orderNo);
	
	AllocationFundDto detail(int id);
	
	int update(AllocationFundDto obj);
	
	int insert(AllocationFundDto obj);
	
	int insert(List<AllocationFundDto> list);
	
	HuaanDto loadPushOrder(HuaanDto obj);
	
	Map<String,Object> listFundByOrderNos(Map<String,Object> map);

	/**
	 * 经理审批订单总计
	 * 已确认放款总计
	 * @return map key{auditCount=经理审批订单总计,lendingTotal=已确认放款总计}
	 */
	Map<String,Object> selectLendingTotalAndAuditCount();

	int updateByOrderNo(AllocationFundDto obj);
}
