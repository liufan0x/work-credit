package com.anjbo.task.fc.order;

import javax.annotation.Resource;

import com.anjbo.service.fc.order.LendingStatisticsService;

public class LendingStatisticsTask {
	@Resource
	private LendingStatisticsService lendingStatisticsService;
	private void run() {
		lendingStatisticsService.run();
	}
}
