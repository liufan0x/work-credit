package com.anjbo.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anjbo.bean.element.DocumentsDto;
import com.anjbo.bean.element.DocumentsReturnDto;
import com.anjbo.dao.DocumentsReturnMapper;
import com.anjbo.service.DocumentsReturnService;

@Transactional
@Service
public class DocumentsReturnServiceImpl implements DocumentsReturnService{

	@Resource
	private DocumentsReturnMapper documentsReturnMapper;
	
	public DocumentsReturnDto detail(String orderNo){
		return documentsReturnMapper.detail(orderNo);
	}
	
	public int insert(DocumentsReturnDto obj){
		return documentsReturnMapper.insert(obj);
	}
	
	public int update(DocumentsReturnDto obj){
		int success = 0;
		DocumentsReturnDto tmp = documentsReturnMapper.detail(obj.getOrderNo());
		if(null==tmp){
			documentsReturnMapper.insert(obj);
		} else {
			documentsReturnMapper.update(obj);
		}
		return success;
	}
}
