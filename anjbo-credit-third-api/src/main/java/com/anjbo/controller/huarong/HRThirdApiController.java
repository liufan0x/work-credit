package com.anjbo.controller.huarong;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.BaseController;
import com.anjbo.service.huarong.ApplyService;
import com.anjbo.service.huarong.BorrowRepaymentService;
import com.anjbo.service.huarong.FileApplyService;
import com.anjbo.service.huarong.LendService;
import com.anjbo.service.huarong.QueryHrSendDataService;
import com.anjbo.service.huarong.QueryLoanStatusService;
import com.anjbo.service.huarong.RepaymentRegisterService;
import com.anjbo.utils.huarong.ReturnParam;
/**
 * 华融控制器
 * @author xufx
 * @date 2017-8-22 下午05:14:41
 */
@Controller
@RequestMapping("/credit/third/api/hr/")
public class HRThirdApiController extends BaseController{
	
	Logger log = Logger.getLogger(HRThirdApiController.class);	
	@Resource
	private ApplyService applyService;//申请信息	
	@Resource
	private FileApplyService fileApplyService;//申请附件	
	@Resource
	private LendService lendService;//放款接口		
	@Resource
	private BorrowRepaymentService borrowRepaymentService;//应还款		
	@Resource
	private RepaymentRegisterService repaymentRegisterService;//回款	
	@Resource
	private QueryLoanStatusService queryLoanStatusService;//查询放款审批状态接口	
	
	@Resource
	private QueryHrSendDataService queryHrSendDataService;//查询快鸽通过此项目发送到华融的数据接口	
	/**
	 * 申请信息接口
	 * @author xufx
	 * @date 2017-8-22 下午05:14:41
	 */
	@RequestMapping("/v/apply")
	@ResponseBody
	public RespStatus apply(HttpServletRequest request,
			@RequestBody Map<String,Object> map) {

		RespStatus resp = new RespStatus();
		try {
		resp=applyService.applySend(map);
		
		} catch (Exception e) {
			log.error("申请信息发送失败");
			RespHelper.setFailRespStatus(resp, "系统异常，调用申请数据发送接口失败");
			e.printStackTrace();
			
		}
		return resp;
	}
	/**
	 * 附件发送接口
	 * @author xufx
	 * @date 2017-8-22 下午05:14:41
	 */
	@RequestMapping("/v/fileApply")
	@ResponseBody 
	public RespDataObject<String> fileApply(HttpServletRequest request,
			@RequestBody List<Map<String,Object>> list) {
		RespDataObject<String> resp = new RespDataObject<String>();
		try {
		resp=fileApplyService.fileApplySend(list);
		
		} catch (Exception e) {
			log.error("附件发送发送失败");
			RespHelper.setFailDataObject(resp, null, "系统异常，调用发送附件接口失败");
			e.printStackTrace();
		}
		return resp;
	}
	
	/**
	 * 放款接口
	 * @author xufx
	 * @date 2017-8-22 下午05:14:41
	 */
	@RequestMapping("/v/lend")
	@ResponseBody
	public RespStatus lend(HttpServletRequest request,
			@RequestBody Map<String,Object> map) {
		RespStatus resp = new RespStatus();
		try {
		resp=lendService.lendSend(map);
		
		} catch (Exception e) {
			log.error("放款接口发送失败");
			RespHelper.setFailRespStatus(resp, "系统异常，调用放款接口失败");
			e.printStackTrace();
		}
		return resp;
	}	
	/**
	 * 应还款接口
	 * @author chenzm
	 * @date 2017-8-22 下午05:14:41
	 */
	@RequestMapping("/v/repayment")
	@ResponseBody
	public RespStatus repayment(HttpServletRequest request,
			@RequestBody Map<String,Object> requestMap) {
		
	
		RespStatus resp = new RespStatus();
		try {
		resp=borrowRepaymentService.borrowRepaymentSend(requestMap);
		
		} catch (Exception e) {
			log.error("应还款接口发送失败");
			RespHelper.setFailRespStatus(resp, "系统异常，调用应还款接口失败");
			e.printStackTrace();
		}
		return resp;
	}
	
