package com.anjbo.controller.impl;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.anjbo.bean.DictDto;
import com.anjbo.bean.OrderLendingStatistics;
import com.anjbo.bean.UserDto;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.IAchievementStatisticsController;
import com.anjbo.controller.ReportBaseController;
import com.anjbo.controller.api.DataApi;
import com.anjbo.controller.api.UserApi;
import com.anjbo.service.AchievementStatisticsService;
import com.anjbo.utils.DateUtils;
import com.anjbo.utils.StringUtil;

@RestController
public class AchievementStatisticsController extends ReportBaseController implements IAchievementStatisticsController{
	
	Logger log = Logger.getLogger(AchievementStatisticsController.class);
	
	@Resource
	private AchievementStatisticsService achievementStatisticsService;
	
	@Resource DataApi dataApi;
	@Resource UserApi userApi;
	
	/**
	 * 查询报表可以查看的城市
	 * @param request
	 * @param response
	 * @return
	 */
	
	@Override
	public RespData<Map<String,Object>> city(){
		RespData<Map<String,Object>> respData = new RespData<Map<String,Object>>();
		UserDto userDto = userApi.getUserDto();
		List<DictDto> cityListTemp = dataApi.getDictDtoListByType("bookingSzAreaOid");
		List<Map<String,Object>> city = new ArrayList<Map<String,Object>>();
		Map<String,Object> map = new HashMap<String, Object>();
		if(userDto.getAuthIds().contains("88")||userDto.getAuthIds().contains("89")){//CEO权限
			map.put("code", "");
			map.put("name", "全国");
			city.add(map);
		}
		for (DictDto dictDto : cityListTemp) {
			if(StringUtils.isEmpty(dictDto.getPcode())){
				map = new HashMap<String, Object>();
				map.put("code", dictDto.getCode());
				map.put("name", dictDto.getName());
				if(userDto.getAuthIds().contains("88")||userDto.getAuthIds().contains("89")){//CEO权限
					city.add(map);
				}else{
					if((userDto.getAuthIds().contains("110")||userDto.getAuthIds().contains("111"))&&dictDto.getName().contains("深圳")){
						city.add(map);
					}
					if((userDto.getAuthIds().contains("112")||userDto.getAuthIds().contains("113"))&&dictDto.getName().contains("厦门")){
						city.add(map);
					}
					if((userDto.getAuthIds().contains("114")||userDto.getAuthIds().contains("115"))&&dictDto.getName().contains("广州")){
						city.add(map);
					}
					if((userDto.getAuthIds().contains("116")||userDto.getAuthIds().contains("117"))&&dictDto.getName().contains("郑州")){
						city.add(map);
					}
					if((userDto.getAuthIds().contains("118")||userDto.getAuthIds().contains("119"))&&dictDto.getName().contains("杭州")){
						city.add(map);
					}
					if((userDto.getAuthIds().contains("120")||userDto.getAuthIds().contains("121"))&&dictDto.getName().contains("重庆")){
						city.add(map);
					}
					if((userDto.getAuthIds().contains("123")||userDto.getAuthIds().contains("124"))&&dictDto.getName().contains("长沙")){
						city.add(map);
					}
					if((userDto.getAuthIds().contains("125")||userDto.getAuthIds().contains("126"))&&dictDto.getName().contains("南京")){
						city.add(map);
					}
					if((userDto.getAuthIds().contains("127")||userDto.getAuthIds().contains("128"))&&dictDto.getName().contains("武汉")){
						city.add(map);
					}
					if((userDto.getAuthIds().contains("129")||userDto.getAuthIds().contains("130"))&&dictDto.getName().contains("福州")){
						city.add(map);
					}
					if((userDto.getAuthIds().contains("131")||userDto.getAuthIds().contains("132"))&&dictDto.getName().contains("上海")){
						city.add(map);
					}
					if((userDto.getAuthIds().contains("133")||userDto.getAuthIds().contains("134"))&&dictDto.getName().contains("南宁")){
						city.add(map);
					}
					if((userDto.getAuthIds().contains("135")||userDto.getAuthIds().contains("136"))&&dictDto.getName().contains("成都")){
						city.add(map);
					}
					if((userDto.getAuthIds().contains("137")||userDto.getAuthIds().contains("138"))&&dictDto.getName().contains("惠州")){
						city.add(map);
					}
					if((userDto.getAuthIds().contains("139")||userDto.getAuthIds().contains("140"))&&dictDto.getName().contains("东莞")){
						city.add(map);
					}
				}
			}
		}
		RespHelper.setSuccessData(respData, city);
		return respData;
	}
	
