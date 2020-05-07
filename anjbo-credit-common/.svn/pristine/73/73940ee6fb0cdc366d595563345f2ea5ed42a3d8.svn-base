package com.anjbo.bean.order;

import java.text.NumberFormat;

import org.apache.commons.lang3.StringUtils;

import com.anjbo.bean.BaseDto;
/**
 * 担保人信息
 * @author liuf
 *
 */
public class OrderBaseCustomerGuaranteeDto extends BaseDto{

	private static final long serialVersionUID = 1L;

	/**担保人信息*/
	private Integer id;
	/**订单编号*/
	private String orderNo;
	/**姓名*/
	private String guaranteeName;
	/**手机号码*/
	private String guaranteePhone;
	/**与借款人关系*/
	private String guaranteeRelationship;
	/**证件类型*/
	private String guaranteeCardType;
	/**证件号码*/
	private String guaranteeCardNumber;
	/**担保方式*/
	private String guaranteeType;
	/**产权证类型*/
	private String guaranteePropertyType;
	/**建筑面积*/
	private String guaranteeAPropertyrchitectureSize;
	/**是否产权人(是,否)*/
	private String guaranteeIsPropertyProple;
	/** 是否完成1:是,2:否 */
	private Integer isFinish;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getGuaranteeName() {
		return guaranteeName;
	}
	public void setGuaranteeName(String guaranteeName) {
		this.guaranteeName = guaranteeName;
	}
	public String getGuaranteePhone() {
		return guaranteePhone;
	}
	public void setGuaranteePhone(String guaranteePhone) {
		this.guaranteePhone = guaranteePhone;
	}
	public String getGuaranteeRelationship() {
		return guaranteeRelationship;
	}
	public void setGuaranteeRelationship(String guaranteeRelationship) {
		this.guaranteeRelationship = guaranteeRelationship;
	}
	public String getGuaranteeCardType() {
		return guaranteeCardType;
	}
	public void setGuaranteeCardType(String guaranteeCardType) {
		this.guaranteeCardType = guaranteeCardType;
	}
	public String getGuaranteeCardNumber() {
		return guaranteeCardNumber;
	}
	public void setGuaranteeCardNumber(String guaranteeCardNumber) {
		this.guaranteeCardNumber = guaranteeCardNumber;
	}
	public String getGuaranteeType() {
		return guaranteeType;
	}
	public void setGuaranteeType(String guaranteeType) {
		this.guaranteeType = guaranteeType;
	}
	public String getGuaranteePropertyType() {
		return guaranteePropertyType;
	}
	public void setGuaranteePropertyType(String guaranteePropertyType) {
		this.guaranteePropertyType = guaranteePropertyType;
	}
	public String getGuaranteeAPropertyrchitectureSize() {
		if(StringUtils.isNotBlank(guaranteeAPropertyrchitectureSize)){
			NumberFormat nfmt = NumberFormat.getInstance();
			nfmt.setMaximumFractionDigits(4);
			nfmt.setGroupingUsed(false);
			return nfmt.format(Double.valueOf(guaranteeAPropertyrchitectureSize));
		}
		return guaranteeAPropertyrchitectureSize;
	}
	public void setGuaranteeAPropertyrchitectureSize(
			String guaranteeAPropertyrchitectureSize) {
		this.guaranteeAPropertyrchitectureSize = guaranteeAPropertyrchitectureSize;
	}
	public String getGuaranteeIsPropertyProple() {
		return guaranteeIsPropertyProple;
	}
	public void setGuaranteeIsPropertyProple(String guaranteeIsPropertyProple) {
		this.guaranteeIsPropertyProple = guaranteeIsPropertyProple;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public OrderBaseCustomerGuaranteeDto() {
		super();
	}
	public Integer getIsFinish() {
		return isFinish;
	}
	public void setIsFinish(Integer isFinish) {
		this.isFinish = isFinish;
	}
	
}
