package com.anjbo.service;

import com.anjbo.bean.risk.EnquiryDto;

import java.util.List;
import java.util.Map;

public interface EnquiryService {

	public int insert(EnquiryDto dto);
	
	public int insertByMap(Map<String,Object> map);
	
	public List<EnquiryDto> listEnquiry(String orderNo);
	/**
	 * 根据订单orderNo和enquiryId更新
	 * @param map
	 * @return
	 */
	public int update(Map<String,Object> map);
	/**
	 * 根据订单orderNo和enquiryId更新
	 * @param dto
	 * @return
	 */
	public int update(EnquiryDto dto);
	
	public int delete(EnquiryDto obj);
	
	public int updateByOrderNo(EnquiryDto obj);

	public List<EnquiryDto> detailByOrderNo(String orderNo);
}
