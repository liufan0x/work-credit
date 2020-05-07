package com.anjbo.service.impl;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;

import com.anjbo.bean.OrderLendingStatistics;
import com.anjbo.dao.AchievementStatisticsMapper;
import com.anjbo.service.AchievementStatisticsService;
import com.anjbo.utils.DateUtils;
import com.anjbo.utils.StringUtil;
@Service
public class AchievementStatisticsServiceImpl implements AchievementStatisticsService {
	DecimalFormat nf = new DecimalFormat("0.####"); 
	@Resource
	private AchievementStatisticsMapper achievementStatisticsMapper;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	@Override
	public List<Map<String,Object>> selectLendingOrders(
			Map<String, Object> map) {
		List<Map<String,Object>> rList = achievementStatisticsMapper.selectLendingOrders(map);
		//将利息，服务费等转化为万元
		for (Map<String, Object> map3 : rList) {
			map3.put("interest", nf.format(MapUtils.getDoubleValue(map3, "interest", 0.0)/10000.0));
			map3.put("fine", nf.format(MapUtils.getDoubleValue(map3, "fine", 0.0)/10000.0));
			map3.put("serviceCharge", nf.format(MapUtils.getDoubleValue(map3, "serviceCharge", 0.0)/10000.0));
			map3.put("rebateMoney", nf.format(MapUtils.getDoubleValue(map3, "rebateMoney", 0.0)/10000.0));
			map3.put("customsPoundage", nf.format(MapUtils.getDoubleValue(map3, "customsPoundage", 0.0)/10000.0));
			map3.put("otherPoundage", nf.format(MapUtils.getDoubleValue(map3, "otherPoundage", 0.0)/10000.0));
			map3.put("income", nf.format(MapUtils.getDoubleValue(map3, "income", 0.0)/10000.0));
		}
		return rList;
	}

	@Override
	public Map<String, Object> selectLendingOrdersSum(
			Map<String, Object> map) {
		Map<String,Object> map3 = achievementStatisticsMapper.selectLendingOrdersSum(map);
		//将利息，服务费等转化为万元
		map3.put("interest", nf.format(MapUtils.getDoubleValue(map3, "interest", 0.0)/10000.0));
		map3.put("fine", nf.format(MapUtils.getDoubleValue(map3, "fine", 0.0)/10000.0));
		map3.put("serviceCharge", nf.format(MapUtils.getDoubleValue(map3, "serviceCharge", 0.0)/10000.0));
		map3.put("rebateMoney", nf.format(MapUtils.getDoubleValue(map3, "rebateMoney", 0.0)/10000.0));
		map3.put("customsPoundage", nf.format(MapUtils.getDoubleValue(map3, "customsPoundage", 0.0)/10000.0));
		map3.put("otherPoundage", nf.format(MapUtils.getDoubleValue(map3, "otherPoundage", 0.0)/10000.0));
		map3.put("income", nf.format(MapUtils.getDoubleValue(map3, "income", 0.0)/10000.0));
		return map3;
	}
	
	@Override
	public int selectlendingCount(Map<String, Object> map) {
		return achievementStatisticsMapper.selectlendingCount(map);
	}

	@Override
	public List<Map<String, Object>> selectPersonalAim(Map<String, Object> map) {
		return achievementStatisticsMapper.selectPersonalAim(map);
	}

	@Override
	public OrderLendingStatistics selectLendingStatisticsByOrderNo(OrderLendingStatistics orderLendingStatistics) {
		// TODO Auto-generated method stub
		return achievementStatisticsMapper.selectLendingStatisticsByOrderNo(orderLendingStatistics);
	}

	@Override
	public int updateLendingStatisticsByOrderNo(OrderLendingStatistics orderLendingStatistics) {
		// TODO Auto-generated method stub
		return achievementStatisticsMapper.updateLendingStatisticsByOrderNo(orderLendingStatistics);
	}

	@Override
	public List<OrderLendingStatistics> selectOrdersByTime(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return achievementStatisticsMapper.selectOrdersByTime(map);
	}

