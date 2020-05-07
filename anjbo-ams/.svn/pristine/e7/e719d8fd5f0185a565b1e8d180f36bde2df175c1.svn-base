package com.anjbo.sms.hander;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.anjbo.sms.SMSMessage;

/**
 * 短信处理调用类
 * @author hex
 *
 */
public class SMSZuulServer {

	private Logger log = Logger.getLogger(getClass());
	
	private volatile static SMSZuulServer smsZuulServer;
	
	private SMSZuulServer(){}
	
	public static SMSZuulServer getsmsZuulServer(){
		if (smsZuulServer == null) {
			synchronized (SMSZuulServer.class) {
				if (smsZuulServer == null) {
					return new SMSZuulServer();
				}
			}
		}
		return smsZuulServer;
	}
	
	public int excute(SMSMessage message){
		try {
			TreeMap<Integer, SMSHandlerWeightBase> weightPool = (TreeMap<Integer, SMSHandlerWeightBase>) SMSHandlerWeightBase.weightCollection.clone();
			Iterator<Entry<Integer, SMSHandlerWeightBase>> iter = weightPool.entrySet().iterator();
			int sum = 0;
			while(iter.hasNext()){
				Entry<Integer, SMSHandlerWeightBase> entry = iter.next();
				sum += entry.getKey();
			}
			int rand = (int)(1+Math.random()*(sum-1+1));
			log.info("===============此次权重随机数为:"+rand);
			return handler(weightPool, message, rand);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * 权重最大处理
	 * @param weightPool
	 * @param message
	 * @param rand
	 * @return
	 */
	private int handler(TreeMap<Integer, SMSHandlerWeightBase> weightPool,SMSMessage message, Integer rand){
		Iterator<Entry<Integer, SMSHandlerWeightBase>> iter = weightPool.entrySet().iterator();
		int weightNumber = 0;
		while(iter.hasNext()){
			Entry<Integer, SMSHandlerWeightBase> entry = iter.next();
			weightNumber += entry.getKey();
			if(rand <= weightNumber){
				SMSHandlerWeightBase handler = entry.getValue();
				int result = handler.validate(message);
				log.info("smsSendChannel1 smsSendCheck checkResult="+result);
				// 检查失败
				if (result < 0) {
					return result;
				}
				if (handler.handle(message) == 0) {
					return 0;
				}
				weightPool.remove(entry.getKey());
				return recursionHandler(weightPool, message);
			}
		}
		return -1;
	}
	
	/**
	 * 顺序处理（权重处理失效的情况下）
	 * @param weightPool
	 * @param message
	 * @return
	 */
	private int recursionHandler(TreeMap<Integer, SMSHandlerWeightBase> weightPool, SMSMessage message) {
		Iterator<Entry<Integer, SMSHandlerWeightBase>> iter = weightPool.entrySet().iterator();
		while(iter.hasNext()){
			Entry<Integer, SMSHandlerWeightBase> entry = iter.next();
			SMSHandlerWeightBase handler = entry.getValue();
			int result = handler.validate(message);
			log.info("smsSendChannel1 smsSendCheck checkResult="+result);
			// 检查失败
			if (result < 0) {
				return result;
			}
			if (handler.handle(message) == 0) {
				return 0;
			}
			weightPool.remove(entry.getKey());
			return recursionHandler(weightPool, message);
		}
		return -1;
	}
}
