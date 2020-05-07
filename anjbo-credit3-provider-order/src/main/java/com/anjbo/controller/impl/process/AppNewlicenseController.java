/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller.impl.process;

import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.anjbo.controller.api.UserApi;
import com.anjbo.controller.BaseController;
import com.anjbo.bean.UserDto;
import com.anjbo.bean.process.AppNewlicenseDto;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.process.IAppNewlicenseController;
import com.anjbo.service.process.AppNewlicenseService;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 17:23:34
 * @version 1.0
 */
@RestController
public class AppNewlicenseController extends BaseController implements IAppNewlicenseController{

	@Resource private AppNewlicenseService appNewlicenseService;
	
	@Resource private UserApi userApi;

	/**
	 * 提交
	 * @author lic 
	 */
	@Override
	public RespStatus processSubmit(@RequestBody AppNewlicenseDto dto) {
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
	public RespDataObject<AppNewlicenseDto> processDetails(@RequestBody AppNewlicenseDto dto) {
		RespDataObject<AppNewlicenseDto> resp = new RespDataObject<AppNewlicenseDto>();
		try {
			
			return RespHelper.setSuccessDataObject(resp,dto);
		}catch (Exception e) {
			logger.error("详情异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, null, RespStatusEnum.FAIL.getMsg());
		}
	}
		
}