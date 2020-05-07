package com.anjbo.dao;

import java.util.List;

import com.anjbo.bean.config.list.PageListColumnsConfigDto;

/**
 * 页面配置 
 * @author lic
 * @date 2017-8-18
 */
public interface PageListColumnsConfigMapper {

	List<PageListColumnsConfigDto> selectPageListColumnsConfig(PageListColumnsConfigDto pageListColumnsConfigDto);

}