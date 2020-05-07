/*
 *Project: anjbo-credit-common
 *File: com.anjbo.bean.customer.vo.AgencyFull.java  <2017年11月15日>
 ****************************************************************
 * 版权所有@2017 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.bean.customer.vo;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;

import com.anjbo.bean.customer.AgencyDto;
import com.anjbo.bean.user.DeptDto;
import com.anjbo.bean.user.RoleDto;

/**
 * 机构完整关联信息(机构基本信息|角色|部门)
 * @Author KangLG 
 * @Date 2017年11月15日 上午10:47:04
 * @version 1.0
 */
public class AgencyFull extends AgencyDto {
	private static final long serialVersionUID = 1L;
	private List<RoleDto> listRole;
	private List<DeptDto> listDept;
	
	public AgencyFull(){}
	public AgencyFull(AgencyDto dto){
		if(null == dto){
			return;
		}		
		try {
			PropertyUtils.copyProperties(this, dto);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
	}
	public boolean isNull(){
		if(null==this.getId() || this.getId()<1){
			return true;
		}
		return false;
	}
	
	/*
	 * getter - setter
	 */
	public List<RoleDto> getListRole() {
		return listRole;
	}
	public void setListRole(List<RoleDto> listRole) {
		this.listRole = listRole;
	}
	public List<DeptDto> getListDept() {
		return listDept;
	}
	public void setListDept(List<DeptDto> listDept) {
		this.listDept = listDept;
	}
	
	
	
	
}
