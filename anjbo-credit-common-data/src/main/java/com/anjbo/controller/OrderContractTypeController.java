package com.anjbo.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.bean.order.OrderListDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.OrderContractTypeService;

@Controller
@RequestMapping("/credit/common/contract/type/v")
public class OrderContractTypeController extends BaseController {

	@Resource
	private OrderContractTypeService orderContractTypeService;
	
	
	/**
	 * 查询订单合同列表
	 * @param request
	 * @param orderListDto
	 * @return
	 */
	@RequestMapping("list")
	@ResponseBody
	public RespDataObject<Map<String, Object>> list(HttpServletRequest request,@RequestBody OrderListDto orderListDto) {
		RespDataObject<Map<String, Object>> resp = new RespDataObject<Map<String, Object>>();
		UserDto userDto = getUserDto(request); // 获取用户信息
		try {
			resp.setData(orderContractTypeService.list());
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(RespStatusEnum.FAIL.getMsg());
			e.printStackTrace();
		}
		return resp;
	}
	
	
}
