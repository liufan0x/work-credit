/**
 * 
 */
package com.anjbo.service.tools.impl;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.anjbo.bean.enquiry.EnquiryAssessResp;
import com.anjbo.bean.enquiry.EnquiryReport;
import com.anjbo.bean.enquiry.ReportReq;
import com.anjbo.bean.tools.EnquiryAssessDto;
import com.anjbo.bean.tools.EnquiryDto;
import com.anjbo.common.Constants;
import com.anjbo.common.FreemarkerHelper;
import com.anjbo.common.MortgageException;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.common.TZCEnquiryParseHelper;
import com.anjbo.service.tools.TZCEnquiryApiService;
import com.anjbo.thirdInterface.tzc.ServiceSoap;
import com.anjbo.utils.StringUtil;


/**
 * @author Kevin Chang
 * 
 */
@Service(value = "tZCEnquiryApiServiceImpl")
public class TZCEnquiryApiServiceImpl implements TZCEnquiryApiService {
	private static final Log log = LogFactory
			.getLog(TZCEnquiryApiServiceImpl.class);
	@Resource(name = "serviceSoapProxy")
	private ServiceSoap serviceSoap;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zxsf360.mortgage.service.impl.TZCEnquiryApiService#queryEnquiryReport
	 * ()
	 */
	@Override
	public List<EnquiryReport> queryEnquiryReport(ReportReq req) {
		List<EnquiryReport> enquiryReports = null;
		String result = "";
		try {
			Map<String, Object> dataMap = new HashMap<String, Object>();
			String resultCondition = "";
			if(req==null){//查全部
				resultCondition = FreemarkerHelper.getIns().processTemplate(
						"tzcreqPolling.ftl", dataMap);
			}else{//根据流水号查询
				resultCondition = FreemarkerHelper.getIns().processTemplate(
						"tzcreqReport.ftl", req);
			}
			log.info("tzcResultReport request:\n"+resultCondition);
			// resultCondition = StringUtil.getBASE64(resultCondition, "GBK");
			result = serviceSoap.tzcResultReport(Constants.TZC_USERID,
					Constants.TZC_PASSWORD, resultCondition, "XML");
			result = StringUtil.getFromBASE64(result, "GBK");
			log.info("tzcResultReport response:\n" + result);
			enquiryReports = TZCEnquiryParseHelper
					.parsePoolingEnquiryResp(result);
		} catch (RemoteException e) {
			// 远程请求异常
			log.error("queryEnquiryReport RemoteException", e);
		}
		return enquiryReports;
	}

	

	
	/**
	 * 同致诚申请评估接口
	 * @param enquiryDto
	 * @return
	 * @throws MortgageException
	 */
	public RespDataObject<String> tzcLimitApply(EnquiryAssessDto assess) throws MortgageException {
		RespDataObject<String> resp = new RespDataObject<String>();
		resp.setCode(RespStatusEnum.FAIL.getCode());
		String result = "";
		assess.setUnitName("快鸽按揭");
		assess.setOrganUnitName("深圳公司");
		try {
			String queryCondition = FreemarkerHelper.getIns().processTemplate(
					"tzcLimitApply.ftl", assess);
			log.info("tzcLimitApply request params=" + queryCondition);
			// base64编码
			queryCondition = StringUtil.getBASE64(queryCondition, "GBK");
			result = serviceSoap.tzcLimitApply(Constants.TZC_USERID,
					Constants.TZC_PASSWORD, queryCondition, "XML");
			log.info("tzcLimitApply response=" + result);
		} catch (RemoteException e) {
			// 远程请求异常
			throw new MortgageException(RespStatusEnum.SYSTEM_ERROR.getCode(),
					RespStatusEnum.SYSTEM_ERROR.getMsg());
		}
		EnquiryAssessResp assessResp = TZCEnquiryParseHelper
				.parseEnquiryAssessResp(result);
		if (assessResp != null) {
			int status = assessResp.getErrCode();
			if (status == 0) {//正常
				resp.setCode(RespStatusEnum.SUCCESS.getCode());
				resp.setMsg(status+assessResp.getErrMsg());
			} else if(status == 3008){
				resp.setMsg(status+assessResp.getErrMsg());
			}else{
				resp.setMsg(status+assessResp.getErrMsg());
			}
		}
		return resp;
	}
	
	/**
	 * 同致诚申请评估报告接口
	 * @param enquiryDto
	 * @return
	 * @throws MortgageException
	 */
	public RespDataObject<String> tzcWebReportApply(EnquiryAssessDto assess) throws MortgageException {
		RespDataObject<String> resp = new RespDataObject<String>();
		resp.setCode(RespStatusEnum.FAIL.getCode());
		resp.setMsg("接口异常");
		String result = "";
		assess.setUnitName("快鸽按揭");
		assess.setOrganUnitName("深圳公司");
		try {
			String queryCondition = FreemarkerHelper.getIns().processTemplate(
					"tzcWebReportApply.ftl", assess);
			log.info("tzcWebReportApply request params=" + queryCondition);
			// base64编码
			queryCondition = StringUtil.getBASE64(queryCondition, "GBK");
			result = serviceSoap.tzcWebReportApply(Constants.TZC_USERID,
					Constants.TZC_PASSWORD, queryCondition, "XML");
			log.info("tzcWebReportApply response=" + result);
		} catch (RemoteException e) {
			// 远程请求异常
			throw new MortgageException(RespStatusEnum.SYSTEM_ERROR.getCode(),
					RespStatusEnum.SYSTEM_ERROR.getMsg());
		}
		EnquiryAssessResp assResp = TZCEnquiryParseHelper
				.parseEnquiryAssessResp(result);
		if (resp != null) {
			int status = assResp.getErrCode();
			if (status == 0) {//正常
				resp.setCode(RespStatusEnum.SUCCESS.getCode());
				resp.setMsg(status+assResp.getErrMsg());
			} else if(status == 3008){
				resp.setMsg(status+assResp.getErrMsg());
			}else {
				resp.setMsg(status+assResp.getErrMsg());
			}
		}
		return resp;
	}




	@Override
	public String tzcEnquiry(EnquiryDto enquiryDto) throws MortgageException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
