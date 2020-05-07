package com.anjbo.controller;

import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.common.Constants;
import com.anjbo.common.RedisOperator;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespStatus;
import com.anjbo.utils.ConfigUtil;

@Controller
@RequestMapping("/credit/config")
public class ConfigController extends BaseController{

	private static final Logger log = Logger.getLogger(ConfigController.class);
	
	public static void main(String[] args) {
		ConfigController configController = new ConfigController();
		configController.initConfig();
		System.out.println(ConfigUtil.getStringValue(Constants.MAIL_SENDER_USERNAME, ConfigUtil.CONFIG_MAIL));
	}
	
	@ResponseBody
	@RequestMapping(value = "/initConfig") 
	public RespStatus initConfig(){
		initConfigBase();
		initConfigUrl();
		initConfigVersion();
		initConfigSms();
		initConfigMail();
		System.out.println(ConfigUtil.getStringValue(Constants.PROJECT_NAME, ConfigUtil.CONFIG_VERSION)+":"+ConfigUtil.getStringValue(Constants.PROJECT_VERSION, ConfigUtil.CONFIG_VERSION));
		return RespHelper.setSuccessRespStatus(new RespStatus());
	}
	
	@ResponseBody
	@RequestMapping(value = "/initConfigBase") 
	public RespStatus initConfigBase(){
		Properties p = new Properties();
		try {
			Map<String, String> configMap = new HashMap<String, String>();
			p.load(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("base.properties"),"utf-8"));
			for (Entry<Object, Object> tmp : p.entrySet()) {
				configMap.put((String) tmp.getKey(), (String) tmp.getValue());
			}
			RedisOperator.set(ConfigUtil.CONFIG_BASE, configMap);
			return RespHelper.setSuccessRespStatus(new RespStatus());
		} catch (Exception e) {
			log.error("加载配置文件:base.properties出错!",e);
			return RespHelper.setFailRespStatus(new RespStatus(), "加载配置文件:base.properties出错!");
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/initConfigUrl") 
	public RespStatus initConfigUrl(){
		Properties p = new Properties();
		try {
			Map<String, String> configMap = new HashMap<String, String>();
			p.load(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("link.properties"),"utf-8"));
			for (Entry<Object, Object> tmp : p.entrySet()) {
				configMap.put((String) tmp.getKey(), (String) tmp.getValue());
			}
			RedisOperator.set(ConfigUtil.CONFIG_LINK, configMap);
			return RespHelper.setSuccessRespStatus(new RespStatus());
		} catch (Exception e) {
			log.error("加载配置文件:link.properties出错!",e);
			return RespHelper.setFailRespStatus(new RespStatus(), "加载配置文件:link.properties出错!");
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/initConfigVersion") 
	public RespStatus initConfigVersion(){
		Properties p = new Properties();
		try {
			Map<String, String> configMap = new HashMap<String, String>();
			p.load(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("project.properties"),"utf-8"));
			for (Entry<Object, Object> tmp : p.entrySet()) {
				configMap.put((String) tmp.getKey(), (String) tmp.getValue());
			}
			RedisOperator.set(ConfigUtil.CONFIG_VERSION, configMap);
			return RespHelper.setSuccessRespStatus(new RespStatus());
		} catch (Exception e) {
			log.error("加载配置文件:project.properties出错!",e);
			return RespHelper.setFailRespStatus(new RespStatus(), "加载配置文件:project.properties出错!");
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/initConfigSms") 
	public RespStatus initConfigSms(){
		Properties p = new Properties();
		try {
			Map<String, String> configMap = new HashMap<String, String>();
			p.load(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("sms-template.properties"),"utf-8"));
			for (Entry<Object, Object> tmp : p.entrySet()) {
				configMap.put((String) tmp.getKey(), (String) tmp.getValue());
			}
			RedisOperator.set(ConfigUtil.CONFIG_SMS, configMap);
			return RespHelper.setSuccessRespStatus(new RespStatus());
		} catch (Exception e) {
			log.error("加载配置文件:sms-template.properties出错!",e);
			return RespHelper.setFailRespStatus(new RespStatus(), "加载配置文件:sms-template.properties出错!");
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/initConfigMail") 
	public RespStatus initConfigMail(){
		Properties p = new Properties();
		try {
			Map<String, String> configMap = new HashMap<String, String>();
			p.load(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("mail.properties"),"utf-8"));
			for (Entry<Object, Object> tmp : p.entrySet()) {
				configMap.put((String) tmp.getKey(), (String) tmp.getValue());
			}
			RedisOperator.set(ConfigUtil.CONFIG_MAIL, configMap);
			return RespHelper.setSuccessRespStatus(new RespStatus());
		} catch (Exception e) {
			log.error("加载配置文件:mailServer.properties出错!",e);
			return RespHelper.setFailRespStatus(new RespStatus(), "加载配置文件:sms.properties出错!");
		}
	}

}
