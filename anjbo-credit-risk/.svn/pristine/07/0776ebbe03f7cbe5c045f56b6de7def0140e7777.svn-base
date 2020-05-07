package com.anjbo.service;

import com.anjbo.bean.risk.BusinfoDto;
import com.anjbo.bean.risk.HuaanDto;
import com.anjbo.common.RespDataObject;

import java.util.List;
import java.util.Map;

public interface HuaanService {

	public HuaanDto detail(String orderNo);
	
	public int delete(String orderNo);
	
	public int insert(HuaanDto obj);
	
	public int update(HuaanDto obj);
	
	public RespDataObject<Map<String,Object>> getOrdinaryBusinfoType(Map<String,Object> map);
	
	public RespDataObject<Map<String,Object>> getBusinfoTypeTree(Map<String,Object> map);
	
	public void insretImg(Map<String,Object> map);
	
	List<BusinfoDto> selectListMap(Map<String,Object> map);
	
	public List<BusinfoDto> lookOver(Map<String, Object> map);
	
	public int deleteImg(Map<String,Object> map);
	
	public RespDataObject<Map<String,Object>> getBusinfoTypeTree(Map<String,Object> map,String key,List<Map<String,Object>> listTmp,List<Map<String,Object>> imgList);
}
