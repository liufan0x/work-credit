package com.anjbo.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anjbo.bean.order.OrderListDto;
import com.anjbo.dao.OrderBaseThirdMapper;
import com.anjbo.service.OrderBaseThirdService;
@Service
public class OrderBaseThirdServiceImpl implements OrderBaseThirdService{
	
	@Resource
	private OrderBaseThirdMapper OrderBaseThirdMapper;
	
	@Override
	public List<OrderListDto> selectOrderList(Map<String,Object> map){
		return OrderBaseThirdMapper.selectOrderList(map);
	}

	@Override
	public int selectOrderCount(Map<String,Object> map){
		return OrderBaseThirdMapper.selectOrderCount(map);
	}

}
