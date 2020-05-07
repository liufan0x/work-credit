package com.anjbo.service.impl;

import com.anjbo.dao.ProductFlowBaseMapper;
import com.anjbo.service.ProductFlowBaseService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.List;
import java.util.Map;
@Service
@Transactional
public class ProductFlowBaseServiceImpl implements ProductFlowBaseService {

	@Resource
	private ProductFlowBaseMapper ProductFlowBaseMapper;
	
	@Override
	@Transactional
	public int insertProductFlowBase(Map<String, Object> params) {
		return ProductFlowBaseMapper.insertProductFlowBase(params);
	}

	@Override
	public List<Map<String, Object>> selectProductFlowBaseList(
			Map<String, Object> params) {
		return ProductFlowBaseMapper.selectProductFlowBaseList(params);
	}

	@Override
	public Map<String, Object> selectProductFlowBase(String orderNo) {
		return ProductFlowBaseMapper.selectProductFlowBase(orderNo);
	}

	/**
	 * 根据节点名称查询最后一条数据
	 *
	 * @param map
	 * @return
	 */
	@Override
	public Map<String, Object> selectProductFlowByProcessId(Map<String, Object> map) {
		return ProductFlowBaseMapper.selectProductFlowByCurrentProcessId(map);
	}

	/**
	 * 可重新打开的订单流水
	 */
	@Override
	public List<Map<String, Object>> selectProductFlowBaseAll() {
		return ProductFlowBaseMapper.selectProductFlowBaseAll();
	}
}
