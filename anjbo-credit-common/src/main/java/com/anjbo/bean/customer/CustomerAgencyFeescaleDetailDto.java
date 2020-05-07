package com.anjbo.bean.customer;

import com.anjbo.bean.BaseDto;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;


/**
  * 收费标准详细 [实体类]
  * @Description: tbl_customer_agency_feescale_detail
  * @author 
  * @date 2017-07-06 15:07:09
  * @version V3.0
 */
public class CustomerAgencyFeescaleDetailDto extends BaseDto implements Serializable {
	private static final long serialVersionUID = 7432947875211752389L;
	/**
	*字段名：id:
	*名称：ID
	*/
	private int id;
	
	/**
	*字段名：feescaleid:
	*名称：收费标准Id
	*/
	private int feescaleid;
	
	/**
	*字段名：raskcontrolid:
	*名称：风控配置Id
	*/
	private int raskcontrolid;
	
	/**
	*字段名：sectionid:
	*名称：节点id
	*/
	private int sectionid;
	
	/**
	*字段名：field:
	*名称：阀值
	*/
	private int field;
	
	/**
	*字段名：maxfield:
	*名称：maxfield
	*/
	private int maxfield;
	
	/**
	*字段名：rate:
	*名称：费率
	*/
	private Double rate;
	
	/**
	*字段名：overduerate:
	*名称：逾期费率
	*/
	private Double overduerate;
	
	/**
	*字段名：modeid:
	*名称：收费方式
	*/
	private int modeid;
	
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
	
	/**feescaleid: 收费标准Id*/
	public int getFeescaleid() {
		return feescaleid;
	}

	/**feescaleid: 收费标准Id*/
	public void setFeescaleid(int feescaleid) {
		this.feescaleid = feescaleid;
	}
	
	/**raskcontrolid: 风控配置Id*/
	public int getRaskcontrolid() {
		return raskcontrolid;
	}

	/**raskcontrolid: 风控配置Id*/
	public void setRaskcontrolid(int raskcontrolid) {
		this.raskcontrolid = raskcontrolid;
	}
	
	/**sectionid: 节点id*/
	public int getSectionid() {
		return sectionid;
	}

	/**sectionid: 节点id*/
	public void setSectionid(int sectionid) {
		this.sectionid = sectionid;
	}
	
	/**field: 阀值*/
	public int getField() {
		return field;
	}

	/**field: 阀值*/
	public void setField(int field) {
		this.field = field;
	}
	
	/**maxfield: maxfield*/
	public int getMaxfield() {
		return maxfield;
	}

	/**maxfield: maxfield*/
	public void setMaxfield(int maxfield) {
		this.maxfield = maxfield;
	}
	
	/**rate: 费率*/
	public Double getRate() {
		return rate;
	}

	/**rate: 费率*/
	public void setRate(Double rate) {
		this.rate = rate;
	}
	
	/**overduerate: 逾期费率*/
	public Double getOverduerate() {
		return overduerate;
	}

	/**overduerate: 逾期费率*/
	public void setOverduerate(Double overduerate) {
		this.overduerate = overduerate;
	}
	
	/**modeid: 收费方式*/
	public int getModeid() {
		return modeid;
	}

	/**modeid: 收费方式*/
	public void setModeid(int modeid) {
		this.modeid = modeid;
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
	

	public CustomerAgencyFeescaleDetailDto(){
	
	}
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
}