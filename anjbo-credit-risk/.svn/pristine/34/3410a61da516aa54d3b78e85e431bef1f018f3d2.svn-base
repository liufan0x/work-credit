package com.anjbo.dao;

import java.util.List;
import java.util.Map;

import com.anjbo.bean.risk.BusinfoDto;
import com.anjbo.bean.risk.HuaanDto;

public interface HuaanMapper {

	public HuaanDto detail(String orderNo);
	
	public int delete(String orderNo);
	
	public int insert(HuaanDto obj);
	
	public int update(HuaanDto obj);
	
	List<Map<String,Object>> getParentBusInfoTypes();
	
	List<Map<String,Object>> getSonBusInfoTypes(Map<String,Object> map);
	
	List<BusinfoDto> selectListMap(Map<String,Object> map);

	public void insretImg(List<Map<String,Object>> list);
	
	public int deleteImg(Map<String,Object> map);
	
	int countBusinfo(String orderNo);
}
