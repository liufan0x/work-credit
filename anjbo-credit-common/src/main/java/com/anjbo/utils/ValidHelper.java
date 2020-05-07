package com.anjbo.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ValidHelper {
	private static final Log log = LogFactory.getLog(ValidHelper.class);
	@SuppressWarnings("serial")
	private static List<String> lstPlatformAgency = new ArrayList<String>(){{
		add("ORG");     //机构后台
		add("FUND");     //资方后台
	}};
	
	
	public static boolean isNotEmptyByKeys(Map<String,Object> map,String... strs){
		for(String str:strs){
			if(map.get(str)==null){
				log.debug(str+"  =  null");
				return false;
			}else if("".equals(map.get(str).toString())){
				log.debug(str+"  =  \"\"");
				return false;
			}
		}
		return true;
	}
	
	public static boolean isNotEmpty(Object... objects){
		if(ArrayUtils.contains(objects, null)){
			log.debug("args not allow contains null");
			return false;
		}else if(ArrayUtils.contains(objects, "")){
			log.debug("args not allow contains \"\"");
			return false;
		}
		return true;
	}
	
	/**
	 * 当前系统是否为机构平台
	 * @Author KangLG<2017年11月7日>
	 * @param platformCode 平台编码
	 * @return true机构平台false内部平台
	 */
	public static boolean isPlatformAgency(String platformCode){
		if(null==platformCode || StringUtils.isEmpty(platformCode)){
			return false;
		}
		return lstPlatformAgency.contains(platformCode);
	}
}