	/**
	 * 回款接口
	 * @author chenzm
	 * @date 2017-8-22 下午05:14:41
	 */
	@RequestMapping("/v/rayMentPlan")
	@ResponseBody
	public RespStatus PayMentPlan(HttpServletRequest request,
			@RequestBody Map<String,Object> map) {
		
	
		RespStatus resp = new RespStatus();
		try {
		resp=repaymentRegisterService.repaymentRegisterSend(map);
		
		} catch (Exception e) {
			log.error("回款接口异常");
			RespHelper.setFailRespStatus(resp, "系统异常，调用回款接口失败");
			e.printStackTrace();
		}
		return resp;
	}
	/**
	 * 放款状态查询接口
	 * @author chenzm
	 * @date 2017-8-22 下午05:14:41
	 */
	@RequestMapping("queryLoanStatus")
	@ResponseBody
	public RespDataObject<Map> queryLoanStatus(HttpServletRequest request,
			@RequestBody Map<String,Object> map) {
		
		RespDataObject<Map> resp = new RespDataObject<Map>();
		try {
		resp=queryLoanStatusService.queryLoanStatusSend(map);
		
		} catch (Exception e) {
			log.error("放款状态查失败");
			RespHelper.setFailDataObject(resp, null, "系统异常，调用查询放款接口失败");
			e.printStackTrace();
		}
		return resp;
	}
	/**
	 * 返回申请数据接口
	 * @author xufx
	 * @date 2017-8-22 下午05:14:41
	 */
	@RequestMapping("queryApply")
	@ResponseBody
	public RespDataObject<Map> queryApply(HttpServletRequest request,
			@RequestBody Map<String,Object> map) {
		
		Map param_map=new HashMap<String,Object>();
		
		boolean flag=false;
	
		RespDataObject<Map> resp = new RespDataObject<Map>();
		try {
			
		String orderNo=map.get("orderNo").toString();
		
		log.info("queryApply返回申请数据接口,请求订单号:"+orderNo);
		   
		Map lcAppoint=new HashMap();
		Map lcApplAppt=new HashMap();
		Map lcApptIndiv=new HashMap();
		Map kgAppoint=new HashMap();
		Map kgApproval=new HashMap();
		Map kgHouse=new HashMap();
		Map kgIndiv=new HashMap();
		
		lcAppoint=queryHrSendDataService.getLcAppoint(orderNo);
		if(lcAppoint==null) {
			flag=true;
		}else if(lcAppoint.isEmpty()) {
			flag=true;
		}
		lcApplAppt=queryHrSendDataService.getLcApplAppt(orderNo);
		if(lcApplAppt==null) {
			flag=true;
		}else if(lcApplAppt.isEmpty()) {
			flag=true;
		}
		lcApptIndiv=queryHrSendDataService.getLcApptIndiv(orderNo);
		if(lcApptIndiv==null) {
			flag=true;
		}else if(lcApptIndiv.isEmpty()) {
			flag=true;
		}
		kgAppoint=queryHrSendDataService.getKgAppoint(orderNo);
		if(kgAppoint==null) {
			flag=true;
		}else if(kgAppoint.isEmpty()) {
			flag=true;
		}
		kgApproval=queryHrSendDataService.getKgApproval(orderNo);
		if(kgApproval==null) {
			flag=true;
		}else if(kgApproval.isEmpty()) {
			flag=true;
		}
		kgHouse=queryHrSendDataService.getKgHouse(orderNo);	
		if(kgHouse==null) {
			flag=true;
		}else if(kgHouse.isEmpty()) {
			flag=true;
		}
		kgIndiv=queryHrSendDataService.getKgIndiv(orderNo);
		if(kgIndiv==null) {
			flag=true;
		}else if(kgIndiv.isEmpty()) {
			flag=true;
		}
		
		param_map.put("lcAppoint", lcAppoint);
		param_map.put("lcApplAppt", lcApplAppt);
		param_map.put("lcApptIndiv", lcApptIndiv);
		param_map.put("kgAppoint", kgAppoint);
		param_map.put("kgApproval", kgApproval);
		param_map.put("kgHouse", kgHouse);
		param_map.put("kgIndiv", kgIndiv);
		resp.setData(param_map);
		if(flag) {
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg("查询失败");
		}else {
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg("查询成功");
		}
		log.info("查询结果resp信息:"+resp.getMsg());
		} catch (Exception e) {
			log.error("queryApply返回申请数据接口失败");
			RespHelper.setFailDataObject(resp, null, "系统异常，调用返回申请数据接口失败");
			e.printStackTrace();
		}
		return resp;
	}
	
