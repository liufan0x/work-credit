/**
 * 
 */
package com.anjbo.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Kevin Chang
 * 
 */
public class CheckUtil {
	public static boolean isWorldUnionEnquiry = true;
	public static Pattern mobilePattern = Pattern
			.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9])|(17[0-9]))\\d{8}$");
	// 要求6个长度以上20以下，且只能包含字母,数字和下划线
	public static Pattern pwdPattern = Pattern.compile("^[0-9a-zA-Z_]{6,20}$");

	/**
	 * 手机号码
	 * 
	 * @param mobile
	 * @return
	 */
	public static boolean checkMobile(String mobile) {
		if (StringUtils.isBlank(mobile)) {
			return false;
		}
		Matcher m = mobilePattern.matcher(mobile);
		return m.matches();
	}

	public static boolean checkPwd(String pwd) {
		if (StringUtils.isBlank(pwd)) {
			return false;
		}
		Matcher m = pwdPattern.matcher(pwd);
		return m.matches();
	}

	/**
	 * 判断是否是邮箱
	 * 
	 * @author Jerry
	 * @version v1.0 2013-9-24 下午06:17:09
	 * @param email
	 *            邮箱
	 * @return 判断结果
	 */
	public static boolean isEmail(String email) {
		if (StringUtils.isBlank(email)) {
			return false;
		}
		String regex = "^([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})$";
		return email.matches(regex);
	}

	public static void main(String[] args) {
		boolean f = CheckUtil.isEmail("15994795620@qq.com.cn");
		System.out.println(f);
		Matcher m = mobilePattern.matcher("18320900526");
		System.out.println( m.matches());
	}
}
