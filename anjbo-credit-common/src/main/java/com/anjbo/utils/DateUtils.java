package com.anjbo.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/**
 * DateUtil 时间操作公共类</br> 如果要添加新的格式化格式，请先添加常量 :)
 * 因为Date类的时间操作都不正确，那么我们使用Calendar进行时间操作，有个注意点是月份从0开始！
 * @Title DateUtil
 * @Description 
 * @author ch
 * @Date 2016年3月11日 下午3:08:39
 */
public class DateUtils {
	/**
	 * 格式：yyyy-MM-dd HH:mm:ss
	 */
	public static final String FMT_TYPE1 = "yyyy-MM-dd HH:mm:ss";

	// ----------------时间转化start---------------------
	// 当然还有date和long。不过date已经实现了，就不写在这了
	public static String dateToString(Date date, String fmt) {
		if (StringUtils.isBlank(fmt)) {
			fmt = FMT_TYPE1;
		}
		SimpleDateFormat format = new SimpleDateFormat(fmt);
		if (date == null) {
			return null;
		}
		return format.format(date);
	}

	public static Date StringToDate(String date, String fmt) {
		if (StringUtils.isBlank(fmt)) {
			fmt = FMT_TYPE1;
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(fmt);
		if (date == null) {
			return null;
		}
		try {
			return simpleDateFormat.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	// ----------------时间转化end---------------------

	// ----------------时间比较start---------------------
	/**
	 * 获取时间值的部分值
	 * 原理：可以根据Calendar的枚举，自定义返回时间的详细字段。例如Calendar.MONTH，只获取月份
	 * @param date
	 * @param CalendarEnum
	 * @return
	 */
	public static int getDatePart(Date date, int CalendarEnum) {
		Calendar instance = Calendar.getInstance();
		instance.setTime(date);
		if (CalendarEnum == Calendar.MONTH) {
			return instance.get(CalendarEnum) + 1;// 月份从0开始
		} else if (CalendarEnum == Calendar.DAY_OF_WEEK) {// 周。从周六 0 开始.
			int i = instance.get(CalendarEnum);
			int x = i < 2 ? i + 6 : i - 1;
			return x;
		} else {
			return instance.get(CalendarEnum);
		}
	}

	/**
	 * 获取某个日期的偏移量日期。
	 * 原理：根据指定的时间值的部分值，进行偏移量计算。返回计算后的时间
	 * @param date
	 * @param CalendarEnum	偏移区域
	 * @param offset    	偏移量
	 * @return
	 */
	public static Date getDatePartOffset(Date date, int CalendarEnum, int offset) {
		Calendar instance = Calendar.getInstance();
		instance.setTime(date);
		instance.add(CalendarEnum, offset);
		return instance.getTime();
	}

	/**
	 * 原理：将比较的内容进行格式化，然后能通过Calendar进行比较的进行比较，不能的就计算获得
	 * @param date1   min
	 * @param date2   max
	 * @param CalendarEnum
	 * @return
	 */
	public static long betDatePart(Date date1, Date date2, int CalendarEnum) {
		String betDateFormat = getBetDateFormat(CalendarEnum);
		Date date3 = StringToDate(dateToString(date1, betDateFormat),
				betDateFormat);
		Date date4 = StringToDate(dateToString(date2, betDateFormat),
				betDateFormat);
		Calendar instance = Calendar.getInstance();
		Calendar instance2 = Calendar.getInstance();
		instance.setTime(date3);
		long timeInMillis1 = instance.getTimeInMillis();
		instance2.setTime(date4);
		long timeInMillis2 = instance2.getTimeInMillis();
		long millis = timeInMillis2 - timeInMillis1;

		switch (CalendarEnum) {
		case Calendar.YEAR:
			return instance2.get(Calendar.YEAR) - instance.get(Calendar.YEAR);
		case Calendar.MONTH:
			return (instance2.get(Calendar.YEAR) - instance.get(Calendar.YEAR))
					* 12
					+ (instance2.get(Calendar.MONTH) - instance
							.get(Calendar.MONTH));
		case Calendar.DATE:
			return millis / 1000 / 60 / 60 / 24;
		case Calendar.HOUR:
			return millis / 1000 / 60 / 60;
		case Calendar.MINUTE:
			return millis / 1000 / 60;
		case Calendar.SECOND:
			return millis / 1000;
		default:
			break;
		}

		return 0;
	}

	/**
	 * 比较天数，那么就去掉分钟，秒 。进行比较（生活上的逻辑）
	 * @param CalendarEnum
	 * @return
	 */
	private static String getBetDateFormat(int CalendarEnum) {
		switch (CalendarEnum) {
		case Calendar.YEAR:
			return "yyyy";
		case Calendar.MONTH:
			return "yyyy-MM";
		case Calendar.DATE:
			return "yyyy-MM-dd";
		case Calendar.HOUR:
			return "yyyy-MM-dd HH";
		case Calendar.MINUTE:
			return "yyyy-MM-dd HH:mm";
		case Calendar.SECOND:
			return "yyyy-MM-dd HH:mm:ss";
		default:
			break;
		}
		return "yyyy-MM-dd HH:mm:ss";
	}

	// ----------------时间比较end---------------------

	/**
	 * 当日秒   当日分   当日时  当日底(时分秒)  周低日 ,月底日,年底日
	这里只获取  最后一天的同一个钟点。如果要最后的钟点，再使用当日底的获取
	 * @param date
	 * @param CalendarEnum
	 * @return
	 */
	public static Date getLastDate(Date date, int CalendarEnum) {
		Calendar instance = Calendar.getInstance();
		instance.setTime(date);

		switch (CalendarEnum) {
		case Calendar.YEAR:
			instance.set(Calendar.MONTH, 0);
			instance.set(Calendar.DATE, 1);
			instance.add(CalendarEnum, 1);
			instance.add(Calendar.DATE, -1);
			return instance.getTime();
		case Calendar.MONTH:
			instance.set(Calendar.DATE, 1);
			instance.add(CalendarEnum, 1);
			instance.add(Calendar.DATE, -1);
			return instance.getTime();
		case Calendar.DATE:
			instance.set(Calendar.HOUR_OF_DAY, 23);
			instance.set(Calendar.MINUTE, 59);
			instance.set(Calendar.SECOND, 59);
			return instance.getTime();
		case Calendar.HOUR:
			instance.set(CalendarEnum, 59);
			return instance.getTime();
		case Calendar.MINUTE:
			instance.set(CalendarEnum, 59);
			return instance.getTime();
		case Calendar.SECOND:
			instance.set(CalendarEnum, 59);
			return instance.getTime();
		default:
			break;
		}
		return new Date();
	}

	public static Date getFirstDate(Date date, int CalendarEnum) {
		Calendar instance = Calendar.getInstance();
		instance.setTime(date);

		switch (CalendarEnum) {
		case Calendar.YEAR:
			instance.set(Calendar.MONTH, 0);
			instance.set(Calendar.DATE, 1);
			return instance.getTime();
		case Calendar.MONTH:
			instance.set(Calendar.DATE, 1);
			return instance.getTime();
		case Calendar.DATE:
			instance.set(Calendar.HOUR_OF_DAY, 0);
			instance.set(Calendar.MINUTE, 0);
			instance.set(Calendar.SECOND, 0);
			return instance.getTime();
		case Calendar.HOUR:
			instance.set(CalendarEnum, 0);
			return instance.getTime();
		case Calendar.MINUTE:
			instance.set(CalendarEnum, 0);
			return instance.getTime();
		case Calendar.SECOND:
			instance.set(CalendarEnum, 0);
			return instance.getTime();
		default:
			break;
		}
		return new Date();
	}

	/**
	 * 中文的方式显示周几
	 * @param week
	 * @return
	 */
	public static String weekName(int week) {
		String weekNames[] = { "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日" };
		try {
			return weekNames[week];
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 判断年份是否是闰年
	 * true 闰年
	 * @return
	 */
	public static boolean checkYear(long year) {
		return year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
	}

	public void test1() {
		System.out.println(DateUtils.getFirstDate(new Date(), Calendar.DATE));
	}
	
	//根据天数计算日期
	 public static Date addDate(Date d,long day){
		  long time = d.getTime(); 
		  day = day*24*60*60*1000; 
		  time+=day; 
		  return new Date(time);
 }
}
