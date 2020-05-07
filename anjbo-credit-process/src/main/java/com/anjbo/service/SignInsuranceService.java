package com.anjbo.service;

import com.anjbo.bean.product.SignInsuranceDto;


public interface SignInsuranceService {

	/**
	 * 查询详情
	 * @param dto
	 * @return
	 */
	public SignInsuranceDto select(SignInsuranceDto dto);
	
}
