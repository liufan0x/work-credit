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
import com.anjbo.bean.RoleAuthorityDto;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.IRoleAuthorityController;
import com.anjbo.service.RoleAuthorityService;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-05-09 12:25:15
 * @version 1.0
 */
@RestController
public class RoleAuthorityController extends BaseController implements IRoleAuthorityController{

	@Resource private RoleAuthorityService roleAuthorityService;

	/**
	 * 分页查询
	 * @author Generator 
	 */
	@Override
	public RespPageData<RoleAuthorityDto> page(@RequestBody RoleAuthorityDto dto){
		RespPageData<RoleAuthorityDto> resp = new RespPageData<RoleAuthorityDto>();
		try {
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
			resp.setRows(roleAuthorityService.search(dto));
			resp.setTotal(roleAuthorityService.count(dto));
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
	public RespData<RoleAuthorityDto> search(@RequestBody RoleAuthorityDto dto){ 
		RespData<RoleAuthorityDto> resp = new RespData<RoleAuthorityDto>();
		try {
			return RespHelper.setSuccessData(resp, roleAuthorityService.search(dto));
		}catch (Exception e) {
			logger.error("查询异常,参数："+dto.toString(), e);
			return RespHelper.setFailData(resp, new ArrayList<RoleAuthorityDto>(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 查找
	 * @author Generator 
	 */
	@Override
	public RespDataObject<RoleAuthorityDto> find(@RequestBody RoleAuthorityDto dto){ 
		RespDataObject<RoleAuthorityDto> resp = new RespDataObject<RoleAuthorityDto>();
		try {
			return RespHelper.setSuccessDataObject(resp, roleAuthorityService.find(dto));
		}catch (Exception e) {
			logger.error("查找异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new RoleAuthorityDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 新增
	 * @author Generator 
	 */
	@Override
	public RespDataObject<RoleAuthorityDto> add(@RequestBody RoleAuthorityDto dto){ 
		RespDataObject<RoleAuthorityDto> resp = new RespDataObject<RoleAuthorityDto>();
		try {
			return RespHelper.setSuccessDataObject(resp, roleAuthorityService.insert(dto));
		}catch (Exception e) {
			logger.error("新增异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new RoleAuthorityDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 编辑
	 * @author Generator 
	 */
	@Override
	public RespStatus edit(@RequestBody RoleAuthorityDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			roleAuthorityService.update(dto);
			return RespHelper.setSuccessRespStatus(resp);
		}catch (Exception e) {
			logger.error("新增异常,参数："+dto.toString(), e);
			return RespHelper.failRespStatus();
		}
	}
		
}