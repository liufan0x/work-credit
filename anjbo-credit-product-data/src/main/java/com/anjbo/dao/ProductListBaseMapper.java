package com.anjbo.dao;

import java.util.List;
import java.util.Map;

import com.anjbo.bean.product.data.ProductDataDto;

/**
 * @author lic
 * @date 2017-6-9
 */
public interface ProductListBaseMapper {
	
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
	 * 插入指定的数据库字段
	 * @param params
	 * @return
	 */
	int insertProductListBaseByKey(ProductDataDto productDataDto);
	
	/**
	 * 修改列表数据
	 * @param params
	 * @return
	 */
	int updateProductListBase(Map<String, Object> params);
	
	/**
	 * 修改列表数据可更新空
	 * @param params
	 * @return
	 */
	int updateProductListBaseNull(Map<String, Object> params);
	
	/**
	 * 更新指定的数据库字段
	 * @param params
	 * @return
	 */
	int updateProductListBaseByKey(ProductDataDto productDataDto);
	
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
	 * 根据订单号查询订单列表信息
	 * @param params
	 * @return
	 */
	Map<String,Object> selectProductListBaseByOrderNo(ProductDataDto productDataDto);
	/**
	 * 修改流程节点
	 * @param params
	 * @return
	 */
	int  updateProcessId(Map<String, Object> params);
	/**
	 * 定时查询
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

	/**
	 *删除
	 * @param map key=tblDataName(表名),key=orderNo
	 * @return
	 */
	int delete(Map<String,Object> map);

	/**
	 * 自定义条件查询list表
	 * @param map(key=tblDataName:表名,key=whereCondition:条件)
	 * @return
	 */
	List<Map<String,Object>> selectProductListCustomWhereCondition(Map<String,Object> map);
	
	public int updatePrice(Map<String, Object> params);
}