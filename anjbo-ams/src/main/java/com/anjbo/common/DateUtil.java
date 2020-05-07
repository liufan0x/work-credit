/**
 * 时间操作辅助类
 * @author Jerry
 * @version v1.0 DateUtil.java 2013-9-25 下午05:47:33
 * 
 */
package com.anjbo.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.anjbo.utils.StringUtil;

/**
 * DateUtil 时间操作公共类</br> 如果要添加新的格式化格式，请先添加常量 :)
 * 
 * @author Jerry
 * @version v1.0 DateUtil.java 2013-9-25 下午05:47:33
 */
public class DateUtil {
	/**
	 * 格式：yyyy-MM-dd HH:mm:ss
	 */
	public static final String FMT_TYPE1 = "yyyy-MM-dd HH:mm:ss";
	/**
	 * 格式：yyyy-MM-dd
	 */
	public static final String FMT_TYPE2 = "yyyy-MM-dd";
	/**
	 * 格式：HH:mm:ss
	 */
	public static final String FMT_TYPE3 = "HH:mm:ss";
	/**
	 * 格式：yyyyMMddHHmmss
	 */
	public static final String FMT_TYPE4 = "yyyyMMddHHmmss";
	/**
	 * 格式：yyyyMMdd
	 */
	public static final String FMT_TYPE5 = "yyyyMMdd";
	/**
	 * 格式：HHmmss
	 */
	public static final String FMT_TYPE6 = "HHmmss";
	/**
	 * 格式：HHmmssSSS
	 */
	public static final String FMT_TYPE7 = "HHmmssSSS";
	/**
	 * 格式：yyyyMMddHHmmssSSS
	 */
	public static final String FMT_TYPE8 = "yyyyMMddHHmmssSSS";
	/**
	 * 格式：HH:mm
	 */
	public static final String FMT_TYPE9 = "HH:mm";
	/**
	 * 格式：yyyyMM
	 */
	public static final String FMT_TYPE10 = "yyyyMM";
	/**
	 * 格式：yyyy-MM-dd HH:mm
	 */
	public static final String FMT_TYPE11 = "yyyy-MM-dd HH:mm";
	/**
	 * 格式：MM月dd日
	 */
	public static final String FMT_TYPE12 = "MM月dd日";
	/**
	 * 格式：yyyy
	 */
	public static final String FMT_TYPE13 = "yyyy";
	
	/**
	 * 格式：yyyy-MM
	 */
	public static final String FMT_TYPE14 = "yyyy-MM";

	/**
	 * 返回指定格式化的日期<br>
	 * 如果 date为null，返回当前时间<br>
	 * 如果 fmt为null，返回 yyyy-MM-dd HH:mm:ss格式时间<br>
	 * 
	 * @author Jerry
	 * @version v1.0 2013-9-25 下午06:11:18
	 * @param date
	 *            时间
	 * @param fmt
	 *            格式化
	 * @return 指定格式化的日期
	 */
	public static String getDateByFmt(Date date, String fmt) {
		if (StringUtil.isBlank(fmt)) {
			fmt = FMT_TYPE1;
		}
		SimpleDateFormat format = new SimpleDateFormat(fmt);
		if (date == null) {
			return format.format(new Date());
		}
		return format.format(date);
	}

	/**
	 * 获取 yyyyMMddHHmmss 格式时间<br>
	 * 如果date为null，返回当前时间
	 * 
	 * @author Jerry
	 * @version v1.0 2013-9-25 下午05:56:57
	 * @param date
	 *            时间
	 * @return 格式化后的时间字符串
	 */
	public static String getNowyyyyMMddHHmmss(Date date) {
		SimpleDateFormat fmt = new SimpleDateFormat(FMT_TYPE4);
		if (date == null) {
			return fmt.format(new Date());
		}
		return fmt.format(date);
	}

	/**
	 * 获取 yyyyMMdd 格式时间<br>
	 * 如果date为null，返回当前时间
	 * 
	 * @author Jerry
	 * @version v1.0 2013-9-25 下午05:57:25
	 * @param date
	 *            时间
	 * @return 格式化后的时间字符串
	 */
	public static String getNowyyyyMMdd(Date date) {
		SimpleDateFormat fmt = new SimpleDateFormat(FMT_TYPE5);
		if (date == null) {
			return fmt.format(new Date());
		}
		return fmt.format(date);
	}

