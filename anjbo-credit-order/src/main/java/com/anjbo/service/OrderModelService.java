package com.anjbo.service;

import java.util.List;
import com.anjbo.bean.BaseItemDto;

public interface OrderModelService {

	List<BaseItemDto> selectBaseItemList(String pageType);
	
}
