package com.anjbo.service.hrtrust.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.apache.poi.hslf.usermodel.SlideShow;
import org.springframework.stereotype.Service;
import com.anjbo.common.Constants;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.common.ThirdApiConstants;
import com.anjbo.dao.hrtrust.HrtrustRepaymentPlanMapper;
import com.anjbo.dao.hrtrust.HrtrustBusinfoMapper;
import com.anjbo.dao.hrtrust.HrtrustLoanMapper;
import com.anjbo.dao.hrtrust.HrtrustKgAppointMapper;
import com.anjbo.dao.hrtrust.HrtrustKgApprovalMapper;
import com.anjbo.dao.hrtrust.HrtrustKgHouseMapper;
import com.anjbo.dao.hrtrust.HrtrustKgIndivMapper;
import com.anjbo.dao.hrtrust.HrtrustLcApplApptMapper;
import com.anjbo.dao.hrtrust.HrtrustLcAppointMapper;
import com.anjbo.dao.hrtrust.HrtrustLcApptIndivMapper;
import com.anjbo.dao.hrtrust.HrtrustLoanStatusMapper;
import com.anjbo.dao.hrtrust.HrtrustRepaymentInfoMapper;
import com.anjbo.service.hrtrust.HrtrustService;
import com.anjbo.utils.DateUtils;
import com.anjbo.utils.SingleUtils;
import com.anjbo.utils.StringUtil;
import com.anjbo.utils.huarong.FromAnjboToHrKeyValue;
import com.anjbo.utils.huarong.ReturnParam;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import com.anjbo.utils.huarong.HrtrustUitls;

@Service
public class HrtrustServiceImpl implements HrtrustService {

	private Logger logger = Logger.getLogger(getClass());

	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

	private String orderNo;

	@Resource
	private HrtrustLoanStatusMapper loanStatusMapper;

	@Resource
	private HrtrustLcApplApptMapper lcApplApptMapper;

	@Resource
	private HrtrustLcApptIndivMapper lcApptIndivMapper;

	@Resource
	private HrtrustKgAppointMapper kgAppointMapper;

	@Resource
	private HrtrustKgApprovalMapper kgApprovalMapper;

	@Resource
	private HrtrustKgHouseMapper kgHouseMapper;

	@Resource
	private HrtrustKgIndivMapper kgIndivMapper;

	@Resource
	private HrtrustLoanMapper hrtrustLoanMapper;

	@Resource
	private HrtrustLcAppointMapper lcAppointMapper;

	@Resource
	private HrtrustBusinfoMapper fileApplyMapper;

	@Resource
	private HrtrustRepaymentPlanMapper borrowRepaymentMapper;

	@Resource
	private HrtrustRepaymentInfoMapper repaymentRegisterMapper;

