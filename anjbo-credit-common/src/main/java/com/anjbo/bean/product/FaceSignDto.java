package com.anjbo.bean.product;

import java.util.Date;

import com.anjbo.bean.BaseDto;

/**
 * 面签信息
 * @author yis
 *
 */
public class FaceSignDto extends BaseDto{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 主键 */
	private Integer id;
	/** 订单编号 */
	private String orderNo;
	
	/** 面签时间 */
	private Date faceSignTime;
	/** 面签照片地址,多个地址使用逗号隔开 */
	private String faceSignPhoto;
	
	private String remark;

	private String faceSignTimeStr;
	
	/**
	 * 是否有人脸识别0未识别,1有并成功,2有并失败,3该地区不需要人脸识别
	 */
	private int isFace;
	
	/**
	 * 账户类型
	 */
	private java.lang.String accountType;
	
	/**
	 * 客户姓名
	 */
	private java.lang.String customerName;
	
	/**
	 * 账户所属银行
	 */
	private java.lang.String bankName;
	
	/**
	 * 账户所属银行id
	 */
	private java.lang.String bankNameId;
	
	/**
	 * 银行账户号
	 */
	private java.lang.String bankCardNo;
	
	/**
	 * 证件类型
	 */
	private java.lang.String certificateType;
	
	/**
	 * 证件号码
	 */
	private java.lang.String certificateNo;
	
	/**
	 * 银行预留手机号
	 */
	private java.lang.String mobile;
	
	/**
	 * 绑定支付渠道1签约流水号
	 */
	private java.lang.String serialNoOne;
	
	/**
	 * 绑定支付渠道2签约流水号
	 */
	private java.lang.String serialNoTwo;
	
	/**
	 * 短信验证码
	 */
	private java.lang.String msgCode;
	
	/**
	 * 绑定支付渠道1
	 */
	private Integer qOne;
	
	/**
	 * 绑定支付渠道2
	 */
	private Integer qTwo;
	
	/**
	 * 绑定支付渠道1结果
	 */
	private java.lang.String returnOne;
	
	/**
	 * 绑定支付渠道2结果
	 */
	private java.lang.String returnTwo;
	
	public String getFaceSignTimeStr() {
		return faceSignTimeStr;
	}

	public void setFaceSignTimeStr(String faceSignTimeStr) {
		this.faceSignTimeStr = faceSignTimeStr;
	}

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

	public Date getFaceSignTime() {
		return faceSignTime;
	}

	public void setFaceSignTime(Date faceSignTime) {
		this.faceSignTime = faceSignTime;
	}

	public String getFaceSignPhoto() {
		return faceSignPhoto;
	}

	public void setFaceSignPhoto(String faceSignPhoto) {
		this.faceSignPhoto = faceSignPhoto;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getIsFace() {
		return isFace;
	}

	public void setIsFace(int isFace) {
		this.isFace = isFace;
	}

	public java.lang.String getAccountType() {
		return accountType;
	}

	public void setAccountType(java.lang.String accountType) {
		this.accountType = accountType;
	}

	public java.lang.String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(java.lang.String customerName) {
		this.customerName = customerName;
	}

	public java.lang.String getBankName() {
		return bankName;
	}

	public void setBankName(java.lang.String bankName) {
		this.bankName = bankName;
	}

	public java.lang.String getBankNameId() {
		return bankNameId;
	}

	public void setBankNameId(java.lang.String bankNameId) {
		this.bankNameId = bankNameId;
	}

	public java.lang.String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(java.lang.String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

	public java.lang.String getCertificateType() {
		return certificateType;
	}

	public void setCertificateType(java.lang.String certificateType) {
		this.certificateType = certificateType;
	}

	public java.lang.String getCertificateNo() {
		return certificateNo;
	}

	public void setCertificateNo(java.lang.String certificateNo) {
		this.certificateNo = certificateNo;
	}

	public java.lang.String getMobile() {
		return mobile;
	}

	public void setMobile(java.lang.String mobile) {
		this.mobile = mobile;
	}

	public java.lang.String getSerialNoOne() {
		return serialNoOne;
	}

	public void setSerialNoOne(java.lang.String serialNoOne) {
		this.serialNoOne = serialNoOne;
	}

	public java.lang.String getSerialNoTwo() {
		return serialNoTwo;
	}

	public void setSerialNoTwo(java.lang.String serialNoTwo) {
		this.serialNoTwo = serialNoTwo;
	}

	public java.lang.String getMsgCode() {
		return msgCode;
	}

	public void setMsgCode(java.lang.String msgCode) {
		this.msgCode = msgCode;
	}

	public Integer getqOne() {
		return qOne;
	}

	public void setqOne(Integer qOne) {
		this.qOne = qOne;
	}

	public Integer getqTwo() {
		return qTwo;
	}

	public void setqTwo(Integer qTwo) {
		this.qTwo = qTwo;
	}

	public java.lang.String getReturnOne() {
		return returnOne;
	}

	public void setReturnOne(java.lang.String returnOne) {
		this.returnOne = returnOne;
	}

	public java.lang.String getReturnTwo() {
		return returnTwo;
	}

	public void setReturnTwo(java.lang.String returnTwo) {
		this.returnTwo = returnTwo;
	}

}
