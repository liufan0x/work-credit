package com.anjbo.controller;

import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.bean.common.DictDto;
import com.anjbo.bean.customer.AgencyDto;
import com.anjbo.bean.order.OrderBaseBorrowDto;
import com.anjbo.bean.order.OrderBaseBorrowRelationDto;
import com.anjbo.bean.order.OrderBaseCustomerBorrowerDto;
import com.anjbo.bean.order.OrderBaseCustomerDto;
import com.anjbo.bean.order.OrderBaseCustomerGuaranteeDto;
import com.anjbo.bean.order.OrderBaseCustomerShareholderDto;
import com.anjbo.bean.order.OrderBaseHouseDto;
import com.anjbo.bean.order.OrderBaseHouseLendingDto;
import com.anjbo.bean.order.OrderBaseHousePropertyDto;
import com.anjbo.bean.order.OrderBaseHousePropertyPeopleDto;
import com.anjbo.bean.order.OrderBaseHousePurchaserDto;
import com.anjbo.bean.order.OrderBaseReceivableForDto;
import com.anjbo.bean.order.OrderFlowDto;
import com.anjbo.bean.order.OrderListDto;
import com.anjbo.bean.product.DistributionMemberDto;
import com.anjbo.bean.product.ProductDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.Constants;
import com.anjbo.common.DateUtil;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.OrderBaseBorrowRelationService;
import com.anjbo.service.OrderBaseBorrowService;
import com.anjbo.service.OrderBaseCustomerBorrowerService;
import com.anjbo.service.OrderBaseCustomerGuaranteeService;
import com.anjbo.service.OrderBaseCustomerService;
import com.anjbo.service.OrderBaseCustomerShareholderService;
import com.anjbo.service.OrderBaseHouseLendingService;
import com.anjbo.service.OrderBaseHousePropertyPeopleService;
import com.anjbo.service.OrderBaseHousePropertyService;
import com.anjbo.service.OrderBaseHousePurchaserService;
import com.anjbo.service.OrderBaseHouseService;
import com.anjbo.service.OrderBaseService;
import com.anjbo.utils.AmsUtil;
import com.anjbo.utils.CommonDataUtil;
import com.anjbo.utils.ConfigUtil;
import com.anjbo.utils.UidUtil;

@RequestMapping("/credit/order/app")
@Controller
public class OrderAppController extends BaseController{
	Logger log = Logger.getLogger(OrderAppController.class);

	@Resource
	private OrderBaseBorrowService orderBaseBorrowService;
	@Resource
	private OrderBaseCustomerService orderBaseCustomerService;
	@Resource
	private OrderBaseHouseService orderBaseHouseService;
	@Resource
	private OrderBaseService orderBaseService;
	@Resource
	private OrderBaseBorrowRelationService orderBaseBorrowRelationService;
	@Resource
	private OrderBaseCustomerBorrowerService orderBaseCustomerBorrowerService;
	@Resource
	private OrderBaseCustomerShareholderService orderBaseCustomerShareholderService;
	@Resource
	private OrderBaseCustomerGuaranteeService orderBaseCustomerGuaranteeService;
	@Resource
	private OrderBaseHousePropertyService orderBaseHousePropertyService;
	@Resource
	private OrderBaseHousePropertyPeopleService orderBaseHousePropertyPeopleService;
	@Resource
	private OrderBaseHousePurchaserService orderBaseHousePurchaserService;
	@Resource 
	private OrderBaseHouseLendingService orderBaseHouseLendingService;
	
	
	/**
	 * 更新借款信息
	 * @param request
	 * @param orderBaseBorrowDto
	 * @return
	 */
	@RequestMapping("/v/updateBorrow")
	@ResponseBody
	public RespStatus update(HttpServletRequest request,
							 @RequestBody OrderBaseBorrowDto orderBaseBorrowDto) {
		RespStatus respStatus = new RespStatus();
		try {
			if(isSubmit(orderBaseBorrowDto.getOrderNo(), "placeOrder")){
				RespHelper.setFailRespStatus(respStatus, "订单已提交审核");
				return respStatus;
			}
			List<OrderBaseReceivableForDto> list = new ArrayList<OrderBaseReceivableForDto>();
			if(orderBaseBorrowDto.getPayMentAmountOne()!=null){
				OrderBaseReceivableForDto obj = new OrderBaseReceivableForDto();
				obj.setPayMentAmount(orderBaseBorrowDto.getPayMentAmountOne());
				obj.setPayMentAmountDate(orderBaseBorrowDto.getPayMentAmountDateOne());
				list.add(obj);
			}
			if(orderBaseBorrowDto.getPayMentAmountTwo()!=null){
				OrderBaseReceivableForDto obj = new OrderBaseReceivableForDto();
				obj.setPayMentAmount(orderBaseBorrowDto.getPayMentAmountTwo());
				obj.setPayMentAmountDate(orderBaseBorrowDto.getPayMentAmountDateTwo());
				list.add(obj);
			}
			if(null!=list&&list.size()>0){
				orderBaseBorrowDto.setOrderReceivableForDto(list);
			}
			if("01".equals(orderBaseBorrowDto.getProductCode())){
				if(orderBaseBorrowDto.getIsOnePay() == 2){
					BigDecimal x = new BigDecimal(orderBaseBorrowDto.getPayMentAmountOne()==null?0:orderBaseBorrowDto.getPayMentAmountOne());
					BigDecimal y = new BigDecimal(orderBaseBorrowDto.getPayMentAmountTwo()==null?0:orderBaseBorrowDto.getPayMentAmountTwo());
					BigDecimal z = new BigDecimal(orderBaseBorrowDto.getLoanAmount()==null?0:orderBaseBorrowDto.getLoanAmount());
					if(z.compareTo(x.add(y)) != 0){
						RespHelper.setFailRespStatus(respStatus, "两次回款金额必须等于借款金额");
						return respStatus;
					}
				}else{
					BigDecimal x = new BigDecimal(orderBaseBorrowDto.getPayMentAmountOne()==null?0:orderBaseBorrowDto.getPayMentAmountOne());
					BigDecimal z = new BigDecimal(orderBaseBorrowDto.getLoanAmount()==null?0:orderBaseBorrowDto.getLoanAmount());
					if(z.compareTo(x) != 0){
						RespHelper.setFailRespStatus(respStatus, "回款金额必须等于借款金额");
						return respStatus;
					}
				}
			}
			
			// 还款专员(机构单)
			// 查询订单机构id
			OrderListDto orderListDto = orderBaseService.selectDetail(orderBaseBorrowDto.getOrderNo());
			if(orderListDto.getAgencyId()>1 && StringUtils.isNotEmpty(orderBaseBorrowDto.getForeclosureMemberUid())){
				DistributionMemberDto distributionMemberDto = new DistributionMemberDto();
				distributionMemberDto.setOrderNo(orderBaseBorrowDto.getOrderNo());
				distributionMemberDto.setForeclosureMemberUid(orderBaseBorrowDto.getForeclosureMemberUid());
				httpUtil.getObject(Constants.LINK_CREDIT, "/credit/process/distributionMember/v/addAgency", distributionMemberDto, RespData.class);
			}
			
			UserDto userDto = getUserDto(request); // 获取用户信息
			orderBaseBorrowDto.setUpdateUid(userDto.getUid());
			orderBaseBorrowDto.setAgencyId(userDto.getAgencyId());
			orderBaseBorrowDto = getOrderBaseBorrowDto(orderBaseBorrowDto);
			orderBaseBorrowDto.setCurrentHandlerUid(userDto.getUid());
			orderBaseBorrowService.appUpdateBorrow(orderBaseBorrowDto);
			
			
			OrderListDto ELementOrderListDto=new OrderListDto();
			
			ELementOrderListDto.setOrderNo(orderBaseBorrowDto.getOrderNo());
			
			ELementOrderListDto.setCustomerName(orderBaseBorrowDto.getBorrowerName());
			
			ELementOrderListDto.setCityName(orderBaseBorrowDto.getCityName());
			
			ELementOrderListDto.setCityCode(orderBaseBorrowDto.getCityCode());
			
			ELementOrderListDto.setProductName(orderBaseBorrowDto.getProductName());
			
			ELementOrderListDto.setProductCode(orderBaseBorrowDto.getProductCode());
			
			ELementOrderListDto.setBorrowingAmount(orderBaseBorrowDto.getLoanAmount());
			
			if(orderBaseBorrowDto.getBorrowingDays()!=null){
				ELementOrderListDto.setBorrowingDay(orderBaseBorrowDto.getBorrowingDays());
			}
			
			ELementOrderListDto.setChannelManagerName(orderBaseBorrowDto.getChannelManagerName());
			
			ELementOrderListDto.setChannelManagerUid(orderBaseBorrowDto.getChannelManagerUid());
			
			ELementOrderListDto.setAcceptMemberUid(orderBaseBorrowDto.getAcceptMemberUid());

			ELementOrderListDto.setAcceptMemberName(orderBaseBorrowDto.getAcceptMemberName()); 
			  
			ELementOrderListDto.setCurrentHandlerUid(orderBaseBorrowDto.getCurrentHandlerUid());
		    
			ELementOrderListDto.setCurrentHandler(orderBaseBorrowDto.getCurrentHandler());
		    
			ELementOrderListDto.setPreviousHandlerUid(orderBaseBorrowDto.getPreviousHandlerUid());
		    
			ELementOrderListDto.setNotarialUid(orderBaseBorrowDto.getNotarialUid());
		    
			ELementOrderListDto.setFacesignUid(orderBaseBorrowDto.getFacesignUid());
		    
			ELementOrderListDto.setCreateUid(orderBaseBorrowDto.getCreateUid());
		    
		    log.info(ELementOrderListDto.getCustomerName()+ELementOrderListDto.getCityName());
		    
			//同步数据到智能要件
			try {
				logger.info("APPupdate执行同步信贷件订单到智能要件");
				RespStatus elementResp = httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/element/list/saveCredit", ELementOrderListDto);
			} catch (Exception e) {
				e.printStackTrace(); 
			}
			
			RespHelper.setSuccessRespStatus(respStatus);
		} catch (Exception e) {
			log.error("更新订单借款信息失败");
			RespHelper.setFailRespStatus(respStatus, "更新订单借款信息失败");
			e.printStackTrace();
		}
		return respStatus;
	}	
	
