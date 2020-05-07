/*
 *Project: anjbo-credit-common
 *File: com.anjbo.utils.regex.FieldRegex.java  <2017年11月7日>
 ****************************************************************
 * 版权所有@2017 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.utils.regex;

import org.apache.commons.lang3.StringUtils;

/**
 * @Author KangLG 
 * @Date 2017年11月7日 上午9:28:59
 * @version 1.0
 */
public class FieldRegex {
	/**手机号码*/
	public static String REGEX_Field_MobileNo = "(01|1)(\\d{10})"; 
	/**电话号码：AreaCode'-'tel*/
	public static String REGEX_Field_TPhoneNo = "((\\(\\d{3}\\))|(\\d{3}\\-))?(\\(0\\d{2,3}\\)|0\\d{2,3}-)?[1-9]\\d{6,7}"; 
	/**小灵通*/
	public static String REGEX_Field_PASNo = "0\\d{10,11}"; 
	
	/**邮箱*/
	public static String REGEX_Field_Email = "\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+"; 
		
	/**图片*/
	public static final String REGEX_FILE_PHOTO = "(jpg|jpeg|gif|png|bmp)"; 
	/**视频*/
	public static final String REGEX_FILE_VIDEO = "(flv|avi|mp4)"; 
	/**常用允许类型*/
	public static final String REGEX_FILE_ALLOWED = "(txt|xls|xlsx|doc|docs|pdf)"; 

	/**
	 * 电话号码(手机号、座机、小灵通)
	 * @Author KangLG<2017年11月7日>
	 * @param PhoneNo
	 * @return
	 */
	public static boolean isPhoneNo(String PhoneNo){
		if(isMobileNo(PhoneNo) || isTelePhoneNo(PhoneNo) || isPASNo(PhoneNo)){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 手机号码
	 * @Author KangLG<2017年11月7日>
	 * @param PhoneNo
	 * @return
	 */
	public static boolean isMobileNo(String PhoneNo){
		if(StringUtils.isNotEmpty(PhoneNo)){
			return PhoneNo.matches(REGEX_Field_MobileNo);
		}
		return false;
	}
	
	/**
	 * 座机号码
	 * @Author KangLG<2017年11月7日>
	 * @param PhoneNo
	 * @return
	 */
	public static boolean isTelePhoneNo(String PhoneNo){
		if(StringUtils.isNotEmpty(PhoneNo)){
			return PhoneNo.matches(REGEX_Field_TPhoneNo);
		}
		return false;
	}
	
	/**
	 * 小灵通号码
	 * @Author KangLG<2017年11月7日>
	 * @param PhoneNo
	 * @return
	 */
	public static boolean isPASNo(String PhoneNo){
		if(StringUtils.isNotEmpty(PhoneNo)){
			return PhoneNo.matches(REGEX_Field_PASNo);
		}
		return false;
	}
	
	/**
	 * 邮箱
	 * @Author KangLG<2017年11月7日>
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email){
		if(StringUtils.isNotEmpty(email)){
			return email.matches(REGEX_Field_Email);
		}
		return false;
	}
}
