package com.anjbo.dao;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestParam;

import com.anjbo.bean.element.DocumentsDto;
import com.anjbo.bean.element.ElementListDto;
import com.anjbo.bean.element.ForeclosureTypeDto;
import com.anjbo.bean.element.PaymentTypeDto;


public interface ElementFileFlowMapper {
	
	//批量插入要件
	public int insertElementFileFlow(List<Map<String,Object>> list);
	
	
	//查询要件
	public List<Map<String, Object>> selectElementFileFlow(String[] ids);
	
	
	//根据订单号查询要件
	public List<Map<String, Object>> selectElementFileFlowByOrderNo(Map<String,Object> param);
	
	//删除旧的流水
	//public void deleteFormOrderNo(Map<String,Object> param);
	
}