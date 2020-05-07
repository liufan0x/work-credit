package com.anjbo.service;

import com.anjbo.bean.product.NewlicenseDto;


public interface NewlicenseService {
	/**
	 * 查询详情
	 * @param dto
	 * @return
	 */
	public NewlicenseDto selectNewlicense(NewlicenseDto dto);
	
	/**
	 * 添加信息
	 * @param dto
	 * @return
	 */
	public int addNewlicense(NewlicenseDto dto);
}
