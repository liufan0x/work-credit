package com.anjbo.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anjbo.bean.order.OrderBaseBorrowDto;
import com.anjbo.bean.order.OrderBaseBorrowRelationDto;
import com.anjbo.bean.order.OrderBaseReceivableForDto;
import com.anjbo.bean.order.OrderListDto;
import com.anjbo.dao.OrderBaseBorrowMapper;
import com.anjbo.dao.OrderBaseBorrowRelationMapper;
import com.anjbo.dao.OrderBaseCustomerMapper;
import com.anjbo.dao.OrderBaseHouseMapper;
import com.anjbo.dao.OrderBaseHousePropertyPeopleMapper;
import com.anjbo.dao.OrderBaseMapper;
import com.anjbo.dao.OrderBaseReceivableForMapper;
import com.anjbo.service.OrderAppService;
@Service
public class OrderAppServiceImpl implements OrderAppService {
	
	@Resource
	private OrderBaseBorrowMapper orderBaseBorrowMapper;
	@Resource
	private OrderBaseHouseMapper orderBaseHouseMapper;
	@Resource
	private OrderBaseHousePropertyPeopleMapper orderBaseHousePropertyPeopleMapper;
	@Resource
	private OrderBaseCustomerMapper orderBaseCustomerMapper;
	@Resource
	private OrderBaseMapper orderBaseMapper;
	@Resource
	private OrderBaseReceivableForMapper orderBaseReceivableForMapper;
	@Resource
	private OrderBaseBorrowRelationMapper orderBaseBorrowRelationMapper;
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Override
	public int updateOrderBorrow(OrderBaseBorrowDto orderBaseBorrowDto) {
		orderBaseBorrowDto.setAgencyId(1);
		// 更新债务置换贷款订单列表信息
		saveOrUpdateOrderList(orderBaseBorrowDto, "update");
		orderBaseBorrowMapper.updateOrderBorrowNull(orderBaseBorrowDto);
		// 更新计划回款信息
		orderBaseReceivableForMapper.deleteReceivableFor(orderBaseBorrowDto
				.getOrderNo());
		List<OrderBaseReceivableForDto> orderBaseReceivableForDto = orderBaseBorrowDto
				.getOrderReceivableForDto();
		for (int j = 0; j < orderBaseReceivableForDto.size(); j++) {
			orderBaseReceivableForDto.get(j).setOrderNo(
					orderBaseBorrowDto.getOrderNo());
			orderBaseReceivableForDto.get(j).setCreateUid(
					orderBaseBorrowDto.getUpdateUid());
			orderBaseReceivableForMapper
					.saveOrderBaseReceivableFor(orderBaseReceivableForDto
							.get(j));
		}
		//有畅贷，更新列表畅贷
		List<OrderBaseBorrowRelationDto> orderBaseBorrowRelationList = orderBaseBorrowRelationMapper.selectOrderBorrowRelationByOrderNo(orderBaseBorrowDto.getOrderNo());
		if(orderBaseBorrowRelationList!=null&&orderBaseBorrowRelationList.size()>0){
			orderBaseBorrowDto.setOrderNo(orderBaseBorrowRelationList.get(0).getOrderNo());
			saveOrUpdateOrderList(orderBaseBorrowDto, "update");
		}
		return 1;
	}
	
	/**
	 * 录入或者更新基础订单列表
	 * 
	 * @param orderBaseBorrowDto
	 */
	public void saveOrUpdateOrderList(OrderBaseBorrowDto orderBaseBorrowDto,
			String operation) {
		OrderListDto orderListDto = new OrderListDto();
		orderListDto.setOrderNo(orderBaseBorrowDto.getOrderNo());
		orderListDto.setAgencyId(orderBaseBorrowDto.getAgencyId());
		orderListDto.setCityCode(orderBaseBorrowDto.getCityCode());
		orderListDto.setCityName(orderBaseBorrowDto.getCityName());
		orderListDto.setProductCode(orderBaseBorrowDto.getProductCode());
		orderListDto.setProductName(orderBaseBorrowDto.getProductName());
		//合作机构名称
		orderListDto.setCooperativeAgencyId(orderBaseBorrowDto.getCooperativeAgencyId());
		orderListDto.setCooperativeAgencyName(orderBaseBorrowDto.getCooperativeAgencyName());
		orderListDto.setBranchCompany(orderBaseBorrowDto.getBranchCompany());
		orderListDto.setCustomerName(orderBaseBorrowDto.getBorrowerName());
		orderListDto.setBorrowingAmount(orderBaseBorrowDto.getLoanAmount());
		if(orderBaseBorrowDto.getBorrowingDays()!=null){
			orderListDto.setBorrowingDay(orderBaseBorrowDto.getBorrowingDays());
		}
		orderListDto.setCooperativeAgencyId(orderBaseBorrowDto
				.getCooperativeAgencyId());
		orderListDto.setChannelManagerUid(orderBaseBorrowDto
				.getChannelManagerUid());
		orderListDto.setChannelManagerName(orderBaseBorrowDto
				.getChannelManagerName());
		orderListDto
				.setAcceptMemberUid(orderBaseBorrowDto.getAcceptMemberUid());
		orderListDto.setAcceptMemberName(orderBaseBorrowDto
				.getAcceptMemberName());
		orderListDto.setPreviousHandlerUid(orderBaseBorrowDto.getPreviousHandlerUid());
		orderListDto
				.setPreviousHandler(orderBaseBorrowDto.getPreviousHandler());
		orderListDto.setState(orderBaseBorrowDto.getState());
		orderListDto.setPreviousHandleTime(sdf.format(new Date()));
		orderListDto.setCurrentHandlerUid(orderBaseBorrowDto
				.getCurrentHandlerUid());
		orderListDto.setCurrentHandler(orderBaseBorrowDto.getNotarialName()+","+orderBaseBorrowDto.getFacesignName()+","+orderBaseBorrowDto.getAcceptMemberName());
		orderListDto.setProcessId(orderBaseBorrowDto.getProcessId());
		orderListDto.setSource(orderBaseBorrowDto.getSource());
		// 公证员面签员
		orderListDto.setNotarialUid(orderBaseBorrowDto.getNotarialUid());
		orderListDto.setFacesignUid(orderBaseBorrowDto.getFacesignUid());
		// 录入或更新
		if ("save".equals(operation)) {
			// 设置当前处理人Uid，也是创建人Uid
			orderListDto
					.setCurrentHandlerUid(orderBaseBorrowDto.getCreateUid());
			orderBaseMapper.insertOrderList(orderListDto);
		} else {
			orderListDto.setUpdateUid(orderBaseBorrowDto.getUpdateUid());
			orderBaseMapper.updateOrderList(orderListDto);
		}
	}

}
