package com.anjbo.bean.customer;

import java.io.Serializable;
import java.util.Date;

import com.anjbo.bean.BaseDto;

/**
 *
 * 机构类型
 *
 */
public class AgencyTypeDto extends BaseDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;  //机构类型表
	private String name;  //机构类型名称
	private String describe;  //机构类型描述
	private int isEnable;  //是否启用（0，启用 1，未启用）
	private Date createTime;  //创建时间
	private Date updateTime;  //最后修改时间
	private String createUid;  //创建人
	private String updateUid;  //修改人
	private String remark;  //备注
	private int typeCount; //机构数量
	
	public int getTypeCount() {
		return typeCount;
	}
	public void setTypeCount(int typeCount) {
		this.typeCount = typeCount;
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
	public int getIsEnable() {
		return isEnable;
	}
	public void setIsEnable(int isEnable) {
		this.isEnable = isEnable;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getCreateUid() {
		return createUid;
	}
	public void setCreateUid(String createUid) {
		this.createUid = createUid;
	}
	public String getUpdateUid() {
		return updateUid;
	}
	public void setUpdateUid(String updateUid) {
		this.updateUid = updateUid;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
