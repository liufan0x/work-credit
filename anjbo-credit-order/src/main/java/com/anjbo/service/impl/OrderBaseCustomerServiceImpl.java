package com.anjbo.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anjbo.bean.order.OrderBaseBorrowDto;
import com.anjbo.bean.order.OrderBaseCustomerBorrowerDto;
import com.anjbo.bean.order.OrderBaseCustomerDto;
import com.anjbo.bean.order.OrderBaseCustomerGuaranteeDto;
import com.anjbo.bean.order.OrderBaseCustomerShareholderDto;
import com.anjbo.bean.order.OrderListDto;
import com.anjbo.dao.OrderBaseBorrowMapper;
import com.anjbo.dao.OrderBaseCustomerBorrowerMapper;
import com.anjbo.dao.OrderBaseCustomerGuaranteeMapper;
import com.anjbo.dao.OrderBaseCustomerMapper;
import com.anjbo.dao.OrderBaseCustomerShareholderMapper;
import com.anjbo.dao.OrderBaseMapper;
import com.anjbo.service.OrderBaseCustomerService;
@Service
public class OrderBaseCustomerServiceImpl implements OrderBaseCustomerService {
	@Resource private OrderBaseMapper orderBaseMapper;
	@Resource private OrderBaseBorrowMapper orderBaseBorrowMapper;
	@Resource
	private OrderBaseCustomerMapper orderBaseCustomerMapper;
	@Resource
	private OrderBaseCustomerBorrowerMapper orderBaseCustomerBorrowerMapper;
	@Resource
	private OrderBaseCustomerGuaranteeMapper orderBaseCustomerGuaranteeMapper;	
	@Resource private OrderBaseCustomerShareholderMapper orderBaseCustomerShareholderMapper;

	@Override
	public int saveOrderCustomer(OrderBaseCustomerDto orderBaseCustomerDto) {
		return orderBaseCustomerMapper.saveOrderCustomer(orderBaseCustomerDto);
	}

	/**
	 * 更新客户信息
	 */
	@Override
	public int updateOrderCustomer(OrderBaseCustomerDto orderBaseCustomerDto) {
		// 更新共同借款人信息
		List<OrderBaseCustomerBorrowerDto> orderBaseCustomerBorrowerList = orderBaseCustomerDto.getCustomerBorrowerDto();		
		orderBaseCustomerBorrowerMapper.deleteBorrowerByOrderNo(orderBaseCustomerDto.getOrderNo());
		if(orderBaseCustomerBorrowerList.size()>0){
			for (int j = 0; j < orderBaseCustomerBorrowerList.size(); j++) {
				orderBaseCustomerBorrowerList.get(j).setOrderNo(orderBaseCustomerDto.getOrderNo());
				orderBaseCustomerBorrowerList.get(j).setCreateUid(orderBaseCustomerDto.getCreateUid());
				orderBaseCustomerBorrowerMapper.saveOrderCustomerBorrower(orderBaseCustomerBorrowerList.get(j));
			}
		}
		// 更新共同担保人信息
		List<OrderBaseCustomerGuaranteeDto> orderBaseCustomerGuaranteeList = orderBaseCustomerDto.getCustomerGuaranteeDto();
		orderBaseCustomerGuaranteeMapper.deleteGuaranteeByOrderNo(orderBaseCustomerDto.getOrderNo());
		if(orderBaseCustomerGuaranteeList.size()>0){
			for (int j = 0; j < orderBaseCustomerGuaranteeList.size(); j++) {
				orderBaseCustomerGuaranteeList.get(j).setOrderNo(orderBaseCustomerDto.getOrderNo());
				orderBaseCustomerGuaranteeList.get(j).setCreateUid(orderBaseCustomerDto.getCreateUid());
				orderBaseCustomerGuaranteeMapper.saveOrderCustomerGuarantee(orderBaseCustomerGuaranteeList.get(j));
			}
		}
		// 更新股权人信息
		List<OrderBaseCustomerShareholderDto> lstCustomerShareholder = orderBaseCustomerDto.getCustomerShareholderDto();
		orderBaseCustomerShareholderMapper.deleteByOrderNo(orderBaseCustomerDto.getOrderNo());
		if(lstCustomerShareholder.size()>0){
			for (int j = 0; j < lstCustomerShareholder.size(); j++) {
				lstCustomerShareholder.get(j).setOrderNo(orderBaseCustomerDto.getOrderNo());
				lstCustomerShareholder.get(j).setCreateUid(orderBaseCustomerDto.getCreateUid());
			}
			orderBaseCustomerShareholderMapper.batchInsert(lstCustomerShareholder);
		}
		
		// 更新客户信息
		orderBaseMapper.updateCustomerName(orderBaseCustomerDto.getOrderNo(), orderBaseCustomerDto.getCustomerName());
		orderBaseCustomerMapper.updateOrderCustomerNull(orderBaseCustomerDto);
		return 1;
	}
	
