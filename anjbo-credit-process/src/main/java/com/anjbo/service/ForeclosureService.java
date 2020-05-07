package com.anjbo.service;

import com.anjbo.bean.product.ForeclosureDto;


public interface ForeclosureService {
	/**
	 * 查询详情
	 * @param dto
	 * @return
	 */
	public ForeclosureDto selectForeclosure(ForeclosureDto dto);
	
	/**
	 * 添加信息
	 * @param dto
	 * @return
	 */
	public int addForeclosure(ForeclosureDto dto);
	
}
