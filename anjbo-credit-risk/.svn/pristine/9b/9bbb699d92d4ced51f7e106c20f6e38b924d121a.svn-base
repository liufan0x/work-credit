package com.anjbo.controller;

import com.anjbo.bean.order.OrderBaseBorrowDto;
import com.anjbo.bean.order.OrderFlowDto;
import com.anjbo.bean.order.OrderListDto;
import com.anjbo.bean.product.ManagerAuditDto;
import com.anjbo.bean.risk.AllocationFundAduitDto;
import com.anjbo.bean.risk.AllocationFundDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.bean.yntrust.YntrustRepaymentPlanDto;
import com.anjbo.common.*;
import com.anjbo.service.AllocationFundService;
import com.anjbo.service.AlloctionFundAduitService;
import com.anjbo.utils.AmsUtil;
import com.anjbo.utils.CommonDataUtil;
import com.anjbo.utils.ConfigUtil;
import com.anjbo.utils.DateUtils;
import com.anjbo.utils.StringUtil;
import net.sf.json.JSONObject;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 待资金审批  （融安）
 * @author admin
 *
 */
@Controller
@RequestMapping("/credit/risk/allocationfundaduit")
public class AlloctionFundAduitController extends BaseController{
	private static final Log log = LogFactory.getLog(AllocationFundController.class);
	@Resource
	private AlloctionFundAduitService alloctionFundAduitService;
	@Resource
	private AllocationFundService fundService;
	/**
	 * 初始化
	 * @param request
	 * @param fundCompleteDto
	 * @return
	 */
	@RequestMapping(value = "/v/init")
	public @ResponseBody
	RespDataObject<Map<String, Object>> init(HttpServletRequest request,@RequestBody AllocationFundAduitDto fundCompleteDto) {
		RespDataObject<Map<String, Object>> rd = new RespDataObject<Map<String, Object>>();
		try {
			Map<String, Object> map=new HashMap<String, Object>();
			boolean isHuarong=false;
			boolean isYunNan = false;
			List<AllocationFundDto> flist = fundService.listDetail(fundCompleteDto.getOrderNo());;
			if(flist!=null){
				for (int i = 0; i < flist.size(); i++) {
					if("110".equals(flist.get(i).getFundCode())){
						isHuarong=true;
					}else if("114".equals(flist.get(i).getFundCode())){
						isYunNan = true;
					}
				}
			}
			AllocationFundAduitDto aduitDto = alloctionFundAduitService.selectFundAudit(fundCompleteDto.getOrderNo());
			if(isHuarong){	//是否为华融
				if(aduitDto!=null){
					if(aduitDto.getGrantStatus()>111){
						//查询应还款计划
						Map<String, Object> paramt=new HashMap<String, Object>();
						paramt.put("orderNo", fundCompleteDto.getOrderNo());
						RespDataObject<Map<String, Object>> obj=httpUtil.getRespDataObject(Constants.LINK_CREDIT, ReqMappingConstants.CREDIT_THIRD_API_QUERYREPAYMENT, paramt, Map.class);
						map.put("fundAudit", obj.getData());
					}
					//修改资金审批状态
					if((!"997".equals(aduitDto.getDealStatus()+"") || !"997".equals(aduitDto.getGrantStatus()+"")) && !"998".equals(aduitDto.getDealStatus()+"")){
						updataStatus(fundCompleteDto.getOrderNo());
						aduitDto = alloctionFundAduitService.selectFundAudit(fundCompleteDto.getOrderNo());
					}
				}
			}
			if(isYunNan){
				//查询还款计划
				Map<String, Object> paramt=new HashMap<String, Object>();
				paramt.put("orderNo", fundCompleteDto.getOrderNo());
				RespDataObject<YntrustRepaymentPlanDto> obj=httpUtil.getRespDataObject(Constants.LINK_CREDIT,"/credit/third/api/yntrust/v/selectRepaymentPlan", paramt, YntrustRepaymentPlanDto.class);
				map.put("fundAudit", obj.getData());
			}
			if(aduitDto==null){ 
			  aduitDto=new AllocationFundAduitDto();
			  aduitDto.setOrderNo(fundCompleteDto.getOrderNo());
			}
			map.put("fundCompleteDto", aduitDto);
			map.put("isHuaRongShow", isHuarong);
			map.put("isYunNanShow", isYunNan);
			rd.setData(map);
			rd.setCode(RespStatusEnum.SUCCESS.getCode());
			rd.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			rd.setCode(RespStatusEnum.FAIL.getCode());
			rd.setMsg(RespStatusEnum.FAIL.getMsg());
		}
		return rd;
	}
	
