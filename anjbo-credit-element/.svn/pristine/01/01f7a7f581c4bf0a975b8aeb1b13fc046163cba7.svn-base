package com.anjbo.dao;

import java.util.Map;

import com.anjbo.bean.element.DocumentsDto;
import com.anjbo.bean.element.ForeclosureTypeDto;
import com.anjbo.bean.element.PaymentTypeDto;


public interface DocumentsMapper {
  
	public DocumentsDto detail(String orderNo);
	
	public int insert(DocumentsDto obj);
	
	public int insertByMap(Map<String,Object> map);
	
	public int update(DocumentsDto obj);
	
	public int updateByMap(Map<String,Object> map);
	
	public int updateForeclosureType(ForeclosureTypeDto obj);
	
	public int updateForeclosureTypeByMap(Map<String,Object> map);
	
	public int updatePaymentType(PaymentTypeDto obj);
	
	public int updatePaymentTypeByMap(Map<String,Object> map);
	
	public int insertPaymentType(PaymentTypeDto obj);
	
	public int insertForeclosureType(ForeclosureTypeDto obj);
	
	public ForeclosureTypeDto detailForeclosureType(String orderNo);
	
	public PaymentTypeDto detailPaymentType(String orderNo);
	
	public int insertPaymentTypeByMap(Map<String,Object> map);
	
	public int insertForeclosureTypeByMap(Map<String,Object> map);
	
	public int updateDocumentImg(DocumentsDto obj);
	
	public int updateNotValidate(DocumentsDto obj);
	
	public int updatePaymentTypeNotValidate(PaymentTypeDto obj);
	
	public int updateForeclosureTypeNotValidate(ForeclosureTypeDto obj);
}