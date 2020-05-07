package com.anjbo.dao;

import java.util.List;

import com.anjbo.bean.product.ProductProcessDto;


/**
 * 产品流程
 * @author lic
 * @date 2017-6-9
 */
public interface ProductProcessMapper {

	List<ProductProcessDto> selectProductProcessList(ProductProcessDto productProcessDto);

	int selectProductProcessCount(ProductProcessDto productProcessDto);
	
}