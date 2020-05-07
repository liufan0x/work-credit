package com.anjbo.service;

import java.util.List;
import java.util.Map;

import com.anjbo.bean.data.PageListDto;

/**
 * 列表数据
 * @author lic
 */
public interface PageListService extends BaseService<PageListDto>{

	Map<String, Object> pageListData(Map<String, Object> params);
	
	List<Map<String, Object>> pageList(Map<String, Object> params);

	int pageCount(Map<String, Object> params);

	void insertPageList(Map<String, Object> params);

	void withdraw(PageListDto pageListDto);
	
}