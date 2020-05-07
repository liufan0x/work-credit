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

import com.anjbo.controller.api.UserApi;
import com.anjbo.controller.BaseController;
import com.anjbo.controller.IAdministrationDivideController;
import com.anjbo.bean.AdministrationDivideDto;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;

import com.anjbo.service.AdministrationDivideService;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-06-27 21:54:08
 * @version 1.0
 */
@RestController
public class AdministrationDivideController extends BaseController implements IAdministrationDivideController{

	@Resource private AdministrationDivideService administrationDivideService;
	
	@Resource private UserApi userApi;

	/**
	 * 分页查询
	 * @author Generator 
	 */
	@Override
	public RespPageData<AdministrationDivideDto> page(@RequestBody AdministrationDivideDto dto){
		RespPageData<AdministrationDivideDto> resp = new RespPageData<AdministrationDivideDto>();
		try {
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
			resp.setRows(administrationDivideService.search(dto));
			resp.setTotal(administrationDivideService.count(dto));
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
	public RespData<AdministrationDivideDto> search(@RequestBody AdministrationDivideDto dto){ 
		RespData<AdministrationDivideDto> resp = new RespData<AdministrationDivideDto>();
		try {
			return RespHelper.setSuccessData(resp, administrationDivideService.search(dto));
		}catch (Exception e) {
			logger.error("查询异常,参数："+dto.toString(), e);
			return RespHelper.setFailData(resp, new ArrayList<AdministrationDivideDto>(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 查找
	 * @author Generator 
	 */
	@Override
	public RespDataObject<AdministrationDivideDto> find(@RequestBody AdministrationDivideDto dto){ 
		RespDataObject<AdministrationDivideDto> resp = new RespDataObject<AdministrationDivideDto>();
		try {
			return RespHelper.setSuccessDataObject(resp, administrationDivideService.find(dto));
		}catch (Exception e) {
			logger.error("查找异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new AdministrationDivideDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 新增
	 * @author Generator 
	 */
	@Override
	public RespDataObject<AdministrationDivideDto> add(@RequestBody AdministrationDivideDto dto){ 
		RespDataObject<AdministrationDivideDto> resp = new RespDataObject<AdministrationDivideDto>();
		try {
			dto.setCreateUid(userApi.getUserDto().getUid());
			return RespHelper.setSuccessDataObject(resp, administrationDivideService.insert(dto));
		}catch (Exception e) {
			logger.error("新增异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new AdministrationDivideDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 编辑
	 * @author Generator 
	 */
	@Override
	public RespStatus edit(@RequestBody AdministrationDivideDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			dto.setUpdateUid(userApi.getUserDto().getUid());
			administrationDivideService.update(dto);
			return RespHelper.setSuccessRespStatus(resp);
		}catch (Exception e) {
			logger.error("编辑异常,参数："+dto.toString(), e);
			return RespHelper.failRespStatus();
		}
	}

	/**
	 * 删除
	 * @author Generator 
	 */
	@Override
	public RespStatus delete(@RequestBody AdministrationDivideDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			administrationDivideService.delete(dto);
			return RespHelper.setSuccessRespStatus(resp);
		}catch (Exception e) {
			logger.error("删除异常,参数："+dto.toString(), e);
			return RespHelper.failRespStatus();
		}
	}
		
}