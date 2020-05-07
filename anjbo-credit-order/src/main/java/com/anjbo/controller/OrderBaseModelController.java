package com.anjbo.controller;

import com.anjbo.bean.BaseCompleteDto;
import com.anjbo.bean.BaseItemDto;
import com.anjbo.bean.common.BankDto;
import com.anjbo.bean.common.DictDto;
import com.anjbo.bean.common.SubBankDto;
import com.anjbo.bean.customer.AgencyDto;
import com.anjbo.bean.element.DocumentsDto;
import com.anjbo.bean.element.DocumentsReturnDto;
import com.anjbo.bean.finance.ReportDto;
import com.anjbo.bean.order.*;
import com.anjbo.bean.product.*;
import com.anjbo.bean.risk.CreditDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.*;
import com.anjbo.service.*;
import com.anjbo.utils.BeanToMapUtil;
import com.anjbo.utils.CommonDataUtil;
import com.anjbo.utils.ConfigUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/credit/order/model/v")
public class OrderBaseModelController extends BaseController{
	
	@Resource
	private OrderModelService orderModelService;
	@Resource
	private OrderBaseService orderBaseService;
	@Resource 
	private OrderBaseBorrowService orderBaseBorrowService;
	@Resource
	private OrderBaseBorrowRelationService orderBaseBorrowRelationService;
	@Resource
	private OrderCompleteService orderCompleteService;
	@Resource
	private OrderBaseCustomerService orderBaseCustomerService;
	@Resource 
	private OrderBaseCustomerBorrowerService orderBaseCustomerBorrowerService;
	@Resource
	private OrderBaseCustomerGuaranteeService orderBaseCustomerGuaranteeService;
	@Resource
	private OrderBaseHouseService orderBaseHouseService;
	@Resource
	private OrderBaseHousePropertyService orderBaseHousePropertyService;
	@Resource
	private OrderBaseHousePropertyPeopleService orderBaseHousePropertyPeopleService;
	@Resource
	private OrderBaseHousePurchaserService orderBaseHousePurchaserService;
	@Resource
	private OrderFlowService orderFlowService;
	
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
	