	@Resource
	private UtilBorrowIdService utilBorrowIdService;

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
    
	
	/**
	 * 申请数据接口
	 * 
	 * @param parmMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public RespStatus applySend(Map<String, Object> parmMap) {
		RespStatus resp = new RespStatus();
		ReturnParam<Object> returnParam = null;
		Map<String, Object> keyValueMap = FromAnjboToHrKeyValue.KeyValue();

		Map<String, Object> lcApptIndivMap = MapUtils.getMap(parmMap, "lcApptIndiv"); // 申请人详细信息
		Map<String, Object> lcApplApptMap = MapUtils.getMap(parmMap, "lcApplAppt"); // 申请人信息
		Map<String, Object> lcAppointMap = MapUtils.getMap(parmMap, "lcAppoint"); // 申请预约信息
		Map<String, Object> kgAppointMap = MapUtils.getMap(parmMap, "kgAppoint"); // 业务信息
		Map<String, Object> kgIndivMap = MapUtils.getMap(parmMap, "kgIndiv"); // 借款人信息
		Map<String, Object> kgHouseMap = MapUtils.getMap(parmMap, "kgHouse"); // 房产信息
		Map<String, Object> kgApprovalMap = MapUtils.getMap(parmMap, "kgApproval"); // 审批信息
		Map<String, Object> lcLoan = MapUtils.getMap(parmMap, "kgLoan"); // 放款明细表
		
		int type = MapUtils.getIntValue(parmMap, "types"); // 申请人详细信息

		String env_ApplSeq = getApplSeq();
		Map<String, Object> map = new HashMap<String, Object>();

		// 申请人详细信息
		Map<String, Object> lcApptIndiv = new HashMap<String, Object>();
		lcApptIndiv.put("applSeq", env_ApplSeq);
		lcApptIndiv.put("custName", MapUtils.getString(lcApptIndivMap, "custName", ""));
		lcApptIndiv.put("indivEdt",
				MapUtils.getString(keyValueMap, MapUtils.getString(lcApptIndivMap, "indivEdt", "").trim())); // 转义数据，教育程度
		logger.info("申请人详细信息请求报文:" + lcApptIndiv);
		 
		// 申请预约信息
		Map<String, Object> lcAppoint = new HashMap<String, Object>();
		lcAppoint.put("applSeq", env_ApplSeq);
		lcAppoint.put("custName", MapUtils.getString(lcAppointMap, "custName", ""));
		lcAppoint.put("idType", MapUtils.getString(keyValueMap, MapUtils.getString(lcAppointMap, "idType", "")));// 转换数据，证件类型
		lcAppoint.put("idNo", MapUtils.getString(lcAppointMap, "idNo", ""));
		lcAppoint.put("mobile", MapUtils.getString(lcAppointMap, "mobile", ""));
		lcAppoint.put("mtdCde", MapUtils.getString(keyValueMap, MapUtils.getString(lcAppointMap, "mtdCde", "")));// 转换数据，还款方式
		lcAppoint.put("applyAmt", multiplication(MapUtils.getString(lcAppointMap, "applyAmt", "0")));
		lcAppoint.put("applyTnr", MapUtils.getString(lcAppointMap, "applyTnr", ""));// 申请期限(按月)
		lcAppoint.put("apr", MapUtils.getString(lcAppointMap, "apr", ""));// 利率
		lcAppoint.put("use", MapUtils.getString(keyValueMap, MapUtils.getString(lcAppointMap, "useHr", "")));// 转换数据，借款用途
		lcAppoint.put("cooprCode", "");// 场景商户代码
		lcAppoint.put("applyTnrUnit",
				MapUtils.getString(keyValueMap, MapUtils.getString(lcAppointMap, "applyTnrUnit", "")));// 转换数据,借款期限单位
		lcAppoint.put("apprvAmt", multiplication(MapUtils.getString(lcAppointMap, "apprvAmt", "0")));
		lcAppoint.put("productId", MapUtils.getString(lcAppointMap, "productId"));
		lcAppoint.put("loanCooprCode", MapUtils.getString(lcAppointMap, "loanCooprCode"));
		logger.info("申请预约信息请求报文:" + lcAppoint);

		// 申请人信息
		Map<String, Object> lcApplAppt = new HashMap<String, Object>();
		lcApplAppt.put("applSeq", env_ApplSeq);
		lcApplAppt.put("idNo", MapUtils.getString(lcApplApptMap, "idNo", ""));
		lcApplAppt.put("idType", MapUtils.getString(lcApplApptMap, "idType", ""));
		lcApplAppt.put("custName", MapUtils.getString(lcApplApptMap, "custName", ""));
		logger.info("申请人信息请求报文:" + lcApplAppt);

		// 业务信息
		Map<String, Object> kgAppoint = new HashMap<String, Object>();
		kgAppoint.put("yIsBank", MapUtils.getString(kgAppointMap, "yIsBank", ""));
		if("是".equals(MapUtils.getString(kgAppointMap, "yIsBank", ""))) {
			kgAppoint.put("yOriBank", MapUtils.getString(kgAppointMap, "oldLoanBankName") + "-" + MapUtils.getString(kgAppointMap, "oldLoanBankSubName"));
		}else {
			kgAppoint.put("yOriBank", MapUtils.getString(kgAppointMap, "yOriBank"));
		}
		
		kgAppoint.put("xIsBank", MapUtils.getString(kgAppointMap, "xIsBank", ""));
		if("是".equals(MapUtils.getString(kgAppointMap, "xIsBank", ""))) {
			kgAppoint.put("xLoanBank", MapUtils.getString(kgAppointMap, "loanBankName")  + "-" + MapUtils.getString(kgAppointMap, "loanSubBankName"));
		}else {
			kgAppoint.put("xLoanBank", MapUtils.getString(kgAppointMap, "xLoanBank"));
		}
		kgAppoint.put("fOpenBank", MapUtils.getString(kgAppointMap, "bankName")  + "-" + MapUtils.getString(kgAppointMap, "bankSubName") );
		kgAppoint.put("hOpenBank", MapUtils.getString(kgAppointMap, "paymentBankName") + "-" + MapUtils.getString(kgAppointMap, "paymentBankSubName"));
		kgAppoint.put("applSeq", env_ApplSeq);
		kgAppoint.put("businessType", MapUtils.getString(kgAppointMap, "businessType", ""));
		kgAppoint.put("custName", MapUtils.getString(kgAppointMap, "custName", ""));
		kgAppoint.put("loanAmount", multiplication(MapUtils.getString(kgAppointMap, "loanAmount", "0")));// 借款金额
		kgAppoint.put("term", MapUtils.getString(kgAppointMap, "term", ""));
		kgAppoint.put("rate", MapUtils.getString(kgAppointMap, "rate", ""));
		kgAppoint.put("overdueTate", MapUtils.getString(kgAppointMap, "overdueTate", ""));
		kgAppoint.put("yOriLenAmount", multiplication(MapUtils.getString(kgAppointMap, "yOriLenAmount", "0")));// 原放贷金额
		kgAppoint.put("yLoanBalance", multiplication(MapUtils.getString(kgAppointMap, "yLoanBalance", "0")));// 贷款余额
		kgAppoint.put("xLoanAmount", multiplication(MapUtils.getString(kgAppointMap, "xLoanAmount", "0")));// 贷款金额
		kgAppoint.put("fAccountType", MapUtils.getString(kgAppointMap, "fAccountType", ""));
		kgAppoint.put("fOpenName", MapUtils.getString(kgAppointMap, "fOpenName", ""));
		kgAppoint.put("fAccountNum", MapUtils.getString(kgAppointMap, "fAccountNum", ""));
		kgAppoint.put("fCaacNo", MapUtils.getString(kgAppointMap, "fCaacNo", ""));
		kgAppoint.put("hAccountType", MapUtils.getString(kgAppointMap, "hAccountType", ""));
		kgAppoint.put("hOpenName", MapUtils.getString(kgAppointMap, "hOpenName", ""));
		kgAppoint.put("hAccountNum", MapUtils.getString(kgAppointMap, "hAccountNum", ""));
		kgAppoint.put("hCaacNo", MapUtils.getString(kgAppointMap, "hCaacNo", ""));
		logger.info("业务信息请求报文:" + kgAppoint);

		// 借款人信息
		Map<String, Object> kgIndiv = new HashMap<String, Object>();
		kgIndiv.put("applSeq", env_ApplSeq);
		kgIndiv.put("custName", MapUtils.getString(kgIndivMap, "custName", ""));
		kgIndiv.put("docType", MapUtils.getString(kgIndivMap, "docType", ""));
		kgIndiv.put("docNo", MapUtils.getString(kgIndivMap, "docNo", ""));
		kgIndiv.put("mobile", MapUtils.getString(kgIndivMap, "mobile", ""));
		kgIndiv.put("marStatus", MapUtils.getString(kgIndivMap, "marStatus", ""));
		kgIndiv.put("sCustName", MapUtils.getString(kgIndivMap, "sCustName", ""));
		kgIndiv.put("sDocType", MapUtils.getString(kgIndivMap, "sDocType", ""));
		kgIndiv.put("sDocNo", MapUtils.getString(kgIndivMap, "sDocNo", ""));
		kgIndiv.put("sMobile", MapUtils.getString(kgIndivMap, "sMobile", ""));
		kgIndiv.put("sMarStatus", MapUtils.getString(kgIndivMap, "sMarStatus", ""));
		kgIndiv.put("cardLife", MapUtils.getString(kgIndivMap, "cardLife", ""));
		kgIndiv.put("totalPremises", multiplication(MapUtils.getString(kgIndivMap, "totalPremises", "0")));// 所有房产评估总值
		kgIndiv.put("totalCredit", multiplication(MapUtils.getString(kgIndivMap, "totalCredit", "0")));// 授信总额
		kgIndiv.put("debtRatio", MapUtils.getString(kgIndivMap, "debtRatio", ""));
		kgIndiv.put("overdueCredit", MapUtils.getString(kgIndivMap, "overdueCredit", ""));
		kgIndiv.put("yIsBank", MapUtils.getString(kgIndivMap, "yIsBank", ""));
		kgIndiv.put("loanYear", MapUtils.getString(kgIndivMap, "loanYear", ""));
		kgIndiv.put("houseNum", MapUtils.getString(kgIndivMap, "houseNum", ""));
		kgIndiv.put("quotaUsed", multiplication(MapUtils.getString(kgIndivMap, "quotaUsed", "0")));// 已用额度
		kgIndiv.put("loanAmount", MapUtils.getString(kgIndivMap, "loanAmount", ""));
		kgIndiv.put("creditFindNum", MapUtils.getString(kgIndivMap, "creditFindNum", ""));
		kgIndiv.put("xIsBank", MapUtils.getString(kgIndivMap, "xIsBank", ""));
		kgIndiv.put("defaultRate", MapUtils.getString(kgIndivMap, "defaultRate", ""));
		kgIndiv.put("overdraft", multiplication(MapUtils.getString(kgIndivMap, "overdraft", "0")));// 信用卡半年月均透支额
		kgIndiv.put("totalCreditLia", multiplication(MapUtils.getString(kgIndivMap, "totalCreditLia", "0")));// 征信总负债
		kgIndiv.put("foreclosureRate", MapUtils.getString(kgIndivMap, "foreclosureRate", ""));
		kgIndiv.put("companyRight", MapUtils.getString(kgIndivMap, "companyRight", ""));
		kgIndiv.put("rightMortgage", MapUtils.getString(kgIndivMap, "rightMortgage", ""));
		kgIndiv.put("remark", MapUtils.getString(kgIndivMap, "remark", ""));
		logger.info("借款人信息请求报文:" + kgIndiv);

		// 房产信息
		Map<String, Object> kgHouse = new HashMap<String, Object>();
		kgHouse.put("applSeq", env_ApplSeq);
		kgHouse.put("owner", MapUtils.getString(kgHouseMap, "owner", ""));
		kgHouse.put("houseAddress", MapUtils.getString(kgHouseMap, "houseAddress", ""));
		kgHouse.put("builtArea", MapUtils.getString(kgHouseMap, "builtArea", ""));
		kgHouse.put("houseNo", MapUtils.getString(kgHouseMap, "houseNo", ""));
		kgHouse.put("totalAssessment", multiplicationY(MapUtils.getString(kgHouseMap, "totalAssessment", "0")));
		kgHouse.put("worthAssessment", multiplicationY(MapUtils.getString(kgHouseMap, "worthAssessment", "0")));
		kgHouse.put("firstHouseLoan", multiplicationY(MapUtils.getString(kgHouseMap, "firstHouseLoan", "0")));
		kgHouse.put("consult", MapUtils.getString(kgHouseMap, "consult", ""));
		kgHouse.put("remark", MapUtils.getString(kgHouseMap, "remark", ""));
		logger.info("房产信息请求报文:" + kgHouse);

		// 审批信息
		Map<String, Object> kgApproval = new HashMap<String, Object>();
		kgApproval.put("applSeq", env_ApplSeq);
		kgApproval.put("trial", MapUtils.getString(kgApprovalMap, "trial", ""));
		kgApproval.put("judgment", MapUtils.getString(kgApprovalMap, "judgment", ""));
		kgApproval.put("chiefRiskOpinion", MapUtils.getString(kgApprovalMap, "chiefRiskOpinion", ""));
		logger.info("审批信息请求报文:" + kgApproval);

		map.put("lcApptIndiv", lcApptIndiv);
		map.put("lcAppoint", lcAppoint);
		map.put("lcApplAppt", lcApplAppt);
		map.put("kgAppoint", kgAppoint);
		map.put("kgIndiv", kgIndiv);
		map.put("kgHouse", kgHouse);
		map.put("kgApproval", kgApproval);
    
		if (type==5) {
			String loanCooprCode = MapUtils.getString(lcAppoint, "loanCooprCode");
			 System.out.println("路径地址"+ThirdApiConstants.HR_APPLY_URL);
			 returnParam = HrtrustUitls.sendHrHaEnCryptMap(loanCooprCode, gson.toJson(map),
					ThirdApiConstants.HR_APPLY_URL, ThirdApiConstants.HR_APPLY_URL_TIMEOUT);
	        System.out.println("路径地址"+ThirdApiConstants.HR_APPLY_URL);
			
			System.out.println("申请数据返回报文" + returnParam);
		}
		

		kgApproval.put("orderNo", orderNo);
		lcApplAppt.put("orderNo", orderNo);
		kgAppoint.put("orderNo", orderNo);
		kgIndiv.put("orderNo", orderNo);
		kgHouse.put("orderNo", orderNo);
		lcAppoint.put("orderNo", orderNo);
		lcApptIndiv.put("orderNo", orderNo);

		// 重置转义字段为快鸽原始字段入库
		lcApptIndiv.put("indivEdt", MapUtils.getString(lcApptIndivMap, "indivEdt", ""));// 转义数据，教育程度
		lcAppoint.put("idType", MapUtils.getString(lcAppointMap, "idType", ""));// 转换数据，证件类型
		lcAppoint.put("mtdCde", MapUtils.getString(lcAppointMap, "mtdCde", ""));// 转换数据，还款方式
		lcAppoint.put("use", MapUtils.getString(lcAppointMap, "useHr", ""));// 转换数据，借款用途
		lcAppoint.put("applyTnrUnit", MapUtils.getString(lcAppointMap, "applyTnrUnit", ""));// 转换数据,借款期限单位

		// 重置转换元为万元
		lcAppoint.put("applyAmt", MapUtils.getString(lcAppointMap, "applyAmt", "0"));
		lcAppoint.put("apprvAmt", MapUtils.getString(lcAppointMap, "apprvAmt", "0"));
		kgAppoint.put("loanAmount", MapUtils.getString(kgAppointMap, "loanAmount", "0"));// 借款金额
		kgAppoint.put("yOriLenAmount", MapUtils.getString(kgAppointMap, "yOriLenAmount", "0"));// 原放贷金额
		kgAppoint.put("yLoanBalance", MapUtils.getString(kgAppointMap, "yLoanBalance", "0"));// 贷款余额
		kgAppoint.put("xLoanAmount", MapUtils.getString(kgAppointMap, "xLoanAmount", "0"));// 贷款金额
		kgIndiv.put("totalPremises", MapUtils.getString(kgIndivMap, "totalPremises", "0"));// 所有房产评估总值
		kgIndiv.put("totalCredit", MapUtils.getString(kgIndivMap, "totalCredit", "0"));// 授信总额
		kgIndiv.put("quotaUsed", MapUtils.getString(kgIndivMap, "quotaUsed", "0"));// 已用额度
		kgIndiv.put("overdraft", MapUtils.getString(kgIndivMap, "overdraft", "0"));// 信用卡半年月均透支额
		kgIndiv.put("totalCreditLia", MapUtils.getString(kgIndivMap, "totalCreditLia", "0"));// 征信总负债

		lcAppoint.put("orderNo", orderNo);
		lcApplAppt.put("orderNo", orderNo);
		lcApptIndiv.put("orderNo", orderNo);
		kgAppoint.put("orderNo", orderNo);
		kgApproval.put("orderNo", orderNo);
		kgHouse.put("orderNo", orderNo);
		kgIndiv.put("orderNo", orderNo);
		lcLoan.put("orderNo", orderNo);
		
		int num1 =0;
		int num2 =0;
		int num3 =0;
		int num4 =0;
		int num5 =0;
		int num6 =0;
		int num7 =0;
		int num8 =0;
		if(null==lcAppointMapper.getLcAppoint(orderNo)) {
			 num1 =lcAppointMapper.saveLcAppoint(lcAppoint);
		}else {
			 num1=lcAppointMapper.updateMap(lcAppoint);
		}
		if(null==lcApplApptMapper.getLcApplAppt(orderNo)) {
			 num2 =lcApplApptMapper.saveLcApplAppt(lcApplAppt);
		}else {
			 num2=lcApplApptMapper.updateMap(lcApplAppt);
		}
		if(null==lcApptIndivMapper.getLcApptIndiv(orderNo)) {
			 num3 =lcApptIndivMapper.saveLcApptIndiv(lcApptIndiv);
		}else {
			 num3=lcApptIndivMapper.updateMap(lcApptIndiv);
		}
		if(null==kgAppointMapper.getKgAppoint(orderNo)) {
			 num4 =kgAppointMapper.saveKgAppoint(kgAppoint);
		}else {
			 num4=kgAppointMapper.updateMap(kgAppoint);
		}
		if(null==kgApprovalMapper.getKgApproval(orderNo)) {
			 num5 =kgApprovalMapper.saveKgApproval(kgApproval);
		}else {
			 num5=kgApprovalMapper.updateMap(kgApproval);
		}
		if(null==kgHouseMapper.getKgHouse(orderNo)) {
			 num6 =kgHouseMapper.saveKgHouse(kgHouse);
		}else {
			 num6=kgHouseMapper.updateMap(kgHouse);
		}
		if(null==kgIndivMapper.getKgIndiv(orderNo)) {
			 num7 =kgIndivMapper.saveKgIndiv(kgIndiv);
		}else {
			 num7=kgIndivMapper.updateMap(kgIndiv);
		}
		if(null==hrtrustLoanMapper.selectHrtrustLoan(orderNo)) {
			 num8=hrtrustLoanMapper.saveHrtrustLoan(lcLoan);
		}else {
			 num8=hrtrustLoanMapper.updateMap(lcLoan);
		}
	 if (type==5) {
		 if (returnParam != null && returnParam.getRetCode().equals("00000")) {
				Map<String, Object> kgLoan = MapUtils.getMap(parmMap, "kgLoan");
				resp.setCode(RespStatusEnum.SUCCESS.getCode());
				resp.setMsg(returnParam.getRetInfo());
				resp = lendSend(kgLoan);
				return resp;
			} else if (returnParam != null && !returnParam.getRetCode().equals("00000")) {
				resp.setCode(RespStatusEnum.FAIL.getCode());
				resp.setMsg(returnParam.getRetInfo());
				return resp;
			} else {
				resp.setCode(RespStatusEnum.THIRD_ERROR.getCode());
				resp.setMsg("调用华融申请数据发送接口异常");
				return resp;
			}
	}
	
	 if (num1==1||num2==1||num3==1||num4==1||num5==1||num6==1||num7==1||num8==1) {
		    resp.setCode("SUCCESS");
			resp.setMsg("保存成功!");
	 }else {
		    resp.setCode("ERROR");
		    resp.setMsg("保存失败!");
	 }
		return resp;
	}

	
	/**
	 * 放款接口
	 * 
	 * @param parmMap
	 * @return
	 */
	public RespStatus lendSend(Map<String, Object> parmMap) {
		RespStatus resp = new RespStatus();
		Map<String, Object> keyValueMap = FromAnjboToHrKeyValue.KeyValue();

		String env_batchNo = "KG" + utilBorrowIdService.getBatchNo();

		List<Map<String, Object>> sendBorrowDetailList = new ArrayList<Map<String, Object>>();

		// 放款批次
		Map<String, Object> hrhaBorrowBatchMap = new HashMap<String, Object>();
		hrhaBorrowBatchMap.put("batchNo", env_batchNo);
		hrhaBorrowBatchMap.put("sendDate", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));// 时间获取
		hrhaBorrowBatchMap.put("allNums", "1");
		hrhaBorrowBatchMap.put("allAccount", multiplication(MapUtils.getString(parmMap, "applyAmt", "0")));
		logger.info("放款批次请求报文:" + hrhaBorrowBatchMap);

