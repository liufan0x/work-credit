package com.anjbo.bean.tools;

import java.util.Date;
import com.anjbo.bean.BaseDto;

/**
 * 查过户价/税费
 * @author limh limh@zxsf360.com
 * @date 2015-9-8 下午04:46:18
 */
public class TransferPrice extends BaseDto {
	private int id;
	private String uid;
	/**0查过户价/1查过户价并计算税费**/
	private int type;
	/**权利人（个人/单位）**/
	private String obligee;
	/**身份证号（权利人为个人）**/
	private String identityNo;
	/**单位名称（权利人为单位）**/
	private String unitName;
	/**证书类别（房地产权证书/不动产权证书）**/
	private String estateType;
	/**房产证号**/
	private String estateNo;
	/**建筑面积（单位：㎡）**/
	private double area;
	/**购房期限(0未满两年，1满两年，2满五年)**/
	private int range;
	private String rangeName;
	/**房产证登记价（单位：元）**/
	private double regPrice;
	/**住宅类型（0普通住宅，1非普通住宅，2拍卖类，3商务公寓）**/
	private int residenceType;
	private String residenceTypeName;
	/**是否唯一住房（0否 1是）**/
	private int onlyHouse;
	/**买方是否家庭首套（0否 1是）**/
	private int firstHouse;
	/**过户价（单位：元）**/
	private double transferPrice;
	/**税费合计(范围值）**/
	private String tax;
	/**查询结果**/
	private String result;
	/**状态（0查询成功 1查询失败 2已计算税费）**/
	private int status;
	/**来源(0深圳市房地产信息系统)**/
	private int source;
	/**个税·核定（单位：元）**/
	private double incomeTax;
	/**个税·核实（单位：元）**/
	private double incomeTaxHs;
	/**土地增值税（单位：元）范围值**/
	private String landTax;
	/**营业税（单位：元）**/
	private double salesTax;
	/**契税（单位：元）**/
	private double deedTax;
	/**交易手续费（单位：元）**/
	private double tranFees;
	/**登记费（单位：元）**/
	private double regFees;
	/**抵押登记费（单位：元）**/
	private double pledgeRegFees;
	/**印花（单位：元）**/
	private double stamp;
	/**贴花（单位：元）**/
	private double applique;
	private Date createTime;
	private String createTimeStr;
	private Date lastUpdateTime;
	private String authCode;
	private String imgCodeUrl;

	private String cookieValue;

	private String selectParam;
	/**所在区域**/
	private String szArea;
	/**套内面积**/
	private double withinArea;
	/**城市维护建设税**/
	private double cityBuildTax;
	/**教育费附加**/
	private double educationFees;
	/**地方教育附加**/
	private double placeEducationFees;
	
	private String title;
	
	/**含税成交价格**/
	private double transactionPrice;
	/**
	 * 是否首套（0：首套   1：两套及以下  2：三套以上）用户惠州地区
	 */
	private int isFirstHouse;
	/**
	 * 增值税
	 */
	private double valueAddedTax;
	
	/**
	 * 税收合计
	 */
	private double totalTax;
	
	/**是否计算三价合一（0否 1是）**/
	private int isCalcT;
	/**过户价（银行核准，单位：元）**/
	private double transferPriceBank;
	/**三价合一·税费合计(范围值）**/
	private String taxBank;
	/**三价合一·个税·核定（单位：元）**/
	private double incomeTaxBank;
	/**三价合一·个税·核实（单位：元）**/
	private double incomeTaxHsBank;
	/**三价合一·土地增值税（单位：元）范围值**/
	private String landTaxBank;
	/**三价合一·营业税（单位：元）**/
	private double salesTaxBank;
	/**三价合一·契税（单位：元）**/
	private double deedTaxBank;
	/**三价合一·交易手续费（单位：元）**/
	private double tranFeesBank;
	/**三价合一·登记费（单位：元）**/
	private double regFeesBank;
	/**三价合一·抵押登记费（单位：元）**/
	private double pledgeRegFeesBank;
	/**三价合一·印花（单位：元）**/
	private double stampBank;
	/**三价合一·贴花（单位：元）**/
	private double appliqueBank;
	/**三价合一·城市维护建设税**/
	private double cityBuildTaxBank;
	/**三价合一·教育费附加**/
	private double educationFeesBank;
	/**三价合一·地方教育附加**/
	private double placeEducationFeesBank;

	public double getValueAddedTax() {
		return valueAddedTax;
	}

	public void setValueAddedTax(double valueAddedTax) {
		this.valueAddedTax = valueAddedTax;
	}

