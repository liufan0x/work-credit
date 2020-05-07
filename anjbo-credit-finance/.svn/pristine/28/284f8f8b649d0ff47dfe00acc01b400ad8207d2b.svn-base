package com.anjbo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.bean.common.DictDto;
import com.anjbo.bean.user.DeptDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.Constants;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.ReceivableForService;
import com.anjbo.utils.HttpUtil;

/**
 * App回款订单
 * 
 * @author jiangyq
 *
 */
@Controller
@RequestMapping("/credit/finance/receivable/app")
public class ReceivableAppController extends BaseController {

	@Resource
	ReceivableForService receivableForService;

	/**
	 * 查询月回款统计
	 * 
	 * @author jiangyq
	 * @param request
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/findPaymentStatistic")
	public RespDataObject<Map<String,Object>> findPaymentStatistic(HttpServletRequest request, @RequestBody Map<String, String> map) {
		RespDataObject<Map<String,Object>> resp = new RespDataObject<Map<String,Object>>();
		try {
			RespDataObject<Map<String,String>> respTemp = httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/user/base/selectUidsByUid", map, Map.class);
			
			if (!RespStatusEnum.SUCCESS.getCode().equals(respTemp.getCode())) {
				resp.setCode(respTemp.getCode());
				resp.setMsg(respTemp.getMsg());
				return resp;
			}
			
			String uids = MapUtils.getString(respTemp.getData(), "uids");
			String agencyId = MapUtils.getString(respTemp.getData(), "agencyId");
			
			map.put("uids", uids);
			RespDataObject<String> res = httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/order/app/findOrderNosByUids", map, String.class);
			
			if (!RespStatusEnum.SUCCESS.getCode().equals(res.getCode())) {
				resp.setCode(respTemp.getCode());
				resp.setMsg(respTemp.getMsg());
				return resp;
			}
			
			String orderNos = res.getData();
			
			Map<String,Object> dataMap = new HashMap<String,Object>();
			if (StringUtils.isNotBlank(orderNos)) {
				
				String[] s = orderNos.split(",");
				StringBuffer sbf = new StringBuffer("'");
				
				for (String str : s) {
					sbf.append(str);
					sbf.append("','");
				}
				orderNos = sbf.substring(0, sbf.length() - 2);
				
				List<Map<String,Object>> list = receivableForService.findPaymentStatisticMonth(agencyId, orderNos);
				Map<String,Object> totalMap = receivableForService.findPaymentStatisticTotal(agencyId, orderNos);
				
				dataMap.putAll(totalMap);
				dataMap.put("list",list);
			}
			
			RespHelper.setSuccessDataObject(resp, dataMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
	
	/**
	 * 查询月回款统计详情
	 * 
	 * @author jiangyq
	 * @param request
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/findPaymentStatisticDetail")
	public RespDataObject<Map<String,Object>> findPaymentStatisticDetail(HttpServletRequest request, @RequestBody Map<String, String> map) {
		RespDataObject<Map<String,Object>> resp = new RespDataObject<Map<String,Object>>();
		try {
			RespDataObject<Map<String,String>> respTemp = httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/user/base/selectUidsByUid", map, Map.class);
			
			if (!RespStatusEnum.SUCCESS.getCode().equals(respTemp.getCode())) {
				resp.setCode(respTemp.getCode());
				resp.setMsg(respTemp.getMsg());
				return resp;
			}
			
			String uids = MapUtils.getString(respTemp.getData(), "uids");
			String agencyId = MapUtils.getString(respTemp.getData(), "agencyId");
			int year = MapUtils.getInteger(map, "year");
			int month = MapUtils.getInteger(map, "month");
			
			map.put("uids", uids);
			RespDataObject<String> res = httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/order/app/findOrderNosByUids", map, String.class);
			
			if (!RespStatusEnum.SUCCESS.getCode().equals(res.getCode())) {
				resp.setCode(respTemp.getCode());
				resp.setMsg(respTemp.getMsg());
				return resp;
			}
			
			String orderNos1 = res.getData();
			
			if (StringUtils.isNotBlank(orderNos1)) {
				String[] s = orderNos1.split(",");
				StringBuffer sbf = new StringBuffer("'");
				
				for (String str : s) {
					sbf.append(str);
					sbf.append("','");
				}
				orderNos1 = sbf.substring(0, sbf.length() - 2);
			}else{
				RespHelper.setSuccessDataObject(resp, new HashMap<String,Object>());
				return resp;
			}
			
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<Map<String,Object>> list = receivableForService.findPaymentStatisticDetail(agencyId, orderNos1, year, month);
			
			StringBuffer orderNo = new StringBuffer("");
			for (Map<String,Object> m : list) {
				orderNo.append("'" + MapUtils.getString(m, "orderNo")+"',");
			}
			
			String orderNos = orderNo.toString();
			if(StringUtils.isNotEmpty(orderNos)){
				orderNos = orderNos.substring(0, orderNos.length() - 1);
			}else{
				dataMap.put("list",list);
				RespHelper.setSuccessDataObject(resp, dataMap);
			}
			
			Map<String, String> map1 = new HashMap<String, String>();
			map1.put("orderNos", orderNos);
			
			RespData<Map<String,Object>> res1 = httpUtil.getRespData(Constants.LINK_CREDIT, "/credit/order/app/findLoanByOrderNos", map1, Map.class);
			
			List<Map<String,Object>> map2 = res1.getData();
			List<DictDto> productList = getDictDtoByType("product");
			if (list != null) {
				for (Map<String,Object> m : list) {
					String orderNo1 = MapUtils.getString(m, "orderNo");
					if (map2 != null) {
						for (Map<String, Object> map3 : map2) {
							String orderNo2 = MapUtils.getString(map3, "orderNo");
							if (orderNo1.equals(orderNo2)) {
								m.putAll(map3);
							}
						}
					}
					
					String productCode = MapUtils.getString(m, "productCode");
					m.put("productName", "");
					for (DictDto dictDto : productList) {
						if (dictDto.getCode().equals(productCode)) {
							m.put("productName", dictDto.getName());
						}
					}
				}
			}
			
			dataMap.put("list",list);
			RespHelper.setSuccessDataObject(resp, dataMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
	
	
	/**
	 * 通过订单查询并返回回款集合信息
	 * @param request
	 * @param uid
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findPaymentByLoan") 
	public RespData<Map<String, Object>> findPaymentByLoan(HttpServletRequest request, @RequestBody Map<String, Object> params){
		RespData<Map<String,Object>> resp = new RespData<Map<String,Object>>();
		try {
			String orderNos = MapUtils.getString(params, "orderNos");
			
			if (StringUtils.isBlank(orderNos)) {
				RespHelper.setSuccessData(resp, new ArrayList<Map<String,Object>>());
				return resp;
			}
			
			if (StringUtils.isNotBlank(orderNos)) {
				String[] s = orderNos.split(",");
				StringBuffer sbf = new StringBuffer("'");
				
				for (String str : s) {
					sbf.append(str);
					sbf.append("','");
				}
				orderNos = sbf.substring(0, sbf.length() - 2);
			}
			
			List<Map<String,Object>> list = receivableForService.findPaymentByLoan(orderNos);
			//resp.setData(list);
			RespHelper.setSuccessData(resp, list);
		} catch (Exception e) {
			e.printStackTrace();
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(RespStatusEnum.FAIL.getMsg());
		}
		
		return resp;
	}
	
	
	/**
	 * 通过订单查询并返回当月回款信息
	 * @param request
	 * @param uid
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findPaymentStatisticByMonthAndOrderNos") 
	public RespDataObject<Map<String, Object>> findPaymentStatisticByMonthAndOrderNos(HttpServletRequest request, @RequestBody Map<String, Object> params){
		RespDataObject<Map<String,Object>> resp = new RespDataObject<Map<String,Object>>();
		try {
			String orderNos = MapUtils.getString(params, "orderNos");
			int year = MapUtils.getIntValue(params, "year");
			int month = MapUtils.getIntValue(params, "month");
			
			if (StringUtils.isBlank(orderNos)) {
				RespHelper.setSuccessDataObject(resp, new HashMap<String,Object>());
				return resp;
			}
			if (StringUtils.isNotBlank(orderNos)) {
				String[] s = orderNos.split(",");
				StringBuffer sbf = new StringBuffer("");
				for (String str : s) {
					sbf.append("'");
					sbf.append(str);
					sbf.append("',");
				}
				orderNos = sbf.substring(0, sbf.length() - 1);
				logger.info("orderNos="+orderNos);
			}
			Map<String,Object> map = receivableForService.findPaymentStatisticByMonthAndOrderNos(orderNos, year, month);
			RespHelper.setSuccessDataObject(resp, map);
			
		} catch (Exception e) {
			e.printStackTrace();
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(RespStatusEnum.FAIL.getMsg());
		}
		
		return resp;
	}
}
