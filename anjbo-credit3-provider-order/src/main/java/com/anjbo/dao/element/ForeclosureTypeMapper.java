/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.dao.element;

import org.apache.ibatis.annotations.Param;

import com.anjbo.bean.element.ForeclosureTypeDto;
import com.anjbo.dao.BaseMapper;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 18:24:31
 * @version 1.0
 */
public interface ForeclosureTypeMapper extends BaseMapper<ForeclosureTypeDto>{
		ForeclosureTypeDto selects(@Param("orderNo") String orderNo);
}
