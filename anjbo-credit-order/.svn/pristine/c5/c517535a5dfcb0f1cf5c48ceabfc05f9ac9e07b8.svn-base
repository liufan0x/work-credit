package com.anjbo.service;

import java.util.List;
import java.util.Map;

import com.anjbo.bean.element.DocumentsReturnDto;
import com.anjbo.bean.finance.LendingDto;
import com.anjbo.bean.finance.RebateDto;
import com.anjbo.bean.finance.ReceivableForDto;
import com.anjbo.bean.finance.ReceivablePayDto;
import com.anjbo.bean.order.OrderFlowDto;

public interface huanTempService {

	List<Map<String, Object>> findByAll();
	List<Map<String, Object>> findByAllHuarong();
	LendingDto findByLending(String orderNo);
	int updOrderList(Map<String, Object> map);
	int updFinanceLending(Map<String, Object> map);
	int updFinanceReceivable(Map<String, Object> map);
	//要件退还
	DocumentsReturnDto finyByReturn(String orderNo);
	int updateReturn(Map<String, Object> map);
	//付费
	ReceivablePayDto findByPay(String orderNo);
	int updatePay(Map<String, Object> map);
	//返佣
	RebateDto findByRebate(String orderNo);
	int updateRebate(Map<String, Object> map);
	//修改流水
	OrderFlowDto findByFlow(Map<String, Object> map);
	int updateOrderFlow(Map<String, Object> map);
	
	List<String> selectOrderNoAll();
	List<String> selectOrderNoAll2();
	List<OrderFlowDto> selectOrderFlow(String orderNo);
	List<OrderFlowDto> selectOrderFlow2(String orderNo);
	int updateFlow(Map<String, Object> map);
	int updateFlow2(Map<String, Object> map);
	List<String> selectOrderNoAlls(String city);
	String numTimeCount(Map<String, Object> map);
	int findByBack(String cityName);
	List<Map<String, Object>> fingByBackList(String cityName);
	
	ReceivableForDto findByOrderNo(String orderNo);
	int addFlow(OrderFlowDto flowDto);
	
	
}
