package com.anjbo.service;

import java.util.List;
import java.util.Map;

import com.anjbo.bean.customer.CustomerFundDto;
import com.anjbo.bean.order.OrderBaseBorrowDto;
import com.anjbo.bean.order.OrderListDto;
import com.anjbo.bean.report.StatisticsDto;
import com.anjbo.common.RespPageData;

public interface OrderBaseService {

	List<OrderListDto> fundOrderList(Map<String,Object> map);

	int fundOrderCount(Map<String,Object> map);

	RespPageData<StatisticsDto> queryBalance(RespPageData<StatisticsDto> result,Map<String,Object> params);

	RespPageData<StatisticsDto> managementExamination(RespPageData<StatisticsDto> result,Map<String,Object> params);

	RespPageData<StatisticsDto> returnBack(RespPageData<StatisticsDto> result,Map<String,Object> params);
	
	CustomerFundDto selectFundByUid(String uid);
	
	OrderBaseBorrowDto queryBorrow(Map<String,Object> params);
	
	Map<String,Object> selectSendRiskInfo(Map<String,Object> map);
	
	/*
	 * 要件管理
	 * */
	List<Map<String,Object>> selectElementList(Map<String,Object> param);
	/*
	 * 要件管理条数统计
	 * */
	int selectElementCount(Map<String,Object> param);
	/*
	 * 要件表信息查询
	 * */
	List<Map<String,Object>> selectAllElementList(Map<String,Object> param);
}
