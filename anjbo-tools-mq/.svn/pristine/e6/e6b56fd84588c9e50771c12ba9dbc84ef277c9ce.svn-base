package com.anjbo.execute;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.anjbo.config.MqConfig;

/**
 * @ClassName: ExecuteQueue 
 * @Description: 队列中心
 * @author limh@anjbo.com
 * @date 2018年5月22日 下午10:05:20 
 * 
 * @param <T>
 */
public class ExecuteQueue<T>{
	private static final Logger log = Logger.getLogger(ExecuteQueue.class);
	private static class SingletonHolder{  
        @SuppressWarnings("rawtypes")
		public static ExecuteQueue<?> executeQueue = new ExecuteQueue();
    }  
	public static ExecuteQueue<?> getInstance() {
		return SingletonHolder.executeQueue;
	}
	private List<ExecuteData<?>> dataList;
	
	private ExecuteQueue() {
		dataList = new CopyOnWriteArrayList<ExecuteData<?>>(
				new LinkedList<ExecuteData<?>>());
		 Timer timer = new Timer();
		 timer.schedule(new clearDataTimeOutTask(), 60000,60000);
	}
	/**
	 * @ClassName: clearDataTimeOutTask 
	 * @Description: 任务清理超时数据
	 * @author limh@anjbo.com
	 * @date 2018年6月8日 上午10:16:17 
	 *
	 */
	class clearDataTimeOutTask extends TimerTask{
		@Override
		public void run() {
			log.info("任务清理超时数据启动...");
			long time = new Date().getTime();
			long timeOut = MqConfig.getIntegerValue("DATA_TIMEOUT");
			Iterator<ExecuteData<?>> it = dataList.iterator();
			while(it.hasNext()){
				ExecuteData<?> executeData = it.next();
				//超过一小时自动清除
				if((time - executeData.getTime()) > timeOut){
					log.info("超时自动清理数据："+JSON.toJSONString(executeData));
					dataList.remove(executeData);
				}
			}
		}
		
	}
	/**
	 * 添加数据到队列
	 * @param t
	 */
	public void addData(ExecuteData<?> t){
		if(getExecuteDataById(t.getId())!=null){
			log.info("添加数据失败，该数据已存在："+JSON.toJSONString(t));
			return;
		}
		t.setStatus(ExecuteData.NOT_READ);
		t.setTime(new Date().getTime());
		dataList.add(t);
		log.info("添加数据成功："+JSON.toJSONString(t));
	}
	/**
	 * 根据id修改数据
	 * @param id
	 * @param msg
	 */
	public synchronized void changeStatusAndMsgById(String id,String msg){
		for (ExecuteData<?> executeData : dataList) {
			if(executeData.getId().equals(id)){
				executeData.setStatus(StringUtils.isEmpty(msg)?
						ExecuteData.FAILDED:ExecuteData.COMPLETE);
				executeData.setMsg(msg);
				log.info("修改数据完成1："+JSON.toJSONString(executeData));
				return;
			}
		}
		if(StringUtils.isNotEmpty(msg)){
			ExecuteData<T> executeData = new ExecuteData<T>();
			executeData.setId(id);
			executeData.setMsg(msg);
			addData(executeData);
			executeData.setStatus(ExecuteData.COMPLETE);
			log.info("修改数据完成2："+JSON.toJSONString(executeData));
		}
	}
	/**
	 * 获取数据结果
	 * @param t 当前对象，未加入的会自动加入
	 * @param times 循环次数，每循环一次睡眠1秒
	 * @return
	 */
	public String getMsgById(ExecuteData<?> t,int times){
		int i = -1;
		ExecuteData<?> executeData = null;
		while (i < times) {
			executeData = getExecuteDataById(t.getId());
			if(executeData==null){
				addData(t);
				executeData = t;
			}else if(executeData.getStatus()==ExecuteData.COMPLETE) {
				dataList.remove(executeData);
				return executeData.getMsg();
			}
			i++;
			if(i<times){
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
			}
		}
		dataList.remove(executeData);
		return null;
	}
	/**
	 * 根据id获取队列数据
	 * @param id
	 * @return
	 */
	public ExecuteData<?> getExecuteDataById(String id){
		for (ExecuteData<?> executeData : dataList) {
			if (executeData.getId().equals(id)) {
				executeData.setTime(new Date().getTime());
				return executeData;
			}
		}
		return null;
	}
	/**
	 * 获取未完成的数据
	 * @return
	 */
	public JSONObject getAllNotCompleteDataToJson() {
		JSONObject result = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		for (ExecuteData<?> executeData : dataList) {
			if(executeData.getStatus()!=ExecuteData.COMPLETE){
				executeData.setStatus(ExecuteData.ALREADY_READ);
				jsonArray.add(executeData.getData());
			}
		}
		result.put("estates", jsonArray);
		return result;
	}
}
