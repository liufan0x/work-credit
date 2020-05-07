package com.anjbo.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Component;

@Component
@RefreshScope
@Configuration
public class Constants {
	
	/** base.properties start **/
	
	//ip白名单
	public static String BASE_AMS_IPWHITE;
	
	//已通过需发短信的流程Id
	public static String BASE_SMS_PROCESS_PASS;

	//待处理需发短信的流程Id
	public static String BASE_SMS_PROCESS_HANDLE;
	
	//fs文件系统
	public static String LINK_ANJBO_FS_URL;

	//credit信贷系统
	public static String LINK_ANJBO_CREDIT_URL;

	//cm建行系统
	public static String LINK_ANJBO_CM_URL;
	
	//ams短信系统
	public static String LINK_ANJBO_AMS_URL;

	//tools工具系统
	public static String LINK_ANJBO_TOOlS_URL;
	
	//快鸽按揭app
	public static String LINK_ANJBO_APP_URL;
	
	//接口地址
		 public static String SGT_WS_URL;
		//影像资料上传
		 public static String SGT_WS_HOST;
		 //端口
		 public static String SGT_WS_PORT;
		 //用户名
		 public static String SGT_WS_USERNAME;
		 //密码
		 public static String SGT_WS_PASSWORD;
		 //地址
		 public static String SGT_WS_DIRECTORY;
		 //合作机构号
		 public static String SGT_WS_BRNO;
		 //项目号
		 public static String SGT_WS_PROJNO;
		 //产品号
		 public static String SGT_WS_PRDTNO;
		 
		// 支付渠道
		 public static String SGT_WS_CARDCHNO;
		// 专户账号
	     public static String SGT_WS_ACNO;
	     
	     @Value("${SGT_WS_URL}")
			public  void setSGT_WS_URL(String sGT_WS_URL) {
				SGT_WS_URL = sGT_WS_URL;
			}

			 @Value("${SGT_WS_HOST}")		 
			public  void setSGT_WS_HOST(String sGT_WS_HOST) {
				SGT_WS_HOST = sGT_WS_HOST;
			}

			 @Value("${SGT_WS_PORT}")	
			public  void setSGT_WS_PORT(String sGT_WS_PORT) {
				SGT_WS_PORT = sGT_WS_PORT;
			}
			 
			 @Value("${SGT_WS_USERNAME}")	
			public  void setSGT_WS_USERNAME(String sGT_WS_USERNAME) {
				SGT_WS_USERNAME = sGT_WS_USERNAME;
			}

	 
			 @Value("${SGT_WS_PASSWORD}")	
			public  void setSGT_WS_PASSWORD(String sGT_WS_PASSWORD) {
				SGT_WS_PASSWORD = sGT_WS_PASSWORD;
			}


			 @Value("${SGT_WS_DIRECTORY}")	
			public  void setSGT_WS_DIRECTORY(String sGT_WS_DIRECTORY) {
				SGT_WS_DIRECTORY = sGT_WS_DIRECTORY;
			}

	       
			 @Value("${SGT_WS_BRNO}")
			public  void setSGT_WS_BRNO(String sGT_WS_BRNO) {
				SGT_WS_BRNO = sGT_WS_BRNO;
			}
			 
			 @Value("${SGT_WS_PROJNO}")
			 public void setSGT_WS_PROJNO(String sSGT_WS_PROJNO) {
				 SGT_WS_PROJNO = sSGT_WS_PROJNO;
			 }
			 
			 @Value("${SGT_WS_CARDCHNO}")
				public  void setSGT_WS_CARDCHNO(String sGT_WS_CARDCHNO) {
					SGT_WS_CARDCHNO = sGT_WS_CARDCHNO;
				}

				
				 @Value("${SGT_WS_ACNO}")
				public  void setSGT_WS_ACNO(String sGT_WS_ACNO) {
					SGT_WS_ACNO = sGT_WS_ACNO;
				}
	

				 @Value("${SGT_WS_PRDTNO}")
				public  void setSGT_WS_PRDTNO(String sGT_WS_PRDTNO) {
					SGT_WS_PRDTNO = sGT_WS_PRDTNO;
				}

	@Value("${link.anjbo.app.url}")
	public void setLinkAnjboAppUrl(String link_anjbo_app_url) {
		LINK_ANJBO_APP_URL = link_anjbo_app_url;
	}
	
	@Value("${link.anjbo.tools.url}")
	public void setLinkAnjboToolsUrl(String link_anjbo_tools_url) {
		LINK_ANJBO_TOOlS_URL = link_anjbo_tools_url;
	}
	
	@Value("${link.anjbo.ams.url}")
	public void setLinkAnjboAmsUrl(String link_anjbo_ams_url) {
		LINK_ANJBO_AMS_URL = link_anjbo_ams_url;
	}
	
	@Value("${link.anjbo.fs.url}")
	public void  setLinkAnjboFsUrl(String link_anjbo_fs_url){
		LINK_ANJBO_FS_URL = link_anjbo_fs_url;
	}
	
	@Value("${link.anjbo.credit.url}")
	public void  setLinkAnjboCreditUrl(String link_anjbo_credit_url){
		LINK_ANJBO_CREDIT_URL = link_anjbo_credit_url;
	}

	@Value("${link.anjbo.cm.url}")
	public void  setLinkAnjboCmUrl(String link_anjbo_cm_url){
		LINK_ANJBO_CM_URL = link_anjbo_cm_url;
	}
	
	@Value("${base.sms.process.pass}")
	public void setBaseSmsProcessPass(String base_sms_process_pass) {
		BASE_SMS_PROCESS_PASS = base_sms_process_pass;
	}
	
	@Value("${base.sms.process.handle}")
	public void setBaseSmsProcessHandle(String base_sms_process_handle) {
		BASE_SMS_PROCESS_HANDLE = base_sms_process_handle;
	}
	
	@Value("${base.ams.ipwhite}")
	public void setBASE_AMS_IPWHITE(String base_ams_ipwhite) {
		BASE_AMS_IPWHITE = base_ams_ipwhite;
	}
	
	
	
	/** base.properties end **/

	@Bean  
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {  
	   return new PropertySourcesPlaceholderConfigurer();  
	} 

	/**登录Key**/
	public static final String LOGIN_USER_KEY = new StringBuffer().append("LOGIN_").append("ANJBO-CREDIT").append("_USER").toString();
	public static final String LOGIN_USER_CODE_SMS = "LOGIN_USER_CODE_SMS";
	

}
