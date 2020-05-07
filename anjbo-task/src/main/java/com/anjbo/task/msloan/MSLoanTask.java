package com.anjbo.task.msloan;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.anjbo.bean.msloan.MSCustomerDto;
import com.anjbo.bean.msloan.MSRespDataObject;
import com.anjbo.common.Constants;
import com.anjbo.common.Enums;
import com.anjbo.common.HttpUtil;
import com.anjbo.service.msloan.MSLoanService;
import com.anjbo.utils.AmsUtil;
import com.anjbo.utils.ConfigUtil;
import com.anjbo.utils.StringUtil;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * 
* @ClassName: MSLoanTask 
* @Description: TODO(民生银行定时查询审批结果) 
* @author dandy Dandy151@FoxMail.com
* @date 2017-5-4 下午02:07:03 
*
 */
@Component
public class MSLoanTask {
	private static Log log=LogFactory.getLog(MSLoanTask.class);
	@Resource
	private MSLoanService msLoanService;
	
	private static HttpUtil httpUtil = new HttpUtil();
	private static Gson gson = new Gson();
	private static final String MSUrl =ConfigUtil.getStringValue("MSUrl");
	private static final String MSTokenUrl = ConfigUtil.getStringValue("MSTokenUrl");
	private static final String APPID =ConfigUtil.getStringValue("MSAPPID");
	private static final String SECRET = ConfigUtil.getStringValue("MSSECRET");
	private static final Map<String,String> mode=new HashMap<String, String>();
	private static final Map<String,String> STATES=new HashMap<String, String>();
	