	/**
	 * 获取业绩概况报表数据
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 */
	@Override
	public RespPageData<Map<String,Object>> selectAchievement(@RequestBody Map<String,Object> map){
		RespPageData<Map<String,Object>> resp = new RespPageData<Map<String,Object>>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String lendingStartTime="";
		String lendingEndTime="";
		//没有城市参数返回失败
		if(!map.containsKey("cityName")||(map.containsKey("cityName")&&StringUtil.isBlank(MapUtils.getString(map, "cityName")))){
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(RespStatusEnum.PARAMETER_ERROR.getMsg());
			return resp;
		}
		if((map.containsKey("startTime")&&StringUtil.isNotBlank(MapUtils.getString(map, "startTime")))||(map.containsKey("endTime")&&StringUtil.isNotBlank(MapUtils.getString(map, "endTime")))){
			lendingStartTime = MapUtils.getString(map, "startTime");
			lendingEndTime = MapUtils.getString(map, "endTime");
		}else if(map.containsKey("lendingTime")&&"上周".equals(MapUtils.getString(map, "lendingTime"))){
			lendingStartTime = sdf.format(DateUtils.getBeginDayOfLastWeek());
			lendingEndTime = sdf.format(DateUtils.getEndDayOfLastWeek()) ;
		}else if(map.containsKey("lendingTime")&&"上月".equals(MapUtils.getString(map, "lendingTime"))){
			lendingStartTime = sdf.format(DateUtils.getBeginDayOfLastMonth());
			lendingEndTime = sdf.format(DateUtils.getEndDayOfLastMonth()) ;
		}else if(map.containsKey("lendingTime")&&"去年".equals(MapUtils.getString(map, "lendingTime"))){
			lendingStartTime = sdf.format(DateUtils.getBeginDayOfLastYear());
			lendingEndTime = sdf.format(DateUtils.getEndDayOfLastYear()) ;
		}else if(map.containsKey("lendingTime")&&"本月".equals(MapUtils.getString(map, "lendingTime"))){
			lendingStartTime = sdf.format(DateUtils.getBeginDayOfMonth());
			lendingEndTime = sdf.format(DateUtils.getEndDayOfMonth()) ;
		}else if(map.containsKey("lendingTime")&&"本年".equals(MapUtils.getString(map, "lendingTime"))){
			lendingStartTime = sdf.format(DateUtils.getBeginDayOfYear());
			lendingEndTime = sdf.format(DateUtils.getEndDayOfYear()) ;
		}
		if(StringUtil.isNotBlank(lendingStartTime)){
			map.put("startTime", lendingStartTime+" 00:00:00");
		}
		if(StringUtil.isNotBlank(lendingEndTime)){
			map.put("endTime", lendingEndTime+" 23:59:59");
		}
		//分组参数
		if(map.containsKey("groupBy")&&"城市".equals(MapUtils.getString(map,"groupBy"))){
			map.put("groupBy", "cityName");
			if(map.containsKey("orderBy")&&"单量".equals(MapUtils.getString(map,"orderBy"))){
				map.put("orderBy", "orderCount desc");
			}else if(map.containsKey("orderBy")&&"创收".equals(MapUtils.getString(map,"orderBy"))){
				map.put("orderBy", "income desc");
			}else{
				map.put("orderBy", "sum(lendingAmount) desc");
			}
			
		}else{
			if(map.containsKey("lendingTime")&&MapUtils.getString(map, "lendingTime").contains("年")){
				map.put("groupBy", "date_format(lendingTime, '%Y-%m')");
			}else{
				map.put("groupBy", "date_format(lendingTime, '%Y-%m-%d')");
			}
			map.put("orderBy", "date_format(lendingTime, '%Y-%m-%d')");
		}
		//如果有排序参数sortName、sortOrder,按参数排序
		if(map.containsKey("sortName")&&StringUtils.isNotBlank(MapUtils.getString(map,"sortName"))){
			map.put("orderBy", MapUtils.getString(map,"sortName")+" "+MapUtils.getString(map, "sortOrder", "asc"));
		}
		//分页参数
		if(map.containsKey("pageSize")&&MapUtils.getIntValue(map, "pageSize")>0){
			map.put("limit", "limit 0,"+(MapUtils.getIntValue(map, "start")+MapUtils.getIntValue(map, "pageSize")));
		}
		//城市不限时
		if(map.containsKey("cityName")&&"不限".equals(MapUtils.getString(map, "cityName"))){
			map.put("cityName", "");
		}
		DecimalFormat nf = new DecimalFormat("0.####");
		//查询时间分组列表权责发生制利息
		double interestAll = 0;
		if((MapUtils.getString(map, "lendingTime","").contains("月")||MapUtils.getString(map, "lendingTime","").contains("年"))
				&&MapUtils.getString(map, "groupBy","").contains("lendingTime")) {
			interestAll = achievementStatisticsService.selectInterestByTime(map);
		}
		//分页查询统计数据
		List<Map<String,Object>> list = achievementStatisticsService.selectLendingOrders(map);
		int count = achievementStatisticsService.selectlendingCount(map);
		//查询对比数据
		Map<String,Map<String,Object>> lastTooMonth = new HashMap<String, Map<String,Object>>();
		Map<String,Map<String,Object>> lastYear = new HashMap<String, Map<String,Object>>();
		Map<String,Map<String,Object>> lastYearContrast = new HashMap<String, Map<String,Object>>();
		//查询总计数据
		Map<String,Object> m = achievementStatisticsService.selectLendingOrdersSum(map);
		//自定义时间刚好为一整月时查询对比数据
		lendingStartTime = MapUtils.getString(map, "startTime");
		lendingEndTime = MapUtils.getString(map, "endTime");
		try {
			Calendar calendarStart = Calendar.getInstance();  
			Calendar calendarEnd = Calendar.getInstance();  
			Date startDate = null;
			Date endDate = null;
			if(StringUtil.isNotBlank(lendingStartTime)&&StringUtil.isNotBlank(lendingEndTime)){
				startDate = sdf.parse(lendingStartTime);
				endDate = sdf.parse(lendingEndTime);
				calendarStart.setTime(startDate);  
				calendarEnd.setTime(endDate);
			}
			if((map.containsKey("startTime")&&StringUtil.isNotBlank(MapUtils.getString(map, "startTime"))&&map.containsKey("endTime")&&StringUtil.isNotBlank(MapUtils.getString(map, "endTime")))
					&&calendarStart.get(2)==calendarEnd.get(2)&&calendarStart.get(Calendar.DAY_OF_MONTH)==1
					&&calendarEnd.get(Calendar.DAY_OF_MONTH)==calendarEnd.getActualMaximum(Calendar.DAY_OF_MONTH)
					&&"cityName".equals(MapUtils.getString(map, "groupBy"))){
				//上月开始时间
				calendarStart.set(Calendar.MONTH, calendarStart.get(2)-1);
				//上月结束时间
				calendarEnd.set(Calendar.MONTH, calendarEnd.get(2)-1);
				calendarEnd.set(Calendar.DATE, calendarStart.getActualMaximum(Calendar.DAY_OF_MONTH));
				lendingStartTime = sdf.format(new Timestamp(calendarStart.getTimeInMillis()));
				lendingEndTime = sdf.format(new Timestamp(calendarEnd.getTimeInMillis())) ;
				log.info("上月开始时间："+lendingStartTime);
				log.info("上月结束时间："+lendingEndTime);
				map.put("lendingStartTime", lendingStartTime);
				map.put("lendingEndTime", lendingEndTime);
				lastTooMonth = queryContrastData(map);
				//去年同月
				calendarStart.setTime(startDate);  
				calendarEnd.setTime(endDate);
				calendarStart.set(Calendar.YEAR, calendarStart.get(Calendar.YEAR)-1);
				calendarEnd.set(Calendar.YEAR, calendarEnd.get(Calendar.YEAR)-1);
				calendarEnd.set(Calendar.DATE, calendarStart.getActualMaximum(Calendar.DAY_OF_MONTH));
				lendingStartTime = sdf.format(new Timestamp(calendarStart.getTimeInMillis()));
				lendingEndTime = sdf.format(new Timestamp(calendarEnd.getTimeInMillis())) ;
				map.put("lendingStartTime", lendingStartTime);
				map.put("lendingEndTime", lendingEndTime);
				log.info("去年同月开始时间："+lendingStartTime);
				log.info("去年同月结束时间："+lendingEndTime);
				lastYear = queryContrastData(map);
			}else if(map.containsKey("lendingTime")&&"上月".equals(MapUtils.getString(map, "lendingTime"))
					&&"cityName".equals(MapUtils.getString(map, "groupBy"))){
				//查询上上月和去年同期
				lendingStartTime = sdf.format(DateUtils.getBeginDayOfLastTooMonth());
				lendingEndTime = sdf.format(DateUtils.getEndDayOfLastTooMonth()) ;
				map.put("lendingStartTime", lendingStartTime);
				map.put("lendingEndTime", lendingEndTime);
				lastTooMonth = queryContrastData(map);
				//去年同期
				lendingStartTime = sdf.format(DateUtils.getBeginDayOfLastMonthLastYear());
				lendingEndTime = sdf.format(DateUtils.getEndDayOfLastMonthLastYear()) ;
				map.put("lendingStartTime", lendingStartTime);
				map.put("lendingEndTime", lendingEndTime);
				lastYear = queryContrastData(map);
			}else if(map.containsKey("lendingTime")&&"本年".equals(MapUtils.getString(map, "lendingTime"))
					&&"cityName".equals(MapUtils.getString(map, "groupBy"))){
				//去年对比
				lendingStartTime = sdf.format(DateUtils.getBeginDayOfLastYear());
				lendingEndTime = sdf.format(DateUtils.getEndDayOfLastYear()) ;
				map.put("lendingStartTime", lendingStartTime);
				map.put("lendingEndTime", lendingEndTime);
				lastYearContrast = queryContrastData(map);
			}
		
			//计算总计
			double lendingAmount = 0.0;
			int orderCount = 0;
			double interest = 0.0;
			double fine = 0.0;
			double serviceCharge = 0.0;
			double income = 0.0;
			//返佣，关外手续费，其他金额
			double rebateMoney  = 0.0;
			double customsPoundage = 0.0;
			double otherPoundage = 0.0;
			Map<String,Object> zj = new HashMap<String, Object>();
			List<Map<String,Object>> rList = new ArrayList<Map<String,Object>>();
			rList.add(zj);
			
			lendingAmount = MapUtils.getDoubleValue(m, "lendingAmount",0.0);
			interest = MapUtils.getDoubleValue(m, "interest",0.0);
			fine = MapUtils.getDoubleValue(m, "fine",0.0);
			serviceCharge = MapUtils.getDoubleValue(m, "serviceCharge",0.0);
			rebateMoney = MapUtils.getDoubleValue(m, "rebateMoney",0.0);
			customsPoundage = MapUtils.getDoubleValue(m, "customsPoundage",0.0);
			otherPoundage = MapUtils.getDoubleValue(m, "otherPoundage",0.0);
			income = MapUtils.getDoubleValue(m, "income",0.0);
			orderCount = MapUtils.getIntValue(m, "orderCount",0);
			for (Map<String,Object> map2 : list) {
				rList.add(map2);
			}
			Map<String,Object> mapNull = new HashMap<String, Object>();
			mapNull.put("lendingAmount", 0);
			mapNull.put("interest", 0);
			mapNull.put("fine", 0);
			mapNull.put("serviceCharge", 0);
			mapNull.put("rebateMoney", 0);
			mapNull.put("customsPoundage", 0);
			mapNull.put("otherPoundage", 0);
			mapNull.put("income", 0);
			mapNull.put("orderCount", 0);
			
			//某个城市，无数据时，填充0
			if(StringUtil.isBlank(MapUtils.getString(map, "cityName"))&&"cityName".equals(MapUtils.getString(map, "groupBy"))){
				List<DictDto> cityListTemp = dataApi.getDictDtoListByType("bookingSzAreaOid");
				Map<String,Object> r2 = new HashMap<String, Object>();
				Map<String,Object> cityRListMap = new HashMap<String, Object>();
				for (Map<String, Object> map2 : rList){
					cityRListMap.put(MapUtils.getString(map2, "cityName"), map2);
				}
				for (DictDto dictDto : cityListTemp) {
					if(StringUtils.isEmpty(dictDto.getPcode())
							&&!cityRListMap.containsKey(dictDto.getName())){
						r2 = new HashMap<String, Object>();
						r2.putAll(mapNull);
						r2.put("cityName", dictDto.getName());
						rList.add(r2);
					}
				}
				count = rList.size()-1;
			}
			
			if(map.containsKey("groupBy")&&"cityName".equals(MapUtils.getString(map,"groupBy"))){
				zj.put("cityName", "总计");
			}else{
				zj.put("lendingTimeStr", "总计");
			}
			zj.put("lendingAmount", nf.format(lendingAmount));
			zj.put("interest", nf.format(interest));
			zj.put("fine", nf.format(fine));
			zj.put("serviceCharge", nf.format(serviceCharge));
			zj.put("rebateMoney", nf.format(rebateMoney));
			zj.put("customsPoundage", nf.format(customsPoundage));
			zj.put("otherPoundage", nf.format(otherPoundage));
			zj.put("income", nf.format(income));
			zj.put("orderCount", orderCount);
			rList.set(0,zj);
			//计算对比数据
			if(((map.containsKey("lendingTime")&&"上月".equals(MapUtils.getString(map, "lendingTime")))
					||(calendarStart.get(2)==calendarEnd.get(2)&&calendarStart.get(Calendar.DAY_OF_MONTH)==1
							&&calendarEnd.get(Calendar.DAY_OF_MONTH)==calendarEnd.getActualMaximum(Calendar.DAY_OF_MONTH)))
					&&"cityName".equals(MapUtils.getString(map, "groupBy"))){
				for (Map<String, Object> map2 : rList) {
					//上上月对比
					double lendingAmountLast = MapUtils.getDoubleValue(MapUtils.getMap(lastTooMonth, MapUtils.getString(map2, "cityName")), "lendingAmount", 0.0);
					double interestLast = MapUtils.getDoubleValue(MapUtils.getMap(lastTooMonth, MapUtils.getString(map2, "cityName")), "interest", 0.0);
					double fineLast = MapUtils.getDoubleValue(MapUtils.getMap(lastTooMonth, MapUtils.getString(map2, "cityName")), "fine", 0.0);
					double serviceChargeLast = MapUtils.getDoubleValue(MapUtils.getMap(lastTooMonth, MapUtils.getString(map2, "cityName")), "serviceCharge", 0.0);
					double rebateMoneyLast = MapUtils.getDoubleValue(MapUtils.getMap(lastTooMonth, MapUtils.getString(map2, "cityName")), "rebateMoney", 0.0);
					double customsPoundageLast = MapUtils.getDoubleValue(MapUtils.getMap(lastTooMonth, MapUtils.getString(map2, "cityName")), "customsPoundage", 0.0);
					double otherPoundageLast = MapUtils.getDoubleValue(MapUtils.getMap(lastTooMonth, MapUtils.getString(map2, "cityName")), "otherPoundage", 0.0);
					double incomeLast = MapUtils.getDoubleValue(MapUtils.getMap(lastTooMonth, MapUtils.getString(map2, "cityName")), "income", 0.0);
					double orderCountLast = MapUtils.getDoubleValue(MapUtils.getMap(lastTooMonth, MapUtils.getString(map2, "cityName")), "orderCount", 0.0);
					String lendingAmountContrast=contrastPercent(MapUtils.getDoubleValue(map2, "lendingAmount", 0.0),lendingAmountLast);
					String interestContrast=contrastPercent(MapUtils.getDoubleValue(map2, "interest", 0.0),interestLast);
					String fineContrast=contrastPercent(MapUtils.getDoubleValue(map2, "fine", 0.0),fineLast);
					String serviceChargeContrast=contrastPercent(MapUtils.getDoubleValue(map2, "serviceCharge", 0.0),serviceChargeLast);
					String rebateMoneyContrast=contrastPercent(MapUtils.getDoubleValue(map2, "rebateMoney", 0.0),rebateMoneyLast);
					String customsPoundageContrast=contrastPercent(MapUtils.getDoubleValue(map2, "customsPoundage", 0.0),customsPoundageLast);
					String otherPoundageContrast=contrastPercent(MapUtils.getDoubleValue(map2, "otherPoundage", 0.0),otherPoundageLast);
					String incomeContrast=contrastPercent(MapUtils.getDoubleValue(map2, "income", 0.0),incomeLast);
					String orderCountContrast=contrastPercent(MapUtils.getDoubleValue(map2, "orderCount", 0.0),orderCountLast);
					
					map2.put("lendingAmountLast", lendingAmountLast);
					map2.put("interestLast",interestLast );
					map2.put("fineLast", fineLast);
					map2.put("serviceChargeLast", serviceChargeLast);
					map2.put("rebateMoneyLast", rebateMoneyLast);
					map2.put("customsPoundageLast", customsPoundageLast);
					map2.put("otherPoundageLast", otherPoundageLast);
					map2.put("incomeLast", incomeLast);
					map2.put("orderCountLast", orderCountLast);
					
					map2.put("lendingAmountContrast", lendingAmountContrast);
					map2.put("interestContrast", interestContrast);
					map2.put("fineContrast", fineContrast);
					map2.put("serviceChargeContrast", serviceChargeContrast);
					map2.put("rebateMoneyContrast", rebateMoneyContrast);
					map2.put("customsPoundageContrast", customsPoundageContrast);
					map2.put("otherPoundageContrast", otherPoundageContrast);
					map2.put("incomeContrast", incomeContrast);
					map2.put("orderCountContrast", orderCountContrast);
					//去年同期对比
					double lendingAmountLastY = MapUtils.getDoubleValue(MapUtils.getMap(lastYear, MapUtils.getString(map2, "cityName")), "lendingAmount", 0.0);
					double interestLastY = MapUtils.getDoubleValue(MapUtils.getMap(lastYear, MapUtils.getString(map2, "cityName")), "interest", 0.0);
					double fineLastY = MapUtils.getDoubleValue(MapUtils.getMap(lastYear, MapUtils.getString(map2, "cityName")), "fine", 0.0);
					double serviceChargeLastY = MapUtils.getDoubleValue(MapUtils.getMap(lastYear, MapUtils.getString(map2, "cityName")), "serviceCharge", 0.0);
					double rebateMoneyLastY = MapUtils.getDoubleValue(MapUtils.getMap(lastYear, MapUtils.getString(map2, "cityName")), "rebateMoney", 0.0);
					double customsPoundageLastY = MapUtils.getDoubleValue(MapUtils.getMap(lastYear, MapUtils.getString(map2, "cityName")), "customsPoundage", 0.0);
					double otherPoundageLastY = MapUtils.getDoubleValue(MapUtils.getMap(lastYear, MapUtils.getString(map2, "cityName")), "otherPoundage", 0.0);
					double incomeLastY = MapUtils.getDoubleValue(MapUtils.getMap(lastYear, MapUtils.getString(map2, "cityName")), "income", 0.0);
					double orderCountLastY = MapUtils.getDoubleValue(MapUtils.getMap(lastYear, MapUtils.getString(map2, "cityName")), "orderCount", 0.0);
					String lendingAmountContrastY=contrastYPercent(MapUtils.getDoubleValue(map2, "lendingAmount", 0.0),lendingAmountLastY);
					String interestContrastY=contrastYPercent(MapUtils.getDoubleValue(map2, "interest", 0.0),interestLastY);
					String fineContrastY=contrastYPercent(MapUtils.getDoubleValue(map2, "fine", 0.0),fineLastY);
					String serviceChargeContrastY=contrastYPercent(MapUtils.getDoubleValue(map2, "serviceCharge", 0.0),serviceChargeLastY);
					String rebateMoneyContrastY=contrastYPercent(MapUtils.getDoubleValue(map2, "rebateMoney", 0.0),rebateMoneyLastY);
					String customsPoundageContrastY=contrastYPercent(MapUtils.getDoubleValue(map2, "customsPoundage", 0.0),customsPoundageLastY);
					String otherPoundageContrastY=contrastYPercent(MapUtils.getDoubleValue(map2, "otherPoundage", 0.0),otherPoundageLastY);
					String incomeContrastY=contrastYPercent(MapUtils.getDoubleValue(map2, "income", 0.0),incomeLastY);
					String orderCountContrastY=contrastYPercent(MapUtils.getDoubleValue(map2, "orderCount", 0.0),orderCountLastY);
					map2.put("lendingAmountLastY", lendingAmountLastY);
					map2.put("interestLastY",interestLastY );
					map2.put("fineLastY", fineLastY);
					map2.put("serviceChargeLastY", serviceChargeLastY);
					map2.put("rebateMoneyLastY", rebateMoneyLastY);
					map2.put("customsPoundageLastY", customsPoundageLastY);
					map2.put("otherPoundageLastY", otherPoundageLastY);
					map2.put("incomeLastY", incomeLastY);
					map2.put("orderCountLastY", orderCountLastY);
					
					map2.put("lendingAmountContrastY", lendingAmountContrastY);
					map2.put("interestContrastY", interestContrastY);
					map2.put("fineContrastY", fineContrastY);
					map2.put("serviceChargeContrastY", serviceChargeContrastY);
					map2.put("rebateMoneyContrastY", rebateMoneyContrastY);
					map2.put("customsPoundageContrastY", customsPoundageContrastY);
					map2.put("otherPoundageContrastY", otherPoundageContrastY);
					map2.put("incomeContrastY", incomeContrastY);
					map2.put("orderCountContrastY", orderCountContrastY);
				}
			}else if(map.containsKey("lendingTime")&&"本年".equals(MapUtils.getString(map, "lendingTime"))
					&&"cityName".equals(MapUtils.getString(map, "groupBy"))){
				//添加去年数据对比
				for (Map<String, Object> map2 : rList) {
					double lendingAmountLastY = MapUtils.getDoubleValue(MapUtils.getMap(lastYearContrast, MapUtils.getString(map2, "cityName")), "lendingAmount", 0.0);
					double interestLastY = MapUtils.getDoubleValue(MapUtils.getMap(lastYearContrast, MapUtils.getString(map2, "cityName")), "interest", 0.0);
					double fineLastY = MapUtils.getDoubleValue(MapUtils.getMap(lastYearContrast, MapUtils.getString(map2, "cityName")), "fine", 0.0);
					double serviceChargeLastY = MapUtils.getDoubleValue(MapUtils.getMap(lastYearContrast, MapUtils.getString(map2, "cityName")), "serviceCharge", 0.0);
					double rebateMoneyLastY = MapUtils.getDoubleValue(MapUtils.getMap(lastYearContrast, MapUtils.getString(map2, "cityName")), "rebateMoney", 0.0);
					double customsPoundageLastY = MapUtils.getDoubleValue(MapUtils.getMap(lastYearContrast, MapUtils.getString(map2, "cityName")), "customsPoundage", 0.0);
					double otherPoundageLastY = MapUtils.getDoubleValue(MapUtils.getMap(lastYearContrast, MapUtils.getString(map2, "cityName")), "otherPoundage", 0.0);
					double incomeLastY = MapUtils.getDoubleValue(MapUtils.getMap(lastYearContrast, MapUtils.getString(map2, "cityName")), "income", 0.0);
					double orderCountLastY = MapUtils.getDoubleValue(MapUtils.getMap(lastYearContrast, MapUtils.getString(map2, "cityName")), "orderCount", 0.0);
					String lendingAmountContrastY=contrastYPercent(MapUtils.getDoubleValue(map2, "lendingAmount", 0.0),lendingAmountLastY);
					String interestContrastY=contrastYPercent(MapUtils.getDoubleValue(map2, "interest", 0.0),interestLastY);
					String fineContrastY=contrastYPercent(MapUtils.getDoubleValue(map2, "fine", 0.0),fineLastY);
					String serviceChargeContrastY=contrastYPercent(MapUtils.getDoubleValue(map2, "serviceCharge", 0.0),serviceChargeLastY);
					String rebateMoneyContrastY=contrastYPercent(MapUtils.getDoubleValue(map2, "rebateMoney", 0.0),rebateMoneyLastY);
					String customsPoundageContrastY=contrastYPercent(MapUtils.getDoubleValue(map2, "customsPoundage", 0.0),customsPoundageLastY);
					String otherPoundageContrastY=contrastYPercent(MapUtils.getDoubleValue(map2, "otherPoundage", 0.0),otherPoundageLastY);
					String incomeContrastY=contrastYPercent(MapUtils.getDoubleValue(map2, "income", 0.0),incomeLastY);
					String orderCountContrastY=contrastYPercent(MapUtils.getDoubleValue(map2, "orderCount", 0.0),orderCountLastY);
					map2.put("lendingAmountLastY", lendingAmountLastY);
					map2.put("interestLastY",interestLastY );
					map2.put("fineLastY", fineLastY);
					map2.put("serviceChargeLastY", serviceChargeLastY);
					map2.put("rebateMoneyLastY", rebateMoneyLastY);
					map2.put("customsPoundageLastY", customsPoundageLastY);
					map2.put("otherPoundageLastY", otherPoundageLastY);
					map2.put("incomeLastY", incomeLastY);
					map2.put("orderCountLastY", orderCountLastY);
					
					map2.put("lendingAmountContrastY", lendingAmountContrastY);
					map2.put("interestContrastY", interestContrastY);
					map2.put("fineContrastY", fineContrastY);
					map2.put("serviceChargeContrastY", serviceChargeContrastY);
					map2.put("rebateMoneyContrastY", rebateMoneyContrastY);
					map2.put("customsPoundageContrastY", customsPoundageContrastY);
					map2.put("otherPoundageContrastY", otherPoundageContrastY);
					map2.put("incomeContrastY", incomeContrastY);
					map2.put("orderCountContrastY", orderCountContrastY);
				}
			}
			
			if(rList.size()==1&&"cityName".equals(MapUtils.getString(map, "groupBy"))){
				Map<String,Object> r2 = new HashMap<String, Object>();
				r2.putAll(rList.get(0));
				r2.put("cityName", MapUtils.getString(map, "cityName"));
				rList.add(r2);
				count = 1;
			}
			
			//时间区间补零
			if(!"cityName".equals(MapUtils.getString(map, "groupBy"))){
				//初始化页面，按时间排序
				if(StringUtil.isBlank(MapUtils.getString(map, "sortName"))) {
					map.put("sortName", "lendingTimeStr");
				}
				List<Map<String,Object>> listDate;
				if(map.containsKey("lendingTime")&&MapUtils.getString(map, "lendingTime").contains("年")){
					listDate = queryDate(MapUtils.getString(map, "startTime"),MapUtils.getString(map, "endTime"),"月");
				}else{
					listDate = queryDate(MapUtils.getString(map, "startTime"),MapUtils.getString(map, "endTime"),"日");
				}
				Map<String,Object> lendingTimeRListMap = new HashMap<String, Object>();
				Map<String,Object> r2 = new HashMap<String, Object>();
				for (Map<String, Object> map2 : rList){
					lendingTimeRListMap.put(MapUtils.getString(map2, "lendingTimeStr"), map2);
				}
				for (Map<String, Object> map2 : listDate) {
					if(!lendingTimeRListMap.containsKey(MapUtils.getString(map2, "lendingTimeStr"))){
						r2 = new HashMap<String, Object>();
						r2.putAll(mapNull);
						r2.put("lendingTimeStr", MapUtils.getString(map2, "lendingTimeStr"));
						rList.add(r2);
					}
				}
				count = rList.size()-1;
			}
			
			//如果有排序参数sortName、sortOrder,按参数排序
			if(map.containsKey("sortName")&&StringUtils.isNotBlank(MapUtils.getString(map,"sortName"))){
				Map<String,Object> r1 = rList.remove(0);
				if(map.containsKey("sortName")&&"lendingTimeStr".equals(MapUtils.getString(map, "sortName"))){
					rList = compareToListByDate(rList,map,MapUtils.getString(map,"sortName"),MapUtils.getString(map, "sortOrder", "asc"));
				}else{
					rList = compareToList(rList,MapUtils.getString(map,"sortName"),MapUtils.getString(map, "sortOrder", "asc"));
				}
				//分页参数截取数据
				List<Map<String,Object>> subList = new ArrayList<Map<String,Object>>();
				int start = MapUtils.getIntValue(map, "start");
				int pageSize = MapUtils.getIntValue(map, "pageSize");
				if(pageSize>0){
					if(rList.size()<start+pageSize){
						subList.addAll(rList.subList(start, rList.size()));
					}else{
						subList.addAll(rList.subList(start, start+pageSize));
					}
					//添加全责发生制利息
					if((MapUtils.getString(map, "lendingTime","").contains("月")||MapUtils.getString(map, "lendingTime","").contains("年"))
							&&MapUtils.getString(map, "groupBy","").contains("lendingTime")) {
						Map<String,Object> r2 = new HashMap<String,Object>();
						r2.putAll(r1);;
						r2.put("interest", nf.format(interestAll));
						r2.put("fine", null);
						r1.put("lendingTimeStr", "总计(计算口径:收付实现制)");
						r2.put("lendingTimeStr", "总计(计算口径:权责发生制)");
						subList.add(0, r1);
						subList.add(1, r2);
					}else{
						subList.add(0, r1);
					}
					rList = subList;
				}else{
					rList.add(0,r1);
				}
				
			}
			
			resp.setRows(rList);
			resp.setTotal(count);
		} catch (ParseException e) {
			e.printStackTrace();
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(RespStatusEnum.FAIL.getMsg());
			return resp;
		}
		resp.setCode(RespStatusEnum.SUCCESS.getCode());
		resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		return resp;
	}
	
