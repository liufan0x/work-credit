package com.anjbo.sms.hander;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import org.springframework.web.context.ContextLoader;

import com.anjbo.sms.SMSMessage;
import com.anjbo.sms.SMSService;
import com.anjbo.utils.ConfigUtil;

/**
 * 权重处理方式抽象类
 * @author hex
 *
 */
public abstract class SMSHandlerWeightBase implements SMSHandler{
	
	final static TreeMap<Integer, SMSHandlerWeightBase> weightCollection = new TreeMap<Integer, SMSHandlerWeightBase>(new Comparator<Integer>() {
		@Override
		public int compare(Integer o1, Integer o2) {
			return o2-o1;
		}
	});
	
	private final String weightNameArgs = ConfigUtil.getStringValue("weight_name");
	
	private final String weightArgs = ConfigUtil.getStringValue("weight");
	
	private String name;
	
	private int weight;

	public void setName(String name) {
		this.name = name;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int validate(SMSMessage message) {
		final Date today = new Date();
		return ContextLoader.getCurrentWebApplicationContext().getBean(SMSService.class).smsSendCheck(message.getMobile(), message.getIp(), message.getSvrIp(), message.getSmsContent(),
				message.getSmsComeFrom(), today);
	}
	
	/**
	 * 获取权重通过权重名
	 * @param str
	 * @return
	 */
	public int getWeightByStr(String str){
		List<String> weightList = new ArrayList(Arrays.asList(weightArgs.split(",")));
		List<String> weightNameList = new ArrayList(Arrays.asList(weightNameArgs.split(",")));
		for (int i=0;i<weightNameList.size();i++) {
			if (str.equals(weightNameList.get(i))) {
				return Integer.parseInt(weightList.get(i));
			}
		}
		return 0;
	}
}
