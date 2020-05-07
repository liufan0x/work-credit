package com.anjbo.controller;

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
import com.anjbo.bean.order.OrderBaseCustomerDto;
import com.anjbo.bean.order.OrderBaseHouseDto;
import com.anjbo.bean.order.OrderBusinfoDto;
import com.anjbo.bean.order.OrderFlowDto;
import com.anjbo.bean.order.OrderListDto;
import com.anjbo.bean.product.DistributionMemberDto;
import com.anjbo.bean.product.NotarizationDto;
import com.anjbo.bean.risk.CreditDto;
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
import com.anjbo.service.OrderBaseCustomerService;
import com.anjbo.service.OrderBaseHouseService;
import com.anjbo.service.OrderBaseService;
import com.anjbo.service.OrderBusinfoService;
import com.anjbo.service.OrderBusinfoTypeService;
import com.anjbo.service.OrderFlowService;
import com.anjbo.utils.AmsUtil;
import com.anjbo.utils.CommonDataUtil;
import com.anjbo.utils.ConfigUtil;
import com.anjbo.utils.HttpUtil;
import com.anjbo.utils.PinYin4JUtil;

@Controller
@RequestMapping("/credit/order/borrow/v")
public class OrderBaseBorrowController extends BaseController {

	Logger log = Logger.getLogger(OrderBaseBorrowController.class);

	@Resource
	private OrderBaseBorrowService orderBaseBorrowService;
	@Resource
	private OrderBaseBorrowRelationService orderBaseBorrowRelationService;
	@Resource
	private OrderBaseCustomerService orderBaseCustomerService;
	@Resource
	private OrderBaseHouseService orderBaseHouseService;
	@Resource
	private OrderBusinfoService orderBusinfoService;
	@Resource
	private OrderBaseService orderBaseService;
	@Resource
	private OrderFlowService orderFlowService;
	@Resource
	private OrderBusinfoTypeService orderBusinfoTypeService;

	@RequestMapping("save")
	@ResponseBody
	public RespDataObject<String> save(HttpServletRequest request,
			@RequestBody OrderBaseBorrowDto orderBaseBorrowDto) {
		RespDataObject<String> resp = new RespDataObject<String>();
		try {
			if(orderBaseBorrowDto.getLoanAmount() != null && orderBaseBorrowDto.getLoanAmount() >= 2000){
				return RespHelper.setFailDataObject(resp, null, "超过2000万的订单请线下处理");
			}
			
			UserDto userDto = getUserDto(request); // 获取用户信息
			orderBaseBorrowDto.setCreateUid(userDto.getUid());
			orderBaseBorrowDto.setAgencyId(userDto.getAgencyId());
			orderBaseBorrowDto.setUpdateUid(userDto.getUid());
			//提单机构id，渠道经理uid
			log.info("提单机构id，渠道经理uid:"+userDto.getAgencyId()+"=="+userDto.getAgencyChanlManUid());
			if(userDto.getAgencyId()>1){//机构提单，设置合作机构，渠道经理
				orderBaseBorrowDto.setChannelManagerUid(userDto.getAgencyChanlManUid());
				orderBaseBorrowDto.setCooperativeAgencyId(userDto.getAgencyId());
			}else if(1==userDto.getAgencyId() && orderBaseBorrowDto.getCooperativeAgencyId()<1 && StringUtils.isNotBlank(orderBaseBorrowDto.getRelationOrderNo())){//机构提关联订单，自动保存合作机构、渠道经理
				OrderListDto relationOrder = orderBaseService.selectDetail(orderBaseBorrowDto.getRelationOrderNo());
				if(null==relationOrder || relationOrder.getCooperativeAgencyId()<1 || StringUtils.isBlank(relationOrder.getChannelManagerUid())){//关联订单无合作机构、渠道经理，不予关联
					RespHelper.setFailDataObject(resp, null, "参数错误，无效的关联订单信息");
					return resp;
				}
				orderBaseBorrowDto.setChannelManagerUid(relationOrder.getChannelManagerUid());
				orderBaseBorrowDto.setCooperativeAgencyId(relationOrder.getCooperativeAgencyId());
			}
			orderBaseBorrowDto = getOrderBaseBorrowDto(orderBaseBorrowDto);
			String orderNo = orderBaseBorrowService
					.saveOrderBorrow(orderBaseBorrowDto);
			setOrderBaseInfo(orderBaseBorrowDto.getProductCode(),orderNo);
			RespHelper.setSuccessDataObject(resp, orderNo);
		} catch (Exception e) {
			log.error("保存订单借款信息集合失败",e);
			RespHelper.setFailDataObject(resp, null, "保存订单借款信息集合失败");
			e.printStackTrace();
		}
		return resp;
	}
	
	/**
	 * 机构提单
	 * 1.渠道经理及机构均默认为当前登录用户的机构信息
	 * @Author KangLG<2017年11月8日>
	 * @param request
	 * @param orderBaseBorrowDto
	 * @return
	 */
	@RequestMapping("saveOrg")
	@ResponseBody
	public RespDataObject<String> saveOrg(HttpServletRequest request, @RequestBody OrderBaseBorrowDto orderBaseBorrowDto) {
		orderBaseBorrowDto.setCooperativeAgencyId(this.getUserDto(request).getAgencyId());
		orderBaseBorrowDto.setChannelManagerUid(this.getUserDto(request).getAgencyChanlManUid());
		return this.save(request, orderBaseBorrowDto);
	}

