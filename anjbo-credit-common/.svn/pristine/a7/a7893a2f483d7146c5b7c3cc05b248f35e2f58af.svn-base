/*
 *Project: anjbo-credit-common
 *File: com.anjbo.service.impl.BaseServiceImpl.java  <2017年10月24日>
 ****************************************************************
 * 版权所有@2017 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.anjbo.bean.BasePager;
import com.anjbo.bean.vo.PageList;
import com.anjbo.dao.BaseMapper;
import com.anjbo.service.BaseService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * @Author KangLG 
 * @Date 2017年10月24日 下午3:17:48
 * @version 1.0
 */
public class BaseServiceImpl<T, KID extends Serializable> implements BaseService<T, KID> {
	private Log logger = LogFactory.getLog(this.getClass());
	@Autowired BaseMapper<T, KID> baseMapper;

	@Override
	public List<T> search(T dto) {
		return baseMapper.search(dto);
	}
	@Override
	public PageList<T> searchPage(T dto) {
		if(dto instanceof BasePager){
			BasePager base = (BasePager)dto;
			// 设置分页前置信息
			PageHelper.startPage(
					base.getStart()>0 ? base.getPageSize()/base.getStart() : 0, 
					base.getPageSize()>0 ? base.getPageSize() : 15, 
					true);	
			// 构建分页数据
			Page<T> pagePlugin = (Page<T>)this.search(dto);				
			return new PageList<T>(pagePlugin.getTotal(), pagePlugin.getResult());
		}		
		return null;
	}

	@Override
	public List<Object> searchComplex(Map<String, Object> map) {
		return baseMapper.searchComplex(map);
	}
	@Override
	public PageList<Object> searchComplexPage(Map<String, Object> map) {
		if(null!=map && map.containsKey("start") && map.containsKey("pageSize")){	
			// 设置分页前置信息
			PageHelper.startPage(
					Integer.valueOf((String)map.get("start")), 
					Integer.valueOf((String)map.get("pageSize"))>0 ? Integer.valueOf((String)map.get("pageSize")) : 15, 
					true);
			// 构建分页数据
			Page<Object> pagePlugin = (Page<Object>)this.searchComplex(map);			
			return new PageList<Object>(pagePlugin.getTotal(), pagePlugin.getResult());
		}
		return null;
	}

	@Override
	public T getEntity(KID id) {
		return baseMapper.getEntity(id);
	}

	@Override
	public KID insert(T dto) {
		return baseMapper.insert(dto);
	}

	@Override
	public int delete(T dto) {
		return baseMapper.delete(dto);
	}

	@Override
	public int update(T dto) {
		return baseMapper.update(dto);
	}

	@Override
	public int batchInsert(List<T> list) {
		return baseMapper.batchInsert(list);
	}

	@Override
	public int batchDelete(List<KID> list) {
		return baseMapper.batchDelete(list);
	}

	@Override
	public int batchUpdate(List<T> list) {
		return baseMapper.batchUpdate(list);
	}

}