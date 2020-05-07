package com.anjbo.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.bean.order.OrderBaseBorrowDto;
import com.anjbo.bean.order.OrderBaseBorrowRelationDto;
import com.anjbo.bean.order.OrderFlowDto;
import com.anjbo.bean.order.OrderListDto;
import com.anjbo.bean.risk.FinalAuditDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.Constants;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.FinalAuditService;
import com.anjbo.service.FirstAuditService;
import com.anjbo.utils.AmsUtil;
import com.anjbo.utils.CommonDataUtil;
import com.anjbo.utils.ConfigUtil;
import com.anjbo.utils.HttpUtil;
import com.google.gson.Gson;

/**
 * 终审
 * @author huanglj
 *
 */
@Controller
@RequestMapping("/credit/risk/final/v")
public class FinalAuditController extends BaseController{

	private static final Log log = LogFactory.getLog(FinalAuditController.class);
	
	@Resource
	private FinalAuditService finalAuditservice;
	@Resource
	private FirstAuditService firstAuditService;
	/**
	 * 新增终审审批信息
	 * @param request
	 * @param firstAudit
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/insert")
	public RespStatus insert(HttpServletRequest request,@RequestBody FinalAuditDto firstAudit){
		RespStatus result = new RespStatus();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			if(StringUtils.isBlank(firstAudit.getOrderNo())){
				result.setMsg("保存终审审批信息失败,缺少订单编号!");
				return result;
			}
			UserDto user = getUserDto(request);
			firstAudit.setCreateUid(user.getUid());
			firstAudit.setAuditTime(new Date());
			firstAudit.setHandleUid(user.getUid());
			finalAuditservice.insert(firstAudit);
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e){
			log.error("新增终审审批信息异常,异常信息为==>"+e.getMessage());
		}
		return result;
	}
	/**
	 * 终审审批详情
	 * @param request
	 * @param finalAudit
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/detail")
	public RespDataObject<FinalAuditDto> detail(HttpServletRequest request,@RequestBody FinalAuditDto finalAudit){
		RespDataObject<FinalAuditDto> result = new RespDataObject<FinalAuditDto>();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			if(StringUtils.isBlank(finalAudit.getOrderNo())){
				result.setMsg("查询终审详情参失败,缺少订单编号!");
				return result;
			}
			String orderNo = finalAudit.getOrderNo();
			
			finalAudit = finalAuditservice.detail(orderNo);
			UserDto user = null;
			if(StringUtils.isNotBlank(finalAudit.getHandleUid())){
				user = CommonDataUtil.getUserDtoByUidAndMobile(finalAudit.getHandleUid());
			}
			if(null!=user&&null!=finalAudit){
				finalAudit.setHandleName(user.getName());
			}
			result.setData(finalAudit);
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e){
			log.error("查询终审审批详情异常,异常信息为==>"+e.getMessage());
		}
		return result;
	}
	
	/**
	 * 终审编辑页加载数据
	 * @param request
	 * @param finalAudit
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/loadFinal")
	public RespDataObject<Map<String,Object>> loadFinal(HttpServletRequest request,@RequestBody FinalAuditDto finalAudit){
		RespDataObject<Map<String,Object>> result = new RespDataObject<Map<String,Object>>();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			if(StringUtils.isBlank(finalAudit.getOrderNo())){
				result.setMsg("加载终审审批信息异常,缺少参数!");
				return result;
			}
			String orderNo = finalAudit.getOrderNo();
			finalAudit = finalAuditservice.detail(orderNo);
			UserDto user = null;
			if(null!=finalAudit){
				user = CommonDataUtil.getUserDtoByUidAndMobile(finalAudit.getHandleUid());
			}
			if(null!=user){
				finalAudit.setHandleName(user.getName());
			}
			orderNo = AllocationFundController.getOrderNo(orderNo);
			
			Map<String,Object> data = new HashMap<String,Object>();
			Map<String,Boolean> map = firstAuditService.getCreditFlagMap(orderNo);
			data.put("auditFirstShow", map.get("auditFirst"));
			data.put("auditFinalShow", map.get("auditFinal"));
			data.put("auditFinal", finalAudit);
			result.setData(data);
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e){
			log.error("加载终审审批信息异常,异常信息为:",e);
		}
		return result;
	}
	/**
	 * 上报复核审批
	 * @param request
	 * @param finalAudit
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/reportReview")
	public RespStatus reportReview(HttpServletRequest request,@RequestBody FinalAuditDto finalAudit){
		RespStatus result = new RespStatus();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			if(StringUtils.isBlank(finalAudit.getOrderNo())){
				result.setMsg("缺少订单编号,上报复核审批失败!");
				return result;
			}
			boolean isSubmit = isSubmit(finalAudit.getOrderNo(),"auditFinal");
			if(isSubmit){
				result.setMsg("该订单已经被提交");
				return result;
			}
			boolean isWithdraw = isWithdraw(finalAudit.getOrderNo(),"auditFinal");
			if(isWithdraw){
				result.setMsg("该订单已经被撤回");
				return result;
			}
			if(finalAudit.getOfficerUid()!=null && finalAudit.getOfficerUidType()==0){//兼容app
			    finalAudit.setOfficerUidType(2);	
			}
			//获取登录人信息
			UserDto user = getUserDto(request);
			finalAudit.setCreateUid(user.getUid());
			finalAudit.setAuditTime(new Date());
			finalAudit.setAuditStatus(4);
			finalAudit.setHandleUid(user.getUid());
			finalAudit.setUpdateTime(new Date());
			finalAuditservice.insert(finalAudit);
			
			OrderFlowDto next = new OrderFlowDto();
			next.setHandleUid(user.getUid());
			next.setHandleName(user.getName());
			next.setUpdateUid(user.getUid());
			OrderListDto orderListDto = new OrderListDto();
			next.setOrderNo(finalAudit.getOrderNo());
			next.setCurrentProcessId("auditFinal");
			next.setCurrentProcessName("风控终审");
			next.setNextProcessId("auditReview");
			next.setNextProcessName("复核审批");
			UserDto nextUser = CommonDataUtil.getUserDtoByUidAndMobile(finalAudit.getNextHandleUid());
			orderListDto.setOrderNo(finalAudit.getOrderNo());
			orderListDto.setCurrentHandler(nextUser.getName());
			orderListDto.setCurrentHandlerUid(nextUser.getUid());
			result = goNextNode(next,orderListDto);
			
		} catch (Exception e){
			log.error("上报复核审批异常,异常信息为==>"+e.getMessage());
		}
		return result;
	}
	
	
	/**
	 * 上报首席风险官审批
	 * @param request
	 * @param finalAudit
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/reportOfficer")
	public RespStatus reportOfficer(HttpServletRequest request,@RequestBody FinalAuditDto finalAudit){
		RespStatus result = new RespStatus();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			if(StringUtils.isBlank(finalAudit.getOrderNo())){
				result.setMsg("缺少订单编号,上报首席风险官失败!");
				return result;
//			} else if(StringUtils.isBlank(finalAudit.getNextHandleUid())){
//				result.setMsg("请选择下一处理人");
//				return result;
			}
			boolean isSubmit = isSubmit(finalAudit.getOrderNo(),"auditFinal");
			if(isSubmit){
				result.setMsg("该订单已经被提交");
				return result;
			}
			boolean isWithdraw = isWithdraw(finalAudit.getOrderNo(),"auditFinal");
			if(isWithdraw){
				result.setMsg("该订单已经被撤回");
				return result;
			}
			//获取登录人信息
			UserDto user = getUserDto(request);
			finalAudit.setCreateUid(user.getUid());
			finalAudit.setAuditTime(new Date());
			finalAudit.setAuditStatus(3);
			finalAudit.setHandleUid(user.getUid());
			finalAuditservice.insert(finalAudit);
			
			
			UserDto nextUser = CommonDataUtil.getUserDtoByUidAndMobile(finalAudit.getNextHandleUid());
			OrderFlowDto next = new OrderFlowDto();
			next.setOrderNo(finalAudit.getOrderNo());
			next.setCurrentProcessId("auditFinal");
			next.setCurrentProcessName("风控终审");
			next.setNextProcessId("auditOfficer");
			next.setNextProcessName("首席风险官审批");
			next.setHandleUid(user.getUid());
			next.setHandleName(user.getName());
			next.setUpdateUid(user.getUid());
			
			OrderListDto orderListDto = new OrderListDto();
			orderListDto.setOrderNo(finalAudit.getOrderNo());
			orderListDto.setCurrentHandler(nextUser.getName());
			orderListDto.setCurrentHandlerUid(nextUser.getUid());
			result = goNextNode(next,orderListDto);
			
		} catch (Exception e){
			log.error("上报首席风险官审批异常,异常信息为==>"+e.getMessage());
		}
		return result;
	}
	
	/**
	 * 终审审批通过
	 * @param request
	 * @param finalAudit
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/pass")
	public RespStatus pass(HttpServletRequest request,@RequestBody FinalAuditDto finalAudit){
		RespStatus result = new RespStatus();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			if(StringUtils.isBlank(finalAudit.getOrderNo())){
				result.setMsg("缺少订单编号,通过失败!");
				return result;
			} else if(StringUtils.isBlank(finalAudit.getRemark())){
				result.setMsg("审批意见不能为空");
				return result;
//			} else if(StringUtils.isBlank(finalAudit.getNextHandleUid())){
//				result.setMsg("请选择下一处理人");
//				return result;
			}
			
			boolean isSubmit = isSubmit(finalAudit.getOrderNo(),"auditFinal");
			if(isSubmit){
				result.setMsg("该订单已经被提交");
				return result;
			}
			boolean isWithdraw = isWithdraw(finalAudit.getOrderNo(),"auditFinal");
			if(isWithdraw){
				result.setMsg("该订单已经被撤回");
				return result;
			}
			//选择的资金分配员
			UserDto user = getUserDto(request);
			finalAudit.setCreateUid(user.getUid());
			finalAudit.setAuditTime(new Date());
			finalAudit.setHandleUid(user.getUid());
			finalAudit.setAuditStatus(1);
			finalAuditservice.insert(finalAudit);
			
			OrderFlowDto next = new OrderFlowDto();
			next.setOrderNo(finalAudit.getOrderNo());
			next.setCurrentProcessId("auditFinal");
			next.setCurrentProcessName("风控终审");
			
			OrderBaseBorrowDto borrow = new OrderBaseBorrowDto();
			borrow.setOrderNo(finalAudit.getOrderNo());
			HttpUtil http = new HttpUtil();
			RespDataObject<OrderBaseBorrowDto> orderobj=http.getRespDataObject(Constants.LINK_CREDIT, "/credit/order/borrowother/v/queryBorrow", borrow,OrderBaseBorrowDto.class);
			borrow=  orderobj.getData();
//			if(borrow.getLoanAmount() >= 1000){
//				next.setNextProcessId("auditJustice");
//				next.setNextProcessName("法务审批");
//			}else{
				next.setNextProcessId("allocationFund");
				next.setNextProcessName("推送金融机构");
//			}
			
			next.setHandleUid(user.getUid());
			next.setHandleName(user.getName());
			next.setUpdateUid(user.getUid());
			
			OrderListDto orderListDto = new OrderListDto();
			orderListDto.setOrderNo(finalAudit.getOrderNo());
			log.info("风控终审通过：orderNo:"+finalAudit.getOrderNo()+"处理人Uid"+finalAudit.getNextHandleUid());
			UserDto nextUser = CommonDataUtil.getUserDtoByUidAndMobile(finalAudit.getNextHandleUid());
			log.info("风控终审通过:处理人Uid,名称:"+nextUser.getUid()+","+nextUser.getName());
			orderListDto.setCurrentHandler(nextUser.getName());
			orderListDto.setCurrentHandlerUid(nextUser.getUid());
			
			result = goNextNode(next,orderListDto);
			sendMessage(user,finalAudit);
//			//发送短信
//			OrderFlowDto flowDto = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/order/flow/v/selectEndOrderFlow", next,OrderFlowDto.class);
//			 if(flowDto!=null&&flowDto.getIsNewWalkProcess()!=2){ //不重新走流程
//				String ipWhite = ConfigUtil.getStringValue(Constants.BASE_AMS_IPWHITE,ConfigUtil.CONFIG_BASE); //ip
//				String ProductName="债务置换";
//				if(borrow!=null && !"01".equals(borrow.getProductCode()) && !"02".equals(borrow.getProductCode())){
//					ProductName=borrow.getProductName();
//				}
//				//发送给金融机构操作人
//				AmsUtil.smsSend(nextUser.getMobile(), ipWhite, Constants.SMS_TEMPLATE_MANAGER, ProductName,borrow.getBorrowerName(),borrow.getLoanAmount(),"推送金融机构");
//			}
		} catch (Exception e){
			log.error("终审审批通过异常,异常信息为==>"+e.getMessage());
		}
		return result;
	}
	/**
	 * 发送短信
	 * @param user
	 * @param finalAudit
	 */
	public void sendMessage(UserDto user,FinalAuditDto finalAudit){
		OrderBaseBorrowDto dto = new OrderBaseBorrowDto();
		dto.setOrderNo(finalAudit.getOrderNo());
		//dto = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/order/borrowother/v/queryBorrow", dto,OrderBaseBorrowDto.class);
		JSONObject jsons = httpUtil.getData(Constants.LINK_CREDIT, "/credit/order/borrowother/v/queryBorrow", dto);
		if(null!=jsons&&jsons.containsKey("data")&&null!=jsons.get("data")) {
			String data = jsons.getString("data");
			Map<String,Object> map = new Gson().fromJson(data, Map.class);
			Integer borrowingDays = MapUtils.getInteger(map,"borrowingDays");
			Integer isChangLoan = MapUtils.getInteger(map,"isChangLoan",-1);
			Double loanAmount = MapUtils.getDouble(map,"loanAmount");
			String acceptMemberUid = MapUtils.getString(map,"acceptMemberUid");
			String borrowerName = MapUtils.getString(map,"borrowerName");
			String channelManagerUid = MapUtils.getString(map,"channelManagerUid");
			dto.setBorrowingDays(borrowingDays);
			dto.setLoanAmount(loanAmount);
			dto.setAcceptMemberUid(acceptMemberUid);
			dto.setChannelManagerUid(channelManagerUid);
//			dto.setIsChangLoan(isChangLoan);
			dto.setProductCode(MapUtils.getString(map,"productCode"));
			dto.setBorrowerName(borrowerName);
//			if(1== isChangLoan){
//				if(MapUtils.getObject(map,"orderBaseBorrowRelationDto") instanceof List){
//					List<Map<String,Object>> relationList = (List<Map<String,Object>>)MapUtils.getObject(map,"orderBaseBorrowRelationDto");
//					Map<String,Object> relationMap = relationList.get(0);
//					borrowingDays = MapUtils.getInteger(relationMap,"borrowingDays");
//					loanAmount = MapUtils.getDouble(relationMap,"loanAmount");
//					dto.setBorrowingDays(borrowingDays);
//					dto.setLoanAmount(loanAmount);
//				}
//			}
		}
		String key = "";
		if("03".equals(dto.getProductCode())){
			key = Constants.SMS_FINAL_PASS_CHANGLOAN_CONTENTMODEL;
			List<OrderBaseBorrowRelationDto> list = dto.getOrderBaseBorrowRelationDto();
			if(null!=list&&list.size()>0&&null!=dto){
				OrderBaseBorrowRelationDto tmp = list.get(0);
				dto.setBorrowingDays(tmp.getBorrowingDays());
				dto.setLoanAmount(tmp.getLoanAmount());
			}
		} else{
			key = Constants.SMS_FINAL_PASS_FCLOAN_CONTENTMODEL;
		}
		UserDto acceptMember = CommonDataUtil.getUserDtoByUidAndMobile(dto.getAcceptMemberUid());
		UserDto channelManager = CommonDataUtil.getUserDtoByUidAndMobile(dto.getChannelManagerUid());
		
		if(null!=acceptMember&&StringUtils.isNotBlank(acceptMember.getMobile())){
			String ipWhite = ConfigUtil.getStringValue(Constants.BASE_AMS_IPWHITE,ConfigUtil.CONFIG_BASE);
			AmsUtil.smsSend(acceptMember.getMobile(), ipWhite,key,dto.getBorrowerName(),dto.getLoanAmount(),user.getName());
		}
		if(null!=channelManager&&StringUtils.isNotBlank(channelManager.getMobile())){
			String ipWhite = ConfigUtil.getStringValue(Constants.BASE_AMS_IPWHITE,ConfigUtil.CONFIG_BASE);
			AmsUtil.smsSend(channelManager.getMobile(), ipWhite,key,dto.getBorrowerName(),dto.getLoanAmount(),user.getName());
		}
	}
}

