package com.anjbo.service;

import java.util.List;
import java.util.Map;

import com.anjbo.bean.element.ElementListDto;
import com.anjbo.bean.order.OrderListDto;
import com.anjbo.bean.user.UserDto;

public interface ElementService {

	
	public  void saveElementOrder(Map<String, Object> params) throws Exception;
	
	/**
	 * 批量更新要件
	 * @param params
	 */
	public int updateElementFile(Map<String,Object> list);
	/**
	 * 更新要件客户基本信息
	 * @param params
	 * @throws Exception
	 */
	public void updateElementCustomerInfo(Map<String,Object> map);
	
	/**
	 * 获取修改要件详情
	 * @param map
	 * @return
	 */
	public Map<String,Object> updateElementDetail(Map<String,Object> map) throws Exception;
	
	/**
	 * 获取要件基本信息修改详情
	 * @param map
	 * @return
	 */
	public Map<String,Object> updateElementBaseInfoDetail(Map<String,Object> map);
	
	/**
	 * 更新要件信息
	 * @param map
	 * @return
	 */
	public int updateByMap(Map<String,Object> map);

	public void saveCredit(Map<String, Object> params) throws Exception;
	
	
	public List<ElementListDto> selectElementList(Map<String, Object> params,UserDto user);
	
	
	public int selectElementListCount(Map<String, Object> params);
	
	
	//查询暂存记录数据
	//public Map<String, Object>  selectAccessFlowTemp(Map<String, Object> params);
	
	//保存暂存记录数据
	public int  insertAccessFlowTemp(Map<String, Object> params);
	
	//保存存取流水记录数据
	public void  saveTakeElementInfo(Map<String, Object> params);
	
	//返回点击取按钮信息
	public Map<String, Object> retrunTakeButtonInfo(Map<String, Object> params);
	//保存退要件信息
	public void saveRefundElementInfo(Map<String, Object> params);
	//返回点击退按钮信息
	public Map<String, Object> retrunRefundButtonInfo(Map<String, Object> params);
	//要件列表点击进入的要件详情信息
	public Map<String, Object> selectElementDetailInfo(Map<String, Object> params);
	//获取存取记录列表
	public List<Map<String, Object>> selectAccessFlowList(Map<String, Object> params);
	//获取存取记录详情信息
	public Map<String, Object> selectAccessFlowDetailInfo(Map<String, Object> params);
	//返回点击存按钮的空模板信息
	public Map<String, Object> retrunStoreButtonInfo(Map<String, Object> params) throws Exception;
	//保存暂存存要件信息方法
	public void saveSuspendedStoreButtonInfo(Map<String, Object> params);
	//点击还按钮返回信息
	public Map<String, Object> retrunButtonInfo(Map<String, Object> params) throws Exception;
	//保存暂还要件信息方法
	public void saveSuspendedRetrunButtonInfo(Map<String, Object> params);
	//保存还要件信息方法
	public void saveReturnElementOrder(Map<String, Object> params);
	//查询公章所属部门
	public List<Map<String, Object>> selectSealDepartmentList(Map<String, Object> params);
	
	//返回该部门已存公章信息，刷新界面存公章数据
	public Map<String, Object> selectSealElementInfo(Map<String, Object> params);
	
	//根据公章所属部门查询该部门的公章订单
	public List<ElementListDto> selectElementBydepartId(Map<String, Object> params);
	
	//点击开箱按钮返回信息
	public Map<String, Object> retrunOpenButtonInfo(Map<String, Object> params);
	//保存开箱信息
	public void saveOpenElementInfo(Map<String, Object> params);
	//返回超时未还信息
	public Map<String, Object> retrunTimeOutInfo(Map<String, Object> params);
	//加载风控意见
	public Map<String, Object> retrunRiskOpinion(Map<String, Object> params);
	//同步信贷订单对象
	public void saveCreditOrderListDto(OrderListDto orderListDto);
	//通过orderNo查询订单对象
	public List<ElementListDto> selectElementByOrderNo(Map<String, Object> params);
	//信贷系统要件要件详情入口
	public Map<String,Object> receiveElementInfo(Map<String, Object> params);
}
