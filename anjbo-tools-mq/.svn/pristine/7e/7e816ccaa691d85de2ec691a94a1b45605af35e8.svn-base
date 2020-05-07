package com.anjbo.service;

import com.anjbo.core.MqServerConnect;
import com.anjbo.exception.SubscribeException;
/**
 * mq消息消费者
 * @author cain
 *
 */
public class MqCustomer {
	/**
	 * 连接mq服务器
	 * @param host mq服务地址
	 * @param userName 用户名
	 * @param password 密码
	 * @param qKey 订阅信息标识码
	 * @return
	 * @throws SubscribeException
	 */
	public MqServerConnect init(String host, int port, String userName, String password, String qKey) throws SubscribeException {
		MqServerConnect mq=MqServerConnect.init(host, port, userName, password, qKey);
		return mq;
	}
	/**
	 * 获取信息（线程阻塞等待消息）
	 * @param mqServerConnect
	 * @return
	 * @throws InterruptedException
	 */
	public String getMessage(MqServerConnect mqServerConnect) throws InterruptedException {
		String message=mqServerConnect.getMessage();
		return message;
	}

}
