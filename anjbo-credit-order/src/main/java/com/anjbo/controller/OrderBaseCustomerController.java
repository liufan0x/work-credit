package com.anjbo.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.bean.order.OrderBaseBorrowRelationDto;
import com.anjbo.bean.order.OrderBaseCustomerDto;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespStatus;
import com.anjbo.service.OrderBaseBorrowRelationService;
import com.anjbo.service.OrderBaseCustomerService;

@Controller
@RequestMapping("/credit/order/customer/v")
public class OrderBaseCustomerController extends BaseController {

	Logger log = Logger.getLogger(OrderBaseCustomerController.class);

	@Resource
	private OrderBaseCustomerService orderBaseCustomerService;
	@Resource
	private OrderBaseBorrowRelationService orderBaseBorrowRelationService;

	@RequestMapping("/save")
	@ResponseBody
	public RespStatus save(HttpServletRequest request,
			@RequestBody OrderBaseCustomerDto orderBaseCustomerDto) {
		RespStatus respStatus = new RespStatus();
		try {
			orderBaseCustomerService.saveOrderCustomer(orderBaseCustomerDto);
			RespHelper.setSuccessRespStatus(respStatus);
		} catch (Exception e) {
			log.error("保存订单借款信息失败");
			RespHelper.setFailRespStatus(respStatus, "保存订单借款信息失败");
			e.printStackTrace();
		}
		return respStatus;
	}

	@RequestMapping("/update")
	@ResponseBody
	public RespStatus update(HttpServletRequest request,
			@RequestBody OrderBaseCustomerDto orderBaseCustomerDto) {
		RespStatus respStatus = new RespStatus();
		try {
			if(isSubmit(orderBaseCustomerDto.getOrderNo(), "placeOrder")){
				RespHelper.setFailRespStatus(respStatus, "订单已提交审核");
				return respStatus;
			}
			if(StringUtils.isBlank(orderBaseCustomerDto.getCreateUid())){
				orderBaseCustomerDto.setCreateUid(this.getUserDto(request).getUid());
			}
			orderBaseCustomerService.updateOrderCustomer(orderBaseCustomerDto);
			RespHelper.setSuccessRespStatus(respStatus);
		} catch (Exception e) {
			log.error("更新订单借款信息失败");
			RespHelper.setFailRespStatus(respStatus, "更新订单借款信息失败");
			e.printStackTrace();
		}
		return respStatus;
	}

	@RequestMapping("/query")
	@ResponseBody
	public RespDataObject<OrderBaseCustomerDto> query(
			HttpServletRequest request,
			@RequestBody OrderBaseCustomerDto orderBaseCustomer) {
		RespDataObject<OrderBaseCustomerDto> resp = new RespDataObject<OrderBaseCustomerDto>();
		try {
			String orderNo = orderBaseCustomer.getOrderNo();
			orderNo = getLoanOrderNo(orderNo);
			orderBaseCustomer.setOrderNo(orderNo);
			OrderBaseCustomerDto orderBaseCustomerDto = orderBaseCustomerService
					.selectOrderCustomerByOrderNo(orderBaseCustomer.getOrderNo());
			RespHelper.setSuccessDataObject(resp, orderBaseCustomerDto);
		} catch (Exception e) {
			log.error("查询订单借款信息失败");
			RespHelper.setFailDataObject(resp, null, "查询订单借款信息失败");
			e.printStackTrace();
		}
		return resp;
	}
	
	/*
	 * 根据orderNos集合查询数据
	 * */
	@RequestMapping("/allCustomerNos")
	@ResponseBody
	public RespDataObject<Map<Object,String>> allCustomerNos(
			@RequestBody Map<Object, String> orderNos) {
		RespDataObject<Map<Object,String>> resp = new RespDataObject<Map<Object,String>>();
		try {
			Map<Object, String> maps = new HashMap<Object, String>();
			if(orderNos==null || orderNos.size()<1)
			{
				resp.setMsg("没有相应的订单号查询");
			}
			maps = orderBaseCustomerService.allCustomerNos(orderNos);
			resp.setData(maps);
		} catch (Exception e) {
			log.error("查询订单关联信息失败");
			RespHelper.setFailDataObject(resp, null, "查询订单关联信息失败");
			e.printStackTrace();
		}
		return resp;
	}
	
	/**
	 * 获取债务置换贷款订单号
	 * @param orderNo
	 * @return
	 */
	public String getLoanOrderNo(String orderNo){
		OrderBaseBorrowRelationDto orderBaseBorrowRelationDto = orderBaseBorrowRelationService.selectRelationByOrderNo(orderNo);
		if(orderBaseBorrowRelationDto!=null && orderBaseBorrowRelationDto.getRelationOrderNo()!=null){
			return orderBaseBorrowRelationDto.getRelationOrderNo();
		}
		return orderNo;
	}
}
