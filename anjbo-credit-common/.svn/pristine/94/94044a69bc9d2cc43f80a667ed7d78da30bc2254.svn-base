/*
 *Project: anjbo-credit-common
 *File: com.anjbo.utils.FileUtil.java  <2017年10月20日>
 ****************************************************************
 * 版权所有@2017 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.utils;

import java.net.URL;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * 文件处理
 * @Author KangLG 
 * @Date 2017年10月20日 下午1:42:20
 * @version 1.0
 */
public class FileUtil {
	
	/**
	 * 获取文件路径
	 * @Author KangLG<2017年10月20日>
	 * @param resource 文件相对路径(classes)
	 * @return
	 */
	public static String getFilePath(String resource) {
		if (StringUtils.isEmpty(resource)) {
			return null;
		}
		URL url = FileUtil.class.getClassLoader().getResource(resource);
		if(null == url)
			return null;
		
		String pathClass = url.toString().replace("%20", " ").replace("file:", "");
    	if(pathClass.indexOf(":")==2){
    		pathClass = pathClass.substring(1);
    	}
    	Logger.getRootLogger().debug("FileHelper.getFilePath: "+ pathClass);
				
		return pathClass;
	}

}
