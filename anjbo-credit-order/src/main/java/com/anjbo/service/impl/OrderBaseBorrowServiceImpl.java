package com.anjbo.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.anjbo.bean.element.DocumentsDto;
import com.anjbo.bean.order.OrderBaseBorrowDto;
import com.anjbo.bean.order.OrderBaseBorrowRelationDto;
import com.anjbo.bean.order.OrderBaseCustomerBorrowerDto;
import com.anjbo.bean.order.OrderBaseCustomerDto;
import com.anjbo.bean.order.OrderBaseCustomerGuaranteeDto;
import com.anjbo.bean.order.OrderBaseHouseDto;
import com.anjbo.bean.order.OrderBaseHouseLendingDto;
import com.anjbo.bean.order.OrderBaseHousePropertyDto;
import com.anjbo.bean.order.OrderBaseHousePropertyPeopleDto;
import com.anjbo.bean.order.OrderBaseHousePurchaserDto;
import com.anjbo.bean.order.OrderBaseReceivableForDto;
import com.anjbo.bean.order.OrderListDto;
import com.anjbo.bean.risk.CreditDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.Constants;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.dao.OrderBaseBorrowMapper;
import com.anjbo.dao.OrderBaseBorrowRelationMapper;
import com.anjbo.dao.OrderBaseCustomerBorrowerMapper;
import com.anjbo.dao.OrderBaseCustomerGuaranteeMapper;
import com.anjbo.dao.OrderBaseCustomerMapper;
import com.anjbo.dao.OrderBaseHouseLendingMapper;
import com.anjbo.dao.OrderBaseHouseMapper;
import com.anjbo.dao.OrderBaseHousePropertyMapper;
import com.anjbo.dao.OrderBaseHousePropertyPeopleMapper;
import com.anjbo.dao.OrderBaseHousePurchaserMapper;
import com.anjbo.dao.OrderBaseMapper;
import com.anjbo.dao.OrderBaseReceivableForMapper;
import com.anjbo.dao.OrderFlowMapper;
import com.anjbo.service.OrderBaseBorrowService;
import com.anjbo.utils.AmsUtil;
import com.anjbo.utils.CommonDataUtil;
import com.anjbo.utils.ConfigUtil;
import com.anjbo.utils.HttpUtil;
import com.anjbo.utils.StringUtil;
import com.anjbo.utils.UidUtil;

@Service
public class OrderBaseBorrowServiceImpl implements OrderBaseBorrowService {

	@Resource
	private OrderBaseBorrowMapper orderBaseBorrowMapper;
	@Resource
	private OrderBaseHouseMapper orderBaseHouseMapper;
	@Resource
	private OrderBaseHousePropertyPeopleMapper orderBaseHousePropertyPeopleMapper;
	@Resource
	private OrderBaseHousePropertyMapper orderBaseHousePropertyMapper;
	@Resource
	private OrderBaseHousePurchaserMapper orderBaseHousePurchaserMapper;
	@Resource
	private OrderBaseCustomerMapper orderBaseCustomerMapper;
	@Resource
	private OrderBaseCustomerBorrowerMapper orderBaseCustomerBorrowerMapper;
	@Resource
	private OrderBaseCustomerGuaranteeMapper orderBaseCustomerGuaranteeMapper;
	@Resource
	private OrderBaseMapper orderBaseMapper;
	@Resource
	private OrderBaseReceivableForMapper orderBaseReceivableForMapper;
	@Resource
	private OrderBaseBorrowRelationMapper orderBaseBorrowRelationMapper;
	@Resource
	private OrderFlowMapper orderFlowMapper;
	@Resource
	private OrderBaseHouseLendingMapper orderBaseHouseLendingMapper;
	