	//定时查询放款审批
    public void updataStatus(String orderNo) {  
		try {
			if(orderNo==null){
				log.error("订单编号为null");
	    	}else{
	    		log.info("订单编号为："+orderNo);
	    		//先查询放款审批
				Map<String,Object> params = new HashMap<String, Object>();
				params.put("orderNo", orderNo);//2017071811111900000
				RespDataObject<Map<String, Object>> resp=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/third/api/hr/queryLoanStatus", params, Map.class);
				Map<String, Object> loanStatus =resp.getData();
				log.info("融安放款状态返回信息:"+loanStatus +"----"+resp.getCode());
				if("SUCCESS".equals(resp.getCode())){
					String dealStatus =loanStatus.get("dealStatus")+"";
					String grantStatus=loanStatus.get("grantStatus")+"";
					if("997".equals(grantStatus) || "998".equals(grantStatus) ||"998".equals(dealStatus)||"997".equals(dealStatus) ||"992".equals(dealStatus)){  
					  params.put("dealStatus", dealStatus);
					  params.put("grantStatus", grantStatus);
					  params.put("lendingTime", loanStatus.get("realLoanTime"));  //放款时间
					  params.put("dealOpinion", loanStatus.get("dealOpinion"));  //审批打回原因
					  //修改资金放款审批状态
					  RespStatus respStatus = httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/risk/allocationfundaduit/updataStatus", params);
					  log.info("修改资金审批状态："+respStatus.getMsg());
					  //发送短信
//					  if("SUCCESS".equals(respStatus.getCode())){
//						  //判断审批状态
//						  if("997".equals(grantStatus) || "998".equals(grantStatus) ||"998".equals(dealStatus) ||"992".equals(dealStatus)){
//						    //查询订单客户姓名及借款金额	
//							RespDataObject<Map<String, Object>> respDate=httpUtil.getRespDataObject(Enums.MODULAR_URL.CREDIT.toString(), "/credit/risk/allocationfundaduit/selectOrder", params, Map.class);
//							Map<String, Object> fundMap=respDate.getData();
//							if(fundMap!=null){
//							    String content="";
//								if("997".equals(grantStatus)){
//				 					  content = "债务置换订单（"+fundMap.get("borrowerName")+"，"+fundMap.get("loanAmount")+"万）已通过资方放款审批，请登录信贷系统发送应还款计";	
//								}else{
//									  if("992".equals(dealStatus)){
//										  content = "债务置换订单（"+fundMap.get("borrowerName")+"，"+fundMap.get("loanAmount")+"万）已被资方打回，请保持跟进";
//									  }else{
//										  content = "债务置换订单（"+fundMap.get("borrowerName")+"，"+fundMap.get("loanAmount")+"万）已被资方否决，请保持跟进";
//									  }
//								}
////								String ipWhite = ConfigUtil.getStringValue(Enums.GLOBAL_CONFIG_KEY.AMS_SMS_IPWHITE.toString());
////								String phone=fundMap.get("phone").toString();
////								AmsUtil.smsSend(phone, ipWhite,content , Constants.SMSCOMEFROM);
//							}
//					  }
//					}
					   if("SUCCESS".equals(respStatus.getCode()) && "997".equals(grantStatus)){
					  	//发送短信
					    OrderListDto orderListDto = new OrderListDto();
						orderListDto.setOrderNo(orderNo);
						OrderBaseBorrowDto orderBaseBorrowDto=new OrderBaseBorrowDto();
						orderBaseBorrowDto.setOrderNo(orderNo);
						RespDataObject<OrderBaseBorrowDto> obj=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/order/borrowother/v/queryBorrow", orderBaseBorrowDto,OrderBaseBorrowDto.class);
						OrderBaseBorrowDto baseBorrowDto=  obj.getData();
						String ipWhite = ConfigUtil.getStringValue(Constants.BASE_AMS_IPWHITE,ConfigUtil.CONFIG_BASE); //ip
						String ProductName="债务置换";
						if(baseBorrowDto!=null && !"01".equals(baseBorrowDto.getProductCode()) && !"02".equals(baseBorrowDto.getProductCode())){
							ProductName=baseBorrowDto.getProductName();
						}
						UserDto nextUser = CommonDataUtil.getUserDtoByUidAndMobile(baseBorrowDto.getAcceptMemberUid());
						UserDto nextUser1 = CommonDataUtil.getUserDtoByUidAndMobile(baseBorrowDto.getChannelManagerUid());
						//发送给受理员
						AmsUtil.smsSend(nextUser.getMobile(), ipWhite, Constants.SMS_TEMPLATE_PROCESS_TOPASS, ProductName,baseBorrowDto.getBorrowerName(),baseBorrowDto.getLoanAmount(),"资金审批");
						//渠道经理
						AmsUtil.smsSend(nextUser1.getMobile(), ipWhite, Constants.SMS_TEMPLATE_PROCESS_TOPASS, ProductName,baseBorrowDto.getBorrowerName(),baseBorrowDto.getLoanAmount(),"资金审批");
						log.info("toAddFinance-发送短信 电话："+nextUser1.getMobile()+","+ProductName+","+baseBorrowDto.getBorrowerName()+baseBorrowDto.getLoanAmount()+"资金审批");
					   }
				  }
				}
	    	}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
	/**
	 * 添加-华融
	 * @param request
	 * @param fundCompleteDto
	 * @return
	 */
	@RequestMapping(value = "/v/add")
	public @ResponseBody
	RespStatus add(HttpServletRequest request,@RequestBody AllocationFundAduitDto fundCompleteDto) {
		RespStatus rd = new RespStatus();
		try {
			UserDto userDto = getUserDto(request);
			fundCompleteDto.setDealStatus(111);
			fundCompleteDto.setGrantStatus(111);
			if(fundCompleteDto.getId()==0){  //添加
				fundCompleteDto.setCreateUid(userDto.getUid());
				fundCompleteDto.setUpdateUid(userDto.getUid());
				alloctionFundAduitService.addFundAuidt(fundCompleteDto);
			}else if(fundCompleteDto.getId()>0){  //修改
				fundCompleteDto.setUpdateUid(userDto.getUid());
				alloctionFundAduitService.updateFundAuidt(fundCompleteDto);
			}
			rd.setCode(RespStatusEnum.SUCCESS.getCode());
			rd.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			rd.setCode(RespStatusEnum.FAIL.getCode());
			rd.setMsg(RespStatusEnum.FAIL.getMsg());
		}
		return rd;
	}
	
	/**
	 * 保存-非华融
	 * @param request
	 * @param fundCompleteDto
	 * @return
	 */
	@RequestMapping(value = "/v/saveFundAudit")
	public @ResponseBody
	RespStatus saveFundAudit(HttpServletRequest request,@RequestBody AllocationFundAduitDto fundCompleteDto) {
		RespStatus result = new RespStatus();
		try {
			String orderNo=fundCompleteDto.getOrderNo();
			boolean isSubmit = isSubmit(orderNo,"fundAduit");
			if(isSubmit){
				result.setMsg("该订单已经被提交");
				return result;
			}
			boolean isWithdraw = isWithdraw(orderNo,"fundAduit");
			if(isWithdraw){
				result.setMsg("该订单已经被撤回");
				return result;
			}
			UserDto userDto = getUserDto(request);
			fundCompleteDto.setDealStatus(0);
			fundCompleteDto.setGrantStatus(0);
			if(fundCompleteDto.getId()==0){  //添加
				fundCompleteDto.setCreateUid(userDto.getUid());
				fundCompleteDto.setUpdateUid(userDto.getUid());
				alloctionFundAduitService.addFundAuidt(fundCompleteDto);
			}else if(fundCompleteDto.getId()>0){  //修改
				fundCompleteDto.setUpdateUid(userDto.getUid());
				alloctionFundAduitService.updateFundAuidt(fundCompleteDto);
			}
			OrderFlowDto next = new OrderFlowDto();
			next.setOrderNo(orderNo);
			next.setCurrentProcessId("fundAduit");
			next.setCurrentProcessName("资金审批");
			next.setHandleUid(userDto.getUid());
			next.setHandleName(userDto.getName());
			next.setUpdateUid(userDto.getUid());
//			next.setNextProcessId("notarization");
//			next.setNextProcessName("公证");
			next.setNextProcessId("repaymentMember");
			next.setNextProcessName("指派还款专员");
			OrderListDto orderListDto = new OrderListDto();
			orderListDto.setOrderNo(orderNo);
			OrderBaseBorrowDto orderBaseBorrowDto=new OrderBaseBorrowDto();
			orderBaseBorrowDto.setOrderNo(orderNo);
			RespDataObject<OrderBaseBorrowDto> obj=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/order/borrowother/v/queryBorrow", orderBaseBorrowDto,OrderBaseBorrowDto.class);
			OrderBaseBorrowDto baseBorrowDto=  obj.getData();
//			if(baseBorrowDto!=null){
//				UserDto nextUser = CommonDataUtil.getUserDtoByUidAndMobile(baseBorrowDto.getNotarialUid());
//				orderListDto.setCurrentHandlerUid(nextUser.getUid());
//				orderListDto.setCurrentHandler(nextUser.getName());
//			}
			//查询受理经理uid
			OrderListDto listDto=new OrderListDto();
			listDto.setOrderNo(orderNo);
			listDto=httpUtil.getObject(Constants.LINK_CREDIT, "/credit/order/base/v/selectDetailByOrderNo", listDto, OrderListDto.class);
			if(listDto!=null && StringUtil.isNotBlank(listDto.getReceptionManagerUid())){
				UserDto nextUser = CommonDataUtil.getUserDtoByUidAndMobile(listDto.getReceptionManagerUid());
				orderListDto.setCurrentHandlerUid(nextUser.getUid());
				orderListDto.setCurrentHandler(nextUser.getName());
			}else{ //老数据
				ManagerAuditDto dis = new ManagerAuditDto();
				dis.setOrderNo(orderNo);
				dis = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/process/managerAudit/v/detail", dis, ManagerAuditDto.class);
				UserDto nextUser = CommonDataUtil.getUserDtoByUidAndMobile(dis.getCreateUid());
				orderListDto.setCurrentHandlerUid(nextUser.getUid());
				orderListDto.setCurrentHandler(nextUser.getName());
			}
			result = goNextNode(next,orderListDto);
			// ==============发送短信Start===================
			UserDto nextUser = CommonDataUtil.getUserDtoByUidAndMobile(orderListDto.getCurrentHandlerUid());
			String type="债务置换";
			if("03".equals(baseBorrowDto.getProductCode())){
				type="畅贷";
			}else if("04".equals(baseBorrowDto.getProductCode())) {
				type="房抵贷";
			}
			if (StringUtil.isNotEmpty(nextUser.getMobile())) {
				String ipWhite = ConfigUtil.getStringValue(
						Constants.BASE_AMS_IPWHITE, ConfigUtil.CONFIG_BASE); // ip
				String cont = baseBorrowDto.getBorrowerName() + " "
						+ baseBorrowDto.getLoanAmount();
				AmsUtil.smsSend(nextUser.getMobile(), ipWhite, Constants.SMS_DEBT_SUBSTITUTION,type,cont, "待指派还款专员");
			}
			// ==============发送短信end===================
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
			} catch (Exception e) {
			e.printStackTrace();
			result.setCode(RespStatusEnum.FAIL.getCode());
			result.setMsg(RespStatusEnum.FAIL.getMsg());
		}
		return result;
	}
	/**
	 * 查询应还款计划信息
	 * @param request
	 * @param fundCompleteDto
	 * @return
	 */
	@RequestMapping(value = "/v/selectFund")
	public @ResponseBody
	RespDataObject<Map<String, Object>> selectFund(HttpServletRequest request,@RequestBody AllocationFundAduitDto fundCompleteDto) {
		RespDataObject<Map<String, Object>> rd = new RespDataObject<Map<String, Object>>();
		try {
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("orderNo", fundCompleteDto.getOrderNo());
			JSONObject jsons = httpUtil.getData(Constants.LINK_CREDIT,"/credit/third/api/hr/v/queryApply",map);
			log.info("返回信息"+jsons);
			//查询订单信息   
//			OrderBaseBorrowDto dto=new OrderBaseBorrowDto();
//			dto.setOrderNo(fundCompleteDto.getOrderNo());
//			RespDataObject<OrderBaseBorrowDto> obj=httpUtil.getRespDataObject(Enums.MODULAR_URL.CREDIT.toString(), ReqMappingConstants.CREDIT_ORDER_QUERYBORROW, dto,OrderBaseBorrowDto.class);
//			OrderBaseBorrowDto baseBorrowDto=  obj.getData();
			JSONObject data = jsons.getJSONObject("data");
			JSONObject lcAppoint = data.getJSONObject("lcAppoint");
			
			if(lcAppoint!=null){
				map.put("loanAmount", lcAppoint.get("applyAmt"));  //借款金额
				map.put("borrowingDays", lcAppoint.get("applyTnr"));  //借款期限
				try {
					List<AllocationFundDto> fundList = fundService.listDetail(fundCompleteDto.getOrderNo());
//					Integer fundId = 0;
					for (AllocationFundDto fund:fundList){
						if("110".equals(fund.getFundCode())){
//							fundId = fund.getFundId();
							map.put("dayRate",0.0306);  //费率
							map.put("overdueRate",0.0306);  //预期费率
							break;
						}
					}
//					CustomerFundCostDto customerFundCostDto = new CustomerFundCostDto();
//					customerFundCostDto.setFundId(fundId);
//					String productId=baseBorrowDto.getCityCode()+baseBorrowDto.getProductCode();
//					if(StringUtil.isNotEmpty(productId)) {
//						customerFundCostDto.setProductId(Integer.parseInt(productId));
//					}
//					RespDataObject<CustomerFundCostDto> baseobj = httpUtil.getRespDataObject(Enums.MODULAR_URL.CREDIT.toString(), "/credit/customer/fund/cost/v/selectCustomerFundCostByFundId", customerFundCostDto,CustomerFundCostDto.class);
//					if(RespStatusEnum.SUCCESS.getCode().equals(baseobj.getCode())){
//						CustomerFundCostDto costDto =baseobj.getData();
//						if(fundId==costDto.getFundId()){
//							map.put("dayRate",0.0306);  //费率
//							map.put("overdueRate",0.0306);  //预期费率
//						}
//					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			AllocationFundAduitDto aduitDto = alloctionFundAduitService.selectFundAudit(fundCompleteDto.getOrderNo());
			int borrowingDays=NumberUtils.toInt(lcAppoint.getString("applyTnr"));  //借款期限
			String time=null;
			if(aduitDto!=null && aduitDto.getLendingTime()!=null){
				Date newDate = DateUtils.addDate(aduitDto.getLendingTime(), (borrowingDays)); 
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				time=format.format(newDate);
			}
			map.put("lendingTime", time);  //应还款时间
			
			rd.setData(map);
			rd.setCode(RespStatusEnum.SUCCESS.getCode());
			rd.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			rd.setCode(RespStatusEnum.FAIL.getCode());
			rd.setMsg(RespStatusEnum.FAIL.getMsg());
		}
		return rd;
	}
	
	/**
	 * 华融提交
	 * @param request
	 * @param paramt
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/v/toAddFinance")
	public RespStatus toAddFinance(HttpServletRequest request,@RequestBody Map<String, Object> paramt){
		RespStatus result = new RespStatus();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			if(MapUtils.isEmpty(paramt)){
				result.setCode(RespStatusEnum.PARAMETER_ERROR.getCode());
				result.setMsg(RespStatusEnum.PARAMETER_ERROR.getMsg());
				return result;
			}
			
			//选下一处理人
//			String loanDirectiveUid = paramt.get("loanDirectiveUid").toString();//发放款指令uid
//			String financeUid = paramt.get("financeUid").toString(); //财务uid
			String orderNo=paramt.get("orderNo").toString();
			boolean isSubmit = isSubmit(orderNo,"fundAduit");
			if(isSubmit){
				result.setMsg("该订单已经被提交");
				return result;
			}
			boolean isWithdraw = isWithdraw(orderNo,"fundAduit");
			if(isWithdraw){
				result.setMsg("该订单已经被撤回");
				return result;
			}
			UserDto user = getUserDto(request);
			OrderFlowDto next = new OrderFlowDto();
			next.setOrderNo(orderNo);
			next.setCurrentProcessId("fundAduit");
			next.setCurrentProcessName("资金审批");
			next.setHandleUid(user.getUid());
			next.setHandleName(user.getName());
			next.setUpdateUid(user.getUid());
			
//			next.setNextProcessId("notarization");
//			next.setNextProcessName("公证");
			
			OrderListDto orderListDto = new OrderListDto();
			orderListDto.setOrderNo(orderNo);
			
			OrderBaseBorrowDto orderBaseBorrowDto=new OrderBaseBorrowDto();
			orderBaseBorrowDto.setOrderNo(orderNo);
			RespDataObject<OrderBaseBorrowDto> obj=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/order/borrowother/v/queryBorrow", orderBaseBorrowDto,OrderBaseBorrowDto.class);
			OrderBaseBorrowDto baseBorrowDto=  obj.getData();
			if(null!=baseBorrowDto.getAgencyId()&&baseBorrowDto.getAgencyId()>1){
				next.setNextProcessId("element");
				next.setNextProcessName("要件校验");
				orderListDto.setCurrentHandlerUid(baseBorrowDto.getElementUid());// 下一处理人要件管理员
				orderListDto.setCurrentHandler(baseBorrowDto.getElementName());
				// ==============发送短信Start===================
				UserDto nextUser = CommonDataUtil.getUserDtoByUidAndMobile(orderListDto.getCurrentHandlerUid());
				String type="债务置换";
				if("03".equals(baseBorrowDto.getProductCode())){
					type="畅贷";
				}else if("04".equals(baseBorrowDto.getProductCode())) {
					type="房抵贷";
				}
				if (StringUtil.isNotEmpty(nextUser.getMobile())) {
					String ipWhite = ConfigUtil.getStringValue(
							Constants.BASE_AMS_IPWHITE, ConfigUtil.CONFIG_BASE); // ip
					String cont = baseBorrowDto.getBorrowerName() + " "
							+ baseBorrowDto.getLoanAmount();
					AmsUtil.smsSend(nextUser.getMobile(), ipWhite, Constants.SMS_DEBT_SUBSTITUTION,type,cont, "待要件校验");
				}
				// ==============发送短信end===================
			}else{
				next.setNextProcessId("repaymentMember");
				next.setNextProcessName("指派还款专员");
				//查询受理经理uid
				OrderListDto listDto=new OrderListDto();
				listDto.setOrderNo(orderNo);
				listDto=httpUtil.getObject(Constants.LINK_CREDIT, "/credit/order/base/v/selectDetailByOrderNo", listDto, OrderListDto.class);
				if(listDto!=null && StringUtil.isNotBlank(listDto.getReceptionManagerUid())){
					UserDto nextUser = CommonDataUtil.getUserDtoByUidAndMobile(listDto.getReceptionManagerUid());
					orderListDto.setCurrentHandlerUid(nextUser.getUid());
					orderListDto.setCurrentHandler(nextUser.getName());
				}else{ //老数据
					ManagerAuditDto dis = new ManagerAuditDto();
					dis.setOrderNo(orderNo);
					dis = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/process/managerAudit/v/detail", dis, ManagerAuditDto.class);
					UserDto nextUser = CommonDataUtil.getUserDtoByUidAndMobile(dis.getCreateUid());
					orderListDto.setCurrentHandlerUid(nextUser.getUid());
					orderListDto.setCurrentHandler(nextUser.getName());
				}
				// ==============发送短信Start===================
				UserDto nextUser = CommonDataUtil.getUserDtoByUidAndMobile(orderListDto.getCurrentHandlerUid());
				String type="债务置换";
				if("03".equals(baseBorrowDto.getProductCode())){
					type="畅贷";
				}else if("04".equals(baseBorrowDto.getProductCode())) {
					type="房抵贷";
				}
				if (StringUtil.isNotEmpty(nextUser.getMobile())) {
					String ipWhite = ConfigUtil.getStringValue(
							Constants.BASE_AMS_IPWHITE, ConfigUtil.CONFIG_BASE); // ip
					String cont = baseBorrowDto.getBorrowerName() + " "
							+ baseBorrowDto.getLoanAmount();
					AmsUtil.smsSend(nextUser.getMobile(), ipWhite, Constants.SMS_DEBT_SUBSTITUTION,type,cont, "待指派还款专员");
				}
				// ==============发送短信end===================
			}
			
			result = goNextNode(next,orderListDto);

//			AllocationFundDto fund = new AllocationFundDto();
//			fund.setOrderNo(orderNo);
//			fund.setLoanDirectiveUid(loanDirectiveUid);
//			fund.setFinanceUid(financeUid);
//			fundService.updateByOrderNo(fund);

//			String type=paramt.get("type").toString();
//			if("1".equals(type)){   //推送融安信息
//				RespStatus status=httpUtil.getRespStatus(Constants.LINK_CREDIT, ReqMappingConstants.CREDIT_THIRD_API_REPAYMENT, paramt.get("auditDto"));  //流程项目
//				if("FAIL".equals(status.getCode())){
//					result.setCode(status.getCode());
//					result.setMsg("推送融安信息失败！");
//				}
//			}
			
			} catch (Exception e){
			log.error("新增资金审批信息异常,异常信息为:",e);
		}
		return result;
	}
	
	
	/**
	 * 定时器用到 获取所有数据
	 * @param request
	 * @param fundCompleteDto
	 * @return
	 */
	@RequestMapping(value = "/list")
	public @ResponseBody
	RespDataObject<List<AllocationFundAduitDto>> list(HttpServletRequest request,@RequestBody AllocationFundAduitDto fundCompleteDto) {
		RespDataObject<List<AllocationFundAduitDto>> rd = new RespDataObject<List<AllocationFundAduitDto>>();
		try {
			List<AllocationFundAduitDto> aduitlist=alloctionFundAduitService.selectAll();
			rd.setData(aduitlist);
			rd.setCode(RespStatusEnum.SUCCESS.getCode());
			rd.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			rd.setCode(RespStatusEnum.FAIL.getCode());
			rd.setMsg(RespStatusEnum.FAIL.getMsg());
		}
		return rd;
	}
	/**
	 * 定时器用到 查询订单信息
	 * @param request
	 * @param fundCompleteDto
	 * @return
	 */
	@RequestMapping(value = "/selectOrder")
	public @ResponseBody
	RespDataObject<Map<String, Object>> selectOrder(HttpServletRequest request,@RequestBody AllocationFundAduitDto fundCompleteDto) {
		RespDataObject<Map<String, Object>> rd = new RespDataObject<Map<String, Object>>();
		try {
			Map<String, Object> map=new HashMap<String, Object>();
			//查询订单信息   
			OrderBaseBorrowDto dto=new OrderBaseBorrowDto();
			dto.setOrderNo(fundCompleteDto.getOrderNo());
			RespDataObject<OrderBaseBorrowDto> obj=httpUtil.getRespDataObject(Constants.LINK_CREDIT,ReqMappingConstants.CREDIT_ORDER_QUERYBORROW, dto,OrderBaseBorrowDto.class);
			OrderBaseBorrowDto baseBorrowDto=  obj.getData();
			if(baseBorrowDto!=null){
				map.put("borrowerName", baseBorrowDto.getBorrowerName());
				map.put("loanAmount", baseBorrowDto.getLoanAmount());  //借款金额
			}
			AllocationFundAduitDto aduitDto = alloctionFundAduitService.selectFundAudit(fundCompleteDto.getOrderNo());
			if(aduitDto!=null){
				map.put("phone", aduitDto.getPhone());
			}
			log.info("资金审批定时查询信息"+map);
			rd.setData(map);
			rd.setCode(RespStatusEnum.SUCCESS.getCode());
			rd.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			rd.setCode(RespStatusEnum.FAIL.getCode());
			rd.setMsg(RespStatusEnum.FAIL.getMsg());
		}
		return rd;
	}
	/**
	 * 定时器用到 修改状态
	 * @param request
	 * @param fundCompleteDto
	 * @return
	 */
	@RequestMapping(value = "/updataStatus")
	public @ResponseBody
	RespStatus updataStatus(HttpServletRequest request,@RequestBody AllocationFundAduitDto fundCompleteDto) {
		RespStatus rd = new RespStatus();
		try {
			alloctionFundAduitService.updataStatus(fundCompleteDto);
			rd.setCode(RespStatusEnum.SUCCESS.getCode());
			rd.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			rd.setCode(RespStatusEnum.FAIL.getCode());
			rd.setMsg(RespStatusEnum.FAIL.getMsg());
		}
		return rd;
	}
	@ResponseBody
	@RequestMapping("/v/selectFundAudit")
	public RespDataObject<AllocationFundAduitDto> selectFundAudit(HttpServletRequest request,@RequestBody AllocationFundAduitDto fundCompleteDto){
		RespDataObject<AllocationFundAduitDto> result = new RespDataObject<AllocationFundAduitDto>();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			if(StringUtils.isBlank(fundCompleteDto.getOrderNo())){
				result.setMsg("查询资金审批缺少参数!");
				return result;
			}
			AllocationFundAduitDto aduitDto = alloctionFundAduitService.selectFundAudit(fundCompleteDto.getOrderNo());
			result.setData(aduitDto);
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e){
			log.error("查询资金审批状态异常:",e);
		}
		return result;
	}
}
