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
		FS_URL,
		/**工具类地址*/
		TOOLS_URL,
		/**信贷系统*/
		CREDIT_URL
	}
	
	/**
	 * 查档产权状态枚举
	*
	 */
	public enum PropertyStatusEnum{
		L1("抵押查封"),
		L2("抵押"),
		L3("查封"),
		L4("有效"),
		L5("该权利人没有房产"),
		L6("没有找到匹配的房产记录"),
		NULL("");
		
		public static Enum<?> getEnumByName(String name) {
			Enum<?> e = PropertyStatusEnum.NULL;
			for (PropertyStatusEnum propertyStatusEnum : PropertyStatusEnum.values()) {
				if (propertyStatusEnum.getName().equals(name)) {
					e = propertyStatusEnum;
				}
			}
			return e;
		}
		private PropertyStatusEnum(String name) {
			this.name = name;
		}
		private String name;

		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String toString(){
			return name;
		}

	}
}
