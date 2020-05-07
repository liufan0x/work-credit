/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.anjbo.bean.DeptDto;
import com.anjbo.bean.UserDto;
import com.anjbo.common.Constants;
import com.anjbo.common.RedisKey;
import com.anjbo.common.RedisOperator;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.common.SMSConstants;
import com.anjbo.controller.BaseUserController;
import com.anjbo.controller.IUserController;
import com.anjbo.controller.impl.api.UserApiController;
import com.anjbo.service.DeptService;
import com.anjbo.service.UserService;
import com.anjbo.utils.AmsUtil;
import com.anjbo.utils.MD5Utils;
import com.anjbo.utils.NumberUtils;
import com.anjbo.utils.PinYin4JUtil;
import com.anjbo.utils.StringUtil;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-05-09 10:00:15
 * @version 1.0
 */
@RestController
public class UserController extends BaseUserController implements IUserController{

	@Resource private UserService userService;
	
	@Resource private DeptService deptService;
	
	@Resource private UserApiController userApi;
	
	/**
	 * 分页查询
	 * @author Generator 
	 */
	@Override
	public RespPageData<UserDto> page(@RequestBody UserDto dto){
		RespPageData<UserDto> resp = new RespPageData<UserDto>();
		try {
			UserDto ud = userApi.getUserDto();
			DeptDto dd = new DeptDto();
			dd.setAgencyId(ud.getAgencyId());
			List<DeptDto> deptList = deptService.search(dd);
			if(dto.getDeptId() != null && dto.getDeptId() > 0){
				Set<Integer> set = getAllDeptSet(deptList,dto.getDeptId());
				String deptIds = set.toString();
				deptIds = deptIds.replace("[","").replace("]","");
				dto.setDeptIds(deptIds);
				dto.setDeptId(0);
			}
			resp.setRows(userService.search(dto));
			resp.setTotal(userService.count(dto));
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
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
	public RespData<UserDto> search(@RequestBody UserDto dto){ 
		RespData<UserDto> resp = new RespData<UserDto>();
		try {
			return RespHelper.setSuccessData(resp, userService.search(dto));
		}catch (Exception e) {
			logger.error("查询异常,参数："+dto.toString(), e);
			return RespHelper.setFailData(resp, new ArrayList<UserDto>(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 查找
	 * @author Generator 
	 */
	@Override
	public RespDataObject<UserDto> find(@RequestBody UserDto dto){ 
		RespDataObject<UserDto> resp = new RespDataObject<UserDto>();
		try {
			return RespHelper.setSuccessDataObject(resp, userService.find(dto));
		}catch (Exception e) {
			logger.error("查找异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new UserDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 新增
	 * @author Generator 
	 */
	@Override
	public RespDataObject<UserDto> add(@RequestBody UserDto dto){ 
		RespDataObject<UserDto> resp = new RespDataObject<UserDto>();
		try {
			return RespHelper.setSuccessDataObject(resp, userService.insert(dto));
		}catch (Exception e) {
			logger.error("新增异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new UserDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 编辑
	 * @author Generator 
	 */
	@Override
	public RespStatus edit(@RequestBody UserDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			userService.update(dto);
			return RespHelper.setSuccessRespStatus(resp);
		}catch (Exception e) {
			logger.error("新增异常,参数："+dto.toString(), e);
			return RespHelper.failRespStatus();
		}
	}
	
	/**
	 * 编辑
	 * @author Generator 
	 */
	@Override
	public RespStatus editToken(@RequestBody UserDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			userService.updateToken(dto);
			return RespHelper.setSuccessRespStatus(resp);
		}catch (Exception e) {
			logger.error("新增异常,参数："+dto.toString(), e);
			return RespHelper.failRespStatus();
		}
	}
	
	@Override
	public RespStatus updataPwd(@RequestBody Map<String, Object> params) {
		try {
			UserDto userDto = userApi.getUserDto();
			String oldPwd  = MapUtils.getString(params, "oldPwd","");
			String newPwd1 = MapUtils.getString(params, "newPwd1","");
			String newPwd2 = MapUtils.getString(params, "newPwd2","");
			if("".equals(oldPwd) || "".equals(newPwd1) || "".equals(newPwd2)){
				return new RespStatus(RespStatusEnum.FAIL.getCode(), RespStatusEnum.PARAMETER_ERROR.getMsg());
			}
			if(!newPwd1.equals(newPwd2)){
				return new RespStatus(RespStatusEnum.FAIL.getCode(), "新密码两次不一致");
			}
			UserDto tempUser = new UserDto();
			if(userDto.getAgencyId() == 1){ //快鸽总部后台，修改密码
				tempUser.setAccount(userDto.getAccount());
				tempUser = userService.find(tempUser);
				if(!(MD5Utils.MD5Encode(oldPwd.trim()+"{"+tempUser.getUid()+"}").equals(tempUser.getPassword()) || MD5Utils.MD5Encode(oldPwd.trim()).equals(tempUser.getPassword()))){
					return new RespStatus(RespStatusEnum.FAIL.getCode(), RespStatusEnum.PASSWORD_ERROR.getMsg());
				}
			}
			tempUser.setPassword(MD5Utils.MD5Encode(newPwd1.trim()));	
			userService.update(tempUser);
			return RespHelper.setSuccessRespStatus(new RespStatus());
		} catch (Exception e) {
			logger.error("修改密码异常",e);
			return RespHelper.failRespStatus();
		}
	}

	/**
	 * 重置密码
	 * @author Generator 
	 */
	@Override
	public RespStatus resetPwd(UserDto userDto) {
		RespStatus resp = new RespStatus();
		resp.setCode(RespStatusEnum.FAIL.getCode());
		try {
			UserDto session =  userApi.getUserDto();
			String newPwd = NumberUtils.randomFixLenthString(10);
			userDto.setPassword(newPwd);
			userDto.setAgencyId(null!=session ? session.getAgencyId() : -1);
			userService.update(userDto);
			if(StringUtils.isNotEmpty(userDto.getUid())){
				Set<String> keys = RedisOperator.keys(RedisKey.PREFIX.ANJBO_CREDIT_LOGININFO + MD5Utils.MD5Encode(userDto.getUid()) + ":" + "*");
				for (String key : keys) {
					RedisOperator.delete(key);
				}
			}
			AmsUtil.smsSend(userDto.getMobile(), Constants.BASE_AMS_IPWHITE,SMSConstants.SMS_RESETPWD, newPwd);
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg("密码重置成功，已通过短信发送至该用户手机");
		} catch (Exception e) {
			logger.error("密码重置异常",e);
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}
	
	@Override
	public RespData<UserDto> searchByType(@RequestBody Map<String, Object> params) {
		RespData<UserDto> resp = new RespData<UserDto>();
		try {
			return RespHelper.setSuccessData(resp, userService.searchByType(params));
		} catch (Exception e) {
			logger.error("查询人员异常", e);
			return RespHelper.setFailData(resp, new ArrayList<UserDto>(), RespStatusEnum.FAIL.getMsg());
		}
	}
	
	@Override
	public RespData<Map<String, Object>> searchByType2(@RequestBody Map<String, Object> params) {
		RespData<Map<String, Object>> resp = new RespData<Map<String, Object>>();
		try {
			List<Map<String, Object>> tempList = new ArrayList<Map<String, Object>>();
			Map<String, Object> tempMap = null;
			for (UserDto userDto : userService.searchByType(params)) {
				if(userDto.getIsEnable() != 0) {
					continue;
				}
				tempMap = new HashMap<String, Object>();
				tempMap.put("id", userDto.getUid());
				tempMap.put("name", userDto.getName());
				tempList.add(tempMap);
			}
			return RespHelper.setSuccessData(resp, tempList);
		} catch (Exception e) {
			logger.error("查询人员异常", e);
			return RespHelper.setFailData(resp, new ArrayList<Map<String, Object>>(), RespStatusEnum.FAIL.getMsg());
		}
	}
	
	@Override
	public RespDataObject<Map<String,Object>> gennerAccountPwd(@RequestBody UserDto userDto){ 
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
	        UserDto user = new UserDto();
	        do{
				number++;
				account = namePy + df.format(number);
				user.setAccount(account);
	        }while (userService.find(user).getAccount()!=null);
			map.put("account",account);
			map.put("pwd",NumberUtils.randomFixLenthString(10));
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
	
}