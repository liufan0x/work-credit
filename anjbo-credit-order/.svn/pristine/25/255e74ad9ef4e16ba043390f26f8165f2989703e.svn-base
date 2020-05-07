package com.anjbo.dao;

import com.anjbo.bean.order.OrderBusinfoDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OrderBusinfoMapper {

	int saveOrderBusinfo(OrderBusinfoDto orderBusinfoDto);
	
	int updateOrderBusinfo(OrderBusinfoDto orderBusinfoDto);
	
	List<OrderBusinfoDto> selectOrderBusinfoByOrderNo(@Param("orderNo") String orderNo);
	
	 /**
     * 组合查询影像资料
     * @param map
     * @return
     */
    List<Map<String, Object>> selectListMap(Map<String, Object> map);
    /**
     * 查询最后的index的值
     * @param map
     * @return
     */
    int selectLastIndex(Map<String, Object> map);
	
	int deleteOrderBusinfo(Map<String,Object> map);
	
	int deleteImgByIds(Map<String,Object> map);
	
	Map<String,Object> getInfoTypeByName(String name);
	/**
	 * 已经上传的资料类型数
	 * @param orderNo
	 * @return
	 */
	int hasBusInfoCount(Map<String,Object> map);
	/**
	 * 面签资料已传种类
	 * @param orderNo
	 * @return
	 */
	int hasFaceBusInfoCount(Map<String,Object> map);

	/**
	 * 批量新增影像资料
	 * @param list
	 * @return
	 */
	int batchBusinfo(List<Map<String,Object>> list);
 }