	@RequestMapping("update")
	@ResponseBody
	public RespStatus update(HttpServletRequest request,
			@RequestBody OrderBaseBorrowDto orderBaseBorrowDto) {
		RespStatus respStatus = new RespStatus();
		try {
			if(isSubmit(orderBaseBorrowDto.getOrderNo(), "placeOrder")&&orderBaseBorrowDto.getIsChangLoan()!=1){
				RespHelper.setFailRespStatus(respStatus, "订单已提交审核");
				return respStatus;
			}
			// 获取用户信息
			UserDto userDto = getUserDto(request); 
			if(orderBaseBorrowDto.getUpdateUid()==null){
				orderBaseBorrowDto.setUpdateUid(userDto.getUid());
			}
			if(userDto.getAgencyId()>1 && orderBaseBorrowDto.getCooperativeAgencyId()==0){ //机构后台录单，合作机构必须是自己
				orderBaseBorrowDto.setCooperativeAgencyId(userDto.getAgencyId());
			}
			
			// 设置订单列表需要的字段
			if(StringUtils.isNotEmpty(orderBaseBorrowDto.getNotarialUid())){
				orderBaseBorrowDto.setNotarialName(CommonDataUtil.getUserDtoByUidAndMobile(orderBaseBorrowDto.getNotarialUid()).getName());
			}
			if(StringUtils.isNotEmpty(orderBaseBorrowDto.getFacesignUid())){
				orderBaseBorrowDto.setFacesignName(CommonDataUtil.getUserDtoByUidAndMobile(orderBaseBorrowDto.getFacesignUid()).getName());
			}
			orderBaseBorrowDto = getOrderBaseBorrowDto(orderBaseBorrowDto);
			if(userDto!=null){
				orderBaseBorrowDto.setCurrentHandlerUid(userDto.getUid());
			}
			orderBaseBorrowService.updateOrderBorrow(orderBaseBorrowDto);
			
			OrderListDto orderListDto=new OrderListDto();
			
			orderListDto.setOrderNo(orderBaseBorrowDto.getOrderNo());
			
			orderListDto.setCustomerName(orderBaseBorrowDto.getBorrowerName());
			
			orderListDto.setCityName(orderBaseBorrowDto.getCityName());
			
			orderListDto.setCityCode(orderBaseBorrowDto.getCityCode());
			
			orderListDto.setProductName(orderBaseBorrowDto.getProductName());
			
			orderListDto.setProductCode(orderBaseBorrowDto.getProductCode());
			
			orderListDto.setBorrowingAmount(orderBaseBorrowDto.getLoanAmount());
			
			if(orderBaseBorrowDto.getBorrowingDays()!=null){
				orderListDto.setBorrowingDay(orderBaseBorrowDto.getBorrowingDays());
			}
				
			orderListDto.setChannelManagerName(orderBaseBorrowDto.getChannelManagerName());
			
			orderListDto.setChannelManagerUid(orderBaseBorrowDto.getChannelManagerUid());
			
			orderListDto.setAcceptMemberUid(orderBaseBorrowDto.getAcceptMemberUid());

		    orderListDto.setAcceptMemberName(orderBaseBorrowDto.getAcceptMemberName()); 
			  
		    orderListDto.setCurrentHandlerUid(orderBaseBorrowDto.getCurrentHandlerUid());
		    
		    orderListDto.setCurrentHandler(orderBaseBorrowDto.getCurrentHandler());
		    
		    orderListDto.setPreviousHandlerUid(orderBaseBorrowDto.getPreviousHandlerUid());
		    
		    orderListDto.setNotarialUid(orderBaseBorrowDto.getNotarialUid());
		    
		    orderListDto.setFacesignUid(orderBaseBorrowDto.getFacesignUid());
		    
		    orderListDto.setCreateUid(orderBaseBorrowDto.getCreateUid());
		    
		    log.info(orderListDto.getCustomerName()+orderListDto.getCityName());
		    
			//同步数据到智能要件
			try {
				logger.info("update执行同步信贷件订单到智能要件");
				RespStatus elementResp = httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/element/list/saveCredit", orderListDto);
			} catch (Exception e) {
				e.printStackTrace(); 
			}
			
			
			
			RespHelper.setSuccessRespStatus(respStatus);
		} catch (Exception e) {
			log.error("更新订单借款信息失败",e);
			RespHelper.setFailRespStatus(respStatus, "更新订单借款信息失败");
			e.printStackTrace();
		}
		return respStatus;
	}

//	@RequestMapping("isChangLoan")
//	@ResponseBody
//	public RespDataObject<OrderBaseBorrowDto> isChangLoan(HttpServletRequest request,
//			@RequestBody OrderBaseBorrowDto orderBaseBorrow) {
//		RespDataObject<OrderBaseBorrowDto> resp = new RespDataObject<OrderBaseBorrowDto>();
//		try {
//			String orderNo = orderBaseBorrow.getOrderNo();
//			boolean changLoanFlag=isChangLoan(orderNo);
//			if(changLoanFlag){
//				orderBaseBorrow.setIsChangLoan(1);
//			}else{
//				orderBaseBorrow.setIsChangLoan(2);
//			} 
//			RespHelper.setSuccessDataObject(resp, orderBaseBorrow);
//		}catch (Exception e) {
//				log.error("查询是否畅贷失败",e);
//				RespHelper.setFailDataObject(resp, null, "查询是否畅贷失败");
//				e.printStackTrace();
//		}
//		return resp;
//	}
	
	@RequestMapping("query")
	@ResponseBody
	public RespDataObject<OrderBaseBorrowDto> query(HttpServletRequest request,
			@RequestBody OrderBaseBorrowDto orderBaseBorrow) {
		RespDataObject<OrderBaseBorrowDto> resp = new RespDataObject<OrderBaseBorrowDto>();
		try {
			OrderBaseBorrowDto orderBaseBorrowDto = orderBaseBorrowService
					.selectOrderBorrowByOrderNo(orderBaseBorrow.getOrderNo());
			orderBaseBorrowDto = getOrderBaseBorrowDto(orderBaseBorrowDto);
			RespHelper.setSuccessDataObject(resp, orderBaseBorrowDto);
		} catch (Exception e) {
			log.error("查询订单借款信息失败",e);
			RespHelper.setFailDataObject(resp, null, "查询订单借款信息失败");
			e.printStackTrace();
		}
		return resp;
	}

	@RequestMapping("allDetail")
	@ResponseBody
	public RespDataObject<Map<String, Object>> allDetail(
			HttpServletRequest request,
			@RequestBody OrderBaseBorrowDto orderBaseBorrow) {
		RespDataObject<Map<String, Object>> resp = new RespDataObject<Map<String, Object>>();
		try {
			String orderNo = orderBaseBorrow.getOrderNo();
			Map<String, Object> map = new HashMap<String, Object>();
			OrderBaseBorrowDto borrow = orderBaseBorrowService
					.selectOrderBorrowByOrderNo(orderNo);
			borrow = getOrderBaseBorrowDto(borrow);
			OrderListDto orderBase = orderBaseService.selectDetail(orderNo);
			OrderBaseCustomerDto customer = orderBaseCustomerService
					.selectOrderCustomerByOrderNo(orderNo);
			OrderBaseHouseDto house = orderBaseHouseService
					.selectOrderHouseByOrderNo(orderNo);
			List<OrderBusinfoDto> businfoList = orderBusinfoService
					.selectOrderBusinfoByOrderNo(orderNo);
			map.put("borrow", borrow);
			map.put("customer", customer);
			map.put("orderBase", orderBase);
			map.put("house", house);
			map.put("businfoList", businfoList);
			RespHelper.setSuccessDataObject(resp, map);
		} catch (Exception e) {
			log.error("查询订单所有详情信息失败",e);
			RespHelper.setFailDataObject(resp, null, "查询订单所有详情信息失败");
			e.printStackTrace();
		}
		return resp;
	}

