package com.anjbo.controller;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.sql.dialect.mysql.ast.MysqlForeignKey.On;
import com.anjbo.bean.common.BankDto;
import com.anjbo.bean.common.SubBankDto;
import com.anjbo.bean.customer.FundAdminDto;
import com.anjbo.bean.order.OrderBaseBorrowDto;
import com.anjbo.bean.order.OrderBaseBorrowRelationDto;
import com.anjbo.bean.order.OrderBaseCustomerDto;
import com.anjbo.bean.order.OrderFlowDto;
import com.anjbo.bean.order.OrderListDto;
import com.anjbo.bean.product.ManagerAuditDto;
import com.anjbo.bean.product.ProductDto;
import com.anjbo.bean.risk.AllocationFundDto;
import com.anjbo.bean.risk.BusinfoDto;
import com.anjbo.bean.risk.DataAuditDto;
import com.anjbo.bean.risk.FirstAuditDto;
import com.anjbo.bean.risk.HuaanDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.Constants;
import com.anjbo.common.Enums;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.AllocationFundService;
import com.anjbo.service.AlloctionFundAduitService;
import com.anjbo.service.FundDockingService;
import com.anjbo.service.HuaanService;
import com.anjbo.utils.AmsUtil;
import com.anjbo.utils.CommonDataUtil;
import com.anjbo.utils.ConfigUtil;
import com.anjbo.utils.DesEncrypterUtil;
import com.anjbo.utils.HttpUtil;
import com.anjbo.utils.MD5Utils;
import com.anjbo.utils.NumberUtil;
import com.anjbo.utils.StringUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 资料推送
 * @author yis
 *
 */
@Controller
@RequestMapping("/credit/risk/fundDocking")
public class FundDockingController  extends BaseController{
	
	private static final Log log = LogFactory.getLog(AllocationFundController.class);

	private static final String FREE_LOAN = "03";//畅贷
	@Resource
	private AlloctionFundAduitService alloctionFundAduitService;
	@Resource
	private AllocationFundService fundService;
	@Resource
	private HuaanService huaanService;
	@Resource
	private FundDockingService dockingService;
	
