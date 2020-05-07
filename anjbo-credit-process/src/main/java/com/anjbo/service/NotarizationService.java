package com.anjbo.service;

import com.anjbo.bean.product.NotarizationDto;


public interface NotarizationService {

	/**
	 * 查询详情
	 * @param dto
	 * @return
	 */
	public NotarizationDto selectNotarization(NotarizationDto dto);
	
	/**
	 * 添加信息
	 * @param dto
	 * @return
	 */
	public int addNotarizetion(NotarizationDto dto);
	
	public int updateNotarizetion(NotarizationDto dto);
}
