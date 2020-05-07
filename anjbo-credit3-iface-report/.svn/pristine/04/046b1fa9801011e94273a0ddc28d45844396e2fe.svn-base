package com.anjbo.controller;
import java.util.Map;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.anjbo.bean.OrderLendingStatistics;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "业绩概况报表数据")
@RequestMapping("/achievementStatistics/v")
public interface IAchievementStatisticsController{
	
	
	@ApiOperation(value = "查询报表可以查看的城市", httpMethod = "POST",response = Map.class)
	@RequestMapping(value = "city")
	public abstract RespData<Map<String, Object>> city();

	
	@ApiOperation(value = "获取业绩概况报表数据", httpMethod = "POST" ,response = Map.class)
	@RequestMapping("achievement")
	public abstract RespPageData<Map<String,Object>> selectAchievement(@RequestBody Map<String,Object> map);
	

	@ApiOperation(value = "获取目标完成率统计", httpMethod = "POST" ,response = Map.class)
	@RequestMapping("aim")
	public RespPageData<Map<String,Object>> aim(@RequestBody Map<String,Object> map);
	
	
	@ApiOperation(value = "查询订单放款量，利息等", httpMethod = "POST" ,response = OrderLendingStatistics.class)
	@RequestMapping("query")
	public RespDataObject<OrderLendingStatistics> queryLendingStatistics(@RequestBody OrderLendingStatistics orderLendingStatistics);
	
	
	@ApiOperation(value = "更新统计罚息和创收", httpMethod = "POST" ,response = OrderLendingStatistics.class)
	@RequestMapping("update")
	public RespStatus updateLendingStatistics(@RequestBody OrderLendingStatistics orderLendingStatistics);
	
	@ApiOperation(value = "获取详情列表", httpMethod = "POST" ,response = OrderLendingStatistics.class)
	@RequestMapping("selectOrderDetailList")
	public RespPageData<OrderLendingStatistics> selectOrderDetailList(@RequestBody OrderLendingStatistics orderLendingStatistics);
	
}
