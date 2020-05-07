package com.anjbo.sms;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.anjbo.common.AnjboException;
import com.anjbo.common.DateUtil;
import com.anjbo.common.Enums;
import com.anjbo.common.RedisKey;
import com.anjbo.common.RedisOperator;
import com.anjbo.dao.system.SMSMapper;
import com.anjbo.sms.hander.HYTHandler;
import com.anjbo.sms.hander.SMSZuulServer;
import com.anjbo.sms.hander.ZSDHandler2;
import com.anjbo.sms.soap.UegateSoap;
import com.anjbo.utils.ConfigUtil;
import com.anjbo.utils.EmailUtil;
import com.anjbo.utils.StringUtil;

/**
 * 短信发信接口
 * 
 * @author Administrator
 * 
 */
@Component
public class SMSService {

	private Logger log = Logger.getLogger(getClass());
	@Autowired
	private SMSMapper smsMapper;

	/**
	 * 发送短信检测
	 * 
	 * @param mobile
	 * @param ip
	 * @param smsContent
	 * @param smsComeFrom
	 * @return
	 */
	public int smsSendCheck(String mobile, String ip, String svrIp,
			String smsContent, String smsComeFrom, Date today) {
		if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(ip)
				|| StringUtils.isEmpty(smsContent)
				|| StringUtils.isEmpty(smsComeFrom)) {
			return -2;
		}
		String todayDate = DateUtil.getDateByFmt(today, DateUtil.FMT_TYPE2);
		String todayYearMonth = DateUtil
				.getDateByFmt(today, DateUtil.FMT_TYPE14);
		// ip黑名单
		String ipBlackList = ConfigUtil.getStringValue("ipblacklist");
		if (StringUtils.isNotEmpty(ipBlackList)
				&& ipBlackList.indexOf(ip) != -1) {
			log.info("(-31)ip黑名单:"+ip);
			return -31;
		}
		// ip白名单
		String ipWhiteList = ConfigUtil.getStringValue("ipWhiteList");
		if (StringUtils.isNotEmpty(ipWhiteList) && ip.equals(svrIp)
				&& ipWhiteList.indexOf(ip) != -1) {
			return 0;
		}
		// (这里应该有一个频率限制机制，比如单个ip一分钟发送超过多少次则返回错误码),下面的ip限制就不用做了.
		int ipMinuteSendCount = NumberUtils.toInt(RedisOperator
				.getString(RedisKey.PREFIX.SMS_IP_LIMIT + ip));
		if (ipMinuteSendCount >= 3) {
			log.info("(-32)ip黑名单:"+ip);
			return -32;
		}
		/**
		 * 判断当天
		 */
		// 一天最多发送的次数
		int maxDayCount = ConfigUtil.getIntegerValue(Enums.CONFIG_KEY.DAYCOUNT
				.toString());
		// 一个手机号码当天发送的次数
		int count = smsMapper.selectSMS(todayDate, mobile, smsComeFrom);
		// 正常流程下，一天发送超过三次短信，返回提示
		if (count >= maxDayCount) {
			log.info("(-9)ip黑名单:"+ip);
			return -9;
		}
		//同一ip一天最多发送的次数
		int maxIpDayCount = ConfigUtil.getIntegerValue(Enums.CONFIG_KEY.IPDAYCOUNT.toString(), 30);
		int ipCount = smsMapper.selectSMSByIp(todayDate, ip, smsComeFrom);
		// 同一个ip当天发送超过10次
		if (ipCount >= maxIpDayCount) {
			log.info("(-33)ip黑名单:"+ip);
			return -33;
		}
		/**
		 * 判断当月
		 */
		// 一个月最多发送的次数
		int maxMonthCount = ConfigUtil
				.getIntegerValue(Enums.CONFIG_KEY.MONTHCOUNT.toString());
		// 一个手机号码当月发送的次数
		final int monthCount = smsMapper
				.selectSMSMounth(todayYearMonth, mobile, smsComeFrom);
		if (monthCount >= maxMonthCount) {
			log.info("(-30)ip黑名单:"+ip);
			return -30;
		}
		//同一ip当月最多发送的次数
		int maxIpMonthCount = ConfigUtil.getIntegerValue(Enums.CONFIG_KEY.IPMONTHCOUNT.toString(), 200);
		int ipMonthCount = smsMapper.selectSMSMounthByIp(todayYearMonth, ip, smsComeFrom);
		// 同一个ip当月发送超过200次
		if (ipMonthCount >= maxIpMonthCount) {
			log.info("(-34)ip黑名单:"+ip);
			return -34;
		}
		// 检测成功
		return 0;
	}

	/**
	 * 发送短信统一入口:加入ip黑名单，ip频率限制，ip发送日月限制和mobile日月限制
	 * 
	 * @param mobile
	 * @param ip
	 * @param smsContent
	 * @param smsComeFrom
	 * @return
	 */
	public int smsSendChannel1(String mobile, String ip, String svrIp,
			String smsContent, String smsComeFrom) {
		log.info("smsSend params|mobile=" + mobile + "|ip=" + ip + "|svrIp="
				+ svrIp + "|smsContent=" + smsContent + "|smsComeFrom="
				+ smsComeFrom + "|channel=1");
		final Date today = new Date();
		int checkResult = smsSendCheck(mobile, ip, svrIp, smsContent,
				smsComeFrom, today);
		log.info("smsSendChannel1 smsSendCheck checkResult="+checkResult);
		// 检查失败
		if (checkResult < 0) {
			return checkResult;
		}
		/**新版短信发送start**/
		String spID = ConfigUtil.getStringValue("soap_xiaoniu365_spid");
		String password = ConfigUtil.getStringValue("soap_xiaoniu365_password");
		String accessCode = ConfigUtil.getStringValue("soap_xiaoniu365_accesscode");
		int result = sendSms(spID,password, accessCode, 
				smsContent, mobile,ip,smsComeFrom,today);
		/**新版短信发送end**/
		log.info("smsSendChannel1 sendResult="+result);
		return result;
	}

	/**
	 * 发送短信统一入口:加入ip黑名单，ip频率限制，ip发送日月限制和mobile日月限制
	 * 
	 * @param mobile
	 * @param ip
	 * @param smsContent
	 * @param smsComeFrom
	 * @return
	 */
	public int smsSendChannel2(String mobile, String ip, String svrIp,
			String smsContent, String smsComeFrom) {
		log.info("smsSend params|mobile=" + mobile + "|ip=" + ip + "|svrIp="
				+ svrIp + "|smsContent=" + smsContent + "|smsComeFrom="
				+ smsComeFrom + "|channel=2");
		SMSMessage message = new SMSMessage();
		message.setMobile(mobile);
		message.setIp(ip);
		message.setSvrIp(svrIp);
		message.setSmsContent(smsContent);
		message.setSmsComeFrom(smsComeFrom);
		message.setToday(new Date());
		return SMSZuulServer.getsmsZuulServer().excute(message);
	}
	

	/**
	 * 发送短信统一入口:加入ip黑名单，ip频率限制，ip发送日月限制和mobile日月限制
	 * 深圳湾的
	 * @param mobile
	 * @param ip
	 * @param smsContent
	 * @param smsComeFrom
	 * @return
	 */
	public int smsSendChannel3(String mobile, String ip, String svrIp,
			String smsContent, String smsComeFrom) {
		log.info("smsSend params|mobile=" + mobile + "|ip=" + ip + "|svrIp="
				+ svrIp + "|smsContent=" + smsContent + "|smsComeFrom="
				+ smsComeFrom + "|channel=3");
		final Date today = new Date();
		int checkResult = smsSendCheck(mobile, ip, svrIp, smsContent,
				smsComeFrom, today);
		log.info("smsSendChannel3 checkResult="+checkResult);
		// 检查失败
		if (checkResult < 0) {
			return checkResult;
		}
		String spID = ConfigUtil.getStringValue("soap_szw_spid");
		String password = ConfigUtil.getStringValue("soap_szw_password");
		String accessCode = ConfigUtil.getStringValue("soap_szw_accesscode");
		int result = sendSms(spID,password, accessCode, 
				smsContent, mobile,ip,smsComeFrom,today);
		log.info("smsSendChannel3 sendResult="+result);
		return result;
	}
	
	/**发送短信统一入口:加入ip黑名单，ip频率限制，ip发送日月限制和mobile日月限制
	 * 众视达
	 * @param mobile
	 * @param ip
	 * @param svrIp
	 * @param smsContent
	 * @param smsComeFrom
	 * @return
	 */
	public int smsSendChannel4(String mobile, String ip, String svrIp,
			String smsContent, String smsComeFrom,String amsCode) {
		amsCode = "hyt";
		log.info("smsSend params|mobile=" + mobile + "|ip=" + ip + "|svrIp="
				+  svrIp + "|smsContent=" + smsContent + "|smsComeFrom="
				+ smsComeFrom + "|channel=4"+"|amsCode="+amsCode);
		final Date today = new Date();
		int checkResult = smsSendCheck(mobile, ip, svrIp, smsContent,
				smsComeFrom, today);
		log.info("smsSendChannel4 smsSendCheck checkResult="+checkResult);
		// 检查失败
		if (checkResult < 0) {
			return checkResult;
		}
		SMSMessage message = new SMSMessage();
		message.setMobile(mobile);
		message.setIp(ip);
		message.setSvrIp(svrIp);
		message.setSmsContent(smsContent);
		message.setSmsComeFrom(smsComeFrom);
		message.setToday(new Date());
		int re=-1;
		if("zsd".equals(amsCode)){
			re=ZSDHandler2.getHandler2().handle(message);
		}else if("hyt".equals(amsCode)){
			re=HYTHandler.getHandler().handle(message);
		}
		return re;
	}
	/**
	 * 新版短信发送
	 * @param spID
	 * @param password
	 * @param accessCode
	 * @param smsContent
	 * @param mobile
	 * @param ip
	 * @param smsComeFrom
	 * @param today
	 * @return
	 */
	public int sendSms(String spID, String password, String accessCode,
			String content, String mobileString, String ip, String smsComeFrom,
			Date today) {
		// 成功
		int result = 0;
		//短信提交
		UegateSoap uegatesoap = new  UegateSoap();
		String submitresult=uegatesoap.Submit(spID,
				password, accessCode, content, mobileString);
		log.info("账号"+spID+"发送短信结果："+submitresult);
		String []mobileArr = mobileString.split(",");
		String lastMobile = mobileArr[mobileArr.length-1];
		String returnCode = StringUtil.processLocation(submitresult, lastMobile+"#@#(.*?)#@#");
		if ("0".equals(returnCode)) {
			smsMapper.insertSMS(ip, today, mobileString, smsComeFrom, content);
			String key = RedisKey.PREFIX.SMS_IP_LIMIT + ip;
			if (!RedisOperator.checkKeyExisted(key)) {
				RedisOperator.setString(key, "1", 60);
			} else {
				RedisOperator.increase(key, 1);
			}
		} else if ("15".equals(returnCode)) {
			result = -1;
			String title = "快鸽按揭帐号:" + spID + "短信余额不足提醒";
			String contents = "快鸽按揭帐号:" + spID + "短信余额不足，请尽快充值";
			try {
				EmailUtil.sendEmial(title, contents, "234362597@qq.com");// 杨总
			} catch (AnjboException e) {
				e.printStackTrace();
			}
		} else {
			result = -1;
		}
		return result;
	}
}
