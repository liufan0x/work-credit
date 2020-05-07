package com.anjbo.common;
/**
 * Redis存储的键值统一在此定义
 * 为避免各项目以及项目内部的存储冲突，定义规则为:"项目名:模块名:唯一标示名(如主键,唯一键)"
 */
public interface RedisKey {
	public static final String SPLIT_FLAG = ":";
	public static final String PROJECT_NAME="MORTGAGE";
	public interface PREFIX{
		/**
		 * 用户注册后如图像信息，姓名信息，电话信息的存储
		 */
		public static final String MORTGAGE_REGISTINFO = PROJECT_NAME+SPLIT_FLAG+"REGISTINFO"+SPLIT_FLAG;
		/**
		 * 用户登录成功后的信息存储
		 */
		public static final String MORTGAGE_LOGININFO = PROJECT_NAME+SPLIT_FLAG+"LOGININFO"+SPLIT_FLAG;
		/**
		 * cookie信息
		 */
		public static final String MORTGAGE_COOKIE = PROJECT_NAME+SPLIT_FLAG+"COOKIE";
		/**
		 * 查档时间限制
		 */
		public static final String MORTGAGE_ARCHIVE_COUNT = PROJECT_NAME+SPLIT_FLAG+"ARCHIVECOUNT"+SPLIT_FLAG;
		/**
		 * 查档次数限制
		 */
		public static final String MORTGAGE_ARCHIVE_DAY_COUNT = PROJECT_NAME+SPLIT_FLAG+"ARCHIVEDAYCOUNT"+SPLIT_FLAG;
		/**
		 * 办文查询控制
		 */
		public static final String MORTGAGE_DOC_SEARCH = PROJECT_NAME+SPLIT_FLAG+"DOCSEARCH"+SPLIT_FLAG;
		/**
		 * 询价查询物业控制
		 */
		public static final String MORTGAGE_PROPERTYNAME_SEARCH = PROJECT_NAME+SPLIT_FLAG+"PROPERTYNAME"+SPLIT_FLAG;
		/**
		 * 查档监控任务
		 */
		public static final String MORTGAGE_ARCHIVE_MONITOR_JOB = PROJECT_NAME+SPLIT_FLAG+"ARCHIVEMONITORJOB"+SPLIT_FLAG;
		public static final String MORTGAGE_ARCHIVE_MONITOR_SMS_SEND_COUNT = PROJECT_NAME+SPLIT_FLAG+"ARCHIVEMONITORSMSSENDCOUNT";
		
		/**
		 * 每小时相同查档限制
		 */
		public static final String MORTGAGE_ARCHIVE_LIMITFORHOUR = PROJECT_NAME+SPLIT_FLAG+"ARCHIVELIMITFORHOUR"+SPLIT_FLAG;
		/**
		 * IOS更新版本提醒
		 */
		public static final String MORTGAGE_IOSREMINDER = PROJECT_NAME+SPLIT_FLAG+"IOSREMINDER";
		/**
		 * aop校验次数限制
		 */
		public static final String MORTGAGE_VALIDATEAOP_DAY_COUNT = PROJECT_NAME+SPLIT_FLAG+"VALIDATEAOPDAYCOUNT"+SPLIT_FLAG;
	   
	}
}