		// 放款明细
		Map<String, Object> borrowDetailmap1 = new HashMap<String, Object>();
		borrowDetailmap1.put("borrowId", getBorrowId());
		borrowDetailmap1.put("batchNo", env_batchNo);
		borrowDetailmap1.put("applSeq", utilBorrowIdService.findApplSeqByOrderNo(orderNo));
		borrowDetailmap1.put("bchCde", "KG");
		borrowDetailmap1.put("bchName", MapUtils.getString(parmMap, "bchName", ""));
		borrowDetailmap1.put("custName", MapUtils.getString(parmMap, "custName", ""));
		borrowDetailmap1.put("idType",
				MapUtils.getString(keyValueMap, MapUtils.getString(parmMap, "idType", "").trim()));// 转换数据，证件类型
		borrowDetailmap1.put("idNo", MapUtils.getString(parmMap, "idNo", ""));
		borrowDetailmap1.put("applyAmt", multiplication(MapUtils.getString(parmMap, "applyAmt", "0")));// 贷款申请金额
		borrowDetailmap1.put("apprvAmt", multiplication(MapUtils.getString(parmMap, "apprvAmt", "0")));// 审批通过金额
		borrowDetailmap1.put("applyTnrUnit",
				MapUtils.getString(keyValueMap, MapUtils.getString(parmMap, "applyTnrUnit", "").trim()));// 转换数据,借款期限单位
		borrowDetailmap1.put("applyTnr", MapUtils.getString(parmMap, "applyTnr", ""));
		borrowDetailmap1.put("mtdCde",
				MapUtils.getString(keyValueMap, MapUtils.getString(parmMap, "mtdCde", "").trim()));// 转换数据，还款方式
		borrowDetailmap1.put("apr", MapUtils.getString(parmMap, "apr", ""));
		borrowDetailmap1.put("odIntRate", MapUtils.getString(parmMap, "odIntRate", ""));
		borrowDetailmap1.put("sysbPct", MapUtils.getString(parmMap, "sysbPct", ""));
		borrowDetailmap1.put("sysbAmt", MapUtils.getString(parmMap, "sysbAmt", ""));
		borrowDetailmap1.put("hrdPct", MapUtils.getString(parmMap, "hrdPct", ""));
		borrowDetailmap1.put("hrdAmt", MapUtils.getString(parmMap, "hrdAmt", ""));
		borrowDetailmap1.put("ifCeOut", MapUtils.getString(parmMap, "ifCeOut", ""));
		borrowDetailmap1.put("repaymentTime", Str_date_To_Long(MapUtils.getString(parmMap, "repaymentTime", "")));
		sendBorrowDetailList.add(borrowDetailmap1);
		logger.info("放款明细请求报文:" + sendBorrowDetailList);

