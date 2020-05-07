package com.anjbo.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anjbo.bean.config.list.ListConfigDto;
import com.anjbo.bean.config.list.PageListColumnsConfigDto;
import com.anjbo.dao.PageListColumnsConfigMapper;
import com.anjbo.dao.PageListConfigMapper;
import com.anjbo.service.PageListConfigService;
@Service
public class PageListConfigServiceImpl implements PageListConfigService {

	@Resource
	private PageListConfigMapper pageListConfigMapper;
	@Resource
	private PageListColumnsConfigMapper pageListColumnsConfigMapper;
	
	@Override
	public ListConfigDto selectPageListConfig(ListConfigDto listConfigDto) {
		listConfigDto = pageListConfigMapper.selectPageListConfig(listConfigDto);
		PageListColumnsConfigDto pageListColumnsConfigDto = new PageListColumnsConfigDto();
		pageListColumnsConfigDto.setListName(listConfigDto.getName());
		Map<String,Object> map = new HashMap<String,Object>();
		List<PageListColumnsConfigDto> pageListColumnsConfigDtos = pageListColumnsConfigMapper.selectPageListColumnsConfig(pageListColumnsConfigDto);
		for (PageListColumnsConfigDto column : pageListColumnsConfigDtos) {
			map.put(column.getField(), column.isVisible());
		}
		listConfigDto.setColumnSwitch(map);
		listConfigDto.setPageListColumnsConfigDtos(pageListColumnsConfigDtos);
		return listConfigDto;
	}

}
