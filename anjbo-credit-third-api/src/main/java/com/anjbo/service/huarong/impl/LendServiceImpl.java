package com.anjbo.service.huarong.impl;

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
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.dao.huarong.BorrowLendMapper;
import com.anjbo.dao.huarong.LcAppointMapper;
import com.anjbo.dao.huarong.LoanStatusMapper;
import com.anjbo.service.huarong.LendService;
import com.anjbo.utils.huarong.FromAnjboToHrKeyValue;
import com.anjbo.utils.huarong.JsonUtils;
import com.anjbo.utils.huarong.ReturnParam;
import com.anjbo.utils.huarong.SendHrHaEnCrypt;
/**
 * 
* @ClassName: LendServiceImpl 
* @Description: TODO(放款数据发送) 
* @author xufx
* @date 2017年8月16日 下午2:05:05 
*
 */
@Service
public class LendServiceImpl implements LendService {
	@Resource
	private UtilBorrowIdService utilBorrowIdService;
	@Resource
	private BorrowLendMapper borrowLendMapper;
	@Resource
	private LoanStatusMapper loanStatusMapper;
	@Resource
	private LcAppointMapper lcAppointMapper;
	
	Logger log = Logger.getLogger(LendServiceImpl.class);

	@Override
	public RespStatus lendSend(Map<String,Object> requestData) throws Exception {
		
	    //Map<String,Object> requestMap=JsonUtil.jsonToMap(requestData);
		log.info("放款接口接收参数:"+requestData);
		
				String orderNo=requestData.get("orderNo").toString();
				
				//判断是否为打回的订单，若为打回的订单则去查找使用原申请编号
				Map LoanMap =loanStatusMapper.getLoanStatus(orderNo);
				boolean Flag_success=true;//默认标志变量为正常单
				if(LoanMap!=null&&!LoanMap.isEmpty()) {
					if(LoanMap.get("dealStatus").equals("992")) {//992审批打回
						Flag_success=false;
					}
				}	
				
				long borrowId=utilBorrowIdService.getBorrowId();//默认生成的借款id
				
				if(!Flag_success) {//如果被打回，使用原来的编号
					
					Map lendMap=borrowLendMapper.getBorrowLend(orderNo);
					if(lendMap!=null&&!lendMap.isEmpty()) {
						borrowId=Long.parseLong(lendMap.get("borrowId").toString());
					}
				}
				
		
		RespStatus resp = new RespStatus();
		Map keyValueMap=FromAnjboToHrKeyValue.KeyValue();
	    String env_batchNo="KG"+utilBorrowIdService.getBatchNo();
	    List<Map<String,Object>> sendBorrowDetailList=new ArrayList<Map<String,Object>>();

		Map<String,Object> hrhaBorrowBatchMap=new HashMap<String,Object>();
		hrhaBorrowBatchMap.put("batchNo",env_batchNo);
		hrhaBorrowBatchMap.put("sendDate",new SimpleDateFormat("yyyy-MM-dd").format(new Date()));//时间获取
		hrhaBorrowBatchMap.put("allNums", "1");
		hrhaBorrowBatchMap.put("allAccount", multiplication(requestData.get("applyAmt").toString()));
		
		Map<String,Object> BorrowDetailmap1=new HashMap<String,Object>();
		
		BorrowDetailmap1.put("borrowId", borrowId);
		log.info("Apply.url请求报文env_ApplSeq------------------------------------borrowId:"+BorrowDetailmap1.get("borrowId"));
		BorrowDetailmap1.put("batchNo", env_batchNo);
		BorrowDetailmap1.put("applSeq", utilBorrowIdService.findApplSeqByOrderNo(requestData.get("orderNo").toString()));
		BorrowDetailmap1.put("bchCde", "KG");
		BorrowDetailmap1.put("bchName", requestData.get("bchName"));//BorrowDetailmap1.put("bchName", "快鸽");
		BorrowDetailmap1.put("custName",requestData.get("custName"));
		BorrowDetailmap1.put("idType", keyValueMap.get(requestData.get("idType")));//转换数据，证件类型
		BorrowDetailmap1.put("idNo", requestData.get("idNo"));
		BorrowDetailmap1.put("applyAmt", multiplication(requestData.get("applyAmt").toString()));//贷款申请金额
		BorrowDetailmap1.put("apprvAmt", multiplication(requestData.get("apprvAmt").toString()));//审批通过金额
		BorrowDetailmap1.put("applyTnrUnit", keyValueMap.get(requestData.get("applyTnrUnit")));//转换数据,借款期限单位
		BorrowDetailmap1.put("applyTnr", requestData.get("applyTnr"));
		BorrowDetailmap1.put("mtdCde", keyValueMap.get(requestData.get("mtdCde")));//转换数据，还款方式
		BorrowDetailmap1.put("apr", requestData.get("apr"));
		BorrowDetailmap1.put("odIntRate", requestData.get("odIntRate"));
		BorrowDetailmap1.put("sysbPct", requestData.get("sysbPct"));
		BorrowDetailmap1.put("sysbAmt", requestData.get("sysbAmt"));
		BorrowDetailmap1.put("hrdPct", requestData.get("hrdPct"));
		BorrowDetailmap1.put("hrdAmt", requestData.get("hrdAmt"));
		BorrowDetailmap1.put("ifCeOut", requestData.get("ifCeOut"));
		BorrowDetailmap1.put("repaymentTime", requestData.get("repaymentTime")!=null?Str_date_To_Long(requestData.get("repaymentTime").toString()):"");
		
		
		
		sendBorrowDetailList.add(BorrowDetailmap1);
		
		
		List<Object> list=new ArrayList<Object>();
		
		List<Object> feeList=new ArrayList<Object>();
		 
		
		list.add(hrhaBorrowBatchMap);
		list.add(sendBorrowDetailList);
		list.add(feeList);
		
		
		//String loanCooprCode="KG";
		//String loanCooprCode=MapUtils.getString(requestData, "loanCooprCode");
		
		Map lcMap=lcAppointMapper.getLcAppoint(orderNo);
		
		String loanCooprCode=MapUtils.getString(lcMap, "loanCooprCode");
		
		
		System.out.println("报文:"+JsonUtils.toJsonString(list));
        
		ReturnParam  param=SendHrHaEnCrypt.sendHrHaEnCryptMap(loanCooprCode, JsonUtils.toJsonString(list), "lend.url");
		
		log.info("lend.url返回报文:"+param);
		BorrowDetailmap1.put("respStatus", param!=null ?param.getRetCode():"" );
		
		BorrowDetailmap1.put("orderId", requestData.get("orderNo"));	
		
		
		System.out.println("param"+param);
        
		//重置转义字段为快鸽原始字段入库
		BorrowDetailmap1.put("idType", requestData.get("idType"));//转换数据，证件类型
		BorrowDetailmap1.put("applyTnrUnit", requestData.get("applyTnrUnit"));//转换数据,借款期限单位
		BorrowDetailmap1.put("mtdCde", requestData.get("mtdCde"));//转换数据，还款方式
		BorrowDetailmap1.put("repaymentTime", requestData.get("repaymentTime"));//时间格式转义字段
		//重置转元为万元
		BorrowDetailmap1.put("applyAmt", requestData.get("applyAmt"));//贷款申请金额
		BorrowDetailmap1.put("apprvAmt", requestData.get("apprvAmt"));//审批通过金额
		
		borrowLendMapper.saveBorrowLend(BorrowDetailmap1);
		
		if(param!=null&&param.getRetCode().equals("00000")) {
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(param.getRetInfo());
		}else if(param!=null&&!param.getRetCode().equals("00000")) {
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(param.getRetInfo());
		}else{
			resp.setCode(RespStatusEnum.THIRD_ERROR.getCode());
			resp.setMsg("调用华融放款接口异常");
		}	
		
		return resp;
	}
	
	   /** 
	    *字符串的日期格式转long类型
	    */  
	        public static long Str_date_To_Long(String smdate){  
	            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
	            Calendar cal = Calendar.getInstance();    
	            try {
	    			cal.setTime(sdf.parse(smdate));
	    		} catch (ParseException e) {
	    			e.printStackTrace();
	    		}    
	            long time1 = cal.getTimeInMillis();
				return time1/1000; 
	        }
	      //转换万元为元
	   	 public static String multiplication(String str) {
	   		 BigDecimal bd = new BigDecimal(str);  
	   		 BigDecimal b2 = new BigDecimal(10000); 
	   		return String.valueOf(bd.multiply(b2)); 
	   	 }

}