	/**
	 * 返回附件数据接口
	 * @author xufx
	 * @date 2017-8-22 下午05:14:41
	 */
	@RequestMapping("queryFileApply")
	@ResponseBody
	public RespDataObject<List> queryFileApply(HttpServletRequest request,
			@RequestBody Map<String,Object> map) {
		
	
		RespDataObject<List> resp = new RespDataObject<List>();
		try {
		List list_FileApply=queryHrSendDataService.getFileApply(map.get("orderNo").toString());
		log.info("queryApply返回附件数据接口,请求订单号:"+map.get("orderNo").toString());
		resp.setData(list_FileApply);
		
		if(list_FileApply!=null){
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg("查询成功");
		}else {
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg("查询失败");
		}
		log.info("查询结果resp信息:"+resp.getMsg());
		} catch (Exception e) {
			log.error("queryFileApply返回附件数据接口失败");
			RespHelper.setFailDataObject(resp, null, "系统异常，返回附件数据接口失败");
			e.printStackTrace();
		}
		return resp;
	}
	
	/**
	 * 返回放款数据接口
	 * @author xufx
	 * @date 2017-8-22 下午05:14:41
	 */
	@RequestMapping("queryLend")
	@ResponseBody
	public RespDataObject<Map> queryLend(HttpServletRequest request,
			@RequestBody Map<String,Object> map) {
		
		log.info("queryLend返回放款数据接口,请求订单号:"+map.get("orderNo").toString());
		RespDataObject<Map> resp = new RespDataObject<Map>();
		try {
		Map map_BorrowLend=queryHrSendDataService.getBorrowLend(map.get("orderNo").toString());
		resp.setData(map_BorrowLend);
		if(map_BorrowLend!=null){
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg("查询成功");
		}else {
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg("查询失败");
		}
		log.info("查询结果resp信息:"+resp.getMsg());
		} catch (Exception e) {
			log.error("queryLend返回放款数据接口失败");
			RespHelper.setFailDataObject(resp, null, "系统异常，返回放款数据接口失败");
			e.printStackTrace();
		}
		System.out.println(resp.getCode()+resp.getMsg());
		return resp;
	}
	
	
	/**
	 * 返回应还款数据接口
	 * @author xufx
	 * @date 2017-8-22 下午05:14:41
	 */
	@RequestMapping("queryRepayment")
	@ResponseBody
	public RespDataObject<Map> queryRepayment(HttpServletRequest request,
			@RequestBody Map<String,Object> map) {
		
		log.info("queryRepayment返回应还款数据接口,请求订单号:"+map.get("orderNo").toString());
		RespDataObject<Map> resp = new RespDataObject<Map>();
		try {
		Map map_BorrowRepayment=queryHrSendDataService.getBorrowRepayment(map.get("orderNo").toString());
		resp.setData(map_BorrowRepayment);
		if(map_BorrowRepayment!=null){
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg("查询成功");
		}else {
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg("查询失败");
		}
		log.info("查询结果resp信息:"+resp.getMsg());
		} catch (Exception e) {
			log.error("queryRepayment返回应还款数据接口失败");
			RespHelper.setFailDataObject(resp, null, "系统异常，返回应还款数据接口失败");
			e.printStackTrace();
		}
		return resp;
	}
	
	/**
	 * 返回回款数据接口
	 * @author xufx
	 * @date 2017-8-22 下午05:14:41
	 */
	@RequestMapping("queryRayMentPlan")
	@ResponseBody
	public RespDataObject<Map> queryRayMentPlan(HttpServletRequest request,
			@RequestBody Map<String,Object> map) {
		
		log.info("queryRayMentPlan返回回款数据接口,请求订单号:"+map.get("orderNo").toString());
		RespDataObject<Map> resp = new RespDataObject<Map>();
		try {
		Map map_RepaymentRegister=queryHrSendDataService.getRepaymentRegister(map.get("orderNo").toString());
		resp.setData(map_RepaymentRegister);
		if(map_RepaymentRegister!=null){
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg("查询成功");
		}else {
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg("查询失败");
		}
		log.info("查询结果resp信息:"+resp.getMsg());
		} catch (Exception e) {
			log.error("queryRayMentPlan返回回款数据接口");
			RespHelper.setFailDataObject(resp, null, "系统异常，返回回款数据接口失败");
			e.printStackTrace();
		}
		return resp;
	}

}
