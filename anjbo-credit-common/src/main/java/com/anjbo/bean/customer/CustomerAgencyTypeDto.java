package com.anjbo.bean.customer;

import com.anjbo.bean.BaseDto;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;


/**
  * 机构类型 [实体类]
  * @Description: tbl_customer_agency_type
  * @author 
  * @date 2017-07-06 15:07:08
  * @version V3.0
 */
public class CustomerAgencyTypeDto extends BaseDto implements Serializable {
	private static final long serialVersionUID = -7740247108328898752L;
	/**
	*字段名：id:
	*名称：机构类型表
	*/
	private int id;
	
	/**
	*字段名：name:
	*名称：机构类型名称
	*/
	private String name;
	
	/**
	*字段名：describe:
	*名称：机构类型描述
	*/
	private String describe;
	
	/**
	*字段名：isEnable:
	*名称：是否启用（0，启用 1，未启用）
	*/
	private int isEnable;
	
	/**
	*字段名：createTime:
	*名称：创建时间
	*/
	private Date createTime;
	
	/**
	*字段名：updateTime:
	*名称：最后修改时间
	*/
	private Date updateTime;
	
	/**
	*字段名：createUid:
	*名称：createUid
	*/
	private String createUid;
	
	/**
	*字段名：updateUid:
	*名称：updateUid
	*/
	private String updateUid;
	
	/**
	*字段名：remark:
	*名称：remark
	*/
	private String remark;
	
	/**机构数量**/
	private int typeCount; 
	
	public int getTypeCount() {
		return typeCount;
	}
	public void setTypeCount(int typeCount) {
		this.typeCount = typeCount;
	}
	
	
	/**id: 机构类型表*/
	public int getId() {
		return id;
	}

	/**id: 机构类型表*/
	public void setId(int id) {
		this.id = id;
	}
	
	/**name: 机构类型名称*/
	public String getName() {
		return name;
	}

	/**name: 机构类型名称*/
	public void setName(String name) {
		this.name = name;
	}
	
	/**describe: 机构类型描述*/
	public String getDescribe() {
		return describe;
	}

	/**describe: 机构类型描述*/
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	
	/**isEnable: 是否启用（0，启用 1，未启用）*/
	public int getIsEnable() {
		return isEnable;
	}

	/**isEnable: 是否启用（0，启用 1，未启用）*/
	public void setIsEnable(int isEnable) {
		this.isEnable = isEnable;
	}
	
	/**createTime: 创建时间*/
	public Date getCreateTime() {
		return createTime;
	}

	/**createTime: 创建时间*/
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	/**updateTime: 最后修改时间*/
	public Date getUpdateTime() {
		return updateTime;
	}

	/**updateTime: 最后修改时间*/
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	/**createUid: createUid*/
	public String getCreateUid() {
		return createUid;
	}

	/**createUid: createUid*/
	public void setCreateUid(String createUid) {
		this.createUid = createUid;
	}
	
	/**updateUid: updateUid*/
	public String getUpdateUid() {
		return updateUid;
	}

	/**updateUid: updateUid*/
	public void setUpdateUid(String updateUid) {
		this.updateUid = updateUid;
	}
	
	/**remark: remark*/
	public String getRemark() {
		return remark;
	}

	/**remark: remark*/
	public void setRemark(String remark) {
		this.remark = remark;
	}
	

	public CustomerAgencyTypeDto(){
	
	}
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
}