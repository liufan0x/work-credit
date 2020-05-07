package com.anjbo.dao;

import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.RequestParam;

import com.anjbo.bean.CustomerFundDto;
import com.anjbo.bean.StatisticsDto;

import com.anjbo.bean.order.BaseBorrowDto;
import com.anjbo.bean.order.BaseListDto;

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

	List<BaseListDto> fundOrderList(Map<String,Object> map);
	
	/**
	 * 华安列表
	 * @return
	 */
	int fundHAOrderCount(Map<String,Object> map);

	List<BaseListDto> fundHAOrderList(Map<String,Object> map);
	
	/**
	 * 其他资方列表
	 * @return
	 */
	int fundOtherOrderCount(Map<String,Object> map);

	List<BaseListDto> fundOtherOrderList(Map<String,Object> map);
	
	CustomerFundDto selectFundByUid(@RequestParam("uid") String uid);
	

	List<StatisticsDto> selectOrderList(Map<String,Object> param);

	int selectOrderCount(Map<String,Object> param);

	List<StatisticsDto> selectRiskList(Map<String,Object> param);

	int selectRiskCount(Map<String,Object> param);

	List<StatisticsDto> selectBackList(Map<String,Object> param);

	int selectBackCount(Map<String,Object> param);

	String selectHandTime(Map<String,Object> map);
	
	BaseBorrowDto queryHRBorrow(Map<String,Object> map);
	
	BaseBorrowDto queryHABorrow(Map<String,Object> map);
	
	BaseBorrowDto queryOtherBorrow(Map<String,Object> map);
	
	Map<String,Object> selectSendRiskInfo(String orderNo);
}