	/**
	 * 提交审核校验订单
	 * @param orderListDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping("check")
	public RespDataObject<Integer> checkOrderSumbit(@RequestBody OrderListDto orderListDto){
		RespDataObject<Integer> resp = new RespDataObject<Integer>();
		String orderNo = orderListDto.getOrderNo();
		OrderListDto orderListdto = orderBaseService.selectDetail(orderNo);
		// 判断是否重复提交
		if (orderListdto!=null) {
			int productId = Integer.parseInt(orderListdto.getCityCode()+orderListdto.getProductCode());
			boolean flag = compareProcessAround(productId,orderListdto.getProcessId(),"placeOrder");
			if(!flag){
				RespHelper.setFailDataObject(resp, null, "已提交审核");
				return resp;
			}
		}
		Map<String,Object> map =orderBaseBorrowService.checkOrder(orderListdto.getProductCode(), orderListdto.getOrderNo(), orderListdto.getRelationOrderNo());
		if(MapUtils.getInteger(map, "code")!=0){
			RespHelper.setFailDataObject(resp,MapUtils.getInteger(map, "code"), MapUtils.getString(map, "msg"));
			return resp;
		}
		
		/*
		 * 征信，不参与编辑
		 * 1.畅贷关联订单   
		 */
		//非畅贷关联订单
		if(!("03".equals(orderListdto.getProductCode()) && StringUtils.isNotBlank(orderListdto.getRelationOrderNo()))){
			// 影像资料校验
			if(!check(orderListdto)){
				RespHelper.setFailDataObject(resp, 6, "请先上传必须的影像资料");
				return resp;
			}
			if(!"06".equals(orderListdto.getProductCode())&&!"07".equals(orderListdto.getProductCode())) {
				CreditDto creditDto =new CreditDto();
				creditDto.setOrderNo(orderListDto.getOrderNo());
				creditDto = httpUtil.getObject(
						Constants.LINK_CREDIT,
						"/credit/risk/ordercredit/v/detail", creditDto,
						CreditDto.class);
				if((creditDto==null)||(creditDto!=null&&creditDto.getIsFinish()!=1)){
					RespHelper.setFailDataObject(resp,7, "请完善征信信息");
					return resp;
				}
			}
		}else{//畅贷关联订单
			//已经面签判断面签资料必传
			OrderFlowDto orderFlow = new OrderFlowDto();
			orderFlow.setOrderNo(orderListdto.getOrderNo());
			orderFlow = orderFlowService
			.selectEndOrderFlow(orderFlow);
			if(orderFlow!=null&&orderFlow.getCurrentProcessId()!=null&&"facesign".equals(orderFlow.getCurrentProcessId())){
				if(orderBusinfoService.faceBusinfoCheck(orderListdto.getOrderNo(), orderListdto.getProductCode(),orderListdto.getAuditSort())){
					resp.setCode(RespStatusEnum.SUCCESS.getCode());
					resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
				}else{
					resp.setCode(RespStatusEnum.FAIL.getCode());
					resp.setMsg("面签资料不完整");
					return resp;
				}
			}
		}
		// 判断是否公证面签
//		NotarizationDto notarizationDto = new NotarizationDto();
//		notarizationDto.setOrderNo(getCreditOrderNo(orderListDto.getOrderNo()));
//		notarizationDto = httpUtil.getObject(
//				Constants.LINK_CREDIT,
//				"/credit/process/notarization/v/detail", notarizationDto,
//				NotarizationDto.class);
//		FaceSignDto faceSignDto = new FaceSignDto();
//		faceSignDto.setOrderNo(getCreditOrderNo(orderListDto.getOrderNo()));
//		faceSignDto = httpUtil.getObject(
//				Constants.LINK_CREDIT,
//				"/credit/process/facesign/v/detail", faceSignDto,
//				FaceSignDto.class);
//		if (notarizationDto == null||notarizationDto.getNotarizationTime()==null) {
//			RespHelper.setFailDataObject(resp, null, "请先进行公证");
//			return resp;
//		}
//		if(faceSignDto == null||faceSignDto.getFaceSignTime()==null){
//			RespHelper.setFailDataObject(resp, null, "请先进行面签");
//			return resp;
//		}
		// 退回重新提交
		OrderFlowDto flowDto = new OrderFlowDto();
		flowDto.setOrderNo(orderNo);
		OrderFlowDto orderFlow = orderFlowService
				.selectEndOrderFlow(flowDto);
		if (orderFlow!=null&&orderFlow.getBackReason() != null
				&& !orderFlow.getBackReason().equals("")){
			RespHelper.setSuccessDataObject(resp, 100);
		}
		
