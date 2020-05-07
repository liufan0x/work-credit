package com.anjbo.service;

import com.anjbo.bean.product.DistributionMemberDto;

public interface DistributionMemberService {
	/**
	 * 查询详情
	 * @param dto
	 * @return
	 */
	public DistributionMemberDto selectDistributionMember(DistributionMemberDto dto);
	
	/**
	 * 添加信息
	 * @param dto
	 * @return
	 */
	public int addDistributionMember(DistributionMemberDto dto);
	public int updateDistributionMember(DistributionMemberDto dto);
}