		List<Object> list = new ArrayList<Object>();
		list.add(hrhaBorrowBatchMap);
		list.add(sendBorrowDetailList);
		list.add(new ArrayList<Object>());

		Map<String, Object> lcMap = lcAppointMapper.getLcAppoint(orderNo);
		String loanCooprCode = MapUtils.getString(lcMap, "loanCooprCode");
		ReturnParam<Object> returnParam = HrtrustUitls.sendHrHaEnCryptMap(loanCooprCode, gson.toJson(list),
				ThirdApiConstants.HR_LEND_URL, ThirdApiConstants.HR_LEND_URL_TIMEOUT);

		logger.info("放款返回报文:" + returnParam);

		borrowDetailmap1.put("sendDate", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		borrowDetailmap1.put("allNums", 1);
		borrowDetailmap1.put("allAccount", multiplication(MapUtils.getString(parmMap, "applyAmt", "0")));
		borrowDetailmap1.put("respStatus", returnParam != null ? returnParam.getRetCode() : "");
		borrowDetailmap1.put("orderNo", orderNo);
		// 重置转义字段为快鸽原始字段入库
		borrowDetailmap1.put("idType", MapUtils.getString(parmMap, "idType", ""));// 转换数据，证件类型
		borrowDetailmap1.put("applyTnrUnit", MapUtils.getString(parmMap, "applyTnrUnit", ""));// 转换数据,借款期限单位
		borrowDetailmap1.put("mtdCde", MapUtils.getString(parmMap, "mtdCde", ""));// 转换数据，还款方式
		borrowDetailmap1.put("repaymentTime", MapUtils.getString(parmMap, "repaymentTime", ""));// 时间格式转义字段
		// 重置转元为万元
		borrowDetailmap1.put("applyAmt", MapUtils.getString(parmMap, "applyAmt", "0"));// 贷款申请金额
		borrowDetailmap1.put("apprvAmt", MapUtils.getString(parmMap, "apprvAmt", "0"));// 审批通过金额
		if(null==hrtrustLoanMapper.selectHrtrustLoan(orderNo)) {          //新加判断
			hrtrustLoanMapper.saveHrtrustLoan(borrowDetailmap1);
		}else {
		hrtrustLoanMapper.updateMap(borrowDetailmap1);
		}
		if (returnParam != null && returnParam.getRetCode().equals("00000")) {
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(returnParam.getRetInfo());
		} else if (returnParam != null && !returnParam.getRetCode().equals("00000")) {
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(returnParam.getRetInfo());
		} else {
			resp.setCode(RespStatusEnum.THIRD_ERROR.getCode());
			resp.setMsg("调用华融申请数据发送接口异常");
		}
		return resp;
	}

