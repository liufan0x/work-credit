package com.anjbo.utils;

import com.anjbo.common.RedisOperator;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
public class ConfigUtil {

	private static final Logger log = Logger.getLogger(ConfigUtil.class);
	public static final String CONFIG_BASE = "credit_config_base";
	public static final String CONFIG_BASE_FILE = "base.properties";
	public static final String CONFIG_LINK = "credit_config_link";
	public static final String CONFIG_LINK_FILE = "link.properties";
	public static final String CONFIG_SMS = "credit_config_sms";
	public static final String CONFIG_SMS_FILE = "sms-template.properties";
	public static final String CONFIG_MAIL = "credit_config_mail";
	public static final String CONFIG_MAIL_FILE = "mail.properties";
	public static final String CONFIG_VERSION = "credit_config_project";
	
	public static String getStringValue(String key, String defaultValue,String config) {
		Map<String, String> configMap = new HashMap<String, String>();
		try {
			configMap.putAll((Map<String, String>)RedisOperator.get(config));
		} catch (Exception e) {
			log.error("获取配置文件"+config+"失败",e);
		}
		String value = configMap.get(key);
		return StringUtils.isEmpty(value) ? defaultValue : value;
	}

	/**
	 * @param key
	 * @param config 对应的key所在配置文件。对应上面常量
	 * @return
	 */
	public static String getStringValue(String key,String config) {
		return getStringValue(key, null, config);
	}
	
	/**
	 * @param key
	 * @param config 对应的key所在配置文件。对应上面常量
	 * @return
	 */
	public static int getIntegerValue(String key,String config) {
		String value = getStringValue(key, config);
		return StringUtils.isEmpty(value) ? 0 : NumberUtils.toInt(value);
	}
	
	/**
	 * @param key
	 * @param config 对应的key所在配置文件。对应上面常量
	 * @return
	 */
	public static double getDoubleValue(String key,String config) {
		String value = getStringValue(key, config);
		return StringUtils.isEmpty(value) ? 0 : NumberUtils.toDouble(value);
	}
	
	public static String getStringValue(String key) {
		return getStringValue(key, null);
	}
}
