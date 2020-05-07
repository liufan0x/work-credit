/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service.impl.order;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.anjbo.bean.DictDto;
import com.anjbo.bean.ProcessDto;
import com.anjbo.bean.ProductDto;
import com.anjbo.bean.UserDto;
import com.anjbo.bean.order.BaseListDto;
import com.anjbo.common.OrderConstants;
import com.anjbo.controller.api.DataApi;
import com.anjbo.controller.api.ToolsApi;
import com.anjbo.dao.order.BaseListMapper;
import com.anjbo.service.order.BaseListService;
import com.anjbo.utils.DateUtils;
import com.anjbo.utils.StringUtil;
import com.anjbo.service.impl.BaseServiceImpl;

/**
 * 
 * @Author ANJBO Generator
 * @Date 2018-05-11 10:57:21
 * @version 1.0
 */
@Service
public class BaseListServiceImpl extends BaseServiceImpl<BaseListDto> implements BaseListService {

	@Resource
	private DataApi dataApi;

	@Resource
	private ToolsApi toolsApi;

	@Resource
	private BaseListMapper baseListMapper;

	@Override
	public BaseListDto selectDetail(String orderNo) {
		return baseListMapper.selectDetail(orderNo);
	}

	@Override
	public int searchPageListCount(BaseListDto orderListDto) {
		return baseListMapper.searchPageListCount(orderListDto);
	}