	/**
	 * 更新担保人信息
	 */
	@Override
	public int appUpdateCustomerGuarantee(OrderBaseCustomerDto orderBaseCustomerDto) {
		// 更新担保人信息
		List<OrderBaseCustomerGuaranteeDto> orderBaseCustomerGuaranteeList = orderBaseCustomerDto.getCustomerGuaranteeDto();
		orderBaseCustomerGuaranteeMapper.deleteGuaranteeByOrderNo(orderBaseCustomerDto.getOrderNo());
		if(orderBaseCustomerGuaranteeList.size()>0){
			for (int j = 0; j < orderBaseCustomerGuaranteeList.size(); j++) {
				orderBaseCustomerGuaranteeList.get(j).setOrderNo(orderBaseCustomerDto.getOrderNo());
				orderBaseCustomerGuaranteeList.get(j).setCreateUid(orderBaseCustomerDto.getCreateUid());
				orderBaseCustomerGuaranteeMapper.saveOrderCustomerGuarantee(orderBaseCustomerGuaranteeList.get(j));
			}
		}
		return 1;
	}
	
	/**
	 * 更新共同借款人信息
	 */
	@Override
	public int appUpdateCustomerBorrower(OrderBaseCustomerDto orderBaseCustomerDto) {
		// 更新共同借款人信息
		List<OrderBaseCustomerBorrowerDto> orderBaseCustomerBorrowerList = orderBaseCustomerDto.getCustomerBorrowerDto();
		orderBaseCustomerBorrowerMapper.deleteBorrowerByOrderNo(orderBaseCustomerDto.getOrderNo());
		if(orderBaseCustomerBorrowerList.size()>0){
			for (int j = 0; j < orderBaseCustomerBorrowerList.size(); j++) {
				orderBaseCustomerBorrowerList.get(j).setOrderNo(orderBaseCustomerDto.getOrderNo());
				orderBaseCustomerBorrowerList.get(j).setCreateUid(orderBaseCustomerDto.getCreateUid());
				orderBaseCustomerBorrowerMapper.saveOrderCustomerBorrower(orderBaseCustomerBorrowerList.get(j));
			}
		}
		return 1;
	}

	@Override
	public OrderBaseCustomerDto selectOrderCustomerByOrderNo(String orderNo) {
		OrderBaseCustomerDto orderBaseCustomerDto = orderBaseCustomerMapper.selectOrderCustomerByOrderNo(orderNo);
		if(null != orderBaseCustomerDto){
			orderBaseCustomerDto.setCustomerGuaranteeDto(orderBaseCustomerGuaranteeMapper.selectOrderCustomerGuaranteeByOrderNo(orderNo));  	//担保人
			orderBaseCustomerDto.setCustomerBorrowerDto(orderBaseCustomerBorrowerMapper.selectOrderCustomerBorrowerByOrderNo(orderNo));			//共同借款人
			orderBaseCustomerDto.setCustomerShareholderDto(orderBaseCustomerShareholderMapper.search(new OrderBaseCustomerShareholderDto(orderNo)));	//企业股东
		}
		return orderBaseCustomerDto;
	}

	@Override
	public int appUpdateOrderCustomer(OrderBaseCustomerDto orderBaseCustomerDto) {
		//更新借款人姓名
		String orderNo = orderBaseCustomerDto.getOrderNo();
		String customerName = orderBaseCustomerDto.getCustomerName();
		String updateUid = orderBaseCustomerDto.getUpdateUid();
		OrderBaseBorrowDto orderBaseBorrowDto = new OrderBaseBorrowDto();
		orderBaseBorrowDto.setOrderNo(orderNo);
		orderBaseBorrowDto.setBorrowerName(customerName);
		orderBaseBorrowDto.setUpdateUid(updateUid);
		orderBaseBorrowMapper.updateOrderBorrow(orderBaseBorrowDto);
		OrderListDto orderListDto = new OrderListDto();
		orderListDto.setOrderNo(orderNo);
		orderListDto.setCustomerName(customerName);
		orderListDto.setUpdateUid(updateUid);
		orderBaseMapper.updateOrderList(orderListDto);
		return orderBaseCustomerMapper.updateOrderCustomerNull(orderBaseCustomerDto);
	}

	@Override
	public int appUpdateCustomerShareholder(
			OrderBaseCustomerDto orderBaseCustomerDto) {
		// 更新共同企业股东信息
		List<OrderBaseCustomerShareholderDto> orderBaseCustomerShareholderList = orderBaseCustomerDto.getCustomerShareholderDto();
		orderBaseCustomerShareholderMapper.deleteByOrderNo(orderBaseCustomerDto.getOrderNo());
		if(orderBaseCustomerShareholderList.size()>0){
			for (int j = 0; j < orderBaseCustomerShareholderList.size(); j++) {
				orderBaseCustomerShareholderList.get(j).setOrderNo(orderBaseCustomerDto.getOrderNo());
				orderBaseCustomerShareholderList.get(j).setCreateUid(orderBaseCustomerDto.getCreateUid());
				orderBaseCustomerShareholderMapper.insert(orderBaseCustomerShareholderList.get(j));
			}
		}
		return 1;
	}

	@Override
	public int appUpdateOrderCustomerCompany(
			OrderBaseCustomerDto orderBaseCustomerDto) {
		return orderBaseCustomerMapper.updateOrderCustomerCompanyNull(orderBaseCustomerDto);
	}

	@Override
	public Map<Object, String> allCustomerNos(Map<Object, String> orderNos) {
		// TODO Auto-generated method stub
		return orderBaseCustomerMapper.allCustomerNos(orderNos);
	}
}
