package com.anjbo.service;

import com.anjbo.bean.product.MortgageDto;


public interface MortgageService {
	/**
	 * 查询详情
	 * @param dto
	 * @return
	 */
	public MortgageDto selectMortgage(MortgageDto dto);
	
	/**
	 * 添加信息
	 * @param dto
	 * @return
	 */
	public int addMortgage(MortgageDto dto);
	public int updateMortgage(MortgageDto dto);
}
