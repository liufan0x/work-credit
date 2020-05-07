package com.anjbo.common;
/**
 * Redis存储的键值统一在此定义
 * 为避免各项目以及项目内部的存储冲突，定义规则为:"项目名:模块名:唯一标示名(如主键,唯一键)"
 */
public interface RedisKey {
	public static final String SPLIT_FLAG = ":";
	public static final String PROJECT_NAME="UAMS";
	public interface PREFIX{
		/**
		 * UAMS IP发送短信限制
		 */
		public static final String SMS_IP_LIMIT = PROJECT_NAME + SPLIT_FLAG
				+ "SMSIPLIMIT" + SPLIT_FLAG;
	   
	}
}

