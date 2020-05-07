package com.anjbo.service;

import java.util.List;
import java.util.Map;

import com.anjbo.bean.product.data.ProductDataDto;

public interface ProductDataBaseService {
	
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
	String updateProductDataBase(ProductDataDto productDataDto);
	
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
	
}
