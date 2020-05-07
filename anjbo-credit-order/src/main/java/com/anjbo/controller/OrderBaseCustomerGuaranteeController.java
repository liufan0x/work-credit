package com.anjbo.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.bean.order.OrderBaseCustomerGuaranteeDto;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespStatus;
import com.anjbo.service.OrderBaseCustomerGuaranteeService;

@Controller
@RequestMapping("/credit/order/customerguarantee/v")
public class OrderBaseCustomerGuaranteeController extends BaseController {

	Logger log = Logger.getLogger(OrderBaseCustomerGuaranteeController.class);

	@Resource
	private OrderBaseCustomerGuaranteeService orderBaseCustomerGuaranteeService;

	@RequestMapping("save")
	@ResponseBody
	public RespStatus save(
			HttpServletRequest request,
			@RequestBody OrderBaseCustomerGuaranteeDto orderBaseCustomerGuaranteeDto) {
		RespStatus respStatus = new RespStatus();
		try {
			orderBaseCustomerGuaranteeService
					.saveOrderCustomerGuarantee(orderBaseCustomerGuaranteeDto);
			RespHelper.setSuccessRespStatus(respStatus);
		} catch (Exception e) {
			log.error("保存担保人信息失败");
			RespHelper.setFailRespStatus(respStatus, "保存担保人信息失败");
			e.printStackTrace();
		}
		return respStatus;
	}

	@RequestMapping("update")
	@ResponseBody
	public RespStatus update(
			HttpServletRequest request,
			@RequestBody OrderBaseCustomerGuaranteeDto orderBaseCustomerGuaranteeDto) {
		RespStatus respStatus = new RespStatus();
		try {
			if(isSubmit(orderBaseCustomerGuaranteeDto.getOrderNo(), "placeOrder")){
				RespHelper.setFailRespStatus(respStatus, "订单已提交审核");
				return respStatus;
			}
			orderBaseCustomerGuaranteeService
					.updateOrderCustomerGuarantee(orderBaseCustomerGuaranteeDto);
			RespHelper.setSuccessRespStatus(respStatus);
		} catch (Exception e) {
			log.error("更新担保人信息失败");
			RespHelper.setFailRespStatus(respStatus, "更新担保人信息失败");
			e.printStackTrace();
		}
		return respStatus;
	}

	@RequestMapping("query")
	@ResponseBody
	public RespDataObject<List<OrderBaseCustomerGuaranteeDto>> query(
			HttpServletRequest request,
			@RequestBody OrderBaseCustomerGuaranteeDto orderBaseCustomerGuaranteeDto) {
		RespDataObject<List<OrderBaseCustomerGuaranteeDto>> resp = new RespDataObject<List<OrderBaseCustomerGuaranteeDto>>();
		try {
			List<OrderBaseCustomerGuaranteeDto> list = orderBaseCustomerGuaranteeService
					.selectOrderCustomerGuaranteeByOrderNo(orderBaseCustomerGuaranteeDto
							.getOrderNo());
			RespHelper.setSuccessDataObject(resp, list);
		} catch (Exception e) {
			log.error("查询担保人信息失败");
			RespHelper.setFailDataObject(resp, null, "查询担保人信息失败");
			e.printStackTrace();
		}
		return resp;
	}
}
