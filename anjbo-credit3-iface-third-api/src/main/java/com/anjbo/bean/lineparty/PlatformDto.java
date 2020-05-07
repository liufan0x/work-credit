package com.anjbo.bean.lineparty;

import com.anjbo.bean.BaseDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "平台信息")
public class PlatformDto extends BaseDto {
	 @ApiModelProperty(value = "平台信息id")
	 private Integer id;
	 
	 @ApiModelProperty(value = "证件类型")
	 private String idCardType;
	  
	 @ApiModelProperty(value = "证件号码")
	 private String idCardNumber;
	 
	 @ApiModelProperty(value = "客户姓名")
	 private String customerName;
	 
	 @ApiModelProperty(value = "保单文件流")
	 private String insuranceFile;
	 
	 @ApiModelProperty(value = "状态")
	 private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIdCardType() {
		return idCardType;
	}

	public void setIdCardType(String idCardType) {
		this.idCardType = idCardType;
	}

	public String getIdCardNumber() {
		return idCardNumber;
	}

	public void setIdCardNumber(String idCardNumber) {
		this.idCardNumber = idCardNumber;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getInsuranceFile() {
		return insuranceFile;
	}

	public void setInsuranceFile(String insuranceFile) {
		this.insuranceFile = insuranceFile;
	}
}
