package com.anjbo.dao;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestParam;

import com.anjbo.bean.customer.CustomerFundDto;
import com.anjbo.bean.order.OrderBaseBorrowDto;
import com.anjbo.bean.order.OrderListDto;
import com.anjbo.bean.report.StatisticsDto;

/**
 * 
 * @author lic
 * @date 2017-6-5
 */
public interface OrderBaseMapper {

	/**
	 * 华融列表
	 * @return
	 */
	int fundOrderCount(Map<String,Object> map);

	List<OrderListDto> fundOrderList(Map<String,Object> map);
	
	/**
	 * 华安列表
	 * @return
	 */
	int fundHAOrderCount(Map<String,Object> map);

	List<OrderListDto> fundHAOrderList(Map<String,Object> map);
	
	/**
	 * 其他资方列表
	 * @return
	 */
	int fundOtherOrderCount(Map<String,Object> map);

	List<OrderListDto> fundOtherOrderList(Map<String,Object> map);
	
	List<OrderListDto> fundOtherOrderLists(Map<String,Object> map);   //新加
	
	CustomerFundDto selectFundByUid(@RequestParam("uid") String uid);
	

	List<StatisticsDto> selectOrderList(Map<String,Object> param);

	int selectOrderCount(Map<String,Object> param);

	List<StatisticsDto> selectRiskList(Map<String,Object> param);

	int selectRiskCount(Map<String,Object> param);

	List<StatisticsDto> selectBackList(Map<String,Object> param);

	int selectBackCount(Map<String,Object> param);

	String selectHandTime(Map<String,Object> map);
	
	OrderBaseBorrowDto queryHRBorrow(Map<String,Object> map);
	
	OrderBaseBorrowDto queryHABorrow(Map<String,Object> map);
	
	OrderBaseBorrowDto queryYNBorrow(Map<String,Object> map);
	
	OrderBaseBorrowDto queryOtherBorrow(Map<String,Object> map);
	
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