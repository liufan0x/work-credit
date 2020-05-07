package com.anjbo.bean.customer;

import com.anjbo.bean.BaseDto;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
  * 收费标准 [实体类]
  * @Description: tbl_customer_agency_feescale
  * @author 
  * @date 2017-07-06 15:07:09
  * @version V3.0
 */
public class CustomerAgencyFeescaleDto extends BaseDto implements Serializable {
	private static final long serialVersionUID = 6286859335589186712L;
	/**
	*字段名：id:
	*名称：ID
	*/
	private int id;
	
	/**
	*字段名：agencyTypeId:
	*名称：agencyTypeId
	*/
	private int agencyTypeId;
	
	/**
	*字段名：productionid:
	*名称：产品ID
	*/
	private int productionid;
	
	/** 产品名称 **/
	private String productName;
	
	/**
	*字段名：servicefee:
	*名称：服务费
	*/
	private double servicefee;
	
	/**
	*字段名：counterfee:
	*名称：手续费
	*/
	private double counterfee;
	
	/**
	*字段名：otherfee:
	*名称：其他费用
	*/
	private double otherfee;
	
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

	/**
	 * 收费标准(风控等级)id

	 */
	private int riskGradeId;
	
	/**风控等级列表**/
	private List<CustomerAgencyFeescaleRiskcontrolDto> customerAgencyFeescaleRiskcontrolList;
	
	/**风控等级列表**/
	public List<CustomerAgencyFeescaleRiskcontrolDto> getCustomerAgencyFeescaleRiskcontrolList() {
		return customerAgencyFeescaleRiskcontrolList;
	}
	/**风控等级列表**/
	public void setCustomerAgencyFeescaleRiskcontrolList(
			List<CustomerAgencyFeescaleRiskcontrolDto> customerAgencyFeescaleRiskcontrolList) {
		this.customerAgencyFeescaleRiskcontrolList = customerAgencyFeescaleRiskcontrolList;
	}

	/**id: ID*/
	public int getId() {
		return id;
	}

	/**id: ID*/
	public void setId(int id) {
		this.id = id;
	}
	
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	/**agencyTypeId: agencyTypeId*/
	public int getAgencyTypeId() {
		return agencyTypeId;
	}

	/**agencyTypeId: agencyTypeId*/
	public void setAgencyTypeId(int agencyTypeId) {
		this.agencyTypeId = agencyTypeId;
	}
	
	/**productionid: 产品ID*/
	public int getProductionid() {
		return productionid;
	}

	/**productionid: 产品ID*/
	public void setProductionid(int productionid) {
		this.productionid = productionid;
	}
	
	/**servicefee: 服务费*/
	public double getServicefee() {
		return servicefee;
	}

	/**servicefee: 服务费*/
	public void setServicefee(double servicefee) {
		this.servicefee = servicefee;
	}
	
	/**counterfee: 手续费*/
	public double getCounterfee() {
		return counterfee;
	}

	/**counterfee: 手续费*/
	public void setCounterfee(double counterfee) {
		this.counterfee = counterfee;
	}
	
	/**otherfee: 其他费用*/
	public double getOtherfee() {
		return otherfee;
	}

	/**otherfee: 其他费用*/
	public void setOtherfee(double otherfee) {
		this.otherfee = otherfee;
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
	

	public CustomerAgencyFeescaleDto(){
	
	}
	public int getRiskGradeId() {
		return riskGradeId;
	}

	public void setRiskGradeId(int riskGradeId) {
		this.riskGradeId = riskGradeId;
	}
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
}