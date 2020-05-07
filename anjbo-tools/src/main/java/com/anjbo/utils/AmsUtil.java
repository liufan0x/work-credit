package com.anjbo.utils;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.anjbo.common.Enums;
import com.anjbo.common.MortgageException;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
/**
 * ams系统工具（发送短信及邮件）
 * @author limh limh@zxsf360.com
 * @date 2016-6-2 下午07:18:42
 */
public class AmsUtil {
	private static final Logger log = Logger.getLogger(AmsUtil.class);
	private static final String SMSSOURCE = "【快鸽按揭】";
	//private static final String SMSURL = "smsSend.act";
	private static final String SMSURL2 = "smsSend2.act";
	private static final String EMAILSEND = "emailSend.act";
	/**
	 * 发送短信
	 * @param phone 手机号
	 * @param ip 客户端ip地址
	 * @param smsContent 短信内容
	 * @param smsComeFrom 来源标记（自定义）
	 * @return
	 * @throws MortgageException
	 */
	public static RespStatus smsSend(String phone, String ip, String smsContent,
			String smsComeFrom) throws MortgageException {
		if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(smsContent)) {
			throw new MortgageException(
					RespStatusEnum.PARAMETER_ERROR.getCode(),
					RespStatusEnum.PARAMETER_ERROR.getMsg());
		}
		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put("phone", phone);
			params.put("ip", ip);
			params.put("smsContent",URLEncoder.encode(SMSSOURCE + smsContent,"UTF-8"));
			params.put("smsComeFrom",smsComeFrom);
			String result = StringUtils.EMPTY;
			String url = ConfigUtil.getStringValue(Enums.GLOBAL_CONFIG_KEY.AMS_URL.toString())+SMSURL2+"?"+getHttpUrl(params);
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
			throw new MortgageException(RespStatusEnum.SYSTEM_ERROR.getCode(),
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
	 * @throws MortgageException
	 * RespStatus
	 * @throws
	 */
	public static RespStatus emailSend(String title, String email, String content) throws MortgageException{
		if (StringUtils.isEmpty(title) || StringUtils.isEmpty(email) || StringUtils.isEmpty(content)) {
			throw new MortgageException(
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
			throw new MortgageException(RespStatusEnum.SYSTEM_ERROR.getCode(),
					RespStatusEnum.SYSTEM_ERROR.getMsg());
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
	public static String getHttpUrl(Map<String, String> params){
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
}
