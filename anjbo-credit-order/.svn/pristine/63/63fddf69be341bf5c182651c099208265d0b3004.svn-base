package com.anjbo.service;

import com.anjbo.bean.order.OrderBaseBorrowDto;
import com.anjbo.bean.order.OrderBaseHouseDto;
import com.anjbo.bean.order.OrderListDto;

import java.util.List;
import java.util.Map;

public interface OrderBaseBorrowService {
	
	String saveOrderBorrow(OrderBaseBorrowDto orderBaseBorrowList);
	
	int updateOrderBorrow(OrderBaseBorrowDto orderBaseBorrowDto);
	
	/**
	 * app更新借款信息，不更新畅贷
	 * @param orderBaseBorrowDto
	 * @return
	 */
	int appUpdateBorrow(OrderBaseBorrowDto orderBaseBorrowDto);
	
	/**
	 * 部分更新借款信息，畅贷信息（不更新多次回款等数据）
	 * @param orderBaseBorrowDto
	 * @return
	 */
	int updateBorrow(OrderBaseBorrowDto orderBaseBorrowDto);
	
	OrderBaseBorrowDto selectOrderBorrowByOrderNo(String orderNo);
	
	int submitAudit(OrderListDto orderListDto);
	
	int reSubmit(OrderListDto orderListDto);
	
	List<OrderBaseBorrowDto> selectOrderBorrowList(OrderBaseBorrowDto orderBaseBorrowDto);
	
	int selectOrderBorrowCount(OrderBaseBorrowDto orderBaseBorrowDto);
	/**
	 * 提交审核校验订单信息
	 * @author ?
	 * @rewrite KangLG<2018年1月19日> 新增参数：产品及关联订单号
	 * @param productCode 产品编码，不含城市
	 * @param orderNo
	 * @param relationOrderNo 关联订单号
	 * @return
	 */
	Map<String,Object> checkOrder(String productCode, String orderNo, String relationOrderNo);
	
	public boolean isFinish(String title,String butUrl,String orderNo);

	/**
	 * 快鸽APP提单
	 * @param orderDto
	 * @param house
	 * @return
	 */
	public void kgAppInsertOrder(OrderBaseBorrowDto orderDto, OrderBaseHouseDto house);
	
	public int assignAcceptMember(OrderBaseBorrowDto order);
	
	public void addRiskList(String orderNo);
	
}