	@ResponseBody
	@RequestMapping(value = "/appPageModel") 
	public RespDataObject<Map<String, Object>> appPageModel(HttpServletRequest request, @RequestBody Map<String, Object> params){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String pageType = MapUtils.getString(params, "pageType");
			String orderNo = MapUtils.getString(params, "orderNo");
			OrderBaseBorrowDto baseBorrowDto = orderBaseBorrowService.selectOrderBorrowByOrderNo(getLoanOrderNo(orderNo));
			params.put("cityCode", baseBorrowDto.getCityCode());
			params.put("productCode", baseBorrowDto.getProductCode());
			if(!pageType.contains("order")
					&& !pageType.contains("Page")
					&& !pageType.equals("addOrder")
					&& !pageType.equals("notarization")
					&& !pageType.equals("facesign")
					&& !pageType.equals("assignAcceptMember")
					&& !pageType.equals("editreport")){
				OrderListDto orderListDto = orderBaseService.selectDetail(baseBorrowDto.getOrderNo());
				Map<String, Object> orderDeatil = new LinkedHashMap<String, Object>();
				String key = "";
				orderDeatil.put("城市", baseBorrowDto.getCityName());
				key += "城市,";
				orderDeatil.put("业务类型", baseBorrowDto.getProductName());
				key += "业务类型,";
				orderDeatil.put("客户姓名", baseBorrowDto.getBorrowerName());
				key += "客户姓名,";
//				if(isChangLoan(MapUtils.getString(params, "orderNo"))){
//					orderDeatil.put("借款金额", baseBorrowDto.getOrderBaseBorrowRelationDto().get(0).getLoanAmount()==null?"-":baseBorrowDto.getOrderBaseBorrowRelationDto().get(0).getLoanAmount()+"");
//					orderDeatil.put("借款期限", baseBorrowDto.getOrderBaseBorrowRelationDto().get(0).getBorrowingDays()==null?"-":baseBorrowDto.getOrderBaseBorrowRelationDto().get(0).getBorrowingDays()+"");
//				}else{
//					orderDeatil.put("借款金额", (baseBorrowDto.getLoanAmount()==null?"-":baseBorrowDto.getLoanAmount())+"");
//					orderDeatil.put("借款期限", (baseBorrowDto.getBorrowingDays()==null?"-":baseBorrowDto.getBorrowingDays().toString())+"");
//				}
				key += "借款金额,";
				key += "借款期限,";
				
				
				Map<String, Object> paramsMap = new HashMap<String, Object>();
				paramsMap.put("agencyId", baseBorrowDto.getCooperativeAgencyId());
				AgencyDto agencyDto = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/customer/agency/v/getAgencyDto", paramsMap, AgencyDto.class);
				if(agencyDto != null){
					orderDeatil.put("合作机构", agencyDto.getName());
				}else{
					orderDeatil.put("合作机构", "-");
				}
				key += "合作机构,";
				if(StringUtils.isNotBlank(baseBorrowDto.getChannelManagerUid())){
					orderDeatil.put("渠道经理", CommonDataUtil.getUserDtoByUidAndMobile(baseBorrowDto.getChannelManagerUid()).getName());
				}else{
					orderDeatil.put("渠道经理", "-");
				}
				key += "渠道经理,";
				ForeclosureDto  obj = new ForeclosureDto();
				obj.setOrderNo(orderListDto.getOrderNo());
				obj = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/process/foreclosure/v/detail", obj, ForeclosureDto.class);
				if(obj!=null){
					orderDeatil.put("结清原贷款时间", obj.getForeclosureTimeStr());
				}else{
					orderDeatil.put("结清原贷款时间", "-");
				}
				key += "结清原贷款时间,";
				

				if(baseBorrowDto.getIsOldLoanBank()!=null){
//					if(baseBorrowDto.getIsOldLoanBank().equals(1)){
//						String loanBankName = "";
//						List<BankDto> bankDtos = getAllBankList();
//						List<SubBankDto> subBankDtos = getAllSubBankList();
//						for (BankDto bankDto : bankDtos) {
//							if(bankDto.getId() == baseBorrowDto.getOldLoanBankNameId()){
//								loanBankName = bankDto.getName();
//								break;
//							}
//						}
//						for (SubBankDto subBankDto : subBankDtos) {
//							if(subBankDto.getId() == baseBorrowDto.getOldLoanBankSubNameId()){
//								loanBankName +="-"+subBankDto.getName();
//								break;
//							}
//						}
//						if(StringUtils.isEmpty(loanBankName)){
//							loanBankName = "-";
//						}
//						orderDeatil.put("结清原贷款地址", loanBankName);
//					}else{
//						orderDeatil.put("结清原贷款地址", baseBorrowDto.getOldLoanBankName());
//					}
				}else{
					orderDeatil.put("结清原贷款地址", "-");
				}
				key += "结清原贷款地址,";
				
				
				if(baseBorrowDto.getIsLoanBank()!=null){
//					if(baseBorrowDto.getIsLoanBank().equals(1)){
//						String loanBankName = "";
//						List<BankDto> bankDtos = getAllBankList();
//						List<SubBankDto> subBankDtos = getAllSubBankList();
//						for (BankDto bankDto : bankDtos) {
//							if(bankDto.getId() == baseBorrowDto.getLoanBankNameId()){
//								loanBankName = bankDto.getName();
//								break;
//							}
//						}
//						for (SubBankDto subBankDto : subBankDtos) {
//							if(subBankDto.getId() == baseBorrowDto.getLoanSubBankNameId()){
//								loanBankName +="-"+subBankDto.getName();
//								break;
//							}
//						}
//						if(StringUtils.isEmpty(loanBankName)){
//							loanBankName = "-";
//						}
//						orderDeatil.put("新贷款地址", loanBankName);
//					}else{
//						orderDeatil.put("新贷款地址", baseBorrowDto.getLoanBankName());
//					}
				}else{
					orderDeatil.put("新贷款地址", "-");
				}
				key += "新贷款地址,";
				
				if(StringUtils.isNotEmpty(orderListDto.getLendingTime())){
					orderDeatil.put("放款时间", orderListDto.getLendingTime().substring(0, 10));
				}else{
					orderDeatil.put("放款时间", "-");
				}
				key += "放款时间,";

				if(StringUtils.isNotEmpty(orderListDto.getPlanPaymentTime())){
					orderDeatil.put("预计回款时间", orderListDto.getPlanPaymentTime().substring(0, 10));
				}else{
					orderDeatil.put("预计回款时间", "-");
				}
				key += "预计回款时间";
				map.put("keys", key);
				map.put("upper", orderDeatil);
			}
			

			if(pageType.contains("Page")){
				map.put("lower", appPageCompleteModel(request, params).getData());
			}else{
				map.put("lower", appOrderPageModel(request, params).getData());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return RespHelper.setSuccessDataObject(new RespDataObject<Map<String, Object>>(), map);
	}
	
	private RespData<List<BaseCompleteDto>> appPageCompleteModel(HttpServletRequest request, @RequestBody Map<String, Object> params){
		List<List<BaseCompleteDto>> list = new ArrayList<List<BaseCompleteDto>>();
		List<BaseCompleteDto> tempList = new ArrayList<BaseCompleteDto>();
		String pageType = MapUtils.getString(params, "pageType");
		String corderNo = MapUtils.getString(params, "orderNo","");
		List<BaseCompleteDto> baseCompleteDtoList = new ArrayList<BaseCompleteDto>();
		//获取债务置换贷款订单号
		String orderNo = getLoanOrderNo(corderNo);
		OrderBaseBorrowDto borrow =  orderBaseBorrowService.selectOrderBorrowByOrderNo(orderNo);
		OrderListDto orderListDto = orderBaseService.selectDetail(orderNo);
		if(pageType.equals("orderBorrowPage")){
			baseCompleteDtoList = orderCompleteService.selectBaseOrderCompleteList(pageType);
			baseCompleteDtoList.remove(1);
			//如果有畅贷
			OrderBaseBorrowDto orderBaseBorrowDto =  orderBaseBorrowService.selectOrderBorrowByOrderNo(orderNo);
			if(orderBaseBorrowDto.getOrderBaseBorrowRelationDto()!=null&&orderBaseBorrowDto.getOrderBaseBorrowRelationDto().size()>0){
				String tempOrder = orderBaseBorrowDto.getOrderBaseBorrowRelationDto().get(0).getOrderNo();
				boolean isDelete = true;
				if(orderBaseService.selectDetail(tempOrder)!=null){
					isDelete = false;
				}
				baseCompleteDtoList.add(new BaseCompleteDto("畅贷信息", true,false, "/credit/order/app/v/deletecd", "orderRelation",1,1,0,"",orderBaseBorrowDto.getOrderBaseBorrowRelationDto().get(0).getOrderNo(),isDelete));
				BaseCompleteDto  dto = orderCompleteService.selectBaseOrderCompleteList(pageType).get(1);
				dto.setTitle("");
				baseCompleteDtoList.add(dto);
			}else if(!(orderListDto != null && !orderListDto.equals("mortgage") && !orderListDto.equals("receivableForEnd") && !orderListDto.equals("pay") && !orderListDto.equals("elementReturn") && !orderListDto.equals("wanjie"))){//订单回款后不可添加关联订单
				BaseCompleteDto  dto = orderCompleteService.selectBaseOrderCompleteList(pageType).get(1);
				dto.setTitle("");
				baseCompleteDtoList.add(dto);
			}else{
				baseCompleteDtoList.add(orderCompleteService.selectBaseOrderCompleteList(pageType).get(1));
			}
		}else if(pageType.equals("orderCustomerPage")){//客户信息
			baseCompleteDtoList = orderCompleteService.selectBaseOrderCompleteList(pageType);
			if(MapUtils.getInteger(params, "isDetail")!=null&&MapUtils.getInteger(params, "isDetail")==1){
				OrderBaseCustomerDto customer = orderBaseCustomerService.selectOrderCustomerByOrderNo(orderNo);
				if(customer.getCustomerBorrowerDto()==null||customer.getCustomerBorrowerDto().size()==0){
					ListIterator<BaseCompleteDto> listIterator = baseCompleteDtoList.listIterator();
			        while (listIterator.hasNext()) {
			            if ("借款人信息".equals(listIterator.next().getTitle())) {
			                listIterator.remove();
			            }
			        }
				}
				if(customer.getCustomerGuaranteeDto()==null||customer.getCustomerGuaranteeDto().size()==0){
					ListIterator<BaseCompleteDto> listIterator = baseCompleteDtoList.listIterator();
			        while (listIterator.hasNext()) {
			            if ("担保人信息".equals(listIterator.next().getTitle())) {
			                listIterator.remove();
			            }
			        }
				}
				List<BaseCompleteDto> temp =new ArrayList<BaseCompleteDto>();
				for (int i=0;i< baseCompleteDtoList.size() ; i++) {
					BaseCompleteDto dto = new BaseCompleteDto();
					if(i==baseCompleteDtoList.size()-1){
						dto = baseCompleteDtoList.get(i);
						dto.setIsEnd(1);
						temp.add(dto);
					}else{
						temp.add(baseCompleteDtoList.get(i));
					}
				}
				if(baseCompleteDtoList.size()==1){
					BaseCompleteDto titleNull = new BaseCompleteDto("", true,false, "", "orderBaseCustomer",1,1,0,"","",false);
					temp.add(titleNull);
				}
				baseCompleteDtoList = temp;
			}
		}else if(pageType.equals("orderBaseCustomerBorrowerPage")){
			List<OrderBaseCustomerBorrowerDto>  listObj = orderBaseCustomerBorrowerService.selectOrderCustomerBorrowerByOrderNo(orderNo);
            if(null!=listObj||listObj.size()>0){
                for(int i=1;i<=listObj.size();i++){
                    baseCompleteDtoList.add(new BaseCompleteDto("借款人"+i, false, "/credit/order/app/v/deleteCustomerBorrow", "orderBaseCustomerBorrower",1,1,listObj.get(i-1).getId(),"",true));
                }
            }
            baseCompleteDtoList.add(new BaseCompleteDto("添加借款人", false, "", "orderBaseCustomerBorrower",1,1,0,"",false));

        }else if(pageType.equals("orderBaseCustomerGuaranteePage")){
			
			List<OrderBaseCustomerGuaranteeDto>  listObj =  orderBaseCustomerGuaranteeService.selectOrderCustomerGuaranteeByOrderNo(orderNo);
			if(null!=listObj||listObj.size()>0){
				for(int i=1;i<=listObj.size();i++){
					baseCompleteDtoList.add(new BaseCompleteDto("担保人"+i, false, "/credit/order/app/v/deleteCustomerGuarantee", "orderBaseCustomerGuarantee",1,1,listObj.get(i-1).getId(),"",true));
				}
			} 
			baseCompleteDtoList.add(new BaseCompleteDto("添加担保人", false, "", "orderBaseCustomerGuarantee",1,1,0,"",false));
			
		}else if(pageType.equals("orderBaseHousePropertyPage")){
			boolean isDelete = false;
			List<OrderBaseHousePropertyDto>  listObj = orderBaseHousePropertyService.selectOrderHousePropertyByOrderNo(orderNo);
			if(null!=listObj||listObj.size()>0){
				isDelete = listObj.size()>1?true:false;
				for(int i=1;i<=listObj.size();i++){
					baseCompleteDtoList.add(new BaseCompleteDto("房产"+i, false, "/credit/order/app/v/deleteHouseProperty", "orderBaseHouseProperty",1,1,listObj.get(i-1).getId(),"",isDelete));
				}
			} 
			
			baseCompleteDtoList.add(new BaseCompleteDto("添加房产", false, "", "orderBaseHouseProperty",1,1,0,"",false));
		}else if(pageType.equals("orderBaseHousePropertyPeoplePage")){
			boolean isDelete = false;
			List<OrderBaseHousePropertyPeopleDto>  listObj = orderBaseHousePropertyPeopleService.selectOrderPropertyPeopleByOrderNo(orderNo);
			if(null!=listObj||listObj.size()>0){
				isDelete = listObj.size()>1?true:false;
				for(int i=1;i<=listObj.size();i++){
					baseCompleteDtoList.add(new BaseCompleteDto("产权人"+i, false, "/credit/order/app/v/deletePropertyPeople", "orderBaseHousePropertyPeople",1,1,listObj.get(i-1).getId(),"",isDelete));
				}
			} 
			
			baseCompleteDtoList.add(new BaseCompleteDto("添加产权人", false, "", "orderBaseHousePropertyPeople",1,1,0,"",false));
		}else if(pageType.equals("orderBaseHousePurchaserPage")){
			boolean isDelete = false;
			List<OrderBaseHousePurchaserDto> listObj = orderBaseHousePurchaserService.selectOrderHousePurchaserByOrderNo(orderNo);
			if(null!=listObj||listObj.size()>0){
				isDelete = listObj.size()>1?true:false;
				for(int i=1;i<=listObj.size();i++){
					baseCompleteDtoList.add(new BaseCompleteDto("买房人"+i, false, "/credit/order/app/v/deleteHousePurchaser", "orderBaseHousePurchaser",1,1,listObj.get(i-1).getId(),"",isDelete));
				}
			} 
			baseCompleteDtoList.add(new BaseCompleteDto("添加买房人", false, "", "orderBaseHousePurchaser",1,1,0,"",false));
		}else if(pageType.equals("orderEnquiryPage")){
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("orderNo", orderNo);
			map = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/risk/lawsuit/v/loadInquiryAndArchiveInit", map, Map.class); 
			JSONArray array = JSONArray.fromObject(map.get("enquiryData"));
			if(array!=null&&array.size()>0){
				for (int i = 0; i < array.size(); i++) {
					baseCompleteDtoList.add(new BaseCompleteDto("询价"+(i+1), false, "/credit/risk/enquiry/v/delete", "/appH5/enquiry.html?orderNo="+orderNo+"&enquiryId="+array.getJSONObject(i).getString("enquiryId")+"&productCode="+borrow.getProductCode(),1,2,0,array.getJSONObject(i).getString("enquiryId"),true));
				}
			}
			baseCompleteDtoList.add(new BaseCompleteDto("添加询价", false, "", "/appH5/enquiry.html?orderNo="+orderNo+"&productCode="+borrow.getProductCode(),1,2,0,"",false));
		}else if(pageType.equals("orderArchivePage")){
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("orderNo", orderNo);
			map = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/risk/lawsuit/v/loadInquiryAndArchiveInit", map, Map.class); 
			JSONArray array = JSONArray.fromObject(map.get("archiveData"));
			if(array!=null&&array.size()>0){
				for (int i = 0; i < array.size(); i++) {
					baseCompleteDtoList.add(new BaseCompleteDto("查档"+(i+1), false, "/credit/risk/archive/v/delete", "/appH5/consult.html?orderNo="+orderNo+"&archiveId="+array.getJSONObject(i).getString("archiveId")+"&productCode="+borrow.getProductCode(),1,2,0,array.getJSONObject(i).getString("archiveId"),true));
				}
			}
			baseCompleteDtoList.add(new BaseCompleteDto("添加查档", false, "", "/appH5/consult.html?orderNo="+orderNo+"&productCode="+borrow.getProductCode(),1,2,0,"",false));
		}else if(pageType.equals("orderLawsuitPage")){
			
		}else{
			baseCompleteDtoList = orderCompleteService.selectBaseOrderCompleteList(pageType);
		}
		//如果是退回的，不选择受理经理和城市
		// 退回重新提交
		OrderFlowDto flowDto = new OrderFlowDto();
		flowDto.setOrderNo(corderNo);
		OrderFlowDto orderFlow = orderFlowService
				.selectEndOrderFlow(flowDto);
		boolean isBack =false;
		if (orderFlow!=null&&orderFlow.getBackReason() != null
				&& !orderFlow.getBackReason().equals("")) {// 退回重新提交
			isBack = true;
		}
		for (BaseCompleteDto baseCompleteDto : baseCompleteDtoList) {
			
			if(("02").equals(MapUtils.getString(params, "productCode"))){
				if(StringUtils.isNotEmpty(baseCompleteDto.getTitle())){
					if(baseCompleteDto.getTitle().equals("买房人")){
						continue;
					}
				}
			}

			//为了测试用baseCompleteDto.setIsFinish(true);
			baseCompleteDto.setIsFinish(orderBaseBorrowService.isFinish(baseCompleteDto.getTitle(),baseCompleteDto.getButUrl(), orderNo));
			if("受理经理".equals(baseCompleteDto.getTitle())){
				OrderBaseBorrowDto baseBorrowDto = orderBaseBorrowService.selectOrderBorrowByOrderNo(orderNo);
				String producCode = baseBorrowDto.getProductCode();
				String url = baseCompleteDto.getButUrl()+"&productCode="+producCode;
				baseCompleteDto.setButUrl(url);
			}
			
			if(baseCompleteDto.getButUrl().equals("orderBaseHouse")){
				if("02".equals(MapUtils.getString(params, "productCode"))){
					baseCompleteDto.setTitle("房贷与新贷款信息");
				}
			}
			if(!isBack||(!"受理经理".equals(baseCompleteDto.getTitle())&&!"城市".equals(baseCompleteDto.getTitle()))){
				tempList.add(baseCompleteDto);
			}else{
				baseCompleteDto.setIsEnd(0);
			}
			if(baseCompleteDto.getIsEnd() == 1){
				list.add(tempList);
				tempList = new ArrayList<BaseCompleteDto>();
			}
		}
		return RespHelper.setSuccessData(new RespData<List<BaseCompleteDto>>(), list);
	}
	
	
	private RespData<List<BaseItemDto>> appOrderPageModel(HttpServletRequest request, @RequestBody Map<String, Object> params){
		List<List<BaseItemDto>> list = new ArrayList<List<BaseItemDto>>();
		String pageType = MapUtils.getString(params, "pageType");
		List<BaseItemDto> baseItemDtos = orderModelService.selectBaseItemList(pageType);
		String orderNo = MapUtils.getString(params, "orderNo");
		Integer id = MapUtils.getInteger(params, "id",0);
		List<BaseItemDto> tempList = new ArrayList<BaseItemDto>();
		UserDto userDto = getUserDto(request);
		Map<String, Object> map = new HashMap<String, Object>();
		String creditOrderNo = orderNo;
		OrderListDto orderListDto = orderBaseService.selectDetail(orderNo);
		int isFace = orderBaseService.selectIsFace(orderNo);
		map = getObjectValue(map,pageType,creditOrderNo,id,userDto,orderListDto);
		Object reportobj = MapUtils.getObject(map,"reportobj",null);
		String cityCode = MapUtils.getString(params, "cityCode");
		String productCode = MapUtils.getString(params, "productCode");
		//如果是畅贷，不可编辑债务置换贷款，如果是债务置换贷款不可编辑畅贷

		//boolean isChangLoan = isChangLoan(orderNo);
		String arrangement = MapUtils.getString(params,"arrangement","");
        Iterator<BaseItemDto> iterator = baseItemDtos.iterator();
		String faceCityCode = ConfigUtil.getStringValue(Constants.BASE_FACE_CITY_CODE,ConfigUtil.CONFIG_BASE);
         while(iterator.hasNext()) {
            BaseItemDto baseItemDto = iterator.next();

            //如果没有包含在需要人脸识别的城市则不显示该项
			 if(100==baseItemDto.getType()
					 &&(StringUtils.isBlank(faceCityCode)
			 			||null==faceCityCode
			 			||StringUtils.isBlank(cityCode))){
				 iterator.remove();
				 continue;
			 } else if(100==baseItemDto.getType()
                    &&StringUtils.isNotBlank(faceCityCode)
                    &&!faceCityCode.contains(cityCode)){
                iterator.remove();
                continue;
            } else if(100==baseItemDto.getType()
					 &&StringUtils.isNotBlank(faceCityCode)
					 &&faceCityCode.contains(cityCode)
					 &&2==isFace){
				 iterator.remove();
				 continue;
			 }else if(101==baseItemDto.getType()
					&&"editrecord".equals(baseItemDto.getKey())
					&&StringUtils.isBlank(MapUtils.getString(map, baseItemDto.getKey(),""))){
				iterator.remove();
				continue;
			} else if(101==baseItemDto.getType()
					&&"replyrecord".equals(baseItemDto.getKey())
					&&StringUtils.isBlank(MapUtils.getString(map, baseItemDto.getKey(),""))){
				iterator.remove();
				continue;
			} else if(101==baseItemDto.getType()
					 &&"editrecord".equals(baseItemDto.getKey())
					 &&StringUtils.isNotBlank(MapUtils.getString(map, baseItemDto.getKey(),""))
					 &&StringUtils.isBlank(MapUtils.getString(map, "replyrecord",""))){
				 baseItemDto.setIsEnd(1);
			 }
            if(StringUtils.isNotEmpty(productCode)){
				if(productCode.equals("02")){
					if(StringUtils.isNotEmpty(baseItemDto.getKey())){
						if(baseItemDto.getKey().equals("isOnePay") 
								|| baseItemDto.getKey().equals("payMentAmountOne") 
								|| baseItemDto.getKey().equals("payMentAmountDateOne") 
								|| baseItemDto.getKey().equals("payMentAmountTwo")
								|| baseItemDto.getKey().equals("payMentAmountDateTwo")
								|| baseItemDto.getKey().equals("houseDealPrice")
								|| baseItemDto.getKey().equals("houseDealDeposit")
								|| baseItemDto.getKey().equals("houseSuperviseAmount")){
							continue;
						}
					}
					if(StringUtils.isNotEmpty(baseItemDto.getKey())){
						if(baseItemDto.getKey().equals("rebateBankCardNum")){
							baseItemDto.setIsEnd(1);
						}
					}
				}
			}
			//如果orderNo不为空，则查对应数据
			if(StringUtils.isNotEmpty(creditOrderNo)){
				if(baseItemDto.getType() == 2){
					if(baseItemDto.getContentType().equals("isOldLoanBankList")){
						if(StringUtils.isNotEmpty(MapUtils.getString(map, baseItemDto.getKey()))){
							baseItemDto.setValue(MapUtils.getString(map, baseItemDto.getKey()));
							if(MapUtils.getString(map, baseItemDto.getKey()).equals("1")){
								baseItemDto.setPlaceholder("是");
							}else{
								baseItemDto.setPlaceholder("否");
							}
						}
					}else if(baseItemDto.getContentType().equals("isBankEndList")){
						if(StringUtils.isNotEmpty(MapUtils.getString(map, baseItemDto.getKey()))){
							baseItemDto.setValue(MapUtils.getString(map, baseItemDto.getKey()));
							if(MapUtils.getString(map, baseItemDto.getKey()).equals("0")){
								baseItemDto.setPlaceholder("是");
							}else{
								baseItemDto.setPlaceholder("否");
							}
						}
					} else if("sexList".equals(baseItemDto.getContentType())
							||"borrowerIsPropertyProleList".equals(baseItemDto.getContentType())
							||"foreclosureTypeList".equals(baseItemDto.getContentType())
							||"accountTypeList".equals(baseItemDto.getContentType())
							||"paymentModeList".equals(baseItemDto.getContentType())){
						if(StringUtils.isNotEmpty(MapUtils.getString(map, baseItemDto.getKey(),""))){
							baseItemDto.setValue(MapUtils.getString(map, baseItemDto.getKey(),""));
							baseItemDto.setPlaceholder(MapUtils.getString(map, baseItemDto.getKey(),""));
						}
					}
					if(baseItemDto.getKey().equals("cityCode")
							&&pageType.equals("editreport")){
						baseItemDto.setValue(MapUtils.getString(map, baseItemDto.getKey(),""));
						baseItemDto.setPlaceholder(MapUtils.getString(map, "cityName",""));
					}
				}else if(baseItemDto.getType() == 3){
					if(baseItemDto.getDataFmt().equals("yyyy-MM-dd")){
						if(baseItemDto.getKey().equals("licenseRevTime")){
							baseItemDto.setValue(MapUtils.getString(map, baseItemDto.getKey()+"Str"));
							baseItemDto.setPlaceholder(MapUtils.getString(map, baseItemDto.getKey()+"Str"));
						}else if(baseItemDto.getKey().equals("notarizationTime")){
							String time=map.get("notarizationTimeStr")==null?"":map.get("notarizationTimeStr")+"";
							baseItemDto.setValue(time);
							baseItemDto.setPlaceholder(time);
						}else if(baseItemDto.getKey().equals("estimatedTime")){
							String time=map.get("estimatedTimeStr")==null?"":map.get("estimatedTimeStr")+"";
							baseItemDto.setValue(time);
							baseItemDto.setPlaceholder(time);
						}else if(baseItemDto.getKey().equals("faceSignTime")){
							String time=map.get("faceSignTimeStr")==null?"":map.get("faceSignTimeStr")+"";
							baseItemDto.setValue(time);
							baseItemDto.setPlaceholder(time);
						}else if(MapUtils.getObject(map, baseItemDto.getKey()) != null){
							baseItemDto.setValue(DateUtil.getDateByFmt((Date)MapUtils.getObject(map, baseItemDto.getKey()), DateUtil.FMT_TYPE2));
							baseItemDto.setPlaceholder(DateUtil.getDateByFmt((Date)MapUtils.getObject(map, baseItemDto.getKey()), DateUtil.FMT_TYPE2));
						}
					}else if(baseItemDto.getKey().equals("estimateOutLoanTimeStr")){
						baseItemDto.setValue(DateUtil.getDateByFmt((Date)MapUtils.getObject(map, "estimateOutLoanTime"), DateUtil.FMT_TYPE11));
						baseItemDto.setPlaceholder(DateUtil.getDateByFmt((Date)MapUtils.getObject(map, "estimateOutLoanTime"), DateUtil.FMT_TYPE11));
					}else{
						baseItemDto.setValue(MapUtils.getString(map, baseItemDto.getKey()));
						baseItemDto.setPlaceholder(MapUtils.getString(map, baseItemDto.getKey()));
					}
				}else if(baseItemDto.getType() == 4||baseItemDto.getType() == 11){
					if(StringUtils.isNotEmpty(MapUtils.getString(map, baseItemDto.getKey()))){
						
						
						//银行
						if(baseItemDto.getKey().equals("licenseRevBank")
								|| baseItemDto.getKey().equals("foreclosureBankNameId")
								|| baseItemDto.getKey().equals("oldLoanBankNameId")
								|| baseItemDto.getKey().equals("loanBankNameId")
								|| baseItemDto.getKey().equals("rebateBankId")
								|| baseItemDto.getKey().equals("bankNameId")
								|| baseItemDto.getKey().equals("paymentBankNameId")){
							baseItemDto.setValue(MapUtils.getString(map, baseItemDto.getKey()));
							baseItemDto.setPlaceholder(CommonDataUtil.getBankNameById(MapUtils.getIntValue(map, baseItemDto.getKey())).getName());
							
						//支行
						}else if(baseItemDto.getKey().equals("licenseRevBankSub")
								|| baseItemDto.getKey().equals("foreclosureBankSubNameId")
								|| baseItemDto.getKey().equals("oldLoanBankSubNameId")
								|| baseItemDto.getKey().equals("loanSubBankNameId")
								|| baseItemDto.getKey().equals("rebateBankSubId")
								|| baseItemDto.getKey().equals("bankSubNameId")
								|| baseItemDto.getKey().equals("paymentBankSubNameId")){
							baseItemDto.setValue(MapUtils.getString(map, baseItemDto.getKey()));
							baseItemDto.setPlaceholder(CommonDataUtil.getSubBankNameById(MapUtils.getIntValue(map, baseItemDto.getKey())).getName());
						//人员
						}else if(baseItemDto.getKey().equals("licenseReverUid")
								|| baseItemDto.getKey().equals("clandBureauUid")
								|| baseItemDto.getKey().equals("tlandBureauUid")
								|| baseItemDto.getKey().equals("nlandBureauUid")
								|| baseItemDto.getKey().equals("mlandBureauUid")
								|| baseItemDto.getKey().equals("channelManagerUid")
								|| baseItemDto.getKey().equals("notarialUid")
								|| baseItemDto.getKey().equals("facesignUid")
								|| baseItemDto.getKey().equals("elementUid")){
							baseItemDto.setValue(MapUtils.getString(map, baseItemDto.getKey()));
							baseItemDto.setPlaceholder(CommonDataUtil.getUserDtoByUidAndMobile(MapUtils.getString(map, baseItemDto.getKey())).getName());
						//国土局
						} else if(baseItemDto.getKey().equals("clandBureau")
								|| baseItemDto.getKey().equals("tlandBureau")
								|| baseItemDto.getKey().equals("nlandBureau")
								|| baseItemDto.getKey().equals("mlandBureau") ){
							baseItemDto.setValue(MapUtils.getString(map, baseItemDto.getKey()));
							baseItemDto.setPlaceholder(getBureauName(MapUtils.getString(map, baseItemDto.getKey())));
						} else if("cooperativeAgencyId".equals(baseItemDto.getKey())){
							baseItemDto.setValue(MapUtils.getString(map,baseItemDto.getKey()));
							String channelManagerUid = MapUtils.getString(map,"channelManagerUid","");
							Integer cooperativeAgencyId = MapUtils.getInteger(map,"cooperativeAgencyId",0);
							baseItemDto.setPlaceholder(MapUtils.getString(getAgency(channelManagerUid,cooperativeAgencyId),"name",""));
						} else if("productCode".equals(baseItemDto.getKey())
								&&pageType.equals("editreport")){
							baseItemDto.setValue(MapUtils.getString(map, baseItemDto.getKey()));
							baseItemDto.setPlaceholder(MapUtils.getString(map, "productName"));
						} else {
							baseItemDto.setValue(MapUtils.getString(map, baseItemDto.getKey()));
							baseItemDto.setPlaceholder(MapUtils.getString(map, baseItemDto.getKey()));
						}
						
						if(StringUtils.isNotEmpty(baseItemDto.getPreArgumentType())){
							if(baseItemDto.getPreArgumentType().contains("2")){
								baseItemDto.setValue(MapUtils.getString(map, baseItemDto.getKey()));
							}
						}
						//收费类型
						if(baseItemDto.getKey().equals("riskGradeId")){
							baseItemDto.setValue(MapUtils.getString(map, baseItemDto.getKey()));
							baseItemDto.setPlaceholder(MapUtils.getString(map,"riskGrade"));
						}
						//合作机构
						if(baseItemDto.getKey().equals("cooperativeAgencyId")){
							baseItemDto.setValue(MapUtils.getString(map, baseItemDto.getKey()));
							baseItemDto.setPlaceholder(MapUtils.getString(map,"cooperativeAgencyName"));
						}
						
						//公证地点
						if(baseItemDto.getKey().equals("notarizationAddressCode")){
							baseItemDto.setValue(MapUtils.getString(map, baseItemDto.getKey()));
							baseItemDto.setPlaceholder(MapUtils.getString(map,"notarizationAddress"));
						}
						
					}
				}else if(baseItemDto.getType() == 5){
					if(StringUtils.isNotEmpty(MapUtils.getString(map, baseItemDto.getKey()))){
						baseItemDto.setValue(MapUtils.getString(map, baseItemDto.getKey()));
						baseItemDto.setPlaceholder("已上传");
					}
				}else if(baseItemDto.getType() == 6){
					boolean flag = false;
					//城市
					if(baseItemDto.getKey().equals("cityCode")||baseItemDto.getKey().equals("city")){
						baseItemDto.setValue(MapUtils.getString(map, baseItemDto.getKey()));
						baseItemDto.setPlaceholder(MapUtils.getString(map,"cityName"));
						flag = true;
					}
					//业务产品
					else if(baseItemDto.getKey().equals("productCode")){
						baseItemDto.setValue(MapUtils.getString(map, baseItemDto.getKey()));
						baseItemDto.setPlaceholder(MapUtils.getString(map,"productName"));
						if("03".equals(MapUtils.getString(map, baseItemDto.getKey()))){
							baseItemDto.setPlaceholder("畅贷");
						}
						//初始化畅贷新增页面业务类型
						if(MapUtils.isEmpty(map)){
							baseItemDto.setValue("03");
							baseItemDto.setPlaceholder("畅贷");
						}
						flag = true;
					}
					//区域
					else if(baseItemDto.getKey().equals("houseRegion")){
						baseItemDto.setValue(MapUtils.getString(map, baseItemDto.getKey()));
						baseItemDto.setPlaceholder(MapUtils.getString(map,"houseRegionName"));
						flag = true;
					}
					if(!flag){
						if(StringUtils.isNotEmpty(MapUtils.getString(map, baseItemDto.getKey()))){
							baseItemDto.setValue(MapUtils.getString(map, baseItemDto.getKey()));
							baseItemDto.setPlaceholder(MapUtils.getString(map, baseItemDto.getKey()));
						}
					}
				}else if(baseItemDto.getType() == 9){
					if(StringUtils.isNotEmpty(MapUtils.getString(map, baseItemDto.getKey()))){
						baseItemDto.setValue(MapUtils.getString(map, baseItemDto.getKey()));
						baseItemDto.setPlaceholder(MapUtils.getString(map, baseItemDto.getKey()));
					}
                }else if(baseItemDto.getType() == 100){
                    FacesignRecognitionDto  obj = new FacesignRecognitionDto();
                    obj.setOrderNo(orderNo);
                    List<FacesignRecognitionDto> respList = httpUtil.getList(Constants.LINK_CREDIT, "/credit/process/facesign/v/faceRecognitionDetail", obj, FacesignRecognitionDto.class);
                    if(null!=respList&&respList.size()>0){
                        boolean isSuccess = false;
                        for (FacesignRecognitionDto f:respList){
                            if(1==f.getIsSuccess()){
                                isSuccess = true;
                            }
                            if(2==f.getIsSuccess()){
                              baseItemDto.setPlaceholder("匹配失败");
                              isSuccess = false;
                              break;
                          }
                        }
                        if(isSuccess) {
                            baseItemDto.setPlaceholder("匹配成功");
                        }
                    }

				}else{
					if(StringUtils.isNotEmpty(MapUtils.getString(map, baseItemDto.getKey()))){
						baseItemDto.setValue(MapUtils.getString(map, baseItemDto.getKey()));
						baseItemDto.setPlaceholder(MapUtils.getString(map, baseItemDto.getKey()));
					}
				}
				
			}
			if(StringUtils.isNotEmpty(baseItemDto.getContentType())){
				baseItemDto.setContent(getContentList(baseItemDto.getContentType()));
			}
			
			if(StringUtils.isNotEmpty(baseItemDto.getContentUrl())){
				if(StringUtils.isNotEmpty(baseItemDto.getKey())){
					params.put("key", baseItemDto.getKey());
					params.put("contentUrl", baseItemDto.getContentUrl());
					baseItemDto.setContentUrl(getContentUrl(params));
				}
			}
			if(StringUtils.isNotEmpty(baseItemDto.getKey())){
				if(baseItemDto.getKey().equals("acceptMemberUid")){
					if(pageType.equals("addOrder")){
						baseItemDto.setValue(userDto.getUid());
						baseItemDto.setPlaceholder(userDto.getName());
					}else{
						if(StringUtils.isNotEmpty(MapUtils.getString(map, baseItemDto.getKey()))){
							baseItemDto.setValue(MapUtils.getString(map, baseItemDto.getKey()));
							baseItemDto.setPlaceholder(CommonDataUtil.getUserDtoByUidAndMobile(MapUtils.getString(map, baseItemDto.getKey())).getName());
						}
					}
				}else if(baseItemDto.getKey().equals("transferTime")){
					if(productCode.equals("01")){
						baseItemDto.setTitle(baseItemDto.getTitle().replace("抵押", "过户"));
					}
				}else if(baseItemDto.getKey().equals("tlandBureau")){
					if(productCode.equals("01")){
						baseItemDto.setTitle(baseItemDto.getTitle().replace("抵押", "过户"));
					}
				}else if(baseItemDto.getKey().equals("tlandBureauUid")){
					if(productCode.equals("01")){
						baseItemDto.setTitle(baseItemDto.getTitle().replace("抵押", "过户"));
						baseItemDto.setContentUrl(baseItemDto.getContentUrl().replace("抵押", "过户"));
					}
				}else if(baseItemDto.getKey().equals("overdueRate") || baseItemDto.getKey().equals("rate")){
					if(MapUtils.getString(map,"modeid","0").equals("1")){
						baseItemDto.setSingle("%");
					}
				}
				if("orderRelation".equals(MapUtils.getString(params, "pageType"))&&baseItemDto.getKey().equals("riskGradeId")){
					baseItemDto.setContentUrl("/credit/customer/risk/v/findRiskGradeList?orderNo="+creditOrderNo);
				}
				if("orderRelation".equals(MapUtils.getString(params, "pageType"))&&baseItemDto.getKey().equals("otherPoundage")&&(MapUtils.getDouble(map, baseItemDto.getKey())==null||MapUtils.getDouble(map, baseItemDto.getKey())==0)){
					OrderBaseBorrowDto borrow = orderBaseBorrowService.selectOrderBorrowByOrderNo(creditOrderNo);
					Map<String,Object> maps = new HashMap<String, Object>();
					maps.put("productId", borrow.getCityCode()+"03");
					maps.put("cooperativeAgencyId", borrow.getCooperativeAgencyId());
					JSONObject json = httpUtil.getData(Constants.LINK_CREDIT, "/credit/customer/risk/v/findAgencyPoundage", maps);
					double otherPoundage=0;
					if(json!=null){
						JSONObject data = json.getJSONObject("data");
						if(data!=null&&!data.isNullObject()){
							Object obj=data.get("otherFees");
							if((data!=null)&&obj instanceof  Double){
								otherPoundage = data.getDouble("otherFees");
							}
						}
					}
					baseItemDto.setValue(otherPoundage+"");
					baseItemDto.setPlaceholder(otherPoundage+"");
				}
			}
			boolean isAdd = true;
			//如果是畅贷，不可编辑债务置换贷款，如果是债务置换贷款不可编辑畅贷
//			if(orderListDto!=null&&orderListDto.getRelationOrderNo()!=null&&!isChangLoan&&pageType.equals("orderRelation")){
//				//去掉畅贷确认按钮
//				if("确认".equals(baseItemDto.getTitle())){
//					//baseItemDto.setTitle("");
//					isAdd = false;
//				}
//				baseItemDto.setType(6);
//				if("备注".equals(baseItemDto.getTitle())){
//					baseItemDto.setType(7);
//					baseItemDto.setReadOnly(1);
//				}
//			}
//			if(isChangLoan&&!pageType.equals("orderRelation")
//					&&!pageType.equals("editreport")){
//				//去掉债务置换贷款借款信息的确认按钮
//				if("确认".equals(baseItemDto.getTitle())||"确定".equals(baseItemDto.getTitle())){
//					//baseItemDto.setTitle("");
//					isAdd = false;
//				}
//				baseItemDto.setType(6);
//				if("备注".equals(baseItemDto.getTitle())){
//					baseItemDto.setType(7);
//					baseItemDto.setReadOnly(1);
//				}
//			}
			if("detail".equals(arrangement)
					&&pageType.equals("editreport")
					&&8==baseItemDto.getType()) {
				iterator.remove();
				continue;
			} else if("detail".equals(arrangement)
					 &&pageType.equals("editreport")){
				 setReadOnly(baseItemDto);
			} else if(null==reportobj
					&&pageType.equals("editreport")){
				setReadOnly(baseItemDto,pageType);
			} else if(null!=reportobj
					&&pageType.equals("editreport")){
				setReadOnly(baseItemDto,true);
			}
			tempList.add(baseItemDto);
			if(baseItemDto.getIsEnd() == 1&&isAdd){
				list.add(tempList);
				tempList = new ArrayList<BaseItemDto>();
				isAdd = true;
			}
		}

		return RespHelper.setSuccessData(new RespData<List<BaseItemDto>>(), list);
	}

	public void setReadOnly(BaseItemDto obj,String pageType){
		if(StringUtils.isNotBlank(obj.getValue())
				&&!"0".equals(obj.getValue())
				&&!"estimateOutLoanTimeStr".equals(obj.getKey())
				&&!"estimateOutLoanTime".equals(obj.getKey())
				&&!"editrecord".equals(obj.getKey())
				&&!"replyrecord".equals(obj.getKey())
				&&7!=obj.getType()
				&&pageType.equals("editreport")){
			obj.setType(6);
		} else if(StringUtils.isNotBlank(obj.getValue())
				&&!"estimateOutLoanTimeStr".equals(obj.getKey())
				&&!"estimateOutLoanTime".equals(obj.getKey())
				&&!"editrecord".equals(obj.getKey())
				&&!"replyrecord".equals(obj.getKey())
				&&7==obj.getType()
				&&pageType.equals("editreport")){
			obj.setReadOnly(1);
		}
	}
	public void setReadOnly(BaseItemDto obj){
		if(!"editrecord".equals(obj.getKey())
				&&!"replyrecord".equals(obj.getKey())
				&&7!=obj.getType()){
			obj.setType(6);
		} else if(!"editrecord".equals(obj.getKey())
				&&!"replyrecord".equals(obj.getKey())
				&&7==obj.getType()){
			obj.setReadOnly(1);
		}
	}
	public void setReadOnly(BaseItemDto obj,boolean isEdit){
		if(isEdit){
			if(!"editrecord".equals(obj.getKey())
					&&!"replyrecord".equals(obj.getKey())
					&&!"estimateOutLoanTime".equals(obj.getKey())
					&&!"estimateOutLoanTimeStr".equals(obj.getKey())
					&&7!=obj.getType()
					&&8!=obj.getType()){
				obj.setType(6);
			} else if(!"editrecord".equals(obj.getKey())
					&&!"replyrecord".equals(obj.getKey())
					&&!"estimateOutLoanTime".equals(obj.getKey())
					&&!"estimateOutLoanTimeStr".equals(obj.getKey())
					&&7==obj.getType()){
				obj.setReadOnly(1);
			}
		} else {
			if(!"editrecord".equals(obj.getKey())
					&&!"replyrecord".equals(obj.getKey())
					&&7!=obj.getType()){
				obj.setType(6);
			} else if(!"editrecord".equals(obj.getKey())
					&&!"replyrecord".equals(obj.getKey())
					&&7==obj.getType()){
				obj.setReadOnly(1);
			}
		}

	}

	public Map<String,Object> getObjectValue(Map<String,Object> map,String pageType,String orderNo,Integer id,UserDto user,OrderListDto orderListDto){
		if(pageType.equals("notarization")){
			NotarizationDto notarizationDto = new NotarizationDto();
			notarizationDto.setOrderNo(orderNo);
			notarizationDto = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/process/notarization/v/detail", notarizationDto, NotarizationDto.class);
			map = BeanToMapUtil.beanToMap(notarizationDto);
		}else if(pageType.equals("facesign")){
			FaceSignDto facesign = new FaceSignDto();
			facesign.setOrderNo(orderNo);
			facesign = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/process/facesign/v/detail", facesign, FaceSignDto.class);
			map = BeanToMapUtil.beanToMap(facesign);
		}else if(pageType.equals("foreclosure")){
			ForeclosureDto  obj = new ForeclosureDto();
			obj.setOrderNo(orderNo);
			obj = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/process/foreclosure/v/detail", obj, ForeclosureDto.class);
			map = BeanToMapUtil.beanToMap(obj);
		}else if(pageType.equals("forensics")){
			ForensicsDto  obj = new ForensicsDto();
			obj.setOrderNo(orderNo);
			obj = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/process/forensics/v/detail", obj, ForensicsDto.class);
			map = BeanToMapUtil.beanToMap(obj);
		}else if(pageType.equals("cancellation")){
			CancellationDto  obj = new CancellationDto();
			obj.setOrderNo(orderNo);
			obj = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/process/cancellation/v/detail", obj, CancellationDto.class);
			map = BeanToMapUtil.beanToMap(obj);
		}else if(pageType.equals("transfer")){
			TransferDto  obj = new TransferDto();
			obj.setOrderNo(orderNo);
			obj = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/process/transfer/v/detail", obj, TransferDto.class);
			map = BeanToMapUtil.beanToMap(obj);
		}else if(pageType.equals("newlicense")){
			NewlicenseDto  obj = new NewlicenseDto();
			obj.setOrderNo(orderNo);
			obj = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/process/newlicense/v/detail", obj, NewlicenseDto.class);
			map = BeanToMapUtil.beanToMap(obj);
		}else if(pageType.equals("mortgage")){
			MortgageDto  obj = new MortgageDto();
			obj.setOrderNo(orderNo);
			obj = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/process/mortgage/v/detail", obj, MortgageDto.class);
			map = BeanToMapUtil.beanToMap(obj);
		}else if(pageType.equals("element")){
			DocumentsReturnDto obj = new DocumentsReturnDto();
			obj.setOrderNo(orderNo);
			obj = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/element/return/v/detail", obj, DocumentsReturnDto.class);
			map = BeanToMapUtil.beanToMap(obj);
		}else if(pageType.equals("orderBorrBorrow")){
			OrderBaseBorrowDto baseBorrowDto = orderBaseBorrowService.selectOrderBorrowByOrderNo(orderNo);
			baseBorrowDto = getOrderBaseBorrowDto(baseBorrowDto);
			map = BeanToMapUtil.beanToMap(baseBorrowDto);
			if(null!=baseBorrowDto
                    &&null!=baseBorrowDto.getOrderReceivableForDto()
                    &&baseBorrowDto.getOrderReceivableForDto().size()>0){
                List<OrderBaseReceivableForDto>  list = baseBorrowDto.getOrderReceivableForDto();
                if(baseBorrowDto.getIsOnePay()!=null&&baseBorrowDto.getIsOnePay()==1){
                    OrderBaseReceivableForDto tmp = list.get(0);
                    String payMentAmountOne=String.valueOf(tmp.getPayMentAmount());
                    if(tmp.getPayMentAmount() != null){
                        if(isMatch(tmp.getPayMentAmount())){
                        	payMentAmountOne = payMentAmountOne.substring(0, payMentAmountOne.indexOf(".")==-1?payMentAmountOne.length():payMentAmountOne.indexOf("."));
                        }
                    }else{
                    	payMentAmountOne = "-";
                    }
                    map.put("payMentAmountOne",payMentAmountOne);
                    map.put("payMentAmountDateOne",tmp.getPayMentAmountDate());
                } else if(baseBorrowDto.getIsOnePay()!=null&&baseBorrowDto.getIsOnePay()==2
                        &&baseBorrowDto.getOrderReceivableForDto().size()>1){
					OrderBaseReceivableForDto tmp = list.get(0);
					String payMentAmountOne=tmp.getPayMentAmount().toString();
					if(tmp.getPayMentAmount() != null){
	                    if(isMatch(tmp.getPayMentAmount())){
	                    	payMentAmountOne = payMentAmountOne.substring(0, payMentAmountOne.indexOf(".")==-1?payMentAmountOne.length():payMentAmountOne.indexOf("."));
	                    }
					}else{
                    	payMentAmountOne = "-";
                    }
					map.put("payMentAmountOne",payMentAmountOne);
					map.put("payMentAmountDateOne",tmp.getPayMentAmountDate());
                    tmp = list.get(1);
                    String payMentAmountTwo=tmp.getPayMentAmount().toString();
                    if(tmp.getPayMentAmount() != null){
	                    if(isMatch(tmp.getPayMentAmount())){
	                    	payMentAmountTwo = payMentAmountTwo.substring(0, payMentAmountTwo.indexOf(".")==-1?payMentAmountTwo.length():payMentAmountTwo.indexOf("."));
	                    }
                    }else{
                    	payMentAmountTwo = "-";
                    }
                    map.put("payMentAmountTwo",payMentAmountTwo);
                    map.put("payMentAmountDateTwo",tmp.getPayMentAmountDate());
                }
            }
			if(baseBorrowDto.getOtherPoundage()==null){
				map.put("otherPoundage", null);
			}else{
				BigDecimal otherPoundage = new BigDecimal(baseBorrowDto.getOtherPoundage());
				map.put("otherPoundage", otherPoundage.toString());
			}
			if(baseBorrowDto.getCustomsPoundage()==null){
				map.put("customsPoundage", null);
			}else{
				BigDecimal customsPoundage = new BigDecimal(baseBorrowDto.getCustomsPoundage());
				map.put("customsPoundage", customsPoundage.toString());
			}
			if(baseBorrowDto.getChargeMoney()==null){
				map.put("chargeMoney", null);
			}else{
				BigDecimal chargeMoney = new BigDecimal(baseBorrowDto.getChargeMoney());
				map.put("chargeMoney", chargeMoney.toString());
			}
		}else if(pageType.equals("orderRelation")){
			OrderBaseBorrowDto orderBaseBorrowDto = orderBaseBorrowService.selectOrderBorrowByOrderNo(orderNo);
			String tempOrderNo = "";
			if(orderBaseBorrowDto.getOrderBaseBorrowRelationDto()!=null&&orderBaseBorrowDto.getOrderBaseBorrowRelationDto().size()>0){
				tempOrderNo = orderBaseBorrowDto.getOrderBaseBorrowRelationDto().get(0).getOrderNo();
			}
			OrderBaseBorrowRelationDto orderBaseBorrowRelationDto = orderBaseBorrowRelationService.selectRelationByOrderNo(tempOrderNo);
			map = BeanToMapUtil.beanToMap(orderBaseBorrowRelationDto);
			if(orderBaseBorrowRelationDto!=null&&orderBaseBorrowRelationDto.getOtherPoundage()==null){
				map.put("otherPoundage", null);
			}else if(orderBaseBorrowRelationDto!=null){
				BigDecimal otherPoundage = new BigDecimal(orderBaseBorrowRelationDto.getOtherPoundage());
				map.put("otherPoundage", otherPoundage.toString());
			}
			if(orderBaseBorrowRelationDto!=null&&orderBaseBorrowRelationDto.getChargeMoney()==null){
				map.put("chargeMoney", null);
			}else if(orderBaseBorrowRelationDto!=null){
				BigDecimal chargeMoney = new BigDecimal(orderBaseBorrowRelationDto.getChargeMoney());
				map.put("chargeMoney", chargeMoney.toString());
			}
		}else if(pageType.equals("orderBaseCustomer")){
			OrderBaseCustomerDto customerDto = orderBaseCustomerService.selectOrderCustomerByOrderNo(orderNo);
			map = BeanToMapUtil.beanToMap(customerDto);
		}else if(pageType.equals("orderBaseCustomerBorrower")){
			OrderBaseCustomerBorrowerDto orderBaseCustomerBorrower = orderBaseCustomerBorrowerService.selectOrderCustomerBorrowerById(id);
			if(null!=orderBaseCustomerBorrower){
				map = BeanToMapUtil.beanToMap(orderBaseCustomerBorrower);
			}
		}else if(pageType.equals("orderBaseCustomerGuarantee")){
			OrderBaseCustomerGuaranteeDto orderBaseCustomerGuaranteeDto = orderBaseCustomerGuaranteeService.selectOrderCustomerGuaranteeById(id);
			if(null!=orderBaseCustomerGuaranteeDto){
				map = BeanToMapUtil.beanToMap(orderBaseCustomerGuaranteeDto);
			}
		}else if(pageType.equals("orderBaseHouse")){
			OrderBaseHouseDto orderBaseHouseDto = orderBaseHouseService.selectOrderHouseByOrderNo(orderNo);
			map = BeanToMapUtil.beanToMap(orderBaseHouseDto);
		}else if(pageType.equals("orderBaseHouseProperty")){//房产信息
			//查询城市名称
			OrderBaseBorrowDto borrow = orderBaseBorrowService.selectOrderBorrowByOrderNo(orderNo);
			String cityCode = borrow.getCityCode();
			String cityName = "";
			List<DictDto> dictDtos = getDictDtoByType("bookingSzAreaOid");
			for (DictDto dictDto : dictDtos) {
				if(dictDto.getCode().equals(cityCode)){
					cityName = dictDto.getName();
					break;
				}
			}
			OrderBaseHousePropertyDto orderBaseHousePropertyDto = orderBaseHousePropertyService.selectOrderHousePropertyById(id);
			if(null!=orderBaseHousePropertyDto){
				orderBaseHousePropertyDto = getOrderBaseHouseProperty(orderBaseHousePropertyDto);
				orderBaseHousePropertyDto.setCity(cityCode);
				orderBaseHousePropertyDto.setCityName(cityName);
				map = BeanToMapUtil.beanToMap(orderBaseHousePropertyDto);
				if(orderBaseHousePropertyDto.getHouseRecordPrice()!=null){
					BigDecimal b = new BigDecimal(orderBaseHousePropertyDto.getHouseRecordPrice());
					map.put("houseRecordPrice", b.toString());
				}
			}else{
				OrderBaseHousePropertyDto bean = new OrderBaseHousePropertyDto();
				bean.setCity(cityCode);
				bean.setCityName(cityName);
				map = BeanToMapUtil.beanToMap(bean);
			}
		}else if(pageType.equals("orderBaseHousePropertyPeople")){
			OrderBaseHousePropertyPeopleDto orderBaseHousePropertyPeopleDto = orderBaseHousePropertyPeopleService.selectOrderPropertyPeopleById(id);
			if(null!=orderBaseHousePropertyPeopleDto){
				map = BeanToMapUtil.beanToMap(orderBaseHousePropertyPeopleDto);
			}
		}else if(pageType.equals("orderBaseHousePurchaser")){
			OrderBaseHousePurchaserDto orderBaseHousePurchaserDto = orderBaseHousePurchaserService.selectOrderHousePurchaserById(id);
			if(null!=orderBaseHousePurchaserDto){
				map = BeanToMapUtil.beanToMap(orderBaseHousePurchaserDto);
			}
		} else if ("orderCredit".equals(pageType)){
			CreditDto obj = new CreditDto();
			obj.setOrderNo(orderNo);
			obj = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/risk/ordercredit/v/detail", obj, CreditDto.class);
			if(null!=obj){
				map = BeanToMapUtil.beanToMap(obj);
			}
		}else if (pageType.equals("orderForeclosureType")){
			DocumentsDto documentsDto = new DocumentsDto();
			documentsDto.setOrderNo(orderNo);
			DocumentsDto obj= httpUtil.getObject(Constants.LINK_CREDIT, "/credit/element/basics/v/detail", documentsDto,DocumentsDto.class);
			if(null!=obj){
				map = BeanToMapUtil.beanToMap(obj.getForeclosureType());
			}
		}else if (pageType.equals("orderPaymentType")){
			DocumentsDto documentsDto = new DocumentsDto();
			documentsDto.setOrderNo(orderNo);
			DocumentsDto obj= httpUtil.getObject(Constants.LINK_CREDIT, "/credit/element/basics/v/detail", documentsDto,DocumentsDto.class);
			if(null!=obj){
				map = BeanToMapUtil.beanToMap(obj.getPaymentType());
				if(null!=obj.getPaymentType()&&null!=obj.getPaymentType().getQuota()) {
					BigDecimal b = new BigDecimal(obj.getPaymentType().getQuota()+"");
					map.put("quota", b.toString());
				}
			}
		} else if(pageType.equals("editreport")){
			ReportDto report = new ReportDto();
			if(null!=orderListDto) {
				report.setOrderNo(orderListDto.getOrderNo());
			} else {
				report.setOrderNo(orderNo);
			}
			report = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/finance/report/v/detailByStatus", report, ReportDto.class);
			if(null!=report){
				map =  BeanToMapUtil.beanToMap(report);
				if(StringUtils.isNotBlank(report.getReportEditRecordStr())){
					map.put("editrecord",report.getReportEditRecordStr());
				}
				if(StringUtils.isNotBlank(report.getReportReplyRecordStr())){
					map.put("replyrecord",report.getReportReplyRecordStr());
				}
				map.put("reportobj",report);
			} else {
				report = getReport(user,orderNo,orderListDto);
				map =  BeanToMapUtil.beanToMap(report);
			}
		}
		return map;
	}

	public ReportDto getReport(UserDto user,String orderNo,OrderListDto orderListDto){
		ReportDto dto = new ReportDto();
		dto.setAcceptMemberName(user.getName());
		dto.setAcceptMemberUid(user.getUid());
		if(StringUtils.isNotBlank(orderNo)){
			DocumentsDto doc = new DocumentsDto();
			doc.setOrderNo(orderNo);
			doc = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/element/basics/v/detail", doc,DocumentsDto.class);
			dto.setCityCode(orderListDto.getCityCode());
			dto.setCityName(orderListDto.getCityName());
			dto.setProductCode(orderListDto.getProductCode());
			dto.setProductName(orderListDto.getProductName());
			dto.setCooperativeAgencyId(orderListDto.getCooperativeAgencyId());
			dto.setCooperativeAgencyName(orderListDto.getCooperativeAgencyName());
			dto.setChannelManagerName(orderListDto.getChannelManagerName());
			dto.setChannelManagerUid(orderListDto.getChannelManagerUid());
			dto.setCustomerName(orderListDto.getCustomerName());
			dto.setLoanAmount(orderListDto.getBorrowingAmount());
			dto.setBorrowingDays(orderListDto.getBorrowingDay());
			if(null!=doc&&null!=doc.getForeclosureType()){
				dto.setPaymentType(doc.getForeclosureType().getForeclosureType());
			}
		}
		return dto;
	}
	
	private List<Map<String, String>> getContentList(String contentType){
		List<Map<String, String>> content = new ArrayList<Map<String,String>>();
		if(contentType.equals("cityList")){
			List<DictDto> dictDtos = getDictDtoByType("bookingSzAreaOid");
			for (DictDto dictDto : dictDtos) {
				if(StringUtils.isEmpty(dictDto.getPcode())){
					Map<String, String> map = new HashMap<String, String>();
					map.put("id", dictDto.getCode());
					map.put("name", dictDto.getName());
					content.add(map);
				}
			}
		}else if(contentType.equals("sexList")){
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", "男");
			map.put("name", "男");
			content.add(map);
			map = new HashMap<String, String>();
			map.put("id", "女");
			map.put("name", "女");
			content.add(map);
		}else if(contentType.equals("isBankEndList")){
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", "0");
			map.put("name", "是");
			content.add(map);
			map = new HashMap<String, String>();
			map.put("id", "1");
			map.put("name", "否");
			content.add(map);
		}else if(contentType.equals("isOldLoanBankList")){
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", "1");
			map.put("name", "是");
			content.add(map);
			map = new HashMap<String, String>();
			map.put("id", "2");
			map.put("name", "否");
			content.add(map);
		}else if(contentType.equals("borrowerIsPropertyProleList")){
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", "是");
			map.put("name", "是");
			content.add(map);
			map = new HashMap<String, String>();
			map.put("id", "否");
			map.put("name", "否");
			content.add(map);
		}else if(contentType.equals("paymentModeList")){
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", "柜台还款");
			map.put("name", "柜台还款");
			content.add(map);
			map = new HashMap<String, String>();
			map.put("id", "网银转账");
			map.put("name", "网银转账");
			content.add(map);
			map = new HashMap<String, String>();
			map.put("id", "POS刷卡");
			map.put("name", "POS刷卡");
			content.add(map);
		}else if(contentType.equals("accountTypeList")){
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", "个人");
			map.put("name", "个人");
			content.add(map);
			map = new HashMap<String, String>();
			map.put("id", "公司");
			map.put("name", "公司");
			content.add(map);
		}else if(contentType.equals("foreclosureTypeList")){
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", "银行自动扣款");
			map.put("name", "银行自动扣款");
			content.add(map);
			map = new HashMap<String, String>();
			map.put("id", "柜台还款");
			map.put("name", "柜台还款");
			content.add(map);
		}
		
		
		return content;
	}
	
	private String getContentUrl(Map<String, Object> params){
		String key = MapUtils.getString(params, "key");
		String contentUrl = MapUtils.getString(params, "contentUrl");
		String cityCode = MapUtils.getString(params, "cityCode");
		String productCode = MapUtils.getString(params, "productCode");
		if(key.equals("notarizationAddressCode") || key.equals("mlandBureau") || key.equals("mlandBureau") || key.equals("nlandBureau") || key.equals("tlandBureau") || key.equals("clandBureau")  ){
			contentUrl += cityCode;
		}else if(key.equals("licenseReverUid") || key.equals("clandBureauUid") || key.equals("tlandBureauUid") || key.equals("nlandBureauUid") || key.equals("mlandBureauUid")){
			contentUrl += "&cityCode="+cityCode+"&productCode="+productCode;
		}else if(key.equals("tlandBureauUid")){
			if(productCode.equals("01")){
				contentUrl = contentUrl.replace("抵押", "过户");
			}
		}else if(key.equals("houseRegion")){
			contentUrl += cityCode;
		}else if(key.equals("foreclosureMemberUid")){	
			contentUrl += "&cityCode="+cityCode+"&productCode="+productCode;
		}else if(key.equals("riskGradeId")){
			if(MapUtils.getString(params, "pageType").equals("orderRelation")){
				contentUrl += "&cityCode="+cityCode+"&productCode=03";
			}
		}
		
		return contentUrl;
	}
	
	/**
	 * 出回款专用
	 * @param request
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getCHProductList") 
	public RespData<Map<String, Object>> getCHProductList(HttpServletRequest request,@RequestBody Map<String, Object> params){
		RespData<Map<String, Object>> resp = getProductList(request, params);
		List<Map<String, Object>> tempList = new ArrayList<Map<String,Object>>();
		for (Map<String, Object> map : resp.getData()) {
			if("10000".equals(MapUtils.getString(map, "id"))){
				continue;
			}
			tempList.add(map);
		}
		resp.setData(tempList);
		return resp;
	}
	
	@ResponseBody
	@RequestMapping(value = "/getProductList") 
	public RespData<Map<String, Object>> getProductList(HttpServletRequest request,@RequestBody Map<String, Object> params){
		// 机构城市产品
		boolean isAgency =false;
		Map<String, String> mapCityProduct = null;
		if(params.get("agencyId")!=null){
			isAgency = MapUtils.getInteger(params, "agencyId")>1;
			if(isAgency){
				RespDataObject<Map<String, String>> respData = httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/customer/agencyProduct/searchEnabled_"+MapUtils.getInteger(params, "agencyId"), null, Map.class);
				if(RespStatusEnum.SUCCESS.getCode().equals(respData.getCode())){
					mapCityProduct = respData.getData();
				}
			}
		}
		UserDto userDto = getUserDtoByMysql(request);
		int isEnable = userDto.getIsEnable();
		List<DictDto> cityListTemp = getDictDtoByType("bookingSzAreaOid");
		List<ProductDto> productListTemp = getProductDtos();
		List<Map<String, Object>> productList = new ArrayList<Map<String,Object>>();
		for (DictDto dictDto : cityListTemp) {
			if(dictDto.getCode().equals(MapUtils.getString(params, "cityCode"))){
				for (ProductDto productDto : productListTemp) {
					if(productDto.getCityCode().equals(dictDto.getCode())){
						//机构已开通城市产品
						if(isAgency && null!=mapCityProduct ){
							String products = String.format(",%s,", MapUtils.getString(mapCityProduct, productDto.getCityCode(), "0"));
							if(!products.contains(String.format(",%s,", productDto.getProductCode()))){
								continue;
							}
							//机构去掉房抵贷
							if(productDto.getProductCode().equals("04")){
								continue;
							}
						}
						//放开畅贷提单
						if(isEnable==0||(isEnable!=0&&!productDto.getProductCode().equals("03"))){
							Map<String, Object> productMap = new HashMap<String, Object>();
							productMap.put("id", productDto.getProductCode());
							productMap.put("name", productDto.getProductName());
							productList.add(productMap);
						}
					}
				}
			}
		}
		return RespHelper.setSuccessData(new RespData<Map<String, Object>>(), productList);
	}
	@ResponseBody
	@RequestMapping(value = "/getAllProductList")
	public RespData<Map<String, Object>> getAllProductList(@RequestBody Map<String, Object> params){
		String cityCode = MapUtils.getString(params,"cityCode","");
		List<ProductDto> productListTemp = getProductDtos();
		List<Map<String, Object>> productList = new ArrayList<Map<String,Object>>();
		if(StringUtils.isNotBlank(cityCode)){
			for (ProductDto productDto : productListTemp) {
				if(productDto.getCityCode().equals(cityCode)){
					Map<String, Object> productMap = new HashMap<String, Object>();
					productMap.put("id", productDto.getProductCode());
					productMap.put("name", productDto.getProductName());
					productList.add(productMap);
				}
			}
		}
		return RespHelper.setSuccessData(new RespData<Map<String, Object>>(), productList);
	}
	
	/**
	 * 查询订单详情中的名称
	 * 
	 * @param orderBaseBorrowDto
	 * @return
	 */
	public OrderBaseBorrowDto getOrderBaseBorrowDto(OrderBaseBorrowDto orderBaseBorrowDto){
		//获取所有城市
		List<DictDto> dictList = getDictDtoByType("bookingSzAreaOid");
		for (DictDto dictDto : dictList) {
			if(dictDto.getCode().equals(orderBaseBorrowDto.getCityCode())){
				orderBaseBorrowDto.setCityName(dictDto.getName());
				break;
			}
		}
		//获取产品名称
		List<ProductDto> prductList = getProductDtos();
		for (ProductDto productDto : prductList) {
			if(productDto.getProductCode().equals(orderBaseBorrowDto.getProductCode())){
				orderBaseBorrowDto.setProductName(productDto.getProductName());
				break;
			}
		}
		//收费类型名称
		List<DictDto> dicts = getDictDtoByType("riskControl");
		if(dicts!=null&&dicts.size()>0){
			for (DictDto dictDto : dicts) {
				if(dictDto.getCode().equals(String.valueOf(orderBaseBorrowDto.getRiskGradeId()))){
					orderBaseBorrowDto.setRiskGrade(dictDto.getName());
				}
				if(orderBaseBorrowDto.getRiskGradeId()!=null&&orderBaseBorrowDto.getRiskGradeId()==0){
					orderBaseBorrowDto.setRiskGrade("其他");
				}
			}
		}
		//获取合作机构名称
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("agencyId", orderBaseBorrowDto.getCooperativeAgencyId());
		AgencyDto agencyDto = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/customer/agency/v/getAgencyDto", map, AgencyDto.class);
		if(agencyDto!=null){
			orderBaseBorrowDto.setCooperativeAgencyName(agencyDto.getName());
		}
		return orderBaseBorrowDto;
	}
	
	/**
	 * 附加城市，区域名称
	 * @param orderBaseHousePropertyDto
	 * @return
	 */
	public OrderBaseHousePropertyDto getOrderBaseHouseProperty(OrderBaseHousePropertyDto orderBaseHousePropertyDto){
		
		//获取所有城市
		List<DictDto> dictList = getDictDtoByType("bookingSzAreaOid");
		for (DictDto dictDto : dictList) {
			if(dictDto.getCode().equals(orderBaseHousePropertyDto.getCity())){
				orderBaseHousePropertyDto.setCityName(dictDto.getName());
			}
			if(dictDto.getCode().equals(orderBaseHousePropertyDto.getHouseRegion())&&dictDto.getPcode().equals(orderBaseHousePropertyDto.getCity())){
				orderBaseHousePropertyDto.setHouseRegionName(dictDto.getName());
			}
		}
		return orderBaseHousePropertyDto;
	}

	/**
	 * 根据字典类型,获取code名称
	 * @param dictType
	 * @param dictCode
	 * @return
	 */
	public String getDictName(String dictType,Integer dictCode){
		String name = "";
		List<DictDto> dictList = getDictDtoByType(dictType);
		for (DictDto d:dictList){
			if(d.getId()==dictCode){
				return d.getName();
			}
		}
		return name;
	}

	/**
	 * 根据渠道经理查询合作机构集合
	 * @param channelManagerUid
	 * @return
	 */
	public List<Map<String,Object>> getAgencyList(String channelManagerUid){
		if(StringUtils.isBlank(channelManagerUid))
			return Collections.emptyList();

		Map<String,Object> param = new HashMap<String,Object>();
		param.put("channelManagerUid",channelManagerUid);
		RespData<Map<String,Object>> result = httpUtil.getRespData(Constants.LINK_CREDIT, "/credit/customer/chanlman/v/findAgencyBychannelManager", param,Map.class);
		if(RespStatusEnum.SUCCESS.getCode().equals(result.getCode())){
			return result.getData();
		}
		return  Collections.emptyList();
	}

	/**
	 * 根据渠道经理查询订单选择的的合作机构
	 * @param channelManagerUid
	 * @return
	 */
	public Map<String,Object> getAgency(String channelManagerUid,Integer cooperativeAgencyId){
		List<Map<String,Object>> list = getAgencyList(channelManagerUid);
		if (null!=list&&list.size()>0){
			for (Map<String,Object> tmp:list){
				if(MapUtils.getInteger(tmp,"id")==cooperativeAgencyId){
					return tmp;
				}
			}
		}
		return Collections.emptyMap();
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
	
	public static boolean isMatch(Object tempValue){
		Pattern  pattern = Pattern.compile("^(([0-9]{1}\\d*)(\\.[0]{1}))$"); 
		return pattern.matcher(tempValue.toString()).matches();
	}

    @ResponseBody
    @RequestMapping(value = "/appFacePageModel")
    public RespData<Map<String,Object>> appFacePageModel(HttpServletRequest request, @RequestBody Map<String, Object> params) {
        List<Map<String,Object>> baseCompleteDtoList = new ArrayList<Map<String,Object>>();
        RespData<Map<String,Object>> result = new RespData<Map<String,Object>>();
        result.setCode(RespStatusEnum.FAIL.getCode());
        result.setMsg(RespStatusEnum.FAIL.getMsg());
        try{
            if(StringUtils.isBlank(MapUtils.getString(params,"orderNo",""))){
                result.setMsg("获取人脸识别信息类别缺少订单编号");
                return result;
            }
            String orderNo = MapUtils.getString(params, "orderNo");
            FacesignRecognitionDto  obj = new FacesignRecognitionDto();
            obj.setOrderNo(orderNo);
            List<FacesignRecognitionDto> respList = httpUtil.getList(Constants.LINK_CREDIT, "/credit/process/facesign/v/faceRecognitionDetail", obj, FacesignRecognitionDto.class);
            Map<String,Object> map = null;
            if(null!=respList&&respList.size()>0){
                for(int i=0;i<respList.size();i++){
                    map = new HashMap<String,Object>();
                    map.put("title",respList.get(i).getCustomerType()+(i+1));
                    map.put("id",respList.get(i).getId());
                    map.put("isSuccess",respList.get(i).getIsSuccess());
                    map.put("customerCardNumber",respList.get(i).getCustomerCardNumber());
                    map.put("customerName",respList.get(i).getCustomerName());
                    map.put("orderNo",respList.get(i).getOrderNo());
                    baseCompleteDtoList.add(map);
                }
            } else {
                map = new HashMap<String,Object>();
                map.put("title","借款人1");
                map.put("id",0);
                map.put("isSuccess",0);
                map.put("customerCardNumber","");
                map.put("customerName","");
                map.put("orderNo",orderNo);
                baseCompleteDtoList.add(map);
            }
            result.setData(baseCompleteDtoList);
            result.setCode(RespStatusEnum.SUCCESS.getCode());
            result.setMsg(RespStatusEnum.SUCCESS.getMsg());
        } catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/appFaceRecognition")
    public RespDataObject<FacesignRecognitionDto> appFaceRecognition(HttpServletRequest request,@RequestBody FacesignRecognitionDto face){
        RespDataObject<FacesignRecognitionDto> result = new RespDataObject<FacesignRecognitionDto>();
        result.setMsg(RespStatusEnum.FAIL.getMsg());
        result.setCode(RespStatusEnum.FAIL.getCode());
        try{
            if(StringUtils.isBlank(face.getCallbackkey())||StringUtils.isBlank(face.getExuid())){
                result.setMsg("识别信息缺少参数!");
                return result;
            }
            if(StringUtils.isBlank(face.getOrderNo())){
                result.setMsg("缺少订单编号!");
                return result;
            }
            //调用人脸识别接口
            RespDataObject<JSONObject>  queryResult =  httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/third/api/baidu/face/query", face, JSONObject.class);
            String score = null;
            if(RespStatusEnum.SUCCESS.getCode().equals(queryResult.getCode())){
				JSONObject json = queryResult.getData();

            	if(null!=json&&json.containsKey("imageUrl")){
                    String imgUrl = queryResult.getData().getString("imageUrl");
                    face.setImageUrl(imgUrl);
                }
                if(null!=json&&json.containsKey("score")&&StringUtils.isNotBlank(json.getString("score"))){
                    score = json.getString("score");
                    BigDecimal bigDecimal = new BigDecimal(score);
                    int isSuccess = bigDecimal.doubleValue()>=70d?1:2;
                    face.setIsSuccess(isSuccess);
                    face.setScore(score);
				} else{
                    face.setIsSuccess(2);
                }
            } else {
                face.setIsSuccess(2);
            }
            UserDto userDto = getUserDto(request);
            face.setCustomerType("借款人");
            if(0==face.getId()) {
                face.setCreateUid(userDto.getUid());
                result =  httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/process/facesign/v/faceRecognition", face, FacesignRecognitionDto.class);
            } else {
                face.setUpdateUid(userDto.getUid());
                RespStatus respStatus = httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/process/facesign/v/updateFaceRecognition", face);
                result.setData(face);
                result.setMsg(respStatus.getMsg());
                result.setCode(respStatus.getCode());
            }
            result.getData().setScore(score);
        } catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/deleteFacesignRecognition")
    public RespStatus deleteFacesignRecognition(HttpServletRequest request, @RequestBody  FacesignRecognitionDto face){
        RespStatus resp=new RespStatus();
        resp.setCode(RespStatusEnum.FAIL.getCode());
        resp.setMsg(RespStatusEnum.FAIL.getMsg());
        try{
            resp = httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/process/facesign/v/deleteFacesignRecognition", face);
        } catch (Exception e){
            e.printStackTrace();
        }
        return resp;
    }

}
