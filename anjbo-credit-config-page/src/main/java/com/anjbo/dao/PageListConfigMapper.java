package com.anjbo.dao;

import java.util.List;

import com.anjbo.bean.config.list.ListConfigDto;
import com.anjbo.bean.config.page.PageConfigDto;
import com.anjbo.bean.config.page.PageConfigSelectValuesDto;
import com.anjbo.bean.config.page.PageTabConfigDto;
import com.anjbo.bean.config.page.PageTabRegionConfigDto;
import com.anjbo.bean.config.page.PageTabRegionFormConfigDto;

/**
 * 页面配置 
 * @author lic
 * @date 2017-8-18
 */
public interface PageListConfigMapper {

	/**
	 * 查询列表配置
	 * @param listConfigDto
	 * @return
	 */
	ListConfigDto selectPageListConfig(ListConfigDto listConfigDto);

}