	/**
	 * 保存畅贷信息
	 * @param request
	 * @param orderBaseBorrowRelationDto
	 * @return
	 */
//	@RequestMapping("/v/savecd")
//	@ResponseBody
//	public RespStatus savecd(HttpServletRequest request,
//			@RequestBody OrderBaseBorrowRelationDto orderBaseBorrowRelationDto) {
//		RespStatus respStatus = new RespStatus();
//		try {
//			if(isSubmit(orderBaseBorrowRelationDto.getOrderNo(), "placeOrder")){
//				RespHelper.setFailRespStatus(respStatus, "订单已提交审核");
//				return respStatus;
//			}
//			UserDto userDto = getUserDto(request); // 获取用户信息
//			String orderNo = orderBaseBorrowRelationDto.getOrderNo();
//			orderNo = getCreditOrderNo(orderNo);
//			orderBaseBorrowRelationDto.setUpdateUid(userDto.getUid());
//			if(orderBaseBorrowRelationDto.getOrderNo()!=null&&!orderBaseBorrowRelationDto.getOrderNo().equals("")){
//				OrderBaseBorrowRelationDto dto = null;
//				if(orderBaseBorrowRelationService.selectOrderBorrowRelationByOrderNo(orderNo)!=null&&orderBaseBorrowRelationService.selectOrderBorrowRelationByOrderNo(orderNo).size()>0){
//					dto = orderBaseBorrowRelationService.selectOrderBorrowRelationByOrderNo(orderNo).get(0);
//				}
//				if(dto!=null){
//					orderBaseBorrowRelationDto.setOrderNo(orderNo);
//					orderBaseBorrowRelationService.updatecd(orderBaseBorrowRelationDto);
//				}else{
//					orderBaseBorrowRelationDto.setCreateUid(userDto.getUid());
//					orderBaseBorrowRelationDto.setRelationOrderNo(orderNo);
//					orderBaseBorrowRelationService.savecd(orderBaseBorrowRelationDto);
//				}
//			}
//			RespHelper.setSuccessRespStatus(respStatus);
//		} catch (Exception e) {
//			log.error("保存畅贷信息失败");
//			RespHelper.setFailRespStatus(respStatus, "保存畅贷信息失败");
//			e.printStackTrace();
//		}
//		return respStatus;
//	}	
	
	/**
	 * 删除畅贷信息
	 * @param request
	 * @param orderBaseBorrowRelationDto
	 * @return
	 */
	@RequestMapping("/v/deletecd")
	@ResponseBody
	public RespStatus deletecd(HttpServletRequest request,
			@RequestBody OrderBaseBorrowRelationDto orderBaseBorrowRelationDto) {
		RespStatus respStatus = new RespStatus();
		try {
			if(isSubmit(orderBaseBorrowRelationDto.getOrderNo(), "placeOrder")){
				RespHelper.setFailRespStatus(respStatus, "订单已提交审核");
				return respStatus;
			}
			UserDto userDto = getUserDto(request); // 获取用户信息
			orderBaseBorrowRelationDto.setCreateUid(userDto.getUid());
			orderBaseBorrowRelationDto.setUpdateUid(userDto.getUid());
			orderBaseBorrowRelationService.deleteRelationByOrderNo(orderBaseBorrowRelationDto.getOrderNo());
			RespHelper.setSuccessRespStatus(respStatus);
		} catch (Exception e) {
			log.error("删除畅贷信息失败");
			RespHelper.setFailRespStatus(respStatus, "删除畅贷信息失败");
			e.printStackTrace();
		}
		return respStatus;
	}
	
	/**
	 * app更新客户信息
	 * @param request
	 * @param orderBaseCustomerDto
	 * @return
	 */
	@RequestMapping("/v/updateCustomer")
	@ResponseBody
	public RespStatus updateCustomer(HttpServletRequest request,
			@RequestBody OrderBaseCustomerDto orderBaseCustomerDto) {
		RespStatus respStatus = new RespStatus();
		try {
			if(isSubmit(orderBaseCustomerDto.getOrderNo(), "placeOrder")){
				RespHelper.setFailRespStatus(respStatus, "订单已提交审核");
				return respStatus;
			}
			UserDto userDto = getUserDto(request); // 获取用户信息
			orderBaseCustomerDto.setUpdateUid(userDto.getUid());
			orderBaseCustomerService.appUpdateOrderCustomer(orderBaseCustomerDto);
			RespHelper.setSuccessRespStatus(respStatus);
		} catch (Exception e) {
			log.error("更新客户信息失败");
			RespHelper.setFailRespStatus(respStatus, "更新客户信息失败");
			e.printStackTrace();
		}
		return respStatus;
	}	
	
	/**
	 * app更新客户企业信息
	 * @param request
	 * @param orderBaseCustomerDto
	 * @return
	 */
	@RequestMapping("/v/updateCustomerCompany")
	@ResponseBody
	public RespStatus updateCustomerCompany(HttpServletRequest request,
			@RequestBody OrderBaseCustomerDto orderBaseCustomerDto) {
		RespStatus respStatus = new RespStatus();
		try {
			if(isSubmit(orderBaseCustomerDto.getOrderNo(), "placeOrder")){
				RespHelper.setFailRespStatus(respStatus, "订单已提交审核");
				return respStatus;
			}
			UserDto userDto = getUserDto(request); // 获取用户信息
			orderBaseCustomerDto.setUpdateUid(userDto.getUid());
			orderBaseCustomerService.appUpdateOrderCustomerCompany(orderBaseCustomerDto);
			RespHelper.setSuccessRespStatus(respStatus);
		} catch (Exception e) {
			log.error("更新客户企业信息失败");
			RespHelper.setFailRespStatus(respStatus, "更新客户企业信息失败");
			e.printStackTrace();
		}
		return respStatus;
	}	
	
	/**
	 * app删除客户企业股东信息
	 * @param request
	 * @param orderBaseBorrowDto
	 * @return
	 */
	@RequestMapping("/v/deleteCustomerShareholder")
	@ResponseBody
	public RespStatus deleteCustomerShareholder(HttpServletRequest request,
			@RequestBody OrderBaseCustomerShareholderDto orderBaseCustomerShareholderDto) {
		RespStatus respStatus = new RespStatus();
		try {
			if(isSubmit(orderBaseCustomerShareholderDto.getOrderNo(), "placeOrder")){
				RespHelper.setFailRespStatus(respStatus, "订单已提交审核");
				return respStatus;
			}
			if(orderBaseCustomerShareholderDto.getId()==null||orderBaseCustomerShareholderDto.getId()==0){
				RespHelper.setFailRespStatus(respStatus, "删除企业股东信息失败，id不能为空");
				return respStatus;
			}
			orderBaseCustomerShareholderService.deleteShareholderById(orderBaseCustomerShareholderDto.getId());
			RespHelper.setSuccessRespStatus(respStatus);
		} catch (Exception e) {
			log.error("删除企业股东信息失败");
			RespHelper.setFailRespStatus(respStatus, "删除企业股东信息失败");
			e.printStackTrace();
		}
		return respStatus;
	}	
	
	
	/**
	 * app保存客戶共同借款人
	 * @param request
	 * @param orderBaseCustomerBorrowerDto
	 * @return
	 */
	@RequestMapping("/v/saveCustomerBorrow")
	@ResponseBody
	public RespStatus saveCustomerBorrow(HttpServletRequest request,
			@RequestBody OrderBaseCustomerBorrowerDto orderBaseCustomerBorrowerDto) {
		RespStatus respStatus = new RespStatus();
		try {
			if(isSubmit(orderBaseCustomerBorrowerDto.getOrderNo(), "placeOrder")){
				RespHelper.setFailRespStatus(respStatus, "订单已提交审核");
				return respStatus;
			}
			UserDto userDto = getUserDto(request); // 获取用户信息
			orderBaseCustomerBorrowerDto.setUpdateUid(userDto.getUid());
			if(orderBaseCustomerBorrowerDto.getId()==null||orderBaseCustomerBorrowerDto.getId()==0){
				orderBaseCustomerBorrowerDto.setCreateUid(userDto.getUid());
				orderBaseCustomerBorrowerService.saveOrderCustomerBorrower(orderBaseCustomerBorrowerDto);
			}else{
				orderBaseCustomerBorrowerService.updateOrderCustomerBorrow(orderBaseCustomerBorrowerDto);
			}
			RespHelper.setSuccessRespStatus(respStatus);
		} catch (Exception e) {
			log.error("保存客戶共同借款人失败");
			RespHelper.setFailRespStatus(respStatus, "保存客戶共同借款人失败");
			e.printStackTrace();
		}
		return respStatus;
	}	
	
