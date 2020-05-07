package com.anjbo.dao;

import com.anjbo.bean.finance.LendingInterestDto;

public interface LendingInterestMapper {
	/**
	 * 查询详情
	 * @param orderNo
	 * @return
	 */
	public LendingInterestDto findByInterest(LendingInterestDto InterestDto);

	/**
	 * 添加待收利息基本信息
	 * @param InterestDto
	 * @return
	 */
	public int addLendingInterest(LendingInterestDto InterestDto);


	/**
	 * 完善信息
	 * @param InterestDto
	 */
	public int updateInterest(LendingInterestDto InterestDto);
	
    /**
     * 删除
     * @param InterestDto
     * @return
     */
    public int delectInterest(LendingInterestDto InterestDto);
}
