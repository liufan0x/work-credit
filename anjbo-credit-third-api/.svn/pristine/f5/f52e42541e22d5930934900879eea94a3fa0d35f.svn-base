package com.anjbo.service.huarong.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.dao.huarong.KgAppointMapper;
import com.anjbo.dao.huarong.KgApprovalMapper;
import com.anjbo.dao.huarong.KgHouseMapper;
import com.anjbo.dao.huarong.KgIndivMapper;
import com.anjbo.dao.huarong.LcApplApptMapper;
import com.anjbo.dao.huarong.LcAppointMapper;
import com.anjbo.dao.huarong.LcApptIndivMapper;
import com.anjbo.dao.huarong.LoanStatusMapper;
import com.anjbo.service.huarong.ApplyService;
import com.anjbo.utils.StringUtil;
import com.anjbo.utils.huarong.FromAnjboToHrKeyValue;
import com.anjbo.utils.huarong.JsonUtils;
import com.anjbo.utils.huarong.ReturnParam;
import com.anjbo.utils.huarong.SendHrHaEnCrypt;
/**
 * 
* @ClassName: ApplyServiceImpl 
* @Description: TODO(申请数据发送) 
* @author xufx
* @date 2017年8月16日 下午2:02:51 
*
 */
@Service
public class ApplyServiceImpl implements ApplyService {
	
	@Resource
	private LoanStatusMapper loanStatusMapper;
	@Resource
	private LcAppointMapper lcAppointMapper;
	
	@Resource
	private LcApplApptMapper lcApplApptMapper;
	
	@Resource
	private LcApptIndivMapper lcApptIndivMapper;
	
	@Resource
	private KgAppointMapper kgAppointMapper;
	
	@Resource
	private KgApprovalMapper kgApprovalMapper;
	
	@Resource
	private KgHouseMapper kgHouseMapper;
	
	@Resource
	private KgIndivMapper kgIndivMapper;
	
	@Resource
	private UtilBorrowIdService utilBorrowIdService;
	
	Logger log = Logger.getLogger(ApplyServiceImpl.class);

