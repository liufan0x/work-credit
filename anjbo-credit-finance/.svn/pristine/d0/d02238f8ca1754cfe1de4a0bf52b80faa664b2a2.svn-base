package com.anjbo.controller;

import com.anjbo.bean.customer.AgencyDto;
import com.anjbo.bean.finance.*;
import com.anjbo.bean.order.OrderBaseBorrowDto;
import com.anjbo.bean.order.OrderFlowDto;
import com.anjbo.bean.order.OrderListDto;
import com.anjbo.bean.product.MortgageDto;
import com.anjbo.bean.risk.AllocationFundDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.bean.vo.ChartVo;
import com.anjbo.common.*;
import com.anjbo.common.DateUtil;
import com.anjbo.service.*;
import com.anjbo.utils.*;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.math.BigDecimal;
import java.util.*;

/**
 * 待回款订单
 * 
 * @author yis
 *
 */
@Controller
@RequestMapping("/credit/finance/receivableFor/v")
public class ReceivableForController extends BaseController {
	private Logger log = Logger.getLogger(getClass());

	@Resource
	ReceivableForService receivableForService;
	@Resource
	LendingService lendingService;
	@Resource
	ReceivableHasService receivableHasService;
	@Resource
	LendingPayService lendingPayService;
	@Resource
	LendingHarvestService lendingHarvestService;
	@Resource
	private PaymentReportService paymentReportService;
	/**
	 * 初始化
	 * 
	 * @param request
	 * @param receivableForDto
	 * @return
	 */
	@RequestMapping(value = "/init")
	public @ResponseBody RespDataObject<Map<String, Object>> init(
			HttpServletRequest request,
			@RequestBody ReceivableForDto receivableForDto) {
		RespDataObject<Map<String, Object>> rd = new RespDataObject<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 查询放款信息
			LendingDto lendingDto = lendingService.selectLendingTime(receivableForDto.getOrderNo());
			// 查询订单信息
			OrderBaseBorrowDto dto = new OrderBaseBorrowDto();
			dto.setOrderNo(receivableForDto.getOrderNo());
			RespDataObject<OrderBaseBorrowDto> obj = httpUtil.getRespDataObject(Constants.LINK_CREDIT,"/credit/order/borrowother/v/queryBorrow", dto,OrderBaseBorrowDto.class);
			OrderBaseBorrowDto baseBorrowDto = obj.getData();
			// 回款信息
			try {
				if ("orderEdit.receivableForFirstEdit".equals(receivableForDto.getForName())|| "orderEdit.receivableForEdit".equals(receivableForDto.getForName())) { // 首期/回款
					map.put("forList", new ArrayList<ReceivableForDto>());
					receivableForService.updwithdraw(receivableForDto); // 首期清空数据
					if (!"03".equals(baseBorrowDto.getProductCode())&&!"06".equals(baseBorrowDto.getProductCode())&&!"07".equals(baseBorrowDto.getProductCode())) { // 置换贷
						if ("02".equals(baseBorrowDto.getProductCode())) { // 非交易类
							lendingDto.setOneTimePay(1);// 是否一次性回款:0:初始化,1:是,2:否(非交易类没有是否一次性回款)
							map.put("forList", new ArrayList<ReceivableForDto>());
						} else {
							lendingDto.setOneTimePay(baseBorrowDto.getIsOnePay());// 是否一次性回款:0:初始化,1:是,2:否(非交易类没有是否一次性回款)
						}
					}else{ //畅贷
						lendingDto.setOneTimePay(1);
					}
				} else if ("orderEdit.receivableForEndEdit".equals(receivableForDto.getForName())) {
					List<ReceivableForDto> list = receivableForService.findByReceivableFor(receivableForDto);
					BigDecimal total = new BigDecimal(0); // 页面总额
					for (int k = 0; k < list.size(); k++) {
						BigDecimal amount = list.get(k).getPayMentAmount();
						total = total.add(amount);
					}
					if (baseBorrowDto != null&& baseBorrowDto.getLoanAmount() == total.doubleValue()) {
						receivableForDto.setIsFrist(2); // 删除尾期数据
						receivableForService.updwithdraw(receivableForDto); // 尾期清空数据
						receivableForDto.setIsFrist(0);
						list = receivableForService.findByReceivableFor(receivableForDto);
					}
//					if (baseBorrowDto.getIsChangLoan() != 1) { // 置换贷
						if ("01".equals(baseBorrowDto.getProductCode())) { // 非交易类
							lendingDto.setOneTimePay(2);// 是否一次性回款:0:初始化,1:是,2:否(非交易类没有是否一次性回款)
						}
//					}
					map.put("forList", list);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			boolean fundCode = false;
			if (baseBorrowDto != null) {
				lendingDto.setProductCode(baseBorrowDto.getProductCode());
				lendingDto.setLoanAmount(baseBorrowDto.getLoanAmount());// 放款金额
				lendingDto.setProductCode(baseBorrowDto.getProductCode());
				if (!"03".equals(baseBorrowDto.getProductCode())) { // 置换贷
					// =========================查询推送华融信息需要Start========================================
					// 资金方信息 是否有华融
					try {
						boolean isRisk=false;
						AllocationFundDto fundDto =new AllocationFundDto();
						fundDto.setOrderNo(receivableForDto.getOrderNo());
						RespData<AllocationFundDto> ob1=httpUtil.getRespData(Constants.LINK_CREDIT, "/credit/risk/allocationfund/v/detail", fundDto,AllocationFundDto.class);
						List<AllocationFundDto> flist=ob1.getData();
						if(flist!=null){
							for (int i = 0; i < flist.size(); i++) {
								if("110".equals(flist.get(i).getFundCode())){
									isRisk=true;
									break;
								}
							}
						}
						if(isRisk){  //判断资金方是否有华融110
							Map<String, Object> map1 = new HashMap<String, Object>();
							map1.put("orderNo", receivableForDto.getOrderNo());
							JSONObject jsons = httpUtil.getData(Constants.LINK_CREDIT,"/credit/third/api/hr/queryApply", map1);
							log.info("返回信息" + jsons);
							if (jsons != null && !jsons.isNullObject()) {  //获取推送信息
								JSONObject data = jsons.getJSONObject("data");
								if (data != null && !data.isNullObject()) {
									JSONObject lcAppoint = data.getJSONObject("lcAppoint");
									if (lcAppoint != null&& !lcAppoint.isNullObject()) {
										map.put("borrowingDays",lcAppoint.get("applyTnr"));
										fundCode = true;
										map.put("dayRate", 0.0306);
										map.put("overdueRate", 0.0306);
									}
								}
							}
						}
						// int fundId=0;
						// AllocationFundDto fundDto =new AllocationFundDto();
						// fundDto.setOrderNo(receivableForDto.getOrderNo());
						// RespData<AllocationFundDto>
						// ob1=httpUtil.getRespData(Enums.MODULAR_URL.CREDIT.toString(),
						// "/credit/risk/allocationfund/v/detail",
						// fundDto,AllocationFundDto.class);
						// List<AllocationFundDto> flist=ob1.getData();
						// if(flist!=null){
						// for (int i = 0; i < flist.size(); i++) {
						// if("110".equals(flist.get(i).getFundCode())){
						// fundCode=true;
						// fundId=flist.get(i).getFundId();
						// map.put("dayRate", 0.0306);
						// map.put("overdueRate", 0.0306);
						// break;
						// }
						// }
						// }
						// 查询管理后台资金方日费率
						// if(fundId>0){
						// CustomerFundCostDto customerFundCostDto=new
						// CustomerFundCostDto();
						// customerFundCostDto.setFundId(fundId);
						// if(baseBorrowDto!=null){
						// String
						// productId=baseBorrowDto.getCityCode()+baseBorrowDto.getProductCode();
						// if(StringUtil.isNotEmpty(productId))
						// customerFundCostDto.setProductId(Integer.parseInt(productId));
						// }
						// RespDataObject<CustomerFundCostDto>
						// baseobj=httpUtil.getRespDataObject(Enums.MODULAR_URL.CREDIT.toString(),
						// "/credit/customer/fund/cost/v/selectCustomerFundCostByFundId",
						// customerFundCostDto,CustomerFundCostDto.class);
						// CustomerFundCostDto costDto=baseobj.getData();
						// Double dayRate=0.0;
						// Double overdueRate=0.0;
						// if(costDto!=null){
						// dayRate=costDto.getDayRate();
						// overdueRate=costDto.getOverdueRate();
						// }
						// map.put("dayRate", dayRate);
						// map.put("overdueRate", overdueRate);
						// }
					} catch (Exception e) {
						e.printStackTrace();
					}
					// int borrowingDays=baseBorrowDto.getBorrowingDays();//
					// 借款期限
					// map.put("borrowingDays", borrowingDays);
					// =========================推送华融信息需要END========================================
				}
			} else {
				map.put("forList", new ArrayList<ReceivableForDto>());
			}
			map.put("fundCode", fundCode);
			map.put("lendingDto", lendingDto);
			// 回款退费信息
			ReceivableHasDto receivableHasDto = receivableHasService.findByReceivableHas(receivableForDto);
			map.put("receivableHasDto", receivableHasDto);
			rd.setCode(RespStatusEnum.SUCCESS.getCode());
			rd.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			rd.setCode(RespStatusEnum.FAIL.getCode());
			rd.setMsg(RespStatusEnum.FAIL.getMsg());
		}
		rd.setData(map);
		return rd;
	}

	/**
	 * 初始化
	 * @param request
	 * @param receivableForDto
	 * @return
	 */
	@RequestMapping(value = "/initByHr")
	public @ResponseBody RespDataObject<Map<String, Object>> initByHr(
			HttpServletRequest request,
			@RequestBody ReceivableForDto receivableForDto) {
		RespDataObject<Map<String, Object>> rd = new RespDataObject<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 查询放款信息
			LendingDto lendingDto = lendingService.selectLendingTime(receivableForDto.getOrderNo());
			if(lendingDto==null){
				lendingDto=new LendingDto();
			}
			// 查询订单信息
			OrderBaseBorrowDto dto = new OrderBaseBorrowDto();
			dto.setOrderNo(receivableForDto.getOrderNo());
			RespDataObject<OrderBaseBorrowDto> obj = httpUtil.getRespDataObject(Constants.LINK_CREDIT,"/credit/order/borrowother/v/queryBorrow", dto,OrderBaseBorrowDto.class);
			OrderBaseBorrowDto baseBorrowDto = obj.getData();
			// 回款信息
			List<ReceivableForDto> list = receivableForService.findByReceivableFor(receivableForDto);
			if(list==null ||list.size()==0){
				map.put("forList", new ArrayList<ReceivableForDto>());
			}else{
				map.put("forList", list);
			}
			boolean fundCode = false;
			if (baseBorrowDto != null) {
				lendingDto.setProductCode(baseBorrowDto.getProductCode());
				lendingDto.setLoanAmount(baseBorrowDto.getLoanAmount());// 放款金额
				lendingDto.setProductCode(baseBorrowDto.getProductCode());
				if (!"03".equals(baseBorrowDto.getProductCode())) { // 置换贷
					// =========================查询推送华融信息需要Start========================================
					// 资金方信息 是否有华融
					try {
						boolean isRisk=false;
						AllocationFundDto fundDto =new AllocationFundDto();
						fundDto.setOrderNo(receivableForDto.getOrderNo());
						RespData<AllocationFundDto> ob1=httpUtil.getRespData(Constants.LINK_CREDIT, "/credit/risk/allocationfund/v/detail", fundDto,AllocationFundDto.class);
						List<AllocationFundDto> flist=ob1.getData();
						if(flist!=null){
							for (int i = 0; i < flist.size(); i++) {
								if("110".equals(flist.get(i).getFundCode())){
									isRisk=true;
									break;
								}
							}
						}
						if(isRisk){  //判断资金方是否有华融110
							Map<String, Object> map1 = new HashMap<String, Object>();
							map1.put("orderNo", receivableForDto.getOrderNo());
							JSONObject jsons = httpUtil.getData(Constants.LINK_CREDIT,"/credit/third/api/hr/queryApply", map1);
							log.info("返回信息" + jsons);
							if (jsons != null && !jsons.isNullObject()) {  //获取推送信息
								JSONObject data = jsons.getJSONObject("data");
								if (data != null && !data.isNullObject()) {
									JSONObject lcAppoint = data.getJSONObject("lcAppoint");
									if (lcAppoint != null&& !lcAppoint.isNullObject()) {
										map.put("borrowingDays",lcAppoint.get("applyTnr"));
										fundCode = true;
										map.put("dayRate", 0.0306);
										map.put("overdueRate", 0.0306);
									}
								}
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} 
			map.put("fundCode", fundCode);
			map.put("lendingDto", lendingDto);
			// 回款退费信息
			ReceivableHasDto receivableHasDto = receivableHasService.findByReceivableHas(receivableForDto);
			map.put("receivableHasDto", receivableHasDto);
			rd.setCode(RespStatusEnum.SUCCESS.getCode());
			rd.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			rd.setCode(RespStatusEnum.FAIL.getCode());
			rd.setMsg(RespStatusEnum.FAIL.getMsg());
		}
		rd.setData(map);
		return rd;
	}
	
	@RequestMapping(value = "/detail")
	public @ResponseBody RespDataObject<List<ReceivableForDto>> detail(
			HttpServletRequest request,
			@RequestBody ReceivableForDto receivableForDto) {
		RespDataObject<List<ReceivableForDto>> rd = new RespDataObject<List<ReceivableForDto>>();
		try {
			// 回款信息
			List<ReceivableForDto> list = receivableForService
					.findByReceivableFor(receivableForDto);
			// 回款退费信息
			ReceivableHasDto receivableHasDto = receivableHasService
					.findByReceivableHas(receivableForDto);
			LendingDto lendingDto = lendingService
					.selectLendingTime(receivableForDto.getOrderNo());
			if (list != null && list.size() > 0) {
				if (receivableHasDto != null)
					list.get(0).setRemark(receivableHasDto.getRemark()); // 备注信息
				if (lendingDto != null)
					list.get(0).setCustomerPaymentTimeStr(
							lendingDto.getCustomerPaymentTimeStr()); // 预计回款时间
				list.get(0).setHuaRongDto(new ReceivableForToHuaRongDto()); // 推送信息
			}
			rd.setData(list);
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
	 * 详情 new 2017-08-23
	 * 
	 * @param request
	 * @param receivableForDto
	 * @return
	 */
	@RequestMapping(value = "/details")
	public @ResponseBody RespDataObject<Map<String, Object>> details(
			HttpServletRequest request,
			@RequestBody ReceivableForDto receivableForDto) {
		RespDataObject<Map<String, Object>> rd = new RespDataObject<Map<String, Object>>();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			// 回款信息
			List<ReceivableForDto> list = receivableForService
					.findByReceivableFor(receivableForDto);
			// 回款退费信息
			ReceivableHasDto receivableHasDto = receivableHasService
					.findByReceivableHas(receivableForDto);
			LendingDto lendingDto = lendingService
					.selectLendingTime(receivableForDto.getOrderNo());
			// 查询推送信息
			Map<String, Object> paramt = new HashMap<String, Object>();
			paramt.put("orderNo", receivableForDto.getOrderNo());
			RespDataObject<Map<String, Object>> obj = httpUtil
					.getRespDataObject(Constants.LINK_CREDIT,
							"/credit/third/api/hr/queryRayMentPlan", paramt,
							Map.class);
			if (obj.getData() != null)
				map.put("auditMap", obj.getData());
			if (receivableHasDto != null)
				map.put("remark", receivableHasDto.getRemark());
			if (lendingDto != null)
				map.put("customerPaymentTimeStr",
						lendingDto.getCustomerPaymentTimeStr());// //预计回款时间
			map.put("list", list);
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
	 * 一次性回款
	 * 
	 * @param request
	 * @param receivableForDto
	 * @return
	 */
	@RequestMapping(value = "/add")
	public @ResponseBody RespStatus add(HttpServletRequest request,
			@RequestBody ReceivableForDto receivableForDto) {
		RespStatus respStatus = new RespStatus();
		try {
			// 判断当前节点
//			if (!isSubmit(receivableForDto.getOrderNo(), "receivableFor")) {
				String payUid = receivableForDto.getCreateUid(); // 付费处理人
				respStatus.setCode(RespStatusEnum.SUCCESS.getCode());
				respStatus.setMsg(RespStatusEnum.SUCCESS.getMsg());
				OrderBaseBorrowDto borrowDto = new OrderBaseBorrowDto();
				borrowDto.setOrderNo(receivableForDto.getOrderNo());
				RespDataObject<OrderBaseBorrowDto> baseobj = httpUtil
						.getRespDataObject(Constants.LINK_CREDIT,
								"/credit/order/borrowother/v/queryBorrow",
								borrowDto, OrderBaseBorrowDto.class);
				borrowDto = baseobj.getData();
				UserDto userDto = getUserDto(request);
				String uid = userDto.getUid();
				receivableForDto.setCreateUid(uid);
				receivableForDto.setUpdateUid(uid);
				if(borrowDto!=null){
					receivableForDto.setAgencyId(borrowDto.getAgencyId());
					receivableForDto.setProductCode(borrowDto.getProductCode());
				}
				int i = receivableForService.addReceivableFor(receivableForDto);
				if (i == -1) {
					respStatus
							.setCode(RespStatusEnum.PARAMETER_ERROR.getCode());
					respStatus.setMsg("参数不合格");
					return respStatus;
				}
				if("06".equals(borrowDto.getProductCode())||"07".equals(borrowDto.getProductCode())) {//平台化，通道类业务，走到已完结
					OrderFlowDto orderFlowDto = new OrderFlowDto();
					OrderListDto listDto = new OrderListDto();
					orderFlowDto.setOrderNo(receivableForDto.getOrderNo());
					orderFlowDto.setCurrentProcessId("receivableFor");
					orderFlowDto.setCurrentProcessName("回款");
					orderFlowDto.setHandleUid(userDto.getUid()); // 当前处理人
					orderFlowDto.setHandleName(userDto.getName());
					orderFlowDto.setNextProcessId("wanjie");
					orderFlowDto.setNextProcessName("已完结");
					listDto.setOrderNo(receivableForDto.getOrderNo());
					listDto.setCurrentHandlerUid("-");
					listDto.setCurrentHandler("-");
					listDto.setProcessId("wanjie");
					listDto.setState("已完结");
					goNextNode(orderFlowDto, listDto); // 流程方法
					respStatus.setCode(RespStatusEnum.SUCCESS.getCode());
					respStatus.setMsg(RespStatusEnum.SUCCESS.getMsg());
					return respStatus;
				}
				
				// 更新借款表 是否回款状态
//				if(borrowDto!=null){
//					OrderBaseBorrowDto orderBaseBorrowDto = new OrderBaseBorrowDto();
//					orderBaseBorrowDto.setOrderNo(receivableForDto.getOrderNo());
//					orderBaseBorrowDto.setIsOnePay(1); // 一次性回款
//					orderBaseBorrowDto.setUpdateUid(uid);
//					orderBaseBorrowDto.setRate(-1d);
//					orderBaseBorrowDto.setRiskGradeId(-1);
//					RespStatus obj = httpUtil.getRespStatus(Constants.LINK_CREDIT,
//							"/credit/order/borrowother/v/updateBorrow",
//							orderBaseBorrowDto);
//					log.info("---------receivableFor-add-updateOrderBorrow:---------"
//							+ obj.getCode());
//				}
				// 修改流程
				OrderFlowDto orderFlowDto = new OrderFlowDto();
				orderFlowDto.setOrderNo(receivableForDto.getOrderNo());
				orderFlowDto.setCurrentProcessId("receivableFor");
				orderFlowDto.setCurrentProcessName("回款");
				orderFlowDto.setHandleUid(userDto.getUid()); // 当前处理人
				orderFlowDto.setHandleName(userDto.getName());
				OrderListDto listDto = new OrderListDto();
				if (i == 3) {
					if ((borrowDto != null && "03".equals(borrowDto.getProductCode()))) { // 畅待
							//V3.2版本
							if(receivableForDto.getRelationOrderNo()!=null && !"0".equals(receivableForDto.getRelationOrderNo())){ //关联置换贷
								LendingHarvestDto harvestDto =new LendingHarvestDto();
								harvestDto.setOrderNo(receivableForDto.getOrderNo());
								harvestDto=lendingHarvestService.findByHarvest(harvestDto);
								if(harvestDto!=null && harvestDto.getReturnMoney()>0){
									orderFlowDto.setNextProcessId("rebate");
									orderFlowDto.setNextProcessName("返佣");
									listDto.setCurrentHandlerUid(payUid);// 下一处理
									UserDto dto = CommonDataUtil.getUserDtoByUidAndMobile(payUid);
									listDto.setCurrentHandler(dto.getName());
								}else{
									//V3.0版本
									orderFlowDto.setNextProcessId("wanjie");
									orderFlowDto.setNextProcessName("已完结");
									listDto.setOrderNo(receivableForDto.getOrderNo());
									listDto.setCurrentHandlerUid("-");
									listDto.setCurrentHandler("-");
									listDto.setProcessId("wanjie");
									listDto.setState("已完结");
								}
							}else{ //不关联置换
								orderFlowDto.setNextProcessId("elementReturn");
								orderFlowDto.setNextProcessName("要件退还");
								// 查询要件管理员信息
								OrderBaseBorrowDto baseBorrowDto = baseobj.getData();
								listDto.setCurrentHandlerUid(baseBorrowDto
										.getElementUid());// 下一处理人要件管理员
								listDto.setCurrentHandler(baseBorrowDto
										.getElementName());
							}
						
					} else {
						// ---------准时回款，更新二级流程节点到要件退回
						orderFlowDto.setNextProcessId("elementReturn");
						orderFlowDto.setNextProcessName("要件退还");
						// 查询要件管理员信息
						OrderBaseBorrowDto baseBorrowDto = baseobj.getData();
						listDto.setCurrentHandlerUid(baseBorrowDto
								.getElementUid());// 下一处理人要件管理员
						listDto.setCurrentHandler(baseBorrowDto
								.getElementName());
					}
				}
				if (i == 4) {
					// 节点处理---------逾期或者提前回款更新流程节点
					orderFlowDto.setNextProcessId("pay");
					orderFlowDto.setNextProcessName("收罚息");
					LendingPayDto lendingPayDto = new LendingPayDto();
					lendingPayDto.setOrderNo(receivableForDto.getOrderNo());
					// LendingPayDto
					// payDto=lendingPayService.findByPay(lendingPayDto);
					listDto.setCurrentHandlerUid(payUid);// 下一处理人出纳
					UserDto dto = CommonDataUtil.getUserDtoByUidAndMobile(payUid);
					listDto.setCurrentHandler(dto.getName());
				}
				goNextNode(orderFlowDto, listDto); // 流程方法
				// ==============发送短信Start===================
				String ipWhite = ConfigUtil.getStringValue(
						Constants.BASE_AMS_IPWHITE, ConfigUtil.CONFIG_BASE); // ip
				String cont = borrowDto.getBorrowerName() + " "
						+ borrowDto.getLoanAmount();
				// 受理经理
				UserDto acceptMemberUser = CommonDataUtil.getUserDtoByUidAndMobile(borrowDto
						.getAcceptMemberUid());
				// 渠道经理
				UserDto channelManagerUid = CommonDataUtil.getUserDtoByUidAndMobile(borrowDto
						.getChannelManagerUid());
				if (borrowDto != null && "03".equals(borrowDto.getProductCode())) { // 畅待
					if (StringUtil.isNotEmpty(acceptMemberUser.getMobile())) {
						AmsUtil.smsSend(acceptMemberUser.getMobile(), ipWhite,
								Constants.SMS_CDLENDINGFOR, cont);
					}
					if (StringUtil.isNotEmpty(channelManagerUid.getMobile())) {
						AmsUtil.smsSend(channelManagerUid.getMobile(), ipWhite,
								Constants.SMS_CDLENDINGFOR, cont);
					}
				} else { // 置换贷
					if (StringUtil.isNotEmpty(acceptMemberUser.getMobile())) {
						AmsUtil.smsSend(acceptMemberUser.getMobile(), ipWhite,
								Constants.SMS_LENDINGFOR, cont);
					}
					if (StringUtil.isNotEmpty(channelManagerUid.getMobile())) {
						AmsUtil.smsSend(channelManagerUid.getMobile(), ipWhite,
								Constants.SMS_LENDINGFOR, cont);
					}
				}
				// ==============发送短信end===================
				// =====================推送融安数据Start=================
				// 查询是否推送过融安，推送过就跳过

//				if (receivableForDto.getIsRongAn() == 1) {
//					ReceivableForToHuaRongDto huaRongDto = receivableForDto
//							.getHuaRongDto();
//					huaRongDto.setOrderNo(receivableForDto.getOrderNo());
//					if (huaRongDto.getLateDays() > 0) { // 逾期
//						huaRongDto.setPpErInd("Y");
//					}
//					RespStatus status = httpUtil.getRespStatus(
//							Constants.LINK_CREDIT,
//							"/credit/third/api/hr/v/rayMentPlan", huaRongDto); // 发送回款计划
//					if ("FAIL".equals(status.getCode())) {
//						respStatus.setCode(status.getCode());
//						respStatus.setMsg("推送融安信息失败！");
//					}
//				}
				// =====================推送融安数据end=================
				

				//============修改机构剩余额度Start=============
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("agencyId", borrowDto.getCooperativeAgencyId());//合作机构ID
				AgencyDto agencyDto = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/customer/agency/v/getAgencyDto", map, AgencyDto.class);
				log.info("兜底模式："+agencyDto==null?"null":agencyDto.getCooperativeModeId());
				if(agencyDto!=null && agencyDto.getCooperativeModeId()==1){//判断 是否有保证金  有则判断授信  无则继续流程
					ReceivableForDto forDto =receivableForDto.getForList().get(0);  
					double pay = Double.valueOf(forDto.getPayMentAmount()+"");
					//修改机构剩余额度
					Double loanAmount=agencyDto.getSurplusQuota()+pay;  //剩余额度 + 回款金额 = 新剩余额度 (回款)
					log.info("新剩余额度：id:"+borrowDto.getCooperativeAgencyId()+"---额度"+loanAmount+" orderNo="+receivableForDto.getOrderNo());
					Map<String,Object> updMap = new HashMap<String, Object>();
					updMap.put("id", borrowDto.getCooperativeAgencyId());
					updMap.put("surplusQuota", loanAmount);
					RespDataObject<Integer>  resp =httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/customer/agency/v/updSurplusQuota", updMap,Integer.class);  
					log.info("回款-修改机构剩返回参数="+resp);
				}
				
				//=============修改机构剩余额度end=================
			/**
			 * 更新回款报备信息
			 */
			PaymentReportDto paymentreportDto = new PaymentReportDto();
			paymentreportDto.setStatus(1);
			paymentreportDto.setOrderNo(receivableForDto.getOrderNo());
			paymentReportService.update(paymentreportDto);
//			} else {
//				respStatus.setCode(RespStatusEnum.NOADOPT_ERROR.getCode());
//				respStatus.setMsg("该订单已被处理，请刷新列表查看");
//			}
		} catch (Exception e) {
			e.printStackTrace();
			respStatus.setCode(RespStatusEnum.FAIL.getCode());
			respStatus.setMsg(RespStatusEnum.FAIL.getMsg());
		}
		return respStatus;
	}

	/**
	 * 多次回款
	 * 
	 * @param request
	 * @param receivableForDto
	 * @return
	 */
	@RequestMapping(value = "/addToMany")
	public @ResponseBody RespData<ReceivableForDto> addToMany(
			HttpServletRequest request,
			@RequestBody ReceivableForDto receivableForDto) {
		RespData<ReceivableForDto> rd = new RespData<ReceivableForDto>();
		try {
			// 判断当前节点
			String processId = "";
			List<ReceivableForDto> forList = receivableForService
					.findByReceivableFor(receivableForDto);
			if (forList != null && forList.size() > 0) {
				processId = "receivableForEnd"; // 尾期
			} else {
				processId = "receivableForFirst"; // 首期
			}
			if (!isSubmit(receivableForDto.getOrderNo(), processId)) {
				String payUid = receivableForDto.getCreateUid();
				rd.setCode(RespStatusEnum.SUCCESS.getCode());
				rd.setMsg(RespStatusEnum.SUCCESS.getMsg());
				UserDto userDto = getUserDto(request);
				String uid = userDto.getUid();
				receivableForDto.setCreateUid(uid);
				receivableForDto.setUpdateUid(uid);
				receivableForDto.setOneTimePay(0);// 否
				//查询基本信息
				OrderBaseBorrowDto borrowDto = new OrderBaseBorrowDto();
				borrowDto.setOrderNo(receivableForDto.getOrderNo());
				RespDataObject<OrderBaseBorrowDto> baseobj = httpUtil
						.getRespDataObject(Constants.LINK_CREDIT,
								"/credit/order/borrowother/v/queryBorrow",
								borrowDto, OrderBaseBorrowDto.class);
				OrderBaseBorrowDto baseBorrowDto = baseobj.getData();
				if(baseBorrowDto!=null){
					receivableForDto.setAgencyId(baseBorrowDto.getAgencyId());
					receivableForDto.setProductCode(baseBorrowDto.getProductCode());
				}
				// 抵押是否完成
				MortgageDto mortgageDto = new MortgageDto();
				mortgageDto.setOrderNo(receivableForDto.getOrderNo());
				RespDataObject<MortgageDto> obj = httpUtil.getRespDataObject(
						Constants.LINK_CREDIT,
						"/credit/process/mortgage/v/detail", mortgageDto,
						MortgageDto.class); // 流程项目
				MortgageDto dto = obj.getData();
				if (dto == null || dto.getMortgageEndTime() == null) {
					receivableForDto.setIsMortgage(1); // 未抵押
				}
				List<ReceivableForDto> list = receivableForService
						.findByReceivableFor(receivableForDto);
				if(baseBorrowDto!=null){
					receivableForDto.setAgencyId(baseBorrowDto.getAgencyId());
					receivableForDto.setProductCode(baseBorrowDto.getProductCode());
				}
				int x = receivableForService
						.addReceivableForToMany(receivableForDto);
				
				
				if (x == -1) {
					rd.setCode(RespStatusEnum.FAIL.getCode());
					rd.setMsg("回款金额超过借款金额或者请等待抵押完成");
					return rd;

				}
				if (x == -2) {
					rd.setCode(RespStatusEnum.FAIL.getCode());
					rd.setMsg("多次回款不能一次回完，要等待抵押后再回尾期款");
					return rd;
				}
				if (x == -10) {
					rd.setCode(RespStatusEnum.FAIL.getCode());
					rd.setMsg("实际回款时间与回款金额未输入，请先点击添加一行");
					return rd;
				}
//				if(list.size() == 0 && borrowDto!=null){ // 添加第一次修改回款状态，其他不用再修改
//					// 更新借款表 是否一次性回款状态
//					OrderBaseBorrowDto orderBaseBorrowDto = new OrderBaseBorrowDto();
//					orderBaseBorrowDto
//							.setOrderNo(receivableForDto.getOrderNo());
//					orderBaseBorrowDto.setUpdateUid(userDto.getUid());
//					orderBaseBorrowDto.setIsOnePay(2);
//					orderBaseBorrowDto.setRate(-1d);
//					orderBaseBorrowDto.setRiskGradeId(-1);
//					RespStatus respStatus = httpUtil.getRespStatus(
//							Constants.LINK_CREDIT,
//							"/credit/order/borrowother/v/updateBorrow",
//							orderBaseBorrowDto);
//					log.info("---------receivableFor-addToMany-updateOrderBorrow:---------"
//							+ respStatus.getCode());
//				}
				OrderFlowDto orderFlowDto = new OrderFlowDto();
				OrderListDto listDto = new OrderListDto();
				if (x > 0) {
					orderFlowDto.setOrderNo(receivableForDto.getOrderNo());
					orderFlowDto.setCurrentProcessId("receivableForEnd");
					orderFlowDto.setCurrentProcessName("回款（尾期）");
					orderFlowDto.setHandleUid(userDto.getUid()); // 当前处理人
					orderFlowDto.setHandleName(userDto.getName());
					listDto.setCurrentHandlerUid(userDto.getUid());// 下一处理人会计（与当前处理人一样）
					listDto.setCurrentHandler(userDto.getName());
				}
				if (x == 4) {// 提前/逾期 （总额达标，更新流程节点到待付费）
					orderFlowDto.setNextProcessId("pay");
					orderFlowDto.setNextProcessName("收罚息");
					LendingPayDto lendingPayDto = new LendingPayDto();
					lendingPayDto.setOrderNo(receivableForDto.getOrderNo());
					// LendingPayDto
					// payDto=lendingPayService.findByPay(lendingPayDto);
					listDto.setCurrentHandlerUid(payUid);// 下一处理人出纳
					UserDto dto2 = CommonDataUtil.getUserDtoByUidAndMobile(payUid);
					listDto.setCurrentHandler(dto2.getName());
					/**
					 * 更新回款报备信息
					 */
					PaymentReportDto paymentreportDto = new PaymentReportDto();
					paymentreportDto.setStatus(1);
					paymentreportDto.setOrderNo(receivableForDto.getOrderNo());
					paymentReportService.update(paymentreportDto);
				}
				if (x == 5) {// 准时还款 （总额达标，更新流程到要件管理）
					orderFlowDto.setNextProcessId("elementReturn");
					orderFlowDto.setNextProcessName("要件退还");
					// 查询要件管理员信息
					listDto.setCurrentHandlerUid(baseBorrowDto.getElementUid());// 下一处理人要件管理员
					listDto.setCurrentHandler(baseBorrowDto.getElementName());
					/**
					 * 更新回款报备信息
					 */
					PaymentReportDto paymentreportDto = new PaymentReportDto();
					paymentreportDto.setStatus(1);
					paymentreportDto.setOrderNo(receivableForDto.getOrderNo());
					paymentReportService.update(paymentreportDto);
				}
				if (x == 3) {// 首次回款更新流程节点到抵押
					orderFlowDto.setCurrentProcessId("receivableForFirst");
					orderFlowDto.setCurrentProcessName("回款（首期）");
					orderFlowDto.setNextProcessId("mortgage");
					orderFlowDto.setNextProcessName("抵押");
					// 查询抵押信息
					MortgageDto mortDto = new MortgageDto();
					mortDto.setOrderNo(receivableForDto.getOrderNo());
					RespDataObject<MortgageDto> mobj = httpUtil.getRespDataObject(Constants.LINK_CREDIT,"/credit/process/mortgage/v/detail",mortDto, MortgageDto.class);
					MortgageDto mortgageDto2 = mobj.getData();
					UserDto userDto2 = CommonDataUtil.getUserDtoByUidAndMobile(mortgageDto2.getMlandBureauUid());
					// 填充列表抵押日期，抵押地点
					try {
						listDto.setAppShowValue1(DateUtil.getDateByFmt(
								mortgageDto2.getMortgageTime(),
								DateUtil.FMT_TYPE2));
						listDto.setAppShowValue2(getBureauName(mortgageDto2
								.getMlandBureau()));
					} catch (Exception e) {
						e.printStackTrace();
					}
					listDto.setCurrentHandlerUid(mortgageDto2
							.getMlandBureauUid());// 下一处理人抵押
					listDto.setCurrentHandler(userDto2.getName());
				}
				if (x > 0) {
					goNextNode(orderFlowDto, listDto); // 流程方法
					// ==============发送短信Start===================
					String ipWhite = ConfigUtil.getStringValue(
							Constants.BASE_AMS_IPWHITE, ConfigUtil.CONFIG_BASE); // ip

					String cont = baseBorrowDto.getBorrowerName() + " "
							+ baseBorrowDto.getLoanAmount();
					// 受理经理
					UserDto acceptMemberUser = CommonDataUtil.getUserDtoByUidAndMobile(baseBorrowDto
							.getAcceptMemberUid());
					// 渠道经理
					UserDto channelManagerUid = CommonDataUtil.getUserDtoByUidAndMobile(baseBorrowDto
							.getChannelManagerUid());
					List<ReceivableForDto> forDto = receivableForDto
							.getNewForList();
					for (int k = 0; k < forDto.size(); k++) {
						BigDecimal amount = forDto.get(k).getPayMentAmount();
						if (StringUtil.isNotEmpty(acceptMemberUser.getMobile())) {
							AmsUtil.smsSend(acceptMemberUser.getMobile(),
									ipWhite, Constants.SMS_LENDINGFORS, cont,
									amount);
						}
						if (StringUtil
								.isNotEmpty(channelManagerUid.getMobile())) {
							AmsUtil.smsSend(channelManagerUid.getMobile(),
									ipWhite, Constants.SMS_LENDINGFORS, cont,
									amount);
						}
					}
					// ==============发送短信end===================
				}
				// =====================推送融安数据Start=================
//				if (receivableForDto.getIsRongAn() == 1) {
//					ReceivableForToHuaRongDto huaRongDto = receivableForDto
//							.getHuaRongDto();
//					huaRongDto.setOrderNo(receivableForDto.getOrderNo());
//					if (huaRongDto.getLateDays() > 0) { // 逾期
//						huaRongDto.setPpErInd("Y");
//					}
//					RespStatus status = httpUtil.getRespStatus(
//							Constants.LINK_CREDIT,
//							"/credit/third/api/hr/v/rayMentPlan", huaRongDto); // 发送回款计划
//					if ("FAIL".equals(status.getCode())) {
//						rd.setCode(status.getCode());
//						rd.setMsg("推送融安信息失败！");
//					}
//				}
				// =====================推送融安数据end=================

				//============修改机构剩余额度Start=============
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("agencyId", baseBorrowDto.getCooperativeAgencyId());//合作机构ID
				AgencyDto agencyDto = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/customer/agency/v/getAgencyDto", map, AgencyDto.class);
				log.info("兜底模式："+agencyDto==null?"null":agencyDto.getCooperativeModeId());
				if(agencyDto!=null && agencyDto.getCooperativeModeId()==1){//判断 是否有保证金  有则判断授信  无则继续流程
//					List<ReceivableForDto> forDto =receivableForDto.getForList();  
					double money = 0;
					ReceivableForDto dto2=new ReceivableForDto();
					dto2.setOrderNo(receivableForDto.getOrderNo());
					if ("receivableForEnd" == processId ) {
						dto2.setIsFrist(2);
					} else  {  //首期
						dto2.setIsFrist(1);
					}
					List<ReceivableForDto> forDto=receivableForService.findByReceivableFor(dto2);
					for(ReceivableForDto fdto:forDto){
						double pay = Double.valueOf(fdto.getPayMentAmount()+"");
						money = money + pay;
					}
					//修改机构剩余额度
					Double loanAmount=agencyDto.getSurplusQuota()+money;  //剩余额度 + 回款金额 = 新剩余额度
					log.info("回款：新剩余额度 ：id:"+baseBorrowDto.getCooperativeAgencyId()+"新额度"+loanAmount+"orderNo="+receivableForDto.getOrderNo());
					Map<String,Object> updMap = new HashMap<String, Object>();
					updMap.put("id", baseBorrowDto.getCooperativeAgencyId());
					updMap.put("surplusQuota", loanAmount);
					RespDataObject<Integer>  resp =httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/customer/agency/v/updSurplusQuota", updMap,Integer.class);  
					log.info("回款-修改机构剩余额度返回参数="+resp);
				}
				//============修改机构剩余额度end=============
			} else {
				rd.setCode(RespStatusEnum.NOADOPT_ERROR.getCode());
				rd.setMsg("该订单已被处理，请刷新列表查看");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info("receivableForDto-addToMany Exception ==>", e);
			rd.setCode(RespStatusEnum.FAIL.getCode());
			rd.setMsg(RespStatusEnum.FAIL.getMsg());
		}
		return rd;
	}

	/**
	 * 根据机构回款时间查询订单
	 * @Author KangLG<2017年11月9日>
	 * @param request
	 * @param dto agencyId/payMentAmountDateStart/payMentAmountDateEnd
	 * @return 订单号字符串数组
	 */
	@ResponseBody
	@RequestMapping(value = "/search4AgencyOrderTime")
	public RespDataObject<String> search4AgencyOrderTime(HttpServletRequest request,  @RequestBody ReceivableForDto dto) {
		RespDataObject<String> rd = new RespDataObject<String>();
		try {			
			List<String> lstOrderNO = this.receivableForService.search4AgencyOrderTime(dto);
			return RespHelper.setSuccessDataObject(rd, null!=lstOrderNO&&!lstOrderNO.isEmpty() ? StringUtils.join(lstOrderNO, ",") : "N");
		} catch (Exception e) {
			e.printStackTrace();			
		}
		return RespHelper.setFailDataObject(rd, null, RespStatusEnum.FAIL.getMsg());
	}
	/**
	 * 回款报表查询
	 * @Author KangLG<2017年11月9日>
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/report4Chart")
	public ChartVo reportLending4Chart(HttpServletRequest request, ReceivableForDto dto) {
		dto.setAgencyId(dto.getAgencyId()>0 ? dto.getAgencyId() : this.getUserDto(request).getAgencyId());
		//看单权限
//		String orderNos = new HttpUtil(true).getObject("http://127.0.0.1:9117", "/credit/order/base/v/listNos4Authority", null, String.class);
		String orderNos = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/order/base/v/listNos4Authority", null, String.class);		
		dto.setOrderNo(orderNos);
		
		return this.receivableForService.search4reportChart(dto, 12);
	}
	@ResponseBody
	@RequestMapping(value = "/report")
	public RespPageData<ReceivableForDto> report(HttpServletRequest request,  @RequestBody ReceivableForDto dto) {
		RespPageData<ReceivableForDto> resp = new RespPageData<ReceivableForDto>();
		try {
			dto.setAgencyId(dto.getAgencyId()>0 ? dto.getAgencyId() : this.getUserDto(request).getAgencyId());
			dto.setPayMentAmountDateStart(DateUtils.dateToString(org.apache.commons.lang.time.DateUtils.addMonths(new Date(), -11), "yyyy-MM"));
			//看单权限
//			String orderNos = new HttpUtil(true).getObject("http://127.0.0.1:9117", "/credit/order/base/v/listNos4Authority", null, String.class);
			String orderNos = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/order/base/v/listNos4Authority", null, String.class);		
			dto.setOrderNo(orderNos);
			
			resp.setRows(this.receivableForService.search4report(dto));
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			e.printStackTrace();	
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}
	@ResponseBody
	@RequestMapping(value = "/toHuarong")
	public RespStatus toHuarong(HttpServletRequest request,  @RequestBody ReceivableForDto receivableForDto) {
		RespStatus resp = new RespStatus();
		resp.setCode(RespStatusEnum.SUCCESS.getCode());
		resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		ReceivableForToHuaRongDto huaRongDto = receivableForDto.getHuaRongDto();
		huaRongDto.setOrderNo(receivableForDto.getOrderNo());
		if (huaRongDto.getLateDays() > 0) { // 逾期
		huaRongDto.setPpErInd("Y");
		}
		RespStatus status = httpUtil.getRespStatus(Constants.LINK_CREDIT,"/credit/third/api/hr/v/rayMentPlan", huaRongDto); // 发送回款计划
		if ("FAIL".equals(status.getCode())) {
			resp.setCode(status.getCode());
			resp.setMsg("推送融安信息失败！");
		}
		return resp;
	}
}
