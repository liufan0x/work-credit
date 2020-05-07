package com.anjbo.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.bean.finance.LendingDto;
import com.anjbo.bean.finance.ReceivableForDto;
import com.anjbo.bean.order.OrderBaseBorrowDto;
import com.anjbo.bean.order.OrderFlowDto;
import com.anjbo.bean.order.OrderListDto;
import com.anjbo.bean.product.CancellationDto;
import com.anjbo.bean.product.FaceSignDto;
import com.anjbo.bean.product.ForeclosureDto;
import com.anjbo.bean.product.ForensicsDto;
import com.anjbo.bean.product.MortgageDto;
import com.anjbo.bean.product.NewlicenseDto;
import com.anjbo.bean.product.NotarizationDto;
import com.anjbo.bean.product.ProductProcessDto;
import com.anjbo.bean.product.TransferDto;
import com.anjbo.common.Constants;
import com.anjbo.common.DateUtil;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.OrderBaseBorrowService;
import com.anjbo.service.OrderBaseService;
import com.anjbo.service.OrderBaseThirdService;
import com.anjbo.service.OrderFlowService;
import com.anjbo.utils.CommonDataUtil;
import com.anjbo.utils.ConfigUtil;
import com.anjbo.utils.MD5Utils;
import com.anjbo.utils.Rc4Util;

@Controller
@RequestMapping("/credit/order/base/third")
public class OrderBaseThirdController extends BaseController{
	
	Logger log = Logger.getLogger(OrderBaseThirdController.class);

	@Resource
	private OrderBaseThirdService orderBaseThirdService;
	@Resource
	private OrderBaseService orderBaseService;
	@Resource
	private OrderBaseBorrowService orderBaseBorrowService;
	@Resource
	private OrderFlowService orderFlowService;
	