	@Override
	public List<BaseListDto> searchPageList(BaseListDto orderListDto) {
		List<BaseListDto> list = baseListMapper.searchPageList(orderListDto);
		for (BaseListDto orderListDtoTemp : list) {
			try {
				if (orderListDtoTemp.getPlanPaymentTime() != null) {
					DateUtils.betDatePart(new Date(), orderListDtoTemp.getPlanPaymentTime(), Calendar.DATE);
				}
				if (orderListDtoTemp.getFinanceOutLoanTime() != null) {
					orderListDtoTemp.setFinanceOutLoanTimeStr(
							DateUtils.dateToString(orderListDtoTemp.getFinanceOutLoanTime(), DateUtils.FMT_TYPE2));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	@Override
	public List<BaseListDto> selectAbleRelationOrder(BaseListDto baseListDto) {
		return baseListMapper.selectAbleRelationOrder(baseListDto);
	}

	@Override
	public List<Map<String, Object>> assembleLis(List<BaseListDto> orderListDtos, UserDto userDto) {
		String reportOrderNos = "";
		for (BaseListDto orderDto : orderListDtos) {
			reportOrderNos += "'" + orderDto.getOrderNo() + "'" + ",";
		}
		if (reportOrderNos.endsWith(",")) {
			reportOrderNos = reportOrderNos.substring(0, reportOrderNos.length() - 1);
		}

		Map<String, Object> repaymentPlanMap = new HashMap<String, Object>();
		Map<String, Object> paymentreportMap = null;
		Map<String, Object> loanPreparationMap = null;
		if (StringUtil.isNotEmpty(reportOrderNos)) {
			paymentreportMap = toolsApi.repaymentPreparationListMap(reportOrderNos);
			loanPreparationMap = toolsApi.loanPreparationListMap(reportOrderNos);
		}

		List<Map<String, Object>> orderList = new ArrayList<Map<String, Object>>();
		Map<String, Object> tempMap = null;
		for (BaseListDto orderDto : orderListDtos) {
			tempMap = new HashMap<String, Object>();
			tempMap.put("orderNo", orderDto.getOrderNo());
			tempMap.put("customerName", orderDto.getCustomerName() == null ? "" : orderDto.getCustomerName());
			tempMap.put("cityName", orderDto.getCityName().replace("市", ""));
			tempMap.put("cityCode", orderDto.getCityCode());
			tempMap.put("productCode", orderDto.getProductCode());
			tempMap.put("productName", orderDto.getProductName().replace("债务", ""));
			tempMap.put("borrowingAmount", orderDto.getBorrowingAmount() == null ? "-" : orderDto.getBorrowingAmount());
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
					|| orderDto.getProcessId().contains("foreclosure") || orderDto.getProcessId().contains("applyLoan")
					|| orderDto.getProcessId().contains("financialAudit"))
					&& !"10000".equals(orderDto.getProductCode())) {
				if (orderDto.getProcessId().contains("foreclosure")) {
					if (orderDto.getCurrentHandlerUid().equals(userDto.getUid())) {
						tempMap.put("butName", "结清原贷款");
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
			if ("04".equals(orderDto.getProductCode()) && ("fddRepayment".equals(orderDto.getProcessId())
					|| "fddMortgageRelease".equals(orderDto.getProcessId()) || "release".equals(orderDto.getProcessId())
					|| "rebate".equals(orderDto.getProcessId()) || "wanjie".equals(orderDto.getProcessId()))
					&& repaymentPlanMap.containsKey(orderDto.getOrderNo())
					&& !MapUtils.getString(repaymentPlanMap, orderDto.getOrderNo()).equals("7")) {
				String butName = MapUtils.getString(tempMap, "butName", "");
				String pageType = MapUtils.getString(tempMap, "pageType", "");
				butName = StringUtils.isBlank(butName) ? "还款计划表" : butName + ",还款计划表";
				pageType = StringUtils.isBlank(pageType) ? "repaymentPlan" : pageType + ",repaymentPlan";
				tempMap.put("butName", butName);
				tempMap.put("pageType", pageType);
			}

			// 出款报备
			String outloanreportProcess = OrderConstants.BASE_FINANCE_OUTLOANREPORT_PROCESS;
			if (StringUtils.isNotBlank(outloanreportProcess) && null != orderDto
					&& StringUtils.isNotBlank(orderDto.getProcessId())
					&& outloanreportProcess.contains(orderDto.getProcessId()) && userDto.getUid() != null
					&& (userDto.getUid().equals(orderDto.getAcceptMemberUid())
							|| userDto.getUid().equals(orderDto.getChannelManagerUid()))) {
				String butName = MapUtils.getString(tempMap, "butName", "");
				String pageType = MapUtils.getString(tempMap, "pageType", "");
				int status = (null == loanPreparationMap || loanPreparationMap.size() <= 0) ? -1
						: MapUtils.getInteger(loanPreparationMap, orderDto.getOrderNo(), -1);
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
			String processConfig = OrderConstants.BASE_FINANCE_LOAN_PROCESS;
			if (StringUtils.isNotBlank(processConfig) && null != orderDto
					&& StringUtils.isNotBlank(orderDto.getProcessId())
					&& processConfig.contains(orderDto.getProcessId()) && StringUtils.isNotBlank(userDto.getUid())
					&& (userDto.getUid().equals(orderDto.getAcceptMemberUid())
							|| userDto.getUid().equals(orderDto.getChannelManagerUid()))) {
				String butName = MapUtils.getString(tempMap, "butName", "");
				String pageType = MapUtils.getString(tempMap, "pageType", "");
				int status = (null == paymentreportMap || paymentreportMap.size() <= 0) ? -1
						: MapUtils.getInteger(paymentreportMap, orderDto.getOrderNo(), -1);
				if (status == -1 || status == 3) {
					butName = StringUtils.isBlank(butName) ? "回款报备" : butName + ",回款报备";
					pageType = StringUtils.isBlank(pageType) ? "editPaymentReport" : pageType + ",editPaymentReport";
				} else if (status == 2 || status == 0) {
					butName = StringUtils.isBlank(butName) ? "撤销回款报备,修改回款报备" : butName + ",撤销回款报备,修改回款报备";
					pageType = StringUtils.isBlank(pageType) ? "cancelPaymentReport,editPaymentReport"
							: pageType + ",cancelPaymentReport,editPaymentReport";
				}
				tempMap.put("butName", butName);
				tempMap.put("pageType", pageType);
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
		return orderList;
	}

	public Map<String, Object> assembleBrush(UserDto user, boolean hasYAJ) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		List<DictDto> cityListTemp = dataApi.getDictDtoListByType("bookingSzAreaOid");
		List<ProductDto> productListTemp = dataApi.getProductList();
		List<Map<String, Object>> cityList = new ArrayList<Map<String, Object>>();
		// 机构城市产品
		boolean isAgency = user.getAgencyId() > 1;
		Map<String, String> mapCityProduct = null;

		// 相关所有产品
		List<Map<String, Object>> productListAll = new LinkedList<Map<String, Object>>();
		List<Map<String, Object>> stateListAll = new LinkedList<Map<String, Object>>();
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
						for (ProcessDto productProcessDto : productDto.getProcessDtos()) {
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
			List<DictDto> dictDtos = dataApi.getDictDtoListByType("branchCompany");
			DictDto dictDto = new DictDto();
			dictDto.setCode("");
			dictDto.setName("不限");
			dictDtos.add(0, dictDto);
			retMap.put("regions", dictDtos);
		}
		retMap.put("canAdd", false);
		try {
			String[] authIds = OrderConstants.BASE_PLACE_ORDER.split(",");
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

}
