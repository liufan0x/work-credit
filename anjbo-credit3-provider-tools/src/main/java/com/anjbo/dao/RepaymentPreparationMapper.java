/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.anjbo.bean.RepaymentPreparationDto;
import com.anjbo.dao.BaseMapper;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-06-25 16:16:15
 * @version 1.0
 */
public interface RepaymentPreparationMapper extends BaseMapper<RepaymentPreparationDto>{

	List<RepaymentPreparationDto> repaymentPreparationListMap(@Param(value="orderNos") String orderNos);
		
}
