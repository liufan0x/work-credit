package com.anjbo.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface OrderBusinfoTypeMapper {
	 /**
     * 查询一级业务分类
     * @param orderNo
     * @return
     */
    List<Map<String, Object>> getParentBusInfoTypes(Map<String,Object> map);
    
    /**
     * 根据父id查询二级业务分类
     * @param parentId
     * @return
     */
    List<Map<String, Object>> getSonBusInfoTypes(Map<String, Object> map);
    
    /**
     * 查询所有子类型
     * @param map
     * @return
     */
    List<Map<String, Object>> getAllType(Map<String, Object> map);
    
    List<Map> getAllBusType();
    
    List<Map<String,Object>> getSonType(Map<String,Object> map);
    
    /**
     * 业务产品必传影像资料种类
     * @param map
     * @return
     */
    int mustBusInfoCount(Map<String,Object> map);
    /***
     * 面签需要的影像资料数量
     * @param map
     * @return
     */
    int mustFaceBusInfoCount(Map<String,Object> map);
    
    List<Map<String,Object>> searchByProductCode(@Param("productCode")String productCode);
}