	/**
	 * 获取目标完成率统计
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 */
	@Override
	public RespPageData<Map<String,Object>> aim(@RequestBody Map<String,Object> map){
		RespPageData<Map<String,Object>> resp = new RespPageData<Map<String,Object>>();
		//没有城市参数返回失败
		if(!map.containsKey("cityName")||(map.containsKey("cityName")&&StringUtil.isBlank(MapUtils.getString(map, "cityName")))){
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(RespStatusEnum.PARAMETER_ERROR.getMsg());
			return resp;
		}
		//查询目标放款量和创收
		int month=0;
		int year = DateUtils.getNowYear();
		map.put("year", year);
		if(map.containsKey("lendingTime")&&"本月".equals(MapUtils.getString(map, "lendingTime"))){
			month = DateUtils.getNowMonth();
			map.put("month", month);
		}else if(map.containsKey("lendingTime")&&"上月".equals(MapUtils.getString(map, "lendingTime"))){
			month = DateUtils.getLastMonth();
			map.put("month", month);
		}else if(map.containsKey("lendingTime")&&"去年".equals(MapUtils.getString(map, "lendingTime"))){
			map.put("year", year-1);
		}
		
		//查询放款量和创收
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String lendingStartTime="";
		String lendingEndTime="";
		if(map.containsKey("lendingTime")&&"上月".equals(MapUtils.getString(map, "lendingTime"))){
			lendingStartTime = sdf.format(DateUtils.getBeginDayOfLastMonth());
			lendingEndTime = sdf.format(DateUtils.getEndDayOfLastMonth()) ;
		}else if(map.containsKey("lendingTime")&&"去年".equals(MapUtils.getString(map, "lendingTime"))){
			lendingStartTime = sdf.format(DateUtils.getBeginDayOfLastYear());
			lendingEndTime = sdf.format(DateUtils.getEndDayOfLastYear()) ;
		}else if(map.containsKey("lendingTime")&&"本月".equals(MapUtils.getString(map, "lendingTime"))){
			lendingStartTime = sdf.format(DateUtils.getBeginDayOfMonth());
			lendingEndTime = sdf.format(DateUtils.getEndDayOfMonth()) ;
		}else if(map.containsKey("lendingTime")&&"本年".equals(MapUtils.getString(map, "lendingTime"))){
			lendingStartTime = sdf.format(DateUtils.getBeginDayOfYear());
			lendingEndTime = sdf.format(DateUtils.getEndDayOfYear()) ;
		}else{
			if(map.containsKey("startTime")){
				lendingStartTime = MapUtils.getString(map, "startTime");
			}
			if(map.containsKey("endTime")){
				lendingEndTime = MapUtils.getString(map, "endTime");
			}
		}
		if(StringUtil.isNotBlank(lendingStartTime)){
			map.put("startTime", lendingStartTime+" 00:00:00");
		}
		if(StringUtil.isNotBlank(lendingEndTime)){
			map.put("endTime", lendingEndTime+" 23:59:59");
		}
		//分组参数
		map.put("groupBy", "cityName");
		map.put("orderBy", "sum(lendingAmount) desc");
		
		//如果有排序参数sortName、sortOrder,按参数排序
		if(map.containsKey("sortName")&&StringUtils.isNotBlank(MapUtils.getString(map,"sortName"))){
			map.put("orderBy", MapUtils.getString(map,"sortName")+" "+MapUtils.getString(map, "sortOrder", "asc"));
		}
		
		//分页参数
		if(map.containsKey("pageSize")&&MapUtils.getIntValue(map, "pageSize")>0){
			map.put("limit", "limit 0,"+(MapUtils.getIntValue(map, "start")+MapUtils.getIntValue(map, "pageSize")));
		}
		//城市不限时
		if(map.containsKey("cityName")&&"不限".equals(MapUtils.getString(map, "cityName"))){
			map.put("cityName", "");
		}
		List<Map<String,Object>> aim = achievementStatisticsService.selectPersonalAim(map);
		Map<String,Object> loanAimMap = new HashMap<String, Object>();
		Map<String,Object> incomeAimMap = new HashMap<String, Object>();
		Map<String,Object> orderAimMap = new HashMap<String, Object>();
		//查询目标和目标总计
		int orderAim = 0;
		double loanAim = 0.0;
		double incomeAim = 0.0;
		for (Map<String, Object> map2 : aim) {
			orderAimMap.put(MapUtils.getString(map2, "cityCode"), MapUtils.getInteger(map2, "orderAim"));
			orderAimMap.put(MapUtils.getString(map2, "cityName"), MapUtils.getInteger(map2, "orderAim"));
			loanAimMap.put(MapUtils.getString(map2, "cityCode"), MapUtils.getDouble(map2, "loanAim"));
			loanAimMap.put(MapUtils.getString(map2, "cityName"), MapUtils.getDouble(map2, "loanAim"));
			incomeAimMap.put(MapUtils.getString(map2, "cityCode"), MapUtils.getDouble(map2, "incomeAim"));
			incomeAimMap.put(MapUtils.getString(map2, "cityName"), MapUtils.getDouble(map2, "incomeAim"));
			orderAim += MapUtils.getIntValue(map2, "orderAim",0);
			loanAim += MapUtils.getDoubleValue(map2, "loanAim",0.0);
			incomeAim += MapUtils.getDoubleValue(map2, "incomeAim",0.0);
		}
		List<Map<String,Object>> list = achievementStatisticsService.selectLendingOrders(map);
		DecimalFormat nf = new DecimalFormat("0.####"); 
		//加总计行
		double lendingAmount = 0.0;
		int orderCount = 0;
		double interest = 0.0;
		double fine = 0.0;
		double serviceCharge = 0.0;
		double rebateMoney = 0.0;
		double customsPoundage = 0.0;
		double otherPoundage = 0.0;
		double income = 0.0;
		Map<String,Object> zj = new HashMap<String, Object>();
		List<Map<String,Object>> rList = new ArrayList<Map<String,Object>>();
		rList.add(zj);
		//查询总计数据
		Map<String,Object> m = achievementStatisticsService.selectLendingOrdersSum(map);
		lendingAmount = MapUtils.getDoubleValue(m, "lendingAmount",0.0);
		interest = MapUtils.getDoubleValue(m, "interest",0.0);
		fine = MapUtils.getDoubleValue(m, "fine",0.0);
		serviceCharge = MapUtils.getDoubleValue(m, "serviceCharge",0.0);
		rebateMoney = MapUtils.getDoubleValue(m, "rebateMoney",0.0);
		customsPoundage = MapUtils.getDoubleValue(m, "customsPoundage",0.0);
		otherPoundage = MapUtils.getDoubleValue(m, "otherPoundage",0.0);
		income = MapUtils.getDoubleValue(m, "income",0.0);
		orderCount = MapUtils.getIntValue(m, "orderCount",0);
		for (Map<String, Object> map2 : list) {
			int orderCountTemp = MapUtils.getIntValue(map2, "orderCount",0);
			int orderAimTemp = MapUtils.getIntValue(orderAimMap, MapUtils.getString(map2, "cityCode"));
			Double lendingAmountTemp = MapUtils.getDoubleValue(map2, "lendingAmount",0.0);
			Double loanAimTemp = MapUtils.getDouble(loanAimMap, MapUtils.getString(map2, "cityCode"));
			Double incomeTemp = MapUtils.getDoubleValue(map2, "income",0.0);
			Double incomeAimTemp = MapUtils.getDouble(incomeAimMap, MapUtils.getString(map2, "cityCode"));
			String orderAimRate = "";
			String loanAimRate = "";
			String incomeAimRate = "";
			if(loanAimTemp!=null&&loanAimTemp>0){
				loanAimRate = nf.format(lendingAmountTemp / loanAimTemp * 100);
			}
			if(incomeAimTemp!=null&&incomeAimTemp>0){
				incomeAimRate = nf.format(incomeTemp / incomeAimTemp * 100);
			}
			if(orderAimTemp>0) {
				orderAimRate = nf.format(Double.valueOf(orderCountTemp)/Double.valueOf(orderAimTemp)*100);
			}
			map2.put("orderAim", orderAimTemp);
			map2.put("loanAim", loanAimTemp);
			map2.put("incomeAim", incomeAimTemp);
			if(StringUtil.isNotBlank(orderAimRate)){
				orderAimRate += "%";
			}
			if(StringUtil.isNotBlank(loanAimRate)){
				loanAimRate += "%";
			}
			if(StringUtil.isNotBlank(incomeAimRate)){
				incomeAimRate += "%";
			}
			map2.put("orderAimRate", orderAimRate);
			map2.put("loanAimRate", loanAimRate);
			map2.put("incomeAimRate", incomeAimRate);
			//计算总计行
//			lendingAmount += MapUtils.getDoubleValue(map2, "lendingAmount",0.0);
//			interest += MapUtils.getDoubleValue(map2, "interest",0.0);
//			fine += MapUtils.getDoubleValue(map2, "fine",0.0);
//			serviceCharge += MapUtils.getDoubleValue(map2, "serviceCharge",0.0);
//			income += MapUtils.getDoubleValue(map2, "income",0.0);
//			orderCount += MapUtils.getIntValue(map2, "orderCount",0);
			
			rList.add(map2);
		}
		//查询所有的目标总计
		
		//loanAim += MapUtils.getDoubleValue(map2, "loanAim",0.0);
		//incomeAim += MapUtils.getDoubleValue(map2, "incomeAim",0.0);
		String zjOrderAimRate="";
		String zjLoanAimRate="";
		String zjIncomeAimRate="";
		if(orderAim>0) {
			zjOrderAimRate = nf.format(Double.valueOf(orderCount) / Double.valueOf(orderAim) * 100);
		}
		if(loanAim>0){
			zjLoanAimRate = nf.format(lendingAmount / loanAim * 100);
		}
		if(incomeAim>0){
			zjIncomeAimRate = nf.format(income / incomeAim * 100);
		}
		if(StringUtil.isNotBlank(zjOrderAimRate)){
			zjOrderAimRate += "%";
		}
		if(StringUtil.isNotBlank(zjLoanAimRate)){
			zjLoanAimRate += "%";
		}
		if(StringUtil.isNotBlank(zjIncomeAimRate)){
			zjIncomeAimRate += "%";
		}
		zj.put("cityName", "总计");
		zj.put("lendingAmount", nf.format(lendingAmount));
		zj.put("interest", nf.format(interest));
		zj.put("fine", nf.format(fine));
		zj.put("serviceCharge", nf.format(serviceCharge));
		zj.put("rebateMoney", nf.format(rebateMoney));
		zj.put("customsPoundage", nf.format(customsPoundage));
		zj.put("otherPoundage", nf.format(otherPoundage));
		zj.put("income", nf.format(income));
		zj.put("orderCount", orderCount);
		zj.put("orderAim", nf.format(orderAim));
		zj.put("orderAimRate", zjOrderAimRate);
		zj.put("loanAim", nf.format(loanAim));
		zj.put("loanAimRate", zjLoanAimRate);
		zj.put("incomeAim", nf.format(incomeAim));
		zj.put("incomeAimRate", zjIncomeAimRate);
		rList.set(0,zj);
		//如果有排序参数sortName、sortOrder,按参数排序
		if(map.containsKey("sortName")&&StringUtils.isNotBlank(MapUtils.getString(map,"sortName"))){
			Map<String,Object> r1 = rList.remove(0);
			rList = compareToList(rList,MapUtils.getString(map,"sortName"),MapUtils.getString(map, "sortOrder", "asc"));
			rList.add(0, r1);
		}
		
		Map<String,Object> mapNull = new HashMap<String, Object>();
		mapNull.put("lendingAmount", 0);
		mapNull.put("interest", 0);
		mapNull.put("fine", 0);
		mapNull.put("serviceCharge", 0);
		mapNull.put("rebateMoney", 0);
		mapNull.put("customsPoundage", 0);
		mapNull.put("otherPoundage", 0);
		mapNull.put("income", 0);
		mapNull.put("orderCount", 0);
		
		//某个城市，无数据时，填充0
		if(StringUtil.isBlank(MapUtils.getString(map, "cityName"))&&"cityName".equals(MapUtils.getString(map, "groupBy"))){
			List<DictDto> cityListTemp = dataApi.getDictDtoListByType("bookingSzAreaOid");
			Map<String,Object> r2 = new HashMap<String, Object>();
			Map<String,Object> cityRListMap = new HashMap<String, Object>();
			for (Map<String, Object> map2 : rList){
				cityRListMap.put(MapUtils.getString(map2, "cityName"), map2);
			}
			for (DictDto dictDto : cityListTemp) {
				if(StringUtils.isEmpty(dictDto.getPcode())
						&&!cityRListMap.containsKey(dictDto.getName())){
					r2 = new HashMap<String, Object>();
					r2.putAll(mapNull);
					r2.put("cityName", dictDto.getName());
					int orderAimTemp = MapUtils.getIntValue(orderAimMap, dictDto.getCode());
					Double loanAimTemp = MapUtils.getDouble(loanAimMap, dictDto.getCode());
					Double incomeAimTemp = MapUtils.getDouble(incomeAimMap, dictDto.getCode());
					r2.put("orderAim", orderAimTemp);
					r2.put("loanAim", loanAimTemp);
					r2.put("incomeAim", incomeAimTemp);
					rList.add(r2);
				}
			}
		}else if(rList.size()==1&&"cityName".equals(MapUtils.getString(map, "groupBy"))){
			Map<String,Object> r2 = new HashMap<String, Object>();
			r2.putAll(rList.get(0));
			r2.put("cityName", MapUtils.getString(map, "cityName"));
			int orderAimTemp = MapUtils.getIntValue(orderAimMap, MapUtils.getString(map, "cityName"));
			Double loanAimTemp = MapUtils.getDouble(loanAimMap, MapUtils.getString(map, "cityName"));
			Double incomeAimTemp = MapUtils.getDouble(incomeAimMap, MapUtils.getString(map, "cityName"));
			r2.put("orderAim", orderAimTemp);
			r2.put("loanAim", loanAimTemp);
			r2.put("incomeAim", incomeAimTemp);
			rList.add(r2);
		}
		resp.setRows(rList);
		resp.setTotal(rList.size()-1);
		resp.setCode(RespStatusEnum.SUCCESS.getCode());
		resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		return resp;
	}
	
