package com.anjbo.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.anjbo.bean.UserDto;
import com.anjbo.common.ToolsConstants;
import com.anjbo.controller.BaseController;

public class ToolsBaseController extends BaseController {	
	
	public String getCityCodes(UserDto userDto,String cityCode) {
		String cityCodes = "";
		List<String> authIds = userDto.getAuthIds();
		String[] preparationAuthids = ToolsConstants.PREPARATION_AUTHID.split(",");
		String[] preparationAuthidsCtiyCodes = ToolsConstants.PREPARATION_AUTHID_CITYCODE.split(",");
		for (int i = 0; i < preparationAuthids.length; i++) {
			if(authIds.contains(preparationAuthids[i])) {
				if(StringUtils.isEmpty(cityCode)) {
					cityCodes += "'" + preparationAuthidsCtiyCodes[i] + "',";
				}else {
					if(cityCode.equals(preparationAuthidsCtiyCodes[i])) {
						cityCodes += "'" + preparationAuthidsCtiyCodes[i] + "',";
					}
				}
			}
		}
		
		if(cityCodes.length() > 0) {
			cityCodes = cityCodes.substring(0, cityCodes.length()-1);
		}else {
			cityCodes = "'无城市'";
		}
		System.out.println(cityCodes);
		return cityCodes;
	}
	
}
