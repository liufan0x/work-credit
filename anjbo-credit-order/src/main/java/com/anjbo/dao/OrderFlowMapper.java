package com.anjbo.dao;

import java.util.List;
import java.util.Map;

import com.anjbo.bean.order.OrderFlowDto;
import com.anjbo.bean.report.StatisticsDto;

/**
 * 订单流水
 * @author lic
 * @date 2017-6-9
 */
public interface OrderFlowMapper {

	List<OrderFlowDto> selectOrderFlowList(String orderNo);
	
	int addOrderFlow(OrderFlowDto orderFlowDto);
	
	int deleteOrderFlow(OrderFlowDto orderFlowDto);

	List<String> selectOrderNoByUid(String uid);

	OrderFlowDto selectEndOrderFlow(OrderFlowDto orderFlowDto);

	void withdrawOrder(String orderNo);
	
	List<StatisticsDto> selectOrderFlowAll(OrderFlowDto orderFlowDto);
	int selectOrderFlowCount(OrderFlowDto orderFlowDto);
	public String selectHandTime(OrderFlowDto orderFlowDto);
	
	int copyFlows(Map<String,Object> map);
	
}