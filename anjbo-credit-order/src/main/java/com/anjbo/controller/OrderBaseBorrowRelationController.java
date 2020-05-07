package com.anjbo.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.bean.order.OrderBaseBorrowDto;
import com.anjbo.bean.order.OrderBaseBorrowRelationDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespStatus;
import com.anjbo.service.OrderBaseBorrowRelationService;
@RequestMapping("/credit/order/relation/v")
@Controller
public class OrderBaseBorrowRelationController extends BaseController{

	@Resource
	private OrderBaseBorrowRelationService orderBaseBorrowRelationService;
	
	@RequestMapping("query")
	@ResponseBody
	public RespDataObject<OrderBaseBorrowRelationDto> query(HttpServletRequest request,
			@RequestBody OrderBaseBorrowRelationDto orderBaseBorrowRelationDto) {
		RespDataObject<OrderBaseBorrowRelationDto> resp = new RespDataObject<OrderBaseBorrowRelationDto>();
		try {
			orderBaseBorrowRelationDto = orderBaseBorrowRelationService.selectRelationByOrderNo(orderBaseBorrowRelationDto.getOrderNo());
			RespHelper.setSuccessDataObject(resp,orderBaseBorrowRelationDto);
		} catch (Exception e) {
			RespHelper.setFailDataObject(resp, null, "查询关联订单订单借款信息失败");
			e.printStackTrace();
		}
		return resp;
	}
	
	/**
	 * 删除畅贷信息
	 * @param request
	 * @param orderBaseBorrowRelationDto
	 * @return
	 */
	@RequestMapping("deletecd")
	@ResponseBody
	public RespStatus deletecd(HttpServletRequest request,
			@RequestBody OrderBaseBorrowDto orderBaseBorrowDto) {
		RespStatus respStatus = new RespStatus();
		try {
			orderBaseBorrowRelationService.deletecd(orderBaseBorrowDto);
			RespHelper.setSuccessRespStatus(respStatus);
		} catch (Exception e) {
			RespHelper.setFailRespStatus(respStatus, "删除畅贷信息失败");
			e.printStackTrace();
		}
		return respStatus;
	}
	
	/**
	 * 获取债务置换贷款订单号
	 * @param orderNo
	 * @return
	 */
	@RequestMapping("loanOrderNo")
	@ResponseBody
	public RespDataObject<OrderBaseBorrowRelationDto> getLoanOrderNo(HttpServletRequest request,@RequestBody OrderBaseBorrowRelationDto orderBaseBorrowRelationDto){
		RespDataObject<OrderBaseBorrowRelationDto> resp = new RespDataObject<OrderBaseBorrowRelationDto>();
		orderBaseBorrowRelationDto = orderBaseBorrowRelationService.selectRelationByOrderNo(orderBaseBorrowRelationDto.getOrderNo());
		if(orderBaseBorrowRelationDto!=null && orderBaseBorrowRelationDto.getRelationOrderNo()!=null){
			orderBaseBorrowRelationDto.setOrderNo(orderBaseBorrowRelationDto.getRelationOrderNo());
			RespHelper.setSuccessDataObject(resp,orderBaseBorrowRelationDto);
		}
		return RespHelper.setSuccessDataObject(resp,orderBaseBorrowRelationDto);
	}
	
	
}
