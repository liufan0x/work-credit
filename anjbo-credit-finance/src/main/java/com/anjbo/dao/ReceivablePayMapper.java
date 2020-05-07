package com.anjbo.dao;

import com.anjbo.bean.finance.ReceivablePayDto;

public interface ReceivablePayMapper {
	/**
	 * 查询详情
	 * @param orderNo
	 * @return
	 */
	public ReceivablePayDto findByReceivablePay(ReceivablePayDto dto);
	/**
	 * 添加
	 * @param orderNo
	 * @return
	 */
	public int addReceivablePay(ReceivablePayDto dto);
	/**
	 * 完善信息
	 * @param dto
	 * @return
	 */
	public int updateReceivablePay(ReceivablePayDto dto);
}
