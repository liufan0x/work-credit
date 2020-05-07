package com.anjbo.service;

import com.anjbo.core.RMqServerConnect;
import com.anjbo.exception.SubscribeException;
import com.anjbo.execute.ExecuteConsumeTask;
/**
 * mq消息消费者
 * @author cain
 *
 */
public class RMqCustomer {

	/**
	 * 对rabbitMq服务进行注册连接
	 * @param host
	 * @param userName
	 * @param password
	 * @param qKey
	 * @param executeConsumeTask
	 * @return
	 * @throws SubscribeException
	 */
	public RMqServerConnect init(String qKey,ExecuteConsumeTask executeConsumeTask) throws SubscribeException {
		RMqServerConnect con=RMqServerConnect.init(qKey, executeConsumeTask);
		return con;
	}


}
