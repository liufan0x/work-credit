package com.anjbo.monitor.processor;

import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Controller;

import com.anjbo.execute.ConsumeTask;
import com.anjbo.execute.ExecuteConsumeTask;
import com.anjbo.execute.ExecuteQueue;
import com.anjbo.monitor.util.ConfigUtil;
import com.anjbo.service.RMqCustomer;

import net.sf.json.JSONObject;

/**
 * 项目在加载完毕后立刻执行
 * @author jyq
 *
 */
@Controller
public class MyAfterPropertiesSet implements InitializingBean {
	private static final Log log = LogFactory.getLog(MyAfterPropertiesSet.class);

	@Override
	public void afterPropertiesSet() throws Exception {
		/**
		 * 对rabbitMq服务进行注册连接
		 */
		final String mqChannel = ConfigUtil.getStringValue("MQ_CHANNEL");
		new RMqCustomer().init(mqChannel, new ExecuteConsumeTask(new ConsumeTask() {
				public boolean executeConsume(String message) throws Exception {
					log.info("rabbitMQ客户端收到消息："+message);
					if(message==null){
						
					}else{
						try {
							JSONObject json = JSONObject.fromObject(message);
							Map<String, Object> map = (Map<String, Object>) JSONObject.toBean(json, Map.class);
							ExecuteQueue.getInstance().changeStatusAndMsgById(MapUtils.getString(map, "id"), MapUtils.getString(map, "msg"));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					
					return false;
				 }
			})
		);
		log.info("rabbitMQ客户端监听启动，channel:" + mqChannel);
	}
}
