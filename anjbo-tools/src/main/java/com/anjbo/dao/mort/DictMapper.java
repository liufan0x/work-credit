package com.anjbo.dao.mort;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;



public interface DictMapper {

	List<Map<String,Object>> selectDict(@Param("type") String type);
	
	int updateDict(Map<String,Object> param);
	
	int addDict(Map<String,Object> param);
}
