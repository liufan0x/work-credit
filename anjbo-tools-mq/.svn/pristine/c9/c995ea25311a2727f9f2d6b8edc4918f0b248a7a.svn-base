package com.anjbo.core;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeoutException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONObject;
import com.anjbo.config.MqConfig;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RMqClientConnect {
	private static final Log log = LogFactory.getLog(RMqClientConnect.class);
	/**
	 * 发送消息
	 * @param qKey 发送者唯一标识
	 * @param msg 消息内容
	 */
	public static void sendMsg(String qKey,Object msg){
		 try {
			//创建连接工厂
			ConnectionFactory factory = new ConnectionFactory();
			//设置RabbitMQ相关信息
			String host = MqConfig.getStringValue("rabbitmq.host");
			int port = MqConfig.getIntegerValue("rabbitmq.port");
			String username = MqConfig.getStringValue("rabbitmq.username");
			String password = MqConfig.getStringValue("rabbitmq.password");
			factory.setHost(host);
			factory.setUsername(username);
			factory.setPassword(password);
			factory.setPort(port);
			//创建一个新的连接
			Connection connection = factory.newConnection();
			//创建一个通道
			Channel channel1 = connection.createChannel();
			//  声明一个队列        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
			//发送消息到队列中
			String msgStr = JSONObject.toJSONString(msg);
			channel1.basicPublish("", qKey, null, msgStr.getBytes("UTF-8"));
			log.info("发送消息：" + msgStr);
			//关闭通道和连接
			channel1.close();
			connection.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
	}
}
