package com.anjbo.service;

import java.io.IOException;

import com.anjbo.core.VMqServerConnect;
import com.anjbo.exception.SubscribeException;
/**
 * mq消息消费者
 * @author cain
 *
 */
public class VMqCustomer {
	/**
	 * 连接mq服务器
	 * @param host mq服务地址
	 * @param userName 用户名
	 * @param password 密码
	 * @param qKey 订阅信息标识码
	 * @return
	 * @throws SubscribeException
	 */
	public VMqServerConnect init(String host, int port, String userName, String password, String qKey) throws SubscribeException {
		VMqServerConnect mq=VMqServerConnect.init(host, port, userName, password, qKey);
		return mq;
	}
	/**
	 * 获取信息（线程阻塞等待消息）
	 * @param mqServerConnect
	 * @return
	 * @throws InterruptedException
	 */
	public String getMessage(VMqServerConnect vmqServerConnect) throws InterruptedException {
		String message=vmqServerConnect.getMessage();
		return message;
	}
	/**
	 * 完成业务后应答方法
	 * @param mqServerConnect
	 * @throws IOException
	 */
	public void ackCompleteMessage(VMqServerConnect vmqServerConnect) throws IOException{
		vmqServerConnect.ackCompleteMessage();
	}

}
