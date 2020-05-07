package com.anjbo.controller.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.anjbo.bean.FundDto;
import com.anjbo.common.RespPageData;
import com.anjbo.controller.IFinancialController;
import com.anjbo.controller.api.UserApi;
import com.anjbo.service.FinancialService;


@RestController
public class FinancialController implements IFinancialController{
	
    @Resource  FinancialService  financialService;
    @Resource  UserApi userApi;
	
	@Override
	public RespPageData<Map<String, Object>> query(@RequestBody Map<String, Object> paramMap) {
		 RespPageData<Map<String,Object>> result = new RespPageData<Map<String,Object>>();
		
		
		 int count = financialService.financialCount(paramMap);    //总条数
		 List<Map<String, Object>> allFinancial = financialService.financialList(paramMap);
		
		 for (int i = 0; i < allFinancial.size(); i++) {
			 Map<String, Object> list = allFinancial.get(i);
			   if (list.containsKey("fundId")) {
				    int fundId = MapUtils.getIntValue(list,"fundId");
				    FundDto fund = new FundDto();
				    fund.setId(fundId);
				    fund = userApi.findByFundId(fund);
				    list.put("fundName",fund.getFundName());
			   }else {
				   list.put("fundName","-");
			   }
			   if (list.containsKey("lendingTime")) {
				   String lendingTime = MapUtils.getString(list,"lendingTime");
				   list.put("lendingTime",lendingTime.substring(0,10));
			   }else {
				   list.put("lendingTime","-");
			   }
			   if (list.containsKey("planPaymentTime")) {
				   list.put("planPaymentTime",MapUtils.getString(list,"planPaymentTime").substring(0,10));
			   }else {
				   list.put("planPaymentTime","-");
		     	}
			   if (list.containsKey("payMentAmountDate")) {
				   list.put("payMentAmountDate",MapUtils.getString(list,"payMentAmountDate").substring(0,10));
			   }else {
				   list.put("payMentAmountDate","-");
			   }
			   if (list.containsKey("interestTime")) {
				   list.put("interestTime",MapUtils.getString(list,"interestTime").substring(0,10));
			   }else {
				   list.put("interestTime","-");
			}
			   if (list.containsKey("rebateTime")) {
				   list.put("rebateTime",MapUtils.getString(list,"rebateTime").substring(0,10));
			   }else {
				   list.put("rebateTime","-");
			}if (list.containsKey("payTime")) {
				   list.put("payTime",MapUtils.getString(list,"payTime").substring(0,10));
			   }else {
				   list.put("payTime","-");
			}
			if (list.containsKey("datediff")) {
				  String datediff = MapUtils.getString(list,"datediff");
				  datediff = datediff.replace("-","");
				  list.put("datediff",datediff);
			   }
		}
		 result.setTotal(count);
		 result.setRows(allFinancial);
		
		 return result;
	}

   
}
