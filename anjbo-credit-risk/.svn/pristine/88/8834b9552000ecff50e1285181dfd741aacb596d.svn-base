package com.anjbo.controller;

import com.anjbo.bean.common.BankDto;
import com.anjbo.bean.common.SubBankDto;
import com.anjbo.bean.element.DocumentsDto;
import com.anjbo.bean.order.*;
import com.anjbo.bean.report.StatisticsDto;
import com.anjbo.bean.risk.CreditDto;
import com.anjbo.bean.risk.EnquiryDto;
import com.anjbo.bean.risk.FirstAuditDto;
import com.anjbo.bean.risk.FirstForeclosureAuditDto;
import com.anjbo.bean.risk.FirstPaymentAuditDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.*;
import com.anjbo.service.CreditService;
import com.anjbo.service.EnquiryService;
import com.anjbo.service.FirstAuditService;
import com.anjbo.utils.AmsUtil;
import com.anjbo.utils.CommonDataUtil;
import com.anjbo.utils.ConfigUtil;
import com.anjbo.utils.HttpUtil;
import com.anjbo.utils.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 初审
 * @author huanglj
 *
 */
@Controller
@RequestMapping("/credit/risk/first/v")
public class FirstAuditController extends BaseController{
	
	private static final Log log = LogFactory.getLog(FirstAuditController.class);
	@Resource
	private FirstAuditService firstAuditService;
	@Resource
	private CreditService creditService;
	@Resource 
	private EnquiryService enquiryService;
	
