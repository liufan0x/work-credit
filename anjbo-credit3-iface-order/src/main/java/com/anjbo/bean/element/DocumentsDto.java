/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.bean.element;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import com.anjbo.bean.BaseDto;
import com.anjbo.utils.StringUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 18:24:30
 * @version 1.0
 */
@ApiModel(value = "订单要件管理")
public class DocumentsDto extends BaseDto{
	
	private static final long serialVersionUID = 1L;
		
	/** 赎楼方式*/
	@ApiModelProperty(value = "赎楼方式")
    private ForeclosureTypeDto foreclosureType;
	
    /**回款方式 */
	@ApiModelProperty(value = "回款方式")
    private PaymentTypeDto paymentType;

	/** 订单要件管理 */
	@ApiModelProperty(value = "订单要件管理")
	private java.lang.Integer id;
	
	/** 订单编号 */
	@ApiModelProperty(value = "订单编号")
	private java.lang.String orderNo;
	
	/** 状态（1初始状态 2.通过钉钉特批，3通过系统校验） */
	@ApiModelProperty(value = "状态（1初始状态 2.通过钉钉特批，3通过系统校验）")
	private java.lang.Integer status;
	
	/** 特批上传图片URL */
	@ApiModelProperty(value = "特批上传图片URL")
	private java.lang.String greenStatusImgUrl;
	
	 /**将要件特批上传的图片按;切割*/
	@ApiModelProperty(value = "将要件特批上传的图片按;切割")
    private List<String> imgUrl;
	
	/** 备注 */
	@ApiModelProperty(value = "备注")
	private java.lang.String remark;
	
	/** 处理人 */
	@ApiModelProperty(value = "处理人")
	private java.lang.String handleUid;
	
	public List<String> getImgUrl() {
		if(StringUtil.isNotBlank(this.greenStatusImgUrl)){
			String tmp = greenStatusImgUrl.replaceAll("_18", "_48").replaceAll(";", ",");
			String[] str = tmp.split(",");
			imgUrl = Arrays.asList(str);
		}
		return imgUrl;
	}

	public void setImgUrl(List<String> imgUrl) {
		this.imgUrl = imgUrl;
	}
	public ForeclosureTypeDto getForeclosureType() {
		return foreclosureType;
	}
	public void setForeclosureType(ForeclosureTypeDto foreclosureType) {
		this.foreclosureType = foreclosureType;
	}
	public PaymentTypeDto getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(PaymentTypeDto paymentType) {
		this.paymentType = paymentType;
	}
	public void setId(java.lang.Integer value) {
		this.id = value;
	}	
	public java.lang.Integer getId() {
		return this.id;
	}
	public void setOrderNo(java.lang.String value) {
		this.orderNo = value;
	}	
	public java.lang.String getOrderNo() {
		return this.orderNo;
	}
	public void setStatus(java.lang.Integer value) {
		this.status = value;
	}	
	public java.lang.Integer getStatus() {
		return this.status;
	}
	public void setGreenStatusImgUrl(java.lang.String value) {
		this.greenStatusImgUrl = value;
	}	
	public java.lang.String getGreenStatusImgUrl() {
		return this.greenStatusImgUrl;
	}
	public void setRemark(java.lang.String value) {
		this.remark = value;
	}	
	public java.lang.String getRemark() {
		return this.remark;
	}
	public void setHandleUid(java.lang.String value) {
		this.handleUid = value;
	}	
	public java.lang.String getHandleUid() {
		return this.handleUid;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("orderNo",getOrderNo())
			.append("status",getStatus())
			.append("greenStatusImgUrl",getGreenStatusImgUrl())
			.append("remark",getRemark())
			.append("handleUid",getHandleUid())
			.toString();
	}
}

