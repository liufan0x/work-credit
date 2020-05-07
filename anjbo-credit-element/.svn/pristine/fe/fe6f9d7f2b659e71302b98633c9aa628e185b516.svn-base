package com.anjbo.dao;

import java.util.List;
import java.util.Map;

import com.anjbo.bean.element.DocumentsDto;
import com.anjbo.bean.element.ElementListDto;
import com.anjbo.bean.element.ForeclosureTypeDto;
import com.anjbo.bean.element.PaymentTypeDto;


public interface AccessFlowMapper {
	
	//存取记录流水
	public int insertAccessFlowRecorde(Map<String,Object> map);
	
	//查询存取记录流水
	public List<Map<String, Object>> selectAccessFlowRecorde(Map<String,Object> map);
	
	
	//查询存取记录流水列表
	public List<Map<String, Object>> selectAccessFlowList(Map<String,Object> map);
	
	
	//通过ID查询存取记录流水
	public Map<String, Object> selectAccessFlowById(Map<String,Object> map);
	
	
	//查询存记录流水集合
	public List<Map<String, Object>> selectAccessFlowListByOrderNo(Map<String,Object> map);
	
	//查询取记录
	public Map<String, Object> selectAccessFlowbyDbId(Map<String,Object> map); 
	
	//查询最后一次存记录或者还记录图片
	public List<Map<String, Object>> selectLastAccessByMap(Map<String,Object> map); 

	public void updateById(Map<String,Object> map);
}