		RespHelper.setSuccessRespStatus(resp);
		return resp;
	}
	/**
	 * 提交审核
	 * 
	 * @param request
	 * @param orderListDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "submitAudit")
	public RespStatus submitAudit(HttpServletRequest request,
			@RequestBody OrderListDto orderListDto) {
		RespStatus resp = new RespStatus();
		try {
			String orderNo = orderListDto.getOrderNo();
			UserDto user = getUserDto(request);
			orderListDto.setCreateUid(user.getUid());
			orderListDto.setUpdateUid(user.getUid());
			orderListDto.setPreviousHandlerUid(user.getUid());
			orderListDto.setPreviousHandler(user.getName());
			String currentHandlerUid = orderListDto.getCurrentHandlerUid();  //分配订单员
			String receptionManagerUid = orderListDto.getReceptionManagerUid();  //还款专员
			log.info("订单分配员："+currentHandlerUid);
			OrderListDto orderListdto = orderBaseService.selectDetail(orderNo);
			// 判断是否重复提交
			if (orderListdto!=null) {
				int productId = Integer.parseInt(orderListdto.getCityCode()+orderListdto.getProductCode());
				boolean flag = compareProcessAround(productId,orderListdto.getProcessId(),"placeOrder");
				if(!flag){
					RespHelper.setFailRespStatus(resp, "已提交审核");
					return resp;
				}
			}
			// 影像资料校验
			/*
			 * 征信，不参与编辑
			 * 1.畅贷关联订单   
			 */
			if(!("03".equals(orderListdto.getProductCode()) && StringUtils.isNotBlank(orderListdto.getRelationOrderNo()))){
				 OrderListDto orderTemp = orderBaseService.selectDetail(orderNo);
					if(!check(orderTemp)){
						RespHelper.setFailRespStatus(resp, "请先上传必须的影像资料");
						return resp;
					}
					if(!"06".equals(orderListdto.getProductCode())&&!"07".equals(orderListdto.getProductCode())) {	
						CreditDto creditDto =new CreditDto();
						creditDto.setOrderNo(orderListDto.getOrderNo());
						creditDto = httpUtil.getObject(
								Constants.LINK_CREDIT,
								"/credit/risk/ordercredit/v/detail", creditDto,
								CreditDto.class);
						if((creditDto==null)||(creditDto!=null&&creditDto.getIsFinish()!=1)){
							RespHelper.setFailRespStatus(resp, "请完善征信信息");
							return resp;
						}
					}
			}else{//畅贷关联订单
				//已经面签判断面签资料必传
				OrderFlowDto orderFlow = new OrderFlowDto();
				orderFlow.setOrderNo(orderListdto.getOrderNo());
				orderFlow = orderFlowService
				.selectEndOrderFlow(orderFlow);
				if(orderFlow!=null&&orderFlow.getCurrentProcessId()!=null&&"facesign".equals(orderFlow.getCurrentProcessId())){
					if(orderBusinfoService.faceBusinfoCheck(orderListdto.getOrderNo(), orderListdto.getProductCode(),orderListdto.getAuditSort())){
						resp.setCode(RespStatusEnum.SUCCESS.getCode());
						resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
					}else{
						resp.setCode(RespStatusEnum.FAIL.getCode());
						resp.setMsg("面签资料不完整");
						return resp;
					}
				}
			}
			//订单信息校验
			Map<String,Object> map = orderBaseBorrowService.checkOrder(orderListdto.getProductCode(), orderListdto.getOrderNo(), orderListdto.getRelationOrderNo());
			if(MapUtils.getInteger(map, "code")!=0){
				RespHelper.setFailRespStatus(resp, MapUtils.getString(map, "msg"));
				return resp;
			}
			// 还款专员(机构置单)
			if(orderListdto.getAgencyId()>1 && StringUtils.isNotEmpty(MapUtils.getString(map, "foreclosureMemberUid", ""))){
				DistributionMemberDto distributionMemberDto = new DistributionMemberDto();
				distributionMemberDto.setOrderNo(orderNo);
				distributionMemberDto.setForeclosureMemberUid(MapUtils.getString(map, "foreclosureMemberUid"));
				httpUtil.getObject(Constants.LINK_CREDIT, "/credit/process/distributionMember/v/addAgency", distributionMemberDto, RespData.class);
			}
			
			// 退回重新提交
			OrderFlowDto flowDto = new OrderFlowDto();
			flowDto.setOrderNo(orderNo);
			OrderFlowDto orderFlow = orderFlowService
					.selectEndOrderFlow(flowDto);
			if (orderFlow!=null&&orderFlow.getBackReason() != null
					&& !orderFlow.getBackReason().equals("")&&orderFlow.getIsNewWalkProcess()==2) {// 退回重新提交(并且不重新走流程时)
				orderListDto = orderBaseService.selectDetail(orderListDto.getOrderNo());
				String productIdStr = orderListDto.getCityCode()
						+ orderListDto.getProductCode();
				int productId = Integer.parseInt(productIdStr);
				//获取节点名称
				//更新orderList
				orderListDto.setCurrentHandlerUid(orderFlow.getHandleUid());
				UserDto userDto=CommonDataUtil.getUserDtoByUidAndMobile(orderFlow.getHandleUid());
				if(userDto!=null){
					orderListDto.setCurrentHandler(userDto.getName());
				}
				orderListDto.setState(getProcessName(productId,
						orderFlow.getCurrentProcessId()));
				orderListDto.setProcessId(orderFlow.getCurrentProcessId());
				//orderBaseBorrowService.reSubmit(orderListDto);
				//录入流水
				OrderFlowDto orderFlowDto = new OrderFlowDto();
				orderFlowDto.setOrderNo(orderNo);
				orderFlowDto.setHandleUid(user.getUid());
				orderFlowDto.setNextProcessId(orderFlow.getCurrentProcessId());
				orderFlowDto.setCurrentProcessId("placeOrder");
				orderFlowDto.setHandleName(user.getName());
				orderFlowDto.setNextProcessName(getProcessName(productId,
						orderFlow.getCurrentProcessId()));
				goNextNode(orderFlowDto, orderListDto);
			} else {//提交审核
				threeSubmit(request, orderListdto,currentHandlerUid,receptionManagerUid);
			}
			//保存人脸识别初始化数据
