package com.anjbo.sms.hander;

import java.util.Date;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParser;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;

import com.anjbo.bean.system.AccessSmsBO;
import com.anjbo.dao.system.SMSMapper;
import com.anjbo.sms.SMSMessage;
import com.anjbo.utils.ConfigUtil;
import com.anjbo.utils.HttpsUtil;
import com.anjbo.utils.JsonUtil;
import com.anjbo.utils.MD5Utils;
import com.thoughtworks.xstream.io.json.JsonWriter.Format;








@Component
public class XMKPHandler extends SMSHandlerWeightBase{

	private Logger log = Logger.getLogger(getClass());
	
	private static final String NAME = "XMKP";
	
	static{
		try {
			XMKPHandler handler = new XMKPHandler();
			handler.setName(NAME);
			handler.setWeight(handler.getWeight());
			SMSHandlerWeightBase.weightCollection.put(handler.getWeight(), handler);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private XMKPHandler(){}

	@Override
	public int handle(SMSMessage message) {
		int rv = 0;
		
		String url = ConfigUtil.getStringValue("XMKP_sendUrl");
		String username = ConfigUtil.getStringValue("XMKP_clientId");
		String password = ConfigUtil.getStringValue("XMKP_passWord");
		String smstype = ConfigUtil.getStringValue("XMKP_smsType");
		
		AccessSmsBO smsModel = new AccessSmsBO();
		
		smsModel.setClientid(username);
		smsModel.setPassword(MD5Utils.MD5Encode(password));
		smsModel.setMobile(message.getMobile());
		smsModel.setContent(message.getSmsContent());
		smsModel.setSmstype(smstype);
		
		String resultJson;
		//String smsp_access_url = url.replace("{clientid}",smsModel.getClientid());
		String smsp_access_url=String.format(url, smsModel.getClientid());
		
		resultJson = HttpsUtil.httpPost(smsp_access_url, JSONObject.fromObject(smsModel).toString(), true);
		log.info("================小马快跑短信调用返回结果:"+resultJson);
		if(resultJson!=null){			
			JSONObject returnData = JSONObject.fromObject(resultJson); 
			JSONArray jarr=returnData.getJSONArray("data");
			JSONObject rejson=jarr.getJSONObject(0);	
			 int code=rejson.getInt("code");
			if(code<0){
				rv = -1;
			}else{
				try {
					ContextLoader.getCurrentWebApplicationContext().getBean(SMSMapper.class)
					.insertSMS(message.getIp(), new Date(), message.getMobile(), message.getSmsComeFrom(), message.getSmsContent());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}else{
			rv = -1;
		}
		
		return rv;
		
	}

	@Override
	public Integer getWeight() {
		return getWeightByStr(NAME);
	}


	
}