	HttpUtil httpUtil = new HttpUtil();
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public String saveOrderBorrow(OrderBaseBorrowDto orderBaseBorrowDto) {
		String orderNo = "";
		if (StringUtils.isNotBlank(orderBaseBorrowDto.getOrderNo())){
			orderNo = orderBaseBorrowDto.getOrderNo();
		} else {
			orderNo = UidUtil.generateOrderId();
		}
		if(StringUtils.isBlank(orderBaseBorrowDto.getSource())){
		    orderBaseBorrowDto.setSource("系统提单");
		}

		orderBaseBorrowDto.setOrderNo(orderNo);
//		orderBaseBorrowDto.setState("待公证/待面签/待提单");
		if(orderBaseBorrowDto.getAuditSort()==2 && "04".equals(orderBaseBorrowDto.getProductCode())){
			orderBaseBorrowDto.setState("待面签");
			orderBaseBorrowDto.setProcessId("facesign");
		}else if(orderBaseBorrowDto.getAuditSort()==2){
			orderBaseBorrowDto.setState("待公证");
			orderBaseBorrowDto.setProcessId("notarization");
			
			String ipWhite = ConfigUtil.getStringValue(Constants.BASE_AMS_IPWHITE,ConfigUtil.CONFIG_BASE); //ip
			String ProductName="债务置换";
			if(orderBaseBorrowDto!=null && !"01".equals(orderBaseBorrowDto.getProductCode()) && !"02".equals(orderBaseBorrowDto.getProductCode())){
				ProductName=orderBaseBorrowDto.getProductName();
			}
			UserDto nextUser = CommonDataUtil.getUserDtoByUidAndMobile(orderBaseBorrowDto.getNotarialUid());
			//发送给公证操作人
			AmsUtil.smsSend(nextUser.getMobile(), ipWhite, Constants.SMS_TEMPLATE_PROCESS, ProductName,orderBaseBorrowDto.getBorrowerName(),orderBaseBorrowDto.getLoanAmount(),"公证");
		}else{
			orderBaseBorrowDto.setState("待提单");
			orderBaseBorrowDto.setProcessId("placeOrder");
		}
		saveOrUpdateOrderList(orderBaseBorrowDto, "save");
		if(StringUtils.isBlank(orderBaseBorrowDto.getRelationOrderNo())){
			// 保存客户信息
			OrderBaseCustomerDto customer = new OrderBaseCustomerDto();
			customer.setCreateUid(orderBaseBorrowDto.getCreateUid());
			customer.setOrderNo(orderNo);
			customer.setCustomerName(orderBaseBorrowDto.getBorrowerName());
			customer.setCustomerBodiesState("健康");
			customer.setCustomerMarriageState("已婚有子女");
			customer.setCustomerCardType("二代身份证");
			customer.setCustomerNationsType("汉族");
			customer.setCustomerStage("购房、装修");
			orderBaseCustomerMapper.saveOrderCustomer(customer);
			// 保存房产交易信息
			OrderBaseHouseDto house = new OrderBaseHouseDto();
			house.setCreateUid(orderBaseBorrowDto.getCreateUid());
			house.setOrderNo(orderNo);
			orderBaseHouseMapper.saveOrderHouse(house);
			//产权人信息
			OrderBaseHousePropertyPeopleDto housePropertyPeopleDto = new OrderBaseHousePropertyPeopleDto();
			housePropertyPeopleDto.setCreateUid(orderBaseBorrowDto.getCreateUid());
			housePropertyPeopleDto.setOrderNo(orderNo);
	        housePropertyPeopleDto.setPropertyMarriageState("已婚有子女");
	        housePropertyPeopleDto.setPropertyCardType("二代身份证");
			orderBaseHousePropertyPeopleMapper.saveOrderPropertyPeople(housePropertyPeopleDto);
			//房产信息
			OrderBaseHousePropertyDto orderBaseHousePropertyDto = new OrderBaseHousePropertyDto();
			orderBaseHousePropertyDto.setCreateUid(orderBaseBorrowDto.getCreateUid());
			orderBaseHousePropertyDto.setOrderNo(orderNo);
			orderBaseHousePropertyDto.setCity(orderBaseBorrowDto.getCityCode());
			orderBaseHousePropertyMapper.saveOrderHouseProperty(orderBaseHousePropertyDto);
			// 交易类初始化买房人信息
			if("01".equals(orderBaseBorrowDto.getProductCode())){
				// 买房人信息
				OrderBaseHousePurchaserDto orderBaseHousePurchaserDto = new OrderBaseHousePurchaserDto();
				orderBaseHousePurchaserDto.setCreateUid(orderBaseBorrowDto.getCreateUid());
				orderBaseHousePurchaserDto.setOrderNo(orderNo);
				orderBaseHousePurchaserMapper.saveOrderBaseHousePurchaser(orderBaseHousePurchaserDto);
			}
			// 保存计划回款信息
			List<OrderBaseReceivableForDto> orderBaseReceivableForDto = orderBaseBorrowDto
					.getOrderReceivableForDto();
			for (int j = 0; j < orderBaseReceivableForDto.size(); j++) {
				orderBaseReceivableForDto.get(j).setOrderNo(orderNo);
				orderBaseReceivableForDto.get(j).setCreateUid(
						orderBaseBorrowDto.getCreateUid());
				orderBaseReceivableForMapper
						.saveOrderBaseReceivableFor(orderBaseReceivableForDto
								.get(j));
			}
		}
		
//		// 保存关联业务借款信息
//		List<OrderBaseBorrowRelationDto> orderBaseBorrowRelationList = orderBaseBorrowDto
//				.getOrderBaseBorrowRelationDto();
//		for (int j = 0; j < orderBaseBorrowRelationList.size(); j++) {
//			String relationOrderNo = UidUtil.generateOrderId();
//			orderBaseBorrowRelationList.get(j).setOrderNo(relationOrderNo);
//			orderBaseBorrowRelationList.get(j).setRelationOrderNo(orderNo);
//			orderBaseBorrowRelationList.get(j).setCreateUid(
//					orderBaseBorrowDto.getCreateUid());
//			orderBaseBorrowRelationMapper
//					.saveOrderBorrowRelation(orderBaseBorrowRelationList.get(j));
//		}
		
		// 保存借款信息
		orderBaseBorrowDto.setAcceptMemberUid(orderBaseBorrowDto.getAcceptMemberUid());
		orderBaseBorrowMapper.saveOrderBorrow(orderBaseBorrowDto);
		return orderNo;
	}

	@Override
	public int updateOrderBorrow(OrderBaseBorrowDto orderBaseBorrowDto) {
		// 更新债务置换贷款订单列表信息
		saveOrUpdateOrderList(orderBaseBorrowDto, "update");
		if(StringUtils.isNotBlank(orderBaseBorrowDto.getBorrowerName())){
			orderBaseMapper.updateCustomerName(orderBaseBorrowDto.getOrderNo(), orderBaseBorrowDto.getBorrowerName());
		}
		
		orderBaseBorrowMapper.updateOrderBorrowNull(orderBaseBorrowDto);
		// 更新计划回款信息
		orderBaseReceivableForMapper.deleteReceivableFor(orderBaseBorrowDto.getOrderNo());
		List<OrderBaseReceivableForDto> orderBaseReceivableForDto = orderBaseBorrowDto.getOrderReceivableForDto();
		if(null!=orderBaseReceivableForDto && !orderBaseReceivableForDto.isEmpty()){
			for (OrderBaseReceivableForDto rceivableForDto : orderBaseReceivableForDto) {
				if(null==rceivableForDto.getPayMentAmount() && null==rceivableForDto.getPayMentAmountDateStr())
					continue;
				rceivableForDto.setOrderNo(orderBaseBorrowDto.getOrderNo());
				rceivableForDto.setCreateUid(orderBaseBorrowDto.getUpdateUid());
				orderBaseReceivableForMapper.saveOrderBaseReceivableFor(rceivableForDto);
			}
		}		
		return 1;
	}

