package com.anjbo.monitor.common;




/**
 * 项目中定义的枚举
 */
public class Enums {
	
	public enum GLOBAL_CONFIG_KEY {
		  AMS_URL, 

		  AMS_SMS_IPWHITE, 

		  FS_URL, 

		  TOOLS_URL, 

		  CREDIT_URL;
	}
	
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
