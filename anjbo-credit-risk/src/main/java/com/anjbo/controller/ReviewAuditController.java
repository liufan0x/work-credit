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
import com.anjbo.bean.risk.FinalAuditDto;
import com.anjbo.bean.risk.FirstAuditDto;
import com.anjbo.bean.risk.ReviewAuditDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.Constants;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.FinalAuditService;
import com.anjbo.service.FirstAuditService;
import com.anjbo.service.ReviewAuditService;
import com.anjbo.utils.AmsUtil;
import com.anjbo.utils.CommonDataUtil;
import com.anjbo.utils.ConfigUtil;
import com.anjbo.utils.HttpUtil;
import com.anjbo.utils.StringUtil;
import com.google.gson.Gson;

/**
 * 复核审批
 * @author yis
 *
 */
@Controller
@RequestMapping("/credit/risk/review/v")
public class ReviewAuditController extends BaseController{

	private static final Log log = LogFactory.getLog(ReviewAuditController.class);
	@Resource
	private FirstAuditService firstAuditService;
	@Resource
	private FinalAuditService finalAuditService;
	@Resource
	private ReviewAuditService reviewAuditService;

	/**
	 * 复核审批初始化
	 * @param request
	 * @param finalAudit
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/init")
	public RespDataObject<ReviewAuditDto> init(HttpServletRequest request,@RequestBody ReviewAuditDto reviewAuditDto){
		RespDataObject<ReviewAuditDto> result = new RespDataObject<ReviewAuditDto>();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			if(StringUtils.isBlank(reviewAuditDto.getOrderNo())){
				result.setMsg("查询复核审批缺少订单编号!");
				return result;
			}
			ReviewAuditDto reviewAudit=new ReviewAuditDto();
			OrderBaseBorrowDto dto=new OrderBaseBorrowDto();
			dto.setOrderNo(reviewAuditDto.getOrderNo());
			RespDataObject<OrderBaseBorrowDto> baseDto=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/order/borrowother/v/queryBorrow", dto,OrderBaseBorrowDto.class);
			OrderBaseBorrowDto baseBorrowDto=  baseDto.getData();
			if("01".equals(baseBorrowDto.getProductCode()) || "02".equals(baseBorrowDto.getProductCode())
					|| "05".equals(baseBorrowDto.getProductCode())){
				FirstAuditDto firstAuditDto=firstAuditService.detail(reviewAuditDto.getOrderNo());
				reviewAudit.setRate(firstAuditDto.getRate());
				reviewAudit.setOverdueRate(firstAuditDto.getOverdueRate());
			}else{
				reviewAudit.setRate(baseBorrowDto.getRate());
				reviewAudit.setOverdueRate(baseBorrowDto.getOverdueRate());
			}
			if(baseBorrowDto!=null){
				reviewAudit.setLoanAmont(baseBorrowDto.getLoanAmount());
				reviewAudit.setBorrowingDays(baseBorrowDto.getBorrowingDays());
			}
			FirstAuditDto first = firstAuditService.detail(reviewAuditDto.getOrderNo());
			if(first!=null&& first.getSunMoney()!=null &&  first.getSunMoney()>0) {
				reviewAudit.setLoanAmont(first.getSunMoney());
			}
			FinalAuditDto finalAuditDto=finalAuditService.detail(reviewAuditDto.getOrderNo());
			if(finalAuditDto!=null && finalAuditDto.getOfficerUidType()==1){
				reviewAudit.setType(1);//首席
			}else{
				reviewAudit.setType(2);//推送金融
			}
			result.setData(reviewAudit);
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e){
			e.printStackTrace();
			log.error("查询复核审批异常,异常信息为==>"+e.getMessage());
		}
		return result;
	}
	
	/**
	 * 复核审批详情
	 * @param request
	 * @param finalAudit
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/detail")
	public RespDataObject<ReviewAuditDto> detail(HttpServletRequest request,@RequestBody ReviewAuditDto reviewAuditDto){
		RespDataObject<ReviewAuditDto> result = new RespDataObject<ReviewAuditDto>();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			if(StringUtils.isBlank(reviewAuditDto.getOrderNo())){
				result.setMsg("查询复核审批详情参失败,缺少订单编号!");
				return result;
			}
			String orderNo = reviewAuditDto.getOrderNo();
			ReviewAuditDto reviewAudit = reviewAuditService.detail(orderNo);
			OrderBaseBorrowDto dto=new OrderBaseBorrowDto();
			dto.setOrderNo(reviewAuditDto.getOrderNo());
			RespDataObject<OrderBaseBorrowDto> baseDto=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/order/borrowother/v/queryBorrow", dto,OrderBaseBorrowDto.class);
			OrderBaseBorrowDto baseBorrowDto=  baseDto.getData();
			if(baseBorrowDto!=null){
				reviewAudit.setLoanAmont(baseBorrowDto.getLoanAmount());
				reviewAudit.setBorrowingDays(baseBorrowDto.getBorrowingDays());
			}
			FirstAuditDto first = firstAuditService.detail(orderNo);
			if(first!=null&& first.getSunMoney()!=null &&  first.getSunMoney()>0) {
				reviewAudit.setLoanAmont(first.getSunMoney());
			}
			UserDto user = null;
			if(StringUtils.isNotBlank(reviewAudit.getHandleUid())){
				user = CommonDataUtil.getUserDtoByUidAndMobile(reviewAudit.getHandleUid());
				if(null!=user&&null!=reviewAudit){
					reviewAudit.setHandleName(user.getName());
				}
			}
			result.setData(reviewAudit);
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e){
			e.printStackTrace();
			log.error("查询复核审批异常,异常信息为==>"+e.getMessage());
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
	public RespStatus reportOfficer(HttpServletRequest request,@RequestBody ReviewAuditDto reviewAuditDto){
		RespStatus result = new RespStatus();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			if(StringUtils.isBlank(reviewAuditDto.getOrderNo())){
				result.setMsg("缺少订单编号,上报复核审批失败!");
				return result;
			}
			boolean isSubmit = isSubmit(reviewAuditDto.getOrderNo(),"auditReview");
			if(isSubmit){
				result.setMsg("该订单已经被提交");
				return result;
			}
			boolean isWithdraw = isWithdraw(reviewAuditDto.getOrderNo(),"auditReview");
			if(isWithdraw){
				result.setMsg("该订单已经被撤回");
				return result;
			}
			FinalAuditDto finalAuditDto=finalAuditService.detail(reviewAuditDto.getOrderNo());
			if(finalAuditDto==null || null == finalAuditDto.getOfficerUid() || 1!=finalAuditDto.getOfficerUidType()){
				result.setMsg("该订单没有选择首席风险官处理人请退回重新选择");
				return result;
			}
			//判断建议费率只能改大，不能改小
			String orderNo = reviewAuditDto.getOrderNo();
			ReviewAuditDto reviewAudit = new ReviewAuditDto();
			OrderBaseBorrowDto dto=new OrderBaseBorrowDto();
			dto.setOrderNo(reviewAuditDto.getOrderNo());
			RespDataObject<OrderBaseBorrowDto> baseDto=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/order/borrowother/v/queryBorrow", dto,OrderBaseBorrowDto.class);
			OrderBaseBorrowDto baseBorrowDto=  baseDto.getData();
			if("01".equals(baseBorrowDto.getProductCode()) || "02".equals(baseBorrowDto.getProductCode())
					|| "05".equals(baseBorrowDto.getProductCode())){
				FirstAuditDto firstAuditDto=firstAuditService.detail(orderNo);
				reviewAudit.setRate(firstAuditDto.getRate());
				reviewAudit.setOverdueRate(firstAuditDto.getOverdueRate());
			}else{
				reviewAudit.setRate(baseBorrowDto.getRate());
				reviewAudit.setOverdueRate(baseBorrowDto.getOverdueRate());
			}
//			if(reviewAuditDto.getRate()<reviewAudit.getRate()){
//				result.setMsg("建议费率不能低于初始费率");
//				return result;
//			}
//			if(reviewAuditDto.getOverdueRate()<reviewAudit.getOverdueRate()){
//				result.setMsg("建议逾期费率不能低于初始建议逾期费率");
//				return result;
//			}
			//获取登录人信息
			UserDto user = getUserDto(request);
			reviewAuditDto.setCreateUid(user.getUid());
			reviewAuditDto.setAuditTime(new Date());
			reviewAuditDto.setAuditStatus(3);
			reviewAuditDto.setHandleUid(user.getUid());
			reviewAuditService.insert(reviewAuditDto);
			OrderFlowDto next = new OrderFlowDto();
			next.setOrderNo(reviewAuditDto.getOrderNo());
			next.setCurrentProcessId("auditReview");
			next.setCurrentProcessName("复核审批");
			next.setNextProcessId("auditOfficer");
			next.setNextProcessName("首席风险官审批");
			next.setHandleUid(user.getUid());
			next.setHandleName(user.getName());
			next.setUpdateUid(user.getUid());
			OrderListDto orderListDto = new OrderListDto();
			orderListDto.setOrderNo(reviewAuditDto.getOrderNo());
			UserDto nextUser = CommonDataUtil.getUserDtoByUidAndMobile(finalAuditDto.getOfficerUid());
			orderListDto.setCurrentHandler(nextUser.getName());
			orderListDto.setCurrentHandlerUid(nextUser.getUid());
			result = goNextNode(next,orderListDto);
			
		} catch (Exception e){
			log.error("上报复核审批异常,异常信息为==>"+e.getMessage());
		}
		return result;
	}
	
	/**
	 * 复核审批通过
	 * @param request
	 * @param finalAudit
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/pass")
	public RespStatus pass(HttpServletRequest request,@RequestBody ReviewAuditDto reviewAuditDto){
		RespStatus result = new RespStatus();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			if(StringUtils.isBlank(reviewAuditDto.getOrderNo())){
				result.setMsg("缺少订单编号,通过失败!");
				return result;
			} 
			boolean isSubmit = isSubmit(reviewAuditDto.getOrderNo(),"auditReview");
			if(isSubmit){
				result.setMsg("该订单已经被提交");
				return result;
			}
			boolean isWithdraw = isWithdraw(reviewAuditDto.getOrderNo(),"auditReview");
			if(isWithdraw){
				result.setMsg("该订单已经被撤回");
				return result;
			}
			FinalAuditDto finalAuditDto=finalAuditService.detail(reviewAuditDto.getOrderNo());
			if(finalAuditDto==null || finalAuditDto.getAllocationFundUid()==null){
				result.setMsg("该订单终审未选择推送金融机构处理员，请退回重新操作");
				return result;
			}
			
			//判断建议费率只能改大，不能改小
			String orderNo = reviewAuditDto.getOrderNo();
			ReviewAuditDto reviewAudit = new ReviewAuditDto();
			OrderBaseBorrowDto dto=new OrderBaseBorrowDto();
			dto.setOrderNo(reviewAuditDto.getOrderNo());
			RespDataObject<OrderBaseBorrowDto> baseDto=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/order/borrowother/v/queryBorrow", dto,OrderBaseBorrowDto.class);
			OrderBaseBorrowDto baseBorrowDto=  baseDto.getData();
			if("01".equals(baseBorrowDto.getProductCode()) || "02".equals(baseBorrowDto.getProductCode())
					|| "05".equals(baseBorrowDto.getProductCode())){
				FirstAuditDto firstAuditDto=firstAuditService.detail(orderNo);
				reviewAudit.setRate(firstAuditDto.getRate());
				reviewAudit.setOverdueRate(firstAuditDto.getOverdueRate());
			}else{
				reviewAudit.setRate(baseBorrowDto.getRate());
				reviewAudit.setOverdueRate(baseBorrowDto.getOverdueRate());
			}
//			if(reviewAuditDto.getRate()<reviewAudit.getRate()){
//				result.setMsg("建议费率不能低于初始费率");
//				return result;
//			}
//			if(reviewAuditDto.getOverdueRate()<reviewAudit.getOverdueRate()){
//				result.setMsg("建议逾期费率不能低于初始建议逾期费率");
//				return result;
//			}
			
			reviewAuditDto.setNextHandleUid(finalAuditDto.getAllocationFundUid());
			//选择的资金分配员
			UserDto user = getUserDto(request);
			reviewAuditDto.setCreateUid(user.getUid());
			reviewAuditDto.setAuditTime(new Date());
			reviewAuditDto.setHandleUid(user.getUid());
			reviewAuditDto.setAuditStatus(1);
			reviewAuditService.insert(reviewAuditDto);
			
			OrderFlowDto next = new OrderFlowDto();
			next.setOrderNo(reviewAuditDto.getOrderNo());
			next.setCurrentProcessId("auditReview");
			next.setCurrentProcessName("复核审批");
			OrderBaseBorrowDto borrow = new OrderBaseBorrowDto();
			borrow.setOrderNo(reviewAuditDto.getOrderNo());
			HttpUtil http = new HttpUtil();
			RespDataObject<OrderBaseBorrowDto> orderobj=http.getRespDataObject(Constants.LINK_CREDIT, "/credit/order/borrowother/v/queryBorrow", borrow,OrderBaseBorrowDto.class);
			borrow=  orderobj.getData();
			next.setNextProcessId("allocationFund");
			next.setNextProcessName("推送金融机构");
			next.setHandleUid(user.getUid());
			next.setHandleName(user.getName());
			next.setUpdateUid(user.getUid());
			
			OrderListDto orderListDto = new OrderListDto();
			orderListDto.setOrderNo(reviewAuditDto.getOrderNo());
			log.info("风控终审通过：orderNo:"+reviewAuditDto.getOrderNo()+"处理人Uid"+reviewAuditDto.getNextHandleUid());
			UserDto nextUser = CommonDataUtil.getUserDtoByUidAndMobile(reviewAuditDto.getNextHandleUid());
			log.info("风控终审通过:处理人Uid,名称:"+nextUser.getUid()+","+nextUser.getName());
			orderListDto.setCurrentHandler(nextUser.getName());
			orderListDto.setCurrentHandlerUid(nextUser.getUid());
			result = goNextNode(next,orderListDto);
			
			// ==============发送短信Start===================
			UserDto aUser = CommonDataUtil.getUserDtoByUidAndMobile(baseBorrowDto.getAcceptMemberUid());
			UserDto cUser = CommonDataUtil.getUserDtoByUidAndMobile(baseBorrowDto.getChannelManagerUid());
			String type="债务置换";
			if("03".equals(baseBorrowDto.getProductCode())){
				type="畅贷";
			}else if("04".equals(baseBorrowDto.getProductCode())) {
				type="房抵贷";
			}
			if (StringUtil.isNotEmpty(aUser.getMobile())) {
				String ipWhite = ConfigUtil.getStringValue(
						Constants.BASE_AMS_IPWHITE, ConfigUtil.CONFIG_BASE); // ip
				String cont = baseBorrowDto.getBorrowerName() + " "
						+ baseBorrowDto.getLoanAmount();
				AmsUtil.smsSend(aUser.getMobile(), ipWhite, Constants.SMS_PASS,type,cont, user.getName());
			}
			if (StringUtil.isNotEmpty(cUser.getMobile())) {
				String ipWhite = ConfigUtil.getStringValue(
						Constants.BASE_AMS_IPWHITE, ConfigUtil.CONFIG_BASE); // ip
				String cont = baseBorrowDto.getBorrowerName() + " "
						+ baseBorrowDto.getLoanAmount();
				AmsUtil.smsSend(cUser.getMobile(), ipWhite, Constants.SMS_PASS,type,cont, user.getName());
			}
			// ==============发送短信end===================

//			OrderFlowDto flowDto = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/order/flow/v/selectEndOrderFlow", next,OrderFlowDto.class);
//			 if(flowDto!=null&&flowDto.getIsNewWalkProcess()!=2){ //不重新走流程
//				//发送短信
//				String ipWhite = ConfigUtil.getStringValue(Constants.BASE_AMS_IPWHITE,ConfigUtil.CONFIG_BASE); //ip
//				String ProductName="债务置换";
//				if(borrow!=null && !"01".equals(borrow.getProductCode()) && !"02".equals(borrow.getProductCode())){
//					ProductName=borrow.getProductName();
//				}
//				//发送给金融机构操作人
//				AmsUtil.smsSend(nextUser.getMobile(), ipWhite, Constants.SMS_TEMPLATE_MANAGER, ProductName,borrow.getBorrowerName(),borrow.getLoanAmount(),"推送金融机构");
//			}
		} catch (Exception e){
			log.error("复核审批通过异常,异常信息为==>"+e.getMessage());
		}
		return result;
	}
	/**
	 * 发送短信
	 * @param user
	 * @param reviewAuditDto
	 */
	public void sendMessage(UserDto user,ReviewAuditDto reviewAuditDto){
		OrderBaseBorrowDto dto = new OrderBaseBorrowDto();
		dto.setOrderNo(reviewAuditDto.getOrderNo());
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

