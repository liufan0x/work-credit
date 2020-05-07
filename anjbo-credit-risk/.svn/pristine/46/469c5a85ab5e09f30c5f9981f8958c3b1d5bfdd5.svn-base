package com.anjbo.controller;

import com.anjbo.bean.common.BankDto;
import com.anjbo.bean.common.SubBankDto;
import com.anjbo.bean.customer.CustomerFundDto;
import com.anjbo.bean.customer.FundAdminDto;
import com.anjbo.bean.order.OrderBaseBorrowDto;
import com.anjbo.bean.order.OrderBaseBorrowRelationDto;
import com.anjbo.bean.order.OrderFlowDto;
import com.anjbo.bean.order.OrderListDto;
import com.anjbo.bean.product.ProductDto;
import com.anjbo.bean.risk.AllocationFundDto;
import com.anjbo.bean.risk.BusinfoDto;
import com.anjbo.bean.risk.FinalAuditDto;
import com.anjbo.bean.risk.HuaanDto;
import com.anjbo.bean.risk.OfficerAuditDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.*;
import com.anjbo.service.AllocationFundService;
import com.anjbo.service.AlloctionFundAduitService;
import com.anjbo.service.HuaanService;
import com.anjbo.utils.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.json.JSONObject;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 分配资金
 * @author admin
 *
 */
@Controller
@RequestMapping("/credit/risk/allocationfund")
public class AllocationFundController extends BaseController{
	
	private static final Log log = LogFactory.getLog(AllocationFundController.class);

