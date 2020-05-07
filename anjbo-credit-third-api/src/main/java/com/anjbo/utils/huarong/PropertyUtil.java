package com.anjbo.utils.huarong;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @Description: 属性文件读取帮助类
 * @see: 用于读取属性文件
 * @version 2017年8月22日 下午3:00:02
 * @author chenzm
 */
public class PropertyUtil {
	private static Map<String, PropertyUtil> instance = Collections.synchronizedMap(new HashMap<String, PropertyUtil>());
	private static Object lock = new Object();
	protected String sourceUrl;
	protected ResourceBundle resourceBundle;
	private static Map<String, String> convert = Collections.synchronizedMap(new HashMap());

	protected PropertyUtil(String sourceUrl) {
		this.sourceUrl = sourceUrl;
		load();
	}

	public static PropertyUtil getInstance(String sourceUrl) {
		synchronized (lock) {
			PropertyUtil manager = (PropertyUtil) instance.get(sourceUrl);
			if (manager == null) {
				manager = new PropertyUtil(sourceUrl);
				instance.put(sourceUrl, manager);
			}
			return manager;
		}
	}

	private synchronized void load() {
		try {
			this.resourceBundle = ResourceBundle.getBundle(this.sourceUrl);
		} catch (Exception e) {
			throw new RuntimeException("sourceUrl = " + this.sourceUrl + " file load error!", e);
		}
	}

	public synchronized String getProperty(String key) {
		return this.resourceBundle.getString(key);
	}

	public Map<String, String> readyConvert() {
		Enumeration enu = this.resourceBundle.getKeys();
		while (enu.hasMoreElements()) {
			String key = (String) enu.nextElement();
			String value = this.resourceBundle.getString(key);
			convert.put(value, key);
		}
		return convert;
	}

	public Map<String, String> readyConvert(ResourceBundle resourcebundle) {
		Enumeration enu = resourcebundle.getKeys();
		while (enu.hasMoreElements()) {
			String key = (String) enu.nextElement();
			String value = resourcebundle.getString(key);
			convert.put(value, key);
		}
		return convert;
	}

	public static void main(String[] args) {
		PropertyUtil manager = getInstance("third-config");
		System.out.println(manager.getProperty("Apply.url"));
	}
}