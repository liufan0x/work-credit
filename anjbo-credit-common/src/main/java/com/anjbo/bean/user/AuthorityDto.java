package com.anjbo.bean.user;

import com.anjbo.bean.BaseDto;

/**
 * 权限
 * @author lic
 *
 */
public class AuthorityDto extends BaseDto{

	private static final long serialVersionUID = 1L;

	/**权限Id**/
	private int id;

	/**权限名称**/
	private String name;

	/**流程Id**/
	private int processId;
	
	/** 是否启用（0，启用 1，未启用） **/
	private int enable;

	/** 备注 **/
	private String remark;

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

	public int getProcessId() {
		return processId;
	}

	public void setProcessId(int processId) {
		this.processId = processId;
	}

	public int getEnable() {
		return enable;
	}

	public void setEnable(int enable) {
		this.enable = enable;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
