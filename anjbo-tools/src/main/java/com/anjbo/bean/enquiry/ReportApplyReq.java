package com.anjbo.bean.enquiry;

/**
 * 同致诚出报告回调参数
 * @author jiangyq
 *
 * @date 2017年11月6日 下午2:43:21
 */
public class ReportApplyReq {
	private String pgsqId;
	private String enquiryAssessId;
	private String progressId;
	private String expressOrderNO;
	private String expressCompany;
	private String origin;//身份校验码
	
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getPgsqId() {
		return pgsqId;
	}
	public void setPgsqId(String pgsqId) {
		this.pgsqId = pgsqId;
	}
	public String getEnquiryAssessId() {
		return enquiryAssessId;
	}
	public void setEnquiryAssessId(String enquiryAssessId) {
		this.enquiryAssessId = enquiryAssessId;
	}
	public String getProgressId() {
		return progressId;
	}
	public void setProgressId(String progressId) {
		this.progressId = progressId;
	}
	public String getExpressOrderNO() {
		return expressOrderNO;
	}
	public void setExpressOrderNO(String expressOrderNO) {
		this.expressOrderNO = expressOrderNO;
	}
	public String getExpressCompany() {
		return expressCompany;
	}
	public void setExpressCompany(String expressCompany) {
		this.expressCompany = expressCompany;
	}

	

}
