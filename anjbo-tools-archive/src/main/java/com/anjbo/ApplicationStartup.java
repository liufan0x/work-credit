package com.anjbo;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.anjbo.chromejs.entity.ArchiveDto;
import com.anjbo.chromejs.manager.ArchiveMonitor;
import com.anjbo.core.RMqClientConnect;
import com.anjbo.exception.SubscribeException;
import com.anjbo.execute.ConsumeTask;
import com.anjbo.execute.ExecuteConsumeTask;
import com.anjbo.execute.ExecuteData;
import com.anjbo.execute.ExecuteQueue;
import com.anjbo.service.RMqCustomer;

@Component
public class ApplicationStartup implements CommandLineRunner{
	private static final Log log = LogFactory.getLog(ApplicationStartup.class);
	
	@Override
	public void run(String... args) throws Exception {
		try {
			/**
			 * 对rabbitMq服务进行注册连接
			 */
			new RMqCustomer().init("queue_archive",new ExecuteConsumeTask(new ConsumeTask() {
					public boolean executeConsume(String message) throws Exception {
						log.info("rabbitMQ客户端收到消息："+message);
						if(message==null){
							
						}else{
							try {
								JSONObject json = JSONObject.parseObject(message);
								ArchiveDto dto = (ArchiveDto) JSONObject.toJavaObject(json,ArchiveDto.class);
								ExecuteData<ArchiveDto> data = new ExecuteData<ArchiveDto>();
								data.setId(dto.getId());
								data.setData(dto);
								String msg = ExecuteQueue.getInstance().getMsgById(data, 10);
								if(StringUtils.isEmpty(msg)){
									//这里通过basicRecover机制将消息重新入队
									ArchiveMonitor.getInstance().checkAdminErrorTimes(true);
									return true;
								}else{
									ArchiveMonitor.getInstance().checkAdminErrorTimes(false);
									data.setMsg(msg);
									RMqClientConnect.sendMsg(json.getString("channel"),data);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						return false;
					 }
				})
			);
			log.info("rabbitMQ客户端监听启动");
		} catch (SubscribeException e) {
			e.printStackTrace();
		}
	}
}
