package com.anjbo.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import com.anjbo.bean.data.PageListDto;

/**
 * 列表数据
 * @author lic
 */
public interface PageListMapper extends BaseMapper<PageListDto>{

	List<Map<String, Object>> pageListList(Map<String, Object> params);

	int pageListCount(Map<String, Object> params);

	Map<String, Object> pageListData(Map<String, Object> params);

	List<String> selectTableColumns(@Param("tblDataName") String tblDataName);
	
	void insertListByKey(Map<String, Object> params);
	
	void updateListByKey(Map<String, Object> params);
	
	void updatePageList(Map<String, Object> params);

	void insertPageList(Map<String, Object> tempMap);
	
}