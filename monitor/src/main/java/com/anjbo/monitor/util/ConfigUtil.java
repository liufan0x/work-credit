package com.anjbo.monitor.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;

public class ConfigUtil
{
  private static final Logger log = Logger.getLogger(ConfigUtil.class);
  private static final Map<String, String> CONFIG = new HashMap();
  private static Properties properties;

  public static String getStringValue(String key, String defaultValue)
  {
    String value = (String)CONFIG.get(key);
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
    return StringUtils.isEmpty(value) ? 0.0D : NumberUtils.toDouble(value);
  }

  public static Properties getProperties(String config) {
    if (null == properties) {
      synchronized (Properties.class) {
        if (null == properties) {
          properties = new Properties();
          String configName = "";
          if (StringUtils.isBlank(config)) {
            configName = "config.properties";
          }
          InputStream in = null;
          try {
            in = ConfigUtil.class.getClassLoader().getResourceAsStream(configName);
            properties.load(new InputStreamReader(in, "UTF-8"));
          } catch (Exception e) {
            log.error("加载配置文件:" + configName + "出错!", e);
          } finally {
            try {
              if (null != in) {
                in.close();
                in = null;
              }
            } catch (Exception e) {
              log.error("加载配置文件:" + configName + "关闭IO流异常", e);
            }
          }
        }
      }
    }

    return properties;
  }

  public static String getStringValue(String config, String key, boolean fig) {
    if (StringUtils.isBlank(key)) {
      return "";
    }
    Properties properties = getProperties(config);
    return properties.getProperty(key);
  }

  static
  {
    Properties p = new Properties();
    try {
      p.load(ConfigUtil.class.getClassLoader().getResourceAsStream("config.properties"));

      for (Map.Entry tmp : p.entrySet())
        CONFIG.put((String)tmp.getKey(), (String)tmp.getValue());
    }
    catch (IOException e) {
      log.error("加载配置文件:config.properties出错!", e);
    }
  }
}