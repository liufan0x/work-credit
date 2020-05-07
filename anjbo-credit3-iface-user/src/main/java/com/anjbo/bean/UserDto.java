/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.bean;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang3.StringUtils;

import com.anjbo.bean.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-05-09 10:00:15
 * @version 1.0
 */
@ApiModel(value = "用户表")
public class UserDto extends BaseDto{
	
	private static final long serialVersionUID = 1L;
	

	/** 用户表 */
	@ApiModelProperty(value = "用户表")
	private java.lang.Integer id;
	
	/** 用户Uid */
	@ApiModelProperty(value = "用户Uid")
	private java.lang.String uid;
	
	/** 账号 */
	@ApiModelProperty(value = "账号")
	private java.lang.String account;
	
	/** 姓名 */
	@ApiModelProperty(value = "姓名")
	private java.lang.String name;
	
	/** 密码 */
	@ApiModelProperty(value = "密码")
	private java.lang.String password;
	
	/** 电话 */
	@ApiModelProperty(value = "电话")
	private java.lang.String telphone;
	
	/** 手机号 */
	@ApiModelProperty(value = "手机号")
	private java.lang.String mobile;
	
	/** 邮箱 */
	@ApiModelProperty(value = "邮箱")
	private java.lang.String email;
	
	/** 职务 */
	@ApiModelProperty(value = "职务")
	private java.lang.String position;
	
	/** 身份（0：普通成员，1：上级） */
	@ApiModelProperty(value = "身份（0：普通成员，1：上级）")
	private int identity;
	
	/** 城市Code */
	@ApiModelProperty(value = "城市Code")
	private java.lang.String cityCode;
	
	/** 角色Id */
	@ApiModelProperty(value = "角色Id")
	private java.lang.Integer roleId;
	
	/** 角色Name */
	@ApiModelProperty(value = "角色Name")
	private String roleName;
	
	/** 部门Id */
	@ApiModelProperty(value = "部门Id")
	private java.lang.Integer deptId;
	
	/** 部门Ids */
	@ApiModelProperty(value = "部门Ids")
	private String deptIds;

	/** 部门Id集合 */
	@ApiModelProperty(value = "部门Id集合")
	private String[] deptIdsArray;
	
	/** 部门name */
	@ApiModelProperty(value = "部门name")
	private String deptName;
	
	/** 部门Ids数组 */
	@ApiModelProperty(value = "部门Ids数组")
	private java.lang.String deptIdArray;
	
	/** 工号 */
	@ApiModelProperty(value = "工号")
	private java.lang.String jobNumber;
	
	/** 机构Id */
	@ApiModelProperty(value = "机构Id")
	private java.lang.Integer agencyId;
	
	/** 机构Name */
	@ApiModelProperty(value = "机构Name")
	private String agencyName;
	
	/** 是否启用（-1待审批, 0启用 ，1未启用，2不通过, 3已解绑） */
	@ApiModelProperty(value = "是否启用（-1待审批, 0启用 ，1未启用，2不通过, 3已解绑）")
	private int isEnable;
	
	/** 审批备注 */
	@ApiModelProperty(value = "审批备注")
	private java.lang.String approveRemark;
	
	/** 身份证号 */
	@ApiModelProperty(value = "身份证号")
	private java.lang.String idCard;
	
	/** 备注 */
	@ApiModelProperty(value = "备注")
	private java.lang.String remark;
	
	/** 是否在App显示（0：否，1：是） */
	@ApiModelProperty(value = "是否在App显示（0：否，1：是）")
	private Integer appIsShow;
	
	/** 钉钉用户ID */
	@ApiModelProperty(value = "钉钉用户ID")
	private java.lang.String dingtalkUid;
	
	/** 钉钉部门ID(保留转deptIdArray) */
	@ApiModelProperty(value = "钉钉部门ID(保留转deptIdArray)")
	private java.lang.String dingtalkDepId;
	
	/** 用户来源：system,org,appAndroid,appIOS */
	@ApiModelProperty(value = "用户来源：system,org,appAndroid,appIOS")
	private java.lang.String sourceFrom;
	
	/** 是否管理员：0否1是 */
	@ApiModelProperty(value = "是否管理员：0否1是")
	private Integer isAdmin;
	
	/** 有效期B */
	@ApiModelProperty(value = "有效期开始")
	private java.util.Date indateStart;
	
	/** 有效期E */
	@ApiModelProperty(value = "有效期结束")
	private java.util.Date indateEnd;

	/** 权限Id集合 */
	@ApiModelProperty(value = "权限Id集合")
	private List<String> authIds;
	
	/** 机构所属快鸽渠道经理 */
	@ApiModelProperty(value = "机构所属快鸽渠道经理")
	private String agencyChanlManUid;
	
	/** 设备token */
	private String token;
	
