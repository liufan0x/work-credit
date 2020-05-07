package com.anjbo.task.fc.risk;

import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.anjbo.bean.fc.fund.ReturnParam;
import com.anjbo.common.Constants;
import com.anjbo.common.Enums;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatus;
import com.anjbo.utils.AmsUtil;
import com.anjbo.utils.ConfigUtil;
import com.anjbo.utils.HttpUtil;
@Component
public class FundAuditTask {  
	Logger Log=Logger.getLogger(FundAuditTask.class);
	/** 创建人uid */
	private String createUid;
	/**任务描述*/
    private String msg; 
    private String orderNo;
    /**手机号*/
    private String phone;
   
    //定时查询放款审批
    public void run() {  
		try {
			if(orderNo==null){
	    		Log.error("订单编号为null");
	    	}else{
	    		Log.info("订单编号为："+orderNo);
	    		//先查询放款审批
				Map<String,Object> params = new HashMap<String, Object>();
				params.put("orderNo", orderNo);//2017071811111900000
				String toolsUrl = ConfigUtil.getStringValue(Enums.GLOBAL_CONFIG_KEY.CREDIT_URL.toString());
				RespDataObject<Map<String, Object>> resp=HttpUtil.getRespDataObject(toolsUrl, "/credit/third/api/hr/queryLoanStatus", params, Map.class);
				Map<String, Object> loanStatus =resp.getData();
				Log.info("融安放款状态返回信息:"+loanStatus +"----"+resp.getCode());
				if("SUCCESS".equals(resp.getCode())){
					String dealStatus =loanStatus.get("dealStatus")+"";
					String grantStatus=loanStatus.get("grantStatus")+"";
					if("997".equals(grantStatus) || "998".equals(grantStatus) ||"998".equals(dealStatus)||"997".equals(dealStatus) ||"992".equals(dealStatus)){  
					  params.put("dealStatus", dealStatus);
					  params.put("grantStatus", grantStatus);
					  params.put("lendingTime", loanStatus.get("realLoanTime"));  //放款时间
					  params.put("dealOpinion", loanStatus.get("dealOpinion"));  //审批打回原因
					  //修改资金放款审批状态
					  String toolsUrl1 = ConfigUtil.getStringValue(Enums.GLOBAL_CONFIG_KEY.CREDIT_URL.toString());
					  RespStatus respStatus = HttpUtil.getRespStatus(toolsUrl1, "/credit/risk/allocationfundaduit/updataStatus", params);
					  Log.info("更新资金方返回学校"+respStatus);
					  if("SUCCESS".equals(respStatus.getCode())){
						  //判断审批状态
						  if("997".equals(grantStatus) || "998".equals(grantStatus) ||"998".equals(dealStatus) ||"992".equals(dealStatus)){
						    //查询订单客户姓名及借款金额	
							RespDataObject<Map<String, Object>> respDate=HttpUtil.getRespDataObject(toolsUrl1, "/credit/risk/allocationfundaduit/selectOrder", params, Map.class);
							Map<String, Object> fundMap=respDate.getData();
							if(fundMap!=null){
							    String content="";
								if("997".equals(grantStatus)){
				 					  content = "债务置换订单（"+fundMap.get("borrowerName")+"，"+fundMap.get("loanAmount")+"万）已通过资方放款审批，请登录信贷系统发送应还款计";	
								}else{
									  if("992".equals(dealStatus)){
										  content = "债务置换订单（"+fundMap.get("borrowerName")+"，"+fundMap.get("loanAmount")+"万）已被资方打回，请保持跟进";
									  }else{
										  content = "债务置换订单（"+fundMap.get("borrowerName")+"，"+fundMap.get("loanAmount")+"万）已被资方否决，请保持跟进";
									  }
								}
								String ipWhite = ConfigUtil.getStringValue(Enums.GLOBAL_CONFIG_KEY.AMS_SMS_IPWHITE.toString());
								String phone=fundMap.get("phone").toString();
								AmsUtil.smsSend(phone, ipWhite,content , Constants.SMSCOMEFROM);
							}
					  }
					}
				  }
				}
	    	}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public FundAuditTask() {
		super();
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPhone() {
		return phone;
	}
	public String getCreateUid() {
		return createUid;
	}
	public void setCreateUid(String createUid) {
		this.createUid = createUid;
	}
	public FundAuditTask(String createUid,String msg,String phone, String orderNo) {
		super();
		this.createUid = createUid;
		this.msg = msg;
		this.phone = phone;
		this.orderNo = orderNo;
	}
	
}  
