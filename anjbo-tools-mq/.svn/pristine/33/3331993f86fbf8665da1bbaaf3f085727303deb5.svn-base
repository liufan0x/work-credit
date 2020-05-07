package com.anjbo.core;

import static com.anjbo.parameter.SettingManage.EXCEPTIONSTR;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeoutException;

import com.anjbo.exception.SubscribeException;
import com.anjbo.parameter.SettingManage;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;
/**
 * 连接mq服务器实体类
 * @author cain
 *
 */
public class MqServerConnect { 
	//取消自动应答
	private static final boolean autoAck = false ;  
	
	private  Connection connection;

	//阻塞队列，FIFO
    private static  LinkedBlockingQueue<String> messageList = new LinkedBlockingQueue<String>(50);

	private static  MqServerConnect mqServerConnect=null;
	
	
	public String getMessage() throws InterruptedException{
		return messageList.take();
		
	}


	private  MqServerConnect() throws SubscribeException, IOException, TimeoutException {
		ConnectionFactory connectionFactory=new ConnectionFactory();
		connectionFactory.setHost(SettingManage.host);
		connectionFactory.setPort(SettingManage.port);
		connectionFactory.setPassword(SettingManage.password);
		connectionFactory.setUsername(SettingManage.userName);
		this.connection=connectionFactory.newConnection();

	}
	

	 public static MqServerConnect init(String host,int port,String userName,String password,String qKey) throws SubscribeException{
			if(mqServerConnect == null){
			        synchronized (MqServerConnect.class){
			            if(mqServerConnect == null){
			            	SettingManage.setting( host, port, userName, password, qKey);
			            	try {
								mqServerConnect=new MqServerConnect();
								Thread t=new Thread(new Runnable() {
									public void run() {
										try {
											mqServerConnect.acceptMessage();
										} catch (ShutdownSignalException e) {
											e.printStackTrace();
										} catch (ConsumerCancelledException e) {
											e.printStackTrace();
										} catch (IOException e) {
											e.printStackTrace();
										} catch (InterruptedException e) {
											e.printStackTrace();
										}
										
									}
								});
								t.start();
							} catch (IOException e) {
								throw new SubscribeException(EXCEPTIONSTR+"The connection Error!",e);
							} catch (TimeoutException e) {
								throw new SubscribeException(EXCEPTIONSTR+"The connection Error!",e);
							}
			            }
			        }
		  	 }
		  return mqServerConnect;
	 }
	 

	public void acceptMessage() throws IOException, ShutdownSignalException, ConsumerCancelledException, InterruptedException{
		 //创建channel
		final Channel channel=this.connection.createChannel();

         //设置队列的属性第一个参数为队列名。
	     //第二个参数为是否创建一个持久队列，
	     //第三个是否创建一个专用的队列，
         //第四个参数为是否自动删除队列，
	     //第五个参数为其他属性（结构参数）
         channel.queueDeclare(SettingManage.qKey, true, false, false, null);
 
         //让每个消费者在同一时刻会分配一个任务
         channel.basicQos(1);
         final Consumer consumer = new DefaultConsumer(channel) {
             @Override
             public void handleDelivery(String consumerTag,Envelope envelope,AMQP.BasicProperties properties,byte[] body) throws IOException {
                 String message = new String(body, "UTF-8");

                 try {
                	 MqServerConnect.messageList.put(message);
                	 //throw new Exception();
                 }catch (Exception e){
                     channel.abort();
                 }finally {
                	 //mq应答
                     channel.basicAck(envelope.getDeliveryTag(),false);
                 }
             }
         };
         
         //QueueingConsumer consumer = new QueueingConsumer(channel);  
      
         channel.basicConsume(SettingManage.qKey, autoAck, consumer);  

//	     while(true){
//	        	 //nextDelivery是一个阻塞方法（内部实现其实是阻塞队列的take方法）  
//	            QueueingConsumer.Delivery delivery = consumer.nextDelivery();  
//	            String message = new String(delivery.getBody());      
//
//	            MqServerConnect.messageList.put(message);
//	            
//	           // 确认消息，已经收到  
//	            channel.basicAck(delivery.getEnvelope().getDeliveryTag()  , false);  
//	     }
	     
	}
	


}
