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
import com.anjbo.controller.BaseUserController;
import com.anjbo.bean.RoleDto;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.IRoleController;
import com.anjbo.controller.impl.api.UserApiController;
import com.anjbo.service.RoleService;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-05-09 10:00:14
 * @version 1.0
 */
@RestController
public class RoleController extends BaseUserController implements IRoleController{

	@Resource private RoleService roleService;

	@Resource private UserApiController userApi;
	
	/**
	 * 分页查询
	 * @author Generator 
	 */
	@Override
	public RespPageData<RoleDto> page(@RequestBody RoleDto dto){
		RespPageData<RoleDto> resp = new RespPageData<RoleDto>();
		try {
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
			dto.setAgencyId(userApi.getUserDto().getAgencyId());
			resp.setRows(roleService.search(dto));
			resp.setTotal(roleService.count(dto));
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
	public RespData<RoleDto> search(@RequestBody RoleDto dto){ 
		RespData<RoleDto> resp = new RespData<RoleDto>();
		try {
			return RespHelper.setSuccessData(resp, roleService.search(dto));
		}catch (Exception e) {
			logger.error("查询异常,参数："+dto.toString(), e);
			return RespHelper.setFailData(resp, new ArrayList<RoleDto>(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 查找
	 * @author Generator 
	 */
	@Override
	public RespDataObject<RoleDto> find(@RequestBody RoleDto dto){ 
		RespDataObject<RoleDto> resp = new RespDataObject<RoleDto>();
		try {
			return RespHelper.setSuccessDataObject(resp, roleService.find(dto));
		}catch (Exception e) {
			logger.error("查找异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new RoleDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 新增
	 * @author Generator 
	 */
	@Override
	public RespDataObject<RoleDto> add(@RequestBody RoleDto dto){ 
		RespDataObject<RoleDto> resp = new RespDataObject<RoleDto>();
		try {
			dto.setCreateUid(userApi.getUid());
			dto.setAgencyId(userApi.getUserDto().getAgencyId());
			return RespHelper.setSuccessDataObject(resp, roleService.insert(dto));
		}catch (Exception e) {
			logger.error("新增异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new RoleDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 编辑
	 * @author Generator 
	 */
	@Override
	public RespStatus edit(@RequestBody RoleDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			dto.setUpdateUid(userApi.getUid());
			roleService.update(dto);
			return RespHelper.setSuccessRespStatus(resp);
		}catch (Exception e) {
			logger.error("新增异常,参数："+dto.toString(), e);
			return RespHelper.failRespStatus();
		}
	}
		
}