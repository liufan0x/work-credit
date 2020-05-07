/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.dao.order;

import java.util.List;
import java.util.Map;

import com.anjbo.bean.order.BusinfoTypeDto;
import com.anjbo.dao.BaseMapper;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 20:14:47
 * @version 1.0
 */
public interface BusinfoTypeMapper extends BaseMapper<BusinfoTypeDto>{
	
    List<Map<String, Object>> getParentBusInfoTypes(Map<String,Object> map);
    
    List<Map<String,Object>> getSonType(Map<String,Object> map);
    
    /**
     * 需要上传的面签资料数
     * @param map
     * @return
     */
    int mustFaceBusInfoCount(Map<String,Object> map);
}
