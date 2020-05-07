/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.bean.sgtong;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import com.anjbo.bean.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-12-12 14:16:18
 * @version 1.0
 */
@ApiModel(value = "抵押人信息表")
public class SgtongMortgagorInformationDto extends BaseDto{
	
	private static final long serialVersionUID = 1L;
	private java.lang.Integer count;
	public java.lang.Integer getCount() {
		return count;
	}
	public void setCount(java.lang.Integer count) {
		this.count = count;
	}


	/** 抵押人信息表 */
	@ApiModelProperty(value = "抵押人信息表")
	private java.lang.Integer id;
	
	/** orderNo */
	@ApiModelProperty(value = "orderNo")
	private java.lang.String orderNo;
	
	/** 押品所有权人名称 */
	@ApiModelProperty(value = "押品所有权人名称")
	private java.lang.String gcustName;
	
	/** 押品所有权人证件类型0身份证1户口簿2护照3军官证4士兵证5港澳居民来往内地通行证6台湾同胞来往内地通行证7临时身份证8外国人居留证9警官证A香港身份证B澳门身份证C台湾身份证X其他证件;Z组织机构代码 */
	@ApiModelProperty(value = "押品所有权人证件类型0身份证1户口簿2护照3军官证4士兵证5港澳居民来往内地通行证6台湾同胞来往内地通行证7临时身份证8外国人居留证9警官证A香港身份证B澳门身份证C台湾身份证X其他证件;Z组织机构代码")
	private java.lang.String gcustIdtype;
	
	/** 押品所有权人证件 */
	@ApiModelProperty(value = "押品所有权人证件")
	private java.lang.String gcustIdno;
	
	/** 所有权人职业0-国家机关、党群组织、企业、事业单位负责人 1-专业技术人员 3-办事人员和有关人员 4-商业、服务业人员 5-农、林、牧、渔、水利业生产人员 6-生产、运输设备操作人员及有关人员 X-军人 Y-不便分类的其他从业人员 Z-未知 */
	@ApiModelProperty(value = "所有权人职业0-国家机关、党群组织、企业、事业单位负责人 1-专业技术人员 3-办事人员和有关人员 4-商业、服务业人员 5-农、林、牧、渔、水利业生产人员 6-生产、运输设备操作人员及有关人员 X-军人 Y-不便分类的其他从业人员 Z-未知")
	private java.lang.String gworkType;
	
	/** 押品类型199-其他质押 213-住房抵押 218-交通工具 299-其他抵押 */
	@ApiModelProperty(value = "押品类型199-其他质押 213-住房抵押 218-交通工具 299-其他抵押")
	private java.lang.String gtype;
	
	/** 押品名称 */
	@ApiModelProperty(value = "押品名称")
	private java.lang.String gname;
	
	/** 押品评估价值 */
	@ApiModelProperty(value = "押品评估价值")
	private java.lang.String gvalue;
	
	/** 权证号码 */
	@ApiModelProperty(value = "权证号码")
	private java.lang.String glicno;
	
	/** 权证类型01-房产证 02-机动车登记证书 */
	@ApiModelProperty(value = "权证类型01-房产证 02-机动车登记证书")
	private java.lang.String glicType;
	
	/** 前手抵押余额该押品前手抵押后的剩余余额 */
	@ApiModelProperty(value = "前手抵押余额该押品前手抵押后的剩余余额")
	private java.lang.String gbegBal;
	
	/** 押品小类01-普通住宅 02-非普通住宅-经济适用房或保障房 03-非普通住宅-别墅 04-商铺 05-其他房屋 06-汽车 07-其他抵押 08-其他质押 09-商住 */
	@ApiModelProperty(value = "押品小类01-普通住宅 02-非普通住宅-经济适用房或保障房 03-非普通住宅-别墅 04-商铺 05-其他房屋 06-汽车 07-其他抵押 08-其他质押 09-商住")
	private java.lang.String gsmType;
	
	/** 是否有车1-是 0-否 2-未知 */
	@ApiModelProperty(value = "是否有车1-是 0-否 2-未知")
	private java.lang.Integer ifCar;
	
	/** 是否有按揭车贷1-是 0-否 2-未知 */
	@ApiModelProperty(value = "是否有按揭车贷1-是 0-否 2-未知")
	private java.lang.Integer ifCarCred;
	
	/** 是否有房1-是 0-否 2-未知 */
	@ApiModelProperty(value = "是否有房1-是 0-否 2-未知")
	private java.lang.Integer ifRoom;
	
	/** 是否有按揭房贷1-是 0-否 2-未知 */
	@ApiModelProperty(value = "是否有按揭房贷1-是 0-否 2-未知")
	private java.lang.Integer ifMort;
	
	/** 是否有贷记卡1-是 0-否 2-未知 */
	@ApiModelProperty(value = "是否有贷记卡1-是 0-否 2-未知")
	private java.lang.Integer ifCard;
	
