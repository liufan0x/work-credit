package com.anjbo.service;

import java.util.List;

import com.anjbo.bean.finance.LendingDto;

public interface LendingService {

	/**
	 * 查询详情
	 * @param orderNo
	 * @return
	 */
	public LendingDto findByLending(LendingDto lendingPayDto);
	/**
	 * 添加订单基本信息
	 * @param dto
	 * @return
	 */
	public int addLending(LendingDto dto);
	/**
	 * 完善信息
	 * @param lendingPayDto
	 * @return
	 */
	public int updateLending(LendingDto lendingPayDto);
	
	/**
     * 撤回
     * @param orderNo
     * @return
     */
    public int updwithdraw(LendingDto lendingPayDto);
    /**
	 * 查询放款时间
	 * @param orderNO
	 * @return
	 */
	public LendingDto selectLendingTime(String orderNO);
	
	public List<String>  selectOrderNo();
	/**
	 * 更新回扣处理人
	 * @param lendingDto
	 * @return
	 */
	public int updatereceivableForUid(LendingDto lendingDto);
	
}
