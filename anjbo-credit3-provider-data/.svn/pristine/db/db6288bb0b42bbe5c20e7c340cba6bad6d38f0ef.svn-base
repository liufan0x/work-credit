package com.anjbo.stream.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import com.anjbo.config.RabbitConfigure;
import com.rabbitmq.client.Channel;

@Component
public class DataListener {
	
	
	@RabbitListener(queues = {RabbitConfigure.QUEUE_AMS})
    public void listener(String obj, Message message, Channel channel) {
    	System.out.println("[listener 监听的消息] - [{" + obj + "}]" + message.toString());
    }
    
}
