package com.anjbo.utils.huarong;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * 字符串操作工具类
 * @author chenzm
 * @since 2017年8月22日
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

	/**
	 * 序列化字符串
	 * @param string 字符串
	 * @return 序列化后的字节数组
	 */
	public static byte[] serialize(final String string, final Charset charset) {
		return (string == null ? null : string.getBytes(charset));
	}

	/**
	 * 反序列化字符串
	 * @param bytes 序列化字节数组
	 * @return 反序列化后的字符串
	 */
	public static String deserialize(final byte[] bytes, final Charset charset) {
		return (bytes == null ? null : new String(bytes, charset));
	}

	/**
	 * 判断str不为null或不为""
	 * @param str
	 * @return
	 */
	public static boolean notEmpty(String str) {
		return !isEmpty(str);
	}

	/**
	 * 判断str不为null或不为""或trim之后不为""
	 * @param str
	 * @return
	 */
	public static boolean notBlank(String str) {
		return !isBlank(str);
	}

	/**
	 * 使用StringBuilder进行拼接
	 * @param strings
	 * @return
	 */
	public static StringBuilder concatToSB(String... strings) {
		StringBuilder builder = new StringBuilder();
		if (strings != null) {
			for (String str : strings) {
				builder.append(str);
			}
		}
		return builder;
	}

	/**
	 * 去掉为NULL的情况
	 * @param str
	 * @return
	 */
	public static String safeValue(String str) {
		if (str == null) return "";
		return str;
	}

	/**
	 * object to string
	 * @param r
	 * @return
	 */
	public static String objectToStr(Object obj) {
		if (obj == null) return "";
		return String.valueOf(obj);
	}


	/**
	 * 截短字符串，返回长度不大于maxLen的子串.
	 * 如果所给源字符串长度过大，则大于maxLen的后面部分用“…”替换
	 * @param str 源字符
	 * @param maxLen 截短后的最大长度(按字节计算，一个汉字或全角标点长度2，一个英文、数字或半角标点长度1)
	 * @return 截短后的字符串
	 */
	public static String getLimitLengthString(String str, int maxLen) {
		return getLimitLengthString(str, maxLen, Charset.defaultCharset().name());
	}

	/**
	 * 截短字符串，返回长度不大于maxLen的子串.
	 * 如果所给源字符串长度过大，则大于maxLen的后面部分用symbol替换。
	 * 如果为空(null)则返回""。
	 * @param str 源字符
	 * @param maxLen 截短后的最大长度(按字节计算，一个汉字或全角标点长度2，一个英文、数字或半角标点长度1)
	 * @param charsetName 字符集
	 * @return 截短后的字符串
	 */
	public static String getLimitLengthString(String str, int maxLen, String charsetName) {
		if (str == null) {
			return "";
		}
		try {
			byte b[] = str.getBytes(charsetName);
			if (b.length <= maxLen) {
				return str;
			}

			// 返回字符串的长度应小于或等于此长度
			int len = maxLen;

			// 使用二分法查找算法
			int index = 0;
			// 记录第一个元素
			int lower = 0;
			// 记录最后一个元素
			int higher = str.length() - 1;
			while (lower <= higher) {
				// 记录中间元素，用两边之和除2
				index = (lower + higher) / 2;
				int tmpLen = str.substring(0, index).getBytes(charsetName).length;
				if (tmpLen == len) {
					// 如果得到的与要查找的相等，则break退出
					break;
				} else if (tmpLen < len) {
					// 如果得到的要小于查找的，就用下标加1
					lower = index + 1;
				} else {
					// 如果得到的要大于查找的，就用下标减1
					higher = index - 1;
				}
			}
			if (lower > higher) {
				index = higher;
			}

			// 调用String构造方法以避免substring导致的内存泄露
			return new String(str.substring(0, index));
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException(e);
		}
	}

	/**
	 * 去掉字符串两端的全角空格和半角空格.
	 * 如果为空(null)则返回""
	 * @param str 字符串
	 * @return 无左右空格的字符串
	 */
	public static String trim(String str) {
		if (str == null || str.equals("")) {
			return "";
		} else {
			return str.replaceAll("^[　 ]+|[　 ]+$", "");
		}
	}

	/**
	 * 去除字符串首尾空格以及中间的所有空格，包括空白符、换行符、段落符、全角空格等
	 * 如果为空(null)则返回""
	 * @param str 源字符
	 * @return 不包含空格的字符串
	 * @see java.lang.Character#isWhitespace(char)
	 */
	public static String trimAll(String str) {
		if (str == null) {
			return "";
		}

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			// 过滤掉各种空白符
			if (!Character.isWhitespace(ch)) {
				sb.append(ch);
			}
		}

		return sb.toString();
	}

	/**
	 * @Description 是否有文本
	 * @param str 要验证的字符串
	 * @return
	 * @see 需要参考的类或方法
	 */
	public static boolean hasText(String str) {
		return hasText(((CharSequence) (str)));
	}

	/**
	 * @Description 是否有文本
	 * @param str 要验证的字符串
	 * @return
	 * @see 需要参考的类或方法
	 */
	public static boolean hasText(CharSequence str) {
		if (!hasLength(str)) return false;
		int strLen = str.length();
		for (int i = 0; i < strLen; i++)
			if (!Character.isWhitespace(str.charAt(i))) return true;

		return false;
	}

	/**
	 * @Description 是否有字符串长度
	 * @param str 要验证的字符
	 * @return
	 * @see 需要参考的类或方法
	 */
	public static boolean hasLength(CharSequence str) {
		return str != null && str.length() > 0;
	}

	/**
	 * 本方法的主要功能是在一个字符串的每个大写字母前面加一个_ ，字符串的首字母除外
	 * 然后把整个串以小写输出
	 * 例如：cusId-->cus_id
	 * @param str 传入的字符串
	 * @return 处理后的字符串
	 */
	public static String AddUnderlineByUppercase(String str) {
		// int count=0;
		String rv = "";

		if (null == str && str.trim().length() == 0) {
			return str;
		}

		StringBuffer tmpStr = new StringBuffer("");
		for (int i = 0; i < str.length(); i++) {
			if (Character.isUpperCase(str.charAt(i))) {
				tmpStr.append("_").append(Character.valueOf(str.charAt(i)));
				// count ++;
			} else {
				tmpStr.append(Character.valueOf(str.charAt(i)));
			}
		}

		rv = tmpStr.toString().toLowerCase();
		if (rv.substring(0, 1).equals("_")) {
			rv = rv.substring(1);
		}
		return rv;
	}
	
	/**
	 * Object转double类型
	 * @Description 一句话描述方法用法
	 * @param value
	 * @return
	 * @see 需要参考的类或方法
	 */
	public static double stringToDouble(Object value) {
		double vv = 0;
		String v = checkNull(value);
		if ("".equals(v)) {
			vv = 0;
		} else {
			try {
				vv = Double.parseDouble(v.toString());
			} catch (Exception e) {
				vv = 0;
			}
		}
		return vv;
	}
	
	/**
	 * 判断该值是否为null
	 * @param value
	 * @return
	 */
	public static String checkNull(Object value) {

		if (value == null) {
			return "";
		}
		return value.toString().trim();
	}

	/**
	 * 提供精确的小数位四舍五入处理。
	 * @param v 需要四舍五入的数字
	 * @param scale 小数点后保留几位
	 * @return 四舍五入后的结果
	 */
	public static double round(double v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("参数scale不能小于零!");
		}
		BigDecimal b = new BigDecimal(v);
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
}