	private static final String FREE_LOAN = "03";//畅贷
	@Resource
	private AlloctionFundAduitService alloctionFundAduitService;
	@Resource
	private AllocationFundService fundService;
	@Resource
	private HuaanService huaanService;
	@ResponseBody
	@RequestMapping(value = "/v/toFinance")
	public RespStatus toFinance(HttpServletRequest request,@RequestBody List<AllocationFundDto> fund){
		RespStatus result = new RespStatus();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			
			if(null==fund||fund.size()<=0){
				result.setMsg("请选择资金分配");
				return result;
			}

			AllocationFundDto f = fund.get(0);
			
			boolean isSubmit = isSubmit(f.getOrderNo(),"allocationFund");
			if(isSubmit){
				result.setMsg("该订单已经被提交");
				return result;
			}
			boolean isWithdraw = isWithdraw(f.getOrderNo(),"allocationFund");
			if(isWithdraw){
				result.setMsg("该订单已经被撤回");
				return result;
			}
			
			UserDto user = getUserDto(request);
			
			List<String> tmpList = new ArrayList<String>(); 
			String fundDesc = "";
			String fundCode = "";
			for(AllocationFundDto obj:fund){
				obj.setAuditTime(new Date());
				obj.setHandleUid(user.getUid());
				obj.setCreateUid(user.getUid());
				obj.setLoanDirectiveUid(f.getLoanDirectiveUid());
				if(f.getFinanceUid()==null){
					List<AllocationFundDto> list = fundService.listDetail(f.getOrderNo());
					if(list!=null && list.size()>0){
						AllocationFundDto dto=list.get(0);
						obj.setFinanceUid(dto.getFinanceUid());
					}else{
						result.setMsg("该订单未选择过财务处理人！");
						return result;
					}
				}else{
					obj.setFinanceUid(f.getFinanceUid());
				}
				tmpList.add(obj.getFundCode());
				fundDesc += obj.getFundDesc()+",";
				fundCode += obj.getFundCode()+",";
			}

			String disableFund = isFundDisable(tmpList,httpUtil);
			if(StringUtils.isNotBlank(disableFund)){
				result.setMsg("该"+disableFund+"资金方已被禁用!");
				return result;
			}
			
			fundService.insert(fund);

			OrderFlowDto next = new OrderFlowDto();
			next.setOrderNo(f.getOrderNo());
			next.setCurrentProcessId("allocationFund");
			next.setCurrentProcessName("推送金融机构");

			next.setHandleUid(user.getUid());
			next.setHandleName(user.getName());
			next.setUpdateUid(user.getUid());
			

			UserDto nextUser = null;
//			boolean flg = isFreeLoan(f.getOrderNo());			
//			if(!flg) {  //是否为畅贷
//				ManagerAuditDto dis = new ManagerAuditDto();
//				dis.setOrderNo(f.getOrderNo());
//				dis = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/process/managerAudit/v/detail", dis, ManagerAuditDto.class);
//				nextUser = CommonDataUtil.getUserDtoByUidAndMobile(dis.getCreateUid());
//				next.setNextProcessId("repaymentMember");
//				next.setNextProcessName("指派还款专员");

				OrderListDto listDto=new OrderListDto();
				listDto.setOrderNo(f.getOrderNo());
				RespDataObject<OrderListDto> listobj=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/order/base/v/selectDetailByOrderNo", listDto,OrderListDto.class);
				listDto=listobj.getData();
				OrderBaseBorrowDto orderBaseBorrowDto=new OrderBaseBorrowDto();
				orderBaseBorrowDto.setOrderNo(f.getOrderNo());
				RespDataObject<OrderBaseBorrowDto> obj=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/order/borrowother/v/queryBorrow", orderBaseBorrowDto,OrderBaseBorrowDto.class);
				OrderBaseBorrowDto baseBorrowDto=  obj.getData();
				if(listDto.getAuditSort()==1){  //先审批再面签
					if("04".equals(baseBorrowDto.getProductCode())){ //房抵贷
						nextUser = CommonDataUtil.getUserDtoByUidAndMobile(baseBorrowDto.getFacesignUid());
						next.setNextProcessId("facesign");
						next.setNextProcessName("面签");
					}else{
						nextUser = CommonDataUtil.getUserDtoByUidAndMobile(baseBorrowDto.getNotarialUid());
						next.setNextProcessId("notarization");
						next.setNextProcessName("公证");
						String ipWhite = ConfigUtil.getStringValue(Constants.BASE_AMS_IPWHITE,ConfigUtil.CONFIG_BASE); //ip
						String ProductName="债务置换";
						if(baseBorrowDto!=null && !"01".equals(baseBorrowDto.getProductCode()) && !"02".equals(baseBorrowDto.getProductCode())){
							ProductName=baseBorrowDto.getProductName();
						}
						try {
							//发送给公证操作人
							AmsUtil.smsSend(nextUser.getMobile(), ipWhite, Constants.SMS_TEMPLATE_PROCESS, ProductName,baseBorrowDto.getBorrowerName(),baseBorrowDto.getLoanAmount(),"公证");
						} catch (Exception e) {
							log.info("推送金融机构发送短信给公证失败");
						}
						
					}
				}else{//先面签后审批
					if(1000 <= baseBorrowDto.getLoanAmount()){  //借款金额大于1000万 走法务
						next.setNextProcessId("auditJustice");
						next.setNextProcessName("法务审批");
						OfficerAuditDto officerAuditDto=new OfficerAuditDto();
						officerAuditDto.setOrderNo(f.getOrderNo());
						officerAuditDto = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/risk/officer/v/detail" ,officerAuditDto, OfficerAuditDto.class);
						if(officerAuditDto!=null && null!=officerAuditDto.getJusticeUid()){
							nextUser=CommonDataUtil.getUserDtoByUidAndMobile(officerAuditDto.getJusticeUid());
						}else{
							FinalAuditDto finalAuditDto=new FinalAuditDto();
							finalAuditDto.setOrderNo(f.getOrderNo());
							finalAuditDto = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/risk/final/v/detail" ,finalAuditDto, FinalAuditDto.class);
							if(finalAuditDto!=null && (null !=finalAuditDto.getOfficerUid() && finalAuditDto.getOfficerUidType()==2)){
								nextUser=CommonDataUtil.getUserDtoByUidAndMobile(finalAuditDto.getOfficerUid());
							}else{
								result.setCode(RespStatusEnum.FAIL.getCode());
								result.setMsg("暂无法务处理人！");
								return result;
							}
							
						}
					}else{
						if("05".equals(baseBorrowDto.getProductCode())) {
							next.setNextProcessId("element");
							next.setNextProcessName("要件校验");
							nextUser=CommonDataUtil.getUserDtoByUidAndMobile(baseBorrowDto.getElementUid());
						}else {
							next.setNextProcessId("fundDocking");
							next.setNextProcessName("资料推送");
							nextUser=CommonDataUtil.getUserDtoByUidAndMobile(user.getUid());
						}
//						OrderFlowDto flowDto = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/order/flow/v/selectEndOrderFlow", next,OrderFlowDto.class);
//						 if(flowDto!=null&&flowDto.getIsNewWalkProcess()!=2){ //不重新走流程
//							//发送短信
//							String ipWhite = ConfigUtil.getStringValue(Constants.BASE_AMS_IPWHITE,ConfigUtil.CONFIG_BASE); //ip
//							String ProductName="债务置换";
//							if(baseBorrowDto!=null && !"01".equals(baseBorrowDto.getProductCode()) && !"02".equals(baseBorrowDto.getProductCode())){
//								ProductName=baseBorrowDto.getProductName();
//							}
//							try {
//								//发送给操作人
//								AmsUtil.smsSend(nextUser.getMobile(), ipWhite, Constants.SMS_TEMPLATE_MANAGER, ProductName,baseBorrowDto.getBorrowerName(),baseBorrowDto.getLoanAmount(),"资料推送");
//							} catch (Exception e) {
//								log.info("推送金融机构发送短信给资料推送失败");
//							}
//						
//						 }
					}
				}
//				next.setNextProcessId("fundAduit");
//				next.setNextProcessName("资金审批");
//			} else{
//				nextUser = CommonDataUtil.getUserDtoByUidAndMobile(f.getFinanceUid());
//				next.setNextProcessId("lendingHarvest");
//				next.setNextProcessName("收利息");
//			}
			
			OrderListDto orderListDto = new OrderListDto();
			orderListDto.setOrderNo(f.getOrderNo());
//			String uid=ConfigUtil.getStringValue(Constants.RISK_FUNDADUIT_UID,ConfigUtil.CONFIG_BASE);
			orderListDto.setCurrentHandlerUid(nextUser.getUid());
			orderListDto.setCurrentHandler(nextUser.getName());
			result = goNextNode(next,orderListDto);
			/*
			if(StringUtils.isNotBlank(fundCode)){
				fundCode = fundCode.substring(0,fundCode.length()-1);
				ReportDto dto = new ReportDto();
				dto.setOrderNo(f.getOrderNo());
				dto.setFund(fundCode);
				dto.setUpdateUid(user.getUid());
				httpUtil.getRespStatus(Constants.LINK_CREDIT,"/credit/finance/report/v/update",dto);
			}*/
			try {
				//发送短信
				String ipWhite = ConfigUtil.getStringValue(Constants.BASE_AMS_IPWHITE,ConfigUtil.CONFIG_BASE); //ip
				String productName="债务置换";
				if(baseBorrowDto!=null && !"01".equals(baseBorrowDto.getProductCode()) && !"02".equals(baseBorrowDto.getProductCode())){
					productName=baseBorrowDto.getProductName();
				}
				//发送给受理员
				UserDto userDto=CommonDataUtil.getUserDtoByUidAndMobile(baseBorrowDto.getAcceptMemberUid());
				log.info("发送短信信息为：电话号码："+userDto.getMobile()+","+productName+","+baseBorrowDto.getBorrowerName()+","+baseBorrowDto.getLoanAmount()+"推送金融机构");
				AmsUtil.smsSend(userDto.getMobile(), ipWhite, Constants.SMS_TEMPLATE_PROCESS_TOEND, productName,baseBorrowDto.getBorrowerName(),baseBorrowDto.getLoanAmount(),"推送金融机构");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.error("短信发送失败,异常信息为:",e);
			}
		
		} catch (Exception e){
			log.error("新增资金方信息异常,异常信息为:",e);
		}
		return result;
	}
	
	/**
	 * 华融提交
	 * @param request
	 * @param fund
	 * @return
	 */
	@RequestMapping("/v/fundAduit")
	@ResponseBody
	public RespStatus fundAduit(HttpServletRequest request,@RequestBody List<AllocationFundDto> fund){
		RespStatus result = new RespStatus();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			if(null==fund||fund.size()<=0){
				result.setMsg("请选择资金分配");
				return result;
			}
			HttpUtil http = new HttpUtil();

			AllocationFundDto f = fund.get(0);

			boolean isSubmit = isSubmit(f.getOrderNo(),"allocationFund");
			if(isSubmit){
				result.setMsg("该订单已经被提交");
				return result;
			}
			boolean isWithdraw = isWithdraw(f.getOrderNo(),"allocationFund");
			if(isWithdraw){
				result.setMsg("该订单已经被撤回");
				return result;
			}

			UserDto user = getUserDto(request);

			List<String> tmpList = new ArrayList<String>();

			for(AllocationFundDto obj:fund){
				obj.setAuditTime(new Date());
				obj.setHandleUid(user.getUid());
				obj.setCreateUid(user.getUid());
				obj.setLoanDirectiveUid(f.getLoanDirectiveUid());
				if(f.getFinanceUid()==null){
					List<AllocationFundDto> list = fundService.listDetail(f.getOrderNo());
					if(list!=null && list.size()>0){
						AllocationFundDto dto=list.get(0);
						obj.setFinanceUid(dto.getFinanceUid());
					}else{
						result.setMsg("该订单未选择过财务处理人！");
						return result;
					}
				}else{
					obj.setFinanceUid(f.getFinanceUid());
				}
				obj.setIsHuarongPush(f.getIsHuarongPush());
				tmpList.add(obj.getFundCode());
			}

			String disableFund = isFundDisable(tmpList,http);
			if(StringUtils.isNotBlank(disableFund)){
				result.setMsg("该"+disableFund+"资金方已被禁用!");
				return result;
			}

			fundService.insert(fund);

			alloctionFundAduitService.deleteByOrderNo(f.getOrderNo());

			OrderFlowDto next = new OrderFlowDto();
			next.setOrderNo(f.getOrderNo());
			next.setCurrentProcessId("allocationFund");
			next.setCurrentProcessName("推送金融机构");

			next.setHandleUid(user.getUid());
			next.setHandleName(user.getName());
			next.setUpdateUid(user.getUid());
			

			UserDto nextUser = null;
//			boolean flg = isFreeLoan(f.getOrderNo());			
//			if(!flg) {  //是否为畅贷
//				ManagerAuditDto dis = new ManagerAuditDto();
//				dis.setOrderNo(f.getOrderNo());
//				dis = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/process/managerAudit/v/detail", dis, ManagerAuditDto.class);
//				nextUser = CommonDataUtil.getUserDtoByUidAndMobile(dis.getCreateUid());
//				next.setNextProcessId("repaymentMember");
//				next.setNextProcessName("指派还款专员");

				OrderListDto listDto=new OrderListDto();
				listDto.setOrderNo(f.getOrderNo());
				RespDataObject<OrderListDto> listobj=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/order/base/v/selectDetailByOrderNo", listDto,OrderListDto.class);
				listDto=listobj.getData();
				OrderBaseBorrowDto orderBaseBorrowDto=new OrderBaseBorrowDto();
				orderBaseBorrowDto.setOrderNo(f.getOrderNo());
				RespDataObject<OrderBaseBorrowDto> obj=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/order/borrowother/v/queryBorrow", orderBaseBorrowDto,OrderBaseBorrowDto.class);
				OrderBaseBorrowDto baseBorrowDto=  obj.getData();
				if(listDto.getAuditSort()==1){  //先审批再面签
					if("04".equals(baseBorrowDto.getProductCode())){ //房抵贷
						nextUser = CommonDataUtil.getUserDtoByUidAndMobile(baseBorrowDto.getFacesignUid());
						next.setNextProcessId("facesign");
						next.setNextProcessName("面签");
					}else{
						nextUser = CommonDataUtil.getUserDtoByUidAndMobile(baseBorrowDto.getNotarialUid());
						next.setNextProcessId("notarization");
						next.setNextProcessName("公证");
						String ipWhite = ConfigUtil.getStringValue(Constants.BASE_AMS_IPWHITE,ConfigUtil.CONFIG_BASE); //ip
						String ProductName="债务置换";
						if(baseBorrowDto!=null && !"01".equals(baseBorrowDto.getProductCode()) && !"02".equals(baseBorrowDto.getProductCode())){
							ProductName=baseBorrowDto.getProductName();
						}
						//发送给公证操作人
						AmsUtil.smsSend(nextUser.getMobile(), ipWhite, Constants.SMS_TEMPLATE_PROCESS, ProductName,baseBorrowDto.getBorrowerName(),baseBorrowDto.getLoanAmount(),"公证");
						
					}
				}else{//先面签后审批
					if(1000 <= baseBorrowDto.getLoanAmount()){  //借款金额大于1000万 走法务
						next.setNextProcessId("auditJustice");
						next.setNextProcessName("法务审批");
						OfficerAuditDto officerAuditDto=new OfficerAuditDto();
						officerAuditDto.setOrderNo(f.getOrderNo());
						officerAuditDto = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/risk/officer/v/detail" ,officerAuditDto, OfficerAuditDto.class);
						if(officerAuditDto!=null && null!=officerAuditDto.getJusticeUid()){
							nextUser=CommonDataUtil.getUserDtoByUidAndMobile(officerAuditDto.getJusticeUid());
						}else{
							result.setCode(RespStatusEnum.FAIL.getCode());
							result.setMsg("暂无法务处理人！");
							return result;
						}
					}else{
						if("05".equals(baseBorrowDto.getProductCode())) {
							next.setNextProcessId("element");
							next.setNextProcessName("要件校验");
							nextUser=CommonDataUtil.getUserDtoByUidAndMobile(baseBorrowDto.getElementUid());
						}else {
							next.setNextProcessId("fundDocking");
							next.setNextProcessName("资料推送");
							nextUser=CommonDataUtil.getUserDtoByUidAndMobile(user.getUid());
						}
						//发送短信
//						String ipWhite = ConfigUtil.getStringValue(Constants.BASE_AMS_IPWHITE,ConfigUtil.CONFIG_BASE); //ip
//						String ProductName="债务置换";
//						if(baseBorrowDto!=null && !"01".equals(baseBorrowDto.getProductCode()) && !"02".equals(baseBorrowDto.getProductCode())){
//							ProductName=baseBorrowDto.getProductName();
//						}
//						//发送给操作人
//						AmsUtil.smsSend(nextUser.getMobile(), ipWhite, Constants.SMS_TEMPLATE_MANAGER, ProductName,baseBorrowDto.getBorrowerName(),baseBorrowDto.getLoanAmount(),"资料推送");
					
					}
				}
//				next.setNextProcessId("fundAduit");
//				next.setNextProcessName("资金审批");
//			} else{
//				nextUser = CommonDataUtil.getUserDtoByUidAndMobile(f.getFinanceUid());
//				next.setNextProcessId("lendingHarvest");
//				next.setNextProcessName("收利息");
//			}
			
			OrderListDto orderListDto = new OrderListDto();
			orderListDto.setOrderNo(f.getOrderNo());
//			String uid=ConfigUtil.getStringValue(Constants.RISK_FUNDADUIT_UID,ConfigUtil.CONFIG_BASE);
			orderListDto.setCurrentHandlerUid(nextUser.getUid());
			orderListDto.setCurrentHandler(nextUser.getName());
			result = goNextNode(next,orderListDto);
			//发送短信
			String ipWhite = ConfigUtil.getStringValue(Constants.BASE_AMS_IPWHITE,ConfigUtil.CONFIG_BASE); //ip
			String ProductName="债务置换";
			if(baseBorrowDto!=null && !"01".equals(baseBorrowDto.getProductCode()) && !"02".equals(baseBorrowDto.getProductCode())){
				ProductName=baseBorrowDto.getProductName();
			}
			//发送给受理员
			UserDto userDto=CommonDataUtil.getUserDtoByUidAndMobile(baseBorrowDto.getAcceptMemberUid());
			AmsUtil.smsSend(userDto.getMobile(), ipWhite, Constants.SMS_TEMPLATE_PROCESS_TOEND, ProductName,baseBorrowDto.getBorrowerName(),baseBorrowDto.getLoanAmount(),"推送金融机构");
		
		} catch (Exception e){
			log.error("订单流转资金审批节点异常:",e);
		}
		return result;
	}
	/**
	 * 判断资金方是否被禁用
	 * @param fund
	 * @param http
	 * @return list 被禁用资金方
	 */
	public String isFundDisable(List<String> fund,HttpUtil http){
		FundAdminDto param = new FundAdminDto();
		param.setStatus(100);
		List<FundAdminDto> list = http.getList(Constants.LINK_CREDIT, "/credit/customer/fund/v/list",param, FundAdminDto.class);
		StringBuilder buf = new StringBuilder();
		for(FundAdminDto tmp:list){
			if(tmp.getStatus()==0&&fund.contains(tmp.getFundCode())){
				buf.append(tmp.getFundCode()).append(",");
			}
		}
		return buf.toString();
	}

	@ResponseBody
	@RequestMapping(value = "/v/detail")
	public RespDataObject<List<AllocationFundDto>> detail(HttpServletRequest request,@RequestBody AllocationFundDto obj){
		RespDataObject<List<AllocationFundDto>> result = new RespDataObject<List<AllocationFundDto>>();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			if(StringUtils.isBlank(obj.getOrderNo())){
				result.setMsg("加载资金方详情失败,订单编号不能为空!");
				return result;
			}
			List<AllocationFundDto> list = fundService.listDetail(obj.getOrderNo());
			result.setData(list);
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e){
			log.error("查询资金方详情信息异常,异常信息为:",e);
		}
		return result;
	}
	
	/**
	 * 修改放款金额为推送的借款金额
	 * @param request
	 * @param obj
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/v/fundDetail")
	public RespDataObject<List<AllocationFundDto>> fundDetail(HttpServletRequest request,@RequestBody AllocationFundDto obj){
		RespDataObject<List<AllocationFundDto>> result = new RespDataObject<List<AllocationFundDto>>();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			if(StringUtils.isBlank(obj.getOrderNo())){
				result.setMsg("加载资金方详情失败,订单编号不能为空!");
				return result;
			}
			
			int fundId=0;
			CustomerFundDto customerFundDto = new CustomerFundDto();
			customerFundDto = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/report/fund/v/queryFund", customerFundDto, CustomerFundDto.class);
			if(customerFundDto!=null){
				fundId = customerFundDto.getId();
				log.info("资方"+fundId+"查询检索条件");
			}
			OrderBaseBorrowDto orderBaseBorrowDto = new OrderBaseBorrowDto();
			orderBaseBorrowDto.setOrderNo(obj.getOrderNo());
			orderBaseBorrowDto = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/report/fund/v/borrow", orderBaseBorrowDto, OrderBaseBorrowDto.class);
			List<AllocationFundDto> list = fundService.listDetail(obj.getOrderNo());
			List<AllocationFundDto> fundList = new ArrayList<AllocationFundDto>();
			for (AllocationFundDto allocationFundDto : list) {
				if(allocationFundDto.getFundId()==customerFundDto.getId()){
					if(orderBaseBorrowDto!=null&&orderBaseBorrowDto.getLoanAmount()!=null){
						allocationFundDto.setLoanAmount(orderBaseBorrowDto.getLoanAmount());
					}
					fundList.add(allocationFundDto);
				}
				
			}
			result.setData(fundList);
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e){
			log.error("查询资金方详情信息异常,异常信息为:",e);
		}
		return result;
	}
	/**
	 * 加载资金方
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/v/listRelation")
	public RespDataObject<List<FundAdminDto>> listRelation(HttpServletRequest request){
		RespDataObject<List<FundAdminDto>> result = new RespDataObject<List<FundAdminDto>>();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			HttpUtil http = new HttpUtil();
			FundAdminDto param = new FundAdminDto();
			param.setStatus(1);
			List<FundAdminDto> list = http.getList(Constants.LINK_CREDIT, "/credit/customer/fund/v/list",param, FundAdminDto.class);
			result.setData(list);
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e){
			log.error("查询资金方列表异常,异常信息为:", e);
		}
		return result;
	}
	/**
	 * 加载华安推送数据
	 * @param request
	 * @param obj
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/v/loadPushOrder")
	public RespDataObject<HuaanDto> loadPushOrder(HttpServletRequest request,@RequestBody HuaanDto obj){
		RespDataObject<HuaanDto> result = new RespDataObject<HuaanDto>();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			if(StringUtils.isBlank(obj.getOrderNo())){
				result.setMsg("加载推送订单数据失败,缺少订单编号!");
				return result;
			}
			UserDto user = getUserDto(request);
			obj.setCreateUid(user.getUid());
			obj = fundService.loadPushOrder(obj);
			//获取产品名称
			List<ProductDto> prductList = getProductDtos();
			for (ProductDto productDto : prductList) {
				if(productDto.getProductCode().equals(obj.getServiceType())){
					obj.setProductName(productDto.getProductName());
					break;
				}
			}
			result.setData(obj);
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e){
			log.error("资金方加载推送信息异常,异常信息为:", e);
		}
		return result;
	}
	
	/***
	 * 同步至华安
	 * @return
	 */
	public void synchronizationHA(final String orderNo){
		try {
			//调用华安接口 提交资金方为华安的数据
			List<AllocationFundDto> fund = fundService.listDetail(orderNo);

			AllocationFundDto huaanFund = null;
			for (AllocationFundDto f : fund) { 
				//循环资金方
				if(Enums.FundEnums.HABX.getFundCode().equals(f.getFundCode())){ //判断是否为华安
					huaanFund = f;
					break;
				}
			}
			if(null==huaanFund)return;
			OrderBaseBorrowDto borrow = new OrderBaseBorrowDto();
			borrow.setOrderNo(orderNo);
			HttpUtil http = new HttpUtil();
			RespDataObject<OrderBaseBorrowDto> orderobj=http.getRespDataObject(Constants.LINK_CREDIT, "/credit/order/borrowother/v/queryBorrow", borrow,OrderBaseBorrowDto.class);
			borrow=  orderobj.getData();String typeId = ConfigUtil.getStringValue(Constants.BASE_TYPE_ID_TRADED,ConfigUtil.CONFIG_BASE);
			String pdfTypeId = ConfigUtil.getStringValue(Constants.BASE_PDF_TYPE_TRADED,ConfigUtil.CONFIG_BASE);
			if(null!=borrow&&borrow.getProductCode().equals("02")){

				typeId = ConfigUtil.getStringValue(Constants.BASE_TYPE_ID_NON_TRADED,ConfigUtil.CONFIG_BASE);
				pdfTypeId = ConfigUtil.getStringValue(Constants.BASE_PDF_TYPE_NON_TRADED,ConfigUtil.CONFIG_BASE);
			}
			//获取图片
			Map<String, Object> params = new HashMap<String, Object>();
			
			params.put("orderNo", orderNo);
			params.put("typeId",typeId); //,10008
			
			//图片转PDF
			Map<String,Object> pdfParams = new HashMap<String,Object>();
			
			pdfParams.put("orderNo", orderNo);
			pdfParams.put("typeId",pdfTypeId);
			
			 List<BusinfoDto> buslist = huaanService.selectListMap(params);
			 String imgs = "";
			 if(buslist.size()>0){
				 for(BusinfoDto b:buslist){
					 imgs += b.getUrl()+";";
				 }
			 }
			List<BusinfoDto> pdflist = huaanService.selectListMap(pdfParams);
			Map<String,Object> psfMap = getPdfImgs(pdflist);
			psfMap = null==psfMap?new HashMap<String,Object>():psfMap;
			
			String trun = "";
			if(StringUtils.isNotBlank(imgs)){
				//String url = "http://fs.anjbo.com/fs/ftp/uploadNew";
				//String url = "http://127.0.0.1:8078/fs/ftp/uploadNew";
				String url = ConfigUtil.getStringValue(Constants.LINK_FS_URL,ConfigUtil.CONFIG_LINK)+"/fs/ftp/uploadNew";
				Map<String,Object> param = new HashMap<String,Object>();
				param.put("imgs",imgs.substring(0,imgs.length()-1));
				param.put("orderNo", orderNo);
				param.put("separator", "/");
				param.put("pdfImgs", psfMap);
				trun = HttpUtil.jsonPost(url, param,(1000*30));   //同步图片到华安服务器
				log.info("图片同步结果:url="+url+";"+trun+";imgs======"+imgs+"orderNo======"+orderNo);
			 }
			
			int type=1; //成功
			int isHuaanPush = 2;
			String msg="";
			if(trun.equals("0") || trun=="0"){  //同步华安数据
				try {
					DesEncrypterUtil desEncrypter = new DesEncrypterUtil("3409d0e771dc7cf7707ff6fd0518a030");
					String prat = huaAn(orderNo,typeId);
					log.info("华安数据："+prat);

					String paramUrl = ConfigUtil.getStringValue(Constants.LINK_HUAAN_URL,ConfigUtil.CONFIG_LINK);
					Map<String,String> paramss = new HashMap<String,String>();
					paramss.put("accid","ANJBO");
					paramss.put("type","HAXB0001");
					paramss.put("params",desEncrypter.encrypt(prat));
					String rspCod = HttpUtil.jsonPost(paramUrl, paramss);
					
					log.info("return------------:"+rspCod);
					System.out.println(rspCod);
					
					JSONObject json = JSONObject.fromObject(rspCod);
					 String code = json.getString("rspCod");
					 if(!"00000".equals(code)){
						 type=2;
						 isHuaanPush = 3;
					 }
					 msg = json.getString("rspMsg");
				} catch (Exception e) {
					e.printStackTrace();
					 type=2;
					 isHuaanPush = 3;
					 msg="数据同步华安失败";
				}
			}else{
				type=2;
				isHuaanPush = 3;
				msg="图片同步华安失败";
			}
			log.info("huaAnReturnMsg:"+msg);
			if(msg==null){
				synchronizationHA(orderNo);
			}else{
				HuaanDto dto = new HuaanDto();
				dto.setOrderNo(orderNo);
				dto.setType(type);
				dto.setMsg(msg);

				huaanService.update(dto);
				huaanFund.setIsHuaanPush(isHuaanPush);
				fundService.update(huaanFund);
			}
			

		} catch (Exception e) {
			e.printStackTrace();
			log.error("synchronizationHA:"+e);
			//synchronizationHA(orderNo);
		}
	}
	
	public Map<String,Object> getPdfImgs(List<BusinfoDto> pdflist){
		if(null==pdflist||pdflist.size()<=0)return null;
		
		List<Map<String,Object>> tmpList = new ArrayList<Map<String,Object>>();
		Map<String,Object> tmp = null;
		for(BusinfoDto b:pdflist){
			tmp = new HashMap<String,Object>();
			if(b.getTypeId()==10202||20302==b.getTypeId()){
				tmp.put("D", b.getUrl());
			} else if(b.getTypeId()==10204){
				tmp.put("H", b.getUrl());
			} else if(b.getTypeId()==20401||b.getTypeId()==10253){
				tmp.put("L", b.getUrl());
			} else if(b.getTypeId()==10252||b.getTypeId()==10253){
				tmp.put("M", b.getUrl());
			} else if(b.getTypeId()==10008||20008==b.getTypeId()){
				tmp.put("E", b.getUrl());
			} else if(b.getTypeId()==20003){
				tmp.put("P", b.getUrl());
			}
			if(tmp.size()>0){
				tmpList.add(tmp);
			}
		}
		Map<String,Object> map = classification(tmpList);
		return map;
	}
	
	public Map<String,Object> classification(List<Map<String,Object>> list){
		
		Iterator<Map<String,Object>> it = list.iterator();
		Map<String,Object> map = new HashMap<String,Object>();
		String url = "";
		String tmpUrl = "";
		while(it.hasNext()){
			Map<String,Object> tmp = it.next();
			if(tmp.containsKey("D")&&StringUtils.isNotBlank(MapUtils.getString(tmp, "D", ""))){
				url = MapUtils.getString(map, "D","");
				tmpUrl = MapUtils.getString(tmp, "D", "");
				url = StringUtils.isBlank(url)?tmpUrl:url+";"+tmpUrl;
				map.put("D", url);
			} else if(tmp.containsKey("H")&&StringUtils.isNotBlank(MapUtils.getString(tmp, "H", ""))){
				url = MapUtils.getString(map, "H","");
				tmpUrl = MapUtils.getString(tmp, "H", "");
				url = StringUtils.isBlank(url)?tmpUrl:url+";"+tmpUrl;
				map.put("H", url);
			} else if(tmp.containsKey("L")&&StringUtils.isNotBlank(MapUtils.getString(tmp, "L", ""))){
				url = MapUtils.getString(map, "L","");
				tmpUrl = MapUtils.getString(tmp, "L", "");
				url = StringUtils.isBlank(url)?tmpUrl:url+";"+tmpUrl;
				map.put("L", url);
			} else if(tmp.containsKey("M")&&StringUtils.isNotBlank(MapUtils.getString(tmp, "M", ""))){
				url = MapUtils.getString(map, "M","");
				tmpUrl = MapUtils.getString(tmp, "M", "");
				url = StringUtils.isBlank(url)?tmpUrl:url+";"+tmpUrl;
				map.put("M", url);
			} else if(tmp.containsKey("E")&&StringUtils.isNotBlank(MapUtils.getString(tmp, "E", ""))){
				url = MapUtils.getString(map, "E","");
				tmpUrl = MapUtils.getString(tmp, "E", "");
				url = StringUtils.isBlank(url)?tmpUrl:url+";"+tmpUrl;
				map.put("E", url);
			} else if(tmp.containsKey("P")&&StringUtils.isNotBlank(MapUtils.getString(tmp, "P", ""))){
				url = MapUtils.getString(map, "P","");
				tmpUrl = MapUtils.getString(tmp, "P", "");
				url = StringUtils.isBlank(url)?tmpUrl:url+";"+tmpUrl;
				map.put("P", url);
			}
		}
		return map;
	}
	/**
	 * 华安的订单数据
	 * @param orderNo
	 * @return
	 */
	public  String huaAn(String orderNo,String typeId){
		String str = "";
		Map<String,Object> allMap = new HashMap<String,Object>();
		try {
			HuaanDto obj = huaanService.detail(orderNo);
			if(1==obj.getIsOldLoanBank()){
				Map<String,Object> map = getBank(obj.getHouseLoanBankId(),obj.getHouseLoanSubBankId());
				obj.setHouseLoanBankName(MapUtils.getString(map, "bankId",""));
				obj.setHouseLoanSubBankName(MapUtils.getString(map, "subBankId",""));
			}
			//-------------------客户信息Start------------//
			//婚姻状况
			String indivMarSt="10"; //未婚
			if("已婚无子女".equals(obj.getCustomerMarriageState())){
				indivMarSt="20";
			}else if("已婚有子女".equals(obj.getCustomerMarriageState())){
				indivMarSt="21";
			}else if("离异".equals(obj.getCustomerMarriageState())){
				indivMarSt="22";
			}else if("复婚".equals(obj.getCustomerMarriageState())){
				indivMarSt="23";
			}else if("丧偶".equals(obj.getCustomerMarriageState())){
				indivMarSt="30";
			}else{
				indivMarSt="90";  //未说明的婚姻状况
			}
			Map<String,Object> tmp = new HashMap<String,Object>();
			tmp.put("cusName", obj.getCustomerName());
			tmp.put("cusType", "110");
			tmp.put("certType", "10");
			tmp.put("certCode", obj.getIdCardNo());
			tmp.put("phone", obj.getPhoneNumber());
			tmp.put("indivMarSt", indivMarSt);
			tmp.put("indivSpsName", obj.getCustomerWifeName());
			
			allMap.put("cusInfo", tmp);
			//-------------------贷款信息Start------------//
			String ransomType="00";  //赎楼类型 
			if(StringUtils.isNotBlank(obj.getServiceType())&&"02".equals(obj.getServiceType())){  //非交易类
				ransomType = "01";
			}
			tmp = new HashMap<String,Object>();
			
			if(null!=obj.getLoanAmount()){
				obj.setLoanAmount(NumberUtil.multiply(obj.getLoanAmount(),10000d));
			}
			if(null!=obj.getOldHouseLoanBalance()){
				obj.setOldHouseLoanBalance(NumberUtil.multiply(obj.getOldHouseLoanBalance(),10000d));
			}
			tmp.put("loanType", "05240303");
			tmp.put("ransomType", ransomType);
			tmp.put("applyAmt", obj.getLoanAmount());
			tmp.put("oldHouseAmt", obj.getOldHouseLoanBalance());
			tmp.put("termType", "0"+obj.getPeriodType());
			tmp.put("term", obj.getApplicationPeriod());
			tmp.put("repaymentMode", "6");
			tmp.put("repayAccountName", obj.getPaymentBankCardName());
			tmp.put("repayBank", obj.getPaymentBankName());
			tmp.put("repayAccount", obj.getPaymentBankNumber());
			tmp.put("assureMeans", "00");
			tmp.put("inputBrId", "01080001");
			tmp.put("partnerNo", "PRJ20161222036808");
				
			allMap.put("loanInfo", tmp);
			//-------------------房产信息Start------------//
			String houseCertNo = "";
			if(StringUtils.isNotBlank(obj.getHousePropertyNumber())){
				houseCertNo = obj.getHousePropertyNumber();
			}
			tmp = new HashMap<String,Object>();
			tmp.put("houseAddr", obj.getHouseName());
			tmp.put("houseOwnerName", obj.getPropertyName());
			tmp.put("houseOwnerCertCode", obj.getPropertyCardNumber());
			tmp.put("houseCertNo", houseCertNo);
			allMap.put("houseInfo", tmp);
				
			//-------------------买家贷款信息Start------------//
			String bankName = "";
			if(1==obj.getIsOldLoanBank()){
				bankName = obj.getHouseLoanBankName()+"-"+obj.getHouseLoanSubBankName();
				if( obj.getHouseLoanBankName()==null || obj.getHouseLoanBankName()=="null" || obj.getHouseLoanBankName()==""){
					bankName = "";
				}
			} else {
				bankName = obj.getHouseLoanAddress();
			}
			
			if(obj.getHouseSuperviseAmount()==null){
				obj.setHouseSuperviseAmount(0.00);
			}
			if(obj.getHouseLoanAmount()==null){
				obj.setHouseLoanAmount(0.00);
			}
			double buyerAmount = obj.getHouseLoanAmount();
			if(buyerAmount!=0.0){
				buyerAmount = obj.getHouseLoanAmount()*10000;
			}
			double buyerFirstAmount = obj.getHouseSuperviseAmount();
			if(buyerFirstAmount!=0.0){
				buyerFirstAmount = obj.getHouseSuperviseAmount()*10000;
			}
			tmp = new HashMap<String,Object>();
			tmp.put("buyerFirstAmount", buyerFirstAmount);
			tmp.put("buyerAmount", buyerAmount);
			tmp.put("buyerBankName", bankName);
			allMap.put("buyerLoanInfo", tmp);
			
			//-------------------卖家信息Start------------//
			tmp = new HashMap<String,Object>();
			tmp.put("cusName", obj.getPropertyName());
			tmp.put("cusType", "110");
			tmp.put("certType", "10");
			tmp.put("certCode", obj.getPropertyCardNumber());
			allMap.put("sellerInfo", tmp);
			
			//-------------------买家信息Start------------//
			tmp = new HashMap<String,Object>();
			tmp.put("cusName", obj.getBuyName());
			tmp.put("cusType","110");
			tmp.put("certType","10");
			tmp.put("certCode",obj.getBuyCardNumber());
			allMap.put("buyerInfo", tmp);
			
			//-------------------保证人信息Start------------//
			tmp = new HashMap<String,Object>();
			tmp.put("cusType", "110");
			tmp.put("certType", "10");
			tmp.put("certCode", obj.getGuaranteeCardNumber());
			tmp.put("cusName", obj.getGuaranteeName());
			tmp.put("proposerRegard", obj.getGuaranteeName());
			allMap.put("guarInfo", tmp);
			//-------------------资料信息Start------------//
			 allMap.put("filePath", "/download/anjbo/"+orderNo+".zip");
			 tmp = new HashMap<String,Object>();
			 List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>();
			 Map<String, Object> pareamt = new HashMap<String, Object>();
			 pareamt.put("orderNo", orderNo);
			 pareamt.put("typeId", typeId); //,10008
			 List<BusinfoDto> buslist = huaanService.selectListMap(pareamt);
			 if(buslist.size()>0){
				 for(BusinfoDto b:buslist){
					 String fileType = "";
					 if(b.getTypeId()==10207){  //买方身份证正反面
						 fileType = "A1";
					 }else if(b.getTypeId()==10208){  //卖方身份证正反面
						 fileType = "A2";
					 }else if(20201==b.getTypeId()){    //借款人身份证正反面
						 fileType = "B1";
					 }else if(20202==b.getTypeId()){  //配偶身份证正反面
						 fileType = "B2";
					 }else if(10185==b.getTypeId()||20205==b.getTypeId()){  //借款人征信授权书   ---征信报告
						 fileType = "C";
					 }else if(10202==b.getTypeId()){  //原借款人还款清单/贷款余额表
						 fileType = "D";
					 }else if(10008==b.getTypeId()||20008==b.getTypeId()){  //投保单
						 fileType = "E";
					 }else if(10009==b.getTypeId()||20009==b.getTypeId()){  //面签照片
						 fileType = "F";
					 }else if(10209==b.getTypeId()||20305==b.getTypeId()){  //回款银行卡信息
						 fileType = "G";
					 }else if(10204==b.getTypeId()){  //房屋买卖合同
						 fileType = "H";
					 }else if(10006==b.getTypeId()||20007==b.getTypeId()){  //公证委托书
						 fileType = "J";
					 }else if(10203==b.getTypeId()||20303==b.getTypeId()){  //房产证
						 fileType = "K";
					 }else if(20401==b.getTypeId()){  //银行批复
						 fileType = "L";
					 }else if(10251==b.getTypeId()){  //资金监管协议或回单
						 fileType = "M";
					 }else if(10211==b.getTypeId()||20306==b.getTypeId()){    //赎楼平台审批意见单
						 fileType = "N";
					 }else if(10002==b.getTypeId()||10003==b.getTypeId()||20002==b.getTypeId()||20003==b.getTypeId()){  //资金方借款合同
						 fileType = "P";
					 }
					 if(StringUtils.isNotBlank(fileType)){
						 String fileName=b.getUrl().substring(b.getUrl().lastIndexOf("/")+1, b.getUrl().length());  //图片名称
						 tmp.put("fileName", fileName);
						 tmp.put("fileType", fileType);
						 listMap.add(tmp);
						 tmp = new HashMap<String,Object>();
					 }
				 }
			 }
			allMap.put("fileInfos", listMap);
			ObjectMapper json = new ObjectMapper();
			str = json.writeValueAsString(allMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return str;
	}
	/**
	 * 保存推送华安数据
	 * @param request
	 * @param obj
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/v/insertHuaan")
	public RespStatus insertHuaan(HttpServletRequest request,@RequestBody HuaanDto obj){
		RespStatus result = new RespStatus();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
		try{
			if(StringUtils.isBlank(obj.getOrderNo())){
				result.setMsg("保存华安数据异常,订单编号不能为空");
				return result;
			}
			UserDto user = getUserDto(request);
			obj.setCreateUid(user.getUid());
			obj.setType(0);
			huaanService.update(obj);

			final String orderNo = obj.getOrderNo();

			scheduledThreadPool.schedule(new Runnable() {
				public void run() {
					synchronizationHA(orderNo);
				}
			}, 0, TimeUnit.SECONDS);
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e){
			log.error("保存推送华安数据异常,异常信息为:", e);
		} finally {
			if(null!=scheduledThreadPool) {
				scheduledThreadPool.shutdown();
			}
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/v/huaanInsertOrDetail")
	public RespDataObject<HuaanDto> huaanDetail(HttpServletRequest request,@RequestBody HuaanDto dto){
		RespDataObject<HuaanDto> result = new RespDataObject<HuaanDto>();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			if(StringUtils.isBlank(dto.getOrderNo())){
				result.setMsg("加载华安资金方数据异常,缺少参数");
				return result;
			}
			HuaanDto huaan = huaanService.detail(dto.getOrderNo());
			if(null==huaan){
				UserDto user = getUserDto(request);
				dto.setCreateUid(user.getUid());
				dto.setType(0);
				huaanService.insert(dto);
				result.setData(dto);
			} else {
				result.setData(huaan);
			}
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e){
			log.error("加载推送华安资金方数据异常,异常信息为:", e);
		}
		return result;
	}
	
	/**
	 * @param request
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/v/loadBusInfo")
	public RespDataObject<Map<String,Object>> loadBusInfo(HttpServletRequest request,@RequestBody Map<String,Object> map){
		RespDataObject<Map<String,Object>> result = new RespDataObject<Map<String,Object>>();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			if(StringUtils.isBlank(MapUtils.getString(map, "orderNo", ""))){
				result.setMsg("缺少订单编号");
				return result;
			}
			 result =  huaanService.getBusinfoTypeTree(map);
			 result.setCode(RespStatusEnum.SUCCESS.getCode());
			 result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e){
			log.error("资金方推送华安信息加载影像资料失败", e);
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/v/addImg")
	public RespDataObject<Map<String,Object>> addImg(HttpServletRequest request,@RequestBody Map<String,Object> map){
		RespDataObject<Map<String,Object>> result = new RespDataObject<Map<String,Object>>();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			if(!map.containsKey("orderNo")){
				result.setMsg("订单编号不能为空");
				return result;
			}
			UserDto user = getUserDto(request);
			map.put("createUid", user.getUid());
			huaanService.insretImg(map);
			result = huaanService.getBusinfoTypeTree(map);
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e){
			log.error("资金方推送华安信息上传影像资料失败", e);
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/test")
	public RespStatus test(HttpServletRequest request,@RequestBody Map<String,Object> map){
		RespStatus restul = new RespStatus();
		//String tmp = huaAn(huaan.getOrderNo(),ConfigUtil.getStringValue(Constants.TYPE_ID_NON_TRADED,ConfigUtil.CONFIG_BASE));
		//synchronizationHA(huaan.getOrderNo());

		/*
		Set<String> keys = map.keySet();
		for(String key:keys){
			System.out.println(key+"--"+MapUtils.getString(map, key));
		}*/
		//System.out.println(map);
		//String tmp = huaAn(huaan.getOrderNo(),ConfigUtil.getStringValue(Constants.TYPE_ID_NON_TRADED,ConfigUtil.CONFIG_BASE));
		//synchronizationHA(huaan.getOrderNo());

		//Map<String,Object> map = new HashMap<String,Object>();
		//map.put("orderNo","2017070721240900000");
		//RespDataObject<Map<String,Object>> result = huaanService.getBusinfoTypeTree(map);

		Set<String> keys = map.keySet();
		for(String key:keys){
			System.out.println(key+"--"+MapUtils.getString(map, key));
		}
		restul.setCode(RespStatusEnum.SUCCESS.getCode());
		restul.setMsg(RespStatusEnum.SUCCESS.getMsg());
		return restul;

	}
	
	@ResponseBody
	@RequestMapping("/v/lookOver")
	public RespDataObject<List<BusinfoDto>> lookOver(HttpServletRequest request,@RequestBody Map<String,Object> map){
		RespDataObject<List<BusinfoDto>> result = new RespDataObject<List<BusinfoDto>>();
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		result.setCode(RespStatusEnum.FAIL.getCode());
		try{
			if(!map.containsKey("typeId")||!map.containsKey("orderNo")){
				result.setMsg("查询影像资料参数缺少");
				return result;
			}
			List<BusinfoDto> list = huaanService.selectListMap(map);
			result.setData(list);
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
			result.setCode(RespStatusEnum.SUCCESS.getCode());
		} catch (Exception e){
			log.error("推送105资金方加载删除影像资料异常,异常信息为:",e);
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/v/deleteImg")
	public RespDataObject<Map<String,Object>> deleteImg(HttpServletRequest request,@RequestBody Map<String,Object> map){
		RespDataObject<Map<String,Object>> result = new RespDataObject<Map<String,Object>>();
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		result.setCode(RespStatusEnum.FAIL.getCode());
		try{
			if(!map.containsKey("ids")){
				result.setMsg("删除影像资料异常,缺少主键");
				return result;
			}
			huaanService.deleteImg(map);
			List<BusinfoDto> list = huaanService.lookOver(map);
			Map<String,Object> all = huaanService.getBusinfoTypeTree(map).getData();
			map.put("allImg", all);
			map.put("thisImg", list);
			result.setData(map);
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
			result.setCode(RespStatusEnum.SUCCESS.getCode());
		} catch (Exception e){
			log.error("推送华安数据删除影像资料异常,异常信息为:",e);
		}
		return result;
	}
	/**
	 * 根据批量订单编号返回分配的资金方代号拼接
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/v/getOrderNoMosaicFundCode")
	public RespDataObject<Map<String,Object>> getOrderNoMosaicFundCode(HttpServletRequest request,@RequestBody Map<String,Object> map){
		RespDataObject<Map<String,Object>> result = new RespDataObject<Map<String,Object>>();
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		result.setCode(RespStatusEnum.FAIL.getCode());
		try{
			if(!map.containsKey("orderNo")){
				result.setMsg("缺少订单编号!");
				return result;
			}
			map = fundService.listFundByOrderNos(map);
			result.setData(map);
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
			result.setCode(RespStatusEnum.SUCCESS.getCode());
		} catch (Exception e){
			e.printStackTrace();
		}
		return result;
	}
	@ResponseBody
	@RequestMapping("/v/deleteAll")
	public RespStatus deleteAll(HttpServletRequest request,@RequestBody Map<String,Object> map){
		RespStatus result = new RespStatus();
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		result.setCode(RespStatusEnum.FAIL.getCode());
		try{
			if(!map.containsKey("orderNo")){
				result.setMsg("删除影像资料异常,缺少订单编号");
				return result;
			}
			huaanService.delete(MapUtils.getString(map, "orderNo"));
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
			result.setCode(RespStatusEnum.SUCCESS.getCode());
		} catch (Exception e){
			log.error("推送华安数据删除影像资料异常,异常信息为:",e);
		}
		return result;
	}
	/**
	 *
	 * @param bankId 银行ID
	 * @param subBankId 支行ID
	 * @return java.util.Map 银行bankId,subBankId为key,银行名称为value
	 */
	public static Map<String,Object> getBank(Integer bankId,Integer subBankId){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("bankId", "");
		map.put("subBankId","");
		if(null==bankId)return map;
		BankDto bankDto =CommonDataUtil.getBankNameById(bankId);
		map.put("bankId", bankDto==null?"":bankDto.getName());
		if(null==subBankId)return map;
		SubBankDto subBankDto = CommonDataUtil.getSubBankNameById(subBankId);
		map.put("subBankId", subBankDto==null?"":subBankDto.getName());
		return map;
	}
	/**
	 *
	 * @param bank 银行id集合
	 * @param subBank 支行id集合
	 * @return java.util.List map key 为银行id值为银行名称
	 */
	public static List<Map<Integer,Object>> getBank(List<Integer> bank,List<Integer> subBank){
		List<Map<Integer,Object>> list = new ArrayList<Map<Integer,Object>>();
		if(null==bank)return list;
        Map<Integer,Object> map = null;
        Iterator<Integer> it = bank.iterator();
        Integer id;
        while(it.hasNext()){
            id = it.next();
            BankDto bankDto =CommonDataUtil.getBankNameById(id);
            if(null != bankDto){
	            map = new HashMap<Integer,Object>();
	            map.put(id, bankDto.getName());
	            list.add(map);
	            it.remove();
            }
        }
		if(null==subBank)return list;
        Iterator<Integer> it1 = subBank.iterator();
        Integer sid;
        while(it1.hasNext()){
            sid = it1.next();
            SubBankDto subBankDto = CommonDataUtil.getSubBankNameById(sid);
            if(null != subBankDto){
	            map = new HashMap<Integer,Object>();
	            map.put(sid, subBankDto.getName());
	            list.add(map);
	            it1.remove();
            }
        }
		return list;
	}
	

	public static String getOrderNo(String orderNo){
		//假如是畅贷订单编号则返回置换贷款订单编号,详情通用
		OrderBaseBorrowRelationDto dto = new OrderBaseBorrowRelationDto();
		dto.setOrderNo(orderNo);
		HttpUtil http = new HttpUtil();
		dto = http.getObject(Constants.LINK_CREDIT, "/credit/order/relation/v/loanOrderNo", dto,OrderBaseBorrowRelationDto.class);
		if(null!=dto&&StringUtils.isNotBlank(dto.getOrderNo())){
			orderNo = dto.getOrderNo();
		}
		return orderNo;
	}

	/**
	 * 是否是畅贷订单
	 * @param orderNo
	 * @return
	 */
//	public static boolean isFreeLoan(String orderNo){
//		boolean flg = false;
//		HttpUtil http = new HttpUtil();
//		OrderBaseBorrowDto dto = new OrderBaseBorrowDto();
//		dto.setOrderNo(orderNo);
//		dto = http.getObject(Constants.LINK_CREDIT, "/credit/order/borrow/v/query", dto,OrderBaseBorrowDto.class);
//		if(null!=dto){
//			flg = dto.getIsChangLoan()==1?true:false;
//		}
//		return flg;
//	}

	/**
	 * 经理审批订单总计
	 * 已确认放款总计
	 * @param request
	 * @param reMap
	 * @return map key{auditCount=经理审批订单总计,lendingTotal=已确认放款总计}
	 */
	@ResponseBody
	@RequestMapping("/selectLendingTotalAndAuditCount")
	public RespDataObject<Map<String,Object>> selectSuccessLendingTotal(HttpServletRequest request,@RequestParam Map<String,Object> reMap){
		RespDataObject<Map<String,Object>> result = new RespDataObject<Map<String,Object>>();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("auditCount", 0);
		map.put("lendingTotal", 0);
		result.setData(map);
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		String date = new SimpleDateFormat("yyyymmdd").format(new Date());
		
		if(!reMap.containsKey("key")||!MD5Utils.MD5Encode("fc.anjbo.com"+date).equals(MapUtils.getString(reMap, "key", ""))){
			return result;
		}
		try{
			map = fundService.selectLendingTotalAndAuditCount();
			result.setData(map);
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e){
			log.error("查询统计经理审批订单总计,已放款订单总计异常,异常信息为:",e);
		}
		return result;
	}
	
	@RequestMapping("/imgPush")
	@ResponseBody
	public RespStatus imgPush(HttpServletRequest request,@RequestBody Map<String,Object> map){
		RespStatus result = new RespStatus();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
		try{

			if(StringUtils.isBlank(MapUtils.getString(map,"orderNo"))
					||StringUtils.isBlank(MapUtils.getString(map,"key"))){
				return result;
			}

			String date = new SimpleDateFormat("yyyymmdd").format(new Date());
			if(!MD5Utils.MD5Encode("sl.anjbo.com"+date).equals(MapUtils.getString(map, "key", ""))){
				return result;
			}
			final String orderNo = MapUtils.getString(map,"orderNo");
			scheduledThreadPool.schedule(new Runnable() {
				public void run() {
					synchronizationHA(orderNo);
				}
			}, 0, TimeUnit.SECONDS);
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(null!=scheduledThreadPool){
				scheduledThreadPool.shutdown();
			}
		}
		return result;
	}
	/**
	 * @param request
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/v/loadHuarongBusInfo")
	public RespDataObject<Map<String,Object>> loadHuarongBusInfo(HttpServletRequest request,@RequestBody Map<String,Object> map){
		RespDataObject<Map<String,Object>> result = new RespDataObject<Map<String,Object>>();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			if(StringUtils.isBlank(MapUtils.getString(map, "orderNo", ""))){
				result.setMsg("缺少订单编号");
				return result;
			}
			OrderBaseBorrowDto borrow = new OrderBaseBorrowDto();
			borrow.setOrderNo(MapUtils.getString(map,"orderNo"));
			HttpUtil http = new HttpUtil();
			borrow = http.getObject(Constants.LINK_CREDIT, "/credit/order/borrow/v/query",borrow, OrderBaseBorrowDto.class);
			String typeId = Constants.BASE_TYPE_ID_HUANGRONG_TRADED;
			Integer pid = 10143;
			if(null!=borrow&&borrow.getProductCode().equals("02")){
				typeId = Constants.BASE_TYPE_ID_HUANGRONG_NON_TRADED;
				pid = 20143;
			}
			Map<String,Object> tmpMap = new HashMap<String,Object>();
			tmpMap.put("name","评估报告");
			tmpMap.put("remark","评估报告");
			tmpMap.put("pid",pid);
			tmpMap.put("id",90000);//自定义id
			List<Map<String,Object>> listTmp = new ArrayList<Map<String,Object>>();
			listTmp.add(tmpMap);

            List<Map<String,Object>> tmpResult =  httpUtil.getList(Constants.LINK_CREDIT, "/credit/third/api/hr/v/queryFileApply", map,Map.class);
            
			result =  huaanService.getBusinfoTypeTree(map,typeId,listTmp,tmpResult);
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e){
			log.error("资金方推送华安信息加载影像资料失败", e);
		}
		return result;
	}
	public static void main(String[] args){
		String str = "{\"start\":0,\"pageSize\":0,\"sortName\":null,\"sortOrder\":null,\"createUid\":\"1501224715588\",\"createTime\":null,\"updateUid\":\"1501224715588\",\"updateTime\":null,\"sid\":null,\"device\":null,\"deviceId\":null,\"version\":\"anjbo-fc-V3.0\",\"key\":\"35816a35c90866c5eecedb5a4efda9037f98a969dafdc30f9c1ab8481a4bdf9e3957d7fd958edc9b72f987baae8f876113aba770254507c59bab65c69077b34baac933c3eca7f2e781771cbb924e49e0201a0776df433bfbc6a1521068cb70d761d569ecfe40f98b\",\"id\":49910,\"type\":0,\"orderNo\":\"2017092114341600000\",\"agencyId\":1,\"cityCode\":\"4403\",\"cityName\":\"深圳市\",\"branchCompany\":null,\"productCode\":\"01\",\"productName\":\"债务置换贷款（交易类）\",\"contractNo\":null,\"customerName\":\"策马奔腾\",\"borrowingAmount\":250,\"borrowingDay\":12,\"cooperativeAgencyId\":0,\"cooperativeAgencyName\":null,\"channelManagerUid\":null,\"channelManagerName\":null,\"acceptMemberUid\":\"1501224715588\",\"acceptMemberName\":\"陈飞\",\"planPaymentTime\":null,\"distancePaymentDay\":null,\"previousHandler\":null,\"previousHandlerUid\":null,\"previousHandleTime\":\"2017-09-21 14:34:17.0\",\"state\":\"待公证/待面签/待提单\",\"currentHandlerUid\":\"1501224715588\",\"currentHandler\":\"陈飞,陈飞,陈飞\",\"lendingTime\":null,\"lendingTimeStart\":null,\"lendingTimeEnd\":null,\"createTimeStart\":null,\"createTimeEnd\":null,\"processId\":\"placeOrder\",\"appShowValue1\":null,\"appShowValue2\":null,\"searchName\":null,\"source\":\"快马App\",\"notarialUid\":\"1501224715588\",\"facesignUid\":\"1501224715588\",\"relationOrderNo\":null}}";
	}
}
