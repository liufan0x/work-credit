package com.anjbo.service;

import com.anjbo.bean.product.CancellationDto;

public interface CancellationService {
	/**
	 * 查询详情
	 * @param dto
	 * @return
	 */
	public CancellationDto selectCancellation(CancellationDto dto);
	/**
	 * 关联查询抵押
	 * @param dto
	 * @return
	 */
	public CancellationDto selectCancellationByMortgage(CancellationDto dto);
	/**
	 * 添加信息
	 * @param dto
	 * @return
	 */
	public int addCancellation(CancellationDto dto);
}
