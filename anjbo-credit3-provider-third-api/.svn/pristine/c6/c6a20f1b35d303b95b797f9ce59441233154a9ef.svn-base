package com.anjbo.controller.impl.hrtrust;

import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.BaseController;
import com.anjbo.controller.hrtrust.IHrtrustAbutmentController;
import com.anjbo.dao.hrtrust.HrtrustRepaymentPlanMapper;
import com.anjbo.dao.hrtrust.HrtrustKgAppointMapper;
import com.anjbo.dao.hrtrust.HrtrustLcAppointMapper;
import com.anjbo.dao.hrtrust.HrtrustRepaymentInfoMapper;
import com.anjbo.service.hrtrust.*;
import java.util.Date;

import org.apache.commons.collections.MapUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 华融控制器
 * 
 * @author xufx
 * @date 2017-8-22 下午05:14:41
 */
@RestController
public class HrtrustAbutmentController extends BaseController implements IHrtrustAbutmentController {

	@Resource
	private QueryHrSendDataService queryHrSendDataService;// 查询快鸽通过此项目发送到华融的数据接口

	@Resource
	private HrtrustService hrtrustService;
	@Resource
	private HrtrustLcAppointMapper lcAppointMapper; // 申请预约信息表
	@Resource
	private HrtrustKgAppointMapper kgAppointMapper; // 快鸽提单业务表
	@Resource
	private HrtrustRepaymentPlanMapper borrowRepaymentMapper; // 应还款信息表
	@Resource
	private HrtrustRepaymentInfoMapper repaymentRegisterMapper; // 回款信息表

	@Override
	public RespStatus apply(@RequestBody Map<String, Object> map) {
		try {
			hrtrustService.setOrderNo(MapUtils.getString(map, "orderNo"));
			return hrtrustService.applySend(map);
		} catch (Exception e) {
			logger.error("申请信息发送失败", e);
			return RespHelper.setFailRespStatus(new RespStatus(), "系统异常，调用申请数据发送接口失败");
		}
	}

	
	/**
	 * 附件发送接口
	 * 
	 * @author xufx
	 * @date 2017-8-22 下午05:14:41
	 */
	@Override
	@SuppressWarnings("unchecked")
	public RespStatus fileApply(@RequestBody Map<String, Object> map) {
		try {
			hrtrustService.setOrderNo(MapUtils.getString(map, "orderNo"));
			new Thread(new Runnable() {
				@Override
				public void run() {
					hrtrustService.fileApplySend((List<Map<String, Object>>) MapUtils.getObject(map, "imgs"));
				}
			}).start();
			return RespHelper.setSuccessRespStatus(new RespStatus());
		} catch (Exception e) {
			logger.error("附件发送发送失败", e);
			return RespHelper.setFailRespStatus(new RespStatus(), "系统异常，调用发送附件接口失败");
		}
	}

	/**
	 * 放款接口
	 * 
	 * @author xufx
	 * @date 2017-8-22 下午05:14:41
	 */
	@Override
	public RespStatus lend(@RequestBody Map<String, Object> map) {
		try {
			hrtrustService.setOrderNo(MapUtils.getString(map, "orderNo"));
			return hrtrustService.lendSend(map);
		} catch (Exception e) {
			logger.error("放款接口发送失败", e);
			return RespHelper.setFailRespStatus(new RespStatus(), "系统异常，调用放款接口失败");
		}
	}

	/**
	 * 应还款接口
	 * 
	 * @author chenzm
	 * @date 2017-8-22 下午05:14:41
	 */
	@Override
	public RespStatus repayment(@RequestBody Map<String, Object> requestMap) {
		try {
			hrtrustService.setOrderNo(MapUtils.getString(requestMap, "orderNo"));
			return hrtrustService.borrowRepaymentSend(requestMap);
		} catch (Exception e) {
			logger.error("应还款接口发送失败", e);
			return RespHelper.setFailRespStatus(new RespStatus(), "系统异常，调用应还款接口失败");
		}
	}