	/**
	 * app删除客戶共同借款人
	 * @param request
	 * @param orderBaseBorrowDto
	 * @return
	 */
	@RequestMapping("/v/deleteCustomerBorrow")
	@ResponseBody
	public RespStatus deleteCustomerBorrow(HttpServletRequest request,
			@RequestBody OrderBaseCustomerBorrowerDto orderBaseCustomerBorrowerDto) {
		RespStatus respStatus = new RespStatus();
		try {
			if(isSubmit(orderBaseCustomerBorrowerDto.getOrderNo(), "placeOrder")){
				RespHelper.setFailRespStatus(respStatus, "订单已提交审核");
				return respStatus;
			}
			if(orderBaseCustomerBorrowerDto.getId()==null||orderBaseCustomerBorrowerDto.getId()==0){
				RespHelper.setFailRespStatus(respStatus, "删除共同借款人失败，id不能为空");
				return respStatus;
			}
			orderBaseCustomerBorrowerService.deleteBorrowerById(orderBaseCustomerBorrowerDto.getId());
			RespHelper.setSuccessRespStatus(respStatus);
		} catch (Exception e) {
			log.error("删除客戶共同借款人失败");
			RespHelper.setFailRespStatus(respStatus, "删除客戶共同借款人失败");
			e.printStackTrace();
		}
		return respStatus;
	}	
	
	/**
	 * app保存客戶担保人
	 * @param request
	 * @param orderBaseBorrowDto
	 * @return
	 */
	@RequestMapping("/v/saveCustomerGuarantee")
	@ResponseBody
	public RespStatus saveCustomerGuarantee(HttpServletRequest request,
			@RequestBody OrderBaseCustomerGuaranteeDto orderBaseCustomerGuaranteeDto) {
		RespStatus respStatus = new RespStatus();
		try {
			if(isSubmit(orderBaseCustomerGuaranteeDto.getOrderNo(), "placeOrder")){
				RespHelper.setFailRespStatus(respStatus, "订单已提交审核");
				return respStatus;
			}
			UserDto userDto = getUserDto(request); // 获取用户信息
			orderBaseCustomerGuaranteeDto.setUpdateUid(userDto.getUid());
			if(orderBaseCustomerGuaranteeDto.getId()==null||orderBaseCustomerGuaranteeDto.getId()==0){
				orderBaseCustomerGuaranteeDto.setCreateUid(userDto.getUid());
				orderBaseCustomerGuaranteeService.saveOrderCustomerGuarantee(orderBaseCustomerGuaranteeDto);
			}else{
				orderBaseCustomerGuaranteeService.updateOrderCustomerGuarantee(orderBaseCustomerGuaranteeDto);
			}
			RespHelper.setSuccessRespStatus(respStatus);
		} catch (Exception e) {
			log.error("保存客戶担保人失败");
			RespHelper.setFailRespStatus(respStatus, "保存客戶担保人失败");
			e.printStackTrace();
		}
		return respStatus;
	}	
	
	/**
	 * app删除客戶担保人
	 * @param request
	 * @param orderBaseBorrowDto
	 * @return
	 */
	@RequestMapping("/v/deleteCustomerGuarantee")
	@ResponseBody
	public RespStatus deleteCustomerGuarantee(HttpServletRequest request,
			@RequestBody OrderBaseCustomerGuaranteeDto orderBaseCustomerGuaranteeDto) {
		RespStatus respStatus = new RespStatus();
		try {
			if(isSubmit(orderBaseCustomerGuaranteeDto.getOrderNo(), "placeOrder")){
				RespHelper.setFailRespStatus(respStatus, "订单已提交审核");
				return respStatus;
			}
			orderBaseCustomerGuaranteeService.deleteGuaranteeById(orderBaseCustomerGuaranteeDto.getId());
			RespHelper.setSuccessRespStatus(respStatus);
		} catch (Exception e) {
			log.error("删除客戶担保人失败");
			RespHelper.setFailRespStatus(respStatus, "删除客戶担保人失败");
			e.printStackTrace();
		}
		return respStatus;
	}	
	
	/**
	 * app更新房产信息
	 * @param request
	 * @param orderBaseBorrowDto
	 * @return
	 */
	@RequestMapping("/v/updateHouse")
	@ResponseBody
	public RespStatus updateHouse(HttpServletRequest request,
			@RequestBody OrderBaseHouseDto orderBaseHouseDto) {
		RespStatus respStatus = new RespStatus();
		try {
			if(isSubmit(orderBaseHouseDto.getOrderNo(), "placeOrder")){
				RespHelper.setFailRespStatus(respStatus, "订单已提交审核");
				return respStatus;
			}
			UserDto userDto = getUserDto(request); // 获取用户信息
			orderBaseHouseDto.setUpdateUid(userDto.getUid());
			orderBaseHouseService.updateOrderHouseApp(orderBaseHouseDto);
			RespHelper.setSuccessRespStatus(respStatus);
		} catch (Exception e) {
			log.error("更新房产信息失败");
			RespHelper.setFailRespStatus(respStatus, "更新房产信息失败");
			e.printStackTrace();
		}
		return respStatus;
	}	
	
	/**
	 * app保存客户房产信息
	 * @param request
	 * @param orderBaseBorrowDto
	 * @return
	 */
	@RequestMapping("/v/saveHouseProperty")
	@ResponseBody
	public RespStatus saveHouseProperty(HttpServletRequest request,
			@RequestBody OrderBaseHousePropertyDto orderBaseHousePropertyDto) {
		RespStatus respStatus = new RespStatus();
		try {
			if(isSubmit(orderBaseHousePropertyDto.getOrderNo(), "placeOrder")){
				RespHelper.setFailRespStatus(respStatus, "订单已提交审核");
				return respStatus;
			}
			UserDto userDto = getUserDto(request); // 获取用户信息
			orderBaseHousePropertyDto.setUpdateUid(userDto.getUid());
			if(orderBaseHousePropertyDto.getId()==null||orderBaseHousePropertyDto.getId()==0){
				orderBaseHousePropertyDto.setCreateUid(userDto.getUid());
				orderBaseHousePropertyService.saveOrderHouseProperty(orderBaseHousePropertyDto);
			}else{
				orderBaseHousePropertyService.updateOrderPropertyHouse(orderBaseHousePropertyDto);
			}
			RespHelper.setSuccessRespStatus(respStatus);
		} catch (Exception e) {
			log.error("保存客户房产信息失败");
			RespHelper.setFailRespStatus(respStatus, "保存客户房产信息失败");
			e.printStackTrace();
		}
		return respStatus;
	}	
	
	/**
	 * app删除客户房产信息
	 * @param request
	 * @param orderBaseBorrowDto
	 * @return
	 */
	@RequestMapping("/v/deleteHouseProperty")
	@ResponseBody
	public RespStatus deleteHouseProperty(HttpServletRequest request,
			@RequestBody OrderBaseHousePropertyDto orderBaseHousePropertyDto) {
		RespStatus respStatus = new RespStatus();
		try {
			if(isSubmit(orderBaseHousePropertyDto.getOrderNo(), "placeOrder")){
				RespHelper.setFailRespStatus(respStatus, "订单已提交审核");
				return respStatus;
			}
			orderBaseHousePropertyService.deleteHousePropertyById(orderBaseHousePropertyDto.getId());
			RespHelper.setSuccessRespStatus(respStatus);
		} catch (Exception e) {
			log.error("删除客户房产信息失败");
			RespHelper.setFailRespStatus(respStatus, "删除客户房产信息失败");
			e.printStackTrace();
		}
		return respStatus;
	}	


