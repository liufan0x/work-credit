package com.anjbo.utils.yntrust;

import com.anjbo.utils.huarong.StringUtils;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Administrator on 2018/3/7.
 */
public enum  ConfigUtils {
    INSTANCE;
    private Logger log = Logger.getLogger(ConfigUtils.class);

    private static Properties properties;

    private Object object = new Object();

    private Properties getPropertis(String configName){
        Properties properties = getPropertis();
        try {
            InputStream resourceAsStream = ConfigUtils.class.getClassLoader().getResourceAsStream(configName);
            properties.load(resourceAsStream);
        } catch (Exception e){
            log.error("加载"+configName+"配置文件异常",e);
        }
        return properties;
    }

    private Properties getPropertis(){
        synchronized (object){
            if(null==properties){
                properties = new Properties();
            }
        }
        return properties;
    }

    public String getStringValue(String key,String configName){
        if(StringUtils.isBlank(key)
                ||StringUtils.isBlank(configName)){
            return null;
        }
        Properties propertis = getPropertis(configName);
        Object obj = propertis.get(key);
        if(obj instanceof String){
            return (String)obj;
        }
        return "";
    }
    public Integer getIntegerValue(String key,String configName){
        if(StringUtils.isBlank(key)
                ||StringUtils.isBlank(configName)){
            return null;
        }
        Properties propertis = getPropertis(configName);
        Object obj = propertis.get(key);
        if(obj instanceof Integer){
            return (Integer)obj;
        }
        return null;
    }
}
