package com.anjbo.dao.hrtrust;

import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 应还款计划表
 * @author Administrator
 *
 */
public interface HrtrustRepaymentPlanMapper {
	
	int saveBorrowRepaymentMapper(Map<String, Object> list);
	Map<String,Object> getBorrowRepayment(@Param("orderNo") String orderNo);
}