	/**
	 * 新增初审审批信息
	 * @param request
	 * @param firstAudit
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/insert")
	public RespStatus insert(HttpServletRequest request,@RequestBody FirstAuditDto firstAudit){
		RespStatus result = new RespStatus();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			if(StringUtils.isBlank(firstAudit.getOrderNo())){
				result.setMsg("保存初审审批意见失败,缺少订单编号!");
				return result;
			}
			UserDto user = getUserDto(request);
			firstAudit.setCreateUid(user.getUid());
			firstAudit.setHandleUid(user.getUid());
			firstAudit.setAuditTime(new Date());
			if(null!=firstAudit.getOther() && StringUtil.isEmpty(firstAudit.getProductName())){
				OrderBaseBorrowDto dto=new OrderBaseBorrowDto();
				dto.setOrderNo(firstAudit.getOrderNo());
				RespDataObject<OrderBaseBorrowDto> baseDto=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/order/borrowother/v/queryBorrow", dto,OrderBaseBorrowDto.class);
				OrderBaseBorrowDto baseBorrowDto=  baseDto.getData();
				if(baseBorrowDto!=null){
					firstAudit.setProductName(baseBorrowDto.getProductName());
				}
			}
			firstAuditService.insert(firstAudit);
			if(null!=firstAudit.getForeclosureAuditList() && firstAudit.getForeclosureAuditList().size()>0){
				firstAuditService.delFirstForeclosure(firstAudit.getOrderNo());
				for(FirstForeclosureAuditDto  auditDto:firstAudit.getForeclosureAuditList()){
					auditDto.setOrderNo(firstAudit.getOrderNo());
					auditDto.setCreateUid(user.getUid());
					auditDto.setUpdateUid(user.getUid());
					firstAuditService.addFirstForeclosure(auditDto);
				}
			}
			if(null!=firstAudit.getFirstPaymentAuditList() && firstAudit.getFirstPaymentAuditList().size()>0){
				firstAuditService.delFirstPayment(firstAudit.getOrderNo());
				for(FirstPaymentAuditDto auditDto:firstAudit.getFirstPaymentAuditList()){
					auditDto.setOrderNo(firstAudit.getOrderNo());
					auditDto.setCreateUid(user.getUid());
					auditDto.setUpdateUid(user.getUid());
					firstAuditService.addFirstPayment(auditDto);
				}
			}
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e){
			log.error("新增初审审批信息异常,异常信息为:",e);
		}
		return result;
	}
	
	/**
	 * 初审审批详情
	 * @param request
	 * @param firstAudit
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/detail")
	public RespDataObject<FirstAuditDto> detail(HttpServletRequest request,@RequestBody FirstAuditDto firstAudit){
		RespDataObject<FirstAuditDto> result = new RespDataObject<FirstAuditDto>();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
//		appDetail(request, firstAudit);
		try{
			if(StringUtils.isBlank(firstAudit.getOrderNo())){
				result.setMsg("查询初审详情参数缺失,查询失败!");
				return result;
			}
			FirstAuditDto obj = firstAuditService.detail(firstAudit.getOrderNo());
			if(null!=obj.getOther() && StringUtil.isEmpty(obj.getProductName())){
				OrderBaseBorrowDto dto=new OrderBaseBorrowDto();
				dto.setOrderNo(firstAudit.getOrderNo());
				RespDataObject<OrderBaseBorrowDto> baseDto=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/order/borrowother/v/queryBorrow", dto,OrderBaseBorrowDto.class);
				OrderBaseBorrowDto baseBorrowDto=  baseDto.getData();
				if(baseBorrowDto!=null){
					obj.setProductName(baseBorrowDto.getProductName());
				}
			}
			UserDto user = null;
			if(StringUtils.isNotBlank(obj.getHandleUid())){
				user = CommonDataUtil.getUserDtoByUidAndMobile(obj.getHandleUid());
			}
			if(null!=user&&null!=obj){
				obj.setHandleName(user.getName());
			}
			if(null!=obj){
				//new 出/回款添加多行  2018-3-22
				List<FirstForeclosureAuditDto> foreclosureAuditList=firstAuditService.findforeclosureList(firstAudit.getOrderNo());
				obj.setForeclosureAuditList(foreclosureAuditList);
				List<FirstPaymentAuditDto> PaymentAuditList = firstAuditService.findPaymentList(firstAudit.getOrderNo());
				obj.setFirstPaymentAuditList(PaymentAuditList);
			}
			if(null !=firstAudit.getForeclosureAuditList() &&firstAudit.getForeclosureAuditList().size()>0){
				for(FirstForeclosureAuditDto auditDto:firstAudit.getForeclosureAuditList()){
					BankDto bankDto =CommonDataUtil.getBankNameById(auditDto.getLoanBankId());
					auditDto.setLoanBankName(bankDto==null ? "":bankDto.getName());
					SubBankDto subBankDto =CommonDataUtil.getSubBankNameById(auditDto.getLoanBankSubId());
					auditDto.setLoanBankSubName(subBankDto == null ?"":subBankDto.getName());
				}
			}
			if(null!=firstAudit.getFirstPaymentAuditList() && firstAudit.getFirstPaymentAuditList().size()>0){
				for(FirstPaymentAuditDto auditDto:firstAudit.getFirstPaymentAuditList()){
					BankDto bankDto =CommonDataUtil.getBankNameById(auditDto.getPaymentBankId());
					auditDto.setPaymentBankName(bankDto==null ? "":bankDto.getName());
					SubBankDto subBankDto =CommonDataUtil.getSubBankNameById(auditDto.getPaymentBankSubId());
					auditDto.setPaymentBankSubName(subBankDto == null ?"":subBankDto.getName());
				}
			}
//			if(null != obj.getLoanBankId() && null == obj.getLoanBankName()){
//				BankDto bankDto =CommonDataUtil.getBankNameById(obj.getLoanBankId());
//				obj.setLoanBankName(bankDto==null ? "":bankDto.getName());
//			}
//			if(null !=obj.getPaymentBankId() && null ==obj.getPaymentBankName()){
//				BankDto bankDto =CommonDataUtil.getBankNameById(obj.getPaymentBankId());
//				obj.setPaymentBankName(bankDto==null ? "":bankDto.getName());
//			}
//			if(null != obj.getLoanBankSubId() && null == obj.getLoanBankSubName()){
//				SubBankDto subBankDto =CommonDataUtil.getSubBankNameById(obj.getLoanBankSubId());
//				obj.setLoanBankSubName(subBankDto == null ?"":subBankDto.getName());
//			}
//			if(null != obj.getPaymentBankSubId() && null ==obj.getPaymentBankSubName()){
//				SubBankDto subBankDto =CommonDataUtil.getSubBankNameById(obj.getPaymentBankSubId());
//				obj.setPaymentBankSubName(subBankDto == null ?"":subBankDto.getName());
//			}
			result.setData(obj);
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e){
			log.error("查询初审审批详情信息异常,异常信息为:",e);
		}
		return result;
	}
	
	/**
	 * 初审编辑页加载数据
	 * @param request
	 * @param firstAudit
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/loadFirst")
	public RespDataObject<Map<String,Object>> loadFirst(HttpServletRequest request,@RequestBody FirstAuditDto firstAudit){
		RespDataObject<Map<String,Object>> result = new RespDataObject<Map<String,Object>>();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			if(StringUtils.isBlank(firstAudit.getOrderNo())){
				result.setMsg("加载初审审批信息异常,缺少参数!");
				return result;
			}
			FirstAuditDto firstDto = firstAuditService.detail(firstAudit.getOrderNo());
			if(null!=firstDto){
				//new 出/回款添加多行  2018-3-22
				List<FirstForeclosureAuditDto> foreclosureAuditList=firstAuditService.findforeclosureList(firstAudit.getOrderNo());
				firstDto.setForeclosureAuditList(foreclosureAuditList);
				List<FirstPaymentAuditDto> PaymentAuditList = firstAuditService.findPaymentList(firstAudit.getOrderNo());
				firstDto.setFirstPaymentAuditList(PaymentAuditList);
			}
			UserDto user = null;
			String orderNo = getOrderNo(firstAudit.getOrderNo());
			if (null!=firstDto && null !=firstDto.getOrderNo() && null !=firstDto.getProductName()){
				user = CommonDataUtil.getUserDtoByUidAndMobile(firstDto.getHandleUid());
			}else{
				///new 备注分成字段 2017-11-9 Start-------------
				OrderBaseBorrowDto dto=new OrderBaseBorrowDto();
				dto.setOrderNo(orderNo);
				RespDataObject<OrderBaseBorrowDto> baseDto=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/order/borrowother/v/queryBorrow", dto,OrderBaseBorrowDto.class);
				OrderBaseBorrowDto baseBorrowDto=  baseDto.getData();
				if(baseBorrowDto!=null && !"03".equals(baseBorrowDto.getProductCode())){
					firstDto.setProductName(baseBorrowDto.getProductName());
					firstDto.setLoanAmount(baseBorrowDto.getLoanAmount());
					firstDto.setAddressOld(baseBorrowDto.getOldLoanBankName());
					firstDto.setAddressNew(baseBorrowDto.getLoanBankName());
					//征信信息
					CreditDto credit = creditService.detail(orderNo);
					if(credit!=null){
						firstDto.setDebtRatio(credit.getLiabilitiesProportion());// 负债比率
						firstDto.setLoanPoportion(credit.getLoanPercentage());  //借款成数
						firstDto.setProductLoanPoportion(credit.getForeclosurePercentage());//债务置换贷款成数
						firstDto.setMonthNum(credit.getLatelyHalfYearSelectNumber()==null?0:credit.getLatelyHalfYearSelectNumber());
						firstDto.setYearNum(credit.getCreditOverdueNumber()==null?0:credit.getCreditOverdueNumber());
						firstDto.setMortgageSituation(credit.getPropertyMortgage());  //抵押情况
					}
					//房产交易信息
					OrderBaseHouseDto house=new OrderBaseHouseDto();
					house.setOrderNo(orderNo);
					RespDataObject<OrderBaseHouseDto> houstDto=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/order/house/v/query", house,OrderBaseHouseDto.class);
					OrderBaseHouseDto hDto=  houstDto.getData();
					if(hDto!=null){
						firstDto.setAmountOld(hDto.getOldHouseLoanAmount()); //原贷款金额
						firstDto.setAmountNew(hDto.getHouseLoanAmount());//新贷款金额
						JSONArray array= JSONArray.fromObject(hDto.getOrderBaseHousePropertyPeopleDto()); //产权人信息
						for (Object object : array) {
							JSONObject obj1 = JSONObject.fromObject(object);
							firstDto.setPropertyOwner(obj1.getString("propertyName")); //产权人姓名
							break;
						}
						JSONArray array2= JSONArray.fromObject(hDto.getOrderBaseHousePropertyDto()); //房产信息
						for (Object object : array2) {
							JSONObject obj1 = JSONObject.fromObject(object);
							firstDto.setPropertyName(obj1.getString("houseName")); //物业名称
							firstDto.setArea(obj1.getString("houseArchitectureSize")); //面积
							break;
						}
						
					}
					//询价
					List<EnquiryDto> enquiryMap = enquiryService.listEnquiry(orderNo);
					if(enquiryMap.size()>0){
						EnquiryDto enquiryDto=enquiryMap.get(0);
						if(StringUtil.isNotBlank(enquiryDto.getArea()))
						firstDto.setArea(enquiryDto.getArea());//面积
						if(StringUtil.isNotBlank(enquiryDto.getPropertyName()))
						firstDto.setPropertyName(enquiryDto.getPropertyName()); //物业名称
						firstDto.setAssessment(enquiryDto.getTotalPrice()); //评估总值
					}
					///new 备注分成字段 2017-11-9 end----------
					//要件信息
					DocumentsDto basics=new DocumentsDto();
					basics.setOrderNo(orderNo);
					RespDataObject<DocumentsDto> doc=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/element/basics/v/detail", basics,DocumentsDto.class);
					basics=doc.getData();
					if(null != basics){
						//出款
						if(null != basics.getForeclosureType()){
							List<FirstForeclosureAuditDto> flist=new ArrayList<FirstForeclosureAuditDto>();
							FirstForeclosureAuditDto foreclosureAuditDto=new FirstForeclosureAuditDto();
							foreclosureAuditDto.setLoanAccountType(basics.getForeclosureType().getAccountType());
							foreclosureAuditDto.setLoanName(basics.getForeclosureType().getBankCardMaster());
							foreclosureAuditDto.setLoanAccount(basics.getForeclosureType().getBankNo());
							Integer bankNameId = basics.getForeclosureType().getBankNameId();
							Integer bankSubNameId = basics.getForeclosureType().getBankSubNameId();
							foreclosureAuditDto.setLoanBankId(null==bankNameId?0:bankNameId);
							foreclosureAuditDto.setLoanBankSubId(null==bankSubNameId?0:bankSubNameId);
							flist.add(foreclosureAuditDto);
							firstDto.setForeclosureAuditList(flist);
						}
						//回款
						if(null != basics.getPaymentType()){
							List<FirstPaymentAuditDto> plist=new ArrayList<FirstPaymentAuditDto>();
							FirstPaymentAuditDto paymentAuditDto=new FirstPaymentAuditDto();
							paymentAuditDto.setPaymentAccountType(basics.getPaymentType().getPaymentaccountType());
							paymentAuditDto.setPaymentName(basics.getPaymentType().getPaymentBankCardName());
							paymentAuditDto.setPaymentAccount(basics.getPaymentType().getPaymentBankNumber());
							Integer paymentBankId = basics.getPaymentType().getPaymentBankNameId();
							Integer paymentBankSubNameId = basics.getPaymentType().getPaymentBankSubNameId();
							paymentAuditDto.setPaymentBankId(null==paymentBankId?0:paymentBankId);
							paymentAuditDto.setPaymentBankSubId(null==paymentBankSubNameId?0:paymentBankSubNameId);
							plist.add(paymentAuditDto);
							firstDto.setFirstPaymentAuditList(plist);
						}
					}
			  }
			}
			if(null!=user){
				firstDto.setHandleName(user.getName());
			}
			Map<String,Object> data = new HashMap<String,Object>();
			Map<String,Boolean> map = firstAuditService.getCreditFlagMap(orderNo);
			List<Map<String, Object>> listLog = creditService.selectCreditLog(firstAudit.getOrderNo());
			data.put("auditFirstShow", map.get("auditFirst"));  //是否显示审批通过
			data.put("auditFinalShow", map.get("auditFinal"));
			data.put("listLog", listLog);  //查询征信
			data.put("firstDto", firstDto);
			result.setData(data);
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e){
			log.error("加载初审审批信息异常,异常信息为:",e);
		}
		return result;
	}
	
	/**
	 * 初审审批通过
	 * @param request
	 * @param firstAudit
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/pass")
	public RespStatus pass(HttpServletRequest request,@RequestBody FirstAuditDto firstAudit){
		RespStatus result = new RespStatus();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			if(StringUtils.isBlank(firstAudit.getOrderNo())){
				result.setMsg("缺少订单编号,初审审批失败");
				return result;
			}
//			if(StringUtils.isBlank(firstAudit.getNextHandleUid())){
//				result.setMsg("下个处理人不能为空");
//				return result;
//			}
			
			boolean isSubmit = isSubmit(firstAudit.getOrderNo(),"auditFirst");
			if(isSubmit){
				result.setMsg("该订单已经被提交");
				return result;
			}
			boolean isWithdraw = isWithdraw(firstAudit.getOrderNo(),"auditFirst");
			if(isWithdraw){
				result.setMsg("该订单已经被撤回");
				return result;
			}
//			if(null != firstAudit.getLoanBankId() && null == firstAudit.getLoanBankName()){
//				BankDto bankDto =CommonDataUtil.getBankNameById(firstAudit.getLoanBankId());
//				firstAudit.setLoanBankName(bankDto==null ? "":bankDto.getName());
//			}
//			if(null !=firstAudit.getPaymentBankId() && null ==firstAudit.getPaymentBankName()){
//				BankDto bankDto =CommonDataUtil.getBankNameById(firstAudit.getPaymentBankId());
//				firstAudit.setPaymentBankName(bankDto==null ? "":bankDto.getName());
//			}
//			if(null != firstAudit.getLoanBankSubId() && null == firstAudit.getLoanBankSubName()){
//				SubBankDto subBankDto =CommonDataUtil.getSubBankNameById(firstAudit.getLoanBankSubId());
//				firstAudit.setLoanBankSubName(subBankDto == null ?"":subBankDto.getName());
//			}
//			if(null != firstAudit.getPaymentBankSubId() && null ==firstAudit.getPaymentBankSubName()){
//				SubBankDto subBankDto =CommonDataUtil.getSubBankNameById(firstAudit.getPaymentBankSubId());
//				firstAudit.setPaymentBankSubName(subBankDto == null ?"":subBankDto.getName());
//			}
			if(null !=firstAudit.getForeclosureAuditList() &&firstAudit.getForeclosureAuditList().size()>0){
				for(FirstForeclosureAuditDto auditDto:firstAudit.getForeclosureAuditList()){
					BankDto bankDto =CommonDataUtil.getBankNameById(auditDto.getLoanBankId());
					auditDto.setLoanBankName(bankDto==null ? "":bankDto.getName());
					SubBankDto subBankDto =CommonDataUtil.getSubBankNameById(auditDto.getLoanBankSubId());
					auditDto.setLoanBankSubName(subBankDto == null ?"":subBankDto.getName());
				}
			}
			if(null!=firstAudit.getFirstPaymentAuditList() && firstAudit.getFirstPaymentAuditList().size()>0){
				for(FirstPaymentAuditDto auditDto:firstAudit.getFirstPaymentAuditList()){
					BankDto bankDto =CommonDataUtil.getBankNameById(auditDto.getPaymentBankId());
					auditDto.setPaymentBankName(bankDto==null ? "":bankDto.getName());
					SubBankDto subBankDto =CommonDataUtil.getSubBankNameById(auditDto.getPaymentBankSubId());
					auditDto.setPaymentBankSubName(subBankDto == null ?"":subBankDto.getName());
				}
			}
			OrderBaseBorrowDto dto=new OrderBaseBorrowDto();
			dto.setOrderNo(firstAudit.getOrderNo());
			RespDataObject<OrderBaseBorrowDto> baseDto=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/order/borrowother/v/queryBorrow", dto,OrderBaseBorrowDto.class);
			OrderBaseBorrowDto baseBorrowDto=  baseDto.getData();
			if(null!=firstAudit.getOther() && StringUtil.isEmpty(firstAudit.getProductName())){
				if(baseBorrowDto!=null){
					firstAudit.setProductName(baseBorrowDto.getProductName());
				}
			}
			
			UserDto user = getUserDto(request);
			firstAudit.setHandleUid(user.getUid());
			firstAudit.setAuditTime(new Date());
			firstAudit.setAuditStatus(1);
			firstAudit.setCreateUid(user.getUid());
			firstAuditService.insert(firstAudit);
			if(null != firstAudit.getForeclosureAuditList()&&firstAudit.getForeclosureAuditList().size()>0){
				firstAuditService.delFirstForeclosure(firstAudit.getOrderNo());
				for(FirstForeclosureAuditDto  auditDto:firstAudit.getForeclosureAuditList()){
					auditDto.setOrderNo(firstAudit.getOrderNo());
					auditDto.setCreateUid(user.getUid());
					auditDto.setUpdateUid(user.getUid());
					firstAuditService.addFirstForeclosure(auditDto);
				}
			}
			if(null!=firstAudit.getFirstPaymentAuditList()&&firstAudit.getFirstPaymentAuditList().size()>0){
				firstAuditService.delFirstPayment(firstAudit.getOrderNo());
				for(FirstPaymentAuditDto auditDto:firstAudit.getFirstPaymentAuditList()){
					auditDto.setOrderNo(firstAudit.getOrderNo());
					auditDto.setCreateUid(user.getUid());
					auditDto.setUpdateUid(user.getUid());
					firstAuditService.addFirstPayment(auditDto);
				}
			}
			UserDto nextUser = CommonDataUtil.getUserDtoByUidAndMobile(firstAudit.getNextHandleUid());
			OrderFlowDto next = new OrderFlowDto();
			next.setOrderNo(firstAudit.getOrderNo());
			next.setCurrentProcessId("auditFirst");
			next.setCurrentProcessName("风控初审");
			next.setNextProcessId("allocationFund");
			next.setNextProcessName("推送金融机构");
			next.setHandleUid(user.getUid());
			next.setHandleName(user.getName());
			next.setUpdateUid(user.getUid());
			
			OrderListDto orderListDto = new OrderListDto();
			orderListDto.setOrderNo(firstAudit.getOrderNo());
			orderListDto.setCurrentHandlerUid(nextUser.getUid());
			orderListDto.setCurrentHandler(nextUser.getName());
			result = goNextNode(next,orderListDto);
//			OrderFlowDto flowDto = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/order/flow/v/selectEndOrderFlow", next,OrderFlowDto.class);
//			 if(flowDto!=null&&flowDto.getIsNewWalkProcess()!=2){ //不重新走流程
//				//发送短信
//				String ipWhite = ConfigUtil.getStringValue(Constants.BASE_AMS_IPWHITE,ConfigUtil.CONFIG_BASE); //ip
//				String ProductName="债务置换";
//				if(baseBorrowDto!=null && !"01".equals(baseBorrowDto.getProductCode()) && !"02".equals(baseBorrowDto.getProductCode())){
//					ProductName=baseBorrowDto.getProductName();
//				}
//				//发送给金融机构操作人
//				AmsUtil.smsSend(nextUser.getMobile(), ipWhite, Constants.SMS_TEMPLATE_MANAGER, ProductName,baseBorrowDto.getBorrowerName(),baseBorrowDto.getLoanAmount(),"推送金融机构");
//			}
		} catch (Exception e){
			log.error("初审审批通过异常,异常信息为:",e);
		}
		return result;
	}
	
	/**
	 * 保存
	 * @param request
	 * @param firstAudit
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/savaAuditFirst")
	public RespStatus savaAuditFirst(HttpServletRequest request,@RequestBody FirstAuditDto firstAudit){
		RespStatus result = new RespStatus();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			if(StringUtils.isBlank(firstAudit.getOrderNo())){
				result.setMsg("缺少订单编号,初审审批失败");
				return result;
			}
//			if(null != firstAudit.getLoanBankId()){
//				BankDto bankDto =CommonDataUtil.getBankNameById(firstAudit.getLoanBankId());
//				firstAudit.setLoanBankName(bankDto==null ? "":bankDto.getName());
//			}
//			if(null !=firstAudit.getPaymentBankId()){
//				BankDto bankDto =CommonDataUtil.getBankNameById(firstAudit.getPaymentBankId());
//				firstAudit.setPaymentBankName(bankDto==null ? "":bankDto.getName());
//			}
//			if(null != firstAudit.getLoanBankSubId()){
//				SubBankDto subBankDto =CommonDataUtil.getSubBankNameById(firstAudit.getLoanBankSubId());
//				firstAudit.setLoanBankSubName(subBankDto == null ?"":subBankDto.getName());
//			}
//			if(null != firstAudit.getPaymentBankSubId() ){
//				SubBankDto subBankDto =CommonDataUtil.getSubBankNameById(firstAudit.getPaymentBankSubId());
//				firstAudit.setPaymentBankSubName(subBankDto == null ?"":subBankDto.getName());
//			}
			if(null!=firstAudit.getForeclosureAuditList()&&firstAudit.getForeclosureAuditList().size()>0){
				for(FirstForeclosureAuditDto auditDto:firstAudit.getForeclosureAuditList()){
					BankDto bankDto =CommonDataUtil.getBankNameById(auditDto.getLoanBankId());
					auditDto.setLoanBankName(bankDto==null ? "":bankDto.getName());
					SubBankDto subBankDto =CommonDataUtil.getSubBankNameById(auditDto.getLoanBankSubId());
					auditDto.setLoanBankSubName(subBankDto == null ?"":subBankDto.getName());
				}
			}
			if(null!=firstAudit.getFirstPaymentAuditList()&&firstAudit.getFirstPaymentAuditList().size()>0){
				for(FirstPaymentAuditDto auditDto:firstAudit.getFirstPaymentAuditList()){
					BankDto bankDto =CommonDataUtil.getBankNameById(auditDto.getPaymentBankId());
					auditDto.setPaymentBankName(bankDto==null ? "":bankDto.getName());
					SubBankDto subBankDto =CommonDataUtil.getSubBankNameById(auditDto.getPaymentBankSubId());
					auditDto.setPaymentBankSubName(subBankDto == null ?"":subBankDto.getName());
				}
			}
			if(firstAudit.getProductName() == null){
				OrderBaseBorrowDto dto=new OrderBaseBorrowDto();
				dto.setOrderNo(firstAudit.getOrderNo());
				RespDataObject<OrderBaseBorrowDto> baseDto=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/order/borrowother/v/queryBorrow", dto,OrderBaseBorrowDto.class);
				OrderBaseBorrowDto baseBorrowDto=  baseDto.getData();
				if(baseBorrowDto!=null){
					firstAudit.setProductName(baseBorrowDto.getProductName());
				}
			}
			UserDto user = getUserDto(request);
			firstAudit.setHandleUid(user.getUid());
			firstAudit.setAuditTime(new Date());
			firstAudit.setAuditStatus(1);
			firstAudit.setCreateUid(user.getUid());
			firstAuditService.insert(firstAudit);
			if(null!=firstAudit.getForeclosureAuditList()&&firstAudit.getForeclosureAuditList().size()>0){
				firstAuditService.delFirstForeclosure(firstAudit.getOrderNo());
				for(FirstForeclosureAuditDto  auditDto:firstAudit.getForeclosureAuditList()){
					auditDto.setOrderNo(firstAudit.getOrderNo());
					auditDto.setCreateUid(user.getUid());
					auditDto.setUpdateUid(user.getUid());
					firstAuditService.addFirstForeclosure(auditDto);
				}
			}
			if(null!=firstAudit.getFirstPaymentAuditList()&&firstAudit.getFirstPaymentAuditList().size()>0){
				firstAuditService.delFirstPayment(firstAudit.getOrderNo());
				for(FirstPaymentAuditDto auditDto:firstAudit.getFirstPaymentAuditList()){
					auditDto.setOrderNo(firstAudit.getOrderNo());
					auditDto.setCreateUid(user.getUid());
					auditDto.setUpdateUid(user.getUid());
					firstAuditService.addFirstPayment(auditDto);
				}
			}
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e){
			log.error("初审保存异常,异常信息为:",e);
		}
		return result;
	}
	/**
	 * 上报终审审批
	 * @param request
	 * @param firstAudit
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/reportFinal")
	public RespStatus reportFinal(HttpServletRequest request,@RequestBody FirstAuditDto firstAudit){
		RespStatus result = new RespStatus();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			
			if(StringUtils.isBlank(firstAudit.getOrderNo())){
				result.setMsg("缺少订单编号,初审审批失败");
				return result;
//			}else if(StringUtils.isBlank(firstAudit.getNextHandleUid())){
//				result.setMsg("下个处理人不能为空");
//				return result;
			}
			
			boolean isSubmit = isSubmit(firstAudit.getOrderNo(),"auditFirst");
			if(isSubmit){
				result.setMsg("该订单已经被提交");
				return result;
			}
			boolean isWithdraw = isWithdraw(firstAudit.getOrderNo(),"auditFirst");
			if(isWithdraw){
				result.setMsg("该订单已经被撤回");
				return result;
			}
//			if(null != firstAudit.getLoanBankId()){
//				BankDto bankDto =CommonDataUtil.getBankNameById(firstAudit.getLoanBankId());
//				firstAudit.setLoanBankName(bankDto==null ? "":bankDto.getName());
//			}
//			if(null !=firstAudit.getPaymentBankId()){
//				BankDto bankDto =CommonDataUtil.getBankNameById(firstAudit.getPaymentBankId());
//				firstAudit.setPaymentBankName(bankDto==null ? "":bankDto.getName());
//			}
//			if(null != firstAudit.getLoanBankSubId()){
//				SubBankDto subBankDto =CommonDataUtil.getSubBankNameById(firstAudit.getLoanBankSubId());
//				firstAudit.setLoanBankSubName(subBankDto == null ?"":subBankDto.getName());
//			}
//			if(null != firstAudit.getPaymentBankSubId()){
//				SubBankDto subBankDto =CommonDataUtil.getSubBankNameById(firstAudit.getPaymentBankSubId());
//				firstAudit.setPaymentBankSubName(subBankDto == null ?"":subBankDto.getName());
//			}
			if(null !=firstAudit.getForeclosureAuditList() &&firstAudit.getForeclosureAuditList().size()>0){
				for(FirstForeclosureAuditDto auditDto:firstAudit.getForeclosureAuditList()){
					BankDto bankDto =CommonDataUtil.getBankNameById(auditDto.getLoanBankId());
					auditDto.setLoanBankName(bankDto==null ? "":bankDto.getName());
					SubBankDto subBankDto =CommonDataUtil.getSubBankNameById(auditDto.getLoanBankSubId());
					auditDto.setLoanBankSubName(subBankDto == null ?"":subBankDto.getName());
				}
			}
			if(null!=firstAudit.getFirstPaymentAuditList() && firstAudit.getFirstPaymentAuditList().size()>0){
				for(FirstPaymentAuditDto auditDto:firstAudit.getFirstPaymentAuditList()){
					BankDto bankDto =CommonDataUtil.getBankNameById(auditDto.getPaymentBankId());
					auditDto.setPaymentBankName(bankDto==null ? "":bankDto.getName());
					SubBankDto subBankDto =CommonDataUtil.getSubBankNameById(auditDto.getPaymentBankSubId());
					auditDto.setPaymentBankSubName(subBankDto == null ?"":subBankDto.getName());
				}
			}
			if(null!=firstAudit.getOther() && StringUtil.isEmpty(firstAudit.getProductName())){
				OrderBaseBorrowDto dto=new OrderBaseBorrowDto();
				dto.setOrderNo(firstAudit.getOrderNo());
				RespDataObject<OrderBaseBorrowDto> baseDto=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/order/borrowother/v/queryBorrow", dto,OrderBaseBorrowDto.class);
				OrderBaseBorrowDto baseBorrowDto=  baseDto.getData();
				if(baseBorrowDto!=null){
					firstAudit.setProductName(baseBorrowDto.getProductName());
				}
			}
			UserDto user = getUserDto(request);
			firstAudit.setCreateUid(user.getUid());
			firstAudit.setAuditTime(new Date());
			firstAudit.setHandleUid(user.getUid());
			firstAudit.setAuditStatus(3);
			firstAuditService.insert(firstAudit);
			if(null!=firstAudit.getForeclosureAuditList()&&firstAudit.getForeclosureAuditList().size()>0){
				firstAuditService.delFirstForeclosure(firstAudit.getOrderNo());
				for(FirstForeclosureAuditDto  auditDto:firstAudit.getForeclosureAuditList()){
					auditDto.setOrderNo(firstAudit.getOrderNo());
					auditDto.setCreateUid(user.getUid());
					auditDto.setUpdateUid(user.getUid());
					firstAuditService.addFirstForeclosure(auditDto);
				}
			}
			if(null!=firstAudit.getFirstPaymentAuditList()&&firstAudit.getFirstPaymentAuditList().size()>0){
				firstAuditService.delFirstPayment(firstAudit.getOrderNo());
				for(FirstPaymentAuditDto auditDto:firstAudit.getFirstPaymentAuditList()){
					auditDto.setOrderNo(firstAudit.getOrderNo());
					auditDto.setCreateUid(user.getUid());
					auditDto.setUpdateUid(user.getUid());
					firstAuditService.addFirstPayment(auditDto);
				}
			}
			log.info("风控终审通过：orderNo:"+firstAudit.getOrderNo()+"处理人Uid"+firstAudit.getNextHandleUid());
			UserDto nextUser = CommonDataUtil.getUserDtoByUidAndMobile(firstAudit.getNextHandleUid());
			log.info("风控终审通过:处理人Uid,名称:"+nextUser.getUid()+","+nextUser.getName());
			OrderFlowDto next = new OrderFlowDto();
			next.setOrderNo(firstAudit.getOrderNo());
			next.setCurrentProcessId("auditFirst");
			next.setCurrentProcessName("风控初审");
			next.setNextProcessId("auditFinal");
			next.setNextProcessName("风控终审");
			next.setHandleUid(user.getUid());
			next.setHandleName(user.getName());
			next.setUpdateUid(user.getUid());
			
			OrderListDto orderListDto = new OrderListDto();
			orderListDto.setOrderNo(firstAudit.getOrderNo());
			orderListDto.setCurrentHandlerUid(nextUser.getUid());
			orderListDto.setCurrentHandler(nextUser.getName());
			result = goNextNode(next,orderListDto);
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e){
			log.error("上报终审审批异常,异常信息为:",e);
		}
		return result;
	}
	
	/**
	 * 报表统计用
	 * @param request
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/listByTime")
	public RespPageData<StatisticsDto> listByTime(HttpServletRequest request,@RequestBody Map<String,Object> map){
		RespPageData<StatisticsDto> result = new RespPageData<StatisticsDto>();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			result.setRows(firstAuditService.listByTime(map));
			result.setTotal(firstAuditService.listByTimeCount(map));
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	public String getOrderNo(String orderNo){
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
	 * 初审审批详情-APP用
	 * @param request
	 * @param firstAudit
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/appDetail")
	public RespDataObject<FirstAuditDto> appDetail(HttpServletRequest request,@RequestBody FirstAuditDto firstAudit){
		RespDataObject<FirstAuditDto> result = new RespDataObject<FirstAuditDto>();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			if(StringUtils.isBlank(firstAudit.getOrderNo())){
				result.setMsg("查询初审详情参数缺失,查询失败!");
				return result;
			}
			FirstAuditDto obj = firstAuditService.detail(firstAudit.getOrderNo());
			UserDto user = null;
			if(StringUtils.isNotBlank(obj.getHandleUid())){
				user = CommonDataUtil.getUserDtoByUidAndMobile(obj.getHandleUid());
			}
			if(null!=user&&null!=obj){
				obj.setHandleName(user.getName());
			}
			if(null!=obj){
				//new 出/回款添加多行  2018-3-22
				List<FirstForeclosureAuditDto> foreclosureAuditList=firstAuditService.findforeclosureList(firstAudit.getOrderNo());
				obj.setForeclosureAuditList(foreclosureAuditList);
				List<FirstPaymentAuditDto> PaymentAuditList = firstAuditService.findPaymentList(firstAudit.getOrderNo());
				obj.setFirstPaymentAuditList(PaymentAuditList);
			}
			if(null!=obj.getOther() && StringUtil.isEmpty(obj.getProductName())){
				OrderBaseBorrowDto dto=new OrderBaseBorrowDto();
				dto.setOrderNo(firstAudit.getOrderNo());
				RespDataObject<OrderBaseBorrowDto> baseDto=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/order/borrowother/v/queryBorrow", dto,OrderBaseBorrowDto.class);
				OrderBaseBorrowDto baseBorrowDto=  baseDto.getData();
				if(baseBorrowDto!=null){
					obj.setProductName(baseBorrowDto.getProductName());
				}
			}
//			if(null != firstAudit.getLoanBankId() && null == firstAudit.getLoanBankName()){
//				BankDto bankDto =CommonDataUtil.getBankNameById(firstAudit.getLoanBankId());
//				firstAudit.setLoanBankName(bankDto==null ? "":bankDto.getName());
//			}
//			if(null !=firstAudit.getPaymentBankId() && null ==firstAudit.getPaymentBankName()){
//				BankDto bankDto =CommonDataUtil.getBankNameById(firstAudit.getPaymentBankId());
//				firstAudit.setPaymentBankName(bankDto==null ? "":bankDto.getName());
//			}
//			if(null != firstAudit.getLoanBankSubId() && null == firstAudit.getLoanBankSubName()){
//				SubBankDto subBankDto =CommonDataUtil.getSubBankNameById(firstAudit.getLoanBankSubId());
//				firstAudit.setLoanBankSubName(subBankDto == null ?"":subBankDto.getName());
//			}
//			if(null != firstAudit.getPaymentBankSubId() && null ==firstAudit.getPaymentBankSubName()){
//				SubBankDto subBankDto =CommonDataUtil.getSubBankNameById(firstAudit.getPaymentBankSubId());
//				firstAudit.setPaymentBankSubName(subBankDto == null ?"":subBankDto.getName());
//			}
			if(null !=firstAudit.getForeclosureAuditList() &&firstAudit.getForeclosureAuditList().size()>0){
				for(FirstForeclosureAuditDto auditDto:firstAudit.getForeclosureAuditList()){
					BankDto bankDto =CommonDataUtil.getBankNameById(auditDto.getLoanBankId());
					auditDto.setLoanBankName(bankDto==null ? "":bankDto.getName());
					SubBankDto subBankDto =CommonDataUtil.getSubBankNameById(auditDto.getLoanBankSubId());
					auditDto.setLoanBankSubName(subBankDto == null ?"":subBankDto.getName());
				}
			}
			if(null!=firstAudit.getFirstPaymentAuditList() && firstAudit.getFirstPaymentAuditList().size()>0){
				for(FirstPaymentAuditDto auditDto:firstAudit.getFirstPaymentAuditList()){
					BankDto bankDto =CommonDataUtil.getBankNameById(auditDto.getPaymentBankId());
					auditDto.setPaymentBankName(bankDto==null ? "":bankDto.getName());
					SubBankDto subBankDto =CommonDataUtil.getSubBankNameById(auditDto.getPaymentBankSubId());
					auditDto.setPaymentBankSubName(subBankDto == null ?"":subBankDto.getName());
				}
			}
			//新数据兼容
			OrderBaseBorrowDto dto=new OrderBaseBorrowDto();
			dto.setOrderNo(firstAudit.getOrderNo());
			RespDataObject<OrderBaseBorrowDto> baseDto=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/order/borrowother/v/queryBorrow", dto,OrderBaseBorrowDto.class);
			OrderBaseBorrowDto baseBorrowDto=  baseDto.getData();
			if(obj!=null && StringUtil.isNotBlank(obj.getProductName()) && obj.getLoanAmount()>0 && obj.getBusiness()>0 && !"04".equals(baseBorrowDto.getProductCode()) && !"03".equals(baseBorrowDto.getProductCode())){
				String remark=obj.getRemark(); //概况
				BigDecimal assessment=BigDecimal.valueOf(obj.getAssessment());
				String str=new String();
				str+="1:业务概况"+"\n";
				str+="  业务类型："+obj.getProductName()+" \n";
				str+="  借款金额："+obj.getLoanAmount()+" 万元  \n";
				str+="  负债比例："+obj.getDebtRatio()+" % \n";
				str+="  借款成数："+obj.getLoanPoportion()+" % \n";
				str+="  债务置换贷款成数："+obj.getProductLoanPoportion()+" % \n" ;
				str+="  房产产权人："+obj.getPropertyOwner()+"\n";
				str+="  物业名称："+obj.getPropertyName()+" \n" ;
				str+="  建筑面积："+obj.getArea()+"㎡  \n" ;
				str+="  评估总值："+assessment.toPlainString()+"元 \n";
				str+="  抵押情况："+obj.getMortgageSituation()+" \n" ;
				str+="  原贷款地点："+obj.getAddressOld()+" \n" ;
				str+="  原贷款金额："+obj.getAmountOld()+" 万元 \n";
				str+="  新贷款地点："+obj.getAddressNew()+"  \n" ;
				str+="  新贷款金额："+obj.getAmountNew()+" 万元 \n";
				str+="  近6个月查询次数："+obj.getMonthNum()+" 次   \n" ;
				str+="  近两年逾期次数："+obj.getYearNum()+" 次 \n";
				str+="  概况："+(remark==null?"":remark)+"\n";
				str+="2：初审意见 "+"\n";
				String business="";
				if(obj.getBusiness()==1){
					business="标准";
				}else if(obj.getBusiness()==2){
					business="非标准";
				}
				str+="  1.业务标准："+business+"\n";
				str+="  2.出款金额："+obj.getSunMoney()+"万元 \n";
				str+="  3.出款银行信息：\n";
				if(null!=obj.getForeclosureAuditList() && obj.getForeclosureAuditList().size()>0){
					for(FirstForeclosureAuditDto auditDto:obj.getForeclosureAuditList()){
						str+="    账户类型："+auditDto.getLoanAccountType()+"  \n" ;
						str+="    开户名："+auditDto.getLoanName()+"  \n" ;
						str+="    账号："+auditDto.getLoanAccount()+"  \n" ;
						str+="    银行-支行："+auditDto.getLoanBankName()+"-"+auditDto.getLoanBankSubName()+"\n";
					}
				}else{
					str+="    开户名："+obj.getLoanName()+"  \n" ;
					str+="    账号："+obj.getLoanAccount()+"  \n" ;
					str+="    银行-支行："+obj.getLoanBankName()+"-"+obj.getLoanBankSubName()+"\n";
				}
				str+="  4.回款银行信息：\n";
				if(null!=obj.getFirstPaymentAuditList() && obj.getFirstPaymentAuditList().size()>0){
					for(FirstPaymentAuditDto auditDto:obj.getFirstPaymentAuditList()){
						str+="    账户类型："+auditDto.getPaymentAccountType()+"  \n" ;
						str+="    开户名："+auditDto.getPaymentName()+"  \n" ;
						str+="    账号："+auditDto.getPaymentAccount()+"  \n" ;
						str+="    银行-支行："+auditDto.getPaymentBankName()+"-"+auditDto.getPaymentBankSubName()+"\n";
					}
				}else{
					str+="    开户名："+obj.getPaymentName()+"  \n" ;
					str+="    账号："+obj.getPaymentAccount()+"  \n" ;
					str+="    银行-支行："+obj.getPaymentBankName()+"-"+obj.getPaymentBankSubName()+"\n";
				}
				str+="  5.建议费率："+obj.getRate()+"%/天  \n" ;
				str+="    建议逾期费率："+obj.getOverdueRate()+" %/天 \n";
				str+="  6.严格控制相关账号账户要件，并更改密码 \n";
				str+="  7.严控流程节点并及时反馈相关节点信息操作 \n";
				str+="  8.其       他："+obj.getOther()+"\n";
				obj.setRemark(str);
			}
			result.setData(obj);
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e){
			log.error("查询初审审批详情信息异常,异常信息为:",e);
		}
		return result;
	}

}
