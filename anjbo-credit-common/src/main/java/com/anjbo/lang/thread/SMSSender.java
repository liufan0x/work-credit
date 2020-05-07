/*
 *Project: anjbo-credit-common
 *File: com.anjbo.lang.thread.SMSSender.java  <2018年3月5日>
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.lang.thread;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.anjbo.common.AnjboException;
import com.anjbo.common.Constants;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.utils.ConfigUtil;
import com.anjbo.utils.HttpUtil;
import com.anjbo.utils.JsonUtil;

/**
 * @Author KangLG 
 * @Date 2018年3月5日 上午10:44:17
 * @version 1.0
 */
public class SMSSender implements Callable<RespStatus> {
	private static final Logger log = Logger.getLogger(SMSSender.class);	
	private static final String SMSSOURCE = "【四海恒通】";
	private static final String SMSURL2 = "smsSend2.act";
	
	private String phone;
	private String ip;
	private String smsContent;
	private String smsComeFrom;
	/**
	 * 
	 * @Author KangLG<2018年3月5日>
	 * @param phone 手机号
	 * @param ip 客户端ip地址
	 * @param smsContent 短信内容
	 * @param smsComeFrom 来源标记（自定义）
	 */
	public SMSSender(String phone, String ip, String smsContent, String smsComeFrom){
		this.phone = phone;
		this.ip = ip;
		this.smsContent = smsContent;
		this.smsComeFrom = smsComeFrom;
	}

	@Override
	public RespStatus call() throws Exception {
		RespStatus resp = this.smsSend(phone, ip, smsContent, smsComeFrom);
		log.info(String.format("短信发送回执(%s):%s", 
				phone, 
				resp
			));	
		return resp;
	}	
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("smsContent",URLEncoder.encode(SMSSOURCE + "http://test-wx.yntrust.com/binding?uniqued=345678986432345789864345786435","UTF-8"));
	}
	
	private RespStatus smsSend(String phone, String ip, String smsContent,
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
			params.put("smsComeFrom",smsComeFrom);
			String result = StringUtils.EMPTY;
			String url = ConfigUtil.getStringValue(Constants.LINK_ANJBO_AMS_URL,ConfigUtil.CONFIG_LINK)+SMSURL2+"?"+HttpUtil.getParam4Map2String(params);
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

	/*
	 * getter - setter
	 */
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getSmsContent() {
		return smsContent;
	}
	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}

	public String getSmsComeFrom() {
		return smsComeFrom;
	}
	public void setSmsComeFrom(String smsComeFrom) {
		this.smsComeFrom = smsComeFrom;
	}

}
