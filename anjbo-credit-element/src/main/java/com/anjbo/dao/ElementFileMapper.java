package com.anjbo.dao;

import java.util.List;
import java.util.Map;

import com.anjbo.bean.element.DocumentsDto;
import com.anjbo.bean.element.ElementListDto;
import com.anjbo.bean.element.ForeclosureTypeDto;
import com.anjbo.bean.element.PaymentTypeDto;


public interface ElementFileMapper {
	
	//批量插入要件
	public int insertElementFile(List<Map<String,Object>> list);
	
	
	//查询要件
	public List<Map<String, Object>> selectElementFileList(String[] ids);
	
	/**
	 * 根据ids和elementType查询要件集合
	 * @param param
	 * @return
	 */
	public List<Map<String,Object>> selectElementFileListByElementType(Map<String,Object> param);
	
	
	//根据订单号查询要件
	public List<Map<String, Object>> selectElementFileListbyOrderNo(Map<String,Object> param);
	
	/**
	 * 批量更新要件
	 * @param list
	 * @return
	 */
	public int updateElementFile(List<Map<String,Object>> list);
	
	/**
	 * 更新要件状态
	 * @param param
	 */
	public void updateStatusByIds(Map<String,Object> param);
}