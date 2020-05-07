package com.anjbo.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.anjbo.bean.order.OrderFlowDto;
import com.anjbo.bean.report.StatisticsDto;
import com.anjbo.dao.OrderFlowMapper;
import com.anjbo.service.OrderFlowService;

@Service
public class OrderFlowServiceImpl implements OrderFlowService {
	
	@Resource
	private OrderFlowMapper orderFlowMapper;
	
	@Override
	public List<OrderFlowDto> selectOrderFlowList(String orderNo) {
		return orderFlowMapper.selectOrderFlowList(orderNo);
	}
	
	@Override
	public OrderFlowDto selectEndOrderFlow(OrderFlowDto orderFlowDto) {
		return orderFlowMapper.selectEndOrderFlow(orderFlowDto);
	}
	
	@Override
	public int addOrderFlow(OrderFlowDto orderFlowDto) {
		return orderFlowMapper.addOrderFlow(orderFlowDto);
	}
	
	@Override
	public int deleteOrderFlow(OrderFlowDto orderFlowDto) {
		return orderFlowMapper.deleteOrderFlow(orderFlowDto);
	}
	
	@Override
	public String selectOrderNoByUid(String uid) {
		List<String> orderNoList = orderFlowMapper.selectOrderNoByUid(uid);
		String orderNos = "";
		for (String orderNo : orderNoList) {
			if(orderNo.contains("k")){
				orderNos += "'"+orderNo + "',";
			}else{
				orderNos += orderNo + ",";
			}
		}
		if(StringUtils.isNotEmpty(orderNos)){
			orderNos = orderNos.substring(0, orderNos.length()-1);
		}
		return orderNos;
	}
	
	@Override
	public void withdrawOrder(String orderNo) {
		orderFlowMapper.withdrawOrder(orderNo);
	}

	@Override
	public List<StatisticsDto> selectOrderFlowAll(OrderFlowDto orderFlowDto) {
		List<StatisticsDto> list= orderFlowMapper.selectOrderFlowAll(orderFlowDto);
		if(list!=null && list.size()>0){
			for(StatisticsDto dto:list){
				OrderFlowDto flowDto=new OrderFlowDto();
				flowDto.setOrderNo(dto.getOrderNo());
				flowDto.setHandleTime(dto.getBackTime());
				dto.setBeginHandleTimeStr(orderFlowMapper.selectHandTime(flowDto));
			}
		}
		return list;
	}

	@Override
	public int selectOrderFlowCount(OrderFlowDto orderFlowDto) {
		return orderFlowMapper.selectOrderFlowCount(orderFlowDto);
	}
	
}
