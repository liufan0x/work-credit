package com.anjbo.sms.hander;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import com.anjbo.dao.system.SMSMapper;
import com.anjbo.sms.SMSMessage;
import com.anjbo.utils.ConfigUtil;
import com.anjbo.utils.HttpUtil;
import com.anjbo.utils.StringUtil;


@Component
public class HYTHandler extends SMSHandlerWeightBase{

	private Logger log = Logger.getLogger(getClass());
	private static final String NAME = "HYT";
	
	private volatile static HYTHandler hytHandler;
	public static HYTHandler getHandler(){
		if (hytHandler == null) {
			synchronized (HYTHandler.class) {
				if (hytHandler == null) {
					return new HYTHandler();
				}
			}
		}
		return hytHandler;
	}
	
	static{
		HYTHandler handler = new HYTHandler();
		handler.setName(NAME);
		handler.setWeight(handler.getWeight());
		SMSHandlerWeightBase.weightCollection.put(handler.getWeight(), handler);
	}
	
	public HYTHandler(){}

	public int handle(SMSMessage message) {
		int rv = 0;
		
		String url = ConfigUtil.getStringValue("HYT_URL");
		String username = ConfigUtil.getStringValue("HYT_username");
		String password = ConfigUtil.getStringValue("HYT_password");
		String id = ConfigUtil.getStringValue("HYT_id");
		
		message.setSmsContent(message.getSmsContent().replace("快鸽按揭", "四海恒通").replace("快马金服APP","四海恒通APP")
				.replace("贷款","订单").replace("（非交易类）订单","").replace("（交易类）订单","")
		.replace("信贷系统","PC系统").replace("快鸽信贷系统","四海PC系统").replace("快鸽PC","四海PC")
				.replace("债务置换订单","订单").replace("万",""));
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
			log.info("================行业通短信调用返回结果:"+messageStr);
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
