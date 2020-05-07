package com.anjbo.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.anjbo.bean.BaseCompleteDto;
import com.anjbo.dao.OrderCompleteMapper;
import com.anjbo.service.OrderCompleteService;

@Service
public class OrderCompleteServiceImpl implements OrderCompleteService{
	
	@Resource
	private OrderCompleteMapper orderCompleteMapper;
	
	@Override
	public List<BaseCompleteDto> selectBaseOrderCompleteList(String pageType) {
		return orderCompleteMapper.selectBaseOrderCompleteList(pageType);
	}
	
}
