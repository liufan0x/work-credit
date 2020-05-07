package com.anjbo.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.bean.order.OrderBaseHousePropertyPeopleDto;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespStatus;
import com.anjbo.service.OrderBaseHousePropertyPeopleService;

@Controller
@RequestMapping("/credit/order/housepropertypeople/v")
public class OrderBaseHousePropertyPeopleController extends BaseController {

	Logger log = Logger.getLogger(OrderBaseHousePropertyPeopleController.class);

	@Resource
	private OrderBaseHousePropertyPeopleService orderBaseHousePropertyPeopleService;

	@RequestMapping("save")
	@ResponseBody
	public RespStatus save(
			HttpServletRequest request,
			@RequestBody OrderBaseHousePropertyPeopleDto orderBaseHousePropertyPeopleDto) {
		RespStatus respStatus = new RespStatus();
		try {
			orderBaseHousePropertyPeopleService
					.saveOrderPropertyPeople(orderBaseHousePropertyPeopleDto);
			RespHelper.setSuccessRespStatus(respStatus);
		} catch (Exception e) {
			log.error("保存产权人信息失败");
			RespHelper.setFailRespStatus(respStatus, "保存产权人信息失败");
			e.printStackTrace();
		}
		return respStatus;
	}

	@RequestMapping("update")
	@ResponseBody
	public RespStatus update(
			HttpServletRequest request,
			@RequestBody OrderBaseHousePropertyPeopleDto orderBaseHousePropertyPeopleDto) {
		RespStatus respStatus = new RespStatus();
		try {
			if(isSubmit(orderBaseHousePropertyPeopleDto.getOrderNo(), "placeOrder")){
				RespHelper.setFailRespStatus(respStatus, "订单已提交审核");
				return respStatus;
			}
			orderBaseHousePropertyPeopleService
					.updateOrderPropertyPeople(orderBaseHousePropertyPeopleDto);
			RespHelper.setSuccessRespStatus(respStatus);
		} catch (Exception e) {
			log.error("更新产权人信息失败");
			RespHelper.setFailRespStatus(respStatus, "更新产权人信息失败");
			e.printStackTrace();
		}
		return respStatus;
	}

	@RequestMapping("query")
	@ResponseBody
	public RespDataObject<List<OrderBaseHousePropertyPeopleDto>> query(
			HttpServletRequest request,
			@RequestBody OrderBaseHousePropertyPeopleDto orderBaseHousePropertyPeopleDto) {
		RespDataObject<List<OrderBaseHousePropertyPeopleDto>> resp = new RespDataObject<List<OrderBaseHousePropertyPeopleDto>>();
		try {
			List<OrderBaseHousePropertyPeopleDto> list = orderBaseHousePropertyPeopleService
					.selectOrderPropertyPeopleByOrderNo(orderBaseHousePropertyPeopleDto
							.getOrderNo());
			RespHelper.setSuccessDataObject(resp, list);
		} catch (Exception e) {
			log.error("查询产权人信息失败");
			RespHelper.setFailDataObject(resp, null, "查询产权人信息失败");
			e.printStackTrace();
		}
		return resp;
	}
}
