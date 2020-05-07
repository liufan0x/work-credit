package com.anjbo.bean.product;

import java.util.Date;

import com.anjbo.bean.BaseDto;

/**
 * 人脸识别信息
 * @author Administrator
 *
 */
public class FacesignRecognitionDto extends BaseDto{

	private int id;
	/**订单编号*/
	private String orderNo;
	/**客户证件号码**/
	private String customerCardNumber;
	/**客户姓名*/
	private String customerName;
	/**是否识别成功(1:成功,2:失败)*/
	private int isSuccess;
	/**客户类型*/
	private String customerType;
	/**百度磐石返回的key*/
	private String callbackkey;
	/**识别人身份证图片*/
	private String imageUrl;
	/**分数*/
	private String score;
	
	private String exuid;
	
	private String osType;
	
	private String terminalType;
	
	private String city;
	
	private String appSource;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getCustomerCardNumber() {
		return customerCardNumber;
	}
	public void setCustomerCardNumber(String customerCardNumber) {
		this.customerCardNumber = customerCardNumber;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public int getIsSuccess() {
		return isSuccess;
	}
	public void setIsSuccess(int isSuccess) {
		this.isSuccess = isSuccess;
	}
	
	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	public String getCallbackkey() {
		return callbackkey;
	}
	public void setCallbackkey(String callbackkey) {
		this.callbackkey = callbackkey;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getExuid() {
		return exuid;
	}
	public void setExuid(String exuid) {
		this.exuid = exuid;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getOsType() {
		return osType;
	}
	public void setOsType(String osType) {
		this.osType = osType;
	}
	public String getTerminalType() {
		return terminalType;
	}
	public void setTerminalType(String terminalType) {
		this.terminalType = terminalType;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getAppSource() {
		return appSource;
	}
	public void setAppSource(String appSource) {
		this.appSource = appSource;
	}
	
}
