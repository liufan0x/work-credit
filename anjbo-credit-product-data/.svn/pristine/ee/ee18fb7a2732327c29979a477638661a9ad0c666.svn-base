package com.anjbo.service;

import java.util.Map;

import com.anjbo.common.RespStatus;

public interface ProductSubmitBaseService {
	
	/**
	 * 处理业务，插入流水
	 * @param map
	 */
	public RespStatus submit(Map<String,Object> map) throws Exception;
	
	/**
	 * 校验信息是否完整
	 * @param map
	 */
	public void check(Map<String,Object> map);
	
	/**
	 * 新单提交失败删除订单
	 * @param map
	 */
	public void submitFailDelete(Map<String,Object> map);
}
