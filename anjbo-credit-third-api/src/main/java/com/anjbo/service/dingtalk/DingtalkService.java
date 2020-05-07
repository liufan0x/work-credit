/*
 *Project: anjbo-credit-third-api
 *File: com.anjbo.service.dingtalk.impl.DingtalkService.java  <2017年10月13日>
 ****************************************************************
 * 版权所有@2017 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service.dingtalk;


import java.util.List;

import com.anjbo.bean.dingtalk.DingtalkUser;
import com.anjbo.bean.dingtalk.ThirdDingtalkBpmsDto;
import com.anjbo.bean.user.DeptDto;
import com.anjbo.bean.user.UserDto;

/**
 * @Author KangLG 
 * @Date 2017年10月13日 下午4:51:13
 * @version 1.0
 */
public interface DingtalkService {

	/**
	 * 根据姓名获取用户信息
	 * @Author KangLG<2017年10月13日>
	 * @param userName
	 * @return
	 */
	DingtalkUser getUserByName(String userName);

	/**
	 * 创建审批流程
	 * @Author KangLG<2017年10月13日>
	 * @param dto
	 * @return
	 */
	String bpmsCreate(ThirdDingtalkBpmsDto dto);
	
	/**
	 * 自动同步部门信息
	 * @Author KangLG<2017年10月31日>
	 * @return
	 */
	List<DeptDto> getDeptList();
	
	/**
	 * 自动同步员工信息
	 * @Author KangLG<2017年10月31日>
	 * @return
	 */
	List<UserDto> getUserList();

}