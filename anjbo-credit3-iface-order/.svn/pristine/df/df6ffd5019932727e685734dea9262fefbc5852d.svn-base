/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller.element;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.anjbo.bean.element.DocumentsReturnDto;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatus;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 18:24:30
 * @version 1.0
 */
@Api(value = "要件退还相关")
@RequestMapping("/documentsReturn/v")
public interface IDocumentsReturnController {

	@ApiOperation(value = "要件退还提交", httpMethod = "POST" ,response = RespStatus.class)
	@RequestMapping(value = "processSubmit", method= {RequestMethod.POST})
	public abstract RespStatus processSubmit(@RequestBody DocumentsReturnDto dto);
	
	@ApiOperation(value = "要件退还详情", httpMethod = "POST" ,response = DocumentsReturnDto.class)
	@RequestMapping(value = "processDetails", method= {RequestMethod.POST})
	public abstract RespDataObject<DocumentsReturnDto> processDetails(@RequestBody DocumentsReturnDto dto);
		
}