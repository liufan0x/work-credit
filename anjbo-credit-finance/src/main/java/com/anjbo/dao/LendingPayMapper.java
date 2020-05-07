package com.anjbo.dao;

import com.anjbo.bean.finance.LendingPayDto;

public interface LendingPayMapper {
	
	/**
	 * 添加订单基本信息
	 * @param dto
	 * @return
	 */
	public int addLendingPay(LendingPayDto dto);
	/**
	 * 完善信息
	 * @param lendingPayDto
	 * @return
	 */
	public int updatePay(LendingPayDto lendingPayDto);
	
	/**
	 * 查询详情
	 * @param orderNo
	 * @return
	 */
	public LendingPayDto findByPay(LendingPayDto lendingPayDto);
	
    /**
	 * 删除
	 * @param orderNo
	 */
	public void deletePay(String orderNo);
}