	/**
	 * 获取 HHmmss 格式时间 <br>
	 * 如果date为null，返回当前时间
	 * 
	 * @author Jerry
	 * @version v1.0 2013-9-25 下午05:59:21
	 * @param date
	 *            时间
	 * @return 格式化后的时间字符串
	 */
	public static String getNowHHmmss(Date date) {
		SimpleDateFormat fmt = new SimpleDateFormat(FMT_TYPE6);
		if (date == null) {
			return fmt.format(new Date());
		}
		return fmt.format(date);
	}

	/**
	 * 获取 HHmmssSSS 格式时间<br>
	 * 如果date为null，返回当前时间
	 * 
	 * @author Jerry
	 * @version v1.0 2013-9-25 下午05:59:25
	 * @param date
	 *            时间
	 * @return 格式化后的时间字符串
	 */
	public static String getNowHHmmssSSS(Date date) {
		SimpleDateFormat fmt = new SimpleDateFormat(FMT_TYPE7);
		if (date == null) {
			return fmt.format(new Date());
		}
		return fmt.format(date);
	}

	/**
	 * 获取 yyyyMMddHHmmssSSS 格式时间<br>
	 * 如果date为null，返回当前时间
	 * 
	 * @author Jerry
	 * @version v1.0 2013-9-25 下午05:59:33
	 * @param date
	 *            时间
	 * @return 格式化后的时间字符串
	 */
	public static String getNowyyyyMMddHHmmssSSS(Date date) {
		SimpleDateFormat fmt = new SimpleDateFormat(FMT_TYPE8);
		if (date == null) {
			return fmt.format(new Date());
		}
		return fmt.format(date);
	}

	/**
	 * 获取 yyyy-MM-dd HH:mm:ss 格式时间<br>
	 * 如果date为null，返回当前时间
	 * 
	 * @author Jerry
	 * @version v1.0 2013-9-25 下午05:59:36
	 * @param date
	 *            时间
	 * @return 格式化后的时间字符串
	 */
	public static String getSimpleDate(Date date) {
		SimpleDateFormat fmt = new SimpleDateFormat(FMT_TYPE1);
		if (date == null) {
			return fmt.format(new Date());
		}
		return fmt.format(date);
	}

