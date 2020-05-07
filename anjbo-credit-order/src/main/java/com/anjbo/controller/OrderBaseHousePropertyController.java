package com.anjbo.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.bean.order.OrderBaseHousePropertyDto;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespStatus;
import com.anjbo.service.OrderBaseHousePropertyService;

@Controller
@RequestMapping("/credit/order/houseproperty/v")
public class OrderBaseHousePropertyController extends BaseController {

	Logger log = Logger.getLogger(OrderBaseHousePropertyController.class);

	@Resource
	private OrderBaseHousePropertyService orderBaseHousePropertyService;

	@RequestMapping("save")
	@ResponseBody
	public RespStatus save(HttpServletRequest request,
			@RequestBody OrderBaseHousePropertyDto orderBaseHousePropertyDto) {
		RespStatus respStatus = new RespStatus();
		try {
			orderBaseHousePropertyService
					.saveOrderHouseProperty(orderBaseHousePropertyDto);
			RespHelper.setSuccessRespStatus(respStatus);
		} catch (Exception e) {
			log.error("保存房产信息失败");
			RespHelper.setFailRespStatus(respStatus, "保存房产信息失败");
			e.printStackTrace();
		}
		return respStatus;
	}

	@RequestMapping("update")
	@ResponseBody
	public RespStatus update(HttpServletRequest request,
			@RequestBody OrderBaseHousePropertyDto orderBaseHousePropertyDto) {
		RespStatus respStatus = new RespStatus();
		try {
			if(isSubmit(orderBaseHousePropertyDto.getOrderNo(), "placeOrder")){
				RespHelper.setFailRespStatus(respStatus, "订单已提交审核");
				return respStatus;
			}
			orderBaseHousePropertyService
					.updateOrderPropertyHouse(orderBaseHousePropertyDto);
			RespHelper.setSuccessRespStatus(respStatus);
		} catch (Exception e) {
			log.error("更新房产信息失败");
			RespHelper.setFailRespStatus(respStatus, "更新房产信息失败");
			e.printStackTrace();
		}
		return respStatus;
	}

	@RequestMapping("query")
	@ResponseBody
	public RespDataObject<List<OrderBaseHousePropertyDto>> query(
			HttpServletRequest request,
			@RequestBody OrderBaseHousePropertyDto orderBaseHousePropertyDto) {
		RespDataObject<List<OrderBaseHousePropertyDto>> resp = new RespDataObject<List<OrderBaseHousePropertyDto>>();
		try {
			List<OrderBaseHousePropertyDto> list = orderBaseHousePropertyService
					.selectOrderHousePropertyByOrderNo(orderBaseHousePropertyDto
							.getOrderNo());
			RespHelper.setSuccessDataObject(resp, list);
		} catch (Exception e) {
			log.error("查询房产信息失败");
			RespHelper.setFailDataObject(resp, null, "查询房产信息失败");
			e.printStackTrace();
		}
		return resp;
	}
}
