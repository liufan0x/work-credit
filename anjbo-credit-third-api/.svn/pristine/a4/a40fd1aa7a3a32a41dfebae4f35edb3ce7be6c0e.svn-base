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
import com.anjbo.dao.huarong.BorrowRepaymentMapper;
import com.anjbo.dao.huarong.LcAppointMapper;
import com.anjbo.service.huarong.BorrowRepaymentService;
import com.anjbo.utils.huarong.JsonUtils;
import com.anjbo.utils.huarong.ReturnParam;
import com.anjbo.utils.huarong.SendHrHaEnCrypt;
/**
 * 
* @ClassName: PayMentPlanServiceImpl 
* @Description: TODO(应还款计划发送) 
* @author xufx
* @date 2017年8月16日 下午2:06:33 
*
 */
@Service
public class BorrowRepaymentServiceImpl implements BorrowRepaymentService {
	
	@Resource
	private BorrowRepaymentMapper borrowRepaymentMapper;

	@Resource
	private UtilBorrowIdService utilBorrowIdService;
	@Resource
	private LcAppointMapper lcAppointMapper;
	
	Logger log = Logger.getLogger(BorrowRepaymentServiceImpl.class);
	/**
	 *     7
	 * @throws Exception 
	 */
	@Override
	public RespStatus borrowRepaymentSend(Map<String,Object>  map) throws Exception {
		
		log.info("应还款接口接收参数:"+map);
		
		RespStatus resp = new RespStatus();
	
		int nums=1;
		//double all_account=0;
		//Map<String,Object> requestMap=JsonUtil.jsonToMap(requestData);
		String env_batchNo=utilBorrowIdService.findBatchNoByOrderNo(map.get("orderNo").toString());
		//JSONArray ary=JSONArray.fromObject(requestMap.get("data"));
		
		List<Map<String,Object>> ListCfBorrowRepayment=new ArrayList<Map<String,Object>>();		
//				for (Object object : ary) {
//					JSONObject json=JSONObject.fromObject(object);				
//					nums+=1;
					//all_account=Double.valueOf(map.get("repaymentAccount").toString());
					Map<String,Object>	 borrowRepayment=new HashMap<String,Object>();
					borrowRepayment.put("borrowId",Long.parseLong(utilBorrowIdService.findBorrowIdByOrderNo(map.get("orderNo").toString())));
					//borrowRepayment.put("batchNo",utilBorrowIdService.findBatchNoByOrderNo(requestMap.get("orderNo").toString()));//批次号
					borrowRepayment.put("order",map.get("order"));
					borrowRepayment.put("repaymentTime", map.get("repaymentTime")!=null?Str_date_To_Long(map.get("repaymentTime").toString()):"");//时间转义数据
					borrowRepayment.put("repaymentAccount", ToDouble(map.get("repaymentAccount").toString()));
					borrowRepayment.put("interest", ToDouble(map.get("interest").toString()));
					borrowRepayment.put("capital", ToDouble(map.get("capital").toString()));
					borrowRepayment.put("sysbAmt", ToDouble(map.get("sysbAmt").toString()));
					borrowRepayment.put("psFeeAmt", ToDouble(map.get("psFeeAmt").toString()));
					borrowRepayment.put("psRemPrcp", map.get("psRemPrcp"));
					borrowRepayment.put("psIntRate", map.get("psIntRate"));
							
					ListCfBorrowRepayment.add(borrowRepayment);												
			//	}
			
		
		Map<String,Object> hrhaBorrowBatchMap=new HashMap<String,Object>();
		
		hrhaBorrowBatchMap.put("batch_no", env_batchNo);
		hrhaBorrowBatchMap.put("send_date",new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		hrhaBorrowBatchMap.put("all_account", ToDouble(map.get("repaymentAccount").toString()));
		hrhaBorrowBatchMap.put("all_nums", nums);	
		
		List<Object> list=new ArrayList<Object>();
				
		list.add(hrhaBorrowBatchMap);
		list.add(ListCfBorrowRepayment);
		
		//String loanCooprCode="KG";
		
		Map lcMap=lcAppointMapper.getLcAppoint(MapUtils.getString(map, "orderNo"));
		
		String loanCooprCode=MapUtils.getString(lcMap, "loanCooprCode");
		
		System.out.println("报文:"+JsonUtils.toJsonString(list));
		
		log.info("repayMent.url请求报文:"+JsonUtils.toJsonString(list));
        
		ReturnParam  param=SendHrHaEnCrypt.sendHrHaEnCryptMap(loanCooprCode, JsonUtils.toJsonString(list), "repayMent.url");
		
		log.info("repayMent.url返回报文:"+param);
    // for (Map<String, Object> map : ListCfBorrowRepayment) {
		borrowRepayment.put("orderId",  map.get("orderNo"));
		borrowRepayment.put("batch_no", env_batchNo);
		borrowRepayment.put("send_date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		borrowRepayment.put("all_account", ToDouble(map.get("repaymentAccount").toString()));
		borrowRepayment.put("all_nums", nums);
	// }
		
		//重置转义字段为快鸽原始字段入库
		borrowRepayment.put("repaymentTime", map.get("repaymentTime"));//时间转义数据
		
     borrowRepaymentMapper.saveBorrowRepaymentMapper(borrowRepayment);
		
		System.out.println("param"+param);
		
		if(param!=null&&param.getRetCode().equals("00000")) {
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(param.getRetInfo());
		}else if(param!=null&&!param.getRetCode().equals("00000")) {
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(param.getRetInfo());
		}else{
			resp.setCode(RespStatusEnum.THIRD_ERROR.getCode());
			resp.setMsg("调用华融应还款接口异常");
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
	        
	        public static BigDecimal ToDouble(String str) {
			   	 BigDecimal bd = new BigDecimal(str);
			   	 if(str.equals("0")) {
			   		  bd = bd.setScale(0, BigDecimal.ROUND_HALF_UP);
			   	 }else {
			   	  bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
			   	 }
					return bd;
			}

}
