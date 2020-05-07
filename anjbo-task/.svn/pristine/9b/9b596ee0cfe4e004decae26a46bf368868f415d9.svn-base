package com.anjbo.task.fc.risk;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.anjbo.common.ApplicationContextHolder;
import com.anjbo.task.fc.monitor.MonitorTask;

public class FundAuditJob extends QuartzJobBean {
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		ApplicationContext applicationContext = ApplicationContextHolder
				.getApplicationContext();
		FundAuditTask fundAuditTask = (FundAuditTask) applicationContext
				.getBean("fundAuditTask");
		JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
		fundAuditTask=(FundAuditTask) jobDataMap.get("fundAuditTask");
		fundAuditTask.run();
	}

	public FundAuditJob() {
		super();
	}
}