	@Override
	public RespStatus applySend(Map<String,Object> parmMap) throws Exception {
		
		RespStatus resp = new RespStatus();
		
		Map keyValueMap=FromAnjboToHrKeyValue.KeyValue();
		

		//Map<String,Object> requestMap=JsonUtil.jsonToMap(requestData);
		
		JSONObject json_lcApptIndiv=JSONObject.fromObject(parmMap.get("lcApptIndiv"));	
		JSONObject json_lcApplAppt=JSONObject.fromObject(parmMap.get("lcApplAppt"));	
		JSONObject json_lcAppoint=JSONObject.fromObject(parmMap.get("lcAppoint"));	
		JSONObject json_kgAppoint=JSONObject.fromObject(parmMap.get("kgAppoint"));	
		JSONObject json_kgIndiv=JSONObject.fromObject(parmMap.get("kgIndiv"));	
		JSONObject json_kgHouse=JSONObject.fromObject(parmMap.get("kgHouse"));	
		JSONObject json_kgApproval=JSONObject.fromObject(parmMap.get("kgApproval"));	
		
		String orderNo=json_kgAppoint.get("orderNo").toString();//获取订单传送过来的单号
		
		//判断是否为打回的订单，若为打回的订单则去查找使用原申请编号
		Map LoanMap =loanStatusMapper.getLoanStatus(orderNo);
		boolean Flag_success=true;
		if(LoanMap!=null&&!LoanMap.isEmpty()) {
			if(LoanMap.get("dealStatus").equals("992")) {//992审批打回
				Flag_success=false;
			}
		}
		
		String env_ApplSeq="KG"+utilBorrowIdService.getApplSeq();//默认是生成最新编号
		
		
		if(!Flag_success) {//如果被打回，使用原来的编号
			
			Map lcMap=lcAppointMapper.getLcAppoint(orderNo);
			if(lcMap!=null&&!lcMap.isEmpty()) {
				env_ApplSeq=lcMap.get("applSeq").toString();
			}
		}
		
		
		
		log.info("Apply.url请求报文env_ApplSeq------------------------------------:"+env_ApplSeq);
		

		Map<String,Object> map=new HashMap<String,Object>();
		
		Map<String,Object> lcApptIndiv=new 	HashMap<String,Object>();			
		lcApptIndiv.put("applSeq",env_ApplSeq );	
		
		lcApptIndiv.put("custName", MapUtils.getString(json_lcApptIndiv, "custName",""));	
		lcApptIndiv.put("indivEdt", keyValueMap.get(json_lcApptIndiv.get("indivEdt").toString().trim()));//转义数据，教育程度
		log.info(json_lcApptIndiv.get("indivEdt"));
		
		
	/*	
		Map<String,Object> lcApptAsset=new 	HashMap<String,Object>();
		lcApptAsset.put("applSeq", "KG1827126101");
		lcApptAsset.put("assetKind", "ASSET");
		lcApptAsset.put("assetTyp", "VEC_FIRST_HAND");
		lcApptAsset.put("assetAmt", "666");*/
	
		
		Map<String,Object> lcAppoint=new HashMap<String,Object>();
		

		lcAppoint.put("applSeq", env_ApplSeq);

		lcAppoint.put("custName",MapUtils.getString(json_lcAppoint, "custName",""));
		
		lcAppoint.put("idType",keyValueMap.get(json_lcAppoint.get("idType")));//转换数据，证件类型
		
		lcAppoint.put("idNo", MapUtils.getString(json_lcAppoint, "idNo",""));
		
		lcAppoint.put("mobile", MapUtils.getString(json_lcAppoint, "mobile",""));
		
		lcAppoint.put("mtdCde", keyValueMap.get(json_lcAppoint.get("mtdCde")));//转换数据，还款方式
		
		lcAppoint.put("applyAmt", multiplication(json_lcAppoint.get("applyAmt").toString()));
		
		log.info("Apply.url请求报文applyAmt------------------------------------:"+multiplication(json_lcAppoint.get("applyAmt").toString()));
		
		lcAppoint.put("applyTnr", json_lcAppoint.get("applyTnr"));//申请期限(按月)
		
		lcAppoint.put("apr",MapUtils.getString(json_lcAppoint, "apr",""));//利率
		
		lcAppoint.put("use",  keyValueMap.get(json_lcAppoint.get("useHr")));//转换数据，借款用途
		
		lcAppoint.put("cooprCode", "");//场景商户代码
		
		lcAppoint.put("applyTnrUnit", keyValueMap.get(json_lcAppoint.get("applyTnrUnit")));//转换数据,借款期限单位

		lcAppoint.put("apprvAmt", multiplication(json_lcAppoint.get("apprvAmt").toString()));
		
		//lcAppoint.put("productId","KG001");
		
		lcAppoint.put("productId",json_lcAppoint.get("productId"));
		
		log.info("productId:"+lcAppoint.get("productId"));
		
		//lcAppoint.put("loanCooprCode", "KG");
		
		lcAppoint.put("loanCooprCode", json_lcAppoint.get("loanCooprCode"));
		
		
		

		Map<String,Object> lcApplAppt=new HashMap<String,Object>();
		
		
		lcApplAppt.put("applSeq", env_ApplSeq);
		lcApplAppt.put("idNo",  MapUtils.getString(json_lcApplAppt, "idNo",""));
		lcApplAppt.put("idType",  MapUtils.getString(json_lcApplAppt, "idType",""));
		lcApplAppt.put("custName", MapUtils.getString(json_lcApplAppt, "custName",""));
		
		//发送快鸽提单信息
		
		Map<String,Object> kgAppoint=new HashMap<String,Object>();	
		
		kgAppoint.put("applSeq",env_ApplSeq);
		kgAppoint.put("businessType",MapUtils.getString(json_kgAppoint, "businessType",""));
		kgAppoint.put("custName",MapUtils.getString(json_kgAppoint, "custName",""));
		kgAppoint.put("loanAmount",multiplication(json_kgAppoint.get("loanAmount").toString()));//借款金额
		kgAppoint.put("term",MapUtils.getString(json_kgAppoint, "term",""));
		kgAppoint.put("rate",MapUtils.getString(json_kgAppoint, "rate",""));
		kgAppoint.put("overdueTate",MapUtils.getString(json_kgAppoint, "overdueTate",""));
		kgAppoint.put("yOriLenAmount",multiplication(json_kgAppoint.get("yOriLenAmount").toString()));//原放贷金额
		kgAppoint.put("yLoanBalance",multiplication(json_kgAppoint.get("yLoanBalance").toString()));//贷款余额
		kgAppoint.put("yIsBank",MapUtils.getString(json_kgAppoint, "yIsBank",""));
		kgAppoint.put("yOriBank",MapUtils.getString(json_kgAppoint, "yOriBank",""));
		kgAppoint.put("xLoanAmount",multiplication(json_kgAppoint.get("xLoanAmount").toString()));//贷款金额
		kgAppoint.put("xIsBank",MapUtils.getString(json_kgAppoint, "xIsBank",""));
		kgAppoint.put("xLoanBank",MapUtils.getString(json_kgAppoint, "xLoanBank",""));
		kgAppoint.put("fAccountType",MapUtils.getString(json_kgAppoint, "fAccountType",""));
		kgAppoint.put("fOpenBank",MapUtils.getString(json_kgAppoint, "fOpenBank",""));
		kgAppoint.put("fOpenName",MapUtils.getString(json_kgAppoint, "fOpenName",""));
		kgAppoint.put("fAccountNum",MapUtils.getString(json_kgAppoint, "fAccountNum",""));
		kgAppoint.put("fCaacNo",MapUtils.getString(json_kgAppoint, "fCaacNo",""));
		kgAppoint.put("hAccountType",MapUtils.getString(json_kgAppoint, "hAccountType",""));
		kgAppoint.put("hOpenBank",MapUtils.getString(json_kgAppoint, "hOpenBank",""));
		kgAppoint.put("hOpenName",MapUtils.getString(json_kgAppoint, "hOpenName",""));
		kgAppoint.put("hAccountNum",MapUtils.getString(json_kgAppoint, "hAccountNum",""));
		kgAppoint.put("hCaacNo",MapUtils.getString(json_kgAppoint, "hCaacNo",""));
		
		Map<String,Object> kgIndiv=new HashMap<String,Object>();
		
		kgIndiv.put("applSeq", env_ApplSeq);
		kgIndiv.put("custName", MapUtils.getString(json_kgIndiv, "custName",""));
		kgIndiv.put("docType", MapUtils.getString(json_kgIndiv, "docType",""));
		kgIndiv.put("docNo", MapUtils.getString(json_kgIndiv, "docNo",""));
		kgIndiv.put("mobile", MapUtils.getString(json_kgIndiv, "mobile",""));
		kgIndiv.put("marStatus", MapUtils.getString(json_kgIndiv, "marStatus",""));
		kgIndiv.put("sCustName",  MapUtils.getString(json_kgIndiv, "sCustName",""));
		kgIndiv.put("sDocType", MapUtils.getString(json_kgIndiv, "sDocType",""));
		kgIndiv.put("sDocNo", MapUtils.getString(json_kgIndiv, "sDocNo",""));
		kgIndiv.put("sMobile",MapUtils.getString(json_kgIndiv, "sMobile",""));
		kgIndiv.put("sMarStatus", MapUtils.getString(json_kgIndiv, "sMarStatus",""));
		kgIndiv.put("cardLife", MapUtils.getString(json_kgIndiv, "cardLife",""));
		kgIndiv.put("totalPremises", multiplication(json_kgIndiv.get("totalPremises").toString()));//所有房产评估总值
		kgIndiv.put("totalCredit", multiplication(json_kgIndiv.get("totalCredit").toString()));//授信总额
		kgIndiv.put("debtRatio", MapUtils.getString(json_kgIndiv, "debtRatio",""));
		kgIndiv.put("overdueCredit", MapUtils.getString(json_kgIndiv, "overdueCredit",""));
		kgIndiv.put("yIsBank", MapUtils.getString(json_kgIndiv, "yIsBank",""));
		kgIndiv.put("loanYear", MapUtils.getString(json_kgIndiv, "loanYear",""));	
		kgIndiv.put("houseNum", MapUtils.getString(json_kgIndiv, "houseNum",""));
		kgIndiv.put("quotaUsed", multiplication(json_kgIndiv.get("quotaUsed").toString()));//已用额度
		kgIndiv.put("loanAmount", MapUtils.getString(json_kgIndiv, "loanAmount",""));		
		kgIndiv.put("creditFindNum",MapUtils.getString(json_kgIndiv, "creditFindNum",""));
		kgIndiv.put("xIsBank", MapUtils.getString(json_kgIndiv, "xIsBank",""));
		kgIndiv.put("defaultRate", MapUtils.getString(json_kgIndiv, "defaultRate",""));
		kgIndiv.put("overdraft", multiplication(json_kgIndiv.get("overdraft").toString()));//信用卡半年月均透支额
		kgIndiv.put("totalCreditLia", multiplication(json_kgIndiv.get("totalCreditLia").toString()));//征信总负债
		kgIndiv.put("foreclosureRate",  MapUtils.getString(json_kgIndiv, "foreclosureRate",""));
		kgIndiv.put("companyRight", MapUtils.getString(json_kgIndiv, "companyRight",""));
		kgIndiv.put("rightMortgage", MapUtils.getString(json_kgIndiv, "rightMortgage",""));
		kgIndiv.put("remark", MapUtils.getString(json_kgIndiv, "remark",""));
		

		Map<String,Object> kgHouse=new HashMap<String,Object>();
		
		kgHouse.put("applSeq", env_ApplSeq);
		kgHouse.put("owner", json_kgHouse.get("owner"));
		kgHouse.put("houseAddress", MapUtils.getString(json_kgHouse, "houseAddress",""));
		kgHouse.put("builtArea", MapUtils.getString(json_kgHouse, "builtArea",""));
		kgHouse.put("houseNo", MapUtils.getString(json_kgHouse, "houseNo",""));
	
		kgHouse.put("totalAssessment", multiplicationY(MapUtils.getString(json_kgHouse, "totalAssessment","")));
		kgHouse.put("worthAssessment", multiplicationY(MapUtils.getString(json_kgHouse, "worthAssessment","")));
		kgHouse.put("firstHouseLoan", multiplicationY(MapUtils.getString(json_kgHouse, "firstHouseLoan","")));
		
		
		kgHouse.put("consult", MapUtils.getString(json_kgHouse, "consult",""));
		kgHouse.put("remark", MapUtils.getString(json_kgHouse, "remark",""));
		
		Map<String,Object> kgApproval=new HashMap<String,Object>();
		
		kgApproval.put("applSeq", env_ApplSeq);
		kgApproval.put("trial", MapUtils.getString(json_kgApproval, "trial",""));
		kgApproval.put("judgment",MapUtils.getString(json_kgApproval, "judgment",""));
		kgApproval.put("chiefRiskOpinion",MapUtils.getString(json_kgApproval, "chiefRiskOpinion",""));
		
		
		
		map.put("lcApptIndiv", lcApptIndiv);//requestMap.get("lcApptIndiv");
		//map.put("lcApptAsset", lcApptAsset);//requestMap.get("lcApptAsset");
		map.put("lcAppoint", lcAppoint);//requestMap.get("lcAppoint");
		map.put("lcApplAppt", lcApplAppt);//requestMap.get("lcApplAppt");
		log.info("kgAppoint:"+kgAppoint);
		map.put("kgAppoint", kgAppoint);//requestMap.get("kgAppoint");
		map.put("kgIndiv", kgIndiv);//requestMap.get("kgIndiv");
		map.put("kgHouse", kgHouse);//requestMap.get("kgHouse");
		map.put("kgApproval", kgApproval);//requestMap.get("kgApproval");

		
		//String loanCooprCode="KG";
		
		String loanCooprCode=json_lcAppoint.get("loanCooprCode").toString();
        
		log.info("Apply.url请求报文:"+JsonUtils.toJsonString(map));
		ReturnParam  param=SendHrHaEnCrypt.sendHrHaEnCryptMap(loanCooprCode, JsonUtils.toJsonString(map), "Apply.url");
		
		//log.info("Apply.url返回报文:"+param);
		
		
		kgApproval.put("orderId", json_kgApproval.get("orderNo"));
		lcApplAppt.put("orderId", json_lcApplAppt.get("orderNo"));
		kgAppoint.put("orderId", json_kgAppoint.get("orderNo"));
		kgIndiv.put("orderId", json_kgIndiv.get("orderNo"));
		kgHouse.put("orderId", json_kgHouse.get("orderNo"));
		lcAppoint.put("orderId", json_lcAppoint.get("orderNo"));
		lcApptIndiv.put("orderId", json_lcApptIndiv.get("orderNo"));
		
		//重置转义字段为快鸽原始字段入库
		lcApptIndiv.put("indivEdt", json_lcApptIndiv.get("indivEdt"));//转义数据，教育程度
		lcAppoint.put("idType",json_lcAppoint.get("idType"));//转换数据，证件类型
		lcAppoint.put("mtdCde", json_lcAppoint.get("mtdCde"));//转换数据，还款方式
		lcAppoint.put("use",  json_lcAppoint.get("useHr"));//转换数据，借款用途
		lcAppoint.put("applyTnrUnit", json_lcAppoint.get("applyTnrUnit"));//转换数据,借款期限单位
		
		//重置转换元为万元
		lcAppoint.put("applyAmt", json_lcAppoint.get("applyAmt"));
		lcAppoint.put("apprvAmt", json_lcAppoint.get("apprvAmt"));
		kgAppoint.put("loanAmount",json_kgAppoint.get("loanAmount"));//借款金额
		kgAppoint.put("yOriLenAmount",json_kgAppoint.get("yOriLenAmount"));//原放贷金额
		kgAppoint.put("yLoanBalance",json_kgAppoint.get("yLoanBalance"));//贷款余额
		kgAppoint.put("xLoanAmount",json_kgAppoint.get("xLoanAmount"));//贷款金额
		kgIndiv.put("totalPremises", json_kgIndiv.get("totalPremises"));//所有房产评估总值
		kgIndiv.put("totalCredit", json_kgIndiv.get("totalCredit"));//授信总额
		kgIndiv.put("quotaUsed", json_kgIndiv.get("quotaUsed"));//已用额度
		kgIndiv.put("overdraft", json_kgIndiv.get("overdraft"));//信用卡半年月均透支额
		kgIndiv.put("totalCreditLia", json_kgIndiv.get("totalCreditLia"));//征信总负债
	
		
		lcAppointMapper.saveLcAppoint(lcAppoint);
		lcApplApptMapper.saveLcApplAppt(lcApplAppt);
	    lcApptIndivMapper.saveLcApptIndiv(lcApptIndiv);
		kgAppointMapper.saveKgAppoint(kgAppoint);
		kgApprovalMapper.saveKgApproval(kgApproval);
		kgHouseMapper.saveKgHouse(kgHouse);
		kgIndivMapper.saveKgIndiv(kgIndiv);
		System.out.println("报文:"+JsonUtils.toJsonString(map));
		
		System.out.println("param"+param);
		
		
		if(param!=null&&param.getRetCode().equals("00000")) {
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(param.getRetInfo());
		}else if(param!=null&&!param.getRetCode().equals("00000")) {
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(param.getRetInfo());
		}else{
			resp.setCode(RespStatusEnum.THIRD_ERROR.getCode());
			resp.setMsg("调用华融申请数据发送接口异常");
		}	
		
	
		return resp;
	}

	//转换万元为元
	 public static String multiplication(String str) {
		 if(StringUtil.isEmpty(str)) {
			 return str;
		 }
		 BigDecimal bd = new BigDecimal(str);  
		 BigDecimal b2 = new BigDecimal(10000); 
		return String.valueOf(bd.multiply(b2)); 
	 }
	 //转换为元
	 public static String multiplicationY(String str) {
		 if(StringUtil.isEmpty(str)) {
			 return str;
		 }
		 BigDecimal bd = new BigDecimal(str);  
		 //BigDecimal b2 = new BigDecimal(10000); 
		return String.valueOf(bd); 
	 }
	 public static void main(String[] args) {
		 System.out.println(multiplication("1.6784428E7"));
	}
}