	/**
	 * app保存产权人信息
	 * @param request
	 * @param orderBaseBorrowDto
	 * @return
	 */
	@RequestMapping("/v/savePropertyPeople")
	@ResponseBody
	public RespStatus savePropertyPeople(HttpServletRequest request,
			@RequestBody OrderBaseHousePropertyPeopleDto orderBaseHousePropertyPeopleDto) {
		RespStatus respStatus = new RespStatus();
		try {
			if(isSubmit(orderBaseHousePropertyPeopleDto.getOrderNo(), "placeOrder")){
				RespHelper.setFailRespStatus(respStatus, "订单已提交审核");
				return respStatus;
			}
			UserDto userDto = getUserDto(request); // 获取用户信息
			orderBaseHousePropertyPeopleDto.setUpdateUid(userDto.getUid());
			if(orderBaseHousePropertyPeopleDto.getId()==null||orderBaseHousePropertyPeopleDto.getId()==0){
				orderBaseHousePropertyPeopleDto.setCreateUid(userDto.getUid());
				orderBaseHousePropertyPeopleService.saveOrderPropertyPeople(orderBaseHousePropertyPeopleDto);
			}else{
				orderBaseHousePropertyPeopleService.updateOrderPropertyPeople(orderBaseHousePropertyPeopleDto);
			}
			RespHelper.setSuccessRespStatus(respStatus);
		} catch (Exception e) {
			log.error("保存产权人信息失败");
			RespHelper.setFailRespStatus(respStatus, "保存产权人信息失败");
			e.printStackTrace();
		}
		return respStatus;
	}	
	
	/**
	 * app删除产权人信息
	 * @param request
	 * @param orderBaseBorrowDto
	 * @return
	 */
	@RequestMapping("/v/deletePropertyPeople")
	@ResponseBody
	public RespStatus deletePropertyPeople(HttpServletRequest request,
			@RequestBody OrderBaseHousePropertyPeopleDto orderBaseHousePropertyPeopleDto) {
		RespStatus respStatus = new RespStatus();
		try {
			if(isSubmit(orderBaseHousePropertyPeopleDto.getOrderNo(), "placeOrder")){
				RespHelper.setFailRespStatus(respStatus, "订单已提交审核");
				return respStatus;
			}
			orderBaseHousePropertyPeopleService.deleteProHouseById(orderBaseHousePropertyPeopleDto.getId());
			RespHelper.setSuccessRespStatus(respStatus);
		} catch (Exception e) {
			log.error("删除产权人信息");
			RespHelper.setFailRespStatus(respStatus, "删除产权人信息");
			e.printStackTrace();
		}
		return respStatus;
	}	
	
	
	/**
	 * app保存买房人信息
	 * @param request
	 * @param orderBaseBorrowDto
	 * @return
	 */
	@RequestMapping("/v/saveHousePurchaser")
	@ResponseBody
	public RespStatus saveHousePurchaser(HttpServletRequest request,
			@RequestBody OrderBaseHousePurchaserDto orderBaseHousePurchaserDto) {
		RespStatus respStatus = new RespStatus();
		try {
			if(isSubmit(orderBaseHousePurchaserDto.getOrderNo(), "placeOrder")){
				RespHelper.setFailRespStatus(respStatus, "订单已提交审核");
				return respStatus;
			}
			UserDto userDto = getUserDto(request); // 获取用户信息
			orderBaseHousePurchaserDto.setUpdateUid(userDto.getUid());
			if(orderBaseHousePurchaserDto.getId()==null||orderBaseHousePurchaserDto.getId()==0){
				orderBaseHousePurchaserDto.setCreateUid(userDto.getUid());
				orderBaseHousePurchaserService.saveOrderBaseHousePurchaser(orderBaseHousePurchaserDto);
			}else{
				orderBaseHousePurchaserService.updateOrderHousePurchaser(orderBaseHousePurchaserDto);
			}
			RespHelper.setSuccessRespStatus(respStatus);
		} catch (Exception e) {
			log.error("保存买房人信息失败");
			RespHelper.setFailRespStatus(respStatus, "保存买房人信息失败");
			e.printStackTrace();
		}
		return respStatus;
	}	
	
	/**
	 * app删除买房人信息
	 * @param request
	 * @param orderBaseBorrowDto
	 * @return
	 */
	@RequestMapping("/v/deleteHousePurchaser")
	@ResponseBody
	public RespStatus deleteHousePurchaser(HttpServletRequest request,
			@RequestBody OrderBaseHousePurchaserDto orderBaseHousePurchaserDto) {
		RespStatus respStatus = new RespStatus();
		try {
			if(isSubmit(orderBaseHousePurchaserDto.getOrderNo(), "placeOrder")){
				RespHelper.setFailRespStatus(respStatus, "订单已提交审核");
				return respStatus;
			}
			orderBaseHousePurchaserService.deleteHousePurchaserById(orderBaseHousePurchaserDto.getId());
			RespHelper.setSuccessRespStatus(respStatus);
		} catch (Exception e) {
			log.error("删除买房人信息失败");
			RespHelper.setFailRespStatus(respStatus, "删除买房人信息失败");
			e.printStackTrace();
		}
		return respStatus;
	}	
	
	/**
	 * app更新放款信息:房抵贷
	 * @param request
	 * @param orderBaseHouseCreditDto
	 * @return
	 */
	@RequestMapping("/v/updateHouseLending")
	@ResponseBody
	public RespStatus updateHouseLending(HttpServletRequest request,
			@RequestBody OrderBaseHouseLendingDto orderBaseHouseLendingDto) {
		RespStatus respStatus = new RespStatus();
		try {
			if(isSubmit(orderBaseHouseLendingDto.getOrderNo(), "placeOrder")){
				RespHelper.setFailRespStatus(respStatus, "订单已提交审核");
				return respStatus;
			}
			String orderNo = orderBaseHouseLendingDto.getOrderNo();
			UserDto userDto = getUserDto(request); // 获取用户信息
			OrderBaseHouseLendingDto houseLendingDto = orderBaseHouseLendingService.selectOrderHouseLendingByOrderNo(orderNo);
			if(houseLendingDto!=null){
				orderBaseHouseLendingDto.setUpdateUid(userDto.getUid());
				if("fdd_loan".equals(orderBaseHouseLendingDto.getVersion())) {//更新放款信息
					orderBaseHouseLendingService.updateOrderLendingNull(orderBaseHouseLendingDto);
				}else{//更新回款信息
					orderBaseHouseLendingService.updateOrderPaymentNull(orderBaseHouseLendingDto);
				}
			}else{
				orderBaseHouseLendingDto.setCreateUid(userDto.getUid());
				orderBaseHouseLendingService.saveOrderHouseLending(orderBaseHouseLendingDto);
			}
			RespHelper.setSuccessRespStatus(respStatus);
		} catch (Exception e) {
			log.error("更新放款信息失败");
			RespHelper.setFailRespStatus(respStatus, "更新放款信息失败");
			e.printStackTrace();
		}
		return respStatus;
	}	
	
