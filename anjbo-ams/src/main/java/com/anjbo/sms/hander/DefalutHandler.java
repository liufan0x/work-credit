package com.anjbo.sms.hander;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;

import com.anjbo.sms.SMSMessage;
import com.anjbo.sms.SMSService;
import com.anjbo.utils.ConfigUtil;

@Component
public class DefalutHandler extends SMSHandlerWeightBase{

	private Logger log = Logger.getLogger(getClass());
	
	private static final String NAME = "DEFAULT";
	
	static{
		DefalutHandler handler = new DefalutHandler();
		handler.setName(NAME);
		handler.setWeight(handler.getWeight());
		SMSHandlerWeightBase.weightCollection.put(handler.getWeight(), handler);
	}

	private DefalutHandler() {}

	@Override
	public int handle(SMSMessage message) {
		String spID = ConfigUtil.getStringValue("soap_anjbo_spid");
		String password = ConfigUtil.getStringValue("soap_anjbo_password");
		String accessCode = ConfigUtil.getStringValue("soap_anjbo_accesscode");
		int result = ContextLoader.getCurrentWebApplicationContext().getBean(SMSService.class).sendSms(spID,password, accessCode, 
				message.getSmsContent(), message.getMobile(),message.getIp(),message.getSmsComeFrom(),message.getToday());
		log.info("老版短信接口被调用====smsSendChannel2 sendResult="+result);
		return result;
	}

	@Override
	public Integer getWeight() {
		return getWeightByStr(NAME);
	}
}