	/**
	 * 获取 yyyy-MM-dd HH:mm:ss 格式时间<br>
	 * 如果date为null，返回当前时间
	 * 
	 * @author Jamin
	 * @version v1.0 2013-10-11 下午05:59:36
	 * @param date
	 *            时间
	 * @return 格式化后的时间字符串
	 */
	public static Date getStartTime() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return c.getTime();
	}

	/**
	 * 获取当前时间 N天前后 的日期</br> 如：-1=当前日期 一天前 的日期，1=当前日期 一天后 的日期
	 * 
	 * @author Jerry
	 * @version v1.0 2013-10-14 下午02:47:39
	 * @param day
	 *            天数
	 * @param fmt_type
	 *            格式化类型
	 * @return 格式化后的日期
	 */
	public static String getDayBeforeDate(int day, String fmt_type) {
		Calendar rightNow = Calendar.getInstance();
		rightNow.add(Calendar.DAY_OF_MONTH, day);
		return getDateByFmt(rightNow.getTime(), fmt_type);
	}

	/**
	 * 获取当前时间 N个月前后 日期，n可为正负整数</br> 如：-1=当前日期 一月前 的日期，1=当前日期 一月后 的日期
	 * 
	 * @author Jerry
	 * @version v1.0 2013-10-14 下午02:44:03
	 * @param month
	 *            月数
	 * @param fmt_type
	 *            格式化类型
	 * @return 按照参数 fmt_type 格式化后的日期
	 */
	public static String getMonthBeforeDate(int month, String fmt_type) {
		Calendar rightNow = Calendar.getInstance();
		rightNow.add(Calendar.MONTH, month);
		return getDateByFmt(rightNow.getTime(), fmt_type);
	}

	/**
	 * 将字符串的转换成date类型</br>
	 * 
	 * @author lifh
	 * @version v1.0 2013-11-28 下午10:44:03
	 * @param date
	 *            日期
	 * @param fmt_type
	 *            格式化类型
	 * @return 按照参数 fmt_type 格式化后的日期
	 */
	public static Date parse(String date, String fmt_type) {
		SimpleDateFormat fmt = new SimpleDateFormat(fmt_type);
		try {
			if (date != null) {
				return fmt.parse(date);
			}
		} catch (ParseException e) {
		}
		return new Date();
	}

	/**
	 * 计算两个日期之间相差年
	 * 
	 * @param sdate
	 * @param edate
	 * @return 相差天数
	 * @throws ParseException
	 */
	@SuppressWarnings("deprecation")
	public static int betYear(Date sdate, Date edate) {
		if (edate.getYear() > sdate.getYear()) {
			return edate.getYear() - sdate.getYear();
		} else {
			return 0;
		}
	}

	/**
	 * 计算两个日期之间相差的天数
	 * 
	 * @param sdate
	 * @param edate
	 * @return 相差天数
	 * @throws ParseException
	 */
	public static int betDays(Date sdate, Date edate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdate = sdf.parse(sdf.format(sdate));
		edate = sdf.parse(sdf.format(edate));

		Calendar cal = Calendar.getInstance();
		cal.setTime(sdate);

		long time1 = cal.getTimeInMillis();
		cal.setTime(edate);

		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 计算两个日期之间相差的分钟
	 * 
	 * @param sdate
	 * @param edate
	 * @return 相差分钟
	 * @throws ParseException
	 */
	public static int betweenMinutes(Date sdate, Date edate) {
		long sdateMillis = sdate.getTime();
		long edateMillis = edate.getTime();
		long bettweenMillis = edateMillis - sdateMillis;
		int minutes = (int) bettweenMillis / 1000 / 60;
		return minutes;
	}

	public static int getMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int month = cal.get(Calendar.MONTH) + 1;
		return month;
	}

	public static int getYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int year = cal.get(Calendar.YEAR);
		return year;
	}
	
	public static int getDay(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int day = cal.get(Calendar.DATE);
		return day;
	}
	
	/**
	 * 获取指定时间的下一个小时
	 * @param date
	 * @param hours传负数可查前一个小时
	 * @return
	 */
	public static Date nextHour(Date date,int hours){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR, hours);
		return cal.getTime();
	}
	public static String getDayBeforeDate(int day,String date,String fmt_type){
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(parse(date, fmt_type));
		rightNow.add(Calendar.DAY_OF_MONTH, day);
		return getDateByFmt(rightNow.getTime(), fmt_type);
	}
    /**
     * 判断当前日期是星期几<br>
     * <br>
     * @param pTime 修要判断的时间<br>
     * @return dayForWeek 判断结果<br>
     * @Exception 发生异常<br>
     */
	 public static int dayForWeek(String pTime) {
		  SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		  Calendar c = Calendar.getInstance();
		  try {
			c.setTime(format.parse(pTime));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		  int dayForWeek = 0;
		  if(c.get(Calendar.DAY_OF_WEEK) == 1){
			  dayForWeek = 7;
		  }else{
			  dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		  }
		  return dayForWeek;
	 }
	 /**
	 * 获取当前时间后一天，并排除周六周天
	 * @param date
	 * @return
	 */
	public static String lastDate(String date){
		String last = DateUtil.getDayBeforeDate(1, date, DateUtil.FMT_TYPE2);
		int week = DateUtil.dayForWeek(last);
		if(week==6||week==7){
			return lastDate(last);
		}
		return last;
	}
	public static String weekName(int week){
		String weekNames[] = {"星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日" };
		try {
			return weekNames[week-1];
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public synchronized static String getDateString() throws InterruptedException{
		Thread.sleep(1);
		return "k" + new SimpleDateFormat("yyMMddHHmmssSSS").format(new Date());
	}

}