	/**
	 * 查询订单放款量，利息等
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 */
	@Override
	public RespDataObject<OrderLendingStatistics> queryLendingStatistics(@RequestBody OrderLendingStatistics orderLendingStatistics){
		RespDataObject<OrderLendingStatistics> resp = new RespDataObject<OrderLendingStatistics>();
		OrderLendingStatistics lendingStatistics = achievementStatisticsService.selectLendingStatisticsByOrderNo(orderLendingStatistics);
		RespHelper.setSuccessDataObject(resp, lendingStatistics);
		return resp;
	}
	
	/**
	 * 更新统计罚息和创收
	 * @param request
	 * @param response
	 * @param orderLendingStatistics
	 * @return
	 */
	@Override
	public RespStatus updateLendingStatistics(@RequestBody OrderLendingStatistics orderLendingStatistics){
		RespStatus resp = new RespStatus();
		achievementStatisticsService.updateLendingStatisticsByOrderNo(orderLendingStatistics);
		RespHelper.setSuccessRespStatus(resp);
		return resp;
	}
	
	/**
	 * 返回环比百分数
	 * @param d1
	 * @param d2
	 * @return
	 */
	public String contrastPercent(double d1,double d2){
		DecimalFormat df = new DecimalFormat("0.####");
		String contrastPercent="";
		if(d2>0){
			contrastPercent = df.format((d1-d2<0?d2-d1:d1-d2) / d2 * 100);
			if(StringUtil.isNotBlank(contrastPercent)){
				contrastPercent += "%";
				if(d1-d2<0){
					contrastPercent = "-"+contrastPercent;
				}
			}
		}
		return contrastPercent;
	}
	
