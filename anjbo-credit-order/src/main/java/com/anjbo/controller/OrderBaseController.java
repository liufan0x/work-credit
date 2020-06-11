package com.anjbo.controller;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.bean.common.DictDto;
import com.anjbo.bean.customer.AgencyDto;
import com.anjbo.bean.customer.CustomerFundDto;
import com.anjbo.bean.element.DocumentsDto;
import com.anjbo.bean.element.DocumentsReturnDto;
import com.anjbo.bean.finance.ApplyLoanDto;
import com.anjbo.bean.finance.AuditDto;
import com.anjbo.bean.finance.FinanceLogDto;
import com.anjbo.bean.finance.LendingDto;
import com.anjbo.bean.finance.LendingHarvestDto;
import com.anjbo.bean.finance.LendingInstructionsDto;
import com.anjbo.bean.finance.LendingPayDto;
import com.anjbo.bean.finance.PaymentReportDto;
import com.anjbo.bean.finance.RebateDto;
import com.anjbo.bean.finance.ReceivableForDto;
import com.anjbo.bean.finance.ReceivablePayDto;
import com.anjbo.bean.finance.ReportDto;
import com.anjbo.bean.finance.StatementDto;
import com.anjbo.bean.order.OrderBaseBorrowDto;
import com.anjbo.bean.order.OrderFlowDto;
import com.anjbo.bean.order.OrderListDto;
import com.anjbo.bean.order.vo.OrderReportLendingVo;
import com.anjbo.bean.product.CancellationDto;
import com.anjbo.bean.product.DistributionMemberDto;
import com.anjbo.bean.product.FaceSignDto;
import com.anjbo.bean.product.FacesignRecognitionDto;
import com.anjbo.bean.product.FddMortgageReleaseDto;
import com.anjbo.bean.product.FddMortgageStorageDto;
import com.anjbo.bean.product.FddReleaseDto;
import com.anjbo.bean.product.ForeclosureDto;
import com.anjbo.bean.product.ForensicsDto;
import com.anjbo.bean.product.ManagerAuditDto;
import com.anjbo.bean.product.MortgageDto;
import com.anjbo.bean.product.NewlicenseDto;
import com.anjbo.bean.product.NotarizationDto;
import com.anjbo.bean.product.ProductDto;
import com.anjbo.bean.product.ProductProcessDto;
import com.anjbo.bean.product.SignInsuranceDto;
import com.anjbo.bean.product.TransferDto;
import com.anjbo.bean.product.UploadInsuranceDto;
import com.anjbo.bean.product.data.ProductDataDto;
import com.anjbo.bean.risk.AllocationFundAduitDto;
import com.anjbo.bean.risk.AllocationFundDto;
import com.anjbo.bean.risk.DataAuditDto;
import com.anjbo.bean.risk.FinalAuditDto;
import com.anjbo.bean.risk.FirstAuditDto;
import com.anjbo.bean.risk.JusticeAuditDto;
import com.anjbo.bean.risk.OfficerAuditDto;
import com.anjbo.bean.risk.ReviewAuditDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.bean.vo.ChartVo;
import com.anjbo.common.Constants;
import com.anjbo.common.DateUtil;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.OrderBaseBorrowRelationService;
import com.anjbo.service.OrderBaseBorrowService;
import com.anjbo.service.OrderBaseService;
import com.anjbo.service.OrderFlowService;
import com.anjbo.utils.CommonDataUtil;
import com.anjbo.utils.ConfigUtil;
import com.anjbo.utils.DateUtils;
import com.anjbo.utils.StringUtil;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

@Controller
@RequestMapping("/credit/order/base/v/")
public class OrderBaseController extends BaseController {

	private Log log = LogFactory.getLog(getClass());

	@Resource
	private OrderBaseService orderBaseService;
	@Resource
	private OrderBaseBorrowService orderBaseBorrowService;
	@Resource
	private OrderFlowService orderFlowService;

	@Resource
	private OrderBaseBorrowRelationService orderBaseBorrowRelationService;

