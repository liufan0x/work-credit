package com.anjbo.service;

import java.util.List;
import java.util.Map;
import com.anjbo.bean.config.PageConfigDto;
import com.anjbo.bean.config.PageConfigSelectValuesDto;
import com.anjbo.bean.config.PageListConfigDto;
import com.anjbo.bean.config.PageTabConfigDto;
import com.anjbo.bean.config.PageTabRegionConfigDto;
import com.anjbo.bean.config.PageTabRegionFormConfigDto;
import com.anjbo.bean.data.PageBusinfoTypeDto;
import com.anjbo.bean.data.PageDataDto;


/**
 * 页面配置 
 * @author lic
 * @date 2017-8-18
 */
public interface PageConfigService {

	PageListConfigDto selectPageListConfig(PageListConfigDto listConfigDto);

	List<PageConfigDto> selectPageConfigDtoList();

	List<PageConfigSelectValuesDto> selectPageConfigSelectValuesDtoList();
	
	List<PageTabConfigDto> selectPageTabConfigDtoList();
	
	List<PageTabRegionConfigDto> selectPageTabRegionConfigDtoList();

	List<PageTabRegionFormConfigDto> selectPageTabRegionFormConfigDtoList();
	
	/**
	 * 初始化页面配置
	 * 从数据库中拿取数据
	 * 并封装到redis中
	 * key：pageClass
	 * value：PageConfigDto
	 */
	void initPageConfig();

	/**
	 * 初始化页面配置的下来框数据
	 * 如果是固定数据，则保存字典表中数据
	 * 如果是非固定数据，则保存需要请求接口的url。
	 * 并封装到redis中
	 * 固定数据存key：list
	 * 非固定数据存key：url
	 */
	void initSelectConfig();
	
	/**
	 * 获取pageClass页面配置
	 * @param params
	 * @return
	 */
	PageConfigDto pageConfig(Map<String, Object> params);
	
	/**
	 * 获取区域表单
	 * 供循环区域新增时用 
	 * pageClass 页面归类
	 * regionClass 区域归类
	 * @param params
	 * @return
	 */
	List<PageTabRegionFormConfigDto> getPageTabRegionConfigDto(Map<String, Object> params);
	
	List<PageBusinfoTypeDto> selectPageBusinfoConfig(PageBusinfoTypeDto pageBusinfoTypeDto);
	
	Map<String, Object> setOtherData(PageTabConfigDto pageTabConfigDto, Map<String, Object> params);
	
}
