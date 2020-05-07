package com.anjbo.chromejs.manager;

import java.util.HashMap;
import java.util.Map;

import com.anjbo.chromejs.entity.LoginConfig;


public class LoginContext {
	private Map<String,LoginConfig> loginMap;
	private static LoginContext loginContext;
	private LoginContext(){
		loginMap = new HashMap<String,LoginConfig>();
	}
	public static synchronized LoginContext getInstance(){
		if (loginContext == null) {
			loginContext = new LoginContext();
		}
		return loginContext;
	}
	public Map<String,LoginConfig> getLoginMap(){
		return loginMap;
	}
	public LoginConfig getLoginConfigByPubName(String pubName){
		return loginMap.get(pubName);
	}
	public LoginConfig putLoginConfigToContext(String pubName,LoginConfig loginConfig){
		return loginMap.put(pubName, loginConfig);
	}
}
