package com.anjbo.service;

import com.anjbo.bean.finance.LendingInstructionsDto;


public interface LendingInstructionService {

	
	/**
	 * 添加基本信息
	 * @param harvestDto
	 * @return
	 */
	public int addLendingInstruction(LendingInstructionsDto lendingInstructionsDto);
	
	/**
	 * 完善信息
	 * @param harvestDto
	 */
	public int updateLendingInstruction(LendingInstructionsDto lendingInstructionsDto);

	/**
	 * 查询详情
	 * @param orderNo
	 * @return
	 */
	public LendingInstructionsDto findByInstruction(String orderNo);
	
    /**
     * 撤回
     * @param orderNo
     * @return
     */
    public int updwithdraw(LendingInstructionsDto lendingInstructionsDto);
    /**
     * 删除
     * @param lendingInstructionsDto
     * @return
     */
    public int delectInstruction(String orderNo);
}
