package com.anjbo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.bean.user.DeptDto;
import com.anjbo.bean.user.RoleDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.AuthorityService;
import com.anjbo.service.DeptService;
import com.anjbo.service.RoleService;
import com.anjbo.utils.CommonDataUtil;


@Controller
@RequestMapping("/credit/user/role")
public class RoleController extends BaseController{	
	@Resource private RoleService roleService;	
	@Resource private AuthorityService authorityService;
	@Autowired private DeptService deptService;
	
	@ResponseBody
	@RequestMapping(value = "/list") 
	public RespPageData<RoleDto> ignList(HttpServletRequest request, @RequestBody RoleDto roleDto){
		if(null==roleDto || roleDto.getAgencyId()<1){
			RespPageData<RoleDto> resp = new RespPageData<RoleDto>();
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(RespStatusEnum.PARAMETER_ERROR.getMsg());
			return resp;
		}
		return this.list(request, roleDto);
	}
	@ResponseBody
	@RequestMapping(value = "/v/list") 
	public RespPageData<RoleDto> list(HttpServletRequest request,@RequestBody RoleDto roleDto){
		RespPageData<RoleDto> resp = new RespPageData<RoleDto>();
		try {
			roleDto.setAgencyId(roleDto.getAgencyId()>0 ? roleDto.getAgencyId() : getUserDto(request).getAgencyId());
			List<RoleDto> list = roleService.selectRoleList(roleDto);
			if(roleDto.getCount() != 0){
				List<UserDto> userList = CommonDataUtil.getUserListByCityCodeOrAgencyId("",0);
				for (RoleDto role : list) {
					int count = 0;
					for (UserDto userDto : userList) {
						if(userDto.getRoleId() == role.getId()){
							count++;
						}
					}
					role.setCount(count);
				}
			}
			resp.setRows(list);
			resp.setTotal(roleService.selectRoleCount(roleDto));
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}
	

	@ResponseBody
	@RequestMapping(value = "/v/updateRole")
	public RespStatus updateRole(HttpServletRequest request,@RequestBody RoleDto roleDto){
		RespStatus resp = new RespStatus();
		resp.setCode(RespStatusEnum.FAIL.getCode());
		resp.setMsg(RespStatusEnum.FAIL.getMsg());
		try {
			if(roleService.updateRole(roleDto)>0){
				resp.setCode(RespStatusEnum.SUCCESS.getCode());
				resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}


	@ResponseBody
	@RequestMapping(value = "/v/insertRole")
	public RespStatus insertRole(HttpServletRequest request,@RequestBody RoleDto roleDto){
		RespStatus resp = new RespStatus();
		resp.setCode(RespStatusEnum.FAIL.getCode());
		resp.setMsg(RespStatusEnum.FAIL.getMsg());
		try {
			UserDto userDto = getUserDto(request);
			roleDto.setAgencyId(userDto.getAgencyId());
			roleDto.setCreateUid(userDto.getUid());
			if(roleService.insertRole(roleDto)>0){
				//加入默认权限
				Map<String, Object> authorityMap = new HashMap<String, Object>();
				authorityMap.put("roleId", roleDto.getId());
				authorityMap.put("authorityId", "");
				authorityService.insertRoleAuthority(authorityMap);
				resp.setCode(RespStatusEnum.SUCCESS.getCode());
				resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}
	
	/**
	 * 新机构默认角色
	 * @Author KangLG<2017年11月15日>
	 * @param request
	 * @param agencyId 机构ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/v/batchInsert_{agencyId}")
	public RespStatus batchInsert(HttpServletRequest request,@PathVariable("agencyId")int agencyId, @RequestBody Map<String, Object> params){
		RespStatus resp = new RespStatus();
		resp.setCode(RespStatusEnum.FAIL.getCode());
		resp.setMsg(RespStatusEnum.FAIL.getMsg());
		try {
			roleService.insertByAgency(agencyId, null!=this.getUserDto(request) ? this.getUserDto(request).getUid() : "--");
			
			// 初始化部门
			DeptDto dto = new DeptDto();
			dto.setAgencyId(agencyId);
			if(deptService.selectDeptCount(dto) < 1){
				dto.setPid(0);
				dto.setName(MapUtils.getString(params, "agencyName", "顶级部门"));
				deptService.insert(dto);
			}		
			
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
	
}
