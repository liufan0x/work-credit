package com.anjbo.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@RefreshScope
@Configuration
public class UserConstants {

	public static String AGENCY_UIDS;
	
	@Value("${agency.uids}")
	public void setAgencyUids(String agencyUids) {
		AGENCY_UIDS = agencyUids;
	}

}
