package com.anjbo.bean.user;

import com.anjbo.bean.BaseDto;

/**
 * 角色
 * @author lic
 *
 */
public class RoleDto extends BaseDto{
	
	private static final long serialVersionUID = 1L;

	/**角色Id**/
	private int id;

	/**名称**/
	private String name;

	/**描述**/
	private String describe;

	/**机构Id**/
	private int agencyId;

	/**是否是常用角色（0：是，1：不是）**/
	private int common;

	/**是否启用（0：启用，1：未启用）**/
	private int enable;
	
	/** 用户数量 **/
	private int count;
	
	public RoleDto(){}
	public RoleDto(int agencyId){
		this.agencyId = agencyId;
		super.setPageSize(10000);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public int getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(int agencyId) {
		this.agencyId = agencyId;
	}

	public int getCommon() {
		return common;
	}

	public void setCommon(int common) {
		this.common = common;
	}

	public int getEnable() {
		return enable;
	}

	public void setEnable(int enable) {
		this.enable = enable;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	
}
