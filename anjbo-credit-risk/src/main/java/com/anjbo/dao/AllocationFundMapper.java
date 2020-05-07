package com.anjbo.dao;

import com.anjbo.bean.risk.AllocationFundDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AllocationFundMapper {
   
	List<AllocationFundDto> listDetail(String orderNo);
	
	AllocationFundDto detail(int id);
	
	int update(AllocationFundDto obj);
	
	int insert(AllocationFundDto obj);
	
	int insertBatch(List<AllocationFundDto> list);
	
	int deleteByOrderNo(String orderNo);
	
	List<AllocationFundDto> listFundByOrderNos(@Param("orderNo") String orderNo);

	/**
	 * 统计已放款总额
	 * @param list 订单号集合
	 * @return
	 */
	double selectSuccessLendingTotal(List<String> list);

	int updateByOrderNo(AllocationFundDto obj);
}