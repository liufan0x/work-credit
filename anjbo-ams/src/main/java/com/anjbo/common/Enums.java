package com.anjbo.common;

/**
 * 项目中定义的枚举
 * @author limh limh@zxsf360.com
 * @date 2016-6-2 下午07:12:05
 */
public class Enums {
	/**
	 * GLOBAL_CONFIG_KEY classpath:config.properties 对应的key
	 * @author Jerry
	 * @version v1.0 Enums.java 2013-9-25 上午11:16:02
	 */
	public enum GLOBAL_CONFIG_KEY {
		/**ams地址**/
		AMS_URL,
		/**ip白名单**/
		AMS_SMS_IPWHITE,
		/**图片上传地址**/
		FS_URL
	}
	public enum CONFIG_KEY {
		DAYCOUNT,
		WEEKCOUNT,
		MONTHCOUNT,
		IPDAYCOUNT,
		IPWEEKCOUNT,
		IPMONTHCOUNT
	}
}