	public double getTotalTax() {
		return totalTax;
	}

	public void setTotalTax(double totalTax) {
		this.totalTax = totalTax;
	}

	public double getTransactionPrice() {
		return transactionPrice;
	}

	public void setTransactionPrice(double transactionPrice) {
		this.transactionPrice = transactionPrice;
	}

	public int getIsFirstHouse() {
		return isFirstHouse;
	}

	public void setIsFirstHouse(int isFirstHouse) {
		this.isFirstHouse = isFirstHouse;
	}

	public String getSzArea() {
		return szArea;
	}

	public void setSzArea(String szArea) {
		this.szArea = szArea;
	}


	public double getWithinArea() {
		return withinArea;
	}

	public void setWithinArea(double withinArea) {
		this.withinArea = withinArea;
	}

	public double getCityBuildTax() {
		return cityBuildTax;
	}

	public void setCityBuildTax(double cityBuildTax) {
		this.cityBuildTax = cityBuildTax;
	}

	public double getEducationFees() {
		return educationFees;
	}

	public void setEducationFees(double educationFees) {
		this.educationFees = educationFees;
	}

	public double getPlaceEducationFees() {
		return placeEducationFees;
	}

	public void setPlaceEducationFees(double placeEducationFees) {
		this.placeEducationFees = placeEducationFees;
	}

	public String getSelectParam() {
		return selectParam;
	}

	public void setSelectParam(String selectParam) {
		this.selectParam = selectParam;
	}

	public String getCookieValue() {
		return cookieValue;
	}

	public void setCookieValue(String cookieValue) {
		this.cookieValue = cookieValue;
	}

	public double getIncomeTaxHs() {
		return incomeTaxHs;
	}

	public void setIncomeTaxHs(double incomeTaxHs) {
		this.incomeTaxHs = incomeTaxHs;
	}

	public String getImgCodeUrl() {
		return imgCodeUrl;
	}

	public void setImgCodeUrl(String imgCodeUrl) {
		this.imgCodeUrl = imgCodeUrl;
	}

