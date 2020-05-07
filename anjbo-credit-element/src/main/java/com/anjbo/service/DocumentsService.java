package com.anjbo.service;

import java.util.Map;

import com.anjbo.bean.element.DocumentsDto;
import com.anjbo.bean.element.ForeclosureTypeDto;
import com.anjbo.bean.element.PaymentTypeDto;

public interface DocumentsService {

	
	public  DocumentsDto detail(String orderNo);
	
	public int insert(DocumentsDto obj);
	
	public int insert(Map<String,Object> map);
	
	public int update(Map<String,Object> map);
	
	public int update(DocumentsDto obj);
	
	public Map<String,Object> detailElementAll(String orderNo);
	/**
	 * 新增要件特批图片
	 * @param obj
	 * @return 要件特批图片集合
	 */
	public DocumentsDto addImg(DocumentsDto obj);
	/**
	 * 删除要件特批图片
	 * @param obj
	 * @return 
	 */
	public DocumentsDto delImg(DocumentsDto obj);
	
	public int updateForeclosureType(DocumentsDto doc);
	
	public int updatePaymentType(DocumentsDto doc);
	
}
