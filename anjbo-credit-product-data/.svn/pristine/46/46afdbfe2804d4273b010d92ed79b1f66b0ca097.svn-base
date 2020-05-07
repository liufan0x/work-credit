package com.anjbo.service.cm;

import java.util.Map;

import com.anjbo.common.RespDataObject;




/**
 * 
 * @author chenzm   
 * @date 2017-8-23 下午05:26:05
 */
public interface AssessService {
	/**
	 * 新增评估
	 * 调用建行C008接口
	 * @param assessDto
	 * @return
	 */
	RespDataObject<Map<String,Object>> addAssess(Map<String,Object> map);
	/**
	 * 匹配客户经理信息
	 * 调用建行C006接口
	 * @return
	 */
	RespDataObject<Map<String, Object>> matchCusManager(Map<String, Object> map);
	/**
	 * 买卖双方信息
	 * 调用建行C024接口
	 * @return
	 */
	RespDataObject<Map<String,Object>> busInfo(Map<String,Object> map);
	/**
	 * 提交银行审核客户信息
	 * 调用建行C001,c002,c027接口
	 * @return
	 * @param 客户信息,贷款信息,影像资料
	 */
	RespDataObject<Map<String, Object>> cusInfo(Map<String,Object> map);
	/**
	 * 存量客户查询
	 * 调用建行C005接口
	 * @return
	 */
	RespDataObject<Map<String, Object>> cusQuery(Map<String, Object> map);
	
	/**
	 * 预约抵押
	 * 调用建行C025接口
	 * @return
	 */
	RespDataObject<Map<String, Object>> preMortgage(Map<String,Object> map);
	/**
	 * 取证抵押
	 * 调用建行C026接口
	 * @return
	 */
	RespDataObject<Map<String, Object>> takeMortgage(Map<String, Object> map);
	
}
