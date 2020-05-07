package com.anjbo.dao;

import com.anjbo.bean.product.UploadInsuranceDto;


public interface UploadInsuranceMapper {
	/**
	 * 查询详情
	 * @param dto
	 * @return
	 */
	public UploadInsuranceDto select(UploadInsuranceDto dto);
	
}
