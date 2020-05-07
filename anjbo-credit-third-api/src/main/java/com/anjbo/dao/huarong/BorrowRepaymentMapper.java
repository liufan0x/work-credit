package com.anjbo.dao.huarong;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
/**
 * 应还款计划表
 * @author Administrator
 *
 */
public interface BorrowRepaymentMapper {
	
	int saveBorrowRepaymentMapper(Map<String,Object> list);
	Map getBorrowRepayment(@Param("orderNo") String  orderNo);
}
