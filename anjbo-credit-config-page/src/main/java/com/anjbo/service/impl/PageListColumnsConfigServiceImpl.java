package com.anjbo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anjbo.bean.config.list.PageListColumnsConfigDto;
import com.anjbo.dao.PageListColumnsConfigMapper;
import com.anjbo.service.PageListColumnsConfigService;
@Service
public class PageListColumnsConfigServiceImpl implements
		PageListColumnsConfigService {
	
	@Resource
	private PageListColumnsConfigMapper pageListColumnsConfigMapper;
	
	@Override
	public List<PageListColumnsConfigDto> selectPageListColumnsConfig(
			PageListColumnsConfigDto pageListColumnsConfigDto) {
		return pageListColumnsConfigMapper.selectPageListColumnsConfig(pageListColumnsConfigDto);
	}

}
