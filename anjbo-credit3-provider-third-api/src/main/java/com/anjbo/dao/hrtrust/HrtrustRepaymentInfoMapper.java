package com.anjbo.dao.hrtrust;

import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 回款计划表
 * @author Administrator
 *
 */
public interface HrtrustRepaymentInfoMapper {
	
	int saveRepaymentRegister(Map<String, Object> map);
	Map<String,Object> getRepaymentRegister(@Param("orderNo") String orderNo);
}