	static{
		mode.put("1", "个人消费贷款");
		mode.put("2", "企业抵押贷款");
		//默认 
		STATES.put("0", "办结完成");
		STATES.put("1", "客户申请");
		STATES.put("2", "初审通过");
		STATES.put("3", "初审失败");
		STATES.put("4", "终审通过");
		STATES.put("5", "终审失败");
		STATES.put("6", "现场调查");
		STATES.put("7", "现场调查完成");
		STATES.put("8", "客户资料通过");
		STATES.put("9", "客户资料不通过");
		STATES.put("10", "补录资料");
		
	}
	/**
	 * 
	* @Title: MSLoanJob 
	* @Description: TODO(民生贷款申请定时查询审核结果。
	* 短信文案：您在快鸽按揭申请的个人消费贷款/企业抵押贷款（xxx物业xxx楼栋xxx房号）最终审批的贷款金额为xxxxx元，打开快鸽按揭APP查看订单进度【快鸽按揭】。) 
	* @param @throws Exception    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void MSLoanJob(){
		log.info("开始执行调用民生银行审核可贷金额查询接口任务");
		List<MSCustomerDto> msCustomerDtos = msLoanService.selectMSDetailByQuota();
		try {
			if(msCustomerDtos.size()>0){
				String token = getToken();
				if(!StringUtils.isNotBlank(token)){
					//token为空
					log.info("获取token失败！执行结束！");
				}else{
					Map<String,String> param=new HashMap<String, String>();
					Map<String,String> toGson=new HashMap<String, String>();
					String result=StringUtils.EMPTY;
					toGson.put("token", token);
					for (MSCustomerDto msCustomerDto : msCustomerDtos) {
						toGson.put("workorderId", msCustomerDto.getWorkorderId());
						log.info("查询WorkorderId为:"+msCustomerDto.getWorkorderId());
						param.put("json", gson.toJson(toGson));
						result = httpUtil.post(MSUrl+"?searchResult&", param);
						MSRespDataObject fromJson = gson.fromJson(result, MSRespDataObject.class);
						if(fromJson!=null&&StringUtils.isNotBlank(fromJson.getResultDesc())&&fromJson.getResultDesc().indexOf("失效")>=0){
							//token失效
							toGson.put("token", getToken());
							result = httpUtil.post(MSUrl+"?searchResult&", param);
							fromJson = gson.fromJson(result, MSRespDataObject.class);
						}
						log.info("接口状态：Code="+fromJson.getResultCode()+" Msg="+fromJson.getResultDesc());
						log.info("返回结果: "+fromJson.getResultData());
						Object resultData=fromJson.getResultData();
						if(resultData!=null && resultData instanceof Map){
							Map resultMap=(Map)resultData;
							Double evaluation = MapUtils.getDouble(resultMap, "evaluation");//金额
							String state = MapUtils.getString(resultMap, "state");//状态
							if(StringUtils.isNotBlank(state)&&state.equals("1")){
								continue;
							}
							String opinion = MapUtils.getString(resultMap, "opinion");//审批意见
							String state2=msCustomerDto.getState();//旧状态
							msCustomerDto.setLoanAmount(evaluation);
							msCustomerDto.setState(state);
							msCustomerDto.setOpinion(opinion);
								if(evaluation.compareTo(0.0d)>0&&(state.equals("4")||state.equals("2"))){//初审通过跟终审通过
									if(!StringUtils.isNotBlank(msCustomerDto.getSmsState())||!msCustomerDto.getSmsState().equals(state)){
										String modeStr =MapUtils.getString(mode, msCustomerDto.getMode(), "个人消费贷款");
										//发送短信通知
										String smsContent = new StringBuilder()
										.append("您好，您在快鸽按揭申请的 "+modeStr)
										.append("（"+msCustomerDto.getPropertyName()+"）")
										.append(STATES.get(state)+"的贷款金额为"+msCustomerDto.getLoanAmount()+"万元")
										.append("，打开快鸽按揭APP查看申请进度.")
										.toString();
										String ipWhite = ConfigUtil.getStringValue(Enums.GLOBAL_CONFIG_KEY.AMS_SMS_IPWHITE.toString());
										try {
											log.info("发送短信到客户手机:"+msCustomerDto.getPhone());
											AmsUtil.smsSend(msCustomerDto.getPhone(), ipWhite, smsContent, Constants.SMSCOMEFROM);
											msCustomerDto.setSmsState(state);
											//记录处理过的客户
											msLoanService.updateMSSmsStatebyId(msCustomerDto);
										} catch (Exception e) {
											log.error("发送短信到客户手机失败!"+smsContent);
										}
									}
								}
								if(StringUtils.isNotBlank(state)&&!state.equals("1")&&!state.equals(state2)){
									//发送email通知相关客服人员
									try {
										msLoanService.updateQuotaById(msCustomerDto);
										log.info("发送email通知相关客服人员");
										sendEmail(msCustomerDto,opinion);
									} catch (Exception e) {
										log.error("发送email通知相关客服人员失败!");
									}
								}
						}
					}
				}
				
			}
		} catch (Exception e) {
			log.info(e.getMessage());
		}
	}
	/**
	 * 
	* @Title: sendEmail 《民生银行定时查询贷款用户审批》初审通过:用户：xxxx,手机号：xxxxx，在快鸽按揭申请的个人消费贷款/企业抵押贷款（xxx物业xxx楼栋xxx房号），金额：xxx，审批意见：xxxx
	* @Description: TODO(发送email通知相关客服人员) 
	* @param @param msCustomerDto  基础资料
	* @return void    返回类型 
	* @throws
	 */
	private void sendEmail(MSCustomerDto msCustomerDto,String opinion)throws Exception{
		StringBuilder content=new StringBuilder();
		content.append("《民生银行定时查询贷款用户审批》"+STATES.get(msCustomerDto.getState())+":").append("用户："+msCustomerDto.getName()+",")
		.append("手机号:"+msCustomerDto.getPhone()+",")
		.append("在快鸽按揭申请的"+MapUtils.getString(mode, msCustomerDto.getMode(), "个人消费贷款"))
		.append("（"+msCustomerDto.getPropertyName()+"）")
		.append(",金额:"+msCustomerDto.getLoanAmount()+",")
		.append("审批意见:"+opinion);
		String email=ConfigUtil.getStringValue("MSLOAN_EMAIL");
		//String[] email={"zhangl@anjbo.com","yangj@anjbo.com","1034006550@qq.com"};
		AmsUtil.emailSend("民生银行定时查询贷款用户审批", email.split(","), content.toString());
	}
	public static void main(String[] args) {
		String str="测试12312";
		System.out.println(str.indexOf("测试"));
	}
	private  String getToken(){
		String result = null;
		try {
			 result = httpUtil.post(MSTokenUrl + "?getToken&appid=" + APPID
					+ "&secret=" + SECRET, null);
			if(!StringUtils.isNotBlank(result))return null;
			MSRespDataObject object = gson.fromJson(result, MSRespDataObject.class);
			return object.getAccess_token();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
