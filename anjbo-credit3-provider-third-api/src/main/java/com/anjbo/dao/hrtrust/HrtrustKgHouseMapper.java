package com.anjbo.dao.hrtrust;

import org.apache.ibatis.annotations.Param;

import com.anjbo.bean.yntrust.YntrustBorrowDto;

import java.util.Map;

public interface HrtrustKgHouseMapper {
	int saveKgHouse(Map<String, Object> map);
	Map<String,Object> getKgHouse(@Param("orderNo") String orderNo);
	 Integer updateMap(Map<String, Object> map);
}
