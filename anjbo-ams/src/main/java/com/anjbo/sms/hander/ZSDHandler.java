package com.anjbo.sms.hander;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;

import com.anjbo.dao.system.SMSMapper;
import com.anjbo.sms.SMSMessage;
import com.anjbo.sms.SMSService;
import com.anjbo.utils.ConfigUtil;
import com.anjbo.utils.HttpUtil;
import com.anjbo.utils.StringUtil;


@Component
public class ZSDHandler extends SMSHandlerWeightBase{

	private Logger log = Logger.getLogger(getClass());
	
	private static final String NAME = "ZSD";
	
	static{
		ZSDHandler handler = new ZSDHandler();
		handler.setName(NAME);
		handler.setWeight(handler.getWeight());
		SMSHandlerWeightBase.weightCollection.put(handler.getWeight(), handler);
	}
	
	private ZSDHandler(){}

	@Override
	public int handle(SMSMessage message) {
		int rv = 0;
		
		String url = ConfigUtil.getStringValue("ZY_URL");
		String username = ConfigUtil.getStringValue("ZY_username");
		String password = ConfigUtil.getStringValue("ZY_password");
		String id = ConfigUtil.getStringValue("ZY_id");
		
		StringBuilder sb = new StringBuilder();
		sb.append("action=send&");
		sb.append("userid="+id+"&");
		sb.append("account="+username+"&");
		sb.append("password="+password+"&");
		sb.append("mobile="+message.getMobile()+"&");
		sb.append("content="+StringUtil.encodeUtf8Str(message.getSmsContent())+"&");
		sb.append("sendTime=&extno=");
		try {
			String messageStr = HttpUtil.get(url+"?"+sb.toString());
			log.info("================众视达短信调用返回结果:"+messageStr);
			Pattern p=Pattern.compile("<returnstatus>(.*)</returnstatus>");
			Matcher m=p.matcher(messageStr);
			String retuenCode = "";
			while(m.find()){
				retuenCode = m.group(1);
			}
			if (!"Success".equals(retuenCode)) {
				rv = -1;
			}else{
				try {
					ContextLoader.getCurrentWebApplicationContext().getBean(SMSMapper.class)
					.insertSMS(message.getIp(), new Date(), message.getMobile(), message.getSmsComeFrom(), message.getSmsContent());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			rv = -1;
		}
		return rv;
		
	}

	@Override
	public Integer getWeight() {
		return getWeightByStr(NAME);
	}
	
}