	/**
	 * 查询出债务置换贷款订单以及关联业务订单借款信息
	 */
	@Override
	public OrderBaseBorrowDto selectOrderBorrowByOrderNo(String orderNo) {
		OrderBaseBorrowDto orderBaseBorrowDto = new OrderBaseBorrowDto();
		orderBaseBorrowDto = orderBaseBorrowMapper.selectOrderBorrowByOrderNo(orderNo);		
		// 计划回款时间
		List<OrderBaseReceivableForDto> orderBaseReceivableForList = orderBaseReceivableForMapper
				.selectOrderReceivableForByOrderNo(orderNo);
		if (orderBaseBorrowDto == null) {
			orderBaseBorrowDto = new OrderBaseBorrowDto();
		}
		orderBaseBorrowDto.setOrderReceivableForDto(orderBaseReceivableForList);
		//查询债务置换贷款按天按段
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("riskGradeId", orderBaseBorrowDto.getRiskGradeId());
		map.put("cooperativeAgencyId", orderBaseBorrowDto.getCooperativeAgencyId());
		map.put("borrowingDays", orderBaseBorrowDto.getBorrowingDays());
		map.put("loanAmount", orderBaseBorrowDto.getLoanAmount());
		map.put("productId", orderBaseBorrowDto.getCityCode()+orderBaseBorrowDto.getProductCode());
		JSONObject json = httpUtil.getData(Constants.LINK_CREDIT, "/credit/customer/risk/v/findStageRate", map);
		if(json!=null){
			JSONObject data = json.getJSONObject("data");
			if(data!=null&&!data.isNullObject()){
				Object obj=data.get("modeid");
				if((data!=null)&&obj instanceof  Integer){
					int modeid = data.getInt("modeid");
					orderBaseBorrowDto.setModeid(modeid);
				}
			}
		}
		orderBaseBorrowDto.setCooperativeAgencyName(orderBaseBorrowDto.getAgencyName());
		return orderBaseBorrowDto;
	}

