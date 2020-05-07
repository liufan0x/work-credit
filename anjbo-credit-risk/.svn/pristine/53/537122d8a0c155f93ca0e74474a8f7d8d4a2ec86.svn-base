package com.anjbo.dao;

import com.anjbo.bean.risk.EnquiryDto;

import java.util.List;
import java.util.Map;

public interface EnquiryMapper {

	public int insert(EnquiryDto dto);
	
	public int insertByMap(Map<String,Object> map);
	
	public List<EnquiryDto> listEnquiry(String orderNo);
	
	public EnquiryDto detail(Map<String,Object> map);
	
	public EnquiryDto detailById(int id);
	
	public int updateByMap(Map<String,Object> map);
	
	public int update(EnquiryDto dto);
	
	public int updateById(Map<String,Object> map);
	
	public int delete(EnquiryDto obj);
	
	public int updateByOrderNo(EnquiryDto obj);
	
	List<EnquiryDto> detailByOrderNo(String orderNo);
	
	EnquiryDto detailByOrderNoLimitOne(String orderNo);
	
	public int updateByOldEnquiryId(Map<String,Object> map);
	
}
