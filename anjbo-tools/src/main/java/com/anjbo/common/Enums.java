package com.anjbo.common;

import org.apache.commons.lang3.StringUtils;

/**
 * 项目中定义的枚举
 * @author limh limh@zxsf360.com
 * @date 2016-6-2 下午07:12:05
 */
public class Enums {

	/**
	 * GLOBAL_CONFIG_KEY classpath:config.properties 对应的key
	 * @author Jerry
	 * @version v1.0 Enums.java 2013-9-25 上午11:16:02
	 */
	public enum GLOBAL_CONFIG_KEY {
		/**ams地址**/
		AMS_URL,
		/**ip白名单**/
		AMS_SMS_IPWHITE,
		/**查档发送邮件**/
		ARCHIVE_EMAILS,
		/**预约取号发送邮件**/
		BOOKING_EMAILS,
		/**办文查询发送邮件**/
		DOCSEARCH_EMAILS,
		/**查档短信预警**/
		ARCHIVE_MONITOR_JOB_MOBILE,
		/**查档邮件预警**/
		ARCHIVE_MONITOR_JOB_EMAIL
	}
	
	public enum ENQUIRY_CONFIG_KEY{
		//询价状态
		ENQUIRY_1(1, "询价中"), 
		ENQUIRY_2(2, "已询价"), 
		ENQUIRY_3(3, "转发询价"), 
		ENQUIRY_4(4, "询价"),
		ENQUIRY_5(5, "回复询价"),
		ENQUIRY_6(6, "询价失败");
		
		private Integer code;
		private String name;

		private ENQUIRY_CONFIG_KEY(Integer code, String name) {
			this.code = code;
			this.name = name;
		}

		public Integer getCode() {
			return code;
		}

		public void setCode(Integer code) {
			this.code = code;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}
	
	public enum RoleEnum {
		
		USER_CUSTOMERMANAGER_MOLS("13692236886","莫经理");

		private String code;
		private String name;
		
		private RoleEnum(String code,String name) {
			this.code = code;
			this.name = name;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	}
	
	public enum GoodNopayEnum {
		//免支付商品类型
		GOODNOPAY_ENQUIRY(1, "询价"),
		GOODNOPAY_ARCHIVE(2, "查档"),
		GOODNOPAY_CONTRACT(3, "打合同"),
		GOODNOPAY_TRANSFER(4, "过户"),
		GOODNOPAY_CREDIT(5, "查征信 "),
		GOODNOPAY_ASSESS(6, "评估"),
		GOODNOPAY_CODE(7, "优惠码"),
		GOODNOPAY_ASSESSREPORT(8, "评估报告"),
		GOODNOPAY_SECHANDLOAN(9, "二手房贷款"),
		GOODNOPAY_DECORATELOAN(10, "装修贷款"),
		GOODNOPAY_PLEDGELOAN(11, "抵押贷款"),
		GOODNOPAY_CREDITLOAN(12, "信用贷款");

		private int code;
		private String name;
		
		private GoodNopayEnum(int code,String name) {
			this.code = code;
			this.name = name;
		}
		public int getCode() {
			return code;
		}
		public void setCode(int code) {
			this.code = code;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	}
	
	public enum CityNameByType {
		
		TZC_SZ("1","深圳",Constants.TZC_ID),
		TZC_GZ("2","广州",Constants.TZC_ID),
		GJ_SZ("1","深圳",Constants.GJ_ID),
		GJ_GZ("2","广州",Constants.GJ_ID),
		YPG_SZ("6","深圳",Constants.YPG_ID),
		YPG_GZ("7","广州",Constants.YPG_ID),
		YPG_DG("8","东莞",Constants.YPG_ID),
		YPG_XM("110","厦门",Constants.YPG_ID),
		YPG_HZ("11","惠州",Constants.YPG_ID);

		private String code;
		private String name;
		private String type;
		
		private CityNameByType(String code,String name,String type) {
			this.code = code;
			this.name = name;
			this.type = type;
		}
		
		public static String getCodeBygNameAndType(String name,String type){
			if(StringUtils.isEmpty(name)||StringUtils.isEmpty(type)){
				return "";
			}
			String code = "";
			for(CityNameByType cityNameByType:CityNameByType.values()){
				if(cityNameByType.getName().equals(name)&&cityNameByType.getType().equals(type)){
					code = cityNameByType.getCode();
					break;
				}
			}
			
			return code;
		}
		
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
	}
	public enum DegreeEnums{
		
		ORGION1("南山","XWCXNS"),
		ORGION2("福田","XWCXFT"),
		ORGION3("罗湖","XWCXLH"),
		ORGION4("宝安","XWCXBA"),
		ORGION5("龙华","XWCXLHA"),
		ORGION6("龙岗","XWCXLG"),
		ORGION7("光明","XWCXGM"),
		ORGION8("盐田","XWCXYT"),
		ORGION9("坪山","XWCXPS"),
		ORGION10("大鹏","XWCXDP");
		
		private String name;
		private String code;

		DegreeEnums(String name,String code) {
			this.name=name;
			this.code=code;
		}
		public static String getNameByCode(String code) {
			String name = "";
			for (DegreeEnums enumsDegree : DegreeEnums.values()) {
				if (enumsDegree.getCode().equals(code)) {
					name = enumsDegree.getName();
					break;
				}
			}
			return name;
		}
		public static String getCodeByContainsName(String name) {
			String code = "";
			for (DegreeEnums enumsDegree : DegreeEnums.values()) {
				if (name.contains(enumsDegree.getName())) {
					code = enumsDegree.getCode();
					break;
				}
			}
			return code;
		}
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}
	}
}
