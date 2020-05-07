package com.anjbo.controller;

import com.anjbo.bean.customer.AgencyDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.*;
import com.anjbo.common.Enums.RoleEnum;
import com.anjbo.service.ChannelManagerService;
import com.anjbo.utils.CommonDataUtil;
import com.anjbo.utils.HttpUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/credit/customer/chanlman")
public class ChannelManagerController extends BaseController {

	@Resource
	private ChannelManagerService channelManagerService;

	@ResponseBody
	@RequestMapping(value = "/v/findChannelManager")
	public RespDataObject<List<UserDto>> getRiskGradeList(
			HttpServletRequest request) {
		RespDataObject<List<UserDto>> resp = new RespDataObject<List<UserDto>>();
		try {
			UserDto user = getUserDto(request);
			// 加载机构 快鸽只能加载自己的机构
			List<UserDto> channelManagerList = new ArrayList<UserDto>();
			if (user.getAgencyId() == Enums.AgencyEnum.KUAIGE.getAgencyId()) {
				String userRole = user.getRoleName();
				if (userRole.equals(Enums.RoleEnum.ACCEPT.getName())) {
					List<AgencyDto> chanlManList = channelManagerService
							.listRoleByRolrNameAndAgencyIds(user.getUid());
					if (chanlManList != null) {
						for (AgencyDto chanMan : chanlManList) {
							UserDto userDto = CommonDataUtil.getUserDtoByUidAndMobile(chanMan.getChanlMan());
							if (userDto != null) {
								channelManagerList.add(userDto);
							}
						}
					}
					// 如果没有关联则查全部
					if (null == chanlManList || chanlManList.size() <= 0) {
						HttpUtil http = new HttpUtil();
						Map<String, String> params = new HashMap<String, String>();
						params.put("type", "roleName");
						params.put("choicePersonnel",
								Enums.RoleEnum.CHANNEL_MANAGER.getName());
						List<UserDto> list = http.getList(
								Constants.LINK_CREDIT,
								"/credit/user/base/v/choicePersonnel", params,
								UserDto.class);
						channelManagerList=list;
					}
				} else {
					HttpUtil http = new HttpUtil();
					Map<String, String> params = new HashMap<String, String>();
					params.put("type", "roleName");
					params.put("choicePersonnel",
							Enums.RoleEnum.CHANNEL_MANAGER.getName());
					List<UserDto> list = http.getList(
							Constants.LINK_CREDIT,
							"/credit/user/base/v/choicePersonnel", params,
							UserDto.class);
					channelManagerList=list;
				}
			} else {
				// 本机构渠道经理
				List<AgencyDto> chan=channelManagerService.findChanlMan(user.getAgencyId());
				if (chan != null) {
					for (AgencyDto chanMan : chan) {
						UserDto userDto = CommonDataUtil.getUserDtoByUidAndMobile(chanMan.getChanlMan());
						if (userDto != null) {
							channelManagerList.add(userDto);
						}
					}
				}
			}
			RespHelper.setSuccessDataObject(resp, channelManagerList);
		} catch (Exception e) {
			RespHelper.setFailDataObject(resp, null, "查询渠道经理失败");
			e.printStackTrace();
		}
		return resp;
	}
	
	@ResponseBody
	@RequestMapping(value = "/v/findAllChannelManager")
	public RespDataObject<List<Map<String,String>>> findAllChannelManager(
			HttpServletRequest request) {
		RespDataObject<List<Map<String,String>>> resp = new RespDataObject<List<Map<String,String>>>();
		try {
			HttpUtil http = new HttpUtil();
			Map<String, String> params = new HashMap<String, String>();
			params.put("type", "roleName");
			params.put("choicePersonnel",
					Enums.RoleEnum.CHANNEL_MANAGER.getName());
			List<UserDto> list = http.getList(
					Constants.LINK_CREDIT,
					"/credit/user/base/v/choicePersonnel", params,
					UserDto.class);
			List<Map<String,String>> listMap = new ArrayList<Map<String,String>>();
			for (UserDto userDto : list) {
				Map<String,String> map =new HashMap<String, String>();
				map.put("id",userDto.getUid());
				map.put("name",userDto.getName());
				listMap.add(map);
			}
			RespHelper.setSuccessDataObject(resp, listMap);
		} catch (Exception e) {
			RespHelper.setFailDataObject(resp, null, "查询渠道经理失败");
			e.printStackTrace();
		}
		return resp;
	}
	
