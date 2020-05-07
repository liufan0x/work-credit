/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller.impl.element;

import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.anjbo.controller.api.UserApi;
import com.anjbo.controller.BaseController;
import com.anjbo.bean.UserDto;
import com.anjbo.bean.element.DocumentsDto;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.element.IDocumentsController;
import com.anjbo.service.element.DocumentsService;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 18:24:30
 * @version 1.0
 */
@RestController
public class DocumentsController extends BaseController implements IDocumentsController{

	@Resource private DocumentsService documentsService;
	
	@Resource private UserApi userApi;

	/**
	 * 保存
	 * @author lic 
	 */
	@Override
	public RespStatus processSave(@RequestBody DocumentsDto dto) {
		RespStatus resp = new RespStatus();
		try {
			UserDto userDto = userApi.getUserDto();
			dto.setCreateUid(userDto.getUid());
			dto.setUpdateUid(userDto.getUid());
			
			
			return RespHelper.setSuccessRespStatus(resp);
		}catch (Exception e) {
			logger.error("保存异常,参数："+dto.toString(), e);
			return RespHelper.failRespStatus();
		}
	}
	
	/**
	 * 提交
	 * @author lic 
	 */
	@Override
	public RespStatus processSubmit(@RequestBody DocumentsDto dto) {
		RespStatus resp = new RespStatus();
		try {
			UserDto userDto = userApi.getUserDto();
			dto.setCreateUid(userDto.getUid());
			dto.setUpdateUid(userDto.getUid());
			
			
			return RespHelper.setSuccessRespStatus(resp);
		}catch (Exception e) {
			logger.error("提交异常,参数："+dto.toString(), e);
			return RespHelper.failRespStatus();
		}
	}
	
	/**
	 * 详情
	 * @author lic 
	 */
	@Override
	public RespDataObject<DocumentsDto> processDetails(@RequestBody DocumentsDto dto) {
		RespDataObject<DocumentsDto> resp = new RespDataObject<DocumentsDto>();
		try {
			
			return RespHelper.setSuccessDataObject(resp,dto);
		}catch (Exception e) {
			logger.error("详情异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, null, RespStatusEnum.FAIL.getMsg());
		}
	}
		
}