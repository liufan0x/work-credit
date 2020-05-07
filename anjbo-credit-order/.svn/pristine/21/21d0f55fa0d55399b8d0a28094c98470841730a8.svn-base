package com.anjbo.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.bean.order.OrderBaseReceivableForDto;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespStatus;
import com.anjbo.service.OrderBaseReceivableForService;

@Controller
@RequestMapping("/credit/order/receivablefor/v")
public class OrderBaseReceivableForController extends BaseController {

	Logger log = Logger.getLogger(OrderBaseReceivableForController.class);

	@Resource
	private OrderBaseReceivableForService orderBaseReceivableForService;

	@RequestMapping("save")
	@ResponseBody
	public RespStatus save(HttpServletRequest request,
			@RequestBody OrderBaseReceivableForDto orderBaseReceivableForDto) {
		RespStatus respStatus = new RespStatus();
		try {
			orderBaseReceivableForService
					.saveOrderBaseReceivableFor(orderBaseReceivableForDto);
			RespHelper.setSuccessRespStatus(respStatus);
		} catch (Exception e) {
			log.error("保存计划回款信息失败");
			RespHelper.setFailRespStatus(respStatus, "保存计划回款信息失败");
			e.printStackTrace();
		}
		return respStatus;
	}

	@RequestMapping("update")
	@ResponseBody
	public RespStatus update(HttpServletRequest request,
			@RequestBody OrderBaseReceivableForDto orderBaseReceivableForDto) {
		RespStatus respStatus = new RespStatus();
		try {
			if(isSubmit(orderBaseReceivableForDto.getOrderNo(), "placeOrder")){
				RespHelper.setFailRespStatus(respStatus, "订单已提交审核");
				return respStatus;
			}
			orderBaseReceivableForService
					.updateOrderReceivableFor(orderBaseReceivableForDto);
			RespHelper.setSuccessRespStatus(respStatus);
		} catch (Exception e) {
			log.error("更新计划回款信息失败");
			RespHelper.setFailRespStatus(respStatus, "更新计划回款信息失败");
			e.printStackTrace();
		}
		return respStatus;
	}

	@RequestMapping("query")
	@ResponseBody
	public RespDataObject<List<OrderBaseReceivableForDto>> query(
			HttpServletRequest request,
			@RequestBody OrderBaseReceivableForDto orderBaseReceivableForDto) {
		RespDataObject<List<OrderBaseReceivableForDto>> resp = new RespDataObject<List<OrderBaseReceivableForDto>>();
		try {
			List<OrderBaseReceivableForDto> list = orderBaseReceivableForService
					.selectOrderReceivableForByOrderNo(orderBaseReceivableForDto
							.getOrderNo());
			RespHelper.setSuccessDataObject(resp, list);
		} catch (Exception e) {
			log.error("查询计划回款信息失败");
			RespHelper.setFailDataObject(resp, null, "查询计划回款信息失败");
			e.printStackTrace();
		}
		return resp;
	}
}
