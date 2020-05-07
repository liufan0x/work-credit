/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller.impl.order;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.anjbo.bean.AgencyDto;
import com.anjbo.bean.FundDto;
import com.anjbo.bean.UserDto;
import com.anjbo.bean.order.BaseListDto;
import com.anjbo.bean.order.FlowDto;
import com.anjbo.common.OrderConstants;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.BaseController;
import com.anjbo.controller.api.DataApi;
import com.anjbo.controller.api.ToolsApi;
import com.anjbo.controller.api.UserApi;
import com.anjbo.controller.order.IBaseListController;
import com.anjbo.service.order.BaseListService;
import com.anjbo.service.order.FlowService;
import com.anjbo.utils.StringUtil;

/**
 * 
 * @Author ANJBO Generator
 * @Date 2018-05-11 10:57:21
 * @version 1.0
 */
@RestController
public class BaseListController extends BaseController implements IBaseListController {
	Logger log = Logger.getLogger(BaseListController.class);

	@Resource
	private BaseListService baseListService;

	@Resource
	private FlowService flowService;

	@Resource
	private UserApi userApi;

	@Resource
	private DataApi dataApi;
	
	@Resource 
	private ToolsApi toolsApi;

	private String getDeptAllUid(UserDto userDto) {
		String deptAllUid = "";
		if (userDto.getAuthIds() != null && userDto.getAuthIds().contains("1")) {
			// 查看部门订单
		} else if (userDto.getAuthIds() != null && userDto.getAuthIds().contains("2")) {
			deptAllUid = userApi.selectUidsByDeptId(userDto);
			// 查看自己订单
		} else {
			deptAllUid = userDto.getUid();
		}
		return deptAllUid;
	}

