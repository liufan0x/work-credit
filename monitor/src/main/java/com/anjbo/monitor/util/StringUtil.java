package com.anjbo.monitor.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 
 * String 常用操作封装类
 * 
 * @author Jerry
 * @version v1.0 StringUtils.java 2013-9-24 下午05:10:23
 */
@SuppressWarnings("restriction")
public class StringUtil extends org.apache.commons.lang3.StringUtils {
	private final static String patternArray = "s0=\"(.*?)\";\nDWREngine";
	private final static String patternObject = "\\=\"(.*?)\";s0\\['(.*?)'\\]\\=";
	private final static String patternResult = "<input type=\"hidden\" name=\"bookingCode\" value=\"(.*?)\" id=\"bookingCode\">";
	private final static String PATTERNSPECIAL = "[`~!@#$%^&*+=|{}':;',\\[\\].<>/?~！@#￥%……&*——+|{}【】‘；：”“’。，、？]";
	private static Pattern pattern = null;
	private static Matcher matcher = null;

	/**
	 * 转换AMS返回参数 为 json格式 <br>
	 * 
	 * @author Jerry
	 * @version v1.0 2013-9-26 上午10:51:49
	 * @param result
	 *            code=0&uid=106859684
	 * @return {"code":"0","uid":"106859684"}
	 */
	public static String parseAmsResultToJson(String result) {
		if (isBlank(result)) {
			return null;
		}
		String[] arrays = result.split("&");
		int length = arrays.length;
		String subStr = EMPTY;
		StringBuilder newStr = new StringBuilder();
		newStr.append("{");
		for (int i = 0; i < length; i++) {
			subStr = arrays[i];
			newStr.append("\"");
			int index = subStr.indexOf("=");
			newStr.append(subStr.substring(0, index));
			newStr.append("\":\"");
			newStr.append(subStr.substring(index + 1, subStr.length()));
			newStr.append("\"");
			if (i != length - 1) {
				newStr.append(",");
			}
		}
		newStr.append("}");
		return newStr.toString();
	}

	/**
	 * 根据请求url返回截取的 项目名称 <br/>
	 * such as : http://www.uuwldh.com/w/ph4i.c will return ph4<br/>
	 * 如果为空，返回 null
	 * 
	 * @param url
	 * @return 提取到的项目名称
	 */
	public static String getPhProjName(String url) {
		try {
			if (isNotBlank(url)) {
				int index1 = url.lastIndexOf("/");
				int index2 = url.lastIndexOf(".c");
				return url.substring(index1 + 1, index2 - 1);
			}
		} catch (Exception e) {
		}
		return "";
	}

	/**
	 * 处理手机号，隐藏中间四位
	 * 
	 * @author Jerry
	 * @version v1.0 2013-10-30 上午11:30:02
	 * @param phone
	 *            手机号码
	 * @return 处理后的手机号码
	 */
	public static String doPhoneNum(String phone) {
		return phone.subSequence(0, 3) + "****" + phone.substring(7);
	}

	/**
	 * 替换HTML标签
	 * 
	 * @Title: replaceHTML
	 * @param @param str
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public static String replaceHTML(String str) {
		if (str == null) {
			return str;
		}
		String s;
		try {
			s = str.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
		} catch (Exception e) {
			// e.printStackTrace();
			return str;
		}
		return s;
	}

	/**
	 * 
	 * @param str
	 * @return
	 */
	public static String encodeUtf8Str(String str) {
		String encodeStr = "";
		if (org.apache.commons.lang3.StringUtils.isEmpty(str)) {
			return encodeStr;
		}
		try {
			encodeStr = URLEncoder.encode(str, "UTF-8");

		} catch (UnsupportedEncodingException e) {
		}
		return encodeStr;
	}

	/**
	 * 
	 * @param str
	 * @return
	 */
	public static String decodeUtf8Str(String str) {
		String decodeStr = "";
		if (org.apache.commons.lang3.StringUtils.isEmpty(str)) {
			return decodeStr;
		}
		try {
			decodeStr = URLDecoder.decode(str, "UTF-8");

		} catch (UnsupportedEncodingException e) {
		}
		return decodeStr;
	}

	// 将 s 进行 BASE64 编码
	public static String getBASE64(String s, String charset) {
		String temp = "";
		if (StringUtils.isEmpty(s)) {
			return temp;
		}
		try {
			if (StringUtils.isEmpty(charset)) {
				charset = "UTF-8";
			}
			temp = (new BASE64Encoder()).encode(s.getBytes(charset));
		} catch (UnsupportedEncodingException e) {
		}
		return temp;
	}