	/**
	 * 非华融-保存
	 * @param request
	 * @param auditDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/v/toFinance")
	public RespStatus toFinance(HttpServletRequest request,@RequestBody DataAuditDto auditDto){
		RespStatus result = new RespStatus();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			boolean isSubmit = isSubmit(auditDto.getOrderNo(),"fundDocking");
			if(isSubmit){
				result.setMsg("该订单已经被提交");
				return result;
			}
			boolean isWithdraw = isWithdraw(auditDto.getOrderNo(),"fundDocking");
			if(isWithdraw){
				result.setMsg("该订单已经被撤回");
				return result;
			}
			UserDto user = getUserDto(request);
			auditDto.setUpdateUid(user.getUid());
			auditDto.setCreateUid(user.getUid());
			
			//添加
			dockingService.add(auditDto);
			OrderFlowDto next = new OrderFlowDto();
			OrderListDto orderListDto = new OrderListDto();
			next.setOrderNo(auditDto.getOrderNo());
			next.setCurrentProcessId("fundDocking");
			next.setCurrentProcessName("资料推送");
			next.setHandleUid(user.getUid());
			next.setHandleName(user.getName());
			
			//获取当前受理员uid
			OrderBaseBorrowDto orderBaseBorrowDto=new OrderBaseBorrowDto();
			orderBaseBorrowDto.setOrderNo(auditDto.getOrderNo());
			RespDataObject<OrderBaseBorrowDto> obj=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/order/borrowother/v/queryBorrow", orderBaseBorrowDto,OrderBaseBorrowDto.class);
			OrderBaseBorrowDto baseBorrowDto=  obj.getData();
			log.info("机构id:"+baseBorrowDto.getAgencyId()+"订单类型："+baseBorrowDto.getProductCode());
			if("03".equals(baseBorrowDto.getProductCode())){ //畅贷
				//是否关联置换贷

//				if(!"0".equals(auditDto.getRelationOrderNo()) && null != auditDto.getRelationOrderNo()){//关联到资料审核
//					next.setNextProcessId("dataAudit");
//					next.setNextProcessName("资料审核");
//					ManagerAuditDto managerAuditDto=new ManagerAuditDto();
//					managerAuditDto.setOrderNo(auditDto.getOrderNo());
//					RespDataObject<ManagerAuditDto> mobj=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/process/managerAudit/v/detail", managerAuditDto,ManagerAuditDto.class);
//					managerAuditDto = mobj.getData();
//					UserDto nextUser = CommonDataUtil.getUserDtoByUidAndMobile(managerAuditDto.getAuditFirstUid()); //风控初审UId
//					orderListDto.setCurrentHandlerUid(nextUser.getUid());
//					orderListDto.setCurrentHandler(nextUser.getName());
//				}else{//不关联到要件校验
				//查询是否是关联的畅贷，关联畅贷默认债务置换贷款的要件管理员
				if(StringUtils.isNotBlank(auditDto.getRelationOrderNo())){
					OrderBaseBorrowDto orderBaseBorrow=new OrderBaseBorrowDto();
					orderBaseBorrow.setOrderNo(auditDto.getRelationOrderNo());
					RespDataObject<OrderBaseBorrowDto> rBorrowResp=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/order/borrowother/v/queryBorrow", orderBaseBorrow,OrderBaseBorrowDto.class);
					OrderBaseBorrowDto borrow=  rBorrowResp.getData();
					orderListDto.setCurrentHandlerUid(borrow.getElementUid());// 下一处理人要件管理员
					orderListDto.setCurrentHandler(borrow.getElementName());
				}else{
					orderListDto.setCurrentHandlerUid(baseBorrowDto.getElementUid());// 下一处理人要件管理员
					orderListDto.setCurrentHandler(baseBorrowDto.getElementName());
				}
				next.setNextProcessId("element");
				next.setNextProcessName("要件校验");
				// ==============发送短信Start===================
				UserDto nextUser = CommonDataUtil.getUserDtoByUidAndMobile(orderListDto.getCurrentHandlerUid());
				if (StringUtil.isNotEmpty(nextUser.getMobile())) {
					String ipWhite = ConfigUtil.getStringValue(
							Constants.BASE_AMS_IPWHITE, ConfigUtil.CONFIG_BASE); // ip
					String cont = baseBorrowDto.getBorrowerName() + " "
							+ baseBorrowDto.getLoanAmount();
					AmsUtil.smsSend(nextUser.getMobile(), ipWhite, Constants.SMS_DEBT_SUBSTITUTION,"畅贷",cont, "待要件校验");
				}
				// ==============发送短信end===================
			}else if("04".equals(baseBorrowDto.getProductCode()) || "04"== baseBorrowDto.getProductCode()){ //房抵贷
				next.setNextProcessId("dataAudit");
				next.setNextProcessName("资料审核");
				FirstAuditDto first=new FirstAuditDto();
				first.setOrderNo(auditDto.getOrderNo());
				RespDataObject<FirstAuditDto> frist=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/risk/first/v/detail", first,FirstAuditDto.class);
				first = frist.getData();
				orderListDto.setCurrentHandlerUid(first.getHandleUid());//风控初审UId
				orderListDto.setCurrentHandler(first.getHandleName());
			}else{  //房抵贷
				if(null!=baseBorrowDto.getAgencyId()&&baseBorrowDto.getAgencyId()>1){  //非快鸽
					next.setNextProcessId("element");
					next.setNextProcessName("要件校验");
					orderListDto.setCurrentHandlerUid(baseBorrowDto.getElementUid());// 下一处理人要件管理员
					orderListDto.setCurrentHandler(baseBorrowDto.getElementName());
					// ==============发送短信Start===================
					UserDto nextUser = CommonDataUtil.getUserDtoByUidAndMobile(orderListDto.getCurrentHandlerUid());
					if (StringUtil.isNotEmpty(nextUser.getMobile())) {
						String ipWhite = ConfigUtil.getStringValue(
								Constants.BASE_AMS_IPWHITE, ConfigUtil.CONFIG_BASE); // ip
						String cont = baseBorrowDto.getBorrowerName() + " "
								+ baseBorrowDto.getLoanAmount();
						AmsUtil.smsSend(nextUser.getMobile(), ipWhite, Constants.SMS_DEBT_SUBSTITUTION,"债务置换",cont, "待要件校验");
					}
					// ==============发送短信end===================
				}else{
					next.setNextProcessId("repaymentMember");
					next.setNextProcessName("指派还款专员");
					//查询受理经理uid
					OrderListDto listDto=new OrderListDto();
					listDto.setOrderNo(auditDto.getOrderNo());
					listDto=httpUtil.getObject(Constants.LINK_CREDIT, "/credit/order/base/v/selectDetailByOrderNo", listDto, OrderListDto.class);
					if(listDto!=null && StringUtil.isNotBlank(listDto.getReceptionManagerUid())){
						UserDto nextUser = CommonDataUtil.getUserDtoByUidAndMobile(listDto.getReceptionManagerUid());
						orderListDto.setCurrentHandlerUid(nextUser.getUid());
						orderListDto.setCurrentHandler(nextUser.getName());
					}else{ //老数据
						ManagerAuditDto dis = new ManagerAuditDto();
						dis.setOrderNo(auditDto.getOrderNo());
						dis = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/process/managerAudit/v/detail", dis, ManagerAuditDto.class);
						UserDto nextUser = CommonDataUtil.getUserDtoByUidAndMobile(dis.getCreateUid());
						orderListDto.setCurrentHandlerUid(nextUser.getUid());
						orderListDto.setCurrentHandler(nextUser.getName());
					}
					// ==============发送短信Start===================
					UserDto nextUser = CommonDataUtil.getUserDtoByUidAndMobile(orderListDto.getCurrentHandlerUid());
					if (StringUtil.isNotEmpty(nextUser.getMobile())) {
						String ipWhite = ConfigUtil.getStringValue(
								Constants.BASE_AMS_IPWHITE, ConfigUtil.CONFIG_BASE); // ip
						String cont = baseBorrowDto.getBorrowerName() + " "
								+ baseBorrowDto.getLoanAmount();
						AmsUtil.smsSend(nextUser.getMobile(), ipWhite, Constants.SMS_DEBT_SUBSTITUTION,"债务置换",cont, "待指派还款专员");
					}
					// ==============发送短信end===================
				}
			}
			orderListDto.setOrderNo(auditDto.getOrderNo());
			result = goNextNode(next,orderListDto);
		} catch (Exception e){
			log.error("新增资料推送异常,异常信息为:",e);
		}
		return result;
	}
	/**
	 * 华融-保存
	 * @param request
	 * @param auditDto
	 * @return
	 */
	@RequestMapping("/v/fundAduit")
	@ResponseBody
	public RespStatus fundAduit(HttpServletRequest request,@RequestBody DataAuditDto auditDto){
		RespStatus result = new RespStatus();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			boolean isSubmit = isSubmit(auditDto.getOrderNo(),"fundDocking");
			if(isSubmit){
				result.setMsg("该订单已经被提交");
				return result;
			}
			boolean isWithdraw = isWithdraw(auditDto.getOrderNo(),"fundDocking");
			if(isWithdraw){
				result.setMsg("该订单已经被撤回");
				return result;
			}
			UserDto user = getUserDto(request);
			auditDto.setUpdateUid(user.getUid());
			auditDto.setCreateUid(user.getUid());
			//添加
			dockingService.add(auditDto);
			alloctionFundAduitService.deleteByOrderNo(auditDto.getOrderNo());
			//更新推送状态
			AllocationFundDto huaanFund =new AllocationFundDto();
			huaanFund.setOrderNo(auditDto.getOrderNo());
			huaanFund.setIsHuarongPush(auditDto.getIsHuarongPush());
			fundService.updateByOrderNo(huaanFund);
			
			OrderFlowDto next = new OrderFlowDto();
			OrderListDto orderListDto = new OrderListDto();
			next.setOrderNo(auditDto.getOrderNo());
			next.setCurrentProcessId("fundDocking");
			next.setCurrentProcessName("资料推送");
			next.setHandleUid(user.getUid());
			next.setHandleName(user.getName());
			next.setUpdateUid(user.getUid());
			orderListDto.setOrderNo(auditDto.getOrderNo());
			if(2==auditDto.getIsHuarongPush()){ //推送成功
				next.setNextProcessId("fundAduit");
				next.setNextProcessName("资金审批");
				orderListDto.setCurrentHandlerUid(user.getUid());
				orderListDto.setCurrentHandler(user.getName());
			}else{ //推送失败
				//获取当前受理员uid
				OrderBaseBorrowDto orderBaseBorrowDto=new OrderBaseBorrowDto();
				orderBaseBorrowDto.setOrderNo(auditDto.getOrderNo());
				RespDataObject<OrderBaseBorrowDto> obj=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/order/borrowother/v/queryBorrow", orderBaseBorrowDto,OrderBaseBorrowDto.class);
				OrderBaseBorrowDto baseBorrowDto=  obj.getData();
				if(null!=baseBorrowDto.getAgencyId()&&baseBorrowDto.getAgencyId()>1){  //非快鸽机构  下一流程要件
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
					listDto.setOrderNo(auditDto.getOrderNo());
					listDto=httpUtil.getObject(Constants.LINK_CREDIT, "/credit/order/base/v/selectDetailByOrderNo", listDto, OrderListDto.class);
					if(listDto!=null && StringUtil.isNotBlank(listDto.getReceptionManagerUid())){
						UserDto nextUser = CommonDataUtil.getUserDtoByUidAndMobile(listDto.getReceptionManagerUid());
						orderListDto.setCurrentHandlerUid(nextUser.getUid());
						orderListDto.setCurrentHandler(nextUser.getName());
					}else{ //老数据
						ManagerAuditDto dis = new ManagerAuditDto();
						dis.setOrderNo(auditDto.getOrderNo());
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
			}
			result = goNextNode(next,orderListDto);
		} catch (Exception e){
			log.error("订单流转资金审批节点异常:",e);
		}
		return result;
	}
	
	/**
	 * 普通资方保存信息
	 * @param request
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/v/ordinaryApply")
	public RespStatus ordinaryApply(HttpServletRequest request,@RequestBody Map<String,Object> map){
		RespStatus result = new RespStatus();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			if(!"04".equals(MapUtils.getObject(map, "productCode"))){
				UserDto user = getUserDto(request);
				if(map.containsKey("kgOrdinary")){ //借款信息
					Object kgOrdinary = MapUtils.getObject(map,"kgOrdinary");
					Map<String,Object> tmp = null;
					if(kgOrdinary instanceof Map){
						tmp = (Map<String,Object>)kgOrdinary;
						tmp.put("fundId", MapUtils.getString(map, "ordinaryFund"));
						tmp.put("createUid", user.getUid());
						tmp.put("updateUid", user.getUid());
						tmp.put("receivableForTime",MapUtils.getString(map, "receivableForTime"));
					}
					if(tmp==null){
						return result;
					}
					if("03".equals(MapUtils.getObject(map, "productCode")) && "0".equals(MapUtils.getObject(map, "relationOrderNo")) && "1".equals(MapUtils.getObject(tmp, "isLoanBank"))){
						Integer loanBankNameId= MapUtils.getInteger(tmp, "loanBankNameId");
						Integer loanSubBankNameId=MapUtils.getInteger(tmp, "loanSubBankNameId");
						if(loanBankNameId!=null){
							BankDto bankDto =CommonDataUtil.getBankNameById(loanBankNameId);
							tmp.put("loanBankName", bankDto.getName());
						}
						if(loanSubBankNameId!=null){
							SubBankDto subBankDto =CommonDataUtil.getSubBankNameById(loanSubBankNameId);
							tmp.put("loanSubBankName", subBankDto.getName());
						}
					}
					dockingService.addOrdinary(tmp);
				}
				if(map.containsKey("kgOrdinaryAudit")){  //审批信息
					Object kgOrdinaryAudit = MapUtils.getObject(map,"kgOrdinaryAudit");
					Map<String,Object> tmp = null;
					if(kgOrdinaryAudit instanceof Map){
						tmp = (Map<String,Object>)kgOrdinaryAudit;
						tmp.put("fundId", MapUtils.getString(map, "ordinaryFund"));
						tmp.put("createUid", user.getUid());
						tmp.put("updateUid", user.getUid());
						tmp.put("finalRemark", MapUtils.getString(map, "finalRemark"));
						tmp.put("officerRemark", MapUtils.getString(map, "officerRemark"));
						tmp.put("loanBankId", MapUtils.getString(map, "loanBankId")==null?0:MapUtils.getString(map, "loanBankId"));
						tmp.put("loanBankSubId", MapUtils.getString(map, "loanBankSubId")==null?0:MapUtils.getString(map, "loanBankSubId"));
						tmp.put("paymentBankId", MapUtils.getString(map, "paymentBankId")==null?0:MapUtils.getString(map, "paymentBankId"));
						tmp.put("paymentBankSubId", MapUtils.getString(map, "paymentBankSubId")==null?0:MapUtils.getString(map, "paymentBankSubId"));
					}
					if(tmp==null){
						return result;
					}
					dockingService.addOrdinaryAudit(tmp);
				}
			}
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e){
			log.error("分配资金方保存普通资方推送信息异常,异常信息为:",e);
		}
		return result;
	}
	/**
	 * 普通资方推送信息查询
	 * @param request
	 * @param obj
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/v/ordinaryDetail")
	public RespDataObject<Map<String,Object>> ordinaryDetail(HttpServletRequest request,@RequestBody AllocationFundDto obj){
		RespDataObject<Map<String,Object>> result = new RespDataObject<Map<String,Object>>();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			if(StringUtils.isBlank(obj.getOrderNo())){
				result.setMsg("加载资料推送详情失败,订单编号不能为空!");
				return result;
			}
			Map<String,Object> map=new HashMap<String, Object>();
			System.out.println("修改成功！+err");
			OrderBaseBorrowDto borrowDto=dockingService.findByOrdinary(obj.getOrderNo());
			FirstAuditDto auditDto =dockingService.findByOrdinaryAudit(obj.getOrderNo());
			map.put("ordinary", borrowDto);
			map.put("ordinaryAudit", auditDto);
			result.setData(map);
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e){
			log.error("查询资料推送信息异常,异常信息为:",e);
		}
		return result;
	}
	
	/**
	 * 详情
	 * @param request
	 * @param obj
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/v/detail")
	public RespDataObject<List<AllocationFundDto>> detail(HttpServletRequest request,@RequestBody AllocationFundDto obj){
		RespDataObject<List<AllocationFundDto>> result = new RespDataObject<List<AllocationFundDto>>();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			if(StringUtils.isBlank(obj.getOrderNo())){
				result.setMsg("加载资料推送详情失败,订单编号不能为空!");
				return result;
			}
			List<AllocationFundDto> list = fundService.listDetail(obj.getOrderNo());
			DataAuditDto auditDto=dockingService.fund(obj.getOrderNo());
			if(auditDto!=null && list.size()>0){
				list.get(0).setRemark(auditDto.getRemark());
			}
			result.setData(list);
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e){
			log.error("查询资料推送信息异常,异常信息为:",e);
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
			borrow=  orderobj.getData();
			
			String typeId = ConfigUtil.getStringValue(Constants.BASE_TYPE_ID_TRADED,ConfigUtil.CONFIG_BASE);
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
	
	/**
	 * 普通资方获取影响资料分类
	 * @param request
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/v/loadOrdinaryBusInfo")
	public RespDataObject<Map<String,Object>> loadOrdinaryBusInfo(HttpServletRequest request,@RequestBody Map<String,Object> map){
		RespDataObject<Map<String,Object>> result = new RespDataObject<Map<String,Object>>();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			if(StringUtils.isBlank(MapUtils.getString(map, "orderNo", ""))){
				result.setMsg("缺少订单编号");
				return result;
			}
			if(StringUtils.isBlank(MapUtils.getString(map, "ordinaryFund", ""))){
				result.setMsg("缺少资金编号");
				return result;
			}
			 result =  huaanService.getOrdinaryBusinfoType(map);
			 result.setCode(RespStatusEnum.SUCCESS.getCode());
			 result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e){
			log.error("推送普通资金方信息加载影像资料失败", e);
		}
		return result;
	}
	@ResponseBody
	@RequestMapping("/v/addOrdinaryImg")
	public RespDataObject<Map<String,Object>> addOrdinaryImg(HttpServletRequest request,@RequestBody Map<String,Object> map){
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
			result = huaanService.getOrdinaryBusinfoType(map);
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e){
			log.error("资金方推送华安信息上传影像资料失败", e);
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
	
	@ResponseBody
	@RequestMapping("/v/deleteOrdinaryImg")
	public RespDataObject<Map<String,Object>> deleteOrdinaryImg(HttpServletRequest request,@RequestBody Map<String,Object> map){
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
			Map<String,Object> all = huaanService.getOrdinaryBusinfoType(map).getData();
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
				result.setMsg("!缺少订单编号");
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
		SubBankDto subBankDto =CommonDataUtil.getSubBankNameById(subBankId);
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
	public static boolean isFreeLoan(String orderNo){
		boolean flg = false;
		HttpUtil http = new HttpUtil();
		OrderBaseBorrowDto dto = new OrderBaseBorrowDto();
		dto.setOrderNo(orderNo);
		dto = http.getObject(Constants.LINK_CREDIT, "/credit/order/borrow/v/query", dto,OrderBaseBorrowDto.class);
		if(null!=dto){
			flg = dto.getIsChangLoan()==1?true:false;
		}
		return flg;
	}

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
	
	@ResponseBody
	@RequestMapping(value = "/v/huarongDetail")
	public RespDataObject<Map<String,Object>> huarongDetail(HttpServletRequest request,@RequestBody AllocationFundDto obj){
		RespDataObject<Map<String,Object>> result = new RespDataObject<Map<String,Object>>();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			Map<String,Object> map=new HashMap<String, Object>();
			if(StringUtils.isBlank(obj.getOrderNo())){
				result.setMsg("加载资料推送详情失败,订单编号不能为空!");
				return result;
			}
			List<AllocationFundDto> list = fundService.listDetail(obj.getOrderNo());
			for(AllocationFundDto fundDto:list){
				if("110".equals(fundDto.getFundCode())){
					OrderBaseCustomerDto customerDto = new OrderBaseCustomerDto();
					customerDto.setOrderNo(obj.getOrderNo());
					HttpUtil http = new HttpUtil();
					RespDataObject<OrderBaseCustomerDto> customerObj=http.getRespDataObject(Constants.LINK_CREDIT, "/credit/order/customer/v/query", customerDto,OrderBaseCustomerDto.class);
					customerDto=  customerObj.getData();
					List<Map<String, Object>> mlist=new ArrayList<Map<String,Object>>();
					OrderBaseBorrowDto borrow = new OrderBaseBorrowDto();
					borrow.setOrderNo(obj.getOrderNo());
					RespDataObject<OrderBaseBorrowDto> orderobj=http.getRespDataObject(Constants.LINK_CREDIT, "/credit/order/borrowother/v/queryBorrow", borrow,OrderBaseBorrowDto.class);
					borrow=  orderobj.getData();
					if(customerObj!=null){
						Map<String, Object> cmap=new HashMap<String, Object>();
						cmap.put("customerName", customerDto.getCustomerName());
						cmap.put("customerCardNumber", customerDto.getCustomerCardNumber());
						cmap.put("customerType", borrow.getCustomerType());
						mlist.add(cmap);
						if(null!=customerDto.getCustomerWifeName() && ""!=customerDto.getCustomerWifeName()){
							cmap=new HashMap<String, Object>();
							cmap.put("customerName", customerDto.getCustomerWifeName());
							cmap.put("customerCardNumber", customerDto.getCustomerWifeCardNumber());
							cmap.put("customerType", 1);
							mlist.add(cmap);
						}
						map.put("mlist", mlist);
					}
				}
				
			}
			result.setData(map);
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e){
			log.error("查询资料推送信息异常,异常信息为:",e);
		}
		return result;
	}
	
	
}