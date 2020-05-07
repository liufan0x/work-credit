/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service.impl.element;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anjbo.bean.element.DocumentsReturnDto;
import com.anjbo.dao.element.DocumentsReturnMapper;
import com.anjbo.service.element.DocumentsReturnService;
import com.anjbo.service.impl.BaseServiceImpl;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 18:24:30
 * @version 1.0
 */
@Service
public class DocumentsReturnServiceImpl extends BaseServiceImpl<DocumentsReturnDto>  implements DocumentsReturnService {
	@Autowired private DocumentsReturnMapper documentsReturnMapper;

}
