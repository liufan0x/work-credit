package com.anjbo.task.fc.monitor;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.anjbo.common.ApplicationContextHolder;

public class MonitorJob extends QuartzJobBean {
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		ApplicationContext applicationContext = ApplicationContextHolder
				.getApplicationContext();
		MonitorTask monitorTask = (MonitorTask) applicationContext
				.getBean("monitorTask");
		JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
		monitorTask=(MonitorTask) jobDataMap.get("monitorTask");
		monitorTask.run();
	}

	public MonitorJob() {
		super();
	}
	
	

}
