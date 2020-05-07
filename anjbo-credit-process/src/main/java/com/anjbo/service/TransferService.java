package com.anjbo.service;

import com.anjbo.bean.product.TransferDto;


public interface TransferService {
	/**
	 * 查询详情
	 * @param dto
	 * @return
	 */
	public TransferDto selectTransfer(TransferDto dto);
	
	/**
	 * 添加信息
	 * @param dto
	 * @return
	 */
	public int addTransfer(TransferDto dto);
}