	/**
	 * 给订单列表字段赋值
	 * 
	 * @param orderBaseBorrowDto
	 * @return
	 */
	public OrderBaseBorrowDto getOrderBaseBorrowDto(OrderBaseBorrowDto orderBaseBorrowDto){
		orderBaseBorrowDto.setAcceptMemberName(CommonDataUtil.getUserDtoByUidAndMobile(orderBaseBorrowDto.getAcceptMemberUid()).getName());
		orderBaseBorrowDto.setNotarialName(CommonDataUtil.getUserDtoByUidAndMobile(orderBaseBorrowDto.getNotarialUid()).getName());
		orderBaseBorrowDto.setFacesignName(CommonDataUtil.getUserDtoByUidAndMobile(orderBaseBorrowDto.getFacesignUid()).getName());
		orderBaseBorrowDto.setElementName(CommonDataUtil.getUserDtoByUidAndMobile(orderBaseBorrowDto.getElementUid()).getName());
		if(null!=orderBaseBorrowDto.getChannelManagerUid()){
			UserDto channelManagerDto = CommonDataUtil.getUserDtoByUidAndMobile(orderBaseBorrowDto.getChannelManagerUid());
			orderBaseBorrowDto.setChannelManagerName(channelManagerDto.getName());
			orderBaseBorrowDto.setCurrentHandler(channelManagerDto.getName());;
		}
		
//		List<UserDto> userList = getUserDtoList();
//		for (UserDto userDto : userList) {
//			//受理员
//			if(userDto.getUid().equals(orderBaseBorrowDto.getAcceptMemberUid())){
//				orderBaseBorrowDto.setAcceptMemberName(userDto.getName());
//			}
//			//渠道经理
//			if(null!=orderBaseBorrowDto.getChannelManagerUid()&&userDto.getUid().equals(orderBaseBorrowDto.getChannelManagerUid())){
//				orderBaseBorrowDto.setChannelManagerName(userDto.getName());
//				orderBaseBorrowDto.setCurrentHandler(userDto.getName());;
//			}
//			//公证员
//			if(userDto.getUid().equals(orderBaseBorrowDto.getNotarialUid())){
//				orderBaseBorrowDto.setNotarialName(userDto.getName());
//			}
//			//面签员
//			if(userDto.getUid().equals(orderBaseBorrowDto.getFacesignUid())){
//				orderBaseBorrowDto.setFacesignName(userDto.getName());
//			}
//			//要件管理员
//			if(userDto.getUid().equals(orderBaseBorrowDto.getElementUid())){
//				orderBaseBorrowDto.setElementName(userDto.getName());
//			}
//		}
		//获取所有城市
		List<DictDto> dictList = getDictDtoByType("bookingSzAreaOid");
		for (DictDto dictDto : dictList) {
			if(dictDto.getCode().equals(orderBaseBorrowDto.getCityCode())){
				orderBaseBorrowDto.setCityName(dictDto.getName());
				break;
			}
		}
		//获取产品名称
//		List<ProductDto> prductList = getProductDtos();
//		for (ProductDto productDto : prductList) {
//			if(productDto.getProductCode().equals(orderBaseBorrowDto.getProductCode())){
//				orderBaseBorrowDto.setProductName(productDto.getProductName());
//				break;
//			}
//		}
		
		if(StringUtils.isNotEmpty(orderBaseBorrowDto.getCityCode()) && StringUtils.isNotEmpty(orderBaseBorrowDto.getProductCode())){
			orderBaseBorrowDto.setProductName(CommonDataUtil.getProductDtoByProductId(orderBaseBorrowDto.getCityCode() + orderBaseBorrowDto.getProductCode()).getProductName());
		}
		
		//获取合作机构名称
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("agencyId", orderBaseBorrowDto.getCooperativeAgencyId());
		AgencyDto agencyDto = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/customer/agency/v/getAgencyDto", map, AgencyDto.class);
		if(agencyDto!=null){
			orderBaseBorrowDto.setCooperativeAgencyName(agencyDto.getName());
		}
		//受理员机构名称
		Map<String,Object> maps = new HashMap<String, Object>();
		maps.put("agencyId", orderBaseBorrowDto.getAgencyId());
		AgencyDto agency = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/customer/agency/v/getAgencyDto", maps, AgencyDto.class);
		if(agency!=null){
			orderBaseBorrowDto.setAgencyName(agency.getName());
		}
		//收费类型名称
		List<DictDto> dicts  = getDictDtoByType("riskControl");
		for (DictDto dictDto : dicts) {
			if(dictDto.getCode().equals(String.valueOf(orderBaseBorrowDto.getRiskGradeId()))){
				orderBaseBorrowDto.setRiskGrade(dictDto.getName());
			}
			if(orderBaseBorrowDto.getRiskGradeId()!=null&&orderBaseBorrowDto.getRiskGradeId()==0){
				orderBaseBorrowDto.setRiskGrade("其他");
			}
		}
		//获取银行支行名称
//		List<BankDto> bankList = getAllBankList();
//		List<SubBankDto> subBankList = getAllSubBankList();
		if((orderBaseBorrowDto.getIsOldLoanBank()!=null&&orderBaseBorrowDto.getIsOldLoanBank()==1)||(orderBaseBorrowDto.getIsLoanBank()!=null&&orderBaseBorrowDto.getIsLoanBank()==1)
				||(orderBaseBorrowDto.getIsRebate()!=null&&orderBaseBorrowDto.getIsRebate()==1)){
			//查询银行名称
				if(orderBaseBorrowDto.getOldLoanBankNameId()!=null){
					orderBaseBorrowDto.setOldLoanBankName(CommonDataUtil.getBankNameById(orderBaseBorrowDto.getOldLoanBankNameId()).getName());
				}
				if(orderBaseBorrowDto.getLoanBankNameId()!=null){
					orderBaseBorrowDto.setLoanBankName(CommonDataUtil.getBankNameById(orderBaseBorrowDto.getLoanBankNameId()).getName());
				}
				if(orderBaseBorrowDto.getRebateBankId()!=null){
					orderBaseBorrowDto.setRebateBank(CommonDataUtil.getBankNameById(orderBaseBorrowDto.getRebateBankId()).getName());
				}
				if(orderBaseBorrowDto.getOldLoanBankSubNameId()!=null){
					orderBaseBorrowDto.setOldLoanBankSubName(CommonDataUtil.getSubBankNameById(orderBaseBorrowDto.getOldLoanBankSubNameId()).getName());
				}
				if(orderBaseBorrowDto.getLoanSubBankNameId()!=null){
					orderBaseBorrowDto.setLoanSubBankName(CommonDataUtil.getSubBankNameById(orderBaseBorrowDto.getLoanSubBankNameId()).getName());
				}
				if(orderBaseBorrowDto.getRebateBankSubId()!=null){
					orderBaseBorrowDto.setRebateSubBank(CommonDataUtil.getSubBankNameById(orderBaseBorrowDto.getRebateBankSubId()).getName());
				}
		}
		return orderBaseBorrowDto;
	}

	/**
	 *快鸽APP提单
	 * @param request
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/kgAppInsertOrder")
	public RespStatus kgAppInsertOrder(HttpServletRequest request,@RequestBody Map<String,Object> map){
		RespStatus result = new RespStatus();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try {

			if(MapUtils.isNotEmpty(map)) {
				OrderBaseBorrowDto orderDto = new OrderBaseBorrowDto();
				orderDto.setCreateTime(new Date());
				String orderNo = MapUtils.getString(map,"orderNo");
				//设置订单号
				if(StringUtils.isBlank(orderNo)){
					orderNo = UidUtil.generateOrderId();
				}
				Double loanAmount = MapUtils.getDouble(map,"loanAmount",0d);//借款金额
				int borrowingDays = MapUtils.getInteger(map,"borrowingDays",0);//借款天数
				String houseRegion = MapUtils.getString(map,"houseRegion","");//房产区域
				String houseName = MapUtils.getString(map,"houseName","");//房产名称
				String borrowerName = MapUtils.getString(map,"borrowerName","");
				String city = MapUtils.getString(map,"cityName","");
				String province = MapUtils.getString(map,"province","");
				String source = MapUtils.getString(map,"source");//订单来源
				String productCode = MapUtils.getString(map,"productCode","");//业务类型
				String phoneNumber = MapUtils.getString(map,"phoneNumber","");//借款人手机号码
				String channelManagerUid = MapUtils.getString(map,"channelManagerUid","");//渠道经理
				if(StringUtils.isEmpty(channelManagerUid)){
					channelManagerUid = "1470744895860";
				}
				String uid = MapUtils.getString(map,"uid","");
				orderDto.setAgencyId(0);
				orderDto.setLoanAmount(loanAmount);
				orderDto.setBorrowingDays(borrowingDays);
				orderDto.setCreateUid(uid);
				orderDto.setPreviousHandlerUid(uid);
				orderDto.setBorrowerName(borrowerName);
				orderDto.setProductCode(productCode);
				orderDto.setPhoneNumber(phoneNumber);
				orderDto.setChannelManagerUid(channelManagerUid);
				orderDto.setOrderNo(orderNo);

				OrderBaseHouseDto house = new OrderBaseHouseDto();
				OrderBaseHousePropertyDto property = new OrderBaseHousePropertyDto();
				property.setCreateUid(uid);
				property.setOrderNo(orderNo);
				List<OrderBaseHousePropertyDto> list = new ArrayList<OrderBaseHousePropertyDto>();

				if (StringUtils.isNotBlank(houseName)) {
					houseName = URLDecoder.decode(houseName, "UTF-8");
					property.setHouseName(houseName);
				}
				if (StringUtils.isNotBlank(city)) {
					city = URLDecoder.decode(city, "UTF-8");
					property.setCity(city);
				}
				if (StringUtils.isNotBlank(province)) {
					province = URLDecoder.decode(province, "UTF-8");
					property.setProvince(province);
				}
				if (StringUtils.isNotBlank(borrowerName)) {
					borrowerName = URLDecoder.decode(borrowerName, "UTF-8");
					orderDto.setBorrowerName(borrowerName);
				}
				if (StringUtils.isNotBlank(houseRegion)) {
					houseRegion = URLDecoder.decode(houseRegion, "UTF-8");
					property.setHouseRegion(houseRegion);
				}
				if (StringUtils.isNotBlank(source)) {
					source = URLDecoder.decode(source, "UTF-8");
				} else {
					source = "快鸽APP";
				}
				list.add(property);
				house.setOrderBaseHousePropertyDto(list);
				orderDto.setSource(source);
				String cityCode="";
				//获取所有城市
				List<DictDto> dictList = getDictDtoByType("bookingSzAreaOid");
				for (DictDto dictDto : dictList) {
					if(dictDto.getName().contains(city)){
						cityCode = dictDto.getCode();
						orderDto.setCityName(dictDto.getName());
						break;
					}
				}
				orderDto.setCityCode(cityCode);
				
				/*
				if (StringUtils.isNotBlank(orderDto.getCity())) {
					String city = URLDecoder.decode(orderDto.getCity(), "UTF-8");
					orderDto.setCity(city);
					Map<String, Object> tmp = orderService.findCityByNameAndType(orderDto.getCity(), "bookingSzAreaOid");
					if (null != tmp && tmp.size() > 0) {
						if (tmp.containsKey("pcode")) {
							orderDto.setProvince(tmp.get("pcode") + "");
						}
						if (tmp.containsKey("code")) {
							orderDto.setCity(tmp.get("code") + "");
						}
					}
				}
				*/

