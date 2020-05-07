package com.anjbo.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.bean.order.OrderBaseCustomerBorrowerDto;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespStatus;
import com.anjbo.service.OrderBaseCustomerBorrowerService;

@Controller
@RequestMapping("/credit/order/customerborrower/v")
public class OrderBaseCustomerBorrowerController extends BaseController {

	Logger log = Logger.getLogger(OrderBaseCustomerBorrowerController.class);

	@Resource
	private OrderBaseCustomerBorrowerService orderBaseCustomerBorrowerService;

	@RequestMapping("save")
	@ResponseBody
	public RespStatus save(
			HttpServletRequest request,
			@RequestBody OrderBaseCustomerBorrowerDto orderBaseCustomerBorrowerDto) {
		RespStatus respStatus = new RespStatus();
		try {
			orderBaseCustomerBorrowerService
					.saveOrderCustomerBorrower(orderBaseCustomerBorrowerDto);
			RespHelper.setSuccessRespStatus(respStatus);
		} catch (Exception e) {
			log.error("保存共同借款人信息失败");
			RespHelper.setFailRespStatus(respStatus, "保存共同借款人信息失败");
			e.printStackTrace();
		}
		return respStatus;
	}

	@RequestMapping("update")
	@ResponseBody
	public RespStatus update(
			HttpServletRequest request,
			@RequestBody OrderBaseCustomerBorrowerDto orderBaseCustomerBorrowerDto) {
		RespStatus respStatus = new RespStatus();
		try {
			if(isSubmit(orderBaseCustomerBorrowerDto.getOrderNo(), "placeOrder")){
				RespHelper.setFailRespStatus(respStatus, "订单已提交审核");
				return respStatus;
			}
			orderBaseCustomerBorrowerService
					.updateOrderCustomerBorrow(orderBaseCustomerBorrowerDto);
			RespHelper.setSuccessRespStatus(respStatus);
		} catch (Exception e) {
			log.error("更新共同借款人信息失败");
			RespHelper.setFailRespStatus(respStatus, "更新共同借款人信息失败");
			e.printStackTrace();
		}
		return respStatus;
	}

	@RequestMapping("queryCustomerBorrower")
	@ResponseBody
	public RespDataObject<List<OrderBaseCustomerBorrowerDto>> query(
			HttpServletRequest request,
			@RequestBody OrderBaseCustomerBorrowerDto orderBaseCustomerBorrower) {
		RespDataObject<List<OrderBaseCustomerBorrowerDto>> resp = new RespDataObject<List<OrderBaseCustomerBorrowerDto>>();
		try {
			List<OrderBaseCustomerBorrowerDto> list = orderBaseCustomerBorrowerService
					.selectOrderCustomerBorrowerByOrderNo(orderBaseCustomerBorrower
							.getOrderNo());
			RespHelper.setSuccessDataObject(resp, list);
		} catch (Exception e) {
			log.error("查询共同借款人信息失败");
			RespHelper.setFailDataObject(resp, null, "查询共同借款人信息失败");
			e.printStackTrace();
		}
		return resp;
	}
}
