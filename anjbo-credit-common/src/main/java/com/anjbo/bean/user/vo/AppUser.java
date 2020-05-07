/*
 *Project: anjbo-credit-common
 *File: com.anjbo.bean.user.vo.AppUser.java  <2017年11月17日>
 ****************************************************************
 * 版权所有@2017 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.bean.user.vo;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

import com.anjbo.bean.common.DictDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.Constants;
import com.anjbo.common.RespData;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.utils.HttpUtil;

/**
 * @Author KangLG 
 * @Date 2017年11月17日 上午11:24:34
 * @version 1.0
 */
public class AppUser implements Serializable {
	private static final long serialVersionUID = 1L;
	private String uid;
	private String password;
	private int agencyId;	//机构
	private String agencyName;
	private int agencyCode;
	private int agencyStatus;     //0禁用 1可用
	private int agencySignStatus; //1未签约 2已签约3已解约
	private int type;		//APP用户类型：0普通用户1管理员
	private int roleId;		//角色
	private String roleName;
	private int deptId;		//部门
	private String deptName;
	private String cityCode;//城市信息
	private String cityName;
	private int identity; 	//身份（0：普通成员，1：上级）
	private String name;	//姓名
	private String phone;	//手机号
	private String email;	//邮箱
	private int status;	//是否启用（-1待审批, 0启用 ，1未启用，2不通过, 3已解绑）
	private String remark;//审批备注
	
	public AppUser(){}
	public AppUser(UserDto dto){
		try {
			PropertyUtils.copyProperties(this, dto);
			this.phone = dto.getMobile();
			this.remark = StringUtils.isNotEmpty(dto.getApproveRemark()) ? dto.getApproveRemark() : null;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * getter - setter
	 */
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getAgencyId() {
		return agencyId;
	}
	public void setAgencyId(int agencyId) {
		this.agencyId = agencyId;
	}
	public String getAgencyName() {
		return agencyName;
	}
	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}
	public int getAgencyCode() {
		return agencyCode;
	}
	public void setAgencyCode(int agencyCode) {
		this.agencyCode = agencyCode;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public int getDeptId() {
		return deptId;
	}
	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public int getIdentity() {
		return identity;
	}
	public void setIdentity(int identity) {
		this.identity = identity;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getAgencyStatus() {
		return agencyStatus;
	}
	public void setAgencyStatus(int agencyStatus) {
		this.agencyStatus = agencyStatus;
	}
	public int getAgencySignStatus() {
		return agencySignStatus;
	}
	public void setAgencySignStatus(int agencySignStatus) {
		this.agencySignStatus = agencySignStatus;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getCityName() {
		if(null==cityName && StringUtils.isNotEmpty(cityCode)){
			Map<String, String> params = new HashMap<String, String>(){{
				put("type", "cityList");
			}};
			try{
				RespData<DictDto> resp = new HttpUtil().getRespData(Constants.LINK_CREDIT, "/credit/common/base/v/choiceDict", params, DictDto.class);
				if(null!=resp && RespStatusEnum.SUCCESS.getCode().equals(resp.getCode()) && null!=resp.getData() && !resp.getData().isEmpty()){	
					resp.getData();
					for (DictDto dict : resp.getData()) {
						if(this.cityCode.equals(dict.getCode())){
							cityName = dict.getName();
							break;
						}
					}
				}
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	

}
