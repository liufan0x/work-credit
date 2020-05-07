package com.anjbo.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anjbo.bean.product.FddMortgageReleaseDto;
import com.anjbo.dao.FddMortgageReleaseMapper;
import com.anjbo.service.FddMortgageReleaseService;

@Service
public class FddMortgageReleaseServiceImpl implements FddMortgageReleaseService{

	@Resource 
	FddMortgageReleaseMapper fddMortgageReleaseMapper;
	@Override
	public FddMortgageReleaseDto findByFddMortgageRelease(String orderNo) {
		// TODO Auto-generated method stub
		return fddMortgageReleaseMapper.findByFddMortgageRelease(orderNo);
	}

	@Override
	public int addFddMortgageRelease(FddMortgageReleaseDto releaseDto) {
		FddMortgageReleaseDto mortgageReleaseDto=fddMortgageReleaseMapper.findByFddMortgageRelease(releaseDto.getOrderNo());
		if(mortgageReleaseDto==null){
		  return fddMortgageReleaseMapper.addFddMortgageRelease(releaseDto);
		}else{
			return fddMortgageReleaseMapper.updFddMortgageRelease(releaseDto);
		}
	}

}
