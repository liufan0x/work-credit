package com.anjbo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anjbo.bean.order.OrderBaseHouseDto;
import com.anjbo.bean.order.OrderBaseHousePropertyDto;
import com.anjbo.bean.order.OrderBaseHousePropertyPeopleDto;
import com.anjbo.bean.order.OrderBaseHousePurchaserDto;
import com.anjbo.dao.OrderBaseBorrowMapper;
import com.anjbo.dao.OrderBaseHouseMapper;
import com.anjbo.dao.OrderBaseHousePropertyMapper;
import com.anjbo.dao.OrderBaseHousePropertyPeopleMapper;
import com.anjbo.dao.OrderBaseHousePurchaserMapper;
import com.anjbo.service.OrderBaseHouseService;
@Service
public class OrderBaseHouseServiceImpl implements OrderBaseHouseService {

	@Resource
	private OrderBaseHouseMapper orderBaseHouseMapper;
	@Resource
	private OrderBaseHousePurchaserMapper orderBaseHousePurchaserMapper;
	@Resource
	private OrderBaseHousePropertyMapper orderBaseHousePropertyMapper;
	@Resource
	private OrderBaseHousePropertyPeopleMapper orderBaseHousePropertyPeopleMapper;
	@Resource
	private OrderBaseBorrowMapper orderBaseBorrowMapper;

	@Override
	public int saveOrderHouse(OrderBaseHouseDto orderBaseHouseDto) {
		return orderBaseHouseMapper.saveOrderHouse(orderBaseHouseDto);
	}

	@Override
	public int updateOrderHouse(OrderBaseHouseDto orderBaseHouseDto) {
		// 更新房产信息
		List<OrderBaseHousePropertyDto> orderBaseHousePropertyList = orderBaseHouseDto.getOrderBaseHousePropertyDto();
		List<OrderBaseHousePurchaserDto> orderBaseHousePurchaserList = orderBaseHouseDto.getOrderBaseHousePurchaserDto();
		List<OrderBaseHousePropertyPeopleDto> orderBaseHousePropertyPeopleList = orderBaseHouseDto.getOrderBaseHousePropertyPeopleDto();
		orderBaseHousePropertyMapper.deleteHousePropertyByOrderNo(orderBaseHouseDto.getOrderNo());
		if(orderBaseHousePropertyList.size()>0){
			for (int j = 0; j < orderBaseHousePropertyList.size(); j++) {
				orderBaseHousePropertyList.get(j).setOrderNo(orderBaseHouseDto.getOrderNo());
				orderBaseHousePropertyList.get(j).setCreateUid(orderBaseHouseDto.getCreateUid());
				orderBaseHousePropertyMapper.saveOrderHouseProperty(orderBaseHousePropertyList.get(j));
			}
		}
		// 更新买房人信息
		orderBaseHousePurchaserMapper.deleteHousePurchaserByOrderNo(orderBaseHouseDto.getOrderNo());
		if(orderBaseHousePurchaserList.size()>0){
			for (int j = 0; j < orderBaseHousePurchaserList.size(); j++) {
				orderBaseHousePurchaserList.get(j).setOrderNo(orderBaseHouseDto.getOrderNo());
				orderBaseHousePurchaserList.get(j).setCreateUid(orderBaseHouseDto.getCreateUid());
				orderBaseHousePurchaserMapper.saveOrderBaseHousePurchaser(orderBaseHousePurchaserList.get(j));
			}
		}
		// 更新产权人信息
		orderBaseHousePropertyPeopleMapper.deleteProHouseByOrderNo(orderBaseHouseDto.getOrderNo());
		if(orderBaseHousePropertyPeopleList.size()>0){
			for (int j = 0; j < orderBaseHousePropertyPeopleList.size(); j++) {
				orderBaseHousePropertyPeopleList.get(j).setOrderNo(orderBaseHouseDto.getOrderNo());
				orderBaseHousePropertyPeopleList.get(j).setCreateUid(orderBaseHouseDto.getCreateUid());
				orderBaseHousePropertyPeopleMapper.saveOrderPropertyPeople(orderBaseHousePropertyPeopleList.get(j));
			}
		}
		// 更新房产交易信息
		orderBaseHouseMapper.updateOrderHouseNull(orderBaseHouseDto);
		return 1;
	}

	@Override
	public int updateOrderHouseApp(OrderBaseHouseDto orderBaseHouseDto) {
		return orderBaseHouseMapper.updateOrderHouseNull(orderBaseHouseDto);
	}
	
