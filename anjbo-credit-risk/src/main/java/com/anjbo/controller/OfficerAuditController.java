package com.anjbo.controller;

import java.util.Date;
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
import com.anjbo.bean.risk.OfficerAuditDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.Constants;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.OfficerAuditService;
import com.anjbo.utils.AmsUtil;
import com.anjbo.utils.CommonDataUtil;
import com.anjbo.utils.ConfigUtil;
import com.anjbo.utils.HttpUtil;
import com.google.gson.Gson;

/**
 * 首席风险官
 * @author huanglj
 *
 */
@Controller
@RequestMapping("/credit/risk/officer/v")
public class OfficerAuditController extends BaseController{

	private static final Log log = LogFactory.getLog(OfficerAuditController.class);
	
	@Resource
	private OfficerAuditService officerAuditService;
	
	/**
	 * 新增首席风险官审批信息
	 * @param request
	 * @param obj
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/insert")
	public RespStatus insert(HttpServletRequest request,@RequestBody OfficerAuditDto obj){
		RespStatus result = new RespStatus();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			if(StringUtils.isBlank(obj.getOrderNo())){
				result.setMsg("保存首席风险官审批信息失败,缺少订单编号");
				return result;
			} 
			UserDto user = getUserDto(request);
			obj.setCreateUid(user.getUid());
			obj.setHandleUid(user.getUid());
			obj.setAuditTime(new Date());
			officerAuditService.insert(obj);
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e){
			log.error("新增首席风险官信息异常,异常信息为:",e);
		}
		return result;
	}
	/**
	 * 查询首席风险官审批信息详情
	 * @param request
	 * @param obj
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/detail")
	public RespDataObject<OfficerAuditDto> detail(HttpServletRequest request,@RequestBody OfficerAuditDto obj){
		RespDataObject<OfficerAuditDto> result = new RespDataObject<OfficerAuditDto>();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			if(StringUtils.isBlank(obj.getOrderNo())){
				result.setMsg("查询首席风险官审批详情异常,缺少订单编号!");
				return result;
			}
			obj = officerAuditService.detail(obj.getOrderNo());
			UserDto user = null;
			if(StringUtils.isNotBlank(obj.getHandleUid())){
				user = CommonDataUtil.getUserDtoByUidAndMobile(obj.getHandleUid());
			}
			if(null!=user&&null!=obj){
				obj.setHandleName(user.getName());
			}
			result.setData(obj);
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e){
			log.error(" 查询首席风险官审批信息详情异常,异常信息为:",e);
		}
		return result;
	}
	/**
	 * 首席风险官审批通过
	 * @param request
	 * @param obj
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/pass")
	public RespStatus pass(HttpServletRequest request,@RequestBody OfficerAuditDto obj){
		RespStatus result = new RespStatus();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			if(StringUtils.isBlank(obj.getOrderNo())){
				result.setMsg("订单编号缺失,通过失败");
				return result;
			} else if(StringUtils.isBlank(obj.getRemark())){
				result.setMsg("请填写审批意见");
				return result;
//			} else if(StringUtils.isBlank(obj.getNextHandleUid())){
//				result.setMsg("请选择下一处理人");
//				return result;
			}
			
			boolean isSubmit = isSubmit(obj.getOrderNo(),"auditOfficer");
			if(isSubmit){
				result.setMsg("该订单已经被提交");
				return result;
			}
			boolean isWithdraw = isWithdraw(obj.getOrderNo(),"auditOfficer");
			if(isWithdraw){
				result.setMsg("该订单已经被撤回");
				return result;
			}
			
			UserDto user = getUserDto(request);
			obj.setCreateUid(user.getUid());
			obj.setHandleUid(user.getUid());
			obj.setAuditTime(new Date());
			obj.setAuditStatus(1);
			officerAuditService.insert(obj);

			UserDto nextUser = CommonDataUtil.getUserDtoByUidAndMobile(obj.getNextHandleUid());
			OrderFlowDto next = new OrderFlowDto();
			next.setOrderNo(obj.getOrderNo());
			next.setCurrentProcessId("auditOfficer");
			next.setCurrentProcessName("首席风险官审批");
			
			OrderBaseBorrowDto orderBaseBorrowDto=new OrderBaseBorrowDto();
			orderBaseBorrowDto.setOrderNo(obj.getOrderNo());
			HttpUtil http = new HttpUtil();
			RespDataObject<OrderBaseBorrowDto> orderobj=http.getRespDataObject(Constants.LINK_CREDIT, "/credit/order/borrowother/v/queryBorrow", orderBaseBorrowDto,OrderBaseBorrowDto.class);
			OrderBaseBorrowDto borrow=  orderobj.getData();
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
			orderListDto.setOrderNo(obj.getOrderNo());
			orderListDto.setCurrentHandlerUid(nextUser.getUid());
			orderListDto.setCurrentHandler(nextUser.getName());
			result = goNextNode(next,orderListDto);
			sendMessage(user,obj);
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
			log.error("首席风险官通过异常,异常信息为:",e);
		}
		return result;
	}
	
	/**
	 * 发送短信
	 * @param user
	 * @param ceo
	 */
	public void sendMessage(UserDto user,OfficerAuditDto ceo){
		OrderBaseBorrowDto dto = new OrderBaseBorrowDto();
		dto.setOrderNo(ceo.getOrderNo());
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
			if(1== isChangLoan){
				if(MapUtils.getObject(map,"orderBaseBorrowRelationDto") instanceof List){
					List<Map<String,Object>> relationList = (List<Map<String,Object>>)MapUtils.getObject(map,"orderBaseBorrowRelationDto");
					Map<String,Object> relationMap = relationList.get(0);
					borrowingDays = MapUtils.getInteger(relationMap,"borrowingDays");
					loanAmount = MapUtils.getDouble(relationMap,"loanAmount");
					dto.setBorrowingDays(borrowingDays);
					dto.setLoanAmount(loanAmount);
				}


			}
		}

		String key = "";
		if("03".equals(dto.getProductCode())){
			key = Constants.SMS_CEO_PASS_CHANGLOAN_CONTENTMODEL;
			List<OrderBaseBorrowRelationDto> list = dto.getOrderBaseBorrowRelationDto();
			if(null!=list&&list.size()>0&&null!=dto){
				OrderBaseBorrowRelationDto tmp = list.get(0);
				dto.setBorrowingDays(tmp.getBorrowingDays());
				dto.setLoanAmount(tmp.getLoanAmount());
			}
		} else{
			key = Constants.SMS_CEO_PASS_FCLOAN_CONTENTMODEL;
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
