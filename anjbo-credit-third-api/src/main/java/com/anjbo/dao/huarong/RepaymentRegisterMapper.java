package com.anjbo.dao.huarong;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
/**
 * 回款计划表
 * @author Administrator
 *
 */
public interface RepaymentRegisterMapper {
	
	int saveRepaymentRegister(Map<String,Object> map);
	Map getRepaymentRegister(@Param("orderNo") String  orderNo);
}