	/**
	 * 录入或者更新基础订单列表
	 * 
	 * @param orderBaseBorrowDto
	 */
	public void saveOrUpdateOrderList(OrderBaseBorrowDto orderBaseBorrowDto,
			String operation) {
		// 关联畅贷，部分数据同步
		if("03".equals(orderBaseBorrowDto.getProductCode())){
			OrderListDto curListOrder = orderBaseMapper.selectDetail(orderBaseBorrowDto.getOrderNo());
			if(null!=curListOrder && StringUtils.isNotBlank(curListOrder.getRelationOrderNo())){
				OrderListDto relationOrder = orderBaseMapper.selectDetail(curListOrder.getRelationOrderNo());				
				// 合作机构信息
				orderBaseBorrowDto.setCooperativeAgencyId(relationOrder.getCooperativeAgencyId());
				orderBaseBorrowDto.setCooperativeAgencyName(relationOrder.getCooperativeAgencyName());
				orderBaseBorrowDto.setChannelManagerUid(relationOrder.getChannelManagerUid());
				orderBaseBorrowDto.setChannelManagerName(relationOrder.getChannelManagerName());
				orderBaseBorrowDto.setBranchCompany(relationOrder.getBranchCompany());
				// 节点负责人信息
				orderBaseBorrowDto.setNotarialUid(relationOrder.getNotarialUid());
				orderBaseBorrowDto.setFacesignUid(relationOrder.getFacesignUid());
				// 客户信息				
				orderBaseBorrowDto.setCustomerType(relationOrder.getCustomerType());
				orderBaseBorrowDto.setBorrowerName(relationOrder.getCustomerName());
			}
			
		}		
		
		OrderListDto orderListDto = new OrderListDto();
		String notfaceAgency  = ConfigUtil.getStringValue(Constants.BASE_NOTFACE_AGENCY,ConfigUtil.CONFIG_BASE);
		if(null!=notfaceAgency
				&&StringUtils.isNotBlank(notfaceAgency)
				&&StringUtils.isNotBlank(orderBaseBorrowDto.getCooperativeAgencyName())
				&&notfaceAgency.contains(orderBaseBorrowDto.getCooperativeAgencyName())){
			orderListDto.setIsFace(2);
		} else {
			orderListDto.setIsFace(1);
		}
		orderListDto.setOrderNo(orderBaseBorrowDto.getOrderNo());
		orderListDto.setCityCode(orderBaseBorrowDto.getCityCode());
		orderListDto.setCityName(orderBaseBorrowDto.getCityName());
		orderListDto.setProductCode(orderBaseBorrowDto.getProductCode());
		orderListDto.setProductName(orderBaseBorrowDto.getProductName());
		//合作机构名称
		orderListDto.setCooperativeAgencyId(orderBaseBorrowDto.getCooperativeAgencyId());
		orderListDto.setCooperativeAgencyName(orderBaseBorrowDto.getCooperativeAgencyName());
		orderListDto.setBranchCompany(orderBaseBorrowDto.getBranchCompany());
		orderListDto.setCustomerType(orderBaseBorrowDto.getCustomerType());
		orderListDto.setCustomerName(orderBaseBorrowDto.getBorrowerName());
		orderListDto.setBorrowingAmount(orderBaseBorrowDto.getLoanAmount());
		if(orderBaseBorrowDto.getBorrowingDays()!=null){
			orderListDto.setBorrowingDay(orderBaseBorrowDto.getBorrowingDays());
		}
		orderListDto.setCooperativeAgencyId(orderBaseBorrowDto.getCooperativeAgencyId());
		orderListDto.setChannelManagerUid(orderBaseBorrowDto.getChannelManagerUid());
		orderListDto.setChannelManagerName(orderBaseBorrowDto.getChannelManagerName());
		orderListDto.setAcceptMemberUid(orderBaseBorrowDto.getAcceptMemberUid());
		orderListDto.setAcceptMemberName(orderBaseBorrowDto.getAcceptMemberName());
		orderListDto.setPreviousHandlerUid(orderBaseBorrowDto.getPreviousHandlerUid());
		orderListDto.setPreviousHandler(orderBaseBorrowDto.getPreviousHandler());
		orderListDto.setState(orderBaseBorrowDto.getState());
		orderListDto.setPreviousHandleTime(sdf.format(new Date()));
		orderListDto.setProcessId(orderBaseBorrowDto.getProcessId());
		orderListDto.setSource(orderBaseBorrowDto.getSource());		
		orderListDto.setAuditSort(orderBaseBorrowDto.getAuditSort());
		//预计出款时间
		orderListDto.setFinanceOutLoanTime(orderBaseBorrowDto.getFinanceOutLoanTime());
		//普通用户
		if("快鸽APP".equals(orderBaseBorrowDto.getSource()) && "save".equals(operation)&&orderBaseBorrowDto.getAgencyId()==0){
			orderListDto.setRelationOrderNo(orderBaseBorrowDto.getRelationOrderNo());
			orderListDto.setCurrentHandlerUid(orderBaseBorrowDto.getChannelManagerUid());
			orderListDto.setCurrentHandler(orderBaseBorrowDto.getChannelManagerName());
			orderListDto.setState("待指派受理员");
			orderListDto.setProcessId("assignAcceptMember");
			orderListDto.setAgencyId(1);
			orderListDto.setCreateUid(orderBaseBorrowDto.getCreateUid());
			orderBaseMapper.insertOrderList(orderListDto);
			return;
			//快鸽提单，创建人为快鸽用户id
			//orderBaseBorrowDto.setCreateUid(orderBaseBorrowDto.getChannelManagerUid());
		}else{
			String currentHandler="";  //下一处理人
//			FaceSignDto faceSignDto = new FaceSignDto();
//			faceSignDto.setOrderNo(orderBaseBorrowDto.getOrderNo());
//			faceSignDto = httpUtil.getObject(
//					Constants.LINK_CREDIT,
//					"/credit/process/facesign/v/detail", faceSignDto,
//					FaceSignDto.class);
//			NotarizationDto notarizationDto = new NotarizationDto();
//			notarizationDto.setOrderNo(orderBaseBorrowDto.getOrderNo());
//			notarizationDto = httpUtil.getObject(
//					Constants.LINK_CREDIT,
//					"/credit/process/notarization/v/detail", notarizationDto,
//					NotarizationDto.class);
//			if(orderBaseBorrowDto.getNotarialName()!=null&&(notarizationDto==null||notarizationDto.getNotarizationTime()==null)){
//				currentHandler+=orderBaseBorrowDto.getNotarialName()+",";
//			}
//			if(orderBaseBorrowDto.getFacesignName()!=null&&(faceSignDto==null||faceSignDto.getFaceSignTime()==null)){
//				currentHandler+=orderBaseBorrowDto.getFacesignName()+",";
//			}
//			currentHandler+=orderBaseBorrowDto.getAcceptMemberName();
			

			if(orderBaseBorrowDto.getAuditSort()==2 && "04".equals(orderBaseBorrowDto.getProductCode())){
				//面签 -先面签再审批 房抵贷
				currentHandler = orderBaseBorrowDto.getFacesignName();
			}else if(orderBaseBorrowDto.getAuditSort()==2){
				//公证-先面签再审批
				currentHandler = orderBaseBorrowDto.getNotarialName();
			}else{
				//完善订单
				currentHandler=orderBaseBorrowDto.getAcceptMemberName();
			}
			orderListDto.setCurrentHandler(currentHandler);
			orderListDto.setProcessId(orderBaseBorrowDto.getProcessId());
		}
		
		if(null!=orderBaseBorrowDto.getAgencyId()){
			orderListDto.setAgencyId(orderBaseBorrowDto.getAgencyId());
		}
		
		// 公证员面签员
		orderListDto.setNotarialUid(orderBaseBorrowDto.getNotarialUid());
		orderListDto.setFacesignUid(orderBaseBorrowDto.getFacesignUid());
		
		// 录入或更新		
		if ("save".equals(operation)) {
			if(orderBaseBorrowDto.getAgencyId()!=0){//非普通用户
				// 设置当前处理人Uid，也是创建人Uid
				if(orderBaseBorrowDto.getAuditSort()==2 && "04".equals(orderBaseBorrowDto.getProductCode())){
					//面签 -先面签再审批 房抵贷
					orderListDto.setCurrentHandlerUid(orderBaseBorrowDto.getFacesignUid());
				}else if(orderBaseBorrowDto.getAuditSort()==2){
					//公证-先面签再审批
					orderListDto.setCurrentHandlerUid(orderBaseBorrowDto.getNotarialUid());
				}else{
					//完善订单
					String currentHandlerUid = StringUtil.isEmpty(orderBaseBorrowDto.getAcceptMemberUid())?orderBaseBorrowDto.getCreateUid():orderBaseBorrowDto.getAcceptMemberUid();
					orderListDto.setCurrentHandlerUid(currentHandlerUid);
				}
				
			}
			orderListDto.setRelationOrderNo(orderBaseBorrowDto.getRelationOrderNo());			
			orderListDto.setCreateUid(orderBaseBorrowDto.getCreateUid());
			orderBaseMapper.insertOrderList(orderListDto);
		} else {			
			// 不更新处理人
			orderListDto.setUpdateUid(orderBaseBorrowDto.getUpdateUid());
			orderBaseMapper.updateOrderList(orderListDto);
		}
	}

