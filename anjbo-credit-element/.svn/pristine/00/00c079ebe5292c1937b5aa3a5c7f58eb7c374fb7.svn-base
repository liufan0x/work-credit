package com.anjbo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.bean.element.DocumentsDto;
import com.anjbo.bean.element.ForeclosureTypeDto;
import com.anjbo.bean.element.PaymentTypeDto;
import com.anjbo.bean.order.OrderBaseBorrowDto;
import com.anjbo.bean.order.OrderBaseBorrowRelationDto;
import com.anjbo.bean.order.OrderFlowDto;
import com.anjbo.bean.order.OrderListDto;
import com.anjbo.bean.risk.AllocationFundDto;
import com.anjbo.bean.risk.FirstAuditDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.Constants;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.DocumentsService;
import com.anjbo.service.ElementService;
import com.anjbo.utils.HttpUtil;

/**
 * 要件管理
 * @author haunglj
 *
 */
@Controller
@RequestMapping("/credit/element/basics/v")
public class DocumentsController extends BaseController{

	private static final Log log = LogFactory.getLog(DocumentsController.class);
	@Resource
	private DocumentsService documentsService;
	@Resource
	private ElementService elementService; 
	
	/**
	 * 加载要件管理信息
	 * @param request
	 * @param obj
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/detail")
	public RespDataObject<DocumentsDto> detail(HttpServletRequest request,@RequestBody DocumentsDto obj){
		RespDataObject<DocumentsDto> result = new RespDataObject<DocumentsDto>();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			if(StringUtils.isBlank(obj.getOrderNo())){
				result.setMsg("查询要件信息异常,查询参数不能为空");
				return result;
			}
//			String orderNo = getOrderNo(obj.getOrderNo());
//			obj = documentsService.detail(orderNo);
//			boolean isSubmit = isSubmit(obj.getOrderNo(),"element");
//			if(StringUtil.isNotEmpty(obj.getRelationOrderNo()) && !isSubmit){
//				obj.setOrderNo(obj.getRelationOrderNo());
//			}
			DocumentsDto dto = documentsService.detail(obj.getOrderNo());
			if(dto==null && "0"!=obj.getRelationOrderNo()){
				dto=documentsService.detail(obj.getRelationOrderNo());
			}
			result.setData(dto);
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e){
			log.error("加载要件管理信息异常,方法名detail,异常信息为:",e);
		}
		return result;
	}
	
	/**
	 * 订单要件信息保存
	 * @Title: orderManageelementEdit 
	 * @param @param request
	 * @param @param ForeclosureTypeDto 赎楼方式对象
	 * @param @param PaymentTypeDto     回款方式对象
	 * @param @return
	 * @return ModelAndView
	 * @throws
	 */
	@ResponseBody
	@RequestMapping("/update")
	public RespStatus manageelementEdit(HttpServletRequest request,@RequestBody DocumentsDto obj){
		
		RespStatus result = new RespStatus();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			if(StringUtils.isBlank(obj.getOrderNo())){
				result.setMsg("加载订单要件数据异常,缺少订单编号");
				return result;
			}
			UserDto user = getUserDto(request);
			obj.setUpdateUid(user.getUid());
			obj.setCreateUid(user.getUid());
			documentsService.update(obj);
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e){
			e.printStackTrace();
			log.error("要件信息保存保存异常,方法名manageelementEdit,异常信息为:", e);
		}
		return result;
	}
	
	/**
	 * 更新要件信息
	 * @param request
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/updateElementInfo")
	public RespStatus updateElementInfo(HttpServletRequest request,@RequestBody Map<String,Object> map){
		
		RespStatus result = new RespStatus();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			if(StringUtils.isBlank(MapUtils.getString(map, "orderNo"))){
				result.setMsg("加载订单要件数据异常,缺少订单编号");
				return result;
			}
			UserDto user = getUserDto(request);
			map.put("updateUid", user.getUid());
			elementService.updateByMap(map);
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e){
			e.printStackTrace();
			log.error("要件信息保存保存异常,方法名updateElementInfo,异常信息为:", e);
		}
		return result;
	}

	
	/**
	 * 要件特批
	 * @param request
	 * @param obj
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/supplement")
	public RespStatus supplement(HttpServletRequest request,@RequestBody DocumentsDto obj){
		RespStatus result = new RespStatus();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			if(StringUtils.isBlank(obj.getOrderNo())){
				result.setMsg("要件校验失败,缺少订单编号");
				return result;
			} 
			UserDto user = getUserDto(request);
			obj.setCreateUid(user.getUid());
			obj.setUpdateUid(user.getUid());
			obj.setHandleUid(user.getUid());
			if(StringUtils.isNotBlank(obj.getGreenStatusImgUrl())){
				obj.setStatus(2);
			} else{
				obj.setStatus(3);
			}
			
			boolean isSubmit = isSubmit(obj.getOrderNo(),"element");
			if(isSubmit){
				result.setMsg("该订单已经被提交");
				return result;
			}
			
			boolean isWithdraw = isWithdraw(obj.getOrderNo(),"element");
			if(isWithdraw){
				result.setMsg("该订单已经被撤回");
				return result;
			}
			
			documentsService.update(obj);

//			//查询获取资金方选择的财务
//			HttpUtil http = new HttpUtil();
//			AllocationFundDto fund = new AllocationFundDto();
//			fund.setOrderNo(obj.getOrderNo());
//			List<AllocationFundDto> list = http.getList(Constants.LINK_CREDIT, "/credit/risk/allocationfund/v/detail", fund,AllocationFundDto.class);
//			if(null!=list&&list.size()>0){
//				fund = list.get(0);
//			};
//			UserDto nextUser = CommonDataUtil.getUserDtoByUidAndMobile(fund.getFinanceUid());
			
			OrderListDto orderListDto = new OrderListDto();
			orderListDto.setOrderNo(obj.getOrderNo());
			OrderFlowDto next = new OrderFlowDto();
			next.setOrderNo(obj.getOrderNo());
			next.setCurrentProcessId("element");
			next.setCurrentProcessName("要件校验");
			next.setHandleUid(user.getUid());
			next.setHandleName(user.getName());
			next.setUpdateUid(user.getUid());
			//提放业务，要件校验到收利息
			OrderBaseBorrowDto borrow = new OrderBaseBorrowDto();
			borrow.setOrderNo(obj.getOrderNo());
			borrow = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/order/borrow/v/query", borrow,OrderBaseBorrowDto.class);
			if("05".equals(borrow.getProductCode())) {
				next.setNextProcessId("isLendingHarvest");
				next.setNextProcessName("收利息");
				AllocationFundDto fundDto = new AllocationFundDto();
				fundDto.setOrderNo(obj.getOrderNo());
				List<AllocationFundDto> fundList = httpUtil.getList(Constants.LINK_CREDIT, "/credit/risk/allocationfund/v/detail", fundDto, AllocationFundDto.class);
				String handlerUid = "";
				for (AllocationFundDto allocationFundDto : fundList) {
					if(StringUtils.isNotEmpty(allocationFundDto.getFinanceUid())) {
						handlerUid = allocationFundDto.getFinanceUid();
					}
				}
				UserDto userDto = getUserDtoByUid(handlerUid);
				orderListDto.setCurrentHandlerUid(handlerUid);
				orderListDto.setCurrentHandler(userDto.getName());
			}else {
				//资料审核
				next.setNextProcessId("dataAudit");
				next.setNextProcessName("资料审核");
				FirstAuditDto first=new FirstAuditDto();
				first.setOrderNo(obj.getOrderNo());
				RespDataObject<FirstAuditDto> mobj=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/risk/first/v/detail", first,FirstAuditDto.class);
				first = mobj.getData();
				orderListDto.setCurrentHandlerUid(first.getHandleUid());
				orderListDto.setCurrentHandler(first.getHandleName());
			}
			//获取下一处理人 还款专员  ---V3.0版本
//			DistributionMemberDto memberDto = new DistributionMemberDto();
//			memberDto.setOrderNo(obj.getOrderNo());
//			RespDataObject<DistributionMemberDto> mobj=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/process/distributionMember/v/detail", memberDto,DistributionMemberDto.class);
//			DistributionMemberDto member=  mobj.getData();
//			UserDto nextUser = CommonDataUtil.getUserDtoByUidAndMobile(member.getForeclosureMemberUid());
//			next.setNextProcessId("applyLoan");
//			next.setNextProcessName("申请放款");
//			orderListDto.setCurrentHandlerUid(nextUser.getUid());
//			orderListDto.setCurrentHandler(nextUser.getName());
			result = goNextNode(next,orderListDto);
		} catch (Exception e){
			log.error("要件信息要件异常,异常信息为:", e);
		}
		return result;
	}
	
	/**
	 * app要件校验
	 * @param request
	 * @param obj
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/appElement")
	public RespStatus appElement(HttpServletRequest request,@RequestBody DocumentsDto obj){
		RespStatus result = new RespStatus();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			if(StringUtils.isBlank(obj.getOrderNo())){
				result.setMsg("要件校验失败,缺少订单编号");
				return result;
			} 
			obj = documentsService.detail(obj.getOrderNo());
			if(StringUtils.isBlank(obj.getGreenStatusImgUrl())&&StringUtils.isBlank(obj.getElementUrl())) {
				result.setMsg("请上传要件照片再提交。");
				return result;
			}
			UserDto user = getUserDto(request);
			obj.setCreateUid(user.getUid());
			obj.setUpdateUid(user.getUid());
			obj.setHandleUid(user.getUid());
			if(StringUtils.isNotBlank(obj.getGreenStatusImgUrl())){
				obj.setStatus(2);
			} else{
				obj.setStatus(3);
			}
			//查询订单
			OrderListDto listDto=new OrderListDto();
			listDto.setOrderNo(obj.getOrderNo());
			RespDataObject<OrderListDto> listobj=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/order/base/v/selectDetailByOrderNo", listDto,OrderListDto.class);
			listDto=listobj.getData();
			//要件不完整时需要特批
			if(StringUtils.isBlank(obj.getGreenStatusImgUrl())) {
				ForeclosureTypeDto  foreclosureTypeDto = obj.getForeclosureType();
				PaymentTypeDto paymentTypeDto = obj.getPaymentType();
				boolean flag = false;
				//债务置换贷款
				if(!"03".equals(listDto.getProductCode())) {
					//出款方式
					if(null==foreclosureTypeDto.getAccountType()) {
						flag = true;
					}else if("公司".equals(foreclosureTypeDto.getAccountType())) {
						if("柜台还款".equals(foreclosureTypeDto.getForeclosureType())) {
							if(!(foreclosureTypeDto.getBankNameId()!=null
									&&foreclosureTypeDto.getBankSubNameId()!=null&&StringUtils.isNotBlank(foreclosureTypeDto.getBankCardMaster())
									&&StringUtils.isNotBlank(foreclosureTypeDto.getBankNo())
									&&foreclosureTypeDto.getIsUpdateBankCardPWD()!=0
									&&foreclosureTypeDto.getIsReceiveCompanyStamp()!=0
									&&foreclosureTypeDto.getIsReceiveFinanceStamp()!=0
									&&foreclosureTypeDto.getIsReceivePrivateStamp()!=0
									&&foreclosureTypeDto.getIsReceiveBusinessLicence()!=0)){
								log.info("出款方式，公司，柜台还款");
								flag = true;
							}
						}else {
							if(!(StringUtils.isNotBlank(foreclosureTypeDto.getForeclosureType())&&foreclosureTypeDto.getBankNameId()!=null
									&&foreclosureTypeDto.getBankSubNameId()!=null&&StringUtils.isNotBlank(foreclosureTypeDto.getBankCardMaster())
									&&StringUtils.isNotBlank(foreclosureTypeDto.getBankNo())
									&&foreclosureTypeDto.getIsMobleBank()!=0//手机银行
									&&foreclosureTypeDto.getIsNetBank()!=0//网银
									&&foreclosureTypeDto.getIsUpdateBankCardPWD()!=0
									&&foreclosureTypeDto.getIsReceiveCompanyStamp()!=0
									&&foreclosureTypeDto.getIsReceiveFinanceStamp()!=0
									&&foreclosureTypeDto.getIsReceivePrivateStamp()!=0
									&&foreclosureTypeDto.getIsReceiveBusinessLicence()!=0)){
								log.info("出款方式，公司，非柜台还款");
								flag = true;
							}
						}
					}else {
						if("柜台还款".equals(foreclosureTypeDto.getForeclosureType())) {
							if(!(foreclosureTypeDto.getBankNameId()!=null
									&&foreclosureTypeDto.getBankSubNameId()!=null&&StringUtils.isNotBlank(foreclosureTypeDto.getBankCardMaster())
									&&StringUtils.isNotBlank(foreclosureTypeDto.getBankNo())&&StringUtils.isNotBlank(foreclosureTypeDto.getIdCard())
									&&foreclosureTypeDto.getIsUpdateBankCardPWD()!=0)) {
								log.info("出款方式，非公司，柜台还款");
								flag = true;
							}
						}else {
							if(!(StringUtils.isNotBlank(foreclosureTypeDto.getForeclosureType())&&foreclosureTypeDto.getBankNameId()!=null
									&&foreclosureTypeDto.getBankSubNameId()!=null&&StringUtils.isNotBlank(foreclosureTypeDto.getBankCardMaster())
									&&StringUtils.isNotBlank(foreclosureTypeDto.getBankNo())&&StringUtils.isNotBlank(foreclosureTypeDto.getIdCard())
									&&foreclosureTypeDto.getIsUpdateBankCardPWD()!=0
									&&foreclosureTypeDto.getIsMobleBank()!=0//手机银行
									&&foreclosureTypeDto.getIsNetBank()!=0)) {
								log.info("出款方式，非公司，非柜台还款");
								flag = true;
							}
						}
					}
					//回款方式
					if(null==paymentTypeDto.getPaymentaccountType()) {
						flag = true;
					}else if("公司".equals(paymentTypeDto.getPaymentaccountType())) {
						if("网银操作提取回款".equals(paymentTypeDto.getPaymentMode())) {
							if(("02".equals(listDto.getProductCode())||"05".equals(listDto.getProductCode()))&&!(paymentTypeDto.getPaymentBankNameId()!=null
									&&paymentTypeDto.getPaymentBankSubNameId()!=null&&StringUtils.isNotBlank(paymentTypeDto.getPaymentBankCardName())
									&&StringUtils.isNotBlank(paymentTypeDto.getPaymentBankNumber())
									&&paymentTypeDto.getPaymentBankCardPwdState()!=0
									&&paymentTypeDto.getPaymentmobileBankState()!=0&&StringUtils.isNotBlank(paymentTypeDto.getNetBankLoginName())
									&&paymentTypeDto.getIsNetBankLoginPwd()!=0&&paymentTypeDto.getVerfuyNetBankPwd()!=0
									&&paymentTypeDto.getPaymentOfficialSealState()!=0&&paymentTypeDto.getPaymentFinanceSealState()!=0
									&&paymentTypeDto.getPaymentPrivateSealState()!=0&&paymentTypeDto.getPaymentBusinessLicenseState()!=0
									&&StringUtils.isNotBlank(paymentTypeDto.getBankProducts()))) {
								log.info("回款方式，公司，网银操作提取回款");
								flag = true;
							}
						}else {
							if(("02".equals(listDto.getProductCode())||"05".equals(listDto.getProductCode()))&&!(StringUtils.isNotBlank(paymentTypeDto.getPaymentMode())&&paymentTypeDto.getPaymentBankNameId()!=null
									&&paymentTypeDto.getPaymentBankSubNameId()!=null&&StringUtils.isNotBlank(paymentTypeDto.getPaymentBankCardName())
									&&StringUtils.isNotBlank(paymentTypeDto.getPaymentBankNumber())
									&&paymentTypeDto.getPaymentBankCardPwdState()!=0
									&&paymentTypeDto.getPaymentmobileBankState()!=0
									&&paymentTypeDto.getPaymentOnlineBankState()!=0
									&&paymentTypeDto.getPaymentOfficialSealState()!=0&&paymentTypeDto.getPaymentFinanceSealState()!=0
									&&paymentTypeDto.getPaymentPrivateSealState()!=0&&paymentTypeDto.getPaymentBusinessLicenseState()!=0
									&&StringUtils.isNotBlank(paymentTypeDto.getBankProducts()))) {
								log.info("回款方式，公司，非网银操作提取回款");
								flag = true;
							}
						}
						if("网银操作提取回款".equals(paymentTypeDto.getPaymentMode())) {
							if("01".equals(listDto.getProductCode())&&!(paymentTypeDto.getPaymentBankNameId()!=null
									&&paymentTypeDto.getPaymentBankSubNameId()!=null&&StringUtils.isNotBlank(paymentTypeDto.getPaymentBankCardName())
									&&StringUtils.isNotBlank(paymentTypeDto.getPaymentBankNumber())
									&&paymentTypeDto.getPaymentBankCardPwdState()!=0
									&&paymentTypeDto.getPaymentmobileBankState()!=0
									&&StringUtils.isNotEmpty(paymentTypeDto.getNetBankLoginName())
									&&paymentTypeDto.getIsNetBankLoginPwd()!=0
									&&paymentTypeDto.getPaymentFinanceSealState()!=0
									&&paymentTypeDto.getVerfuyNetBankPwd()!=0
									&&paymentTypeDto.getPaymentOfficialSealState()!=0
									&&paymentTypeDto.getPaymentFinanceSealState()!=0
									&&paymentTypeDto.getPaymentPrivateSealState()!=0
									&&paymentTypeDto.getPaymentBusinessLicenseState()!=0
									&&StringUtils.isNotBlank(paymentTypeDto.getBankProducts()))) {
								log.info("回款方式，公司，网银操作提取回款");
								flag = true;
							}
						}else {
							if("01".equals(listDto.getProductCode())&&!(StringUtils.isNotBlank(paymentTypeDto.getPaymentMode())&&paymentTypeDto.getPaymentBankNameId()!=null
									&&paymentTypeDto.getPaymentBankSubNameId()!=null&&StringUtils.isNotBlank(paymentTypeDto.getPaymentBankCardName())
									&&StringUtils.isNotBlank(paymentTypeDto.getPaymentBankNumber())
									&&paymentTypeDto.getPaymentBankCardPwdState()!=0
									&&paymentTypeDto.getPaymentmobileBankState()!=0&&paymentTypeDto.getPaymentOnlineBankState()!=0
									&&paymentTypeDto.getPaymentOfficialSealState()!=0&&paymentTypeDto.getPaymentFinanceSealState()!=0
									&&paymentTypeDto.getPaymentPrivateSealState()!=0&&paymentTypeDto.getPaymentBusinessLicenseState()!=0
									&&StringUtils.isNotBlank(paymentTypeDto.getBankProducts()))) {
								log.info("回款方式，公司，非网银操作提取回款");
								flag = true;
							}
						}
					}else {
						if("网银操作提取回款".equals(paymentTypeDto.getPaymentMode())) {
							if(("02".equals(listDto.getProductCode())||"05".equals(listDto.getProductCode()))&&!(paymentTypeDto.getPaymentBankNameId()!=null
									&&paymentTypeDto.getPaymentBankSubNameId()!=null&&StringUtils.isNotBlank(paymentTypeDto.getPaymentBankCardName())
									&&StringUtils.isNotBlank(paymentTypeDto.getPaymentBankNumber())&&StringUtils.isNotBlank(paymentTypeDto.getPaymentIdCardNo())
									&&paymentTypeDto.getPaymentBankCardPwdState()!=0
									&&paymentTypeDto.getPaymentmobileBankState()!=0
									&&StringUtils.isNotBlank(paymentTypeDto.getNetBankLoginName())&&paymentTypeDto.getIsNetBankLoginPwd()!=0
									&&paymentTypeDto.getVerfuyNetBankPwd()!=0
									&&StringUtils.isNotBlank(paymentTypeDto.getBankProducts()))) {
								log.info("回款方式，非公司，网银操作提取回款");
								flag = true;
							}
						}else {
							if(("02".equals(listDto.getProductCode())||"05".equals(listDto.getProductCode()))&&!(StringUtils.isNotBlank(paymentTypeDto.getPaymentMode())&&paymentTypeDto.getPaymentBankNameId()!=null
									&&paymentTypeDto.getPaymentBankSubNameId()!=null&&StringUtils.isNotBlank(paymentTypeDto.getPaymentBankCardName())
									&&StringUtils.isNotBlank(paymentTypeDto.getPaymentBankNumber())&&StringUtils.isNotBlank(paymentTypeDto.getPaymentIdCardNo())
									&&paymentTypeDto.getPaymentBankCardPwdState()!=0
									&&paymentTypeDto.getPaymentmobileBankState()!=0
									&&paymentTypeDto.getPaymentOnlineBankState()!=0
									&&StringUtils.isNotBlank(paymentTypeDto.getBankProducts()))) {
								log.info("回款方式，非公司，非网银操作提取回款");
								flag = true;
							}
						}
						if("网银操作提取回款".equals(paymentTypeDto.getPaymentMode())) {
							if("01".equals(listDto.getProductCode())&&!(paymentTypeDto.getPaymentBankNameId()!=null
									&&paymentTypeDto.getPaymentBankSubNameId()!=null&&StringUtils.isNotBlank(paymentTypeDto.getPaymentBankCardName())
									&&StringUtils.isNotBlank(paymentTypeDto.getPaymentBankNumber())&&StringUtils.isNotBlank(paymentTypeDto.getPaymentIdCardNo())
									&&paymentTypeDto.getPaymentBankCardPwdState()!=0
									&&paymentTypeDto.getPaymentmobileBankState()!=0
									&&StringUtils.isNotBlank(paymentTypeDto.getNetBankLoginName())&&paymentTypeDto.getIsNetBankLoginPwd()!=0
									&&paymentTypeDto.getVerfuyNetBankPwd()!=0
									&&StringUtils.isNotBlank(paymentTypeDto.getBankProducts()))) {
								log.info("回款方式，非公司，非网银操作提取回款");
								flag = true;
							}
						}else {
							if("01".equals(listDto.getProductCode())&&!(StringUtils.isNotBlank(paymentTypeDto.getPaymentMode())&&paymentTypeDto.getPaymentBankNameId()!=null
									&&paymentTypeDto.getPaymentBankSubNameId()!=null&&StringUtils.isNotBlank(paymentTypeDto.getPaymentBankCardName())
									&&StringUtils.isNotBlank(paymentTypeDto.getPaymentBankNumber())&&StringUtils.isNotBlank(paymentTypeDto.getPaymentIdCardNo())
									&&paymentTypeDto.getPaymentBankCardPwdState()!=0
									&&paymentTypeDto.getPaymentmobileBankState()!=0
									&&paymentTypeDto.getPaymentOnlineBankState()!=0
									&&StringUtils.isNotBlank(paymentTypeDto.getBankProducts()))) {
								log.info("回款方式，非公司，网银操作提取回款");
								flag = true;
							}
						}
					}
				}else if("03".equals(listDto.getProductCode())&&StringUtils.isNotBlank(listDto.getRelationOrderNo())) {//关联畅贷
					//银行支行，户名账号
					if(!(foreclosureTypeDto.getBankNameId()!=null&&foreclosureTypeDto.getBankSubNameId()!=null
							&&StringUtils.isNotBlank(foreclosureTypeDto.getBankCardMaster())
							&&StringUtils.isNotBlank(foreclosureTypeDto.getBankNo()))) {
						flag = true;
					}
				}else {//不关联畅贷
					//出款方式
					//银行支行，户名账号
					if(!(foreclosureTypeDto.getBankNameId()!=null&&foreclosureTypeDto.getBankSubNameId()!=null
							&&StringUtils.isNotBlank(foreclosureTypeDto.getBankCardMaster())
							&&StringUtils.isNotBlank(foreclosureTypeDto.getBankNo()))) {
						flag = true;
					}
					//回款方式
					if(null==paymentTypeDto.getPaymentaccountType()) {
						flag = true;
					}else if("公司".equals(paymentTypeDto.getPaymentaccountType())) {
						if("网银操作提取回款".equals(paymentTypeDto.getPaymentMode())) {
							if(!(paymentTypeDto.getPaymentBankNameId()!=null
									&&paymentTypeDto.getPaymentBankSubNameId()!=null&&StringUtils.isNotBlank(paymentTypeDto.getPaymentBankCardName())
									&&StringUtils.isNotBlank(paymentTypeDto.getPaymentBankNumber())
									&&paymentTypeDto.getPaymentBankCardPwdState()!=0
									&&paymentTypeDto.getPaymentmobileBankState()!=0&&StringUtils.isNotBlank(paymentTypeDto.getNetBankLoginName())
									&&paymentTypeDto.getIsNetBankLoginPwd()!=0&&paymentTypeDto.getVerfuyNetBankPwd()!=0
									&&paymentTypeDto.getPaymentOfficialSealState()!=0&&paymentTypeDto.getPaymentFinanceSealState()!=0
									&&paymentTypeDto.getPaymentPrivateSealState()!=0&&paymentTypeDto.getPaymentBusinessLicenseState()!=0
									&&StringUtils.isNotBlank(paymentTypeDto.getBankProducts()))) {
								log.info("回款方式，公司，网银操作提取回款");
								flag = true;
							}
						}else {
							if(!(StringUtils.isNotBlank(paymentTypeDto.getPaymentMode())&&paymentTypeDto.getPaymentBankNameId()!=null
									&&paymentTypeDto.getPaymentBankSubNameId()!=null&&StringUtils.isNotBlank(paymentTypeDto.getPaymentBankCardName())
									&&StringUtils.isNotBlank(paymentTypeDto.getPaymentBankNumber())
									&&paymentTypeDto.getPaymentBankCardPwdState()!=0
									&&paymentTypeDto.getPaymentmobileBankState()!=0
									&&paymentTypeDto.getPaymentOnlineBankState()!=0
									&&paymentTypeDto.getPaymentOfficialSealState()!=0&&paymentTypeDto.getPaymentFinanceSealState()!=0
									&&paymentTypeDto.getPaymentPrivateSealState()!=0&&paymentTypeDto.getPaymentBusinessLicenseState()!=0
									&&StringUtils.isNotBlank(paymentTypeDto.getBankProducts()))) {
								log.info("回款方式，公司，非网银操作提取回款");
								flag = true;
							}
						}
					}else {
						if("网银操作提取回款".equals(paymentTypeDto.getPaymentMode())) {
							if(!(paymentTypeDto.getPaymentBankNameId()!=null
									&&paymentTypeDto.getPaymentBankSubNameId()!=null&&StringUtils.isNotBlank(paymentTypeDto.getPaymentBankCardName())
									&&StringUtils.isNotBlank(paymentTypeDto.getPaymentBankNumber())&&StringUtils.isNotBlank(paymentTypeDto.getPaymentIdCardNo())
									&&paymentTypeDto.getPaymentBankCardPwdState()!=0
									&&paymentTypeDto.getPaymentmobileBankState()!=0
									&&StringUtils.isNotBlank(paymentTypeDto.getNetBankLoginName())&&paymentTypeDto.getIsNetBankLoginPwd()!=0
									&&paymentTypeDto.getVerfuyNetBankPwd()!=0
									&&StringUtils.isNotBlank(paymentTypeDto.getBankProducts()))) {
								log.info("回款方式，非公司，网银操作提取回款");
								flag = true;
							}
						}else {
							if(!(StringUtils.isNotBlank(paymentTypeDto.getPaymentMode())&&paymentTypeDto.getPaymentBankNameId()!=null
									&&paymentTypeDto.getPaymentBankSubNameId()!=null&&StringUtils.isNotBlank(paymentTypeDto.getPaymentBankCardName())
									&&StringUtils.isNotBlank(paymentTypeDto.getPaymentBankNumber())&&StringUtils.isNotBlank(paymentTypeDto.getPaymentIdCardNo())
									&&paymentTypeDto.getPaymentBankCardPwdState()!=0
									&&paymentTypeDto.getPaymentmobileBankState()!=0
									&&paymentTypeDto.getPaymentOnlineBankState()!=0
									&&StringUtils.isNotBlank(paymentTypeDto.getBankProducts()))) {
								log.info("回款方式，非公司，非网银操作提取回款");
								flag = true;
							}
						}
					}
				}
				if(flag) {
					result.setMsg("要件不完整，如已通过钉钉特批请上传截屏证明再提交。");
					return result;
				}
			}
			boolean isSubmit = isSubmit(obj.getOrderNo(),"element");
			if(isSubmit){
				result.setMsg("该订单已经被提交");
				return result;
			}
			
			boolean isWithdraw = isWithdraw(obj.getOrderNo(),"element");
			if(isWithdraw){
				result.setMsg("该订单已经被撤回");
				return result;
			}
			
			//documentsService.update(obj);
			//更新要件校验方式
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("orderNo", obj.getOrderNo());
			map.put("status", obj.getStatus());
			elementService.updateByMap(map);
			OrderListDto orderListDto = new OrderListDto();
			orderListDto.setOrderNo(obj.getOrderNo());
			OrderFlowDto next = new OrderFlowDto();
			next.setOrderNo(obj.getOrderNo());
			next.setCurrentProcessId("element");
			next.setCurrentProcessName("要件校验");
			next.setHandleUid(user.getUid());
			next.setHandleName(user.getName());
			next.setUpdateUid(user.getUid());
			
			//提放业务，要件校验到收利息
			OrderBaseBorrowDto borrow = new OrderBaseBorrowDto();
			borrow.setOrderNo(obj.getOrderNo());
			borrow = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/order/borrow/v/query", borrow,OrderBaseBorrowDto.class);
			if("05".equals(borrow.getProductCode())) {
				next.setNextProcessId("isLendingHarvest");
				next.setNextProcessName("收利息");
				AllocationFundDto fundDto = new AllocationFundDto();
				fundDto.setOrderNo(obj.getOrderNo());
				List<AllocationFundDto> fundList = httpUtil.getList(Constants.LINK_CREDIT, "/credit/risk/allocationfund/v/detail", fundDto, AllocationFundDto.class);
				String handlerUid = "";
				for (AllocationFundDto allocationFundDto : fundList) {
					if(StringUtils.isNotEmpty(allocationFundDto.getFinanceUid())) {
						handlerUid = allocationFundDto.getFinanceUid();
					}
				}
				UserDto userDto = getUserDtoByUid(handlerUid);
				orderListDto.setCurrentHandlerUid(handlerUid);
				orderListDto.setCurrentHandler(userDto.getName());
			}else {
				//资料审核
				next.setNextProcessId("dataAudit");
				next.setNextProcessName("资料审核");
				FirstAuditDto first=new FirstAuditDto();
				first.setOrderNo(obj.getOrderNo());
				RespDataObject<FirstAuditDto> mobj=httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/risk/first/v/detail", first,FirstAuditDto.class);
				first = mobj.getData();
				orderListDto.setCurrentHandlerUid(first.getHandleUid());
				orderListDto.setCurrentHandler(first.getHandleName());
			}
			result = goNextNode(next,orderListDto);
		} catch (Exception e){
			log.error("要件信息要件异常,异常信息为:", e);
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/addImg")
	public RespDataObject<DocumentsDto> addImg(HttpServletRequest request,@RequestBody DocumentsDto obj){
		RespDataObject<DocumentsDto> result = new RespDataObject<DocumentsDto>();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			if(StringUtils.isBlank(obj.getOrderNo())
					&&StringUtils.isBlank(obj.getGreenStatusImgUrl())){
				result.setMsg("上传图片失败,缺少订单编号或图片!");
				return result;
			}
			UserDto user = getUserDto(request);
			obj.setCreateUid(user.getUid());
			obj.setUpdateUid(user.getUid());
			obj = documentsService.addImg(obj);
			result.setData(obj);
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e){
			log.error("要件上传图片异常,异常信息为:",e);
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/delImg")
	public RespDataObject<DocumentsDto> delImg(HttpServletRequest request,@RequestBody DocumentsDto obj){
		RespDataObject<DocumentsDto> result = new  RespDataObject<DocumentsDto>();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			if(StringUtils.isBlank(obj.getOrderNo())){
				result.setMsg("删除图片失败,缺少订单编号!");
				return result;
			}
			obj = documentsService.delImg(obj);
			result.setData(obj);
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e){
			log.error("要件删除图片信息异常,异常信息为:",e);
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/updatePaymentType")
	public RespStatus updatePaymentType(HttpServletRequest request,@RequestBody PaymentTypeDto dto){
		RespStatus result = new RespStatus();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			if(StringUtils.isBlank(dto.getOrderNo())){
				result.setMsg("保存要件回款信息参数异常!");
				return result;
			}
			UserDto user = getUserDto(request);
			DocumentsDto doc = new DocumentsDto();
			doc.setCreateUid(user.getUid());
			doc.setUpdateUid(user.getUid());
			doc.setOrderNo(dto.getOrderNo());
			doc.setPaymentType(dto);
			documentsService.updatePaymentType(doc);
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e){
			log.error("保存要件回款信息异常,异常信息为:",e);
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/updateForeclosureType")
	public RespStatus updateForeclosureType(HttpServletRequest request,@RequestBody ForeclosureTypeDto dto){
		RespStatus result = new RespStatus();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			if(StringUtils.isBlank(dto.getOrderNo())){
				result.setMsg("保存要件赎楼信息参数异常!");
				return result;
			}
			UserDto user = getUserDto(request);
			DocumentsDto doc = new DocumentsDto();
			doc.setCreateUid(user.getUid());
			doc.setUpdateUid(user.getUid());
			doc.setOrderNo(dto.getOrderNo());
			doc.setForeclosureType(dto);
			documentsService.updateForeclosureType(doc);
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e){
			log.error("保存要件赎楼信息异常,异常信息为:",e);
		}
		return result;
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
}
