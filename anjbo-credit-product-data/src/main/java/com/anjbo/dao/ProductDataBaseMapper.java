package com.anjbo.dao;

import java.util.List;
import java.util.Map;

import com.anjbo.bean.product.data.ProductDataDto;

/**
 * @author lic
 * @date 2017-6-9
 */
public interface ProductDataBaseMapper {
	
	/**
	 * 新增产品数据
	 * @param params
	 * @return
	 */
	int insertProductDataBase(ProductDataDto productDataDto);
	
	/**
	 * 修改产品数据
	 * @param params
	 * @return
	 */
	int updateProductDataBase(ProductDataDto productDataDto);
	
	/**
	 * 查询产品数据
	 * @param params
	 * @return
	 */
	Map<String, Object> selectProductDataBase(ProductDataDto productDataDto);
	
	/**
	 * 查询产品数据集合
	 * @param productDataDto
	 * @return
	 */
	List<Map<String,Object>> selectProductDataBaseList(ProductDataDto productDataDto);
	
	/**
	 * 查询产品数据
	 * @param params
	 * @return
	 */
	ProductDataDto selectProductDataBaseDto(ProductDataDto productDataDto);

	/**
	 * 根据订单编号查询所以的数据
	 * @param productDataDto
	 * @return
	 */
	List<ProductDataDto> selectAllProductDataBaseList(ProductDataDto productDataDto);

	/**
	 * 删除
	 * @param map key=tblDataName(表名),key=tblName,key=orderNo
	 * @return
	 */
	int delete(Map<String,Object> map);
}