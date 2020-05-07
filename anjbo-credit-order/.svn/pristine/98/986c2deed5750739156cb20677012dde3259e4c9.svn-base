package com.anjbo.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.bean.order.OrderBaseHouseLendingDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespStatus;
import com.anjbo.service.OrderBaseHouseLendingService;

@Controller
@RequestMapping("/credit/order/house/lending/v")
public class OrderBaseHouseLendingController extends BaseController {

	Logger log = Logger.getLogger(OrderBaseHouseLendingController.class);
	
	@Resource
	private OrderBaseHouseLendingService orderBaseHouseLendingService;
	
	/**
	 * 查询放款信息
	 * @param request
	 * @param orderBaseHouseLendingDto
	 * @return
	 */
	@RequestMapping("query")
	@ResponseBody
	public RespDataObject<OrderBaseHouseLendingDto> query(HttpServletRequest request,
			@RequestBody OrderBaseHouseLendingDto orderBaseHouseLendingDto) {
		RespDataObject<OrderBaseHouseLendingDto> resp = new RespDataObject<OrderBaseHouseLendingDto>();
		try {
			String orderNo = orderBaseHouseLendingDto.getOrderNo();
			orderBaseHouseLendingDto =  orderBaseHouseLendingService.selectOrderHouseLendingByOrderNo(orderNo);
			RespHelper.setSuccessDataObject(resp, orderBaseHouseLendingDto);
		} catch (Exception e) {
			log.error("查询放款信息失败");
			RespHelper.setFailDataObject(resp, null, "查询放款信息失败");
			e.printStackTrace();
		}
		return resp;
	}
	
	/**
	 * 更新放款信息:房抵贷
	 * @param request
	 * @param orderBaseHouseLendingDto
	 * @return
	 */
	@RequestMapping("update")
	@ResponseBody
	public RespStatus updateHouseLending(HttpServletRequest request,
			@RequestBody OrderBaseHouseLendingDto orderBaseHouseLendingDto) {
		RespStatus respStatus = new RespStatus();
		try {
			if(isSubmit(orderBaseHouseLendingDto.getOrderNo(), "placeOrder")){
				RespHelper.setFailRespStatus(respStatus, "订单已提交审核");
				return respStatus;
			}
			String orderNo = orderBaseHouseLendingDto.getOrderNo();
			UserDto userDto = getUserDto(request); // 获取用户信息
			OrderBaseHouseLendingDto houseLendingDto = orderBaseHouseLendingService.selectOrderHouseLendingByOrderNo(orderNo);
			if(houseLendingDto!=null){
				orderBaseHouseLendingDto.setUpdateUid(userDto.getUid());
				orderBaseHouseLendingService.updateOrderHouseLendingNull(orderBaseHouseLendingDto);
			}else{
				orderBaseHouseLendingDto.setCreateUid(userDto.getUid());
				orderBaseHouseLendingService.saveOrderHouseLending(orderBaseHouseLendingDto);
			}
			RespHelper.setSuccessRespStatus(respStatus);
		} catch (Exception e) {
			log.error("更新放款信息:房抵贷失败");
			RespHelper.setFailRespStatus(respStatus, "更新放款信息:房抵贷失败");
			e.printStackTrace();
		}
		return respStatus;
	}	
}
