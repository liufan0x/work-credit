package com.anjbo.service.huarong.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
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
import com.anjbo.dao.huarong.LcAppointMapper;
import com.anjbo.dao.huarong.RepaymentRegisterMapper;
import com.anjbo.service.huarong.RepaymentRegisterService;
import com.anjbo.utils.huarong.JsonUtils;
import com.anjbo.utils.huarong.ReturnParam;
import com.anjbo.utils.huarong.SendHrHaEnCrypt;
/**
 * 
* @ClassName: PayMentPlanServiceImpl 
* @Description: TODO(回款计划发送) 
* @author xufx
* @date 2017年8月16日 下午3:03:20 
*
 */
@Service
public class RepaymentRegisterServiceImpl implements RepaymentRegisterService {
	
	@Resource
	private RepaymentRegisterMapper repaymentRegisterMapper;
	
	@Resource
	private UtilBorrowIdService utilBorrowIdService;
	@Resource
	private LcAppointMapper lcAppointMapper;
	
	Logger log = Logger.getLogger(RepaymentRegisterServiceImpl.class);
		/**
		 *    8
		 * @throws Exception 
		 */
	@Override
	public RespStatus repaymentRegisterSend(Map<String,Object> map) throws Exception {
		
		log.info("回款接口接收参数:"+map);
		
		RespStatus resp = new RespStatus();
	
		int nums=1;
	  //Map<String,Object> requestMap=JsonUtil.jsonToMap(requestData);
		
		String env_batchNo=utilBorrowIdService.findBatchNoByOrderNo(map.get("orderNo").toString());
        List<Object> list=new ArrayList<Object>();
        
    	List<Object> huaoRechargeVoList=new ArrayList<Object>();
        
		Map<String,Object> hrhaRepaymentRegisterBatch=new HashMap<String,Object>(); //回款计划批次表
		hrhaRepaymentRegisterBatch.put("batch_no", env_batchNo);
		hrhaRepaymentRegisterBatch.put("repay_date", map.get("repaymentYestime"));
		hrhaRepaymentRegisterBatch.put("send_date",  new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		//hrhaRepaymentRegisterBatch.put("all_account", ToDouble(map.get("repaymentYesaccount").toString()));
		hrhaRepaymentRegisterBatch.put("all_account", ToDouble(map.get("repaymentYesaccount").toString()));
		hrhaRepaymentRegisterBatch.put("all_nums", nums);
		hrhaRepaymentRegisterBatch.put("confirm_repay_date",  map.get("repaymentYestime"));	
	
		
		
		
		List<Map<String,Object>> hrhalist=new ArrayList<Map<String,Object>>();
		
		
//			JSONArray ary=JSONArray.fromObject(requestMap.get("data"));
//			for (Object object : ary) {		
				//JSONObject json=JSONObject.fromObject(map.get("huaRongDto"));				

				Map<String,Object>	 hrhaRepaymentRegisterDetail=new HashMap<String,Object>();
				hrhaRepaymentRegisterDetail.put("borrowId",Long.parseLong(utilBorrowIdService.findBorrowIdByOrderNo(map.get("orderNo").toString())));
				//hrhaRepaymentRegisterDetail.put("batchNo","KG"+utilBorrowIdService.getBatchNo());//批次号
				hrhaRepaymentRegisterDetail.put("order",map.get("order"));
				hrhaRepaymentRegisterDetail.put("status", map.get("status"));
				//hrhaRepaymentRegisterDetail.put("repaymentTime", map.get("repaymentTime"));
				hrhaRepaymentRegisterDetail.put("repaymentTime",map.get("repaymentTime")!=null?Str_date_To_Long(map.get("repaymentTime").toString()):"");
				//hrhaRepaymentRegisterDetail.put("repaymentYestime", map.get("repaymentYestime"));	
				hrhaRepaymentRegisterDetail.put("repaymentYestime", map.get("repaymentYestime")!=null?Str_date_To_Long(map.get("repaymentYestime").toString()):"");
				hrhaRepaymentRegisterDetail.put("repaymentAccount",ToDouble(map.get("repaymentAccount").toString()));
				hrhaRepaymentRegisterDetail.put("repaymentYesaccount", ToDouble(map.get("repaymentYesaccount").toString()).subtract(ToDouble(map.get("setlLateInterest").toString())));				
				hrhaRepaymentRegisterDetail.put("lateDays", map.get("lateDays"));
				hrhaRepaymentRegisterDetail.put("lateInterest", ToDouble(map.get("lateInterest").toString()));
				hrhaRepaymentRegisterDetail.put("interest", ToDouble(map.get("interest").toString()));				
				hrhaRepaymentRegisterDetail.put("capital", ToDouble(map.get("capital").toString()));	
				hrhaRepaymentRegisterDetail.put("setlCapital", ToDouble(map.get("setlCapital").toString()));
				hrhaRepaymentRegisterDetail.put("setlInterest", ToDouble(map.get("setlInterest").toString()));
				hrhaRepaymentRegisterDetail.put("setlLateInterest",ToDouble(map.get("setlLateInterest").toString()));
				hrhaRepaymentRegisterDetail.put("sysbAmt", ToDouble(map.get("sysbAmt").toString()));				
				hrhaRepaymentRegisterDetail.put("setlSysbAmt", ToDouble(map.get("setlSysbAmt").toString()));			
				hrhaRepaymentRegisterDetail.put("setlFeeAmt", ToDouble(map.get("setlFeeAmt").toString()));				
				hrhaRepaymentRegisterDetail.put("ppErInd", map.get("ppErInd"));				
				hrhaRepaymentRegisterDetail.put("psOdInd", map.get("psOdInd"));	
				hrhaRepaymentRegisterDetail.put("psIntRate", ToDouble(map.get("psIntRate").toString()));	
				hrhaRepaymentRegisterDetail.put("psRemPrcp",ToDouble(map.get("psRemPrcp").toString()));
				hrhaRepaymentRegisterDetail.put("borrowStatus", map.get("borrowStatus"));
				hrhaRepaymentRegisterDetail.put("repaymentStatus", map.get("repayMentStatus"));
				hrhaRepaymentRegisterDetail.put("isLastPeriod", map.get("isLastPeriod"));
				hrhalist.add(hrhaRepaymentRegisterDetail);												
			//}
	

		list.add(hrhaRepaymentRegisterBatch);
		list.add(hrhalist);
		list.add(huaoRechargeVoList);
		log.info("payMentPlan.url请求报文:"+JsonUtils.toJsonString(list));
		
		Map lcMap=lcAppointMapper.getLcAppoint(MapUtils.getString(map, "orderNo"));
			
		String loanCooprCode=MapUtils.getString(lcMap, "loanCooprCode");
		
		ReturnParam  param=SendHrHaEnCrypt.sendHrHaEnCryptMap(loanCooprCode, JsonUtils.toJsonString(list), "payMentPlan.url");
		log.info("payMentPlan.url返回报文:"+param);
		hrhaRepaymentRegisterDetail.put("batch_no", env_batchNo);
		hrhaRepaymentRegisterDetail.put("repay_date", map.get("repaymentYestime"));
		hrhaRepaymentRegisterDetail.put("send_date", map.get("repaymentYesaccount"));
		hrhaRepaymentRegisterDetail.put("confirm_repay_date", map.get("repaymentYestime"));
		hrhaRepaymentRegisterDetail.put("all_nums", nums);
		hrhaRepaymentRegisterDetail.put("orderId", map.get("orderNo"));
		hrhaRepaymentRegisterDetail.put("psIntRate",map.get("psIntRate").toString());
		
		repaymentRegisterMapper.saveRepaymentRegister(hrhaRepaymentRegisterDetail);
		
		
		
		if(param!=null&&param.getRetCode().equals("00000")) {
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(param.getRetInfo());
		}else if(param!=null&&!param.getRetCode().equals("00000")) {
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(param.getRetInfo());
		}else{
			resp.setCode(RespStatusEnum.THIRD_ERROR.getCode());
			resp.setMsg("调用华融回款接口异常");
		}	
		return resp;
	}


	   public static BigDecimal ToDouble(String str) {
		   	 BigDecimal bd = new BigDecimal(str);
		   	 if(str.equals("0")) {
		   		  bd = bd.setScale(0, BigDecimal.ROUND_HALF_UP);
		   	 }else {
		   	  bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
		   	 }
				return bd;
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

	        /* 
	         * 将时间戳转换为时间
	         */
	        public static String stampToDate(String s){
	            String res;
	            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	            long lt = new Long(s);
	            Date date = new Date(lt);
	            res = simpleDateFormat.format(date);
	            return res;
	        }
	
	   public static void main(String[] args) {
		   
		   //System.out.println(Str_date_To_Long("2018-01-05"));
		   
		   //System.out.println(stampToDate("1517155200000"));
	   }
}
