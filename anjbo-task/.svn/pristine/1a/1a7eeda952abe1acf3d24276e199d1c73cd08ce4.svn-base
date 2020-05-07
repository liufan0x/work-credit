package com.anjbo.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

public class ConfigUtil {

	private static final Logger log = Logger.getLogger(ConfigUtil.class);
	private static final Map<String, String> CONFIG = new HashMap<String, String>();
    private static Properties properties;
	static {
		Properties p = new Properties();
		try {
			p.load(ConfigUtil.class.getClassLoader().getResourceAsStream(
					"config.properties"));

			for (Entry<Object, Object> tmp : p.entrySet()) {
				CONFIG.put((String) tmp.getKey(), (String) tmp.getValue());
			}
		} catch (IOException e) {
			log.error("加载配置文件:config.properties出错!", e);
		}
	}

	public static String getStringValue(String key, String defaultValue) {
		String value = CONFIG.get(key);
		return StringUtils.isEmpty(value) ? defaultValue : value;
	}

	public static String getStringValue(String key) {
		return getStringValue(key, null);
	}

	public static int getIntegerValue(String key) {
		String value = getStringValue(key);
		return StringUtils.isEmpty(value) ? 0 : NumberUtils.toInt(value);
	}

	public static double getDoubleValue(String key) {
		String value = getStringValue(key);
		return StringUtils.isEmpty(value) ? 0 : NumberUtils.toDouble(value);
	}

	public static Properties getProperties(String config){
		if(null==properties){
		    synchronized (Properties.class) {
		        if(null==properties) {
                    properties = new Properties();
                    String configName = "";
                    if(StringUtil.isBlank(config)){
                        configName = "config.properties";
                    }
                    InputStream in = null;
                    try {
                        in = ConfigUtil.class.getClassLoader().getResourceAsStream(configName);
                        properties.load(new InputStreamReader(in,"UTF-8"));
                    } catch (Exception e){
                        log.error("加载配置文件:"+configName+"出错!", e);
                    } finally {
                        try{
                            if(null!=in){
                                in.close();
                                in = null;
                            }
                        } catch (Exception e){
                            log.error("加载配置文件:"+configName+"关闭IO流异常", e);
                        }
                    }
                }
            }

        }
		return properties;
	}

	public static String getStringValue(String config,String key,boolean fig){
	    if(StringUtil.isBlank(key)){
	        return StringUtil.EMPTY;
        }
        Properties properties = getProperties(config);
        return properties.getProperty(key);
    }
}
