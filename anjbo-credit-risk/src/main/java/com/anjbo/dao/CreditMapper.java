package com.anjbo.dao;

import java.util.List;
import java.util.Map;

import com.anjbo.bean.risk.CreditDto;

public interface CreditMapper {

	public CreditDto detail(String orderNo);
	
	public int insertCredit(CreditDto dto);
	
	public int updateCredit(CreditDto dto);
	
	public int updateNull(CreditDto dto);
	
	public List<Map<String,Object>> selectCreditLogAsc(String orderNo);
	
	public List<Map<String,Object>> selectCreditLogDesc(String orderNo);
	
	public int insertCreditLog(Map<String,Object> map);
	
	public int insertCreditLogBatch(List<Map<String,Object>> list);
	
	public int updateCreditLog(Map<String,Object> map);
	
}
