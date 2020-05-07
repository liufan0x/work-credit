package com.anjbo.service;

import java.util.List;
import com.anjbo.bean.order.OrderFlowDto;
import com.anjbo.bean.report.StatisticsDto;

public interface OrderFlowService {

	List<OrderFlowDto> selectOrderFlowList(String orderNo);

	int addOrderFlow(OrderFlowDto orderFlowDto);
	
	int deleteOrderFlow(OrderFlowDto orderFlowDto);
	
	String selectOrderNoByUid(String uid);

	OrderFlowDto selectEndOrderFlow(OrderFlowDto orderFlowDto);

	void withdrawOrder(String orderNo);
	
	List<StatisticsDto> selectOrderFlowAll(OrderFlowDto orderFlowDto);
	int selectOrderFlowCount(OrderFlowDto orderFlowDto);
}
