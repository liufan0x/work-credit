package com.anjbo.service;

import java.util.Map;

import com.anjbo.bean.element.DocumentsDto;
import com.anjbo.bean.element.DocumentsReturnDto;

public interface DocumentsReturnService {
	
	public DocumentsReturnDto detail(String orderNo);
	
	public int insert(DocumentsReturnDto obj);
	
	public int update(DocumentsReturnDto obj);

}
