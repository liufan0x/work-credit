package com.anjbo.service;

import com.anjbo.bean.finance.RebateDto;

public interface RebateService {

	public RebateDto findByAll(String orderNO);
	public int insert(RebateDto rebateDto);
	public int delete(String orderNo);
}
