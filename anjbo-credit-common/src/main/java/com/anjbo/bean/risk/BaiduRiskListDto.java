package com.anjbo.bean.risk;

import java.util.Date;

import com.anjbo.bean.BaseDto;

public class BaiduRiskListDto extends BaseDto{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**主键**/
	private Integer id;
	/**关联订单号*/
	private String orderNo;
	/**姓名*/
	private String name;
	/**身份证号**/
	private String identity;
	/**手机号**/
	private String phone;
	/**风险名单等级*/
	private String blackLevel;
	/**风险名单原因*/
	private String blackReason;
	/**风险名单详情*/
	private String blackDetails;
	/**创建时间**/
	private Date createTime;
	/**创建时间字符串表示*/
	private String createTimeStr;
	/**更新时间**/
	private Date updateTime;
	/**创建人uid*/
	private String createUid;
	/**创建人*/
	private String createName;
	public BaiduRiskListDto() {
		super();
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getBlackLevel() {
		return blackLevel;
	}
	public void setBlackLevel(String blackLevel) {
		this.blackLevel = blackLevel;
	}
	public String getBlackReason() {
		return blackReason;
	}
	public void setBlackReason(String blackReason) {
		this.blackReason = blackReason;
	}
	public String getBlackDetails() {
		return blackDetails;
	}
	public void setBlackDetails(String blackDetails) {
		this.blackDetails = blackDetails;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getCreateUid() {
		return createUid;
	}
	public void setCreateUid(String createUid) {
		this.createUid = createUid;
	}
	public String getCreateName() {
		return createName;
	}
	public void setCreateName(String createName) {
		this.createName = createName;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getIdentity() {
		return identity;
	}
	public void setIdentity(String identity) {
		this.identity = identity;
	}
	public String getCreateTimeStr() {
		return createTimeStr;
	}
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	} 
	
	
}
