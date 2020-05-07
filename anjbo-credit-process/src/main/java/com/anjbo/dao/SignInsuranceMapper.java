package com.anjbo.dao;

import com.anjbo.bean.product.SignInsuranceDto;


public interface SignInsuranceMapper {
	/**
	 * 查询详情
	 * @param dto
	 * @return
	 */
	public SignInsuranceDto select(SignInsuranceDto dto);
	
}
