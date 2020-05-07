package com.anjbo.service;

import com.anjbo.bean.order.OrderBusinfoDto;
import com.anjbo.bean.user.UserDto;

import java.util.List;
import java.util.Map;

public interface OrderBusinfoService {

	int saveOrderBusinfo(OrderBusinfoDto orderBusinfoDto);
	
	int updateOrderBusinfo(OrderBusinfoDto orderBusinfoDto);
	
	List<OrderBusinfoDto> selectOrderBusinfoByOrderNo(String orderNo);
	
	int deleteOrderBusinfo(Map<String,Object> map);
	
	int deleteImgByIds(Map<String,Object> map);
	
	public Map<String, Object> getBusinfoTypeTree(Map<String,Object> map);
	
	public void addBusinfo(Map<String, Object> map);
	
	/**
	 * 将图片移动位置
	 * @param map
	 */
	public void moveBusinfo(Map<String,Object> map);
	
	/**
	 * 删除影像记录
	 * @param map
	 */
	public void delBusinfo(Map<String,Object> map);
	
	/**
	 * 查看图片
	 * @return map
	 */
	public Map<String,Object> lookOver(Map<String, Object> map);
	
    /**
     * 查询所有子类型
     * @param map
     * @return
     */
    public Map<String, Object> getAllType(Map<String, Object> map);
    
    Map<String,Object> getInfoTypeByName(String name);
    
    public Map<String,Object> getBusinfoAndType(Map<String,Object> map);
    
    /**
     * 构建影像资料树
     * @Author KangLG<2018年3月1日>
     * @param productCode
     * @return
     */
    public List<Map<String, Object>> searchByProductCode(String productCode);
    
    /**
     * app查询畅贷影像资料或者债务置换贷款影像资料
     * @param map
     * @return
     */
    public Map<String, Object> getAppBusInfoByOrderNo(Map<String, Object> map);
    /**
	 * 已经上传的资料类型数
	 * @param orderNo
	 * @return
	 */
	int hasBusInfoCount(Map<String,Object> map);
	
	/**
	 * 校验面签影像资料
	 * @param orderNo
	 * @param productCode
	 * @return
	 */
	public boolean faceBusinfoCheck(String orderNo,String productCode,int auditSort);
	
	/**
	 * 校验房抵贷公证影像资料
	 * @param orderNo
	 * @param productCode
	 * @return
	 */
	public boolean notarizationBusinfoCheck(String orderNo,String productCode);

	/**
	 * 批量上传影像资料
	 * @param list
	 * @param orderNo
	 * @param user
	 * @return
	 */
	public int batchBusinfo(List<Map<String,Object>> list, String orderNo, UserDto user);
    
}
