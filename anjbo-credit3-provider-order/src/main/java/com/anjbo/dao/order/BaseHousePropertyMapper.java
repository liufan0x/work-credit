/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.dao.order;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.anjbo.bean.order.BaseHousePropertyDto;
import com.anjbo.dao.BaseMapper;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 20:14:46
 * @version 1.0
 */
public interface BaseHousePropertyMapper extends BaseMapper<BaseHousePropertyDto>{
	List<Map<String, Object>> findAll(BaseHousePropertyDto baseHousePropertyDto);
}
