/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.anjbo.controller.api.UserApi;
import com.anjbo.controller.BaseController;
import com.anjbo.bean.AuthorityDto;
import com.anjbo.bean.ResourceDto;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.IResourceController;
import com.anjbo.service.AuthorityService;
import com.anjbo.service.ResourceService;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-05-15 09:25:28
 * @version 1.0
 */
@RestController
public class ResourceController extends BaseController implements IResourceController{

	@Resource private ResourceService resourceService;
	
	@Resource private AuthorityService authorityService;
	
	@Resource private UserApi userApi;

	/**
	 * 分页查询
	 * @author Generator 
	 */
	@Override
	public RespPageData<ResourceDto> page(@RequestBody ResourceDto dto){
		RespPageData<ResourceDto> resp = new RespPageData<ResourceDto>();
		try {
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
			resp.setRows(resourceService.search(dto));
			resp.setTotal(resourceService.count(dto));
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
	public RespData<ResourceDto> search(@RequestBody ResourceDto dto){ 
		RespData<ResourceDto> resp = new RespData<ResourceDto>();
		try {
			return RespHelper.setSuccessData(resp, resourceService.search(dto));
		}catch (Exception e) {
			logger.error("查询异常,参数："+dto.toString(), e);
			return RespHelper.setFailData(resp, new ArrayList<ResourceDto>(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 查找
	 * @author Generator 
	 */
	@Override
	public RespDataObject<ResourceDto> find(@RequestBody ResourceDto dto){ 
		RespDataObject<ResourceDto> resp = new RespDataObject<ResourceDto>();
		try {
			return RespHelper.setSuccessDataObject(resp, resourceService.find(dto));
		}catch (Exception e) {
			logger.error("查找异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new ResourceDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 新增
	 * @author Generator 
	 */
	@Override
	public RespDataObject<ResourceDto> add(@RequestBody ResourceDto dto){ 
		RespDataObject<ResourceDto> resp = new RespDataObject<ResourceDto>();
		try {
			dto.setCreateUid(userApi.getUserDto().getUid());
			return RespHelper.setSuccessDataObject(resp, resourceService.insert(dto));
		}catch (Exception e) {
			logger.error("新增异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new ResourceDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 编辑
	 * @author Generator 
	 */
	@Override
	public RespStatus edit(@RequestBody ResourceDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			dto.setUpdateUid(userApi.getUserDto().getUid());
			resourceService.update(dto);
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
	public RespStatus delete(@RequestBody ResourceDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			resourceService.delete(dto);
			return RespHelper.setSuccessRespStatus(resp);
		}catch (Exception e) {
			logger.error("删除异常,参数："+dto.toString(), e);
			return RespHelper.failRespStatus();
		}
	}
	
	@Override
	public RespData<ResourceDto> selectResourceAuthority() {
		RespData<ResourceDto> resp = new RespData<ResourceDto>();
		try {
			List<ResourceDto> resourceList = resourceService.search(null);
			List<AuthorityDto> authorityList = authorityService.search(null);
			for (ResourceDto resourceMap : resourceList) {
				List<AuthorityDto> list = new ArrayList<AuthorityDto>();
				for (AuthorityDto authorityMap : authorityList) {
					if(authorityMap.getResourceId() != null){
						if(resourceMap.getId().equals(authorityMap.getResourceId())){
							list.add(authorityMap);
						}
					}
				}
				if("看单权限配置".equals(resourceMap.getResourceName())){
					list.add(new AuthorityDto());
				}
				resourceMap.setAuthorityList(list);
			}
			RespHelper.setSuccessData(resp, resourceList);
		} catch (Exception e) {
			logger.error("查看权限资源异常",e);
			RespHelper.setFailData(resp, null, RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}
}