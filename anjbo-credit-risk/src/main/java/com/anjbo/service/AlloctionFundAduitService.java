package com.anjbo.service;

import java.util.List;

import com.anjbo.bean.risk.AllocationFundAduitDto;

public interface AlloctionFundAduitService {

	
	public AllocationFundAduitDto selectFundAudit(String orderNo);
	public int addFundAuidt(AllocationFundAduitDto aduitDto);
	public int updateFundAuidt(AllocationFundAduitDto aduitDto);
	public int updataStatus(AllocationFundAduitDto aduitDto);
	//定时器用
	public List<AllocationFundAduitDto> selectAll();
	
	public int deleteByOrderNo(String orderNo);
	
}
