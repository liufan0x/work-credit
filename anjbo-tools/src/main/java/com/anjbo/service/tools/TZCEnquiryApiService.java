/**
 * 
 */
package com.anjbo.service.tools;

import java.util.List;

import com.anjbo.bean.enquiry.EnquiryReport;
import com.anjbo.bean.enquiry.ReportReq;
import com.anjbo.bean.tools.EnquiryAssessDto;
import com.anjbo.bean.tools.EnquiryDto;
import com.anjbo.common.MortgageException;
import com.anjbo.common.RespDataObject;


/**
 * @author Kevin Chang
 *
 */
public interface TZCEnquiryApiService {

	/**
	 * 向同致诚提交查询请求
	 */
	public abstract String tzcEnquiry(EnquiryDto enquiryDto)
			throws MortgageException;

	/**
	 * 向同致诚提交查询结果请求
	 */
	public abstract List<EnquiryReport> queryEnquiryReport(ReportReq req);

	
	/**
	 * 同致诚申请评估接口
	 * @param assess
	 * @return
	 */
	public abstract RespDataObject<String> tzcLimitApply(EnquiryAssessDto assess) throws MortgageException;
	
	/**
	 * 同致诚申请评估报告接口
	 * @param assess
	 * @return
	 */
	public abstract RespDataObject<String> tzcWebReportApply(EnquiryAssessDto assess)throws MortgageException;
}