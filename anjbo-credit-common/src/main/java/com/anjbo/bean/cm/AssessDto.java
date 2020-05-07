package com.anjbo.bean.cm;

import java.math.BigDecimal;
import java.util.Date;

import com.anjbo.bean.BaseDto;

public class AssessDto extends BaseDto{
	private int id;  					//评估基本表
	private String uid;  				//操作人UID
	private String orderNo; 			//订单编号
	private String appNo;  				//申请编号（由建行返回作为标识）
	
	private String branchCompany;  		//分公司（营业部）
	private String channelManagerUid;	//渠道经理uid
	private String cooperativeAgencyId; //合作机构Id
	private int bankId;  			    //银行id（关联tbl_cm_system_bank）
	private int subBankId; 			 	//支行id（关联tbl_cm_system_bank_sub）
	
	private String custName;  			//贷款申请人
	private String certificateType;  	//证件类型（买方）
	private String certificateNo;  		//证件号码（买方）
	
	private String propertyName;  		//物业名称
	private String buildingName;  		//楼栋
	private String roomName;  			//房号
	
	private String estateNo;  			//房产证号
	private String estateImg; 			//房产证图片
	private int estateType;  			//产权证书类型（0房地产权证书 1不动产权证书）
	private String yearNo;  			//粤（不动产权证书要用）
	
	private String custManagerName;  	//客户经理姓名
	private String custManagerMobile;  	//客户经理手机号
	private String agentName;  			//经办人姓名
	private String agentMobile;  		//经办人手机号
	private String ownerName; 			//业主姓名
	private String ownerCertificateType;//业主证件类型
	private String ownerCertificateNo;  //业主证件号码
	private BigDecimal actPrice; 			//实际成交价
	
	private Date createTime;  			//创建时间
	private String accreditStatus; 		//授权查征信状态 0未授权 1已授权
	private int status;  				//状态（0初始状态 1评估中 2评估成功 3评估失败、4订单关闭）
	private int source;  				//来源（1快鸽APP）
	private String remark;  			//备注

	public String getChannelManagerUid() {
		return channelManagerUid;
	}
	public void setChannelManagerUid(String channelManagerUid) {
		this.channelManagerUid = channelManagerUid;
	}
	public String getCooperativeAgencyId() {
		return cooperativeAgencyId;
	}
	public void setCooperativeAgencyId(String cooperativeAgencyId) {
		this.cooperativeAgencyId = cooperativeAgencyId;
	}
	public String getEstateImg() {
		return estateImg;
	}
	public void setEstateImg(String estateImg) {
		this.estateImg = estateImg;
	}
	public String getBranchCompany() {
		return branchCompany;
	}
	public void setBranchCompany(String branchCompany) {
		this.branchCompany = branchCompany;
	}
	public int getSource() {
		return source;
	}
	public void setSource(int source) {
		this.source = source;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public String getOwnerCertificateType() {
		return ownerCertificateType;
	}
	public void setOwnerCertificateType(String ownerCertificateType) {
		this.ownerCertificateType = ownerCertificateType;
	}
	public String getOwnerCertificateNo() {
		return ownerCertificateNo;
	}
	public void setOwnerCertificateNo(String ownerCertificateNo) {
		this.ownerCertificateNo = ownerCertificateNo;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getAppNo() {
		return appNo;
	}
	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}
	public int getBankId() {
		return bankId;
	}
	public void setBankId(int bankId) {
		this.bankId = bankId;
	}
	public int getSubBankId() {
		return subBankId;
	}
	public void setSubBankId(int subBankId) {
		this.subBankId = subBankId;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getCertificateType() {
		return certificateType;
	}
	public void setCertificateType(String certificateType) {
		this.certificateType = certificateType;
	}
	public String getCertificateNo() {
		return certificateNo;
	}
	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}
	public String getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	public String getBuildingName() {
		return buildingName;
	}
	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public int getEstateType() {
		return estateType;
	}
	public void setEstateType(int estateType) {
		this.estateType = estateType;
	}
	public String getYearNo() {
		return yearNo;
	}
	public void setYearNo(String yearNo) {
		this.yearNo = yearNo;
	}
	public String getEstateNo() {
		return estateNo;
	}
	public void setEstateNo(String estateNo) {
		this.estateNo = estateNo;
	}
	public String getCustManagerName() {
		return custManagerName;
	}
	public void setCustManagerName(String custManagerName) {
		this.custManagerName = custManagerName;
	}
	public String getCustManagerMobile() {
		return custManagerMobile;
	}
	public void setCustManagerMobile(String custManagerMobile) {
		this.custManagerMobile = custManagerMobile;
	}
	public String getAgentName() {
		return agentName;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	public String getAgentMobile() {
		return agentMobile;
	}
	public void setAgentMobile(String agentMobile) {
		this.agentMobile = agentMobile;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public BigDecimal getActPrice() {
		return actPrice;
	}
	public void setActPrice(BigDecimal actPrice) {
		this.actPrice = actPrice;
	}
	public String getAccreditStatus() {
		return accreditStatus;
	}
	public void setAccreditStatus(String accreditStatus) {
		this.accreditStatus = accreditStatus;
	}

}