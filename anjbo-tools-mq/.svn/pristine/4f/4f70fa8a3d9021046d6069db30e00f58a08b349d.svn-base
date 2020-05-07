package com.anjbo.parameter;

import org.springframework.util.StringUtils;

import com.anjbo.exception.SubscribeException;
/**
 * 设置参数管理
 * @author cain
 *
 */
public class SettingManage {
	public static final String EXCEPTIONSTR="[Subscription_Service]:";
	
	//连接ip地址
	public static String host;
	//端口
	public static int port;
	//账号名
	public static String userName;
	//账号密码
	public static String password;
	//使用者唯一标识
	public static String qKey;
	
	public static void setting(String host,int port,String userName,String password,String qKey) throws SubscribeException{
			if(StringUtils.isEmpty(host)){
				throw new SubscribeException(EXCEPTIONSTR+"The Host cannot be empty!");
			}
			if(StringUtils.isEmpty(userName)){
				throw new SubscribeException(EXCEPTIONSTR+"The userName cannot be empty!");
			}
			if(StringUtils.isEmpty(password)){
				throw new SubscribeException(EXCEPTIONSTR+"The password cannot be empty!");
			}
			if(StringUtils.isEmpty(qKey)){
				throw new SubscribeException(EXCEPTIONSTR+"The qKey cannot be empty!");
			}
			
			SettingManage.host=host;
			SettingManage.port=port;
			SettingManage.userName=userName;
			SettingManage.password=password;
			SettingManage.qKey=qKey;
	 }
	
}
