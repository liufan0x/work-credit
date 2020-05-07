package com.anjbo.chromejs.manager;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.anjbo.chromejs.utils.AmsUtil;
import com.anjbo.chromejs.utils.Constants;
import com.anjbo.chromejs.utils.IpUtil;

public class ArchiveMonitor {
	private String jkid;
	private int adminErrorTimes = 0;//后台监控错误次数
	private int adminSendTimes = 0;//后台监控发送次数
	private static final Log log = LogFactory.getLog(ArchiveMonitor.class);
	private static ArchiveMonitor archiveMonitor;
	
	public static synchronized ArchiveMonitor getInstance() {
		if (archiveMonitor == null) {
			archiveMonitor = new ArchiveMonitor();
		}
		return archiveMonitor;
	}
	private Map<String,Map<String,String>> map;
	
	private ArchiveMonitor() {
		map = new ConcurrentHashMap<String,Map<String,String>>();
	}
	/**
	 * 插件端检测
	 */
	public void sendChrome(){
		//是否开启监控
		if(!"true".equals(Constants.ARCHIVE_MONITOR_OPEN)){
			return;
		}
		log.info("插件端检测开始："+map.toString());
		Iterator<String> it = map.keySet().iterator();
		while(it.hasNext()){
			String key = it.next();
			Map<String,String> dataMap = map.get(key);
			int sendChrome = NumberUtils.toInt(dataMap.get("sendChrome"));//插件端发送次数
			long timeChrome = NumberUtils.toLong(dataMap.get("timeChrome"));//插件端第一次发送时间值
			long timeChromeOut = NumberUtils.toLong(dataMap.get("timeChromeOut"));//插件端访问服务器时间值
			long timeOut = (new Date().getTime()-timeChromeOut)/60000;
			if(timeOut>3){//3分钟超时预警
				if(sendChrome == 0){
					timeChrome = new Date().getTime();
				}else if(sendChrome>=2){//最多提示次数
					return;
				}
				long time = (new Date().getTime()-timeChrome)/60000;
				if(sendChrome==1&&time<5){//5分钟再次预警
					return;
				}
				emailSend(String.format("查档服务器%s长时间未请求后台服务器，请检查浏览器以及插件是否正常",key));
				sendChrome++;
			}else if(sendChrome>0){
				emailSend(String.format("查档服务器%s已恢复请求后台服务器，请悉知",key));
				sendChrome = 0;
				timeChrome = 0;
			}
			dataMap.put("sendChrome", String.valueOf(sendChrome));
			dataMap.put("timeChrome", String.valueOf(timeChrome));
			map.put(key,dataMap);
		}
	}
	public static void main(String[] args) {
		emailSend(String.format("查档服务器%s已恢复请求后台服务器，请悉知","192.168.1.8[jk01]"));
	}
	/**
	 * 发送短信预警
	 * @param smsContent
	 */
	private static void smsSend(String smsContent){
		try {
			String ip = IpUtil.V4IP;
			String phones = Constants.ARCHIVE_MONITOR_PHONE;
			String []phone = phones.split(",");
			for (String p : phone) {
				AmsUtil.smsSend(p, ip, smsContent, "anjbo-tools-archive-monitor");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 发送邮件预警
	 * @param smsContent
	 */
	private static void emailSend(String smsContent){
		smsSend(smsContent);
		try {
			String emails = Constants.ARCHIVE_MONITOR_EMAIL;
			String []email = emails.split(",");
			for (String e : email) {
				AmsUtil.emailSend(smsContent, e, smsContent);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 插件端更新访问时间
	 */
	public void timeChromeOut(String jkid){
		this.jkid = jkid;
		String ip = IpUtil.V4IP;//获取外网ip地址
		String key = ip;
		if(StringUtils.isNotEmpty(jkid)){
			key = String.format("%s[%s]",ip,jkid);
		}
		Map<String,String> dataMap = map.get(key);
		if(dataMap==null){
			dataMap = new HashMap<String,String>();
		}
		dataMap.put("timeChromeOut", String.valueOf(new Date().getTime()));
		map.put(key,dataMap);
	}
	public void checkAdminErrorTimes(boolean tag){
		if(tag){
			this.adminErrorTimes++;
		}else{
			this.adminErrorTimes = 0;
			this.adminSendTimes = 0;
		}
		log.info("错误次数："+this.adminErrorTimes+"发送次数："+this.adminSendTimes);
		if(adminErrorTimes>3&&adminSendTimes==0){
			log.info("后台监控启动");
			String ip = IpUtil.V4IP;//获取外网ip地址
			String key = ip;
			if(StringUtils.isNotEmpty(jkid)){
				key = String.format("%s[%s]",ip,jkid);
			}
			emailSend(String.format("查档服务器%s后台未收到数据，请检查国土局网站是否正常",key));
			this.adminSendTimes++;
		}
	}
}
