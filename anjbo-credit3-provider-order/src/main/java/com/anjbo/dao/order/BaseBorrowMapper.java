/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.dao.order;

import org.apache.ibatis.annotations.Param;

import com.anjbo.bean.order.BaseBorrowDto;
import com.anjbo.dao.BaseMapper;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 20:14:44
 * @version 1.0
 */
public interface BaseBorrowMapper extends BaseMapper<BaseBorrowDto>{
	BaseBorrowDto selectOrderBorrowByOrderNo(@Param("orderNo")String orderNo);
}