	/**不动产权证书粤号**/
	private String year = "2015";

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return "查询过户价/税费提醒";
	}

	/**购房期限(0未满两年，1满两年，2满五年)**/
	private String range(int range) {
		if (range == 0) {
			return "未满两年";
		} else if (range == 1) {
			return "满两年";
		} else if (range == 2) {
			return "满五年";
		}
		return "";
	}

	/**住宅类型（0普通住宅，1非普通住宅，2拍卖类，3商务公寓）**/
	private String residenceType(int residenceType) {
		if (residenceType == 0) {
			return "普通住宅";
		} else if (residenceType == 1) {
			return "非普通住宅";
		} else if (residenceType == 2) {
			return "拍卖类";
		} else if (residenceType == 3) {
			return "商务公寓";
		}
		return "";
	}

	public String getEstateType() {
		return estateType;
	}

	public void setEstateType(String estateType) {
		this.estateType = estateType;
	}

	public double getApplique() {
		return applique;
	}

	public void setApplique(double applique) {
		this.applique = applique;
	}

	public String getLandTax() {
		return landTax;
	}

	public void setLandTax(String landTax) {
		this.landTax = landTax;
	}

	public String getRangeName() {
		return rangeName;
	}

	public void setRangeName(String rangeName) {
		this.rangeName = rangeName;
	}

	public String getResidenceTypeName() {
		return residenceTypeName;
	}

	public void setResidenceTypeName(String residenceTypeName) {
		this.residenceTypeName = residenceTypeName;
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getObligee() {
		return obligee;
	}

	public void setObligee(String obligee) {
		this.obligee = obligee;
	}

	public String getIdentityNo() {
		return identityNo;
	}

	public void setIdentityNo(String identityNo) {
		this.identityNo = identityNo;
	}

	public String getEstateNo() {
		return estateNo;
	}

	public void setEstateNo(String estateNo) {
		this.estateNo = estateNo;
	}

	public double getArea() {
		return area;
	}

	public void setArea(double area) {
		this.area = area;
	}

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}

	public double getRegPrice() {
		return regPrice;
	}

	public void setRegPrice(double regPrice) {
		this.regPrice = regPrice;
	}

	public int getResidenceType() {
		return residenceType;
	}

	public void setResidenceType(int residenceType) {
		this.residenceType = residenceType;
	}

	public int getOnlyHouse() {
		return onlyHouse;
	}

	public void setOnlyHouse(int onlyHouse) {
		this.onlyHouse = onlyHouse;
	}

	public int getFirstHouse() {
		return firstHouse;
	}

	public void setFirstHouse(int firstHouse) {
		this.firstHouse = firstHouse;
	}

	public double getTransferPrice() {
		return transferPrice;
	}

	public void setTransferPrice(double transferPrice) {
		this.transferPrice = transferPrice;
	}

	public String getTax() {
		return tax;
	}

	public void setTax(String tax) {
		this.tax = tax;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getSource() {
		return source;
	}

	public void setSource(int source) {
		this.source = source;
	}

	public double getIncomeTax() {
		return incomeTax;
	}

	public void setIncomeTax(double incomeTax) {
		this.incomeTax = incomeTax;
	}

	public double getSalesTax() {
		return salesTax;
	}

	public void setSalesTax(double salesTax) {
		this.salesTax = salesTax;
	}

	public double getDeedTax() {
		return deedTax;
	}

	public void setDeedTax(double deedTax) {
		this.deedTax = deedTax;
	}

	public double getTranFees() {
		return tranFees;
	}

	public void setTranFees(double tranFees) {
		this.tranFees = tranFees;
	}

	public double getRegFees() {
		return regFees;
	}

	public void setRegFees(double regFees) {
		this.regFees = regFees;
	}

	public double getPledgeRegFees() {
		return pledgeRegFees;
	}

	public void setPledgeRegFees(double pledgeRegFees) {
		this.pledgeRegFees = pledgeRegFees;
	}

	public double getStamp() {
		return stamp;
	}

	public void setStamp(double stamp) {
		this.stamp = stamp;
	}

	public double getTransferPriceBank() {
		return transferPriceBank;
	}

	public void setTransferPriceBank(double transferPriceBank) {
		this.transferPriceBank = transferPriceBank;
	}

	public String getTaxBank() {
		return taxBank;
	}

	public void setTaxBank(String taxBank) {
		this.taxBank = taxBank;
	}

	public double getIncomeTaxBank() {
		return incomeTaxBank;
	}

	public void setIncomeTaxBank(double incomeTaxBank) {
		this.incomeTaxBank = incomeTaxBank;
	}

	public double getIncomeTaxHsBank() {
		return incomeTaxHsBank;
	}

	public void setIncomeTaxHsBank(double incomeTaxHsBank) {
		this.incomeTaxHsBank = incomeTaxHsBank;
	}

	public String getLandTaxBank() {
		return landTaxBank;
	}

	public void setLandTaxBank(String landTaxBank) {
		this.landTaxBank = landTaxBank;
	}

	public double getSalesTaxBank() {
		return salesTaxBank;
	}

	public void setSalesTaxBank(double salesTaxBank) {
		this.salesTaxBank = salesTaxBank;
	}

	public double getDeedTaxBank() {
		return deedTaxBank;
	}

	public void setDeedTaxBank(double deedTaxBank) {
		this.deedTaxBank = deedTaxBank;
	}

	public double getTranFeesBank() {
		return tranFeesBank;
	}

	public void setTranFeesBank(double tranFeesBank) {
		this.tranFeesBank = tranFeesBank;
	}

	public double getRegFeesBank() {
		return regFeesBank;
	}

	public void setRegFeesBank(double regFeesBank) {
		this.regFeesBank = regFeesBank;
	}

	public double getPledgeRegFeesBank() {
		return pledgeRegFeesBank;
	}

	public void setPledgeRegFeesBank(double pledgeRegFeesBank) {
		this.pledgeRegFeesBank = pledgeRegFeesBank;
	}

	public double getStampBank() {
		return stampBank;
	}

	public void setStampBank(double stampBank) {
		this.stampBank = stampBank;
	}

	public double getAppliqueBank() {
		return appliqueBank;
	}

	public void setAppliqueBank(double appliqueBank) {
		this.appliqueBank = appliqueBank;
	}

	public double getCityBuildTaxBank() {
		return cityBuildTaxBank;
	}

	public void setCityBuildTaxBank(double cityBuildTaxBank) {
		this.cityBuildTaxBank = cityBuildTaxBank;
	}

	public double getEducationFeesBank() {
		return educationFeesBank;
	}

	public void setEducationFeesBank(double educationFeesBank) {
		this.educationFeesBank = educationFeesBank;
	}

	public double getPlaceEducationFeesBank() {
		return placeEducationFeesBank;
	}

	public void setPlaceEducationFeesBank(double placeEducationFeesBank) {
		this.placeEducationFeesBank = placeEducationFeesBank;
	}

	public int getIsCalcT() {
		return isCalcT;
	}

	public void setIsCalcT(int isCalcT) {
		this.isCalcT = isCalcT;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

}
