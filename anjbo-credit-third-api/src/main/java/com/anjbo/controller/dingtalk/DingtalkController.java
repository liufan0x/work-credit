/*
 *Project: anjbo-credit-third-api
 *File: com.anjbo.controller.dingtalk.DingtalkController.java  <2017年10月17日>
 ****************************************************************
 * 版权所有@2017 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller.dingtalk;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anjbo.bean.dingtalk.DingtalkUser;
import com.anjbo.bean.user.DeptDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.dingtalk.DingtalkService;

/**
 * 钉钉接口
 * @Author KangLG 
 * @Date 2017年10月17日 上午10:05:37
 * @version 1.0
 */
@RestController
@RequestMapping("/credit/third/api/dingtalk")
public class DingtalkController {
	@Autowired private DingtalkService dingtalkService;
	
	/**
	 * 获取钉钉部门列表
	 * @Author KangLG<2017年11月1日>
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/dept/list")
	public RespPageData<DeptDto> getDeptList(HttpServletRequest request){ 
		RespPageData<DeptDto> resp = new RespPageData<DeptDto>();
		try {
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
			resp.setRows(dingtalkService.getDeptList());
		} catch (Exception e) {	
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}
	
	/**
	 * 获取钉钉员工列表
	 * @Author KangLG<2017年11月1日>
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/user/list")
	public RespPageData<UserDto> getUserList(HttpServletRequest request){ 
		RespPageData<UserDto> resp = new RespPageData<UserDto>();
		try {
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
			resp.setRows(dingtalkService.getUserList());
		} catch (Exception e) {	
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(RespStatusEnum.FAIL.getMsg());
		}
		return resp;		
	}
	
	/**
	 * 获取用户信息by姓名
	 * @Author KangLG<2017年10月17日>
	 * @param request
	 * @param userName
	 * @return
	 */
	@RequestMapping(value = "/user/get")
	public RespDataObject<DingtalkUser> userGet(HttpServletRequest request, String userName){ 
		try {
			return RespHelper.setSuccessDataObject(new RespDataObject<DingtalkUser>(), dingtalkService.getUserByName(userName));
		} catch (Exception e) {	
			return RespHelper.failDataObject(null);
		}
		
	}

}
