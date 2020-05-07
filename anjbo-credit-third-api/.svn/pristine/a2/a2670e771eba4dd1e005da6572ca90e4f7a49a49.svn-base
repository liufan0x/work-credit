package com.anjbo.service.huarong.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.dao.huarong.LcAppointMapper;
import com.anjbo.dao.huarong.LoanStatusMapper;
import com.anjbo.dao.huarong.UtilBorrowIdMapper;
import com.anjbo.service.huarong.QueryLoanStatusService;
import com.anjbo.utils.huarong.ReturnParam;
import com.anjbo.utils.huarong.SendHrHaEnCrypt;

/**
 * 
* @ClassName: QueryLoanStatusServiceImpl 
* @Description: TODO(查询放款审批状态接口) 
* @author xufx
* @date 2017年8月16日 下午3:12:57 
*
 */
@Service
public class QueryLoanStatusServiceImpl implements QueryLoanStatusService {

	
	@Resource
	private UtilBorrowIdMapper utilBorrowIdMapper;
	@Resource
	private LoanStatusMapper loanStatusMapper;
	@Resource
	private LcAppointMapper lcAppointMapper;
	Logger log = Logger.getLogger(QueryLoanStatusServiceImpl.class);
	/**
	 *    9
	 * @throws Exception 
	 */
	
	@SuppressWarnings("all")
	public RespDataObject<Map> queryLoanStatusSend(Map<String,Object> map) throws Exception {
		
		
		log.info("放款状态查询接口接收参数:"+map);
		
		//Map<String,Object> requestMap=JsonUtil.jsonToMap(requestData);
		
		RespDataObject<Map> resp = new RespDataObject<Map>();
			
		
		        //String batchNo="KG20170889797935";
				String orderNo=map.get("orderNo").toString();
				
		        String batchNo=utilBorrowIdMapper.findBatchNoByOrderNo(orderNo);
		        
		        log.info("queryLoanStatus.url请求批次号:"+batchNo);
		        
		        Map lcMap=lcAppointMapper.getLcAppoint(orderNo);
				
				String loanCooprCode=MapUtils.getString(lcMap, "loanCooprCode");
		        
				ReturnParam  param=SendHrHaEnCrypt.sendHrHaEnCryptMap(loanCooprCode,batchNo, "queryLoanStatus.url");
				log.info("queryLoanStatus.url返回报文:"+param);
				System.out.println("param:"+param);
				
				if(param!=null&&param.getRetCode().equals("00000")) {
					resp.setCode(RespStatusEnum.SUCCESS.getCode());
					resp.setData((Map)param.getRetData());
					//接口返回消息入库
					Map loanMap=(Map)param.getRetData();
					if(loanMap!=null&&!loanMap.isEmpty()) {
						loanMap.put("orderId", orderNo);
						loanStatusMapper.saveLoanStatus(loanMap);
					}
					
					resp.setMsg(param.getRetInfo());
				}else if(param!=null&&!param.getRetCode().equals("00000")) {
					resp.setCode(RespStatusEnum.FAIL.getCode());
					resp.setMsg(param.getRetInfo());
					resp.setData((Map) param.getRetData());
					//接口返回消息入库
					Map loanMap=(Map)param.getRetData();
					if(loanMap!=null&&!loanMap.isEmpty()) {
						loanMap.put("orderId", orderNo);
						loanStatusMapper.saveLoanStatus(loanMap);
					}
					
				}else{
					resp.setCode(RespStatusEnum.THIRD_ERROR.getCode());
					resp.setMsg("调用华融放款接口异常");
				}	
				
			
		return resp;
	}

}