	/**
	 * 回款接口
	 * 
	 * @author chenzm
	 * @date 2017-8-22 下午05:14:41
	 */
	@Override
	public RespStatus PayMentPlan(@RequestBody Map<String, Object> map) {
		try {
			hrtrustService.setOrderNo(MapUtils.getString(map, "orderNo"));
			return hrtrustService.repaymentRegisterSend(map);
		} catch (Exception e) {
			logger.error("回款接口异常", e);
			return RespHelper.setFailRespStatus(new RespStatus(), "系统异常，调用回款接口失败");
		}
	}

	/**
	 * 放款状态查询接口
	 * 
	 * @author chenzm
	 * @date 2017-8-22 下午05:14:41
	 */
	@Override
	public RespDataObject<Map<String, Object>> queryLoanStatus(@RequestBody Map<String, Object> map) {
		try {
			hrtrustService.setOrderNo(MapUtils.getString(map, "orderNo"));
			return hrtrustService.queryLoanStatusSend(map);
		} catch (Exception e) {
			logger.error("放款状态查失败", e);
			return RespHelper.setFailDataObject(new RespDataObject<Map<String, Object>>(), null, "系统异常，调用回款接口失败");
		}
	}

	/**
	 * 返回申请数据接口
	 * 
	 * @author xufx
	 * @date 2017-8-22 下午05:14:41
	 */
	@Override
	public RespDataObject<Map<String, Object>> queryApply(@RequestBody Map<String, Object> map) {
		RespDataObject<Map<String, Object>> resp = new RespDataObject<Map<String, Object>>();
		Map<String, Object> param_map = new HashMap<String, Object>();
		try {
			String orderNo = MapUtils.getString(map, "orderNo");
			logger.info("queryApply返回申请数据接口,请求订单号:" + orderNo);
			Map<String, Object> lcAppoint = queryHrSendDataService.getLcAppoint(orderNo);
			Map<String, Object> lcApplAppt = queryHrSendDataService.getLcApplAppt(orderNo);
			Map<String, Object> lcApptIndiv = queryHrSendDataService.getLcApptIndiv(orderNo);
			Map<String, Object> kgAppoint = queryHrSendDataService.getKgAppoint(orderNo);
			Map<String, Object> kgApproval = queryHrSendDataService.getKgApproval(orderNo);
			Map<String, Object> kgHouse = queryHrSendDataService.getKgHouse(orderNo);
			Map<String, Object> kgIndiv = queryHrSendDataService.getKgIndiv(orderNo);
			Map<String, Object> kgLoan = queryHrSendDataService.selectHrtrustLoan(orderNo);
			param_map.put("lcAppoint", lcAppoint);
			param_map.put("lcApplAppt", lcApplAppt);
			param_map.put("lcApptIndiv", lcApptIndiv);
			param_map.put("kgAppoint", kgAppoint);
			param_map.put("kgApproval", kgApproval);
			param_map.put("kgHouse", kgHouse);
			param_map.put("kgIndiv", kgIndiv);
			param_map.put("kgLoan", kgLoan);
			resp.setData(param_map);
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg("查询成功");
		} catch (Exception e) {
			logger.error("queryApply返回申请数据接口失败", e);
			RespHelper.setFailDataObject(resp, null, "系统异常，调用返回申请数据接口失败");
		}
		return resp;
	}

	/**
	 * 返回附件数据接口
	 * 
	 * @author xufx
	 * @date 2017-8-22 下午05:14:41
	 */
	@Override
	public RespDataObject<List<Map<String, Object>>> queryFileApply(@RequestBody Map<String, Object> map) {
		RespDataObject<List<Map<String, Object>>> resp = new RespDataObject<List<Map<String, Object>>>();
		try {
			List<Map<String, Object>> list_FileApply = queryHrSendDataService
					.getFileApply(MapUtils.getString(map, "orderNo"));
			resp.setData(list_FileApply);
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg("查询成功");
		} catch (Exception e) {
			logger.error("queryFileApply返回附件数据接口失败", e);
			RespHelper.setFailDataObject(resp, null, "系统异常，返回附件数据接口失败");
		}
		return resp;
	}

