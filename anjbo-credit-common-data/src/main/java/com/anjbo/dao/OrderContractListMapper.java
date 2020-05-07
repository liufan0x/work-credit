package com.anjbo.dao;

import java.util.List;
import java.util.Map;

import com.anjbo.bean.order.OrderListDto;

public interface OrderContractListMapper {
	
	/**
	 * 录入合同列表
	 * @param orderContractListDto
	 * @return
	 */
	int insertOrderContractList(OrderListDto orderListDto);
	
	/**
	 * 查询合同列表
	 * @param orderListDto
	 * @return
	 */
	List<OrderListDto> selectContractList(OrderListDto orderListDto);
	
	/**
	 * 查询合同总数
	 * @param orderListDto
	 * @return
	 */
	int selectContractCount(OrderListDto orderListDto);
	
	/**
	 * 查询新建合同可以关联的订单
	 * @param map
	 * @return
	 */
	List<Map<String,Object>> selectContractAbleRelationOrder(Map<String,Object> map);
}
