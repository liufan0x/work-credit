package com.anjbo.chromejs.utils;

public class Constants {

	public static final int JSURL_MAX_STAY_TIME;
	public static final int REFRESH_INTERVAL_TIME;
	public static final int REFRESH_INTERVAL_TIME_OUT;

	public static final int LOGIN_JSURL_MAX_STAY_TIME;
	public static final int REFRESH_LOGIN_INTERVAL_TIME;

	public static final int GET_HTML_INTERVAL_TIME;
	public static final int RETRY_GET_HTML_TIME;

	public static final int READ_JSURL_INTERVAL_TIME;
	public static final int CALCULATE_CALL_SEND_SERVLET_OFFSET;
	public static final int CALCULATE_CALL_SEND_SERVLET_BASE;
	public static final String CHROME_UPDATING_FILE;
	public static final String FROM_MAIL;
	public static final String TO_MAIL;
	public static final String TO_IPHONE;
	public static final int TIMECOUNT = 60000;
	public static final int CHROME_TIME_OUT_COUNT = 100000;
	public static final int CHROME_TIME_OUT_MAX = 160000;
	public static final String MEAIL_FOCUS = "1.VNC环境(HK server)登录server如 : balloon.wisers.com:2   "
			+ "password: chrome  \n\n"
			+ "2.检查google浏览器是否打开并刷新正常，如果已经关闭,选applications->Internet->chrome打开即可！\n\n"
			+ "3.检查报警的url是否正常打开，并能自动刷新！如:almond.wisers.com:8080/crawler/urlmanager.jsp?tag=1\n\n"
			+ "4.当收到邮件恢复正常时，表示已经启动成功!"
			+ "如有疑问，请联系:";
	public static final String SMSCOMEFROM_ARCHIVE_MONITOR_JOB="mortgage-archive-monitor-job";
	public static final String MEAIL_SUPORT;
	public static final String AMS_URL;
	public static final String AMS_SMS_IPWHITE;
	public static final String ARCHIVE_MONITOR_OPEN;
	public static final String ARCHIVE_MONITOR_EMAIL;
	public static final String ARCHIVE_MONITOR_PHONE;
	static {
		Config config = new Config("javascript_chrome.properties");
		TO_IPHONE=config
		.getPropertyNotEmpty("pagecrawler.warning.conn_chrome.receive_telephone_to");
		FROM_MAIL = config
				.getPropertyNotEmpty("pagecrawler.warning.conn_chrome.receive_mail_address");
		TO_MAIL = config
				.getPropertyNotEmpty("pagecrawler.warning.conn_chrome.receive_mail_to");
		CHROME_UPDATING_FILE = config
				.getPropertyNotEmpty("chrome_updating_file");
		JSURL_MAX_STAY_TIME = config
				.getIntPropertyNotEmpty("jsurl_max_stay_time");
		REFRESH_INTERVAL_TIME = config
				.getIntPropertyNotEmpty("refresh_interval_time");
		REFRESH_INTERVAL_TIME_OUT= config
		.getIntPropertyNotEmpty("refresh_interval_time_out");
		LOGIN_JSURL_MAX_STAY_TIME = config
				.getIntPropertyNotEmpty("login_jsurl_max_stay_time");
		REFRESH_LOGIN_INTERVAL_TIME = config
				.getIntPropertyNotEmpty("refresh_login_interval_time");

		GET_HTML_INTERVAL_TIME = config
				.getIntPropertyNotEmpty("get_html_interval_time");
		RETRY_GET_HTML_TIME = config
				.getIntPropertyNotEmpty("retry_get_html_time");

		READ_JSURL_INTERVAL_TIME = config
				.getIntPropertyNotEmpty("read_jsurl_interval_time");
		CALCULATE_CALL_SEND_SERVLET_OFFSET = config
				.getIntPropertyNotEmpty("calculate_call_send_servlet_offset");
		CALCULATE_CALL_SEND_SERVLET_BASE = REFRESH_INTERVAL_TIME
				/ READ_JSURL_INTERVAL_TIME;
		MEAIL_SUPORT=config
		.getPropertyNotEmpty("pagecrawler.warning.conn_chrome.tec_mail_support");
		AMS_URL=config
		.getPropertyNotEmpty("AMS_URL");
		AMS_SMS_IPWHITE=config
		.getPropertyNotEmpty("AMS_SMS_IPWHITE");
		ARCHIVE_MONITOR_OPEN=config
		.getPropertyNotEmpty("ARCHIVE_MONITOR_OPEN");
		ARCHIVE_MONITOR_EMAIL=config
		.getPropertyNotEmpty("ARCHIVE_MONITOR_EMAIL");
		ARCHIVE_MONITOR_PHONE=config
		.getPropertyNotEmpty("ARCHIVE_MONITOR_PHONE");
	}
}