	/**
	 * 提交审核
	 */
	@Override
	public int submitAudit(OrderListDto orderListDto) {
		String orderNo = getLoanOrderNo(orderListDto.getOrderNo());
		// 查询是否有畅贷
		List<OrderBaseBorrowRelationDto> orderBaseBorrowRelationList = orderBaseBorrowRelationMapper
				.selectOrderBorrowRelationByOrderNo(orderListDto.getOrderNo());
		if (!orderBaseBorrowRelationList.isEmpty()
				&& orderBaseBorrowRelationList.size() > 0) {
			for (int i = 0; i < orderBaseBorrowRelationList.size(); i++) {
				// 有畅贷则录入订单列表
				OrderListDto orderBase = orderBaseMapper.selectDetail(orderBaseBorrowRelationList.get(i).getOrderNo());
				if(orderBase==null){
					OrderListDto orderList = orderBaseMapper.selectDetail(orderListDto.getOrderNo());
					orderList.setOrderNo(orderBaseBorrowRelationList.get(i).getOrderNo());
					orderList.setProductCode(orderBaseBorrowRelationList.get(i).getProductCode());
					orderList.setProductName("畅贷");
					orderList.setBorrowingDay(orderBaseBorrowRelationList.get(i).getBorrowingDays());
					orderList.setBorrowingAmount(orderBaseBorrowRelationList.get(i).getLoanAmount());
					orderList.setRelationOrderNo(orderNo);
					orderBaseMapper.insertOrderList(orderList);
					orderListDto.setRelationOrderNo(orderBaseBorrowRelationList.get(i).getOrderNo());
					orderBaseMapper.updateOrderList(orderListDto);
					//复制畅贷公证面签流水
					Map<String,Object> map = new HashMap<String, Object>();
					map.put("orderNo", orderBaseBorrowRelationList.get(i).getOrderNo());
					map.put("relationOrderNo",orderListDto.getOrderNo());
					orderFlowMapper.copyFlows(map);
					return 0;
				}
			}
		} 
		orderBaseMapper.submitAuditUpdateOrderList(orderListDto);
		return 1;
	}

	@Override
	public List<OrderBaseBorrowDto> selectOrderBorrowList(
			OrderBaseBorrowDto orderBaseBorrowDto) {
		return orderBaseBorrowMapper.selectOrderBorrowList(orderBaseBorrowDto);
	}

	@Override
	public int selectOrderBorrowCount(OrderBaseBorrowDto orderBaseBorrowDto) {
		return orderBaseBorrowMapper.selectOrderBorrowCount(orderBaseBorrowDto);
	}

	/**
	 * 重新提交
	 */
	@Override
	public int reSubmit(OrderListDto orderListDto) {
		// 设置受理经理
		orderListDto.setPreviousHandleTime(sdf.format(new Date()));
		orderBaseMapper.updateOrderList(orderListDto);
		return 1;
	}

	@Override
	public int updateBorrow(OrderBaseBorrowDto orderBaseBorrowDto) {
		orderBaseBorrowMapper.updateOrderBorrow(orderBaseBorrowDto);
		return 1;
	}
	
	/**
	 * 是否畅贷订单号
	 * @param orderNo
	 * @return
	 */
//	public boolean isChangLoan(String orderNo) {
//		OrderBaseBorrowRelationDto orderBaseBorrowRelationDto = orderBaseBorrowRelationMapper
//				.selectRelationByOrderNo(orderNo);
//		if (orderBaseBorrowRelationDto != null) {
//			return true;
//		} else {
//			return false;
//		}
//	}
	
	/**
	 * 是否有畅贷
	 * @param orderNo
	 * @return
	 */
	public int hasChangLoan(String orderNo){
		List<OrderBaseBorrowRelationDto> orderBaseBorrowRelation = orderBaseBorrowRelationMapper.selectOrderBorrowRelationByOrderNo(orderNo);
		if(orderBaseBorrowRelation!=null && orderBaseBorrowRelation.size()>0){
			return 1;
		}
		return 2;
	}
	
	/**
	 * 获取债务置换贷款订单号
	 * @param orderNo
	 * @return
	 */
	public String getLoanOrderNo(String orderNo){
		OrderBaseBorrowRelationDto orderBaseBorrowRelationDto = orderBaseBorrowRelationMapper.selectRelationByOrderNo(orderNo);
		if(orderBaseBorrowRelationDto!=null && orderBaseBorrowRelationDto.getRelationOrderNo()!=null){
			return orderBaseBorrowRelationDto.getRelationOrderNo();
		}
		return orderNo;
	}
	
	/**
	 * 转换对象为畅贷
	 * @param orderBaseBorrowDto
	 * @return
	 */
	public OrderBaseBorrowRelationDto getOrderBaseBorrowRelationDto(OrderBaseBorrowDto orderBaseBorrowDto){
		OrderBaseBorrowRelationDto orderBaseBorrowRelationDto = new OrderBaseBorrowRelationDto();
		orderBaseBorrowRelationDto.setLoanAmount(orderBaseBorrowDto.getLoanAmount());
		orderBaseBorrowRelationDto.setBorrowingDays(orderBaseBorrowDto.getBorrowingDays());
		orderBaseBorrowRelationDto.setRiskGradeId(orderBaseBorrowDto.getRiskGradeId());
		orderBaseBorrowRelationDto.setChargeMoney(orderBaseBorrowDto.getChargeMoney());
		orderBaseBorrowRelationDto.setRate(orderBaseBorrowDto.getRate());
		orderBaseBorrowRelationDto.setOverdueRate(orderBaseBorrowDto.getOverdueRate());
		orderBaseBorrowRelationDto.setOrderNo(orderBaseBorrowDto.getOrderNo());
		orderBaseBorrowRelationDto.setServiceCharge(orderBaseBorrowDto.getServiceCharge());
		return orderBaseBorrowRelationDto;
	}