	@ResponseBody
	@RequestMapping(value = "/v/appChannelManager")
	public RespDataObject<List<Map<String,Object>>> appFindChannelManager(
			HttpServletRequest request) {
		RespDataObject<List<Map<String,Object>>> resp = new RespDataObject<List<Map<String,Object>>>();
		List<Map<String,Object>> listData = new ArrayList<Map<String,Object>>();
		try {
			UserDto user = getUserDto(request);
			// 加载机构 快鸽只能加载自己的机构
			List<UserDto> channelManagerList = new ArrayList<UserDto>();
			if (user.getAgencyId() == Enums.AgencyEnum.KUAIGE.getAgencyId()) {
				String userRole = user.getRoleName();
				if (userRole.equals(Enums.RoleEnum.ACCEPT.getName())) {
					List<AgencyDto> chanlManList = channelManagerService
							.listRoleByRolrNameAndAgencyIds(user.getUid());
					if (chanlManList != null) {
						for (AgencyDto chanMan : chanlManList) {
							UserDto userDto = CommonDataUtil.getUserDtoByUidAndMobile(chanMan.getChanlMan());
							if (userDto != null) {
								channelManagerList.add(userDto);
							}
						}
					}
					// 如果没有关联则查全部
					if (null == chanlManList || chanlManList.size() <= 0) {
						HttpUtil http = new HttpUtil();
						Map<String, String> params = new HashMap<String, String>();
						params.put("type", "roleName");
						params.put("choicePersonnel",
								Enums.RoleEnum.CHANNEL_MANAGER.getName());
						List<UserDto> list = http.getList(
								Constants.LINK_CREDIT,
								"/credit/user/base/v/choicePersonnel", params,
								UserDto.class);
						channelManagerList=list;
					}
				} else {
					HttpUtil http = new HttpUtil();
					Map<String, String> params = new HashMap<String, String>();
					params.put("type", "roleName");
					params.put("choicePersonnel",
							Enums.RoleEnum.CHANNEL_MANAGER.getName());
					List<UserDto> list = http.getList(
							Constants.LINK_CREDIT,
							"/credit/user/base/v/choicePersonnel", params,
							UserDto.class);
					channelManagerList=list;
				}
			} else {
				// 本机构渠道经理
				List<AgencyDto> chan=channelManagerService.findChanlMan(user.getAgencyId());
				if (chan != null) {
					for (AgencyDto chanMan : chan) {
						UserDto userDto = CommonDataUtil.getUserDtoByUidAndMobile(chanMan.getChanlMan());
						if (userDto != null) {
							channelManagerList.add(userDto);
						}
					}
				}
			}
			for (UserDto userDto : channelManagerList) {
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("id", userDto.getUid());
				map.put("uid", userDto.getUid());
				map.put("name", userDto.getName());
				listData.add(map);
			}
			RespHelper.setSuccessDataObject(resp, listData);
		} catch (Exception e) {
			RespHelper.setFailDataObject(resp, null, "查询渠道经理失败");
			e.printStackTrace();
		}
		return resp;
	}
	
