package com.anjbo.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfigure {

	public final static String EXCHANGE = "anjbo-credit3-exchange";

	public final static String QUEUE = "anjbo-credit3-queue";

	public final static String QUEUE_AMS = "anjbo-credit3-ams";

	public final static String QUEUE_MAIL = "anjbo-credit3-mail";


	@Bean
	public DirectExchange exchange() {
		return new DirectExchange(EXCHANGE);
	}

	@Bean
	public Queue queue() {
		return new Queue(QUEUE,true);
	}

	@Bean
	Binding binding(Queue queue,DirectExchange exchange){
		return BindingBuilder.bind(queue).to(exchange).with("anjbo-credit3");
	}


	@Bean
	public Queue queue_ams() {
		return new Queue(QUEUE_AMS,true);
	}

	@Bean
	Binding binding_ams(Queue queue_ams,DirectExchange exchange){
		return BindingBuilder.bind(queue_ams).to(exchange).with("anjbo-credit3-ams");
	}

	@Bean
	public Queue queue_mail() {
		return new Queue(QUEUE_MAIL,true);
	}

	@Bean
	Binding binding_mail(Queue queue_mail,DirectExchange exchange){
		return BindingBuilder.bind(queue_mail).to(exchange).with("anjbo-credit3-mail");
	}


}