	/**
	 * 返回同比百分数
	 * @param d1
	 * @param d2
	 * @return
	 */
	public String contrastYPercent(double d1,double d2){
		DecimalFormat df = new DecimalFormat("0.####");
		String contrastPercent="";
		if(d2>0){
			contrastPercent = df.format(d1 / d2 * 100);
			if(StringUtil.isNotBlank(contrastPercent)){
				contrastPercent += "%";
			}
		}
		return contrastPercent;
	}
	
	/**
	 * 获取城市对比数据，比如上月，去年同期放款量，单量，利息，罚息，服务费，创收
	 * @param map
	 * @return
	 */
	public Map<String,Map<String,Object>> queryContrastData(Map<String,Object> map){
		Map<String,Map<String,Object>> rMap = new HashMap<String, Map<String,Object>>();
		String lendingStartTime=MapUtils.getString(map, "lendingStartTime");
		String lendingEndTime=MapUtils.getString(map, "lendingEndTime");
		
		if(StringUtil.isNotBlank(lendingStartTime)){
			map.put("startTime", lendingStartTime+" 00:00:00");
		}
		if(StringUtil.isNotBlank(lendingEndTime)){
			map.put("endTime", lendingEndTime+" 23:59:59");
		}
		//分组参数
		if(map.containsKey("groupBy")&&"cityName".equals(MapUtils.getString(map,"groupBy"))){
			map.put("groupBy", "cityName");
			if(map.containsKey("orderBy")&&"单量".equals(MapUtils.getString(map,"orderBy"))){
				map.put("orderBy", "orderCount desc");
			}else if(map.containsKey("orderBy")&&"创收".equals(MapUtils.getString(map,"orderBy"))){
				map.put("orderBy", "income desc");
			}else{
				map.put("orderBy", "sum(lendingAmount) desc");
			}
			
		}else{
			if(map.containsKey("lendingTime")&&MapUtils.getString(map, "lendingTime").contains("年")){
				map.put("groupBy", "date_format(lendingTime, '%Y-%m')");
			}else{
				map.put("groupBy", "date_format(lendingTime, '%Y-%m-%d')");
			}
			map.put("orderBy", "date_format(lendingTime, '%Y-%m-%d')");
		}
		//如果有排序参数sortName、sortOrder,按参数排序
		if(map.containsKey("sortName")&&StringUtils.isNotBlank(MapUtils.getString(map,"sortName"))){
			map.put("orderBy", MapUtils.getString(map,"sortName")+" "+MapUtils.getString(map, "sortOrder", "asc"));
		}
		//分页参数
		if(map.containsKey("pageSize")&&MapUtils.getIntValue(map, "pageSize")>0){
			map.put("limit", "limit 0,"+(MapUtils.getIntValue(map, "start")+MapUtils.getIntValue(map, "pageSize")));
		}
		//城市不限时
		if(map.containsKey("cityName")&&"不限".equals(MapUtils.getString(map, "cityName"))){
			map.put("cityName", "");
		}
		List<Map<String,Object>> list = achievementStatisticsService.selectLendingOrders(map);
		double lendingAmount = 0.0;
		int orderCount = 0;
		double interest = 0.0;
		double fine = 0.0;
		double serviceCharge = 0.0;
		double rebateMoney = 0.0;
		double customsPoundage = 0.0;
		double otherPoundage = 0.0;
		double income = 0.0;
		Map<String,Object> zj = new HashMap<String, Object>();
		List<Map<String,Object>> rList = new ArrayList<Map<String,Object>>();
		rList.add(zj);
		//查询总计数据
		Map<String,Object> m = achievementStatisticsService.selectLendingOrdersSum(map);
		lendingAmount = MapUtils.getDoubleValue(m, "lendingAmount",0.0);
		interest = MapUtils.getDoubleValue(m, "interest",0.0);
		fine = MapUtils.getDoubleValue(m, "fine",0.0);
		serviceCharge = MapUtils.getDoubleValue(m, "serviceCharge",0.0);
		rebateMoney = MapUtils.getDoubleValue(m, "rebateMoney",0.0);
		customsPoundage = MapUtils.getDoubleValue(m, "customsPoundage",0.0);
		otherPoundage = MapUtils.getDoubleValue(m, "otherPoundage",0.0);
		income = MapUtils.getDoubleValue(m, "income",0.0);
		orderCount = MapUtils.getIntValue(m, "orderCount",0);
		for (Map<String,Object> map2 : list) {
			rList.add(map2);
			rMap.put(MapUtils.getString(map2, "cityName"), map2);
		}
		if(map.containsKey("groupBy")&&"cityName".equals(MapUtils.getString(map,"groupBy"))){
			zj.put("cityName", "总计");
		}else{
			zj.put("lendingTimeStr", "总计");
		}
		DecimalFormat nf = new DecimalFormat("0.####");
		zj.put("lendingAmount", nf.format(lendingAmount));
		zj.put("interest", nf.format(interest));
		zj.put("fine", nf.format(fine));
		zj.put("serviceCharge", nf.format(serviceCharge));
		zj.put("rebateMoney", nf.format(rebateMoney));
		zj.put("customsPoundage", nf.format(customsPoundage));
		zj.put("otherPoundage", nf.format(otherPoundage));
		zj.put("income", nf.format(income));
		zj.put("orderCount", orderCount);
		rMap.put("总计", zj);
		//rList.set(0,zj);
		return rMap;
	}
	
