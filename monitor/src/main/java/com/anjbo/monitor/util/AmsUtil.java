package com.anjbo.monitor.util;

import com.anjbo.monitor.common.AnjboException;
import com.anjbo.monitor.common.RespStatus;
import com.anjbo.monitor.common.RespStatusEnum;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public class AmsUtil
  implements Callable<RespStatus>
{
  private static final Logger log = Logger.getLogger(AmsUtil.class);
  public static final Map<String, String> smsMap = new HashMap();
  private static final String SMSSOURCE = "【快鸽按揭】";
  private static final String SMSURL2 = "smsSend2.act";
  private static final String SMSURL4 = "smsSend4.act";
  private static final String EMAILSEND = "emailSend.act";
  private String phone;
  private String ip;
  private String smsContent;
  private String smsComeFrom;
  private String amsCode;

  public static void main(String[] args)
    throws AnjboException
  {
  }

  public AmsUtil(String phone, String ip, String smsContent, String smsComeFrom, String amsCode)
  {
    this.phone = phone;
    this.ip = ip;
    this.smsContent = smsContent;
    this.smsComeFrom = smsComeFrom;
    this.amsCode = amsCode;
  }

  public static RespStatus smsSend(String phone, String ip, String smsContent, String smsComeFrom)
    throws AnjboException
  {
    if ((StringUtils.isEmpty(phone)) || (StringUtils.isEmpty(smsContent)))
    {
      throw new AnjboException(RespStatusEnum.PARAMETER_ERROR
        .getCode(), RespStatusEnum.PARAMETER_ERROR
        .getMsg());
    }
    try {
      Map params = new HashMap();
      params.put("phone", phone);
      params.put("ip", ip);
      params.put("smsContent", URLEncoder.encode(new StringBuilder().append("【快鸽按揭】").append(smsContent).toString(), "UTF-8"));
      params.put("smsComeFrom", new StringBuilder().append("ams-").append(smsComeFrom).toString());
      params.put("amsCode", "hyt");
      String result = "";
      String url = new StringBuilder().append(ConfigUtil.getStringValue("AMS_URL")).append("smsSend4.act").append("?").append(getHttpUrl(params)).toString();
      result = HttpUtil.get(url);
      log.info(new StringBuilder().append("result==").append(result).toString());
      if (result == null) {
        return new RespStatus(RespStatusEnum.REQUEST_TIMEOUT.getCode(), RespStatusEnum.REQUEST_TIMEOUT
          .getMsg());
      }
      Map map = JsonUtil.parseAMSResult(result);
      int code = Integer.valueOf((String)map.get("code")).intValue();
      if (code == 0)
        return new RespStatus(RespStatusEnum.SUCCESS.getCode(), RespStatusEnum.SUCCESS
          .getMsg());
      if (code == -9)
        return new RespStatus(RespStatusEnum.SMS_DAY_THREE.getCode(), RespStatusEnum.SMS_DAY_THREE
          .getMsg());
      if (code == -30) {
        return new RespStatus(RespStatusEnum.SMS_MONTH_FIVE.getCode(), RespStatusEnum.SMS_MONTH_FIVE
          .getMsg());
      }
      return new RespStatus(RespStatusEnum.FAIL.getCode(), RespStatusEnum.FAIL
        .getMsg());
    }
    catch (Exception e) {
      log.error("smsSend Exception.", e);
    }
    throw new AnjboException(RespStatusEnum.SYSTEM_ERROR.getCode(), RespStatusEnum.SYSTEM_ERROR
      .getMsg());
  }

  public static RespStatus emailSend(String title, String email, String content)
    throws AnjboException
  {
    if ((StringUtils.isEmpty(title)) || (StringUtils.isEmpty(email)) || (StringUtils.isEmpty(content)))
    {
      throw new AnjboException(RespStatusEnum.PARAMETER_ERROR
        .getCode(), RespStatusEnum.PARAMETER_ERROR
        .getMsg());
    }
    try {
      Map params = new HashMap();
      params.put("uid", URLEncoder.encode(title, "utf-8"));
      params.put("email", email);
      params.put("m", URLEncoder.encode(content, "utf-8"));
      params.put("flag", "");
      String result = "";
      String url = new StringBuilder().append(ConfigUtil.getStringValue("AMS_URL")).append("emailSend.act").append("?").append(getHttpUrl(params)).toString();
      result = HttpUtil.get(url);
      if (result == null) {
        return new RespStatus(RespStatusEnum.REQUEST_TIMEOUT.getCode(), RespStatusEnum.REQUEST_TIMEOUT
          .getMsg());
      }
      Map map = JsonUtil.parseAMSResult(result);
      int code = Integer.valueOf((String)map.get("code")).intValue();
      if (code == 0) {
        return new RespStatus(RespStatusEnum.SUCCESS.getCode(), RespStatusEnum.SUCCESS
          .getMsg());
      }
      return new RespStatus(RespStatusEnum.FAIL.getCode(), RespStatusEnum.FAIL
        .getMsg());
    }
    catch (Exception e) {
      log.error("smsSend Exception.", e);
    }
    throw new AnjboException(RespStatusEnum.SYSTEM_ERROR.getCode(), RespStatusEnum.SYSTEM_ERROR
      .getMsg());
  }

  public static void emailSend(String title, String[] email, String content) throws AnjboException {
    for (int i = 0; i < email.length; i++)
      emailSend(title, email[i], content);
  }

  private static String getHttpUrl(Map<String, String> params)
  {
    StringBuilder builder = new StringBuilder();
    if (params == null) {
      return null;
    }
    int i = 1;
    int size = params.entrySet().size();
    for (Map.Entry entry : params.entrySet()) {
      builder.append((String)entry.getKey());
      builder.append("=");
      builder.append(StringUtil.trimToEmpty((String)entry.getValue()));
      if (i != size) {
        builder.append("&");
      }
      i++;
    }
    return builder.toString();
  }

  public static void smsSend(String phone, String ip, String constantsKey, Object[] param)
  {
    String smsContent = String.format((String)smsMap.get(constantsKey), param);
    log.info(String.format("发送短信手机号：%s；发送内容：%s", new Object[] { phone, smsContent }));
    if (StringUtils.isEmpty(smsContent)) return;
    String smsComeFrom = new StringBuilder().append("monitor_").append(constantsKey).toString();
    try {
      smsSend(phone, ip, smsContent, smsComeFrom);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void smsSendNoLimit(String phone, String constantsKey, Object[] param)
  {
    String smsIpWhite = ConfigUtil.getStringValue("AMS_SMS_IPWHITE");
    smsSend(phone, smsIpWhite, constantsKey, param);
  }

  public static void smsSend2(String phone, String ip, String amsCode, String smsContent, String smsComeFrom)
  {
    log.info(String.format("发送短信手机号：%s；发送内容：%s", new Object[] { phone, smsContent }));
    if (StringUtils.isEmpty(smsContent)) return; try
    {
      Callable callSMS = new AmsUtil(phone, ip, smsContent, smsComeFrom, amsCode);
      FutureTask futuretask = new FutureTask(callSMS);
      new Thread(futuretask, "Thread-SMS-Sender").start();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public RespStatus call()
    throws Exception
  {
    RespStatus resp = smsSend(this.phone, this.ip, this.smsContent, this.smsComeFrom, this.amsCode);
    log.info(String.format("短信发送回执(%s):%s", new Object[] { this.phone, resp }));
    return resp;
  }

  private RespStatus smsSend(String phone, String ip, String smsContent, String smsComeFrom, String amsCode) throws AnjboException
  {
    if ((StringUtils.isEmpty(phone)) || (StringUtils.isEmpty(smsContent)))
    {
      throw new AnjboException(RespStatusEnum.PARAMETER_ERROR
        .getCode(), RespStatusEnum.PARAMETER_ERROR
        .getMsg());
    }
    try {
      Map params = new HashMap();
      params.put("phone", phone);
      params.put("ip", ip);
      params.put("smsContent", URLEncoder.encode(new StringBuilder().append("【快鸽按揭】").append(smsContent).toString(), "UTF-8"));
      params.put("smsComeFrom", new StringBuilder().append("ams-").append(smsComeFrom).toString());
      params.put("amsCode", amsCode);
      String result = "";
      String url = new StringBuilder().append(ConfigUtil.getStringValue("AMS_URL")).append("smsSend4.act").append("?").append(getHttpUrl(params)).toString();
      result = HttpUtil.get(url);
      log.info(new StringBuilder().append("result==").append(result).toString());
      if (result == null) {
        return new RespStatus(RespStatusEnum.REQUEST_TIMEOUT.getCode(), RespStatusEnum.REQUEST_TIMEOUT
          .getMsg());
      }
      Map map = JsonUtil.parseAMSResult(result);
      int code = Integer.valueOf((String)map.get("code")).intValue();
      if (code == 0)
        return new RespStatus(RespStatusEnum.SUCCESS.getCode(), RespStatusEnum.SUCCESS
          .getMsg());
      if (code == -9)
        return new RespStatus(RespStatusEnum.SMS_DAY_THREE.getCode(), RespStatusEnum.SMS_DAY_THREE
          .getMsg());
      if (code == -30) {
        return new RespStatus(RespStatusEnum.SMS_MONTH_FIVE.getCode(), RespStatusEnum.SMS_MONTH_FIVE
          .getMsg());
      }
      return new RespStatus(RespStatusEnum.FAIL.getCode(), RespStatusEnum.FAIL
        .getMsg());
    }
    catch (Exception e) {
      log.error("smsSend Exception.", e);
    }
    throw new AnjboException(RespStatusEnum.SYSTEM_ERROR.getCode(), RespStatusEnum.SYSTEM_ERROR
      .getMsg());
  }

  static
  {
    Properties p = new Properties();
    try {
      p.load(new InputStreamReader(ConfigUtil.class.getClassLoader().getResourceAsStream("sms.properties"), "utf-8"));

      for (Map.Entry tmp : p.entrySet())
        smsMap.put((String)tmp.getKey(), (String)tmp.getValue());
    }
    catch (IOException e) {
      log.error("加载配置文件:sms.properties出错!", e);
    }
  }
}