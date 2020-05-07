package com.anjbo.dao;

import java.util.List;
import java.util.Map;

import com.anjbo.bean.customer.FundAdminDto;


public interface FundAdminMapper {
	
	public List<FundAdminDto> list(FundAdminDto obj);
	
	public FundAdminDto detail(FundAdminDto obj);
	
	public List<Map<String,Object>> selectFundsByProductId(Map<String,Object> map);
	
}