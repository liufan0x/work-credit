package com.anjbo.utils.common;

import com.anjbo.utils.huarong.PropertyUtil;

/**
 * @Description: 读取配置文件类
 * @see: ReturnParam 此处填写需要参考的类
 * @version 2017年8月22日 下午15:19:06
 * @author chenzm
 */
public class ThirdConfigUtil {
	private ThirdConfigUtil() {
		super();
	}
	
	public static final PropertyUtil propertyUtil;

	
	static {
		// 读取华融快鸽配置文件
		propertyUtil = PropertyUtil.getInstance("third-config");		
	}
	
	public static String getProperty(String url){
		return propertyUtil.getProperty(url);
	}

	/** 编码格式：ISO8859-1 */
	public static final String ENCODING_ISO88591 = "ISO8859-1";
	/** 编码格式：UTF-8 */
	public static final String ENCODING_UTF8 = "UTF-8";
	/** HTTPS接口超时时间:15s */
	public static final int HTTP_TIMEOUT = 15 * 1000;
}
