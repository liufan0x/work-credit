package com.anjbo.dao;

import java.util.List;
import java.util.Map;

/**
 * 示例Dao
 * @author limh limh@zxsf360.com
 * @date 2016-6-1 下午03:17:26
 */
public interface RiskBaseMapper {

	List<Map<String,Object>> listImg(Map<String,Object> map);
	
	int deleteImgById(Map<String,Object> map);
	
	int insertImg(List<Map<String,Object>> list);

	List<Map<String,Object>> listImgById(Map<String,Object> map);
}