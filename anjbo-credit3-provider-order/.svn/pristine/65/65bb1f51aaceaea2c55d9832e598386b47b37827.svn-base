/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.dao.order;

import java.util.List;
import java.util.Map;

import com.anjbo.bean.order.BusinfoDto;
import com.anjbo.dao.BaseMapper;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 20:14:47
 * @version 1.0
 */
public interface BusinfoMapper extends BaseMapper<BusinfoDto>{
	/**
     * 组合查询影像资料
     * @param map
     * @return
     */
    List<Map<String, Object>> selectListMap(Map<String, Object> map);
    
    
    int insertAll(Map<String, Object> map);
    /**
	 * 批量新增影像资料
	 * @param list
	 * @return
	 */
	 int batchBusinfo(List<Map<String,Object>> list);

	 /**
     * 查询最后的index的值
     * @param map
     * @return
     */
     int selectLastIndex(Map<String, Object> map);
     
     int deleteaAll(Map<String, Object> map);
     
     /**
      * 已经上传的面签资料数
      * @param map
      * @return
      */
     int hasFaceBusInfoCount(Map<String, Object> map);
}
