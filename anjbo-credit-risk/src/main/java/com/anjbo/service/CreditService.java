package com.anjbo.service;

import java.util.List;
import java.util.Map;

import com.anjbo.bean.risk.CreditDto;

public interface CreditService {

	/**
	 * 根据订单编号查询征信信息
	 * @param orderNo 订单编号 
	 * @return CreditDto
	 */
	public CreditDto detail(String orderNo);
	
	/**
	 * 新增征信信息
	 * @param credit 征信数据
	 * @return int 影响条数
	 */
	public int insertCredit(CreditDto credit);
	
	/**
	 * 更新征信信息
	 * @param credit 征信数据
	 * @return int 影响条数
	 */
	public int updateCredit(CreditDto credit)throws IllegalAccessException;
	
	public int updateNull(CreditDto dto);
	/**
	 * 查询征信日志流水
	 * @param orderNo
	 * @return
	 */
	public List<Map<String,Object>> selectCreditLog(String orderNo);
	
	/**
	 * 新增修改征信日志流水
	 * @param map{key=createUid(创建人),key=createTime(创建时间),key=orderNo(订单编号)
	 * 				,key=beanColumn(修改的字段对应实体属性),key=colName(字段名称),key=startVal(初始值)
	 * 				,key=endVal(修改值),key=isShow(是否展示),key=type(类型),key=operationType(操作类型)}
	 * @return
	 */
	public int insertCreditLog(Map<String,Object> map);
	
	/**
	 * 批量新增修改征信日志流水
	 * @param map{key=createUid(创建人),key=createTime(创建时间),key=orderNo(订单编号)
	 * 				,key=beanColumn(修改的字段对应实体属性),key=colName(字段名称),key=startVal(初始值)
	 * 				,key=endVal(修改值),key=isShow(是否展示),key=type(类型),key=operationType(操作类型)}
	 * @param list
	 * @return
	 */
	public int insertCreditLogBatch(List<Map<String,Object>> list);
	
	/**
	 * 更新修改征信日志流水
	* @param map{key=updateUid(修改人),key=colName(字段名称),key=beanColumn(修改的字段对应实体属性:必传)
	 * 				,key=startVal(初始值),key=endVal(修改值),key=isShow(是否展示)
	 * 				,key=type(类型),key=operationType(操作类型),key=orderNo(订单号:必传)}
	 * @return
	 */
	public int updateCreditLog(Map<String,Object> map);
	
}