	/**
	 * 根据渠道经理UID查询机构 
	 * @Title: findAgencyBychannelManager 
	 * @param @return
	 * @return RespData<OrderDto>
	 */
	@ResponseBody
	@RequestMapping("/v/findAgencyBychannelManager")
	public RespData<Map<String,Object>> findAgencyBychannelManager(HttpServletRequest request,@RequestBody Map<String,Object> map) {
		RespData<Map<String,Object>> result = new RespData<Map<String,Object>>();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try {
			UserDto user = getUserDto(request);
			AgencyDto agencyDto =new AgencyDto();
			String channelManagerUid=MapUtils.getString(map,"channelManagerUid")==null?MapUtils.getString(map,"chanlMan"):MapUtils.getString(map,"channelManagerUid");
			agencyDto.setChanlMan(channelManagerUid);
			if(StringUtils.isNotBlank(agencyDto.getChanlMan())){
				List<Map<String,Object>> listMap = null;
				String userRole = user.getRoleName();
				if(RoleEnum.ACCEPT.getName().equals(userRole)){
					agencyDto.setAcceptUid(user.getUid());
					listMap = channelManagerService.findAgencyByChanlAndAccept(agencyDto);
					//如果没有关联则查全部
					if(null==listMap||listMap.size()<=0 ){
						listMap = channelManagerService.findAgencyByChanlMan(agencyDto.getChanlMan());
					}
				}
				else{
					listMap = channelManagerService.findAgencyByChanlMan(agencyDto.getChanlMan());
				}
				boolean f2 = false;
				for (Map<String,Object> tMap : listMap) {
					if("汉鹏德上".equals(MapUtils.getString(tMap, "name"))) {
						f2 = true;
					}
				}
				if(!f2) {
					Map<String,Object> hMap = new HashMap<String,Object>();
					hMap.put("id", "10220");
					hMap.put("agencyCode", "101132");
					hMap.put("name", "汉鹏德上");
					hMap.put("simName", "汉鹏德上");
					hMap.put("type", "1");
					listMap.add(hMap);
				}
				result.setData(listMap);
				result.setCode(RespStatusEnum.SUCCESS.getCode());
				result.setMsg(RespStatusEnum.SUCCESS.getMsg());
			}
			else{
				result.setMsg("渠道经理参数不能为空!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 查询所有渠道经理
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/v/channelManager")
	public RespData<UserDto> channelManager(){
		RespData<UserDto> result = new RespData<UserDto>();
		HttpUtil http = new HttpUtil();
		Map<String, String> params = new HashMap<String, String>();
		params.put("type", "roleName");
		params.put("choicePersonnel",
				Enums.RoleEnum.CHANNEL_MANAGER.getName());
		List<UserDto> list = http.getList(
				Constants.LINK_CREDIT,
				"/credit/user/base/v/choicePersonnel", params,
				UserDto.class);
		result.setData(list);
		result.setCode(RespStatusEnum.SUCCESS.getCode());
		result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		return result;
	}

	/**
	 * 快鸽APP获取渠道经理
	 * @Title: appFindManager
	 * @param @param request
	 * @param @return
	 * @return RespDataObject<List<Map<String,Object>>>
	 */
	@ResponseBody
	@RequestMapping("/appFindManager")
	public RespData<UserDto> appFindManager(HttpServletRequest request){
		RespData<UserDto> result = new RespData<UserDto>();
		HttpUtil http = new HttpUtil();
		Map<String, String> params = new HashMap<String, String>();
		params.put("type", "roleName");
		params.put("choicePersonnel",
				Enums.RoleEnum.CHANNEL_MANAGER.getName());
		List<UserDto> list = http.getList(
				Constants.LINK_CREDIT,
				"/credit/user/base/v/choicePersonnel", params,
				UserDto.class);
		List<UserDto> listTemp = new ArrayList<UserDto>();
		for (UserDto userDto : list) {
			if(userDto.getAppIsShow() == 1){
				listTemp.add(userDto);
			}
		}
		result.setData(listTemp);
		result.setCode(RespStatusEnum.SUCCESS.getCode());
		result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		return result;
	}
}
