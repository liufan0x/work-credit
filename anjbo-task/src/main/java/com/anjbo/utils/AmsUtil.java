package com.anjbo.utils;

import com.anjbo.common.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * ams系统工具（发送短信及邮件）
 * @author limh limh@zxsf360.com
 * @date 2016-6-2 下午07:18:42
 */
public class AmsUtil implements Callable<RespStatus>{
	private static final Logger log = Logger.getLogger(AmsUtil.class);
	public static final Map<String, String> smsMap = new HashMap<String, String>();

	static {
		Properties p = new Properties();
		try {
			p.load(new InputStreamReader(ConfigUtil.class.getClassLoader().getResourceAsStream(
					"sms.properties"),"utf-8"));

			for (Entry<Object, Object> tmp : p.entrySet()) {
				smsMap.put((String) tmp.getKey(), (String) tmp.getValue());
			}
		} catch (IOException e) {
			log.error("加载配置文件:sms.properties出错!", e);
		}
	}
	private static final String SMSSOURCE = "【快鸽按揭】";
	private static final String SMSURL2 = "smsSend2.act";
	private static final String SMSURL4 = "smsSend4.act";
	private static final String EMAILSEND = "emailSend.act";
	private String phone;
	private String ip;
	private String smsContent;
	private String smsComeFrom;
	private String amsCode;
	public static void main(String[] args) throws AnjboException {
		//smsSendNoLimit("13066999269", Constants.SMSCOMEFROM_TEST,"Mark");
	}
	/**
	 *
	 * @param phone 手机号
	 * @param ip 客户端ip地址
	 * @param smsContent 短信内容
	 * @param smsComeFrom 来源标记（自定义）
	 */
	public AmsUtil(String phone, String ip, String smsContent, String smsComeFrom,String amsCode){
		this.phone = phone;
		this.ip = ip;
		this.smsContent = smsContent;
		this.smsComeFrom = smsComeFrom;
		this.amsCode = amsCode;
	}
	/**
	 * 发送短信
	 * @param phone 手机号
	 * @param ip 客户端ip地址
	 * @param smsContent 短信内容
	 * @param smsComeFrom 来源标记（自定义）
	 * @return
	 * @throws AnjboException
	 */
	public static RespStatus smsSend(String phone, String ip, String smsContent,
			String smsComeFrom) throws AnjboException {
		if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(smsContent)) {
			throw new AnjboException(
					RespStatusEnum.PARAMETER_ERROR.getCode(),
					RespStatusEnum.PARAMETER_ERROR.getMsg());
		}
		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put("phone", phone);
			params.put("ip", ip);
			params.put("smsContent",URLEncoder.encode(SMSSOURCE + smsContent,"UTF-8"));
			params.put("smsComeFrom","ams-"+smsComeFrom);
			params.put("amsCode","hyt");
			String result = StringUtils.EMPTY;
			String url = ConfigUtil.getStringValue(Enums.GLOBAL_CONFIG_KEY.AMS_URL.toString())+SMSURL4+"?"+getHttpUrl(params);
			result = HttpUtil.get(url);
			log.info("result=="+result);
			if (result == null) {
				return new RespStatus(RespStatusEnum.REQUEST_TIMEOUT.getCode(),
						RespStatusEnum.REQUEST_TIMEOUT.getMsg());
			}
			Map<String, String> map = JsonUtil.parseAMSResult(result);
			int code = Integer.valueOf(map.get("code"));
			if (code == 0) {
				return new RespStatus(RespStatusEnum.SUCCESS.getCode(),
						RespStatusEnum.SUCCESS.getMsg());
			} else if (code == -9) {
				return new RespStatus(RespStatusEnum.SMS_DAY_THREE.getCode(),
						RespStatusEnum.SMS_DAY_THREE.getMsg());
			} else if (code == -30) {
				return new RespStatus(RespStatusEnum.SMS_MONTH_FIVE.getCode(),
						RespStatusEnum.SMS_MONTH_FIVE.getMsg());
			} else {
				return new RespStatus(RespStatusEnum.FAIL.getCode(),
						RespStatusEnum.FAIL.getMsg());
			}
		} catch (Exception e) {
			log.error("smsSend Exception.", e);
			throw new AnjboException(RespStatusEnum.SYSTEM_ERROR.getCode(),
					RespStatusEnum.SYSTEM_ERROR.getMsg());
		}
	}
	/**
	 * 发送邮件
	 * @Title: emailSend 
	 * @param title 邮件标题
	 * @param email 接收邮件者
	 * @param content 邮件内容
	 * @return
	 * @throws AnjboException
	 * RespStatus
	 * @throws
	 */
	public static RespStatus emailSend(String title, String email, String content) throws AnjboException{
		if (StringUtils.isEmpty(title) || StringUtils.isEmpty(email) || StringUtils.isEmpty(content)) {
			throw new AnjboException(
					RespStatusEnum.PARAMETER_ERROR.getCode(),
					RespStatusEnum.PARAMETER_ERROR.getMsg());
		}
		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put("uid", URLEncoder.encode(title, "utf-8"));
			params.put("email", email);
			params.put("m", URLEncoder.encode(content, "utf-8"));
			params.put("flag", "");
			String result = StringUtils.EMPTY;
			String url = ConfigUtil.getStringValue(Enums.GLOBAL_CONFIG_KEY.AMS_URL.toString())+EMAILSEND+"?"+getHttpUrl(params);
			result = HttpUtil.get(url);
			if (result == null) {
				return new RespStatus(RespStatusEnum.REQUEST_TIMEOUT.getCode(),
						RespStatusEnum.REQUEST_TIMEOUT.getMsg());
			}
			Map<String, String> map = JsonUtil.parseAMSResult(result);
			int code = Integer.valueOf(map.get("code"));
			if (code == 0) {
				return new RespStatus(RespStatusEnum.SUCCESS.getCode(),
						RespStatusEnum.SUCCESS.getMsg());
			} else {
				return new RespStatus(RespStatusEnum.FAIL.getCode(),
						RespStatusEnum.FAIL.getMsg());
			}
		} catch (Exception e) {
			log.error("smsSend Exception.", e);
			throw new AnjboException(RespStatusEnum.SYSTEM_ERROR.getCode(),
					RespStatusEnum.SYSTEM_ERROR.getMsg());
		}
	}
	public static void emailSend(String title, String[] email, String content) throws AnjboException{
		for (int i = 0; i < email.length; i++) {
			emailSend(title, email[i], content);
		}
	}
	/**
	 * 通过map参数集，获取拼接url<br />
	 * 如 a=111,b=222,返回   a=111&b=222
	 * @author Jerry
	 * @version v1.0 2013-10-15 上午08:53:19 
	 * @param params map参数集
	 * @return 拼接后的连接
	 */
	private static String getHttpUrl(Map<String, String> params){
		StringBuilder builder = new StringBuilder();
		if (params == null) {
			return null;
		}
		int i = 1;
		int size = params.entrySet().size();
		for (Entry<String, String> entry : params.entrySet()) {
			builder.append(entry.getKey());
			builder.append("=");
			builder.append(StringUtil.trimToEmpty(entry.getValue()));
			if (i!=size){
				builder.append("&");
			} 
			i++;
		}
		return builder.toString();
	}
	/**
	 * 发送短信
	 * @param phone 手机号
	 * @param ip 客户端ip，用IpUtil类获取
	 * @param constantsKey Constants配置的发送短信来源
	 * @param param 如果短信内容有%s占位符需要传入相应的参数替换
	 */
	public static void smsSend(String phone,String ip,String constantsKey,Object ...param){
		String smsContent = String.format(smsMap.get(constantsKey),param);
		log.info(String.format("发送短信手机号：%s；发送内容：%s",phone,smsContent));
		if(StringUtils.isEmpty(smsContent)){return;}
		String smsComeFrom = Constants.SMSCOMEFROM+constantsKey;
		try {
			smsSend(phone,ip,smsContent,smsComeFrom);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 发送短信（无限制，用于内部发送）
	 * @param phone 手机号
	 * @param constantsKey Constants配置的发送短信来源
	 * @param param 如果短信内容有%s占位符需要传入相应的参数替换
	 */
	public static void smsSendNoLimit(String phone,String constantsKey,Object ...param){
		String smsIpWhite = ConfigUtil.getStringValue(Enums.GLOBAL_CONFIG_KEY.AMS_SMS_IPWHITE.toString());
		smsSend(phone, smsIpWhite, constantsKey, param);
	}

	/**
	 * 行业通/众视达
	 * 发送短信
	 * @param phone 手机号
	 * @param ip 客户端ip，用IpUtil类获取
	 * @param  amsCode:hyt(行业通)/zsd（众视达）
	 */
	public static void smsSend2(String phone,String ip,String amsCode,String smsContent,String smsComeFrom){
		log.info(String.format("发送短信手机号：%s；发送内容：%s",phone,smsContent));
		if(StringUtils.isEmpty(smsContent)){return;}
		try {
			Callable<RespStatus> callSMS = new AmsUtil(phone,ip,smsContent,smsComeFrom,amsCode);
			FutureTask<RespStatus> futuretask = new FutureTask<RespStatus>(callSMS);
			new Thread(futuretask, "Thread-SMS-Sender").start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * Computes a result, or throws an exception if unable to do so.
	 *
	 * @return computed result
	 * @throws Exception if unable to compute a result
	 */
	@Override
	public RespStatus call() throws Exception {
		RespStatus resp = this.smsSend(phone, ip, smsContent, smsComeFrom,amsCode);
		log.info(String.format("短信发送回执(%s):%s",phone,resp));
		return resp;
	}

	private RespStatus smsSend(String phone, String ip, String smsContent,
							   String smsComeFrom,String amsCode) throws AnjboException {
		if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(smsContent)) {
			throw new AnjboException(
					RespStatusEnum.PARAMETER_ERROR.getCode(),
					RespStatusEnum.PARAMETER_ERROR.getMsg());
		}
		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put("phone", phone);
			params.put("ip", ip);
			params.put("smsContent",URLEncoder.encode(SMSSOURCE + smsContent,"UTF-8"));
			params.put("smsComeFrom","ams-"+smsComeFrom);
			params.put("amsCode", amsCode);
			String result = StringUtils.EMPTY;
			String url = ConfigUtil.getStringValue(Enums.GLOBAL_CONFIG_KEY.AMS_URL.toString())+SMSURL4+"?"+getHttpUrl(params);
			result = HttpUtil.get(url);
			log.info("result=="+result);
			if (result == null) {
				return new RespStatus(RespStatusEnum.REQUEST_TIMEOUT.getCode(),
						RespStatusEnum.REQUEST_TIMEOUT.getMsg());
			}
			Map<String, String> map = JsonUtil.parseAMSResult(result);
			int code = Integer.valueOf(map.get("code"));
			if (code == 0) {
				return new RespStatus(RespStatusEnum.SUCCESS.getCode(),
						RespStatusEnum.SUCCESS.getMsg());
			} else if (code == -9) {
				return new RespStatus(RespStatusEnum.SMS_DAY_THREE.getCode(),
						RespStatusEnum.SMS_DAY_THREE.getMsg());
			} else if (code == -30) {
				return new RespStatus(RespStatusEnum.SMS_MONTH_FIVE.getCode(),
						RespStatusEnum.SMS_MONTH_FIVE.getMsg());
			} else {
				return new RespStatus(RespStatusEnum.FAIL.getCode(),
						RespStatusEnum.FAIL.getMsg());
			}
		} catch (Exception e) {
			log.error("smsSend Exception.", e);
			throw new AnjboException(RespStatusEnum.SYSTEM_ERROR.getCode(),
					RespStatusEnum.SYSTEM_ERROR.getMsg());
		}
	}
}
