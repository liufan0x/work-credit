package com.anjbo.utils.ccb;


/**
 * 项目中定义的枚举
 * 
 * @author limh limh@zxsf360.com
 * @date 2016-6-2 下午07:12:05
 */
public class CCBEnums {
	/**
	 * GLOBAL_CONFIG_KEY classpath:config.properties 对应的key
	 * 
	 * @author Jerry
	 * @version v1.0 Enums.java 2013-9-25 上午11:16:02
	 */
	public enum GLOBAL_CONFIG_KEY {
		CCB_SECRET_KEY, CCB_COMNO, CCB_BANKNO, CCB_CHANNELNO, CCB_WS_URL,
	}
	public enum Order_Task_Status {
		PROCESSED("PROCESSED"), PROCESSING("PROCESSING"), UNPROCESS("UNPROCESS"), FAILE("FAILE");
		private String status;

		private Order_Task_Status(String status) {
			this.setStatus(status);
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}
	}
}
