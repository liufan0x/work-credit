package com.anjbo.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.bean.order.OrderBaseHousePurchaserDto;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespStatus;
import com.anjbo.service.OrderBaseHousePurchaserService;

@Controller
@RequestMapping("/credit/order/housepurchaser/v")
public class OrderBaseHousePurchaserController extends BaseController {

	Logger log = Logger.getLogger(OrderBaseHousePurchaserController.class);

	@Resource
	private OrderBaseHousePurchaserService orderBaseHousePurchaserService;

	@RequestMapping("save")
	@ResponseBody
	public RespStatus save(HttpServletRequest request,
			@RequestBody OrderBaseHousePurchaserDto orderBaseHousePurchaserDto) {
		RespStatus respStatus = new RespStatus();
		try {
			orderBaseHousePurchaserService
					.saveOrderBaseHousePurchaser(orderBaseHousePurchaserDto);
			RespHelper.setSuccessRespStatus(respStatus);
		} catch (Exception e) {
			log.error("保存买房人信息失败");
			RespHelper.setFailRespStatus(respStatus, "保存买房人信息失败");
			e.printStackTrace();
		}
		return respStatus;
	}

	@RequestMapping("update")
	@ResponseBody
	public RespStatus update(HttpServletRequest request,
			@RequestBody OrderBaseHousePurchaserDto orderBaseHousePurchaserDto) {
		RespStatus respStatus = new RespStatus();
		try {
			if(isSubmit(orderBaseHousePurchaserDto.getOrderNo(), "placeOrder")){
				RespHelper.setFailRespStatus(respStatus, "订单已提交审核");
				return respStatus;
			}
			orderBaseHousePurchaserService
					.updateOrderHousePurchaser(orderBaseHousePurchaserDto);
			RespHelper.setSuccessRespStatus(respStatus);
		} catch (Exception e) {
			log.error("更新买房人信息失败");
			RespHelper.setFailRespStatus(respStatus, "更新买房人信息失败");
			e.printStackTrace();
		}
		return respStatus;
	}

	@RequestMapping("query")
	@ResponseBody
	public RespDataObject<List<OrderBaseHousePurchaserDto>> query(
			HttpServletRequest request,
			@RequestBody OrderBaseHousePurchaserDto orderBaseHousePurchaserDto) {
		RespDataObject<List<OrderBaseHousePurchaserDto>> resp = new RespDataObject<List<OrderBaseHousePurchaserDto>>();
		try {
			List<OrderBaseHousePurchaserDto> list = orderBaseHousePurchaserService
					.selectOrderHousePurchaserByOrderNo(orderBaseHousePurchaserDto
							.getOrderNo());
			RespHelper.setSuccessDataObject(resp, list);
		} catch (Exception e) {
			log.error("查询买房人信息失败");
			RespHelper.setFailDataObject(resp, null, "查询买房人信息失败");
			e.printStackTrace();
		}
		return resp;
	}
}