	@Override
	public Map<String,Object> checkOrder(String productCode, String orderNo, String relationOrderNo) {
		Map<String,Object> map = new HashMap<String,Object>();
		OrderBaseBorrowDto orderBaseBorrowDto = orderBaseBorrowMapper.selectOrderBorrowByOrderNo(orderNo);
		if(orderBaseBorrowDto==null||(orderBaseBorrowDto!=null&&orderBaseBorrowDto.getIsFinish()==2)){
			map.put("code", 1);
			map.put("msg", "请完善借款信息");
			return map;
		}
		map.put("foreclosureMemberUid", orderBaseBorrowDto.getForeclosureMemberUid());
		
		List<OrderBaseBorrowRelationDto> orderBaseBorrowRelationList = orderBaseBorrowRelationMapper.selectOrderBorrowRelationByOrderNo(orderNo);
		for (OrderBaseBorrowRelationDto orderBaseBorrowRelationDto : orderBaseBorrowRelationList) {
			if(orderBaseBorrowRelationDto!=null&&orderBaseBorrowRelationDto.getIsFinish()==2){
				map.put("code", 1);
				map.put("msg", "请完善关联订单信息");
				return map;
			}
		}
		
		if("04".equals(productCode)){
			OrderBaseHouseLendingDto orderBaseHouseLendingDto = orderBaseHouseLendingMapper.selectOrderHouseLendingByOrderNo(orderNo);
			if(orderBaseHouseLendingDto!=null&&orderBaseHouseLendingDto.getIsFinish()==2){
				map.put("code", 1);
				map.put("msg", "请完善放款信息");
				return map;
			}
		}
		
		/*
		 * 客户信息，不参与编辑
		 * 1.畅贷关联订单   
		 */
		if(!("03".equals(productCode) && StringUtils.isNotBlank(relationOrderNo))){
			OrderBaseCustomerDto orderBaseCustomerDto = orderBaseCustomerMapper.selectOrderCustomerByOrderNo(orderNo);
			if(orderBaseCustomerDto!=null&&orderBaseCustomerDto.getIsFinish()==2){
				map.put("code", 2);
				map.put("msg", "请完善客户信息");
				return map;
			}
			List<OrderBaseCustomerBorrowerDto>  orderBaseCustomerBorrower =orderBaseCustomerBorrowerMapper.selectOrderCustomerBorrowerByOrderNo(orderNo);
			for (OrderBaseCustomerBorrowerDto orderBaseCustomerBorrowerDto : orderBaseCustomerBorrower) {
				if(orderBaseCustomerBorrowerDto!=null&&orderBaseCustomerBorrowerDto.getIsFinish()==2){
					map.put("code", 2);
					map.put("msg", "请完善共同借款人信息");
					return map;
				}
			}
			List<OrderBaseCustomerGuaranteeDto> orderBaseCustomerGuarantee = orderBaseCustomerGuaranteeMapper.selectOrderCustomerGuaranteeByOrderNo(orderNo);
			for (OrderBaseCustomerGuaranteeDto orderBaseCustomerGuaranteeDto : orderBaseCustomerGuarantee) {
				if(orderBaseCustomerGuaranteeDto!=null&&orderBaseCustomerGuaranteeDto.getIsFinish()==2){
					map.put("code", 2);
					map.put("msg", "请完善担保人信息");
					return map;
				}
			}
		}
		/*
		 * 房产信息，不参与编辑
		 * 1.畅贷关联订单   
		 */
		if(!("03".equals(productCode) && StringUtils.isNotBlank(relationOrderNo))){
			OrderBaseHouseDto orderHouse =orderBaseHouseMapper.selectOrderHouseByOrderNo(orderNo);
			if(orderHouse!=null&&orderHouse.getIsFinish()==2&&!"04".equals(productCode)){
				map.put("code", 3);
				map.put("msg", "请完善房产交易信息");
				return map;
			}
			List<OrderBaseHousePropertyDto>  orderBaseHouseProperty =orderBaseHousePropertyMapper.selectOrderHousePropertyByOrderNo(orderNo);
			for (OrderBaseHousePropertyDto orderBaseHousePropertyDto : orderBaseHouseProperty) {
				if(orderBaseHousePropertyDto!=null&&orderBaseHousePropertyDto.getIsFinish()==2){
					map.put("code", 3);
					map.put("msg", "请完善房产信息");
					return map;
				}
			}
			List<OrderBaseHousePropertyPeopleDto> orderBaseHousePropertyPeople =orderBaseHousePropertyPeopleMapper.selectOrderPropertyPeopleByOrderNo(orderNo);
			for (OrderBaseHousePropertyPeopleDto orderBaseHousePropertyPeopleDto : orderBaseHousePropertyPeople) {
				if(orderBaseHousePropertyPeopleDto!=null&&orderBaseHousePropertyPeopleDto.getIsFinish()==2){
					map.put("code", 3);
					map.put("msg", "请完善产权人信息");
					return map;
				}
			}
			if(!"03".equals(productCode) || 1==orderHouse.getBussinessType()){
				List<OrderBaseHousePurchaserDto> orderBaseHousePurchaser = orderBaseHousePurchaserMapper.selectOrderHousePurchaserByOrderNo(orderNo);
				for (OrderBaseHousePurchaserDto orderBaseHousePurchaserDto : orderBaseHousePurchaser) {
					if(orderBaseHousePurchaserDto!=null&&orderBaseHousePurchaserDto.getIsFinish()==2){
						map.put("code", 3);
						map.put("msg", "请完善买房人信息");
						return map;
					}
				}
			}			
		}
		/*
		 * 要件检验，不参与编辑
		 * 1.畅贷关联订单   
		 */
		if(!("03".equals(productCode) && StringUtils.isNotBlank(relationOrderNo))&&!"04".equals(productCode)&&!"05".equals(productCode)&&!"06".equals(productCode)&&!"07".equals(productCode)){
			DocumentsDto dtoDocuments = new DocumentsDto();
			dtoDocuments.setOrderNo(orderNo);
			RespDataObject<DocumentsDto> respDocument = this.httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/element/basics/v/detail", dtoDocuments, DocumentsDto.class);		
			if(null==respDocument || !RespStatusEnum.SUCCESS.getCode().equalsIgnoreCase(respDocument.getCode()) || null==respDocument.getData() || null==respDocument.getData().getPaymentType()
				|| StringUtils.isEmpty(respDocument.getData().getPaymentType().getPaymentMode()) || StringUtils.isEmpty(respDocument.getData().getPaymentType().getBankProducts())){
				map.put("code", 4);
				map.put("msg", "请完善要件检验信息");
				return map;
			}
		}
		
		map.put("code", 0);
		map.put("msg", "通过");
		return map;
	}
	@Override
	public boolean isFinish(String title,String butUrl,String orderNo){
		if(title.equals("借款信息")&&butUrl.equals("orderBorrowPage")){
			OrderBaseBorrowDto orderBaseBorrowDto = orderBaseBorrowMapper.selectOrderBorrowByOrderNo(orderNo);
			if(orderBaseBorrowDto!=null&&orderBaseBorrowDto.getIsFinish()==2){
				return false;
			}
			List<OrderBaseBorrowRelationDto> orderBaseBorrowRelationList = orderBaseBorrowRelationMapper.selectOrderBorrowRelationByOrderNo(orderNo);
			for (OrderBaseBorrowRelationDto orderBaseBorrowRelationDto : orderBaseBorrowRelationList) {
				if(orderBaseBorrowRelationDto!=null&&orderBaseBorrowRelationDto.getIsFinish()==2){
					return false;
				}
			}
			return true;
		}
		if(title.equals("借款信息")&&butUrl.equals("orderBorrBorrow")){
			OrderBaseBorrowDto orderBaseBorrowDto = orderBaseBorrowMapper.selectOrderBorrowByOrderNo(orderNo);
			if(orderBaseBorrowDto!=null&&orderBaseBorrowDto.getIsFinish()==2){
				return false;
			}
			return true;
		}
		if(title.equals("畅贷信息")){
			List<OrderBaseBorrowRelationDto> orderBaseBorrowRelationList = orderBaseBorrowRelationMapper.selectOrderBorrowRelationByOrderNo(orderNo);
			for (OrderBaseBorrowRelationDto orderBaseBorrowRelationDto : orderBaseBorrowRelationList) {
				if(orderBaseBorrowRelationDto!=null&&orderBaseBorrowRelationDto.getIsFinish()==2){
					return false;
				}
			}
			return true;
		}
		if(title.equals("客户信息")){
			OrderBaseCustomerDto orderBaseCustomerDto = orderBaseCustomerMapper.selectOrderCustomerByOrderNo(orderNo);
			if(orderBaseCustomerDto!=null&&orderBaseCustomerDto.getIsFinish()==2){
				return false;
			}
			List<OrderBaseCustomerBorrowerDto>  orderBaseCustomerBorrower =orderBaseCustomerBorrowerMapper.selectOrderCustomerBorrowerByOrderNo(orderNo);
			for (OrderBaseCustomerBorrowerDto orderBaseCustomerBorrowerDto : orderBaseCustomerBorrower) {
				if(orderBaseCustomerBorrowerDto!=null&&orderBaseCustomerBorrowerDto.getIsFinish()==2){
					return false;
				}
			}
			List<OrderBaseCustomerGuaranteeDto> orderBaseCustomerGuarantee = orderBaseCustomerGuaranteeMapper.selectOrderCustomerGuaranteeByOrderNo(orderNo);
			for (OrderBaseCustomerGuaranteeDto orderBaseCustomerGuaranteeDto : orderBaseCustomerGuarantee) {
				if(orderBaseCustomerGuaranteeDto!=null&&orderBaseCustomerGuaranteeDto.getIsFinish()==2){
					return false;
				}
			}
			return true;
		}
		if(title.equals("基本信息")){
			OrderBaseCustomerDto orderBaseCustomerDto = orderBaseCustomerMapper.selectOrderCustomerByOrderNo(orderNo);
			if(orderBaseCustomerDto!=null&&orderBaseCustomerDto.getIsFinish()==1){
				return true;
			}
			return false;
		}
		if(title.equals("借款人信息")){
			List<OrderBaseCustomerBorrowerDto>  orderBaseCustomerBorrower =orderBaseCustomerBorrowerMapper.selectOrderCustomerBorrowerByOrderNo(orderNo);
			for (OrderBaseCustomerBorrowerDto orderBaseCustomerBorrowerDto : orderBaseCustomerBorrower) {
				if(orderBaseCustomerBorrowerDto!=null&&orderBaseCustomerBorrowerDto.getIsFinish()==1){
					return true;
				}
			}
			return false;
		}
		if(title.equals("担保人信息")){
			List<OrderBaseCustomerGuaranteeDto> orderBaseCustomerGuarantee = orderBaseCustomerGuaranteeMapper.selectOrderCustomerGuaranteeByOrderNo(orderNo);
			for (OrderBaseCustomerGuaranteeDto orderBaseCustomerGuaranteeDto : orderBaseCustomerGuarantee) {
				if(orderBaseCustomerGuaranteeDto!=null&&orderBaseCustomerGuaranteeDto.getIsFinish()==1){
					return true;
				}
			}
			return false;
		}
		if(title.equals("房产信息")){
			OrderBaseHouseDto orderHouse =orderBaseHouseMapper.selectOrderHouseByOrderNo(orderNo);
			if(orderHouse!=null&&orderHouse.getIsFinish()==2){
				return false;
			}
			List<OrderBaseHousePropertyDto>  orderBaseHouseProperty =orderBaseHousePropertyMapper.selectOrderHousePropertyByOrderNo(orderNo);
			for (OrderBaseHousePropertyDto orderBaseHousePropertyDto : orderBaseHouseProperty) {
				if(orderBaseHousePropertyDto!=null&&orderBaseHousePropertyDto.getIsFinish()==2){
					return false;
				}
			}
			List<OrderBaseHousePropertyPeopleDto> orderBaseHousePropertyPeople =orderBaseHousePropertyPeopleMapper.selectOrderPropertyPeopleByOrderNo(orderNo);
			for (OrderBaseHousePropertyPeopleDto orderBaseHousePropertyPeopleDto : orderBaseHousePropertyPeople) {
				if(orderBaseHousePropertyPeopleDto!=null&&orderBaseHousePropertyPeopleDto.getIsFinish()==2){
					return false;
				}
			}
			List<OrderBaseHousePurchaserDto> orderBaseHousePurchaser = orderBaseHousePurchaserMapper.selectOrderHousePurchaserByOrderNo(orderNo);
			for (OrderBaseHousePurchaserDto orderBaseHousePurchaserDto : orderBaseHousePurchaser) {
				if(orderBaseHousePurchaserDto!=null&&orderBaseHousePurchaserDto.getIsFinish()==2){
					return false;
				}
			}
			return true;
		}
		if(title.equals("房产")){
			List<OrderBaseHousePropertyDto>  orderBaseHouseProperty =orderBaseHousePropertyMapper.selectOrderHousePropertyByOrderNo(orderNo);
			for (OrderBaseHousePropertyDto orderBaseHousePropertyDto : orderBaseHouseProperty) {
				if(orderBaseHousePropertyDto!=null&&orderBaseHousePropertyDto.getIsFinish()==1){
					return true;
				}
			}
			return false;
		}
		if(title.equals("产权人")){
			List<OrderBaseHousePropertyPeopleDto> orderBaseHousePropertyPeople =orderBaseHousePropertyPeopleMapper.selectOrderPropertyPeopleByOrderNo(orderNo);
			for (OrderBaseHousePropertyPeopleDto orderBaseHousePropertyPeopleDto : orderBaseHousePropertyPeople) {
				if(orderBaseHousePropertyPeopleDto!=null&&orderBaseHousePropertyPeopleDto.getIsFinish()==1){
					return true;
				}
			}
			return false;
		}
		if(title.equals("买房人")){
			List<OrderBaseHousePurchaserDto> orderBaseHousePurchaser = orderBaseHousePurchaserMapper.selectOrderHousePurchaserByOrderNo(orderNo);
			for (OrderBaseHousePurchaserDto orderBaseHousePurchaserDto : orderBaseHousePurchaser) {
				if(orderBaseHousePurchaserDto!=null&&orderBaseHousePurchaserDto.getIsFinish()==1){
					return true;
				}
			}
			return false;
		}
		if(title.equals("房贷与交易信息")||title.equals("房贷与新贷款信息")){
			OrderBaseHouseDto orderHouse =orderBaseHouseMapper.selectOrderHouseByOrderNo(orderNo);
			if(orderHouse!=null&&orderHouse.getIsFinish()==1){
				return true;
			}
			return false;
		}
		if(title.equals("征信")){
			// 判断征信是否完整
			CreditDto creditDto =new CreditDto();
			creditDto.setOrderNo(orderNo);
			creditDto = httpUtil.getObject(
					Constants.LINK_CREDIT,
					"/credit/risk/ordercredit/v/detail", creditDto,
					CreditDto.class);
			if(creditDto!=null&&creditDto.getIsFinish()==1){
				return true;
			}
			return false;
		}
		return false;
	}

