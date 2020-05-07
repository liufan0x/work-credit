package com.anjbo.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
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
import com.anjbo.service.OrderBaseBorrowService;
import com.anjbo.utils.CommonDataUtil;

@Controller
@RequestMapping("/credit/order/borrowother/v")
public class OrderBaseBorrowOtherController extends BaseController{

	Logger log = Logger.getLogger(OrderBaseBorrowOtherController.class);
	
	@Resource
	private OrderBaseBorrowService orderBaseBorrowService;
	@Resource
	private OrderBaseBorrowRelationService orderBaseBorrowRelationService;
	/**
	 * 部分更新借款信息或畅贷（不删除多次回款信息）
	 * @param request
	 * @param orderBaseBorrowDto
	 * @return
	 */
	@RequestMapping("updateBorrow")
	@ResponseBody
	public RespStatus updateBorrow(HttpServletRequest request,
			@RequestBody OrderBaseBorrowDto orderBaseBorrowDto) {
		RespStatus respStatus = new RespStatus();
		try {
			UserDto userDto = getUserDto(request); // 获取用户信息
			if(userDto!=null)
			orderBaseBorrowDto.setUpdateUid(userDto.getUid());
			orderBaseBorrowService.updateBorrow(orderBaseBorrowDto);
			RespHelper.setSuccessRespStatus(respStatus);
		} catch (Exception e) {
			log.error("更新订单借款信息失败");
			RespHelper.setFailRespStatus(respStatus, "更新订单借款信息失败");
			e.printStackTrace();
		}
		return respStatus;
	}
	
	@RequestMapping("queryBorrow")
	@ResponseBody
	public RespDataObject<OrderBaseBorrowDto> queryBorrow(HttpServletRequest request,
			@RequestBody OrderBaseBorrowDto orderBaseBorrow) {
		RespDataObject<OrderBaseBorrowDto> resp = new RespDataObject<OrderBaseBorrowDto>();
		try {
//			String orderNo = orderBaseBorrow.getOrderNo();
//			boolean changLoanFlag=isChangLoan(orderNo);
//			orderNo = getCreditOrderNo(orderNo);
//			orderBaseBorrow.setOrderNo(orderNo);
			OrderBaseBorrowDto orderBaseBorrowDto = orderBaseBorrowService
					.selectOrderBorrowByOrderNo(orderBaseBorrow.getOrderNo());
//			if(changLoanFlag){
//				orderBaseBorrowDto.setIsChangLoan(1);
//			}else{
//				orderBaseBorrowDto.setIsChangLoan(2);
//			}
			orderBaseBorrowDto.setElementName(CommonDataUtil.getUserDtoByUidAndMobile(orderBaseBorrowDto.getElementUid()).getName());
			RespHelper.setSuccessDataObject(resp, orderBaseBorrowDto);
		}catch (Exception e) {
			log.error("查询订单简单借款信息失败");
			RespHelper.setFailDataObject(resp, null, "查询订单简单借款信息失败");
			e.printStackTrace();
		}
		return resp;
	}
	
	/**
	 * 是否畅贷订单号
	 * @param orderNo
	 * @return
	 */
//	public boolean isChangLoan(String orderNo) {
//		OrderBaseBorrowRelationDto orderBaseBorrowRelationDto = orderBaseBorrowRelationService
//				.selectRelationByOrderNo(orderNo);
//		if (orderBaseBorrowRelationDto != null) {
//			return true;
//		} else {
//			return false;
//		}
//	}
}