	@ResponseBody
	@RequestMapping(value = "/selectAllOrder")
	public RespDataObject<List<Map<String, Object>>> selectAllOrder(HttpServletRequest request,
			@RequestBody Map<String, Object> map) {
		RespDataObject<List<Map<String, Object>>> resp = new RespDataObject<List<Map<String, Object>>>();
		try {
			List<Map<String, Object>> list = orderBaseService.selectAllOrder(map);
			RespHelper.setSuccessDataObject(resp, list);
		} catch (Exception e) {
			e.printStackTrace();
			RespHelper.setFailDataObject(resp, null, RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}

	/**
	 * 查询可关联的订单
	 * 
	 * @Author LiuF
	 * @rewrite KangLG<2018年1月18日> 校验入参，未提交审核的不予关联
	 * @param request
	 * @param map
	 *            {agencyId机构, borrowerName客户姓名}
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectAbleRelationOrder")
	public RespData<Map<String, Object>> selectAbleRelationOrder(HttpServletRequest request,
			@RequestBody Map<String, Object> map) {
		RespData<Map<String, Object>> resp = new RespData<Map<String, Object>>();
		try {
			if (null == map || !map.containsKey("borrowerName")) {
				RespHelper.setFailData(resp, null, RespStatusEnum.PARAMETER_ERROR.getMsg());
				return resp;
			} else if (!map.containsKey("agencyId") || 0 == MapUtils.getIntValue(map, "agencyId", 0)) {
				map.put("agencyId", super.getUserDto(request).getAgencyId());
			}

			List<Map<String, Object>> list = orderBaseService.selectAbleRelationOrder(map);
			if (list != null) {
				Iterator<Map<String, Object>> it = list.iterator();
				while (it.hasNext()) {
					Map<String, Object> mapIt = it.next();
					// 未提交审核的置换贷，不能关联(可能没合作机构和渠道经理，导致关联畅贷后续问题)
					if ("placeOrder".equalsIgnoreCase(MapUtils.getString(mapIt, "processId"))) {
						it.remove();
						continue;
					}
					// 回款过后的债务置换贷款不可再添加关联畅贷
					boolean flag = compareProcessAround(
							Integer.parseInt(
									MapUtils.getString(mapIt, "cityCode") + MapUtils.getString(mapIt, "productCode")),
							MapUtils.getString(mapIt, "processId"), "receivableFor");
					if (!flag) {
						it.remove();
						continue;
					}

					mapIt.put("id", MapUtils.getString(mapIt, "orderNo"));
					mapIt.put("name",
							MapUtils.getString(mapIt, "customerName") + "， "
									+ NumberFormat.getInstance()
											.format(Double.valueOf(MapUtils.getString(mapIt, "borrowingAmount", "0")))
									+ "万， " + MapUtils.getString(mapIt, "borrowingDay", "-") + "天");
					mapIt.put("notarialName",
							CommonDataUtil.getUserDtoByUidAndMobile(mapIt.get("notarialUid")).getName());
				}
			} else {
				list = new ArrayList<Map<String, Object>>();
			}
			return RespHelper.setSuccessData(resp, list);
		} catch (Exception e) {
			e.printStackTrace();
			RespHelper.setFailData(resp, null, RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}

	/**
	 * 查询订单公证经办人
	 * 
	 * @param request
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectNotarialUidByOrderNo")
	public RespDataObject<Map<String, Object>> selectNotarialUidByOrderNo(HttpServletRequest request,
			@RequestBody Map<String, Object> map) {
		RespDataObject<Map<String, Object>> resp = new RespDataObject<Map<String, Object>>();
		try {
			Map<String, Object> m = orderBaseService.selectNotarialUidByOrderNo(MapUtils.getString(map, "orderNo"));
			m.put("notarialName", getUserDtoByUid(MapUtils.getString(map, "notarialUid")).getName());
			return RespHelper.setSuccessDataObject(resp, m);
		} catch (Exception e) {
			e.printStackTrace();
			RespHelper.setFailDataObject(resp, null, RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}

	@ResponseBody
	@RequestMapping(value = "/selectionConditions")
	public RespDataObject<Map<String, Object>> selectionConditions(HttpServletRequest request) {
		RespDataObject<Map<String, Object>> resp = new RespDataObject<Map<String, Object>>();
		try {
			UserDto user = getUserDtoByMysql(request);
			Map<String, Object> retMap = assembleBrush(user, false);

			RespHelper.setSuccessDataObject(resp, retMap);
		} catch (Exception e) {
			e.printStackTrace();
			RespHelper.setFailDataObject(resp, null, RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}

	/**
	 * 资方看单检索条件
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectionConditionsByFund")
	public RespDataObject<Map<String, Object>> selectionConditionsByFund(HttpServletRequest request) {
		RespDataObject<Map<String, Object>> resp = new RespDataObject<Map<String, Object>>();
		try {
			UserDto user = getUserDtoByMysql(request);
			user.setAgencyId(1);
			Map<String, Object> retMap = fundAssembleBrush(user, false);

			RespHelper.setSuccessDataObject(resp, retMap);
		} catch (Exception e) {
			e.printStackTrace();
			RespHelper.setFailDataObject(resp, null, RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}

	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/selectCityList")
	public RespData<Map<String, Object>> selectCityList(HttpServletRequest request) {
		RespData<Map<String, Object>> resp = new RespData<Map<String, Object>>();
		try {
			UserDto user = getUserDtoByMysql(request);
			Map<String, Object> retMap = assembleBrush(user, true);
			List<Map<String, Object>> list = (List<Map<String, Object>>) MapUtils.getObject(retMap, "citys");
			List<Map<String, Object>> tempList = new ArrayList<Map<String, Object>>();
			for (Map<String, Object> map : list) {
				if ("不限".equals(MapUtils.getString(map, "cityName"))) {
					continue;
				}
				Map<String, Object> map1 = new HashMap<String, Object>();
				map1.put("id", MapUtils.getObject(map, "cityCode"));
				map1.put("name", MapUtils.getObject(map, "cityName"));
				tempList.add(map1);
			}
			RespHelper.setSuccessData(resp, tempList);
		} catch (Exception e) {
			e.printStackTrace();
			RespHelper.setFailData(resp, null, RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}

	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/selectProductList")
	public RespData<Map<String, Object>> selectProductList(HttpServletRequest request,
			@RequestBody Map<String, Object> params) {
		RespData<Map<String, Object>> resp = new RespData<Map<String, Object>>();
		try {
			UserDto user = getUserDtoByMysql(request);
			Map<String, Object> retMap = assembleBrush(user, true);
			List<Map<String, Object>> list = (List<Map<String, Object>>) MapUtils.getObject(retMap, "citys");
			List<Map<String, Object>> temp = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> tempList = new ArrayList<Map<String, Object>>();
			for (Map<String, Object> map : list) {
				if (MapUtils.getString(params, "cityCode", "").equals(MapUtils.getString(map, "cityCode", ""))) {
					temp = (List<Map<String, Object>>) MapUtils.getObject(map, "productList");
					for (Map<String, Object> map1 : temp) {
						if ("不限".equals(MapUtils.getString(map1, "productName"))) {
							continue;
						}
						if ("03".equals(MapUtils.getString(map1, "productCode"))) {
							continue;
						}
						Map<String, Object> map2 = new HashMap<String, Object>();
						map2.put("id", MapUtils.getObject(map1, "productCode"));
						map2.put("name", MapUtils.getObject(map1, "productName"));
						tempList.add(map2);
					}
					break;
				}
			}
			RespHelper.setSuccessData(resp, tempList);
		} catch (Exception e) {
			e.printStackTrace();
			RespHelper.setFailData(resp, null, RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}

	// 智能要件柜选产品类型接口
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/selectElementProductList")
	public RespData<Map<String, Object>> selectElementProductList(HttpServletRequest request,
			@RequestBody Map<String, Object> params) {
		RespData<Map<String, Object>> resp = new RespData<Map<String, Object>>();
		try {
			UserDto user = getUserDtoByMysql(request);
			Map<String, Object> retMap = assembleBrush(user, true);
			List<Map<String, Object>> list = (List<Map<String, Object>>) MapUtils.getObject(retMap, "citys");
			List<Map<String, Object>> temp = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> tempList = new ArrayList<Map<String, Object>>();
			for (Map<String, Object> map : list) {
				if (MapUtils.getString(params, "cityCode", "").equals(MapUtils.getString(map, "cityCode", ""))) {
					temp = (List<Map<String, Object>>) MapUtils.getObject(map, "productList");
					for (Map<String, Object> map1 : temp) {
						if ("不限".equals(MapUtils.getString(map1, "productName"))) {
							continue;
						}
						/*
						 * if("03".equals(MapUtils.getString(map1, "productCode"))){ continue; }
						 */
						if ("10000".equals(MapUtils.getString(map1, "productCode"))) {
							continue;
						}
						Map<String, Object> map2 = new HashMap<String, Object>();
						map2.put("id", MapUtils.getObject(map1, "productCode"));
						map2.put("name", MapUtils.getObject(map1, "productName"));
						tempList.add(map2);
					}
					break;
				}
			}
			RespHelper.setSuccessData(resp, tempList);
		} catch (Exception e) {
			e.printStackTrace();
			RespHelper.setFailData(resp, null, RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}

	@ResponseBody
	@RequestMapping(value = "/selectDetailByOrderNo")
	public RespDataObject<OrderListDto> selectDetailByOrderNo(@RequestBody OrderListDto orderListDto) {
		RespDataObject<OrderListDto> resp = new RespDataObject<OrderListDto>();
		try {
			orderListDto = orderBaseService.selectDetail(orderListDto.getOrderNo());
			RespHelper.setSuccessDataObject(resp, orderListDto);
		} catch (Exception e) {
			e.printStackTrace();
			RespHelper.setFailDataObject(resp, null, RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}

	@ResponseBody
	@RequestMapping(value = "/list")
	public RespPageData<OrderListDto> list(HttpServletRequest request, @RequestBody OrderListDto orderListDto) {
		RespPageData<OrderListDto> resp = new RespPageData<OrderListDto>();
		resp.setCode(RespStatusEnum.FAIL.getCode());
		resp.setMsg(RespStatusEnum.FAIL.getMsg());
		try {
			UserDto userDto = getUserDto(request);
			String orderNos = "";
			switch (orderListDto.getType()) {
			// 全部订单
			case 0:
				String deptAllUid = "";
				// 查看全部订单
				if(userDto.getAgencyId()>1) {
					deptAllUid = userDto.getUid();
				} else if (userDto.getAuthIds().contains("1")) {
					// 查看部门订单
				} else if (userDto.getAuthIds().contains("2")) {
					userDto.setCreateTime(null);
					userDto.setUpdateTime(null);
					RespDataObject<Map<String, String>> respTemp = httpUtil.getRespDataObject(Constants.LINK_CREDIT,
							"/credit/user/base/v/selectUidsByDeptId", userDto, Map.class);
					deptAllUid = MapUtils.getString(respTemp.getData(), "uids");
					// 查看自己订单
				} else {
					deptAllUid = userDto.getUid();
				}
				orderNos = orderFlowService.selectOrderNoByUid(userDto.getUid());
				if (StringUtils.isNotEmpty(orderNos)) {
					orderListDto.setOrderNo(orderNos);
				}
				orderListDto.setCurrentHandlerUid(deptAllUid);
				orderListDto.setUpdateUid(userDto.getUid());
				break;
			// 我处理过的订单
			case 1:
				orderNos = orderFlowService.selectOrderNoByUid(userDto.getUid());
				if (StringUtils.isEmpty(orderNos)) {
					resp.setTotal(0);
					resp.setRows(new ArrayList<OrderListDto>());
					resp.setCode(RespStatusEnum.SUCCESS.getCode());
					resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
					return resp;
				}
				orderListDto.setOrderNo(orderNos);
				break;
			// 默认显示待我处理的订单
			default:
				orderListDto.setType(2);
				orderListDto.setCurrentHandlerUid(userDto.getUid());
				break;
			}
			orderListDto.setLoginUid(userDto.getUid());
			List<OrderListDto> list = orderBaseService.selectOrderList(orderListDto);
			// 查询房抵贷是否为陕国投
			String orderDtos = "";
			for (OrderListDto orderDto : list) {
				if (orderDto.getOrderNo().contains("k")) {
					orderNos += "'" + orderDto.getOrderNo() + "',";
				} else {
					orderNos += orderDto.getOrderNo() + ",";
				}
			}
			Map<String, Object> sgtMap = new HashMap<String, Object>();
			if (StringUtil.isNotBlank(orderDtos)) {
				orderDtos = orderDtos.substring(0, orderDtos.length() - 1);
				Map<String, Object> orderMap = new HashMap<String, Object>();
				orderMap.put("orderNo", orderDtos);
				RespDataObject<Map<String, Object>> respData = httpUtil.getRespDataObject(Constants.LINK_CREDIT,
						"/credit/risk/allocationfund/v/getOrderNoMosaicFundCode", orderMap, Map.class);
				Map<String, Object> map = (Map<String, Object>) respData.getData();
				Iterator<Map.Entry<String, Object>> entries = map.entrySet().iterator();
				while (entries.hasNext()) {
					Map.Entry<String, Object> entry = entries.next();
					if (entry.getValue() != null && entry.getValue().toString().contains("1000")) {
						sgtMap.put(entry.getKey(), true);
					}
				}
			}
			for (OrderListDto orderDto : list) {
				if (sgtMap.containsKey(orderDto.getOrderNo())) {
					orderDto.setSgt(true);
				}
			}
			resp.setTotal(orderBaseService.selectOrderCount(orderListDto));
			resp.setRows(list);
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}

	/**
	 * 机构订单列表
	 * 
	 * @Author KangLG<2017年11月8日>
	 * @param request
	 * @param orderListDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/listAgency")
	public RespPageData<OrderListDto> listAgency(HttpServletRequest request, @RequestBody OrderListDto orderListDto) {
		orderListDto.setAgencyId(this.getUserDto(request).getAgencyId());
		// 根据回款时间查询订单
		if (StringUtils.isNotEmpty(orderListDto.getPlanPaymentTimeStart())
				|| StringUtils.isNotEmpty(orderListDto.getPlanPaymentTimeEnd())) {
			ReceivableForDto dto = new ReceivableForDto();
			dto.setAgencyId(orderListDto.getAgencyId());
			if (StringUtils.isNotEmpty(orderListDto.getPlanPaymentTimeStart())) {
				dto.setPayMentAmountDateStart(orderListDto.getPlanPaymentTimeStart());
			}
			if (StringUtils.isNotEmpty(orderListDto.getPlanPaymentTimeEnd())) {
				dto.setPayMentAmountDateEnd(orderListDto.getPlanPaymentTimeEnd());
			}

			RespPageData<OrderListDto> resp = new RespPageData<OrderListDto>();
			try {
				// String resReceivableFor = new
				// HttpUtil(true).getObject("http://127.0.0.1:9120",
				// "/credit/finance/receivableFor/v/search4AgencyOrderTime", dto, String.class);
				String resReceivableFor = httpUtil.getObject(Constants.LINK_CREDIT,
						"/credit/finance/receivableFor/v/search4AgencyOrderTime", dto, String.class);
				if (StringUtils.isNotEmpty(resReceivableFor)) {
					orderListDto
							.setPlanPaymentTimeOrders(String.format("'%s'", resReceivableFor.replaceAll(",", "','")));
				} else {
					resp.setCode(RespStatusEnum.SUCCESS.getCode());
					resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
				}
			} catch (Exception e) {
				e.printStackTrace();
				resp.setCode(RespStatusEnum.FAIL.getCode());
				resp.setMsg(RespStatusEnum.FAIL.getMsg());
				return resp;
			}
		}
		return this.list(request, orderListDto);
	}

	/**
	 * 查询权限内所有订单
	 * 
	 * @Author KangLG<2017年11月27日>
	 * @param request
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/list4Authority")
	public RespPageData<OrderListDto> list4Authority(HttpServletRequest request) {
		UserDto userDto = this.getUserDto(request);
		OrderListDto orderListDto = new OrderListDto();
		orderListDto.setAgencyId(userDto.getAgencyId());
		orderListDto.setType(0);
		orderListDto.setPageSize(9999999);

		// 部门权限
		String deptAllUid = "";
		if (userDto.getAuthIds().contains("1")) {
			// 查看全部订单
		} else if (userDto.getAuthIds().contains("2")) {
			// 查看部门订单
			userDto.setCreateTime(null);
			userDto.setUpdateTime(null);
			RespDataObject<Map<String, String>> respTemp = httpUtil.getRespDataObject(Constants.LINK_CREDIT,
					"/credit/user/base/v/selectUidsByDeptId", userDto, Map.class);
			deptAllUid = MapUtils.getString(respTemp.getData(), "uids");
		} else {
			// 查看自己订单
			deptAllUid = userDto.getUid();
		}
		orderListDto.setCurrentHandlerUid(deptAllUid);

		// 自己操作过的单
		String orderNos = orderFlowService.selectOrderNoByUid(userDto.getUid());
		if (StringUtils.isNotEmpty(orderNos)) {
			orderListDto.setOrderNo(orderNos);
		}
		return this.list(request, orderListDto);
	}

	@ResponseBody
	@RequestMapping(value = "/listNos4Authority")
	public RespDataObject<String> listNos4Authority(HttpServletRequest request) {
		RespDataObject<String> rd = new RespDataObject<String>();
		List<String> lstOrderNos = new LinkedList<String>();
		// 构建订单号集合
		RespPageData<OrderListDto> respPages = this.list4Authority(request);
		if (!RespStatusEnum.SUCCESS.getCode().equals(respPages.getCode())) {
			return RespHelper.setFailDataObject(rd, null, RespStatusEnum.FAIL.getMsg());
		}
		List<OrderListDto> list = respPages.getRows();
		if (null != list && !list.isEmpty()) {
			for (OrderListDto dto : list) {
				lstOrderNos.add(String.format("'%s'", dto.getOrderNo()));
			}
		}
		return RespHelper.setSuccessDataObject(rd,
				null != lstOrderNos && !lstOrderNos.isEmpty() ? StringUtils.join(lstOrderNos, ",") : "'N'");
	}

	@ResponseBody
	@RequestMapping(value = "/reportList")
	public RespPageData<OrderListDto> reportList(@RequestBody OrderListDto orderListDto) {
		RespPageData<OrderListDto> resp = new RespPageData<OrderListDto>();
		resp.setCode(RespStatusEnum.FAIL.getCode());
		resp.setMsg(RespStatusEnum.FAIL.getMsg());
		try {
			orderListDto.setType(-1);
			resp.setTotal(orderBaseService.selectOrderCount(orderListDto));
			resp.setRows(orderBaseService.selectOrderList(orderListDto));
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}

	@ResponseBody
	@RequestMapping(value = "/updateOrderList")
	public RespStatus updateOrderList(HttpServletRequest request, @RequestBody OrderListDto orderListDto) {
		RespStatus resp = new RespStatus();
		try {
			if (orderListDto.getState() == null || orderListDto.getCurrentHandler() == null) {
				String processId = orderListDto.getProcessId();
				String currentHandlerUid = orderListDto.getCurrentHandlerUid();
				String processName = getProcessName(
						Integer.parseInt(orderListDto.getCityCode() + orderListDto.getProductCode()), processId);
				if (!"已完结".equals(processName)) {
					processName = "待" + processName;
				}
				orderListDto.setState(processName);
				orderListDto.setCurrentHandler(getUserDtoByUid(currentHandlerUid).getName());
			}
			log.info("修改订单列表" + orderListDto.getOrderNo() + ",节点" + orderListDto.getState() + ",处理人"
					+ orderListDto.getCurrentHandler());
			orderBaseService.updateOrderList(orderListDto);
			RespHelper.setSuccessRespStatus(resp);
		} catch (Exception e) {
			e.printStackTrace();
			RespHelper.setFailRespStatus(resp, RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}

	@ResponseBody
	@RequestMapping(value = "/insertOrderList")
	public RespStatus insertOrderList(HttpServletRequest request, @RequestBody OrderListDto orderListDto) {
		RespStatus resp = new RespStatus();
		try {
			orderBaseService.insertOrderList(orderListDto);
			RespHelper.setSuccessRespStatus(resp);
		} catch (Exception e) {
			e.printStackTrace();
			RespHelper.setFailRespStatus(resp, RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}

	@ResponseBody
	@RequestMapping(value = "/selectlendingCount")
	public RespDataObject<Integer> selectlendingCount(HttpServletRequest request,
			@RequestBody OrderListDto orderListDto) {
		RespDataObject<Integer> resp = new RespDataObject<Integer>();
		try {
			RespHelper.setSuccessDataObject(resp, orderBaseService.selectlendingCount(orderListDto));
		} catch (Exception e) {
			e.printStackTrace();
			RespHelper.setFailDataObject(resp, null, RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}

	/**
	 * 关闭订单
	 * 
	 * @param request
	 * @param orderListDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/closeOrder")
	public RespStatus closeOrder(HttpServletRequest request, @RequestBody OrderListDto orderListDto) {
		RespStatus resp = new RespStatus();
		try {
			if ("".equals(orderListDto.getOrderNo())) {
				RespHelper.setFailRespStatus(resp, RespStatusEnum.PARAMETER_ERROR.getMsg());
				return resp;
			}
			orderListDto.setUpdateUid(getUserDto(request).getUid());
			if (StringUtils.isEmpty(orderListDto.getState())) {
				orderListDto.setState("已关闭");
			} else {
				orderListDto.setProcessId("closeOrder");
				orderListDto.setCurrentHandlerUid("-");
				orderListDto.setCurrentHandler("-");
			}

			// 同步数据到智能要件
			try {
				logger.info("执行了同步信贷件订单到智能要件");
				RespStatus elementResp = httpUtil.getRespStatus(Constants.LINK_CREDIT,
						"/credit/element/list/saveCredit", orderListDto);
			} catch (Exception e) {
				e.printStackTrace();
			}

			orderBaseService.updateOrderList(orderListDto);
			RespHelper.setSuccessRespStatus(resp);
		} catch (Exception e) {
			e.printStackTrace();
			RespHelper.setFailRespStatus(resp, RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}

	/**
	 * app列表
	 * 
	 * @param request
	 * @param orderListDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/appList")
	public RespDataObject<Map<String, Object>> appList(HttpServletRequest request,
			@RequestBody OrderListDto orderListDto) {
		RespDataObject<Map<String, Object>> resp = new RespDataObject<Map<String, Object>>();
		RespHelper.setFailDataObject(resp, null, RespStatusEnum.FAIL.getMsg());
		try {
			String requestSource = orderListDto.getSource();
			Map<String, Object> retMap = new HashMap<String, Object>();
			UserDto userDto = getUserDtoByMysql(request);
			orderListDto.setType(3);
			String deptAllUid = "";
			List<OrderListDto> orderListDtos = new ArrayList<OrderListDto>();
			OrderListDto noConditionOrder = new OrderListDto();
			noConditionOrder.setType(3);
			List<OrderListDto> noConditionOrderList = new ArrayList<OrderListDto>();
			String orderNos = "";
			// 快鸽app登录用户id不存在时
			if (userDto == null) {// 快鸽APP未登录

			} else if (userDto.getIsEnable() == 100) {
				// 根据快鸽uid查询本人创建的订单
				log.info("快鸽查询不到的普通用户isEnable：" + userDto.getIsEnable());
				/*orderNos = orderFlowService.selectOrderNoByUid(userDto.getUid());
				orderListDto.setOrderNo(orderNos);
				orderListDto.setUpdateUid(userDto.getUid());
				orderListDto.setCurrentHandlerUid(userDto.getUid());
				orderListDtos = orderBaseService.selectOrderListApp(orderListDto);
				// 无条件检索是否有单
				noConditionOrder.setStart(0);
				noConditionOrder.setPageSize(1);
				noConditionOrder.setOrderNo(orderNos);
				noConditionOrder.setUpdateUid(userDto.getUid());
				noConditionOrder.setCurrentHandlerUid(userDto.getUid());
				noConditionOrderList = orderBaseService.selectOrderListApp(noConditionOrder);*/
			} else {
				// 解绑用户查看处理过的订单,3解绑用户,agencyId初始化0普通用户
				if (userDto.getIsEnable() == 3 || 0 == userDto.getAgencyId()) {
					deptAllUid = userDto.getUid();
				} else {
					if (userDto.getAuthIds() == null) {
						log.info("无权限，看自己单：" + userDto.getUid());
						deptAllUid = userDto.getUid();
					} else {
						// 查看全部订单
						if(userDto.getAgencyId()>1) {
							deptAllUid = userDto.getUid();
						} else if (userDto.getAuthIds().contains("1") && userDto.getIsEnable() == 0) {
							log.info("查看全部订单：" + userDto.getUid() + "机构id：" + userDto.getAgencyId());
							// 查看部门订单
						} else if (userDto.getAuthIds().contains("2") && userDto.getIsEnable() == 0) {
							// 日期格式 json转换出错
							userDto.setCreateTime(null);
							userDto.setUpdateTime(null);
							RespDataObject<Map<String, String>> respTemp = httpUtil.getRespDataObject(
									Constants.LINK_CREDIT, "/credit/user/base/v/selectUidsByDeptId", userDto,
									Map.class);
							deptAllUid = MapUtils.getString(respTemp.getData(), "uids");
							if (StringUtils.isBlank(deptAllUid)) {// 部门没有用户，查看自己的订单
								deptAllUid = userDto.getUid();
								log.info("部门没有用户，查看自己的订单：" + deptAllUid);
							}
							log.info("查看部门单：" + deptAllUid);
							// 查看自己订单
						} else {
							log.info("查看自己订单：" + userDto.getUid());
							deptAllUid = userDto.getUid();
						}
					}
				}
				orderNos = orderFlowService.selectOrderNoByUid(userDto.getUid());
				orderListDto.setOrderNo(orderNos);
				orderListDto.setUpdateUid(userDto.getUid());
				// 机构用户会判断查看本机构订单
				if (userDto.getAuthIds() != null && userDto.getAuthIds().contains("1") && 0 == userDto.getIsEnable()
						&& userDto.getAgencyId() > 1) {
					orderListDto.setAgencyId(userDto.getAgencyId());
					deptAllUid = userDto.getUid();
				}
				// 提单选择的非兜底合作机构登陆看到此订单
				if (userDto.getAgencyId() > 1) {
					AgencyDto agencyDto = new AgencyDto();
					agencyDto.setAgencyId(userDto.getAgencyId());
					agencyDto = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/customer/agency/v/getAgencyDto",
							agencyDto, AgencyDto.class);
					if (agencyDto.getCooperativeModeId() == 2) {
						orderListDto.setCooperativeAgencyId(userDto.getAgencyId());
					}
				}
				orderListDto.setCurrentHandlerUid(deptAllUid);
				orderListDtos = orderBaseService.selectOrderListApp(orderListDto);
				// 无条件检索是否有单
				noConditionOrder.setStart(0);
				noConditionOrder.setPageSize(1);
				noConditionOrder.setOrderNo(orderNos);
				noConditionOrder.setUpdateUid(userDto.getUid());
				if (userDto.getAuthIds() != null && userDto.getAuthIds().contains("1") && 0 == userDto.getIsEnable()
						&& userDto.getAgencyId() > 1) {
					noConditionOrder.setAgencyId(userDto.getAgencyId());
					deptAllUid = userDto.getUid();
				}
				noConditionOrder.setCurrentHandlerUid(deptAllUid);
				noConditionOrderList = orderBaseService.selectOrderListApp(noConditionOrder);
			}
			// 如果没有订单，给快鸽app返回示例订单
			boolean isExampleOrder = false;
			if (orderListDto.getStart() == 0 && noConditionOrderList.size() == 0) {
				// orderListDto.setType(1);
				// orderNos =
				// ConfigUtil.getStringValue(Constants.BASE_ORDER_EXAMPLE,ConfigUtil.CONFIG_BASE);
				// orderListDto.setOrderNo(orderNos);
				// orderListDto.setAgencyId(0);//示例订单不加机构条件
				// orderListDtos = orderBaseService.selectOrderListApp(orderListDto);
				// //各种类型订单数量
				// Map<String,Object> map = new HashMap<String, Object>();
				// map.put("tCount", 0);
				// map.put("nonTCount", 0);
				// map.put("cmCount", 0);
				// retMap.put("orderCount", map);
				retMap.put("isExample", "1");
				isExampleOrder = true;
			} else {
				// 各种类型订单数量
				// orderListDto.setProductCode("01");
				// List<OrderListDto> tOrderList =
				// orderBaseService.selectOrderListApp(orderListDto);
				// orderListDto.setProductCode("02");
				// List<OrderListDto> tNonOrderList =
				// orderBaseService.selectOrderListApp(orderListDto);
				// orderListDto.setProductCode("04");
				// List<OrderListDto> cmOrderList =
				// orderBaseService.selectOrderListApp(orderListDto);
				// List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
				// Map<String,Object> map = new HashMap<String, Object>();
				// map.put("name", "债务置换贷款交易类");
				// map.put("count", tOrderList.size());
				// list.add(map);
				// map.put("name", "债务置换贷款非交易类");
				// map.put("nonTCount", tNonOrderList.size());
				// list.add(map);
				// map.put("name", "云按揭");
				// map.put("cmCount", cmOrderList.size());
				// list.add(map);
				// retMap.put("orderCount", list);
			}
			ReportDto report = new ReportDto();
			PaymentReportDto paymentReportDto = new PaymentReportDto();
			String reportOrderNos = "";
			Map<String, Object> reportMap = null;
			Map<String, Object> paymentreportMap = null;
			for (OrderListDto orderDto : orderListDtos) {
				reportOrderNos += "'" + orderDto.getOrderNo() + "'" + ",";
			}
			if (reportOrderNos.endsWith(",")) {
				reportOrderNos = reportOrderNos.substring(0, reportOrderNos.length() - 1);
			}
			report.setRelationOrderNo(reportOrderNos);
			paymentReportDto.setRelationOrderNo(reportOrderNos);
			report.setPageSize(0);
			paymentReportDto.setPageSize(0);
			reportMap = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/finance/report/v/listMap", report,
					Map.class);
			paymentreportMap = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/finance/paymentreport/v/listMap",
					paymentReportDto, Map.class);
			Map<String, Object> mm = new HashMap<String, Object>();
			mm.put("orderNo", reportOrderNos);
			RespDataObject<Map<String, Object>> res = httpUtil.getRespDataObject(Constants.LINK_CREDIT,
					"/credit/finance/afterLoanList/v/listLoanStatus", mm, Map.class);
			Map<String, Object> repaymentPlanMap = res.getData();
			List<Map<String, Object>> orderList = new ArrayList<Map<String, Object>>();
			String cityName = "";
			String productName = "";
			String productNamePrefix = "";
			String productNameSuffix = "";
			// 查询云按揭可以重新打开的订单
			Map<String, Object> tm = new HashMap<String, Object>();
			List<Map<String, Object>> tList = httpUtil.getList(Constants.LINK_CREDIT,
					"/credit/product/data/flow/base/v/reopenFlow", tm, Map.class);
			Map<String, Object> reopen = new HashMap<String, Object>();
			if (tList != null && tList.size() > 0) {
				for (Map<String, Object> map : tList) {
					if (map.get("nextProcessId") != null
							&& "subMortgage".equals(MapUtils.getString(map, "nextProcessId"))) {
						reopen.put(MapUtils.getString(map, "orderNo"), true);
					}
				}
			}
			Map<String, Object> tempMap = null;
			for (OrderListDto orderDto : orderListDtos) {
				tempMap = new HashMap<String, Object>();
				// 是否示例订单
				if (isExampleOrder) {
					tempMap.put("isExample", "1");
				} else {
					tempMap.put("isExample", "2");
				}
				cityName = "";
				productName = "";
				if (StringUtils.isNotBlank(orderDto.getCityName())) {
					cityName = orderDto.getCityName().replace("市", "");
				}
				if (StringUtils.isNotBlank(orderDto.getProductName())) {
					productName = orderDto.getProductName().replace("债务", "");
					if (productName.contains("畅贷")) {
						productNamePrefix = "畅贷";
					} else if (productName.contains("房抵贷")) {
						productNamePrefix = "房抵贷";
					} else {
						productNamePrefix = "置换贷款";
					}
					if (orderDto.getProductName().contains("非交易类")) {
						productNameSuffix = "非交易类";
					} else if (orderDto.getProductName().contains("交易类")) {
						productNameSuffix = "交易类";
					}
				}
				// 展示示例订单
				if (userDto == null) {// 未登录
					userDto = new UserDto();// 不显示操作按钮
					userDto.setUid("123456");
					userDto.setIsEnable(100);
				}
				// 建行云按揭
				if ("10000".equals(orderDto.getProductCode())) {
					cityName = "深圳";
					productName = "云按揭";
					productNamePrefix = "云按揭";
					tempMap.put("appShowKeyEnd", "提单人/经理/机构");
					tempMap.put("borrowingTermUnit", "月");
					if (orderDto.getState().contains("待指派渠道经理")) {
						tempMap.put("appShowKey1", "业主姓名");
						tempMap.put("appShowKey2", "房产证号");
						tempMap.put("appShowValue1", orderDto.getAppShowValue1());
						tempMap.put("appShowValue2", orderDto.getAppShowValue2());
						if (userDto.getIsEnable() == 0 && (orderDto.getCurrentHandlerUid().equals(userDto.getUid())
								|| orderDto.getCreateUid().equals(userDto.getUid()))) {
							tempMap.put("butName", "指派渠道经理,关闭订单");
							tempMap.put("pageType", orderDto.getProcessId() + ",closeOrder");
						}
					} else if (orderDto.getState().contains("待提交评估") || orderDto.getState().contains("待评估")
							|| orderDto.getState().contains("评估失败")) {
						tempMap.put("appShowKey1", "业主姓名");
						tempMap.put("appShowKey2", "房产证号");
						tempMap.put("appShowValue1", orderDto.getAppShowValue1());
						tempMap.put("appShowValue2", orderDto.getAppShowValue2());
						if (userDto.getIsEnable() == 0 && orderDto.getState().contains("待提交评估")
								&& (userDto.getUid().equals(orderDto.getCurrentHandlerUid())
										|| orderDto.getCreateUid().equals(userDto.getUid())
										|| userDto.getUid().equals(orderDto.getChannelManagerUid()))) {
							tempMap.put("butName", "提交评估,关闭订单");
							tempMap.put("pageType", orderDto.getProcessId() + ",closeOrder");
						}
					} else if (orderDto.getState().contains("买卖双方信息") || orderDto.getState().contains("待提交申请按揭")
							|| orderDto.getState().contains("已关闭")) {
						tempMap.put("appShowKey1", "业主姓名");
						tempMap.put("appShowKey2", "房产证号");
						tempMap.put("appShowKey3", "评估总值");
						tempMap.put("appShowValue1",
								orderDto.getAppShowValue1() == null ? "-" : orderDto.getAppShowValue1());
						tempMap.put("appShowValue2",
								orderDto.getAppShowValue2() == null ? "-" : orderDto.getAppShowValue2());
						tempMap.put("appShowValue3",
								orderDto.getAppShowValue3() == null ? "-" : orderDto.getAppShowValue3());
						if (userDto.getIsEnable() == 0 && orderDto.getState().contains("买卖双方信息")
								&& (userDto.getUid().equals(orderDto.getCurrentHandlerUid())
										|| orderDto.getCreateUid().equals(userDto.getUid())
										|| userDto.getUid().equals(orderDto.getChannelManagerUid()))) {
							tempMap.put("butName", "完善买卖双方信息");
							tempMap.put("pageType", orderDto.getProcessId());
						}
						if (userDto.getIsEnable() == 0 && orderDto.getState().contains("待提交申请按揭")
								&& (userDto.getUid().equals(orderDto.getCurrentHandlerUid())
										|| orderDto.getCreateUid().equals(userDto.getUid())
										|| userDto.getUid().equals(orderDto.getChannelManagerUid()))) {
							tempMap.put("butName", "申请按揭,关闭订单");
							tempMap.put("pageType", orderDto.getProcessId() + ",closeOrder");
						}
						if (userDto.getIsEnable() == 0 && orderDto.getState().contains("已关闭")
								&& reopen.containsKey(orderDto.getOrderNo())
								&& (userDto.getUid().equals(orderDto.getCurrentHandlerUid())
										|| orderDto.getCreateUid().equals(userDto.getUid())
										|| orderDto.getChannelManagerUid().equals(userDto.getUid()))) {
							tempMap.put("butName", "重新开启");
							tempMap.put("pageType", "reopen");
						}
					} else if (orderDto.getState().contains("待客户经理审核") || orderDto.getState().contains("待审批前材料准备")
							|| orderDto.getState().contains("待审批")) {
						tempMap.put("appShowKey1", "首付款金额");
						tempMap.put("appShowKey2", "房产证号");
						tempMap.put("appShowKey3", "评估总值");
						tempMap.put("appShowValue1",
								orderDto.getAppShowValue1() == null ? "-" : orderDto.getAppShowValue1());
						tempMap.put("appShowValue2",
								orderDto.getAppShowValue2() == null ? "-" : orderDto.getAppShowValue2());
						tempMap.put("appShowValue3",
								orderDto.getAppShowValue3() == null ? "-" : orderDto.getAppShowValue3());
					} else if (orderDto.getState().contains("审批失败")) {
						tempMap.put("appShowKey1", "审批结果");
						tempMap.put("appShowKey2", "意见类型");
						tempMap.put("appShowKey3", "审批时间");
						tempMap.put("appShowValue1", orderDto.getAppShowValue1());
						tempMap.put("appShowValue2", orderDto.getAppShowValue2());
						tempMap.put("appShowValue3", orderDto.getAppShowValue3());
						if (userDto.getIsEnable() == 0 && orderDto.getCurrentHandlerUid().equals(userDto.getUid())) {
							tempMap.put("butName", "重新申请按揭");
							tempMap.put("pageType", orderDto.getProcessId());
						}
					} else if (orderDto.getState().contains("待准备房产过户和抵押") || orderDto.getState().contains("待预约抵押登记")) {
						tempMap.put("appShowKey1", "审批结果");
						tempMap.put("appShowKey2", "审批时间");
						tempMap.put("appShowValue1", orderDto.getAppShowValue1());
						tempMap.put("appShowValue2", orderDto.getAppShowValue2());
						if (userDto.getIsEnable() == 0 && orderDto.getState().contains("待预约抵押登记")
								&& (userDto.getUid().equals(orderDto.getCurrentHandlerUid())
										|| orderDto.getCreateUid().equals(userDto.getUid())
										|| userDto.getUid().equals(orderDto.getChannelManagerUid()))) {
							tempMap.put("butName", "预约抵押");
							tempMap.put("pageType", orderDto.getProcessId());
						}
					} else if (orderDto.getState().contains("待取证抵押")) {
						tempMap.put("appShowKey1", "预约抵押登记日期");
						tempMap.put("appShowKey2", "预约地点");
						tempMap.put("appShowValue1", orderDto.getAppShowValue1());
						tempMap.put("appShowValue2", orderDto.getAppShowValue2());
						if ((userDto.getIsEnable() == 0 && orderDto.getCurrentHandlerUid().equals(userDto.getUid())
								|| orderDto.getCreateUid().equals(userDto.getUid())
								|| userDto.getUid().equals(orderDto.getChannelManagerUid()))) {
							tempMap.put("butName", "取证抵押");
							tempMap.put("pageType", orderDto.getProcessId());
						}
					} else if (orderDto.getState().contains("待放款")) {
						tempMap.put("appShowKey1", "抵押时间");
						tempMap.put("appShowKey2", "新房产证");
						tempMap.put("appShowValue1", orderDto.getAppShowValue1());
						tempMap.put("appShowValue2", orderDto.getAppShowValue2());
					} else if (orderDto.getState().contains("已完结") || orderDto.getState().contains("放款失败")) {
						tempMap.put("appShowKey1", "完成时间");
						tempMap.put("appShowValue1", orderDto.getAppShowValue1());
					}
				}
				tempMap.put("orderNo", orderDto.getOrderNo());
				tempMap.put("customerName", orderDto.getCustomerName() == null ? "" : orderDto.getCustomerName());
				tempMap.put("cityName", cityName);
				tempMap.put("cityCode", orderDto.getCityCode());
				tempMap.put("productCode", orderDto.getProductCode());
				tempMap.put("productName", productName);
				tempMap.put("productNamePrefix", productNamePrefix);
				tempMap.put("productNameSuffix", productNameSuffix);
				tempMap.put("borrowingAmount",
						orderDto.getBorrowingAmount() == null ? "-" : orderDto.getBorrowingAmount());
				tempMap.put("borrowingDay", orderDto.getBorrowingDay());
				if ("10000".equals(orderDto.getProductCode())
						&& (orderDto.getState().contains("已关闭") || orderDto.getState().contains("待提交申请按揭"))) {// 不显示右上角金额，期限
					tempMap.put("borrowingAmount", "-");
					tempMap.put("borrowingDay", 0);
				}
				tempMap.put("channelManagerName",
						orderDto.getChannelManagerName() == null ? "-" : orderDto.getChannelManagerName());
				tempMap.put("cooperativeAgencyName",
						orderDto.getCooperativeAgencyName() == null ? "-" : orderDto.getCooperativeAgencyName());
				tempMap.put("acceptMemberName",
						orderDto.getAcceptMemberName() == null ? "-" : orderDto.getAcceptMemberName());
				tempMap.put("state", orderDto.getState());
				tempMap.put("currentHandler", orderDto.getCurrentHandler());
				tempMap.put("processId", orderDto.getProcessId());
				tempMap.put("isRelationOrder", orderDto.getIsRelationOrder());

				boolean isChanLoan = !"03".equals(orderDto.getProductCode());

				if ((orderDto.getProcessId().contains("auditFirst") || orderDto.getProcessId().contains("auditFinal")
						|| orderDto.getProcessId().contains("auditOfficer")
						|| orderDto.getProcessId().contains("auditReview")
						|| orderDto.getProcessId().contains("repaymentMember")
						|| orderDto.getProcessId().contains("foreclosure")
						|| orderDto.getProcessId().contains("applyLoan")
						|| orderDto.getProcessId().contains("financialAudit")
						|| orderDto.getProcessId().contains("managerAudit")
						|| "element".equals(orderDto.getProcessId())) && !"10000".equals(orderDto.getProductCode())) {
					if (orderDto.getProcessId().contains("foreclosure")) {
						if (orderDto.getCurrentHandlerUid().equals(userDto.getUid())) {
							tempMap.put("butName", "结清原贷款");
							tempMap.put("pageType", orderDto.getProcessId());
						}
					} else if (orderDto.getProcessId().contains("managerAudit")) {
						if (orderDto.getCurrentHandlerUid().equals(userDto.getUid())) {
							tempMap.put("butName", "分配订单");
							tempMap.put("pageType", orderDto.getProcessId());
						}
					} else if (orderDto.getProcessId().contains("repaymentMember")) {
						if (orderDto.getCurrentHandlerUid().equals(userDto.getUid())) {
							if (orderDto.getState().contains("退回")) {
								tempMap.put("butName", "重新指派还款专员");
							} else {
								tempMap.put("butName", "指派还款专员");
							}

							tempMap.put("pageType", orderDto.getProcessId());
						}
					} else if (null != userDto && userDto.getAgencyId() > 1
							&& (orderDto.getProcessId().contains("auditFirst")
									|| orderDto.getProcessId().contains("auditFinal")
									|| orderDto.getProcessId().contains("auditReview")
									|| orderDto.getProcessId().contains("auditOfficer"))) { // 机构用户，“风控初审”“风控终审”“首席风险官审批”合并为“风控审批”
						tempMap.put("state", "待风控审批");
					} else if (orderDto.getProcessId().contains("auditFinal")) {
						if (orderDto.getCurrentHandlerUid().equals(userDto.getUid())) {
							if (orderDto.getState().contains("退回")) {
								tempMap.put("butName", "重新风控终审");
							} else {
								tempMap.put("butName", "风控终审");
							}
							tempMap.put("pageType", orderDto.getProcessId());
						}
					} else if (orderDto.getProcessId().contains("auditReview")) {
						if (orderDto.getCurrentHandlerUid().equals(userDto.getUid())) {
							if (orderDto.getState().contains("退回")) {
								tempMap.put("butName", "重新复核审批");
							} else {
								tempMap.put("butName", "复核审批");
							}
							tempMap.put("pageType", orderDto.getProcessId());
						}
					} else if (orderDto.getProcessId().contains("auditOfficer")) {
						if (orderDto.getCurrentHandlerUid().equals(userDto.getUid())) {
							if (orderDto.getState().contains("退回")) {
								tempMap.put("butName", "重新风险官审批");
							} else {
								tempMap.put("butName", "风险官审批");
							}
							tempMap.put("pageType", orderDto.getProcessId());
						}
					} else if (orderDto.getProcessId().contains("applyLoan")) {
						if (orderDto.getCurrentHandlerUid().equals(userDto.getUid())) {
							if (orderDto.getState().contains("退回")) {
								tempMap.put("butName", "重新申请放款");
							} else {
								tempMap.put("butName", "申请放款");
							}
							tempMap.put("pageType", orderDto.getProcessId());
						}
					} else if (orderDto.getProcessId().contains("financialAudit")) {
						if (orderDto.getCurrentHandlerUid().equals(userDto.getUid())) {
							if (orderDto.getState().contains("退回")) {
								tempMap.put("butName", "重新财务审核");
							} else {
								tempMap.put("butName", "财务审核");
							}
							tempMap.put("pageType", orderDto.getProcessId());
						}
					} else if ("element".equals(orderDto.getProcessId())) {// 要件校验
						if (orderDto.getCurrentHandlerUid().equals(userDto.getUid())) {
							tempMap.put("butName", "要件校验");
							tempMap.put("pageType", orderDto.getProcessId() + "_process");
						}
					}
					if (isChanLoan) {
						tempMap.put("appShowKey1", "预计出款");
						tempMap.put("appShowKey2", "结清原贷款");
						tempMap.put("appShowValue1",
								orderDto.getAppShowValue1() == null ? "-" : orderDto.getAppShowValue1());
						tempMap.put("appShowValue2", orderDto.getAppShowValue2());
					}
				} else if (orderDto.getState().contains("待取证") && !orderDto.getState().contains("待取证抵押")) {
					if (isChanLoan) {
						tempMap.put("appShowKey1", "预计取证");
						if (orderDto.getAppShowValue2() != null && orderDto.getAppShowValue2().contains("-")) {
							tempMap.put("appShowKey2", "取证银行");
						} else {
							tempMap.put("appShowKey2", "取证地点");
						}
						tempMap.put("appShowValue1", orderDto.getAppShowValue1());
						tempMap.put("appShowValue2", orderDto.getAppShowValue2());
					}
					if (orderDto.getCurrentHandlerUid().equals(userDto.getUid())) {
						tempMap.put("butName", "取证");
						tempMap.put("pageType", orderDto.getProcessId());
					}
				} else if (orderDto.getState().contains("待注销")) {
					if (isChanLoan) {
						tempMap.put("appShowKey1", "预计注销");
						tempMap.put("appShowKey2", "国土局");
						tempMap.put("appShowValue1", orderDto.getAppShowValue1());
						tempMap.put("appShowValue2", orderDto.getAppShowValue2());
					}
					if (orderDto.getCurrentHandlerUid().equals(userDto.getUid())) {
						tempMap.put("butName", "注销");
						tempMap.put("pageType", orderDto.getProcessId());
					}
				} else if (orderDto.getState().contains("待过户")) {
					if (isChanLoan) {
						tempMap.put("appShowKey1", "预计过户");
						tempMap.put("appShowKey2", "国土局");
						tempMap.put("appShowValue1", orderDto.getAppShowValue1());
						tempMap.put("appShowValue2", orderDto.getAppShowValue2());
					}
					if (orderDto.getCurrentHandlerUid().equals(userDto.getUid())) {
						tempMap.put("butName", "过户");
						tempMap.put("pageType", orderDto.getProcessId());
					}
				} else if (orderDto.getState().contains("待领新证")) {
					if (isChanLoan) {
						tempMap.put("appShowKey1", "预计领新证");
						tempMap.put("appShowKey2", "国土局");
						tempMap.put("appShowValue1", orderDto.getAppShowValue1());
						tempMap.put("appShowValue2", orderDto.getAppShowValue2());
					}
					if (orderDto.getCurrentHandlerUid().equals(userDto.getUid())) {
						tempMap.put("butName", "领新证");
						tempMap.put("pageType", orderDto.getProcessId());
					}
				} else if (orderDto.getState().contains("待回款")) {
					// tempMap.put("appShowKey1", "预计回款");
					// tempMap.put("appShowKey2", "应回款");
					// tempMap.put("appShowValue1", orderDto.getAppShowValue1());
					// tempMap.put("appShowValue2", orderDto.getAppShowValue2());
				} else if ("待抵押".equals(orderDto.getState())) {
					if (isChanLoan) {
						tempMap.put("appShowKey1", "预计抵押");
						tempMap.put("appShowKey2", "国土局");
						tempMap.put("appShowValue1", orderDto.getAppShowValue1());
						tempMap.put("appShowValue2", orderDto.getAppShowValue2());
					}
					if (orderDto.getCurrentHandlerUid().equals(userDto.getUid())) {
						tempMap.put("butName", "抵押");
						tempMap.put("pageType", orderDto.getProcessId());
					}
				} else if (orderDto.getState().contains("待要件退还")) {
					if (orderDto.getCurrentHandlerUid().equals(userDto.getUid())) {
						tempMap.put("butName", "要件退还");
						tempMap.put("pageType", orderDto.getProcessId());
					}
				} else if (orderDto.getState().contains("提单")) {
					String butName = "";
					String pageType = "";
					if (userDto.getUid().equals(orderDto.getCurrentHandlerUid())) {
						// tempMap.put("isDelete", true);
						butName += "删除,完善订单";
						pageType += "isDelete,placeOrder";
					}
					if (orderDto.getState().contains("退回")) {
						butName = butName.replace("完善订单", "重新提交");
					}
					tempMap.put("butName", butName);
					tempMap.put("pageType", pageType);
				} else if (orderDto.getState().contains("公证")) {
					String butName = MapUtils.getString(tempMap, "butName", "");
					String pageType = MapUtils.getString(tempMap, "pageType", "");
					if (userDto.getUid().equals(orderDto.getCurrentHandlerUid())) {
						butName = StringUtils.isBlank(butName) ? "公证" : butName + ",公证";
						pageType = StringUtils.isBlank(pageType) ? "notarization" : pageType + ",notarization";
						tempMap.put("butName", butName);
						tempMap.put("pageType", pageType);
					}
				} else if (orderDto.getState().contains("面签")) {
					String butName = MapUtils.getString(tempMap, "butName", "");
					String pageType = MapUtils.getString(tempMap, "pageType", "");
					if (userDto.getUid().equals(orderDto.getCurrentHandlerUid())) {
						butName = StringUtils.isBlank(butName) ? "面签" : butName + ",面签";
						pageType = StringUtils.isBlank(pageType) ? "facesign" : pageType + ",facesign";
						tempMap.put("butName", butName);
						tempMap.put("pageType", pageType);
					}
				} else if (orderDto.getState().contains("待指派受理员")) {
					if (userDto.getUid().equals(orderDto.getCurrentHandlerUid())) {
						// tempMap.put("isDelete", true);
						tempMap.put("butName", "删除,指派受理员");
						tempMap.put("pageType", "isDelete,assignAcceptMember");
					}
				}
				// 房抵贷
				else if (orderDto.getState().contains("抵押品入库")) {
					if (userDto.getUid().equals(orderDto.getCurrentHandlerUid())) {
						tempMap.put("butName", "抵押品入库");
						tempMap.put("pageType", "fddMortgageStorage");
					}
				} else if (orderDto.getState().contains("抵押品出库")) {
					if (userDto.getUid().equals(orderDto.getCurrentHandlerUid())) {
						tempMap.put("butName", "抵押品出库");
						tempMap.put("pageType", "fddMortgageRelease");
					}
				} else if (orderDto.getState().contains("解押")) {
					if (userDto.getUid().equals(orderDto.getCurrentHandlerUid())) {
						tempMap.put("butName", "解押");
						tempMap.put("pageType", "release");
					}
				}
				// fddRepayment 待还款
				if ("04".equals(orderDto.getProductCode())
						&& ("fddRepayment".equals(orderDto.getProcessId())
								|| "fddMortgageRelease".equals(orderDto.getProcessId())
								|| "release".equals(orderDto.getProcessId()) || "rebate".equals(orderDto.getProcessId())
								|| "wanjie".equals(orderDto.getProcessId()))
						&& repaymentPlanMap.containsKey(orderDto.getOrderNo())
						&& !MapUtils.getString(repaymentPlanMap, orderDto.getOrderNo()).equals("7")) {
					String butName = MapUtils.getString(tempMap, "butName", "");
					String pageType = MapUtils.getString(tempMap, "pageType", "");
					butName = StringUtils.isBlank(butName) ? "还款计划表" : butName + ",还款计划表";
					pageType = StringUtils.isBlank(pageType) ? "repaymentPlan" : pageType + ",repaymentPlan";
					tempMap.put("butName", butName);
					tempMap.put("pageType", pageType);
				}
				if (!"04".equals(orderDto.getProductCode())) {
					// 出款报备
					String outloanreportProcess = ConfigUtil
							.getStringValue(Constants.BASE_FINANCE_OUTLOANREPORT_PROCESS, ConfigUtil.CONFIG_BASE);
					if (StringUtils.isNotBlank(outloanreportProcess) && null != orderDto
							&& StringUtils.isNotBlank(orderDto.getProcessId())
							&& outloanreportProcess.contains(orderDto.getProcessId()) && userDto.getUid() != null
							&& (userDto.getUid().equals(orderDto.getAcceptMemberUid())
									|| userDto.getUid().equals(orderDto.getChannelManagerUid()))) {
						String butName = MapUtils.getString(tempMap, "butName", "");
						String pageType = MapUtils.getString(tempMap, "pageType", "");
						int status = (null == reportMap || reportMap.size() <= 0) ? -1
								: MapUtils.getInteger(reportMap, orderDto.getOrderNo(), -1);
						if (status == -1 || status == 3) {
							butName = StringUtils.isBlank(butName) ? "出款报备" : butName + ",出款报备";
							pageType = StringUtils.isBlank(pageType) ? "editreport" : pageType + ",editreport";
						} else if (status == 2 || status == 0) {
							butName = StringUtils.isBlank(butName) ? "撤销出款报备,修改出款报备" : butName + ",撤销出款报备,修改出款报备";
							pageType = StringUtils.isBlank(pageType) ? "cancelReport,editreport"
									: pageType + ",cancelReport,editreport";
						}
						tempMap.put("butName", butName);
						tempMap.put("pageType", pageType);
					}

					// 回款报备
					String processConfig = ConfigUtil.getStringValue(Constants.BASE_FINANCE_LOAN_PROCESS,
							ConfigUtil.CONFIG_BASE);
					if (StringUtils.isNotBlank(processConfig) && null != orderDto
							&& StringUtils.isNotBlank(orderDto.getProcessId())
							&& processConfig.contains(orderDto.getProcessId())
							&& StringUtils.isNotBlank(userDto.getUid())
							&& (userDto.getUid().equals(orderDto.getAcceptMemberUid())
									|| userDto.getUid().equals(orderDto.getChannelManagerUid()))) {
						String butName = MapUtils.getString(tempMap, "butName", "");
						String pageType = MapUtils.getString(tempMap, "pageType", "");
						int status = (null == paymentreportMap || paymentreportMap.size() <= 0) ? -1
								: MapUtils.getInteger(paymentreportMap, orderDto.getOrderNo(), -1);
						if (status == -1 || status == 3) {
							butName = StringUtils.isBlank(butName) ? "回款报备" : butName + ",回款报备";
							pageType = StringUtils.isBlank(pageType) ? "editPaymentReport"
									: pageType + ",editPaymentReport";
						} else if (status == 2 || status == 0) {
							butName = StringUtils.isBlank(butName) ? "撤销回款报备,修改回款报备" : butName + ",撤销回款报备,修改回款报备";
							pageType = StringUtils.isBlank(pageType) ? "cancelPaymentReport,editPaymentReport"
									: pageType + ",cancelPaymentReport,editPaymentReport";
						}
						tempMap.put("butName", butName);
						tempMap.put("pageType", pageType);
					}
				}

				String butName = MapUtils.getString(tempMap, "butName", "");
				String pageType = MapUtils.getString(tempMap, "pageType", "");
				if (StringUtil.isNotBlank(orderDto.getIsUp())) {
					butName = StringUtils.isBlank(butName) ? "取消置顶" : butName + ",取消置顶";
					pageType = StringUtils.isBlank(pageType) ? "orderDown" : pageType + ",orderDown";
				} else {
					butName = StringUtils.isBlank(butName) ? "置顶" : butName + ",置顶";
					pageType = StringUtils.isBlank(pageType) ? "orderUp" : pageType + ",orderUp";
				}
				// 撤回功能
				if (StringUtil.isNotBlank(orderDto.getPreviousHandlerUid())
						&& orderDto.getPreviousHandlerUid().equals(userDto.getUid())
						&& !"isBackExpenses".equals(orderDto.getProcessId())
						&& !"placeOrder".equals(orderDto.getProcessId()) && !"forensics".equals(orderDto.getProcessId())
						&& !"cancellation".equals(orderDto.getProcessId())
						&& !"transfer".equals(orderDto.getProcessId()) && !"newlicense".equals(orderDto.getProcessId())
						&& !"receivableFor".equals(orderDto.getProcessId())
						&& !"receivableForFirst".equals(orderDto.getProcessId())
						&& !"receivableForEnd".equals(orderDto.getProcessId())
						&& !"wanjie".equals(orderDto.getProcessId()) && !"closeOrder".equals(orderDto.getProcessId())
						&& !(orderDto.getState().contains("退回"))
						&& !("mortgage".equals(orderDto.getProcessId()) && "03".equals(orderDto.getProductCode()))
						&& !"fddMortgageRelease".equals(orderDto.getProcessId())) {
					butName = StringUtils.isBlank(butName) ? "撤回" : "撤回," + butName;
					pageType = StringUtils.isBlank(pageType) ? "withdrawOrder" : "withdrawOrder," + pageType;
				}
				tempMap.put("butName", butName);
				tempMap.put("pageType", pageType);
				if ("04".equals(orderDto.getProductCode())) { // 房抵贷不展示
					tempMap.remove("appShowKey1");
					tempMap.remove("appShowKey2");
					tempMap.remove("appShowValue1");
					tempMap.remove("appShowValue2");
				}
				orderList.add(tempMap);
			}

			if (userDto != null) {
				retMap.put("brush", assembleBrush(userDto, true));
			}
			if ((isExampleOrder && requestSource != null && requestSource.contains("快鸽"))||userDto.getIsEnable() == 100) {// 示例订单
				//orderList = exampleOrder();
				orderList = new ArrayList<Map<String,Object>>();
			}
			retMap.put("orderList", orderList);
			retMap.put("isFace", 1);//是否人脸
			RespHelper.setSuccessDataObject(resp, retMap);
		} catch (Exception e) {
			e.printStackTrace();
			RespHelper.setFailDataObject(resp, null, RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}

	/**
	 * 是否畅贷订单号
	 * 
	 * @param orderNo
	 * @return
	 */
	// public boolean isChangLoan(String orderNo) {
	// OrderBaseBorrowRelationDto orderBaseBorrowRelationDto =
	// orderBaseBorrowRelationService
	// .selectRelationByOrderNo(orderNo);
	// if (orderBaseBorrowRelationDto != null) {
	// return true;
	// } else {
	// return false;
	// }
	// }
	/**
	 * App详情
	 * 
	 * @param request
	 * @param orderListDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/appDetail")
	public RespDataObject<Map<String, Object>> appDetail(HttpServletRequest request,
			@RequestBody OrderListDto orderListDto) {
		RespDataObject<Map<String, Object>> resp = new RespDataObject<Map<String, Object>>();
		try {
			UserDto user = getUserDto(request);
			Map<String, Object> retMap = new HashMap<String, Object>();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("pageClass", "tbl_sl_detail_page");
			map.put("regionClass", "slDetail");
			RespData<Map<String, Object>> respData = httpUtil.getRespData(Constants.LINK_CREDIT,
					"/credit/config/page/base/v/getPageTabRegionConfigDto", map, Map.class);
			List<Map<String, Object>> configlist = respData.getData();
			if (configlist.size() < 12) {
				RespHelper.setFailDataObject(resp, null, "数据库详情slDetail配置缺失数量(小于12)");
				return resp;
			}
			String orderNo = orderListDto.getOrderNo();
			orderListDto = orderBaseService.selectDetail(orderListDto.getOrderNo());
			boolean isRelationC = orderListDto.getProductCode().equals("03")
					&& StringUtils.isNotBlank(orderListDto.getRelationOrderNo());
			boolean isChangloan = "03".equals(orderListDto.getProductCode());
			int productId = 440301;
			if (orderListDto != null) {
				try {
					productId = NumberUtils.toInt(orderListDto.getCityCode() + orderListDto.getProductCode());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("productId", productId);
			Map<String, Object> authMap = new HashMap<String, Object>();
			List<String> authList = new ArrayList<String>();
			try {
				authMap = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/data/api/v/selectAuthorityByProductId",
						params, Map.class);
				authList = CommonDataUtil.getUserDtoByUidAndMobile(user.getUid()).getAuthIds();
			} catch (Exception e) {
				e.printStackTrace();
			}
			String channelManagerUid;
			OrderBaseBorrowDto baseBorrowDto = orderBaseBorrowService.selectOrderBorrowByOrderNo(orderNo);
			OrderBaseBorrowDto slBaseBorrowDto = new OrderBaseBorrowDto();
			if (isRelationC) {
				slBaseBorrowDto = orderBaseBorrowService.selectOrderBorrowByOrderNo(orderListDto.getRelationOrderNo());
				channelManagerUid = slBaseBorrowDto.getChannelManagerUid();
			} else {
				channelManagerUid = baseBorrowDto.getChannelManagerUid();
			}
			List<OrderFlowDto> list = orderFlowService.selectOrderFlowList(orderListDto.getOrderNo());
			OrderFlowDto orderFlow = null;
			Iterator<OrderFlowDto> its = list.iterator();
			String orderNos = ConfigUtil.getStringValue(Constants.BASE_ORDER_EXAMPLE, ConfigUtil.CONFIG_BASE);

			List<ProductProcessDto> productProcessDtoList = getProductProcessDto(productId);
			// List<UserDto> userList = getUserDtoList();
			while (its.hasNext()) {
				orderFlow = its.next();
				logger.info(orderFlow.getCurrentProcessId() + "----" + orderFlow.getCurrentProcessName());
				if ("financialStatement".equals(orderFlow.getCurrentProcessId())) {
					orderFlow.setCurrentProcessName("财务制单");
				} else if ("financialAudit".equals(orderFlow.getCurrentProcessId())) {
					orderFlow.setCurrentProcessName("财务审核");
				}
				// 删除待还款节点
				if (orderFlow.getCurrentProcessId().equals("fddRepayment")) {
					its.remove();
				}
				for (ProductProcessDto productProcessDto : productProcessDtoList) {
					if (orderFlow.getCurrentProcessId().equals(productProcessDto.getProcessId())) {
						orderFlow.setCurrentProcessName(productProcessDto.getProcessName());
					}
					if (orderFlow.getNextProcessId().equals(productProcessDto.getProcessId())) {
						orderFlow.setNextProcessName(productProcessDto.getProcessName());
					}
				}

				orderFlow.setHandleName(CommonDataUtil.getUserDtoByUidAndMobile(orderFlow.getHandleUid()).getName());

				// for (UserDto userDto : userList) {
				// if(userDto.getUid().equals(orderFlow.getHandleUid())){
				// orderFlow.setHandleName(CommonDataUtil.CommonDataUtil.getUserDtoByUidAndMobileAndMobile(orderFlow.getHandleUid()).getName());
				// break;
				// }
				// }

				orderFlow.setCreateTimeStr(
						"系统提交时间：" + DateUtil.getDateByFmt(orderFlow.getHandleTime(), DateUtil.FMT_TYPE1));
				if ("notarization".equals(orderFlow.getCurrentProcessId())) {
					NotarizationDto obj = new NotarizationDto();
					obj.setOrderNo(orderNo);
					obj = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/process/notarization/v/detail", obj,
							NotarizationDto.class);
					orderFlow.setHandleTimeStr(
							"公证时间：" + DateUtil.getDateByFmt(obj.getNotarizationTime(), DateUtil.FMT_TYPE2));
				} else if ("facesign".equals(orderFlow.getCurrentProcessId())) {
					FaceSignDto obj = new FaceSignDto();
					obj.setOrderNo(orderNo);
					obj = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/process/facesign/v/detail", obj,
							FaceSignDto.class);
					if (obj != null && obj.getFaceSignTime() != null) {
						orderFlow.setHandleTimeStr(
								"面签时间：" + DateUtil.getDateByFmt(obj.getFaceSignTime(), DateUtil.FMT_TYPE2));
					}
				} else if ("lending".equals(orderFlow.getCurrentProcessId())) {
					LendingDto obj = new LendingDto();
					obj.setOrderNo(orderNo);
					obj = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/finance/lending/v/detail", obj,
							LendingDto.class);
					orderFlow.setHandleTimeStr(
							"放款时间：" + DateUtil.getDateByFmt(obj.getLendingTime(), DateUtil.FMT_TYPE2));
				} else if ("foreclosure".equals(orderFlow.getCurrentProcessId())) {
					ForeclosureDto obj = new ForeclosureDto();
					obj.setOrderNo(orderNo);
					obj = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/process/foreclosure/v/detail", obj,
							ForeclosureDto.class);
					log.info("结清原贷款对象：" + obj);
					log.info("结清原贷款时间：" + obj.getForeclosureTime());
					orderFlow.setHandleTimeStr(
							"结清时间：" + DateUtil.getDateByFmt(obj.getForeclosureTime(), DateUtil.FMT_TYPE2));
				} else if ("forensics".equals(orderFlow.getCurrentProcessId())) {
					ForensicsDto obj = new ForensicsDto();
					obj.setOrderNo(orderNo);
					obj = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/process/forensics/v/detail", obj,
							ForensicsDto.class);
					orderFlow.setHandleTimeStr(
							"取证时间：" + DateUtil.getDateByFmt(obj.getLicenseRevTime(), DateUtil.FMT_TYPE2));
				} else if ("cancellation".equals(orderFlow.getCurrentProcessId())) {
					CancellationDto obj = new CancellationDto();
					obj.setOrderNo(orderNo);
					obj = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/process/cancellation/v/detail", obj,
							CancellationDto.class);
					orderFlow
							.setHandleTimeStr("注销时间：" + DateUtil.getDateByFmt(obj.getCancelTime(), DateUtil.FMT_TYPE2));
				} else if ("transfer".equals(orderFlow.getCurrentProcessId())) {
					TransferDto obj = new TransferDto();
					obj.setOrderNo(orderNo);
					obj = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/process/transfer/v/detail", obj,
							TransferDto.class);
					orderFlow.setHandleTimeStr(
							"过户时间：" + DateUtil.getDateByFmt(obj.getTransferTime(), DateUtil.FMT_TYPE2));
				} else if ("newlicense".equals(orderFlow.getCurrentProcessId())) {
					NewlicenseDto obj = new NewlicenseDto();
					obj.setOrderNo(orderNo);
					obj = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/process/newlicense/v/detail", obj,
							NewlicenseDto.class);
					orderFlow.setHandleTimeStr(
							"领新证时间：" + DateUtil.getDateByFmt(obj.getNewlicenseTime(), DateUtil.FMT_TYPE2));
				} else if ("mortgage".equals(orderFlow.getCurrentProcessId())) {
					MortgageDto obj = new MortgageDto();
					obj.setOrderNo(orderNo);
					obj = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/process/mortgage/v/detail", obj,
							MortgageDto.class);
					orderFlow.setHandleTimeStr(
							"抵押时间：" + DateUtil.getDateByFmt(obj.getMortgageTime(), DateUtil.FMT_TYPE2));
				} else if ("receivableForFirst".equals(orderFlow.getCurrentProcessId())
						|| "receivableForEnd".equals(orderFlow.getCurrentProcessId())
						|| "receivableFor".equals(orderFlow.getCurrentProcessId())) {
					ReceivableForDto obj = new ReceivableForDto();
					obj.setOrderNo(orderNo);
					List<ReceivableForDto> listFor = httpUtil.getList(Constants.LINK_CREDIT,
							"/credit/finance/receivableFor/v/detail", obj, ReceivableForDto.class);
					if (listFor != null && listFor.size() > 0) {
						if ("receivableForFirst".equals(orderFlow.getCurrentProcessId())) {
							orderFlow.setHandleTimeStr(
									"首期回款时间：" + listFor.get(0).getPayMentAmountDate().substring(0, 10));
						} else if ("receivableForEnd".equals(orderFlow.getCurrentProcessId())) {
							orderFlow.setHandleTimeStr("尾期回款时间："
									+ listFor.get(listFor.size() - 1).getPayMentAmountDate().substring(0, 10));
						} else if ("receivableFor".equals(orderFlow.getCurrentProcessId())) {
							orderFlow
									.setHandleTimeStr("回款时间：" + listFor.get(0).getPayMentAmountDate().substring(0, 10));
						}
					}
				}
				if (orderFlow.getCurrentProcessId().equals("wanjie")) {
					continue;
				}
				//log.info("authList:"+authList);
				// log.info("user.isEnable:"+user.getIsEnable());
				if (null != authList && 0 == user.getIsEnable()) {
					String authA = MapUtils.getString(authMap, orderFlow.getCurrentProcessId() + "[A]");
					String authB = MapUtils.getString(authMap, orderFlow.getCurrentProcessId() + "[B]");
					if (!(authList.contains(authA + "") || authList.contains(authB + ""))) {
						orderFlow.setKey(orderFlow.getCurrentProcessId());
						orderFlow.setCurrentProcessId("noAuth");
					}
				}
				// 非总部机构节点重构 (App当前节点为“noAuth”,均不能点节点详情)
				if (user.getAgencyId() != 1) {
					if (ArrayUtils.contains(OrderFlowDto.arrayRiskName, orderFlow.getCurrentProcessName())) {
						this.logger.info(orderFlow.getCurrentProcessName() + "##" + orderFlow.getNextProcessName());
						if (ArrayUtils.contains(OrderFlowDto.arrayRiskName, orderFlow.getNextProcessName())) {
							its.remove();
							continue;
						}
						orderFlow.setCurrentProcessId("noAuth");
						orderFlow.setCurrentProcessName(OrderFlowDto.ORDER_NODE_RISK);
					}
					// 节点及节点详情展示控制
					if (ArrayUtils.contains(OrderFlowDto.arrayNodeWithout, orderFlow.getKey())) {
						its.remove();
						continue;
					} else if (ArrayUtils.contains(OrderFlowDto.arrayNodeDetailWithout,
							orderFlow.getCurrentProcessId())) {
						orderFlow.setCurrentProcessId("noAuth");
					} else if (StringUtils.isNotEmpty(orderFlow.getKey())
							&& ArrayUtils.contains(OrderFlowDto.arrayNodeDetailNeed, orderFlow.getKey())) {
						orderFlow.setCurrentProcessId(orderFlow.getKey());
					}
				}
				// 普通用户订单示例订单也不能点击节点详情
				if (0 != user.getIsEnable() || orderNos.contains(orderNo)) {
					orderFlow.setCurrentProcessId("noAuth");
				}
			}
			Map<String, Object> orderDeatil = new LinkedHashMap<String, Object>();
			String key = "";
			orderDeatil.put(MapUtils.getString(configlist.get(0), "title"), baseBorrowDto.getCityName());
			key += MapUtils.getString(configlist.get(0), "title") + ",";
			orderDeatil.put(MapUtils.getString(configlist.get(1), "title"), baseBorrowDto.getProductName());
			key += MapUtils.getString(configlist.get(1), "title") + ",";
			orderDeatil.put(MapUtils.getString(configlist.get(2), "title"), orderListDto.getCustomerName());
			key += MapUtils.getString(configlist.get(2), "title") + ",";
			orderDeatil.put(MapUtils.getString(configlist.get(3), "title"),
					(baseBorrowDto.getLoanAmount() == null ? "-" : baseBorrowDto.getLoanAmount()) + "万元");
			orderDeatil.put(MapUtils.getString(configlist.get(4), "title"),
					(baseBorrowDto.getBorrowingDays() == null ? "-" : baseBorrowDto.getBorrowingDays().toString())
							+ "天");
			key += MapUtils.getString(configlist.get(3), "title") + ",";
			key += MapUtils.getString(configlist.get(4), "title") + ",";

			// Map<String, Object> paramsMap = new HashMap<String, Object>();
			// paramsMap.put("agencyId", baseBorrowDto.getCooperativeAgencyId());
			// AgencyDto agencyDto = httpUtil.getObject(Constants.LINK_CREDIT,
			// "/credit/customer/agency/v/getAgencyDto", paramsMap, AgencyDto.class);
			if (orderListDto.getCooperativeAgencyName() != null && orderListDto.getAgencyId() <= 1) {
				orderDeatil.put(MapUtils.getString(configlist.get(5), "title"),
						orderListDto.getCooperativeAgencyName());
				key += MapUtils.getString(configlist.get(5), "title") + ",";
			} else if (orderListDto.getCooperativeAgencyName() == null && orderListDto.getAgencyId() <= 1) {
				key += MapUtils.getString(configlist.get(5), "title") + ",";
				orderDeatil.put(MapUtils.getString(configlist.get(5), "title"), "-");
			} else {
				// 机构订单没有合作机构
			}
			if (StringUtils.isNotBlank(baseBorrowDto.getChannelManagerUid())) {
				orderDeatil.put(MapUtils.getString(configlist.get(6), "title"),
						CommonDataUtil.getUserDtoByUidAndMobile(channelManagerUid).getName());
			} else {
				orderDeatil.put(MapUtils.getString(configlist.get(6), "title"), "-");
			}
			ForeclosureDto obj = new ForeclosureDto();
			obj.setOrderNo(orderListDto.getOrderNo());
			obj = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/process/foreclosure/v/detail", obj,
					ForeclosureDto.class);
			if (!"04".equals(orderListDto.getProductCode())) {
				key += MapUtils.getString(configlist.get(6), "title") + ",";
				// 畅贷不展示
				if (!isChangloan) {
					if (obj != null) {
						orderDeatil.put(MapUtils.getString(configlist.get(7), "title"),
								obj.getForeclosureTimeStr() == null ? "-" : obj.getForeclosureTimeStr());
					} else {
						orderDeatil.put(MapUtils.getString(configlist.get(7), "title"), "-");
					}
					key += MapUtils.getString(configlist.get(7), "title") + ",";
				}

				// List<BankDto> bankDtos = null;
				// List<SubBankDto> subBankDtos = null;
				// 畅贷不展示
				if (!isChangloan) {
					if (baseBorrowDto.getIsOldLoanBank() != null) {
						if (baseBorrowDto.getIsOldLoanBank().equals(1)) {
							String loanBankName = "";
							if (baseBorrowDto.getOldLoanBankNameId() != null) {
								loanBankName = CommonDataUtil.getBankNameById(baseBorrowDto.getOldLoanBankNameId())
										.getName();
							}
							if (baseBorrowDto.getOldLoanBankSubNameId() != null) {
								loanBankName += "-" + CommonDataUtil
										.getSubBankNameById(baseBorrowDto.getOldLoanBankSubNameId()).getName();
								;
							}
							if (StringUtils.isEmpty(loanBankName)) {
								loanBankName = "-";
							}
							orderDeatil.put(MapUtils.getString(configlist.get(8), "title"), loanBankName);
						} else {
							orderDeatil.put(MapUtils.getString(configlist.get(8), "title"),
									StringUtils.isEmpty(baseBorrowDto.getOldLoanBankName()) ? "-"
											: baseBorrowDto.getOldLoanBankName());
						}
					} else {
						orderDeatil.put(MapUtils.getString(configlist.get(8), "title"), "-");
					}
					key += MapUtils.getString(configlist.get(8), "title") + ",";
				}
				// 畅贷不展示
				if (!isChangloan) {
					if (baseBorrowDto.getIsLoanBank() != null) {
						if (baseBorrowDto.getIsLoanBank().equals(1)) {
							String loanBankName = "";
							if (baseBorrowDto.getLoanBankNameId() != null) {
								loanBankName = CommonDataUtil.getBankNameById(baseBorrowDto.getLoanBankNameId())
										.getName();
							}
							if (baseBorrowDto.getLoanSubBankNameId() != null) {
								loanBankName += "-" + CommonDataUtil
										.getSubBankNameById(baseBorrowDto.getLoanSubBankNameId()).getName();
							}
							if (StringUtils.isEmpty(loanBankName)) {
								loanBankName = "-";
							}
							orderDeatil.put(MapUtils.getString(configlist.get(9), "title"), loanBankName);
						} else {
							orderDeatil.put(MapUtils.getString(configlist.get(9), "title"),
									StringUtils.isEmpty(baseBorrowDto.getLoanBankName()) ? "-"
											: baseBorrowDto.getLoanBankName());
						}
					} else {
						orderDeatil.put(MapUtils.getString(configlist.get(9), "title"), "-");
					}
					key += MapUtils.getString(configlist.get(9), "title") + ",";
				}
				// 畅贷不展示
				if (!isChangloan) {
					if (StringUtils.isNotEmpty(orderListDto.getLendingTime())) {
						orderDeatil.put(MapUtils.getString(configlist.get(10), "title"),
								orderListDto.getLendingTime().substring(0, 10));
					} else {
						orderDeatil.put(MapUtils.getString(configlist.get(10), "title"), "-");
					}
					key += MapUtils.getString(configlist.get(10), "title") + ",";

					if (StringUtils.isNotEmpty(orderListDto.getPlanPaymentTime())) {
						orderDeatil.put(MapUtils.getString(configlist.get(11), "title") + "",
								orderListDto.getPlanPaymentTime().substring(0, 10));
					} else {
						orderDeatil.put(MapUtils.getString(configlist.get(11), "title"), "-");
					}
					key += MapUtils.getString(configlist.get(11), "title") + "";
				}
				// 畅贷展示受理员
				if (isChangloan) {
					String acceptMemberName = StringUtils.isNotBlank(orderListDto.getAcceptMemberName())
							? orderListDto.getAcceptMemberName()
							: "-";
					orderDeatil.put(MapUtils.getString(configlist.get(12), "title"), acceptMemberName);
					key += MapUtils.getString(configlist.get(12), "title") + "";
				}
			} else {
				key += MapUtils.getString(configlist.get(6), "title");
			}

			// 普通用户订单或者示例订单不可操作影像资料
			if (StringUtils.isEmpty(orderListDto.getAcceptMemberUid()) || 0 != user.getIsEnable()
					|| orderNos.contains(orderNo)) {
				retMap.put("isAcceptMember", false);
			} else if (null != user && null != orderListDto && (user.getUid().equals(orderListDto.getAcceptMemberUid())
					|| user.getUid().equals(orderListDto.getFacesignUid()))) {
				retMap.put("isAcceptMember", true);
			} else {
				retMap.put("isAcceptMember", false);
			}
			// 是否显示查看影像资料入口
			if (0 != user.getIsEnable() || orderNos.contains(orderNo)) {
				retMap.put("see", false);
			} else {
				retMap.put("see", true);
			}
			retMap.put("orderFlowList", list);
			retMap.put("order", orderDeatil);
			orderDeatil.put("keys", key);
			retMap.put("upper", orderDeatil);
			RespHelper.setSuccessDataObject(resp, retMap);
		} catch (Exception e) {
			log.error("获取订单详情异常", e);
			RespHelper.setFailDataObject(resp, null, RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}

	/**
	 * App节点详情
	 * 
	 * @param request
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/appNodeDetail")
	public RespDataObject<Map<String, Object>> appNodeDetail(HttpServletRequest request,
			@RequestBody Map<String, Object> params) {
		RespDataObject<Map<String, Object>> resp = new RespDataObject<Map<String, Object>>();
		try {
			String orderNo = MapUtils.getString(params, "orderNo", "");
			String processId = MapUtils.getString(params, "processId", "");
			if ("".equals(orderNo) || "".equals(processId)) {
				RespHelper.setFailDataObject(resp, null, RespStatusEnum.PARAMETER_ERROR.getMsg());
				return resp;
			}
			RespHelper.setSuccessDataObject(resp, appNodeDetailByOrderNo(processId, orderNo, request));
		} catch (Exception e) {
			e.printStackTrace();
			RespHelper.setFailDataObject(resp, null, RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}

	/**
	 * 节点详情
	 * 
	 * @param processId
	 * @param orderNo
	 * @param request
	 * @return
	 */
	private Map<String, Object> appNodeDetailByOrderNo(String processId, String orderNo, HttpServletRequest request) {
		Map<String, Object> retMap = new HashMap<String, Object>();

		OrderListDto orderListDto = orderBaseService.selectDetail(orderNo);
		OrderBaseBorrowDto borrow = orderBaseBorrowService.selectOrderBorrowByOrderNo(orderNo);
		UserDto user = getUserDto(request);
		log.info("信息：" + orderListDto);
		if ("noAuth".equals(processId)) {
			Map<String, Object> upperMap = new LinkedHashMap<String, Object>();
			upperMap.put("权限", "暂无权限");
			upperMap.put("keys", "权限");
			retMap.put("upper", upperMap);
		} else if("signInsurancePolicy".equals(processId)) {//签订投保单
			SignInsuranceDto signInsuranceDto = new SignInsuranceDto();
			signInsuranceDto.setOrderNo(orderNo);
			signInsuranceDto = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/process/signInsurance/v/detail",
					signInsuranceDto, SignInsuranceDto.class);
			// 详情上面
			Map<String, Object> upperMap = new LinkedHashMap<String, Object>();
			if(signInsuranceDto.getSignTime() != null) {
				upperMap.put("签订时间", DateUtil.getDateByFmt(signInsuranceDto.getSignTime(), DateUtil.FMT_TYPE2));
			} else {
				upperMap.put("签订时间", "-");
			}
			String keys = "签订时间";
			if (StringUtils.isNotEmpty(signInsuranceDto.getRemark())) {
				upperMap.put("备注", signInsuranceDto.getRemark());
				keys += ",备注";
			}
			// key排序
			upperMap.put("keys", keys);
			// 详情下面
			Map<String, Object> downMap = new LinkedHashMap<String, Object>();
			String imgs = cleanImgUrl(signInsuranceDto.getSignImg());
			downMap.put("imgs", imgs);
			retMap.put("upper", upperMap);
			retMap.put("down", downMap);
		} else if("uploadInsurancePolicy".equals(processId)) {//上传电子保单
			UploadInsuranceDto uploadInsuranceDto = new UploadInsuranceDto();
			uploadInsuranceDto.setOrderNo(orderNo);
			uploadInsuranceDto = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/process/uploadInsurance/v/detail",
					uploadInsuranceDto, UploadInsuranceDto.class);
			// 详情上面
			Map<String, Object> upperMap = new LinkedHashMap<String, Object>();
			String keys = "备注";
			if (StringUtils.isNotEmpty(uploadInsuranceDto.getRemark())) {
				upperMap.put("备注", uploadInsuranceDto.getRemark());
			} else {
				upperMap.put("备注", "-");
			}
			// key排序
			upperMap.put("keys", keys);
			// 详情下面
			Map<String, Object> downMap = new LinkedHashMap<String, Object>();
			String imgs = cleanImgUrl(uploadInsuranceDto.getUploadInsurancePdf());
			downMap.put("imgs", imgs);
			retMap.put("upper", upperMap);
			retMap.put("down", downMap);
		} else if ("notarization".equals(processId)) {
			NotarizationDto notarizationDto = new NotarizationDto();
			notarizationDto.setOrderNo(orderNo);
			notarizationDto.setRelationOrderNo(orderListDto.getRelationOrderNo());
			notarizationDto = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/process/notarization/v/detail",
					notarizationDto, NotarizationDto.class);

			// 详情上面
			Map<String, Object> upperMap = new LinkedHashMap<String, Object>();
			if (notarizationDto.getNotarizationTime() != null) {
				upperMap.put("公证时间", DateUtil.getDateByFmt(notarizationDto.getNotarizationTime(), DateUtil.FMT_TYPE2));
			} else {
				upperMap.put("公证时间", "-");
			}
			System.out.println(notarizationDto.getNotarizationAddress());
			upperMap.put("公证地点", notarizationDto.getNotarizationAddress());
			if (notarizationDto.getEstimatedTime() != null) {
				upperMap.put("预计出款日期", DateUtil.getDateByFmt(notarizationDto.getEstimatedTime(), DateUtil.FMT_TYPE2));
			} else {
				upperMap.put("预计出款日期", "-");
			}

			String keys = "公证时间,公证地点,预计出款日期";
			if (null != notarizationDto.getNotarizationType() && !"".equals(notarizationDto.getNotarizationType())) {
				upperMap.put("公证类型", notarizationDto.getNotarizationType());
				keys += ",公证类型";
			}
			if (StringUtils.isNotEmpty(notarizationDto.getRemark())) {
				upperMap.put("备注", notarizationDto.getRemark());
				keys += ",备注";
			}
			// key排序
			upperMap.put("keys", keys);

			// 详情下面
			Map<String, Object> downMap = new LinkedHashMap<String, Object>();
