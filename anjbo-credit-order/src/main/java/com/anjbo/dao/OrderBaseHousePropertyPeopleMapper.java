package com.anjbo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.anjbo.bean.order.OrderBaseHousePropertyPeopleDto;

public interface OrderBaseHousePropertyPeopleMapper {
	
	int saveOrderPropertyPeople(OrderBaseHousePropertyPeopleDto orderBaseHousePropertyPeopleDto);
	
	int updateOrderPropertyPeople(OrderBaseHousePropertyPeopleDto orderBaseHousePropertyPeopleDto);
	
	int updateOrderPropertyPeopleNull(OrderBaseHousePropertyPeopleDto orderBaseHousePropertyPeopleDto);
	
	List<OrderBaseHousePropertyPeopleDto> selectOrderPropertyPeopleByOrderNo(@Param("orderNo") String orderNo);
	
	OrderBaseHousePropertyPeopleDto selectOrderPropertyPeopleById(@Param("id") int id);
	
	int deleteProHouseById(int id);
	
	int deleteProHouseByOrderNo(String orderNo);
}
