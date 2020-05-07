package com.anjbo.service;

import java.util.List;
import java.util.Map;

import com.anjbo.bean.product.data.ProductDataDto;

public interface ProductListBaseService {
	
	/**
	 * 查询所有订单
	 * @param map
	 * @return
	 */
	List<Map<String,Object>> selectAllOrder(Map<String,Object> map);
	
	/**
	 * 新增列表数据
	 * @param params
	 * @return
	 */
	int insertProductListBase(Map<String, Object> params);
	
	/**
	 * 查询列表数据
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> selectProductListBase(Map<String, Object> params);
	
	/**
	 * 查询列表数据总数
	 * @param params(tblName)
	 * @return
	 */
	int selectProductListBaseCount(Map<String, Object> params);
	
	/**
	 * 修改列表数据
	 * @param params
	 * @return
	 */
	int updateProductListBase(Map<String, Object> params);
	
	/**
	 * 根据订单号查询订单列表信息
	 * @param params
	 * @return
	 */
	Map<String,Object> selectProductListBaseByOrderNo(ProductDataDto productDataDto);
	
	/**
	 * 指派渠道经理
	 * @param params
	 * @return
	 */
	int repaymentChannelManager(Map<String,Object> params);
	
	/**
	 * 关闭订单
	 * @param params
	 * @return
	 */
	int close(Map<String,Object> params);
	
	/**
	 * 重新开启订单
	 * @param params
	 * @return
	 */
	int reopen(Map<String,Object> params);
	
	/**
	 * 撤回订单
	 * @param params
	 * @return
	 */
	int withdraw(Map<String,Object> params);
	/**
	 * 修改流程节点
	 * @param params
	 * @return
	 */
	int  updateProcessId(Map<String, Object> params);
	/**
	 * 定时查询
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> findTask();
	/**
	 * 修改列表贷款金额
	 * @param params
	 * @return
	 */
	int updataLoanAmount(Map<String, Object> params);
	
	/**
	 * 查询订单列表信息
	 * @param params
	 * @return
	 */
	Map<String,Object> selectOrderList(Map<String, Object> params);
	/**
	 * 根据机构码查询机构列表信息
	 * @param params
	 * @return
	 */
	Map<String,Object> selectSmList(Map<String, Object> params);

	int updateProductListBaseByKey(ProductDataDto productDataDto);
	/**
	 * 自定义条件查询list表
	 * @param map(key=tblDataName:表名,key=whereCondition:条件)
	 * @return
	 */
	List<Map<String,Object>> selectProductListCustomWhereCondition(Map<String,Object> map);
	
	
	public int updatePrice(Map<String, Object> params);
	
}
