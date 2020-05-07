package com.anjbo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anjbo.bean.product.ProductProcessDto;
import com.anjbo.dao.ProductProcessMapper;
import com.anjbo.service.ProductProcessService;


/**
 * 产品流程
 * @author lic
 * @date 2017-6-9
 */
@Service
public class ProductProcessServiceImpl implements ProductProcessService{

	@Resource
	private ProductProcessMapper productProcessMapper;
	
	public List<ProductProcessDto> selectProductProcessList(ProductProcessDto productProcessDto){
		return productProcessMapper.selectProductProcessList(productProcessDto);
	}
	
	@Override
	public int selectProductProcessCount(ProductProcessDto productProcessDto) {
		return productProcessMapper.selectProductProcessCount(productProcessDto);
	}
	
}