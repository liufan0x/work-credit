package com.anjbo.controller.order;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.common.RespHelper;
import com.anjbo.common.RespStatus;
import com.anjbo.service.fc.order.LendingStatisticsService;

@RequestMapping("/credit/task/order")
@Controller
public class LendingStatisticsController {

	@Resource
	private LendingStatisticsService lendingStatisticsService;
	/**
	 * 初始化放款量等数据
	 * @param request
	 * @param response
	 * @param orderLendingStatistics
	 * @return
	 */
	@RequestMapping("init")
	@ResponseBody
	public RespStatus updateLendingStatistics(HttpServletRequest request,HttpServletResponse response){
		RespStatus resp = new RespStatus();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new Date());
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("lendingStartTime", "2015-01-01"+" 00:00:00");
		map.put("lendingEndTime", date+" 00:00:00");
		lendingStatisticsService.init(map);
		RespHelper.setSuccessRespStatus(resp);
		return resp;
	}
}
