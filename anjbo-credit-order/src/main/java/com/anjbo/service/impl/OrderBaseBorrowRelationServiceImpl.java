package com.anjbo.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anjbo.bean.common.DictDto;
import com.anjbo.bean.order.OrderBaseBorrowDto;
import com.anjbo.bean.order.OrderBaseBorrowRelationDto;
import com.anjbo.bean.order.OrderListDto;
import com.anjbo.common.Constants;
import com.anjbo.common.Enums;
import com.anjbo.dao.OrderBaseBorrowMapper;
import com.anjbo.dao.OrderBaseBorrowRelationMapper;
import com.anjbo.dao.OrderBaseMapper;
import com.anjbo.service.OrderBaseBorrowRelationService;
import com.anjbo.utils.CommonDataUtil;
import com.anjbo.utils.HttpUtil;
import com.anjbo.utils.UidUtil;
@Service
public class OrderBaseBorrowRelationServiceImpl implements OrderBaseBorrowRelationService{

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Resource
	private OrderBaseBorrowRelationMapper orderBaseBorrowRelationMapper;
	@Resource
	private OrderBaseBorrowMapper orderBaseBorrowMapper;
	@Resource
	private OrderBaseMapper orderBaseMapper;
	HttpUtil httpUtil = new HttpUtil();
	
	@Override
	public int savecd(OrderBaseBorrowRelationDto orderBaseBorrowRelationDto) {
		String orderNo = UidUtil.generateOrderId();
		orderBaseBorrowRelationDto.setOrderNo(orderNo);
		return orderBaseBorrowRelationMapper.saveOrderBorrowRelation(orderBaseBorrowRelationDto);
	}

	@Override
	public int updatecd(OrderBaseBorrowRelationDto orderBaseBorrowRelationDto) {
		orderBaseBorrowRelationMapper.updateOrderBorrowRelationNull(orderBaseBorrowRelationDto);
		// 更新列表畅贷信息
		OrderBaseBorrowDto orderBaseBorrowDto = orderBaseBorrowMapper.selectOrderBorrowByOrderNo(orderBaseBorrowRelationDto.getOrderNo());
		orderBaseBorrowDto.setOrderNo(orderBaseBorrowRelationDto
				.getOrderNo());
		orderBaseBorrowDto.setUpdateUid(orderBaseBorrowDto.getUpdateUid());
		orderBaseBorrowDto.setLoanAmount(orderBaseBorrowRelationDto
				.getLoanAmount());
		orderBaseBorrowDto.setBorrowingDays(orderBaseBorrowRelationDto.getBorrowingDays());
		orderBaseBorrowDto.setRate(orderBaseBorrowRelationDto
				.getRate());
		orderBaseBorrowDto.setOverdueRate(orderBaseBorrowRelationDto.getOverdueRate());
		orderBaseBorrowDto.setChargeMoney(orderBaseBorrowRelationDto.getChargeMoney());
		orderBaseBorrowDto.setCustomsPoundage(orderBaseBorrowRelationDto.getCustomsPoundage());
		orderBaseBorrowDto.setRiskGradeId(orderBaseBorrowRelationDto.getRiskGradeId());
		orderBaseBorrowDto.setOtherPoundage(orderBaseBorrowRelationDto.getOtherPoundage());
		saveOrUpdateOrderList(orderBaseBorrowDto, "update");
		return 1;
	}
	
	

	
	
	@Override
	public int deleteRelationByOrderNo(String orderNo){
		return orderBaseBorrowRelationMapper.deleteRelationByOrderNo(orderNo);
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

	/**
	 * 畅贷订单号查询
	 */
	@Override
	public OrderBaseBorrowRelationDto selectRelationByOrderNo(String orderNo) {
		OrderBaseBorrowRelationDto orderBaseBorrowRelationDto = orderBaseBorrowRelationMapper.selectRelationByOrderNo(orderNo);
		if(orderBaseBorrowRelationDto==null){
			return null;
		}
		return getOrderBaseBorrowRelationDto(orderBaseBorrowRelationDto);
	}
	
	public OrderBaseBorrowRelationDto getOrderBaseBorrowRelationDto(OrderBaseBorrowRelationDto orderBaseBorrowRelation){
		//收费类型名称
		List<DictDto> dicts = CommonDataUtil.getDictDtoByType("riskControl");
		if(dicts!=null&&dicts.size()>0){
			for (DictDto dictDto : dicts) {
						if(dictDto.getCode().equals(String.valueOf(orderBaseBorrowRelation.getRiskGradeId()))){
							orderBaseBorrowRelation.setRiskGrade(dictDto.getName());
						}
						if(orderBaseBorrowRelation.getRiskGradeId()!=null&&orderBaseBorrowRelation.getRiskGradeId()==0){
							orderBaseBorrowRelation.setRiskGrade("其他");
						}
				}
		}
		return orderBaseBorrowRelation;
	}

	@Override
	public List<OrderBaseBorrowRelationDto> selectOrderBorrowRelationByOrderNo(
			String orderNo) {
		return orderBaseBorrowRelationMapper.selectOrderBorrowRelationByOrderNo(orderNo);
	}

	/**
	 * 删除畅贷
	 */
	@Override
	public int deletecd(OrderBaseBorrowDto orderBaseBorrowDto) {
		orderBaseBorrowRelationMapper.deleteOrderBorrowRelation(orderBaseBorrowDto.getOrderNo());
		//删除列表畅贷
		if(orderBaseBorrowDto.getOrderBaseBorrowRelationDto()!=null&&orderBaseBorrowDto.getOrderBaseBorrowRelationDto().size()>0){
			orderBaseMapper.deleteOrderList(orderBaseBorrowDto.getOrderBaseBorrowRelationDto().get(0).getOrderNo());
		}
		return 1;
	}


}
