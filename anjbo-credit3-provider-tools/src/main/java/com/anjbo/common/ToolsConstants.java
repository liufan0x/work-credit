package com.anjbo.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@RefreshScope
@Configuration
public class ToolsConstants {
	
	/**出回款报备权限*/
	public static String PREPARATION_AUTHID;

	/**出回款报备权限*/
	public static String PREPARATION_AUTHID_CITYCODE;

	@Value("${tools.preparation.authid}")
	public void setPreparation_authid(String preparation_authid) {
		ToolsConstants.PREPARATION_AUTHID = preparation_authid;
	}

	@Value("${tools.preparation.authid.cityCode}")
	public void setPreparation_authid_cityCode(String preparation_authid_cityCode) {
		ToolsConstants.PREPARATION_AUTHID_CITYCODE = preparation_authid_cityCode;
	}
	
}
