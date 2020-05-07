package com.anjbo.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anjbo.bean.order.OrderListDto;
import com.anjbo.dao.OrderContractListMapper;
import com.anjbo.service.OrderContractListService;
@Service
public class OrderContractListServiceImpl implements OrderContractListService {

	@Resource
	private OrderContractListMapper orderContractListMapper; 
	@Override
	public int insertOrderContractList(OrderListDto orderListDto) {
		return orderContractListMapper.insertOrderContractList(orderListDto);
	}
	
	@Override
	public List<OrderListDto> selectContractList(OrderListDto orderListDto) {
		return orderContractListMapper.selectContractList(orderListDto);
	}
	
	@Override
	public int selectContractCount(OrderListDto orderListDto) {
		return orderContractListMapper.selectContractCount(orderListDto);
	}

	@Override
	public List<Map<String, Object>> selectContractAbleRelationOrder(
			Map<String, Object> map) {
		return orderContractListMapper.selectContractAbleRelationOrder(map);
	}

}