	// 将 BASE64 编码的字符串 s 进行解码
	public static String getFromBASE64(String s, String charset) {
		String temp = "";
		if (StringUtils.isEmpty(s)) {
			return temp;
		}
		BASE64Decoder decoder = new BASE64Decoder();
		if (StringUtils.isEmpty(charset)) {
			charset = "UTF-8";
		}
		try {
			byte[] b = decoder.decodeBuffer(s);
			temp = new String(b, charset);
		} catch (Exception e) {
		}
		return temp;
	}

	/**
	 * 生成随机字符串
	 * 
	 * @param length
	 * @return
	 */
	public static String getRandomString(int length) { // length表示生成字符串的长度
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	/**
	 * 正则取内容，提取中括号内容
	 * 
	 * @author Jamin
	 * @version v1.0 2015-05-28 上午11:30:02
	 * @param content
	 *            字符
	 * @return 处理后的手机号码
	 */
	public static String processLocationArray(String location) {
		if (StringUtils.isBlank(location))
			return location;
		pattern = Pattern.compile(patternArray);
		matcher = pattern.matcher(location);
		if (matcher.find()) {
			location = matcher.group(1);
		}
		return location;
	}

	public static String processLocationResult(String location) {
		if (StringUtils.isBlank(location))
			return location;
		pattern = Pattern.compile(patternResult);
		matcher = pattern.matcher(location);
		if (matcher.find()) {
			location = matcher.group(1);
		}
		return location;
	}

	public static String processLocation(String location, String patternReg) {
		if (StringUtils.isBlank(location))
			return location;
		pattern = Pattern.compile(patternReg);
		matcher = pattern.matcher(location);
		if (matcher.find()) {
			location = matcher.group(1);
		}
		return location;
	}

	public static String processLocationObject(String location) {
		if (StringUtils.isBlank(location))
			return location;
		pattern = Pattern.compile(patternObject);
		matcher = pattern.matcher(location);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			sb.append("\"").append(matcher.group(2)).append("\":");
			//sb.append(matcher.group(1)).append(",");
			sb.append("\"").append(matcher.group(1)).append("\",");
		}
		location = sb.toString();
		if(location.length()>0){
			location = location.substring(0, location.length() - 1);
		}
		location ="{"+location+"}";
		return location;
	}

	/**
	 * Java中\\u格式的unicode码转中文
	 * 
	 * @param unicodeStr
	 * @return
	 */
	public static String decode(String unicodeStr) {
		if (StringUtils.isBlank(unicodeStr))
			return unicodeStr;
		StringBuffer retBuf = new StringBuffer();
		int maxLoop = unicodeStr.length();
		for (int i = 0; i < maxLoop; i++) {
			if (unicodeStr.charAt(i) == '\\') {
				if ((i < maxLoop - 5)
						&& ((unicodeStr.charAt(i + 1) == 'u') || (unicodeStr
								.charAt(i + 1) == 'U')))
					try {
						retBuf.append((char) Integer.parseInt(
								unicodeStr.substring(i + 2, i + 6), 16));
						i += 5;
					} catch (NumberFormatException localNumberFormatException) {
						retBuf.append(unicodeStr.charAt(i));
					}
				else
					retBuf.append(unicodeStr.charAt(i));
			} else {
				retBuf.append(unicodeStr.charAt(i));
			}
		}
		String dayResule = retBuf.toString();
		dayResule = dayResule.replaceAll("\\\\", "");
		return dayResule;
	}

	public static void main(String[] args) {
		String str = "\u9884\u7EA6\u5FC5\u987B\u5728\u9884\u7EA6\u65F6\u95F4\u524D\u4E00\u5DE5\u4F5C\u65E5\u768417:00\u70B9\u4E4B\u524D\u5B8C\u6210\uFF01";
		System.out.println(StringEscapeUtils.unescapeJava(str));
	}

	public static boolean isChineseChar(String str) {
		boolean temp = false;
		Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
		Matcher m = p.matcher(str);
		if (m.find()) {
			temp = true;
		}
		return temp;
	}
	
	/**
	 * 是否包含特殊字符
	 * @return 包含返回true
	 */
	public static boolean hasSpecialStr(String str){
		return Pattern.compile(PATTERNSPECIAL).matcher(str).find();
	}
	
	public static Map<Integer,String> processLocationMap(String location, String patternReg) {
		Map<Integer,String> map = new LinkedHashMap<Integer, String>();
		if (StringUtils.isBlank(location))
			return map;
		pattern = Pattern.compile(patternReg);
		matcher = pattern.matcher(location);
		int i=0;
		while (matcher.find()) {
			map.put(i++, matcher.group(1));
		}
		return map;
	}
	public static String getUuid(){
		return UUID.randomUUID().toString().replace("-", "");
	}
	/**
	 * 去除字符串中的空格、回车、换行符、制表符
	 * @param str
	 * @return
	 */
	public static String replaceBlank(String str) {
		String dest = "";
		if (str!=null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}

	public static synchronized String getUid() {
		return java.lang.String.valueOf(new Date().getTime());
	}
}
