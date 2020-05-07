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
import com.anjbo.bean.risk.AllocationFundDto;
import com.anjbo.bean.risk.JusticeAuditDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.Constants;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.JusticeAuditService;
import com.anjbo.utils.AmsUtil;
import com.anjbo.utils.CommonDataUtil;
import com.anjbo.utils.ConfigUtil;
import com.google.gson.Gson;

/**
 * 法务审批
 * @author huanglj
 *
 */
@Controller
@RequestMapping("/credit/risk/justice/v")
public class JusticeAuditController extends BaseController{

	private static final Log log = LogFactory.getLog(JusticeAuditController.class);
	
	@Resource
	private JusticeAuditService justiceAuditService;
	
	/**
	 * 新增法务审批信息
	 * @param request
	 * @param obj
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/insert")
	public RespStatus insert(HttpServletRequest request,@RequestBody JusticeAuditDto obj){
		RespStatus result = new RespStatus();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			if(StringUtils.isBlank(obj.getOrderNo())){
				result.setMsg("保存法务审批信息失败,缺少订单编号");
				return result;
			} 
			UserDto user = getUserDto(request);
			obj.setCreateUid(user.getUid());
			obj.setHandleUid(user.getUid());
			obj.setAuditTime(new Date());
			justiceAuditService.insert(obj);
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e){
			log.error("新增首席风险官信息异常,异常信息为:",e);
		}
		return result;
	}
	/**
	 * 查询法务审批信息详情
	 * @param request
	 * @param obj
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/detail")
	public RespDataObject<JusticeAuditDto> detail(HttpServletRequest request,@RequestBody JusticeAuditDto obj){
		RespDataObject<JusticeAuditDto> result = new RespDataObject<JusticeAuditDto>();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			if(StringUtils.isBlank(obj.getOrderNo())){
				result.setMsg("查询法务审批详情异常,缺少订单编号!");
				return result;
			}
			obj = justiceAuditService.detail(obj.getOrderNo());
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
			log.error(" 查询法务审批信息详情异常,异常信息为:",e);
		}
		return result;
	}
	/**
	 * 法务审批通过
	 * @param request
	 * @param obj
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/pass")
	public RespStatus pass(HttpServletRequest request,@RequestBody JusticeAuditDto obj){
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
			}
			
			boolean isSubmit = isSubmit(obj.getOrderNo(),"auditJustice");
			if(isSubmit){
				result.setMsg("该订单已经被提交");
				return result;
			}
			boolean isWithdraw = isWithdraw(obj.getOrderNo(),"auditJustice");
			if(isWithdraw){
				result.setMsg("该订单已经被撤回");
				return result;
			}
			
			UserDto user = getUserDto(request);
			obj.setCreateUid(user.getUid());
			obj.setHandleUid(user.getUid());
			obj.setAuditTime(new Date());
			obj.setAuditStatus(1);
			justiceAuditService.insert(obj);

			
			OrderFlowDto next = new OrderFlowDto();
			next.setOrderNo(obj.getOrderNo());
			next.setCurrentProcessId("auditJustice");
			next.setCurrentProcessName("法务审批");
			next.setHandleUid(user.getUid());
			next.setHandleName(user.getName());
			next.setUpdateUid(user.getUid());
			
			OrderListDto orderListDto = new OrderListDto();
			orderListDto.setOrderNo(obj.getOrderNo());
			//V3.1
//			UserDto nextUser = CommonDataUtil.getUserDtoByUidAndMobile(obj.getNextHandleUid());
//			next.setNextProcessId("allocationFund");
//			next.setNextProcessName("分配资金");	
			//V3.2
			next.setNextProcessId("fundDocking");
			next.setNextProcessName("资料推送");
			AllocationFundDto allocationFundDto =new AllocationFundDto();
			allocationFundDto.setOrderNo(obj.getOrderNo());
			List<AllocationFundDto> list = httpUtil.getList(Constants.LINK_CREDIT, "/credit/risk/allocationfund/v/detail",allocationFundDto, AllocationFundDto.class);
			String nextUid = list==null?null:list.get(0).getCreateUid();
			UserDto nextUser=CommonDataUtil.getUserDtoByUidAndMobile(nextUid);
			orderListDto.setCurrentHandlerUid(nextUser.getUid());
			orderListDto.setCurrentHandler(nextUser.getName());
			
			result = goNextNode(next,orderListDto);
//			sendMessage(user,obj);
//			OrderFlowDto flowDto = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/order/flow/v/selectEndOrderFlow", next,OrderFlowDto.class);
//			 if(flowDto!=null&&flowDto.getIsNewWalkProcess()!=2){ //不重新走流程
//				//发送短信
//				OrderBaseBorrowDto orderBaseBorrowDto=new OrderBaseBorrowDto();
//				orderBaseBorrowDto.setOrderNo(obj.getOrderNo());
//				RespDataObject<OrderBaseBorrowDto>dataObject=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/order/borrowother/v/queryBorrow", orderBaseBorrowDto,OrderBaseBorrowDto.class);
//				OrderBaseBorrowDto baseBorrowDto=  dataObject.getData();
//				String ipWhite = ConfigUtil.getStringValue(Constants.BASE_AMS_IPWHITE,ConfigUtil.CONFIG_BASE); //ip
//				String ProductName="债务置换";
//				if(baseBorrowDto!=null && !"01".equals(baseBorrowDto.getProductCode()) && !"02".equals(baseBorrowDto.getProductCode())){
//					ProductName=baseBorrowDto.getProductName();
//				}
//				//发送给操作人
//				AmsUtil.smsSend(nextUser.getMobile(), ipWhite, Constants.SMS_TEMPLATE_MANAGER, ProductName,baseBorrowDto.getBorrowerName(),baseBorrowDto.getLoanAmount(),"资料推送");
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
	public void sendMessage(UserDto user,JusticeAuditDto ceo){
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
