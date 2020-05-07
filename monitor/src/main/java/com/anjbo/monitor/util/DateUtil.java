package com.anjbo.monitor.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil
{
  public static final String FMT_TYPE1 = "yyyy-MM-dd HH:mm:ss";
  public static final String FMT_TYPE2 = "yyyy-MM-dd";
  public static final String FMT_TYPE3 = "HH:mm:ss";
  public static final String FMT_TYPE4 = "yyyyMMddHHmmss";
  public static final String FMT_TYPE5 = "yyyyMMdd";
  public static final String FMT_TYPE6 = "HHmmss";
  public static final String FMT_TYPE7 = "HHmmssSSS";
  public static final String FMT_TYPE8 = "yyyyMMddHHmmssSSS";
  public static final String FMT_TYPE9 = "HH:mm";
  public static final String FMT_TYPE10 = "yyyyMM";
  public static final String FMT_TYPE11 = "yyyy-MM-dd HH:mm";
  public static final String FMT_TYPE12 = "MM月dd日";
  public static final String FMT_TYPE13 = "yyyy";

  public static String getDateByFmt(Date date, String fmt)
  {
    if (StringUtil.isBlank(fmt)) {
      fmt = "yyyy-MM-dd HH:mm:ss";
    }
    SimpleDateFormat format = new SimpleDateFormat(fmt);
    if (date == null) {
      return format.format(new Date());
    }
    return format.format(date);
  }

  public static String getNowyyyyMMddHHmmss(Date date)
  {
    SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmss");
    if (date == null) {
      return fmt.format(new Date());
    }
    return fmt.format(date);
  }

  public static String getNowyyyyMMdd(Date date)
  {
    SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
    if (date == null) {
      return fmt.format(new Date());
    }
    return fmt.format(date);
  }

  public static String getNowHHmmss(Date date)
  {
    SimpleDateFormat fmt = new SimpleDateFormat("HHmmss");
    if (date == null) {
      return fmt.format(new Date());
    }
    return fmt.format(date);
  }

  public static String getNowHHmmssSSS(Date date)
  {
    SimpleDateFormat fmt = new SimpleDateFormat("HHmmssSSS");
    if (date == null) {
      return fmt.format(new Date());
    }
    return fmt.format(date);
  }

  public static String getNowyyyyMMddHHmmssSSS(Date date)
  {
    SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    if (date == null) {
      return fmt.format(new Date());
    }
    return fmt.format(date);
  }

  public static String getSimpleDate(Date date)
  {
    SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    if (date == null) {
      return fmt.format(new Date());
    }
    return fmt.format(date);
  }

  public static Date getStartTime()
  {
    Calendar c = Calendar.getInstance();
    c.set(11, 0);
    c.set(12, 0);
    c.set(13, 0);
    return c.getTime();
  }

  public static String getDayBeforeDate(int day, String fmt_type)
  {
    Calendar rightNow = Calendar.getInstance();
    rightNow.add(5, day);
    return getDateByFmt(rightNow.getTime(), fmt_type);
  }

  public static String getMonthBeforeDate(int month, String fmt_type)
  {
    Calendar rightNow = Calendar.getInstance();
    rightNow.add(2, month);
    return getDateByFmt(rightNow.getTime(), fmt_type);
  }

  public static Date parse(String date, String fmt_type)
  {
    SimpleDateFormat fmt = new SimpleDateFormat(fmt_type);
    try {
      if (date != null)
        return fmt.parse(date);
    }
    catch (ParseException localParseException) {
    }
    return new Date();
  }

  public static int betYear(Date sdate, Date edate)
  {
    if (edate.getYear() > sdate.getYear()) {
      return edate.getYear() - sdate.getYear();
    }
    return 0;
  }

  public static int betDays(Date sdate, Date edate)
    throws ParseException
  {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    sdate = sdf.parse(sdf.format(sdate));
    edate = sdf.parse(sdf.format(edate));

    Calendar cal = Calendar.getInstance();
    cal.setTime(sdate);

    long time1 = cal.getTimeInMillis();
    cal.setTime(edate);

    long time2 = cal.getTimeInMillis();
    long between_days = (time2 - time1) / 86400000L;

    return Integer.parseInt(String.valueOf(between_days));
  }

  public static int betweenMinutes(Date sdate, Date edate)
  {
    long sdateMillis = sdate.getTime();
    long edateMillis = edate.getTime();
    long bettweenMillis = edateMillis - sdateMillis;

    int minutes = (int)(bettweenMillis / 1000L / 60L);
    return minutes;
  }

  public static int getMonth(Date date) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    int month = cal.get(2) + 1;
    return month;
  }

  public static int getYear(Date date) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    int year = cal.get(1);
    return year;
  }

  public static int getDay(Date date) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    int day = cal.get(5);
    return day;
  }

  public static Date nextHour(Date date, int hours)
  {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.add(10, hours);
    return cal.getTime();
  }
  public static String getDayBeforeDate(int day, String date, String fmt_type) {
    Calendar rightNow = Calendar.getInstance();
    rightNow.setTime(parse(date, fmt_type));
    rightNow.add(5, day);
    return getDateByFmt(rightNow.getTime(), fmt_type);
  }

  public static int dayForWeek(String pTime)
  {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    Calendar c = Calendar.getInstance();
    try {
      c.setTime(format.parse(pTime));
    } catch (ParseException e) {
      e.printStackTrace();
    }
    int dayForWeek = 0;
    if (c.get(7) == 1)
      dayForWeek = 7;
    else {
      dayForWeek = c.get(7) - 1;
    }
    return dayForWeek;
  }

  public static String lastDate(String date)
  {
    String last = getDayBeforeDate(1, date, "yyyy-MM-dd");
    int week = dayForWeek(last);
    if ((week == 6) || (week == 7)) {
      return lastDate(last);
    }
    return last;
  }
  public static String weekName(int week) {
    String[] weekNames = { "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日" };
    try {
      return weekNames[(week - 1)];
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public static synchronized String getDateString() throws InterruptedException {
    Thread.sleep(1L);
    return "k" + new SimpleDateFormat("yyMMddHHmmssSSS").format(new Date());
  }

  public static Date dateTodate(Date date, String h) throws Exception {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String time = dateFormat.format(date) + " " + h;
    dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    return dateFormat.parse(time);
  }

  public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime)
  {
    if ((nowTime.getTime() == startTime.getTime()) || 
      (nowTime
      .getTime() == endTime.getTime())) {
      return true;
    }

    Calendar date = Calendar.getInstance();
    date.setTime(nowTime);

    Calendar begin = Calendar.getInstance();
    begin.setTime(startTime);

    Calendar end = Calendar.getInstance();
    end.setTime(endTime);

    if ((date.after(begin)) && (date.before(end))) {
      return true;
    }
    return false;
  }
}