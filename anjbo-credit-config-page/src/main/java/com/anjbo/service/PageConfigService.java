package com.anjbo.service;

import com.anjbo.bean.config.page.*;

import java.util.List;
import java.util.Map;

/**
 * 页面配置 
 * @author lic
 * @date 2017-8-18
 */
public interface PageConfigService {

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
	
	/**
	 * 校验tab标签数据的数据
	 * @param params
	 * @return
	 */
	boolean checkPageTabConfigDto(PageTabConfigDto pageTabConfigDto,Map<String, Object> params);
}
