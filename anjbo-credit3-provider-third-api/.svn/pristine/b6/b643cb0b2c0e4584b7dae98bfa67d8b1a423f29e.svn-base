/*
 *Project: anjbo-credit-third-api
 *File: com.anjbo.controller.dingtalk.DingtalkController.java  <2017年10月17日>
 ****************************************************************
 * 版权所有@2017 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller.impl.dingtalk;

import com.anjbo.controller.BaseController;
import com.anjbo.controller.dingtalk.IDingtalkController;
import com.anjbo.service.dingtalk.DingtalkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * 钉钉接口
 * @Author KangLG 
 * @Date 2017年10月17日 上午10:05:37
 * @version 1.0
 */
@RestController
public class DingtalkController extends BaseController implements IDingtalkController {
	@Autowired
    private DingtalkService dingtalkService;
	
	/**
	 * 获取钉钉部门列表
	 * @Author KangLG<2017年11月1日>
	 * @return
	 */
	/*
	@Override
	public RespPageData<DeptDto> getDeptList(){
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
	}*/
	
	/**
	 * 获取钉钉员工列表
	 * @Author KangLG<2017年11月1日>
	 * @param request
	 * @return
	 */
	/*
	@Override
	public RespPageData<UserDto> getUserList(){
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
	*/
	/**
	 * 获取用户信息by姓名
	 * @Author KangLG<2017年10月17日>
	 * @param userName
	 * @return
	 */
	/*
	@Override
	public RespDataObject<DingtalkUser> userGet(String userName){
		try {
			return RespHelper.setSuccessDataObject(new RespDataObject<DingtalkUser>(), dingtalkService.getUserByName(userName));
		} catch (Exception e) {
			return RespHelper.failDataObject(null);
		}
		
	}
	*/
}
