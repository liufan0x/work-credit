package com.anjbo.service;

import java.util.List;
import com.anjbo.bean.BaseCompleteDto;

public interface OrderCompleteService {

	List<BaseCompleteDto> selectBaseOrderCompleteList(String pageType);
	
}
