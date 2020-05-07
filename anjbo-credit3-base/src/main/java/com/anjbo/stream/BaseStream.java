package com.anjbo.stream;

import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import com.anjbo.config.RabbitConfigure;

public class BaseStream {
	protected final Logger logger = Logger.getLogger(this.getClass());	
	
	@Autowired
    private RabbitTemplate rabbitTemplate;
	
	public void send(String routeKey,Object message){
		CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
		logger.info("发送成功routeKey:"+routeKey+",message:"+message+",correlationData:"+correlationData.getId());
        this.rabbitTemplate.convertAndSend(RabbitConfigure.EXCHANGE, routeKey , message, correlationData);
    }
	
	public void send(String routeKey,Object message,ConfirmCallback confirmCallback){
		rabbitTemplate.setConfirmCallback(confirmCallback);
        send(routeKey, message);
    }
	
	public void sendAMS(Object message){
		send(RabbitConfigure.QUEUE_AMS, message);
    }
	
	public void sendMail(String routeKey,Object message){
		send(RabbitConfigure.QUEUE_MAIL, message);
    }
	
}
