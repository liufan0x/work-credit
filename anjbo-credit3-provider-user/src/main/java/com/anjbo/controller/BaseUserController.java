/*
 *Project: anjbo-credit3-user-provider
 *File: com.anjbo.controller.BaseUserController.java  <2018年2月7日>
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.anjbo.bean.DeptDto;

/**
 * @Author KangLG 
 * @Date 2018年2月7日 下午2:13:21
 * @version 1.0
 */
public class BaseUserController extends BaseController {
	
	
	/**
	 * 根据当前部门获取所有部门编号
	 * @param alldeps
	 * @param rootId
	 * @return
	 */
	public Set<Integer> getAllDeptSet(List<DeptDto> alldeps, int rootId){
		Set<Integer> sTemp = new HashSet<Integer>();
		Set<Integer> sTempChd = new HashSet<Integer>();
		sTempChd.add(rootId);
		while(sTempChd.size()>0){
			sTemp.addAll(sTempChd);
			Set<Integer> sTempChdTemp = new HashSet<Integer>();
			for (DeptDto deptDto : alldeps) {
				if(sTempChd.contains(deptDto.getPid())){
					sTempChdTemp.add(deptDto.getId());
				}
			}
			sTempChd = sTempChdTemp;
		}
		return sTemp;
	}
	
}