	/**
	 * 附件接口
	 * 
	 * @param list
	 * @return
	 */
	public  RespStatus fileApplySend(List<Map<String, Object>> list) {
		System.out.println("这就进来了文件上传锁去掉");
		RespStatus resp = new RespStatus();
		Map<String, Object> keyValueMap = FromAnjboToHrKeyValue.KeyValue();
		List<String> lstSuccess = fileApplyMapper.searchFilePaths(orderNo);   //查询api表所有影像路径

		Map<String, Object> lcMap = lcAppointMapper.getLcAppoint(orderNo);
		String loanCooprCode = MapUtils.getString(lcMap, "loanCooprCode");

		boolean isVeto = false;
		Map<String, Object> statusMap = loanStatusMapper.getLoanStatus(orderNo);
		if (statusMap != null && !statusMap.isEmpty()) {
			if ("998".equals(MapUtils.getString(statusMap, "grantStatus"))) {
				isVeto = true;
			}
		}

		Date date = new Date();
		long l = date.getTime() / 1000;
		List<Map<String, Object>> fileList = new ArrayList<Map<String, Object>>();// 上传成功文件list
		String docIo = "";
		String applSeq = utilBorrowIdService.findApplSeqByOrderNo(orderNo);
		String errorFile = "";
		for (Map<String, Object> file : list) {
			// 已经推过的附件。第二次不退。 否决的单需要重新推资料
			String url = MapUtils.getString(file, "url");
			if (!isVeto && lstSuccess != null && lstSuccess.contains(url)) {
				continue;
			}
			docIo = getRspContent(url);
			if (StringUtils.isEmpty(docIo)) {// 若出现read time out异常，docIo为空，再次请求一次
				docIo = getRspContent(url);
			}

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("applSeq", applSeq);
			map.put("docFileType", MapUtils.getString(keyValueMap, MapUtils.getString(file, "typeId")));// 此处需要转义为华融字段
			map.put("addtime", l);// 10位long 类型时间
			map.put("docIo", docIo);
			ReturnParam<Object> returnParam = null;
			try {
				boolean flag = true;
				int i = 1;
				do {
					logger.info("华融附件请求报文:" + file);
					returnParam = HrtrustUitls.sendHrHaEnCryptMap(loanCooprCode, gson.toJson(map),
							ThirdApiConstants.HR_FILE_APPLY_URL, ThirdApiConstants.HR_FILE_APPLY_URL_TIMEOUT);
					logger.info("华融附件返回报文:" + returnParam);
					if (returnParam != null && returnParam.getRetCode().equals("00000")) {
						flag = false;
						if (i == 3) {
							errorFile += url + ",";
						}
					}
					i++;
				} while (flag && i < 4);
			} catch (Exception e) {
				resp.setCode(RespStatusEnum.THIRD_ERROR.getCode());
				resp.setMsg("调用华融发送附件接口失败");
				logger.error("调用华融发送附件接口失败", e);
				return resp;
			}

			if (returnParam != null && returnParam.getRetCode().equals("00000")) {// 记录上传成功的附件，入库
				String typeId = MapUtils.getString(file, "typeId");
				String index = MapUtils.getString(file, "index");
				Map<String, Object> success_map = new HashMap<String, Object>();
				success_map.put("respStatus", returnParam.getRetCode());
				success_map.put("orderNo", orderNo);
				success_map.put("docFileType", typeId);
				success_map.put("filePath", url);
				success_map.put("applSeq", applSeq);
				success_map.put("addtime", DateUtils.dateToString(date, DateUtils.FMT_TYPE1));// 10位long 类型时间
				success_map.put("index", index);
				fileList.add(success_map);
			}
		}

		if (fileList.size() > 0) {
			fileApplyMapper.saveFileApply(fileList);
		}
		resp.setCode(RespStatusEnum.SUCCESS.getCode());
		if (StringUtils.isNotEmpty(errorFile)) {
			resp.setMsg("存在图片上传失败");
			logger.error("有图上传失败:" + errorFile);
		} else {
			resp.setMsg("所有图片上传成功");
		}
		return resp;
	}