	/**
	 * 蓝蜗牛通过h5页面查看订单列表
	 * @param request
	 * @param orderListDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/appList") 
	public RespDataObject<Map<String, Object>> list(HttpServletRequest request, @RequestBody Map<String,Object> map){
		RespDataObject<Map<String, Object>> resp = new RespDataObject<Map<String, Object>>();
		resp.setCode(RespStatusEnum.FAIL.getCode());
		resp.setMsg(RespStatusEnum.FAIL.getMsg());
		try {
			String key = "67dd646190413680e8c8874f";
			String dateStr = Rc4Util.rc4Encrypt(DateUtil.getNowyyyyMMdd(new Date()));//16
			//验证秘钥
			if(MD5Utils.MD5Encode(dateStr+key+MapUtils.getString(map, "phoneNumber")).equalsIgnoreCase(MapUtils.getString(map, "source"))){
				List<OrderListDto> orderListDtos = orderBaseThirdService.selectOrderList(map);
				Map<String, Object> tempMap = null;
				String cityName = "";
				String productName = "";
				String productNamePrefix = "";
				String productNameSuffix = "";
				List<Map<String, Object>> orderList = new ArrayList<Map<String,Object>>();
				Map<String, Object> retMap = new HashMap<String, Object>();
				for (OrderListDto orderDto : orderListDtos) {

					tempMap = new HashMap<String, Object>();
					cityName = "";
					productName = "";
					if(StringUtils.isNotBlank(orderDto.getCityName())){
						cityName = orderDto.getCityName().replace("市", "");
					}
					if(StringUtils.isNotBlank(orderDto.getProductName())){
						productName = orderDto.getProductName().replace("债务", "");
						if(productName.contains("畅贷")){
							productNamePrefix = "畅贷";
						}else if(productName.contains("房抵贷")){
							productNamePrefix = "房抵贷";
						}else{
							productNamePrefix = "置换贷款";
						}
						if(orderDto.getProductName().contains("非交易类")){
							productNameSuffix = "非交易类";
						}else if(orderDto.getProductName().contains("交易类")){
							productNameSuffix = "交易类";
						}
					}
					//建行云按揭
					tempMap.put("orderNo", orderDto.getOrderNo());
					tempMap.put("customerName", orderDto.getCustomerName()==null?"":orderDto.getCustomerName());
					tempMap.put("cityName", cityName);
					tempMap.put("cityCode", orderDto.getCityCode());
					tempMap.put("productCode",orderDto.getProductCode());
					tempMap.put("productName",productName);
					tempMap.put("productNamePrefix", productNamePrefix);
					tempMap.put("productNameSuffix", productNameSuffix);
					tempMap.put("borrowingAmount", orderDto.getBorrowingAmount()==null?"-":orderDto.getBorrowingAmount());
					tempMap.put("borrowingDay", orderDto.getBorrowingDay());
					if("10000".equals(orderDto.getProductCode())&&(orderDto.getState().contains("已关闭")||orderDto.getState().contains("待提交申请按揭"))){//不显示右上角金额，期限
						tempMap.put("borrowingAmount", "-");
						tempMap.put("borrowingDay", 0);
					}
					tempMap.put("channelManagerName", orderDto.getChannelManagerName()==null?"-":orderDto.getChannelManagerName());
					tempMap.put("cooperativeAgencyName", orderDto.getCooperativeAgencyName()==null?"-":orderDto.getCooperativeAgencyName());
					tempMap.put("acceptMemberName", orderDto.getAcceptMemberName()==null?"-":orderDto.getAcceptMemberName());
					tempMap.put("state", orderDto.getState());
					tempMap.put("currentHandler", orderDto.getCurrentHandler());
					tempMap.put("processId", orderDto.getProcessId());
					tempMap.put("isRelationOrder", orderDto.getIsRelationOrder());

					boolean isChanLoan = !"03".equals(orderDto.getProductCode());

					if((orderDto.getProcessId().contains("auditFirst") 
						|| orderDto.getProcessId().contains("auditFinal") 
						|| orderDto.getProcessId().contains("auditOfficer") 
						|| orderDto.getProcessId().contains("repaymentMember") 
						|| orderDto.getProcessId().contains("foreclosure")
						|| orderDto.getProcessId().contains("applyLoan")
						|| orderDto.getProcessId().contains("financialAudit"))
						&&!"10000".equals(orderDto.getProductCode())
						){
						if(isChanLoan) {
							tempMap.put("appShowKey1", "预计出款");
							tempMap.put("appShowKey2", "结清原贷款");
							tempMap.put("appShowValue1", orderDto.getAppShowValue1()==null?"-":orderDto.getAppShowValue1());
							tempMap.put("appShowValue2", orderDto.getAppShowValue2());
						}
					}else if(orderDto.getState().contains("待取证")&&!orderDto.getState().contains("待取证抵押")){
						if(isChanLoan) {
							tempMap.put("appShowKey1", "预计取证");
							if(orderDto.getAppShowValue2()!=null&&orderDto.getAppShowValue2().contains("-")){
								tempMap.put("appShowKey2", "取证银行");
							}else{
								tempMap.put("appShowKey2", "取证地点");
							}
							tempMap.put("appShowValue1", orderDto.getAppShowValue1());
							tempMap.put("appShowValue2", orderDto.getAppShowValue2());
						}
					}else if(orderDto.getState().contains("待注销")){
						if(isChanLoan) {
							tempMap.put("appShowKey1", "预计注销");
							tempMap.put("appShowKey2", "国土局");
							tempMap.put("appShowValue1", orderDto.getAppShowValue1());
							tempMap.put("appShowValue2", orderDto.getAppShowValue2());
						}
					}else if(orderDto.getState().contains("待过户")){
						if(isChanLoan) {
							tempMap.put("appShowKey1", "预计过户");
							tempMap.put("appShowKey2", "国土局");
							tempMap.put("appShowValue1", orderDto.getAppShowValue1());
							tempMap.put("appShowValue2", orderDto.getAppShowValue2());
						}
					}else if(orderDto.getState().contains("待领新证")){
						if(isChanLoan) {
							tempMap.put("appShowKey1", "预计领新证");
							tempMap.put("appShowKey2", "国土局");
							tempMap.put("appShowValue1", orderDto.getAppShowValue1());
							tempMap.put("appShowValue2", orderDto.getAppShowValue2());
						}
					}else if("待抵押".equals(orderDto.getState())){
						if(isChanLoan) {
							tempMap.put("appShowKey1", "预计抵押");
							tempMap.put("appShowKey2", "国土局");
							tempMap.put("appShowValue1", orderDto.getAppShowValue1());
							tempMap.put("appShowValue2", orderDto.getAppShowValue2());
						}
					}
					orderList.add(tempMap);
				}
				retMap.put("orderList", orderList);
				RespHelper.setSuccessDataObject(resp, retMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}
	
	/**
	 * App详情
	 * @param request
	 * @param orderListDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/appDetail") 
	public RespDataObject<Map<String, Object>> appDetail(HttpServletRequest request, @RequestBody OrderListDto orderListDto){
		RespDataObject<Map<String, Object>> resp = new RespDataObject<Map<String,Object>>();
		try {
			String keys = "67dd646190413680e8c8874f";
			String dateStr = Rc4Util.rc4Encrypt(DateUtil.getNowyyyyMMdd(new Date()));//16
			//验证秘钥
			if(MD5Utils.MD5Encode(dateStr+keys+orderListDto.getPhoneNumber()).equalsIgnoreCase(orderListDto.getSource())){
				RespHelper.setFailDataObject(resp, null, RespStatusEnum.FAIL.getMsg());
				return resp;
			}
			Map<String, Object> retMap = new HashMap<String, Object>();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("pageClass", "tbl_sl_detail_page");
			map.put("regionClass", "slDetail");
			RespData<Map<String,Object>> respData = httpUtil.getRespData(Constants.LINK_CREDIT, "/credit/config/page/base/v/getPageTabRegionConfigDto", map, Map.class);
			List<Map<String,Object>> configlist = respData.getData();
			if(configlist.size()<12){
				RespHelper.setFailDataObject(resp, null, "数据库详情slDetail配置缺失数量(小于12)");
				return resp;
			}
			String orderNo =orderListDto.getOrderNo();
			orderListDto = orderBaseService.selectDetail(orderListDto.getOrderNo());
			boolean isRelationC = orderListDto.getProductCode().equals("03")&&StringUtils.isNotBlank(orderListDto.getRelationOrderNo());
			boolean isChangloan = "03".equals(orderListDto.getProductCode());
			int productId = 440301;
			if(orderListDto != null){
				try {
					productId = NumberUtils.toInt(orderListDto.getCityCode()+orderListDto.getProductCode());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("productId", productId);
			Map<String, Object> authMap = new HashMap<String, Object>();
			List<String> authList = new ArrayList<String>();
			String channelManagerUid ;
			OrderBaseBorrowDto baseBorrowDto = orderBaseBorrowService.selectOrderBorrowByOrderNo(orderNo);
			OrderBaseBorrowDto slBaseBorrowDto = new OrderBaseBorrowDto();
			if(isRelationC){
				slBaseBorrowDto = orderBaseBorrowService.selectOrderBorrowByOrderNo(orderListDto.getRelationOrderNo());
				channelManagerUid = slBaseBorrowDto.getChannelManagerUid();
			}else{
				channelManagerUid = baseBorrowDto.getChannelManagerUid();
			}
			List<OrderFlowDto> list = orderFlowService.selectOrderFlowList(orderListDto.getOrderNo());
			OrderFlowDto orderFlow = null;
			Iterator<OrderFlowDto> its = list.iterator();
			String orderNos = ConfigUtil.getStringValue(Constants.BASE_ORDER_EXAMPLE,ConfigUtil.CONFIG_BASE);
			
			List<ProductProcessDto> productProcessDtoList = getProductProcessDto(productId);
//			List<UserDto> userList = getUserDtoList();
			while(its.hasNext()){
				orderFlow = its.next();
				logger.info(orderFlow.getCurrentProcessId()+"----"+orderFlow.getCurrentProcessName());
				for (ProductProcessDto productProcessDto : productProcessDtoList) {
					if(orderFlow.getCurrentProcessId().equals(productProcessDto.getProcessId())){
						orderFlow.setCurrentProcessName(productProcessDto.getProcessName());
					}
					if(orderFlow.getNextProcessId().equals(productProcessDto.getProcessId())){
						orderFlow.setNextProcessName(productProcessDto.getProcessName());
					}
				}
				
				orderFlow.setHandleName(CommonDataUtil.getUserDtoByUidAndMobile(orderFlow.getHandleUid()).getName());
				
//				for (UserDto userDto : userList) {
//					if(userDto.getUid().equals(orderFlow.getHandleUid())){
//						orderFlow.setHandleName(CommonDataUtil.CommonDataUtil.getUserDtoByUidAndMobileAndMobile(orderFlow.getHandleUid()).getName());
//						break;
//					}
//				}
				
				orderFlow.setCreateTimeStr("系统提交时间："+DateUtil.getDateByFmt(orderFlow.getHandleTime(), DateUtil.FMT_TYPE1));
				if("notarization".equals(orderFlow.getCurrentProcessId())){
					NotarizationDto obj = new NotarizationDto();
					obj.setOrderNo(orderNo);
					obj = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/process/notarization/v/detail", obj, NotarizationDto.class);
					orderFlow.setHandleTimeStr("公证时间："+DateUtil.getDateByFmt(obj.getNotarizationTime(), DateUtil.FMT_TYPE2));
				}else if("facesign".equals(orderFlow.getCurrentProcessId())){
					FaceSignDto obj = new FaceSignDto();
					obj.setOrderNo(orderNo);
					obj = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/process/facesign/v/detail", obj, FaceSignDto.class);
					if(obj!=null&&obj.getFaceSignTime()!=null){
						orderFlow.setHandleTimeStr("面签时间："+DateUtil.getDateByFmt(obj.getFaceSignTime(), DateUtil.FMT_TYPE2));
					}
				}else if("lending".equals(orderFlow.getCurrentProcessId())){
					LendingDto  obj = new LendingDto();
					obj.setOrderNo(orderNo);
					obj = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/finance/lending/v/detail", obj, LendingDto.class);
					orderFlow.setHandleTimeStr("放款时间："+DateUtil.getDateByFmt(obj.getLendingTime(), DateUtil.FMT_TYPE2));
				}else if("foreclosure".equals(orderFlow.getCurrentProcessId())){
					ForeclosureDto  obj = new ForeclosureDto();
					obj.setOrderNo(orderNo);
					obj = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/process/foreclosure/v/detail", obj, ForeclosureDto.class);
					log.info("结清原贷款对象："+obj);
					log.info("结清原贷款时间："+obj.getForeclosureTime());
					orderFlow.setHandleTimeStr("结清时间："+DateUtil.getDateByFmt(obj.getForeclosureTime(), DateUtil.FMT_TYPE2));
				}else if("forensics".equals(orderFlow.getCurrentProcessId())){
					ForensicsDto  obj = new ForensicsDto();
					obj.setOrderNo(orderNo);
					obj = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/process/forensics/v/detail", obj, ForensicsDto.class);
					orderFlow.setHandleTimeStr("取证时间："+DateUtil.getDateByFmt(obj.getLicenseRevTime(),DateUtil.FMT_TYPE2));
				}else if("cancellation".equals(orderFlow.getCurrentProcessId())){
					CancellationDto  obj = new CancellationDto();
					obj.setOrderNo(orderNo);
					obj = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/process/cancellation/v/detail", obj, CancellationDto.class);
					orderFlow.setHandleTimeStr("注销时间："+DateUtil.getDateByFmt(obj.getCancelTime(),DateUtil.FMT_TYPE2));
				}else if("transfer".equals(orderFlow.getCurrentProcessId())){
					TransferDto  obj = new TransferDto();
					obj.setOrderNo(orderNo);
					obj = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/process/transfer/v/detail", obj, TransferDto.class);
					orderFlow.setHandleTimeStr("过户时间："+DateUtil.getDateByFmt(obj.getTransferTime(),DateUtil.FMT_TYPE2));
				}else if("newlicense".equals(orderFlow.getCurrentProcessId())){
					NewlicenseDto  obj = new NewlicenseDto();
					obj.setOrderNo(orderNo);
					obj = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/process/newlicense/v/detail", obj, NewlicenseDto.class);
					orderFlow.setHandleTimeStr("领新证时间："+DateUtil.getDateByFmt(obj.getNewlicenseTime(),DateUtil.FMT_TYPE2));
				}else if("mortgage".equals(orderFlow.getCurrentProcessId())){
					MortgageDto  obj = new MortgageDto();
					obj.setOrderNo(orderNo);
					obj = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/process/mortgage/v/detail", obj, MortgageDto.class);
					orderFlow.setHandleTimeStr("抵押时间："+DateUtil.getDateByFmt(obj.getMortgageTime(),DateUtil.FMT_TYPE2));
				}else if("receivableForFirst".equals(orderFlow.getCurrentProcessId())||"receivableForEnd".equals(orderFlow.getCurrentProcessId())||"receivableFor".equals(orderFlow.getCurrentProcessId())){
					ReceivableForDto obj = new ReceivableForDto();
					obj.setOrderNo(orderNo);
					List<ReceivableForDto> listFor = httpUtil.getList(Constants.LINK_CREDIT, "/credit/finance/receivableFor/v/detail", obj, ReceivableForDto.class);
					if(listFor!=null && listFor.size()>0){
						if("receivableForFirst".equals(orderFlow.getCurrentProcessId())){
							orderFlow.setHandleTimeStr("首期回款时间："+listFor.get(0).getPayMentAmountDate().substring(0, 10));
						}else if("receivableForEnd".equals(orderFlow.getCurrentProcessId())){
							orderFlow.setHandleTimeStr("尾期回款时间："+listFor.get(listFor.size()-1).getPayMentAmountDate().substring(0, 10));
						}else if("receivableFor".equals(orderFlow.getCurrentProcessId())){
							orderFlow.setHandleTimeStr("回款时间："+listFor.get(0).getPayMentAmountDate().substring(0, 10));
						}
					}
				}
				if(orderFlow.getCurrentProcessId().equals("wanjie")){
					continue;
				}
			}
			Map<String, Object> orderDeatil = new LinkedHashMap<String, Object>();
			String key = "";
			orderDeatil.put(MapUtils.getString(configlist.get(0), "title"), baseBorrowDto.getCityName());
			key += MapUtils.getString(configlist.get(0), "title")+",";
			orderDeatil.put(MapUtils.getString(configlist.get(1), "title"), baseBorrowDto.getProductName());
			key += MapUtils.getString(configlist.get(1), "title")+",";
			orderDeatil.put(MapUtils.getString(configlist.get(2), "title"), orderListDto.getCustomerName());
			key += MapUtils.getString(configlist.get(2), "title")+",";
			orderDeatil.put(MapUtils.getString(configlist.get(3), "title"), (baseBorrowDto.getLoanAmount()==null?"-":baseBorrowDto.getLoanAmount())+"万元");
			orderDeatil.put(MapUtils.getString(configlist.get(4), "title"), (baseBorrowDto.getBorrowingDays()==null?"-":baseBorrowDto.getBorrowingDays().toString())+"天");
			key += MapUtils.getString(configlist.get(3), "title")+",";
			key += MapUtils.getString(configlist.get(4), "title")+",";
			
			
//			Map<String, Object> paramsMap = new HashMap<String, Object>();
//			paramsMap.put("agencyId", baseBorrowDto.getCooperativeAgencyId());
//			AgencyDto agencyDto = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/customer/agency/v/getAgencyDto", paramsMap, AgencyDto.class);
			if(orderListDto.getCooperativeAgencyName() != null&&orderListDto.getAgencyId()<=1){
				orderDeatil.put(MapUtils.getString(configlist.get(5), "title"), orderListDto.getCooperativeAgencyName());
				key += MapUtils.getString(configlist.get(5), "title")+",";
			}else if(orderListDto.getCooperativeAgencyName()==null&&orderListDto.getAgencyId()<=1){
				key += MapUtils.getString(configlist.get(5), "title")+",";
				orderDeatil.put(MapUtils.getString(configlist.get(5), "title"), "-");
			}else{
				//机构订单没有合作机构
			}
			if(StringUtils.isNotBlank(baseBorrowDto.getChannelManagerUid())){
				orderDeatil.put(MapUtils.getString(configlist.get(6), "title"), CommonDataUtil.getUserDtoByUidAndMobile(channelManagerUid).getName());
			}else{
				orderDeatil.put(MapUtils.getString(configlist.get(6), "title"), "-");
			}
			key += MapUtils.getString(configlist.get(6), "title")+",";
			ForeclosureDto  obj = new ForeclosureDto();
			obj.setOrderNo(orderListDto.getOrderNo());
			obj = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/process/foreclosure/v/detail", obj, ForeclosureDto.class);
			if(!"04".equals(orderListDto.getProductCode())){
				//畅贷不展示
				if(!isChangloan) {
					if (obj != null) {
						orderDeatil.put(MapUtils.getString(configlist.get(7), "title"), obj.getForeclosureTimeStr() == null ? "-" : obj.getForeclosureTimeStr());
					} else {
						orderDeatil.put(MapUtils.getString(configlist.get(7), "title"), "-");
					}
					key += MapUtils.getString(configlist.get(7), "title") + ",";
				}
				
//				List<BankDto> bankDtos = null;
//				List<SubBankDto> subBankDtos = null;
				//畅贷不展示
				if(!isChangloan) {
					if (baseBorrowDto.getIsOldLoanBank() != null) {
						if (baseBorrowDto.getIsOldLoanBank().equals(1)) {
							String loanBankName = "";
							if (baseBorrowDto.getOldLoanBankNameId() != null) {
								loanBankName = CommonDataUtil.getBankNameById(baseBorrowDto.getOldLoanBankNameId()).getName();
							}
							if (baseBorrowDto.getOldLoanBankSubNameId() != null) {
								loanBankName += "-" + CommonDataUtil.getSubBankNameById(baseBorrowDto.getOldLoanBankSubNameId()).getName();
								;
							}
							if (StringUtils.isEmpty(loanBankName)) {
								loanBankName = "-";
							}
							orderDeatil.put(MapUtils.getString(configlist.get(8), "title"), loanBankName);
						} else {
							orderDeatil.put(MapUtils.getString(configlist.get(8), "title"), StringUtils.isEmpty(baseBorrowDto.getOldLoanBankName()) ? "-" : baseBorrowDto.getOldLoanBankName());
						}
					} else {
						orderDeatil.put(MapUtils.getString(configlist.get(8), "title"), "-");
					}
					key += MapUtils.getString(configlist.get(8), "title") + ",";
				}
				//畅贷不展示
				if(!isChangloan) {
					if (baseBorrowDto.getIsLoanBank() != null) {
						if (baseBorrowDto.getIsLoanBank().equals(1)) {
							String loanBankName = "";
							if (baseBorrowDto.getLoanBankNameId() != null) {
								loanBankName = CommonDataUtil.getBankNameById(baseBorrowDto.getLoanBankNameId()).getName();
							}
							if (baseBorrowDto.getLoanSubBankNameId() != null) {
								loanBankName += "-" + CommonDataUtil.getSubBankNameById(baseBorrowDto.getLoanSubBankNameId()).getName();
							}
							if (StringUtils.isEmpty(loanBankName)) {
								loanBankName = "-";
							}
							orderDeatil.put(MapUtils.getString(configlist.get(9), "title"), loanBankName);
						} else {
							orderDeatil.put(MapUtils.getString(configlist.get(9), "title"), StringUtils.isEmpty(baseBorrowDto.getLoanBankName()) ? "-" : baseBorrowDto.getLoanBankName());
						}
					} else {
						orderDeatil.put(MapUtils.getString(configlist.get(9), "title"), "-");
					}
					key += MapUtils.getString(configlist.get(9), "title") + ",";
				}
				//畅贷不展示
				if(!isChangloan) {
					if (StringUtils.isNotEmpty(orderListDto.getLendingTime())) {
						orderDeatil.put(MapUtils.getString(configlist.get(10), "title"), orderListDto.getLendingTime().substring(0, 10));
					} else {
						orderDeatil.put(MapUtils.getString(configlist.get(10), "title"), "-");
					}
					key += MapUtils.getString(configlist.get(10), "title") + ",";
	
					if (StringUtils.isNotEmpty(orderListDto.getPlanPaymentTime())) {
						orderDeatil.put(MapUtils.getString(configlist.get(11), "title") + "", orderListDto.getPlanPaymentTime().substring(0, 10));
					} else {
						orderDeatil.put(MapUtils.getString(configlist.get(11), "title"), "-");
					}
					key += MapUtils.getString(configlist.get(11), "title") + "";
				}
				//畅贷展示受理员
				if(isChangloan){
					String acceptMemberName = StringUtils.isNotBlank(orderListDto.getAcceptMemberName())?orderListDto.getAcceptMemberName():"-";
					orderDeatil.put(MapUtils.getString(configlist.get(12), "title"),acceptMemberName);
					key += MapUtils.getString(configlist.get(12), "title") + "";
				}
			}
			retMap.put("orderFlowList", list);
			retMap.put("order", orderDeatil);
			orderDeatil.put("keys", key);
			retMap.put("upper", orderDeatil);
			RespHelper.setSuccessDataObject(resp, retMap);
		} catch (Exception e) {
			log.error("获取订单详情异常", e);
			RespHelper.setFailDataObject(resp, null, RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}
}
