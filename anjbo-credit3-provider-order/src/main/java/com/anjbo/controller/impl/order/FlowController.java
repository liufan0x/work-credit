/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller.impl.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.anjbo.bean.ProcessDto;
import com.anjbo.bean.UserDto;
import com.anjbo.bean.order.BaseListDto;
import com.anjbo.bean.order.FlowDto;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.BaseController;
import com.anjbo.controller.api.DataApi;
import com.anjbo.controller.api.UserApi;
import com.anjbo.controller.order.IFlowController;
import com.anjbo.service.order.BaseListService;
import com.anjbo.service.order.FlowService;

/**
 * 
 * @Author ANJBO Generator
 * @Date 2018-07-10 16:09:44
 * @version 1.0
 */
@RestController
public class FlowController extends BaseController implements IFlowController {

	@Resource
	private FlowService flowService;

	@Resource
	private BaseListService baseListService;

	@Resource
	private UserApi userApi;

	@Resource
	private DataApi dataApi;
	
	/**
	 * 分页查询
	 * 
	 * @author Generator
	 */
	@Override
	public RespPageData<FlowDto> page(@RequestBody FlowDto dto) {
		RespPageData<FlowDto> resp = new RespPageData<FlowDto>();
		try {
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
			resp.setRows(flowService.search(dto));
			resp.setTotal(flowService.count(dto));
		} catch (Exception e) {
			logger.error("分页异常,参数：" + dto.toString(), e);
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}

	/**
	 * 查询
	 * 
	 * @author Generator
	 */
	@Override
	public RespData<FlowDto> search(@RequestBody FlowDto dto) {
		RespData<FlowDto> resp = new RespData<FlowDto>();
		try {
			return RespHelper.setSuccessData(resp, flowService.search(dto));
		} catch (Exception e) {
			logger.error("查询异常,参数：" + dto.toString(), e);
			return RespHelper.setFailData(resp, new ArrayList<FlowDto>(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 查找
	 * 
	 * @author Generator
	 */
	@Override
	public RespDataObject<FlowDto> find(@RequestBody FlowDto dto) {
		RespDataObject<FlowDto> resp = new RespDataObject<FlowDto>();
		try {
			return RespHelper.setSuccessDataObject(resp, flowService.find(dto));
		} catch (Exception e) {
			logger.error("查找异常,参数：" + dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new FlowDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 新增
	 * 
	 * @author Generator
	 */
	@Override
	public RespDataObject<FlowDto> add(@RequestBody FlowDto dto) {
		RespDataObject<FlowDto> resp = new RespDataObject<FlowDto>();
		try {
			if (dto == null || "".equals(dto.getOrderNo()) || "".equals(dto.getUpdateUid())
					|| "".equals(dto.getCurrentProcessId()) || "".equals(dto.getNextProcessId())
					|| "".equals(dto.getHandleUid())) {
				RespHelper.setFailRespStatus(resp, RespStatusEnum.PARAMETER_ERROR.getMsg());
				return resp;
			}
			
			dto.setCreateUid(userApi.getUserDto().getUid());
		 	dto = flowService.insert(dto);
		 	
			return RespHelper.setSuccessDataObject(resp, dto);
		} catch (Exception e) {
			logger.error("新增异常,参数：" + dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new FlowDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 编辑
	 * 
	 * @author Generator
	 */
	@Override
	public RespStatus edit(@RequestBody FlowDto dto) {
		RespStatus resp = new RespStatus();
		try {
			dto.setUpdateUid(userApi.getUserDto().getUid());
			flowService.update(dto);
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
	public RespStatus delete(@RequestBody FlowDto dto) {
		RespStatus resp = new RespStatus();
		try {
			flowService.delete(dto);
			return RespHelper.setSuccessRespStatus(resp);
		} catch (Exception e) {
			logger.error("删除异常,参数：" + dto.toString(), e);
			return RespHelper.failRespStatus();
		}
	}

	@Override
	public FlowDto selectEndOrderFlow(@RequestBody FlowDto dto) {
		return flowService.selectEndOrderFlow(dto);
	}

	@Override
	public RespData<FlowDto> selectOrderFlowList(@RequestBody FlowDto dto) {
		RespData<FlowDto> resp = new RespData<FlowDto>();
		try {
			BaseListDto baseListDto = new BaseListDto();
			baseListDto.setOrderNo(dto.getOrderNo());
			baseListDto = baseListService.find(baseListDto);
			Integer productId = Integer.valueOf(baseListDto.getCityCode() + baseListDto.getProductCode());
			if (productId == null) {
				productId = 440301;
			}
			List<FlowDto> list = flowService.search(dto);
			List<UserDto> userDtos = userApi.selectAllUserDto();
			List<FlowDto> tempList = new ArrayList<FlowDto>();
			List<ProcessDto> processDtos = dataApi.findProcessDto(productId);
			boolean fl1 = false;
			boolean fl2 = false;
			for (int i = 0; i < list.size(); i++) {
				FlowDto orderFlow = list.get(i);
				for (ProcessDto processDto : processDtos) {
					if (orderFlow.getCurrentProcessId().equals(processDto.getProcessId())) {
						orderFlow.setCurrentProcessName(processDto.getProcessName());
					}
					if (orderFlow.getNextProcessId().equals(processDto.getProcessId())) {
						orderFlow.setNextProcessName(processDto.getProcessName());
					}
				}
				for (UserDto user : userDtos) {
					if (user.getUid().equals(orderFlow.getHandleUid())) {
						orderFlow.setHandleName(user.getName());
						break;
					}
				}
				if (!fl2) {
					tempList.add(orderFlow);
				} else {
					fl2 = false;
					continue;
				}
				fl1 = false;
				if (StringUtils.isNotEmpty(orderFlow.getBackReason())) {
					if (orderFlow.getIsNewWalkProcess() == 1) {
						Iterator<FlowDto> flowList = tempList.iterator();
						while (flowList.hasNext()) {
							FlowDto temp = flowList.next();
							if (temp.getCurrentProcessId().equals(orderFlow.getNextProcessId())) {
								fl1 = true;
							}
							if (fl1) {
								flowList.remove();
							}
						}
					} else {
						for (FlowDto temp : tempList) {
							if (temp.getCurrentProcessId().equals(orderFlow.getNextProcessId())) {
								temp = orderFlow;
								fl2 = true;
							}
						}
						tempList.remove(orderFlow);
					}
				}
			}
			RespHelper.setSuccessData(resp, tempList);
		} catch (Exception e) {
			logger.error("获取订单流水异常,参数：" + dto.toString(), e);
			return RespHelper.setFailData(resp, null, RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}

	@Override
	public RespData<FlowDto> selectOrderFlowListRepeat(@RequestBody FlowDto dto) {
		RespData<FlowDto> resp = new RespData<FlowDto>();
		try {
			BaseListDto temp = new BaseListDto();
			UserDto userDto = userApi.getUserDto();
			temp.setOrderNo(dto.getOrderNo());
			temp = baseListService.find(temp);

			Integer productId = Integer.valueOf(temp.getCityCode() + temp.getProductCode());
			if (productId == null) {
				productId = 440301;
			}

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("productId", productId);
			Map<String, Object> authMap = dataApi.selectAuthorityByProductId(params).getData();
			List<String> authList = userDto.getAuthIds();
			List<FlowDto> list = flowService.search(dto);
			Iterator<FlowDto> its = list.iterator();
			List<ProcessDto> processDtos = dataApi.findProcessDto(productId);
			List<UserDto> userDtos = userApi.selectAllUserDto();
			FlowDto orderFlow = null;
			while (its.hasNext()) {
				orderFlow = its.next();
				try {
					for (ProcessDto processDto : processDtos) {
						if (orderFlow.getCurrentProcessId().equals(processDto.getProcessId())) {
							orderFlow.setCurrentProcessName(processDto.getProcessName());
						}
						if (orderFlow.getNextProcessId().equals(processDto.getProcessId())) {
							orderFlow.setNextProcessName(processDto.getProcessName());
						}
					}
					for (UserDto user : userDtos) {
						if (user.getUid().equals(orderFlow.getHandleUid())) {
							orderFlow.setHandleName(user.getName());
							break;
						}
					}

					if (orderFlow.getCurrentProcessId().equals("wanjie")) {
						continue;
					}
					// 删除待还款节点
					if (orderFlow.getCurrentProcessId().equals("fddRepayment")) {
						its.remove();
						continue;
					}

					// 机构节点重构
					if (userDto.getAgencyId() > 1) {
						if (ArrayUtils.contains(FlowDto.arrayRiskName, orderFlow.getCurrentProcessName())) {
							this.logger.info(orderFlow.getCurrentProcessName() + "##" + orderFlow.getNextProcessName());
							if (ArrayUtils.contains(FlowDto.arrayRiskName, orderFlow.getNextProcessName())) {
								its.remove();
								continue;
							}
							this.logger.info("to noAuth!");
							orderFlow.setCurrentProcessId("noAuth");
							orderFlow.setCurrentProcessName(FlowDto.ORDER_NODE_RISK);
						}
						// 节点及节点详情展示控制
						if (ArrayUtils.contains(FlowDto.arrayNodeWithout, orderFlow.getCurrentProcessId())) {
							its.remove();
							continue;
						} else if (ArrayUtils.contains(FlowDto.arrayNodeDetailWithout,
								orderFlow.getCurrentProcessId())) {
							orderFlow.setCurrentProcessId("");
						} else if (StringUtils.isNotEmpty(orderFlow.getCurrentProcessId())
								&& ArrayUtils.contains(FlowDto.arrayNodeDetailNeed, orderFlow.getCurrentProcessId())) {
							orderFlow.setCurrentProcessId(orderFlow.getCurrentProcessId());
						}
					}

					String authA = MapUtils.getString(authMap, orderFlow.getCurrentProcessId() + "[A]");
					String authB = MapUtils.getString(authMap, orderFlow.getCurrentProcessId() + "[B]");
					if (!(authList.contains(authA + "") || authList.contains(authB + ""))) {
						if ("customerReceivable".equals(orderFlow.getCurrentProcessId())) {
							orderFlow.setCurrentProcessName("客户回款");
							orderFlow.setCurrentProcessId("customerReceivable");
						} else {
							orderFlow.setCurrentProcessId("noAuth");
						}
					}
				} catch (Exception e) {
					this.logger.error("流水异常" + orderFlow.getHandleUid(), e);
				}
			}
			RespHelper.setSuccessData(resp, list);
		} catch (Exception e) {
			logger.error("获取订单流水（重复）异常,参数：" + dto.toString(), e);
			return RespHelper.setFailData(resp, null, RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}

}