package com.anjbo.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.anjbo.bean.BaseItemDto;
import com.anjbo.dao.OrderModelMapper;
import com.anjbo.service.OrderModelService;

@Service
public class OrderModelServiceImpl implements OrderModelService{
	
	@Resource
	private OrderModelMapper orderModelMapper;
	
	@Override
	public List<BaseItemDto> selectBaseItemList(String pageType) {
		return orderModelMapper.selectBaseItemList(pageType);
	}
	
}
