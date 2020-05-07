package com.anjbo.bean.user;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.anjbo.bean.BaseDto;

/**
 * 用户
 * @author lic
 * 
 */
public class UserDto extends BaseDto{

	private static final long serialVersionUID = 1L;

	/**用户Id**/
	private int id;

	/**用户uId**/
	private String uid;

	/**账户**/
	private String account;

	/**密码**/
	private String password;

	/**姓名**/
	private String name;
	
	/**电话*/
	private String telphone;

	/**手机号**/
	private String mobile;

	/**邮箱**/
	private String email;

	/**职务**/
	private String position;

	/**身份（0：普通成员，1：上级）**/
	private int identity;

	/** 城市Code **/
	private String cityCode;
	
	/** 城市名称 **/
	private String cityName;
	
	/**角色Id**/
	private int roleId;

	/**角色名称**/
	private String roleName;

	/**部门Id**/
	private int deptId;
	private String deptIdArray;
	
	/**工号*/
	private String jobNumber;
	
	/**部门名称**/
	private String deptName;
	
	/**机构Id**/
	private int agencyId;
	private int agencyCode;
	private String agencyName;	
	private String agencyChanlManUid;//机构所属快鸽渠道经理

	/**是否启用（-1待审批, 0启用 ，1未启用，2不通过, 3已解绑）**/
	private int isEnable;
	private String approveRemark;
	
	/**部门id集合*/
	private String deptIds;
	private String[] deptIdsArray;
		
	/** 权限Id集合 **/
	private List<String> authIds;
	
	/** 是否在App显示（0：否，1：是） **/
	private int appIsShow;
	
	/** 用户所在部门，用于查询部门用户处理上下级关系 **/
	private int myDeptId;
	
	/**钉钉：用户ID*/
	private String dingtalkUid;
	/**钉钉：用户部门ID*/
	@Deprecated
	private String dingtalkDepId;
	
	/**备注**/
	private String remark;
	
	/**用户来源：system,appAndroid,appIOS*/
	private String sourceFrom;
	/**是否管理员：0否1是*/
	private boolean isAdmin;
	/**有效期*/
	private String indateStart;
	private String indateEnd;
	/**身份证号码*/
	private String idCard;
	/** 资方id */
	private Integer fundId;

	public String getSourceFrom() {
		return sourceFrom;
	}

	public void setSourceFrom(String sourceFrom) {
		this.sourceFrom = sourceFrom;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDingtalkUid() {
		return dingtalkUid;
	}

	public void setDingtalkUid(String dingtalkUid) {
		this.dingtalkUid = dingtalkUid;
	}

	public String getDingtalkDepId() {
		return dingtalkDepId;
	}

	public void setDingtalkDepId(String dingtalkDepId) {
		this.dingtalkDepId = dingtalkDepId;
	}

	public int getMyDeptId() {
		return myDeptId;
	}

	public void setMyDeptId(int myDeptId) {
		this.myDeptId = myDeptId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public int getIdentity() {
		return identity;
	}

	public void setIdentity(int identity) {
		this.identity = identity;
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

	public int getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(int isEnable) {
		this.isEnable = isEnable;
	}

	public String getDeptIds() {
		return deptIds;
	}

	public void setDeptIds(String deptIds) {
		this.deptIds = deptIds;
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

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public List<String> getAuthIds() {
		return authIds;
	}

	public void setAuthIds(List<String> authIds) {
		this.authIds = authIds;
	}

	public int getAppIsShow() {
		return appIsShow;
	}

	public void setAppIsShow(int appIsShow) {
		this.appIsShow = appIsShow;
	}

	public String getDeptIdArray() {
		return StringUtils.isNotEmpty(deptIdArray) ? deptIdArray : deptId>0?String.valueOf(deptId):"";
	}

	public void setDeptIdArray(String deptIdArray) {
		this.deptIdArray = deptIdArray;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	public String getJobNumber() {
		return jobNumber;
	}

	public void setJobNumber(String jobNumber) {
		this.jobNumber = jobNumber;
	}

	public String getAgencyChanlManUid() {
		return agencyChanlManUid;
	}

	public void setAgencyChanlManUid(String agencyChanlManUid) {
		this.agencyChanlManUid = agencyChanlManUid;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getIndateStart() {
		return indateStart;
	}

	public void setIndateStart(String indateStart) {
		this.indateStart = indateStart;
	}

	public String getIndateEnd() {
		return indateEnd;
	}

	public void setIndateEnd(String indateEnd) {
		this.indateEnd = indateEnd;
	}

	public int getAgencyCode() {
		return agencyCode;
	}

	public void setAgencyCode(int agencyCode) {
		this.agencyCode = agencyCode;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getApproveRemark() {
		return approveRemark;
	}

	public void setApproveRemark(String approveRemark) {
		this.approveRemark = approveRemark;
	}

	public Integer getFundId() {
		return fundId;
	}

	public void setFundId(Integer fundId) {
		this.fundId = fundId;
	}
	
}
