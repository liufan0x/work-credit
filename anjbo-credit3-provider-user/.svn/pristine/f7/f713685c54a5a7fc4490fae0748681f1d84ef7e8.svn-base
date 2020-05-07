/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller.impl;

import java.util.ArrayList;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.anjbo.controller.BaseController;
import com.anjbo.bean.UserAuthorityDto;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.IUserAuthorityController;
import com.anjbo.service.UserAuthorityService;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-05-09 12:25:15
 * @version 1.0
 */
@RestController
public class UserAuthorityController extends BaseController implements IUserAuthorityController{

	@Resource private UserAuthorityService userAuthorityService;

	/**
	 * 分页查询
	 * @author Generator 
	 */
	@Override
	public RespPageData<UserAuthorityDto> page(@RequestBody UserAuthorityDto dto){
		RespPageData<UserAuthorityDto> resp = new RespPageData<UserAuthorityDto>();
		try {
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
			resp.setRows(userAuthorityService.search(dto));
			resp.setTotal(userAuthorityService.count(dto));
		}catch (Exception e) {
			logger.error("分页异常,参数："+dto.toString(), e);
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}


	/**
	 * 查询
	 * @author Generator 
	 */
	@Override
	public RespData<UserAuthorityDto> search(@RequestBody UserAuthorityDto dto){ 
		RespData<UserAuthorityDto> resp = new RespData<UserAuthorityDto>();
		try {
			return RespHelper.setSuccessData(resp, userAuthorityService.search(dto));
		}catch (Exception e) {
			logger.error("查询异常,参数："+dto.toString(), e);
			return RespHelper.setFailData(resp, new ArrayList<UserAuthorityDto>(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 查找
	 * @author Generator 
	 */
	@Override
	public RespDataObject<UserAuthorityDto> find(@RequestBody UserAuthorityDto dto){ 
		RespDataObject<UserAuthorityDto> resp = new RespDataObject<UserAuthorityDto>();
		try {
			return RespHelper.setSuccessDataObject(resp, userAuthorityService.find(dto));
		}catch (Exception e) {
			logger.error("查找异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new UserAuthorityDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 新增
	 * @author Generator 
	 */
	@Override
	public RespDataObject<UserAuthorityDto> add(@RequestBody UserAuthorityDto dto){ 
		RespDataObject<UserAuthorityDto> resp = new RespDataObject<UserAuthorityDto>();
		try {
			return RespHelper.setSuccessDataObject(resp, userAuthorityService.insert(dto));
		}catch (Exception e) {
			logger.error("新增异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new UserAuthorityDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 编辑
	 * @author Generator 
	 */
	@Override
	public RespStatus edit(@RequestBody UserAuthorityDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			userAuthorityService.update(dto);
			return RespHelper.setSuccessRespStatus(resp);
		}catch (Exception e) {
			logger.error("新增异常,参数："+dto.toString(), e);
			return RespHelper.failRespStatus();
		}
	}
		
}