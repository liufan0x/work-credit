package com.anjbo.bean.customer;

import com.anjbo.bean.BaseDto;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;


/**
  * 机构受理员 [实体类]
  * @Description: tbl_customer_agency_accept
  * @author 
  * @date 2017-07-06 15:07:09
  * @version V3.0
 */
public class CustomerAgencyAcceptDto extends BaseDto implements Serializable {
	private static final long serialVersionUID = -7347492045488693720L;
	/**
	*字段名：id:
	*名称：机构受理员关联表
	*/
	private int id;
	
	/**
	*字段名：agencyId:
	*名称：机构id
	*/
	private int agencyId;
	
	/**
	*字段名：acceptUid:
	*名称：受理员uid
	*/
	private String acceptUid;
	
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
	
	
	/**id: 机构受理员关联表*/
	public int getId() {
		return id;
	}

	/**id: 机构受理员关联表*/
	public void setId(int id) {
		this.id = id;
	}
	
	/**agencyId: 机构id*/
	public int getAgencyId() {
		return agencyId;
	}

	/**agencyId: 机构id*/
	public void setAgencyId(int agencyId) {
		this.agencyId = agencyId;
	}
	
	/**acceptUid: 受理员uid*/
	public String getAcceptUid() {
		return acceptUid;
	}

	/**acceptUid: 受理员uid*/
	public void setAcceptUid(String acceptUid) {
		this.acceptUid = acceptUid;
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
	

	public CustomerAgencyAcceptDto(){
	
	}
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
}