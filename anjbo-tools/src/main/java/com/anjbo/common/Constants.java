/**
 * 公共包
 * @author Jerry
 * @version v1.0 Constants.java 2013-9-24 下午06:32:02
 * 
 */
package com.anjbo.common;

/**
 * 定义系统中用到的常量
 * @author limh limh@zxsf360.com
 * @date 2016-6-2 下午07:11:49
 */
public class Constants {
	/**
	 * 默认登录方式:手机号码登录
	 */
	public static final String DEF_LOGIN_TYPE = "1";
	//同致诚接口
	public static final String TZC_USERID = "wxjyAdmin";
	public static final String TZC_PASSWORD = "Wxjy001";
	public static final String TZC_SECRET_KEY="619a3096c1d34793aff00b6bc62b043d";
	//世联接口
	public static final String WORLDUNION_USERID = "ttj";
	public static final String WORLDUNION_PASSWORD = "ttj";
	// 同致诚
	public static final int ENQUIRY_TZC_RATE_MAX = 100;
	// 世联
	public static final int ENQUIRY_SL_RATE_MAX = 100;
	
	//世联查询物业默认城市
	public static final String DEFAULT_CITYID = "1";//深圳
	
	public static final String DEFAULT_CITY = "深圳";//深圳
	
	//询价查询限制次数
	public static final int ENQUIRY_COUNT = 3;

	//评估公司鲁克评估
	public static final String LKPG_ID = "5";
	//评估公司云评估
	public static final String YPG_ID = "4";
	//评估公司国策
	public static final String GJ_ID = "3";
	//评估公司同致诚
	public static final String TZC_ID = "2";
	//评估公司世联
	public static final String SL_ID = "1";
	

	// 各大银行ID
	public static final String BANK_ICBC = "3";// 工行
	public static final String BANK_ABC = "5";// 农行
	public static final String BANK_CCB = "6";// 建行
	public static final String BANK_CMB = "1";// 招行
	public static final String BANK_BOB = "43";// 北京
	public static final String BANK_BCM = "14";// 交通
	public static final String BANK_PSBC = "67";// 邮政
	public static final String BANK_MORTGAGE = "190";//快鸽按揭
	public static final String[] BANKIDARR = new String[] { BANK_ICBC,
			BANK_ABC, BANK_CCB, BANK_CMB, BANK_BOB, BANK_BCM, BANK_PSBC };

	public static final String SMS_FLAG_1 = "1";// 注册时发送短信
	public static final String SMS_FLAG_2 = "2";// 忘记密码时发送短信
	public static final String SMS_FLAG_3 = "3";// 新增订单
	public static final String SMS_FLAG_4 = "4";// 修改订单
	public static final String SMS_FLAG_5 = "5";// 按揭宝注册时发送短信
	public static final String SMS_FLAG_6 = "6";// 按揭宝忘记密码时发送短信

	public static final String ALIPAY = "alipay";
	public static final String WXPAY = "wxpay";
	public static final String DEVICE_IOS = "iOS";//苹果手机端
	public static final String DEVICE_ANDROID = "Android";//安卓手机端
	public static final String DEVICE_WEIXIN = "WeiXin";//微信端
	public static final String DEVICE_WAP = "wap";//wap端
	public static final String DEVICE_WEB = "web";//pc端
	public static final String DEVICE_NAN = "NaN";//未知设备
	
	public static final String SMSCOMEFROM_LOGIN="mortgage-login";
	public static final String SMSCOMEFROM_REG="mortgage-reg";
	public static final String SMSCOMEFROM_FORGETPWD="mortgage-forgetpwd";
	public static final String SMSCOMEFROM_REGCOUPON="mortgage-regcoupon";
	public static final String SMSCOMEFROM_RECEIVE50COUPON="mortgage-receive50coupon";
	public static final String SMSCOMEFROM_LOAN="mortgage-loan";
	public static final String SMSCOMEFROM_ASSESS="mortgage-assess";
	public static final String SMSCOMEFROM_ARCHIVE="mortgage-archive";
	public static final String SMSCOMEFROM_ORDER_FORECLOSURE="mortgage-order-foreclosure";
	public static final String SMSCOMEFROM_ORDER_SECONDHANDHOUSE="mortgage-order-secondhandhouse";
	public static final String SMSCOMEFROM_ORDER_ASSESS_REPORT="mortgage-order-assess-report";
	public static final String SMSCOMEFROM_ARCHIVE_MONITOR_JOB="mortgage-archive-monitor-job";

	// 统计接口地址
	public static final String STATISTICS_URL = "http://st.xiaoniu365.com/";
}
