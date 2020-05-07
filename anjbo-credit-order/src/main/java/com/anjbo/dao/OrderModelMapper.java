package com.anjbo.dao;

import java.util.List;

import com.anjbo.bean.BaseItemDto;

public interface OrderModelMapper {

	List<BaseItemDto> selectBaseItemList(String pageType);
	
}