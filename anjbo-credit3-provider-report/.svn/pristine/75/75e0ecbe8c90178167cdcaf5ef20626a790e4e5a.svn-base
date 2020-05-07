package com.anjbo.service;

import java.util.List;
import java.util.Map;

import com.anjbo.bean.OrderLendingStatistics;

public interface AchievementStatisticsService {
	/**
	 * 业绩统计
	 * @param map
	 * @return
	 */
	List<Map<String,Object>> selectLendingOrders(Map<String,Object> map);
	
	/**
	 * 查询总计数据
	 * @param map
	 * @return
	 */
	Map<String,Object> selectLendingOrdersSum(Map<String,Object> map);
	
	/**
	 *总条数 
	 * @return
	 */
	int selectlendingCount(Map<String,Object> map);
	
	/**
	 * 查询本月，本年城市放款量目标，创收目标
	 * @param map
	 * @return
	 */
	List<Map<String,Object>> selectPersonalAim(Map<String,Object> map);
	
	/**
	 * 查询订单放款量,利息服务费等
	 * @param map
	 * @return
	 */
	OrderLendingStatistics selectLendingStatisticsByOrderNo(OrderLendingStatistics orderLendingStatistics);
	
	/**
	 * 按日期查询所有的统计记录
	 * @param map
	 * @return
	 */
	List<OrderLendingStatistics> selectOrdersByTime(Map<String,Object> map);
	
	/**
	 * 查询权责发生制利息
	 */
	double selectInterestByTime(Map<String,Object> map);
	
	/**
	 * 更新利息和创收
	 * @param orderLendingStatistics
	 * @return
	 */
	int updateLendingStatisticsByOrderNo(OrderLendingStatistics orderLendingStatistics);
	
	/**
	 * 获取详情列表
	 * @param orderLendingStatistics
	 * @return
	 */
	List<OrderLendingStatistics> selectOrderDetailList(OrderLendingStatistics orderLendingStatistics);
	
	/**
	 * 获取详情列表条数
	 * @param orderLendingStatistics
	 * @return
	 */
	int selectOrderDetailCount(OrderLendingStatistics orderLendingStatistics);
}
