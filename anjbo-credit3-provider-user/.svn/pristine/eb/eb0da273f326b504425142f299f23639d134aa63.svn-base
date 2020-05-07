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
import com.anjbo.bean.FundCostDiscountDto;
import com.anjbo.bean.FundCostDto;
import com.anjbo.bean.ProductDto;
import com.anjbo.controller.api.DataApi;
import com.anjbo.dao.FundCostMapper;
import com.anjbo.service.FundCostDiscountService;
import com.anjbo.service.FundCostService;
import com.anjbo.service.impl.BaseServiceImpl;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-05-09 17:56:48
 * @version 1.0
 */
@Service
public class FundCostServiceImpl extends BaseServiceImpl<FundCostDto>  implements FundCostService {
	@Autowired private FundCostMapper fundCostMapper;
	
	@Resource private FundCostDiscountService fundCostDiscountService;
	
	@Resource private DataApi dataApi;
	
	@Override
	public List<FundCostDto> search(FundCostDto dto) {
		List<ProductDto> productDtos = dataApi.getProductList();
		List<DictDto> dictDtos = dataApi.getDictDtoListByType("cityList");
		List<FundCostDto> fundCostDtos = super.search(dto);
		
		List<FundCostDiscountDto> fundCostDiscountDtos = fundCostDiscountService.search(null);
		for (FundCostDto fundCostDto : fundCostDtos) {
			if(fundCostDto.getProductId() != null) {
				String cityCode = (fundCostDto.getProductId()+"").substring(0, 4);
				for (ProductDto productDto : productDtos) {
					if(productDto.getId().equals(fundCostDto.getProductId())) {
						
						for (DictDto dictDto : dictDtos) {
							if(cityCode.equals(dictDto.getCode())){
								fundCostDto.setProductName(dictDto.getName()+"-"+productDto.getProductName());
								break;
							}
						}
						break;
					}
				}
			}
			
			if(fundCostDto.getDiscountHas() == 1){
				String discountHasStr = "";
				for (FundCostDiscountDto fundCostDiscountDto : fundCostDiscountDtos) {
					if(fundCostDiscountDto.getFundCostId().equals(fundCostDto.getId())){
						discountHasStr += "满"+fundCostDiscountDto.getMoney()+"万"+fundCostDiscountDto.getRate()+"%</br>";
					}
				}
				fundCostDto.setDiscountHasStr(discountHasStr);
			}
			
		}
		return fundCostDtos;
	}
	
	@Override
	public FundCostDto insert(FundCostDto dto) {
		dto = super.insert(dto);
		if(dto.getDiscountHas() == 1){
			for (FundCostDiscountDto fundCostDiscountDto : dto.getFundCostDiscountDtos()) {
				fundCostDiscountDto.setFundCostId(dto.getId());
				fundCostDiscountService.insert(fundCostDiscountDto);
			}
		}
		return dto;
	}
	
	@Override
	public int update(FundCostDto dto) {
		FundCostDiscountDto tempDto = new FundCostDiscountDto();
		tempDto.setFundCostId(dto.getId());
		fundCostDiscountService.delete(tempDto);
		if(dto.getDiscountHas() == 1){
			for (FundCostDiscountDto fundCostDiscountDto : dto.getFundCostDiscountDtos()) {
				fundCostDiscountDto.setFundCostId(dto.getId());
				fundCostDiscountService.insert(fundCostDiscountDto);
			}
		}
		return super.update(dto);
	}
	
}
