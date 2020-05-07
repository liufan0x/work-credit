package com.anjbo.chromejs.manager;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ArchiveTask {
	@Scheduled(cron = "0 0/1 * * * ?")
	public void sendChrome(){
		ArchiveMonitor.getInstance().sendChrome();
	}
}
