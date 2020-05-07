package com.anjbo.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.bean.customer.AgencyDto;
import com.anjbo.bean.product.ProductProcessDto;
import com.anjbo.bean.user.AuthorityDto;
import com.anjbo.bean.user.DeptDto;
import com.anjbo.bean.user.RoleDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.Constants;
import com.anjbo.common.Enums;
import com.anjbo.common.RedisOperator;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.AuthorityService;
import com.anjbo.service.DeptService;
import com.anjbo.service.RoleService;
import com.anjbo.service.UserService;
import com.anjbo.utils.CommonDataUtil;

@Controller
@RequestMapping("/credit/user/userUtil/v")
public class UserUtilController extends BaseController{

	private Log log = LogFactory.getLog(getClass());

	@Resource
	private AuthorityService authorityService;

	@Resource
	private UserService userService;
	
	@Resource
	private RoleService roleService;

	@Resource
	private DeptService deptService;
	
	/**
	 * 初始化用户
	 * @param request
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/initUserList")
	public RespStatus initUserList() {
		try {
			List<UserDto> userList =  userList().getData();
			RedisOperator.set("userList", userList);
			return RespHelper.setSuccessRespStatus(new RespStatus());
		} catch (Exception e) {
			RedisOperator.set("userList", userList().getData());
			log.error("初始化用户异常",e);
			return RespHelper.setFailRespStatus(new RespStatus(),RespStatusEnum.FAIL.getMsg());
		}
	}
	
	/**
	 * 获取当前登陆人
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getUser")
	public RespDataObject<UserDto> getUser(HttpServletRequest request) {
		RespDataObject<UserDto> resp = new RespDataObject<UserDto>();
		try {
			RespHelper.setSuccessDataObject(resp, getUserDto(request));
		} catch (Exception e) {
			log.error("获取登陆用户异常",e);
			RespHelper.setFailDataObject(resp, null, RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}
	
	/**
	 * 用户列表
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/userList")
	public RespData<UserDto> userList(){
		List<UserDto> userList = userService.selectUserList(null);
		List<RoleDto> roleList = roleService.selectRoleList(null);
		List<DeptDto> deptList = deptService.selectDeptList(null);
		List<AgencyDto> agencyList = httpUtil.getList(Constants.LINK_CREDIT, "/credit/customer/agency/v/agencyList", null, AgencyDto.class);
		if(agencyList == null){
			log.error("获取机构列表异常");
		}
		List<Map<String, Object>> userAuthList = authorityService.selectUserAuthorityList();
		try {
			for (UserDto userDto : userList) {
				userDto.setAuthIds(new ArrayList<String>());
				if(roleList != null){
					for (RoleDto roleDto : roleList) {
						if(userDto.getRoleId() == roleDto.getId()){
							userDto.setRoleName(roleDto.getName());
							break;
						}
					}
				}
				if(deptList != null && StringUtils.isNotEmpty(userDto.getDeptIdArray())){
					List<String> userDeptList = Arrays.asList(userDto.getDeptIdArray().split(","));
					for (DeptDto deptDto : deptList) {
						if(userDeptList.contains(deptDto.getId()+"")){
							if(StringUtils.isEmpty(userDto.getDeptName())){
								userDto.setDeptName(deptDto.getName());
							}else{
								userDto.setDeptName(userDto.getDeptName() + "," +deptDto.getName());
							}
						}
					}
				}
				if(agencyList != null){
					for (AgencyDto agencyDto : agencyList) {
						if(agencyDto.getId() == userDto.getAgencyId()){
							userDto.setAgencyCode(null!=agencyDto.getAgencyCode() ? agencyDto.getAgencyCode() : 0);
							userDto.setAgencyName(agencyDto.getName());
							userDto.setAgencyChanlManUid(null!=agencyDto.getChanlMan() ? agencyDto.getChanlMan() : "");
							break;
						}
					}
				}
				if(userAuthList != null){
					for (Map<String, Object> map : userAuthList) {
						if(MapUtils.getString(map, "uid").equals(userDto.getUid())){
							userDto.setAuthIds(Arrays.asList(MapUtils.getString(map, "authorityId","").split(",")));
						}
					}
				}
			}
			Map<Object, Object> uidMap = new HashMap<Object, Object>();
			Map<Object, Object> modelMap = new HashMap<Object, Object>();
			for (UserDto userDto : userList) {
				uidMap.put(userDto.getUid(), userDto);
				modelMap.put(userDto.getUid(), userDto);
			}
			RedisOperator.putToMap("userListMapByUid",uidMap);
			RedisOperator.putToMap("userListMapByMobile",uidMap);
		} catch (Exception e) {
			log.error("初始化用户列表异常",e);
		}
		return RespHelper.setSuccessData(new RespData<UserDto>(), userList);
	}
	
	/**
	 * 选择人员
	 * @param request
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/choicePersonnel")
	public RespData<Map<String, String>> choicePersonnel(HttpServletRequest request, @RequestBody Map<String, String> params) {
		RespData<Map<String, String>> resp = new RespData<Map<String, String>>();
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		try {
			String type = MapUtils.getString(params, "type","authName");
			String cityCode = MapUtils.getString(params, "cityCode");
			int agencyId = MapUtils.getIntValue(params, "agencyId", getUserDto(request).getAgencyId());
			String choicePersonnel = MapUtils.getString(params, "choicePersonnel");
			int productId = MapUtils.getIntValue(params, "productId",440301);
			List<UserDto> userList = null;
			AuthorityDto authorityDto = new AuthorityDto();
			if(type.equals("authName")){
				userList = CommonDataUtil.getUserListByCityCodeOrAgencyId("",agencyId);
				List<ProductProcessDto> productProcessDtoList = getProductProcessDto(productId);
				for (ProductProcessDto productProcessDto : productProcessDtoList) {
					if(productProcessDto.getProcessName().equals(choicePersonnel)){
						authorityDto.setProcessId(productProcessDto.getId());
						authorityDto.setName(choicePersonnel+"[A]");
						authorityDto = authorityService.selectAuthorityByProductId(authorityDto);
					}
				}
				if(authorityDto == null){
					RespHelper.setFailData(resp, new ArrayList<Map<String, String>>(), "无数据");
					return resp;
				}
			}else{
				userList = CommonDataUtil.getUserListByCityCodeOrAgencyId(cityCode,agencyId);
			}
			for (UserDto userDto : userList) {
				if(userDto.getAccount().equals("admin") || 0!=userDto.getIsEnable()){
					continue;
				}
				if(type.equals("roleName")){
					if(StringUtils.isNotEmpty(userDto.getRoleName())){
						if(userDto.getRoleName().equals(choicePersonnel)){
							Map<String, String> map = new HashMap<String, String>();
							map.put("id", userDto.getUid());
							map.put("name", userDto.getName());
							list.add(map);
						}
					}
				}else{
					if(userDto.getAuthIds() != null){
						if(userDto.getAuthIds().contains(authorityDto.getId()+"")){
							Map<String, String> map = new HashMap<String, String>();
							map.put("id", userDto.getUid());
							map.put("name", userDto.getName());
							list.add(map);
						}
					}
				}
			}
			RespHelper.setSuccessData(resp, list);
		} catch (Exception e) {
			log.error("选择人员失败"+MapUtils.getString(params, "type","authName"),e);
			RespHelper.setFailData(resp, null, RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}

	/**
	 * 选择渠道经理
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findChannelManager")
	public RespData<Map<String, String>> findChannelManager(HttpServletRequest request) {
		RespData<Map<String, String>> resp = new RespData<Map<String, String>>();
		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put("type", "roleName");
			params.put("choicePersonnel",Enums.RoleEnum.CHANNEL_MANAGER.getName());
			params.put("agencyId", Enums.AgencyEnum.KUAIGE.getAgencyId()+"");
			return choicePersonnel(request, params);
		} catch (Exception e) {
			log.error("查询渠道经理失败",e);
			RespHelper.setFailData(resp, null, "查询渠道经理失败");
		}
		return resp;
	}
	
}
