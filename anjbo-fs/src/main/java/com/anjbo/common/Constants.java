package com.anjbo.common;

import com.anjbo.utils.ConfigUtil;

public class Constants {
	
	/**品牌标示*/
	//public static String UPLOAD_URL = "fs.anjbo.com";
	public static String UPLOAD_URL = ConfigUtil.getStringValue("FS_URL");
	
}
