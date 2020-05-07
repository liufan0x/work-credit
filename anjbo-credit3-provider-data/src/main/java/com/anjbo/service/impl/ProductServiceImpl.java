/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anjbo.bean.DictDto;
import com.anjbo.bean.ProductDto;
import com.anjbo.dao.ProductMapper;
import com.anjbo.service.DictService;
import com.anjbo.service.ProductService;
import com.anjbo.service.impl.BaseServiceImpl;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-05-03 09:49:33
 * @version 1.0
 */
@Service
public class ProductServiceImpl extends BaseServiceImpl<ProductDto>  implements ProductService {
	
	@Autowired private ProductMapper productMapper;
	
	@Resource private DictService dictService;
	
	@Override
	public List<ProductDto> search(ProductDto dto) {
		DictDto tempDto = new DictDto();
		tempDto.setType("cityList");
		List<DictDto> dictDtos = dictService.search(tempDto);
		List<ProductDto> productDtos = super.search(dto);
		for (ProductDto productDto : productDtos) {
			for (DictDto dictDto : dictDtos) {
				if(dictDto.getCode().equals(productDto.getCityCode())) {
					productDto.setCityName(dictDto.getName());
					break;
				}
			}
		}
		return productDtos;
	}
	
	@Override
	public ProductDto find(ProductDto dto) {
		DictDto tempDto = new DictDto();
		tempDto.setType("cityList");
		List<DictDto> dictDtos = dictService.search(tempDto);
		ProductDto productDto = super.find(dto);
		for (DictDto dictDto : dictDtos) {
			if(dictDto.getCode().equals(productDto.getCityCode())) {
				productDto.setCityName(dictDto.getName());
				break;
			}
		}
		return productDto;
	}
	
}
