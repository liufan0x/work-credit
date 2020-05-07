package com.anjbo.bean.product;

import java.util.Date;

import com.anjbo.bean.BaseDto;

/**
 * 公证信息
 * 
 * @author yis
 *
 */
public class NotarizationDto extends BaseDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 公证主键 */
	private Integer id;
	/** 订单编号 */
	private String orderNo;
	/** 公证时间 */
	private Date notarizationTime;
	/** 公证照片 */
	private String notarizationImg;
	/** 预计出款日期 */
	private Date estimatedTime;
	/** 公证地址 */
	private String notarizationAddress;
	private String notarizationAddressCode;

	private String remark;
	private String code;

	private String notarizationTimeStr;
	private String estimatedTimeStr;
	private String notarizationType;  //公证类型
	public String getNotarizationType() {
		return notarizationType;
	}

	public void setNotarizationType(String notarizationType) {
		this.notarizationType = notarizationType;
	}
	
	public String getNotarizationAddressCode() {
		return notarizationAddressCode;
	}
	public void setNotarizationAddressCode(String notarizationAddressCode) {
		this.notarizationAddressCode = notarizationAddressCode;
	}

	public String getNotarizationTimeStr() {
		return notarizationTimeStr;
	}

	public void setNotarizationTimeStr(String notarizationTimeStr) {
		this.notarizationTimeStr = notarizationTimeStr;
	}

	public String getEstimatedTimeStr() {
		return estimatedTimeStr;
	}

	public void setEstimatedTimeStr(String estimatedTimeStr) {
		this.estimatedTimeStr = estimatedTimeStr;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getNotarizationImg() {
		return notarizationImg;
	}

	public void setNotarizationImg(String notarizationImg) {
		this.notarizationImg = notarizationImg;
	}

	public Date getNotarizationTime() {
		return notarizationTime;
	}

	public void setNotarizationTime(Date notarizationTime) {
		this.notarizationTime = notarizationTime;
	}

	public Date getEstimatedTime() {
		return estimatedTime;
	}

	public void setEstimatedTime(Date estimatedTime) {
		this.estimatedTime = estimatedTime;
	}

	public String getNotarizationAddress() {
		return notarizationAddress;
	}

	public void setNotarizationAddress(String notarizationAddress) {
		this.notarizationAddress = notarizationAddress;
	}

	

}