	/**
	 * 分页查询
	 * 
	 * @author Generator
	 */
	@Override
	public RespPageData<BaseListDto> page(@RequestBody BaseListDto orderListDto) {
		RespPageData<BaseListDto> resp = new RespPageData<BaseListDto>();
		try {
			UserDto userDto = userApi.getUserDto();
			if (0 == orderListDto.getType() || 1 == orderListDto.getType()) {
				FlowDto flowDto = new FlowDto();
				flowDto.setHandleUid(userDto.getUid());
				List<FlowDto> flowDtos = flowService.search(flowDto);
				String orderNos = "";
				for (FlowDto flow : flowDtos) {
					orderNos += "'" + flow.getOrderNo() + "',";
				}
				if (StringUtils.isNotEmpty(orderNos)) {
					orderNos = orderNos.substring(0, orderNos.length() - 1);
				}
				orderListDto.setOrderNo(orderNos);
				if (0 == orderListDto.getType()) {
					orderListDto.setCurrentHandlerUid(getDeptAllUid(userDto));
				}

			} else {
				orderListDto.setType(2);
				orderListDto.setCurrentHandlerUid(userDto.getUid());
			}
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
			resp.setRows(baseListService.search(orderListDto));
			resp.setTotal(baseListService.count(orderListDto));
		} catch (Exception e) {
			logger.error("分页异常,参数：" + orderListDto.toString(), e);
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}

	@Override
	public RespPageData<BaseListDto> fundList(@RequestBody BaseListDto dto) {
		RespPageData<BaseListDto> resp = new RespPageData<BaseListDto>();
		try {
			UserDto userDto = userApi.getUserDto();
			FundDto fundDto = new FundDto();
			fundDto.setManagerUid(userDto.getUid());
			fundDto = userApi.findByFundId(fundDto);
			
			
		} catch (Exception e) {
			logger.error("资方列表异常,参数：" + dto.toString(), e);
			RespHelper.setFailRespStatus(resp, RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}
	
	
	@Override
	public RespDataObject<Map<String, Object>> appList(@RequestBody BaseListDto orderListDto) {
		RespDataObject<Map<String, Object>> resp = new RespDataObject<Map<String, Object>>();
		try {
			Map<String, Object> retMap = new HashMap<String, Object>();
			UserDto userDto = userApi.getUserDto();
			String deptAllUid = "";

			List<BaseListDto> orderListDtos = new ArrayList<BaseListDto>();
			if (userDto != null && userDto.getIsEnable() == 100) {

			} else if (userDto != null) {
				if (userDto.getIsEnable() == 3 || 0 == userDto.getAgencyId()) {
					deptAllUid = userDto.getUid();
				} else {
					deptAllUid = getDeptAllUid(userDto);
				}
				// 提单选择的非兜底合作机构登陆看到此订单
				// 机构用户会判断查看本机构订单
				if (userDto.getAgencyId() > 1) {
					orderListDto.setAgencyId(userDto.getAgencyId());
					AgencyDto agencyDto = new AgencyDto();
					agencyDto.setId(userDto.getAgencyId());
					agencyDto = userApi.getAgencyDto(agencyDto);
					if (agencyDto.getCooperativeModeId() == 2) {
						orderListDto.setCooperativeAgencyId(userDto.getAgencyId());
					}
				}
				orderListDto.setCurrentHandlerUid(deptAllUid);
				orderListDtos = baseListService.search(orderListDto);
			}

			String reportOrderNos = "";
			for (BaseListDto orderDto : orderListDtos) {
				reportOrderNos += "'" + orderDto.getOrderNo() + "'" + ",";
			}
			if (reportOrderNos.endsWith(",")) {
				reportOrderNos = reportOrderNos.substring(0, reportOrderNos.length() - 1);
			}

			orderListDto.setType(0);
			orderListDto.setUpdateUid(userDto.getUid());
			orderListDto.setCurrentHandlerUid(getDeptAllUid(userDto));

			Map<String, Object> reopen = new HashMap<String, Object>();

			Map<String, Object> repaymentPlanMap = new HashMap<String, Object>();//待还款列表
			Map<String, Object> reportMap = toolsApi.loanPreparationListMap(reportOrderNos);//出款报备
			Map<String, Object> paymentreportMap = toolsApi.repaymentPreparationListMap(reportOrderNos);//回款报备
			
			
			
			
			List<Map<String, Object>> orderList = new ArrayList<Map<String, Object>>();
			
			String cityName = "";
			String productName = "";
			String productNamePrefix = "";
			String productNameSuffix = "";
			Map<String, Object> tempMap = null;
			for (BaseListDto orderDto : orderListDtos) {
				tempMap = new HashMap<String, Object>();
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
					tempMap.put("isExample", "1");
				} else {
					tempMap.put("isExample", "2");
				}

				if ("10000".equals(orderListDto.getProductCode())) {
					tempMap = yunAnJie(orderDto, tempMap, userDto, reopen);
				} else {
					tempMap = zhaiWuZhiHuan(orderDto, tempMap, userDto, repaymentPlanMap, paymentreportMap, reportMap);
				}

				tempMap.put("orderNo", orderDto.getOrderNo());
				tempMap.put("customerName", orderDto.getCustomerName()==null?"":orderDto.getCustomerName());
				tempMap.put("cityName", cityName);
				tempMap.put("cityCode", orderDto.getCityCode());
				tempMap.put("productCode",orderDto.getProductCode());
				tempMap.put("productName",productName);
				tempMap.put("productNamePrefix", productNamePrefix);
				tempMap.put("productNameSuffix", productNameSuffix);
				tempMap.put("borrowingAmount", orderDto.getBorrowingAmount()==null?"-":orderDto.getBorrowingAmount());
				tempMap.put("borrowingDay", orderDto.getBorrowingDay());
				if("10000".equals(orderDto.getProductCode())&&(orderDto.getState().contains("已关闭")||orderDto.getState().contains("待提交申请按揭"))){//不显示右上角金额，期限
					tempMap.put("borrowingAmount", "-");
					tempMap.put("borrowingDay", 0);
				}
				tempMap.put("channelManagerName", orderDto.getChannelManagerName()==null?"-":orderDto.getChannelManagerName());
				tempMap.put("cooperativeAgencyName", orderDto.getCooperativeAgencyName()==null?"-":orderDto.getCooperativeAgencyName());
				tempMap.put("acceptMemberName", orderDto.getAcceptMemberName()==null?"-":orderDto.getAcceptMemberName());
				tempMap.put("state", orderDto.getState());
				tempMap.put("currentHandler", orderDto.getCurrentHandler());
				tempMap.put("processId", orderDto.getProcessId());
				tempMap.put("isRelationOrder", orderDto.getIsRelationOrder());
				orderList.add(tempMap);
			}

			if ("10000".equals(orderListDto.getProductCode())) {
				retMap.put("orderList", baseListService.assembleLis(orderListDtos, userDto));
			} else {
				orderListDtos = baseListService.searchPageList(orderListDto);
				if (orderListDtos != null && orderListDtos.size() > 0) {
					retMap.put("orderList", baseListService.assembleLis(orderListDtos, userDto));
				}
			}
			if (userDto != null) {
				retMap.put("brush", baseListService.assembleBrush(userDto, true));
			}
			RespHelper.setSuccessDataObject(resp, retMap);
		} catch (Exception e) {
			logger.error("app分页异常,参数：" + orderListDto.toString(), e);
			RespHelper.setFailRespStatus(resp, RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}

	/**
	 * 查询
	 * 
	 * @author Generator
	 */
	@Override
	public RespData<BaseListDto> search(@RequestBody BaseListDto dto) {
		RespData<BaseListDto> resp = new RespData<BaseListDto>();
		try {
			return RespHelper.setSuccessData(resp, baseListService.search(dto));
		} catch (Exception e) {
			logger.error("查询异常,参数：" + dto.toString(), e);
			return RespHelper.setFailData(resp, new ArrayList<BaseListDto>(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 查找
	 * 
	 * @author Generator
	 */
	@Override
	public RespDataObject<BaseListDto> find(@RequestBody BaseListDto dto) {
		RespDataObject<BaseListDto> resp = new RespDataObject<BaseListDto>();
		try {
			return RespHelper.setSuccessDataObject(resp, baseListService.find(dto));
		} catch (Exception e) {
			logger.error("查找异常,参数：" + dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new BaseListDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 新增
	 * 
	 * @author Generator
	 */
	@Override
	public RespDataObject<BaseListDto> add(@RequestBody BaseListDto dto) {
		RespDataObject<BaseListDto> resp = new RespDataObject<BaseListDto>();
		try {
			return RespHelper.setSuccessDataObject(resp, baseListService.insert(dto));
		} catch (Exception e) {
			logger.error("新增异常,参数：" + dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new BaseListDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 编辑
	 * 
	 * @author Generator
	 */
	@Override
	public RespStatus edit(@RequestBody BaseListDto dto) {
		RespStatus resp = new RespStatus();
		try {
			dto.setUpdateUid(userApi.getUserDto().getUid());
			baseListService.update(dto);
			return RespHelper.setSuccessRespStatus(resp);
		} catch (Exception e) {
			logger.error("编辑异常,参数：" + dto.toString(), e);
			return RespHelper.failRespStatus();
		}
	}

	/**
	 * 删除
	 * 
	 * @author Generator
	 */
	@Override
	public RespStatus delete(@RequestBody BaseListDto dto) {
		RespStatus resp = new RespStatus();
		try {
			baseListService.delete(dto);
			return RespHelper.setSuccessRespStatus(resp);
		} catch (Exception e) {
			logger.error("删除异常,参数：" + dto.toString(), e);
			return RespHelper.failRespStatus();
		}
	}

	/**
	 * 云按揭封装
	 * 
	 * @param orderDto
	 * @param tempMap
	 * @param userDto
	 * @param reopen
	 * @return
	 */
	private Map<String, Object> yunAnJie(BaseListDto orderDto, Map<String, Object> tempMap, UserDto userDto,
			Map<String, Object> reopen) {
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
			tempMap.put("appShowValue1", orderDto.getAppShowValue1() == null ? "-" : orderDto.getAppShowValue1());
			tempMap.put("appShowValue2", orderDto.getAppShowValue2() == null ? "-" : orderDto.getAppShowValue2());
			tempMap.put("appShowValue3", orderDto.getAppShowValue3() == null ? "-" : orderDto.getAppShowValue3());
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
			tempMap.put("appShowValue1", orderDto.getAppShowValue1() == null ? "-" : orderDto.getAppShowValue1());
			tempMap.put("appShowValue2", orderDto.getAppShowValue2() == null ? "-" : orderDto.getAppShowValue2());
			tempMap.put("appShowValue3", orderDto.getAppShowValue3() == null ? "-" : orderDto.getAppShowValue3());
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
		return tempMap;
	}

	private Map<String, Object> zhaiWuZhiHuan(BaseListDto orderDto, Map<String, Object> tempMap,UserDto userDto,Map<String, Object> repaymentPlanMap,Map<String, Object> paymentreportMap,Map<String, Object> reportMap) {
		boolean isChanLoan = !"03".equals(orderDto.getProductCode());

		if ((orderDto.getProcessId().contains("auditFirst") || orderDto.getProcessId().contains("auditFinal")
				|| orderDto.getProcessId().contains("auditOfficer") || orderDto.getProcessId().contains("auditReview")
				|| orderDto.getProcessId().contains("repaymentMember")
				|| orderDto.getProcessId().contains("foreclosure") || orderDto.getProcessId().contains("applyLoan")
				|| orderDto.getProcessId().contains("financialAudit")
				|| orderDto.getProcessId().contains("managerAudit")) && !"10000".equals(orderDto.getProductCode())) {
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
					&& (orderDto.getProcessId().contains("auditFirst") || orderDto.getProcessId().contains("auditFinal")
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
				tempMap.put("appShowValue1", orderDto.getAppShowValue1() == null ? "-" : orderDto.getAppShowValue1());
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
		String processConfig = OrderConstants.BASE_FINANCE_LOAN_PROCESS;
		if (StringUtils.isNotBlank(processConfig) && null != orderDto && StringUtils.isNotBlank(orderDto.getProcessId())
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
		// 撤回功能
		if (StringUtil.isNotBlank(orderDto.getPreviousHandlerUid())
				&& orderDto.getPreviousHandlerUid().equals(userDto.getUid())
				&& !"isBackExpenses".equals(orderDto.getProcessId()) && !"placeOrder".equals(orderDto.getProcessId())
				&& !"forensics".equals(orderDto.getProcessId()) && !"cancellation".equals(orderDto.getProcessId())
				&& !"transfer".equals(orderDto.getProcessId()) && !"newlicense".equals(orderDto.getProcessId())
				&& !"receivableFor".equals(orderDto.getProcessId())
				&& !"receivableForFirst".equals(orderDto.getProcessId())
				&& !"receivableForEnd".equals(orderDto.getProcessId()) && !"wanjie".equals(orderDto.getProcessId())
				&& !"closeOrder".equals(orderDto.getProcessId()) && !(orderDto.getState().contains("退回"))
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
		return tempMap;
	}

	public RespData<Map<String, Object>> selectAbleRelationOrder(@RequestBody BaseListDto baseListDto){
		RespData<Map<String, Object>> resp = new RespData<Map<String, Object>>();
		try {
			if(baseListDto != null && StringUtil.isEmpty(baseListDto.getCustomerName())){
				RespHelper.setFailData(resp, null, RespStatusEnum.PARAMETER_ERROR.getMsg());
				return resp;
			}
			UserDto userDto = userApi.getUserDto();
			baseListDto.setUpdateUid(userDto.getUid());
			baseListDto.setOrderNo(flowService.selectOrderNoByUid(userDto.getUid()));
			baseListDto.setCurrentHandlerUid(getDeptAllUid(userDto));
			
			List<BaseListDto> list = baseListService.selectAbleRelationOrder(baseListDto);
			List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
			for (BaseListDto temp : list) {
				 Map<String, Object> map = new HashMap<String, Object>();
				 map.put("id", temp.getOrderNo());
				 map.put("orderNo", temp.getOrderNo());
				 map.put("name", temp.getCustomerName()+"， "+ NumberFormat.getInstance().format(Double.valueOf(temp.getBorrowingAmount())) +"万， "+temp.getBorrowingDay()+"天");
				 listMap.add(map);
			}
			return RespHelper.setSuccessData(resp, listMap);
		} catch (Exception e) {
			e.printStackTrace();
			RespHelper.setFailData(resp, null, RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}
	
}