	/**
	 * 应还款计划接口
	 * 
	 * @param parmMap
	 * @return
	 */
	public RespStatus borrowRepaymentSend(Map<String, Object> parmMap) {
		RespStatus resp = new RespStatus();
		String env_batchNo = utilBorrowIdService.findBatchNoByOrderNo(orderNo);

		String borrowId = utilBorrowIdService.findBorrowIdByOrderNo(orderNo);

		List<Map<String, Object>> ListCfBorrowRepayment = new ArrayList<Map<String, Object>>();

		List<Object> list = new ArrayList<Object>();
		// 应还款批次
		Map<String, Object> hrhaBorrowBatchMap = new HashMap<String, Object>();
		hrhaBorrowBatchMap.put("batch_no", env_batchNo);
		hrhaBorrowBatchMap.put("send_date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		hrhaBorrowBatchMap.put("all_account", ToDouble(MapUtils.getString(parmMap, "repaymentAccount")));
		hrhaBorrowBatchMap.put("all_nums", 1);

		// 还款计划明细
		Map<String, Object> borrowRepayment = new HashMap<String, Object>();
		borrowRepayment.put("borrowId", borrowId); // 借款编号
		borrowRepayment.put("order", 1);
		borrowRepayment.put("repaymentTime", Str_date_To_Long(MapUtils.getString(parmMap, "repaymentTime"))); // 时间转义数据
																												// 应还款时间
		borrowRepayment.put("repaymentAccount", ToDouble(MapUtils.getString(parmMap, "repaymentAccount"))); // 期供金额（本金+利息）
		borrowRepayment.put("interest", ToDouble(MapUtils.getString(parmMap, "interest"))); // 应还利息
		borrowRepayment.put("capital", ToDouble(MapUtils.getString(parmMap, "capital"))); // 应还本金
		borrowRepayment.put("sysbAmt", ToDouble(MapUtils.getString(parmMap, "sysbAmt"))); // 应还贴息(商户贴息)
		borrowRepayment.put("psFeeAmt", ToDouble(MapUtils.getString(parmMap, "psFeeAmt"))); // 应还账户管理费
		borrowRepayment.put("psRemPrcp", MapUtils.getString(parmMap, "psRemPrcp")); // 剩余本金
		borrowRepayment.put("psIntRate", MapUtils.getString(parmMap, "psIntRate")); // 贷款执行利率
		ListCfBorrowRepayment.add(borrowRepayment);
		list.add(hrhaBorrowBatchMap);
		list.add(ListCfBorrowRepayment);

		Map<String, Object> lcMap = lcAppointMapper.getLcAppoint(orderNo);
		String loanCooprCode = MapUtils.getString(lcMap, "loanCooprCode");

		logger.info("应还款计划请求报文:" + gson.toJson(list));
		ReturnParam<Object> returnParam = HrtrustUitls.sendHrHaEnCryptMap(loanCooprCode, gson.toJson(list),
				ThirdApiConstants.HR_REPAYMENT_URL, ThirdApiConstants.HR_REPAYMENT_URL_TIMEOUT);
		logger.info("应还款计划返回报文:" + returnParam);
		if ("FAIL".equals(returnParam.getRetSts())) {
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg("推送应还款计划失败");
			return resp;
		}

		borrowRepayment.put("orderNo", orderNo);
		borrowRepayment.put("batchNo", env_batchNo); // 批次号
		borrowRepayment.put("sendDate", new SimpleDateFormat("yyyy-MM-dd").format(new Date())); // 当前批次数据发送时间
		borrowRepayment.put("allAccount", ToDouble(MapUtils.getString(parmMap, "repaymentAccount"))); // 当前批次总金额
		borrowRepayment.put("all_nums", 1);
		borrowRepayment.put("repaymentTime", MapUtils.getString(parmMap, "repaymentTime"));// 时间转义数据 应还款时间
		borrowRepaymentMapper.saveBorrowRepaymentMapper(borrowRepayment);

		if (returnParam != null && returnParam.getRetCode().equals("00000")) {
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(returnParam.getRetInfo());
		} else if (returnParam != null && !returnParam.getRetCode().equals("00000")) {
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(returnParam.getRetInfo());
		} else {
			resp.setCode(RespStatusEnum.THIRD_ERROR.getCode());
			resp.setMsg("调用华融应还款接口异常");
		}
		return resp;
	}

	/**
	 * 回款接口
	 * 
	 * @param parmMap
	 * @return
	 */
	public RespStatus repaymentRegisterSend(Map<String, Object> parmMap) {
		RespStatus resp = new RespStatus();
		//String env_batchNo = utilBorrowIdService.findBatchNoByOrderNo(orderNo);
		String env_batchNo = "KG" + utilBorrowIdService.getBatchNo();
		String borrowId = utilBorrowIdService.findBorrowIdByOrderNo(orderNo);

		List<Object> list = new ArrayList<Object>();
		List<Object> huaoRechargeVoList = new ArrayList<Object>();
	
		// 回款批次
		Map<String, Object> hrhaRepaymentRegisterBatch = new HashMap<String, Object>();
		hrhaRepaymentRegisterBatch.put("batch_no", env_batchNo);
		hrhaRepaymentRegisterBatch.put("repay_date", MapUtils.getString(parmMap, "repaymentYestime"));
		hrhaRepaymentRegisterBatch.put("send_date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		hrhaRepaymentRegisterBatch.put("all_account", ToDouble(MapUtils.getString(parmMap, "repaymentYesaccount")));
		hrhaRepaymentRegisterBatch.put("all_nums", 1);
		hrhaRepaymentRegisterBatch.put("confirm_repay_date", MapUtils.getString(parmMap, "repaymentYestime"));

		// 回款明细
		List<Map<String, Object>> hrhalist = new ArrayList<Map<String, Object>>();
		Map<String, Object> hrhaRepaymentRegisterDetail = new HashMap<String, Object>();
		hrhaRepaymentRegisterDetail.put("borrowId",borrowId);
		hrhaRepaymentRegisterDetail.put("order", 1);
		hrhaRepaymentRegisterDetail.put("status", 1);
		hrhaRepaymentRegisterDetail.put("repaymentTime",
				Str_date_To_Long(MapUtils.getString(parmMap, "repaymentTime")));
		hrhaRepaymentRegisterDetail.put("repaymentYestime",
				Str_date_To_Long(MapUtils.getString(parmMap, "repaymentYestime")));
		hrhaRepaymentRegisterDetail.put("repaymentAccount", ToDouble(MapUtils.getString(parmMap, "repaymentAccount")));
		hrhaRepaymentRegisterDetail.put("repaymentYesaccount",
				ToDouble(MapUtils.getString(parmMap, "repaymentYesaccount"))
						.subtract(ToDouble(MapUtils.getString(parmMap, "setlLateInterest"))));
		hrhaRepaymentRegisterDetail.put("lateDays", MapUtils.getString(parmMap, "lateDays"));
		hrhaRepaymentRegisterDetail.put("lateInterest", ToDouble(MapUtils.getString(parmMap, "lateInterest")));
		hrhaRepaymentRegisterDetail.put("interest", ToDouble(MapUtils.getString(parmMap, "interest")));
		hrhaRepaymentRegisterDetail.put("capital", ToDouble(MapUtils.getString(parmMap, "capital")));
		hrhaRepaymentRegisterDetail.put("setlCapital", ToDouble(MapUtils.getString(parmMap, "setlCapital")));
		hrhaRepaymentRegisterDetail.put("setlInterest", ToDouble(MapUtils.getString(parmMap, "setlInterest")));
		hrhaRepaymentRegisterDetail.put("setlLateInterest", ToDouble(MapUtils.getString(parmMap, "setlLateInterest")));
		hrhaRepaymentRegisterDetail.put("sysbAmt", ToDouble(MapUtils.getString(parmMap, "sysbAmt")));
		hrhaRepaymentRegisterDetail.put("setlSysbAmt", ToDouble(MapUtils.getString(parmMap, "setlSysbAmt")));
		hrhaRepaymentRegisterDetail.put("setlFeeAmt", ToDouble(MapUtils.getString(parmMap, "setlFeeAmt")));
		if (MapUtils.getIntValue(parmMap, "lateDays", 0) > 0) {
			hrhaRepaymentRegisterDetail.put("ppErInd", "Y");
		} else {
			hrhaRepaymentRegisterDetail.put("ppErInd", "N");
		}
		hrhaRepaymentRegisterDetail.put("psOdInd", "N");
		hrhaRepaymentRegisterDetail.put("psIntRate", ToDouble(MapUtils.getString(parmMap, "psIntRate")));
		hrhaRepaymentRegisterDetail.put("psRemPrcp", ToDouble(MapUtils.getString(parmMap, "psRemPrcp")));
		hrhaRepaymentRegisterDetail.put("borrowStatus", "Y");
		hrhaRepaymentRegisterDetail.put("repaymentStatus", "Y");
		hrhaRepaymentRegisterDetail.put("isLastPeriod", "Y");
		hrhalist.add(hrhaRepaymentRegisterDetail);
		list.add(hrhaRepaymentRegisterBatch);
		list.add(hrhalist);
		list.add(huaoRechargeVoList);
		Map<String, Object> lcMap = lcAppointMapper.getLcAppoint(orderNo);
		String loanCooprCode = MapUtils.getString(lcMap, "loanCooprCode");
		logger.info("回款请求报文:" + gson.toJson(list));
		ReturnParam<Object> returnParam = HrtrustUitls.sendHrHaEnCryptMap(loanCooprCode, gson.toJson(list),
				ThirdApiConstants.HR_PAY_MENT_PLAN_URL, ThirdApiConstants.HR_PAY_MENT_PLAN_URL_TIMEOUT);
		logger.info("回款返回报文:" + returnParam);
		if (returnParam.getRetSts().equals("FAIL")) {
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg("推送回款计划失败");
			return resp;
		}

		hrhaRepaymentRegisterDetail.put("batchNo", env_batchNo);
		hrhaRepaymentRegisterDetail.put("repayDate", MapUtils.getString(parmMap, "repaymentYestime"));
		hrhaRepaymentRegisterDetail.put("sendDate", MapUtils.getString(parmMap, "repaymentYesaccount"));
		hrhaRepaymentRegisterDetail.put("confirmRepayDate", MapUtils.getString(parmMap, "repaymentYestime"));
		hrhaRepaymentRegisterDetail.put("allNum", 1);
		hrhaRepaymentRegisterDetail.put("orderNo", orderNo);
		hrhaRepaymentRegisterDetail.put("psIntRate", MapUtils.getString(parmMap, "psIntRate"));
		hrhaRepaymentRegisterDetail.put("repaymentTime",MapUtils.getString(parmMap, "repaymentTime"));
		hrhaRepaymentRegisterDetail.put("repaymentYestime",MapUtils.getString(parmMap, "repaymentYestime"));
		
		repaymentRegisterMapper.saveRepaymentRegister(hrhaRepaymentRegisterDetail);

		if (returnParam != null && returnParam.getRetCode().equals("00000")) {
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(returnParam.getRetInfo());
		} else if (returnParam != null && !returnParam.getRetCode().equals("00000")) {
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(returnParam.getRetInfo());
		} else {
			resp.setCode(RespStatusEnum.THIRD_ERROR.getCode());
			resp.setMsg("调用华融回款接口异常");
		}
		return resp;
	}

	/**
	 * 放款状态查询
	 * 
	 * @param parmMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public RespDataObject<Map<String, Object>> queryLoanStatusSend(Map<String, Object> parmMap) {
		RespDataObject<Map<String, Object>> resp = new RespDataObject<Map<String, Object>>();
		String batchNo = utilBorrowIdService.findBatchNoByOrderNo(orderNo);
		if (batchNo == null) {
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg("待推送");
			return resp;
		}
		Map<String, Object> lcMap = lcAppointMapper.getLcAppoint(orderNo);
		String loanCooprCode = MapUtils.getString(lcMap, "loanCooprCode");
		logger.info("放款状态请求批次号:" + batchNo);
		ReturnParam<Object> returnParam = HrtrustUitls.sendHrHaEnCryptMap(loanCooprCode, batchNo,
				ThirdApiConstants.HR_QUERY_LOAN_STATUS_URL, ThirdApiConstants.HR_QUERY_LOAN_STATUS_URL_TIMEOUT);
		logger.info("放款状态返回批次号:" + returnParam);
		Map<String, Object> loanMap = (Map<String, Object>) returnParam.getRetData();
		if (returnParam != null && returnParam.getRetCode().equals("00000")) {
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setData(loanMap);
			// 接口返回消息入库
			if (loanMap != null && !loanMap.isEmpty()) {
				loanMap.put("orderNo", orderNo);
				loanStatusMapper.saveLoanStatus(loanMap);
			}
			resp.setMsg(returnParam.getRetInfo());
		} else if (returnParam != null && !returnParam.getRetCode().equals("00000")) {
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(returnParam.getRetInfo());
			resp.setData(loanMap);
			// 接口返回消息入库
			if (loanMap != null && !loanMap.isEmpty()) {
				loanMap.put("loanMap", orderNo);
				loanStatusMapper.saveLoanStatus(loanMap);
			}
		} else {
			resp.setCode(RespStatusEnum.THIRD_ERROR.getCode());
			resp.setMsg("调用华融放款接口异常");
		}
		return resp;
	}

	private String getApplSeq() {
		String applSeq = "";
		Map<String, Object> statusMap = loanStatusMapper.getLoanStatus(orderNo);
		if (statusMap != null && !statusMap.isEmpty()) {
			if ("992".equals(MapUtils.getString(statusMap, "dealStatus"))) {
				Map<String, Object> lcMap = lcAppointMapper.getLcAppoint(orderNo);
				if (lcMap != null && !lcMap.isEmpty()) {
					applSeq = MapUtils.getString(lcMap, "applSeq");
				}
			}
		}
		if (StringUtils.isEmpty(applSeq)) {
			applSeq = "KG" + utilBorrowIdService.getApplSeq();
		}
		return applSeq;
	}

	private long getBorrowId() {
		long borrowId = 0;
		Map<String, Object> statusMap = loanStatusMapper.getLoanStatus(orderNo);
		if (statusMap != null && !statusMap.isEmpty()) {
			if (!"992".equals(MapUtils.getString(statusMap, "dealStatus"))) {
				Map<String, Object> lendMap = hrtrustLoanMapper.selectHrtrustLoan(orderNo);
				if (lendMap != null && !lendMap.isEmpty()) {
					borrowId = MapUtils.getLongValue(lendMap, "borrowId", 0);
					//borrowId = utilBorrowIdService.getBorrowId();
				}
			}
		}
		if (borrowId == 0) {
			borrowId = utilBorrowIdService.getBorrowId();
		}
		return borrowId;
	}

	public static String Str_date_To_Long(String smdate) {
		if (StringUtils.isEmpty(smdate)) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(sdf.parse(smdate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long time1 = cal.getTimeInMillis();
		return time1 / 1000 + "";
	}

	// 转换万元为元
	private static String multiplication(String str) {
		if (StringUtil.isEmpty(str)) {
			return str;
		}
		BigDecimal bd = new BigDecimal(str);
		BigDecimal b2 = new BigDecimal(10000);
		return String.valueOf(bd.multiply(b2));
	}

	// 转换为元
	private static String multiplicationY(String str) {
		if (StringUtil.isEmpty(str)) {
			return str;
		}
		BigDecimal bd = new BigDecimal(str);
		return String.valueOf(bd);
	}

	public static BigDecimal ToDouble(String str) {
		BigDecimal bd = new BigDecimal(str);
		if (str.equals("0")) {
			bd = bd.setScale(0, BigDecimal.ROUND_HALF_UP);
		} else {
			bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
		}
		return bd;
	}
	


	public String getRspContent(String http_requrl) {
		String base_url = Constants.LINK_ANJBO_FS_URL;
		if (http_requrl.contains(".pdf")) {
			http_requrl = base_url + "/fs/Hr/filePDFStream?after_filepath=" + http_requrl;
		} else {
			http_requrl = base_url + "/fs/Hr/fileStream?after_filepath=" + http_requrl;
		}
		try {
			return SingleUtils.getRestTemplate().getForObject(http_requrl, String.class);
		} catch (Exception e) {
			logger.error("调用fs项目获取base64编码字符串失败", e);
			return "";
		}
	}
	
		public static String getString() {
			String str = "A";
			try {
				str = "B";
				return str;
			} finally {
				System.out.println("finally change return string to C");
				str = "C";
				return str;
			}
		}
	
	public static void main(String[] args) {
		/*System.out.println(getString());
		long timeStampSec = System.currentTimeMillis()/1000;
        String timestamp = String.format("%010d", timeStampSec);
        System.out.println(timestamp);*/
	}
}
