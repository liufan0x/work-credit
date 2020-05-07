package com.anjbo.bean.user;

import com.anjbo.bean.BaseDto;

/**
 * 部门
 * @author lic
 *
 */
public class DeptDto extends BaseDto{

	private static final long serialVersionUID = 1L;

	/**部门Id**/
	private int id;

	/**父Id**/
	private int pid;

	/**名称**/
	private String name;

	/**机构Id**/
	private int agencyId;
	
	/** 用户数量 **/
	private int userCount;
	
	/**备注**/
	private String remark;
	
	public DeptDto(){}
	public DeptDto(int agencyId){
		this.agencyId = agencyId;
		super.setPageSize(10000);
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(int agencyId) {
		this.agencyId = agencyId;
	}

	public int getUserCount() {
		return userCount;
	}

	public void setUserCount(int userCount) {
		this.userCount = userCount;
	}
	
}