	@Override
	public OrderBaseHouseDto selectOrderHouseByOrderNo(String orderNo) {
		OrderBaseHouseDto orderBaseHouseDto = orderBaseHouseMapper.selectOrderHouseByOrderNo(orderNo);
		//房产信息
	    List<OrderBaseHousePropertyDto> orderBaseHousePropertyDto = orderBaseHousePropertyMapper.selectOrderHousePropertyByOrderNo(orderNo);
	    //买房人信息
	    List<OrderBaseHousePurchaserDto> orderBaseHousePurchaserDto = orderBaseHousePurchaserMapper.selectOrderHousePurchaserByOrderNo(orderNo);
	    //产权人信息
	    List<OrderBaseHousePropertyPeopleDto> orderBaseHousePropertyPeopleDto = orderBaseHousePropertyPeopleMapper.selectOrderPropertyPeopleByOrderNo(orderNo);
	    if(orderBaseHouseDto!=null){
	    	orderBaseHouseDto.setOrderBaseHousePropertyDto(orderBaseHousePropertyDto);
	    	orderBaseHouseDto.setOrderBaseHousePurchaserDto(orderBaseHousePurchaserDto);
	    	orderBaseHouseDto.setOrderBaseHousePropertyPeopleDto(orderBaseHousePropertyPeopleDto);
	    }
	    return orderBaseHouseDto;
	}

	@Override
	public int appUpdateHousePurchaser(OrderBaseHouseDto orderBaseHouseDto) {
		// 更新买房人信息
		List<OrderBaseHousePurchaserDto> orderBaseHousePurchaserList = orderBaseHouseDto.getOrderBaseHousePurchaserDto();
		orderBaseHousePurchaserMapper.deleteHousePurchaserByOrderNo(orderBaseHouseDto.getOrderNo());
		if(orderBaseHousePurchaserList.size()>0){
			for (int j = 0; j < orderBaseHousePurchaserList.size(); j++) {
				orderBaseHousePurchaserList.get(j).setOrderNo(orderBaseHouseDto.getOrderNo());
				orderBaseHousePurchaserList.get(j).setCreateUid(orderBaseHouseDto.getCreateUid());
				orderBaseHousePurchaserMapper.saveOrderBaseHousePurchaser(orderBaseHousePurchaserList.get(j));
			}
		}
		return 1;
	}

	@Override
	public int appUpdateHouseProperty(OrderBaseHouseDto orderBaseHouseDto) {
		// 更新房产信息
		List<OrderBaseHousePropertyDto> orderBaseHousePropertyList = orderBaseHouseDto.getOrderBaseHousePropertyDto();
		orderBaseHousePropertyMapper.deleteHousePropertyByOrderNo(orderBaseHouseDto.getOrderNo());
		if(orderBaseHousePropertyList.size()>0){
			for (int j = 0; j < orderBaseHousePropertyList.size(); j++) {
				orderBaseHousePropertyList.get(j).setOrderNo(orderBaseHouseDto.getOrderNo());
				orderBaseHousePropertyList.get(j).setCreateUid(orderBaseHouseDto.getCreateUid());
				orderBaseHousePropertyMapper.saveOrderHouseProperty(orderBaseHousePropertyList.get(j));
			}
		}
		return 1;
	}

	@Override
	public int appUpdateHousePropertyPeople(OrderBaseHouseDto orderBaseHouseDto) {
		List<OrderBaseHousePropertyPeopleDto> orderBaseHousePropertyPeopleList = orderBaseHouseDto.getOrderBaseHousePropertyPeopleDto();
		// 更新产权人信息
		orderBaseHousePropertyPeopleMapper.deleteProHouseByOrderNo(orderBaseHouseDto.getOrderNo());
		if(orderBaseHousePropertyPeopleList.size()>0){
			for (int j = 0; j < orderBaseHousePropertyPeopleList.size(); j++) {
				orderBaseHousePropertyPeopleList.get(j).setOrderNo(orderBaseHouseDto.getOrderNo());
				orderBaseHousePropertyPeopleList.get(j).setCreateUid(orderBaseHouseDto.getCreateUid());
				orderBaseHousePropertyPeopleMapper.saveOrderPropertyPeople(orderBaseHousePropertyPeopleList.get(j));
			}
		}
		return 1;
	}
}
