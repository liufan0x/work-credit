package com.anjbo.controller.impl.ordinary;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.anjbo.bean.UserDto;
import com.anjbo.bean.ordinary.AuditFundDockingOrdinaryAuditDto;
import com.anjbo.bean.ordinary.AuditFundDockingOrdinaryDto;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.BaseController;
import com.anjbo.controller.api.OrderApi;
import com.anjbo.controller.api.UserApi;
import com.anjbo.controller.ordinary.IOrdinaryCapitalController;
import com.anjbo.service.ordinary.AuditFundDockingOrdinaryAuditService;
import com.anjbo.service.ordinary.AuditFundDockingOrdinaryBusinfoServer;
import com.anjbo.service.ordinary.AuditFundDockingOrdinaryService;

@RestController
public class OrdinaryCapitalController extends BaseController implements IOrdinaryCapitalController {
	private Logger log = Logger.getLogger(getClass());
	@Resource
	private OrderApi orderApi;
	@Resource
	private UserApi userApi;
	@Resource
	private AuditFundDockingOrdinaryService auditFundDockingOrdinaryService;
	@Resource
	private AuditFundDockingOrdinaryAuditService auditFundDockingOrdinaryAuditService;
	@Resource
	private AuditFundDockingOrdinaryBusinfoServer auditFundDockingOrdinaryBusinfoServer;

	/**
	 * 普通资方获取影像资料分类
	 * 
	 * @param request
	 * @param map
	 *            资料推送是时会调用此接口
	 * @return
	 */
	@Override
	public RespDataObject<Map<String, Object>> loadOrdinaryBusInfo(@RequestBody Map<String, Object> map) {
		RespDataObject<Map<String, Object>> result = new RespDataObject<Map<String, Object>>();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try {
			if (StringUtils.isBlank(MapUtils.getString(map, "orderNo", ""))) {
				result.setMsg("缺少订单编号");
				return result;
			}
			if (StringUtils.isBlank(MapUtils.getString(map, "ordinaryFund", ""))) {
				result.setMsg("缺少资金编号");
				return result;
			}
			result.setData(auditFundDockingOrdinaryBusinfoServer.getOrdinaryBusinfoType(map));
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			log.error("推送普通资金方信息加载影像资料失败", e);
		}
		return result;
	}

	/**
	 * 普通资方保存信息
	 * 
	 * @param request
	 * @param map
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public RespStatus ordinaryApply(@RequestBody Map<String, Object> map) {
		// 保存资方信息表。
		RespStatus result = new RespStatus();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try {
			if (!"04".equals(MapUtils.getObject(map, "productCode"))) {
				UserDto user = userApi.getUserDto();
				if (map.containsKey("kgOrdinary")) { // 借款信息
					Object kgOrdinary = MapUtils.getObject(map, "kgOrdinary");
					Map<String, Object> tmp = null;
					if (kgOrdinary instanceof Map) {
						tmp = (Map<String, Object>) kgOrdinary;
						tmp.put("fundId", MapUtils.getString(map, "ordinaryFund"));
						tmp.put("createUid", user.getUid());
						tmp.put("updateUid", user.getUid());
						tmp.put("receivableForTime", MapUtils.getString(map, "receivableForTime"));
					}
					if (tmp == null) {
						return result;
					}
					auditFundDockingOrdinaryService.insert(tmp);
				}
				if (map.containsKey("kgOrdinaryAudit")) { // 审批信息
					Object kgOrdinaryAudit = MapUtils.getObject(map, "kgOrdinaryAudit");
					Map<String, Object> tmp = null;
					if (kgOrdinaryAudit instanceof Map) {
						tmp = (Map<String, Object>) kgOrdinaryAudit;
						tmp.put("fundId", MapUtils.getString(map, "ordinaryFund"));
						tmp.put("createUid", user.getUid());
						tmp.put("updateUid", user.getUid());
						tmp.put("paymentBankId", MapUtils.getString(tmp, "paymentBankId") == null ? 0
								: MapUtils.getString(tmp, "paymentBankId")); // 回款银行
						tmp.put("paymentBankSubId", MapUtils.getString(tmp, "paymentBankSubId") == null ? 0
								: MapUtils.getString(tmp, "paymentBankSubId"));
					}
					if (tmp == null) {
						return result;
					}
					auditFundDockingOrdinaryAuditService.insert(tmp);
				}
			}
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			log.error("分配资金方保存普通资方推送信息异常,异常信息为:", e);
		}
		return result;
	}

	/**
	 * 普通资方推送信息查询
	 * 
	 * @param request
	 * @param obj
	 * @return
	 */
	@Override
	public RespDataObject<Map<String, Object>> ordinaryDetail(@RequestBody Map<String, Object> map) {
		RespDataObject<Map<String, Object>> result = new RespDataObject<Map<String, Object>>();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		String orderNo = MapUtils.getString(map, "orderNo");
		String fundId = MapUtils.getString(map, "fundId");
		try {
			if (StringUtils.isBlank(orderNo)) {
				result.setMsg("加载资料推送详情失败,订单编号不能为空!");
				return result;
			}
			if (StringUtils.isBlank(fundId)) {
				result.setMsg("加载资料推送详情失败,资方编号不能为空!");
				return result;
			}
			Map<String, Object> maps = new HashMap<String, Object>();
			AuditFundDockingOrdinaryDto auditFundDockingOrdinaryDto = auditFundDockingOrdinaryService
					.findByOrdinary(map);
			AuditFundDockingOrdinaryAuditDto auditDto = auditFundDockingOrdinaryAuditService
					.findByOrdinaryAudit(map);
			maps.put("ordinary", auditFundDockingOrdinaryDto);
			maps.put("ordinaryAudit", auditDto);
			result.setData(maps);
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			log.error("查询资料推送信息异常,异常信息为:", e);
		}
		return result;
	}

	/**
	 * 普通资方影像资料删除
	 */
	@Override
	public RespStatus deleteOrdinaryImg(@RequestBody Map<String, Object> map) {
		RespStatus result = new RespStatus();
		try {
			if (!map.containsKey("ids")) {
				result.setMsg("删除影像资料异常,缺少主键");
				return result;
			}
			auditFundDockingOrdinaryBusinfoServer.deleteImg(map);
			RespHelper.setSuccessRespStatus(result);
		} catch (Exception e) {
			log.error("推送华安数据删除影像资料异常,异常信息为:", e);
			RespHelper.setFailRespStatus(result, RespStatusEnum.FAIL.getMsg());
		}
		return result;
	}

	/**
	 * 普通资方影像资料添加
	 */
	@Override
	public RespStatus addOrdinaryImg(@RequestBody Map<String, Object> map) {
		RespStatus result = new RespStatus();
		try {
			if (!map.containsKey("orderNo")) {
				result.setMsg("订单编号不能为空");
				return result;
			}
			if (!map.containsKey("fundId")) {
				result.setMsg("资方编号不能为空");
				return result;
			}
			map.put("createUid", getUid());
			auditFundDockingOrdinaryBusinfoServer.insretImg(map);
			RespHelper.setSuccessRespStatus(result);
		} catch (Exception e) {
			log.error("资金方推送华安信息上传影像资料失败", e);
			RespHelper.setFailRespStatus(result, RespStatusEnum.FAIL.getMsg());
		}
		return result;
	}

}
