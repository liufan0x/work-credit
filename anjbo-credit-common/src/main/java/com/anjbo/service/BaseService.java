/*
 *Project: anjbo-credit-common
 *File: com.anjbo.service.BaseService.java  <2017年10月24日>
 ****************************************************************
 * 版权所有@2017 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.anjbo.bean.vo.PageList;

/**
 * @Author KangLG 
 * @Date 2017年10月24日 下午3:16:30
 * @version 1.0
 */
public interface BaseService<T, KID extends Serializable>{
	/**
	 * 列表查询
	 * @Author KangLG<2017年10月24日>
	 * @param dto
	 * @return
	 */
	List<T> search(T dto);
	PageList<T> searchPage(T dto);
	
	/**
	 * 列表复合查询
	 * @Author KangLG<2017年10月24日>
	 * @param map
	 * @return
	 */
	List<Object> searchComplex(Map<String, Object> map);
	PageList<Object> searchComplexPage(Map<String, Object> map);
	
	/**
	 * 获取实体BY主键
	 * @Author KangLG<2017年10月24日>
	 * @param id
	 * @return
	 */
	T getEntity(KID id);
	
	/**
	 * 新增
	 * @Author KangLG<2017年10月24日>
	 * @param dto
	 * @return 主键ID
	 */
	KID insert(T dto);
	
	/**
	 * 删除
	 * @Author KangLG<2017年10月24日>
	 * @param dto
	 * @return 受影响行数
	 */
	int delete(T dto);
	
	/**
	 * 修改
	 * @Author KangLG<2017年10月24日>
	 * @param dto
	 * @return 受影响行数
	 */
	int update(T dto);
	
	/**
	 * 批量新增
	 * @Author KangLG<2017年10月24日>
	 * @param list
	 * @return 受影响行数
	 */
    int batchInsert(List<T> list);
    
    /**
     * 批量删除
     * @Author KangLG<2017年10月24日>
     * @param list
     * @return 受影响行数
     */
	int batchDelete(List<KID> list);
	
    /**
     * 批量修改
     * @Author KangLG<2017年10月24日>
     * @param list
     * @return 受影响行数
     */
    int batchUpdate(List<T> list);
	
}