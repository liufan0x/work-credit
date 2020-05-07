package com.anjbo.service;

import java.util.List;

import com.anjbo.bean.product.ProductDto;

public interface ProductBaseService {

	List<ProductDto> selectProductBaseList(ProductDto productDto);

	int selectProductBaseCount(ProductDto productDto);
	
	ProductDto selectProductBase(ProductDto productDto);
	
	int updateProductBase(ProductDto productDto);

	ProductDto findproductDto(ProductDto productDto);
}
