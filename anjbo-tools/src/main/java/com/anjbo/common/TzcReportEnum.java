package com.anjbo.common;

import org.apache.commons.lang3.StringUtils;

/**
 * 申请评估报告状态
 * @author jiangyq
 *
 * @date 2017年11月7日 下午8:23:05
 */
public enum TzcReportEnum {
	
	ASSESS_REPORT_STATUS_1("1","已申请出评估报告"),
	ASSESS_REPORT_STATUS_2("2","评估已受理"),
	ASSESS_REPORT_STATUS_3("3","已上门勘查"),
	ASSESS_REPORT_STATUS_4("4","已出报告"),
	ASSESS_REPORT_STATUS_5("5","已寄快递"),
	ASSESS_REPORT_STATUS_6("6","已签收");
	
	private String code;
	private String name;
	/**
	 * 
	 */
	private TzcReportEnum(String code,String name) {
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
	
	public static String getNameByCode(String code){
		if(StringUtils.isEmpty(code)){
			return "";
		}
		String name = "";
		for(TzcReportEnum roleEnum:TzcReportEnum.values()){
			if(roleEnum.getCode().equals(code)){
				name = roleEnum.getName();
				break;
			}
		}
		return name;
	}
}
