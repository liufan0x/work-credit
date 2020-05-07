package com.anjbo.dao;

import java.util.List;

import com.anjbo.bean.finance.LendingDto;

public interface LendingMapper {
	
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
	 * 查询详情
	 * @param orderNo
	 * @return
	 */
	public LendingDto findByLending(LendingDto lendingPayDto);
	
	/**
     * 撤回
     * @param orderNo
     * @return
     */
    public int updwithdraw(LendingDto lendingPayDto);
    /**
	 * 删除
	 * @param orderNo
	 */
	public void deleteLending(String orderNo);
	
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
