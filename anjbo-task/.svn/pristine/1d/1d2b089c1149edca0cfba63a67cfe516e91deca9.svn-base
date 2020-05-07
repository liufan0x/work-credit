package com.anjbo.task.fc.monitor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SimpleTrigger;
import org.springframework.beans.factory.annotation.Autowired;

import com.anjbo.bean.fc.monitor.MonitorArchiveDto;
import com.anjbo.common.Constants;
import com.anjbo.common.Enums;
import com.anjbo.service.fc.monitor.MonitorArchiveService;
import com.anjbo.utils.AmsUtil;
import com.anjbo.utils.ConfigUtil;

public class MonitorSpringQtz {
	Logger Log=Logger.getLogger(MonitorSpringQtz.class);
	@Resource(name="fcScheduler")
	private Scheduler scheduler;
	@Autowired
	private MonitorArchiveService monitorArchiveService;
	private static int counter = 0;

	public void execute() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 访问数据库，将任务添加到调度器
		System.out.println(sdf.format(new Date()) + " 宿主定时器开始执行----->");
		Log.info(sdf.format(new Date()) + " 宿主定时器开始执行----->");
		MonitorArchiveDto archiveDto = new MonitorArchiveDto();
		List<MonitorArchiveDto> urlList = monitorArchiveService.selectArchiveList(archiveDto);
		System.out.println("schedule启动状态：" + scheduler.isStarted());
		String[] jobNames=scheduler.getJobNames(Scheduler.DEFAULT_GROUP);
		//遍历数据库url集合
		for (int i = 0; i < urlList.size(); i++) {
			String keyName=urlList.get(i).getEstateNo()+"-"+urlList.get(i).getId();
			SimpleTrigger trigger=(SimpleTrigger) scheduler.getTrigger("trigger:"+keyName, Scheduler.DEFAULT_GROUP);
			if(trigger!=null){
				System.out.println(keyName+"定时器已启动");
				
				String endTime = urlList.get(i).getEndTime();
				Date endDate = sdf.parse(endTime);
				if (endDate.getTime() < new Date().getTime()) {
					scheduler.deleteJob("job:"+keyName, Scheduler.DEFAULT_GROUP);
					System.out.println(keyName+"定时器到期停止");
				}
				continue;
			}
			try {
				String startTime = urlList.get(i).getStartTime();
				String endTime = urlList.get(i).getEndTime();
				Date startDate = sdf.parse(startTime);
				Date endDate = sdf.parse(endTime);
				if (startDate.getTime() > new Date().getTime()
						|| endDate.getTime() < new Date().getTime()) {
					continue;
				}
				// 作业、任务
				JobDetail jobDetail = new JobDetail();
				jobDetail.setName("job:"+keyName);
				jobDetail.setGroup(Scheduler.DEFAULT_GROUP);

				MonitorTask monitorTask = new MonitorTask();
				monitorTask.setCreateUid(urlList.get(i).getCreateUid());
				monitorTask.setMsg("job:"+keyName);
				monitorTask.setArchiveId(urlList.get(i).getArchiveId());
				monitorTask.setPhone(urlList.get(i).getPhone());
				monitorTask.setEstateNo(urlList.get(i).getEstateNo());
				monitorTask.setIdentityNo(urlList.get(i).getIdentityNo());
				JobDataMap map = new JobDataMap();
				map.put("monitorTask", monitorTask);
				jobDetail.setJobDataMap(map);
				jobDetail.setJobClass(MonitorJob.class);
				// 触发器
				trigger = new SimpleTrigger("trigger:"+keyName, Scheduler.DEFAULT_GROUP);
				// 启动时间
				trigger.setStartTime(new Date());
				int queryFrequency=urlList.get(i).getQueryFrequency();
				queryFrequency=queryFrequency>5?5:queryFrequency;
				int spacTime = (24/queryFrequency)*3600*1000;
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
					//发短信
					String phone = ConfigUtil.getStringValue("MONTITOR_STOP");
					String ipWhite = ConfigUtil.getStringValue(Enums.GLOBAL_CONFIG_KEY.AMS_SMS_IPWHITE.toString());
					String content = "房产监测定时器停止运行后执行了start，请到项目日志中查看定时器是否重启成功！";
					AmsUtil.smsSend(phone, ipWhite,content , Constants.SMSCOMEFROM);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		//无效定时器任务删除
		for(String jobName:jobNames){
			boolean flag=false;
			for(MonitorArchiveDto monUrl:urlList){
				String keyName=monUrl.getEstateNo()+"-"+monUrl.getId();
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
		MonitorSpringQtz.counter = counter;
	}



}