	public java.lang.Integer getId() {
		return id;
	}
	public void setId(java.lang.Integer id) {
		this.id = id;
	}
	public java.lang.String getUid() {
		return uid;
	}
	public void setUid(java.lang.String uid) {
		this.uid = uid;
	}
	public java.lang.String getAccount() {
		return account;
	}
	public void setAccount(java.lang.String account) {
		this.account = account;
	}
	public java.lang.String getName() {
		return name;
	}
	public void setName(java.lang.String name) {
		this.name = name;
	}
	public java.lang.String getPassword() {
		return password;
	}
	public void setPassword(java.lang.String password) {
		this.password = password;
	}
	public java.lang.String getTelphone() {
		return telphone;
	}
	public void setTelphone(java.lang.String telphone) {
		this.telphone = telphone;
	}
	public java.lang.String getMobile() {
		return mobile;
	}
	public void setMobile(java.lang.String mobile) {
		this.mobile = mobile;
	}
	public java.lang.String getEmail() {
		return email;
	}
	public void setEmail(java.lang.String email) {
		this.email = email;
	}
	public java.lang.String getPosition() {
		return position;
	}
	public void setPosition(java.lang.String position) {
		this.position = position;
	}
	public int getIdentity() {
		return identity;
	}
	public void setIdentity(int identity) {
		this.identity = identity;
	}
	public java.lang.String getCityCode() {
		return cityCode;
	}
	public void setCityCode(java.lang.String cityCode) {
		this.cityCode = cityCode;
	}
	public java.lang.Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(java.lang.Integer roleId) {
		this.roleId = roleId;
	}
	public java.lang.Integer getDeptId() {
		return deptId;
	}
	public void setDeptId(java.lang.Integer deptId) {
		this.deptId = deptId;
	}
	public java.lang.String getDeptIdArray() {
		return deptIdArray;
	}
	public void setDeptIdArray(java.lang.String deptIdArray) {
		this.deptIdArray = deptIdArray;
	}
	public java.lang.String getJobNumber() {
		return jobNumber;
	}
	public void setJobNumber(java.lang.String jobNumber) {
		this.jobNumber = jobNumber;
	}
	public java.lang.Integer getAgencyId() {
		return agencyId;
	}
	public void setAgencyId(java.lang.Integer agencyId) {
		this.agencyId = agencyId;
	}
	public int getIsEnable() {
		return isEnable;
	}
	public void setIsEnable(int isEnable) {
		this.isEnable = isEnable;
	}
	public java.lang.String getApproveRemark() {
		return approveRemark;
	}
	public void setApproveRemark(java.lang.String approveRemark) {
		this.approveRemark = approveRemark;
	}
	public java.lang.String getIdCard() {
		return idCard;
	}
	public void setIdCard(java.lang.String idCard) {
		this.idCard = idCard;
	}
	public java.lang.String getRemark() {
		return remark;
	}
	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}
	public Integer getAppIsShow() {
		return appIsShow;
	}
	public void setAppIsShow(Integer appIsShow) {
		this.appIsShow = appIsShow;
	}
	public java.lang.String getDingtalkUid() {
		return dingtalkUid;
	}
	public void setDingtalkUid(java.lang.String dingtalkUid) {
		this.dingtalkUid = dingtalkUid;
	}
	public java.lang.String getDingtalkDepId() {
		return dingtalkDepId;
	}
	public void setDingtalkDepId(java.lang.String dingtalkDepId) {
		this.dingtalkDepId = dingtalkDepId;
	}
	public java.lang.String getSourceFrom() {
		return sourceFrom;
	}
	public void setSourceFrom(java.lang.String sourceFrom) {
		this.sourceFrom = sourceFrom;
	}
	public Integer getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(Integer isAdmin) {
		this.isAdmin = isAdmin;
	}
	public java.util.Date getIndateStart() {
		return indateStart;
	}
	public void setIndateStart(java.util.Date indateStart) {
		this.indateStart = indateStart;
	}
	public java.util.Date getIndateEnd() {
		return indateEnd;
	}
	public void setIndateEnd(java.util.Date indateEnd) {
		this.indateEnd = indateEnd;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public List<String> getAuthIds() {
		return authIds;
	}
	public void setAuthIds(List<String> authIds) {
		this.authIds = authIds;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getAgencyName() {
		return agencyName;
	}
	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}
	public String getDeptIds() {
		return deptIds;
	}
	public void setDeptIds(String deptIds) {
		this.deptIds = deptIds;
	}
	public String getAgencyChanlManUid() {
		return agencyChanlManUid;
	}
	public void setAgencyChanlManUid(String agencyChanlManUid) {
		this.agencyChanlManUid = agencyChanlManUid;
	}
	public String[] getDeptIdsArray() {
		if(null!=deptIdsArray && deptIdsArray.length>0){
			return deptIdsArray;
		}else if(StringUtils.isNotEmpty(this.deptIds)){
			deptIdsArray = deptIds.replaceAll(" ", "").split(",");
			return deptIdsArray;
		}
		return null;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("uid",getUid())
			.append("account",getAccount())
			.append("name",getName())
			.append("password",getPassword())
			.append("telphone",getTelphone())
			.append("mobile",getMobile())
			.append("email",getEmail())
			.append("position",getPosition())
			.append("identity",getIdentity())
			.append("cityCode",getCityCode())
			.append("roleId",getRoleId())
			.append("deptId",getDeptId())
			.append("deptIdArray",getDeptIdArray())
			.append("jobNumber",getJobNumber())
			.append("agencyId",getAgencyId())
			.append("isEnable",getIsEnable())
			.append("approveRemark",getApproveRemark())
			.append("idCard",getIdCard())
			.append("remark",getRemark())
			.append("appIsShow",getAppIsShow())
			.append("dingtalkUid",getDingtalkUid())
			.append("dingtalkDepId",getDingtalkDepId())
			.append("sourceFrom",getSourceFrom())
			.append("isAdmin",getIsAdmin())
			.append("indateStart",getIndateStart())
			.append("indateEnd",getIndateEnd())
			.toString();
	}
}

