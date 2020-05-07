package com.anjbo.dao;

import java.util.List;
import java.util.Map;

import com.anjbo.bean.element.DocumentsDto;
import com.anjbo.bean.element.ElementListDto;
import com.anjbo.bean.element.ForeclosureTypeDto;
import com.anjbo.bean.element.PaymentTypeDto;


public interface ElementMapper {

	public int saveElementOrder(Map<String, Object> params);

	public int saveCredit(Map<String, Object> params);

	public List<ElementListDto> selectElementList(Map<String, Object> params);

	public int selectElementListCount(Map<String, Object> params);	
	
	//修改要件订单表存按钮显示状态
	public int operationStoreButton(int storeButton);	
	//修改要件订单表取按钮显示状态
	public int operationTakeButton(int takeButton);	
	//修改要件订单列表借按钮显示状态
	public int operationBorrowButton(int borrowButton);	
	//修改要件订单表还按钮显示状态
	public int operationReturnButton(Map<String,Object> params);
	//根据订单号查询订单信息
	public List<ElementListDto> selectElementByOrderNo(Map<String, Object> params);
	
	//修改订单信息
	public void UpdateElementByOrderNo(Map<String, Object> params);
	
	//修改信贷订单信息
	public void UpdateCreditElementByOrderNo(Map<String, Object> params);
	
	//修改订单信息状态
	public void UpdateElementStatusByOrderNo(Map<String, Object> params);
	
	
	//根据公章所属部门查询订单
	public List<ElementListDto> selectElementBydepartId(Map<String, Object> params);
	//同步修改信贷订单
	public void updateCreditElementOrder(Map<String, Object> params);
	
	public void updateTimeByOrderNo(Map<String, Object> params);
	
	
}