package com.anjbo.controller;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.bean.contract.OrderContractDataDto;
import com.anjbo.bean.order.OrderListDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.Constants;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.OrderContractDataService;
import com.anjbo.service.OrderContractListService;
import com.anjbo.utils.StringUtil;
import com.anjbo.utils.UidUtil;

@Controller
@RequestMapping("/credit/common/contract/list/v")
public class OrderContractListController extends BaseController {

	@Resource
	private OrderContractListService orderContractListService;
	@Resource
	private OrderContractDataService orderContractDataService;
	/**
	 * 新建订单合同
	 * @param request
	 * @param orderListDto
	 * @return
	 */
	@RequestMapping("save")
	@ResponseBody
	public RespDataObject<String> save(HttpServletRequest request,@RequestBody OrderListDto orderListDto) {
		RespDataObject<String> respDataObject = new RespDataObject<String>();
		String orderNo="";
		UserDto userDto = getUserDto(request); // 获取用户信息
		orderListDto.setCreateUid(userDto.getUid());
		orderListDto.setAgencyId(userDto.getAgencyId());
		if(StringUtil.isBlank(orderListDto.getOrderNo())){
			orderNo = UidUtil.generateOrderId();
			orderListDto.setOrderNo(orderNo);
			orderContractListService.insertOrderContractList(orderListDto);
		}else{
			orderNo = orderListDto.getOrderNo();
			orderListDto = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/order/base/v/selectDetailByOrderNo", orderListDto, OrderListDto.class);
			orderContractListService.insertOrderContractList(orderListDto);
		}
		//初始化所有合同
		OrderContractDataDto orderContractDataDto = new OrderContractDataDto();
		orderContractDataDto.setCreateUid(userDto.getUid());
		orderContractDataDto.setOrderNo(orderNo);
		orderContractDataService.insertOrderContractData(orderContractDataDto);
		RespHelper.setSuccessDataObject(respDataObject, orderNo);
		return respDataObject;
	}
	
	/**
	 * 查询订单合同列表
	 * @param request
	 * @param orderListDto
	 * @return
	 */
	@RequestMapping("list")
	@ResponseBody
	public RespPageData<OrderListDto> list(HttpServletRequest request,@RequestBody OrderListDto orderListDto) {
		RespPageData<OrderListDto> resp = new RespPageData<OrderListDto>();
		UserDto userDto = getUserDto(request); // 获取用户信息
		try {
			orderListDto.setLoginUid(userDto.getUid());
			resp.setTotal(orderContractListService.selectContractCount(orderListDto));
			resp.setRows(orderContractListService.selectContractList(orderListDto));
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(RespStatusEnum.FAIL.getMsg());
			e.printStackTrace();
		}
		return resp;
	}
	
	/**
	 * 查询可关联的订单
	 * @Author LiuF
	 * @rewrite 
	 * @param request
	 * @param map 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectContractAbleRelationOrder") 
	public RespData<Map<String, Object>> selectAbleRelationOrder(HttpServletRequest request,@RequestBody Map<String,Object> map){
		RespData<Map<String, Object>> resp = new RespData<Map<String, Object>>();
		try {
			if(null==map || !map.containsKey("customerName")){
				RespHelper.setFailData(resp, null, RespStatusEnum.PARAMETER_ERROR.getMsg());
				return resp;
			}
			UserDto userDto = getUserDto(request);
			String deptAllUid = "";
			//查看全部订单
			if(userDto.getAuthIds().contains("1")){
			//查看部门订单
			}else if(userDto.getAuthIds().contains("2")){
				userDto.setCreateTime(null);
				userDto.setUpdateTime(null);
				RespDataObject<Map<String,String>> respTemp = httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/user/base/v/selectUidsByDeptId",userDto , Map.class);
				deptAllUid = MapUtils.getString(respTemp.getData(), "uids");
			//查看自己订单
			}else{
				deptAllUid = userDto.getUid();
			}
			//处理过的订单
			Map<String,Object> m = new HashMap<String, Object>();
			m.put("uid", userDto.getUid());
			RespDataObject<Map<String,String>> respTemp = httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/order/base/v/selectCreditOrderNos",m , Map.class);
			String orderNos = MapUtils.getString(respTemp.getData(), "orderNos");
			if(StringUtils.isNotEmpty(orderNos)){
				map.put("orderNo", orderNos);
			}
			map.put("currentHandlerUid", deptAllUid);
			map.put("updateUid", userDto.getUid());
			List<Map<String, Object>> list = orderContractListService.selectContractAbleRelationOrder(map);
			if(list!=null){
				Iterator<Map<String, Object>> it = list.iterator();
				while(it.hasNext()) {
				  Map<String,Object> mapIt = it.next();
				  mapIt.put("id", MapUtils.getString(mapIt, "orderNo"));
				  mapIt.put("name", MapUtils.getString(mapIt, "customerName")+"， "+ NumberFormat.getInstance().format(Double.valueOf(MapUtils.getString(mapIt, "borrowingAmount","0"))) +"万， "+MapUtils.getString(mapIt, "borrowingDay","-")+"天");
				}
			}else{
				list = new ArrayList<Map<String,Object>>();
			}
			return RespHelper.setSuccessData(resp, list);
		} catch (Exception e) {
			e.printStackTrace();
			RespHelper.setFailData(resp, null, RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}
	
}
