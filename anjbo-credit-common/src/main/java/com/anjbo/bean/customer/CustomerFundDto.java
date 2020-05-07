package com.anjbo.bean.customer;

import com.anjbo.bean.BaseDto;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;


/**
  * 合作资金方 [实体类]
  * @Description: tbl_customer_fund
  * @author 
  * @date 2017-07-06 15:07:09
  * @version V3.0
 */
public class CustomerFundDto extends BaseDto implements Serializable {
	private static final long serialVersionUID = -7917170623001222996L;
	/**
	*字段名：id:
	*名称：合作资金方
	*/
	private int id;
	
	/**
	*字段名：fundName:
	*名称：资金方名称
	*/
	private String fundName;
	
	/**
	*字段名：fundDesc:
	*名称：资金方名称
	*/
	private Object fundDesc;
	
	/**
	*字段名：status:
	*名称：使用状态，0禁用，1可用
	*/
	private int status;
	
	/**
	*字段名：fundCode:
	*名称：资金方代号
	*/
	private String fundCode;
	
	/**
	*字段名：contactMan:
	*名称：联系人
	*/
	private String contactMan;
	
	/**
	*字段名：contactTel:
	*名称：联系电话
	*/
	private String contactTel;
	
	/**
	*字段名：createTime:
	*名称：创建时间
	*/
	private Date createTime;
	
	/**
	*字段名：updateTime:
	*名称：最后更新时间
	*/
	private Date updateTime;
	
	/**
	*字段名：createUid:
	*名称：创建人uid
	*/
	private String createUid;
	
	/**
	*字段名：updateUid:
	*名称：updateUid
	*/
	private String updateUid;
	
	/**
	*字段名：remark:
	*名称：备注
	*/
	private String remark;
	
	// 经理人
	private String managerUid;
	// 经理人状态(-1未知0启用1禁用)
	private Integer managerStatus;
	
	/** 权限数组 */
	private String auths;
	
	/**id: 合作资金方*/
	public int getId() {
		return id;
	}

	/**id: 合作资金方*/
	public void setId(int id) {
		this.id = id;
	}
	
	/**fundName: 资金方名称*/
	public String getFundName() {
		return fundName;
	}

	/**fundName: 资金方名称*/
	public void setFundName(String fundName) {
		this.fundName = fundName;
	}
	
	/**fundDesc: 资金方名称*/
	public Object getFundDesc() {
		return fundDesc;
	}

	/**fundDesc: 资金方名称*/
	public void setFundDesc(Object fundDesc) {
		this.fundDesc = fundDesc;
	}
	
	/**status: 使用状态，0禁用，1可用*/
	public int getStatus() {
		return status;
	}

	/**status: 使用状态，0禁用，1可用*/
	public void setStatus(int status) {
		this.status = status;
	}
	
	/**fundCode: 资金方代号*/
	public String getFundCode() {
		return fundCode;
	}

	/**fundCode: 资金方代号*/
	public void setFundCode(String fundCode) {
		this.fundCode = fundCode;
	}
	
	/**contactMan: 联系人*/
	public String getContactMan() {
		return contactMan;
	}

	/**contactMan: 联系人*/
	public void setContactMan(String contactMan) {
		this.contactMan = contactMan;
	}
	
	/**contactTel: 联系电话*/
	public String getContactTel() {
		return contactTel;
	}

	/**contactTel: 联系电话*/
	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}
	
	/**createTime: 创建时间*/
	public Date getCreateTime() {
		return createTime;
	}

	/**createTime: 创建时间*/
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	/**updateTime: 最后更新时间*/
	public Date getUpdateTime() {
		return updateTime;
	}

	/**updateTime: 最后更新时间*/
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	/**createUid: 创建人uid*/
	public String getCreateUid() {
		return createUid;
	}

	/**createUid: 创建人uid*/
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
	
	/**remark: 备注*/
	public String getRemark() {
		return remark;
	}

	/**remark: 备注*/
	public void setRemark(String remark) {
		this.remark = remark;
	}
	

	public CustomerFundDto(){
	
	}
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public String getAuths() {
		return auths;
	}

	public void setAuths(String auths) {
		this.auths = auths;
	}

	public String getManagerUid() {
		return managerUid;
	}

	public void setManagerUid(String managerUid) {
		this.managerUid = managerUid;
	}

	public Integer getManagerStatus() {
		return managerStatus;
	}

	public void setManagerStatus(Integer managerStatus) {
		this.managerStatus = managerStatus;
	}
	
}