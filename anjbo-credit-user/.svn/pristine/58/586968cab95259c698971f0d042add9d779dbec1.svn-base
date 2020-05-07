package com.anjbo.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.bean.common.DictDto;
import com.anjbo.bean.customer.AgencyDto;
import com.anjbo.bean.customer.CustomerFundDto;
import com.anjbo.bean.customer.vo.AgencyFull;
import com.anjbo.bean.product.ProductProcessDto;
import com.anjbo.bean.user.AuthorityDto;
import com.anjbo.bean.user.DeptDto;
import com.anjbo.bean.user.RoleDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.bean.user.vo.AppUser;
import com.anjbo.common.Constants;
import com.anjbo.common.Enums.UserUpdateFrom;
import com.anjbo.common.RedisKey;
import com.anjbo.common.RedisOperator;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.AuthorityService;
import com.anjbo.service.DeptService;
import com.anjbo.service.RoleService;
import com.anjbo.service.UserService;
import com.anjbo.utils.AmsUtil;
import com.anjbo.utils.CommonDataUtil;
import com.anjbo.utils.ConfigUtil;
import com.anjbo.utils.CookieUtil;
import com.anjbo.utils.IpUtil;
import com.anjbo.utils.MD5Utils;
import com.anjbo.utils.NumberUtil;
import com.anjbo.utils.PinYin4JUtil;
import com.anjbo.utils.StringUtil;
import com.anjbo.utils.UidUtil;
import com.anjbo.utils.regex.FieldRegex;

@Controller
@RequestMapping("/credit/user/base")
public class UserController extends BaseController{

	@Resource
	private UserService userService;

	@Resource
	private RoleService roleService;

	@Resource
	private DeptService deptService;

	@Resource
	private AuthorityService authorityService;
	