//			String imgs = cleanImgUrl(notarizationDto.getNotarizationImg());
//			downMap.put("imgs", imgs);

			retMap.put("upper", upperMap);
			retMap.put("down", downMap);

		} else if ("facesign".equals(processId)) {
			FaceSignDto facesign = new FaceSignDto();
			facesign.setOrderNo(orderNo);
			facesign = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/process/facesign/v/detail", facesign,
					FaceSignDto.class);

			Map<String, Object> upperMap = new LinkedHashMap<String, Object>();

			upperMap.put("面签时间", DateUtil.getDateByFmt(facesign.getFaceSignTime(), DateUtil.FMT_TYPE2));

			String keys = "面签时间";

			// 是否陕国投面签
			boolean sgt = false;
			Map<String, Object> orderMap = new HashMap<String, Object>();
			orderMap.put("orderNo", orderNo);
			RespDataObject<Map<String, Object>> respData = httpUtil.getRespDataObject(Constants.LINK_CREDIT,
					"/credit/risk/allocationfund/v/getOrderNoMosaicFundCode", orderMap, Map.class);
			Map<String, Object> mm = (Map<String, Object>) respData.getData();
			Iterator<Map.Entry<String, Object>> entries = mm.entrySet().iterator();
			while (entries.hasNext()) {
				Map.Entry<String, Object> entry = entries.next();
				if (entry.getValue().toString().contains("1000")) {
					sgt = true;
				}
			}
			if (sgt) {
				upperMap.put("账户类型", facesign.getAccountType());
				upperMap.put("客户名称", facesign.getCustomerName());
				upperMap.put("账户所属银行", facesign.getBankName());
				upperMap.put("银行账户号", facesign.getBankCardNo());
				upperMap.put("证件类型", facesign.getCertificateType());
				upperMap.put("证件号", facesign.getCertificateNo());
				upperMap.put("银行预留手机号", facesign.getMobile());
				String returnOne = facesign.getReturnOne();
				String returnTwo = facesign.getReturnTwo();
				upperMap.put("绑定支付渠道1", returnOne == null ? "-" : returnOne.contains("成功") ? "中金支付 成功" : returnOne);
				upperMap.put("绑定支付渠道2", returnTwo == null ? "-" : returnTwo.contains("成功") ? "宝付支付 成功" : returnTwo);
				keys = "账户类型,客户名称,账户所属银行,银行账户号,证件类型,证件号,银行预留手机号,绑定支付渠道1,绑定支付渠道2,面签时间";
			}

			List<FacesignRecognitionDto> respList = null;
			// 获取需要人脸识别支持的城市
			// String faceCityCode =
			// ConfigUtil.getStringValue(Constants.BASE_FACE_CITY_CODE,ConfigUtil.CONFIG_BASE);
			// if(StringUtils.isNotBlank(faceCityCode)
			// &&faceCityCode.contains(orderListDto.getCityCode())){
			// FacesignRecognitionDto obj = new FacesignRecognitionDto();
			// obj.setOrderNo(orderNo);
			// respList = httpUtil.getList(Constants.LINK_CREDIT,
			// "/credit/process/facesign/v/faceRecognitionDetail", obj,
			// FacesignRecognitionDto.class);
			// upperMap.put("人脸识别","未识别");
			// keys += ",人脸识别";
			// }
			// if(null!=respList&&respList.size()>0){
			// boolean isSuccess = false;
			// for (FacesignRecognitionDto f:respList){
			// if(1==f.getIsSuccess()){
			// isSuccess = true;
			// }
			// if(2==f.getIsSuccess()){
			// upperMap.put("人脸识别","匹配失败");
			// isSuccess = false;
			// break;
			// }
			// }
			// if(isSuccess) {
			// upperMap.put("人脸识别","匹配成功");
			// }
			// }
			if (StringUtils.isNotEmpty(facesign.getRemark())) {
				upperMap.put("备注", facesign.getRemark());
				keys += ",备注";
			}

			upperMap.put("keys", keys);

			// 详情下面
			Map<String, Object> downMap = new LinkedHashMap<String, Object>();
			String imgs = cleanImgUrl(facesign.getFaceSignPhoto());
			downMap.put("imgs", imgs);

			retMap.put("upper", upperMap);
			retMap.put("down", downMap);
		} else if ("managerAudit".equals(processId) && orderListDto != null
				&& ("01".equals(orderListDto.getProductCode()) || "02".equals(orderListDto.getProductCode())
						|| "03".equals(orderListDto.getProductCode()) || "04".equals(orderListDto.getProductCode())
						|| "05".equals(orderListDto.getProductCode())|| "06".equals(orderListDto.getProductCode())
						|| "07".equals(orderListDto.getProductCode()))) {
			ManagerAuditDto managerAudit = new ManagerAuditDto();
			managerAudit.setOrderNo(orderNo);
			managerAudit = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/process/managerAudit/v/detail",
					managerAudit, ManagerAuditDto.class);

			Map<String, Object> upperMap = new LinkedHashMap<String, Object>();
			String managerAuditName = "-";
			if (StringUtils.isNotBlank(managerAudit.getManagerAuditName())) {
				managerAuditName = managerAudit.getManagerAuditName();
			}
			upperMap.put("分配人", managerAuditName);
			upperMap.put("分配时间", DateUtil.getDateByFmt(managerAudit.getAuditTime(), DateUtil.FMT_TYPE2));

			// key排序
			upperMap.put("keys", "分配人,分配时间");
			Map<String, Object> downMap = new LinkedHashMap<String, Object>();
			downMap.put("备注", managerAudit.getRemark());

			retMap.put("upper", upperMap);
			retMap.put("down", downMap);

		} else if ("auditFirst".equals(processId)) {
			FirstAuditDto firstAuditDto = new FirstAuditDto();
			firstAuditDto.setOrderNo(orderNo);
			firstAuditDto = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/risk/first/v/appDetail", firstAuditDto,
					FirstAuditDto.class);

			Map<String, Object> upperMap = new LinkedHashMap<String, Object>();
			String handleName = "-";
			if (StringUtils.isNotBlank(firstAuditDto.getHandleName())) {
				handleName = firstAuditDto.getHandleName();
			}
			upperMap.put("初审人", handleName);

			upperMap.put("初审时间", DateUtil.getDateByFmt(firstAuditDto.getAuditTime(), DateUtil.FMT_TYPE2));
			String auditStatus = "-";
			if (firstAuditDto.getAuditStatus() == 1) {
				auditStatus = "通过";
			} else if (firstAuditDto.getAuditStatus() == 2) {
				auditStatus = "退回";
			} else if (firstAuditDto.getAuditStatus() == 3) {
				auditStatus = "上报终审";
			}
			upperMap.put("初审结果", auditStatus);
			upperMap.put("keys", "初审人,初审时间,初审结果");

			Map<String, Object> downMap = new LinkedHashMap<String, Object>();
			if (StringUtils.isNotBlank(firstAuditDto.getRemark())) {
				downMap.put("初审意见", firstAuditDto.getRemark());
			}

			// 风控修改征信日志
			// CreditDto credit = new CreditDto();
			// credit.setOrderNo(orderNo);
			// List<Map<String,Object>> respData = httpUtil.getList(Constants.LINK_CREDIT,
			// "/credit/risk/ordercredit/v/selectCreditLog", credit, Map.class);
			// String creditLog = "";
			// creditLog += "操作记录（操作人:"+firstAuditDto.getHandleName()+"）\n";
			// if(respData!=null&&respData.size()>0){
			// for (int i=0;i<respData.size();i++) {
			// creditLog += "
			// "+(i+1)+":将【"+respData.get(i).get("colName")+"】从\""+respData.get(i).get("startVal")+"\"改为\""+respData.get(i).get("endVal")+";"+this.getStrTime(MapUtils.getString(respData.get(i),
			// "updateTime"))+"\n";
			// }
			// downMap.put("风控修改征信日志", creditLog);
			// }

			retMap.put("upper", upperMap);
			retMap.put("down", downMap);

		} else if ("auditFinal".equals(processId)) {

			FinalAuditDto finalAuditDto = new FinalAuditDto();
			finalAuditDto.setOrderNo(orderNo);
			finalAuditDto = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/risk/final/v/detail", finalAuditDto,
					FinalAuditDto.class);

			Map<String, Object> upperMap = new LinkedHashMap<String, Object>();

			String handleName = "-";
			if (StringUtils.isNotBlank(finalAuditDto.getHandleName())) {
				handleName = finalAuditDto.getHandleName();
			}
			upperMap.put("终审人", handleName);

			upperMap.put("终审时间", DateUtil.getDateByFmt(finalAuditDto.getAuditTime(), DateUtil.FMT_TYPE2));
			String auditStatus = "-";
			if (finalAuditDto.getAuditStatus() == 1) {
				auditStatus = "通过";
			} else if (finalAuditDto.getAuditStatus() == 2) {
				auditStatus = "未通过";
			} else if (finalAuditDto.getAuditStatus() == 3) {
				auditStatus = "上报首席风险官";
			} else if (finalAuditDto.getAuditStatus() == 4) {
				auditStatus = "上报复核审批";
			}
			upperMap.put("终审结果", auditStatus);
			String keys = "终审人,终审时间,终审结果";
			if (null != finalAuditDto.getPaymentType() && !"".equals(finalAuditDto.getPaymentType())) {
				upperMap.put("还款方式", finalAuditDto.getPaymentType());
				keys += ",还款方式";
			}
			upperMap.put("keys", keys);

			Map<String, Object> downMap = new LinkedHashMap<String, Object>();
			if (StringUtils.isNotBlank(finalAuditDto.getRemark())) {
				downMap.put("终审意见", finalAuditDto.getRemark());
			}

			retMap.put("upper", upperMap);
			retMap.put("down", downMap);

		} else if ("auditReview".equals(processId)) {

			ReviewAuditDto reviewAuditDto = new ReviewAuditDto();
			reviewAuditDto.setOrderNo(orderNo);
			reviewAuditDto = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/risk/review/v/detail", reviewAuditDto,
					ReviewAuditDto.class);

			Map<String, Object> upperMap = new LinkedHashMap<String, Object>();

			String handleName = "-";
			if (StringUtils.isNotBlank(reviewAuditDto.getHandleName())) {
				handleName = reviewAuditDto.getHandleName();
			}
			upperMap.put("复核审批人", handleName);

			upperMap.put("复核审批时间", DateUtil.getDateByFmt(reviewAuditDto.getAuditTime(), DateUtil.FMT_TYPE2));
			String auditStatus = "-";
			if (reviewAuditDto.getAuditStatus() == 1) {
				auditStatus = "通过";
			} else if (reviewAuditDto.getAuditStatus() == 2) {
				auditStatus = "未通过";
			} else if (reviewAuditDto.getAuditStatus() == 3) {
				auditStatus = "上报首席风险官";
			}
			upperMap.put("复核审批结果", auditStatus);
			upperMap.put("借款金额", reviewAuditDto.getLoanAmont() + "万");
			upperMap.put("借款期限", reviewAuditDto.getBorrowingDays() + "天");
			upperMap.put("建议费率", reviewAuditDto.getRate() + "%/天");
			upperMap.put("建议逾期费率", reviewAuditDto.getOverdueRate() + "%/天");
			String keys = "复核审批人,复核审批时间,复核审批结果,借款金额,借款期限,建议费率,建议逾期费率";
			upperMap.put("keys", keys);

			Map<String, Object> downMap = new LinkedHashMap<String, Object>();
			if (StringUtils.isNotBlank(reviewAuditDto.getRemark())) {
				downMap.put("复核审批意见", reviewAuditDto.getRemark());
			}

			retMap.put("upper", upperMap);
			retMap.put("down", downMap);

		} else if ("auditOfficer".equals(processId)) {

			OfficerAuditDto officerAuditDto = new OfficerAuditDto();
			officerAuditDto.setOrderNo(orderNo);
			officerAuditDto = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/risk/officer/v/detail",
					officerAuditDto, OfficerAuditDto.class);

			Map<String, Object> upperMap = new LinkedHashMap<String, Object>();
			String handleName = "-";
			if (StringUtils.isNotBlank(officerAuditDto.getHandleName())) {
				handleName = officerAuditDto.getHandleName();
			}
			upperMap.put("首席风险官", handleName);
			upperMap.put("审批时间", DateUtil.getDateByFmt(officerAuditDto.getAuditTime(), DateUtil.FMT_TYPE2));
			String auditStatus = "-";
			if (officerAuditDto.getAuditStatus() == 1) {
				auditStatus = "通过";
			} else if (officerAuditDto.getAuditStatus() == 2) {
				auditStatus = "未通过";
			}
			upperMap.put("审批结果", auditStatus);

			upperMap.put("keys", "首席风险官,审批时间,审批结果");

			Map<String, Object> downMap = new LinkedHashMap<String, Object>();
			if (StringUtils.isNotBlank(officerAuditDto.getRemark())) {
				downMap.put("首席风险官意见", officerAuditDto.getRemark());
			}

			retMap.put("upper", upperMap);
			retMap.put("down", downMap);

		} else if ("auditJustice".equals(processId)) {

			JusticeAuditDto justiceAuditDto = new JusticeAuditDto();
			justiceAuditDto.setOrderNo(orderNo);
			justiceAuditDto = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/risk/justice/v/detail",
					justiceAuditDto, JusticeAuditDto.class);

			Map<String, Object> upperMap = new LinkedHashMap<String, Object>();
			String handleName = "-";
			if (StringUtils.isNotBlank(justiceAuditDto.getHandleName())) {
				handleName = justiceAuditDto.getHandleName();
			}
			upperMap.put("法务", handleName);
			upperMap.put("审批时间", DateUtil.getDateByFmt(justiceAuditDto.getAuditTime(), DateUtil.FMT_TYPE2));
			String auditStatus = "-";
			if (justiceAuditDto.getAuditStatus() == 1) {
				auditStatus = "通过";
			} else if (justiceAuditDto.getAuditStatus() == 2) {
				auditStatus = "未通过";
			}
			upperMap.put("审批结果", auditStatus);

			upperMap.put("keys", "法务,审批时间,审批结果");

			Map<String, Object> downMap = new LinkedHashMap<String, Object>();
			if (StringUtils.isNotBlank(justiceAuditDto.getRemark())) {
				downMap.put("法务意见", justiceAuditDto.getRemark());
			}

			retMap.put("upper", upperMap);
			retMap.put("down", downMap);

		} else if ("allocationFund".equals(processId)) {
			AllocationFundDto obj = new AllocationFundDto();
			obj.setOrderNo(orderNo);
			List<AllocationFundDto> list = httpUtil.getList(Constants.LINK_CREDIT,
					"/credit/risk/allocationfund/v/detail", obj, AllocationFundDto.class);
			String fundCode = "";
			Double loanAmount = 0d;

			String keys = "资金方代号,放款金额";
			Map<String, Object> upperMap = new LinkedHashMap<String, Object>();
			for (AllocationFundDto tmp : list) {
				loanAmount += tmp.getLoanAmount();
				fundCode += tmp.getFundDesc() + ",";
			}
			if (StringUtils.isNotBlank(fundCode)) {
				fundCode = fundCode.substring(0, fundCode.length() - 1);
			} else {
				fundCode = "-";
			}
			upperMap.put("资金方代号", fundCode);
			upperMap.put("放款金额", BigDecimal.valueOf(loanAmount) + "万");

			if (null != list || list.size() > 0) {
				obj = list.get(0);
				if (StringUtils.isNotBlank(obj.getRemark())) {
					upperMap.put("备注", obj.getRemark());
					keys += ",备注";
				}
			}
			upperMap.put("keys", keys);

			retMap.put("upper", upperMap);

		} else if ("dataAudit".equals(processId)) {
			DataAuditDto dataAuditDto = new DataAuditDto();
			dataAuditDto.setOrderNo(orderNo);
			RespDataObject<DataAuditDto> dataAudit = httpUtil.getRespDataObject(Constants.LINK_CREDIT,
					"/credit/risk/dataAudit/v/detail", dataAuditDto, DataAuditDto.class);
			dataAuditDto = dataAudit.getData();
			Map<String, Object> upperMap = new LinkedHashMap<String, Object>();
			String handleName = "-";
			if (StringUtils.isNotBlank(dataAuditDto.getName())) {
				handleName = dataAuditDto.getName();
			}
			upperMap.put("审核人", handleName);
			upperMap.put("审批时间", DateUtil.getDateByFmt(dataAuditDto.getAuditTime(), DateUtil.FMT_TYPE2));
			upperMap.put("keys", "审核人,审批时间");

			Map<String, Object> downMap = new LinkedHashMap<String, Object>();
			downMap.put("审批意见", dataAuditDto.getRemark());

			retMap.put("upper", upperMap);
			retMap.put("down", downMap);

		} else if ("fundAduit".equals(processId)) {
			AllocationFundAduitDto obj = new AllocationFundAduitDto();
			obj.setOrderNo(orderNo);
			obj = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/risk/allocationfundaduit/v/selectFundAudit", obj,
					AllocationFundAduitDto.class);
			Map<String, Object> upperMap = new LinkedHashMap<String, Object>();
			String status = "-";
			String lendingTime = "-";
			boolean isHuarong = true;
			if ("111".equals(obj.getDealStatus()) || 111 == obj.getDealStatus()) {
				status = "待审批";
			} else if ("997".equals(obj.getDealStatus()) || 997 == obj.getDealStatus()) {
				status = "通过";
			} else if ("998".equals(obj.getDealStatus()) || 998 == obj.getDealStatus()) {
				status = "否决";
			} else if ("991".equals(obj.getDealStatus()) || 991 == obj.getDealStatus()) {
				status = "准入项校验否决";
			} else if ("999".equals(obj.getDealStatus()) || 999 == obj.getDealStatus()) {
				status = "部分通过";
			}
			if ("0".equals(obj.getDealStatus()) || 0 == obj.getDealStatus()) {
				isHuarong = false;
			}
			if (isHuarong) {
				lendingTime = "".equals(obj.getLendingTimeStr()) ? "-" : obj.getLendingTimeStr();
				upperMap.put("审批状态", status);
				upperMap.put("放款时间", lendingTime);
				String keys = "审批状态,放款时间";
				upperMap.put("keys", keys);
				retMap.put("upper", upperMap);
			} else {
				String handleName = "-";
				if (StringUtils.isNotBlank(obj.getCreateUid())) {
					UserDto dto = CommonDataUtil.getUserDtoByUidAndMobile(obj.getCreateUid());
					handleName = dto.getName();
				}
				upperMap.put("审批人", handleName);
				upperMap.put("审批时间", DateUtil.getDateByFmt(obj.getCreateTime(), DateUtil.FMT_TYPE2));
				upperMap.put("审批状态", "通过");
				upperMap.put("keys", "审批人,审批时间,审批状态");
				Map<String, Object> downMap = new LinkedHashMap<String, Object>();
				downMap.put("审批意见", obj.getRemark());
				retMap.put("upper", upperMap);
				retMap.put("down", downMap);
			}
			// Map<String, Object> downMap = new LinkedHashMap<String, Object>();
		} else if ("fundDocking".equals(processId)) {
			AllocationFundDto fundDto = new AllocationFundDto();
			fundDto.setOrderNo(orderNo);
			List<AllocationFundDto> list = httpUtil.getList(Constants.LINK_CREDIT, "/credit/risk/fundDocking/v/detail",
					fundDto, AllocationFundDto.class);
			AllocationFundDto allocationFundDto = list.get(0);
			Map<String, Object> upperMap = new LinkedHashMap<String, Object>();
			String handleName = "-";
			if (StringUtils.isNotBlank(allocationFundDto.getCreateUid())) {
				UserDto dto = CommonDataUtil.getUserDtoByUidAndMobile(allocationFundDto.getCreateUid());
				handleName = dto.getName();
			}
			boolean isyunnan = false;
			for (AllocationFundDto fundlist : list) {
				if ("114".equals(fundlist.getFundCode()) || "115".equals(fundlist.getFundCode())) {
					isyunnan = true;
				}
			}
			String url = null;
			if (isyunnan) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("orderNo", orderNo);
				RespDataObject<Map<String, Object>> ynRespDataObject = httpUtil.getRespDataObject(Constants.LINK_CREDIT,
						"/credit/third/api/yntrust/v/getQRCode", map, Map.class);
				if (null != ynRespDataObject.getData() && null != ynRespDataObject.getData().get("url")) {
					url = ynRespDataObject.getData().get("url") + "";
				}
			}
			upperMap.put("操作人", handleName);
			upperMap.put("对接时间", DateUtil.getDateByFmt(allocationFundDto.getAuditTime(), DateUtil.FMT_TYPE2));
			String keys = "操作人,对接时间";
			if (null != url && "" != url) {
				upperMap.put("合同签署地址", url);
				keys += ",合同签署地址";
			}
			if (StringUtils.isNotBlank(allocationFundDto.getRemark())) {
				upperMap.put("备注", allocationFundDto.getRemark());
				keys += ",备注";
			}
			upperMap.put("keys", keys);
			retMap.put("upper", upperMap);

		} else if ("repaymentMember".equals(processId)) {
			DistributionMemberDto obj = new DistributionMemberDto();
			obj.setOrderNo(orderNo);
			obj = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/process/distributionMember/v/detail", obj,
					DistributionMemberDto.class);

			Map<String, Object> upperMap = new LinkedHashMap<String, Object>();
			upperMap.put("指派时间", DateUtil.getDateByFmt(obj.getDistributionTime(), DateUtil.FMT_TYPE2));
			String foreclosureMemberName = "-";
			if (StringUtils.isNotBlank(obj.getForeclosureMemberName())) {
				foreclosureMemberName = obj.getForeclosureMemberName();
			}
			upperMap.put("还款专员", foreclosureMemberName);

			String keys = "指派时间,还款专员";
			if (StringUtils.isNotBlank(obj.getRemark())) {
				upperMap.put("备注", obj.getRemark());
				keys += ",备注";
			}
			upperMap.put("keys", keys);
			retMap.put("upper", upperMap);
		} else if ("element".equals(processId)) {
			DocumentsDto obj = new DocumentsDto();
			obj.setOrderNo(orderNo);
			obj = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/element/basics/v/detail", obj, DocumentsDto.class);

			String type = "-";
			if (obj.getStatus() == 2) {
				type = "通过钉钉特批";
			} else if (obj.getStatus() == 3) {
				type = "通过系统校验";
			}
			Map<String, Object> upperMap = new LinkedHashMap<String, Object>();
			upperMap.put("校验方式", type);

			String keys = "校验方式";
			if (StringUtils.isNotBlank(obj.getRemark())) {
				upperMap.put("备注", obj.getRemark());
				keys += ",备注";
			}
			upperMap.put("keys", keys);

			Map<String, Object> downMap = new LinkedHashMap<String, Object>();
			String imgs = cleanImgUrl(obj.getGreenStatusImgUrl());

			if (StringUtil.isNotEmpty(obj.getElementUrl())) {
				if (StringUtil.isNotEmpty(imgs)) {
					imgs += ",";
				}
				imgs += cleanImgUrl(obj.getElementUrl());
			}
			downMap.put("imgs", imgs);

			retMap.put("upper", upperMap);
			retMap.put("down", downMap);
		} else if ("applyLoan".equals(processId)) {
			ApplyLoanDto obj = new ApplyLoanDto();
			obj.setOrderNo(orderNo);
			Map<String, Object> upperMap = new LinkedHashMap<String, Object>();
			JSONObject jsons = httpUtil.getData(Constants.LINK_CREDIT, "/credit/finance/applyLoan/v/detail", obj);
			RespDataObject<LinkedTreeMap<String, Object>> result = new Gson().fromJson(jsons.toString(),
					RespDataObject.class);
			Map<String, Object> tmp = new HashMap<String, Object>();
			JSONArray jsonArray = new JSONArray();
			List<FinanceLogDto> logDtos = new ArrayList<FinanceLogDto>();
			if (RespStatusEnum.SUCCESS.getCode().equals(result.getCode())) {
				LinkedTreeMap<String, Object> map = result.getData();
				if (map.containsKey("loanDto")) {
					tmp = (Map<String, Object>) map.get("loanDto");
				}
				if (map.containsKey("logDtos")) {
					jsonArray = JSONArray.fromObject(map.get("logDtos"));
					logDtos = JSONArray.toList(jsonArray, FinanceLogDto.class);
				}
			}
			upperMap.put("客户姓名", MapUtils.getString(tmp, "borrowerName"));
			String borrowingDays = MapUtils.getString(tmp, "borrowingDays", "-");
			if ("04".equals(orderListDto.getProductCode())) {
				upperMap.put("借款期限", borrowingDays.replace(".0", "") + "期");
			} else {
				upperMap.put("借款期限", borrowingDays.replace(".0", "") + "天");
			}
			upperMap.put("借款金额", BigDecimal.valueOf(MapUtils.getDouble(tmp, "loanAmount", 0.0)) + "万");
			upperMap.put("放款卡银行-支行",
					MapUtils.getString(tmp, "lendingBank", "") + "-" + MapUtils.getString(tmp, "lendingBankSub", ""));
			upperMap.put("银行卡户名", MapUtils.getString(tmp, "bankName", "-"));
			upperMap.put("银行卡账号", MapUtils.getString(tmp, "bankAccount", "-"));

			String keys = "客户姓名,借款期限,借款金额,放款卡银行-支行,银行卡户名,银行卡账号";
			if (StringUtils.isNotBlank(MapUtils.getString(tmp, "remark"))) {
				upperMap.put("备注", MapUtils.getString(tmp, "remark", "-"));
				keys += ",备注";
			}

			upperMap.put("keys", keys);

			Map<String, Object> downMap = new LinkedHashMap<String, Object>();
			String log = "";
			if (logDtos.size() > 0) {
				for (int i = 0; i < logDtos.size(); i++) {
					log += (i + 1) + " : 将 " + logDtos.get(i).getColName() + " 从‘" + logDtos.get(i).getStartVal()
							+ "’ 改成 ‘ " + logDtos.get(i).getEndVal() + "' \n";
				}
				// log += ",修改日志";
				downMap.put("logDtos", log);
			}

			String imgs = MapUtils.getString(tmp, "chargesReceivedImg", "");
			if (StringUtil.isNotEmpty(imgs)) {
				imgs = cleanImgUrl(imgs + "," + MapUtils.getString(tmp, "payAccountImg", ""));
			} else {
				imgs = cleanImgUrl(MapUtils.getString(tmp, "payAccountImg", ""));
			}

			String mortgageImg = MapUtils.getString(tmp, "mortgageImg", "");
			if (StringUtil.isNotEmpty(imgs)) {
				if (StringUtil.isNotEmpty(mortgageImg)) {
					imgs = cleanImgUrl(imgs + "," + MapUtils.getString(tmp, "mortgageImg", ""));
				}
			} else {
				imgs = cleanImgUrl(MapUtils.getString(tmp, "payAccountImg", ""));
			}
			downMap.put("imgs", imgs);

			retMap.put("upper", upperMap);
			retMap.put("down", downMap);
		} else if ("isLendingHarvest".equals(processId)) {
			LendingHarvestDto obj = new LendingHarvestDto();
			obj.setOrderNo(orderNo);
			obj.setType(1);
			Map<String, Object> upperMap = new LinkedHashMap<String, Object>();
			JSONObject jsons = httpUtil.getData(Constants.LINK_CREDIT, "/credit/finance/lendingInterest/v/detail", obj);
			System.out.println(jsons);
			RespDataObject<LinkedTreeMap<String, Object>> result = new Gson().fromJson(jsons.toString(),
					RespDataObject.class);
			Map<String, Object> tmp = new HashMap<String, Object>();
			JSONArray jsonArray = new JSONArray();
			if (RespStatusEnum.SUCCESS.getCode().equals(result.getCode())) {
				LinkedTreeMap<String, Object> map = result.getData();
				if (map.containsKey("interest")) {
					tmp = (Map<String, Object>) map.get("interest");
				}
			}
			double d = MapUtils.getDouble(tmp, "collectInterestMoney", 0.0);
			DecimalFormat df = new DecimalFormat("###############0.00#");
			String collectInterestMoney = df.format(d);
			upperMap.put("实收利息", collectInterestMoney + "元");
			upperMap.put("收利息时间", MapUtils.getString(tmp, "interestTimeStr"));
			String keys = "实收利息,收利息时间";
			if (StringUtils.isNotBlank(MapUtils.getString(tmp, "remark"))) {
				upperMap.put("备注", MapUtils.getString(tmp, "remark", "-"));
				keys += ",备注";
			}
			upperMap.put("keys", keys);
			Map<String, Object> downMap = new LinkedHashMap<String, Object>();
			String imgs = MapUtils.getString(tmp, "interestImg", "");
			if (StringUtil.isNotEmpty(imgs)) {
				imgs = cleanImgUrl(imgs + "," + MapUtils.getString(tmp, "rateImg", ""));
			} else {
				imgs = cleanImgUrl(MapUtils.getString(tmp, "rateImg", ""));
			}
			downMap.put("imgs", imgs);

			retMap.put("upper", upperMap);
			retMap.put("down", downMap);
		} else if ("lendingHarvest".equals(processId)) {// 核实利息
			LendingHarvestDto obj = new LendingHarvestDto();
			obj.setOrderNo(orderNo);
			obj.setType(2); // 用于查询日志
			Map<String, Object> upperMap = new LinkedHashMap<String, Object>();
			JSONObject jsons = httpUtil.getData(Constants.LINK_CREDIT, "/credit/finance/lendingHarvest/v/detail", obj);
			RespDataObject<LinkedTreeMap<String, Object>> result = new Gson().fromJson(jsons.toString(),
					RespDataObject.class);
			Map<String, Object> tmp = new HashMap<String, Object>();
			JSONArray jsonArray = new JSONArray();
			List<FinanceLogDto> logDtos = new ArrayList<FinanceLogDto>();
			if (RespStatusEnum.SUCCESS.getCode().equals(result.getCode())) {
				LinkedTreeMap<String, Object> map = result.getData();
				if (map.containsKey("harvest")) {
					tmp = (Map<String, Object>) map.get("harvest");
				}
				if (map.containsKey("logDtos")) {
					jsonArray = JSONArray.fromObject(map.get("logDtos"));
					logDtos = JSONArray.toList(jsonArray, FinanceLogDto.class);
				}
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("cooperativeAgencyId", MapUtils.getString(tmp, "cooperativeAgencyId"));
			map.put("productId", MapUtils.getString(tmp, "cityCode") + MapUtils.getString(tmp, "productCode"));
			map.put("borrowingDays", borrow.getBorrowingDays());
			map.put("riskGradeId", MapUtils.getString(tmp, "riskGradeId"));
			map.put("loanAmount", MapUtils.getString(tmp, "loanAmount"));
			RespDataObject<Map<String, Object>> cusMap = httpUtil.getRespDataObject(Constants.LINK_CREDIT,
					"/credit/customer/risk/v/findStageRate", map, Map.class);
			Map<String, Object> rateMap = cusMap.getData();
			log.info("按天按段：" + rateMap);
			String modeid = MapUtils.getString(rateMap, "modeid", "0");
			upperMap.put("收费类型", MapUtils.getString(tmp, "riskGrade", "其他"));
			upperMap.put("期限", borrow.getBorrowingDays() + "天");
			if ("1".equals(modeid) && 0 != MapUtils.getIntValue(tmp, "riskGradeId", 0)) {
				upperMap.put("费率", MapUtils.getString(tmp, "rate") + "%");
				upperMap.put("逾期费率", MapUtils.getString(tmp, "overdueRate") + "%");
			} else { // 按天
				upperMap.put("费率", MapUtils.getString(tmp, "rate") + "%/天");
				upperMap.put("逾期费率", MapUtils.getString(tmp, "overdueRate") + "%/天");
			}
			upperMap.put("应收金额", BigDecimal.valueOf(MapUtils.getDouble(tmp, "chargeMoney", 0.0)) + "元");
			upperMap.put("服务费", BigDecimal.valueOf(MapUtils.getDouble(tmp, "serviceCharge", 0.0)) + "元");
			if (!borrow.getProductCode().equals("03")) {
				upperMap.put("关外手续费", BigDecimal.valueOf(MapUtils.getDouble(tmp, "customsPoundage", 0.0)) + "元");
			}
			upperMap.put("其他费用", BigDecimal.valueOf(MapUtils.getDouble(tmp, "otherPoundage", 0.0)) + "元");
			upperMap.put("收利息时间", MapUtils.getString(tmp, "interestTimeStr"));
			upperMap.put("实收费用合计", BigDecimal.valueOf(MapUtils.getDouble(tmp, "collectInterestMoney", 0.0)) + "元");
			upperMap.put("返佣金额", BigDecimal.valueOf(MapUtils.getDouble(tmp, "returnMoney", 0.0)) + "元");
			upperMap.put("应收利息", BigDecimal.valueOf(MapUtils.getDouble(tmp, "receivableInterestMoney", 0.0)) + "元");
			// upperMap.put("应付利息",
			// BigDecimal.valueOf(MapUtils.getDouble(tmp,"payInterestMoney",0.0))+"元");
			String keys = "";
			if (!borrow.getProductCode().equals("03")) {
				keys = "收费类型,期限,费率,逾期费率,应收金额,服务费,关外手续费,其他费用,收利息时间,实收费用合计,返佣金额,应收利息";
			} else {
				keys = "收费类型,期限,费率,逾期费率,应收金额,服务费,其他费用,收利息时间,实收费用合计,返佣金额,应收利息";
			}
			if (StringUtils.isNotBlank(MapUtils.getString(tmp, "remark"))) {
				upperMap.put("备注", MapUtils.getString(tmp, "remark", "-"));
				keys += ",备注";
			}
			upperMap.put("keys", keys);

			Map<String, Object> downMap = new LinkedHashMap<String, Object>();
			String log = "";
			if (logDtos.size() > 0) {
				for (int i = 0; i < logDtos.size(); i++) {
					log += (i + 1) + " : 将 " + logDtos.get(i).getColName() + " 从‘" + logDtos.get(i).getStartVal()
							+ "’ 改成 ‘ " + logDtos.get(i).getEndVal() + "' \n";
				}
				// log += ",修改日志";
				downMap.put("logDtos", log);
			}
			String imgs = MapUtils.getString(tmp, "interestImg", "");
			if (StringUtil.isNotEmpty(imgs)) {
				imgs = cleanImgUrl(imgs + "," + MapUtils.getString(tmp, "rateImg", ""));
			} else {
				imgs = cleanImgUrl(MapUtils.getString(tmp, "rateImg", ""));
			}
			downMap.put("imgs", imgs);

			retMap.put("upper", upperMap);
			retMap.put("down", downMap);

		} else if ("charge".equals(processId)) {
			LendingHarvestDto obj = new LendingHarvestDto();
			obj.setOrderNo(orderNo);
			obj.setType(3);
			Map<String, Object> upperMap = new LinkedHashMap<String, Object>();
			JSONObject jsons = httpUtil.getData(Constants.LINK_CREDIT, "/credit/finance/fddCharge/v/detail", obj);
			System.out.println(jsons);
			RespDataObject<LinkedTreeMap<String, Object>> result = new Gson().fromJson(jsons.toString(),
					RespDataObject.class);
			Map<String, Object> tmp = new HashMap<String, Object>();
			JSONArray jsonArray = new JSONArray();
			if (RespStatusEnum.SUCCESS.getCode().equals(result.getCode())) {
				LinkedTreeMap<String, Object> map = result.getData();
				if (map.containsKey("interest")) {
					tmp = (Map<String, Object>) map.get("interest");
				}
			}
			double d = MapUtils.getDouble(tmp, "collectInterestMoney", 0.0);
			DecimalFormat df = new DecimalFormat("###############0.00#");
			String collectInterestMoney = df.format(d);
			upperMap.put("实收手续费", collectInterestMoney + "元");
			upperMap.put("收手续费时间", MapUtils.getString(tmp, "interestTimeStr"));
			String keys = "实收手续费,收手续费时间";
			if (StringUtils.isNotBlank(MapUtils.getString(tmp, "remark"))) {
				upperMap.put("备注", MapUtils.getString(tmp, "remark", "-"));
				keys += ",备注";
			}
			upperMap.put("keys", keys);
			Map<String, Object> downMap = new LinkedHashMap<String, Object>();
			String imgs = MapUtils.getString(tmp, "interestImg", "");
			if (StringUtil.isNotEmpty(imgs)) {
				imgs = cleanImgUrl(imgs + "," + MapUtils.getString(tmp, "rateImg", ""));
			} else {
				imgs = cleanImgUrl(MapUtils.getString(tmp, "rateImg", ""));
			}
			downMap.put("imgs", imgs);

			retMap.put("upper", upperMap);
			retMap.put("down", downMap);
		} else if ("isCharge".equals(processId)) {
			LendingHarvestDto obj = new LendingHarvestDto();
			obj.setOrderNo(orderNo);
			obj.setType(4); // 用于查询日志
			Map<String, Object> upperMap = new LinkedHashMap<String, Object>();
			JSONObject jsons = httpUtil.getData(Constants.LINK_CREDIT, "/credit/finance/fddIsCharge/v/detail", obj);
			RespDataObject<LinkedTreeMap<String, Object>> result = new Gson().fromJson(jsons.toString(),
					RespDataObject.class);
			Map<String, Object> tmp = new HashMap<String, Object>();
			JSONArray jsonArray = new JSONArray();
			List<FinanceLogDto> logDtos = new ArrayList<FinanceLogDto>();
			if (RespStatusEnum.SUCCESS.getCode().equals(result.getCode())) {
				LinkedTreeMap<String, Object> map = result.getData();
				if (map.containsKey("harvest")) {
					tmp = (Map<String, Object>) map.get("harvest");
				}
				if (map.containsKey("logDtos")) {
					jsonArray = JSONArray.fromObject(map.get("logDtos"));
					logDtos = JSONArray.toList(jsonArray, FinanceLogDto.class);
				}
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("cooperativeAgencyId", MapUtils.getString(tmp, "cooperativeAgencyId"));
			map.put("productId", MapUtils.getString(tmp, "cityCode") + MapUtils.getString(tmp, "productCode"));
			map.put("borrowingDays", MapUtils.getString(tmp, "borrowingDays"));
			map.put("loanAmount", MapUtils.getString(tmp, "loanAmount"));

			upperMap.put("费率", MapUtils.getString(tmp, "rate") + "%/天");
			upperMap.put("逾期费率", MapUtils.getString(tmp, "overdueRate") + "%/天");
			upperMap.put("收费金额", BigDecimal.valueOf(MapUtils.getDouble(tmp, "chargeMoney", 0.0)) + "元");
			// upperMap.put("服务费",
			// BigDecimal.valueOf(MapUtils.getDouble(tmp,"serviceCharge",0.0))+"元");
			upperMap.put("收手续费时间", MapUtils.getString(tmp, "interestTimeStr"));
			upperMap.put("实收手续费", BigDecimal.valueOf(MapUtils.getDouble(tmp, "collectInterestMoney", 0.0)) + "元");
			upperMap.put("返佣金额", BigDecimal.valueOf(MapUtils.getDouble(tmp, "returnMoney", 0.0)) + "元");

			String keys = "费率,逾期费率,收费金额,收手续费时间,实收手续费,返佣金额";
			if (StringUtils.isNotBlank(MapUtils.getString(tmp, "remark"))) {
				upperMap.put("备注", MapUtils.getString(tmp, "remark", "-"));
				keys += ",备注";
			}
			upperMap.put("keys", keys);

			Map<String, Object> downMap = new LinkedHashMap<String, Object>();
			String log = "";
			if (logDtos.size() > 0) {
				for (int i = 0; i < logDtos.size(); i++) {
					log += (i + 1) + " : 将 " + logDtos.get(i).getColName() + " 从‘" + logDtos.get(i).getStartVal()
							+ "’ 改成 ‘ " + logDtos.get(i).getEndVal() + "' \n";
				}
				// log += ",修改日志";
				downMap.put("logDtos", log);
			}
			String imgs = MapUtils.getString(tmp, "interestImg", "");
			if (StringUtil.isNotEmpty(imgs)) {
				imgs = cleanImgUrl(imgs + "," + MapUtils.getString(tmp, "rateImg", ""));
			} else {
				imgs = cleanImgUrl(MapUtils.getString(tmp, "rateImg", ""));
			}
			downMap.put("imgs", imgs);

			retMap.put("upper", upperMap);
			retMap.put("down", downMap);

		} else if ("financialStatement".equals(processId)) { // 财务制单
			StatementDto obj = new StatementDto();
			obj.setOrderNo(orderNo);
			obj = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/finance/statement/v/detail", obj,
					StatementDto.class);

			Map<String, Object> upperMap = new LinkedHashMap<String, Object>();
			upperMap.put("财务制单时间", DateUtil.getDateByFmt(obj.getCreateTime(), DateUtil.FMT_TYPE2));

			String keys = "财务制单时间";
			if (StringUtils.isNotBlank(obj.getRemark())) {
				upperMap.put("备注", obj.getRemark());
				keys += ",备注";
			}
			upperMap.put("keys", keys);
			retMap.put("upper", upperMap);
		} else if ("financialAudit".equals(processId)) { // 财务审核
			AuditDto obj = new AuditDto();
			obj.setOrderNo(orderNo);
			obj = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/finance/audit/v/detail", obj, AuditDto.class);

			Map<String, Object> upperMap = new LinkedHashMap<String, Object>();
			upperMap.put("财务审核时间", DateUtil.getDateByFmt(obj.getCreateTime(), DateUtil.FMT_TYPE2));

			String keys = "财务审核时间";
			if (StringUtils.isNotBlank(obj.getRemark())) {
				upperMap.put("备注", obj.getRemark());
				keys += ",备注";
			}
			upperMap.put("keys", keys);
			retMap.put("upper", upperMap);
		} else if ("isBackExpenses".equals(processId)) {// 扣回后置费用
			LendingHarvestDto obj = new LendingHarvestDto();
			obj.setOrderNo(orderNo);
			obj.setType(3);
			Map<String, Object> upperMap = new LinkedHashMap<String, Object>();
			JSONObject jsons = httpUtil.getData(Constants.LINK_CREDIT, "/credit/finance/lendingHarvest/v/detail", obj);
			RespDataObject<LinkedTreeMap<String, Object>> result = new Gson().fromJson(jsons.toString(),
					RespDataObject.class);
			Map<String, Object> tmp = new HashMap<String, Object>();
			if (RespStatusEnum.SUCCESS.getCode().equals(result.getCode())) {
				LinkedTreeMap<String, Object> map = result.getData();
				if (map.containsKey("harvest")) {
					tmp = (Map<String, Object>) map.get("harvest");
				}
			}

			upperMap.put("收后置费用时间", MapUtils.getString(tmp, "interestTimeStr"));
			upperMap.put("实收费用", BigDecimal.valueOf(MapUtils.getDouble(tmp, "backExpensesMoney", 0.0)) + "元");

			String keys = "收后置费用时间,实收费用";
			if (StringUtils.isNotBlank(MapUtils.getString(tmp, "remark"))) {
				upperMap.put("备注", MapUtils.getString(tmp, "remark", "-"));
				keys += ",备注";
			}
			upperMap.put("keys", keys);

			Map<String, Object> downMap = new LinkedHashMap<String, Object>();
			String imgs = MapUtils.getString(tmp, "interestImg", "");
			if (StringUtil.isNotEmpty(imgs)) {
				imgs = cleanImgUrl(imgs + "," + MapUtils.getString(tmp, "rateImg", ""));
			} else {
				imgs = cleanImgUrl(MapUtils.getString(tmp, "rateImg", ""));
			}
			downMap.put("imgs", imgs);

			retMap.put("upper", upperMap);
			retMap.put("down", downMap);
		} else if ("backExpenses".equals(processId)) {// 核实后置费用
			LendingHarvestDto obj = new LendingHarvestDto();
			obj.setOrderNo(orderNo);
			obj.setType(3);
			Map<String, Object> upperMap = new LinkedHashMap<String, Object>();
			JSONObject jsons = httpUtil.getData(Constants.LINK_CREDIT, "/credit/finance/lendingHarvest/v/detail", obj);
			RespDataObject<LinkedTreeMap<String, Object>> result = new Gson().fromJson(jsons.toString(),
					RespDataObject.class);
			Map<String, Object> tmp = new HashMap<String, Object>();
			JSONArray jsonArray = new JSONArray();
			List<FinanceLogDto> logDtos = new ArrayList<FinanceLogDto>();
			if (RespStatusEnum.SUCCESS.getCode().equals(result.getCode())) {
				LinkedTreeMap<String, Object> map = result.getData();
				if (map.containsKey("harvest")) {
					tmp = (Map<String, Object>) map.get("harvest");
				}
				if (map.containsKey("logDtos")) {
					jsonArray = JSONArray.fromObject(map.get("logDtos"));
					logDtos = JSONArray.toList(jsonArray, FinanceLogDto.class);
				}
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("cooperativeAgencyId", MapUtils.getString(tmp, "cooperativeAgencyId"));
			map.put("productId", MapUtils.getString(tmp, "cityCode") + MapUtils.getString(tmp, "productCode"));
			map.put("borrowingDays", MapUtils.getString(tmp, "borrowingDays"));
			map.put("riskGradeId", MapUtils.getString(tmp, "riskGradeId"));
			map.put("loanAmount", MapUtils.getString(tmp, "loanAmount"));
			RespDataObject<Map<String, Object>> cusMap = httpUtil.getRespDataObject(Constants.LINK_CREDIT,
					"/credit/customer/risk/v/findStageRate", map, Map.class);
			Map<String, Object> rateMap = cusMap.getData();
			String modeid = MapUtils.getString(rateMap, "modeid", "0");

			upperMap.put("收费类型", MapUtils.getString(tmp, "riskGrade", "其他"));
			if ("1".equals(modeid) && 0 != MapUtils.getIntValue(tmp, "riskGradeId", 0)) {
				upperMap.put("费率", MapUtils.getString(tmp, "rate") + "%");
				upperMap.put("逾期费率", MapUtils.getString(tmp, "overdueRate") + "%");
			} else { // 按天
				upperMap.put("费率", MapUtils.getString(tmp, "rate") + "%/天");
				upperMap.put("逾期费率", MapUtils.getString(tmp, "overdueRate") + "%/天");
			}
			upperMap.put("期限", borrow.getBorrowingDays() + "天");
			upperMap.put("收费金额", BigDecimal.valueOf(MapUtils.getDouble(tmp, "chargeMoney", 0.0)) + "元");
			upperMap.put("服务费", BigDecimal.valueOf(MapUtils.getDouble(tmp, "serviceCharge", 0.0)) + "元");
			upperMap.put("关外手续费", BigDecimal.valueOf(MapUtils.getDouble(tmp, "customsPoundage", 0.0)) + "元");
			upperMap.put("应收利息", BigDecimal.valueOf(MapUtils.getDouble(tmp, "receivableInterestMoney", 0.0)) + "元");
			upperMap.put("其他费用", BigDecimal.valueOf(MapUtils.getDouble(tmp, "otherPoundage", 0.0)) + "元");
			upperMap.put("应收金额", BigDecimal.valueOf(MapUtils.getDouble(tmp, "chargeMoney", 0.0)) + "元");
			upperMap.put("扣回后置时间", MapUtils.getString(tmp, "interestTimeStr"));
			upperMap.put("扣回后置总费用", BigDecimal.valueOf(MapUtils.getDouble(tmp, "backExpensesMoney", 0.0)) + "元");
			upperMap.put("返佣金额", BigDecimal.valueOf(MapUtils.getDouble(tmp, "returnMoney", 0.0)) + "元");

			String keys = "收费类型,期限,费率,逾期费率,服务费,关外手续费,应收利息,其他费用,应收金额,扣回后置时间,扣回后置总费用,返佣金额";
			if (StringUtils.isNotBlank(MapUtils.getString(tmp, "remark"))) {
				upperMap.put("备注", MapUtils.getString(tmp, "remark", "-"));
				keys += ",备注";
			}
			upperMap.put("keys", keys);

			Map<String, Object> downMap = new LinkedHashMap<String, Object>();
			String log = "";
			if (logDtos.size() > 0) {
				for (int i = 0; i < logDtos.size(); i++) {
					log += (i + 1) + " : 将 " + logDtos.get(i).getColName() + " 从‘" + logDtos.get(i).getStartVal()
							+ "’ 改成 ‘ " + logDtos.get(i).getEndVal() + "' \n";
				}
				// log += ",修改日志";
				downMap.put("logDtos", log);
			}
			String imgs = MapUtils.getString(tmp, "interestImg", "");
			if (StringUtil.isNotEmpty(imgs)) {
				imgs = cleanImgUrl(imgs + "," + MapUtils.getString(tmp, "rateImg", ""));
			} else {
				imgs = cleanImgUrl(MapUtils.getString(tmp, "rateImg", ""));
			}
			downMap.put("imgs", imgs);

			retMap.put("upper", upperMap);
			retMap.put("down", downMap);

			// LendingHarvestDto obj = new LendingHarvestDto();
			// obj.setOrderNo(orderNo);
			// obj.setType(2);
			// obj = httpUtil.getObject(Constants.LINK_CREDIT,
			// "/credit/finance/lendingHarvest/v/appDetail", obj, LendingHarvestDto.class);
			//
			// Map<String, Object> upperMap = new LinkedHashMap<String, Object>();
			// upperMap.put("扣回后置时间", DateUtil.getDateByFmt(obj.getInterestTime(),
			// DateUtil.FMT_TYPE2));
			// upperMap.put("扣回后置费用", obj.getBackExpensesMoney()+"元");
			// upperMap.put("返佣金额",obj.getReturnMoney()+"元");
			//
			// String keys = "扣回后置时间,扣回后置费用,返佣金额";
			// if(StringUtils.isNotBlank(obj.getRemark())){
			// upperMap.put("备注",obj.getRemark());
			// keys += ",备注";
			// }
			// upperMap.put("keys",keys);
			//
			// Map<String, Object> downMap = new LinkedHashMap<String, Object>();
			// String imgs = cleanImgUrl(obj.getInterestImg());
			// downMap.put("imgs", imgs);
			//
			// retMap.put("upper", upperMap);
			// retMap.put("down", downMap);
		} else if ("lendingPay".equals(processId)) {
			LendingPayDto obj = new LendingPayDto();
			obj.setOrderNo(orderNo);
			obj = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/finance/lendingPay/v/detail", obj,
					LendingPayDto.class);

			Map<String, Object> upperMap = new LinkedHashMap<String, Object>();
			upperMap.put("应付利息", obj.getPayInterestMoney() + "元");
			upperMap.put("付利息时间", DateUtil.getDateByFmt(obj.getPayTime(), DateUtil.FMT_TYPE2));

			String keys = "应付利息,付利息时间";
			if (StringUtils.isNotBlank(obj.getRemark())) {
				upperMap.put("备注", obj.getRemark());
				keys += ",备注";
			}
			upperMap.put("keys", keys);

			Map<String, Object> downMap = new LinkedHashMap<String, Object>();
			String imgs = cleanImgUrl(obj.getPayImg());
			downMap.put("imgs", imgs);

			retMap.put("upper", upperMap);
			retMap.put("down", downMap);
		} else if ("lendingInstructions".equals(processId)) {
			LendingInstructionsDto obj = new LendingInstructionsDto();
			obj.setOrderNo(orderNo);

			JSONObject jsons = httpUtil.getData(Constants.LINK_CREDIT, "/credit/finance/lendingInstruction/v/detail",
					obj);
			RespDataObject<LinkedTreeMap<String, Object>> result = new Gson().fromJson(jsons.toString(),
					RespDataObject.class);

			Map<String, Object> tmp = new HashMap<String, Object>();
			if (RespStatusEnum.SUCCESS.getCode().equals(result.getCode())) {
				LinkedTreeMap<String, Object> map = result.getData();
				if (map.containsKey("instructionsDto")) {
					tmp = (Map<String, Object>) map.get("instructionsDto");
				}
			}
			Map<String, Object> upperMap = new LinkedHashMap<String, Object>();

			upperMap.put("放款期限", MapUtils.getString(tmp, "borrowingDays", "-") + "天");
			upperMap.put("放款金额", BigDecimal.valueOf(MapUtils.getDouble(tmp, "loanAmount", 0.0)) + "万");
			upperMap.put("放款账户-银行",
					MapUtils.getString(tmp, "lendingBank", "") + "-" + MapUtils.getString(tmp, "openingBank", ""));
			upperMap.put("银行卡户名", MapUtils.getString(tmp, "bankName", "-"));
			upperMap.put("银行卡账号", MapUtils.getString(tmp, "bankAccount", "-"));

			String keys = "放款期限,放款金额,放款账户-银行,银行卡户名,银行卡账号";
			if (StringUtils.isNotBlank(MapUtils.getString(tmp, "remark"))) {
				upperMap.put("备注", MapUtils.getString(tmp, "remark", "-"));
				keys += ",备注";
			}
			upperMap.put("keys", keys);

			Map<String, Object> downMap = new LinkedHashMap<String, Object>();
			String imgs = cleanImgUrl(MapUtils.getString(tmp, "chargesReceivedImg", ""));
			downMap.put("imgs", imgs);

			retMap.put("upper", upperMap);
			retMap.put("down", downMap);
		} else if ("lending".equals(processId)) {
			LendingDto obj = new LendingDto();
			obj.setOrderNo(orderNo);
			obj = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/finance/lending/v/detail", obj, LendingDto.class);

			Map<String, Object> upperMap = new LinkedHashMap<String, Object>();
			upperMap.put("放款金额", (obj.getLoanAmount() == null ? "0" : BigDecimal.valueOf(obj.getLoanAmount())) + "万元");
			upperMap.put("放款账户-银行", (obj.getLendingBank() == null ? "-" : obj.getLendingBank()) + ""
					+ (obj.getOpeningBank() == null ? "" : "-" + obj.getOpeningBank()));
			String bankName = "-";
			if (StringUtils.isNotBlank(obj.getBankName())) {
				bankName = obj.getBankName();
			}
			String bankAccount = "-";
			if (StringUtils.isNotBlank(obj.getBankAccount())) {
				bankAccount = obj.getBankAccount();
			}
			upperMap.put("银行卡户名", bankName);
			upperMap.put("银行卡账号", bankAccount);
			upperMap.put("放款时间", DateUtil.getDateByFmt(obj.getLendingTime(), DateUtil.FMT_TYPE2));

			String keys = "放款金额,放款账户-银行,银行卡户名,银行卡账号,放款时间";
			if (StringUtils.isNotBlank(obj.getRemark())) {
				upperMap.put("备注", obj.getRemark());
				keys += ",备注";
			}

			upperMap.put("keys", keys);

			Map<String, Object> downMap = new LinkedHashMap<String, Object>();
			String imgs = cleanImgUrl(obj.getLendingImg());
			downMap.put("imgs", imgs);

			retMap.put("upper", upperMap);
			retMap.put("down", downMap);
		} else if ("foreclosure".equals(processId)) {

			ForeclosureDto obj = new ForeclosureDto();
			obj.setOrderNo(orderNo);
			obj = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/process/foreclosure/v/detail", obj,
					ForeclosureDto.class);
			if (null == obj) {
				obj = new ForeclosureDto();
			}
			String foreclosureTime = "-";
			if (null != obj.getForeclosureTime()) {
				foreclosureTime = DateUtil.getDateByFmt(obj.getForeclosureTime(), DateUtil.FMT_TYPE2);
			}
			Map<String, Object> upperMap = new LinkedHashMap<String, Object>();
			upperMap.put("结清时间", foreclosureTime);
			String foreclosureAddress = "-";
			if (obj.getIsBankEnd() == 0) {
				String foreclosureBankName = obj.getForeclosureBankName();
				String foreclosureBankSubName = obj.getForeclosureBankSubName();
				foreclosureAddress = foreclosureBankName + "-" + foreclosureBankSubName;
			} else {
				foreclosureAddress = obj.getForeclosureAddress();
			}
			if (StringUtils.isBlank(foreclosureAddress)) {
				foreclosureAddress = "-";
			}

			upperMap.put("结清地点", foreclosureAddress);
			// upperMap.put("银行卡户名", obj.getBankCardMaster());
			String foreclosureMemberName = "-";
			if (StringUtils.isNotBlank(obj.getForeclosureMemberName())) {
				foreclosureMemberName = obj.getForeclosureMemberName();
			}
			String keys = "结清时间,结清地点,还款专员";
			upperMap.put("还款专员", foreclosureMemberName);
			if (StringUtils.isNotBlank(obj.getRemark())) {
				upperMap.put("备注", obj.getRemark());
				keys += ",备注";
			}
			upperMap.put("keys", keys);

			Map<String, Object> downMap = new LinkedHashMap<String, Object>();
			String imgs = obj.getForeclosureImg();
			if (StringUtil.isNotEmpty(imgs)) {
				imgs = cleanImgUrl(imgs + "," + obj.getVoucherImg());
			} else {
				imgs = cleanImgUrl(obj.getVoucherImg());
			}
			downMap.put("imgs", imgs);

			retMap.put("upper", upperMap);
			retMap.put("down", downMap);
			if ("forensics".equals(orderListDto.getProcessId())
					&& user.getUid().equals(orderListDto.getPreviousHandlerUid())) {
				retMap.put("isEdit", 1);
			}
			retMap.put("pageType", "foreclosure");
		} else if ("forensics".equals(processId)) {
			ForensicsDto obj = new ForensicsDto();
			obj.setOrderNo(orderNo);
			obj = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/process/forensics/v/detail", obj,
					ForensicsDto.class);

			Map<String, Object> upperMap = new LinkedHashMap<String, Object>();
			upperMap.put("取证时间", DateUtil.getDateByFmt(obj.getLicenseRevTime(), DateUtil.FMT_TYPE2));

			String licenseRever = "-";
			if (StringUtils.isNotBlank(obj.getLicenseRever())) {
				licenseRever = obj.getLicenseRever();
			}
			String licenseRevBankName = "-";
			String licenseRevBankSubName = "-";
			if (StringUtils.isNotBlank(obj.getLicenseRevBankName())) {
				licenseRevBankName = obj.getLicenseRevBankName();
			}
			if (StringUtils.isNotBlank(obj.getLicenseRevBankSubName())) {
				licenseRevBankSubName = obj.getLicenseRevBankSubName();
			}
			String keys = "取证时间,取证银行,取证员";
			if (obj.getIsLicenseRevBank() != null && obj.getIsLicenseRevBank() == 2
					&& !"04".equals(orderListDto.getProductCode())) {
				upperMap.put("取证地址", obj.getLicenseRevAddress());
				keys = "取证时间,取证地址,取证员";
			} else {
				upperMap.put("取证银行", licenseRevBankName + "-" + licenseRevBankSubName);
			}
			upperMap.put("取证员", licenseRever);

			if (StringUtils.isNotBlank(obj.getRemark())) {
				upperMap.put("备注", obj.getRemark());
				keys += ",备注";
			}
			upperMap.put("keys", keys);

			Map<String, Object> downMap = new LinkedHashMap<String, Object>();
			String imgs = cleanImgUrl(obj.getForensiceImg());
			downMap.put("imgs", imgs);

			retMap.put("upper", upperMap);
			retMap.put("down", downMap);

			if ("cancellation".equals(orderListDto.getProcessId())
					&& user.getUid().equals(orderListDto.getPreviousHandlerUid())) {
				retMap.put("isEdit", 1);
			}
			retMap.put("pageType", "forensics");
		} else if ("fddForensics".equals(processId)) {
			ForensicsDto obj = new ForensicsDto();
			obj.setOrderNo(orderNo);
			obj = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/process/fddForensics/v/detail", obj,
					ForensicsDto.class);

			Map<String, Object> upperMap = new LinkedHashMap<String, Object>();
			upperMap.put("取证时间", DateUtil.getDateByFmt(obj.getLicenseRevTime(), DateUtil.FMT_TYPE11));

			String licenseRever = "-";
			if (StringUtils.isNotBlank(obj.getLicenseRever())) {
				licenseRever = obj.getLicenseRever();
			}
			// String licenseRevBankName = "-";
			// String licenseRevBankSubName = "-";
			// if(StringUtils.isNotBlank(obj.getLicenseRevBankName())){
			// licenseRevBankName = obj.getLicenseRevBankName();
			// }
			// if(StringUtils.isNotBlank(obj.getLicenseRevBankSubName())){
			// licenseRevBankSubName = obj.getLicenseRevBankSubName();
			// }
			// String keys = "取证时间,取证银行,取证员";
			// if(obj.getIsLicenseRevBank()!=null&&obj.getIsLicenseRevBank()==2){
			// upperMap.put("取证地址", obj.getLicenseRevAddress());
			// keys = "取证时间,取证地址,取证员";
			// }else{
			// upperMap.put("取证银行", licenseRevBankName+"-"+licenseRevBankSubName);
			// }

			upperMap.put("国土局", obj.getFlandBureauName());
			upperMap.put("取证员", licenseRever);
			String keys = "取证时间,国土局,取证员";

			if (StringUtils.isNotBlank(obj.getRemark())) {
				upperMap.put("备注", obj.getRemark());
				keys += ",备注";
			}
			upperMap.put("keys", keys);

			Map<String, Object> downMap = new LinkedHashMap<String, Object>();
			String imgs = cleanImgUrl(obj.getForensiceImg());
			downMap.put("imgs", imgs);

			retMap.put("upper", upperMap);
			retMap.put("down", downMap);

			if ("fddMortgageStorage".equals(orderListDto.getProcessId())
					&& user.getUid().equals(orderListDto.getPreviousHandlerUid())) {
				retMap.put("isEdit", 1);
			}
			retMap.put("pageType", "fddForensics");
		} else if ("cancellation".equals(processId)) {
			CancellationDto obj = new CancellationDto();
			obj.setOrderNo(orderNo);
			obj = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/process/cancellation/v/detail", obj,
					CancellationDto.class);

			Map<String, Object> upperMap = new LinkedHashMap<String, Object>();
			upperMap.put("注销时间", DateUtil.getDateByFmt(obj.getCancelTime(), DateUtil.FMT_TYPE11));
			String clandBureauName = "-";
			String clandBureauUserName = "-";
			if (StringUtils.isNotBlank(obj.getClandBureauName())) {
				clandBureauName = obj.getClandBureauName();
			}
			if (StringUtils.isNotBlank(obj.getClandBureauUserName())) {
				clandBureauUserName = obj.getClandBureauUserName();
			}
			upperMap.put("国土局", clandBureauName);
			upperMap.put("国土局驻点", clandBureauUserName);

			String keys = "注销时间,国土局,国土局驻点";
			if (StringUtils.isNotBlank(obj.getRemark())) {
				upperMap.put("备注", obj.getRemark());
				keys += ",备注";
			}
			upperMap.put("keys", keys);

			Map<String, Object> downMap = new LinkedHashMap<String, Object>();
			String imgs = cleanImgUrl(obj.getCancelImg());
			downMap.put("imgs", imgs);

			retMap.put("upper", upperMap);
			retMap.put("down", downMap);

			if ("01".equals(orderListDto.getProductCode()) && "transfer".equals(orderListDto.getProcessId())
					&& user.getUid().equals(orderListDto.getPreviousHandlerUid())) {
				retMap.put("isEdit", 1);
			} else if (("02".equals(orderListDto.getProductCode())||"05".equals(orderListDto.getProductCode()))
					&& "mortgage".equals(orderListDto.getProcessId())
					&& user.getUid().equals(orderListDto.getPreviousHandlerUid())) {
				retMap.put("isEdit", 1);
			}
			retMap.put("pageType", "cancellation");
		} else if ("transfer".equals(processId) && orderListDto != null && ("01".equals(orderListDto.getProductCode())
				|| "02".equals(orderListDto.getProductCode()) || "03".equals(orderListDto.getProductCode()))) {
			TransferDto obj = new TransferDto();
			obj.setOrderNo(orderNo);
			obj = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/process/transfer/v/detail", obj,
					TransferDto.class);

			Map<String, Object> upperMap = new LinkedHashMap<String, Object>();
			upperMap.put("过户时间", DateUtil.getDateByFmt(obj.getTransferTime(), DateUtil.FMT_TYPE11));
			String receiptNumber = "-";
			if (StringUtils.isNotBlank(obj.getReceiptNumber())) {
				receiptNumber = obj.getReceiptNumber();
			}
			String tlandBureauName = "-";
			if (StringUtils.isNotBlank(obj.getTlandBureauName())) {
				tlandBureauName = obj.getTlandBureauName();
			}
			String tlandBureauUserName = "-";
			if (StringUtils.isNotBlank(obj.getTlandBureauUserName())) {
				tlandBureauUserName = obj.getTlandBureauUserName();
			}

			upperMap.put("过户回执编号", receiptNumber + "");
			upperMap.put("国土局", tlandBureauName);
			upperMap.put("国土局驻点", tlandBureauUserName);

			String keys = "过户时间,过户回执编号,国土局,国土局驻点";
			if (StringUtils.isNotBlank(obj.getRemark())) {
				upperMap.put("备注", obj.getRemark());
				keys += ",备注";
			}
			upperMap.put("keys", keys);

			Map<String, Object> downMap = new LinkedHashMap<String, Object>();
			String imgs = cleanImgUrl(obj.getTransferImg());
			downMap.put("imgs", imgs);

			retMap.put("upper", upperMap);
			retMap.put("down", downMap);

			if ("newlicense".equals(orderListDto.getProcessId())
					&& user.getUid().equals(orderListDto.getPreviousHandlerUid())) {
				retMap.put("isEdit", 1);
			}
			retMap.put("pageType", "transfer");
		} else if ("newlicense".equals(processId)) {
			NewlicenseDto obj = new NewlicenseDto();
			obj.setOrderNo(orderNo);
			obj = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/process/newlicense/v/detail", obj,
					NewlicenseDto.class);

			Map<String, Object> upperMap = new LinkedHashMap<String, Object>();
			upperMap.put("领新证时间", DateUtil.getDateByFmt(obj.getNewlicenseTime(), DateUtil.FMT_TYPE11));
			String nlandBureauName = "-";
			if (StringUtils.isNotBlank(obj.getNlandBureauName())) {
				nlandBureauName = obj.getNlandBureauName();
			}
			String nlandBureauUserName = "-";
			if (StringUtils.isNotBlank(obj.getNlandBureauUserName())) {
				nlandBureauUserName = obj.getNlandBureauUserName();
			}
			upperMap.put("国土局", nlandBureauName);
			upperMap.put("国土局驻点", nlandBureauUserName);

			String keys = "领新证时间,国土局,国土局驻点";
			if (StringUtils.isNotBlank(obj.getRemark())) {
				upperMap.put("备注", obj.getRemark());
				keys += ",备注";
			}

			upperMap.put("keys", keys);

			Map<String, Object> downMap = new LinkedHashMap<String, Object>();
			String imgs = cleanImgUrl(obj.getNewlicenseImg());
			downMap.put("imgs", imgs);

			retMap.put("upper", upperMap);
			retMap.put("down", downMap);

			if (("receivableFor".equals(orderListDto.getProcessId())
					|| "receivableForFirst".equals(orderListDto.getProcessId()))
					&& user.getUid().equals(orderListDto.getPreviousHandlerUid())) {
				retMap.put("isEdit", 1);
			}
			retMap.put("pageType", "newlicense");
		} else if ("mortgage".equals(processId) || "fddMortgage".equals(processId)) {
			MortgageDto obj = new MortgageDto();
			obj.setOrderNo(orderNo);
			obj = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/process/mortgage/v/detail", obj,
					MortgageDto.class);

			Map<String, Object> upperMap = new LinkedHashMap<String, Object>();
			upperMap.put("抵押时间", DateUtil.getDateByFmt(obj.getMortgageTime(), DateUtil.FMT_TYPE2));
			String mlandBureauName = "-";
			if (StringUtils.isNotBlank(obj.getMlandBureauName())) {
				mlandBureauName = obj.getMlandBureauName();
			}
			String mlandBureauUserName = "-";
			if (StringUtils.isNotBlank(obj.getMlandBureauUserName())) {
				mlandBureauUserName = obj.getMlandBureauUserName();
			}
			// 畅贷国土局驻点默认受理员
			if (("03".equals(orderListDto.getProductCode()) || "04".equals(orderListDto.getProductCode()))
					&& "-".equals(mlandBureauUserName)) {
				mlandBureauUserName = orderListDto.getAcceptMemberName();
			}
			upperMap.put("国土局", mlandBureauName);
			upperMap.put("国土局驻点", mlandBureauUserName);

			String keys = "抵押时间,国土局,国土局驻点";
			if ("fddMortgage".equals(processId)) {
				upperMap.put("抵押机构名称", obj.getMortgageName());
				keys += ",抵押机构名称";
			}
			if (StringUtils.isNotBlank(obj.getRemark())) {
				upperMap.put("备注", obj.getRemark());
				keys += ",备注";
			}

			upperMap.put("keys", keys);

			Map<String, Object> downMap = new LinkedHashMap<String, Object>();
			String imgs = cleanImgUrl(obj.getMortgageImg());
			downMap.put("imgs", imgs);

			retMap.put("upper", upperMap);
			retMap.put("down", downMap);
			String nextProcessId = "";
			if ("04".equals(orderListDto.getProductCode())) {// 查询终审还款方式
				FinalAuditDto finalAuditDto = new FinalAuditDto();
				finalAuditDto.setOrderNo(orderNo);
				finalAuditDto = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/risk/final/v/detail", finalAuditDto,
						FinalAuditDto.class);
				if (finalAuditDto.getPaymentType() != null && finalAuditDto.getPaymentType().contains("凭抵押")) {
					nextProcessId = "applyLoan";
				} else {
					nextProcessId = "fddForensics";
				}
			}
			if ("01".equals(orderListDto.getProductCode()) && "receivableForEnd".equals(orderListDto.getProcessId())
					&& user.getUid().equals(orderListDto.getPreviousHandlerUid())) {
				retMap.put("isEdit", 1);
			} else if (("02".equals(orderListDto.getProductCode()) || "03".equals(orderListDto.getProductCode()))
					&& "receivableFor".equals(orderListDto.getProcessId())
					&& user.getUid().equals(orderListDto.getPreviousHandlerUid())) {
				retMap.put("isEdit", 1);
			} else if ("04".equals(orderListDto.getProductCode())
					&& nextProcessId.equals(orderListDto.getProcessId())) {
				retMap.put("isEdit", 1);
			}
			if ("04".equals(orderListDto.getProductCode())) {
				retMap.put("pageType", "fddMortgage");
			} else {
				retMap.put("pageType", "mortgage");
			}
		}
		// 房抵贷节点详情
		// 抵押品入库
		else if ("fddMortgageStorage".equals(processId)) {// 抵押品入库
			FddMortgageStorageDto obj = new FddMortgageStorageDto();
			obj.setOrderNo(orderNo);
			obj = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/process/fddMortgageStorage/v/detail", obj,
					FddMortgageStorageDto.class);
			Map<String, Object> upperMap = new LinkedHashMap<String, Object>();
			upperMap.put("产权证类型", obj.getHousePropertyType());
			upperMap.put("产权证号", obj.getHousePropertyNumber());
			upperMap.put("所在地区", obj.getRegion());
			upperMap.put("房产名称", obj.getHouseName());
			upperMap.put("入库时间", DateUtil.getDateByFmt(obj.getCollateralTime(), DateUtil.FMT_TYPE2));
			String keys = "产权证类型,产权证号,所在地区,房产名称,入库时间";
			if (StringUtils.isNotBlank(obj.getRemark())) {
				upperMap.put("备注", obj.getRemark());
				keys += ",备注";
			}

			upperMap.put("keys", keys);
			retMap.put("upper", upperMap);
			String nextProcessId = "";
			FinalAuditDto finalAuditDto = new FinalAuditDto();
			finalAuditDto.setOrderNo(orderNo);
			finalAuditDto = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/risk/final/v/detail", finalAuditDto,
					FinalAuditDto.class);
			if (finalAuditDto.getPaymentType() != null && finalAuditDto.getPaymentType().contains("凭抵押")) {
				nextProcessId = "charge";
			} else {
				nextProcessId = "applyLoan";
			}
			if (nextProcessId.equals(orderListDto.getProcessId())) {
				retMap.put("isEdit", 1);
			}
			retMap.put("pageType", "fddMortgageStorage");
		} else if ("fddMortgageRelease".equals(processId)) {// 抵押品出库
			FddMortgageReleaseDto obj = new FddMortgageReleaseDto();
			obj.setOrderNo(orderNo);
			obj = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/process/fddMortgageRelease/v/detail", obj,
					FddMortgageReleaseDto.class);

			Map<String, Object> upperMap = new LinkedHashMap<String, Object>();
			upperMap.put("产权证类型", obj.getHousePropertyType());
			upperMap.put("产权证号", obj.getHousePropertyNumber());
			upperMap.put("所在地区", obj.getRegion());
			upperMap.put("房产名称", obj.getHouseName());
			upperMap.put("出库时间", DateUtil.getDateByFmt(obj.getCollateralOutTime(), DateUtil.FMT_TYPE2));
			String keys = "产权证类型,产权证号,所在地区,房产名称,出库时间";
			if (StringUtils.isNotBlank(obj.getRemark())) {
				upperMap.put("备注", obj.getRemark());
				keys += ",备注";
			}

			upperMap.put("keys", keys);
			retMap.put("upper", upperMap);
			if ("release".equals(orderListDto.getProcessId())) {
				retMap.put("isEdit", 1);
			}
			retMap.put("pageType", "fddMortgageRelease");
		} else if ("release".equals(processId)) {// 解押
			FddReleaseDto obj = new FddReleaseDto();
			obj.setOrderNo(orderNo);
			obj = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/process/release/v/detail", obj,
					FddReleaseDto.class);

			Map<String, Object> upperMap = new LinkedHashMap<String, Object>();
			upperMap.put("国土局", obj.getRlandBureauName());
			upperMap.put("解押时间", DateUtil.getDateByFmt(obj.getReleaseTime(), DateUtil.FMT_TYPE2));
			String keys = "国土局,解押时间";
			if (StringUtils.isNotBlank(obj.getRemark())) {
				upperMap.put("备注", obj.getRemark());
				keys += ",备注";
			}

			upperMap.put("keys", keys);
			retMap.put("upper", upperMap);
			if ("rebate".equals(orderListDto.getProcessId())) {
				retMap.put("isEdit", 1);
			}
			retMap.put("pageType", "release");
		} else if ("financialAudit".equals(processId)) {// 财务审核
			AuditDto obj = new AuditDto();
			obj.setOrderNo(orderNo);
			obj = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/finance/audit/v/detail", obj, AuditDto.class);

			Map<String, Object> upperMap = new LinkedHashMap<String, Object>();
			upperMap.put("审核时间", DateUtil.getDateByFmt(obj.getLastUpdateTime(), DateUtil.FMT_TYPE11));
			String keys = "审核时间";
			if (StringUtils.isNotBlank(obj.getRemark())) {
				upperMap.put("备注", obj.getRemark());
				keys += ",备注";
			}

			upperMap.put("keys", keys);
			retMap.put("upper", upperMap);

			retMap.put("pageType", "financialAudit");
		} else if ("elementReturn".equals(processId)) {// 要件退还
			DocumentsReturnDto obj = new DocumentsReturnDto();
			obj.setOrderNo(orderNo);
			obj = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/element/return/v/detail", obj,
					DocumentsReturnDto.class);

			Map<String, Object> upperMap = new LinkedHashMap<String, Object>();
			upperMap.put("要件退还时间", DateUtil.getDateByFmt(obj.getReturnTime(), DateUtil.FMT_TYPE2));

			String handleName = "-";
			if (StringUtils.isNotBlank(obj.getReturnHandleName())) {
				handleName = obj.getReturnHandleName();
			}
			upperMap.put("退还操作人", handleName);
			String keys = "要件退还时间,退还操作人";
			if (StringUtils.isNotBlank(obj.getRemark())) {
				upperMap.put("备注", obj.getRemark());
				keys += ",备注";
			}
			upperMap.put("keys", keys);
			Map<String, Object> downMap = new LinkedHashMap<String, Object>();
			String imgs = cleanImgUrl(obj.getReturnImgUrl());
			downMap.put("imgs", imgs);

			retMap.put("upper", upperMap);
			retMap.put("down", downMap);
			if ("rebate".equals(orderListDto.getProcessId())
					&& user.getUid().equals(orderListDto.getPreviousHandlerUid())) {
				retMap.put("isEdit", 1);
			}
			retMap.put("pageType", "elementReturn");
		} else if ("receivableForFirst".equals(processId)) {
			ReceivableForDto obj = new ReceivableForDto();
			obj.setOrderNo(orderNo);
			obj.setIsFrist(1);
			List<ReceivableForDto> list = httpUtil.getList(Constants.LINK_CREDIT,
					"/credit/finance/receivableFor/v/detail", obj, ReceivableForDto.class);
			String remark = "-";

			Map<String, Object> upperMap = new LinkedHashMap<String, Object>();
			Map<String, Object> downMap = new LinkedHashMap<String, Object>();
			String imgs = "";
			if (null != list && list.size() > 0) {
				String keys = "";
				for (int i = 0; i < list.size(); i++) {
					obj = list.get(i);
					if (i == 0) {
						upperMap.put("预计回款时间",
								StringUtils.isNotBlank(orderListDto.getPlanPaymentTime())
										? orderListDto.getPlanPaymentTime().substring(0, 10)
										: "-");
						upperMap.put("实际首次回款时间",
								"".equals(obj.getPayMentAmountDate()) ? "-" : obj.getPayMentAmountDate());
						upperMap.put("回款金额", null == obj.getPayMentAmount() ? "-" : obj.getPayMentAmount() + "万元");
						remark = obj.getRemark();
						keys = "预计回款时间,实际首次回款时间,回款金额";
					} else {
						upperMap.put("首次第" + (i + 1) + "次回款时间",
								"".equals(obj.getPayMentAmountDate()) ? "-" : obj.getPayMentAmountDate());
						upperMap.put("首次第" + (i + 1) + "次回款金额",
								null == obj.getPayMentAmount() ? "-" : obj.getPayMentAmount() + "万元");
						keys += ",首次第" + (i + 1) + "次回款时间,首次第" + (i + 1) + "次回款金额";
					}
					if (StringUtils.isNotBlank(obj.getPayMentPic())) {
						imgs += ";" + obj.getPayMentPic();
					}
				}
				if (StringUtils.isNotBlank(remark)) {
					upperMap.put("备注", remark);
					keys += ",备注";
				}
				upperMap.put("keys", keys);
			} else {
				upperMap.put("预计回款时间",
						StringUtils.isNotBlank(orderListDto.getPlanPaymentTime())
								? orderListDto.getPlanPaymentTime().substring(0, 10)
								: "-");
				upperMap.put("实际首次回款时间", "".equals(obj.getPayMentAmountDate()) ? "-" : obj.getPayMentAmountDate());
				upperMap.put("首次回款金额", null == obj.getPayMentAmount() ? "-" : obj.getPayMentAmount() + "");

				String keys = "预计回款时间,实际首次回款时间,首次回款金额";
				if (StringUtils.isNotBlank(obj.getRemark())) {
					upperMap.put("备注", obj.getRemark());
					keys += ",备注";
				}
				upperMap.put("keys", keys);
				imgs = obj.getPayMentPic();
			}

			imgs = cleanImgUrl(imgs);

			downMap.put("imgs", imgs);

			retMap.put("upper", upperMap);
			retMap.put("down", downMap);

		} else if ("receivableForEnd".equals(processId)) {
			ReceivableForDto obj = new ReceivableForDto();
			obj.setOrderNo(orderNo);
			List<ReceivableForDto> list = httpUtil.getList(Constants.LINK_CREDIT,
					"/credit/finance/receivableFor/v/detail", obj, ReceivableForDto.class);

			String remark = "-";

			Map<String, Object> upperMap = new LinkedHashMap<String, Object>();
			Map<String, Object> downMap = new LinkedHashMap<String, Object>();
			String imgs = "";
			if (null != list && list.size() > 0) {
				String keys = "";
				for (int i = 0; i < list.size(); i++) {
					obj = list.get(i);
					if (i == 0) {
						upperMap.put("预计回款时间",
								StringUtils.isNotBlank(orderListDto.getPlanPaymentTime())
										? orderListDto.getPlanPaymentTime().substring(0, 10)
										: "-");
						upperMap.put("实际首次回款时间",
								"".equals(obj.getPayMentAmountDate()) ? "-" : obj.getPayMentAmountDate());
						upperMap.put("回款金额", null == obj.getPayMentAmount() ? "-" : obj.getPayMentAmount() + "万元");
						remark = obj.getRemark();
						keys = "预计回款时间,实际首次回款时间,回款金额";
					} else {
						upperMap.put("第" + (i + 1) + "次回款时间",
								"".equals(obj.getPayMentAmountDate()) ? "-" : obj.getPayMentAmountDate());
						upperMap.put("第" + (i + 1) + "次回款金额",
								null == obj.getPayMentAmount() ? "-" : obj.getPayMentAmount() + "万元");
						keys += ",第" + (i + 1) + "次回款时间,第" + (i + 1) + "次回款金额";
					}
					if (StringUtils.isNotBlank(obj.getPayMentPic())) {
						imgs += ";" + obj.getPayMentPic();
					}
				}
				if (StringUtils.isNotBlank(remark)) {
					upperMap.put("备注", remark);
					keys += ",备注";
				}
				upperMap.put("keys", keys);
			} else {
				upperMap.put("预计回款时间",
						StringUtils.isNotBlank(orderListDto.getPlanPaymentTime())
								? orderListDto.getPlanPaymentTime().substring(0, 10)
								: "-");
				upperMap.put("实际首次回款时间", "".equals(obj.getPayMentAmountDate()) ? "-" : obj.getPayMentAmountDate());
				upperMap.put("回款金额", null == obj.getPayMentAmount() ? "-" : obj.getPayMentAmount() + "");

				String keys = "预计回款时间,实际首次回款时间,回款金额";
				if (StringUtils.isNotBlank(obj.getRemark())) {
					upperMap.put("备注", obj.getRemark());
					keys += ",备注";
				}
				upperMap.put("keys", keys);
				imgs = obj.getPayMentPic();
			}

			imgs = cleanImgUrl(imgs);

			downMap.put("imgs", imgs);

			retMap.put("upper", upperMap);
			retMap.put("down", downMap);
		} else if ("receivableFor".equals(processId)) {
			ReceivableForDto obj = new ReceivableForDto();
			obj.setOrderNo(orderNo);
			List<ReceivableForDto> list = httpUtil.getList(Constants.LINK_CREDIT,
					"/credit/finance/receivableFor/v/detail", obj, ReceivableForDto.class);

			String remark = "-";

			Map<String, Object> upperMap = new LinkedHashMap<String, Object>();
			Map<String, Object> downMap = new LinkedHashMap<String, Object>();
			String imgs = "";
			if (null != list && list.size() > 0) {
				String keys = "";
				for (int i = 0; i < list.size(); i++) {
					obj = list.get(i);
					if (i == 0) {
						upperMap.put("预计回款时间",
								StringUtils.isNotBlank(orderListDto.getPlanPaymentTime())
										? orderListDto.getPlanPaymentTime().substring(0, 10)
										: "-");
						upperMap.put("实际回款时间",
								"".equals(obj.getPayMentAmountDate()) ? "-" : obj.getPayMentAmountDate());
						upperMap.put("回款金额", null == obj.getPayMentAmount() ? "-" : obj.getPayMentAmount() + "万元");
						remark = obj.getRemark();
						keys = "预计回款时间,实际回款时间,回款金额";
					} else {
						upperMap.put("第" + (i + 1) + "次回款时间",
								"".equals(obj.getPayMentAmountDate()) ? "-" : obj.getPayMentAmountDate());
						upperMap.put("第" + (i + 1) + "次回款金额",
								null == obj.getPayMentAmount() ? "-" : obj.getPayMentAmount() + "万元");
						keys += ",第" + (i + 1) + "次回款时间,第" + (i + 1) + "次回款金额";
					}
					if (StringUtils.isNotBlank(obj.getPayMentPic())) {
						imgs += ";" + obj.getPayMentPic();
					}
				}
				if (StringUtils.isNotBlank(remark)) {
					upperMap.put("备注", remark);
					keys += ",备注";
				}
				upperMap.put("keys", keys);
			} else {
				upperMap.put("预计回款时间",
						StringUtils.isNotBlank(orderListDto.getPlanPaymentTime())
								? orderListDto.getPlanPaymentTime().substring(0, 10)
								: "-");
				upperMap.put("实际回款时间", "".equals(obj.getPayMentAmountDate()) ? "-" : obj.getPayMentAmountDate());
				upperMap.put("回款金额", null == obj.getPayMentAmount() ? "-" : obj.getPayMentAmount() + "");

				String keys = "预计回款时间,实际回款时间,回款金额";
				if (StringUtils.isNotBlank(obj.getRemark())) {
					upperMap.put("备注", obj.getRemark());
					keys += ",备注";
				}
				upperMap.put("keys", keys);
				imgs = obj.getPayMentPic();
			}

			imgs = cleanImgUrl(imgs);

			downMap.put("imgs", imgs);

			retMap.put("upper", upperMap);
			retMap.put("down", downMap);
		} else if ("fddMortgageStorage".equals(processId)) {
			FddMortgageStorageDto obj = new FddMortgageStorageDto();
			obj.setOrderNo(orderNo);
			obj = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/process/fddMortgageStorage/v/detail", obj,
					FddMortgageStorageDto.class);

			Map<String, Object> upperMap = new LinkedHashMap<String, Object>();

			String keys = "产权证类型,产权证号,所在地区,房产名称,入库时间";
			upperMap.put("产权证类型", obj.getHousePropertyType());
			upperMap.put("产权证号", obj.getHousePropertyNumber());
			upperMap.put("所在地区", obj.getRegion());
			upperMap.put("房产名称", obj.getHouseName());
			upperMap.put("入库时间", DateUtil.getDateByFmt(obj.getCollateralTime(), DateUtil.FMT_TYPE2));
			if (StringUtils.isNotBlank(obj.getRemark())) {
				upperMap.put("备注", obj.getRemark());
				keys += ",备注";
			}
			upperMap.put("keys", keys);
			// Map<String, Object> downMap = new LinkedHashMap<String, Object>();
			// String imgs = cleanImgUrl(obj.getPayImg());
			// downMap.put("imgs",imgs);
			retMap.put("upper", upperMap);
			// retMap.put("down", downMap);
		} else if ("fddMortgageRelease".equals(processId)) {
			FddMortgageReleaseDto obj = new FddMortgageReleaseDto();
			obj.setOrderNo(orderNo);
			obj = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/process/fddMortgageRelease/v/detail", obj,
					FddMortgageReleaseDto.class);

			Map<String, Object> upperMap = new LinkedHashMap<String, Object>();

			String keys = "产权证类型,产权证号,所在地区,房产名称,出库时间";
			upperMap.put("产权证类型", obj.getHousePropertyType());
			upperMap.put("产权证号", obj.getHousePropertyNumber());
			upperMap.put("所在地区", obj.getRegion());
			upperMap.put("房产名称", obj.getHouseName());
			upperMap.put("出库时间", DateUtil.getDateByFmt(obj.getCollateralOutTime(), DateUtil.FMT_TYPE2));
			if (StringUtils.isNotBlank(obj.getRemark())) {
				upperMap.put("备注", obj.getRemark());
				keys += ",备注";
			}
			upperMap.put("keys", keys);
			// Map<String, Object> downMap = new LinkedHashMap<String, Object>();
			// String imgs = cleanImgUrl(obj.getPayImg());
			// downMap.put("imgs",imgs);
			retMap.put("upper", upperMap);
			// retMap.put("down", downMap);
		} else if ("release".equals(processId)) {
			FddReleaseDto obj = new FddReleaseDto();
			obj.setOrderNo(orderNo);
			obj = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/process/release/v/detail", obj,
					FddReleaseDto.class);

			Map<String, Object> upperMap = new LinkedHashMap<String, Object>();

			String keys = "解押时间,国土局";
			upperMap.put("解押时间", DateUtil.getDateByFmt(obj.getReleaseTime(), DateUtil.FMT_TYPE2));
			upperMap.put("国土局", obj.getRlandBureauName());
			if (StringUtils.isNotBlank(obj.getRemark())) {
				upperMap.put("备注", obj.getRemark());
				keys += ",备注";
			}
			upperMap.put("keys", keys);
			// Map<String, Object> downMap = new LinkedHashMap<String, Object>();
			// String imgs = cleanImgUrl(obj.getPayImg());
			// downMap.put("imgs",imgs);
			retMap.put("upper", upperMap);
			// retMap.put("down", downMap);
		} else if ("pay".equals(processId)) {// 收罚息
			ReceivablePayDto obj = new ReceivablePayDto();
			obj.setOrderNo(orderNo);
			obj = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/finance/receivablePay/v/detail", obj,
					ReceivablePayDto.class);

			Map<String, Object> upperMap = new LinkedHashMap<String, Object>();

			String keys = "逾期费率,逾期天数,应收罚息,收罚息时间,实收罚息";
			if (obj.getRebateMoney() != null && obj.getRebateMoney().doubleValue() > 0) {
				keys += ",返佣金额";
				upperMap.put("返佣金额", obj.getRebateMoney().toString() + "元");
			}
			upperMap.put("逾期费率", borrow.getOverdueRate() + "%/天");
			upperMap.put("逾期天数", Math.abs(obj.getDatediff()) + "天");
			upperMap.put("应收罚息", obj.getPenaltyPayable().toString() + "元");
			upperMap.put("实收罚息", obj.getPenaltyReal().toString() + "元");
			upperMap.put("收罚息时间", DateUtil.getDateByFmt(obj.getPayTime(), DateUtil.FMT_TYPE2));
			if (StringUtils.isNotBlank(obj.getRemark())) {
				upperMap.put("备注", obj.getRemark());
				keys += ",备注";
			}
			upperMap.put("keys", keys);
			Map<String, Object> downMap = new LinkedHashMap<String, Object>();
			String imgs = cleanImgUrl(obj.getPayImg());
			downMap.put("imgs", imgs);
			retMap.put("upper", upperMap);
			retMap.put("down", downMap);
		} else if ("rebate".equals(processId)) {
			RebateDto obj = new RebateDto();
			obj.setOrderNo(orderNo);
			obj = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/finance/rebate/v/detail", obj, RebateDto.class);

			Map<String, Object> upperMap = new LinkedHashMap<String, Object>();

			String keys = "返佣金额,返佣时间";
			upperMap.put("返佣金额", obj.getRebateMoney() + "元");
			upperMap.put("返佣时间", obj.getRebateTimeStr());
			if (StringUtils.isNotBlank(obj.getRemark())) {
				upperMap.put("备注", obj.getRemark());
				keys += ",备注";
			}
			upperMap.put("keys", keys);
			Map<String, Object> downMap = new LinkedHashMap<String, Object>();
			String imgs = cleanImgUrl(obj.getRebateImg());
			downMap.put("imgs", imgs);
			retMap.put("upper", upperMap);
			retMap.put("down", downMap);
		} else if ("repaymentChannelManager".equals(processId)) { // 指派渠道经理
			ProductDataDto obj = new ProductDataDto();
			obj.setOrderNo(orderNo);
			obj.setTblDataName("tbl_cm_data");
			obj.setTblName("tbl_cm_repaymentChannelManager");
			Map<String, Object> upperMap = new LinkedHashMap<String, Object>();
			JSONObject jsons = httpUtil.getData(Constants.LINK_CREDIT, "/credit/product/data/base/v/select", obj);
			RespDataObject<LinkedTreeMap<String, Object>> result = new Gson().fromJson(jsons.toString(),
					RespDataObject.class);
			Map<String, Object> tmp = new HashMap<String, Object>();
			if (RespStatusEnum.SUCCESS.getCode().equals(result.getCode())) {
				LinkedTreeMap<String, Object> map = result.getData();
				if (map.containsKey("data")) {
					tmp = (Map<String, Object>) map.get("data");
				}
			}
			upperMap.put("渠道经理", MapUtils.getString(tmp, "channelManagerName", "-"));
			String keys = "渠道经理";
			if (StringUtils.isNotBlank(MapUtils.getString(tmp, "remark"))) {
				upperMap.put("备注", MapUtils.getString(tmp, "remark", "-"));
				keys += ",备注";
			}
			upperMap.put("keys", keys);
			retMap.put("upper", upperMap);
		} else if ("assess".equals(processId)) { // 申请评估
			ProductDataDto obj = new ProductDataDto();
			obj.setOrderNo(orderNo);
			obj.setTblDataName("tbl_cm_data");
			obj.setTblName("tbl_cm_assess");
			Map<String, Object> upperMap = new LinkedHashMap<String, Object>();
			JSONObject jsons = httpUtil.getData(Constants.LINK_CREDIT, "/credit/product/data/base/v/select", obj);
			RespDataObject<LinkedTreeMap<String, Object>> result = new Gson().fromJson(jsons.toString(),
					RespDataObject.class);
			Map<String, Object> tmp = new HashMap<String, Object>();
			if (RespStatusEnum.SUCCESS.getCode().equals(result.getCode())) {
				LinkedTreeMap<String, Object> map = result.getData();
				if (map.containsKey("data")) {
					tmp = (Map<String, Object>) map.get("data");
				}
			}
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("orderNo", orderNo);
			params.put("tblName", "tbl_cm_");
			RespDataObject<Map<String, Object>> respD = httpUtil.getRespDataObject(Constants.LINK_CREDIT,
					"/credit/product/data/list/base/v/select", params, Map.class);
			params = respD.getData();
			if (MapUtils.getIntValue(params, "agencyId") <= 1) {
				upperMap.put("分公司(营业部)", MapUtils.getString(tmp, "branchCompanyCode", "-"));
			}
			upperMap.put("渠道经理", MapUtils.getString(tmp, "channelManagerName", "-"));
			upperMap.put("合作机构", MapUtils.getString(tmp, "cooperativeAgencyName", "-"));
			upperMap.put("银行", MapUtils.getString(tmp, "bankName", "-"));
			upperMap.put("客户经理手机号", MapUtils.getString(tmp, "custManagerMobile", "-"));
			upperMap.put("客户经理姓名", MapUtils.getString(tmp, "custManagerName", "-"));
			upperMap.put("支行", MapUtils.getString(tmp, "subBankName", "-"));
			upperMap.put("业主姓名", MapUtils.getString(tmp, "ownerName", "-"));
			upperMap.put("业主证件类型", MapUtils.getString(tmp, "ownerCertificateTypeCode", "-"));
			upperMap.put("业主证件号码", MapUtils.getString(tmp, "ownerCertificateNo", "-"));
			upperMap.put("产权证书类型", MapUtils.getString(tmp, "estateType", "-"));
			upperMap.put("房产证号", MapUtils.getString(tmp, "estateNo", "-"));
			upperMap.put("实际成交价格", MapUtils.getString(tmp, "actPrice", "-") + "元");
			String keys = "";
			if (MapUtils.getIntValue(params, "agencyId") <= 1) {
				keys = "分公司(营业部),渠道经理,合作机构,银行,客户经理手机号,客户经理姓名,支行,业主姓名,业主证件类型,业主证件号码,产权证书类型,房产证号,实际成交价格";
			} else {
				keys = "渠道经理,合作机构,银行,客户经理手机号,客户经理姓名,支行,业主姓名,业主证件类型,业主证件号码,产权证书类型,房产证号,实际成交价格";
			}
			if (StringUtils.isNotBlank(MapUtils.getString(tmp, "remark"))) {
				upperMap.put("补充说明", MapUtils.getString(tmp, "remark", "-"));
				keys += ",补充说明";
			}
			upperMap.put("keys", keys);

			Map<String, Object> downMap = new LinkedHashMap<String, Object>();
			String imgs = cleanImgUrl(MapUtils.getString(tmp, "estateImg", ""));
			downMap.put("imgs", imgs);

			retMap.put("upper", upperMap);
			retMap.put("down", downMap);
		} else if ("assessFail".equals(processId)) { // 评估失败
			ProductDataDto obj = new ProductDataDto();
			obj.setOrderNo(orderNo);
			obj.setTblDataName("tbl_cm_data");
			obj.setTblName("tbl_cm_assessFail");
			Map<String, Object> upperMap = new LinkedHashMap<String, Object>();
			JSONObject jsons = httpUtil.getData(Constants.LINK_CREDIT, "/credit/product/data/base/v/select", obj);
			RespDataObject<LinkedTreeMap<String, Object>> result = new Gson().fromJson(jsons.toString(),
					RespDataObject.class);
			Map<String, Object> tmp = new HashMap<String, Object>();
			if (RespStatusEnum.SUCCESS.getCode().equals(result.getCode())) {
				LinkedTreeMap<String, Object> map = result.getData();
				if (map.containsKey("data")) {
					tmp = (Map<String, Object>) map.get("data");
				}
			}
			upperMap.put("评估时间", MapUtils.getString(tmp, "createTime", "-"));
			upperMap.put("失败原因", MapUtils.getString(tmp, "reason", "-"));

			String keys = "评估时间,失败原因";
			upperMap.put("keys", keys);

			retMap.put("upper", upperMap);
		} else if ("assessSuccess".equals(processId)) { // 评估成功
			ProductDataDto obj = new ProductDataDto();
			obj.setOrderNo(orderNo);
			obj.setTblDataName("tbl_cm_data");
			obj.setTblName("tbl_cm_assessSuccess");
			Map<String, Object> upperMap = new LinkedHashMap<String, Object>();
			RespDataObject<Map<String, Object>> dataObject = httpUtil.getRespDataObject(Constants.LINK_CREDIT,
					"/credit/product/data/base/v/select", obj, Map.class);
			JSONObject jsonObject = JSONObject.fromObject(dataObject.getData().get("data"));
			Map<String, Object> tmp = new HashMap<String, Object>();
			tmp = (Map<String, Object>) jsonObject;
			upperMap.put("评估时间", MapUtils.getString(tmp, "createTime", "-"));
			upperMap.put("房屋用途", MapUtils.getString(tmp, "houseUse", "-"));
			upperMap.put("总值", MapUtils.getString(tmp, "totalAmount", "-") + "元");
			upperMap.put("净值（扣契税）", MapUtils.getString(tmp, "netDeedTax", "-") + "元");
			upperMap.put("净值（扣全税）", MapUtils.getString(tmp, "netAllTax", "-") + "元");
			String keys = "评估时间,房屋用途,总值,净值（扣契税）,净值（扣全税）";
			upperMap.put("keys", keys);
			retMap.put("upper", upperMap);
		} else if ("managerAudit".equals(processId)) {// 客户经理审核
			ProductDataDto obj = new ProductDataDto();
			obj.setOrderNo(orderNo);
			obj.setTblDataName("tbl_cm_data");
			obj.setTblName("tbl_cm_managerAudit");
			Map<String, Object> upperMap = new LinkedHashMap<String, Object>();
			JSONObject jsons = httpUtil.getData(Constants.LINK_CREDIT, "/credit/product/data/base/v/select", obj);
			RespDataObject<LinkedTreeMap<String, Object>> result = new Gson().fromJson(jsons.toString(),
					RespDataObject.class);
			Map<String, Object> tmp = new HashMap<String, Object>();
			if (RespStatusEnum.SUCCESS.getCode().equals(result.getCode())) {
				LinkedTreeMap<String, Object> map = result.getData();
				if (map.containsKey("data")) {
					tmp = (Map<String, Object>) map.get("data");
				}
			}
			upperMap.put("完成时间", MapUtils.getString(tmp, "createTime", "-"));
			String keys = "完成时间";
			upperMap.put("keys", keys);
			retMap.put("upper", upperMap);
		} else if ("storesReserve".equals(processId)) {// 审批前材料准备
			ProductDataDto obj = new ProductDataDto();
			obj.setOrderNo(orderNo);
			obj.setTblDataName("tbl_cm_data");
			obj.setTblName("tbl_cm_storesReserve");
			Map<String, Object> upperMap = new LinkedHashMap<String, Object>();
			JSONObject jsons = httpUtil.getData(Constants.LINK_CREDIT, "/credit/product/data/base/v/select", obj);
			RespDataObject<LinkedTreeMap<String, Object>> result = new Gson().fromJson(jsons.toString(),
					RespDataObject.class);
			Map<String, Object> tmp = new HashMap<String, Object>();
			if (RespStatusEnum.SUCCESS.getCode().equals(result.getCode())) {
				LinkedTreeMap<String, Object> map = result.getData();
				if (map.containsKey("data")) {
					tmp = (Map<String, Object>) map.get("data");
				}
			}
			upperMap.put("完成时间", MapUtils.getString(tmp, "createTime", "-"));
			String keys = "完成时间";
			upperMap.put("keys", keys);
			retMap.put("upper", upperMap);
		} else if ("auditSuccess".equals(processId)) { // 审批通过
			ProductDataDto obj = new ProductDataDto();
			obj.setOrderNo(orderNo);
			obj.setTblDataName("tbl_cm_data");
			obj.setTblName("tbl_cm_auditSuccess");
			Map<String, Object> upperMap = new LinkedHashMap<String, Object>();
			JSONObject jsons = httpUtil.getData(Constants.LINK_CREDIT, "/credit/product/data/base/v/select", obj);
			RespDataObject<LinkedTreeMap<String, Object>> result = new Gson().fromJson(jsons.toString(),
					RespDataObject.class);
			Map<String, Object> tmp = new HashMap<String, Object>();
			if (RespStatusEnum.SUCCESS.getCode().equals(result.getCode())) {
				LinkedTreeMap<String, Object> map = result.getData();
				if (map.containsKey("data")) {
					tmp = (Map<String, Object>) map.get("data");
				}
			}
			upperMap.put("完成时间", MapUtils.getString(tmp, "createTime", "-"));
			upperMap.put("贷款金额", MapUtils.getString(tmp, "loanamt", "-") + "元");
			upperMap.put("贷款期限", MapUtils.getString(tmp, "loantrm", "-") + "月");
			upperMap.put("还款方式", MapUtils.getString(tmp, "repaymentWayCode", "-"));
			String keys = "完成时间,贷款金额,贷款期限,还款方式";
			upperMap.put("keys", keys);
			retMap.put("upper", upperMap);
		} else if ("auditFail".equals(processId)) { // 审批失败
			ProductDataDto obj = new ProductDataDto();
			obj.setOrderNo(orderNo);
			obj.setTblDataName("tbl_cm_data");
			obj.setTblName("tbl_cm_auditFail");
			Map<String, Object> upperMap = new LinkedHashMap<String, Object>();
			JSONObject jsons = httpUtil.getData(Constants.LINK_CREDIT, "/credit/product/data/base/v/select", obj);
			RespDataObject<LinkedTreeMap<String, Object>> result = new Gson().fromJson(jsons.toString(),
					RespDataObject.class);
			Map<String, Object> tmp = new HashMap<String, Object>();
			if (RespStatusEnum.SUCCESS.getCode().equals(result.getCode())) {
				LinkedTreeMap<String, Object> map = result.getData();
				if (map.containsKey("data")) {
					tmp = (Map<String, Object>) map.get("data");
				}
			}
			upperMap.put("完成时间", MapUtils.getString(tmp, "createTime", "-"));
			upperMap.put("原因", MapUtils.getString(tmp, "reason", "-"));
			String keys = "完成时间,原因";
			upperMap.put("keys", keys);
			retMap.put("upper", upperMap);
		} else if ("transfer".equals(processId)) { // 房产过户和抵押
			ProductDataDto obj = new ProductDataDto();
			obj.setOrderNo(orderNo);
			obj.setTblDataName("tbl_cm_data");
			obj.setTblName("tbl_cm_transfer");
			Map<String, Object> upperMap = new LinkedHashMap<String, Object>();
			JSONObject jsons = httpUtil.getData(Constants.LINK_CREDIT, "/credit/product/data/base/v/select", obj);
			RespDataObject<LinkedTreeMap<String, Object>> result = new Gson().fromJson(jsons.toString(),
					RespDataObject.class);
			Map<String, Object> tmp = new HashMap<String, Object>();
			if (RespStatusEnum.SUCCESS.getCode().equals(result.getCode())) {
				LinkedTreeMap<String, Object> map = result.getData();
				if (map.containsKey("data")) {
					tmp = (Map<String, Object>) map.get("data");
				}
			}
			upperMap.put("完成时间", MapUtils.getString(tmp, "createTime", "-"));
			String keys = "完成时间";
			upperMap.put("keys", keys);
			retMap.put("upper", upperMap);
		} else if ("reserveMortgage".equals(processId)) { // 预约抵押登记
			ProductDataDto obj = new ProductDataDto();
			obj.setOrderNo(orderNo);
			obj.setTblDataName("tbl_cm_data");
			obj.setTblName("tbl_cm_reserveMortgage");
			Map<String, Object> upperMap = new LinkedHashMap<String, Object>();
			JSONObject jsons = httpUtil.getData(Constants.LINK_CREDIT, "/credit/product/data/base/v/select", obj);
			RespDataObject<LinkedTreeMap<String, Object>> result = new Gson().fromJson(jsons.toString(),
					RespDataObject.class);
			Map<String, Object> tmp = new HashMap<String, Object>();
			if (RespStatusEnum.SUCCESS.getCode().equals(result.getCode())) {
				LinkedTreeMap<String, Object> map = result.getData();
				if (map.containsKey("data")) {
					tmp = (Map<String, Object>) map.get("data");
				}
			}
			upperMap.put("预约日期", MapUtils.getString(tmp, "reserveDate", "-"));
			upperMap.put("预约时间", MapUtils.getString(tmp, "reserveTimeCode", "-"));
			upperMap.put("预约地点", MapUtils.getString(tmp, "reserveAddressCode", "-"));
			String keys = "预约日期,预约时间,预约地点";
			if (StringUtils.isNotBlank(MapUtils.getString(tmp, "remark"))) {
				upperMap.put("备注", MapUtils.getString(tmp, "remark", "-"));
				keys += ",备注";
			}
			upperMap.put("keys", keys);
			retMap.put("upper", upperMap);
		} else if ("forensicsMortgage".equals(processId)) { // 取证抵押
			ProductDataDto obj = new ProductDataDto();
			obj.setOrderNo(orderNo);
			obj.setTblDataName("tbl_cm_data");
			obj.setTblName("tbl_cm_forensicsMortgage");
			Map<String, Object> upperMap = new LinkedHashMap<String, Object>();
			JSONObject jsons = httpUtil.getData(Constants.LINK_CREDIT, "/credit/product/data/base/v/select", obj);
			RespDataObject<LinkedTreeMap<String, Object>> result = new Gson().fromJson(jsons.toString(),
					RespDataObject.class);
			Map<String, Object> tmp = new HashMap<String, Object>();
			if (RespStatusEnum.SUCCESS.getCode().equals(result.getCode())) {
				LinkedTreeMap<String, Object> map = result.getData();
				if (map.containsKey("data")) {
					tmp = (Map<String, Object>) map.get("data");
				}
			}
			upperMap.put("新证年份", MapUtils.getString(tmp, "newYear", "-"));
			upperMap.put("新房产证号", MapUtils.getString(tmp, "newPropertyNumber", "-"));
			// upperMap.put("新房产证图片", MapUtils.getString(tmp,"newPropertyLicensePic","-"));
			// upperMap.put("抵押回执图片", MapUtils.getString(tmp,"mortgageReceiptPic","-"));
			String keys = "新证年份,新房产证号";
			upperMap.put("keys", keys);
			Map<String, Object> downMap = new LinkedHashMap<String, Object>();
			String imgs = MapUtils.getString(tmp, "newPropertyLicensePic", "");
			if (StringUtil.isNotEmpty(imgs)) {
				imgs = cleanImgUrl(imgs + "," + MapUtils.getString(tmp, "mortgageReceiptPic", ""));
			} else {
				imgs = cleanImgUrl(MapUtils.getString(tmp, "mortgageReceiptPic", ""));
			}
			downMap.put("imgs", imgs);

			retMap.put("upper", upperMap);
			retMap.put("down", downMap);

			retMap.put("upper", upperMap);
		} else if ("loanSuccess".equals(processId)) { // 放款成功
			ProductDataDto obj = new ProductDataDto();
			obj.setOrderNo(orderNo);
			obj.setTblDataName("tbl_cm_data");
			obj.setTblName("tbl_cm_loanSuccess");
			Map<String, Object> upperMap = new LinkedHashMap<String, Object>();
			JSONObject jsons = httpUtil.getData(Constants.LINK_CREDIT, "/credit/product/data/base/v/select", obj);
			RespDataObject<LinkedTreeMap<String, Object>> result = new Gson().fromJson(jsons.toString(),
					RespDataObject.class);
			Map<String, Object> tmp = new HashMap<String, Object>();
			if (RespStatusEnum.SUCCESS.getCode().equals(result.getCode())) {
				LinkedTreeMap<String, Object> map = result.getData();
				if (map.containsKey("data")) {
					tmp = (Map<String, Object>) map.get("data");
				}
			}
			upperMap.put("完成时间", MapUtils.getString(tmp, "createTime", "-"));
			upperMap.put("贷款账号", MapUtils.getString(tmp, "loanAccount", "-"));
			upperMap.put("放款账号", MapUtils.getString(tmp, "lendingAccount", "-"));
			upperMap.put("放款金额", MapUtils.getString(tmp, "lendingAmount", "-") + "元");
			upperMap.put("利率", MapUtils.getString(tmp, "rate", "-") + "%");
			upperMap.put("贷款期限", MapUtils.getString(tmp, "loanLimit", "-") + "月");
			upperMap.put("还款方式", MapUtils.getString(tmp, "repaymentWayCode", "-"));
			upperMap.put("放款日", MapUtils.getString(tmp, "lendingDate", "-"));
			upperMap.put("到期日", MapUtils.getString(tmp, "maturityDate", "-"));
			String keys = "完成时间,贷款账号,放款账号,放款金额,利率,贷款期限,还款方式,放款日,到期日";
			upperMap.put("keys", keys);
			retMap.put("upper", upperMap);
		} else if ("loanFail".equals(processId)) { // 放款失败
			ProductDataDto obj = new ProductDataDto();
			obj.setOrderNo(orderNo);
			obj.setTblDataName("tbl_cm_data");
			obj.setTblName("tbl_cm_loanFail");
			Map<String, Object> upperMap = new LinkedHashMap<String, Object>();
			JSONObject jsons = httpUtil.getData(Constants.LINK_CREDIT, "/credit/product/data/base/v/select", obj);
			RespDataObject<LinkedTreeMap<String, Object>> result = new Gson().fromJson(jsons.toString(),
					RespDataObject.class);
			Map<String, Object> tmp = new HashMap<String, Object>();
			if (RespStatusEnum.SUCCESS.getCode().equals(result.getCode())) {
				LinkedTreeMap<String, Object> map = result.getData();
				if (map.containsKey("data")) {
					tmp = (Map<String, Object>) map.get("data");
				}
			}
			upperMap.put("完成时间", MapUtils.getString(tmp, "createTime", "-"));
			upperMap.put("失败原因", MapUtils.getString(tmp, "reason", "-"));
			String keys = "完成时间,失败原因";
			upperMap.put("keys", keys);
			retMap.put("upper", upperMap);
		} else if ("closeOrder".equals(processId)) { // 关闭
			ProductDataDto obj = new ProductDataDto();
			obj.setOrderNo(orderNo);
			obj.setTblDataName("tbl_cm_data");
			obj.setTblName("tbl_cm_closeOrder");
			Map<String, Object> upperMap = new LinkedHashMap<String, Object>();
			JSONObject jsons = httpUtil.getData(Constants.LINK_CREDIT, "/credit/product/data/base/v/select", obj);
			RespDataObject<LinkedTreeMap<String, Object>> result = new Gson().fromJson(jsons.toString(),
					RespDataObject.class);
			Map<String, Object> tmp = new HashMap<String, Object>();
			if (RespStatusEnum.SUCCESS.getCode().equals(result.getCode())) {
				LinkedTreeMap<String, Object> map = result.getData();
				if (map.containsKey("data")) {
					tmp = (Map<String, Object>) map.get("data");
				}
			}
			upperMap.put("关闭时间", MapUtils.getString(tmp, "closeTime", "-"));
			upperMap.put("关闭原因", MapUtils.getString(tmp, "closeReason", "-"));
			String keys = "关闭时间,关闭原因";
			upperMap.put("keys", keys);
			retMap.put("upper", upperMap);
		}
		return retMap;
	}

	/**
	 * 去除字符開頭和末尾逗號
	 * 
	 * @param img
	 * @return
	 */
	public String cleanImgUrl(String img) {
		if (StringUtils.isBlank(img))
			return img;
		img = img.replaceAll(";", ",");
		if (img.startsWith(",") && img.length() > 1) {
			img = img.substring(1, img.length());
		}
		if (img.endsWith(",") && img.length() > 1) {
			img = img.substring(0, img.length() - 1);
		}
		return img;
	}

	/**
	 * 
	 * @Author
	 * @Rewrite KangLG<2018年1月12日> 新增hasYAJ参数
	 * @param user
	 * @param hasYAJ
	 *            下拉是否包含云按揭
	 * @return
	 */
	private Map<String, Object> assembleBrush(UserDto user, boolean hasYAJ) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		List<DictDto> cityListTemp = getDictDtoByType("bookingSzAreaOid");
		List<ProductDto> productListTemp = getProductDtos();
		List<Map<String, Object>> cityList = new ArrayList<Map<String, Object>>();
		// 机构城市产品
		boolean isAgency = user.getAgencyId() > 1;
		Map<String, String> mapCityProduct = null;
		log.info("agencyId:" + user.getAgencyId() + "isEnable:" + user.getIsEnable());
		if (isAgency && user.getIsEnable() == 0) {
			RespDataObject<Map<String, String>> respData = httpUtil.getRespDataObject(Constants.LINK_CREDIT,
					"/credit/customer/agencyProduct/searchEnabled_" + user.getAgencyId(), null, Map.class);
			if (RespStatusEnum.SUCCESS.getCode().equals(respData.getCode())) {
				mapCityProduct = respData.getData();
				log.info("cityProduct:" + mapCityProduct);
			}
		}

		// 相关所有产品
		List<Map<String, Object>> productListAll = new LinkedList<Map<String, Object>>(),
				stateListAll = new LinkedList<Map<String, Object>>();
		List<String> lstKeysPro = new LinkedList<String>(), lstKeysState = new LinkedList<String>();
		List<Map<String, Object>> productList = null, stateList = null;
		Map<String, Object> cityMap = null, productMap = null, stateMap = null;
		for (DictDto dictDto : cityListTemp) {
			if (StringUtils.isEmpty(dictDto.getPcode())) {
				// 机构已开通城市
				if (isAgency && null != mapCityProduct && !mapCityProduct.containsKey(dictDto.getCode())) {
					continue;
				}
				cityMap = new HashMap<String, Object>();
				cityMap.put("cityCode", dictDto.getCode());
				cityMap.put("cityName", dictDto.getName());

				productList = new LinkedList<Map<String, Object>>();
				for (ProductDto productDto : productListTemp) {
					if (productDto.getProductCode().equals("100")) {
						continue;
					}
					if (productDto.getCityCode().equals(dictDto.getCode())) {
						// 机构已开通城市产品
						if (isAgency && null != mapCityProduct) {
							String products = String.format(",%s,",
									MapUtils.getString(mapCityProduct, productDto.getCityCode(), "0"));
							if (!products.contains(String.format(",%s,", productDto.getProductCode()))) {
								continue;
							}
							// 机构去掉房抵贷
							if (productDto.getProductCode().equals("04")) {
								continue;
							}
						}
						productMap = new HashMap<String, Object>();
						productMap.put("productCode", productDto.getProductCode());
						productMap.put("productName", productDto.getProductName());
						productMap.put("type", productDto.getType());

						stateList = new LinkedList<Map<String, Object>>();
						for (ProductProcessDto productProcessDto : productDto.getProductProcessDtos()) {
							stateMap = new HashMap<String, Object>();
							if (productProcessDto.getProcessName().equals("已完结")) {
								stateMap.put("state", productProcessDto.getProcessName());
								stateMap.put("stateName", productProcessDto.getProcessName());
							} else {
								if (!productProcessDto.getProcessName().contains("关闭")
										&& !productProcessDto.getProcessName().contains("失败")
										&& !productProcessDto.getProcessName().contains("审批通过")
										&& !productProcessDto.getProcessName().contains("审批不通过")
										&& !productProcessDto.getProcessName().contains("已放款")
										&& !productProcessDto.getProcessName().contains("买卖双方信息")
										&& !productProcessDto.getProcessName().contains("已完结")) {
									stateMap.put("state", "待" + productProcessDto.getProcessName());
									stateMap.put("stateName", "待" + productProcessDto.getProcessName());
								} else {
									stateMap.put("state", productProcessDto.getProcessName());
									stateMap.put("stateName", productProcessDto.getProcessName());
								}
								if (productProcessDto.getProcessName().contains("买卖双方信息")) {
									stateMap.put("state", "待完善" + productProcessDto.getProcessName());
									stateMap.put("stateName", "待完善" + productProcessDto.getProcessName());
								}
							}
							if ("10000".equals(productDto.getProductCode())
									&& (productProcessDto.getProcessName().contains("审批通过")
											|| productProcessDto.getProcessName().contains("已放款"))) {

							} else {
								stateList.add(stateMap);
								if (!"-".equals(MapUtils.getString(stateMap, "state", "-"))
										&& !lstKeysState.contains(MapUtils.getString(stateMap, "state", "-"))) {
									if ("10000".equals(productDto.getProductCode())) {
										if (hasYAJ) {
											lstKeysState.add(MapUtils.getString(stateMap, "state", "-"));
											stateListAll.add(stateMap);
										}
									} else {
										lstKeysState.add(MapUtils.getString(stateMap, "state", "-"));
										stateListAll.add(stateMap);
									}
								}
							}
						}
						stateMap = new HashMap<String, Object>();
						stateMap.put("state", "");
						stateMap.put("stateName", "不限");
						stateList.add(0, stateMap);
						if (!lstKeysState.contains("")) {
							lstKeysState.add("");
							stateListAll.add(0, stateMap);
						}
						stateMap = new HashMap<String, Object>();
						stateMap.put("state", "订单已停止");
						stateMap.put("stateName", "订单已停止");
						stateList.add(stateMap);
						stateListAll.add(stateMap);
						stateMap = new HashMap<String, Object>();
						stateMap.put("state", "已关闭");
						stateMap.put("stateName", "已关闭");
						stateList.add(stateMap);
						if (!lstKeysState.contains("closeOrder")) {
							lstKeysState.add("closeOrder");
							stateListAll.add(stateMap);
						}

						if (!"01".equals(productDto.getProductCode()) && !"02".equals(productDto.getProductCode())
								&& !"03".equals(productDto.getProductCode())
								&& !"05".equals(productDto.getProductCode())
								&& !"10000".equals(productDto.getProductCode())) {
							stateMap = new HashMap<String, Object>();
							stateMap.put("state", "待还款");
							stateMap.put("stateName", "待还款");
							stateList.add(stateMap);
							if (!lstKeysState.contains("fddRepayment")) {
								lstKeysState.add("fddRepayment");
								stateListAll.add(stateMap);
							}
						}

						productMap.put("stateList", stateList);
						productList.add(productMap);
						if (!lstKeysPro.contains(productDto.getProductCode())) {
							if ("10000".equals(productDto.getProductCode())) {
								if (hasYAJ) {
									lstKeysPro.add(productDto.getProductCode());
									productListAll.add(productMap);
								}
							} else {
								lstKeysPro.add(productDto.getProductCode());
								productListAll.add(productMap);
							}
						}
					}
				}
				productMap = new HashMap<String, Object>();
				productMap.put("productCode", "");
				productMap.put("productName", "不限");
				productMap.put("type", "1");
				productMap.put("stateList", stateListAll);
				productList.add(0, productMap);
				if (!lstKeysPro.contains("")) {
					lstKeysPro.add("");
					productListAll.add(0, productMap);
				}
				cityMap.put("productList", productList);
				cityList.add(cityMap);
			}
		}
		cityMap = new HashMap<String, Object>();
		cityMap.put("cityCode", "");
		cityMap.put("cityName", "不限");
		cityMap.put("productList", productListAll);
		cityList.add(0, cityMap);
		retMap.put("citys", cityList);
		if (user.getAgencyId() == 1) {
			List<DictDto> dictDtos = getDictDtoByType("branchCompany");
			DictDto dictDto = new DictDto();
			dictDto.setCode("");
			dictDto.setName("不限");
			dictDtos.add(0, dictDto);
			retMap.put("regions", dictDtos);
		}
		retMap.put("canAdd", false);
		try {
			String[] authIds = ConfigUtil.getStringValue(Constants.BASE_PLACE_ORDER, ConfigUtil.CONFIG_BASE).split(",");
			if (isAgency || user.getAuthIds() == null) {// 机构用户或者普通用户
				retMap.put("canAdd", true);
			} else {
				for (String auth : authIds) {
					if (user.getAuthIds().contains(auth)) {
						retMap.put("canAdd", true);
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retMap;
	}

	/**
	 * 
	 * @Author
	 * @Rewrite KangLG<2018年1月12日> 新增hasYAJ参数
	 * @param user
	 * @param hasYAJ
	 *            下拉是否包含云按揭
	 * @return
	 */
	private Map<String, Object> fundAssembleBrush(UserDto user, boolean hasYAJ) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		List<DictDto> cityListTemp = getDictDtoByType("bookingSzAreaOid");
		List<ProductDto> productListTemp = getProductDtos();
		List<Map<String, Object>> cityList = new ArrayList<Map<String, Object>>();
		// 机构城市产品
		boolean isAgency = user.getAgencyId() > 1;
		Map<String, String> mapCityProduct = null;
		log.info("agencyId:" + user.getAgencyId() + "isEnable:" + user.getIsEnable());
		if (isAgency && user.getIsEnable() == 0) {
			RespDataObject<Map<String, String>> respData = httpUtil.getRespDataObject(Constants.LINK_CREDIT,
					"/credit/customer/agencyProduct/searchEnabled_" + user.getAgencyId(), null, Map.class);
			if (RespStatusEnum.SUCCESS.getCode().equals(respData.getCode())) {
				mapCityProduct = respData.getData();
				log.info("cityProduct:" + mapCityProduct);
			}
		}
		int fundId = 0;
		CustomerFundDto customerFundDto = new CustomerFundDto();
		customerFundDto = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/report/fund/v/queryFund", customerFundDto,
				CustomerFundDto.class);
		if (customerFundDto != null) {
			fundId = customerFundDto.getId();
			log.info("资方" + fundId + "查询检索条件");
		}
		// 相关所有产品
		List<Map<String, Object>> productListAll = new LinkedList<Map<String, Object>>(),
				stateListAll = new LinkedList<Map<String, Object>>();
		List<String> lstKeysPro = new LinkedList<String>(), lstKeysState = new LinkedList<String>();
		List<Map<String, Object>> productList = null, stateList = null;
		Map<String, Object> cityMap = null, productMap = null, stateMap = null;
		for (DictDto dictDto : cityListTemp) {
			if (StringUtils.isEmpty(dictDto.getPcode())) {
				// 机构已开通城市
				if (isAgency && null != mapCityProduct && !mapCityProduct.containsKey(dictDto.getCode())) {
					continue;
				}
				cityMap = new HashMap<String, Object>();
				cityMap.put("cityCode", dictDto.getCode());
				cityMap.put("cityName", dictDto.getName());

				productList = new LinkedList<Map<String, Object>>();
				for (ProductDto productDto : productListTemp) {
					if (productDto.getProductCode().equals("100")) {
						continue;
					}
					if (productDto.getCityCode().equals(dictDto.getCode())) {
						// 机构已开通城市产品
						if (isAgency && null != mapCityProduct) {
							String products = String.format(",%s,",
									MapUtils.getString(mapCityProduct, productDto.getCityCode(), "0"));
							if (!products.contains(String.format(",%s,", productDto.getProductCode()))) {
								continue;
							}
						}
						productMap = new HashMap<String, Object>();
						productMap.put("productCode", productDto.getProductCode());
						productMap.put("productName", productDto.getProductName());
						productMap.put("type", productDto.getType());

						stateList = new LinkedList<Map<String, Object>>();
						for (ProductProcessDto productProcessDto : productDto.getProductProcessDtos()) {
							stateMap = new HashMap<String, Object>();
							// 分配资金方后可查看关联信息
							boolean flag = productProcessDto.getSort() < 10;
							if (flag || (!"01".equals(productDto.getProductCode())
									&& !"02".equals(productDto.getProductCode())
									&& !"03".equals(productDto.getProductCode())
									&& !"05".equals(productDto.getProductCode()))) {
								continue;
							}
							if (fundId != 31) {
								if ("managerAudit".equals(productProcessDto.getProcessId())
										|| "fundDocking".equals(productProcessDto.getProcessId())
										|| "auditJustice".equals(productProcessDto.getProcessId())
										|| "isLendingHarvest".equals(productProcessDto.getProcessId())
										|| "isBackExpenses".equals(productProcessDto.getProcessId())
										|| "backExpenses".equals(productProcessDto.getProcessId())
										|| "receivableForFirst".equals(productProcessDto.getProcessId())
										|| "mortgage".equals(productProcessDto.getProcessId())
										|| "receivableFor".equals(productProcessDto.getProcessId())
										|| "receivableForEnd".equals(productProcessDto.getProcessId())
										|| "pay".equals(productProcessDto.getProcessId())
										|| "rebate".equals(productProcessDto.getProcessId())
										|| "lendingPay".equals(productProcessDto.getProcessId())
										|| "lendingHarvest".equals(productProcessDto.getProcessId())) {
									continue;
								}
							}
							if (productProcessDto.getProcessName().equals("已完结")) {
								stateMap.put("state", productProcessDto.getProcessName());
								stateMap.put("stateName", productProcessDto.getProcessName());
							} else {
								if (!productProcessDto.getProcessName().contains("关闭")
										&& !productProcessDto.getProcessName().contains("失败")
										&& !productProcessDto.getProcessName().contains("审批通过")
										&& !productProcessDto.getProcessName().contains("审批不通过")
										&& !productProcessDto.getProcessName().contains("已放款")
										&& !productProcessDto.getProcessName().contains("买卖双方信息")
										&& !productProcessDto.getProcessName().contains("已完结")) {
									stateMap.put("state", "待" + productProcessDto.getProcessName());
									stateMap.put("stateName", "待" + productProcessDto.getProcessName());
								} else {
									stateMap.put("state", productProcessDto.getProcessName());
									stateMap.put("stateName", productProcessDto.getProcessName());
								}
								if (productProcessDto.getProcessName().contains("买卖双方信息")) {
									stateMap.put("state", "待完善" + productProcessDto.getProcessName());
									stateMap.put("stateName", "待完善" + productProcessDto.getProcessName());
								}
							}
							if ("10000".equals(productDto.getProductCode())
									&& (productProcessDto.getProcessName().contains("审批通过")
											|| productProcessDto.getProcessName().contains("已放款"))) {

							} else {
								stateList.add(stateMap);
								if (!"-".equals(MapUtils.getString(stateMap, "state", "-"))
										&& !lstKeysState.contains(MapUtils.getString(stateMap, "state", "-"))) {
									if ("10000".equals(productDto.getProductCode())) {
										if (hasYAJ) {
											lstKeysState.add(MapUtils.getString(stateMap, "state", "-"));
											stateListAll.add(stateMap);
										}
									} else {
										lstKeysState.add(MapUtils.getString(stateMap, "state", "-"));
										stateListAll.add(stateMap);
									}
								}
							}
						}
						stateMap = new HashMap<String, Object>();
						stateMap.put("state", "");
						stateMap.put("stateName", "不限");
						stateList.add(0, stateMap);
						if (!lstKeysState.contains("")) {
							lstKeysState.add("");
							stateListAll.add(0, stateMap);
						}
						stateMap = new HashMap<String, Object>();
						stateMap.put("state", "订单已停止");
						stateMap.put("stateName", "订单已停止");
						stateList.add(stateMap);
						if (!lstKeysState.contains("closeOrder")) {
							lstKeysState.add("closeOrder");
							stateListAll.add(stateMap);
						}

						productMap.put("stateList", stateList);
						productList.add(productMap);
						if (!lstKeysPro.contains(productDto.getProductCode())) {
							if ("10000".equals(productDto.getProductCode())) {
								if (hasYAJ) {
									lstKeysPro.add(productDto.getProductCode());
									productListAll.add(productMap);
								}
							} else if ("01".equals(productDto.getProductCode())
									|| "02".equals(productDto.getProductCode())
									|| "03".equals(productDto.getProductCode())
									|| "05".equals(productDto.getProductCode())) {
								lstKeysPro.add(productDto.getProductCode());
								productListAll.add(productMap);
							}
						}
					}
				}
				productMap = new HashMap<String, Object>();
				productMap.put("productCode", "");
				productMap.put("productName", "不限");
				productMap.put("type", "1");
				productMap.put("stateList", stateListAll);
				productList.add(0, productMap);
				if (!lstKeysPro.contains("")) {
					lstKeysPro.add("");
					productListAll.add(0, productMap);
				}
				cityMap.put("productList", productList);
				cityList.add(cityMap);
			}
		}
		cityMap = new HashMap<String, Object>();
		cityMap.put("cityCode", "");
		cityMap.put("cityName", "不限");
		cityMap.put("productList", productListAll);
		cityList.add(0, cityMap);
		retMap.put("citys", cityList);
		if (user.getAgencyId() == 1) {
			List<DictDto> dictDtos = getDictDtoByType("branchCompany");
			DictDto dictDto = new DictDto();
			dictDto.setCode("");
			dictDto.setName("不限");
			dictDtos.add(0, dictDto);
			retMap.put("regions", dictDtos);
		}
		retMap.put("canAdd", false);
		try {
			String[] authIds = ConfigUtil.getStringValue(Constants.BASE_PLACE_ORDER, ConfigUtil.CONFIG_BASE).split(",");
			if (isAgency || user.getAuthIds() == null) {// 机构用户或者普通用户
				retMap.put("canAdd", true);
			} else {
				for (String auth : authIds) {
					if (user.getAuthIds().contains(auth)) {
						retMap.put("canAdd", true);
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retMap;
	}

	/**
	 * 根据orderNo查询列表(统计报表用)
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryOrderBaseList")
	public RespDataObject<List<OrderListDto>> queryOrderBaseList(HttpServletRequest request,
			@RequestBody OrderListDto orderListDto) {
		RespDataObject<List<OrderListDto>> resp = new RespDataObject<List<OrderListDto>>();
		try {
			List<OrderListDto> list = orderBaseService.selectOrderAll(orderListDto);
			RespHelper.setSuccessDataObject(resp, list);
		} catch (Exception e) {
			RespHelper.setFailDataObject(resp, null, "查询借款信息列表失败");
			e.printStackTrace();
		}
		return resp;
	}

	@ResponseBody
	@RequestMapping("/isFace")
	public RespDataObject<Integer> isFace(HttpServletRequest request, @RequestBody Map<String, Object> map) {
		RespDataObject<Integer> resp = new RespDataObject<Integer>();
		try {
			String orderNo = MapUtils.getString(map, "orderNo", "");
			int isFace = orderBaseService.selectIsFace(orderNo);
			RespHelper.setSuccessDataObject(resp, isFace);
		} catch (Exception e) {
			RespHelper.setFailDataObject(resp, null, "查询订单是否需要面签异常");
			e.printStackTrace();
		}
		return resp;
	}

	@ResponseBody
	@RequestMapping("/updateIsFace")
	public RespStatus updateIsFace(HttpServletRequest request, @RequestBody Map<String, Object> map) {
		RespStatus resp = new RespStatus();
		try {
			UserDto user = getUserDto(request);
			if (!user.getAccount().equals("lic")) {
				RespHelper.setFailRespStatus(resp, "当前账号不能更新订单是否需要面签");
				return resp;
			}
			String orderNo = MapUtils.getString(map, "orderNo", "");
			int isFace = MapUtils.getInteger(map, "isFace", -1);
			if (StringUtils.isBlank(orderNo) || isFace == -1) {
				RespHelper.setFailRespStatus(resp, "更新订单是否需要面签缺少参数");
				return resp;
			}
			orderBaseService.updateIsFace(map);
			RespHelper.setSuccessRespStatus(resp);
		} catch (Exception e) {
			RespHelper.setFailRespStatus(resp, "更新订单是否需要面签异常");
			e.printStackTrace();
		}
		return resp;
	}

	/**
	 * 放款报表
	 * 
	 * @Author KangLG<2017年11月10日>
	 * @param dto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/reportLending4Chart")
	public ChartVo reportLending4Chart(HttpServletRequest request, OrderListDto dto) {
		UserDto userDto = this.getUserDto(request);
		dto.setAgencyId(dto.getAgencyId() > 0 ? dto.getAgencyId() : userDto.getAgencyId());
		// 权限看单
		dto.setOrderNo(this.listNos4Authority(request).getData());

		return this.orderBaseService.search4reportLendingChart(dto, 12);
	}

	@ResponseBody
	@RequestMapping(value = "/reportLending")
	public RespPageData<OrderReportLendingVo> reportLending(HttpServletRequest request, @RequestBody OrderListDto dto) {
		RespPageData<OrderReportLendingVo> resp = new RespPageData<OrderReportLendingVo>();
		try {
			dto.setAgencyId(dto.getAgencyId() > 0 ? dto.getAgencyId() : this.getUserDto(request).getAgencyId());
			dto.setLendingTimeStart(DateUtils
					.dateToString(org.apache.commons.lang.time.DateUtils.addMonths(new Date(), -11), "yyyy-MM"));
			// 权限看单
			dto.setOrderNo(this.listNos4Authority(request).getData());

			resp.setRows(orderBaseService.search4reportLending(dto));
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}

	/**
	 * 智能要件同步信贷订单数据集合
	 * 
	 * @param request
	 * @param orderListDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/elementCreditOrderList")
	public RespDataObject<List<Map<String, Object>>> elementCreditOrderList(HttpServletRequest request,
			@RequestBody Map<String, Object> params) {
		RespDataObject<List<Map<String, Object>>> resp = new RespDataObject<List<Map<String, Object>>>();
		RespHelper.setFailDataObject(resp, null, RespStatusEnum.FAIL.getMsg());
		try {

			List<Map<String, Object>> orderList = orderBaseService.selectAllOrder(params);

			RespHelper.setSuccessDataObject(resp, orderList);
		} catch (Exception e) {
			e.printStackTrace();
			RespHelper.setFailDataObject(resp, null, RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}

	/**
	 * 获取信贷系统订单集合
	 * 
	 * @param request
	 * @param orderListDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectCreditOrderNos")
	public RespDataObject<Map<String, Object>> selectCreditOrderNos(HttpServletRequest request,
			@RequestBody Map<String, Object> params) {
		RespDataObject<Map<String, Object>> resp = new RespDataObject<Map<String, Object>>();
		RespHelper.setFailDataObject(resp, null, RespStatusEnum.FAIL.getMsg());
		try {

			String orderNos = orderFlowService.selectOrderNoByUid(MapUtils.getString(params, "uid"));
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("orderNos", orderNos);
			RespHelper.setSuccessDataObject(resp, map);
		} catch (Exception e) {
			e.printStackTrace();
			RespHelper.setFailDataObject(resp, null, RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}

	/**
	 * 返回示例订单
	 * 
	 * @return
	 */
	public List<Map<String, Object>> exampleOrder() {
		// 2016112415164100017,2016102510124200000,2017110714552200049
		List<Map<String, Object>> exampleOrder = new ArrayList<Map<String, Object>>();
		Map<String, Object> tempMap = new HashMap<String, Object>();
		tempMap.put("isExample", "1");
		tempMap.put("orderNo", "2016112415164100017");
		tempMap.put("appShowKeyEnd", "受理员/经理/机构");
		tempMap.put("borrowingTermUnit", "天");
		tempMap.put("customerName", "张立详");
		tempMap.put("cityName", "深圳");
		tempMap.put("cityCode", "4403");
		tempMap.put("productCode", "01");
		tempMap.put("productName", "");
		tempMap.put("productNamePrefix", "置换贷款");
		tempMap.put("productNameSuffix", "交易类");
		tempMap.put("borrowingAmount", "450");
		tempMap.put("borrowingDay", "25");
		tempMap.put("channelManagerName", "何佳");
		tempMap.put("cooperativeAgencyName", "晒晒机构");
		tempMap.put("acceptMemberName", "何佳");
		tempMap.put("state", "已完结");
		tempMap.put("currentHandler", "何佳");
		tempMap.put("processId", "wanjie");
		exampleOrder.add(tempMap);
		// 非交易类
		tempMap = new HashMap<String, Object>();
		tempMap.put("isExample", "1");
		tempMap.put("orderNo", "2016102510124200000");
		tempMap.put("appShowKeyEnd", "受理员/经理/机构");
		tempMap.put("borrowingTermUnit", "天");
		tempMap.put("customerName", "秦程");
		tempMap.put("cityName", "深圳");
		tempMap.put("cityCode", "4403");
		tempMap.put("productCode", "02");
		tempMap.put("productName", "");
		tempMap.put("productNamePrefix", "置换贷款");
		tempMap.put("productNameSuffix", "非交易类");
		tempMap.put("borrowingAmount", "400");
		tempMap.put("borrowingDay", "45");
		tempMap.put("channelManagerName", "何佳");
		tempMap.put("cooperativeAgencyName", "晒晒机构");
		tempMap.put("acceptMemberName", "何佳");
		tempMap.put("state", "已完结");
		tempMap.put("currentHandler", "何佳");
		tempMap.put("processId", "wanjie");
		exampleOrder.add(tempMap);
		// 云按揭
		tempMap = new HashMap<String, Object>();
		tempMap.put("isExample", "1");
		tempMap.put("orderNo", "2017110714552200049");
		tempMap.put("appShowKeyEnd", "提单人/经理/机构");
		tempMap.put("borrowingTermUnit", "月");
		tempMap.put("customerName", "陈春燕");
		tempMap.put("cityName", "深圳");
		tempMap.put("cityCode", "4403");
		tempMap.put("productCode", "10000");
		tempMap.put("productName", "云按揭");
		tempMap.put("productNamePrefix", "云按揭");
		tempMap.put("borrowingAmount", "300");
		tempMap.put("borrowingDay", "250");
		tempMap.put("channelManagerName", "陈飞");
		tempMap.put("cooperativeAgencyName", "晒晒机构");
		tempMap.put("acceptMemberName", "陈飞");
		tempMap.put("appShowKey1", "完成时间");
		tempMap.put("appShowValue1", "2018-01-05 14:00:00");
		tempMap.put("state", "已完结");
		tempMap.put("currentHandler", "陈飞");
		tempMap.put("processId", "wanjie");
		exampleOrder.add(tempMap);
		return exampleOrder;
	}

	@ResponseBody
	@RequestMapping("/getOrderLoan")
	public RespDataObject<OrderListDto> getOrderLoan(HttpServletRequest request, @RequestBody Map<String, Object> map) {
		RespDataObject<OrderListDto> resp = new RespDataObject<OrderListDto>();
		RespHelper.setFailDataObject(resp, null, RespStatusEnum.FAIL.getMsg());
		try {
			if (!map.containsKey("cooperativeAgencyId") || null == MapUtils.getInteger(map, "cooperativeAgencyId")) {
				return resp;
			}
			String processId = ConfigUtil.getStringValue(Constants.BASE_FINANCE_LOAN_PROCESS, ConfigUtil.CONFIG_BASE);
			if (StringUtils.isBlank(processId)) {
				resp.setMsg("没有获取到配置文件节点信息");
				return resp;
			}
			String[] arr = processId.split(",");
			String where = "";
			for (String s : arr) {
				if (StringUtils.isBlank(where)) {
					where = "processId='" + s + "'";
				} else {
					where += " OR processId='" + s + "'";
				}
			}
			map.put("whereProcess", where);
			OrderListDto order = orderBaseService.selectAgencyLoan(map);
			RespHelper.setSuccessDataObject(resp, order);
		} catch (Exception e) {
			log.error("查询机构放款放款统计异常", e);
		}
		return resp;
	}

	/**
	 * App风控节点详情
	 * 
	 * @param request
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/appRiskDetailByOrderNo")
	public RespDataObject<Map<String, Object>> appRiskDetail(HttpServletRequest request,
			@RequestBody Map<String, Object> params) {
		RespDataObject<Map<String, Object>> resp = new RespDataObject<Map<String, Object>>();
		try {
			String orderNo = MapUtils.getString(params, "orderNo", "");
			// String processId = MapUtils.getString(params, "processId","");
			if ("".equals(orderNo)) {
				RespHelper.setFailDataObject(resp, null, RespStatusEnum.PARAMETER_ERROR.getMsg());
				return resp;
			}
			RespHelper.setSuccessDataObject(resp, appRiskDetailByOrderNo(orderNo));
		} catch (Exception e) {
			e.printStackTrace();
			RespHelper.setFailDataObject(resp, null, RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}

	public Map<String, Object> appRiskDetailByOrderNo(String orderNo) {
		String processId = "auditFirst,auditFinal,auditOfficer,auditJustice";
		Map<String, Object> returnMap = new HashMap<String, Object>();
		if (processId.contains("auditFirst")) {
			Map<String, Object> retMap = new HashMap<String, Object>();
			FirstAuditDto firstAuditDto = new FirstAuditDto();
			firstAuditDto.setOrderNo(orderNo);
			firstAuditDto = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/risk/first/v/appDetail", firstAuditDto,
					FirstAuditDto.class);

			Map<String, Object> upperMap = new LinkedHashMap<String, Object>();
			String handleName = "-";
			if (StringUtils.isNotBlank(firstAuditDto.getHandleName())) {
				handleName = firstAuditDto.getHandleName();
			}
			upperMap.put("初审人", handleName);

			upperMap.put("初审时间", DateUtil.getDateByFmt(firstAuditDto.getAuditTime(), DateUtil.FMT_TYPE2));
			String auditStatus = "-";
			if (firstAuditDto.getAuditStatus() == 1) {
				auditStatus = "通过";
			} else if (firstAuditDto.getAuditStatus() == 2) {
				auditStatus = "退回";
			} else if (firstAuditDto.getAuditStatus() == 3) {
				auditStatus = "上报终审";
			}
			upperMap.put("初审结果", auditStatus);
			upperMap.put("keys", "初审人,初审时间,初审结果");

			Map<String, Object> downMap = new LinkedHashMap<String, Object>();
			if (StringUtils.isNotBlank(firstAuditDto.getRemark())) {
				downMap.put("初审意见", firstAuditDto.getRemark());
			}

			retMap.put("upper", upperMap);
			retMap.put("down", downMap);

			if (handleName.equals("-")) {
				returnMap.put("first", null);
			} else {
				returnMap.put("first", retMap);
			}

		}
		if (processId.contains("auditFinal")) {
			Map<String, Object> retMap = new HashMap<String, Object>();
			FinalAuditDto finalAuditDto = new FinalAuditDto();
			finalAuditDto.setOrderNo(orderNo);
			finalAuditDto = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/risk/final/v/detail", finalAuditDto,
					FinalAuditDto.class);

			Map<String, Object> upperMap = new LinkedHashMap<String, Object>();

			String handleName = "-";
			if (StringUtils.isNotBlank(finalAuditDto.getHandleName())) {
				handleName = finalAuditDto.getHandleName();
			}
			upperMap.put("终审人", handleName);

			upperMap.put("终审时间", DateUtil.getDateByFmt(finalAuditDto.getAuditTime(), DateUtil.FMT_TYPE2));
			String auditStatus = "-";
			if (finalAuditDto.getAuditStatus() == 1) {
				auditStatus = "通过";
			} else if (finalAuditDto.getAuditStatus() == 2) {
				auditStatus = "未通过";
			} else if (finalAuditDto.getAuditStatus() == 3) {
				auditStatus = "上报首席风险官";
			}
			upperMap.put("终审结果", auditStatus);

			upperMap.put("keys", "终审人,终审时间,终审结果");

			Map<String, Object> downMap = new LinkedHashMap<String, Object>();
			if (StringUtils.isNotBlank(finalAuditDto.getRemark())) {
				downMap.put("终审意见", finalAuditDto.getRemark());
			}

			retMap.put("upper", upperMap);
			retMap.put("down", downMap);
			if (handleName.equals("-")) {
				returnMap.put("final", null);
			} else {
				returnMap.put("final", retMap);
			}
		}
		if ("auditReview".equals(processId)) {
			Map<String, Object> retMap = new HashMap<String, Object>();
			ReviewAuditDto reviewAuditDto = new ReviewAuditDto();
			reviewAuditDto.setOrderNo(orderNo);
			reviewAuditDto = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/risk/review/v/detail", reviewAuditDto,
					ReviewAuditDto.class);

			Map<String, Object> upperMap = new LinkedHashMap<String, Object>();

			String handleName = "-";
			if (StringUtils.isNotBlank(reviewAuditDto.getHandleName())) {
				handleName = reviewAuditDto.getHandleName();
			}
			upperMap.put("复核审批人", handleName);

			upperMap.put("复核审批时间", DateUtil.getDateByFmt(reviewAuditDto.getAuditTime(), DateUtil.FMT_TYPE2));
			String auditStatus = "-";
			if (reviewAuditDto.getAuditStatus() == 1) {
				auditStatus = "通过";
			} else if (reviewAuditDto.getAuditStatus() == 2) {
				auditStatus = "未通过";
			} else if (reviewAuditDto.getAuditStatus() == 3) {
				auditStatus = "上报首席风险官";
			}
			upperMap.put("复核审批结果", auditStatus);
			upperMap.put("借款金额", reviewAuditDto.getLoanAmont() + "万");
			upperMap.put("借款期限", reviewAuditDto.getBorrowingDays() + "天");
			upperMap.put("建议费率", reviewAuditDto.getRate() + "%/天");
			upperMap.put("建议逾期费率", reviewAuditDto.getOverdueRate() + "%/天");
			String keys = "复核审批人,复核审批时间,复核审批结果,借款金额,借款期限,建议费率,建议逾期费率";
			upperMap.put("keys", keys);

			Map<String, Object> downMap = new LinkedHashMap<String, Object>();
			if (StringUtils.isNotBlank(reviewAuditDto.getRemark())) {
				downMap.put("复核审批意见", reviewAuditDto.getRemark());
			}

			retMap.put("upper", upperMap);
			retMap.put("down", downMap);
			if (handleName.equals("-")) {
				returnMap.put("review", null);
			} else {
				returnMap.put("review", retMap);
			}
		}
		if (processId.contains("auditOfficer")) {
			Map<String, Object> retMap = new HashMap<String, Object>();
			OfficerAuditDto officerAuditDto = new OfficerAuditDto();
			officerAuditDto.setOrderNo(orderNo);
			officerAuditDto = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/risk/officer/v/detail",
					officerAuditDto, OfficerAuditDto.class);

			Map<String, Object> upperMap = new LinkedHashMap<String, Object>();
			String handleName = "-";
			if (StringUtils.isNotBlank(officerAuditDto.getHandleName())) {
				handleName = officerAuditDto.getHandleName();
			}
			upperMap.put("首席风险官", handleName);
			upperMap.put("审批时间", DateUtil.getDateByFmt(officerAuditDto.getAuditTime(), DateUtil.FMT_TYPE2));
			String auditStatus = "-";
			if (officerAuditDto.getAuditStatus() == 1) {
				auditStatus = "通过";
			} else if (officerAuditDto.getAuditStatus() == 2) {
				auditStatus = "未通过";
			}
			upperMap.put("审批结果", auditStatus);

			upperMap.put("keys", "首席风险官,审批时间,审批结果");

			Map<String, Object> downMap = new LinkedHashMap<String, Object>();
			if (StringUtils.isNotBlank(officerAuditDto.getRemark())) {
				downMap.put("首席风险官意见", officerAuditDto.getRemark());
			}

			retMap.put("upper", upperMap);
			retMap.put("down", downMap);

			if (handleName.equals("-")) {
				returnMap.put("officer", null);
			} else {
				returnMap.put("officer", retMap);
			}
		}
		if (processId.contains("auditJustice")) {
			Map<String, Object> retMap = new HashMap<String, Object>();
			JusticeAuditDto justiceAuditDto = new JusticeAuditDto();
			justiceAuditDto.setOrderNo(orderNo);
			justiceAuditDto = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/risk/justice/v/detail",
					justiceAuditDto, JusticeAuditDto.class);

			Map<String, Object> upperMap = new LinkedHashMap<String, Object>();
			String handleName = "-";
			if (StringUtils.isNotBlank(justiceAuditDto.getHandleName())) {
				handleName = justiceAuditDto.getHandleName();
			}
			upperMap.put("法务", handleName);
			upperMap.put("审批时间", DateUtil.getDateByFmt(justiceAuditDto.getAuditTime(), DateUtil.FMT_TYPE2));
			String auditStatus = "-";
			if (justiceAuditDto.getAuditStatus() == 1) {
				auditStatus = "通过";
			} else if (justiceAuditDto.getAuditStatus() == 2) {
				auditStatus = "未通过";
			}
			upperMap.put("审批结果", auditStatus);

			upperMap.put("keys", "法务,审批时间,审批结果");

			Map<String, Object> downMap = new LinkedHashMap<String, Object>();
			if (StringUtils.isNotBlank(justiceAuditDto.getRemark())) {
				downMap.put("法务意见", justiceAuditDto.getRemark());
			}

			retMap.put("upper", upperMap);
			retMap.put("down", downMap);

			if (handleName.equals("-")) {
				returnMap.put("justice", null);
			} else {
				returnMap.put("justice", retMap);
			}
		}
		return returnMap;
	}

	/**
	 * 管理系统查所有订单，修改处理人和节点
	 */
	@ResponseBody
	@RequestMapping(value = "/smOrderList")
	public RespPageData<OrderListDto> smOrderList(HttpServletRequest request, @RequestBody OrderListDto orderListDto) {
		RespPageData<OrderListDto> resp = new RespPageData<OrderListDto>();
		resp.setCode(RespStatusEnum.FAIL.getCode());
		resp.setMsg(RespStatusEnum.FAIL.getMsg());
		try {
			UserDto userDto = getUserDto(request);
			String deptAllUid = "";
			orderListDto.setCurrentHandlerUid(deptAllUid);
			orderListDto.setUpdateUid(userDto.getUid());
			resp.setTotal(orderBaseService.selectOrderCount(orderListDto));
			resp.setRows(orderBaseService.selectOrderList(orderListDto));
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}

	/**
	 * 返回产品节点
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectProductProcessId")
	public RespDataObject<List<ProductProcessDto>> selectProductProcessId(HttpServletRequest request,
			@RequestBody OrderListDto orderListDto) {
		RespDataObject<List<ProductProcessDto>> resp = new RespDataObject<List<ProductProcessDto>>();
		try {
			int productId = Integer.parseInt(orderListDto.getCityCode() + orderListDto.getProductCode());
			List<ProductProcessDto> productProcessTemp = getProductProcessDto(productId);
			for (ProductProcessDto productProcessDto : productProcessTemp) {
				if (!"已完结".equals(productProcessDto.getProcessName())) {
					productProcessDto.setProcessName("待" + productProcessDto.getProcessName());
				}
			}
			RespHelper.setSuccessDataObject(resp, productProcessTemp);
		} catch (Exception e) {
			e.printStackTrace();
			RespHelper.setFailDataObject(resp, null, RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}

	/**
	 * 返回所有用户
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getAllUserList")
	public RespDataObject<List<UserDto>> getAllUserDtoList(HttpServletRequest request) {
		RespDataObject<List<UserDto>> resp = new RespDataObject<List<UserDto>>();
		try {
			List<UserDto> tempList = new ArrayList<UserDto>();
			List<UserDto> userList = getAllUserDtoList();
			UserDto user = new UserDto();
			for (int i = 0; i < userList.size(); i++) {
				if (0 == userList.get(i).getIsEnable()) {
					user.setUid(userList.get(i).getUid());
					user.setName(userList.get(i).getName());
					tempList.add(user);
				}
			}
			RespHelper.setSuccessDataObject(resp, userList);
		} catch (Exception e) {
			e.printStackTrace();
			RespHelper.setFailDataObject(resp, null, RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}

	/**
	 * 查询用户某个节点待处理的订单数量
	 * 
	 * @param request
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectUidOrderCount")
	public RespDataObject<Map<String, Object>> selectUidOrderCount(HttpServletRequest request,
			@RequestBody Map<String, Object> map) {
		RespDataObject<Map<String, Object>> resp = new RespDataObject<Map<String, Object>>();
		try {
			List<Map<String, Object>> list = orderBaseService.selectUidOrderCount(map);
			Map<String, Object> rMap = new HashMap<String, Object>();
			if (list != null && list.size() > 0) {
				for (Map<String, Object> map2 : list) {
					rMap.put(MapUtils.getString(map2, "currentHandlerUid"), MapUtils.getString(map2, "count"));
				}
			}
			RespHelper.setSuccessDataObject(resp, rMap);
		} catch (Exception e) {
			e.printStackTrace();
			RespHelper.setFailDataObject(resp, null, RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}

	@ResponseBody
	@RequestMapping(value = "/selectOrderListByContractNo")
	public RespDataObject<OrderListDto> selectOrderListByContractNo(@RequestBody OrderListDto orderListDto) {
		RespDataObject<OrderListDto> resp = new RespDataObject<OrderListDto>();
		try {
			RespHelper.setSuccessDataObject(resp, orderBaseService.selectOrderListByContractNo(orderListDto));
		} catch (Exception e) {
			e.printStackTrace();
			RespHelper.setFailDataObject(resp, null, RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}

	// 将时间戳转为字符串
	public String getStrTime(String cc_time) {
		long lTime = Long.parseLong(cc_time); // int放不下的，用long
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sLastTime = sdf.format(lTime);
		return sLastTime;
	}
}