	@Override
	public double selectInterestByTime(Map<String, Object> map) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String startTime1=MapUtils.getString(map, "startTime");
		String endTime1=MapUtils.getString(map, "endTime");
		Map<String,Object> dMap = new HashMap<String,Object>();
		try {
			Date startTime = sdf.parse(startTime1);
			startTime = DateUtils.addDate(startTime, -182);
			dMap.put("startTime", sdf.format(startTime));
			dMap.put("endTime", endTime1);
			dMap.put("cityName",MapUtils.getString(map,"cityName"));
			dMap.put("productCode",MapUtils.getString(map,"productCode"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<OrderLendingStatistics> orders = achievementStatisticsMapper.selectOrdersByTime(dMap);
		double interestAll=0;
		for (OrderLendingStatistics order : orders) {
			if(order.getInterest()!=null&&order.getInterest()>0) {
				String startTime2 = order.getLendingTimeStr();
				Date endTime = DateUtils.addDate(order.getLendingTime(), order.getBorrowingDay()-1);
				String endTime2 = sdf.format(endTime);
				int day = queryIntersectionDate(startTime1, endTime1, startTime2, endTime2);
				double interest = order.getBorrowingAmount()*(order.getRate()!=null?order.getRate():0)*day;
				interestAll += interest;
			}
		}
		return interestAll/100.0;
	}

	/**
	 * 获取两个日期区间交集天数
	 * @param startTime1
	 * @param endTime1
	 * @param startTime2
	 * @param endTime2
	 * @return
	 */
	public int queryIntersectionDate(String startTime1,String endTime1,String startTime2,String endTime2) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			if((sdf.parse(endTime2).after(sdf.parse(startTime1))||endTime2.equals(startTime1))
					&&(sdf.parse(startTime2).before(sdf.parse(endTime1))||startTime2.equals(endTime1))) {//有交集
				//开始时间取较大的，结束时间取较小的
				Date startTime = sdf.parse(startTime1).after(sdf.parse(startTime2))?sdf.parse(startTime1):sdf.parse(startTime2);
				Date endTime = sdf.parse(endTime1).before(sdf.parse(endTime2))?sdf.parse(endTime1):sdf.parse(endTime2);
				int day = DateUtils.getDiffDays(startTime, endTime);
				return day+1<0?0:day+1;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public List<OrderLendingStatistics> selectOrderDetailList(OrderLendingStatistics orderLendingStatistics) {
		//处理放款时间查询，startTime和endTime为空时去lendingTime
		String startTime = "";
		String endTime = "";
		if(StringUtil.isNotBlank(orderLendingStatistics.getStartTime())||StringUtil.isNotBlank(orderLendingStatistics.getEndTime())){
			startTime = orderLendingStatistics.getStartTime();
			endTime = orderLendingStatistics.getEndTime();
		}else if(StringUtil.isNotBlank(orderLendingStatistics.getLendingTimeStr())&&"上周".equals(orderLendingStatistics.getLendingTimeStr())){
			startTime = sdf.format(DateUtils.getBeginDayOfLastWeek());
			endTime = sdf.format(DateUtils.getEndDayOfLastWeek()) ;
		}else if(StringUtil.isNotBlank(orderLendingStatistics.getLendingTimeStr())&&"上月".equals(orderLendingStatistics.getLendingTimeStr())){
			startTime = sdf.format(DateUtils.getBeginDayOfLastMonth());
			endTime = sdf.format(DateUtils.getEndDayOfLastMonth()) ;
		}else if(StringUtil.isNotBlank(orderLendingStatistics.getLendingTimeStr())&&"去年".equals(orderLendingStatistics.getLendingTimeStr())){
			startTime = sdf.format(DateUtils.getBeginDayOfLastYear());
			endTime = sdf.format(DateUtils.getEndDayOfLastYear()) ;
		}else if(StringUtil.isNotBlank(orderLendingStatistics.getLendingTimeStr())&&"本月".equals(orderLendingStatistics.getLendingTimeStr())){
			startTime = sdf.format(DateUtils.getBeginDayOfMonth());
			endTime = sdf.format(DateUtils.getEndDayOfMonth()) ;
		}else if(StringUtil.isNotBlank(orderLendingStatistics.getLendingTimeStr())&&"本年".equals(orderLendingStatistics.getLendingTimeStr())){
			startTime = sdf.format(DateUtils.getBeginDayOfYear());
			endTime = sdf.format(DateUtils.getEndDayOfYear()) ;
		}
		if(StringUtil.isNotBlank(startTime)){
			orderLendingStatistics.setStartTime(startTime+" 00:00:00");
		}
		if(StringUtil.isNotBlank(endTime)){
			orderLendingStatistics.setEndTime(endTime+" 23:59:59");
		}
		List<OrderLendingStatistics>  list = achievementStatisticsMapper.selectOrderDetailList(orderLendingStatistics);
		for (OrderLendingStatistics order : list) {
			//实际用款天数=实际回款时间-放款时间+1
			int actualLoanDay = 0;
			if(StringUtil.isNotBlank(order.getPayMentAmountDate())) {
				try {
					actualLoanDay = DateUtils.getDiffDays(sdf.parse(sdf.format(order.getLendingTime())), sdf.parse(order.getPayMentAmountDate()));
					order.setActualLoanDay(actualLoanDay+1);
					//提前还款天数=
					//客户逾期天数
					if(actualLoanDay>order.getBorrowingDay()) {//逾期
						order.setYqDatediff(actualLoanDay-order.getBorrowingDay()+1);
					}
					if(actualLoanDay<order.getBorrowingDay()) {
						order.setTqDatediff(order.getBorrowingDay()-actualLoanDay-1);
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}

	@Override
	public int selectOrderDetailCount(OrderLendingStatistics orderLendingStatistics) {
		return achievementStatisticsMapper.selectOrderDetailCount(orderLendingStatistics);
	}
	

}