	/**
	 * 清空用户
	 * @param request
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/clearUserList")
	public RespStatus clearUserList(HttpServletRequest request) {
		try {
			List<UserDto> userList =  userList().getData();
			RedisOperator.set("userList", userList);
			return RespHelper.setSuccessRespStatus(new RespStatus());
		} catch (Exception e) {
			RedisOperator.set("userList", userList().getData());
			e.printStackTrace();
			return RespHelper.setFailRespStatus(new RespStatus(),RespStatusEnum.FAIL.getMsg());
		}
	}
	
	
	/**
	 * 登陆
	 * @param request
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/login")
	public RespDataObject<UserDto> login(HttpServletRequest request, @RequestBody Map<String, String> params) {
		try {
			RespDataObject<UserDto> resp = userService.login(request, params);
			if(RespStatusEnum.SUCCESS.getCode().equals(resp.getCode())){
				UserDto user = resp.getData();
				CustomerFundDto customerFund = userService.selectFundByUid(user.getUid());
				if(customerFund!=null){
					user.setFundId(customerFund.getId());
					resp.setData(user);
				}
			}
			return resp;
		} catch (Exception e) {
			e.printStackTrace();
			return new RespDataObject<UserDto>(null, RespStatusEnum.FAIL.getCode(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 退出
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/logout")
	public RespStatus logout(HttpServletRequest request) {
		try {
			HttpSession session = request.getSession(false);
			session.removeAttribute(Constants.LOGIN_USER_KEY);
			return new RespStatus(RespStatusEnum.SUCCESS.getCode(),RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			return new RespStatus(RespStatusEnum.FAIL.getCode(),RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * app登陆
	 * @param request
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/appLogin")
	public RespDataObject<UserDto> appLogin(HttpServletRequest request, @RequestBody Map<String, String> params) {
		return userService.appLogin(request, params);
	}

	/**
	 * app退出
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/appLogout")
	public RespStatus appLogout(HttpServletRequest request){
		RespStatus resp = new RespStatus();
		try {
			RedisOperator.delete(getUserDto(request).getUid());
		} catch (Exception e) {

		}
		RespHelper.setSuccessRespStatus(resp);
		return resp;
	}

	/**
	 * 获取当前登陆人
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/v/getUser")
	public RespDataObject<UserDto> getUser(HttpServletRequest request) {
		RespDataObject<UserDto> resp = new RespDataObject<UserDto>();
		try {
			RespHelper.setSuccessDataObject(resp, getUserDto(request));
		} catch (Exception e) {
			RespHelper.setFailDataObject(resp, null, RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}
		
	/**
	 * 获取机构管理员(测试账号)
	 * @Author KangLG<2017年11月14日>
	 * @param request
	 * @param agencyId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/v/getAgentAdmin_{agencyId}")
	public RespDataObject<UserDto> getAgentAdmin(HttpServletRequest request, @PathVariable("agencyId")int agencyId) {
		RespDataObject<UserDto> resp = new RespDataObject<UserDto>();
		try {
			UserDto dto = new UserDto();
			dto.setAgencyId(agencyId);
			dto.setAdmin(true);
			List<UserDto> list = userService.search(dto);
			RespHelper.setSuccessDataObject(resp, null!=list&&!list.isEmpty() ? list.get(0) : null);			
		} catch (Exception e) {
			RespHelper.setFailDataObject(resp, null, RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}
	
	@ResponseBody
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/v/userList")
	public RespData<UserDto> userList(){
		List<UserDto> userList = userService.selectUserList(null);
		List<RoleDto> roleList = roleService.selectRoleList(null);
		List<AgencyDto> agencyList = httpUtil.getList(Constants.LINK_CREDIT, "/credit/customer/agency/v/agencyList", null, AgencyDto.class);
		List<DeptDto> deptList = deptService.selectDeptList(null);
		List<Map<String, Object>> userAuthList = authorityService.selectUserAuthorityList();
		try {
			for (UserDto userDto : userList) {
				userDto.setRoleName("");
				userDto.setAgencyName("");
				userDto.setDeptName("");
				userDto.setAuthIds(new ArrayList<String>());
				for (RoleDto roleDto : roleList) {
					if(userDto.getRoleId() == roleDto.getId()){
						userDto.setRoleName(roleDto.getName());
						break;
					}
				}
				try {
					for (AgencyDto agencyDto : agencyList) {
						if(agencyDto.getId() == userDto.getAgencyId()){							
							userDto.setAgencyCode(null!=agencyDto.getAgencyCode() ? agencyDto.getAgencyCode() : 0);
							userDto.setAgencyName(agencyDto.getName());
							userDto.setAgencyChanlManUid(null!=agencyDto.getChanlMan() ? agencyDto.getChanlMan() : "");
							break;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				for (DeptDto deptDto : deptList) {
					if(deptDto.getId() == userDto.getDeptId()){
						userDto.setDeptName(deptDto.getName());
						break;
					}
				}
				for (Map<String, Object> map : userAuthList) {
					if(MapUtils.getString(map, "uid").equals(userDto.getUid())){
						userDto.setAuthIds(Arrays.asList(MapUtils.getString(map, "authorityId","").split(",")));
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
			this.logger.error("初始化用户列表异常",e);
			e.printStackTrace();
		}
		return new RespHelper().setSuccessData(new RespData<UserDto>(), userList);
	}
	
	@SuppressWarnings("static-access")
	@ResponseBody
	@RequestMapping(value = "/choiceDingtalkPersonnel")
	public RespData<UserDto> choiceDingtalkPersonnel(){
		List<UserDto> list = userService.searchByDingtalk(null);
		return new RespHelper().setSuccessData(new RespData<UserDto>(), list);
	}

	/**
	 * 选择人员
	 * @param request
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/v/choicePersonnel")
	public RespData<UserDto> choicePersonnel(HttpServletRequest request, @RequestBody Map<String, String> params) {
		RespData<UserDto> resp = new RespData<UserDto>();
		List<UserDto> list = new ArrayList<UserDto>();
		try {
			String type = MapUtils.getString(params, "type","authName");
			String cityCode = MapUtils.getString(params, "cityCode");
			int agencyId = 1;
			try {
				agencyId = MapUtils.getIntValue(params, "agencyId", this.getUserDto(request).getAgencyId());
			} catch (Exception e) {
				
			}
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
					RespHelper.setFailData(resp, new ArrayList<UserDto>(), "无数据");
				}
			}else{
				userList = CommonDataUtil.getUserListByCityCodeOrAgencyId(cityCode,agencyId);
			}
			for (UserDto userDto : userList) {
				if(userDto.getAccount().equals("admin") || userDto.getIsEnable() == 1){
					continue;
				}
				if(type.equals("roleName")){
					if(StringUtils.isNotEmpty(userDto.getRoleName())){
						if(userDto.getRoleName().equals(choicePersonnel)){
							list.add(userDto);
						}
					}
				}else{
					if(userDto.getAuthIds() != null){
						if(userDto.getAuthIds().contains(authorityDto.getId()+"")){
							list.add(userDto);
						}
					}
				}
			}
			RespHelper.setSuccessData(resp, list);
		} catch (Exception e) {
			e.printStackTrace();
			RespHelper.setFailData(resp, null, RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}


	/**
	 * 根据权限选择人员
	 * @param request
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/v/choicePersonnelByAuthName")
	public RespData<Map<String, String>> choicePersonnelByRoleName(HttpServletRequest request, @RequestBody Map<String, String> params) {
		RespData<Map<String, String>> resp = new RespData<Map<String, String>>();
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		try {
			String cityCode = MapUtils.getString(params, "cityCode");
			int agencyId = MapUtils.getIntValue(params, "agencyId",1);
			String choicePersonnel = MapUtils.getString(params, "choicePersonnel");
			int productId = NumberUtils.toInt(cityCode + MapUtils.getString(params, "productCode"));
			List<UserDto> userList = CommonDataUtil.getUserListByCityCodeOrAgencyId("", agencyId);
			AuthorityDto authorityDto = new AuthorityDto();
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
			for (UserDto userDto : userList) {
				if(userDto.getAccount().equals("admin") || 0!=userDto.getIsEnable()){
					continue;
				}
				if(userDto.getAuthIds() != null){
					if(userDto.getAuthIds().contains(authorityDto.getId()+"")){
						Map<String, String> map = new HashMap<String, String>();
						map.put("id", userDto.getUid());
						map.put("name", userDto.getName());
						list.add(map);
					}
				}
			}
			RespHelper.setSuccessData(resp, list);
		} catch (Exception e) {
			e.printStackTrace();
			RespHelper.setFailData(resp, null, RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}
	
	
	/**
	 * 根据权限选择人员
	 * @param request
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/v/findAcceptMemberName")
	public RespData<Map<String, String>> findAcceptMemberName(HttpServletRequest request, @RequestBody Map<String, String> params) {
		RespData<Map<String, String>> resp = new RespData<Map<String, String>>();
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		try {
			String cityCode = MapUtils.getString(params, "cityCode");
			int agencyId = MapUtils.getIntValue(params, "agencyId",getUserDto(request).getAgencyId());
			String choicePersonnel = MapUtils.getString(params, "choicePersonnel");
			int productId = NumberUtils.toInt(cityCode + MapUtils.getString(params, "productCode"));
			List<UserDto> userList = CommonDataUtil.getUserListByCityCodeOrAgencyId(cityCode,agencyId);
			AuthorityDto authorityDto = new AuthorityDto();
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
			}
			for (UserDto userDto : userList) {
				if(userDto.getAccount().equals("admin") || userDto.getIsEnable() == 1){
					continue;
				}
				if(userDto.getAuthIds() != null){
					if(userDto.getAuthIds().contains(authorityDto.getId()+"")){
						Map<String, String> map = new HashMap<String, String>();
						map.put("id", userDto.getUid());
						map.put("name", userDto.getName());
						list.add(map);
					}
				}
			}
			RespHelper.setSuccessData(resp, list);
		} catch (Exception e) {
			e.printStackTrace();
			RespHelper.setFailData(resp, null, RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}

	/**
	 * 
	 * @Author 未知<xxxx年xx月xx日>
	 * @Rewrite KangLG<2017年11月03日> 新增多部门迭代器deptNodeName(...)
	 * @param request
	 * @param userDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/v/list") 
	public RespPageData<UserDto> list(HttpServletRequest request,@RequestBody UserDto userDto){
		RespPageData<UserDto> resp = new RespPageData<UserDto>();
		try {
			UserDto ud = getUserDto(request);
			DeptDto dd = new DeptDto();
			userDto.setAgencyId(userDto.getAgencyId()>0 ? userDto.getAgencyId() : ud.getAgencyId());
			userDto.setUid(ud.getUid());
			userDto.setMyDeptId(ud.getDeptId());
			userDto.setIdentity(ud.getIdentity());
			dd.setAgencyId(ud.getAgencyId());
			List<DeptDto> deptList = deptService.selectDeptList(dd);
			if(userDto.getDeptId() > 0){
				Set<Integer> set = getAllDeptSet(deptList,userDto.getDeptId());
				String deptIds = set.toString();
				deptIds = deptIds.replace("[","").replace("]","");
				userDto.setDeptIds(deptIds);
				userDto.setDeptId(0);
			}			
			Map<String, DeptDto> mapDeptName = new HashMap<String, DeptDto>(); //部门ID、Name键值对
			for (DeptDto deptDto : deptList) {
				mapDeptName.put(String.valueOf(deptDto.getId()), deptDto);
			}
			Map<String, String> mapCity = new HashMap<String, String>();
			List<DictDto> lstCity = super.getDictDtoByType("cityList");
			for (DictDto city : lstCity) {
				mapCity.put(city.getCode(), city.getName());
			}
			
			List<UserDto> userList = userService.selectUserList(userDto);
			List<RoleDto> roleList = roleService.selectRoleList(null);
			for (UserDto user : userList) {
				user.setCityName(MapUtils.getString(mapCity, user.getCityCode(), null));
				user.setRoleName("");
				user.setDeptName("");
				for (RoleDto roleDto : roleList) {
					if(user.getRoleId() == roleDto.getId()){
						user.setRoleName(roleDto.getName());
						break;
					}
				}
				
				// 部门名加载	
				String userDeptIds = user.getDeptIdArray();
				if(null!=userDeptIds && userDeptIds.contains(",")){	//多部门		
					String[] deptIdArray = userDeptIds.split(",");
					StringBuffer deptIdSbf = new StringBuffer("");
					for (String _deptId : deptIdArray) {
						DeptDto objDept = mapDeptName.get(_deptId.trim()); 
						deptIdSbf.append("/" + deptNodeName(mapDeptName, objDept, ""));
					}
					user.setDeptName(deptIdSbf.toString().replaceFirst("/", "").replaceAll("深圳市快鸽互联网金融服务有限公司-", ""));
				}else{ 
					DeptDto objDept = mapDeptName.get(userDeptIds); 
					user.setDeptName(null!=objDept ? objDept.getName() : "");
				}				
			}
			resp.setRows(userList);
			resp.setTotal(userService.selectUserCount(userDto));
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}		
	private String deptNodeName(Map<String, DeptDto> mapDeptName, DeptDto objDept, String retNode){		
		if(null == objDept){
			return retNode;
		}else if(objDept.getPid()>1){
			DeptDto _objDept = mapDeptName.get(String.valueOf(objDept.getPid()));
			retNode = _objDept.getName() + "-"+ retNode;			
			return deptNodeName(mapDeptName, _objDept, retNode) + "-"+ objDept.getName();
		}	
		return objDept.getName();
	}
	
	/**
	 * 编辑机构测试账号(即机构管理员)
	 * @Author KangLG<2017年11月14日>
	 * @param userDto: [uid]agencyId机构ID(10资方机构), isEnable(0启用1禁用), name姓名, mobile电话, indateStart/indateEnd有效期
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/v/editAgentAdmin")
	public RespStatus editAgentAdmin(HttpServletRequest request,@RequestBody UserDto userDto){		
		RespStatus resp = new RespStatus();		
		try {	
			this.logger.info(String.format("机构测试员请求参数：UID=%s,agencyId=%s, isEnable=%s, name=%s, mobile=%s", userDto.getUid(), userDto.getAgencyId(), userDto.getIsEnable(), userDto.getName(), userDto.getMobile()));
			if(StringUtils.isEmpty(userDto.getMobile())){
				return RespHelper.setFailRespStatus(new RespStatus(), RespStatusEnum.PARAMETER_ERROR.getMsg()); 
			}else if(StringUtils.isNotEmpty(userDto.getIndateEnd()) && userDto.getIndateEnd().length()==10){
				userDto.setIndateEnd(userDto.getIndateEnd() +" 23:59:59");
			}
			// 过滤无效UID为新增，即信贷系统根本没这个UID;
			// 出现的原因是，APP申请合作时，都带上了APP的用户ID导致
			if(StringUtils.isNotEmpty(userDto.getUid()) && null==userService.getEntityByUid(userDto.getUid())){
				this.logger.info("开通机构管理员，当前UID不存在，已置空："+ userDto.getUid());
				userDto.setUid(null);
			}
			// 手机号存在处理逻辑
			UserDto dtoUserOld = userService.getEntityByMobile(userDto.getMobile());
			if(null!=dtoUserOld){
				if(dtoUserOld.getIsEnable() > 1){	//非价值用户(2未通过3已解绑)，重置UID，预执行修改流程
					userDto.setUid(dtoUserOld.getUid());
				}else if(!dtoUserOld.getUid().equals(userDto.getUid())){ //价值用户，UID不匹配，提示
					resp.setCode(RespStatusEnum.MOBILE_REPEAT.getCode());
					resp.setMsg(RespStatusEnum.MOBILE_REPEAT.getMsg());
					return resp;
				}
			}
			
			userDto.setAdmin(true);
			userDto.setIsEnable(0==userDto.getIsEnable()||1==userDto.getIsEnable() ? userDto.getIsEnable() : 0);
			userDto.setRoleId(-1);
			userDto.setDeptId(-1);
			userDto.setDeptIdArray("-1");
			userDto.setIdentity(1);
			userDto.setPosition("  ");
			userDto.setSourceFrom("system");			
			// 添加(无UID即新增)
			if(StringUtils.isEmpty(userDto.getUid())){		
				// 非资方机构，仅支持单管理员。(注:目前所有‘资方账户’均归属‘资方机构’下面)
				if(10 != userDto.getAgencyId()){
					List<UserDto> list = userService.search(userDto);
					if(null!=list && list.size()>0){
						return RespHelper.setFailRespStatus(resp, "抱歉，当前机构已存在管理员，请勿重复创建！");
					}
				}
				if(StringUtils.isEmpty(userDto.getIndateStart()) || StringUtils.isEmpty(userDto.getIndateEnd())){
					userDto.setIndateStart(null);
					userDto.setIndateEnd(null);
				}

				resp = this.insertUser(request, userDto, true, null, StringUtils.isEmpty(userDto.getIndateEnd()));
				if(RespStatusEnum.SUCCESS.getCode().equals(resp.getCode())){
					resp.setMsg(userDto.getUid());					
				}
				return resp;
			}
			
			// 正式签约，1.同步更新权限  2.开放有效期
			if(StringUtils.isEmpty(userDto.getIndateStart()) || StringUtils.isEmpty(userDto.getIndateEnd())){
				authorityService.signUserAuthorityAdmin(userDto.getUid());
				userDto.setIndateStart("0000-00-00");
				userDto.setIndateEnd("0000-00-00");
			}			
			resp = userService.updateUser(UserUpdateFrom.AGENCY, userDto, false);
			if(RespStatusEnum.SUCCESS.getCode().equals(resp.getCode())){
				resp.setMsg(userDto.getUid());			
			}
			return resp;
		} catch (Exception e) {
			e.printStackTrace();
			resp.setCodeMsg(RespStatusEnum.FAIL, RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}
	
	@ResponseBody
	@RequestMapping(value = "/v/insertUser")
	public RespStatus insertUser(HttpServletRequest request, @RequestBody UserDto userDto, boolean isAdmin4Org, String _uid, boolean isAllPermi){
		RespStatus resp = new RespStatus();
		resp.setCode(RespStatusEnum.FAIL.getCode());
		resp.setMsg(RespStatusEnum.FAIL.getMsg());
		try {
			userDto.setUid(StringUtils.isNotEmpty(_uid) ? _uid : "-1");
			if(userService.findUserDto(userDto) == null){				
				userDto.setUid(StringUtils.isNotEmpty(_uid) ? _uid : StringUtil.getUid());
				if(!isAdmin4Org){//是后台添加员工
					userDto.setAdmin(false);
					userDto.setAgencyId(userDto.getAgencyId()>0 ? userDto.getAgencyId() : getUserDto(request).getAgencyId());
				}	
				// 保存及刷新缓存
				resp = userService.insertUser(userDto, StringUtils.isNotEmpty(_uid));
				if(RespStatusEnum.SUCCESS.getCode().equals(resp.getCode())){
					resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
					try{
						// 新增用户，赋予权限
						Map<String, Object> authorityMap = null;
						int resultAuthority = 0;
						if(userDto.isAdmin()){//确保管理员至少拥有基本权限						
							resultAuthority = authorityService.insertUserAuthorityAdmin(userDto.getAgencyId(), userDto.getUid(), isAllPermi);
						}else{
							//后台新增普通用户，赋予角色权限
							authorityMap = authorityService.selectRoleAuthority(userDto.getRoleId());
							if(null == authorityMap){
								authorityMap = new HashMap<String, Object>();	
							}	
							authorityMap.put("uid", userDto.getUid());
							resultAuthority = authorityService.insertUserAuthority(authorityMap);
						}
						logger.info(String.format("%s机构初始化用户权限(管理员%s)，%s(UID%s)，影响结果集数%s", userDto.getAgencyId(), userDto.isAdmin(), userDto.getName(), userDto.getUid(), resultAuthority));
						// 历史因素，先保留，后期移到dao层或拦截器实现
						RedisOperator.set("userList", userList().getData());
					}catch(Exception e){
						logger.warn("初始化用户权限，或刷新缓存失败");
						e.printStackTrace();
					}
				}
			}else{				
				resp.setCode(RespStatusEnum.MOBILE_REPEAT.getCode());
				// APP机构绑定
				if(StringUtils.isNotEmpty(_uid)){
					AgencyDto agencyDto = httpUtil.getObject(Constants.LINK_CREDIT, String.format("/credit/customer/agency/%d", userDto.getAgencyId()), null, AgencyDto.class);
					resp.setMsg(String.format("该手机号码已被【%s】机构绑定，暂不支持绑定其它机构！", null!=agencyDto ? agencyDto.getName() : "--"));
				}else{					
					resp.setMsg(RespStatusEnum.MOBILE_REPEAT.getMsg());
				}				
			}
 		} catch (Exception e) {
			e.printStackTrace();
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(RespStatusEnum.FAIL.getMsg());
		} finally {
			this.clearUserList(request);
		}
		return resp;
	}
				
	@ResponseBody
	@RequestMapping(value = "/v/update")
	public RespStatus update(HttpServletRequest request,@RequestBody UserDto userDto, boolean isApp){
		RespStatus resp = new RespStatus();
		resp.setCode(RespStatusEnum.FAIL.getCode());
		try {
			if(null!=userDto && 3==userDto.getIsEnable()){ //用户解绑，同步APP
				AppUser appUser = new AppUser();
				appUser.setUid(userDto.getUid());
				appUser.setStatus(3);
				RespDataObject<AppUser> respApp = httpUtil.getRespDataObject(
						Constants.LINK_ANJBO_APP_URL/*Constants.LINK_ANJBO_APP_URL http://192.168.1.72:8080*/, 
						"/mortgage/agency/updateUserNotify", 
						appUser, AppUser.class);
				if(null!=respApp && RespStatusEnum.SUCCESS.getCode().equals(respApp.getCode())){
					resp = this.unbind4App(request, userDto.getUid());				
				}else{				
					resp.setCodeMsg(RespStatusEnum.FAIL, String.format("同步快鸽APP失败(%s)：%s", userDto.getUid(), respApp.getMsg()));
				}				
			}else if(userService.findUserDto(userDto) == null){
				userDto.setPassword(null);
				resp = userService.updateUser(UserUpdateFrom.DEFAULT, userDto, isApp);
				if(RespStatusEnum.SUCCESS.getCode().equals(resp.getCode())){
					if(userDto.getRoleId() != 0){
						UserDto usetTemp = CommonDataUtil.getUserDtoByUidAndMobile(userDto.getUid());
						// 如果角色变更权限也要变更(清空原用户权限)。
						if(usetTemp.getRoleId() != userDto.getRoleId()){
							Map<String, Object> authorityMap = authorityService.selectRoleAuthority(userDto.getRoleId());
							if(null == authorityMap){
								authorityMap = new HashMap<String, Object>();
								authorityMap.put("authorityId", "");
							}
							// 更新用户权限
							authorityMap.put("uid", userDto.getUid());
							if(usetTemp.isAdmin()){//确保管理员至少拥有基本权限：1看单权限配置7用户列表9角色列表27部门维护
								authorityMap.put("authorityId", "1,7,9,27"+(null==authorityMap.get("authorityId")||"".equals(authorityMap.get("authorityId")) ? "" : ","+authorityMap.get("authorityId")));
							}
							authorityService.updateUserAuthority(authorityMap);							
						}
					}					
					resp.setCode(RespStatusEnum.SUCCESS.getCode());
					resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
				}
			}else{
				resp.setMsg(RespStatusEnum.MOBILE_REPEAT.getMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(RespStatusEnum.FAIL.getMsg());
		} finally {
			this.clearUserList(request);
		}
		return resp;
	}
	@ResponseBody
	@RequestMapping(value = "/v/updateStatus")
	public RespStatus updateStatus(HttpServletRequest request, @RequestBody UserDto userDto){
		try {
			userService.updateStatus(userDto);
			return RespHelper.setSuccessRespStatus(new RespStatus());
		} catch (Exception e) {
			e.printStackTrace();
			return RespHelper.setFailRespStatus(new RespStatus(), "操作失败!");
		} finally {
			this.clearUserList(request);
		}
	}
	
	/**
	 * 修改密码
	 * @param request
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/v/updataPwd")
	public RespStatus updataPwd(HttpServletRequest request, @RequestBody Map<String, Object> params) {
		return userService.updataPwd(getUserDto(request), params);
	}
	@ResponseBody
	@RequestMapping(value = "/v/resetPwd")
	public RespStatus resetPwd(HttpServletRequest request,@RequestBody UserDto userDto){
		RespStatus resp = new RespStatus();
		resp.setCode(RespStatusEnum.FAIL.getCode());
		try {
			UserDto session = this.getUserDto(request);
			
			String newPwd = 10==userDto.getAgencyId() ? "888888" : NumberUtil.randomFixLenthString(10);
			userDto.setPassword(newPwd);
			userDto.setAgencyId(null!=session ? session.getAgencyId() : -1);
			userService.updatePwd(userDto);
			if(StringUtils.isNotEmpty(userDto.getUid())){
				Set<String> keys = RedisOperator.keys(RedisKey.PREFIX.ANJBO_CREDIT_LOGININFO + MD5Utils.MD5Encode(userDto.getUid()) + ":" + "*");
				for (String key : keys) {
					RedisOperator.delete(key);
				}
			}
			String ipWhite = ConfigUtil.getStringValue(Constants.BASE_AMS_IPWHITE,ConfigUtil.CONFIG_BASE);
			AmsUtil.smsSend(userDto.getMobile(), ipWhite, Constants.SMS_RESETPWD, newPwd);
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg("密码重置成功，已通过短信发送至该用户手机");
		} catch (Exception e) {
			e.printStackTrace();
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}
	
	@ResponseBody
	@RequestMapping(value = "/v/findUserDto")
	public RespDataObject<UserDto> findUserDto(HttpServletRequest request,@RequestBody UserDto userDto){
		RespDataObject<UserDto> resp = new RespDataObject<UserDto>();
		resp.setCode(RespStatusEnum.FAIL.getCode());
		try {
			resp.setData(userService.findUserDto(userDto));
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}
	
	/**
	 * 获取一个用户，含权限
	 * @Author KangLG<2018年1月12日>
	 * @param request
	 * @param userDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/v/get4Auth")
	public RespDataObject<UserDto> get4Auth(HttpServletRequest request, @RequestBody UserDto userDto){
		RespDataObject<UserDto> resp = new RespDataObject<UserDto>();
		resp.setCode(RespStatusEnum.FAIL.getCode());
		try {
			UserDto dto = userService.findUserDto(userDto);
			if(null == dto){
				resp.setMsg(String.format("当前用户不存在！UID=%s, Mobile=%s", userDto.getUid(), userDto.getMobile()));
				return resp;
			}
			// 加载权限信息
			dto.setAuthIds(new ArrayList<String>());
			RoleDto dtoRole = dto.getRoleId()>0 ? roleService.findRoleByRoleId(dto.getRoleId()) : null;
			dto.setRoleName(null!=dtoRole ? dtoRole.getName() : "--");		
			Map<String, Object> userAuthList = authorityService.selectUserAuthority(dto.getUid());	
			dto.getAuthIds().addAll(Arrays.asList(MapUtils.getString(userAuthList, "authorityId","").split(",")));
//			for (Map<String, Object> map : userAuthList) {
//				if(MapUtils.getString(map, "uid").equals(dto.getUid())){
//					dto.getAuthIds().addAll(Arrays.asList(MapUtils.getString(map, "authorityId","").split(",")));
//				}
//			}
			resp.setData(dto);
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
	@RequestMapping(value = "/v/findAccepList")
	public RespData<Map<String, String>> findAccepList(HttpServletRequest request,@RequestBody Map<String, Object> params){
		RespData<Map<String, String>> resp = new RespData<Map<String, String>>();
		resp.setCode(RespStatusEnum.FAIL.getCode());
		try {
			String choicePersonnel = MapUtils.getString(params, "choicePersonnel");
			String cityCode = MapUtils.getString(params, "cityCode");
			AuthorityDto authorityDto = new AuthorityDto();
			authorityDto.setName(choicePersonnel+"[A]");
			List<AuthorityDto> authorityDtoList = authorityService.selectAuthorityDtoByAuthorityDto(authorityDto);
			List<UserDto> userList = CommonDataUtil.getUserListByCityCodeOrAgencyId(cityCode, this.getUserDto(request).getAgencyId());
			List<Map<String, String>> list = new ArrayList<Map<String,String>>();
			for (UserDto userDto : userList) {
				if(userDto.getAccount().equals("admin")){
					continue;
				}
				if(userDto.getAuthIds() != null){
					for (AuthorityDto authority : authorityDtoList) {
						if(userDto.getAuthIds().contains(authority.getId()+"")){
							Map<String, String> map = new HashMap<String, String>();
							map.put("id", userDto.getUid());
							map.put("name", userDto.getName());
							list.add(map);
							break;
						}
					}
				}
			}
			resp.setData(list);
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
	@RequestMapping(value = "/v/gennerAccountPwd")
	public RespDataObject<Map<String,Object>> gennerAccountPwd(HttpServletRequest request,@RequestBody UserDto userDto){
		RespDataObject<Map<String,Object>> resp = new RespDataObject<Map<String,Object>>();
		resp.setCode(RespStatusEnum.FAIL.getCode());
		if(StringUtil.isEmpty(userDto.getName())){
			resp.setCode(RespStatusEnum.PARAMETER_ERROR.getCode());
			return resp;
		}
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			String namePy = PinYin4JUtil.getFirstSpell(userDto.getName());
			String account = "";
	        int number = 0;
	        DecimalFormat df=new DecimalFormat("000");
	        do{
				number++;
				account = namePy + df.format(number);
	        }while (userService.selectUserByAccount(account)!=null);
			map.put("account",account);
			map.put("pwd",NumberUtil.randomFixLenthString(10));
			resp.setData(map);
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}
	
	/**
	 * 根据部门分组统计用户数量集合
	 * @rewrite KangLG<2017-11-02> 修正部门员工数量，可能一个员工同属于多个部门
	 * @param request
	 * @param userDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/v/mapUserCountGroupByDeptId") 
	public RespDataObject<Map<String,Map<String, Integer>>> mapUserCountGroupByDeptId(HttpServletRequest request,@RequestBody UserDto userDto){
		RespDataObject<Map<String,Map<String, Integer>>> resp = new RespDataObject<Map<String,Map<String, Integer>>>();
		List<Map<String, Object>> list = userService.selectUserCountGroupByDeptId(userDto);
		Map<String, Integer> map0 = new HashMap<String, Integer>();
		Map<String, Integer> map1 = new HashMap<String, Integer>();
		String deptIdsStr;
		String[] deptIdsArray;
		for (Map<String, Object> map : list) {
			if(!map.containsKey("deptId")){
				continue;
			}
			deptIdsStr = MapUtils.getString(map, "deptId");
			if(MapUtils.getBooleanValue(map, "identity")){
//				map1.put(MapUtils.getString(map, "deptId"), MapUtils.getIntValue(map, "count"));
				if(!deptIdsStr.contains(",")){
					map1.put(deptIdsStr, MapUtils.getIntValue(map, "count")+MapUtils.getIntValue(map1, deptIdsStr, 0));
				}else{
					deptIdsArray = deptIdsStr.split(",");
					for (String deptId : deptIdsArray) {
						map1.put(deptId, MapUtils.getIntValue(map, "count")+MapUtils.getIntValue(map1, deptId, 0));
					}
				}
			}else{
//				map0.put(MapUtils.getString(map, "deptId"), MapUtils.getIntValue(map, "count"));				
				if(!deptIdsStr.contains(",")){
					map0.put(deptIdsStr, MapUtils.getIntValue(map, "count")+MapUtils.getIntValue(map0, deptIdsStr, 0));
				}else{
					deptIdsArray = deptIdsStr.split(",");
					for (String deptId : deptIdsArray) {
						map0.put(deptId, MapUtils.getIntValue(map, "count")+MapUtils.getIntValue(map0, deptId, 0));
					}
				}
			}
		}
		Map<String,Map<String, Integer>> map = new HashMap<String,Map<String, Integer>>();
		map.put("map0", map0);//普通
		map.put("map1", map1);//上级
		resp.setData(map);
		resp.setCode(RespStatusEnum.SUCCESS.getCode());
		resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		return resp;
	}
	
	@ResponseBody
	@RequestMapping(value = "/v/selectUidsByDeptId") 
	public RespDataObject<Map<String, String>> selectUidsByDeptId(HttpServletRequest request,@RequestBody UserDto userDto){
		RespDataObject<Map<String, String>> resp = new RespDataObject<Map<String, String>>();
		try {
			Map<String, String> map = new HashMap<String, String>();
			DeptDto deptDto = new DeptDto();
			deptDto.setAgencyId(userDto.getAgencyId());
			List<DeptDto> list = deptService.selectDeptList(deptDto);
			
			// 新增多部门情况分支			
			List<Integer> lstDeptIds = new LinkedList<Integer>();
			if(StringUtils.isNotEmpty(userDto.getDeptIdArray()) && userDto.getDeptIdArray().contains(",")){
				String[] aryDeptIds = userDto.getDeptIdArray().split(",");				
				for(String deptId: aryDeptIds){
					lstDeptIds.addAll(getAllDeptSet(list, Integer.valueOf(deptId)));
				}
				this.logger.info("所属部门集合(多部门)："+ lstDeptIds.toString());
			}else{
				this.logger.info("所属部门集合(单部门)："+ userDto.getDeptId());
				lstDeptIds.addAll(getAllDeptSet(list, userDto.getDeptId()));				
			}
			String uids = userService.selectUidByDeptList(lstDeptIds);
			this.logger.info("所属部门人员UID："+ uids);
			
			map.put("uids", uids);
			resp.setData(map);
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}
	
	/**
	 * 同步钉钉员工
	 * @Author KangLG<2017年11月1日>
	 * @param request
	 */
	@ResponseBody
	@RequestMapping(value = "/autoSyncDingtalkUser") 
	public void autoSyncDingtalkUser(HttpServletRequest request){
		userService.autoSyncDingtalkUser();		
	}
	/**
	 * 同步信贷老用户to快鸽App
	 * @Author KangLG<2017年12月21日>
	 * @param request
	 */
	@ResponseBody
	@RequestMapping(value = "/autoSyncOldUser") 
	public void autoSyncOldUser(HttpServletRequest request){
		userService.autoSyncOldUser();		
	}
	
	/**
	 * 手机号登录短信验证码
	 * @Author KangLG<2017年11月6日>
	 * @param request
	 */
	@ResponseBody
	@RequestMapping(value = "/sendSMS") 
	public RespStatus sendSMS(HttpServletRequest request, @RequestBody Map<String, String> params){
		String validateCode = MapUtils.getString(params, "validateCode", "");
		if(null==params || StringUtils.isEmpty(MapUtils.getString(params, "userMobile", "")) || !FieldRegex.isMobileNo(params.get("userMobile")) || StringUtils.isEmpty(validateCode)){
			return RespHelper.setFailRespStatus(new RespStatus(), RespStatusEnum.PARAMETER_ERROR.getMsg());
		}else if(CookieUtil.authCode(request.getSession(), validateCode)){
			return RespHelper.setFailRespStatus(new RespStatus(), RespStatusEnum.VERIFYCODE_ERROR.getMsg());
		}
		try {
			String code = UidUtil.generateNo(4);
			AmsUtil.smsSend(params.get("userMobile"), IpUtil.getClientIp(request), "sms.template.code", code);
			request.getSession().setAttribute(Constants.LOGIN_USER_CODE_SMS, code);
			return RespHelper.setSuccessRespStatus(new RespStatus());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return RespHelper.failRespStatus();		
	}
	
	/**
	 * 通过uid判断并返回uid集合
	 * @param request
	 * @param uid
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectUidsByUid") 
	public RespDataObject<Map<String, String>> selectUidsByUid(HttpServletRequest request, @RequestBody Map<String, Object> params){
		RespDataObject<Map<String, String>> resp = new RespDataObject<Map<String, String>>();
		try {
			String uid = MapUtils.getString(params, "uid");
			UserDto bean = new UserDto();
			bean.setUid(uid);
			bean.setMobile(null);
			UserDto userDto = userService.findUserDto(bean);
			if (userDto == null) {
				resp.setCode(RespStatusEnum.ACCOUNT_NO_FIND.getCode());
				resp.setMsg(RespStatusEnum.ACCOUNT_NO_FIND.getMsg());
				return resp;
			}
			
			String uids = userDto.getUid();
			Map<String, String> map = new HashMap<String, String>();
			map.put("type", "0");
			map.put("agencyId", userDto.getAgencyId()+"");
			
			if (userDto.isAdmin()) {
				uids = userService.selectUidByAgencyId(userDto.getAgencyId());
				map.put("type", "2");
				
			} else {
				if (userDto.getIdentity() == 1) {
					DeptDto deptDto = new DeptDto();
					deptDto.setAgencyId(userDto.getAgencyId());
					deptDto.setId(userDto.getDeptId());
					List<DeptDto> list = deptService.selectDeptList(deptDto);
					Set<Integer> set = getAllDeptSet(list,deptDto.getId());
					String deptIds = set.toString();
					deptIds = deptIds.replace("[","").replace("]","");
					uids = userService.selectUidByDeptIds(deptIds);
					map.put("type", "1");
				}
			}
			
			map.put("uids",uids);
			resp.setData(map);
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}
	
	/**
	 * 加载用户信息(by手机号)
	 * @Author KangLG<2017年11月15日>
	 * @param request
	 * @param mobile
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getEntityByMobile_{mobile}")
	public RespDataObject<UserDto> getEntityByMobile(HttpServletRequest request, @PathVariable("mobile")String mobile){
		// 有效性校验
		UserDto userDto = CommonDataUtil.getUserDtoByUidAndMobile(mobile);		
		if(userDto == null || StringUtils.isEmpty(userDto.getUid())){
			RespHelper.setFailDataObject(new RespDataObject<UserDto>(), null, "未找到相关数据，请确认手机号码是否输入有误！");
		}
		try{
			// 机构信息
			Map<String, Object> paramsMap = new HashMap<String, Object>();
			paramsMap.put("agencyId", userDto.getAgencyId());
			try {
				AgencyDto agencyDto = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/customer/agency/v/getAgencyDto", paramsMap, AgencyDto.class);
				if(null!=agencyDto) {
					userDto.setAgencyName(agencyDto.getName());
					userDto.setAgencyChanlManUid(agencyDto.getChanlMan());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 角色&部门信息
			RoleDto dtoRole = roleService.findRoleByRoleId(userDto.getRoleId());
			userDto.setRoleName(null!=dtoRole ? dtoRole.getName() : "");
			DeptDto dtoDept = deptService.findDeptByDeptId(userDto.getDeptId());
			userDto.setDeptName(null!=dtoDept ? dtoDept.getName() : "");
		} catch (Exception e) {
			RespHelper.setFailDataObject(new RespDataObject<UserDto>(), null, RespStatusEnum.FAIL.getMsg());
		}
		return RespHelper.setSuccessDataObject(new RespDataObject<UserDto>(), userDto);
	}
	/**
	 * APP机构绑定
	 * 1.不存在：新增
	 * 2.存在：返回
	 * @Author KangLG<2017年11月15日>
	 * @param request
	 * @param userDto {uid, agencyId机构ID,roleId,deptId, name,mobile,email}
	 * @return 用户ID
	 */
	@ResponseBody
	@RequestMapping(value = "/bind4App")
	public RespStatus bind4App(HttpServletRequest request, @RequestBody UserDto userDto){
		if(StringUtils.isEmpty(userDto.getUid()) || userDto.getAgencyId()<2){//快鸽总部禁止绑定
			return RespHelper.setFailRespStatus(new RespStatus(), RespStatusEnum.PARAMETER_ERROR.getMsg());
		}
		// 0.校验机构信息
		AgencyDto agencyDto = httpUtil.getObject(Constants.LINK_CREDIT, String.format("/credit/customer/agency/%d", userDto.getAgencyId()), null, AgencyDto.class);
		if(null == agencyDto){
			return RespHelper.setFailDataObject(new RespDataObject<AgencyFull>(), null, RespStatusEnum.AGENCY_UNFIND.getMsg());
		}else if(!agencyDto.getStatus().equals(1)){	//使用状态，0禁用1可用
			return RespHelper.setFailDataObject(new RespDataObject<AgencyFull>(), null, RespStatusEnum.AGENCY_UNENABLED.getMsg());
		}else if(agencyDto.getSignStatus() != 2){	//签约状态，1未签约2已签约3已解约
			return RespHelper.setFailDataObject(new RespDataObject<AgencyFull>(), null, RespStatusEnum.AGENCY_UNSIGN.getMsg());
		}
		// 1.信贷系统不存在，则新增
		UserDto dto = userService.getEntityByUid(userDto.getUid());
		if(null == dto){
			userDto.setIsEnable(-1);//待审核
			return this.insertUser(request, userDto, false, userDto.getUid(), false);				
		}else if(dto.getAgencyId()>0 && dto.getAgencyId()!=userDto.getAgencyId() && dto.getIsEnable()<2){ //曾经绑定过的机构用户，是审核未通过/解绑时，可绑定到新机构
			agencyDto = httpUtil.getObject(Constants.LINK_CREDIT, String.format("/credit/customer/agency/%d", dto.getAgencyId()), null, AgencyDto.class);
			if(null != agencyDto){
				return RespHelper.setFailRespStatus(new RespStatus(), String.format("该账号已被【%s】机构绑定，请联系解绑后重新绑定！", agencyDto.getName()));
			}
		}
		// 2.信贷系统存在，则更新		
		userDto.setIsEnable(-1);//若信贷系统存在，则重新启用审核流程
		userDto.setApproveRemark("  ");
		return this.update(request, userDto, true);
	}	
	/**
	 * APP机构解绑
	 * @Author KangLG<2017年11月16日>
	 * @param request
	 * @param uid
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/unbind4App_{uid}")
	public RespStatus unbind4App(HttpServletRequest request, @PathVariable("uid")String uid){
		if(StringUtils.isEmpty(uid)){
			return RespHelper.setFailRespStatus(new RespStatus(), RespStatusEnum.PARAMETER_ERROR.getMsg());
		}
		try {
			UserDto dto = new UserDto();
			dto.setUid(uid);
			userService.update4Unbind(dto);
			return RespHelper.setSuccessRespStatus(new RespStatus());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.clearUserList(request);
		}
		return RespHelper.failRespStatus();
	}
	
	/**
	 * 机构解绑，批量解除用户
	 * @Author KangLG<2017年12月21日>
	 * @param request
	 * @param agencyId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/v/unbind4Agency_{agencyId}")
	public RespStatus unbind4Agency(HttpServletRequest request, @PathVariable("agencyId")int agencyId){
		if(agencyId<=1 && this.getUserDto(request).getAgencyId()!=1){
			return RespHelper.setFailRespStatus(new RespStatus(), RespStatusEnum.PARAMETER_ERROR.getMsg());
		}
		try {
			UserDto dto = new UserDto();
			dto.setAgencyId(agencyId);	
			userService.update4UnbindAgency(dto);
			return RespHelper.setSuccessRespStatus(new RespStatus());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.clearUserList(request);
		}
		return RespHelper.failRespStatus();
	}
	
	/**
	 * 检测手机号码是否存在
	 * @Author KangLG<2017年12月7日>
	 * @param mobile
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/checkMobile_{mobile}")
	public RespStatus checkMobile(HttpServletRequest request, @PathVariable("mobile")String mobile) {
		try {
			UserDto dto = userService.getEntityByMobile(mobile);
			return null==dto ? RespHelper.setSuccessRespStatus("") : RespHelper.setFailRespStatus(new RespStatus(), RespStatusEnum.MOBILE_REPEAT.getMsg());			
		} catch (Exception e) {
			this.logger.error(e);
			return RespHelper.failRespStatus();
		}		
	}
	/**
	 * 检测有效手机号码是否存在，不含(审核不通过/已解绑用户)
	 * @Author KangLG<2017年12月20日>
	 * @param request
	 * @param mobile
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/checkEnableMobile_{mobile}")
	public RespStatus checkUidMobile(HttpServletRequest request, @PathVariable("mobile")String mobile) {
		try {	
			UserDto dto = userService.getEntityByMobile(mobile);
			// -1待审批, 0启用 ，1未启用，2不通过, 3已解绑
			if(null!=dto && dto.getIsEnable()<2){
				return RespHelper.setFailRespStatus(new RespStatus(), RespStatusEnum.MOBILE_REPEAT.getMsg());
			}
		} catch (Exception e) {
			this.logger.error(e);
			return RespHelper.failRespStatus();
		}		
		return RespHelper.setSuccessRespStatus("");
	}
}
