package com.anjbo.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.anjbo.bean.product.ProductDto;
import com.anjbo.bean.product.ProductProcessDto;
import com.anjbo.dao.ProductBaseMapper;
import com.anjbo.dao.ProductProcessMapper;
import com.anjbo.service.ProductBaseService;

@Service
public class ProductBaseServiceImpl implements ProductBaseService {
	
	@Resource
	private ProductBaseMapper productBaseMapper;
	@Resource
	private ProductProcessMapper productProcessMapper;

	@Override
	public List<ProductDto> selectProductBaseList(ProductDto productDto){
		List<ProductDto> productDtos = productBaseMapper.selectProductBaseList(productDto);
		if(productDto == null){
			List<ProductProcessDto> productProcessDtos = productProcessMapper.selectProductProcessList(null);
			List<ProductProcessDto> temp = null;
			for (ProductDto product : productDtos) {
				temp = new ArrayList<ProductProcessDto>();
				for (ProductProcessDto productProcessDto : productProcessDtos) {
					if(product.getId() == productProcessDto.getProductId()){
						temp.add(productProcessDto);
					}
				}
				product.setProductProcessDtos(temp);
			}
		}
		return productDtos;
	}
	
	@Override
	public int selectProductBaseCount(ProductDto productDto) {
		return productBaseMapper.selectProductBaseCount(productDto);
	}
	@Override
	public ProductDto findproductDto(ProductDto productDto) {
		return productBaseMapper.findproductDto(productDto);
	}
	
	@Override
	public ProductDto selectProductBase(ProductDto productDto) {
		return productBaseMapper.selectProductBase(productDto);
	}
	
	@Override
	public int updateProductBase(ProductDto productDto) {
		return productBaseMapper.updateProductBase(productDto);
	}
	
}
