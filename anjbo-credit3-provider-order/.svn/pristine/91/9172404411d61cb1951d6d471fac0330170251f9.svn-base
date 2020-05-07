/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.dao.order;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.anjbo.bean.order.BaseListDto;
import com.anjbo.dao.BaseMapper;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-05-11 10:57:21
 * @version 1.0
 */
public interface BaseListMapper extends BaseMapper<BaseListDto>{
	 BaseListDto selectDetail(@Param("orderNo") String orderNo);

	List<BaseListDto> searchPageList(BaseListDto orderListDto);

	int searchPageListCount(BaseListDto orderListDto);
	
	BaseListDto findOne(BaseListDto orderListDto);

	List<BaseListDto> selectAbleRelationOrder(BaseListDto baseListDto);
}