				//获取产品名称
				List<ProductDto> prductList = getProductDtos();
				for (ProductDto productDto : prductList) {
					if(productDto.getProductCode().equals(orderDto.getProductCode())){
						orderDto.setProductName(productDto.getProductName());
						break;
					}
				}
				
				UserDto channelManagerUser = null;
				//渠道经理
				if(StringUtils.isNotEmpty(orderDto.getChannelManagerUid())){
					channelManagerUser = CommonDataUtil.getUserDtoByUidAndMobile(orderDto.getChannelManagerUid());
					orderDto.setChannelManagerName(channelManagerUser.getName());
					orderDto.setCurrentHandler(channelManagerUser.getName());;
				}
				orderBaseBorrowService.kgAppInsertOrder(orderDto,house);
				//设置订单基本信息
				log.info("快鸽按揭提单，设置订单基础信息orderNo:"+orderNo);
				setOrderBaseInfo(orderDto.getProductCode(),orderNo);
				//发送短信
				if(StringUtils.isNotBlank(orderDto.getChannelManagerUid())){
					String customerTitle = "";
					if(StringUtils.isNotBlank(orderDto.getPhoneNumber())){
						customerTitle = orderDto.getPhoneNumber();
					}
					if(null!=channelManagerUser&&StringUtils.isNotBlank(channelManagerUser.getMobile())){
						String ipWhite = ConfigUtil.getStringValue(Constants.BASE_AMS_IPWHITE,ConfigUtil.CONFIG_BASE);
						AmsUtil.smsSend(channelManagerUser.getMobile(), ipWhite,Constants.SMS_KGAPP_SUBMIT_ORDER,orderDto.getBorrowerName(),orderDto.getLoanAmount(),customerTitle);
					}

				}
			}
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 指派受理员
	 * @Title: assignAcceptMember 
	 * @param @param request
	 * @param @param map
	 * @param @return
	 * @return RespData<List<Map<String,Object>>>
	 */
	@SuppressWarnings("unused")
	@RequestMapping("/assignAcceptMember")
	@ResponseBody
	private RespStatus assignAcceptMember(HttpServletRequest request,@RequestBody OrderBaseBorrowDto order) {
		RespStatus result = new RespStatus();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		//设置渠道经理uid
		UserDto user = getUserDto(request);
		if(StringUtils.isBlank(order.getChannelManagerUid())){
			order.setChannelManagerUid(user.getUid());
		}
		try{
			if(StringUtils.isNotBlank(order.getOrderNo())&&
					StringUtils.isNotBlank(order.getAcceptMemberUid())){
				OrderBaseBorrowDto  tmp = orderBaseBorrowService.selectOrderBorrowByOrderNo(order.getOrderNo());
				
//				List<UserDto> userList = getUserDtoList();
				UserDto acceptMember = CommonDataUtil.getUserDtoByUidAndMobile(order.getAcceptMemberUid());
				UserDto channelManager = CommonDataUtil.getUserDtoByUidAndMobile(order.getChannelManagerUid());
				order.setAcceptMemberName(acceptMember.getName());
				
				
//				int index = 0;
//				for (UserDto userDto : userList) {
//					//受理员
//					if(userDto.getUid().equals(order.getAcceptMemberUid())){
//						order.setAcceptMemberName(acceptMember.getName());
//						acceptMember = userDto;
//						index ++;
//					}
//					if(userDto.getUid().equals(tmp.getChannelManagerUid())){
//						channelManager = userDto;
//						index++;
//					}
//					if(index==2){
//						break;
//					}
//				}
				order.setAgencyId(acceptMember.getAgencyId());
				int success = orderBaseBorrowService.assignAcceptMember(order);
				
				//发送短信
				if(null!=acceptMember&&StringUtils.isNotBlank(acceptMember.getMobile())){
					String ipWhite = ConfigUtil.getStringValue(Constants.BASE_AMS_IPWHITE,ConfigUtil.CONFIG_BASE);
					AmsUtil.smsSend(acceptMember.getMobile(), ipWhite,Constants.SMS_ASSIGN_ACCEPTMEMBER,channelManager.getName(),tmp.getBorrowerName(),tmp.getLoanAmount());
				}

			}
			else{
				return result; 
			}
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	@Resource
	private OrderFlowController orderFlowController;
	
	@ResponseBody
	@RequestMapping("/orderDetail")
	public RespDataObject<Map<String,Object>> orderDetail(HttpServletRequest request,String orderNo,String agency) {
		RespDataObject<Map<String,Object>> resp = new RespDataObject<Map<String,Object>>();
		try {
			
			Map<String,Object> dataMap = new HashMap<String,Object>();
			OrderFlowDto orderFlowDto = new OrderFlowDto();
			orderFlowDto.setOrderNo(orderNo);
			RespData<OrderFlowDto> respData = orderFlowController.selectOrderFlowList(request, orderFlowDto);
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			for (OrderFlowDto orderFlow : respData.getData()) {
				if(orderFlow.getCurrentProcessId().equals("auditFirst") || orderFlow.getCurrentProcessId().equals("auditFinal") || orderFlow.getCurrentProcessId().equals("auditOfficer") || orderFlow.getCurrentProcessId().equals("allocationFund")){
					continue;
				}
				Map<String, Object> temp = new HashMap<String, Object>();
				temp.put("progressId", orderFlow.getCurrentProcessId());
				temp.put("finishTime", DateUtil.getDateByFmt(orderFlow.getHandleTime(), DateUtil.FMT_TYPE1));
				temp.put("progressName", orderFlow.getCurrentProcessName()+"已完成,操作人："+orderFlow.getHandleName());
				list.add(temp);
			}
			OrderBaseBorrowDto  order = orderBaseBorrowService.selectOrderBorrowByOrderNo(orderNo);
			List<OrderBaseHousePropertyDto> houseList = orderBaseHousePropertyService.selectOrderHousePropertyByOrderNo(orderNo);
			OrderBaseHousePropertyDto house = null;
			if(null==order){
				order = new OrderBaseBorrowDto();
			}
			if(null!=houseList&&houseList.size()>0){
				house = houseList.get(0);
			} else {
				house = new OrderBaseHousePropertyDto();
			}
			String cityName = "-";
			if(StringUtils.isNotBlank(house.getCityName())){
				cityName = house.getCityName();
			}
			String houseRegion = "-";
			if(StringUtils.isNotBlank(house.getHouseRegion())){
				houseRegion = house.getHouseRegion();
			}
			String borrowerName = "-";
			if(StringUtils.isNotBlank(order.getBorrowerName())){
				borrowerName = order.getBorrowerName();
			}
			List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
			Map<String,Object> m1 = new HashMap<String,Object>();
			m1.put("key","债务置换贷款金额");
			m1.put("val",null==order.getLoanAmount()?"-":order.getLoanAmount()+"万");
			dataList.add(m1);
			m1 = new HashMap<String,Object>();
			m1.put("key","债务置换贷款天数");
			m1.put("val",order.getBorrowingDays()+"天");
			dataList.add(m1);
			m1 = new HashMap<String,Object>();
			m1.put("key","房产地址");
			m1.put("val",cityName);
			dataList.add(m1);
			m1 = new HashMap<String,Object>();
			m1.put("key","所在区域");
			m1.put("val",houseRegion);
			dataList.add(m1);
			m1 = new HashMap<String,Object>();
			m1.put("key","借款人姓名");
			m1.put("val",borrowerName);
			dataList.add(m1);
			if(list.size()==0){
				Map<String, Object> temp = new HashMap<String, Object>();
				temp.put("progressId", "1");
				temp.put("finishTime", DateUtil.getDateByFmt(order.getCreateTime(), DateUtil.FMT_TYPE1));
				temp.put("progressName", "订单已提交(等待客服审核)");
				list.add(temp);
			}
			dataMap.put("detailList",dataList);
			dataMap.put("orderSecFlowList", list);
			System.out.println(list);
			RespHelper.setSuccessDataObject(resp, dataMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
	
	/**
	 * 查询月放款统计
	 * 
	 * @author jiangyq
	 * @param request
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/findLoanStatistic")
	public RespDataObject<Map<String,Object>> findLoanStatistic(HttpServletRequest request, @RequestBody Map<String, String> map) {
		RespDataObject<Map<String,Object>> resp = new RespDataObject<Map<String,Object>>();
		try {
			RespDataObject<Map<String,String>> respTemp = httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/user/base/selectUidsByUid", map, Map.class);
			
			if (!RespStatusEnum.SUCCESS.getCode().equals(respTemp.getCode())) {
				resp.setCode(respTemp.getCode());
				resp.setMsg(respTemp.getMsg());
				return resp;
			}
			
			String uids = MapUtils.getString(respTemp.getData(), "uids");
			String agencyId = MapUtils.getString(respTemp.getData(), "agencyId");
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<Map<String,Object>> list = orderBaseService.findLoanStatisticMonth(agencyId, uids);
			Map<String,Object> totalMap = orderBaseService.findLoanStatisticTotal(agencyId, uids);
			
			dataMap.putAll(totalMap);
			dataMap.put("list",list);
			RespHelper.setSuccessDataObject(resp, dataMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
	
	/**
	 * 查询月放款统计详情
	 * 
	 * @author jiangyq
	 * @param request
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/findLoanStatisticDetail")
	public RespDataObject<Map<String,Object>> findLoanStatisticDetail(HttpServletRequest request, @RequestBody Map<String, String> map) {
		RespDataObject<Map<String,Object>> resp = new RespDataObject<Map<String,Object>>();
		try {
			RespDataObject<Map<String,String>> respTemp = httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/user/base/selectUidsByUid", map, Map.class);
			
			if (!RespStatusEnum.SUCCESS.getCode().equals(respTemp.getCode())) {
				resp.setCode(respTemp.getCode());
				resp.setMsg(respTemp.getMsg());
				return resp;
			}
			
			String uids = MapUtils.getString(respTemp.getData(), "uids");
			String agencyId = MapUtils.getString(respTemp.getData(), "agencyId");
			int year = MapUtils.getInteger(map, "year");
			int month = MapUtils.getInteger(map, "month");
			
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<Map<String,Object>> list = orderBaseService.findLoanStatisticDetail(agencyId, uids, year, month);
			
			List<DictDto> productList = getDictDtoByType("product");
			if (list != null) {
				for (Map<String,Object> m : list) {
					String productCode = MapUtils.getString(m, "productCode");
					m.put("productName", "");
					for (DictDto dictDto : productList) {
						if (dictDto.getCode().equals(productCode)) {
							m.put("productName", dictDto.getName());
						}
					}
				}
			}
			
			dataMap.put("list",list);
			RespHelper.setSuccessDataObject(resp, dataMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
	
	
	/**
	 * 查询月订单完成统计
	 * 
	 * @author jiangyq
	 * @param request
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/findFinishStatistic")
	public RespDataObject<Map<String,Object>> findFinishStatistic(HttpServletRequest request, @RequestBody Map<String, String> map) {
		RespDataObject<Map<String,Object>> resp = new RespDataObject<Map<String,Object>>();
		try {
			RespDataObject<Map<String,String>> respTemp = httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/user/base/selectUidsByUid", map, Map.class);
			
			if (!RespStatusEnum.SUCCESS.getCode().equals(respTemp.getCode())) {
				resp.setCode(respTemp.getCode());
				resp.setMsg(respTemp.getMsg());
				return resp;
			}
			
			String uids = MapUtils.getString(respTemp.getData(), "uids");
			String agencyId = MapUtils.getString(respTemp.getData(), "agencyId");
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<Map<String,Object>> list = orderBaseService.findFinishStatisticMonth(agencyId, uids);
			Map<String,Object> totalMap = orderBaseService.findFinishStatisticTotal(agencyId, uids);
			
			dataMap.putAll(totalMap);
			dataMap.put("list",list);
			RespHelper.setSuccessDataObject(resp, dataMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
	
	/**
	 * 查询月订单完成统计详情
	 * 
	 * @author jiangyq
	 * @param request
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/findFinishStatisticDetail")
	public RespDataObject<Map<String,Object>> findFinishStatisticDetail(HttpServletRequest request, @RequestBody Map<String, String> map) {
		RespDataObject<Map<String,Object>> resp = new RespDataObject<Map<String,Object>>();
		try {
			RespDataObject<Map<String,String>> respTemp = httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/user/base/selectUidsByUid", map, Map.class);
			
			if (!RespStatusEnum.SUCCESS.getCode().equals(respTemp.getCode())) {
				resp.setCode(respTemp.getCode());
				resp.setMsg(respTemp.getMsg());
				return resp;
			}
			
			String uids = MapUtils.getString(respTemp.getData(), "uids");
			String agencyId = MapUtils.getString(respTemp.getData(), "agencyId");
			int year = MapUtils.getInteger(map, "year");
			int month = MapUtils.getInteger(map, "month");
			
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<Map<String,Object>> list = orderBaseService.findFinishStatisticDetail(agencyId, uids, year, month);
			
			StringBuffer orderNo = new StringBuffer("");
			for (Map<String,Object> m : list) {
				orderNo.append(MapUtils.getString(m, "orderNo")+",");
			}
			
			String orderNos = orderNo.toString();
			if(StringUtils.isNotEmpty(orderNos)){
				orderNos = orderNos.substring(0, orderNos.length()-1);
			}
			
			Map<String, String> map1 = new HashMap<String, String>();
			map1.put("orderNos", orderNos);
			
			RespData<Map<String,Object>> res = httpUtil.getRespData(Constants.LINK_CREDIT, "/credit/finance/receivable/app/findPaymentByLoan", map1, Map.class);
			
			List<Map<String,Object>> map2 = res.getData();
			List<DictDto> productList = getDictDtoByType("product");
			if (list != null) {
				for (Map<String,Object> m : list) {
					String orderNo1 = MapUtils.getString(m, "orderNo");
					if (map2 != null) {
						for (Map<String, Object> map3 : map2) {
							String orderNo2 = MapUtils.getString(map3, "orderNo");
							if (orderNo1.equals(orderNo2)) {
								m.putAll(map3);
							}
						}
					}
					String productCode = MapUtils.getString(m, "productCode");
					m.put("productName", "");
					for (DictDto dictDto : productList) {
						if (dictDto.getCode().equals(productCode)) {
							m.put("productName", dictDto.getName());
						}
					}
				}
			}
			dataMap.put("list",list);
			RespHelper.setSuccessDataObject(resp, dataMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
	
	/**
	 * 通过订单查询并返回订单集合信息
	 * @param request
	 * @param uid
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findLoanByOrderNos") 
	public RespData<Map<String, Object>> findLoanByOrderNos(HttpServletRequest request, @RequestBody Map<String, Object> params){
		RespData<Map<String,Object>> resp = new RespData<Map<String,Object>>();
		try {
			String orderNos = MapUtils.getString(params, "orderNos");
			List<Map<String,Object>> list = orderBaseService.findLoanByOrderNos(orderNos);
			resp.setData(list);
			RespHelper.setSuccessData(resp, list);
		} catch (Exception e) {
			e.printStackTrace();
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(RespStatusEnum.FAIL.getMsg());
		}
		
		return resp;
	}
	
	/**
	 * 查询所有的产品
	 * @param request
	 * @param uid
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findProductList") 
	public RespData<DictDto> findProductList(HttpServletRequest request){
		RespData<DictDto> resp = new RespData<DictDto>();
		try {
			List<DictDto> list = getDictDtoByType("product");
			resp.setData(list);
			RespHelper.setSuccessData(resp, list);
		} catch (Exception e) {
			e.printStackTrace();
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(RespStatusEnum.FAIL.getMsg());
		}
		
		return resp;
	}
	
	/**
	 * 通过用户uids查询并返回订单号OrderNos
	 * @param request
	 * @param uid
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findOrderNosByUids") 
	public RespDataObject<String> findOrderNosByUids(HttpServletRequest request, @RequestBody Map<String, Object> params){
		RespDataObject<String> resp = new RespDataObject<String>();
		try {
			String uids = MapUtils.getString(params, "uids");
			
			if (StringUtils.isBlank(uids)) {
				RespHelper.setSuccessDataObject(resp, "");
				return resp;
			}
			
			String orderNos = orderBaseService.findOrderNosByUids(uids);
			RespHelper.setSuccessDataObject(resp, orderNos);
		} catch (Exception e) {
			e.printStackTrace();
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(RespStatusEnum.FAIL.getMsg());
		}
		
		return resp;
	}
	//以下是新版方法2017-12-01
	/**
	 * app保存客戶共同借款人
	 * @param request
	 * @param orderBaseCustomerDto
	 * @return
	 */
	@RequestMapping("/v/updateCustomerBorrowerList")
	@ResponseBody
	public RespStatus saveCustomerBorrowList(HttpServletRequest request,
			@RequestBody OrderBaseCustomerDto orderBaseCustomerDto) {
		RespStatus respStatus = new RespStatus();
		try {
			if(isSubmit(orderBaseCustomerDto.getOrderNo(), "placeOrder")){
				RespHelper.setFailRespStatus(respStatus, "订单已提交审核");
				return respStatus;
			}
			UserDto userDto = getUserDto(request); // 获取用户信息
			orderBaseCustomerDto.setCreateUid(userDto.getUid());
			orderBaseCustomerDto.setUpdateUid(userDto.getUid());
			orderBaseCustomerService.appUpdateCustomerBorrower(orderBaseCustomerDto);
			RespHelper.setSuccessRespStatus(respStatus);
		} catch (Exception e) {
			log.error("app保存客戶共同借款人失败");
			RespHelper.setFailRespStatus(respStatus, "app保存客戶共同借款人失败");
			e.printStackTrace();
		}
		return respStatus;
	}	
	
	/**
	 * app保存客户企业股东信息
	 * @param request
	 * @param orderBaseCustomerShareholderDto
	 * @return
	 */
	@RequestMapping("/v/updateCustomerShareholderList")
	@ResponseBody
	public RespStatus saveCustomerShareholder(HttpServletRequest request,
			@RequestBody OrderBaseCustomerDto orderBaseCustomerDto) {
		RespStatus respStatus = new RespStatus();
		try {
			if(isSubmit(orderBaseCustomerDto.getOrderNo(), "placeOrder")){
				RespHelper.setFailRespStatus(respStatus, "订单已提交审核");
				return respStatus;
			}
			UserDto userDto = getUserDto(request); // 获取用户信息
			orderBaseCustomerDto.setCreateUid(userDto.getUid());
			orderBaseCustomerDto.setUpdateUid(userDto.getUid());
			orderBaseCustomerService.appUpdateCustomerShareholder(orderBaseCustomerDto);
			RespHelper.setSuccessRespStatus(respStatus);
		} catch (Exception e) {
			log.error("app保存客户企业股东信息失败");
			RespHelper.setFailRespStatus(respStatus, "app保存客户企业股东信息失败");
			e.printStackTrace();
		}
		return respStatus;
	}	
	
	/**
	 * app保存客戶担保人信息
	 * @param request
	 * @param orderBaseCustomerDto
	 * @return
	 */
	@RequestMapping("/v/updateCustomerGuaranteeList")
	@ResponseBody
	public RespStatus saveCustomerGuaranteeList(HttpServletRequest request,
			@RequestBody OrderBaseCustomerDto orderBaseCustomerDto) {
		RespStatus respStatus = new RespStatus();
		try {
			if(isSubmit(orderBaseCustomerDto.getOrderNo(), "placeOrder")){
				RespHelper.setFailRespStatus(respStatus, "订单已提交审核");
				return respStatus;
			}
			UserDto userDto = getUserDto(request); // 获取用户信息
			orderBaseCustomerDto.setCreateUid(userDto.getUid());
			orderBaseCustomerDto.setUpdateUid(userDto.getUid());
			orderBaseCustomerService.appUpdateCustomerGuarantee(orderBaseCustomerDto);
			RespHelper.setSuccessRespStatus(respStatus);
		} catch (Exception e) {
			log.error("app保存客戶担保人信息失败");
			RespHelper.setFailRespStatus(respStatus, "app保存客戶担保人信息失败");
			e.printStackTrace();
		}
		return respStatus;
	}	
	
	/**
	 * app保存客戶房产信息
	 * @param request
	 * @param orderBaseCustomerDto
	 * @return
	 */
	@RequestMapping("/v/updateHousePropertyList")
	@ResponseBody
	public RespStatus updateHousePropertyList(HttpServletRequest request,
			@RequestBody OrderBaseHouseDto orderBaseHouseDto) {
		RespStatus respStatus = new RespStatus();
		try {
			if(isSubmit(orderBaseHouseDto.getOrderNo(), "placeOrder")){
				RespHelper.setFailRespStatus(respStatus, "订单已提交审核");
				return respStatus;
			}
			UserDto userDto = getUserDto(request); // 获取用户信息
			orderBaseHouseDto.setCreateUid(userDto.getUid());
			orderBaseHouseDto.setUpdateUid(userDto.getUid());
			orderBaseHouseService.appUpdateHouseProperty(orderBaseHouseDto);
			RespHelper.setSuccessRespStatus(respStatus);
		} catch (Exception e) {
			log.error("app保存客戶房产信息失败");
			RespHelper.setFailRespStatus(respStatus, "app保存客戶房产信息失败");
			e.printStackTrace();
		}
		return respStatus;
	}	
	
	/**
	 * app保存产权人信息
	 * @param request
	 * @param orderBaseCustomerDto
	 * @return
	 */
	@RequestMapping("/v/updateHousePropertyPeopleList")
	@ResponseBody
	public RespStatus updateHousePropertyPeopleList(HttpServletRequest request,
			@RequestBody OrderBaseHouseDto orderBaseHouseDto) {
		RespStatus respStatus = new RespStatus();
		try {
			if(isSubmit(orderBaseHouseDto.getOrderNo(), "placeOrder")){
				RespHelper.setFailRespStatus(respStatus, "订单已提交审核");
				return respStatus;
			}
			UserDto userDto = getUserDto(request); // 获取用户信息
			orderBaseHouseDto.setCreateUid(userDto.getUid());
			orderBaseHouseDto.setUpdateUid(userDto.getUid());
			orderBaseHouseService.appUpdateHousePropertyPeople(orderBaseHouseDto);
			RespHelper.setSuccessRespStatus(respStatus);
		} catch (Exception e) {
			log.error("app保存产权人信息失败");
			RespHelper.setFailRespStatus(respStatus, "app保存产权人信息失败");
			e.printStackTrace();
		}
		return respStatus;
	}	
	
	/**
	 * app保存买房人信息
	 * @param request
	 * @param orderBaseCustomerDto
	 * @return
	 */
	@RequestMapping("/v/updateHousePurchaserList")
	@ResponseBody
	public RespStatus updateHousePurchaserList(HttpServletRequest request,
			@RequestBody OrderBaseHouseDto orderBaseHouseDto) {
		RespStatus respStatus = new RespStatus();
		try {
			if(isSubmit(orderBaseHouseDto.getOrderNo(), "placeOrder")){
				RespHelper.setFailRespStatus(respStatus, "订单已提交审核");
				return respStatus;
			}
			UserDto userDto = getUserDto(request); // 获取用户信息
			orderBaseHouseDto.setCreateUid(userDto.getUid());;
			orderBaseHouseDto.setUpdateUid(userDto.getUid());
			orderBaseHouseService.appUpdateHousePurchaser(orderBaseHouseDto);
			RespHelper.setSuccessRespStatus(respStatus);
		} catch (Exception e) {
			log.error("app保存买房人信息失败");
			RespHelper.setFailRespStatus(respStatus, "app保存买房人信息失败");
			e.printStackTrace();
		}
		return respStatus;
	}	
	
	
	/**
	 * 查询当月放款和回款统计
	 * 
	 * @author jiangyq
	 * @param request
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/findTotalStatistic")
	public RespDataObject<Map<String,Object>> findTotalStatistic(HttpServletRequest request, @RequestBody Map<String, String> map) {
		RespDataObject<Map<String,Object>> resp = new RespDataObject<Map<String,Object>>();
		try {
			RespDataObject<Map<String,String>> respTemp = httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/user/base/selectUidsByUid", map, Map.class);
			
			if (!RespStatusEnum.SUCCESS.getCode().equals(respTemp.getCode())) {
				resp.setCode(respTemp.getCode());
				resp.setMsg(respTemp.getMsg());
				return resp;
			}
			
			String uids = MapUtils.getString(respTemp.getData(), "uids");
			//String agencyId = MapUtils.getString(respTemp.getData(), "agencyId");
			Map<String,Object> dataMap = new HashMap<String,Object>();
			Date now = new Date();
			int year = DateUtil.getYear(now);
			int month = DateUtil.getMonth(now);
			Map<String,Object> loanMap = orderBaseService.findLoanStatisticByMonthAndUids(uids, year, month);
			
			//所有的订单号
			String orderNos = orderBaseService.findOrderNosByUids(uids);
			if (StringUtils.isNotBlank(orderNos)) {
				Map<String, Object> map1 = new HashMap<String, Object>();
				map1.put("orderNos", orderNos);
				map1.put("year", year);
				map1.put("month", month);
				RespDataObject<Map<String,Object>> res = httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/finance/receivable/app/findPaymentStatisticByMonthAndOrderNos", map1, Map.class);
				dataMap.putAll(res.getData());
			}else{
				dataMap.put("paymentTotalAmount", 0);
				dataMap.put("paymentCount", 0);
			}
			
			dataMap.putAll(loanMap);
			RespHelper.setSuccessDataObject(resp, dataMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
	
}
