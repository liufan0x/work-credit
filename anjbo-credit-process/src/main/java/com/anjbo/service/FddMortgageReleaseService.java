package com.anjbo.service;

import com.anjbo.bean.product.FddMortgageReleaseDto;

public interface FddMortgageReleaseService {
	
	public FddMortgageReleaseDto findByFddMortgageRelease(String orderNo);
	public int addFddMortgageRelease(FddMortgageReleaseDto releaseDto);
}
