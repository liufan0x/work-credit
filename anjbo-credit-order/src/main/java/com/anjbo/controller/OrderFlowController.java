package com.anjbo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.anjbo.bean.order.OrderFlowDto;
import com.anjbo.bean.order.OrderListDto;
import com.anjbo.bean.product.DistributionMemberDto;
import com.anjbo.bean.product.ProductProcessDto;
import com.anjbo.bean.report.StatisticsDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.Constants;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.OrderBaseService;
import com.anjbo.service.OrderFlowService;
import com.anjbo.utils.AmsUtil;
import com.anjbo.utils.CommonDataUtil;
import com.anjbo.utils.ConfigUtil;

@Controller
@RequestMapping("/credit/order/flow/v")
public class OrderFlowController extends BaseController {
	@Resource
	private OrderFlowService orderFlowService;
	@Resource
	private OrderBaseService orderBaseService;

	/**
	 * 查询订单流水(有重复的)
	 * 
	 * @param request
	 * @param orderFlowDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectOrderFlowListRepeat")
	public RespData<OrderFlowDto> selectOrderFlowListRepeat(HttpServletRequest request,
			@RequestBody OrderFlowDto orderFlowDto) {
		RespData<OrderFlowDto> resp = new RespData<OrderFlowDto>();
		UserDto userSession = getUserDto(request);
		try {

			// OrderListDto orderListDto =
			// orderBaseService.selectDetail(orderFlowDto.getOrderNo());
			Map<String, Object> orderBaseInfo = getOrderBaseInfo(orderFlowDto.getOrderNo());
			String productId = "440301";
			if (orderBaseInfo != null) {
				productId = MapUtils.getString(orderBaseInfo, "cityCode")
						+ MapUtils.getString(orderBaseInfo, "productCode");
			}

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("productId", productId);
			System.out.println(params);
			Map<String, Object> authMap = httpUtil.getObject(Constants.LINK_CREDIT,
					"/credit/data/api/v/selectAuthorityByProductId", params, Map.class);
			List<String> authList = userSession.getAuthIds();

			// 展示数据重新组装
			List<OrderFlowDto> list = orderFlowService.selectOrderFlowList(orderFlowDto.getOrderNo());
			OrderFlowDto orderFlow = null;
			Iterator<OrderFlowDto> its = list.iterator();

			List<ProductProcessDto> productProcessDtoList = CommonDataUtil.getProductProcessDto(productId);

			while (its.hasNext()) {
				orderFlow = its.next();
				try {

					for (ProductProcessDto productProcessDto : productProcessDtoList) {
						if (orderFlow.getCurrentProcessId().equals(productProcessDto.getProcessId())) {
							orderFlow.setCurrentProcessName(productProcessDto.getProcessName());
						}
						if (orderFlow.getNextProcessId().equals(productProcessDto.getProcessId())) {
							orderFlow.setNextProcessName(productProcessDto.getProcessName());
						}
					}

					orderFlow
							.setHandleName(CommonDataUtil.getUserDtoByUidAndMobile(orderFlow.getHandleUid()).getName());

					if (orderFlow.getCurrentProcessId().equals("wanjie")) {
						continue;
					}
					// 删除待还款节点
					if (orderFlow.getCurrentProcessId().equals("fddRepayment")) {
						its.remove();
					}
					String authA = MapUtils.getString(authMap, orderFlow.getCurrentProcessId() + "[A]");
					String authB = MapUtils.getString(authMap, orderFlow.getCurrentProcessId() + "[B]");
					if (!(authList.contains(authA + "") || authList.contains(authB + ""))) {
						if ("customerReceivable".equals(orderFlow.getCurrentProcessId())) {
							orderFlow.setKey(orderFlow.getCurrentProcessId());
							orderFlow.setCurrentProcessName("客户回款");
							orderFlow.setCurrentProcessId("customerReceivable");
						} else {
							orderFlow.setKey(orderFlow.getCurrentProcessId());
							orderFlow.setCurrentProcessId("noAuth");
						}
					}
				} catch (Exception e) {
					this.logger.error(orderFlow.getHandleUid());
					e.printStackTrace();
				}
				// 机构节点重构
				if (userSession.getAgencyId() > 1) {
					if (ArrayUtils.contains(OrderFlowDto.arrayRiskName, orderFlow.getCurrentProcessName())) {
						this.logger.info(orderFlow.getCurrentProcessName() + "##" + orderFlow.getNextProcessName());
						if (ArrayUtils.contains(OrderFlowDto.arrayRiskName, orderFlow.getNextProcessName())) {
							its.remove();
							continue;
						}
						this.logger.info("to noAuth!");
						orderFlow.setCurrentProcessId("noAuth");
						orderFlow.setCurrentProcessName(OrderFlowDto.ORDER_NODE_RISK);
					}
					// 节点及节点详情展示控制
					if (ArrayUtils.contains(OrderFlowDto.arrayNodeWithout, orderFlow.getKey())) {
						its.remove();
						continue;
					} else if (ArrayUtils.contains(OrderFlowDto.arrayNodeDetailWithout,
							orderFlow.getCurrentProcessId())) {
						orderFlow.setCurrentProcessId("");
					} else if (StringUtils.isNotEmpty(orderFlow.getKey())
							&& ArrayUtils.contains(OrderFlowDto.arrayNodeDetailNeed, orderFlow.getKey())) {
						orderFlow.setCurrentProcessId(orderFlow.getKey());
					}
				}
			}
			RespHelper.setSuccessData(resp, list);
		} catch (Exception e) {
			e.printStackTrace();
			RespHelper.setFailData(resp, null, RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}

	/**
	 * 查询订单流水(无重复的)
	 * 
	 * @param request
	 * @param orderFlowDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectOrderFlowList")
	public RespData<OrderFlowDto> selectOrderFlowList(HttpServletRequest request,
			@RequestBody OrderFlowDto orderFlowDto) {
		RespData<OrderFlowDto> resp = new RespData<OrderFlowDto>();
		try {

			Map<String, Object> orderBaseInfo = getOrderBaseInfo(orderFlowDto.getOrderNo());
			String productId = "440301";
			if (orderBaseInfo != null) {
				productId = MapUtils.getString(orderBaseInfo, "cityCode")
						+ MapUtils.getString(orderBaseInfo, "productCode");
			}

			List<OrderFlowDto> list = orderFlowService.selectOrderFlowList(orderFlowDto.getOrderNo());
			List<OrderFlowDto> tempList = new ArrayList<OrderFlowDto>();
			List<ProductProcessDto> productProcessDtoList = CommonDataUtil.getProductProcessDto(productId);
			boolean fl1 = false;
			boolean fl2 = false;
			for (int i = 0; i < list.size(); i++) {
				OrderFlowDto orderFlow = list.get(i);

				for (ProductProcessDto productProcessDto : productProcessDtoList) {
					if (orderFlow.getCurrentProcessId().equals(productProcessDto.getProcessId())) {
						orderFlow.setCurrentProcessName(productProcessDto.getProcessName());
					}
					if (orderFlow.getNextProcessId().equals(productProcessDto.getProcessId())) {
						orderFlow.setNextProcessName(productProcessDto.getProcessName());
					}
				}

				orderFlow.setHandleName(CommonDataUtil.getUserDtoByUidAndMobile(orderFlow.getHandleUid()).getName());

				// System.out.println(orderFlow.getCurrentProcessId()+":"+tempList.size());
				// for (OrderFlowDto orderFlowDto2 : tempList) {
				// System.out.print(orderFlowDto2.getCurrentProcessId()+",");
				// }
				// System.out.println("");

				if (!fl2) {
					tempList.add(orderFlow);
				} else {
					fl2 = false;
					continue;
				}
				fl1 = false;
				if (StringUtils.isNotEmpty(orderFlow.getBackReason())) {

					if (orderFlow.getIsNewWalkProcess() == 1) {
						Iterator<OrderFlowDto> flowList = tempList.iterator();
						while (flowList.hasNext()) {
							OrderFlowDto temp = flowList.next();
							if (temp.getCurrentProcessId().equals(orderFlow.getNextProcessId())) {
								fl1 = true;
							}
							if (fl1) {
								flowList.remove();
							}
						}
					} else {
						for (OrderFlowDto temp : tempList) {
							if (temp.getCurrentProcessId().equals(orderFlow.getNextProcessId())) {
								temp = orderFlow;
								fl2 = true;
							}
						}
						tempList.remove(orderFlow);
					}
				}

				// if(!fl) {
				// if(!tempMap.containsKey(orderFlow.getCurrentProcessId())) {
				// tempMap.put(orderFlow.getCurrentProcessId(), 0);
				//
				// }
				// }

				// if(fl && orderFlow.getCurrentProcessId().equals("placeOrder")){
				// for (int i = 0; i < tempList.size(); i++) {
				// if(tempList.get(i).getCurrentProcessId().equals("placeOrder")){
				// tempList.set(i, orderFlow);
				// }
				// }
				// }else{
				// tempList.add(orderFlow);
				// }
				// if(StringUtils.isNotEmpty(orderFlow.getBackReason())){
				// if(orderFlow.getNextProcessId().equals("placeOrder")){
				// tempList.remove(orderFlow);
				// fl = true;
				// }else{
				// List<OrderFlowDto> tempRemoveList = new ArrayList<OrderFlowDto>();
				// boolean removeFl = true;
				// for (int i = 0; i < tempList.size(); i++) {
				// if(tempList.get(i).getCurrentProcessId().equals(orderFlow.getNextProcessId())){
				// removeFl = false;
				// }
				// if(removeFl){
				// tempRemoveList.add(tempList.get(i));
				// }
				// }
				// tempList = new ArrayList<OrderFlowDto>();
				// tempList.addAll(tempRemoveList);
				// }
				// }
			}

			RespHelper.setSuccessData(resp, tempList);
		} catch (Exception e) {
			e.printStackTrace();
			RespHelper.setFailData(resp, null, RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}

	/**
	 * 查询最后一条订单流水
	 * 
	 * @param request
	 * @param orderFlowDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectEndOrderFlow")
	public RespDataObject<OrderFlowDto> selectEndOrderFlow(HttpServletRequest request,
			@RequestBody OrderFlowDto orderFlowDto) {
		RespDataObject<OrderFlowDto> resp = new RespDataObject<OrderFlowDto>();
		try {
			if (orderFlowDto != null && "".equals(orderFlowDto.getOrderNo())) {
				RespHelper.setFailRespStatus(resp, RespStatusEnum.PARAMETER_ERROR.getMsg());
				return resp;
			}
			orderFlowDto = orderFlowService.selectEndOrderFlow(orderFlowDto);
			RespHelper.setSuccessDataObject(resp, orderFlowDto);
		} catch (Exception e) {
			e.printStackTrace();
			RespHelper.setFailRespStatus(resp, RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}

	/**
	 * 新增流水
	 * 
	 * @param request
	 * @param orderFlowDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addOrderFlow")
	public RespStatus addOrderFlow(HttpServletRequest request, @RequestBody OrderFlowDto orderFlowDto) {
		RespStatus resp = new RespStatus();
		try {
			if (orderFlowDto == null || "".equals(orderFlowDto.getOrderNo()) || "".equals(orderFlowDto.getUpdateUid())
					|| "".equals(orderFlowDto.getCurrentProcessId()) || "".equals(orderFlowDto.getNextProcessId())
					|| "".equals(orderFlowDto.getHandleUid())) {
				RespHelper.setFailRespStatus(resp, RespStatusEnum.PARAMETER_ERROR.getMsg());
				return resp;
			}
			orderFlowService.addOrderFlow(orderFlowDto);

			try {
				String processIds = "managerAudit,auditFirst,notarization,facesign,auditFinal,auditOfficer,repaymentMember,element,foreclosure,forensics,cancellation,transfer,newlicense,mortgage,applyLoan,elementReturn,auditReview,financialAudit,financialStatement";
				if (processIds.contains(orderFlowDto.getNextProcessId())) {
					OrderListDto orderListDto = orderBaseService.selectDetail(orderFlowDto.getOrderNo());
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("uid", orderListDto.getCurrentHandlerUid());
					String state = orderListDto.getState();
					if(StringUtils.isNotEmpty(state)) {
						state = state.replaceAll("待", "");
					}
					map.put("message", "您有1条新订单【客户姓名："+orderListDto.getCustomerName()+"】需要进行["+state+"]，请查看！");
					httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/third/api/umeng/v/pushText", map);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			RespHelper.setSuccessRespStatus(resp);
		} catch (Exception e) {
			e.printStackTrace();
			RespHelper.setFailRespStatus(resp, RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}

	/**
	 * 撤回订单
	 * 
	 * @param request
	 * @param orderFlowDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/withdrawOrder")
	public RespStatus withdrawOrder(HttpServletRequest request, @RequestBody Map<String, Object> params) {
		RespStatus resp = new RespStatus();
		try {
			String orderNo = MapUtils.getString(params, "orderNo");
			if ("".equals(orderNo)) {
				RespHelper.setFailRespStatus(resp, RespStatusEnum.PARAMETER_ERROR.getMsg());
				return resp;
			}
			orderFlowService.withdrawOrder(orderNo);
			OrderListDto order = orderBaseService.selectDetail(orderNo);
			// String processId = MapUtils.getString(params, "processId");
			String customerName = order.getCustomerName();
			String borrowingAmount = order.getBorrowingAmount().toString();
			String productName = order.getProductName();
			String acceptMemberUid = order.getAcceptMemberUid();
			if (!isWithdraw(orderNo, "auditFinal")) {
				try {
					String ipWhite = ConfigUtil.getStringValue(Constants.BASE_AMS_IPWHITE, ConfigUtil.CONFIG_BASE);
					AmsUtil.smsSend(CommonDataUtil.getUserDtoByUidAndMobile(acceptMemberUid).getMobile(), ipWhite,
							Constants.SMS_AUDIT_FINAL_WITHDRAW_ORDER,
							productName.replaceAll("贷款（交易类）", "").replaceAll("贷款（非交易类）", ""), customerName,
							borrowingAmount, getUserDto(request).getName(), "风控终审");
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (!isWithdraw(orderNo, "auditOfficer")) {
				try {
					String ipWhite = ConfigUtil.getStringValue(Constants.BASE_AMS_IPWHITE, ConfigUtil.CONFIG_BASE);
					AmsUtil.smsSend(CommonDataUtil.getUserDtoByUidAndMobile(acceptMemberUid).getMobile(), ipWhite,
							Constants.SMS_AUDIT_FINAL_WITHDRAW_ORDER,
							productName.replaceAll("贷款（交易类）", "").replaceAll("贷款（非交易类）", ""), customerName,
							borrowingAmount, getUserDto(request).getName(), "首席风险官审批");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			// 同步数据到智能要件
			try {
				OrderListDto orderListDto = new OrderListDto();
				orderListDto = orderBaseService.selectDetail(orderNo);

				logger.info("执行了baseControl同步信贷件订单到智能要件");
				RespStatus elementResp = httpUtil.getRespStatus(Constants.LINK_CREDIT,
						"/credit/element/list/saveCredit", orderListDto);
			} catch (Exception e) {
				e.printStackTrace();
			}

			// orderFlowService.deleteOrderFlow(orderFlowDto);
			// OrderListDto orderListDto = new OrderListDto();
			// orderListDto = orderBaseService.selectDetail(orderFlowDto.getOrderNo());
			// orderListDto.setCurrentHandler(getUserDto(request).getName());
			// orderListDto.setCurrentHandlerUid(getUserDto(request).getUid());
			// List<OrderFlowDto> list =
			// orderFlowService.selectOrderFlowList(orderFlowDto.getOrderNo());
			// if(list.size() > 0){
			// OrderFlowDto orderFlow= list.get(list.size()-1);
			// orderListDto.setProcessId(orderFlow.getCurrentProcessId());
			// orderListDto.setState("待"+orderFlow.getCurrentProcessName());
			// }
			// orderListDto.setProcessId(orderListDto.getp);
			// orderBaseService.updateOrderList(orderListDto);
			RespHelper.setSuccessRespStatus(resp);
		} catch (Exception e) {
			e.printStackTrace();
			RespHelper.setFailRespStatus(resp, RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}

	/**
	 * 退回订单
	 * 
	 * @param request
	 * @param orderFlowDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/backOrder")
	public RespStatus backOrder(HttpServletRequest request, @RequestBody OrderFlowDto orderFlowDto) {
		RespStatus resp = new RespStatus();
		try {
			if ("".equals(orderFlowDto.getOrderNo()) || "".equals(orderFlowDto.getBackReason())
					|| "".equals(orderFlowDto.getCurrentProcessId()) || "".equals(orderFlowDto.getCurrentProcessName())
					|| "".equals(orderFlowDto.getNextProcessId()) || "".equals(orderFlowDto.getNextProcessName())
					|| "".equals(orderFlowDto.getHandleUid()) || "".equals(orderFlowDto.getHandleName())
					|| "".equals(orderFlowDto.getReturnType())) {
				RespHelper.setFailRespStatus(resp, RespStatusEnum.PARAMETER_ERROR.getMsg());
				return resp;
			}
			UserDto userDto = getUserDto(request);
			orderFlowDto.setUpdateUid(userDto.getUid());

			OrderListDto orderListDto = new OrderListDto();
			orderListDto.setUpdateUid(userDto.getUid());
			orderListDto.setOrderNo(orderFlowDto.getOrderNo());
			orderListDto.setProcessId(orderFlowDto.getNextProcessId());
			orderListDto.setPreviousHandler(userDto.getName());
			orderListDto.setCurrentHandlerUid(orderFlowDto.getHandleUid());
			orderListDto.setCurrentHandler(orderFlowDto.getHandleName());
			orderListDto.setState(
					"【" + orderFlowDto.getCurrentProcessName() + "】退回【" + orderFlowDto.getNextProcessName() + "】");
			String ipWhite = ConfigUtil.getStringValue(Constants.BASE_AMS_IPWHITE, ConfigUtil.CONFIG_BASE);
			OrderListDto tempDto = orderBaseService.selectDetail(orderFlowDto.getOrderNo());
			UserDto user = CommonDataUtil.getUserDtoByUidAndMobile(orderFlowDto.getHandleUid());
			// if("placeOrder".equals(orderListDto.getProcessId())){
			// if("03".equals(tempDto.getProductCode())){
			// AmsUtil.smsSend(user.getMobile(),
			// ipWhite,Constants.SMS_BACK_ORDER,"畅贷",tempDto.getCustomerName(),tempDto.getBorrowingAmount(),getUserDto(request).getName(),orderFlowDto.getCurrentProcessName());
			// if(orderFlowDto.getCurrentProcessId().equals("auditFinal")){
			// //受理员
			// AmsUtil.smsSend(user.getMobile(),
			// ipWhite,Constants.SMS_FINAL_FAIL,"畅贷",tempDto.getCustomerName(),tempDto.getBorrowingAmount(),"风控终审",getUserDto(request).getName());
			// //渠道经理
			// AmsUtil.smsSend(CommonDataUtil.getUserDtoByUidAndMobile(tempDto.getChannelManagerUid()).getMobile(),
			// ipWhite,Constants.SMS_FINAL_FAIL,"畅贷",tempDto.getCustomerName(),tempDto.getBorrowingAmount(),"风控终审",getUserDto(request).getName());
			// }else if(orderFlowDto.getCurrentProcessId().equals("auditOfficer")){
			// //受理员
			// AmsUtil.smsSend(user.getMobile(),
			// ipWhite,Constants.SMS_FINAL_FAIL,"畅贷",tempDto.getCustomerName(),tempDto.getBorrowingAmount(),"首席风险官审批",getUserDto(request).getName());
			// //渠道经理
			// AmsUtil.smsSend(CommonDataUtil.getUserDtoByUidAndMobile(tempDto.getChannelManagerUid()).getMobile(),
			// ipWhite,Constants.SMS_FINAL_FAIL,"畅贷",tempDto.getCustomerName(),tempDto.getBorrowingAmount(),"首席风险官审批",getUserDto(request).getName());
			// }
			// }else{
			// if(orderFlowDto.getCurrentProcessId().equals("auditFinal")){
			// AmsUtil.smsSend(user.getMobile(),
			// ipWhite,Constants.SMS_BACK_ORDER,"债务置换",tempDto.getCustomerName(),tempDto.getBorrowingAmount(),getUserDto(request).getName(),orderFlowDto.getCurrentProcessName());
			// //受理员
			// AmsUtil.smsSend(user.getMobile(),
			// ipWhite,Constants.SMS_FINAL_FAIL,"债务置换",tempDto.getCustomerName(),tempDto.getBorrowingAmount(),"风控终审",getUserDto(request).getName());
			// //渠道经理
			// AmsUtil.smsSend(CommonDataUtil.getUserDtoByUidAndMobile(tempDto.getChannelManagerUid()).getMobile(),
			// ipWhite,Constants.SMS_FINAL_FAIL,"债务置换",tempDto.getCustomerName(),tempDto.getBorrowingAmount(),"风控终审",getUserDto(request).getName());
			// }else if(orderFlowDto.getCurrentProcessId().equals("auditOfficer")){
			// AmsUtil.smsSend(user.getMobile(),
			// ipWhite,Constants.SMS_BACK_ORDER,"债务置换",tempDto.getCustomerName(),tempDto.getBorrowingAmount(),getUserDto(request).getName(),orderFlowDto.getCurrentProcessName());
			// //受理员
			// AmsUtil.smsSend(user.getMobile(),
			// ipWhite,Constants.SMS_FINAL_FAIL,"债务置换",tempDto.getCustomerName(),tempDto.getBorrowingAmount(),"首席风险官审批",getUserDto(request).getName());
			// //渠道经理
			// AmsUtil.smsSend(CommonDataUtil.getUserDtoByUidAndMobile(tempDto.getChannelManagerUid()).getMobile(),
			// ipWhite,Constants.SMS_FINAL_FAIL,"债务置换",tempDto.getCustomerName(),tempDto.getBorrowingAmount(),"首席风险官审批",getUserDto(request).getName());
			// }
			// }
			// }
			// 分控审批退回
			if ((orderFlowDto.getCurrentProcessId().equals("auditFirst")
					|| orderFlowDto.getCurrentProcessId().equals("auditFinal")
					|| orderFlowDto.getCurrentProcessId().equals("auditReview")
					|| orderFlowDto.getCurrentProcessId().equals("auditOfficer")
					|| orderFlowDto.getCurrentProcessId().equals("auditJustice"))
					&& (orderFlowDto.getNextProcessId().equals("placeOrder")
							|| orderFlowDto.getNextProcessId().equals("managerAudit")
							|| orderFlowDto.getNextProcessId().equals("facesign")
							|| orderFlowDto.getNextProcessId().equals("notarization")
							|| orderFlowDto.getNextProcessId().equals("auditFirst"))) {
				String ProductName = "债务置换";
				if (tempDto != null && !"01".equals(tempDto.getProductCode())
						&& !"02".equals(tempDto.getProductCode())) {
					ProductName = tempDto.getProductName();
				}
				// 受理员
				AmsUtil.smsSend(user.getMobile(), ipWhite, Constants.SMS_TEMPLATE_BACK_AUDIT, ProductName,
						tempDto.getCustomerName(), tempDto.getBorrowingAmount(), orderFlowDto.getCurrentProcessName(),
						orderFlowDto.getNextProcessName());
				// 渠道经理
				AmsUtil.smsSend(CommonDataUtil.getUserDtoByUidAndMobile(tempDto.getChannelManagerUid()).getMobile(),
						ipWhite, Constants.SMS_TEMPLATE_BACK_AUDIT, ProductName, tempDto.getCustomerName(),
						tempDto.getBorrowingAmount(), orderFlowDto.getCurrentProcessName(),
						orderFlowDto.getNextProcessName());
			}
			// 核实利息/核实收费
			if (orderFlowDto.getCurrentProcessId().equals("isCharge")
					|| orderFlowDto.getCurrentProcessId().equals("lendingHarvest")) {
				Map<String, Object> to = new HashMap<String, Object>();
				to.put("orderNo", orderFlowDto.getOrderNo());
				RespDataObject<DistributionMemberDto> menber = httpUtil.getRespDataObject(Constants.LINK_CREDIT,
						"/credit/process/distributionMember/v/detail", to, DistributionMemberDto.class);
				if (menber.getData() != null) {
					UserDto menberUser = CommonDataUtil
							.getUserDtoByUidAndMobile(menber.getData().getForeclosureMemberUid());
					String ProductName = "债务置换";
					if (tempDto != null && !"01".equals(tempDto.getProductCode())
							&& !"02".equals(tempDto.getProductCode())) {
						ProductName = tempDto.getProductName();
					}
					// 受理员
					AmsUtil.smsSend(user.getMobile(), ipWhite, Constants.SMS_TEMPLATE_BACK_AUDIT, ProductName,
							tempDto.getCustomerName(), tempDto.getBorrowingAmount(),
							orderFlowDto.getCurrentProcessName(), orderFlowDto.getNextProcessName());
					// 结清原贷款
					AmsUtil.smsSend(menberUser.getMobile(), ipWhite, Constants.SMS_TEMPLATE_BACK_AUDIT, ProductName,
							tempDto.getCustomerName(), tempDto.getBorrowingAmount(),
							orderFlowDto.getCurrentProcessName(), orderFlowDto.getNextProcessName());
				}
			}
			orderFlowDto.setHandleUid(userDto.getUid());
			orderFlowDto.setHandleName(userDto.getName());

			resp = goNextNode(orderFlowDto, orderListDto);
		} catch (Exception e) {
			e.printStackTrace();
			RespHelper.setFailRespStatus(resp, RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}

	/**
	 * 查询所有退回的订单 （统计报表用）
	 * 
	 * @param request
	 * @param orderFlowDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectOrderFlowAll")
	public RespPageData<StatisticsDto> selectOrderFlowAll(HttpServletRequest request,
			@RequestBody OrderFlowDto orderFlowDto) {
		RespPageData<StatisticsDto> resp = new RespPageData<StatisticsDto>();
		resp.setCode(RespStatusEnum.FAIL.getCode());
		resp.setMsg(RespStatusEnum.FAIL.getMsg());
		try {
			resp.setTotal(orderFlowService.selectOrderFlowCount(orderFlowDto));
			resp.setRows(orderFlowService.selectOrderFlowAll(orderFlowDto));
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}

	// private @Resource OrderFlowMapper orderFlowMapper;
	//
	// private @Resource OrderBaseMapper orderBaseMapper;
	//
	// /**
	// * @param request
	// * @param orderFlowDto
	// * @return
	// */
	// @ResponseBody
	// @RequestMapping(value = "/test")
	// public RespPageData<StatisticsDto> test(HttpServletRequest request){
	// RespPageData<StatisticsDto> resp = new RespPageData<StatisticsDto>();
	// resp.setCode(RespStatusEnum.FAIL.getCode());
	// resp.setMsg(RespStatusEnum.FAIL.getMsg());
	// try {
	//
	// Map<String, Object> temp201701 = new HashMap<String, Object>();
	// Map<String, Object> temp201702 = new HashMap<String, Object>();
	// Map<String, Object> temp201703 = new HashMap<String, Object>();
	// Map<String, Object> temp201704 = new HashMap<String, Object>();
	// Map<String, Object> temp201705 = new HashMap<String, Object>();
	// Map<String, Object> temp201706 = new HashMap<String, Object>();
	// Map<String, Object> temp201707 = new HashMap<String, Object>();
	// Map<String, Object> temp201708 = new HashMap<String, Object>();
	// Map<String, Object> temp201709 = new HashMap<String, Object>();
	// Map<String, Object> temp201710 = new HashMap<String, Object>();
	// Map<String, Object> temp201711 = new HashMap<String, Object>();
	// Map<String, Object> temp201712 = new HashMap<String, Object>();
	// Map<String, Object> temp201801 = new HashMap<String, Object>();
	//
	// List<OrderListDto> orderListDtos = orderBaseMapper.selectAllOrderList();
	// for (OrderListDto orderListDto : orderListDtos) {
	//
	// if(orderListDto.getState().equals("已关闭")){
	// continue;
	// }
	// if(orderListDto.getState().equals("订单已停止")){
	// continue;
	// }
	//
	// List<OrderFlowDto> orderFlowDtos =
	// orderFlowMapper.selectOrderFlowList(orderListDto.getOrderNo());
	// Date kaishiHandleTime = null;
	// Date jiesuHandleTime = null;
	// String nextProcessId = "";
	// String tempStr ="";
	// for (OrderFlowDto orderFlowDto : orderFlowDtos) {
	// if(orderFlowDto.getCurrentProcessId().equals("auditFirst")){
	// kaishiHandleTime = orderFlowDto.getHandleTime();
	// nextProcessId = orderFlowDto.getNextProcessId();
	// jiesuHandleTime = null;
	// }
	// if(StringUtils.isNotEmpty(nextProcessId) &&
	// orderFlowDto.getCurrentProcessId().equals(nextProcessId)){
	// jiesuHandleTime = orderFlowDto.getHandleTime();
	// tempStr = DateUtil.getDateByFmt(jiesuHandleTime, DateUtil.FMT_TYPE10);
	// }
	// }
	// if(kaishiHandleTime == null || jiesuHandleTime == null){
	// continue;
	// }
	// if(DateUtil.betweenMinutes(kaishiHandleTime, jiesuHandleTime)>1000){
	// System.out.println("大于1000分钟的:"+orderListDto.getCustomerName()+"时间："+DateUtil.betweenMinutes(kaishiHandleTime,
	// jiesuHandleTime));
	// }
	//
	// if(tempStr.equals("201801")){
	// temp201801.put(orderListDto.getOrderNo(),
	// DateUtil.betweenMinutes(kaishiHandleTime, jiesuHandleTime));
	// }else if(tempStr.equals("201701")){
	// temp201701.put(orderListDto.getOrderNo(),
	// DateUtil.betweenMinutes(kaishiHandleTime, jiesuHandleTime));
	// }else if(tempStr.equals("201702")){
	// temp201702.put(orderListDto.getOrderNo(),
	// DateUtil.betweenMinutes(kaishiHandleTime, jiesuHandleTime));
	// }else if(tempStr.equals("201703")){
	// temp201703.put(orderListDto.getOrderNo(),
	// DateUtil.betweenMinutes(kaishiHandleTime, jiesuHandleTime));
	// }else if(tempStr.equals("201704")){
	// temp201704.put(orderListDto.getOrderNo(),
	// DateUtil.betweenMinutes(kaishiHandleTime, jiesuHandleTime));
	// }else if(tempStr.equals("201705")){
	// temp201705.put(orderListDto.getOrderNo(),
	// DateUtil.betweenMinutes(kaishiHandleTime, jiesuHandleTime));
	// }else if(tempStr.equals("201706")){
	// temp201706.put(orderListDto.getOrderNo(),
	// DateUtil.betweenMinutes(kaishiHandleTime, jiesuHandleTime));
	// }else if(tempStr.equals("201707")){
	// temp201707.put(orderListDto.getOrderNo(),
	// DateUtil.betweenMinutes(kaishiHandleTime, jiesuHandleTime));
	// }else if(tempStr.equals("201708")){
	// temp201708.put(orderListDto.getOrderNo(),
	// DateUtil.betweenMinutes(kaishiHandleTime, jiesuHandleTime));
	// }else if(tempStr.equals("201709")){
	// temp201709.put(orderListDto.getOrderNo(),
	// DateUtil.betweenMinutes(kaishiHandleTime, jiesuHandleTime));
	// }else if(tempStr.equals("201710")){
	// temp201710.put(orderListDto.getOrderNo(),
	// DateUtil.betweenMinutes(kaishiHandleTime, jiesuHandleTime));
	// }else if(tempStr.equals("201711")){
	// temp201711.put(orderListDto.getOrderNo(),
	// DateUtil.betweenMinutes(kaishiHandleTime, jiesuHandleTime));
	// }else if(tempStr.equals("201712")){
	// temp201712.put(orderListDto.getOrderNo(),
	// DateUtil.betweenMinutes(kaishiHandleTime, jiesuHandleTime));
	// }
	// }
	//
	// int time = 0;
	// for (String key : temp201701.keySet()) {
	// time += MapUtils.getIntValue(temp201701, key);
	// }
	// System.out.println(time/temp201701.size()+"\t"+temp201701.size());
	// time = 0;
	//
	// for (String key : temp201702.keySet()) {
	// time += MapUtils.getIntValue(temp201702, key);
	// }
	// System.out.println(time/temp201702.size()+"\t"+temp201702.size());
	// time = 0;
	//
	// for (String key : temp201703.keySet()) {
	// time += MapUtils.getIntValue(temp201703, key);
	// }
	// System.out.println(time/temp201703.size()+"\t"+temp201703.size());
	// time = 0;
	//
	// for (String key : temp201704.keySet()) {
	// time += MapUtils.getIntValue(temp201704, key);
	// }
	// System.out.println(time/temp201704.size()+"\t"+temp201704.size());
	// time = 0;
	//
	// for (String key : temp201705.keySet()) {
	// time += MapUtils.getIntValue(temp201705, key);
	// }
	// System.out.println(time/temp201705.size()+"\t"+temp201705.size());
	// time = 0;
	//
	// for (String key : temp201706.keySet()) {
	// time += MapUtils.getIntValue(temp201706, key);
	// }
	// System.out.println(time/temp201706.size()+"\t"+temp201706.size());
	// time = 0;
	//
	// for (String key : temp201707.keySet()) {
	// time += MapUtils.getIntValue(temp201707, key);
	// }
	// System.out.println(time/temp201707.size()+"\t"+temp201707.size());
	// time = 0;
	//
	// for (String key : temp201708.keySet()) {
	// time += MapUtils.getIntValue(temp201708, key);
	// }
	// System.out.println(time/temp201708.size()+"\t"+temp201708.size());
	// time = 0;
	//
	// for (String key : temp201709.keySet()) {
	// time += MapUtils.getIntValue(temp201709, key);
	// }
	// System.out.println(time/temp201709.size()+"\t"+temp201709.size());
	// time = 0;
	//
	// for (String key : temp201710.keySet()) {
	// time += MapUtils.getIntValue(temp201710, key);
	// }
	// System.out.println(time/temp201710.size()+"\t"+temp201710.size());
	// time = 0;
	//
	// for (String key : temp201711.keySet()) {
	// time += MapUtils.getIntValue(temp201711, key);
	// }
	// System.out.println(time/temp201711.size()+"\t"+temp201711.size());
	// time = 0;
	//
	//
	// for (String key : temp201712.keySet()) {
	// time += MapUtils.getIntValue(temp201712, key);
	// }
	// System.out.println(time/temp201712.size()+"\t"+temp201712.size());
	// time = 0;
	//
	//
	// for (String key : temp201801.keySet()) {
	// time += MapUtils.getIntValue(temp201801, key);
	// }
	// System.out.println(time/temp201801.size()+"\t"+temp201801.size());
	// time = 0;
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// resp.setCode(RespStatusEnum.FAIL.getCode());
	// resp.setMsg(RespStatusEnum.FAIL.getMsg());
	// }
	// return resp;
	// }
}
