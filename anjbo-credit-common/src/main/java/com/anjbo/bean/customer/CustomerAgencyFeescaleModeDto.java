package com.anjbo.bean.customer;

import com.anjbo.bean.BaseDto;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;


/**
  * 收费方式 [实体类]
  * @Description: tbl_customer_agency_feescale_mode
  * @author 
  * @date 2017-07-06 15:07:09
  * @version V3.0
 */
public class CustomerAgencyFeescaleModeDto extends BaseDto implements Serializable {
	private static final long serialVersionUID = -2543444351858259047L;
	/**
	*字段名：id:
	*名称：ID
	*/
	private int id;
	
	/**
	*字段名：name:
	*名称：收费方式
	*/
	private String name;
	
	/**
	*字段名：createTime:
	*名称：createTime
	*/
	private Date createTime;
	
	/**
	*字段名：updateTime:
	*名称：updateTime
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
	
	
	/**id: ID*/
	public int getId() {
		return id;
	}

	/**id: ID*/
	public void setId(int id) {
		this.id = id;
	}
	
	/**name: 收费方式*/
	public String getName() {
		return name;
	}

	/**name: 收费方式*/
	public void setName(String name) {
		this.name = name;
	}
	
	/**createTime: createTime*/
	public Date getCreateTime() {
		return createTime;
	}

	/**createTime: createTime*/
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	/**updateTime: updateTime*/
	public Date getUpdateTime() {
		return updateTime;
	}

	/**updateTime: updateTime*/
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
	

	public CustomerAgencyFeescaleModeDto(){
	
	}
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
}