	/** 贷记卡最低额度 */
	@ApiModelProperty(value = "贷记卡最低额度")
	private java.lang.String cardAmt;
	
	/** 是否填写申请表1-是 0-否 2-未知 */
	@ApiModelProperty(value = "是否填写申请表1-是 0-否 2-未知")
	private java.lang.Integer ifApp;
	
	/** 是否以签订合同1-是 0-否 2-未知 */
	@ApiModelProperty(value = "是否以签订合同1-是 0-否 2-未知")
	private java.lang.Integer ifPact;
	
	/** 是否有身份证信息1-是 0-否 2-未知 */
	@ApiModelProperty(value = "是否有身份证信息1-是 0-否 2-未知")
	private java.lang.Integer ifId;
	

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
	public void setGcustName(java.lang.String value) {
		this.gcustName = value;
	}	
	public java.lang.String getGcustName() {
		return this.gcustName;
	}
	public void setGcustIdtype(java.lang.String value) {
		this.gcustIdtype = value;
	}	
	public java.lang.String getGcustIdtype() {
		return this.gcustIdtype;
	}
	public void setGcustIdno(java.lang.String value) {
		this.gcustIdno = value;
	}	
	public java.lang.String getGcustIdno() {
		return this.gcustIdno;
	}
	public void setGworkType(java.lang.String value) {
		this.gworkType = value;
	}	
	public java.lang.String getGworkType() {
		return this.gworkType;
	}
	public void setGtype(java.lang.String value) {
		this.gtype = value;
	}	
	public java.lang.String getGtype() {
		return this.gtype;
	}
	public void setGname(java.lang.String value) {
		this.gname = value;
	}	
	public java.lang.String getGname() {
		return this.gname;
	}
	public void setGvalue(java.lang.String value) {
		this.gvalue = value;
	}	
	public java.lang.String getGvalue() {
		return this.gvalue;
	}
	public void setGlicno(java.lang.String value) {
		this.glicno = value;
	}	
	public java.lang.String getGlicno() {
		return this.glicno;
	}
	public void setGlicType(java.lang.String value) {
		this.glicType = value;
	}	
	public java.lang.String getGlicType() {
		return this.glicType;
	}
	public void setGbegBal(java.lang.String value) {
		this.gbegBal = value;
	}	
	public java.lang.String getGbegBal() {
		return this.gbegBal;
	}
	public void setGsmType(java.lang.String value) {
		this.gsmType = value;
	}	
	public java.lang.String getGsmType() {
		return this.gsmType;
	}
	public void setIfCar(java.lang.Integer value) {
		this.ifCar = value;
	}	
	public java.lang.Integer getIfCar() {
		return this.ifCar;
	}
	public void setIfCarCred(java.lang.Integer value) {
		this.ifCarCred = value;
	}	
	public java.lang.Integer getIfCarCred() {
		return this.ifCarCred;
	}
	public void setIfRoom(java.lang.Integer value) {
		this.ifRoom = value;
	}	
	public java.lang.Integer getIfRoom() {
		return this.ifRoom;
	}
	public void setIfMort(java.lang.Integer value) {
		this.ifMort = value;
	}	
	public java.lang.Integer getIfMort() {
		return this.ifMort;
	}
	public void setIfCard(java.lang.Integer value) {
		this.ifCard = value;
	}	
	public java.lang.Integer getIfCard() {
		return this.ifCard;
	}
	public void setCardAmt(java.lang.String value) {
		this.cardAmt = value;
	}	
	public java.lang.String getCardAmt() {
		return this.cardAmt;
	}
	public void setIfApp(java.lang.Integer value) {
		this.ifApp = value;
	}	
	public java.lang.Integer getIfApp() {
		return this.ifApp;
	}
	public void setIfPact(java.lang.Integer value) {
		this.ifPact = value;
	}	
	public java.lang.Integer getIfPact() {
		return this.ifPact;
	}
	public void setIfId(java.lang.Integer value) {
		this.ifId = value;
	}	
	public java.lang.Integer getIfId() {
		return this.ifId;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("orderNo",getOrderNo())
			.append("gcustName",getGcustName())
			.append("gcustIdtype",getGcustIdtype())
			.append("gcustIdno",getGcustIdno())
			.append("gworkType",getGworkType())
			.append("gtype",getGtype())
			.append("gname",getGname())
			.append("gvalue",getGvalue())
			.append("glicno",getGlicno())
			.append("glicType",getGlicType())
			.append("gbegBal",getGbegBal())
			.append("gsmType",getGsmType())
			.append("ifCar",getIfCar())
			.append("ifCarCred",getIfCarCred())
			.append("ifRoom",getIfRoom())
			.append("ifMort",getIfMort())
			.append("ifCard",getIfCard())
			.append("cardAmt",getCardAmt())
			.append("ifApp",getIfApp())
			.append("ifPact",getIfPact())
			.append("ifId",getIfId())
			.toString();
	}
}