	/**
	 * 返回放款数据接口
	 * 
	 * @author xufx
	 * @date 2017-8-22 下午05:14:41
	 */
	@Override
	public RespDataObject<Map<String, Object>> queryLend(@RequestBody Map<String, Object> map) {
		RespDataObject<Map<String, Object>> resp = new RespDataObject<Map<String, Object>>();
		try {
			Map<String, Object> map_BorrowLend = queryHrSendDataService
					.selectHrtrustLoan(MapUtils.getString(map, "orderNo"));
			resp.setData(map_BorrowLend);
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg("查询成功");
		} catch (Exception e) {
			logger.error("queryLend返回放款数据接口失败", e);
			RespHelper.setFailDataObject(resp, null, "系统异常，返回放款数据接口失败");
		}
		return resp;
	}

	/**
	 * 返回应还款数据接口
	 * 
	 * @author xufx
	 * @date 2017-8-22 下午05:14:41
	 */
	@Override
	public RespDataObject<Map<String, Object>> queryRepayment(@RequestBody Map<String, Object> map) {
		RespDataObject<Map<String, Object>> resp = new RespDataObject<Map<String, Object>>();
		try {
			Map<String, Object> map_BorrowRepayment = queryHrSendDataService
					.getBorrowRepayment(MapUtils.getString(map, "orderNo"));
			resp.setData(map_BorrowRepayment);
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg("查询成功");
		} catch (Exception e) {
			logger.error("queryRepayment返回应还款数据接口失败", e);
			RespHelper.setFailDataObject(resp, null, "系统异常，返回应还款数据接口失败");
		}
		return resp;
	}

	/**
	 * 返回回款数据接口 查看还款计划
	 * 
	 * @author xufx
	 * @date 2017-8-22 下午05:14:41
	 */
	@Override
	public RespDataObject<Map<String, Object>> queryRayMentPlan(@RequestBody Map<String, Object> map) {
		RespDataObject<Map<String, Object>> resp = new RespDataObject<Map<String, Object>>();
		try {
			Map<String, Object> map_RepaymentRegister = queryHrSendDataService
					.getRepaymentRegister(MapUtils.getString(map, "orderNo"));
			resp.setData(map_RepaymentRegister);
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg("查询成功");
		} catch (Exception e) {
			logger.error("queryRayMentPlan返回回款数据接口", e);
			RespHelper.setFailDataObject(resp, null, "系统异常，返回回款数据接口失败");
		}
		return resp;
	}

	/**
	 * 审批状态查询
	 **/
	@Override
	public RespDataObject<Map<String, Object>> allStatus(@RequestBody Map<String, Object> map) {
		RespDataObject<Map<String, Object>> resp = new RespDataObject<Map<String, Object>>();
		try {
			String orderNo = MapUtils.getString(map, "orderNo");
			Map<String, Object> maps = lcAppointMapper.getLcAppoint(orderNo);
			if (maps == null) {
				resp.setCode(RespStatusEnum.SUCCESS.getCode());
				resp.setMsg("待推送");
				return resp;
			}
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg("已推送");

			resp = queryLoanStatus(map);
			if (resp.getCode().equals("FAIL") && resp.getMsg().equals("待推送"))
				return resp;

			int dealStatus = MapUtils.getIntValue(resp.getData(), "dealStatus"); // 放款状态
			int grantStatus = MapUtils.getIntValue(resp.getData(), "grantStatus"); // 勾兑状态

			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			if (dealStatus == 997 && grantStatus == 997) { // 勾兑，放款成功
				resp.setMsg("待推送应还款信息");
				if (borrowRepaymentMapper.getBorrowRepayment(orderNo) != null) { // 推送应还款计划
					resp.setMsg("待推送回款计划");
					if (repaymentRegisterMapper.getRepaymentRegister(orderNo) != null) { // 回款批次表
						resp.setMsg("推送完成");
					}
				}
			} else if (dealStatus == 111) {
				resp.setMsg("待审批");
			} else if (dealStatus == 992) {
				resp.setMsg("已打回");
			} else if (dealStatus == 998) {
				resp.setMsg("已否决");
			} else if (grantStatus == 111) {
				resp.setMsg("待勾兑");
			}
		} catch (Exception e) {
			logger.error("放款状态查失败", e);
			RespHelper.setFailDataObject(resp, null, "系统异常，调用查询放款接口失败");
		}
		return resp;
	}

}