	/**
	 * 对list排序
	 * @param list
	 * @param filed
	 * @return
	 */
	public List<Map<String,Object>> compareToList(List<Map<String,Object>> list,final String filed,final String sortOrder){
		Collections.sort(list, new Comparator<Map<String, Object>>() {
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
            	if("desc".equals(sortOrder)){
            		return MapUtils.getDouble(o2, filed, 0.0).compareTo(MapUtils.getDouble(o1, filed, 0.0));
            	}else{
            		return MapUtils.getDouble(o1, filed, 0.0).compareTo(MapUtils.getDouble(o2, filed, 0.0));
            	}
            	
            }
        });
		return list;
	}
	
	/**
	 * 对list按日期排序
	 * @param list
	 * @param filed
	 * @return
	 */
	public List<Map<String,Object>> compareToListByDate(List<Map<String,Object>> list,final Map<String,Object> map,final String filed,final String sortOrder){
		 Collections.sort(list, new Comparator<Map<String, Object>>() {  
	            @Override  
	            public int compare(Map<String, Object> o1, Map<String, Object> o2) {  
	            	SimpleDateFormat format;
	            	if(map.containsKey("lendingTime")&&MapUtils.getString(map, "lendingTime").contains("年")){
	            		format = new SimpleDateFormat("yyyy-MM");  
	            	}else{
	            		format = new SimpleDateFormat("yyyy-MM-dd");  
	            	}
	                try {  
	                    Date dt1 = format.parse(MapUtils.getString(o1, "lendingTimeStr"));  
	                    Date dt2 = format.parse(MapUtils.getString(o2, "lendingTimeStr"));  
	                    if (dt1.getTime() > dt2.getTime()) {  
	                        return 1;  
	                    } else if (dt1.getTime() < dt2.getTime()) {  
	                        return -1;  
	                    } else {  
	                        return 0;  
	                    }  
	                } catch (Exception e) {  
	                    e.printStackTrace();  
	                }  
	                return 0;  
	            }  
	        });  
		 return list;
	}
	
	/**
	 * 返回时间区间集合
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<Map<String,Object>> queryDate(String startTime,String endTime,String type){
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		SimpleDateFormat format;
		if(type.equals("月")){
			format = new SimpleDateFormat("yyyy-MM");
		}else{
			format = new SimpleDateFormat("yyyy-MM-dd");
		}
        Date start = null;
        Date end = null;
        Date nowDate = null;
		try {
			start = format.parse(startTime);
			end = format.parse(endTime);
			String nowDateStr = format.format(new Date());
			nowDate = format.parse(nowDateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
        Map<String,Object> map = new HashMap<String, Object>();
        if(type.equals("月")){
        	Calendar cal = Calendar.getInstance();
			cal.setTime(start);
			String eTimeStr = format.format(end);
			map.put("lendingTimeStr", format.format(start));
			list.add(map);
			for (int i = 0; i < 100; i++) {
				cal.add(Calendar.MONTH, 1);
				start = cal.getTime();
				String sTimeStr = format.format(start);
				System.out.println(sTimeStr);
				map = new HashMap<String, Object>();
				map.put("lendingTimeStr", format.format(start));
				list.add(map);
				if(!start.before(nowDate)||eTimeStr.equals(sTimeStr)){
					break;
				}
			}
        }else{
			Calendar cal = Calendar.getInstance();
			cal.setTime(start);
			String eTimeStr = format.format(end);
			map.put("lendingTimeStr", format.format(start));
			list.add(map);
			for (int i = 0; i < 1000; i++) {
				cal.add(Calendar.DAY_OF_MONTH, 1);
				start = cal.getTime();
				String sTimeStr = format.format(start);
				map = new HashMap<String, Object>();
				map.put("lendingTimeStr", format.format(start));
				list.add(map);
				if(!start.before(nowDate)||eTimeStr.equals(sTimeStr)){
					break;
				}
			}
        }
        return list;
	}

	@Override
	public RespPageData<OrderLendingStatistics> selectOrderDetailList(@RequestBody OrderLendingStatistics orderLendingStatistics) {
		RespPageData<OrderLendingStatistics> resp = new RespPageData<OrderLendingStatistics>();
		List<OrderLendingStatistics> orderLendingStatisticsList = achievementStatisticsService.selectOrderDetailList(orderLendingStatistics);
		resp.setRows(orderLendingStatisticsList);
		resp.setTotal(achievementStatisticsService.selectOrderDetailCount(orderLendingStatistics));
		resp.setCode(RespStatusEnum.SUCCESS.getCode());
		resp.setCode(RespStatusEnum.SUCCESS.getMsg());
		return resp;
	}
	
	
	/*public static void main(String[] args) {
		String startTime = "2018-05-28";
		String endTime = "2018-06-01";
		String startTime2 = "2018-06-28";
		String endTime2 = "2018-06-30";
		int i = queryIntersectionDate(startTime, endTime, startTime2, endTime2);
		System.out.println(i);
	}*/

	
	
}
