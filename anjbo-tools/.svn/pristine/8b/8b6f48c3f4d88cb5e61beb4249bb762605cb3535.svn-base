package com.anjbo.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 
 * @author Kevin Chang
 * 
 */
public class NumberUtil {
	public static double formatDecimal(double d) {
		return formatDecimal(d, 2);
	}

	public static double formatDecimal(double d, int scale) {
		BigDecimal bg = new BigDecimal(d);
		double fmt = bg.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();

		return fmt;
	}

	public static String formatDecimalToStr(double d) {
		return formatDecimalToStr(d, 2);
	}

	public static String formatDecimalToStr(double d, int scale) {
		DecimalFormat df = (DecimalFormat) DecimalFormat.getInstance();
		df.setMaximumFractionDigits(scale);
		df.setGroupingUsed(false);
		String str = df.format(d);
		return str;
	}

	/**
	 * 除法运算
	 * @param v1 除数
	 * @param v2 被除数
	 * @param scale
	 * @return
	 */
	public static double divide(Double v1, Double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("精度指定错误,请指定一个>=0的精度");
		}
		BigDecimal bg1 = new BigDecimal(v1);
		BigDecimal bg2 = new BigDecimal(v2);
		return bg1.divide(bg2, scale, scale).doubleValue();
	}

	public static double multiply(Double v1, Double v2) {
		BigDecimal bg1 = new BigDecimal(v1);
		BigDecimal bg2 = new BigDecimal(v2);
		return bg1.multiply(bg2).doubleValue();
	}
}