//			OrderBaseCustomerDto orderBaseCustomerDto = orderBaseCustomerService.selectOrderCustomerByOrderNo(orderNo);
//			insertFacesignRecognition(orderBaseCustomerDto);
//			String phoneNumber = null;
//			try {
//				//注册蓝蜗牛
//				OrderBaseBorrowDto orderBaseBorrowDto = orderBaseBorrowService.selectOrderBorrowByOrderNo(orderNo);
//				Map<String,Object> m = new HashMap<String, Object>();
//				phoneNumber = orderBaseBorrowDto.getPhoneNumber();
//				m.put("mobile", phoneNumber);
//				RespDataObject<Map<String,Object>> res = httpUtil.getRespDataObject(Constants.LINK_ANJBO_APP_URL, "/mortgage/snail/user/creditReg",  m, Map.class);
//				log.info("注册结果"+res.getCode());
//			} catch (Exception e) {
//				log.error("注册蓝蜗牛失败,手机号："+phoneNumber);
//				e.printStackTrace();
//			}
			RespHelper.setSuccessRespStatus(resp);
		} catch (Exception e) {
			e.printStackTrace();
			RespHelper.setFailRespStatus(resp, RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}
	
	/**
	 * 三种提交审核（一、债务置换贷款，畅贷一起提交审核。二、债务置换贷款提交审核。三、畅贷提交审核）
	 * @param request
	 * @param orderListDto
	 */
	public void threeSubmit(HttpServletRequest request,OrderListDto orderListdto,String currentHandlerUid,String receptionManagerUid){
		
		// 判断是否公证面签
//		NotarizationDto notarizationDto = new NotarizationDto();
//		notarizationDto.setOrderNo(getCreditOrderNo(orderListdto.getOrderNo()));
//		notarizationDto = httpUtil.getObject(
//				Constants.LINK_CREDIT,
//				"/credit/process/notarization/v/detail", notarizationDto,
//				NotarizationDto.class);
		if(StringUtils.isBlank(currentHandlerUid)){
			currentHandlerUid = orderListdto.getDistributionOrderUid();
		}
		String orderNo = orderListdto.getOrderNo();
		UserDto user = getUserDto(request);
		OrderListDto orderListDto = new OrderListDto();
		orderListDto.setOrderNo(orderNo);
		orderListDto.setUpdateUid(user.getUid());
		orderListdto.setCurrentHandlerUid(currentHandlerUid);//订单分配员
			// 提交审核
			// 设置原贷款银行名称或者原贷款地点
		//三种提交审核（一、债务置换贷款，畅贷一起提交审核。二、债务置换贷款提交审核。三、畅贷提交审核）
//		OrderBaseBorrowDto orderBaseBorrowDto = orderBaseBorrowService
//				.selectOrderBorrowByOrderNo(getCreditOrderNo(orderListDto.getOrderNo()));
//		if (orderBaseBorrowDto.getIsOldLoanBank() != null
//				&& orderBaseBorrowDto.getIsOldLoanBank()==1) {
//			orderListDto.setAppShowValue2(CommonDataUtil.getBankNameById(orderBaseBorrowDto.getOldLoanBankNameId()).getName()+ "-"+ CommonDataUtil.getBankNameById(orderBaseBorrowDto.getOldLoanBankSubNameId()).getName());
//		} else {
//			orderListDto.setAppShowValue2(orderBaseBorrowDto.getOldLoanBankName());
//		}
//		OrderListDto orderTemp = orderListDto;
//		orderTemp.setOrderNo(getCreditOrderNo(orderListDto.getOrderNo()));
//		orderTemp.setReceptionManagerUid(receptionManagerUid); //指派还款专员
//		int changLoanCount = orderBaseBorrowService.submitAudit(orderTemp);
//		String[] orders = new String[2];
//		orders[0]=orderListdto.getOrderNo();
//		orders[1]=null;
//		if(getChangLoanOrderNo(orderListdto.getOrderNo())!=null&&changLoanCount==0){
//			orders[1]=getChangLoanOrderNo(orderListdto.getOrderNo());
//		}
		OrderListDto listDto = orderBaseService.selectDetail(orderNo);
		
		if(StringUtils.isEmpty(listDto.getContractNo())) {
			String contractNo = "("+DateUtil.getYear(new Date())+") 借字第"+PinYin4JUtil.getFirstSpell(listDto.getCustomerName()) + String.format("%02d", DateUtil.getMonth(new Date()))  + DateUtil.getDay(new Date()) +"号 ";
			listDto.setContractNo(contractNo);
			OrderListDto tempOrderList = orderBaseService.selectOrderListByContractNo(listDto);
			if(tempOrderList == null) {
				orderListDto.setContractNo(contractNo);
			}else if(tempOrderList.getOrderNo().equals(orderNo)) {
				orderListDto.setContractNo(tempOrderList.getContractNo());
			}else if(tempOrderList.getContractNo().equals(contractNo)){
				orderListDto.setContractNo(contractNo + String.format("%03d",1));
			}else {
				int i = 1;
				while (!(contractNo + String.format("%03d",i)).equals(tempOrderList.getContractNo())){
					 i++;
				}
				i++;
				orderListDto.setContractNo(contractNo + String.format("%03d",i));
			}
		}
		
		OrderFlowDto orderFlowDto = new OrderFlowDto();
		orderFlowDto.setOrderNo(orderNo);
		orderFlowDto.setHandleUid(user.getUid());
		orderFlowDto.setCurrentProcessId("placeOrder");
		orderFlowDto.setHandleName(user.getName());
		orderListDto.setReceptionManagerUid(receptionManagerUid);
		orderListDto.setDistributionOrderUid(currentHandlerUid);
//		if(listDto.getAuditSort()==1){
			orderFlowDto.setNextProcessId("managerAudit");
			orderFlowDto.setNextProcessName("分配订单");
			orderListDto.setProcessId("managerAudit");
			orderListDto.setState("待分配订单");
			orderListDto.setCurrentHandlerUid(currentHandlerUid);
			orderListDto.setCurrentHandler(CommonDataUtil.getUserDtoByUidAndMobile(currentHandlerUid).getName());
//		}else{
//			if("04".equals(listDto.getProductCode())){
//				orderFlowDto.setNextProcessId("facesign");
//				orderFlowDto.setNextProcessName("面签");
//				orderListDto.setProcessId("facesign");
//				orderListDto.setState("待面签");
//				orderListDto.setCurrentHandlerUid(listDto.getFacesignUid());
//				orderListDto.setCurrentHandler(CommonDataUtil.getUserDtoByUidAndMobile(listDto.getFacesignUid()).getName());
//			}else{
//				orderFlowDto.setNextProcessId("notarization");
//				orderFlowDto.setNextProcessName("公证");
//				orderListDto.setProcessId("notarization");
//				orderListDto.setState("待公证");
//				orderListDto.setCurrentHandlerUid(listDto.getNotarialUid());
//				orderListDto.setCurrentHandler(CommonDataUtil.getUserDtoByUidAndMobile(listDto.getNotarialUid()).getName());
//			}
//		}
		
		OrderBaseBorrowDto baseBorrowDto = orderBaseBorrowService
				.selectOrderBorrowByOrderNo(orderNo);
		if ("1".equals(baseBorrowDto.getIsOldLoanBank())) {
			orderListDto.setAppShowValue2(CommonDataUtil.getBankNameById(baseBorrowDto.getOldLoanBankNameId()).getName()+ "-"+ CommonDataUtil.getBankNameById(baseBorrowDto.getOldLoanBankSubNameId()).getName());
		} else {
			orderListDto.setAppShowValue2(baseBorrowDto
					.getOldLoanBankName());
		}
//				if(notarizationDto!=null){
//					orderListDto
//					.setAppShowValue1(DateUtil.getDateByFmt(
//							notarizationDto.getEstimatedTime(),
//							DateUtil.FMT_TYPE2));
//				}
		orderListDto.setNotarialUid("");
		orderListDto.setFacesignUid("");
		orderListDto.setRelationOrderNo(null);
		goNextNode(orderFlowDto, orderListDto);
		orderBaseBorrowService.addRiskList(orderNo);
		//==============发送短信Start===================
//		String ipWhite = ConfigUtil.getStringValue(Constants.BASE_AMS_IPWHITE,ConfigUtil.CONFIG_BASE); //ip
//		String ProductName="债务置换";
//		if(listDto!=null && !"01".equals(listDto.getProductCode()) && !"02".equals(listDto.getProductCode())){
//			ProductName=listDto.getProductName();
//		}
//		AmsUtil.smsSend(CommonDataUtil.getUserDtoByUidAndMobile(currentHandlerUid).getMobile(), ipWhite, Constants.SMS_TEMPLATE_MANAGER, ProductName,listDto.getCustomerName(),listDto.getBorrowingAmount(),"分配订单");
		
	}
	
	/**
	 * 查询统计报表列表
	 * 
	 * @param request
	 * @param orderBaseBorrowDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/list")
	public RespDataObject<List<OrderBaseBorrowDto>> queryOrderList(
			HttpServletRequest request,
			@RequestBody OrderBaseBorrowDto orderBaseBorrowDto) {
		RespDataObject<List<OrderBaseBorrowDto>> resp = new RespDataObject<List<OrderBaseBorrowDto>>();
		try {
			List<OrderBaseBorrowDto> list = orderBaseBorrowService
					.selectOrderBorrowList(orderBaseBorrowDto);
			RespHelper.setSuccessDataObject(resp, list);
		} catch (Exception e) {
			RespHelper.setFailDataObject(resp, null, "查询借款信息列表失败");
			e.printStackTrace();
		}
		return resp;
	}

	/**
	 * 查询统计报表数量
	 * 
	 * @param request
	 * @param orderBaseBorrowDto
	 * @return
	 */
	@RequestMapping("count")
	@ResponseBody
	public RespDataObject<Integer> queryOrderCount(HttpServletRequest request,
			@RequestBody OrderBaseBorrowDto orderBaseBorrowDto) {
		RespDataObject<Integer> resp = new RespDataObject<Integer>();
		try {
			int count = orderBaseBorrowService
					.selectOrderBorrowCount(orderBaseBorrowDto);
			RespHelper.setSuccessDataObject(resp, count);
		} catch (Exception e) {
			RespHelper.setFailDataObject(resp, null, "查询借款信息列表条数失败");
			e.printStackTrace();
		}
		return resp;
	}
	
	/**
	 * 影像资料校验
	 * @param orderListDto
	 * @return
	 */
	public boolean check(OrderListDto orderListDto){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("productCode", orderListDto.getProductCode());
		map.put("auditSort", orderListDto.getAuditSort());
		map.put("orderNo", orderListDto.getOrderNo());
		//不公证时，不需要传公证委托书 
		if(orderListDto.getAuditSort()==2){
			NotarizationDto obj = new NotarizationDto();
			obj.setOrderNo(orderListDto.getOrderNo());
			obj.setRelationOrderNo(orderListDto.getRelationOrderNo());
			obj = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/process/notarization/v/init", obj, NotarizationDto.class);
			map.put("notarizationType", obj.getNotarizationType());
		}else{
			map.put("notarizationType", null);
		}
		int a = orderBusinfoTypeService.mustBusInfoCount(map);
		int b = orderBusinfoService.hasBusInfoCount(map);
		log.info("需要上传的个数："+a);
		log.info("已经上传的个数："+b);
		if(a!=b){
			return false;
		}
		return true;
	}
	
	
	/**
	 * 是否有畅贷
	 * @param orderNo
	 * @return
	 */
	public int hasChangLoan(String orderNo){
		OrderBaseBorrowDto orderBaseBorrowDto = orderBaseBorrowService.selectOrderBorrowByOrderNo(orderNo);
		if(orderBaseBorrowDto!=null && orderBaseBorrowDto.getOrderBaseBorrowRelationDto()!=null&&orderBaseBorrowDto.getOrderBaseBorrowRelationDto().size()>0){
			return 1;
		}
		return 2;
	}
	
	/**
	 * 是否畅贷订单号
	 * @param orderNo
	 * @return
	 */
//	public boolean isChangLoan(String orderNo) {
//		OrderBaseBorrowRelationDto orderBaseBorrowRelationDto = orderBaseBorrowRelationService
//				.selectRelationByOrderNo(orderNo);
//		if (orderBaseBorrowRelationDto != null) {
//			return true;
//		} else {
//			return false;
//		}
//	}
	
	/**
	 * 获取畅贷订单号
	 * @param orderNo
	 * @return
	 */
	public String getChangLoanOrderNo(String orderNo){
		OrderBaseBorrowDto orderBaseBorrowDto = orderBaseBorrowService.selectOrderBorrowByOrderNo(orderNo);
		if(orderBaseBorrowDto!=null && orderBaseBorrowDto.getOrderBaseBorrowRelationDto()!=null&&orderBaseBorrowDto.getOrderBaseBorrowRelationDto().size()>0){
			return orderBaseBorrowDto.getOrderBaseBorrowRelationDto().get(0).getOrderNo();
		}
		return null;
	}
	
	/**
	 * 获取债务置换贷款订单号
	 * @param orderNo
	 * @return
	 */
	public String getLoanOrderNo(String orderNo){
		OrderBaseBorrowRelationDto orderBaseBorrowRelationDto = orderBaseBorrowRelationService.selectRelationByOrderNo(orderNo);
		if(orderBaseBorrowRelationDto!=null && orderBaseBorrowRelationDto.getRelationOrderNo()!=null){
			return orderBaseBorrowRelationDto.getRelationOrderNo();
		}
		return orderNo;
	}

	/**
	 * 给订单列表字段赋值
	 * 
	 * @param orderBaseBorrowDto
	 * @return
	 */
	public OrderBaseBorrowDto getOrderBaseBorrowDto(OrderBaseBorrowDto orderBaseBorrowDto){
		
		if(null != orderBaseBorrowDto.getChannelManagerUid()){
			UserDto channelManagerDto = CommonDataUtil.getUserDtoByUidAndMobile(orderBaseBorrowDto.getChannelManagerUid());
			orderBaseBorrowDto.setChannelManagerName(channelManagerDto.getName());
			orderBaseBorrowDto.setCurrentHandler(channelManagerDto.getName());
		}
		orderBaseBorrowDto.setAcceptMemberName(CommonDataUtil.getUserDtoByUidAndMobile(orderBaseBorrowDto.getAcceptMemberUid()).getName());
		if(StringUtils.isNotEmpty(orderBaseBorrowDto.getForeclosureMemberUid())){
			orderBaseBorrowDto.setForeclosureMemberName(CommonDataUtil.getUserDtoByUidAndMobile(orderBaseBorrowDto.getForeclosureMemberUid()).getName());
		}
		orderBaseBorrowDto.setNotarialName(CommonDataUtil.getUserDtoByUidAndMobile(orderBaseBorrowDto.getNotarialUid()).getName());
		orderBaseBorrowDto.setFacesignName(CommonDataUtil.getUserDtoByUidAndMobile(orderBaseBorrowDto.getFacesignUid()).getName());
		orderBaseBorrowDto.setElementName(CommonDataUtil.getUserDtoByUidAndMobile(orderBaseBorrowDto.getElementUid()).getName());
		
//		List<UserDto> userList = getUserDtoList();
//		for (UserDto userDto : userList) {
//			//渠道经理
//			if(null!=orderBaseBorrowDto.getChannelManagerUid()&&userDto.getUid().equals(orderBaseBorrowDto.getChannelManagerUid())){
//				orderBaseBorrowDto.setChannelManagerName(userDto.getName());
//				orderBaseBorrowDto.setCurrentHandler(userDto.getName());
//			}
//			//受理员
//			if(userDto.getUid().equals(orderBaseBorrowDto.getAcceptMemberUid())){
//				orderBaseBorrowDto.setAcceptMemberName(userDto.getName());
//			}
//			//还款专员
//			if(StringUtils.isNotEmpty(orderBaseBorrowDto.getForeclosureMemberUid()) && orderBaseBorrowDto.getForeclosureMemberUid().equals(userDto.getUid())){
//				orderBaseBorrowDto.setForeclosureMemberName(userDto.getName());
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
		orderBaseBorrowDto.setCityName(CommonDataUtil.getDictDtoByTypeAndCode("bookingSzAreaOid", orderBaseBorrowDto.getCityCode()).getName());
		
//		List<DictDto> dictList = getDictDtoByType("bookingSzAreaOid");
//		for (DictDto dictDto : dictList) {
//			if(dictDto.getCode().equals(orderBaseBorrowDto.getCityCode())){
//				orderBaseBorrowDto.setCityName(dictDto.getName());
//				break;
//			}
//		}
		
		
		//获取产品名称
//		List<ProductDto> prductList = getProductDtos();
//		for (ProductDto productDto : prductList) {
//			if(productDto.getProductCode().equals(orderBaseBorrowDto.getProductCode())){
//				orderBaseBorrowDto.setProductName(productDto.getProductName());
//				break;
//			}
//		}
		if(StringUtils.isBlank(orderBaseBorrowDto.getProductName()) && StringUtils.isNotEmpty(orderBaseBorrowDto.getCityCode()) && StringUtils.isNotEmpty(orderBaseBorrowDto.getProductCode())){
			orderBaseBorrowDto.setProductName(CommonDataUtil.getProductDtoByProductId(orderBaseBorrowDto.getCityCode() + orderBaseBorrowDto.getProductCode()).getProductName());
		}
		
		//获取合作机构名称
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("agencyId", orderBaseBorrowDto.getCooperativeAgencyId());
		HttpUtil httpUtil = new HttpUtil();
		AgencyDto agencyDto = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/customer/agency/v/getAgencyDto", map, AgencyDto.class);
		if(agencyDto!=null){
			orderBaseBorrowDto.setCooperativeAgencyName(agencyDto.getName());
		}
		//受理员机构名称
		map.put("agencyId", orderBaseBorrowDto.getAgencyId());
		agencyDto = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/customer/agency/v/getAgencyDto", map, AgencyDto.class);
		if(agencyDto!=null){
			orderBaseBorrowDto.setAgencyName(agencyDto.getName());
		}
		
//		if(orderBaseBorrowDto.getRiskGradeId()!=null&&orderBaseBorrowDto.getRiskGradeId()==0){
//			orderBaseBorrowDto.setRiskGrade("其他");
//		}else{
//			System.out.println("收费类型id"+orderBaseBorrowDto.getRiskGradeId());
//			DictDto dictDto = CommonDataUtil.getDictDtoByTypeAndCode("riskControl", orderBaseBorrowDto.getRiskGradeId());
//			orderBaseBorrowDto.setRiskGrade(dictDto.getName());
//		}
//		
//		//畅贷
//		if(orderBaseBorrowDto.getOrderBaseBorrowRelationDto()!=null&&orderBaseBorrowDto.getOrderBaseBorrowRelationDto().size()>0){
//			for (OrderBaseBorrowRelationDto orderBaseBorrowRelation : orderBaseBorrowDto.getOrderBaseBorrowRelationDto()) {
//				if(orderBaseBorrowRelation.getRiskGradeId()!=null&&orderBaseBorrowRelation.getRiskGradeId()==0){
//					orderBaseBorrowRelation.setRiskGrade("其他");
//				}else{
//					DictDto dictDto = CommonDataUtil.getDictDtoByTypeAndCode("riskControl", orderBaseBorrowRelation.getRiskGradeId());
//					orderBaseBorrowRelation.setRiskGrade(dictDto.getName());
//				}
//			}
//		}
		
		//收费类型名称
		List<DictDto> dicts = CommonDataUtil.getDictDtoByType("riskControl");
		if(dicts!=null&&dicts.size()>0){
			for (DictDto dictDto : dicts) {
				if(dictDto.getCode().equals(String.valueOf(orderBaseBorrowDto.getRiskGradeId()))){
					orderBaseBorrowDto.setRiskGrade(dictDto.getName());
				}
				if(orderBaseBorrowDto.getRiskGradeId()!=null&&orderBaseBorrowDto.getRiskGradeId()==0){
					orderBaseBorrowDto.setRiskGrade("其他");
				}
				//畅贷收费类型名称
//				if(orderBaseBorrowDto.getOrderBaseBorrowRelationDto()!=null&&orderBaseBorrowDto.getOrderBaseBorrowRelationDto().size()>0){
//					for (OrderBaseBorrowRelationDto orderBaseBorrowRelation : orderBaseBorrowDto.getOrderBaseBorrowRelationDto()) {
//						if(dictDto.getCode().equals(String.valueOf(orderBaseBorrowRelation.getRiskGradeId()))){
//							orderBaseBorrowRelation.setRiskGrade(dictDto.getName());
//						}
//						if(orderBaseBorrowRelation.getRiskGradeId()!=null&&orderBaseBorrowRelation.getRiskGradeId()==0){
//							orderBaseBorrowRelation.setRiskGrade("其他");
//						}
//					}
//				}
			}
		}
		//获取银行支行名称
//		List<BankDto> bankList = getAllBankList();
//		List<SubBankDto> subBankList = getAllSubBankList();
//		if((orderBaseBorrowDto.getIsOldLoanBank()!=null&&orderBaseBorrowDto.getIsOldLoanBank()==1)||(orderBaseBorrowDto.getIsLoanBank()!=null&&orderBaseBorrowDto.getIsLoanBank()==1)
//				||(orderBaseBorrowDto.getIsRebate()!=null&&orderBaseBorrowDto.getIsRebate()==1)){
			if(orderBaseBorrowDto.getIsOldLoanBank() != null && orderBaseBorrowDto.getIsOldLoanBank()==1){
				orderBaseBorrowDto.setOldLoanBankName(CommonDataUtil.getBankNameById(orderBaseBorrowDto.getOldLoanBankNameId()).getName());
				orderBaseBorrowDto.setOldLoanBankSubName(CommonDataUtil.getSubBankNameById(orderBaseBorrowDto.getOldLoanBankSubNameId()).getName());
			}
			if(orderBaseBorrowDto.getIsLoanBank() != null && orderBaseBorrowDto.getIsLoanBank()==1){
				orderBaseBorrowDto.setLoanBankName(CommonDataUtil.getBankNameById(orderBaseBorrowDto.getLoanBankNameId()).getName());
				orderBaseBorrowDto.setLoanSubBankName(CommonDataUtil.getSubBankNameById(orderBaseBorrowDto.getLoanSubBankNameId()).getName());
			}
			if(orderBaseBorrowDto.getIsRebate() != null && orderBaseBorrowDto.getIsRebate()==1){
				orderBaseBorrowDto.setRebateBank(CommonDataUtil.getBankNameById(orderBaseBorrowDto.getRebateBankId()).getName());
				orderBaseBorrowDto.setRebateSubBank(CommonDataUtil.getSubBankNameById(orderBaseBorrowDto.getRebateBankSubId()).getName());
			}
			//查询银行名称
//			for (BankDto bankDto : bankList) {
//				if(orderBaseBorrowDto.getOldLoanBankNameId()!=null&&bankDto.getId()==orderBaseBorrowDto.getOldLoanBankNameId()&&orderBaseBorrowDto.getIsOldLoanBank()!=null&&orderBaseBorrowDto.getIsOldLoanBank()==1){
//					orderBaseBorrowDto.setOldLoanBankName(bankDto.getName());
//				}
//				if(orderBaseBorrowDto.getLoanBankNameId()!=null&&bankDto.getId()==orderBaseBorrowDto.getLoanBankNameId()&&orderBaseBorrowDto.getIsLoanBank()!=null&&orderBaseBorrowDto.getIsLoanBank()==1){
//					orderBaseBorrowDto.setLoanBankName(bankDto.getName());
//				}
//				if(orderBaseBorrowDto.getRebateBankId()!=null&&bankDto.getId()==orderBaseBorrowDto.getRebateBankId()&&orderBaseBorrowDto.getIsRebate()!=null&&orderBaseBorrowDto.getIsRebate()==1){
//					orderBaseBorrowDto.setRebateBank(bankDto.getName());
//				}
//			}
//			for (SubBankDto subBankDto : subBankList) {
//				if(orderBaseBorrowDto.getOldLoanBankSubNameId()!=null&&subBankDto.getId()==orderBaseBorrowDto.getOldLoanBankSubNameId()&&orderBaseBorrowDto.getIsOldLoanBank()!=null&&orderBaseBorrowDto.getIsOldLoanBank()==1){
//					orderBaseBorrowDto.setOldLoanBankSubName(subBankDto.getName());
//				}
//				if(orderBaseBorrowDto.getLoanSubBankNameId()!=null&&subBankDto.getId()==orderBaseBorrowDto.getLoanSubBankNameId()&&orderBaseBorrowDto.getIsLoanBank()!=null&&orderBaseBorrowDto.getIsLoanBank()==1){
//					orderBaseBorrowDto.setLoanSubBankName(subBankDto.getName());
//				}
//				if(orderBaseBorrowDto.getRebateBankSubId()!=null&&subBankDto.getId()==orderBaseBorrowDto.getRebateBankSubId()&&orderBaseBorrowDto.getIsRebate()!=null&&orderBaseBorrowDto.getIsRebate()==1){
//					orderBaseBorrowDto.setRebateSubBank(subBankDto.getName());
//				}
//			}
//		}
		//1：费用前置  2：费用后置
		if(orderBaseBorrowDto.getPaymentMethod()==1){
			orderBaseBorrowDto.setPaymentMethodName("费用前置");
		}else if(orderBaseBorrowDto.getPaymentMethod()==2){
			orderBaseBorrowDto.setPaymentMethodName("费用后置");
		}
		return orderBaseBorrowDto;
	}
		
}
