package com.anjbo.dao;

import java.util.List;

import com.anjbo.bean.config.PageConfigDto;
import com.anjbo.bean.config.PageConfigSelectValuesDto;
import com.anjbo.bean.config.PageListColumnsConfigDto;
import com.anjbo.bean.config.PageListConfigDto;
import com.anjbo.bean.config.PageTabConfigDto;
import com.anjbo.bean.config.PageTabRegionConfigDto;
import com.anjbo.bean.config.PageTabRegionFormConfigDto;
import com.anjbo.bean.data.PageBusinfoTypeDto;

/**
 * 页面配置 
 * @author lic
 * @date 2017-8-18
 */
public interface PageConfigMapper {

	List<PageBusinfoTypeDto> selectPageBusinfoConfig(PageBusinfoTypeDto pageBusinfoTypeDto);
	
	PageListConfigDto selectPageListConfig(PageListConfigDto listConfigDto);
	
	List<PageListColumnsConfigDto> selectPageListColumnsConfig(PageListColumnsConfigDto pageListColumnsConfigDto);
	
	List<PageConfigDto> selectPageConfigDtoList();

	List<PageConfigSelectValuesDto> selectPageConfigSelectValuesDtoList();
	
	List<PageTabConfigDto> selectPageTabConfigDtoList();
	
	List<PageTabRegionConfigDto> selectPageTabRegionConfigDtoList();

	List<PageTabRegionFormConfigDto> selectPageTabRegionFormConfigDtoList();


}