package com.anjbo.common;
/**
 * Redis存储的键值统一在此定义
 * 为避免各项目以及项目内部的存储冲突，定义规则为:"项目名:模块名:唯一标示名(如主键,唯一键)"
 */
public interface RedisKey {
	public static final String SPLIT_FLAG = ":";
	public static final String PROJECT_NAME="ANJBO_CREDIT";
	public interface PREFIX{
		/**
		 * 用户注册后如图像信息，姓名信息，电话信息的存储
		 */
		public static final String MORTGAGE_REGISTINFO = PROJECT_NAME+SPLIT_FLAG+"REGISTINFO"+SPLIT_FLAG;
		/**
		 * 用户登录成功后的信息存储
		 */
		public static final String ANJBO_CREDIT_LOGININFO = PROJECT_NAME+SPLIT_FLAG+"LOGININFO"+SPLIT_FLAG;
		/**
		 * 用户登录成功后的信息存储
		 */
		public static final String ANJBO_CREDIT_ORDER_BASE_INFO = PROJECT_NAME+SPLIT_FLAG+"ORDERBASEINFO"+SPLIT_FLAG;
	   
	}
}

