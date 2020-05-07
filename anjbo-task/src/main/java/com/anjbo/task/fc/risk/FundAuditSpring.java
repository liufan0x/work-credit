package com.anjbo.task.fc.risk;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SimpleTrigger;
import org.springframework.beans.factory.annotation.Autowired;
import com.anjbo.bean.fc.fund.AllocationFundAuditDto;
import com.anjbo.bean.fc.monitor.MonitorArchiveDto;
import com.anjbo.common.Constants;
import com.anjbo.common.Enums;
import com.anjbo.common.RespData;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.fc.monitor.MonitorArchiveService;
import com.anjbo.utils.AmsUtil;
import com.anjbo.utils.ConfigUtil;
import com.anjbo.utils.HttpUtil;
import com.google.gson.JsonArray;

public class FundAuditSpring {
	Logger Log=Logger.getLogger(FundAuditSpring.class);
	@Resource(name="riskScheduler")
	private Scheduler scheduler;
	@Autowired
	private MonitorArchiveService monitorArchiveService;
	private static int counter = 0;

	public void execute() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		// 访问数据库，将任务添加到调度器
		System.out.println(sdf.format(new Date()) + " 宿主定时器开始执行----->");
		Log.info(sdf.format(new Date()) + " 宿主定时器开始执行----->");
		
		String toolsUrl = ConfigUtil.getStringValue(Enums.GLOBAL_CONFIG_KEY.CREDIT_URL.toString());
		List<AllocationFundAuditDto> urlList=HttpUtil.getList(toolsUrl, "/credit/risk/allocationfundaduit/list",null,AllocationFundAuditDto.class);
		System.out.println("schedule启动状态：" + scheduler.isStarted());
		String[] jobNames=scheduler.getJobNames(Scheduler.DEFAULT_GROUP);
		//遍历数据库url集合
		if(urlList!=null && urlList.size()>0){
			for (int i = 0; i < urlList.size(); i++) {
				String keyName=urlList.get(i).getOrderNo()+"-"+urlList.get(i).getId();
				SimpleTrigger trigger=(SimpleTrigger) scheduler.getTrigger("trigger:"+keyName, Scheduler.DEFAULT_GROUP);
				if(trigger!=null){
					System.out.println(keyName+"定时器已启动");
					String endTime = urlList.get(i).getEndTimeStr()+" 23:59";
					Date endDate = sdf.parse(endTime);
					if (endDate.getTime() < new Date().getTime()) {
						scheduler.deleteJob("job:"+keyName, Scheduler.DEFAULT_GROUP);
						System.out.println(keyName+"定时器到期停止");
					}
					continue;
				}
				try {
					if(null != urlList.get(i).getStartTimeStr() && null != urlList.get(i).getEndTimeStr()){
						String startTime = urlList.get(i).getStartTimeStr()+" 00:00";
						String endTime = urlList.get(i).getEndTimeStr()+" 23:59";
						Date startDate = sdf.parse(startTime);
						Date endDate = sdf.parse(endTime);
						System.out.println("startTime="+startDate.getTime()+" new Date="+(new Date().getTime()));
						System.out.println("endDate="+endDate.getTime()+" new Date="+(new Date().getTime()));
						if (startDate.getTime() > new Date().getTime()
								|| endDate.getTime() < new Date().getTime()) {
							continue;
						}
						// 作业、任务
						JobDetail jobDetail = new JobDetail();
						jobDetail.setName("job:"+keyName);
						jobDetail.setGroup(Scheduler.DEFAULT_GROUP);
		
						FundAuditTask fundAuditTask = new FundAuditTask();
						fundAuditTask.setCreateUid(urlList.get(i).getCreateUid());
						fundAuditTask.setMsg("job:"+keyName);
						fundAuditTask.setPhone(urlList.get(i).getPhone());
						fundAuditTask.setOrderNo(urlList.get(i).getOrderNo());
						JobDataMap map = new JobDataMap();
						map.put("fundAuditTask", fundAuditTask);
						jobDetail.setJobDataMap(map);
						jobDetail.setJobClass(FundAuditJob.class);
						// 触发器
						trigger = new SimpleTrigger("trigger:"+keyName, Scheduler.DEFAULT_GROUP);
						// 启动时间
						trigger.setStartTime(new Date());
						int queryFrequency=urlList.get(i).getQueryFrequency();  //查询频率
	//					queryFrequency=queryFrequency>5?5:queryFrequency;
						int spacTime = (24*3600*1000)/queryFrequency;
						System.out.println("查询频率："+spacTime+"毫秒");
						// 设置作业执行间隔 毫秒
						trigger.setRepeatInterval(spacTime);
						// 设置作业执行次数
						trigger.setRepeatCount(-1);
						System.out.println("任务"+(i+1));
						System.out.println(sdf.format(new Date()) + " 添加定时器:"+keyName);
						scheduler.scheduleJob(jobDetail, trigger);
						Thread.sleep(3000);
						if(!scheduler.isStarted()){
							scheduler.start();
							//定时停止发短信
		//					String phone = ConfigUtil.getStringValue("MONTITOR_STOP");
		//					String ipWhite = ConfigUtil.getStringValue(Enums.GLOBAL_CONFIG_KEY.AMS_SMS_IPWHITE.toString());
		//					String content = "融安查询放款审批停止运行后执行了start，请到项目日志中查看定时器是否重启成功！";
		//					AmsUtil.smsSend(phone, ipWhite,content , Constants.SMSCOMEFROM);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		//无效定时器任务删除
		for(String jobName:jobNames){
			boolean flag=false;
			for(AllocationFundAuditDto monUrl:urlList){
				String keyName=monUrl.getOrderNo()+"-"+monUrl.getId();
				if(jobName.equals("job:"+keyName)){
					flag=true;
					break;
				}
			}
			if(!flag){
				scheduler.deleteJob(jobName, scheduler.DEFAULT_GROUP);
				System.out.println(jobName+"定时器数据失效，已删除");
			}
		}
	}

	public Scheduler getScheduler() {
		return scheduler;
	}

	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}

	public MonitorArchiveService getMonitorArchiveService() {
		return monitorArchiveService;
	}

	public void setMonitorArchiveService(MonitorArchiveService monitorArchiveService) {
		this.monitorArchiveService = monitorArchiveService;
	}

	public static int getCounter() {
		return counter;
	}

	public static void setCounter(int counter) {
		FundAuditSpring.counter = counter;
	}



}