	/**
	 * 快鸽APP提单
	 * @param orderDto
	 * @param house
	 * @return
	 */
	public void kgAppInsertOrder(OrderBaseBorrowDto orderDto, OrderBaseHouseDto house){
		saveOrderBorrow(orderDto);
		orderBaseHousePropertyMapper.deleteHousePropertyByOrderNo(orderDto.getOrderNo());
		if(null!=house&&null!=house.getOrderBaseHousePropertyDto()&&house.getOrderBaseHousePropertyDto().size()>0){
			orderBaseHousePropertyMapper.saveOrderHouseProperty(house.getOrderBaseHousePropertyDto().get(0));
		}
	}

	@Override
	public int appUpdateBorrow(OrderBaseBorrowDto orderBaseBorrowDto) {
		// 更新债务置换贷款订单列表信息
				saveOrUpdateOrderList(orderBaseBorrowDto, "update");
				orderBaseBorrowMapper.updateOrderBorrowNull(orderBaseBorrowDto);
				// 更新计划回款信息
				orderBaseReceivableForMapper.deleteReceivableFor(orderBaseBorrowDto
						.getOrderNo());
				//更新客户姓名
				OrderBaseCustomerDto customer = new OrderBaseCustomerDto();
				customer.setOrderNo(orderBaseBorrowDto.getOrderNo());
				customer.setCustomerName(orderBaseBorrowDto.getBorrowerName());
				customer.setUpdateUid(orderBaseBorrowDto.getUpdateUid());
				orderBaseCustomerMapper.updateOrderCustomer(customer);
				List<OrderBaseReceivableForDto> orderBaseReceivableForDto = orderBaseBorrowDto
						.getOrderReceivableForDto();
				for (int j = 0; j < orderBaseReceivableForDto.size(); j++) {
					orderBaseReceivableForDto.get(j).setOrderNo(
							orderBaseBorrowDto.getOrderNo());
					orderBaseReceivableForDto.get(j).setCreateUid(
							orderBaseBorrowDto.getUpdateUid());
					orderBaseReceivableForMapper
							.saveOrderBaseReceivableFor(orderBaseReceivableForDto
									.get(j));
				}
				return 1;
	}
	@Override
	public int assignAcceptMember(OrderBaseBorrowDto order){
		OrderListDto orderList = new OrderListDto();
		orderList.setOrderNo(order.getOrderNo());
		orderList.setAcceptMemberUid(order.getAcceptMemberUid());
		orderList.setAcceptMemberName(order.getAcceptMemberName());
		orderList.setCurrentHandler(order.getAcceptMemberName());
		orderList.setCurrentHandlerUid(order.getAcceptMemberUid());
		orderList.setState("待提单");
		orderList.setProcessId("placeOrder");
		
		int success = orderBaseMapper.updateAcceptMember(orderList);
		success = orderBaseBorrowMapper.updateAcceptMember(order);
		return success;
	}

	@Override
	public void addRiskList(String orderNo) {
		OrderBaseBorrowDto borrow = orderBaseBorrowMapper.selectOrderBorrowByOrderNo(orderNo);
		OrderBaseCustomerDto customer = orderBaseCustomerMapper.selectOrderCustomerByOrderNo(orderNo);
		//提交审核时添加风险名单查询
		if(customer!=null&&"二代身份证".equals(customer.getCustomerCardType())){
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("name", customer.getCustomerName());
			map.put("identity", customer.getCustomerCardNumber());
			map.put("phone", borrow.getPhoneNumber());
			map.put("uid", borrow.getAcceptMemberUid());
			map.put("orderNo", borrow.getOrderNo());
			JSONObject json = httpUtil.getData(Constants.LINK_CREDIT, "credit/risk/riskList/v/addRiskList", map);
			System.out.println("添加风险名单返回:"+json.toString());
		}
	}
	
}
