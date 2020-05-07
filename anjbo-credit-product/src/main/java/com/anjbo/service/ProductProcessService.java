package com.anjbo.service;

import java.util.List;

import com.anjbo.bean.product.ProductProcessDto;


/**
 * 产品流程
 * @author lic
 * @date 2017-6-9
 */
public interface ProductProcessService {

	List<ProductProcessDto> selectProductProcessList(ProductProcessDto productProcessDto);

	int selectProductProcessCount(ProductProcessDto productProcessDto);
	
}