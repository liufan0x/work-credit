package com.anjbo.controller;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.bean.common.DictDto;
import com.anjbo.bean.config.page.PageConfigDto;
import com.anjbo.bean.config.page.PageTabConfigDto;
import com.anjbo.bean.config.page.PageTabRegionConfigDto;
import com.anjbo.bean.config.page.PageTabRegionFormConfigDto;
import com.anjbo.bean.element.DocumentsDto;
import com.anjbo.bean.element.DocumentsReturnDto;
import com.anjbo.bean.element.ForeclosureTypeDto;
import com.anjbo.bean.element.PaymentTypeDto;
import com.anjbo.bean.finance.ApplyLoanDto;
import com.anjbo.bean.finance.LendingDto;
import com.anjbo.bean.finance.LendingHarvestDto;
import com.anjbo.bean.finance.LendingInstructionsDto;
import com.anjbo.bean.finance.LendingPayDto;
import com.anjbo.bean.finance.PaymentReportDto;
import com.anjbo.bean.finance.RebateDto;
import com.anjbo.bean.finance.ReceivableForDto;
import com.anjbo.bean.finance.ReceivablePayDto;
import com.anjbo.bean.finance.ReportDto;
import com.anjbo.bean.order.OrderBaseBorrowDto;
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
import com.anjbo.bean.product.CancellationDto;
import com.anjbo.bean.product.DistributionMemberDto;
import com.anjbo.bean.product.FaceSignDto;
import com.anjbo.bean.product.FacesignRecognitionDto;
import com.anjbo.bean.product.FddMortgageReleaseDto;
import com.anjbo.bean.product.FddMortgageStorageDto;
import com.anjbo.bean.product.FddReleaseDto;
import com.anjbo.bean.product.ForeclosureDto;
import com.anjbo.bean.product.ForensicsDto;
import com.anjbo.bean.product.ManagerAuditDto;
import com.anjbo.bean.product.MortgageDto;
import com.anjbo.bean.product.NewlicenseDto;
import com.anjbo.bean.product.NotarizationDto;
import com.anjbo.bean.product.TransferDto;
import com.anjbo.bean.risk.AllocationFundAduitDto;
import com.anjbo.bean.risk.AllocationFundDto;
import com.anjbo.bean.risk.CreditDto;
import com.anjbo.bean.risk.DataAuditDto;
import com.anjbo.bean.risk.FinalAuditDto;
import com.anjbo.bean.risk.FirstAuditDto;
import com.anjbo.bean.risk.JusticeAuditDto;
import com.anjbo.bean.risk.ReviewAuditDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.Constants;
import com.anjbo.common.DateUtil;
import com.anjbo.common.RedisOperator;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.ConfigBusinfoTypeService;
import com.anjbo.service.PageConfigService;
import com.anjbo.utils.BeanToMapUtil;
import com.anjbo.utils.CommonDataUtil;
import com.anjbo.utils.ConfigUtil;
import com.anjbo.utils.HttpUtil;
import com.anjbo.utils.JsonUtil;
import com.anjbo.utils.NumberUtil;
import com.anjbo.utils.UidUtil;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

@Controller
@RequestMapping("/credit/config/page/app/base/v")
public class AppPageConfigController extends BaseController{

	private Log log = LogFactory.getLog(getClass());

	@Resource
	private PageConfigService pageConfigService;
	@Resource
	private ConfigBusinfoTypeService configBusinfoTypeService;

	/**
	 * 初始化页面配置
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/initPageConfig") 
	public RespStatus initPageConfig(){
		RespStatus resp = new RespStatus();
		try {
			pageConfigService.initPageConfig();
			RespHelper.setSuccessRespStatus(resp);
			log.info("初始App页面配置成功");
		} catch (Exception e) {
			log.error("初始App页面配置失败", e);
			RespHelper.setFailRespStatus(resp, RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}


	/**
	 * 初始表单下拉框配置
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/initSelectConfig") 
	public RespStatus initSelectConfig(){
		RespStatus resp = new RespStatus();
		try {
			pageConfigService.initSelectConfig();
			RespHelper.setSuccessRespStatus(resp);
			log.info("初始App表单下拉框配置成功");
		} catch (Exception e) {
			log.error("初始App表单下拉框配置失败",e);
			RespHelper.setFailRespStatus(resp, RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}
	
	/**
	 * 根据产品code获取系统名称
	 * @param params
	 * @return
	 */
	private String setProductSystemName(Map<String, Object> params){
		if("10000".equals(MapUtils.getString(params, "productCode",""))){
			return "tbl_cm";
		}else if("04".equals(MapUtils.getString(params, "productCode",""))){
			return "tbl_fdd";
		}else if("06".equals(MapUtils.getString(params, "productCode",""))){
			return "tbl_pth";
		}else if("07".equals(MapUtils.getString(params, "productCode",""))){
			return "tbl_tdl";
		}else{
			return "tbl_sl";
		}
	}
	
	/**
	 * 根据产品code,processId获取pageClass
	 * @param params
	 * @return
	 */
	private String setPageClass(Map<String, Object> params){
		if(StringUtils.isEmpty(MapUtils.getString(params, "orderNo",""))&&"kgAddOrder".equals(MapUtils.getString(params, "processId",""))){
			return "tbl_order_kgAddOrder_page";
		}else if(StringUtils.isEmpty(MapUtils.getString(params, "orderNo",""))&&!"editreport".equals(MapUtils.getString(params, "processId",""))){
			return "tbl_order_addOrder_page";
		}else{
			String pageClass = setProductSystemName(params)+"_"+MapUtils.getString(params, "processId","")+"_page";
			return pageClass;
		}
	}
	
	/**
	 * 获取App页面配置
	 * @param request
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/pageConfig") 
	public RespDataObject<Object> pageConfig(HttpServletRequest request, @RequestBody Map<String, Object> params){
		RespDataObject<Object> resp = new RespDataObject<Object>();
		boolean isEdit=false;
		UserDto user = getUserDtoByMysql(request);
		params.put("agencyId", user.getAgencyId());
		log.info("pageConfig_params:"+params);
		log.info("机构id："+user.getAgencyId());
		log.info("用户状态isEnable:"+user.getIsEnable());
		if((0==user.getAgencyId() || user.getIsEnable() != 0 ) && params.containsKey("processId")&&"addOrder".equals(MapUtils.getString(params, "processId"))){//快鸽普通用户提单
			params.put("processId", "kgAddOrder");
		}
		String processId = null ;
		if(null!=params.get("processId")){
			processId = MapUtils.getString(params, "processId");
		}
		//列表编辑
		if(null!=params.get("tableClasses")){
			isEdit = true;
		}
		try {
			//测试代码开始
//			initPageConfig();
//			initSelectConfig();
			//测试代码结束
			Map<String,Object> md = getOrderBaseInfo(MapUtils.getString(params, "orderNo"));
			params.put("uid", getUserDto(request).getUid());
			if(md!=null){
				if(md.get("processId")!=null){
					params.put("processId",md.get("processId"));
				}
				params.put("cityCode", md.get("cityCode"));
				params.put("cityName", md.get("cityName"));
				if(md.get("productCode")!=null&&StringUtils.isBlank(MapUtils.getString(params, "productCode"))){
					log.info("redis中基本信息业务类型："+md.get("productCode"));
					params.put("productCode",md.get("productCode"));
				}
				params.put("borrowingAmount", md.get("borrowingAmount"));
			}
			//进入节点详情
			if(null!=processId){
				params.put("processId", processId);
			}
			//列表按钮操作
			if(null!=params.get("tableClasses")){
				params.put("processId", params.get("tableClasses"));
			}
			params.put("pageClass", setPageClass(params));
			System.out.println(params.get("pageClass"));
			PageConfigDto pageConfigDto = pageConfigService.pageConfig(params);
			//节点详情去掉按钮
			if(null==params.get("tableClasses")&&null!=processId&&!"addOrder".equals(processId)&&!"editreport".equals(processId)&&!"kgAddOrder".equals(processId)){
				pageConfigDto.setSubmitButName(null);
			}
			
			if("01".equals(params.get("productCode"))
					||"02".equals(params.get("productCode"))
					||"03".equals(params.get("productCode"))
					||"04".equals(params.get("productCode"))
					||"05".equals(params.get("productCode"))
					||"06".equals(params.get("productCode"))
					||"07".equals(params.get("productCode"))
					||"editreport".equals(processId)
					||"editPaymentReport".equals(MapUtils.getString(params,"processId"))
					||"editPaymentReport".equals(processId)
					||"editreport".equals(MapUtils.getString(params,"processId"))){//债务置换贷款节点详情
				pageConfigDto = specialHandle(pageConfigDto,params,isEdit,user);
			}else{//提单初始化页面
				Iterator<PageTabConfigDto> iter = pageConfigDto.getPageTabConfigDtos().iterator();
				while (iter.hasNext()) {
					PageTabConfigDto pageTabConfigDto = iter.next();
					if(null==params.get("tableClasses")&&null!=processId&&!"addOrder".equals(processId)&&!"editreport".equals(processId)&&!"kgAddOrder".equals(processId)){
						pageTabConfigDto.setSaveButName(null);
					}
					for (PageTabRegionConfigDto pageTabRegionConfigDto : pageTabConfigDto.getPageTabRegionConfigDtos()) {
						for (PageTabRegionFormConfigDto pageTabRegionFormConfigDto : pageTabRegionConfigDto.getValueList().get(0)) {
							if("agencyId".equals(pageTabRegionFormConfigDto.getKey())){//机构提单，设置机构id选人
								pageTabRegionFormConfigDto.setValue(MapUtils.getString(params, "agencyId"));
							}
							//默认身份等不可修改
							if("loanApplicant".equals(pageTabRegionFormConfigDto.getFormClass())&&
									"certificateTypeCode".equals(pageTabRegionFormConfigDto.getKey())){
								pageTabRegionFormConfigDto.setType(1);
							}
							//详情页空改为-
							if(StringUtils.isBlank(pageTabRegionFormConfigDto.getValue())&&!isEdit
									&&!MapUtils.getString(params, "processId").equals("addOrder")&&!MapUtils.getString(params, "processId").equals("kgAddOrder")){
								pageTabRegionFormConfigDto.setValue("-");
							}
						}
						if("tbl_cm_closeOrder_page".equals(pageConfigDto.getPageClass()) && isEdit){
							for (int i = 0; i < pageTabRegionConfigDto.getValueList().get(0).size(); i++) {
								PageTabRegionFormConfigDto pageTabRegionFormConfigDto =pageTabRegionConfigDto.getValueList().get(0).get(i);
								if(pageTabRegionFormConfigDto.getKey().equals("closeTime")){
									pageTabRegionConfigDto.getValueList().get(0).remove(i);
									break;
								}
							}
						}
					}
					setData(pageTabConfigDto, params,isEdit);
				}
			}
			
			//根据不同的参数获取不同的层级
			if(pageConfigDto != null && pageConfigDto.getPageTabConfigDtos() != null){
				if(pageConfigDto.getPageTabConfigDtos().size() == 1){
					if(StringUtils.isEmpty(MapUtils.getString(params, "regionClass"))){
						if("买卖双方".equals(pageConfigDto.getPageTabConfigDtos().get(0).getTitle()) || "评估对象".equals(pageConfigDto.getPageTabConfigDtos().get(0).getTitle())){
							pageConfigDto.getPageTabConfigDtos().get(0).setSaveButName(pageConfigDto.getSubmitButName());
						}
						for(PageTabConfigDto pageTabConfigDto : pageConfigDto.getPageTabConfigDtos()) {
							for (PageTabRegionConfigDto pageTabRegionConfigDto : pageTabConfigDto.getPageTabRegionConfigDtos()) {
								List<PageTabRegionFormConfigDto> formList = new ArrayList<PageTabRegionFormConfigDto>();
								for (PageTabRegionFormConfigDto pageTabRegionFormConfigDto : pageTabRegionConfigDto.getValueList().get(0)) {
									//快鸽指派渠道经理后，渠道经理不可修改
									if(("channelManagerName").equals(pageTabRegionFormConfigDto.getKey())&&StringUtils.isNotBlank(pageTabRegionFormConfigDto.getValue())&&!"repaymentChannelManager".equals("pageTabRegionFormConfigDto.getFormClass()")){
										pageTabRegionFormConfigDto.setIsReadOnly(2);
									}
									//替换typeDepend中的pcode,productCode
									if(pageTabRegionFormConfigDto.getTypeDepend()!=null&&pageTabRegionFormConfigDto.getTypeDepend().contains("pcode=4403")&&params.get("cityCode")!=null&&!"4403".equals(MapUtils.getString(params, "cityCode"))){
										String typeDepend = pageTabRegionFormConfigDto.getTypeDepend().replace("4403", MapUtils.getString(params, "cityCode"));
										pageTabRegionFormConfigDto.setTypeDepend(typeDepend);
									}else if(pageTabRegionFormConfigDto.getTypeDepend()!=null&&pageTabRegionFormConfigDto.getTypeDepend().contains("cityCode=4403&productCode=01")
											&&((params.get("cityCode")!=null&&!"4403".equals(MapUtils.getString(params, "cityCode")))
													||(params.get("productCode")!=null&&!"01".equals(MapUtils.getString(params, "productCode"))))){

										String typeDepend = pageTabRegionFormConfigDto.getTypeDepend().replace("4403", MapUtils.getString(params, "cityCode")).replace("01", MapUtils.getString(params, "productCode"));
										pageTabRegionFormConfigDto.setTypeDepend(typeDepend);
									}
									//机构合作用户不展示分公司，设置默认渠道经理和合作机构
									if(user.getAgencyId()>1&&user.getIsEnable()==0&&"分公司(营业部)".equals(pageTabRegionFormConfigDto.getTitle())){
										continue;
									}else if(user.getAgencyId()>1&&user.getIsEnable()==0&&"渠道经理".equals(pageTabRegionFormConfigDto.getTitle())){
										pageTabRegionFormConfigDto.setValue(CommonDataUtil.getUserDtoByUidAndMobile(user.getAgencyChanlManUid()).getName());
										pageTabRegionFormConfigDto.setSpecialValue(user.getAgencyChanlManUid());
										pageTabRegionFormConfigDto.setIsReadOnly(2);
										pageTabRegionFormConfigDto.setType(1);
										formList.add(pageTabRegionFormConfigDto);
									}else if(user.getAgencyId()>1&&user.getIsEnable()==0&&"合作机构".equals(pageTabRegionFormConfigDto.getTitle())){
										pageTabRegionFormConfigDto.setValue(user.getAgencyName());
										pageTabRegionFormConfigDto.setSpecialValue(""+user.getAgencyId());
										pageTabRegionFormConfigDto.setIsReadOnly(2);
										pageTabRegionFormConfigDto.setType(1);
										formList.add(pageTabRegionFormConfigDto);
									}else{
										formList.add(pageTabRegionFormConfigDto);
									}
								}
								pageTabRegionConfigDto.getValueList().set(0, formList);
							}
						}
						RespHelper.setSuccessDataObject(resp, pageConfigDto.getPageTabConfigDtos().get(0));
					}else{
						for (PageTabRegionConfigDto pageTabRegionConfigDto : pageConfigDto.getPageTabConfigDtos().get(0).getPageTabRegionConfigDtos()) {
							if(pageTabRegionConfigDto.getRegionClass().equals(MapUtils.getString(params, "regionClass"))){
								RespHelper.setSuccessDataObject(resp, pageTabRegionConfigDto);
								return resp;
							}
						}
					}
				}else{
					if(StringUtils.isNotEmpty(MapUtils.getString(params, "tabClass"))){
						for(PageTabConfigDto pageTabConfigDto : pageConfigDto.getPageTabConfigDtos()) {
							if(pageTabConfigDto.getTabClass().equals(MapUtils.getString(params, "tabClass")) && StringUtils.isEmpty(MapUtils.getString(params, "regionClass"))){
								RespHelper.setSuccessDataObject(resp, pageTabConfigDto);
								return resp;
							}else{
								for (PageTabRegionConfigDto pageTabRegionConfigDto : pageTabConfigDto.getPageTabRegionConfigDtos()) {
									if(pageTabRegionConfigDto.getRegionClass().equals(MapUtils.getString(params, "regionClass"))){
										RespHelper.setSuccessDataObject(resp, pageTabRegionConfigDto);
										return resp;
									}
								}
							}
						}
					}else{
						RespHelper.setSuccessDataObject(resp, pageConfigDto);
						return resp;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("获取"+MapUtils.getString(params, "pageClass")+"，App页面配置失败", e);
			RespHelper.setFailDataObject(resp, null, RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}
	/**
	 * 设置债务置换贷款的页面数据
	 * @param pageTabRegionConfigDto
	 * @param pageTabRegionConfigDto
	 * @param object
	 */
	public void setSlData(PageTabRegionConfigDto pageTabRegionConfigDto,Object object){
		try {
			if(object instanceof List){//循环区域赋值
				List<Object> list = (List<Object>) object;
				List<PageTabRegionFormConfigDto> formList = pageTabRegionConfigDto.getValueList().get(0);
				for (int i = 0; i < list.size(); i++) {
					String json = JsonUtil.BeanToJson(list.get(i));
					Map<String,Object> map = JsonUtil.jsonToMap(json);
					List<PageTabRegionFormConfigDto> tempList = new ArrayList<PageTabRegionFormConfigDto>();
					for (PageTabRegionFormConfigDto pageTabRegionFormConfigDto : formList) {
							tempList.add((PageTabRegionFormConfigDto)BeanUtils.cloneBean(pageTabRegionFormConfigDto));
					}
					setValues(tempList,map);
					pageTabRegionConfigDto.getValueList().add(tempList);
				}
				pageTabRegionConfigDto.getValueList().remove(0);
			}else if(object instanceof JSONArray){//循环区域赋值
				JSONArray json = (JSONArray) object;
				List<PageTabRegionFormConfigDto> formList = pageTabRegionConfigDto.getValueList().get(0);
				for (int i = 0; i < json.size(); i++) {
					Map<String,Object> map = JsonUtil.jsonToMap(json.getJSONObject(i).toString());
					List<PageTabRegionFormConfigDto> tempList = new ArrayList<PageTabRegionFormConfigDto>();
					for (PageTabRegionFormConfigDto pageTabRegionFormConfigDto : formList) {
							tempList.add((PageTabRegionFormConfigDto) BeanUtils.cloneBean(pageTabRegionFormConfigDto));
					}
					setValues(tempList,map);
					pageTabRegionConfigDto.getValueList().add(tempList);
				}
				pageTabRegionConfigDto.getValueList().remove(0);
			}else if(object instanceof Map){
				Map<String,Object> data = (Map<String, Object>) object;
				//普通区域赋值
				for (List<PageTabRegionFormConfigDto>  formList: pageTabRegionConfigDto.getValueList()) {
					setValues(formList, data);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings({ "unchecked"})
	private PageConfigDto specialHandle(PageConfigDto pageConfigDto, Map<String, Object> params,boolean isEdit,UserDto user){
		//特殊处理
		String orderNo = MapUtils.getString(params, "orderNo");
		log.info("orderNo:"+orderNo);
		Map<String,Object> map = new HashMap<String, Object>();
		
		OrderListDto orderListDto = new OrderListDto();
		boolean isRelationC = false;
		boolean isChangloan = false;
		if(StringUtils.isNotBlank(orderNo)) {
			orderListDto.setOrderNo(orderNo);
			orderListDto = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/order/base/v/selectDetailByOrderNo", orderListDto, OrderListDto.class);
			if(null!=orderListDto) {
				isRelationC = orderListDto.getProductCode().equals("03") && StringUtils.isNotBlank(orderListDto.getRelationOrderNo());
				isChangloan = "03".equals(orderListDto.getProductCode());
			}
		}
		//询价
		if("slEnquiry".equals(MapUtils.getString(params,"regionClass"))){
			if(isRelationC){
				map.put("orderNo", orderListDto.getRelationOrderNo());
			}else{
				map.put("orderNo", orderNo);
			}
			map = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/risk/lawsuit/v/loadInquiryAndArchiveInit", map, Map.class); 
			JSONArray array = JSONArray.fromObject(map.get("enquiryData"));
			for(PageTabConfigDto pageTabConfigDto : pageConfigDto.getPageTabConfigDtos()) {
				for (PageTabRegionConfigDto pageTabRegionConfigDto : pageTabConfigDto.getPageTabRegionConfigDtos()) {
						setSlData(pageTabRegionConfigDto, array);
						pageTabRegionConfigDto.setDeleteUrl("/credit/risk/enquiry/v/delete");
				}
			}
		}
		//查档
		/*else if("slArchive".equals(MapUtils.getString(params,"regionClass"))){
			if(isRelationC){
				map.put("orderNo", orderListDto.getRelationOrderNo());
			}else{
				map.put("orderNo", orderNo);
			}
			map = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/risk/lawsuit/v/loadInquiryAndArchiveInit", map, Map.class); 
			JSONArray array = JSONArray.fromObject(map.get("archiveData"));
			for(PageTabConfigDto pageTabConfigDto : pageConfigDto.getPageTabConfigDtos()) {
				for (PageTabRegionConfigDto pageTabRegionConfigDto : pageTabConfigDto.getPageTabRegionConfigDtos()) {
						setSlData(pageTabRegionConfigDto, array);
						pageTabRegionConfigDto.setDeleteUrl("/credit/risk/archive/v/delete");
				}
			}
		}*/
		//完善订单
		else if("tbl_sl_placeOrder_page".equals(pageConfigDto.getPageClass())||"tbl_fdd_placeOrder_page".equals(pageConfigDto.getPageClass())){
			
//			for(PageTabConfigDto pageTabConfigDto : pageConfigDto.getPageTabConfigDtos()) {
//				for (PageTabRegionConfigDto pageTabRegionConfigDto : pageTabConfigDto.getPageTabRegionConfigDtos()) {
//					for (PageTabRegionFormConfigDto pageTabRegionFormConfigDto : pageTabRegionConfigDto.getValueList().get(0)) {
//						if("agencyId".equals(pageTabRegionFormConfigDto.getKey())){
//							pageTabRegionFormConfigDto.setValue(MapUtils.getString(params, "agencyId"));
//						}
//						if(!MapUtils.getString(params, "agencyId","").equals("1") && pageTabRegionFormConfigDto.getKey().equals("channelManagerName")){
//							pageTabRegionFormConfigDto.setType(1);
//							pageTabRegionFormConfigDto.setIsReadOnly(2);
//						}
//						if(!MapUtils.getString(params, "agencyId","").equals("1") && pageTabRegionFormConfigDto.getKey().equals("cooperativeAgencyName")){
//							pageTabRegionFormConfigDto.setType(1);
//							pageTabRegionFormConfigDto.setIsReadOnly(2);
//						}
//					}
//				}
//			}
			
			// 退回重新提交
			OrderFlowDto orderFlow = new OrderFlowDto();
			orderFlow.setOrderNo(orderNo);
			orderFlow = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/order/flow/v/selectEndOrderFlow", orderFlow,OrderFlowDto.class);
			if (orderFlow!=null&&orderFlow.getBackReason() != null
					&& !orderFlow.getBackReason().equals("")/*&&orderFlow.getIsNewWalkProcess()==1*/) {// 退回重新提交
				pageConfigDto.getPageTabConfigDtos().remove(10);
				pageConfigDto.getPageTabConfigDtos().remove(9);
				pageConfigDto.getPageTabConfigDtos().remove(8);
				if(!"04".equals(orderListDto.getProductCode())){
					pageConfigDto.getPageTabConfigDtos().remove(7);
				}
			}else if(user.getAgencyId()>1&&user.getIsEnable()==0){//机构用户提单，没有受理经理
				pageConfigDto.getPageTabConfigDtos().remove(10);
			}else if(isRelationC){
				pageConfigDto.getPageTabConfigDtos().remove(10);
			}else if("03".equals(orderListDto.getProductCode())||"05".equals(orderListDto.getProductCode())){
				pageConfigDto.getPageTabConfigDtos().remove(10);
			}
			//详情页不返回选人字段
			if(!isEdit&&pageConfigDto.getPageTabConfigDtos().size()>7){
				if(pageConfigDto.getPageTabConfigDtos().size()==8){
					if(!"04".equals(orderListDto.getProductCode())){
						pageConfigDto.getPageTabConfigDtos().remove(7);
					}
				}else if(pageConfigDto.getPageTabConfigDtos().size()==9){
					pageConfigDto.getPageTabConfigDtos().remove(8);
					if(!"04".equals(orderListDto.getProductCode())){
						pageConfigDto.getPageTabConfigDtos().remove(7);
					}
				}else if(pageConfigDto.getPageTabConfigDtos().size()==10){
					pageConfigDto.getPageTabConfigDtos().remove(9);
					pageConfigDto.getPageTabConfigDtos().remove(8);
					if(!"04".equals(orderListDto.getProductCode())){
						pageConfigDto.getPageTabConfigDtos().remove(7);
					}
				}else if(pageConfigDto.getPageTabConfigDtos().size()==11){
					pageConfigDto.getPageTabConfigDtos().remove(10);
					pageConfigDto.getPageTabConfigDtos().remove(9);
					pageConfigDto.getPageTabConfigDtos().remove(8);
					if(!"04".equals(orderListDto.getProductCode())){
						pageConfigDto.getPageTabConfigDtos().remove(7);
					}
				}
			}
			//查询订单信息
			//借款信息
			OrderBaseBorrowDto borrow = new OrderBaseBorrowDto();
			//债务置换贷款借款信息
			OrderBaseBorrowDto slBorrow = new OrderBaseBorrowDto();
			if("rslBorrow".equals(MapUtils.getString(params, "regionClass"))){
				borrow.setOrderNo(orderListDto.getRelationOrderNo());
			}else{
				if(isRelationC){
					slBorrow.setOrderNo(orderListDto.getRelationOrderNo());
					slBorrow = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/order/borrow/v/query", slBorrow,OrderBaseBorrowDto.class);
				}
				borrow.setOrderNo(orderNo);
			}
			borrow = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/order/borrow/v/query", borrow,OrderBaseBorrowDto.class);
			//客户信息
			OrderBaseCustomerDto customer = new OrderBaseCustomerDto();
			if(params.get("tabClass")!=null&&("slCustomer".equals(MapUtils.getString(params, "tabClass"))||"fdd_customer".equals(MapUtils.getString(params, "tabClass")))){
				if(isRelationC){
					customer.setOrderNo(orderListDto.getRelationOrderNo()); 
				}else{
					customer.setOrderNo(orderNo); 
				}
				customer = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/order/customer/v/query", customer,OrderBaseCustomerDto.class);
			}
			//房产信息
			OrderBaseHouseDto house = new OrderBaseHouseDto();
			if(params.get("tabClass")!=null&&("slHouse".equals(MapUtils.getString(params, "tabClass")))||"fdd_house".equals(MapUtils.getString(params, "tabClass"))){
//				if("fdd_house".equals(MapUtils.getString(params, "tabClass"))&&"fdd_oneLoan".equals(MapUtils.getString(params, "regionClass"))){//房抵贷一抵贷信息
//					houseCredit.setOrderNo(orderNo);
//					houseCredit = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/order/house/credit/v/query", houseCredit,OrderBaseHouseCreditDto.class);
//				}else{
					if(isRelationC){
						house.setOrderNo(orderListDto.getRelationOrderNo());
					}else{
						house.setOrderNo(orderNo);
					}
					house = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/order/house/v/query", house,OrderBaseHouseDto.class);
//				}
			}
			//放款信息:房抵贷
			OrderBaseHouseLendingDto houseLending = new OrderBaseHouseLendingDto();
			if(params.get("tabClass")!=null&&("fdd_loan".equals(MapUtils.getString(params, "regionClass"))||"fdd_payment".equals(MapUtils.getString(params, "regionClass")))){
				houseLending.setOrderNo(orderNo);
				houseLending = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/order/house/lending/v/query", houseLending,OrderBaseHouseLendingDto.class);
			}
			//要件
			DocumentsDto documents = new DocumentsDto();
			if(params.get("tabClass")!=null&&"slElement".equals(MapUtils.getString(params, "tabClass"))){
				if(isRelationC&&("slForeclosureType".equals(MapUtils.getString(params, "regionClass"))
						||"slPaymentType".equals(MapUtils.getString(params, "regionClass")))){
					documents.setOrderNo(orderListDto.getRelationOrderNo());
				}else{
					documents.setOrderNo(orderNo);
				}
				documents= httpUtil.getObject(Constants.LINK_CREDIT, "/credit/element/basics/v/detail", documents,DocumentsDto.class);
			}
			//征信
			CreditDto credit = new CreditDto();
			if(params.get("tabClass")!=null&&"slCredit".equals(MapUtils.getString(params, "tabClass"))){
				if(orderListDto.getProductCode().equals("03")&&StringUtils.isNotBlank(orderListDto.getRelationOrderNo())){
					credit.setOrderNo(orderListDto.getRelationOrderNo());
				}else{
					credit.setOrderNo(orderNo);
				}
				credit = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/risk/ordercredit/v/detail", credit, CreditDto.class);
			}
			//房抵贷征信
			if(params.get("tabClass")!=null&&"fdd_credit".equals(MapUtils.getString(params, "tabClass"))){
				credit.setOrderNo(orderNo);
				credit = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/risk/ordercredit/v/detail", credit, CreditDto.class);
			}
			Iterator<PageTabConfigDto> iter = pageConfigDto.getPageTabConfigDtos().iterator();
			while (iter.hasNext()) {
				PageTabConfigDto pageTabConfigDto = iter.next();
				if("04".equals(params.get("productCode"))&&"fdd_imageData".equals(pageTabConfigDto.getTabClass())){
					iter.remove();
				}
				if (orderFlow!=null&&orderFlow.getBackReason() != null
						&& !orderFlow.getBackReason().equals("")) {// 退回重新提交
					pageConfigDto.setSubmitButName("重新提交");
				}
				List<PageTabRegionConfigDto> regionList = new ArrayList<PageTabRegionConfigDto>();
				for (PageTabRegionConfigDto pageTabRegionConfigDto : pageTabConfigDto.getPageTabRegionConfigDtos()) {
					//查询借款信息时
					for (PageTabRegionFormConfigDto pageTabRegionFormConfigDto : pageTabRegionConfigDto.getValueList().get(0)) {
						if("agencyId".equals(pageTabRegionFormConfigDto.getKey())){
							pageTabRegionFormConfigDto.setValue(MapUtils.getString(params, "agencyId"));
						}
						if(!MapUtils.getString(params, "agencyId","").equals("1") && pageTabRegionFormConfigDto.getKey().equals("channelManagerName")){
							pageTabRegionFormConfigDto.setType(1);
							pageTabRegionFormConfigDto.setIsReadOnly(2);
						}
						if(!MapUtils.getString(params, "agencyId","").equals("1") && pageTabRegionFormConfigDto.getKey().equals("cooperativeAgencyName")){
							pageTabRegionFormConfigDto.setType(1);
							pageTabRegionFormConfigDto.setIsReadOnly(2);
						}
						//畅贷关联订单不可编辑债务置换贷款信息
						if(isRelationC&&!"slBorrow".equals(MapUtils.getString(params, "regionClass"))&&!"cForeclosureType".equals(MapUtils.getString(params, "regionClass"))){
							if(pageTabRegionFormConfigDto.getType()>1&&pageTabRegionFormConfigDto.getType()!=6&&pageTabRegionFormConfigDto.getType()!=9){
								pageTabRegionFormConfigDto.setType(1);
							}
							pageTabRegionFormConfigDto.setIsReadOnly(2);
						}
					}
					if(isRelationC&&!"slBorrow".equals(MapUtils.getString(params, "regionClass"))&&!"cForeclosureType".equals(MapUtils.getString(params, "regionClass"))){
						pageTabRegionConfigDto.setCurAbleEdit(2);//当前页不能编辑
					}else{
						pageTabRegionConfigDto.setCurAbleEdit(1);//当前页可以编辑
					}
					
					if(StringUtils.isNotEmpty(pageTabRegionConfigDto.getKey())){//循环区域赋值
						List list = null;
							if(customer != null && (pageTabRegionConfigDto.getRegionClass().equals("slCustomerGuarantee")||pageTabRegionConfigDto.getRegionClass().equals("fdd_customerGuarantee"))
									&&customer.getCustomerGuaranteeDto()!=null){//担保人信息
								list = customer.getCustomerGuaranteeDto();
							}else if(customer != null && (pageTabRegionConfigDto.getRegionClass().equals("slCustomerBorrower")||pageTabRegionConfigDto.getRegionClass().equals("fdd_customerBorrower"))
									&&customer.getCustomerBorrowerDto()!=null){
								list = customer.getCustomerBorrowerDto();
							}else if(customer != null && (pageTabRegionConfigDto.getRegionClass().equals("slCustomerShareholder")||pageTabRegionConfigDto.getRegionClass().equals("fdd_customerShareholder"))
									&&customer.getCustomerShareholderDto()!=null){
								list = customer.getCustomerShareholderDto();
							}else if(house != null && (pageTabRegionConfigDto.getRegionClass().equals("slHouse")||pageTabRegionConfigDto.getRegionClass().equals("fdd_house"))
									&&house.getOrderBaseHousePropertyDto()!=null){
								for (PageTabRegionFormConfigDto pageTabRegionFormConfigDto : pageTabRegionConfigDto.getValueList().get(0)) {
									if(pageTabRegionFormConfigDto.getKey().equals("houseRegion")){
										pageTabRegionFormConfigDto.setTypeDepend("/credit/data/dict/v/searchMap?type=bookingSzAreaOid&pcode="+orderListDto.getCityCode());
									}
								}
								list = house.getOrderBaseHousePropertyDto();
							}else if(house != null && (pageTabRegionConfigDto.getRegionClass().equals("slPropertyPeople")||pageTabRegionConfigDto.getRegionClass().equals("fdd_propertyPeople"))
									&&house.getOrderBaseHousePropertyPeopleDto()!=null){
								list = house.getOrderBaseHousePropertyPeopleDto();
							}else if(house != null && pageTabRegionConfigDto.getRegionClass().equals("slHousePurchaser")&&house.getOrderBaseHousePurchaserDto()!=null){
								list = house.getOrderBaseHousePurchaserDto();
							}
							if(null!=list){
								setSlData(pageTabRegionConfigDto,list);
							}
							for (List<PageTabRegionFormConfigDto> temList : pageTabRegionConfigDto.getValueList()){
								for (PageTabRegionFormConfigDto pageTabRegionFormConfigDto : temList) {
									if(pageTabRegionFormConfigDto.getKey().equals("cityName")){
										pageTabRegionFormConfigDto.setValue(orderListDto.getCityName());
										pageTabRegionFormConfigDto.setSpecialValue(orderListDto.getCityCode());
									}
								}
							}
					}else if(StringUtils.isEmpty(pageTabRegionConfigDto.getKey())){//普通区域赋值
						if((pageTabRegionConfigDto.getRegionClass().equals("slBorrow")||pageTabRegionConfigDto.getRegionClass().equals("rslBorrow")||pageTabRegionConfigDto.getRegionClass().equals("fdd_borrow"))&&borrow!=null){
							String payMentAmountDateOne = null;
							String payMentAmountDateTwo = null;
							if(null!=borrow.getOrderReceivableForDto()&&borrow.getOrderReceivableForDto().size()>0){
								for (int i = 0; i < borrow.getOrderReceivableForDto().size(); i++) {
									JSONObject obj = JSONObject.fromObject(borrow.getOrderReceivableForDto().get(i));
									OrderBaseReceivableForDto  orderBaseReceivableForDto = (OrderBaseReceivableForDto)JSONObject.toBean(obj, OrderBaseReceivableForDto.class);
									if(i==0){
										borrow.setPayMentAmountOne(orderBaseReceivableForDto.getPayMentAmount());
										payMentAmountDateOne = orderBaseReceivableForDto.getPayMentAmountDateStr();
									}else{
										borrow.setPayMentAmountTwo(orderBaseReceivableForDto.getPayMentAmount());
										payMentAmountDateTwo = orderBaseReceivableForDto.getPayMentAmountDateStr();
									}
								}
							}
							Map<String,Object> m = BeanToMapUtil.beanToMap(borrow);
							if("0".equals(MapUtils.getString(m, "cooperativeAgencyId"))){
								m.put("cooperativeAgencyId", null);
							}
							m.put("payMentAmountDateOne", payMentAmountDateOne);
							m.put("payMentAmountDateTwo", payMentAmountDateTwo);
							//是否一次性回款
							if(borrow.getIsOnePay()!=null&&borrow.getIsOnePay()==1){
								m.put("isOnePayName", "是");
							}else if(borrow.getIsOnePay()!=null&&borrow.getIsOnePay()!=1){
								m.put("isOnePayName", "否");
							}
							//原贷款是否银行
							if(borrow.getIsOldLoanBank()!=null&&borrow.getIsOldLoanBank()==1){
								m.put("isOldLoanBankName", "是");
							}else if(borrow.getIsOldLoanBank()!=null&&borrow.getIsOldLoanBank()!=1){
								m.put("isOldLoanBankName", "否");
							}
							//新贷款是否银行
							if(borrow.getIsLoanBank()!=null&&borrow.getIsLoanBank()==1){
								m.put("isLoanBankName", "是");
							}else if(borrow.getIsLoanBank()!=null&&borrow.getIsLoanBank()!=1){
								m.put("isLoanBankName", "否");
							}
							//是否返佣
							if(borrow.getIsRebate()!=null&&borrow.getIsRebate()==1){
								m.put("isRebateName", "是");
							}else if(borrow.getIsRebate()!=null&&borrow.getIsRebate()!=1){
								m.put("isRebateName", "否");
							}
							//费用支付方式
							if(borrow.getPaymentMethod()==1){
								m.put("paymentMethodName","费用前置");
							}else if(borrow.getPaymentMethod()==2){
								m.put("paymentMethodName","费用后置");
							}else{
								m.put("paymentMethod",null);
								m.put("paymentMethodName",null);
							}
							m.put("financeOutLoanTime", borrow.getFinanceOutLoanTimeStr());
							//设置值
							setSlData(pageTabRegionConfigDto,m);
							//机构提单去掉部门所在区域,加上还款专员
							List<PageTabRegionFormConfigDto> formList = pageTabRegionConfigDto.getValueList().get(0);
							List<PageTabRegionFormConfigDto> tempList = new ArrayList<PageTabRegionFormConfigDto>();
							for (PageTabRegionFormConfigDto pageTabRegionFormConfigDto : formList) {
								//关联畅贷的借款信息
								if(isRelationC&&"slBorrow".equals(MapUtils.getString(params, "regionClass"))){
									if("城市".equals(pageTabRegionFormConfigDto.getTitle())
											||"业务类型".equals(pageTabRegionFormConfigDto.getTitle())
											||"合作机构".equals(pageTabRegionFormConfigDto.getTitle())
											||"借款金额".equals(pageTabRegionFormConfigDto.getTitle())
											||"借款期限".equals(pageTabRegionFormConfigDto.getTitle())
											||"收费类型".equals(pageTabRegionFormConfigDto.getTitle())
											||"费率".equals(pageTabRegionFormConfigDto.getTitle())
											||"逾期费率".equals(pageTabRegionFormConfigDto.getTitle())
											||"其他金额".equals(pageTabRegionFormConfigDto.getTitle())
											||"服务费".equals(pageTabRegionFormConfigDto.getTitle())
											||"收费金额".equals(pageTabRegionFormConfigDto.getTitle())
											||"按天按段".equals(pageTabRegionFormConfigDto.getTitle())
											||"预计出款时间".equals(pageTabRegionFormConfigDto.getTitle())
											||"备注".equals(pageTabRegionFormConfigDto.getTitle())){
										if("合作机构".equals(pageTabRegionFormConfigDto.getTitle())){
											pageTabRegionFormConfigDto.setValue(slBorrow.getCooperativeAgencyName());
											pageTabRegionFormConfigDto.setSpecialValue(slBorrow.getCooperativeAgencyId()+"");
											pageTabRegionFormConfigDto.setType(-2);
										}
										if("预计出款时间".equals(pageTabRegionFormConfigDto.getTitle())){
											pageTabRegionFormConfigDto.setTitle("预计出款时间 ");
										}
										tempList.add(pageTabRegionFormConfigDto);
									}
								}else if(orderListDto.getProductCode().equals("03")&&StringUtils.isBlank(orderListDto.getRelationOrderNo())){//不关联的畅贷
									if("预计出款时间".equals(pageTabRegionFormConfigDto.getTitle())){
										pageTabRegionFormConfigDto.setTitle("预计出款时间 ");
									}
									if(!"原贷款是否银行".equals(pageTabRegionFormConfigDto.getTitle())
											&&!"原贷款地点".equals(pageTabRegionFormConfigDto.getTitle())
											&&!"费用支付方式".equals(pageTabRegionFormConfigDto.getTitle())
											&&!"关外手续费".equals(pageTabRegionFormConfigDto.getTitle())){
										if(!"部门所在区域".equals(pageTabRegionFormConfigDto.getTitle())||borrow.getAgencyId()==null||borrow.getAgencyId()<=1){
											tempList.add(pageTabRegionFormConfigDto);
										}
									}
								}else{
									if("预计出款时间".equals(pageTabRegionFormConfigDto.getTitle())){
										pageTabRegionFormConfigDto.setTitle("预计出款时间 ");
									}
									if(!"部门所在区域".equals(pageTabRegionFormConfigDto.getTitle())||borrow.getAgencyId()==null||borrow.getAgencyId()<=1){
										tempList.add(pageTabRegionFormConfigDto);
									}else{
										//还款专员
										PageTabRegionFormConfigDto pageTabRegionFormConfig = new PageTabRegionFormConfigDto(3, "还款专员", "foreclosureMemberName", borrow.getForeclosureMemberName(), borrow.getForeclosureMemberUid());
										pageTabRegionFormConfig.setSpecialKey("foreclosureMemberUid");
										pageTabRegionFormConfig.setTypeDepend("/credit/user/user/v/searchByType2?name=结清原贷款&cityCode="+borrow.getCityCode()+"&productCode="+borrow.getProductCode()+"&agencyId="+borrow.getAgencyId());
										tempList.add(pageTabRegionFormConfig);
									}
								}
							}
							pageTabRegionConfigDto.getValueList().set(0, tempList);
						}else if((pageTabRegionConfigDto.getRegionClass().equals("slCustomer")||pageTabRegionConfigDto.getRegionClass().equals("slCompany")
								||pageTabRegionConfigDto.getRegionClass().equals("fdd_customer")||pageTabRegionConfigDto.getRegionClass().equals("fdd_company"))&&customer!=null){
							setSlData(pageTabRegionConfigDto,BeanToMapUtil.beanToMap(customer));
						}else if(pageTabRegionConfigDto.getRegionClass().equals("slHouseLoan")&&house!=null){//贷款信息
							setSlData(pageTabRegionConfigDto,BeanToMapUtil.beanToMap(house));
							//区分交易类和非交易类
							if("02".equals(borrow.getProductCode())||"02".equals(slBorrow.getProductCode())||house.getBussinessType()==2||"05".equals(borrow.getProductCode())){//非交易类
									List<PageTabRegionFormConfigDto> tempList = new ArrayList<PageTabRegionFormConfigDto>();
									for (List<PageTabRegionFormConfigDto>  formList: pageTabRegionConfigDto.getValueList()) {
										for (PageTabRegionFormConfigDto pageTabRegionFormConfigDto : formList) {
											if(!"成交价格".equals(pageTabRegionFormConfigDto.getTitle())&&!"成交定金".equals(pageTabRegionFormConfigDto.getTitle())&&!"资金监管金额".equals(pageTabRegionFormConfigDto.getTitle())){
												if(house.getBussinessType()==2&&("原房贷金额".equals(pageTabRegionFormConfigDto.getTitle())||"原房贷余额".equals(pageTabRegionFormConfigDto.getTitle()))){
													continue;
												}
												tempList.add(pageTabRegionFormConfigDto);
												if(!isEdit||(params.get("isDetail")!=null&&1==MapUtils.getInteger(params, "isDetail"))
														||isRelationC){
												if("贷款金额".equals(pageTabRegionFormConfigDto.getTitle())){
													//新贷款是否银行
													if(isRelationC){
														borrow = slBorrow;
													}
													if(borrow.getIsLoanBank()!=null&&borrow.getIsLoanBank()==1){
														pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1, "新贷款是否银行", "isLoanBankName", "是", null);
														pageTabRegionFormConfigDto.setIsReadOnly(2);
														tempList.add(pageTabRegionFormConfigDto);
														pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1, "贷款银行经理", "loanBankNameManager", borrow.getLoanBankNameManager(), null);
														pageTabRegionFormConfigDto.setIsReadOnly(2);
														tempList.add(pageTabRegionFormConfigDto);
														pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1, "贷款银行经理电话", "loanBankNameManagerPhone", borrow.getLoanBankNameManagerPhone(), null);
														pageTabRegionFormConfigDto.setIsReadOnly(2);
														tempList.add(pageTabRegionFormConfigDto);
														pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1, "贷款银行", "loanBankName", borrow.getLoanBankName(), null);
														pageTabRegionFormConfigDto.setIsReadOnly(2);
														tempList.add(pageTabRegionFormConfigDto);
														pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1, "贷款支行", "loanSubBankName", borrow.getLoanSubBankName(), null);
														pageTabRegionFormConfigDto.setIsReadOnly(2);
														tempList.add(pageTabRegionFormConfigDto);
													}else if(borrow.getIsLoanBank()!=null&&borrow.getIsLoanBank()!=1){
														pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1, "新贷款是否银行", "isLoanBankName", "否", null);
														pageTabRegionFormConfigDto.setIsReadOnly(2);
														tempList.add(pageTabRegionFormConfigDto);
														pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1, "贷款地点", "loanBankName", borrow.getLoanBankName(), null);
														pageTabRegionFormConfigDto.setIsReadOnly(2);
														tempList.add(pageTabRegionFormConfigDto);
													}
												}
											}
											}
										}
									}
									pageTabRegionConfigDto.getValueList().set(0, tempList);
								if(house.getBussinessType()==2){//畅贷
									pageTabRegionConfigDto.setTitle("新贷款信息（非交易类）");
								}else{
									pageTabRegionConfigDto.setTitle("房贷与新贷款信息");
								}
								pageTabConfigDto.setTitle("房产信息");
							}else if("01".equals(borrow.getProductCode())||"01".equals(slBorrow.getProductCode())||house.getBussinessType()==1){
									List<PageTabRegionFormConfigDto> tempList = new ArrayList<PageTabRegionFormConfigDto>();
									for (List<PageTabRegionFormConfigDto>  formList: pageTabRegionConfigDto.getValueList()) {
										for (PageTabRegionFormConfigDto pageTabRegionFormConfigDto : formList) {
											if(house.getBussinessType()==1&&("原房贷金额".equals(pageTabRegionFormConfigDto.getTitle())||"原房贷余额".equals(pageTabRegionFormConfigDto.getTitle()))){
												continue;
											}
											if("资金监管金额".equals(pageTabRegionFormConfigDto.getTitle())){
												tempList.add(pageTabRegionFormConfigDto);
												if(!isEdit||(params.get("isDetail")!=null&&1==MapUtils.getInteger(params, "isDetail"))
														||isRelationC){
												//新贷款是否银行
												if(isRelationC){
													borrow = slBorrow;
												}
												if(borrow.getIsLoanBank()!=null&&borrow.getIsLoanBank()==1){
													pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1, "新贷款是否银行", "isLoanBankName", "是", null);
													pageTabRegionFormConfigDto.setIsReadOnly(2);
													tempList.add(pageTabRegionFormConfigDto);
													pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1, "贷款银行经理", "loanBankNameManager", borrow.getLoanBankNameManager(), null);
													pageTabRegionFormConfigDto.setIsReadOnly(2);
													tempList.add(pageTabRegionFormConfigDto);
													pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1, "贷款银行经理电话", "loanBankNameManagerPhone", borrow.getLoanBankNameManagerPhone(), null);
													pageTabRegionFormConfigDto.setIsReadOnly(2);
													tempList.add(pageTabRegionFormConfigDto);
													pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1, "贷款银行", "loanBankName", borrow.getLoanBankName(), null);
													pageTabRegionFormConfigDto.setIsReadOnly(2);
													tempList.add(pageTabRegionFormConfigDto);
													pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1, "贷款支行", "loanSubBankName", borrow.getLoanSubBankName(), null);
													pageTabRegionFormConfigDto.setIsReadOnly(2);
													tempList.add(pageTabRegionFormConfigDto);
												}else if(borrow.getIsLoanBank()!=null&&borrow.getIsLoanBank()!=1){
													pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1, "新贷款是否银行", "isLoanBankName", "否", null);
													pageTabRegionFormConfigDto.setIsReadOnly(2);
													tempList.add(pageTabRegionFormConfigDto);
													pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1, "贷款地点", "loanBankName", borrow.getLoanBankName(), null);
													pageTabRegionFormConfigDto.setIsReadOnly(2);
													tempList.add(pageTabRegionFormConfigDto);
												}
												}
											}else{
												tempList.add(pageTabRegionFormConfigDto);
											}
										}
									}
									pageTabRegionConfigDto.getValueList().set(0, tempList);
								pageTabRegionConfigDto.setTitle("房贷与交易信息");
								if("03".equals(orderListDto.getProductCode())&&StringUtils.isBlank(orderListDto.getRelationOrderNo())){
									pageTabConfigDto.setTitle("房产信息");
								}else{
									pageTabConfigDto.setTitle("房产交易信息");
								}
							}
							else{
								pageTabConfigDto.setTitle("房产信息");
							}
						}else if(pageTabRegionConfigDto.getRegionClass().equals("slTransactionInfo")&&house!=null){
							setSlData(pageTabRegionConfigDto,BeanToMapUtil.beanToMap(house));
						}else if(pageTabRegionConfigDto.getRegionClass().equals("slNewLoanInfo")&&house!=null){//新贷款信息
							setSlData(pageTabRegionConfigDto,BeanToMapUtil.beanToMap(house));
						}else if(pageTabRegionConfigDto.getRegionClass().equals("fdd_oneLoan")&&house!=null){//一抵贷信息:房抵贷
							setSlData(pageTabRegionConfigDto,BeanToMapUtil.beanToMap(house));
						}else if((pageTabRegionConfigDto.getRegionClass().equals("fdd_loan")||pageTabRegionConfigDto.getRegionClass().equals("fdd_payment"))&&houseLending!=null){//放款信息:房抵贷
							setSlData(pageTabRegionConfigDto,BeanToMapUtil.beanToMap(houseLending));
						}else if(pageTabRegionConfigDto.getRegionClass().equals("slForeclosureType")&&documents!=null){//出款方式
							map = BeanToMapUtil.beanToMap(documents.getForeclosureType());
							setSlData(pageTabRegionConfigDto,map);
							//畅贷出款方式
							if(orderListDto.getProductCode().equals("03")&&StringUtils.isNotBlank(orderListDto.getRelationOrderNo())
									&&"cForeclosureType".equals(MapUtils.getString(params, "regionClass"))
									||(orderListDto.getProductCode().equals("03")&&StringUtils.isBlank(orderListDto.getRelationOrderNo())
											&&"slForeclosureType".equals(MapUtils.getString(params, "regionClass")))){
								List<PageTabRegionFormConfigDto> formList = pageTabRegionConfigDto.getValueList().get(0);
								List<PageTabRegionFormConfigDto> tempList = new ArrayList<PageTabRegionFormConfigDto>();
								for (PageTabRegionFormConfigDto pageTabRegionFormConfigDto : formList) {
									if("开户银行".equals(pageTabRegionFormConfigDto.getTitle())
											||"开户支行".equals(pageTabRegionFormConfigDto.getTitle())
											||"银行卡户名".equals(pageTabRegionFormConfigDto.getTitle())
											||"银行卡账号".equals(pageTabRegionFormConfigDto.getTitle())
											||"账户类型".equals(pageTabRegionFormConfigDto.getTitle())){
										if("账户类型".equals(pageTabRegionFormConfigDto.getTitle())){
											pageTabRegionFormConfigDto.setType(-2);
											pageTabRegionFormConfigDto.setValue("其他");
										}
										tempList.add(pageTabRegionFormConfigDto);
									}
								}
								pageTabRegionConfigDto.getValueList().set(0, tempList);
							}
						}else if(pageTabRegionConfigDto.getRegionClass().equals("slPaymentType")&&documents!=null){//回款方式
							setSlData(pageTabRegionConfigDto,BeanToMapUtil.beanToMap(documents.getPaymentType()));
						}else if(pageTabRegionConfigDto.getRegionClass().equals("slCredit")&&credit!=null){//征信
							setSlData(pageTabRegionConfigDto,BeanToMapUtil.beanToMap(credit));
							//畅贷征信
							if(orderListDto.getProductCode().equals("03")&&StringUtils.isBlank(orderListDto.getRelationOrderNo())){
								List<PageTabRegionFormConfigDto> formList = pageTabRegionConfigDto.getValueList().get(0);
								List<PageTabRegionFormConfigDto> tempList = new ArrayList<PageTabRegionFormConfigDto>();
								for (PageTabRegionFormConfigDto pageTabRegionFormConfigDto : formList) {
									if(!"债务置换贷款成数".equals(pageTabRegionFormConfigDto.getTitle())){
										tempList.add(pageTabRegionFormConfigDto);
									}
								}
								pageTabRegionConfigDto.getValueList().set(0, tempList);
							}
						}else if(pageTabRegionConfigDto.getRegionClass().equals("fdd_credit")&&credit!=null){
							setSlData(pageTabRegionConfigDto,BeanToMapUtil.beanToMap(credit));
						}
					}
					//非交易类去掉买房人信息，畅贷，选择业务类型
					if(!(("买房人信息".equals(pageTabRegionConfigDto.getTitle())&&("02".equals(borrow.getProductCode())||"05".equals(borrow.getProductCode())))
							||((customer.getCustomerBorrowerDto()==null||customer.getCustomerBorrowerDto().size()==0)
							&&"担保人".equals(pageTabRegionConfigDto.getTitle())&&(!isEdit||(1==MapUtils.getInteger(params, "isDetail",0))))
							||((customer.getCustomerBorrowerDto()==null||customer.getCustomerBorrowerDto().size()==0)
									&&"共同借款人".equals(pageTabRegionConfigDto.getTitle())&&(!isEdit||(1==MapUtils.getInteger(params, "isDetail",0)))))){
						if(orderListDto.getCustomerType()==1&&(pageTabRegionConfigDto.getTitle().contains("股东信息")||pageTabRegionConfigDto.getTitle().contains("企业信息"))){
							continue;
						}
						//关联的畅贷
						if(isRelationC){
							if("slForeclosureType".equals(pageTabRegionConfigDto.getRegionClass())&&"出款方式".equals(pageTabRegionConfigDto.getTitle())){
								PageTabRegionConfigDto cPageTabRegionConfigDto = new PageTabRegionConfigDto();
										try {
											cPageTabRegionConfigDto = (PageTabRegionConfigDto) BeanUtils.cloneBean(pageTabRegionConfigDto);
										} catch (Exception e) {
											e.printStackTrace();
										} 
								cPageTabRegionConfigDto.setTitle("畅贷（出款方式）");
								cPageTabRegionConfigDto.setRegionClass("cForeclosureType");
								regionList.add(cPageTabRegionConfigDto);
							}
							if("slBorrow".equals(MapUtils.getString(params, "tabClass"))){//关联畅贷
								regionList.add(pageTabRegionConfigDto);
								PageTabRegionConfigDto cPageTabRegionConfigDto = new PageTabRegionConfigDto();
								try {
									cPageTabRegionConfigDto = (PageTabRegionConfigDto) BeanUtils.cloneBean(pageTabRegionConfigDto);
								} catch (Exception e) {
									e.printStackTrace();
								} 
								cPageTabRegionConfigDto.setTitle("关联的订单");
								cPageTabRegionConfigDto.setRegionClass("rslBorrow");
								regionList.add(cPageTabRegionConfigDto);
								continue;
							}
						}
						//畅贷房产信息
						if("03".equals(orderListDto.getProductCode())&&StringUtils.isBlank(orderListDto.getRelationOrderNo())
								&&("房贷与交易信息".equals(pageTabRegionConfigDto.getTitle())
										||"房贷与新贷款信息".equals(pageTabRegionConfigDto.getTitle())
										||"新贷款信息（非交易类）".equals(pageTabRegionConfigDto.getTitle()))){
							PageTabRegionConfigDto pageTabRegionConfigDtoTemp = new PageTabRegionConfigDto();
							pageTabRegionConfigDtoTemp.setType(1);//选择项
							pageTabRegionConfigDtoTemp.setAbleEdit(2);
							pageTabRegionConfigDtoTemp.setIsNeed(2);
							pageTabRegionConfigDtoTemp.setTitle("业务类型");
							pageTabRegionConfigDtoTemp.setRegionClass("cProductCode");
							pageTabRegionConfigDtoTemp.setFormClasses("cProductCode");
							PageTabRegionFormConfigDto pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1,"业务类型","bussinessTypeName",null);
							pageTabRegionFormConfigDto.setIsNeed(2);
							pageTabRegionFormConfigDto.setType(2);
							pageTabRegionFormConfigDto.setTypeDepend("cProductName");
							pageTabRegionFormConfigDto.setSpecialKey("bussinessType");
							pageTabRegionFormConfigDto.setFormClass("cProductCode");
							pageTabRegionFormConfigDto.setValue(house.getBussinessTypeName());
							pageTabRegionFormConfigDto.setSpecialValue(house.getBussinessType()+"");
							List<Map<String,String>> dataList = new ArrayList<Map<String,String>>();
							Map<String,String> data = new HashMap<String, String>();
							data.put("id", "1");
							data.put("name", "交易类");
							dataList.add(data);
							data = new HashMap<String, String>();
							data.put("id", "2");
							data.put("name", "非交易类");
							dataList.add(data);
							pageTabRegionFormConfigDto.setDataList(dataList);
							List<PageTabRegionFormConfigDto> tempList = new ArrayList<PageTabRegionFormConfigDto>();
							tempList.add(pageTabRegionFormConfigDto);
							List<List<PageTabRegionFormConfigDto>> valueList = new ArrayList<List<PageTabRegionFormConfigDto>>();
							valueList.add(tempList);
							pageTabRegionConfigDtoTemp.setValueList(valueList);
							regionList.add(pageTabRegionConfigDtoTemp);
						}
						if("03".equals(orderListDto.getProductCode())&&StringUtils.isBlank(orderListDto.getRelationOrderNo())&&house.getBussinessType()==0
								&&("买房人信息".equals(pageTabRegionConfigDto.getTitle())
										||"房贷与交易信息".equals(pageTabRegionConfigDto.getTitle()))){
							continue;
						}
						if(!(("02".equals(borrow.getProductCode())||"02".equals(slBorrow.getProductCode())||house.getBussinessType()==2||"05".equals(borrow.getProductCode()))
								&&("买房人信息".equals(pageTabRegionConfigDto.getTitle())
								||"房贷与交易信息".equals(pageTabRegionConfigDto.getTitle())))){//不关联的畅贷
							if(house.getBussinessType()==1&&"买房人信息".equals(pageTabRegionConfigDto.getTitle())){
								pageTabRegionConfigDto.setTitle("买房人信息（交易类）");
								regionList.add(pageTabRegionConfigDto);
							}else if(house.getBussinessType()==1&&"房贷与交易信息".equals(pageTabRegionConfigDto.getTitle())){
								pageTabRegionConfigDto.setTitle("交易信息（交易类）");
								regionList.add(pageTabRegionConfigDto);
							}else{
								regionList.add(pageTabRegionConfigDto);
							}
						}
					}
				}
				pageTabConfigDto.setPageTabRegionConfigDtos(regionList);
				//畅贷关联订单不可编辑债务置换贷款信息
				if(isRelationC&&!"slBorrow".equals(MapUtils.getString(params, "regionClass"))&&!"cForeclosureType".equals(MapUtils.getString(params, "regionClass"))){
					pageTabConfigDto.setSaveButName(null);
				}
			}
		}
		else if("tbl_sl_element_process_page".equals(pageConfigDto.getPageClass())) {
			DocumentsDto documents = new DocumentsDto();
			if(isRelationC&&("slForeclosureType".equals(MapUtils.getString(params, "regionClass"))
					||"slPaymentType".equals(MapUtils.getString(params, "regionClass")))){
				documents.setOrderNo(orderListDto.getRelationOrderNo());
			}else{
				documents.setOrderNo(orderNo);
			}
			documents= httpUtil.getObject(Constants.LINK_CREDIT, "/credit/element/basics/v/detail", documents,DocumentsDto.class);
			Iterator<PageTabConfigDto> iter = pageConfigDto.getPageTabConfigDtos().iterator();
			while (iter.hasNext()) {
				PageTabConfigDto pageTabConfigDto = iter.next();
				List<PageTabRegionConfigDto> regionList = new ArrayList<PageTabRegionConfigDto>();
				for (PageTabRegionConfigDto pageTabRegionConfigDto : pageTabConfigDto.getPageTabRegionConfigDtos()) {
					regionList.add(pageTabRegionConfigDto);
					if(pageTabRegionConfigDto.getRegionClass().equals("slForeclosureType")&&documents!=null){//出款方式
						map = BeanToMapUtil.beanToMap(documents.getForeclosureType());
						setSlData(pageTabRegionConfigDto,map);
						//畅贷出款方式
						if(orderListDto.getProductCode().equals("03")&&StringUtils.isNotBlank(orderListDto.getRelationOrderNo())
								&&"cForeclosureType".equals(MapUtils.getString(params, "regionClass"))
								||(orderListDto.getProductCode().equals("03")&&StringUtils.isBlank(orderListDto.getRelationOrderNo())
										&&"slForeclosureType".equals(MapUtils.getString(params, "regionClass")))){
							List<PageTabRegionFormConfigDto> formList = pageTabRegionConfigDto.getValueList().get(0);
							List<PageTabRegionFormConfigDto> tempList = new ArrayList<PageTabRegionFormConfigDto>();
							for (PageTabRegionFormConfigDto pageTabRegionFormConfigDto : formList) {
								if("开户银行".equals(pageTabRegionFormConfigDto.getTitle())
										||"开户支行".equals(pageTabRegionFormConfigDto.getTitle())
										||"银行卡户名".equals(pageTabRegionFormConfigDto.getTitle())
										||"银行卡账号".equals(pageTabRegionFormConfigDto.getTitle())
										||"账户类型".equals(pageTabRegionFormConfigDto.getTitle())){
									if("账户类型".equals(pageTabRegionFormConfigDto.getTitle())){
										pageTabRegionFormConfigDto.setType(-2);
										pageTabRegionFormConfigDto.setValue("其他");
									}
									tempList.add(pageTabRegionFormConfigDto);
								}
							}
							pageTabRegionConfigDto.getValueList().set(0, tempList);
						}
					}else if(pageTabRegionConfigDto.getRegionClass().equals("slPaymentType")&&documents!=null){//回款方式
						setSlData(pageTabRegionConfigDto,BeanToMapUtil.beanToMap(documents.getPaymentType()));
					}else if(pageTabRegionConfigDto.getRegionClass().equals("slElementInfo")&&documents!=null) {//要件信息
						setSlData(pageTabRegionConfigDto,BeanToMapUtil.beanToMap(documents));
					}
					//畅贷（出款方式）
					if(isRelationC){
						if("slForeclosureType".equals(pageTabRegionConfigDto.getRegionClass())&&"出款方式".equals(pageTabRegionConfigDto.getTitle())){
							PageTabRegionConfigDto cPageTabRegionConfigDto = new PageTabRegionConfigDto();
									try {
										cPageTabRegionConfigDto = (PageTabRegionConfigDto) BeanUtils.cloneBean(pageTabRegionConfigDto);
									} catch (Exception e) {
										e.printStackTrace();
									} 
							cPageTabRegionConfigDto.setTitle("畅贷（出款方式）");
							cPageTabRegionConfigDto.setRegionClass("cForeclosureType");
							regionList.add(cPageTabRegionConfigDto);
						}
					}
					pageTabConfigDto.setPageTabRegionConfigDtos(regionList);
					//畅贷关联订单不可编辑债务置换贷款信息
					if(isRelationC&&!"cForeclosureType".equals(MapUtils.getString(params, "regionClass"))&&!"slElementInfo".equals(MapUtils.getString(params, "regionClass"))){
						pageTabRegionConfigDto.setCurAbleEdit(2);
					}
				}
			}
			
		}
		//公证
		else if("tbl_sl_notarization_page".equals(pageConfigDto.getPageClass())
				||"tbl_fdd_notarization_page".equals(pageConfigDto.getPageClass())){
				NotarizationDto obj = new NotarizationDto();
				obj.setOrderNo(orderNo);
				obj.setRelationOrderNo(orderListDto.getRelationOrderNo());
				obj = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/process/notarization/v/init", obj, NotarizationDto.class);
				if(obj==null){
					obj = new NotarizationDto();
				}
				int isReadOnly = 1;
				if(isRelationC&&StringUtils.isNotBlank(obj.getNotarizationTimeStr())
						&&StringUtils.isNotBlank(obj.getNotarizationAddress())
						&&StringUtils.isNotBlank(obj.getEstimatedTimeStr())
						&&StringUtils.isNotBlank(obj.getNotarizationImg())){
					isReadOnly = 2;
				}
				map = BeanToMapUtil.beanToMap(obj);
				if(obj.getNotarizationTime()!=null){
					map.put("notarizationTime", DateUtil.getDateByFmt(obj.getNotarizationTime(), DateUtil.FMT_TYPE2));
				}
				if(obj.getEstimatedTime()!=null){
					map.put("estimatedTime", DateUtil.getDateByFmt(obj.getEstimatedTime(), DateUtil.FMT_TYPE2));//预计出款日期
				}
				List<PageTabRegionFormConfigDto> formList = new ArrayList<PageTabRegionFormConfigDto>();
				for(PageTabConfigDto pageTabConfigDto : pageConfigDto.getPageTabConfigDtos()) {
					for (PageTabRegionConfigDto pageTabRegionConfigDto : pageTabConfigDto.getPageTabRegionConfigDtos()) {
							//赋值
							if(!isEdit){//节点详情
								PageTabRegionFormConfigDto pageTabRegionFormConfigDto;
								pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1,"公证时间","notarizationTime",DateUtil.getDateByFmt(obj.getNotarizationTime(), DateUtil.FMT_TYPE2));
								formList.add(pageTabRegionFormConfigDto);
								pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1,"公证地点","notarizationAddress",obj.getNotarizationAddress());
								formList.add(pageTabRegionFormConfigDto);
								pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1,"预计出款日期","estimatedTime",DateUtil.getDateByFmt(obj.getEstimatedTime(), DateUtil.FMT_TYPE2));
								formList.add(pageTabRegionFormConfigDto);
								pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(6,"备注","remark",obj.getRemark());
								formList.add(pageTabRegionFormConfigDto);
								pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(110,null,"imgs",obj.getNotarizationImg());
								formList.add(pageTabRegionFormConfigDto);
								pageTabRegionConfigDto.getValueList().set(0, formList);
								//加编辑按钮
								if("facesign".equals(orderListDto.getProcessId())){
									pageTabRegionConfigDto.setAbleEdit(1);
								}
							}else{
								setSlData(pageTabRegionConfigDto, map);
								
								for (PageTabRegionFormConfigDto pageTabRegionFormConfigDtoTemp:pageTabRegionConfigDto.getValueList().get(0)) {
									if(pageTabRegionFormConfigDtoTemp.getKey().equals("notarizationAddress")){
										pageTabRegionFormConfigDtoTemp.setTypeDepend(pageTabRegionFormConfigDtoTemp.getTypeDepend().replace("4403", orderListDto.getCityCode()));
									}
									if(isReadOnly == 2){//关联畅贷不编辑已经公证过的信息，直接提交
										if(pageTabRegionFormConfigDtoTemp.getType()<5){
											pageTabRegionFormConfigDtoTemp.setType(1);
										}
										if(pageTabRegionFormConfigDtoTemp.getType()!=6){
											pageTabRegionFormConfigDtoTemp.setIsReadOnly(2);
										}
									}
								}
								
							}
					}
				}
		}
		//面签
		else if("tbl_sl_facesign_page".equals(pageConfigDto.getPageClass())
				||"tbl_fdd_facesign_page".equals(pageConfigDto.getPageClass())){
			FacesignRecognitionDto dto=new FacesignRecognitionDto();
			dto.setOrderNo(orderNo);
			//List<FacesignRecognitionDto> respList = httpUtil.getList(Constants.LINK_CREDIT, "/credit/process/facesign/v/faceRecognitionDetail", dto, FacesignRecognitionDto.class);
			FaceSignDto facesign = new FaceSignDto();
			facesign.setOrderNo(orderNo);
			facesign = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/process/facesign/v/detail", facesign, FaceSignDto.class);
			Integer isFace = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/order/base/v/isFace", map, Integer.class);
			
			//是否陕国投面签
			boolean sgt = false;
			Map<String,Object> orderMap = new HashMap<String,Object>();
			orderMap.put("orderNo", orderNo);
			RespDataObject<Map<String,Object>> respData = httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/risk/allocationfund/v/getOrderNoMosaicFundCode", orderMap, Map.class);
			Map<String,Object> mm = (Map<String, Object>) respData.getData();
			Iterator<Map.Entry<String,Object>>  entries = mm.entrySet().iterator();
			while (entries.hasNext()) {
				Map.Entry<String,Object> entry = entries.next();
				if(entry.getValue().toString().contains("1000")) {
					sgt = true;
				}
			}
			
			List<PageTabRegionFormConfigDto> tempList = new ArrayList<PageTabRegionFormConfigDto>();
			for(PageTabConfigDto pageTabConfigDto : pageConfigDto.getPageTabConfigDtos()) {
				for (PageTabRegionConfigDto pageTabRegionConfigDto : pageTabConfigDto.getPageTabRegionConfigDtos()) {
					for (List<PageTabRegionFormConfigDto> valuseList : pageTabRegionConfigDto.getValueList()) {
						for (PageTabRegionFormConfigDto pageTabRegionFormConfigDto : valuseList) {
							int i = 0;
//							if("担保人".equals(pageTabRegionFormConfigDto.getTitle())||"共同借款人".equals(pageTabRegionFormConfigDto.getTitle())||"借款人".equals(pageTabRegionFormConfigDto.getTitle())){
								//2!=isFace 现在已改不需要人脸识别
//								if(false){
//									for (FacesignRecognitionDto facesignRecognitionDto : respList) {
//										if(pageTabRegionFormConfigDto.getTitle().equals(facesignRecognitionDto.getCustomerType())){
//											if(i == 0){
//												pageTabRegionFormConfigDto.setTitle(pageTabRegionFormConfigDto.getTitle());
//												pageTabRegionFormConfigDto.setValue(facesignRecognitionDto.getIsSuccess()+"");
//												pageTabRegionFormConfigDto.setSpecialValue(facesignRecognitionDto.getId()+"");
//												tempList.add(pageTabRegionFormConfigDto);
//											}else{
//												PageTabRegionFormConfigDto tempDto = new PageTabRegionFormConfigDto();
//												tempDto = pageTabRegionFormConfigDto;
//												tempDto = pageTabRegionFormConfigDto;
//												tempDto.setTitle(tempDto.getTitle());
//												tempDto.setValue(facesignRecognitionDto.getIsSuccess()+"");
//												tempDto.setSpecialValue(facesignRecognitionDto.getId()+"");
//												tempList.add(tempDto);
//											}
//											i++;
//										}
//									}
//								}
//							}else{
							if((sgt||(!"账户类型".equals(pageTabRegionFormConfigDto.getTitle())
									&&!"客户名称".equals(pageTabRegionFormConfigDto.getTitle())
									&&!"账户所属银行".equals(pageTabRegionFormConfigDto.getTitle())
									&&!"银行账户号".equals(pageTabRegionFormConfigDto.getTitle())
									&&!"证件类型".equals(pageTabRegionFormConfigDto.getTitle())
									&&!"证件号码".equals(pageTabRegionFormConfigDto.getTitle())
									&&!"回款卡基本信息".equals(pageTabRegionFormConfigDto.getTitle())
									&&!"绑定支付渠道1".equals(pageTabRegionFormConfigDto.getTitle())
									&&!"绑定支付渠道2".equals(pageTabRegionFormConfigDto.getTitle())
									&&!"手机号".equals(pageTabRegionFormConfigDto.getTitle())
									))&&(!"担保人".equals(pageTabRegionFormConfigDto.getTitle())
											&&!"共同借款人".equals(pageTabRegionFormConfigDto.getTitle())
											&&!"借款人".equals(pageTabRegionFormConfigDto.getTitle()))) {
								if(facesign!=null&&((facesign.getReturnOne()!=null&&facesign.getReturnOne().contains("成功"))
										||(facesign.getReturnTwo()!=null&&facesign.getReturnTwo().contains("成功")))
										&&!"绑定支付渠道1".equals(pageTabRegionFormConfigDto.getTitle())
										&&!"绑定支付渠道2".equals(pageTabRegionFormConfigDto.getTitle())
										&&!"备注".equals(pageTabRegionFormConfigDto.getTitle())
										&&!"添加面签影像资料".equals(pageTabRegionFormConfigDto.getTitle())){
									pageTabRegionFormConfigDto.setIsReadOnly(2);
									if("证件类型".equals(pageTabRegionFormConfigDto.getTitle())) {
										pageTabRegionFormConfigDto.setType(1);
									}
								}
								tempList.add(pageTabRegionFormConfigDto);
							}
//							}
						}
					}
				}
			}
			map = BeanToMapUtil.beanToMap(facesign);
			if(facesign!=null&&facesign.getFaceSignTime()!=null){
				map.put("faceSignTime", DateUtil.getDateByFmt(facesign.getFaceSignTime(), DateUtil.FMT_TYPE2));
			}
			map.put("customerName", orderListDto.getCustomerName());
			//房抵贷陕国投面签绑定数据
			OrderBaseCustomerDto customer = new OrderBaseCustomerDto();
			if(null!=orderListDto) {
				isRelationC = orderListDto.getProductCode().equals("03") && StringUtils.isNotBlank(orderListDto.getRelationOrderNo());
				if(isRelationC) {
					customer.setOrderNo(orderListDto.getRelationOrderNo());
				}else {
					customer.setOrderNo(orderNo);
				}
			}
			customer = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/order/customer/v/query", customer,OrderBaseCustomerDto.class);
			if((facesign==null||StringUtils.isBlank(facesign.getCertificateType()))&&customer!=null) {
				map.put("certificateType", customer.getCustomerCardType());
				map.put("certificateNo", customer.getCustomerCardNumber());
			}
			OrderBaseHouseLendingDto houseLending = new OrderBaseHouseLendingDto();
			houseLending.setOrderNo(orderNo);
			houseLending = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/order/house/lending/v/query", houseLending,OrderBaseHouseLendingDto.class);
			if((facesign==null||StringUtils.isBlank(facesign.getBankName()))&&houseLending!=null) {
				Map<String,Object> choiceMap = new HashMap<String,Object>();
				choiceMap.put("type", "sgtBank");
				RespData<DictDto> resp = new HttpUtil().getRespData(Constants.LINK_CREDIT, "/credit/common/base/v/choiceDict", choiceMap, DictDto.class);
				List<DictDto> dicts = resp.getData();
				for (DictDto dictDto : dicts) {
					if(dictDto.getName().equals(houseLending.getPaymentBankName())) {
						map.put("bankName", dictDto.getName());
						map.put("bankNameId", dictDto.getCode());
					}
				}
				map.put("bankCardNo", houseLending.getPaymentBankAccount());
				map.put("mobile", houseLending.getPaymentPhoneNumber());
			}
			if(facesign!=null) {
				if(facesign.getReturnOne()!=null&&facesign.getReturnOne().contains("成功")) {
					map.put("returnOne", "中金支付 成功");
				}
				if(facesign.getReturnTwo()!=null&&facesign.getReturnTwo().contains("成功")) {
					map.put("returnTwo", "宝付支付 成功");
				}
			}
			for(PageTabConfigDto pageTabConfigDto : pageConfigDto.getPageTabConfigDtos()) {
				for (PageTabRegionConfigDto pageTabRegionConfigDto : pageTabConfigDto.getPageTabRegionConfigDtos()) {
					pageTabRegionConfigDto.getValueList().set(0, tempList);
					if(!isEdit){//节点详情
						//加编辑按钮
						if("dataAudit".equals(orderListDto.getProcessId())){
							pageTabRegionConfigDto.setAbleEdit(1);
						}
					}
					setSlData(pageTabRegionConfigDto, map);
				}
			}
		}
		//分配订单
		else if("tbl_sl_managerAudit_page".equals(pageConfigDto.getPageClass())
				||"tbl_fdd_managerAudit_page".equals(pageConfigDto.getPageClass())){
			ManagerAuditDto managerAuditDto = new ManagerAuditDto();
			managerAuditDto.setOrderNo(orderNo);
			managerAuditDto = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/process/managerAudit/v/detail", managerAuditDto, ManagerAuditDto.class);
			List<PageTabRegionFormConfigDto> tempList = new ArrayList<PageTabRegionFormConfigDto>();
			for(PageTabConfigDto pageTabConfigDto : pageConfigDto.getPageTabConfigDtos()) {
				for (PageTabRegionConfigDto pageTabRegionConfigDto : pageTabConfigDto.getPageTabRegionConfigDtos()) {
					for (List<PageTabRegionFormConfigDto> valuseList : pageTabRegionConfigDto.getValueList()) {
						for (PageTabRegionFormConfigDto pageTabRegionFormConfigDto : valuseList) {
							tempList.add(pageTabRegionFormConfigDto);
						}
					}
				}
			}
			map = BeanToMapUtil.beanToMap(managerAuditDto);
			PageTabRegionFormConfigDto pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(103, "确认","pass", "/credit/process/managerAudit/v/add", "/credit/user/user/v/searchByType2?name=风控初审&title=选择初审经理&cityCode="+orderListDto.getCityCode()+"&cityName="+orderListDto.getCityName()+"&productCode="+MapUtils.getString(params, "productCode"));
			pageTabRegionFormConfigDto.setIsReadOnly(1);
			tempList.add(pageTabRegionFormConfigDto);
			for(PageTabConfigDto pageTabConfigDto : pageConfigDto.getPageTabConfigDtos()) {
				for (PageTabRegionConfigDto pageTabRegionConfigDto : pageTabConfigDto.getPageTabRegionConfigDtos()) {
					pageTabRegionConfigDto.getValueList().set(0, tempList);
					setSlData(pageTabRegionConfigDto, map);
				}
			}
		}
		//风控终审
		else if("tbl_sl_auditFinal_page".equals(pageConfigDto.getPageClass())
				||"tbl_fdd_auditFinal_page".equals(pageConfigDto.getPageClass())){
			FirstAuditDto firstAuditDto = new FirstAuditDto();
			FinalAuditDto finalAuditDto = new FinalAuditDto();
			ForeclosureDto foreclosure = new ForeclosureDto();
			OrderBaseBorrowDto baseBorrowDto = new OrderBaseBorrowDto();
			if(isEdit){//节点详情
				//初审信息
				firstAuditDto.setOrderNo(orderNo);
				firstAuditDto = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/risk/first/v/appDetail", firstAuditDto, FirstAuditDto.class);
				//终审信息
				finalAuditDto.setOrderNo(orderNo);
				finalAuditDto =  httpUtil.getObject(Constants.LINK_CREDIT, "/credit/risk/final/v/detail", finalAuditDto, FinalAuditDto.class);
				//结清原贷款
				foreclosure.setOrderNo(orderNo);
				foreclosure = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/process/foreclosure/v/detail", foreclosure, ForeclosureDto.class);
				//借款信息
				baseBorrowDto.setOrderNo(orderNo);
				baseBorrowDto = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/order/borrow/v/query", baseBorrowDto,OrderBaseBorrowDto.class);
			}
			//最后一条流水
			OrderFlowDto orderFlowDto = new OrderFlowDto();
			orderFlowDto.setOrderNo(orderNo);
			orderFlowDto = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/order/flow/v/selectEndOrderFlow", orderFlowDto,OrderFlowDto.class);
			Map<String, Object> dataMap1 = MapUtils.getMap((Map<String, Object>)httpUtil.getData(Constants.LINK_CREDIT, "/credit/risk/final/v/loadFinal", params), "data",new HashMap<String, Object>());
			params.put("processId", "auditFinal");
			Map<String, Object> dataMap2 = (Map<String, Object>)httpUtil.getData(Constants.LINK_CREDIT, "/credit/risk/base/v/orderIsBack", params);
			List<PageTabRegionFormConfigDto> formList = new ArrayList<PageTabRegionFormConfigDto>();
			for(PageTabConfigDto pageTabConfigDto : pageConfigDto.getPageTabConfigDtos()) {
				for (PageTabRegionConfigDto pageTabRegionConfigDto : pageTabConfigDto.getPageTabRegionConfigDtos()) {
					//赋值
					if(!isEdit){//节点详情
						PageTabRegionFormConfigDto pageTabRegionFormConfigDto;
						map = MapUtils.getMap(dataMap1, "auditFinal");
						pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1,"终审人","handleName",MapUtils.getString(map, "handleName",""));
						formList.add(pageTabRegionFormConfigDto);
						Date date = new Date(MapUtils.getLongValue(map, "auditTime"));
						pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1,"终审时间","auditTime", DateUtil.getDateByFmt(date, DateUtil.FMT_TYPE2));
						formList.add(pageTabRegionFormConfigDto);
						String auditStatus = "-";
						if(MapUtils.getInteger(map, "auditStatus")==1){
							auditStatus = "通过";
						}else if(MapUtils.getInteger(map, "auditStatus")==2){
							auditStatus = "不通过(退回)";
						}else if(MapUtils.getInteger(map, "auditStatus")==3){
							auditStatus = "上报首席风险官";
						}
						pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1,"终审结果","auditTime",auditStatus);
						formList.add(pageTabRegionFormConfigDto);
						pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(6,"终审意见","remark",MapUtils.getString(map, "remark"));
						formList.add(pageTabRegionFormConfigDto);
						pageTabRegionConfigDto.getValueList().set(0, formList);
					}else{
						PageTabRegionFormConfigDto pageTabRegionFormConfigDto;
						pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1, "城市", "cityName", orderListDto.getCityName(), orderListDto.getCityCode());
						pageTabRegionFormConfigDto.setIsReadOnly(2);
						formList.add(pageTabRegionFormConfigDto);
						pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1, "业务类型", "productName", orderListDto.getProductName(), orderListDto.getProductCode());
						pageTabRegionFormConfigDto.setIsReadOnly(2);
						formList.add(pageTabRegionFormConfigDto);
						pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1, "客户姓名", "customerName", orderListDto.getCustomerName(), null);
						pageTabRegionFormConfigDto.setIsReadOnly(2);
						formList.add(pageTabRegionFormConfigDto);
						pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1, "借款金额", "borrowingAmount", orderListDto.getBorrowingAmount()+"万元", null);
						pageTabRegionFormConfigDto.setIsReadOnly(2);
						formList.add(pageTabRegionFormConfigDto);
						if("tbl_fdd_auditFinal_page".equals(pageConfigDto.getPageClass())) {
							pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1, "借款期限", "borrowingDay", orderListDto.getBorrowingDay()+"期", null);
						}else {
							pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1, "借款期限", "borrowingDay", orderListDto.getBorrowingDay()+"天", null);
						}
						pageTabRegionFormConfigDto.setIsReadOnly(2);
						formList.add(pageTabRegionFormConfigDto);
						pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1, "合作机构", "cooperativeAgencyName", orderListDto.getCooperativeAgencyName(), orderListDto.getCooperativeAgencyId()+"");
						pageTabRegionFormConfigDto.setIsReadOnly(2);
						formList.add(pageTabRegionFormConfigDto);
						pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1, "渠道经理", "channelManagerName", orderListDto.getChannelManagerName(), orderListDto.getChannelManagerUid());
						pageTabRegionFormConfigDto.setIsReadOnly(2);
						formList.add(pageTabRegionFormConfigDto);
						if(isChangloan){
							pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1, "受理员", "acceptMemberName", orderListDto.getAcceptMemberName(), orderListDto.getAcceptMemberUid());
							pageTabRegionFormConfigDto.setIsReadOnly(2);
							formList.add(pageTabRegionFormConfigDto);
						}
						if(!"03".equals(orderListDto.getProductCode())&&!"04".equals(orderListDto.getProductCode())){
							if(foreclosure!=null){
								pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1, "结清原贷款时间", "channelManagerName", foreclosure.getForeclosureTimeStr()==null?"-":foreclosure.getForeclosureTimeStr(), null);
							}else{
								pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1, "结清原贷款时间", "channelManagerName", "-", null);
							}
							pageTabRegionFormConfigDto.setIsReadOnly(2);
							formList.add(pageTabRegionFormConfigDto);
							//原贷款银行，地点
							String oldLoanBankName = "";
							if(baseBorrowDto.getIsOldLoanBank()!=null){
								if(baseBorrowDto.getIsOldLoanBank().equals(1)){
										if(baseBorrowDto.getOldLoanBankNameId()!=null){
											oldLoanBankName = CommonDataUtil.getBankNameById(baseBorrowDto.getOldLoanBankNameId()).getName();
										}
										if(baseBorrowDto.getOldLoanBankSubNameId()!=null){
											oldLoanBankName +="-"+CommonDataUtil.getSubBankNameById(baseBorrowDto.getOldLoanBankSubNameId()).getName();
										}
									if(StringUtils.isEmpty(oldLoanBankName)){
										oldLoanBankName = "-";
									}
									pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1, "结清原贷款地址", "loanBankName", oldLoanBankName, null);
								}else{
									pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1, "结清原贷款地址", "loanBankName", StringUtils.isEmpty(baseBorrowDto.getOldLoanBankName())?"-":baseBorrowDto.getOldLoanBankName(), null);
								}
							}else{
								pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1, "结清原贷款地址", "loanBankName", "-", null);
							}
							pageTabRegionFormConfigDto.setIsReadOnly(2);
							formList.add(pageTabRegionFormConfigDto);
							//新贷款银行地点
							String loanBankName = "";
							if(baseBorrowDto.getIsLoanBank()!=null){
								if(baseBorrowDto.getIsLoanBank().equals(1)){
									if(baseBorrowDto.getLoanBankNameId()!=null){
										loanBankName = CommonDataUtil.getBankNameById(baseBorrowDto.getLoanBankNameId()).getName();
									}
									if(baseBorrowDto.getLoanSubBankNameId()!=null){
										loanBankName +="-"+CommonDataUtil.getSubBankNameById(baseBorrowDto.getLoanSubBankNameId()).getName();
									}
									if(StringUtils.isEmpty(loanBankName)){
										loanBankName = "-";
									}
									pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1, "新贷款地址", "loanBankName", loanBankName, null);
								}else{
									pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1, "新贷款地址", "loanBankName", StringUtils.isEmpty(baseBorrowDto.getLoanBankName())?"-":baseBorrowDto.getLoanBankName(), null);
								}
							}else{
								pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1, "新贷款地址", "loanBankName", "-", null);
							}
							pageTabRegionFormConfigDto.setIsReadOnly(2);
							formList.add(pageTabRegionFormConfigDto);
							//放款时间
							if(StringUtils.isNotEmpty(orderListDto.getLendingTime())){
								pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1, "放款时间","lendingTime", orderListDto.getLendingTime().substring(0, 10), null);
							}else{
								pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1, "放款时间","lendingTime", "-", null);
							}
							pageTabRegionFormConfigDto.setIsReadOnly(2);
							formList.add(pageTabRegionFormConfigDto);
							//预计回款时间
							if(StringUtils.isNotEmpty(orderListDto.getPlanPaymentTime())){
								pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1, "预计回款时间","planPaymentTime", orderListDto.getPlanPaymentTime().substring(0, 10), null);
							}else{
								pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1, "预计回款时间","planPaymentTime", "-", null);
							}
							pageTabRegionFormConfigDto.setIsReadOnly(2);
							formList.add(pageTabRegionFormConfigDto);
						}
						
						//业务概况,初审意见
						pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(102, "风控初审意见","firstRemark", firstAuditDto.getRemark(), null);
						formList.add(pageTabRegionFormConfigDto);
						//风控修改征信日志
						CreditDto credit = new CreditDto();
						credit.setOrderNo(orderNo);
						List<Map<String,Object>> respData = httpUtil.getList(Constants.LINK_CREDIT, "/credit/risk/ordercredit/v/selectCreditLog", credit, Map.class);
						String creditLog = "";
						creditLog += "操作记录（操作人:"+firstAuditDto.getHandleName()+"）\n";
						if(respData!=null&&respData.size()>0){
							for (int i=0;i<respData.size();i++) {
								creditLog += " "+(i+1)+":将【"+respData.get(i).get("colName")+"】从\""+respData.get(i).get("startVal")+"\"改为\""+respData.get(i).get("endVal")+";"+this.getStrTime(MapUtils.getString(respData.get(i), "updateTime"))+"\n";
							}
							pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(102, "风控修改征信日志","creditLog", creditLog, null);
							formList.add(pageTabRegionFormConfigDto);
						}
						if("04".equals(orderListDto.getProductCode())){
							//还款方式
							pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(2, "还款方式","paymentType", finalAuditDto.getPaymentType(), null);
							pageTabRegionFormConfigDto.setIsNeed(2);
							pageTabRegionFormConfigDto.setSpecialValue(finalAuditDto.getPaymentType());
							pageTabRegionFormConfigDto.setSpecialKey("paymentType");
							pageTabRegionFormConfigDto.setTypeDepend("repaymentMethodType");
							List<Map<String,String>> dataList = new ArrayList<Map<String,String>>();
							Map<String,String> m = new HashMap<String, String>();
							m.put("id", "凭抵押回执放款");
							m.put("code", "凭抵押回执放款");
							m.put("name", "凭抵押回执放款");
							dataList.add(m);
							m = new HashMap<String, String>();
							m.put("id", "凭抵押状态放款");
							m.put("code", "凭抵押状态放款");
							m.put("name", "凭抵押状态放款");
							dataList.add(m);
							m = new HashMap<String, String>();
							m.put("id", "凭他项权证放款");
							m.put("code", "凭他项权证放款");
							m.put("name", "凭他项权证放款");
							dataList.add(m);
							pageTabRegionFormConfigDto.setDataList(dataList);
							formList.add(pageTabRegionFormConfigDto);
						}
						//终审意见
						pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(6, "风控终审意见","remark", finalAuditDto.getRemark(), null);
						pageTabRegionFormConfigDto.setIsNeed(2);
						pageTabRegionFormConfigDto.setPlaceholder("请在此处输入审批意见（必填）");
						formList.add(pageTabRegionFormConfigDto);
						//复核审批
						//是否上报首席风险官
						pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(103, "复核审批","pass", "/credit/risk/final/v/reportReview", "/credit/user/user/v/searchByType2?name=复核审批&title=选择复核审批操作人&cityCode="+orderListDto.getCityCode()+"&cityName="+orderListDto.getCityName()+"&productCode="+MapUtils.getString(params, "productCode"));
						String specialValue = pageTabRegionFormConfigDto.getSpecialValue()+"|/credit/user/user/v/searchByType2?name=推送金融机构&title=选择推送金融机构操作人&cityCode="+orderListDto.getCityCode()+"&cityName="+orderListDto.getCityName()+"&productCode="+MapUtils.getString(params, "productCode");
						if(orderListDto.getBorrowingAmount()>=3000){
							specialValue+="|/credit/user/user/v/searchByType2?name=法务审批&title=选择法务&cityCode="+orderListDto.getCityCode()+"&cityName="+orderListDto.getCityName()+"&productCode="+MapUtils.getString(params, "productCode");
						}
						specialValue+="|/credit/user/user/v/searchByType2?name=首席风险官审批&title=选择首席风险官&cityCode="+orderListDto.getCityCode()+"&cityName="+orderListDto.getCityName()+"&productCode="+MapUtils.getString(params, "productCode");
						pageTabRegionFormConfigDto.setSpecialValue(specialValue);
						formList.add(pageTabRegionFormConfigDto);
						if(orderFlowDto!=null&&orderFlowDto.getIsNewWalkProcess()==2){//不重新走流程
							pageTabRegionFormConfigDto.setIsReadOnly(2);
						}
						//通过
						pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(103, "通过","pass", "/credit/risk/final/v/pass", "/credit/user/user/v/searchByType2?name=推送金融机构&title=选择推送金融机构操作人&cityCode="+orderListDto.getCityCode()+"&cityName="+orderListDto.getCityName()+"&productCode="+MapUtils.getString(params, "productCode"));
						if(orderFlowDto!=null&&orderFlowDto.getIsNewWalkProcess()==2){//不重新走流程
							pageTabRegionFormConfigDto.setIsReadOnly(2);
						}
						if(MapUtils.getBooleanValue(dataMap1, "auditFinalShow",true)&&orderListDto.getBorrowingAmount()<3000){
							//pageTabRegionFormConfigDto.setType(0);
							formList.add(pageTabRegionFormConfigDto);
						}
						if(MapUtils.getBooleanValue(dataMap1, "auditFinalShow",true)&&orderListDto.getBorrowingAmount()>=3000){
							pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(103, "通过","pass", "/credit/risk/final/v/pass", "/credit/user/user/v/searchByType2?name=推送金融机构&title=选择推送金融机构操作人&cityCode="+orderListDto.getCityCode()+"&cityName="+orderListDto.getCityName()+"&productCode="+MapUtils.getString(params, "productCode"));
							pageTabRegionFormConfigDto.setSpecialValue("/credit/user/user/v/searchByType2?name=法务审批&title=选择法务&cityCode="+orderListDto.getCityCode()+"&cityName="+orderListDto.getCityName()+"&productCode="+MapUtils.getString(params, "productCode")+"|"+pageTabRegionFormConfigDto.getSpecialValue());
							if(orderFlowDto!=null&&orderFlowDto.getIsNewWalkProcess()==2){//不重新走流程
								pageTabRegionFormConfigDto.setIsReadOnly(2);
							}
							formList.add(pageTabRegionFormConfigDto);
						}
						//上报风险官
						pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(103, "上报风险官","report", "/credit/risk/final/v/reportOfficer", "/credit/user/user/v/searchByType2?name=首席风险官审批&title=选择首席风险官&cityCode="+orderListDto.getCityCode()+"&cityName="+orderListDto.getCityName()+"&productCode="+MapUtils.getString(params, "productCode"));
						if(orderFlowDto!=null&&orderFlowDto.getIsNewWalkProcess()==2){//不重新走流程
							pageTabRegionFormConfigDto.setIsReadOnly(2);
						}
						formList.add(pageTabRegionFormConfigDto);
						//退回
						if(!(orderFlowDto!=null&&StringUtils.isNotBlank(orderFlowDto.getBackReason()))){
							pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(106, "不通过","repulse", "/credit/order/flow/v/backOrder", null);
							if(MapUtils.getBooleanValue(dataMap2, "data",true)){
								//pageTabRegionFormConfigDto.setType(0);
							}
							formList.add(pageTabRegionFormConfigDto);
						}
						
						pageTabRegionConfigDto.getValueList().set(0, formList);
					}
				}
			}
		}
		//复核审批
		else if("tbl_sl_auditReview_page".equals(pageConfigDto.getPageClass())
				||"tbl_fdd_auditReview_page".equals(pageConfigDto.getPageClass())){
			//初审信息
			FirstAuditDto firstAuditDto = new FirstAuditDto(); 
			FinalAuditDto finalAuditDto = new FinalAuditDto();
			//初审信息
			firstAuditDto.setOrderNo(orderNo);
			firstAuditDto = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/risk/first/v/appDetail", firstAuditDto, FirstAuditDto.class);
			//终审信息
			finalAuditDto.setOrderNo(orderNo);
			finalAuditDto =  httpUtil.getObject(Constants.LINK_CREDIT, "/credit/risk/final/v/detail", finalAuditDto, FinalAuditDto.class);
			//复核审批信息
			ReviewAuditDto reviewAuditDto = new ReviewAuditDto(); 
			reviewAuditDto.setOrderNo(orderNo);
			reviewAuditDto = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/risk/review/v/init", reviewAuditDto, ReviewAuditDto.class);
			//最后一条流水
			OrderFlowDto orderFlowDto = new OrderFlowDto();
			orderFlowDto.setOrderNo(orderNo);
			orderFlowDto = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/order/flow/v/selectEndOrderFlow", orderFlowDto,OrderFlowDto.class);
			List<PageTabRegionFormConfigDto> formList = new ArrayList<PageTabRegionFormConfigDto>();
			for(PageTabConfigDto pageTabConfigDto : pageConfigDto.getPageTabConfigDtos()) {
				for (PageTabRegionConfigDto pageTabRegionConfigDto : pageTabConfigDto.getPageTabRegionConfigDtos()) {
						PageTabRegionFormConfigDto pageTabRegionFormConfigDto;
						//业务概况，初审意见
						pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(102, "风控初审意见","firstRemark", firstAuditDto.getRemark(), null);
						formList.add(pageTabRegionFormConfigDto);
						//终审意见
						pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(102, "风控终审意见","finalRemark", finalAuditDto.getRemark(), null);
						formList.add(pageTabRegionFormConfigDto);
						if("04".equals(orderListDto.getProductCode())){
							pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1, "还款方式","paymentType", finalAuditDto.getPaymentType(), null);
							pageTabRegionFormConfigDto.setIsReadOnly(2);
							formList.add(pageTabRegionFormConfigDto);
						}
						pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1, "借款金额", "borrowingAmount", reviewAuditDto.getLoanAmont()+"万元", null);
						pageTabRegionFormConfigDto.setIsReadOnly(2);
						formList.add(pageTabRegionFormConfigDto);
						if("tbl_fdd_auditReview_page".equals(pageConfigDto.getPageClass())) {
							pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1, "借款期限", "borrowingDay", orderListDto.getBorrowingDay()+"期", null);
						}else {
							pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1, "借款期限", "borrowingDay", orderListDto.getBorrowingDay()+"天", null);
						}
						pageTabRegionFormConfigDto.setIsReadOnly(2);
						formList.add(pageTabRegionFormConfigDto);
						pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1, "建议费率", "rate",reviewAuditDto.getRate()+"", null);
						pageTabRegionFormConfigDto.setSingle("%/天");
						formList.add(pageTabRegionFormConfigDto);
						pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1, "建议逾期费率", "overdueRate", reviewAuditDto.getOverdueRate()+"", null);
						pageTabRegionFormConfigDto.setSingle("%/天");
						formList.add(pageTabRegionFormConfigDto);
						//复核审批意见
						pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(6, "复核审批意见","remark", null, null);
						pageTabRegionFormConfigDto.setIsNeed(1);
						pageTabRegionFormConfigDto.setPlaceholder("请在此处输入审批意见（非必填）");
						formList.add(pageTabRegionFormConfigDto);
						//通过
						if(reviewAuditDto.getType()==1){
							pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(103, "审批通过","pass", "/credit/risk/review/v/reportOfficer","");
							pageTabRegionFormConfigDto.setIsReadOnly(2);
							formList.add(pageTabRegionFormConfigDto);
						}else{
							pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(103, "审批通过","pass", "/credit/risk/review/v/pass","");
							pageTabRegionFormConfigDto.setIsReadOnly(2);
							formList.add(pageTabRegionFormConfigDto);
						}
						//退回
						if(!(orderFlowDto!=null&&StringUtils.isNotBlank(orderFlowDto.getBackReason()))){
							pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(106, "不通过","repulse", "/credit/order/flow/v/backOrder", null);
							formList.add(pageTabRegionFormConfigDto);
						}
						pageTabRegionConfigDto.getValueList().set(0, formList);
				}
			}
		}
		//首席风险官
		else if("tbl_sl_auditOfficer_page".equals(pageConfigDto.getPageClass())
				||"tbl_fdd_auditOfficer_page".equals(pageConfigDto.getPageClass())){
			FirstAuditDto firstAuditDto = new FirstAuditDto(); 
			FinalAuditDto finalAuditDto = new FinalAuditDto();
			ForeclosureDto  foreclosure = new ForeclosureDto();
			OrderBaseBorrowDto baseBorrowDto = new OrderBaseBorrowDto();
			if(isEdit){//节点详情
				//初审信息
				firstAuditDto.setOrderNo(orderNo);
				firstAuditDto = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/risk/first/v/appDetail", firstAuditDto, FirstAuditDto.class);
				//终审信息
				finalAuditDto.setOrderNo(orderNo);
				finalAuditDto =  httpUtil.getObject(Constants.LINK_CREDIT, "/credit/risk/final/v/detail", finalAuditDto, FinalAuditDto.class);
				//结清原贷款
				foreclosure.setOrderNo(orderNo);
				foreclosure = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/process/foreclosure/v/detail", foreclosure, ForeclosureDto.class);
				//借款信息
				baseBorrowDto.setOrderNo(orderNo);
				baseBorrowDto = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/order/borrow/v/query", baseBorrowDto,OrderBaseBorrowDto.class);
			}
			//最后一条流水
			OrderFlowDto orderFlowDto = new OrderFlowDto();
			orderFlowDto.setOrderNo(orderNo);
			orderFlowDto = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/order/flow/v/selectEndOrderFlow", orderFlowDto,OrderFlowDto.class);
			Map<String, Object> dataMap1 = MapUtils.getMap((Map<String, Object>)httpUtil.getData(Constants.LINK_CREDIT, "/credit/risk/officer/v/detail", params), "data",new HashMap<String, Object>());
			params.put("processId", "auditOfficer");
			Map<String, Object> dataMap2 = (Map<String, Object>)httpUtil.getData(Constants.LINK_CREDIT, "/credit/risk/base/v/orderIsBack", params);
			List<PageTabRegionFormConfigDto> formList = new ArrayList<PageTabRegionFormConfigDto>();
			for(PageTabConfigDto pageTabConfigDto : pageConfigDto.getPageTabConfigDtos()) {
				for (PageTabRegionConfigDto pageTabRegionConfigDto : pageTabConfigDto.getPageTabRegionConfigDtos()) {
					//赋值
					if(!isEdit){//节点详情
						PageTabRegionFormConfigDto pageTabRegionFormConfigDto;
						pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1,"首席风险官","handleName",MapUtils.getString(dataMap1, "handleName"));
						formList.add(pageTabRegionFormConfigDto);
						Date date = new Date(MapUtils.getLongValue(dataMap1, "auditTime"));
						pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1,"审批时间","auditTime", DateUtil.getDateByFmt(date, DateUtil.FMT_TYPE2));
						formList.add(pageTabRegionFormConfigDto);
						String auditStatus = "-";
						if(MapUtils.getInteger(dataMap1, "auditStatus")==1){
							auditStatus = "通过";
						}else if(MapUtils.getInteger(dataMap1, "auditStatus")==2){
							auditStatus = "不通过(退回)";
						}
						pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1,"审批结果","auditTime",auditStatus);
						formList.add(pageTabRegionFormConfigDto);
						pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(6,"首席风险官意见","remark",MapUtils.getString(dataMap1, "remark"));
						formList.add(pageTabRegionFormConfigDto);
						pageTabRegionConfigDto.getValueList().set(0, formList);
					}else{
						PageTabRegionFormConfigDto pageTabRegionFormConfigDto;
						pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1, "城市", "cityName", orderListDto.getCityName(), orderListDto.getCityCode());
						pageTabRegionFormConfigDto.setIsReadOnly(2);
						formList.add(pageTabRegionFormConfigDto);
						pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1, "业务类型", "productName", orderListDto.getProductName(), orderListDto.getProductCode());
						pageTabRegionFormConfigDto.setIsReadOnly(2);
						formList.add(pageTabRegionFormConfigDto);
						pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1, "客户姓名", "customerName", orderListDto.getCustomerName(), null);
						pageTabRegionFormConfigDto.setIsReadOnly(2);
						formList.add(pageTabRegionFormConfigDto);
						pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1, "借款金额", "borrowingAmount", orderListDto.getBorrowingAmount()+"万元", null);
						pageTabRegionFormConfigDto.setIsReadOnly(2);
						formList.add(pageTabRegionFormConfigDto);
						if("tbl_fdd_auditOfficer_page".equals(pageConfigDto.getPageClass())) {
							pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1, "借款期限", "borrowingDay", orderListDto.getBorrowingDay()+"期", null);
						}else {
							pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1, "借款期限", "borrowingDay", orderListDto.getBorrowingDay()+"天", null);
						}
						pageTabRegionFormConfigDto.setIsReadOnly(2);
						formList.add(pageTabRegionFormConfigDto);
						pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1, "合作机构", "cooperativeAgencyName", orderListDto.getCooperativeAgencyName(), orderListDto.getCooperativeAgencyId()+"");
						pageTabRegionFormConfigDto.setIsReadOnly(2);
						formList.add(pageTabRegionFormConfigDto);
						pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1, "渠道经理", "channelManagerName", orderListDto.getChannelManagerName(), orderListDto.getChannelManagerUid());
						pageTabRegionFormConfigDto.setIsReadOnly(2);
						formList.add(pageTabRegionFormConfigDto);
						if(isChangloan){
							pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1, "受理员", "acceptMemberName", orderListDto.getAcceptMemberName(), orderListDto.getAcceptMemberUid());
							pageTabRegionFormConfigDto.setIsReadOnly(2);
							formList.add(pageTabRegionFormConfigDto);
						}
						if(!"03".equals(orderListDto.getProductCode())&&!"04".equals(orderListDto.getProductCode())){
							if(foreclosure!=null){
								pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1, "结清原贷款时间", "channelManagerName", foreclosure.getForeclosureTimeStr()==null?"-":foreclosure.getForeclosureTimeStr(), null);
							}else{
								pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1, "结清原贷款时间", "channelManagerName", "-", null);
							}
							pageTabRegionFormConfigDto.setIsReadOnly(2);
							formList.add(pageTabRegionFormConfigDto);
							//原贷款银行，地点
							String oldLoanBankName = "";
							if(baseBorrowDto.getIsOldLoanBank()!=null){
								if(baseBorrowDto.getIsOldLoanBank().equals(1)){
									if(baseBorrowDto.getOldLoanBankNameId()!=null){
										oldLoanBankName = CommonDataUtil.getBankNameById(baseBorrowDto.getOldLoanBankNameId()).getName();
									}
									if(baseBorrowDto.getOldLoanBankSubNameId()!=null){
										oldLoanBankName +="-"+CommonDataUtil.getSubBankNameById(baseBorrowDto.getOldLoanBankSubNameId()).getName();
									}
									if(StringUtils.isEmpty(oldLoanBankName)){
										oldLoanBankName = "-";
									}
									pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1, "结清原贷款地址", "loanBankName", oldLoanBankName, null);
								}else{
									pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1, "结清原贷款地址", "loanBankName", StringUtils.isEmpty(baseBorrowDto.getOldLoanBankName())?"-":baseBorrowDto.getOldLoanBankName(), null);
								}
							}else{
								pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1, "结清原贷款地址", "loanBankName", "-", null);
							}
							pageTabRegionFormConfigDto.setIsReadOnly(2);
							formList.add(pageTabRegionFormConfigDto);
							//新贷款银行地点
							String loanBankName = "";
							if(baseBorrowDto.getIsLoanBank()!=null){
								if(baseBorrowDto.getIsLoanBank().equals(1)){
									if(baseBorrowDto.getLoanBankNameId()!=null){
										loanBankName = CommonDataUtil.getBankNameById(baseBorrowDto.getLoanBankNameId()).getName();
									}
									if(baseBorrowDto.getLoanSubBankNameId()!=null){
										loanBankName +="-"+CommonDataUtil.getSubBankNameById(baseBorrowDto.getLoanSubBankNameId()).getName();
									}
									if(StringUtils.isEmpty(loanBankName)){
										loanBankName = "-";
									}
									pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1, "新贷款地址", "loanBankName", loanBankName, null);
								}else{
									pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1, "新贷款地址", "loanBankName", StringUtils.isEmpty(baseBorrowDto.getLoanBankName())?"-":baseBorrowDto.getLoanBankName(), null);
								}
							}else{
								pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1, "新贷款地址", "loanBankName", "-", null);
							}
							pageTabRegionFormConfigDto.setIsReadOnly(2);
							formList.add(pageTabRegionFormConfigDto);
							//放款时间
							if(StringUtils.isNotEmpty(orderListDto.getLendingTime())){
								pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1, "放款时间","lendingTime", orderListDto.getLendingTime().substring(0, 10), null);
							}else{
								pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1, "放款时间","lendingTime", "-", null);
							}
							pageTabRegionFormConfigDto.setIsReadOnly(2);
							formList.add(pageTabRegionFormConfigDto);
							//预计回款时间
							if(StringUtils.isNotEmpty(orderListDto.getPlanPaymentTime())){
								pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1, "预计回款时间","planPaymentTime", orderListDto.getPlanPaymentTime().substring(0, 10), null);
							}else{
								pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1, "预计回款时间","planPaymentTime", "-", null);
							}
							pageTabRegionFormConfigDto.setIsReadOnly(2);
							formList.add(pageTabRegionFormConfigDto);
						}
						
						//业务概况，初审意见
						pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(102, "风控初审意见","firstRemark", firstAuditDto.getRemark(), null);
						formList.add(pageTabRegionFormConfigDto);
						//终审意见
						pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(102, "风控终审意见","finalRemark", finalAuditDto.getRemark(), null);
						formList.add(pageTabRegionFormConfigDto);
						if("04".equals(orderListDto.getProductCode())){
							pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1, "还款方式","paymentType", finalAuditDto.getPaymentType(), null);
							pageTabRegionFormConfigDto.setIsReadOnly(2);
							formList.add(pageTabRegionFormConfigDto);
						}
						//首席风险官意见
						pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(6, "首席风险官审批意见","remark", null, null);
						pageTabRegionFormConfigDto.setIsNeed(2);
						pageTabRegionFormConfigDto.setPlaceholder("请在此处输入审批意见（必填）");
						formList.add(pageTabRegionFormConfigDto);
						//通过
						if(orderListDto.getBorrowingAmount()<3000){
							pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(103, "通过","pass", "/credit/risk/officer/v/pass", "/credit/user/user/v/searchByType2?name=推送金融机构&title=选择推送金融机构操作人&cityCode="+orderListDto.getCityCode()+"&cityName="+orderListDto.getCityName()+"&productCode="+MapUtils.getString(params, "productCode"));
							if(orderFlowDto!=null&&orderFlowDto.getIsNewWalkProcess()==2){//不重新走流程
								pageTabRegionFormConfigDto.setIsReadOnly(2);
							}
							formList.add(pageTabRegionFormConfigDto);
						}else{
							pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(103, "通过","pass", "/credit/risk/officer/v/pass", "/credit/user/user/v/searchByType2?name=法务审批&title=选择法务&cityCode="+orderListDto.getCityCode()+"&cityName="+orderListDto.getCityName()+"&productCode="+MapUtils.getString(params, "productCode"));
							String specialValue = pageTabRegionFormConfigDto.getSpecialValue()+"|/credit/user/user/v/searchByType2?name=推送金融机构&title=选择推送金融机构操作人&cityCode="+orderListDto.getCityCode()+"&cityName="+orderListDto.getCityName()+"&productCode="+MapUtils.getString(params, "productCode");
							pageTabRegionFormConfigDto.setSpecialValue(specialValue);
							if(orderFlowDto!=null&&orderFlowDto.getIsNewWalkProcess()==2){//不重新走流程
								pageTabRegionFormConfigDto.setIsReadOnly(2);
							}
							formList.add(pageTabRegionFormConfigDto);
						}
						//退回
						if(!(orderFlowDto!=null&&StringUtils.isNotBlank(orderFlowDto.getBackReason()))){
							pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(106, "不通过","repulse", "/credit/order/flow/v/backOrder", null);
							if(MapUtils.getBooleanValue(dataMap2, "data",true)){
								//pageTabRegionFormConfigDto.setType(0);
							}
							formList.add(pageTabRegionFormConfigDto);
						}
						
						pageTabRegionConfigDto.getValueList().set(0, formList);
					
					}
				}
			}
		}
		//申请放款
		else if("tbl_sl_applyLoan_page".equals(pageConfigDto.getPageClass())){
				//房产信息
				OrderBaseHouseDto house = new OrderBaseHouseDto();
				if(isRelationC){
					house.setOrderNo(orderListDto.getRelationOrderNo());
				}else{
					house.setOrderNo(orderNo);
				}
				house = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/order/house/v/query", house,OrderBaseHouseDto.class);
				String propertyOwner="";
				if(house.getOrderBaseHousePropertyPeopleDto()!=null&&house.getOrderBaseHousePropertyPeopleDto().size()>0){
					List<OrderBaseHousePropertyPeopleDto> list = house.getOrderBaseHousePropertyPeopleDto();
					String json = JsonUtil.BeanToJson(list.get(0));
					Map<String,Object> m = new HashMap<String, Object>();
					try {
						m = JsonUtil.jsonToMap(json);
					} catch (JSONException e) {
						e.printStackTrace();
					}
					propertyOwner = MapUtils.getString(m, "propertyName");
				}
				//指派还款专员
				DistributionMemberDto distributionMember = new DistributionMemberDto();
				distributionMember.setOrderNo(orderNo);
				distributionMember = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/process/distributionMember/v/detail", distributionMember, DistributionMemberDto.class);
				map.put("borrowerName", orderListDto.getCustomerName());
				map.put("loanAmount", orderListDto.getBorrowingAmount());
				map.put("borrowingDays", orderListDto.getBorrowingDay());
				map.put("bankCardMaster", orderListDto.getCustomerName());
				//要件
				DocumentsDto documentsDto = new DocumentsDto();
				documentsDto.setOrderNo(orderNo);
				DocumentsDto documents= httpUtil.getObject(Constants.LINK_CREDIT, "/credit/element/basics/v/detail", documentsDto,DocumentsDto.class);
				if(documents!=null){
					ForeclosureTypeDto foreclosureTypeDto  = documents.getForeclosureType();//出款方式
					if(foreclosureTypeDto!=null){
						map.put("lendingBank", foreclosureTypeDto.getBankName());
						map.put("lendingBankId", foreclosureTypeDto.getBankNameId());
						map.put("lendingBankSub", foreclosureTypeDto.getBankSubName());
						map.put("lendingBankSubId", foreclosureTypeDto.getBankSubNameId());
						map.put("bankName", foreclosureTypeDto.getBankCardMaster());
						map.put("bankAccount", foreclosureTypeDto.getBankNo());
						map.put("bankCardMaster", foreclosureTypeDto.getBankCardMaster());
						map.put("bankNo", foreclosureTypeDto.getBankNo());
					}
				}
				ApplyLoanDto obj = new ApplyLoanDto();
				obj.setOrderNo(orderNo);
				obj = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/finance/applyLoan/v/init", obj, ApplyLoanDto.class);
				try {
					Map<String,Object> tempMap = BeanToMapUtil.beanToMap(obj);
					map.putAll(tempMap);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				PageTabRegionFormConfigDto pageTabRegionFormConfigDto;
				List<PageTabRegionFormConfigDto> list = null;
				for(PageTabConfigDto pageTabConfigDto : pageConfigDto.getPageTabConfigDtos()) {
					for (PageTabRegionConfigDto pageTabRegionConfigDto : pageTabConfigDto.getPageTabRegionConfigDtos()) {
						setSlData(pageTabRegionConfigDto, map);
						list = pageTabRegionConfigDto.getValueList().get(0);
					}
				}
				//加入产权人姓名和还款专员姓名
				pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(-3, "产权人姓名","propertyOwner", propertyOwner+","+distributionMember.getForeclosureMemberName(), "bankCardMaster");
				pageTabRegionFormConfigDto.setPlaceholder("你修改的银行卡户名异常，确定修改吗？");
				list.add(pageTabRegionFormConfigDto);
				for(PageTabConfigDto pageTabConfigDto : pageConfigDto.getPageTabConfigDtos()) {
					for (PageTabRegionConfigDto pageTabRegionConfigDto : pageTabConfigDto.getPageTabRegionConfigDtos()) {
						pageTabRegionConfigDto.getValueList().set(0, list);
					}
				}
		}
		//申请放款
		//房抵贷申请放款在此节点信息均不可修改！
		else if("tbl_fdd_applyLoan_page".equals(pageConfigDto.getPageClass())){
			//房产信息
			OrderBaseHouseDto house = new OrderBaseHouseDto();
			house.setOrderNo(orderNo);
			house = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/order/house/v/query", house,OrderBaseHouseDto.class);
			String propertyOwner="";
			if(house.getOrderBaseHousePropertyPeopleDto()!=null&&house.getOrderBaseHousePropertyPeopleDto().size()>0){
				List<OrderBaseHousePropertyPeopleDto> list = house.getOrderBaseHousePropertyPeopleDto();
				String json = JsonUtil.BeanToJson(list.get(0));
				Map<String,Object> m = new HashMap<String, Object>();
				try {
					m = JsonUtil.jsonToMap(json);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				propertyOwner = MapUtils.getString(m, "propertyName");
			}
			map.put("borrowerName", orderListDto.getCustomerName());
			map.put("loanAmount", orderListDto.getBorrowingAmount());
			map.put("borrowingDays", orderListDto.getBorrowingDay());
			map.put("bankCardMaster", orderListDto.getCustomerName());
			//放款信息
			OrderBaseHouseLendingDto houseLending = new OrderBaseHouseLendingDto();
			houseLending.setOrderNo(orderNo);
			houseLending = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/order/house/lending/v/query", houseLending,OrderBaseHouseLendingDto.class);
			if(houseLending!=null){
				map.put("lendingBank", houseLending.getLendingBankName());
				map.put("lendingBankId", houseLending.getLendingBankId());
				map.put("lendingBankSub", houseLending.getLendingBankBranchName());
				map.put("lendingBankSubId", houseLending.getLendingBankBranchId());
				map.put("bankName", houseLending.getBankUserName());
				map.put("bankAccount", houseLending.getBankAccount());
				map.put("bankCardMaster", houseLending.getBankUserName());
				map.put("bankNo", houseLending.getBankAccount());
			}
			ApplyLoanDto obj = new ApplyLoanDto();
			obj.setOrderNo(orderNo);
			obj = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/finance/applyLoan/v/init", obj, ApplyLoanDto.class);
			try {
				Map<String,Object> tempMap = BeanToMapUtil.beanToMap(obj);
				map.putAll(tempMap);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			PageTabRegionFormConfigDto pageTabRegionFormConfigDto;
			List<PageTabRegionFormConfigDto> list = null;
			for(PageTabConfigDto pageTabConfigDto : pageConfigDto.getPageTabConfigDtos()) {
				for (PageTabRegionConfigDto pageTabRegionConfigDto : pageTabConfigDto.getPageTabRegionConfigDtos()) {
					for (PageTabRegionFormConfigDto formConfigDto : pageTabRegionConfigDto.getValueList().get(0)) {
						if("lendingBank".equals(formConfigDto.getKey())
								||"lendingBankSub".equals(formConfigDto.getKey())
								||"bankName".equals(formConfigDto.getKey())
								||"bankAccount".equals(formConfigDto.getKey())
								||"chargesReceivedImg".equals(formConfigDto.getKey())
								||"payAccountImg".equals(formConfigDto.getKey())
								||"remark".equals(formConfigDto.getKey())
								||"mortgageImg".equals(formConfigDto.getKey())
								){
							formConfigDto.setIsReadOnly(1);
						}else{
							formConfigDto.setIsReadOnly(2);
						}
					}
					setSlData(pageTabRegionConfigDto, map);
					list = pageTabRegionConfigDto.getValueList().get(0);
				}
			}
			//加入产权人姓名和还款专员姓名
			pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(-3, "产权人姓名","propertyOwner", propertyOwner+","+orderListDto.getCustomerName(), "bankCardMaster");
			pageTabRegionFormConfigDto.setPlaceholder("你修改的银行卡户名异常，确定修改吗？");
			list.add(pageTabRegionFormConfigDto);
			for(PageTabConfigDto pageTabConfigDto : pageConfigDto.getPageTabConfigDtos()) {
				for (PageTabRegionConfigDto pageTabRegionConfigDto : pageTabConfigDto.getPageTabRegionConfigDtos()) {
					pageTabRegionConfigDto.getValueList().set(0, list);
				}
			}
		}
		//结清原贷款
		else if("tbl_sl_foreclosure_page".equals(pageConfigDto.getPageClass())){
				ForeclosureDto  obj = new ForeclosureDto();
				obj.setOrderNo(orderNo);
				obj = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/process/foreclosure/v/detail", obj, ForeclosureDto.class);
				map = BeanToMapUtil.beanToMap(obj);
				if(obj.getForeclosureBankSubNameId()!=null){//未结清原贷款
					//借款信息
					OrderBaseBorrowDto borrow = new OrderBaseBorrowDto();
					borrow.setOrderNo(orderNo);
					borrow = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/order/borrow/v/query", borrow,OrderBaseBorrowDto.class);
					if(borrow.getIsOldLoanBank()==1){
						map.put("foreclosureBankSubName", borrow.getOldLoanBankSubName());
					}
				}else{
					if(obj.getIsBankEnd() == 0){
						map.put("isBankEndCode", "是");
					}else{
						map.put("isBankEndCode", "否");
					}
				}
				map.put("foreclosureTime", DateUtil.getDateByFmt(obj.getForeclosureTime(), DateUtil.FMT_TYPE2));
				map.put("licenseRevTime", DateUtil.getDateByFmt(obj.getLicenseRevTime(), DateUtil.FMT_TYPE2));
				for(PageTabConfigDto pageTabConfigDto : pageConfigDto.getPageTabConfigDtos()) {
					for (PageTabRegionConfigDto pageTabRegionConfigDto : pageTabConfigDto.getPageTabRegionConfigDtos()) {
						for (PageTabRegionFormConfigDto pageTabRegionFormConfigDto : pageTabRegionConfigDto.getValueList().get(0)) {
							if(pageTabRegionFormConfigDto.getKey().equals("licenseRever")){
								pageTabRegionFormConfigDto.setTypeDepend(pageTabRegionFormConfigDto.getTypeDepend()+"&cityCode="+orderListDto.getCityCode()+"&productCode="+orderListDto.getProductCode()+"&agencyId="+MapUtils.getString(params, "agencyId","1"));
							}
						}
						setSlData(pageTabRegionConfigDto, map);
						if(!isEdit&&"forensics".equals(orderListDto.getProcessId())){
							//加编辑按钮
							pageTabRegionConfigDto.setAbleEdit(1);
						}
					}
				}
				
		}
		//取证
		else if("tbl_sl_forensics_page".equals(pageConfigDto.getPageClass())){
				ForensicsDto  obj = new ForensicsDto();
				obj.setOrderNo(orderNo);
				obj = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/process/forensics/v/detail", obj, ForensicsDto.class);
				//是否取证银行，取借款信息是否新贷款银行
				if(obj.getIsLicenseRevBank()==null){
					OrderBaseBorrowDto borrow = new OrderBaseBorrowDto();
					borrow.setOrderNo(orderNo);
					borrow = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/order/borrow/v/query", borrow,OrderBaseBorrowDto.class);
					obj.setIsLicenseRevBank(borrow.getIsLoanBank());
					if(borrow.getIsLoanBank()!=null&&borrow.getIsLoanBank()==1){
						obj.setLicenseRevBank(borrow.getLoanBankNameId());
						obj.setLicenseRevBankName(borrow.getLoanBankName());
						obj.setLicenseRevBankSub(borrow.getLoanSubBankNameId());
						obj.setLicenseRevBankSubName(borrow.getLoanSubBankName());
					}else{
						obj.setLicenseRevAddress(borrow.getLoanBankName());
					}
				}
				if(obj.getIsLicenseRevBank()!=null&&obj.getIsLicenseRevBank()==1){
					obj.setIsLicenseRevBankName("是");
				}else{
					obj.setIsLicenseRevBankName("否");
				}
				map = BeanToMapUtil.beanToMap(obj);
				map.put("licenseRevTime", DateUtil.getDateByFmt(obj.getLicenseRevTime(), DateUtil.FMT_TYPE2));
				map.put("cancelTime", DateUtil.getDateByFmt(obj.getCancelTime(), DateUtil.FMT_TYPE2));
				for(PageTabConfigDto pageTabConfigDto : pageConfigDto.getPageTabConfigDtos()) {
					for (PageTabRegionConfigDto pageTabRegionConfigDto : pageTabConfigDto.getPageTabRegionConfigDtos()) {
						for (PageTabRegionFormConfigDto pageTabRegionFormConfigDto : pageTabRegionConfigDto.getValueList().get(0)) {
							if(pageTabRegionFormConfigDto.getKey().equals("clandBureauUserName")){
								pageTabRegionFormConfigDto.setTypeDepend(pageTabRegionFormConfigDto.getTypeDepend()+"&cityCode="+orderListDto.getCityCode()+"&productCode="+orderListDto.getProductCode()+"&agencyId="+MapUtils.getString(params, "agencyId","1"));
							}
							if("clandBureauName".equals(pageTabRegionFormConfigDto.getKey())) {
								pageTabRegionFormConfigDto.setTypeDepend(pageTabRegionFormConfigDto.getTypeDepend().replace("4403", orderListDto.getCityCode()));
							}
						}
						setSlData(pageTabRegionConfigDto, map);
						if(!isEdit&&"cancellation".equals(orderListDto.getProcessId())){
							//加编辑按钮
							pageTabRegionConfigDto.setAbleEdit(1);
						}
					}
				}
		}
		//注销
		else if("tbl_sl_cancellation_page".equals(pageConfigDto.getPageClass())){
				CancellationDto  obj = new CancellationDto();
				obj.setOrderNo(orderNo);
				obj = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/process/cancellation/v/detail", obj, CancellationDto.class);
				map = BeanToMapUtil.beanToMap(obj);
				map.put("cancelTime", DateUtil.getDateByFmt(obj.getCancelTime(), DateUtil.FMT_TYPE2));
				map.put("transferTime", DateUtil.getDateByFmt(obj.getTransferTime(), DateUtil.FMT_TYPE2));
				for(PageTabConfigDto pageTabConfigDto : pageConfigDto.getPageTabConfigDtos()) {
					for (PageTabRegionConfigDto pageTabRegionConfigDto : pageTabConfigDto.getPageTabRegionConfigDtos()) {
						for (PageTabRegionFormConfigDto pageTabRegionFormConfigDto : pageTabRegionConfigDto.getValueList().get(0)) {
							if(pageTabRegionFormConfigDto.getKey().equals("tlandBureauUserName")){
								pageTabRegionFormConfigDto.setTypeDepend(pageTabRegionFormConfigDto.getTypeDepend()+"&cityCode="+orderListDto.getCityCode()+"&productCode="+orderListDto.getProductCode()+"&agencyId="+MapUtils.getString(params, "agencyId","1"));
							}
							if(pageTabRegionFormConfigDto.getKey().equals("tlandBureauName")||"clandBureauName".equals(pageTabRegionFormConfigDto.getKey())){
								pageTabRegionFormConfigDto.setTypeDepend(pageTabRegionFormConfigDto.getTypeDepend().replace("4403", orderListDto.getCityCode()));
							}
						}
						setSlData(pageTabRegionConfigDto, map);
						if(!isEdit&&"transfer".equals(orderListDto.getProcessId())){
							//加编辑按钮
							pageTabRegionConfigDto.setAbleEdit(1);
						}
					}
				}
		}
		//过户
		else if("tbl_sl_transfer_page".equals(pageConfigDto.getPageClass())){
				TransferDto  obj = new TransferDto();
				obj.setOrderNo(orderNo);
				obj = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/process/transfer/v/detail", obj, TransferDto.class);
				map = BeanToMapUtil.beanToMap(obj);
				map.put("transferTime", DateUtil.getDateByFmt(obj.getTransferTime(), DateUtil.FMT_TYPE2));
				map.put("newlicenseTime", DateUtil.getDateByFmt(obj.getNewlicenseTime(), DateUtil.FMT_TYPE2));
				for(PageTabConfigDto pageTabConfigDto : pageConfigDto.getPageTabConfigDtos()) {
					for (PageTabRegionConfigDto pageTabRegionConfigDto : pageTabConfigDto.getPageTabRegionConfigDtos()) {
						for (PageTabRegionFormConfigDto pageTabRegionFormConfigDto : pageTabRegionConfigDto.getValueList().get(0)) {
							if(pageTabRegionFormConfigDto.getKey().equals("nlandBureauUserName")){
								pageTabRegionFormConfigDto.setTypeDepend(pageTabRegionFormConfigDto.getTypeDepend()+"&cityCode="+orderListDto.getCityCode()+"&productCode="+orderListDto.getProductCode()+"&agencyId="+MapUtils.getString(params, "agencyId","1"));
							}
							if("nlandBureauName".equals(pageTabRegionFormConfigDto.getKey())||"tlandBureauName".equals(pageTabRegionFormConfigDto.getKey())) {
								pageTabRegionFormConfigDto.setTypeDepend(pageTabRegionFormConfigDto.getTypeDepend().replace("4403", orderListDto.getCityCode()));
							}
						}
						setSlData(pageTabRegionConfigDto, map);
						if(!isEdit&&"newlicense".equals(orderListDto.getProcessId())){
							//加编辑按钮
							pageTabRegionConfigDto.setAbleEdit(1);
						}
					}
				}
		}
		//领新证
		else if("tbl_sl_newlicense_page".equals(pageConfigDto.getPageClass())){
				NewlicenseDto  obj = new NewlicenseDto();
				obj.setOrderNo(orderNo);
				obj = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/process/newlicense/v/detail", obj, NewlicenseDto.class);
				map = BeanToMapUtil.beanToMap(obj);
				map.put("newlicenseTime", DateUtil.getDateByFmt(obj.getNewlicenseTime(), DateUtil.FMT_TYPE2));
				map.put("mortgageTime", DateUtil.getDateByFmt(obj.getMortgageTime(), DateUtil.FMT_TYPE2));
				for(PageTabConfigDto pageTabConfigDto : pageConfigDto.getPageTabConfigDtos()) {
					for (PageTabRegionConfigDto pageTabRegionConfigDto : pageTabConfigDto.getPageTabRegionConfigDtos()) {
						for (PageTabRegionFormConfigDto pageTabRegionFormConfigDto : pageTabRegionConfigDto.getValueList().get(0)) {
							if(pageTabRegionFormConfigDto.getKey().equals("mlandBureauUserName")){
								pageTabRegionFormConfigDto.setTypeDepend(pageTabRegionFormConfigDto.getTypeDepend()+"&cityCode="+orderListDto.getCityCode()+"&productCode="+orderListDto.getProductCode()+"&agencyId="+MapUtils.getString(params, "agencyId","1"));
							}
							if(pageTabRegionFormConfigDto.getKey().equals("mlandBureauName")||"nlandBureauName".equals(pageTabRegionFormConfigDto.getKey())){
								pageTabRegionFormConfigDto.setTypeDepend(pageTabRegionFormConfigDto.getTypeDepend().replace("4403", orderListDto.getCityCode()));
							}
						}
						setSlData(pageTabRegionConfigDto, map);
						if(!isEdit&&("mortgage".equals(orderListDto.getProcessId())||"receivableForFirst".equals(orderListDto.getProcessId()))){
							//加编辑按钮
							pageTabRegionConfigDto.setAbleEdit(1);
						}
					}
				}
		}
		//抵押
		else if("tbl_sl_mortgage_page".equals(pageConfigDto.getPageClass())){
				MortgageDto  obj = new MortgageDto();
				obj.setOrderNo(orderNo);
				obj = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/process/mortgage/v/detail", obj, MortgageDto.class);
				map = BeanToMapUtil.beanToMap(obj);
				map.put("mortgageTime", DateUtil.getDateByFmt(obj.getMortgageTime(), DateUtil.FMT_TYPE2));
				for(PageTabConfigDto pageTabConfigDto : pageConfigDto.getPageTabConfigDtos()) {
					for (PageTabRegionConfigDto pageTabRegionConfigDto : pageTabConfigDto.getPageTabRegionConfigDtos()) {
						for (PageTabRegionFormConfigDto pageTabRegionFormConfigDto : pageTabRegionConfigDto.getValueList().get(0)) {
							if(pageTabRegionFormConfigDto.getKey().equals("mlandBureauName")){
								pageTabRegionFormConfigDto.setTypeDepend(pageTabRegionFormConfigDto.getTypeDepend().replace("4403", orderListDto.getCityCode()));
							}
						}
						setSlData(pageTabRegionConfigDto, map);
						if(!isEdit&&("receivableFor".equals(orderListDto.getProcessId())||"receivableForEnd".equals(orderListDto.getProcessId()))){
							//加编辑按钮
							pageTabRegionConfigDto.setAbleEdit(1);
						}
					}
				}
		}
		//财务审核
		else if("tbl_sl_financialAudit_page".equals(pageConfigDto.getPageClass())){
//				AuditDto  obj = new AuditDto();
//				obj.setOrderNo(orderNo);
//				obj = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/finance/audit/v/detail", obj, AuditDto.class);
				//要件
				DocumentsDto documentsDto = new DocumentsDto();
				documentsDto.setOrderNo(orderNo);
				DocumentsDto documents= httpUtil.getObject(Constants.LINK_CREDIT, "/credit/element/basics/v/detail", documentsDto,DocumentsDto.class);
				if(documents!=null){
					ForeclosureTypeDto foreclosureTypeDto  = documents.getForeclosureType();//出款方式
					if(foreclosureTypeDto!=null){
						map.put("lendingBank", foreclosureTypeDto.getBankName());
						map.put("lendingBankId", foreclosureTypeDto.getBankNameId());
						map.put("lendingBankSub", foreclosureTypeDto.getBankSubName());
						map.put("lendingBankSubId", foreclosureTypeDto.getBankSubNameId());
						map.put("bankName", foreclosureTypeDto.getBankCardMaster());
						map.put("bankAccount", foreclosureTypeDto.getBankNo());
						map.put("bankCardMaster", foreclosureTypeDto.getBankCardMaster());
						map.put("bankNo", foreclosureTypeDto.getBankNo());
					}
				}
				
				ReportDto reportDto = null;
				try {
					log.info("查询出款报备orderNo:"+orderNo);
					Map<String,Object> mm = new HashMap<String, Object>();
					mm.put("orderNo", orderNo);
					reportDto = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/finance/report/v/detailByStatus", mm, ReportDto.class);
					log.info("出款报备对象："+reportDto);
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					ApplyLoanDto applyLoanDto = new ApplyLoanDto();
					applyLoanDto.setOrderNo(orderNo);
					RespDataObject<Map<String, Object>> resp = httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/finance/applyLoan/v/detail", applyLoanDto, Map.class);
					Map<String,Object> m= new HashMap<String, Object>();
					if(resp.getCode().equals(RespStatusEnum.SUCCESS.getCode())){
						Map<String,Object> data = resp.getData();
						if(data!=null){
							JSONObject json = JSONObject.fromObject(data.get("loanDto"));
							if(json!=null){
								m = JsonUtil.jsonToMap(json.toString());
							}
						}
					}
				
					Map<String,Object> reportDtoMap = BeanToMapUtil.beanToMap(reportDto);//出款报备
					Map<String,Object> baseMap = BeanToMapUtil.beanToMap(orderListDto);//订单基本信息
					map.putAll(baseMap);
					if(m!=null){
						map.putAll(m);//申请放款信息
					}
					map.putAll(reportDtoMap);
					String planPaymentTime="-";
					if(StringUtils.isNotBlank(orderListDto.getPlanPaymentTime())){
						planPaymentTime = orderListDto.getPlanPaymentTime().replace(".0", "");
					}
					map.put("planPaymentTime", planPaymentTime);
					if(StringUtils.isBlank(MapUtils.getString(map, "bankName"))){
						map.put("bankName", "-");
					}
					if(StringUtils.isBlank(MapUtils.getString(map, "bankAccount"))){
						map.put("bankAccount", "-");
					}
					if(StringUtils.isBlank(MapUtils.getString(map, "lendingBank"))){
						map.put("lendingBank", "-");
					}
					if(StringUtils.isBlank(MapUtils.getString(map, "lendingBankSub"))){
						map.put("lendingBankSub", "-");
					}
					if(StringUtils.isBlank(MapUtils.getString(map, "financeOutLoanTime"))){
						map.put("financeOutLoanTime", "-");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				//最后一条流水
				OrderFlowDto orderFlowDto = new OrderFlowDto();
				orderFlowDto.setOrderNo(orderNo);
				orderFlowDto = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/order/flow/v/selectEndOrderFlow", orderFlowDto,OrderFlowDto.class);
				List<PageTabRegionFormConfigDto> formList = new ArrayList<PageTabRegionFormConfigDto>();
				PageTabRegionFormConfigDto pageTabRegionFormConfig;
				for(PageTabConfigDto pageTabConfigDto : pageConfigDto.getPageTabConfigDtos()) {
					for (PageTabRegionConfigDto pageTabRegionConfigDto : pageTabConfigDto.getPageTabRegionConfigDtos()) {
						for (PageTabRegionFormConfigDto pageTabRegionFormConfigDto : pageTabRegionConfigDto.getValueList().get(0)) {
							if(pageTabRegionFormConfigDto.getTitle().equals("备注")){
								formList.add(pageTabRegionFormConfigDto);
								pageTabRegionFormConfig = new PageTabRegionFormConfigDto(103, "通过","pass", "/credit/order/audit/v/processSubmit", "/credit/user/user/v/searchByType2?name=放款&title=选择放款人员&cityCode="+orderListDto.getCityCode()+"&cityName="+orderListDto.getCityName()+"&productCode="+MapUtils.getString(params, "productCode"));
								if(orderFlowDto!=null&&orderFlowDto.getIsNewWalkProcess()==2){//不重新走流程
									pageTabRegionFormConfigDto.setIsReadOnly(2);
								}
								formList.add(pageTabRegionFormConfig);
								//退回
								if(!(orderFlowDto!=null&&StringUtils.isNotBlank(orderFlowDto.getBackReason()))){
									pageTabRegionFormConfig = new PageTabRegionFormConfigDto(106, "不通过","repulse", "/credit/order/flow/v/backOrder", null);
									formList.add(pageTabRegionFormConfig);
								}
							}else{
								formList.add(pageTabRegionFormConfigDto);
							}
						}
						pageTabRegionConfigDto.getValueList().set(0, formList);
						setSlData(pageTabRegionConfigDto, map);
					}
				}
		}
		//房抵贷财务审核
		else if("tbl_fdd_financialAudit_page".equals(pageConfigDto.getPageClass())) {
			//要件
			/*DocumentsDto documentsDto = new DocumentsDto();
			documentsDto.setOrderNo(orderNo);
			DocumentsDto documents= httpUtil.getObject(Constants.LINK_CREDIT, "/credit/element/basics/v/detail", documentsDto,DocumentsDto.class);
			if(documents!=null){
				ForeclosureTypeDto foreclosureTypeDto  = documents.getForeclosureType();//出款方式
				if(foreclosureTypeDto!=null){
					map.put("lendingBank", foreclosureTypeDto.getBankName());
					map.put("lendingBankId", foreclosureTypeDto.getBankNameId());
					map.put("lendingBankSub", foreclosureTypeDto.getBankSubName());
					map.put("lendingBankSubId", foreclosureTypeDto.getBankSubNameId());
					map.put("bankName", foreclosureTypeDto.getBankCardMaster());
					map.put("bankAccount", foreclosureTypeDto.getBankNo());
					map.put("bankCardMaster", foreclosureTypeDto.getBankCardMaster());
					map.put("bankNo", foreclosureTypeDto.getBankNo());
				}
			}*/
			
			ReportDto reportDto = null;
			try {
				log.info("查询出款报备orderNo:"+orderNo);
				Map<String,Object> mm = new HashMap<String, Object>();
				mm.put("orderNo", orderNo);
				reportDto = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/finance/report/v/detailByStatus", mm, ReportDto.class);
				log.info("出款报备对象："+reportDto);
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				ApplyLoanDto applyLoanDto = new ApplyLoanDto();
				applyLoanDto.setOrderNo(orderNo);
				RespDataObject<Map<String, Object>> resp = httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/finance/applyLoan/v/detail", applyLoanDto, Map.class);
				Map<String,Object> m= new HashMap<String, Object>();
				if(resp.getCode().equals(RespStatusEnum.SUCCESS.getCode())){
					Map<String,Object> data = resp.getData();
					if(data!=null){
						JSONObject json = JSONObject.fromObject(data.get("loanDto"));
						if(json!=null){
							m = JsonUtil.jsonToMap(json.toString());
						}
					}
				}
			
				Map<String,Object> reportDtoMap = BeanToMapUtil.beanToMap(reportDto);//出款报备
				Map<String,Object> baseMap = BeanToMapUtil.beanToMap(orderListDto);//订单基本信息
				map.putAll(baseMap);
				if(m!=null){
					map.putAll(m);//申请放款信息
				}
				map.putAll(reportDtoMap);
				//查询账户信息中的放款信息
				OrderBaseHouseLendingDto houseLending = new OrderBaseHouseLendingDto();
				houseLending.setOrderNo(orderNo);
				houseLending = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/order/house/lending/v/query", houseLending,OrderBaseHouseLendingDto.class);
				
				if(StringUtils.isBlank(houseLending.getLendingBankName())){
					map.put("lendingBank", "-");
				}else {
					map.put("lendingBank", houseLending.getLendingBankName());
				}
				if(StringUtils.isBlank(houseLending.getLendingBankBranchName())){
					map.put("lendingBankSub", "-");
				}else {
					map.put("lendingBankSub", houseLending.getLendingBankBranchName());
				}
				if(StringUtils.isBlank(houseLending.getBankUserName())){
					map.put("bankUserName", "-");
				}else {
					map.put("bankUserName", houseLending.getBankUserName());
				}
				if(StringUtils.isBlank(houseLending.getBankAccount())){
					map.put("bankAccount", "-");
				}else {
					map.put("bankAccount", houseLending.getBankAccount());
				}
				map.put("accountType", "个人借记卡账户");
				map.put("accountUse", "放款账户");
				//客户信息
				OrderBaseCustomerDto customer = new OrderBaseCustomerDto();
				if(isRelationC){
					customer.setOrderNo(orderListDto.getRelationOrderNo()); 
				}else{
					customer.setOrderNo(orderNo); 
				}
				customer = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/order/customer/v/query", customer,OrderBaseCustomerDto.class);
				map.put("customerCardType", customer.getCustomerCardType());
				map.put("customerCardNumber", customer.getCustomerCardNumber());
				map.put("lendingPhoneNumber", houseLending.getLendingPhoneNumber());
			} catch (Exception e) {
				e.printStackTrace();
			}
			//最后一条流水
			OrderFlowDto orderFlowDto = new OrderFlowDto();
			orderFlowDto.setOrderNo(orderNo);
			orderFlowDto = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/order/flow/v/selectEndOrderFlow", orderFlowDto,OrderFlowDto.class);
			List<PageTabRegionFormConfigDto> formList = new ArrayList<PageTabRegionFormConfigDto>();
			PageTabRegionFormConfigDto pageTabRegionFormConfig;
			for(PageTabConfigDto pageTabConfigDto : pageConfigDto.getPageTabConfigDtos()) {
				for (PageTabRegionConfigDto pageTabRegionConfigDto : pageTabConfigDto.getPageTabRegionConfigDtos()) {
					for (PageTabRegionFormConfigDto pageTabRegionFormConfigDto : pageTabRegionConfigDto.getValueList().get(0)) {
						if(pageTabRegionFormConfigDto.getTitle().equals("放款期限")) {
							pageTabRegionFormConfigDto.setSingle("期");
							formList.add(pageTabRegionFormConfigDto);
						}
						else if(pageTabRegionFormConfigDto.getTitle().equals("备注")){
							formList.add(pageTabRegionFormConfigDto);
							pageTabRegionFormConfig = new PageTabRegionFormConfigDto(103, "通过","pass", "/credit/order/audit/v/processSubmit", "/credit/user/user/v/searchByType2?name=放款&title=选择放款人员&cityCode="+orderListDto.getCityCode()+"&cityName="+orderListDto.getCityName()+"&productCode="+MapUtils.getString(params, "productCode"));
							if(orderFlowDto!=null&&orderFlowDto.getIsNewWalkProcess()==2){//不重新走流程
								pageTabRegionFormConfigDto.setIsReadOnly(2);
							}
							formList.add(pageTabRegionFormConfig);
							//退回
							if(!(orderFlowDto!=null&&StringUtils.isNotBlank(orderFlowDto.getBackReason()))){
								pageTabRegionFormConfig = new PageTabRegionFormConfigDto(106, "不通过","repulse", "/credit/order/flow/v/backOrder", null);
								formList.add(pageTabRegionFormConfig);
							}
						}else{
							formList.add(pageTabRegionFormConfigDto);
						}
					}
					pageTabRegionConfigDto.getValueList().set(0, formList);
					setSlData(pageTabRegionConfigDto, map);
				}
			}
		}		
		//要件退还
		else if("tbl_sl_elementReturn_page".equals(pageConfigDto.getPageClass())){
			DocumentsReturnDto obj = new DocumentsReturnDto();
			obj.setOrderNo(orderNo);
			obj = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/element/return/v/detail", obj, DocumentsReturnDto.class);
			if(null!=obj){
				map = BeanToMapUtil.beanToMap(obj);
				map.put("returnTime", DateUtil.getDateByFmt(obj.getReturnTime(), DateUtil.FMT_TYPE2));
			}
			
			//借款信息
			OrderBaseBorrowDto borrow = new OrderBaseBorrowDto();
			borrow.setOrderNo(orderNo);
			borrow = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/order/borrow/v/query", borrow,OrderBaseBorrowDto.class);
			LendingHarvestDto  lendingHarvestDto= new LendingHarvestDto();
			lendingHarvestDto.setOrderNo(orderNo);
			lendingHarvestDto.setType(borrow.getPaymentMethod());//收利息
			Map<String,Object> harvestMap = new HashMap<String, Object>();
			Map<String,Object> m = (Map<String,Object>)httpUtil.getData(Constants.LINK_CREDIT, "/credit/finance/lendingHarvest/v/detail", lendingHarvestDto);
			if(m.get("data")!=null){
				Map<String,Object> data = MapUtils.getMap(m, "data");
				if(data.get("harvest")!=null){
					harvestMap = MapUtils.getMap(data, "harvest");
				}
			}
			
			for(PageTabConfigDto pageTabConfigDto : pageConfigDto.getPageTabConfigDtos()) {
				for (PageTabRegionConfigDto pageTabRegionConfigDto : pageTabConfigDto.getPageTabRegionConfigDtos()) {
					setSlData(pageTabRegionConfigDto, map);
					if(!isEdit&&"rebate".equals(orderListDto.getProcessId())){
						//加编辑按钮
						pageTabRegionConfigDto.setAbleEdit(1);
					}
				}
			}
			ReceivablePayDto oo = new ReceivablePayDto();
			oo.setOrderNo(orderNo);
			oo = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/finance/receivablePay/v/detail", oo, ReceivablePayDto.class);
			//有返佣
			if((null!=harvestMap.get("returnMoney")&&MapUtils.getInteger(harvestMap, "returnMoney")>0)
					||(oo!=null&&oo.getRebateMoney()!=null&&oo.getRebateMoney().doubleValue()>0)){
				List<PageTabRegionFormConfigDto> formList = new ArrayList<PageTabRegionFormConfigDto>();
				PageTabRegionFormConfigDto pageTabRegionFormConfig;
				for(PageTabConfigDto pageTabConfigDto : pageConfigDto.getPageTabConfigDtos()) {
					for (PageTabRegionConfigDto pageTabRegionConfigDto : pageTabConfigDto.getPageTabRegionConfigDtos()) {
						for (PageTabRegionFormConfigDto pageTabRegionFormConfigDto : pageTabRegionConfigDto.getValueList().get(0)) {
							if(pageTabRegionFormConfigDto.getTitle().equals("上传凭证")){
								formList.add(pageTabRegionFormConfigDto);
								pageTabRegionFormConfig = new PageTabRegionFormConfigDto(3, "返佣专员", "handleName", null, null);
								pageTabRegionFormConfig.setSpecialKey("handleUid");
								pageTabRegionFormConfig.setTypeDepend("/credit/user/user/v/searchByType2?name=返佣&cityCode="+borrow.getCityCode()+"&productCode="+borrow.getProductCode());
								formList.add(pageTabRegionFormConfig);
							}else{
								formList.add(pageTabRegionFormConfigDto);
							}
						}
						pageTabRegionConfigDto.getValueList().set(0, formList);
					}
				}
			}
		}
		//出款报备
		else if("tbl_sl_editreport_page".equals(pageConfigDto.getPageClass())
				||"tbl_fdd_editreport_page".equals(pageConfigDto.getPageClass())){
				ReportDto obj = null;
				try {
					log.info("出款报备orderNo:"+MapUtils.getString(params, "orderNo"));
					Map<String,Object> mm = new HashMap<String, Object>();
					mm.put("orderNo", MapUtils.getString(params, "orderNo"));
					obj = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/finance/report/v/detailByStatus", mm, ReportDto.class);
					log.info("出款报备对象："+obj);
				} catch (Exception e) {
					e.printStackTrace();
				}

				if(null!=obj){
					if(StringUtils.isNotBlank(obj.getReportEditRecordStr())){
						obj.setEditrecord(obj.getReportEditRecordStr());
					}
					if(StringUtils.isNotBlank(obj.getReportReplyRecordStr())){
						obj.setReplyrecord(obj.getReportReplyRecordStr());
					}

				} else if(null==obj&&StringUtils.isNotBlank(orderNo)){
					obj = getReport(orderListDto);
				}

			    boolean isReportEdit = (null==obj);

				map = BeanToMapUtil.beanToMap(obj);
				if(obj!=null&&obj.getEstimateOutLoanTime()!=null){
					map.put("estimateOutLoanTimeStr", DateUtil.getDateByFmt(obj.getEstimateOutLoanTime(), DateUtil.FMT_TYPE11));
				}
				if(null!=obj&&null!=obj.getFinanceOutLoanTime()){
					map.put("financeOutLoanTimeStr", DateUtil.getDateByFmt(obj.getFinanceOutLoanTime(), DateUtil.FMT_TYPE11));
				}
				if(null!=obj&&null!=obj.getSort()&&obj.getSort()>0){
					map.put("outLoanSort","当前排位：第"+obj.getSort()+"位");
				} else if(null!=obj&&null!=obj.getStatus()&&1==obj.getStatus()){
					map.put("outLoanSort","已出款");
				}
				//渠道经理，合作机构
				if(orderListDto == null){
					orderListDto = new OrderListDto();
				}
				if(obj == null){
					log.info("出款报备，取列表渠道经理："+orderListDto.getChannelManagerName());
					map.put("channelManagerUid", orderListDto.getChannelManagerUid());
					map.put("channelManagerName", orderListDto.getChannelManagerName());
					map.put("cooperativeAgencyId", orderListDto.getCooperativeAgencyId());
					map.put("cooperativeAgencyName", orderListDto.getCooperativeAgencyName());
					//受理员
					map.put("acceptMemberUid", MapUtils.getString(params, "uid"));
					UserDto u = CommonDataUtil.getUserDtoByUidAndMobile(MapUtils.getString(params, "uid"));
					map.put("acceptMemberName", u.getName());
				}
				for(PageTabConfigDto pageTabConfigDto : pageConfigDto.getPageTabConfigDtos()) {
					for (PageTabRegionConfigDto pageTabRegionConfigDto : pageTabConfigDto.getPageTabRegionConfigDtos()) {
						setSlData(pageTabRegionConfigDto, map);
					}
				}
				
				for(PageTabConfigDto pageTabConfigDto : pageConfigDto.getPageTabConfigDtos()) {
					for (PageTabRegionConfigDto pageTabRegionConfigDto : pageTabConfigDto.getPageTabRegionConfigDtos()) {
						List<PageTabRegionFormConfigDto> formList = new ArrayList<PageTabRegionFormConfigDto>();
						for (PageTabRegionFormConfigDto pageTabRegionFormConfigDto  : pageTabRegionConfigDto.getValueList().get(0)) {

							if((!isReportEdit||StringUtils.isNotBlank(orderNo))
									&&!pageTabRegionFormConfigDto.getKey().equals("estimateOutLoanTimeStr")
									&&!"editrecord".equals(pageTabRegionFormConfigDto.getKey())
									&&!"replyrecord".equals(pageTabRegionFormConfigDto.getKey())){
								if(("paymentType".equals(pageTabRegionFormConfigDto.getKey())
										||"customerTypeName".equals(pageTabRegionFormConfigDto.getKey()))
										&&StringUtils.isBlank(pageTabRegionFormConfigDto.getValue())){
								} else if(!(pageTabRegionFormConfigDto.getKey().equals("remark")||pageTabRegionFormConfigDto.getKey().equals("paymentType"))){
									pageTabRegionFormConfigDto.setType(1);
									pageTabRegionFormConfigDto.setIsReadOnly(2);
								}
							}
							if("fundExamine".equals(pageTabRegionFormConfigDto.getKey())
								&&StringUtils.isBlank(pageTabRegionFormConfigDto.getValue())){
								pageTabRegionFormConfigDto.setValue(null);
							}
							//机构渠道经理不可修改,设置为机构渠道经理
							/*
							if(null!=orderListDto
									&&orderListDto.getAgencyId()>1
									&&pageTabRegionFormConfigDto.getKey().equals("channelManagerName")){
								pageTabRegionFormConfigDto.setType(1);
								pageTabRegionFormConfigDto.setIsReadOnly(2);
								String agencyChanlManUid = user.getAgencyChanlManUid();
								pageTabRegionFormConfigDto.setSpecialValue(agencyChanlManUid);
								String channelManagerName = CommonDataUtil.getUserDtoByUidAndMobile(agencyChanlManUid).getName();
								log.info("出款报备，取用户所属机构渠道经理Uid："+user.getAgencyChanlManUid());
								log.info("出款报备，取用户所属机构渠道经理："+channelManagerName);
								pageTabRegionFormConfigDto.setValue(channelManagerName);
							}*/
							//修改与回复记录为则不显示
							if((obj==null||StringUtils.isBlank(obj.getEditrecord()))&&"editrecord".equals(pageTabRegionFormConfigDto.getKey())){
								continue;
							}else if((obj==null||StringUtils.isBlank(obj.getReplyrecord()))&&"replyrecord".equals(pageTabRegionFormConfigDto.getKey())){
								continue;
							}else{
								formList.add(pageTabRegionFormConfigDto);
							}
						}
						pageTabRegionConfigDto.getValueList().set(0, formList);
					}
				}
		}
		//PC端操作节点详情
		//分配订单
		else if("tbl_sl_managerAudit_page".equals(pageConfigDto.getPageClass())){
			ManagerAuditDto obj = new ManagerAuditDto();
			obj.setOrderNo(orderNo);
			obj = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/process/managerAudit/v/detail", obj, ManagerAuditDto.class);
			map = BeanToMapUtil.beanToMap(obj);
			//时间格式化
			map.put("auditTime", DateUtil.getDateByFmt(obj.getAuditTime(), DateUtil.FMT_TYPE2));
			for(PageTabConfigDto pageTabConfigDto : pageConfigDto.getPageTabConfigDtos()) {
				for (PageTabRegionConfigDto pageTabRegionConfigDto : pageTabConfigDto.getPageTabRegionConfigDtos()) {
					setSlData(pageTabRegionConfigDto, map);
				}
			}
		}
		//风控初审
		else if("tbl_sl_auditFirst_page".equals(pageConfigDto.getPageClass())){
			FirstAuditDto obj = new FirstAuditDto();
			obj.setOrderNo(orderNo);
			obj = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/risk/first/v/detail", obj, FirstAuditDto.class);
			map = BeanToMapUtil.beanToMap(obj);
			if("1".equals(MapUtils.getString(map, "auditStatus"))){
				map.put("auditStatus", "通过");
			}else if("2".equals(MapUtils.getString(map, "auditStatus"))){
				map.put("auditStatus", "退回");
			}else if("3".equals(MapUtils.getString(map, "auditStatus"))){
				map.put("auditStatus", "上报终审");
			}
			map.put("auditTime", DateUtil.getDateByFmt(obj.getAuditTime(), DateUtil.FMT_TYPE2));
			for(PageTabConfigDto pageTabConfigDto : pageConfigDto.getPageTabConfigDtos()) {
				for (PageTabRegionConfigDto pageTabRegionConfigDto : pageTabConfigDto.getPageTabRegionConfigDtos()) {
					setSlData(pageTabRegionConfigDto, map);
				}
			}
		}
		//法务审批
		else if("tbl_sl_auditJustice_page".equals(pageConfigDto.getPageClass())){
			JusticeAuditDto obj = new JusticeAuditDto();
			obj.setOrderNo(orderNo);
			obj = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/risk/justice/v/detail", obj, JusticeAuditDto.class);
			map = BeanToMapUtil.beanToMap(obj);
			map.put("auditTime", DateUtil.getDateByFmt(obj.getAuditTime(), DateUtil.FMT_TYPE2));
			if("1".equals(MapUtils.getString(map, "auditStatus"))){
				map.put("auditStatus", "通过");
			}else if("2".equals(MapUtils.getString(map, "auditStatus"))){
				map.put("auditStatus", "未通过");
			}
			for(PageTabConfigDto pageTabConfigDto : pageConfigDto.getPageTabConfigDtos()) {
				for (PageTabRegionConfigDto pageTabRegionConfigDto : pageTabConfigDto.getPageTabRegionConfigDtos()) {
					setSlData(pageTabRegionConfigDto, map);
				}
			}
		}
		//分配资金方
		else if("tbl_sl_allocationFund_page".equals(pageConfigDto.getPageClass())){
			AllocationFundDto obj = new AllocationFundDto();
			obj.setOrderNo(orderNo);
			List<AllocationFundDto> list = httpUtil.getList(Constants.LINK_CREDIT, "/credit/risk/allocationfund/v/detail", obj, AllocationFundDto.class);
			String fundCode = "";
			Double loanAmount = 0d;
			for(AllocationFundDto tmp:list){
				loanAmount += tmp.getLoanAmount();
				fundCode += tmp.getFundDesc()+",";
			}
			if(StringUtils.isNotBlank(fundCode)){
				fundCode = fundCode.substring(0,fundCode.length()-1);
			} else {
				fundCode = "-";
			}
			map = BeanToMapUtil.beanToMap(obj);
			map.put("fundCode", fundCode);
			map.put("loanAmount", loanAmount);//配置加单位（万）
			if(list!=null&&list.size()>0){
				map.put("remark", list.get(0).getRemark());
			}
			for(PageTabConfigDto pageTabConfigDto : pageConfigDto.getPageTabConfigDtos()) {
				for (PageTabRegionConfigDto pageTabRegionConfigDto : pageTabConfigDto.getPageTabRegionConfigDtos()) {
					setSlData(pageTabRegionConfigDto, map);
				}
			}
		}
		//指派还款专员
		else if("tbl_sl_repaymentMember_page".equals(pageConfigDto.getPageClass())){
			DistributionMemberDto obj = new DistributionMemberDto();
			obj.setOrderNo(orderNo);
			obj = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/process/distributionMember/v/detail", obj, DistributionMemberDto.class);
			map = BeanToMapUtil.beanToMap(obj);
			map.put("distributionTime", DateUtil.getDateByFmt(obj.getDistributionTime(), DateUtil.FMT_TYPE2));
			for(PageTabConfigDto pageTabConfigDto : pageConfigDto.getPageTabConfigDtos()) {
				for (PageTabRegionConfigDto pageTabRegionConfigDto : pageTabConfigDto.getPageTabRegionConfigDtos()) {
					for (PageTabRegionFormConfigDto pageTabRegionFormConfigDto : pageTabRegionConfigDto.getValueList().get(0)) {
						if(pageTabRegionFormConfigDto.getKey().equals("foreclosureMemberName")){
							pageTabRegionFormConfigDto.setTypeDepend(pageTabRegionFormConfigDto.getTypeDepend()+"&cityCode="+orderListDto.getCityCode()+"&productCode="+orderListDto.getProductCode()+"&agencyId="+MapUtils.getString(params, "agencyId","1"));
						}
					}
					setSlData(pageTabRegionConfigDto, map);
				}
			}
		}
		//要件校验
		else if("tbl_sl_element_page".equals(pageConfigDto.getPageClass())){
			DocumentsDto obj = new DocumentsDto();
			obj.setOrderNo(orderNo);
			obj = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/element/basics/v/detail", obj, DocumentsDto.class);
			map = BeanToMapUtil.beanToMap(obj);
			if("2".equals(MapUtils.getString(map, "status"))){
				map.put("status", "通过钉钉特批");
			}else if("3".equals(MapUtils.getString(map, "status"))){
				map.put("status", "通过系统校验");
			}
			for(PageTabConfigDto pageTabConfigDto : pageConfigDto.getPageTabConfigDtos()) {
				for (PageTabRegionConfigDto pageTabRegionConfigDto : pageTabConfigDto.getPageTabRegionConfigDtos()) {
					setSlData(pageTabRegionConfigDto, map);
				}
			}
		}
		//收利息
		else if("tbl_sl_lendingHarvest_page".equals(pageConfigDto.getPageClass())){
			OrderBaseBorrowDto borrow = new OrderBaseBorrowDto();
			borrow.setOrderNo(orderNo);
			borrow = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/order/borrow/v/query", borrow,OrderBaseBorrowDto.class);
			LendingHarvestDto obj = new LendingHarvestDto();
			obj.setOrderNo(orderNo);
			obj.setType(1);//收利息
			Map<String,Object> m = (Map<String,Object>)httpUtil.getData(Constants.LINK_CREDIT, "/credit/finance/lendingHarvest/v/detail", obj);
			Map<String,Object> data = MapUtils.getMap(m, "data");
			map = MapUtils.getMap(data, "harvest");
			String imgs = cleanImgUrl(MapUtils.getString(map, "interestImg"));
			List<PageTabRegionFormConfigDto> formList = new ArrayList<PageTabRegionFormConfigDto>();
			PageTabRegionFormConfigDto pageTabRegionFormConfigDto;
			for(PageTabConfigDto pageTabConfigDto : pageConfigDto.getPageTabConfigDtos()) {
				for (PageTabRegionConfigDto pageTabRegionConfigDto : pageTabConfigDto.getPageTabRegionConfigDtos()) {
					pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1,"收费类型","riskGrade",borrow.getRiskGrade()==null?"-":borrow.getRiskGrade());
					formList.add(pageTabRegionFormConfigDto);
					pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1,"费率","rate",borrow.getRate()+"%");
					formList.add(pageTabRegionFormConfigDto);
					pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1,"逾期费率","overdueRate",borrow.getOverdueRate()+"%");
					formList.add(pageTabRegionFormConfigDto);
					pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1,"收费金额","chargeMoney",borrow.getChargeMoney()+"元");
					formList.add(pageTabRegionFormConfigDto);
					pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1,"服务费","serviceCharge",borrow.getServiceCharge()+"元");
					formList.add(pageTabRegionFormConfigDto);
					Date date = new Date(MapUtils.getLongValue(map, "interestTime"));
					pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1,"收利息时间","interestTime",DateUtil.getDateByFmt(date, DateUtil.FMT_TYPE2));
					formList.add(pageTabRegionFormConfigDto);
					pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1,"实收利息","collectInterestMoney",MapUtils.getString(map, "collectInterestMoney")+"元");
					formList.add(pageTabRegionFormConfigDto);
					pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1,"返佣金额","returnMoney",MapUtils.getString(map, "returnMoney")+"元");
					formList.add(pageTabRegionFormConfigDto);
					pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1,"应收利息","receivableInterestMoney",MapUtils.getString(map, "receivableInterestMoney")+"元");
					formList.add(pageTabRegionFormConfigDto);
					pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(6,"备注","remark",MapUtils.getString(map, "remark"));
					formList.add(pageTabRegionFormConfigDto);
					pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(110,null,"imgs",imgs);
					formList.add(pageTabRegionFormConfigDto);
					pageTabRegionConfigDto.getValueList().set(0, formList);
				}
			}
		}
		//付利息
		else if("tbl_sl_lendingPay_page".equals(pageConfigDto.getPageClass())){
			LendingPayDto obj = new LendingPayDto();
			obj.setOrderNo(orderNo);
			obj = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/finance/lendingPay/v/detail", obj, LendingPayDto.class);
			map = BeanToMapUtil.beanToMap(obj);
			map.put("payTime", DateUtil.getDateByFmt(obj.getPayTime(), DateUtil.FMT_TYPE2));
			String imgs = cleanImgUrl(obj.getPayImg());
			map.put("imgs", imgs);
			for(PageTabConfigDto pageTabConfigDto : pageConfigDto.getPageTabConfigDtos()) {
				for (PageTabRegionConfigDto pageTabRegionConfigDto : pageTabConfigDto.getPageTabRegionConfigDtos()) {
					setSlData(pageTabRegionConfigDto, map);
				}
			}
		}
		//发放款指令
		else if("tbl_sl_lendingInstructions_page".equals(pageConfigDto.getPageClass())){
			LendingInstructionsDto  obj = new LendingInstructionsDto();
			obj.setOrderNo(orderNo);

			JSONObject jsons = httpUtil.getData(Constants.LINK_CREDIT, "/credit/finance/lendingInstruction/v/detail", obj);
			RespDataObject<LinkedTreeMap<String,Object>> result = new Gson().fromJson(jsons.toString(), RespDataObject.class);
			
			if(RespStatusEnum.SUCCESS.getCode().equals(result.getCode())){
				LinkedTreeMap<String,Object> m = result.getData();
				if(m.containsKey("instructionsDto")){
					map = (Map<String,Object>)m.get("instructionsDto");
				}
			}
			//放款账户，银行
			map.put("lendingBank", MapUtils.getString(map,"lendingBank","")+"-"+MapUtils.getString(map,"openingBank",""));
			String imgs = cleanImgUrl(MapUtils.getString(map, "chargesReceivedImg", ""));
			map.put("imgs", imgs);
			for(PageTabConfigDto pageTabConfigDto : pageConfigDto.getPageTabConfigDtos()) {
				for (PageTabRegionConfigDto pageTabRegionConfigDto : pageTabConfigDto.getPageTabRegionConfigDtos()) {
					setSlData(pageTabRegionConfigDto, map);
				}
			}
		}
		//放款
		else if("tbl_sl_lending_page".equals(pageConfigDto.getPageClass())){
			LendingDto  obj = new LendingDto();
			obj.setOrderNo(orderNo);
			obj = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/finance/lending/v/detail", obj, LendingDto.class);
			map = BeanToMapUtil.beanToMap(obj);
			//放款账户，银行
			map.put("lendingBank", MapUtils.getString(map,"lendingBank","")+"-"+MapUtils.getString(map,"openingBank",""));
			map.put("lendingTime", DateUtil.getDateByFmt(obj.getLendingTime(), DateUtil.FMT_TYPE2));
			String imgs = cleanImgUrl(obj.getLendingImg());
			map.put("imgs", imgs);
			for(PageTabConfigDto pageTabConfigDto : pageConfigDto.getPageTabConfigDtos()) {
				for (PageTabRegionConfigDto pageTabRegionConfigDto : pageTabConfigDto.getPageTabRegionConfigDtos()) {
					setSlData(pageTabRegionConfigDto, map);
				}
			}
		}
		//已扣回后置费用
		else if("tbl_sl_backExpenses_page".equals(pageConfigDto.getPageClass())){
			OrderBaseBorrowDto borrow = new OrderBaseBorrowDto();
			borrow.setOrderNo(orderNo);
			borrow = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/order/borrow/v/query", borrow,OrderBaseBorrowDto.class);
			LendingHarvestDto obj = new LendingHarvestDto();
			obj.setOrderNo(orderNo);
			obj.setType(2);//扣回后置费用
			Map<String,Object> m = (Map<String,Object>)httpUtil.getData(Constants.LINK_CREDIT, "/credit/finance/lendingHarvest/v/detail", obj);
			Map<String,Object> data = MapUtils.getMap(m, "data");
			map = MapUtils.getMap(data, "harvest");
			String imgs = cleanImgUrl(MapUtils.getString(map, "interestImg"));
			List<PageTabRegionFormConfigDto> formList = new ArrayList<PageTabRegionFormConfigDto>();
			PageTabRegionFormConfigDto pageTabRegionFormConfigDto;
			for(PageTabConfigDto pageTabConfigDto : pageConfigDto.getPageTabConfigDtos()) {
				for (PageTabRegionConfigDto pageTabRegionConfigDto : pageTabConfigDto.getPageTabRegionConfigDtos()) {
					pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1,"收费类型","riskGrade",borrow.getRiskGrade()==null?"-":borrow.getRiskGrade());
					formList.add(pageTabRegionFormConfigDto);
					pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1,"费率","rate",borrow.getRate()+"%");
					formList.add(pageTabRegionFormConfigDto);
					pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1,"逾期费率","overdueRate",borrow.getOverdueRate()+"%");
					formList.add(pageTabRegionFormConfigDto);
					pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1,"收费金额","chargeMoney",borrow.getChargeMoney()+"元");
					formList.add(pageTabRegionFormConfigDto);
					pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1,"服务费","serviceCharge",borrow.getServiceCharge()+"元");
					formList.add(pageTabRegionFormConfigDto);
					Date date = new Date(MapUtils.getLongValue(map, "interestTime"));
					pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1,"扣回后置时间","interestTime",DateUtil.getDateByFmt(date, DateUtil.FMT_TYPE2));
					formList.add(pageTabRegionFormConfigDto);
					pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1,"扣回后置费用","backExpensesMoney",MapUtils.getString(map, "backExpensesMoney")+"元");
					formList.add(pageTabRegionFormConfigDto);
					pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(6,"备注","remark",MapUtils.getString(map, "remark"));
					formList.add(pageTabRegionFormConfigDto);
					pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(110,null,"imgs",imgs);
					formList.add(pageTabRegionFormConfigDto);
					pageTabRegionConfigDto.getValueList().set(0, formList);
				}
			}
		}
		//已返佣
		else if("tbl_sl_rebate_page".equals(pageConfigDto.getPageClass())){
			RebateDto obj = new RebateDto();
			obj.setOrderNo(orderNo);
			obj = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/finance/rebate/v/detail", obj,RebateDto.class);
			String imgs = cleanImgUrl(obj.getRebateImg());
			List<PageTabRegionFormConfigDto> formList = new ArrayList<PageTabRegionFormConfigDto>();
			PageTabRegionFormConfigDto pageTabRegionFormConfigDto;
			for(PageTabConfigDto pageTabConfigDto : pageConfigDto.getPageTabConfigDtos()) {
				for (PageTabRegionConfigDto pageTabRegionConfigDto : pageTabConfigDto.getPageTabRegionConfigDtos()) {
					pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1,"返佣时间","rebateTime",obj.getRebateTimeStr());
					formList.add(pageTabRegionFormConfigDto);
					pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1,"返佣金额","backExpensesMoney",obj.getRebateMoney()+"元");
					formList.add(pageTabRegionFormConfigDto);
					pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(6,"备注","remark",obj.getRemark());
					formList.add(pageTabRegionFormConfigDto);
					pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(110,null,"imgs",imgs);
					formList.add(pageTabRegionFormConfigDto);
					pageTabRegionConfigDto.getValueList().set(0, formList);
				}
			}
		}
		//回款
		else if("tbl_sl_receivableForFirst_page".equals(pageConfigDto.getPageClass())||"tbl_sl_receivableForEnd_page".equals(pageConfigDto.getPageClass())||"tbl_sl_receivableFor_page".equals(pageConfigDto.getPageClass())){
			ReceivableForDto obj = new ReceivableForDto();
			obj.setOrderNo(orderNo);
			List<ReceivableForDto> list = httpUtil.getList(Constants.LINK_CREDIT, "/credit/finance/receivableFor/v/detail", obj, ReceivableForDto.class);
			String remark = "-";
			for(PageTabConfigDto pageTabConfigDto : pageConfigDto.getPageTabConfigDtos()) {
				for (PageTabRegionConfigDto pageTabRegionConfigDto : pageTabConfigDto.getPageTabRegionConfigDtos()) {
					List<PageTabRegionFormConfigDto> formList = new ArrayList<PageTabRegionFormConfigDto>();
						String imgs = "";
						PageTabRegionFormConfigDto pageTabRegionFormConfigDto ;
						if(null!=list&&list.size()>0){
							for(int i=0;i<list.size();i++){
								obj = list.get(i);
								if(i==0){
									pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1,"预计回款时间","customerPaymentTime",DateUtil.getDateByFmt(obj.getCustomerPaymentTime(), DateUtil.FMT_TYPE2));
									formList.add(pageTabRegionFormConfigDto);
									pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1,"实际回款时间","payMentAmountDate","".equals(obj.getPayMentAmountDate())?"-":obj.getPayMentAmountDate());
									formList.add(pageTabRegionFormConfigDto);
									pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1,"回款金额","payMentAmount",null==obj.getPayMentAmount()?"-":obj.getPayMentAmount()+"万元");
									formList.add(pageTabRegionFormConfigDto);
									remark = obj.getRemark();
								} else {
									pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1,"第"+(i+1)+"次回款时间","payMentAmountDate","".equals(obj.getPayMentAmountDate())?"-":obj.getPayMentAmountDate());
									formList.add(pageTabRegionFormConfigDto);
									pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1,"第"+(i+1)+"次回款金额","payMentAmount",null==obj.getPayMentAmount()?"-":obj.getPayMentAmount()+"万元");
									formList.add(pageTabRegionFormConfigDto);
								}
								if(StringUtils.isNotBlank(obj.getPayMentPic())){
									imgs += ";"+obj.getPayMentPic();
								}
							}
							if(StringUtils.isNotBlank(remark)){
								pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(6,"备注","remark",remark);
								formList.add(pageTabRegionFormConfigDto);
							}
						} else{
							pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1,"预计回款时间","customerPaymentTime",DateUtil.getDateByFmt(obj.getCustomerPaymentTime(), DateUtil.FMT_TYPE2));
							formList.add(pageTabRegionFormConfigDto);
							pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1,"实际回款时间","payMentAmountDate","".equals(obj.getPayMentAmountDate())?"-":obj.getPayMentAmountDate());
							formList.add(pageTabRegionFormConfigDto);
							pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1,"回款金额","payMentAmount",null==obj.getPayMentAmount()?"-":obj.getPayMentAmount()+"万元");
							formList.add(pageTabRegionFormConfigDto);
							imgs = obj.getPayMentPic();
							if(StringUtils.isNotBlank(obj.getRemark())){
								pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(6,"备注","remark",remark);
								formList.add(pageTabRegionFormConfigDto);
							}
						}
						imgs = cleanImgUrl(imgs);
						pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(110,null,"imgs",imgs);
						formList.add(pageTabRegionFormConfigDto);
						pageTabRegionConfigDto.getValueList().set(0, formList);
				}
			}
			
		}
		//收罚息
		else if("tbl_sl_pay_page".equals(pageConfigDto.getPageClass())){
			ReceivablePayDto obj = new ReceivablePayDto();
			obj.setOrderNo(orderNo);
			obj = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/finance/receivablePay/v/detail", obj, ReceivablePayDto.class);
            for(PageTabConfigDto pageTabConfigDto : pageConfigDto.getPageTabConfigDtos()) {
				for (PageTabRegionConfigDto pageTabRegionConfigDto : pageTabConfigDto.getPageTabRegionConfigDtos()) {
					List<PageTabRegionFormConfigDto> formList = new ArrayList<PageTabRegionFormConfigDto>();
						PageTabRegionFormConfigDto pageTabRegionFormConfigDto;
//						String type="-";
//						if(obj.getType()==0){
//							type = "准时回款";
//						}else if(obj.getType()==1){
//							type = "提前回款";
//						}else if(obj.getType()==2){
//							type = "逾期回款";
//						}
//						pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1,"回款结果","type",type);
//						formList.add(pageTabRegionFormConfigDto);
//						if(obj.getRefund()==1){
//							pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1,"是否退费","reFund","是");
//							formList.add(pageTabRegionFormConfigDto);
//							if(obj.getType()==1){
//								pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1,"退费金额","penaltyPayable",obj.getPenaltyPayable()+"");
//								formList.add(pageTabRegionFormConfigDto);
//								pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1,"退费时间","payTime",DateUtil.getDateByFmt(obj.getPayTime(), DateUtil.FMT_TYPE2));
//								formList.add(pageTabRegionFormConfigDto);
//							}else if(obj.getType()==2){
								pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1,"收罚息金额","penaltyPayable",obj.getPenaltyPayable()+"");
								formList.add(pageTabRegionFormConfigDto);
								pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1,"收罚息时间","payTime",DateUtil.getDateByFmt(obj.getPayTime(), DateUtil.FMT_TYPE2));
								formList.add(pageTabRegionFormConfigDto);
//							}
//						}else {
//							pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1,"是否退费","reFund","否");
//							formList.add(pageTabRegionFormConfigDto);
//						}
						if(StringUtils.isNotBlank(obj.getRemark())){
							pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1,"备注","remark",obj.getRemark());
							formList.add(pageTabRegionFormConfigDto);
						}
			            String imgs = cleanImgUrl(obj.getPayImg());
						imgs = cleanImgUrl(imgs);
						pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(110,null,"imgs",imgs);
						formList.add(pageTabRegionFormConfigDto);
						pageTabRegionConfigDto.getValueList().set(0, formList);
				}
			}
		}
		//资方审批
		else if("tbl_sl_fundAduit_page".equals(pageConfigDto.getPageClass())){
			AllocationFundAduitDto obj = new AllocationFundAduitDto();
			obj.setOrderNo(orderNo);
			obj = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/risk/allocationfundaduit/v/selectFundAudit", obj, AllocationFundAduitDto.class);
			map = BeanToMapUtil.beanToMap(obj);
			
			if(null!=obj){
				if("111".equals(obj.getDealStatus())||111==obj.getDealStatus()){
					map.put("dealStatus", "待审批");
				} else if("997".equals(obj.getDealStatus())||997==obj.getDealStatus()){
					map.put("dealStatus", "通过");
				} else if("998".equals(obj.getDealStatus())||998==obj.getDealStatus()){
					map.put("dealStatus", "否决");
				} else if("991".equals(obj.getDealStatus())||991==obj.getDealStatus()){
					map.put("dealStatus", "准入项校验否决");
				} else if("999".equals(obj.getDealStatus())||999==obj.getDealStatus()){
					map.put("dealStatus", "部分通过");
				}
				map.put("lendingTime", DateUtil.getDateByFmt(obj.getLendingTime(), DateUtil.FMT_TYPE2));
			} 
			for(PageTabConfigDto pageTabConfigDto : pageConfigDto.getPageTabConfigDtos()) {
				for (PageTabRegionConfigDto pageTabRegionConfigDto : pageTabConfigDto.getPageTabRegionConfigDtos()) {
					setSlData(pageTabRegionConfigDto, map);
				}
			}
		}
		//资料审核
		else if("tbl_sl_dataAudit_page".equals(pageConfigDto.getPageClass())){
			DataAuditDto obj = new DataAuditDto();
			obj.setOrderNo(orderNo);
			map = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/risk/dataAudit/v/detail", obj,Map.class);
			map.put("auditTime", DateUtil.getDateByFmt(obj.getAuditTime(), DateUtil.FMT_TYPE2));
			for(PageTabConfigDto pageTabConfigDto : pageConfigDto.getPageTabConfigDtos()) {
				for (PageTabRegionConfigDto pageTabRegionConfigDto : pageTabConfigDto.getPageTabRegionConfigDtos()) {
					setSlData(pageTabRegionConfigDto, map);
				}
			}
		}
		//对接资方
		else if("tbl_sl_fundDocking_page".equals(pageConfigDto.getPageClass())){
			AllocationFundDto obj = new AllocationFundDto();
			obj.setOrderNo(orderNo);
			List<AllocationFundDto> list = httpUtil.getList(Constants.LINK_CREDIT, "/credit/risk/fundDocking/v/detail", obj,AllocationFundDto.class);
			if(list!=null && list.size()>0){
				String remark = list.get(0).getRemark();
				Date auditTime = list.get(0).getAuditTime();
				map.put("handleName", CommonDataUtil.getUserDtoByUidAndMobile(list.get(0).getHandleUid()).getName());
				map.put("auditTime", DateUtil.getDateByFmt(auditTime, DateUtil.FMT_TYPE2));
				map.put("remark", remark);
			}
			for(PageTabConfigDto pageTabConfigDto : pageConfigDto.getPageTabConfigDtos()) {
				for (PageTabRegionConfigDto pageTabRegionConfigDto : pageTabConfigDto.getPageTabRegionConfigDtos()) {
					setSlData(pageTabRegionConfigDto, map);
				}
			}
		}
		//指派受理员
		else if("tbl_sl_assignAcceptMember_page".equals(pageConfigDto.getPageClass())){
			for(PageTabConfigDto pageTabConfigDto : pageConfigDto.getPageTabConfigDtos()) {
				for (PageTabRegionConfigDto pageTabRegionConfigDto : pageTabConfigDto.getPageTabRegionConfigDtos()) {
					for (PageTabRegionFormConfigDto pageTabRegionFormConfigDto : pageTabRegionConfigDto.getValueList().get(0)) {
						if(pageTabRegionFormConfigDto.getKey().equals("acceptMemberName")){
							pageTabRegionFormConfigDto.setTypeDepend(pageTabRegionFormConfigDto.getTypeDepend()+"&cityCode="+orderListDto.getCityCode()+"&productCode="+orderListDto.getProductCode());
						}
					}
				}
			}
		}
		//回款报备
		else if("tbl_sl_editPaymentReport_page".equals(pageConfigDto.getPageClass())
				||"tbl_fdd_editPaymentReport_page".equals(pageConfigDto.getPageClass())){
			configPaymentReport(params,pageConfigDto,orderNo,orderListDto,user);
		}
		//房抵贷
		//抵押
		else if("tbl_fdd_fddMortgage_page".equals(pageConfigDto.getPageClass())){
			    MortgageDto  obj = new MortgageDto();
				obj.setOrderNo(orderNo);
				obj = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/process/fddMortgage/v/detail", obj, MortgageDto.class);
				map = BeanToMapUtil.beanToMap(obj);
				map.put("mortgageTime", DateUtil.getDateByFmt(obj.getMortgageTime(), DateUtil.FMT_TYPE2));
				map.put("licenseRevTime", DateUtil.getDateByFmt(obj.getLicenseRevTime(), DateUtil.FMT_TYPE2));
				for(PageTabConfigDto pageTabConfigDto : pageConfigDto.getPageTabConfigDtos()) {
					for (PageTabRegionConfigDto pageTabRegionConfigDto : pageTabConfigDto.getPageTabRegionConfigDtos()) {
						for (PageTabRegionFormConfigDto pageTabRegionFormConfigDto : pageTabRegionConfigDto.getValueList().get(0)) {
							if(pageTabRegionFormConfigDto.getKey().equals("mlandBureauName")){
								pageTabRegionFormConfigDto.setTypeDepend(pageTabRegionFormConfigDto.getTypeDepend().replace("4403", orderListDto.getCityCode()));
							}
							if(pageTabRegionFormConfigDto.getKey().equals("licenseRever")){
								pageTabRegionFormConfigDto.setTypeDepend(pageTabRegionFormConfigDto.getTypeDepend().replace("{cityCode}", orderListDto.getCityCode()).replace("{productCode}", orderListDto.getProductCode()));
							}
						}
						setSlData(pageTabRegionConfigDto, map);
						if(!isEdit&&("mortgage".equals(orderListDto.getProcessId())||"receivableForFirst".equals(orderListDto.getProcessId()))){
							//加编辑按钮
							pageTabRegionConfigDto.setAbleEdit(1);
						}
					}
				}
		}
		//取证
		else if("tbl_fdd_fddForensics_page".equals(pageConfigDto.getPageClass())){
				ForensicsDto  obj = new ForensicsDto();
				obj.setOrderNo(orderNo);
				obj = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/process/fddForensics/v/detail", obj, ForensicsDto.class);
				map = BeanToMapUtil.beanToMap(obj);
				if(obj==null){
					obj = new ForensicsDto();
				}
				map.put("licenseRevTime", DateUtil.getDateByFmt(obj.getLicenseRevTime(), DateUtil.FMT_TYPE2));
				map.put("collateralTime", DateUtil.getDateByFmt(obj.getCollateralTime(), DateUtil.FMT_TYPE2));
				for(PageTabConfigDto pageTabConfigDto : pageConfigDto.getPageTabConfigDtos()) {
					for (PageTabRegionConfigDto pageTabRegionConfigDto : pageTabConfigDto.getPageTabRegionConfigDtos()) {
						for (PageTabRegionFormConfigDto pageTabRegionFormConfigDto : pageTabRegionConfigDto.getValueList().get(0)) {
							if(pageTabRegionFormConfigDto.getKey().equals("collateralName")){
								pageTabRegionFormConfigDto.setTypeDepend(pageTabRegionFormConfigDto.getTypeDepend().replace("4403", orderListDto.getCityCode()).replace("01", "04"));
							}
						}
						setSlData(pageTabRegionConfigDto, map);
						if(!isEdit&&("mortgage".equals(orderListDto.getProcessId())||"receivableForFirst".equals(orderListDto.getProcessId()))){
							//加编辑按钮
							pageTabRegionConfigDto.setAbleEdit(1);
						}
					}
				}
		}
		//抵押品入库
		else if("tbl_fdd_fddMortgageStorage_page".equals(pageConfigDto.getPageClass())){
				FddMortgageStorageDto  obj = new FddMortgageStorageDto();
				obj.setOrderNo(orderNo);
				obj = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/process/fddMortgageStorage/v/detail", obj, FddMortgageStorageDto.class);
				if(obj==null){
					obj = new FddMortgageStorageDto();
				}
				if(obj.getHousePropertyType()==null){//默认所在地区，房产名称，产权证类型，产权证号
					OrderBaseHouseDto house = new OrderBaseHouseDto();
					house.setOrderNo(orderNo);
					house = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/order/house/v/query", house,OrderBaseHouseDto.class);
					if(house.getOrderBaseHousePropertyDto()!=null){
						Object bean = house.getOrderBaseHousePropertyDto().get(0);
						String json = JsonUtil.BeanToJson(bean);
						Map<String, Object> m;
						try {
							m = JsonUtil.jsonToMap(json);
							obj.setHousePropertyType(MapUtils.getString(m, "housePropertyType"));
							obj.setHousePropertyNumber(MapUtils.getString(m, "housePropertyNumber"));
							obj.setHouseName(MapUtils.getString(m, "houseName"));
							obj.setRegion(MapUtils.getString(m, "houseRegion"));
						} catch (JSONException e) {
							e.printStackTrace();
						}
						
					}
				}
				map = BeanToMapUtil.beanToMap(obj);
				map.put("collateralTime", DateUtil.getDateByFmt(obj.getCollateralTime(), DateUtil.FMT_TYPE2));
				for(PageTabConfigDto pageTabConfigDto : pageConfigDto.getPageTabConfigDtos()) {
					for (PageTabRegionConfigDto pageTabRegionConfigDto : pageTabConfigDto.getPageTabRegionConfigDtos()) {
						for (PageTabRegionFormConfigDto pageTabRegionFormConfigDto : pageTabRegionConfigDto.getValueList().get(0)) {
							if(pageTabRegionFormConfigDto.getKey().equals("region")){
								pageTabRegionFormConfigDto.setTypeDepend(pageTabRegionFormConfigDto.getTypeDepend().replace("{cityCode}", orderListDto.getCityCode()));
							}
						}
						setSlData(pageTabRegionConfigDto, map);
						if(!isEdit&&("mortgage".equals(orderListDto.getProcessId())||"receivableForFirst".equals(orderListDto.getProcessId()))){
							//加编辑按钮
							pageTabRegionConfigDto.setAbleEdit(1);
						}
					}
				}
		}
		//抵押品出库
		else if("tbl_fdd_fddMortgageRelease_page".equals(pageConfigDto.getPageClass())){
				FddMortgageReleaseDto  obj = new FddMortgageReleaseDto();
				obj.setOrderNo(orderNo);
				obj = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/process/fddMortgageRelease/v/detail", obj, FddMortgageReleaseDto.class);
				if(obj==null){
					obj = new FddMortgageReleaseDto();
				}
				if(obj.getHousePropertyType()==null){//默认所在地区，房产名称，产权证类型，产权证号
					OrderBaseHouseDto house = new OrderBaseHouseDto();
					house.setOrderNo(orderNo);
					house = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/order/house/v/query", house,OrderBaseHouseDto.class);
					if(house.getOrderBaseHousePropertyDto()!=null){
						Object bean = house.getOrderBaseHousePropertyDto().get(0);
						String json = JsonUtil.BeanToJson(bean);
						Map<String, Object> m;
						try {
							m = JsonUtil.jsonToMap(json);
							obj.setHousePropertyType(MapUtils.getString(m, "housePropertyType"));
							obj.setHousePropertyNumber(MapUtils.getString(m, "housePropertyNumber"));
							obj.setHouseName(MapUtils.getString(m, "houseName"));
							obj.setRegion(MapUtils.getString(m, "houseRegion"));
						} catch (JSONException e) {
							e.printStackTrace();
						}
						
					}
				}
				map = BeanToMapUtil.beanToMap(obj);
				map.put("collateralOutTime", DateUtil.getDateByFmt(obj.getCollateralOutTime(), DateUtil.FMT_TYPE2));
				for(PageTabConfigDto pageTabConfigDto : pageConfigDto.getPageTabConfigDtos()) {
					for (PageTabRegionConfigDto pageTabRegionConfigDto : pageTabConfigDto.getPageTabRegionConfigDtos()) {
						for (PageTabRegionFormConfigDto pageTabRegionFormConfigDto : pageTabRegionConfigDto.getValueList().get(0)) {
							if(pageTabRegionFormConfigDto.getKey().equals("region")){
								pageTabRegionFormConfigDto.setTypeDepend(pageTabRegionFormConfigDto.getTypeDepend().replace("{cityCode}", orderListDto.getCityCode()));
							}
							if(pageTabRegionFormConfigDto.getKey().equals("releaseName")){
								pageTabRegionFormConfigDto.setTypeDepend(pageTabRegionFormConfigDto.getTypeDepend().replace("4403", orderListDto.getCityCode()).replace("01", "04"));
							}
						}
						setSlData(pageTabRegionConfigDto, map);
						if(!isEdit&&("mortgage".equals(orderListDto.getProcessId())||"receivableForFirst".equals(orderListDto.getProcessId()))){
							//加编辑按钮
							pageTabRegionConfigDto.setAbleEdit(1);
						}
					}
				}
		}
		//解押
		else if("tbl_fdd_release_page".equals(pageConfigDto.getPageClass())){
				FddReleaseDto  obj = new FddReleaseDto();
				obj.setOrderNo(orderNo);
				obj = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/process/release/v/detail", obj, FddReleaseDto.class);
				if(obj==null){
					obj = new FddReleaseDto();
				}
				map = BeanToMapUtil.beanToMap(obj);
				map.put("releaseTime", DateUtil.getDateByFmt(obj.getReleaseTime(), DateUtil.FMT_TYPE2));
				for(PageTabConfigDto pageTabConfigDto : pageConfigDto.getPageTabConfigDtos()) {
					for (PageTabRegionConfigDto pageTabRegionConfigDto : pageTabConfigDto.getPageTabRegionConfigDtos()) {
						for (PageTabRegionFormConfigDto pageTabRegionFormConfigDto : pageTabRegionConfigDto.getValueList().get(0)) {
							if(pageTabRegionFormConfigDto.getKey().equals("nlandBureauName")){
								pageTabRegionFormConfigDto.setTypeDepend(pageTabRegionFormConfigDto.getTypeDepend().replace("4403", orderListDto.getCityCode()));
							}
						}
						setSlData(pageTabRegionConfigDto, map);
						if(!isEdit&&("mortgage".equals(orderListDto.getProcessId())||"receivableForFirst".equals(orderListDto.getProcessId()))){
							//加编辑按钮
							pageTabRegionConfigDto.setAbleEdit(1);
						}
					}
				}
		}
		//还款计划表
		else if("tbl_fdd_repaymentPlan_page".equals(pageConfigDto.getPageClass())){
				Map<String,Object> m = new HashMap<String, Object>();
				m.put("orderNo", orderNo);
				RespDataObject<Map<String,Object>> result = httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/finance/afterLoanList/v/detailLastLoan", m,Map.class);
				Map<String,Object> loan = new HashMap<String, Object>();
				Map<String,Object> detail = new HashMap<String, Object>();
				if("SUCCESS".equals(result.getCode())){
					Map<String,Object> data = result.getData();
					String json = JsonUtil.BeanToJson(data);
					try {
						data = JsonUtil.jsonToMap(json);
						String loanStr = MapUtils.getString(data, "loan");
						if(StringUtils.isNotBlank(loanStr)){
							loan = JsonUtil.jsonToMap(loanStr);
							log.info(loan);
						}
						String detailStr = MapUtils.getString(data, "detail");
						if(StringUtils.isNotBlank(detailStr)){
							detail = JsonUtil.jsonToMap(detailStr);
							log.info(detail);
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
					
				}
				List<PageTabRegionFormConfigDto> listForm = new ArrayList<PageTabRegionFormConfigDto>();
				PageTabRegionFormConfigDto pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto();
				BigDecimal bd = new BigDecimal(MapUtils.getDouble(loan, "loanAmount")*10000);
				DecimalFormat df = new DecimalFormat(",###,###");
				String loanAmount = df.format(bd);
				
				DecimalFormat df00 = new DecimalFormat(",###,###.00");
				//剩余本金
				String surplusPrincipal = "";
				if(detail!=null&&detail.get("surplusPrincipal")!=null){
					BigDecimal bds = new BigDecimal(MapUtils.getDouble(detail, "surplusPrincipal"));
					if(MapUtils.getDouble(detail, "surplusPrincipal")==0){
						surplusPrincipal = "0";
					}else if(MapUtils.getDouble(detail, "surplusPrincipal")<1){
						surplusPrincipal = NumberUtil.formatDecimal(MapUtils.getDouble(detail, "surplusPrincipal"), 2)+"";
					}else{
						surplusPrincipal = df00.format(bds);
					}
				}
				//应还金额
				String repayAmount = "";
				if(detail!=null&&detail.get("repayAmount")!=null){
					BigDecimal bds = new BigDecimal(MapUtils.getDouble(detail, "repayAmount"));
					if(MapUtils.getDouble(detail, "repayAmount")==0){
						repayAmount = "0";
					}else if(MapUtils.getDouble(detail, "repayAmount")<1){
						repayAmount = NumberUtil.formatDecimal(MapUtils.getDouble(detail, "repayAmount"), 2)+"";
					}else{
						repayAmount = df00.format(bds);
					}
				}
				//应还本金
				String repayPrincipal = "";
				if(detail!=null&&detail.get("repayPrincipal")!=null){
					BigDecimal bds = new BigDecimal(MapUtils.getDouble(detail, "repayPrincipal"));
					if(MapUtils.getDouble(detail, "repayPrincipal")==0){
						repayPrincipal = "0";
					}else if(MapUtils.getDouble(detail, "repayPrincipal")<1){
						repayPrincipal = NumberUtil.formatDecimal(MapUtils.getDouble(detail, "repayPrincipal"), 2)+"";
					}else{
						repayPrincipal = df00.format(bds);
					}
				}
				//应还利息
				String repayInterest = "";
				if(detail!=null&&detail.get("repayInterest")!=null){
					BigDecimal bds = new BigDecimal(MapUtils.getDouble(detail, "repayInterest"));
					if(MapUtils.getDouble(detail, "repayInterest")==0){
						repayInterest = "0";
					}else if(MapUtils.getDouble(detail, "repayInterest")<1){
						repayInterest = NumberUtil.formatDecimal(MapUtils.getDouble(detail, "repayInterest"), 2)+"";
					}else{
						repayInterest = df00.format(bds);
					}
				}
				//应还逾期费
				String repayOverdue = "";
				if(detail!=null&&detail.get("repayOverdue")!=null){
					BigDecimal bds = new BigDecimal(MapUtils.getDouble(detail, "repayOverdue"));
					if(MapUtils.getDouble(detail, "repayOverdue")==0){
						repayOverdue = "0";
					}else if(MapUtils.getDouble(detail, "repayOverdue")<1){
						repayOverdue = NumberUtil.formatDecimal(MapUtils.getDouble(detail, "repayOverdue"), 2)+"";
					}else{
						repayOverdue = df00.format(bds);
					}
				}
				//已还本金
				String givePayPrincipal = "";
				if(detail!=null&&detail.get("givePayPrincipal")!=null){
					BigDecimal bds = new BigDecimal(MapUtils.getDouble(detail, "givePayPrincipal"));
					if(MapUtils.getDouble(detail, "givePayPrincipal")==0){
						givePayPrincipal = "0";
					}else if(MapUtils.getDouble(detail, "givePayPrincipal")<1){
						givePayPrincipal = NumberUtil.formatDecimal(MapUtils.getDouble(detail, "givePayPrincipal"), 2)+"";
					}else{
						givePayPrincipal = df00.format(bds);
					}
				}
				//已还利息
				String givePayInterest = "";
				if(detail!=null&&detail.get("givePayInterest")!=null){
					BigDecimal bds = new BigDecimal(MapUtils.getDouble(detail, "givePayInterest"));
					if(MapUtils.getDouble(detail, "givePayInterest")==0){
						givePayInterest = "0";
					}else if(MapUtils.getDouble(detail, "givePayInterest")<1){
						givePayInterest = NumberUtil.formatDecimal(MapUtils.getDouble(detail, "givePayInterest"), 2)+"";
					}else{
						givePayInterest = df00.format(bds);
					}
				}
				//已还逾期费
				String givePayOverdue = "";
				if(detail!=null&&detail.get("givePayOverdue")!=null){
					BigDecimal bds = new BigDecimal(MapUtils.getDouble(detail, "givePayOverdue"));
					if(MapUtils.getDouble(detail, "givePayOverdue")==0){
						givePayOverdue = "0";
					}else if(MapUtils.getDouble(detail, "givePayOverdue")<1){
						givePayOverdue = NumberUtil.formatDecimal(MapUtils.getDouble(detail, "givePayOverdue"), 2)+"";
					}else{
						givePayOverdue = df00.format(bds);
					}
				}
				//还款日期
				String repaymentDateStr="";
				if(detail!=null&&detail.get("repaymentDate")!=null){
					long repaymentDate = MapUtils.getLong(detail, "repaymentDate");
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					repaymentDateStr = sdf.format(repaymentDate);
				}
				
				for(PageTabConfigDto pageTabConfigDto : pageConfigDto.getPageTabConfigDtos()) {
					for (PageTabRegionConfigDto pageTabRegionConfigDto : pageTabConfigDto.getPageTabRegionConfigDtos()) {
						pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1, "客户姓名：","customerName" , StringUtils.isBlank(MapUtils.getString(loan, "borrowerName"))?"-":MapUtils.getString(loan, "borrowerName"), null);
						pageTabRegionFormConfigDto.setIsReadOnly(2);
						listForm.add(pageTabRegionFormConfigDto);
						pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1, "合同编号：","contractNo" , StringUtils.isBlank(MapUtils.getString(loan, "contractNo"))?"-":MapUtils.getString(loan, "contractNo"), null);
						pageTabRegionFormConfigDto.setIsReadOnly(2);
						listForm.add(pageTabRegionFormConfigDto);
						pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1, "产品类型：","productName" , StringUtils.isBlank(MapUtils.getString(loan, "productName"))?"-":MapUtils.getString(loan, "productName"), null);
						pageTabRegionFormConfigDto.setIsReadOnly(2);
						listForm.add(pageTabRegionFormConfigDto);
						pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1, "借款金额：","loanAmount" , StringUtils.isBlank(loanAmount)?"-":loanAmount+"元", null);
						pageTabRegionFormConfigDto.setIsReadOnly(2);
						listForm.add(pageTabRegionFormConfigDto);
						pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1, "还款方式：","repaymentTypeName" , StringUtils.isBlank(MapUtils.getString(loan, "repaymentName"))?"-":MapUtils.getString(loan, "repaymentName"), null);
						pageTabRegionFormConfigDto.setIsReadOnly(2);
						listForm.add(pageTabRegionFormConfigDto);
						pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1, "借款期限：","borrowingPeriods" , StringUtils.isBlank(MapUtils.getString(loan, "borrowingPeriods"))?"-":MapUtils.getString(loan, "borrowingPeriods")+"期", null);
						pageTabRegionFormConfigDto.setIsReadOnly(2);
						listForm.add(pageTabRegionFormConfigDto);
						pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1, "利率：","rate" , StringUtils.isBlank(MapUtils.getString(loan, "rate"))?"-":MapUtils.getString(loan, "rate")+"% /月", null);
						pageTabRegionFormConfigDto.setIsReadOnly(2);
						listForm.add(pageTabRegionFormConfigDto);
						pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1, "逾期利率：","overdueRate" , StringUtils.isBlank(MapUtils.getString(loan, "overdueRate"))?"-":MapUtils.getString(loan, "overdueRate")+"% /天", null);
						pageTabRegionFormConfigDto.setIsReadOnly(2);
						listForm.add(pageTabRegionFormConfigDto);
						pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(-1, "最新还款计划：","customerName" , " ", null);
						pageTabRegionFormConfigDto.setIsReadOnly(2);
						listForm.add(pageTabRegionFormConfigDto);
						pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1, "还款期数：","repaymentPeriods" , StringUtils.isBlank(MapUtils.getString(detail, "repaymentPeriods"))?"-":MapUtils.getString(detail, "repaymentPeriods"), null);
						pageTabRegionFormConfigDto.setIsReadOnly(2);
						listForm.add(pageTabRegionFormConfigDto);
						pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1, "还款日期：","repaymentDate" , StringUtils.isBlank(repaymentDateStr)?"-":repaymentDateStr, null);
						pageTabRegionFormConfigDto.setIsReadOnly(2);
						listForm.add(pageTabRegionFormConfigDto);
						pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1, "剩余本金(元)：","surplusPrincipal" , StringUtils.isBlank(surplusPrincipal)?"-":surplusPrincipal, null);
						pageTabRegionFormConfigDto.setIsReadOnly(2);
						listForm.add(pageTabRegionFormConfigDto);
						pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1, "应还金额(元)：","repayAmount" , StringUtils.isBlank(repayAmount)?"-":repayAmount, null);
						pageTabRegionFormConfigDto.setIsReadOnly(2);
						listForm.add(pageTabRegionFormConfigDto);
						pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1, "应还本金(元)：","repayPrincipal" , StringUtils.isBlank(repayPrincipal)?"-":repayPrincipal, null);
						pageTabRegionFormConfigDto.setIsReadOnly(2);
						listForm.add(pageTabRegionFormConfigDto);
						pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1, "应还利息(元)：","repayInterest" , StringUtils.isBlank(repayInterest)?"-":repayInterest, null);
						pageTabRegionFormConfigDto.setIsReadOnly(2);
						listForm.add(pageTabRegionFormConfigDto);
						pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1, "应还逾期费(元)：","repayOverdue" , StringUtils.isBlank(repayOverdue)?"-":repayOverdue, null);
						pageTabRegionFormConfigDto.setIsReadOnly(2);
						listForm.add(pageTabRegionFormConfigDto);
						pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1, "已还本金(元)：","givePayPrincipal" , StringUtils.isBlank(givePayPrincipal)?"-":givePayPrincipal, null);
						pageTabRegionFormConfigDto.setIsReadOnly(2);
						listForm.add(pageTabRegionFormConfigDto);
						pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1, "已还利息(元)：","givePayInterest" , StringUtils.isBlank(givePayInterest)?"-":givePayInterest, null);
						pageTabRegionFormConfigDto.setIsReadOnly(2);
						listForm.add(pageTabRegionFormConfigDto);
						pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1, "已还逾期费(元)：","givePayOverdue" , StringUtils.isBlank(givePayOverdue)?"-":givePayOverdue, null);
						pageTabRegionFormConfigDto.setIsReadOnly(2);
						listForm.add(pageTabRegionFormConfigDto);
						pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1, "逾期天数：","lateDay" , StringUtils.isBlank(MapUtils.getString(detail, "lateDay"))?"-":MapUtils.getString(detail, "lateDay"), null);
						pageTabRegionFormConfigDto.setIsReadOnly(2);
						listForm.add(pageTabRegionFormConfigDto);
						pageTabRegionFormConfigDto = new PageTabRegionFormConfigDto(1, "还款状态：","status" , StringUtils.isBlank(MapUtils.getString(detail, "statusName"))?"-":MapUtils.getString(detail, "statusName"), null);
						pageTabRegionFormConfigDto.setIsReadOnly(2);
						listForm.add(pageTabRegionFormConfigDto);
						pageTabRegionConfigDto.getValueList().set(0, listForm);
					}
				}
		}
		return pageConfigDto;
	}
	
	@SuppressWarnings("unchecked")
	private void setData(PageTabConfigDto pageTabConfigDto,Map<String, Object> params,boolean isEdit){
		params.put("tblName", pageTabConfigDto.getTblName());
		if("tbl_add_order".equals(pageTabConfigDto.getTblName())||"tbl_kg_add_order".equals(pageTabConfigDto.getTblName())){
			return;
		}
		Map<String, Object> selectMap = (Map<String, Object>) httpUtil.getData(Constants.LINK_CREDIT, "/credit/product/data/base/v/select", params);
		log.info("请求数据："+params);
		log.info("返回结果："+selectMap);
		if(!"SUCCESS".equals(selectMap.get("code"))){
			return;
		}
		Map<String, Object> dataMap = (Map<String, Object>) MapUtils.getObject(selectMap, "data","");
		if(StringUtils.isNotEmpty(MapUtils.getString(dataMap, "showText",""))){
			pageTabConfigDto.setRemarks(MapUtils.getString(dataMap, "showText",""));
		}else{
			pageTabConfigDto.setRemarks("");
			List<PageTabRegionConfigDto> pageTabRegionConfigList=null;
			for(PageTabRegionConfigDto pageTabRegionConfigDto : pageTabConfigDto.getPageTabRegionConfigDtos()) {
				if(StringUtils.isNotEmpty(pageTabRegionConfigDto.getKey())){
					if(JSONObject.fromObject(MapUtils.getString(dataMap, "data","")).get(pageTabRegionConfigDto.getKey())!=null){
						//循环区域赋值
						List<Map<String, Object>> tempDataList = JSONObject.fromObject(MapUtils.getString(dataMap, "data","")).getJSONArray(pageTabRegionConfigDto.getKey()); 
						
						List<PageTabRegionFormConfigDto> formList = pageTabRegionConfigDto.getValueList().get(0);
						for (Map<String, Object> tempData : tempDataList) {
							List<PageTabRegionFormConfigDto> tempList = new ArrayList<PageTabRegionFormConfigDto>();
							for (PageTabRegionFormConfigDto pageTabRegionFormConfigDto : formList) {
								try {
									if(!isEdit){
										pageTabRegionFormConfigDto.setIsReadOnly(2);
										if(pageTabRegionFormConfigDto.getType()==2||pageTabRegionFormConfigDto.getType()==3||pageTabRegionFormConfigDto.getType()==4){
											pageTabRegionFormConfigDto.setType(1);
										}
									}
									tempList.add((PageTabRegionFormConfigDto)BeanUtils.cloneBean(pageTabRegionFormConfigDto));
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
							setValues(tempList,tempData);
							pageTabRegionConfigDto.getValueList().add(tempList);
						}
						pageTabRegionConfigDto.getValueList().remove(0);
					}else{
						if("历史评估记录".equals(pageTabRegionConfigDto.getTitle())){
							pageTabRegionConfigList = new ArrayList<PageTabRegionConfigDto>();
							pageTabRegionConfigList.add(pageTabRegionConfigDto);
						}
					}
				}else if(StringUtils.isNotEmpty(MapUtils.getString(dataMap, "data",""))){
					//普通区域赋值
					Map<String, Object> data =  JSONObject.fromObject(MapUtils.getString(dataMap, "data",""));
					for (List<PageTabRegionFormConfigDto>  formList: pageTabRegionConfigDto.getValueList()) {
						for (PageTabRegionFormConfigDto pageTabRegionFormConfigDto : formList) {
							if(!isEdit){
								pageTabRegionFormConfigDto.setIsReadOnly(2);
								if(pageTabRegionFormConfigDto.getType()==2||pageTabRegionFormConfigDto.getType()==3||pageTabRegionFormConfigDto.getType()==4){
									pageTabRegionFormConfigDto.setType(1);
								}
							}
						}
						setValues(formList, data);
					}
				}
			}
			//删除空对象
			if(pageTabRegionConfigList!=null){
				for (PageTabRegionConfigDto pageTabRegionConfig : pageTabRegionConfigList) {
					pageTabConfigDto.getPageTabRegionConfigDtos().remove(pageTabRegionConfig);
				}
			}
		}
	}
	
	/**
	 * 设置表单值
	 * @param formList
	 * @param data
	 */
	private void setValues(List<PageTabRegionFormConfigDto> formList,Map<String, Object> data){
		//匹配小数
		Pattern p = Pattern.compile("(^[1-9](\\.\\d{0,})?)|(^[0-9]\\.\\d{1,})\\d*$");

		for (PageTabRegionFormConfigDto pageTabRegionFormConfigDto : formList) {
			String key = pageTabRegionFormConfigDto.getKey();
			String specialKey = pageTabRegionFormConfigDto.getSpecialKey();
			
			if(StringUtils.isNotEmpty(MapUtils.getString(data, key,""))){
				try{
					if(data.get(key) instanceof Double) {
						//Double b = Double.parseDouble(MapUtils.getString(data, key,""));
						BigDecimal bigDecimal = new BigDecimal(MapUtils.getString(data, key, ""));
						pageTabRegionFormConfigDto.setValue(bigDecimal.toPlainString());
						//pageTabRegionFormConfigDto.setValue(bigDecimal.toPlainString());
					} else if(p.matcher(MapUtils.getString(data,key,"")).find()){
						BigDecimal bigDecimal = new BigDecimal(MapUtils.getString(data, key, ""));
						pageTabRegionFormConfigDto.setValue(bigDecimal.toPlainString());
					}else{
						pageTabRegionFormConfigDto.setValue(MapUtils.getString(data, key,""));
					}
				}catch(Exception e){
					pageTabRegionFormConfigDto.setValue(MapUtils.getString(data, key,""));
				}
			}
			
			if(StringUtils.isNotEmpty(MapUtils.getString(data, specialKey,""))){
				pageTabRegionFormConfigDto.setSpecialValue(MapUtils.getString(data, specialKey,""));
			}
		}
	}
	
//	public static void main(String[] args) {
//		AppPageConfigController appPageConfigController = new AppPageConfigController();
//		appPageConfigController.setData(pageTabConfigDto, params);
//	}


	/**
	 * 获取区域表单
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getPageTabRegionConfigDto") 
	public RespData<PageTabRegionFormConfigDto> getPageTabRegionConfigDto(@RequestBody Map<String, Object> params){
		RespData<PageTabRegionFormConfigDto> resp = new RespData<PageTabRegionFormConfigDto>();
		try {
			String processId = null;
			if(params.get("processId")!=null){
			    processId=MapUtils.getString(params, "processId");
			}
			params.putAll(getOrderBaseInfo(MapUtils.getString(params, "orderNo")));
			if(processId!=null){
				params.put("processId", processId);
			}
			params.put("pageClass", setPageClass(params));
			List<PageTabRegionFormConfigDto> list = pageConfigService.getPageTabRegionConfigDto(params);
			RespHelper.setSuccessData(resp,list);
		} catch (Exception e) {
			RespHelper.setFailData(resp, null, RespStatusEnum.FAIL.getMsg());
			e.printStackTrace();
			log.error("获取"+MapUtils.getString(params, "pageClass")+"-"+MapUtils.getString(params, "regionClass")+"页面区域配置失败", e);
		}
		return resp;
	}

	/**
	 * 保存标签
	 * @param request
	 * @param params
	 * @return
	 */
	@ResponseBody
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/savePageTabConfigDto")
	public RespStatus savePageTabConfigDto(HttpServletRequest request,@RequestBody Map<String, Object> params){
		RespStatus resp = new RespStatus(); 
		String pageClass="";
		try {
			String orderNo = "";
			if(params.containsKey("orderNo")){
				orderNo = MapUtils.getString(params, "orderNo");
			}
			UserDto user = getUserDto(request);
			log.info("savePageTabConfigDto_params:"+params);
			if("01".equals(params.get("productCode"))||"02".equals(params.get("productCode"))||"03".equals(params.get("productCode"))||"04".equals(params.get("productCode"))||"05".equals(params.get("productCode"))||"06".equals(params.get("productCode"))||"07".equals(params.get("productCode"))){//债务置换贷款保存
				if(params.containsKey("tbl_add_order")){//简单提单
					Map<String,Object> m = MapUtils.getMap(params, "tbl_add_order");
					if(user.getAgencyId()>1){//机构提单
						m.put("source", "四海APP");
					}else{
						m.put("source", "四海APP");
					}
					RespDataObject<String> res = httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/order/borrow/v/save", m, String.class);
					if("SUCCESS".equals(res.getCode())){
						RespHelper.setSuccessRespStatus(resp);
						return resp;
					}else{
						RespHelper.setFailRespStatus(resp, res.getMsg());
						return resp;
					}
				}else if(params.containsKey("tbl_kg_add_order")){//快鸽普通用户提单
					Map<String,Object> p = MapUtils.getMap(params, "tbl_kg_add_order");
					p.put("uid", MapUtils.getString(params, "uid"));
					RespStatus res = httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/order/app/kgAppInsertOrder", p);
					if("SUCCESS".equals(res.getCode())){
						RespHelper.setSuccessRespStatus(resp);
						return resp;
					}else{
						RespHelper.setFailRespStatus(resp, "申请失败");
						return resp;
					}
				}
			}
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("orderNo", orderNo);
			if(params.containsKey("tbl_sl_borrow")){//保存借款信息
				map.putAll(MapUtils.getMap(params, "tbl_sl_borrow"));
				return httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/order/app/v/updateBorrow",map);
			}else if(params.containsKey("tbl_fdd_borrow")){//保存借款信息
				map.putAll(MapUtils.getMap(params, "tbl_fdd_borrow"));
				return httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/order/app/v/updateBorrow",map);
			}else if(params.containsKey("tbl_sl_customer")||params.containsKey("tbl_fdd_customer")){//保存客户信息
				Map<String,Object> customer = new HashMap<String, Object>();
				if(params.containsKey("tbl_sl_customer")){
					map.putAll(MapUtils.getMap(params, "tbl_sl_customer"));
					customer = MapUtils.getMap(params, "tbl_sl_customer");
				}else{
					map.putAll(MapUtils.getMap(params, "tbl_fdd_customer"));
					customer = MapUtils.getMap(params, "tbl_fdd_customer");
				}
				if(customer!=null&&customer.containsKey("customerGuaranteeDto")){//保存担保人信息
					List<OrderBaseCustomerGuaranteeDto> tempDataList = (List<OrderBaseCustomerGuaranteeDto>) MapUtils.getObject(customer, "customerGuaranteeDto");
					OrderBaseCustomerDto orderBaseCustomerDto = new OrderBaseCustomerDto();
					orderBaseCustomerDto.setOrderNo(orderNo);
					orderBaseCustomerDto.setCustomerGuaranteeDto(tempDataList);
					return httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/order/app/v/updateCustomerGuaranteeList",orderBaseCustomerDto);
				}
				else if(customer!=null&&customer.containsKey("customerBorrowerDto")){//保存共同借款人
					List<OrderBaseCustomerBorrowerDto> tempDataList = (List<OrderBaseCustomerBorrowerDto>) MapUtils.getObject(customer, "customerBorrowerDto");
					OrderBaseCustomerDto orderBaseCustomerDto = new OrderBaseCustomerDto();
					orderBaseCustomerDto.setOrderNo(orderNo);
					orderBaseCustomerDto.setCustomerBorrowerDto(tempDataList);
					return httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/order/app/v/updateCustomerBorrowerList",orderBaseCustomerDto);
				}else if(customer!=null&&customer.containsKey("customerShareholderDto")){//客户企业股东信息
					List<OrderBaseCustomerShareholderDto> tempDataList = (List<OrderBaseCustomerShareholderDto>) MapUtils.getObject(customer, "customerShareholderDto");
					OrderBaseCustomerDto orderBaseCustomerDto = new OrderBaseCustomerDto();
					orderBaseCustomerDto.setOrderNo(orderNo);
					orderBaseCustomerDto.setCustomerShareholderDto(tempDataList);
					return httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/order/app/v/updateCustomerShareholderList",orderBaseCustomerDto);
				}else{//保存客户基本信息
					if(params.get("regionClass")!=null&&("slCompany".equals(MapUtils.getString(params, "regionClass"))||"fdd_company".equals(MapUtils.getString(params, "regionClass")))){
						return httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/order/app/v/updateCustomerCompany",map);
					}
					return httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/order/app/v/updateCustomer",map);
				}
			}else if(params.containsKey("tbl_sl_house")||params.containsKey("tbl_fdd_house")){//保存房产信息
				Map<String,Object> house = new HashMap<String, Object>();
				if(params.containsKey("tbl_sl_house")){
					map.putAll(MapUtils.getMap(params, "tbl_sl_house"));
					house = MapUtils.getMap(params, "tbl_sl_house");
				}else{
					map.putAll(MapUtils.getMap(params, "tbl_fdd_house"));
					house = MapUtils.getMap(params, "tbl_fdd_house");
				}
				if(house!=null&&house.containsKey("orderBaseHousePropertyDto")){//保存房产信息orderBaseHousePropertyDto
					List<OrderBaseHousePropertyDto> tempDataList = (List<OrderBaseHousePropertyDto>) MapUtils.getObject(house, "orderBaseHousePropertyDto");
					OrderBaseHouseDto orderBaseHouseDto = new OrderBaseHouseDto();
					orderBaseHouseDto.setOrderNo(orderNo);
					orderBaseHouseDto.setOrderBaseHousePropertyDto(tempDataList);
					return httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/order/app/v/updateHousePropertyList",orderBaseHouseDto);
				}
				else if(house!=null&&house.containsKey("orderBaseHousePropertyPeopleDto")){//保存产权人信息orderBaseHousePropertyPeopleDto
					List<OrderBaseHousePropertyPeopleDto> tempDataList = (List<OrderBaseHousePropertyPeopleDto>) MapUtils.getObject(house, "orderBaseHousePropertyPeopleDto");
					OrderBaseHouseDto orderBaseHouseDto = new OrderBaseHouseDto();
					orderBaseHouseDto.setOrderNo(orderNo);
					orderBaseHouseDto.setOrderBaseHousePropertyPeopleDto(tempDataList);
					return httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/order/app/v/updateHousePropertyPeopleList",orderBaseHouseDto);
				}
				else if(house!=null&&house.containsKey("orderBaseHousePurchaserDto")){//保存买房人信息orderBaseHousePurchaserDto
					List<OrderBaseHousePurchaserDto> tempDataList = (List<OrderBaseHousePurchaserDto>) MapUtils.getObject(house, "orderBaseHousePurchaserDto");
					OrderBaseHouseDto orderBaseHouseDto = new OrderBaseHouseDto();
					orderBaseHouseDto.setOrderNo(orderNo);
					orderBaseHouseDto.setOrderBaseHousePurchaserDto(tempDataList);
					return httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/order/app/v/updateHousePurchaserList",orderBaseHouseDto);
				}else if(house!=null&&"fdd_oneLoan".equals(MapUtils.getString(params, "regionClass"))){
					return httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/order/app/v/updateHouse",map);
				}else{//更新房贷与交易信息
					if("cProductCode".equals(MapUtils.getString(params, "regionClass"))){
						map.put("isFinish",null);
					}
					return httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/order/app/v/updateHouse",map);
				}
			}else if(params.containsKey("tbl_fdd_loan")){//房抵贷：放款信息
				map.putAll(MapUtils.getMap(params, "tbl_fdd_loan"));
				String regionClass = (String) params.get("regionClass");
				if("fdd_loan".equals(regionClass)) {
					map.put("version", "fdd_loan");
				}else {
					map.put("version", "fdd_payment");
				}
				return httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/order/app/v/updateHouseLending",map);
			}else if(params.containsKey("tbl_sl_element") && params.containsKey("regionClass")){//保存要件信息
				map.putAll(MapUtils.getMap(params, "tbl_sl_element"));
				DocumentsDto doc = new DocumentsDto();
				doc.setOrderNo(orderNo);
				String regionClass = (String) params.get("regionClass");
				//要件信息
				if("slElementInfo".equals(regionClass)) {
					map.put("orderNo", orderNo);
					return httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/element/basics/v/updateElementInfo",map);
				}
				//出款方式
				if("slForeclosureType".equals(regionClass)||"cForeclosureType".equals(regionClass)){
					ForeclosureTypeDto fors = new ForeclosureTypeDto();
					BeanUtils.populate(fors, map );
					doc.setForeclosureType(fors);
				}
				//回款方式
				if("slPaymentType".equals(regionClass)){
					PaymentTypeDto  pay = new PaymentTypeDto();
					BeanUtils.populate(pay, map );
					doc.setPaymentType(pay);
				}
				doc.setNextHandleUid("APP");
				return httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/element/basics/v/update",doc);
			}else if(params.containsKey("tbl_sl_credit")||params.containsKey("tbl_fdd_credit")){//保存征信信息
				if(params.containsKey("tbl_sl_credit")){
					map.putAll(MapUtils.getMap(params, "tbl_sl_credit"));
				}else{
					map.putAll(MapUtils.getMap(params, "tbl_fdd_credit"));
				}
				return httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/risk/ordercredit/v/updateCredit",map);
			}
		
			//获取订单基本信息
			if(StringUtils.isNotEmpty(MapUtils.getString(params, "orderNo",""))){
				Map<String,Object> md = getOrderBaseInfo(MapUtils.getString(params, "orderNo"));
				params.put("uid", getUserDto(request).getUid());
				if(md!=null){
					if(params.containsKey("tbl_cm_assess")){
						params.put("processId", "assess");
					}else if(params.containsKey("tbl_cm_roundTurn")){
						params.put("processId", "roundTurn");
					}else if(params.containsKey("tbl_cm_customer")||params.containsKey("tbl_cm_loan")){
						params.put("processId", "subMortgage");
					}else if(params.containsKey("tbl_cm_reserveMortgage")){
						params.put("processId", "reserveMortgage");
					}else if(params.containsKey("tbl_cm_forensicsMortgage")){
						params.put("processId", "forensicsMortgage");
					}
					params.put("cityCode", md.get("cityCode"));
					params.put("cityName", md.get("cityName"));
					if(md.get("productCode")!=null){
						params.put("productCode",md.get("productCode"));
					}
					params.put("borrowingAmount", md.get("borrowingAmount"));
				}
			}else{
				params.putAll(MapUtils.getMap(params, "tbl_add_order",new HashMap<String, Object>()));
			}
			pageClass = setPageClass(params);	//获取pageClass
			System.out.println("pageClass:"+pageClass);
			PageConfigDto pageConfigDto = (PageConfigDto) RedisOperator.get(pageClass);	//获取pageClass配置
			
			
			
			for (PageTabConfigDto pageTabConfigDto : pageConfigDto.getPageTabConfigDtos()){
				params.put("tblName", pageTabConfigDto.getTblName());
				
				//如果没有数据不保存
				if(MapUtils.getObject(params, pageTabConfigDto.getTblName(),null) == null){
					System.out.println("数据没有加表名，保存失败");
					continue;
				}
				
				Map<String, Object> selectMap = (Map<String, Object>) httpUtil.getData(Constants.LINK_CREDIT, "/credit/product/data/base/v/select", params);
				Map<String, Object> dataTempMap = (Map<String, Object>) MapUtils.getObject(selectMap, "data","");
				Map<String, Object> dataMap = JSONObject.fromObject(MapUtils.getString(dataTempMap, "data",""));
				System.out.println("保存校验参数："+dataMap);
				//保存请求
				dataMap = setDataMap(dataMap, params);
				if(!pageConfigService.checkPageTabConfigDto(pageTabConfigDto,dataMap)){
					RespHelper.setFailRespStatus(resp, RespStatusEnum.PARAMETER_ERROR.getMsg());
					return resp;
				}else{
					
					resp = saveData(pageTabConfigDto, params, dataMap);
					if(!RespStatusEnum.SUCCESS.getCode().equals(resp.getCode())){
						return resp;
					}
				}
			}
			
		} catch (Exception e) {
			RespHelper.setFailRespStatus(resp, RespStatusEnum.FAIL.getMsg());
			log.error("保存"+pageClass+"-"+MapUtils.getString(params, "tabClass")+"页面标签数据失败", e);
		}
		return resp;
	}

	/**
	 * 提交页面(即保存页面下所有标签数据)
	 * @param request
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/submitPageConfigDto")
	public RespStatus submitPageConfigDto(HttpServletRequest request,@RequestBody Map<String, Object> params){
		RespStatus resp = new RespStatus(); 
		try {
			log.info("提交params:"+params);
			String orderNo = "";
			if(params.containsKey("orderNo")){
				orderNo = MapUtils.getString(params, "orderNo");
			}
			UserDto user = getUserDto(request);
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("orderNo", orderNo);
			String processId=null;
			if(null!=params.get("processId")){
				processId = MapUtils.getString(params, "processId");
			}
			if(params.containsKey("tbl_add_order")){//债务置换贷款保存
				if("01".equals(MapUtils.getMap(params, "tbl_add_order").get("productCode"))||"02".equals(MapUtils.getMap(params, "tbl_add_order").get("productCode"))||"03".equals(MapUtils.getMap(params, "tbl_add_order").get("productCode"))
						||"04".equals(MapUtils.getMap(params, "tbl_add_order").get("productCode"))
						||"05".equals(MapUtils.getMap(params, "tbl_add_order").get("productCode"))
						||"06".equals(MapUtils.getMap(params, "tbl_add_order").get("productCode"))
						||"07".equals(MapUtils.getMap(params, "tbl_add_order").get("productCode"))){
					Map<String,Object> m = MapUtils.getMap(params, "tbl_add_order");
					UserDto u = getUserDtoByRedis(request);
					if(user.getAgencyId()>1||"快鸽".equals(u.getSourceFrom())){//快鸽提单
						m.put("source", "四海APP");
					}else{
						m.put("source", "四海APP");
					}
					RespDataObject<String> res = httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/order/borrow/v/save",m , String.class);
					if("SUCCESS".equals(res.getCode())){
						RespHelper.setSuccessRespStatus(resp);
						return resp;
					}else{
						RespHelper.setFailRespStatus(resp, res.getMsg());
						return resp;
					}
				}
			}else if(params.containsKey("tbl_kg_add_order")){//快鸽普通用户提单
				if("01".equals(MapUtils.getMap(params, "tbl_kg_add_order").get("productCode"))||"02".equals(MapUtils.getMap(params, "tbl_kg_add_order").get("productCode"))||"03".equals(MapUtils.getMap(params, "tbl_kg_add_order").get("productCode"))
						||"04".equals(MapUtils.getMap(params, "tbl_kg_add_order").get("productCode"))
						||"05".equals(MapUtils.getMap(params, "tbl_kg_add_order").get("productCode"))
						||"06".equals(MapUtils.getMap(params, "tbl_add_order").get("productCode"))
						||"07".equals(MapUtils.getMap(params, "tbl_add_order").get("productCode"))){
					log.info("快鸽普通用户提单");
					Map<String,Object> p = MapUtils.getMap(params, "tbl_kg_add_order");
					p.put("uid", MapUtils.getString(params, "uid"));
					RespStatus res = httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/order/app/kgAppInsertOrder", p);
					if("SUCCESS".equals(res.getCode())){
						RespHelper.setSuccessRespStatus(resp);
						return resp;
					}else{
						RespHelper.setFailRespStatus(resp, "申请失败");
						return resp;
					}
				}else if("10000".equals(MapUtils.getMap(params, "tbl_kg_add_order").get("productCode"))){//云按揭只是保存，不提交
					Map<String,Object> dataMap = MapUtils.getMap(params, "tbl_kg_add_order");
					String pageClass = "tbl_cm_assess_page";
					PageConfigDto pageConfigDto = (PageConfigDto) RedisOperator.get(pageClass);	//获取pageClass配置
					for (PageTabConfigDto pageTabConfigDto : pageConfigDto.getPageTabConfigDtos()){
						//保存请求
						params.put("tblName", "tbl_kg_add_order");
						dataMap = setDataMap(dataMap, params);
						params.put("tblName", "tbl_cm_assess");
						params.put("cityCode", MapUtils.getString(dataMap, "cityCode"));
						params.put("productCode", MapUtils.getString(dataMap, "productCode"));
						params.put("source", "快鸽APP");
						params.put("addOrderType", 2);//普通用户提单
						if(!pageConfigService.checkPageTabConfigDto(pageTabConfigDto,dataMap)){
							RespHelper.setFailRespStatus(resp, RespStatusEnum.PARAMETER_ERROR.getMsg());
							return resp;
						}else{
							resp = saveData(pageTabConfigDto, params, dataMap);
							if(RespStatusEnum.SUCCESS.getCode().equals(resp.getCode())){
								RespHelper.setSuccessRespStatus(resp);
								return resp;
							}
						}
					}
				}
			}else if(params.containsKey("tbl_sl_assignAcceptMember")){//指派受理员
				map.putAll(MapUtils.getMap(params, "tbl_sl_assignAcceptMember"));
				return httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/order/app/assignAcceptMember",map);
			}else if(params.containsKey("tbl_sl_managerAudit")) {//分配订单
				map.putAll(MapUtils.getMap(params, "tbl_sl_managerAudit"));
				return httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/process/managerAudit/v/add",map);
			}else if(params.containsKey("tbl_sl_editreport")){//出款报备
				map.putAll(MapUtils.getMap(params, "tbl_sl_editreport"));
				map.put("acceptMemberUid", user.getUid());
				return httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/finance/report/v/insert",map);
			}else if(params.containsKey("tbl_sl_notarization")){//公证,传入关联订单号
				map.putAll(MapUtils.getMap(params, "tbl_sl_notarization"));
				OrderListDto orderListDto = new OrderListDto();
				orderListDto.setOrderNo(orderNo);
				orderListDto = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/order/base/v/selectDetailByOrderNo", orderListDto, OrderListDto.class);
				if("03".equals(orderListDto.getProductCode())&&StringUtils.isNotBlank(orderListDto.getRelationOrderNo())){
					map.put("relationOrderNo", orderListDto.getRelationOrderNo());
				}
				return httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/process/notarization/v/add",map);
			}else if(params.containsKey("tbl_sl_facesign")){//面签
				map.putAll(MapUtils.getMap(params, "tbl_sl_facesign"));
				return httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/process/facesign/v/add",map);
			}else if(params.containsKey("tbl_sl_applyLoan")){//申请放款
				map.putAll(MapUtils.getMap(params, "tbl_sl_applyLoan"));
				return httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/finance/applyLoan/v/add",map);
			}else if(params.containsKey("tbl_sl_financialAudit_page")){//财务审核
				map.putAll(MapUtils.getMap(params, "tbl_sl_financialAudit_page"));
				return httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/order/audit/v/processSubmit",map);
			}else if(params.containsKey("tbl_sl_foreclosure")){//结清原贷款
				map.putAll(MapUtils.getMap(params, "tbl_sl_foreclosure"));
				return httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/process/foreclosure/v/add",map);
			}else if(params.containsKey("tbl_sl_forensics")){//取证
				map.putAll(MapUtils.getMap(params, "tbl_sl_forensics"));
				return httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/process/forensics/v/add",map);
			}else if(params.containsKey("tbl_sl_cancellation")){//注销
				map.putAll(MapUtils.getMap(params, "tbl_sl_cancellation"));
				return httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/process/cancellation/v/add",map);
			}else if(params.containsKey("tbl_sl_transfer")){//过户
				map.putAll(MapUtils.getMap(params, "tbl_sl_transfer"));
				return httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/process/transfer/v/add",map);
			}else if(params.containsKey("tbl_sl_newlicense")){//领新证
				map.putAll(MapUtils.getMap(params, "tbl_sl_newlicense"));
				return httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/process/newlicense/v/add",map);
			}else if(params.containsKey("tbl_sl_mortgage")){//抵押
				map.putAll(MapUtils.getMap(params, "tbl_sl_mortgage"));
				return httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/process/mortgage/v/add",map);
			}else if(params.containsKey("tbl_sl_elementReturn")){//要件退还
				map.putAll(MapUtils.getMap(params, "tbl_sl_elementReturn"));
				return httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/element/return/v/insert",map);
			}else if(params.containsKey("tbl_sl_repaymentMember")){//指派还款专员
				map.putAll(MapUtils.getMap(params, "tbl_sl_repaymentMember"));
				return httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/process/distributionMember/v/add",map);
			}else if(params.containsKey("tbl_sl_paymentReport")){//回款报备
				map.putAll(MapUtils.getMap(params, "tbl_sl_paymentReport"));
				return httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/finance/paymentreport/v/insert",map);
			} else if("placeOrder".equals(MapUtils.getString(params, "processId"))){//提交审核
				return httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/order/borrow/v/submitAudit",params);
			}else if(params.containsKey("tbl_sl_element")) {//要件校验
				return httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/element/basics/v/appElement",params);
			}else if(params.containsKey("tbl_fdd_notarization")){//公证
				map.putAll(MapUtils.getMap(params, "tbl_fdd_notarization"));
				return httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/process/notarization/v/add",map);
			}else if(params.containsKey("tbl_fdd_facesign")){//面签
				map.putAll(MapUtils.getMap(params, "tbl_fdd_facesign"));
				return httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/process/facesign/v/add",map);
			}else if(params.containsKey("tbl_fdd_fddMortgage")){//房抵贷(抵押)
				map.putAll(MapUtils.getMap(params, "tbl_fdd_fddMortgage"));
				return httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/process/fddMortgage/v/add",map);
			}else if(params.containsKey("tbl_fdd_fddForensics")){//房抵贷(取证)
				map.putAll(MapUtils.getMap(params, "tbl_fdd_fddForensics"));
				return httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/process/fddForensics/v/add",map);
			}else if(params.containsKey("tbl_fdd_fddMortgageStorage")){//抵押品入库
				map.putAll(MapUtils.getMap(params, "tbl_fdd_fddMortgageStorage"));
				return httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/process/fddMortgageStorage/v/add",map);
			}else if(params.containsKey("tbl_fdd_applyLoan")){//申请放款
				map.putAll(MapUtils.getMap(params, "tbl_fdd_applyLoan"));
				//新接口面签
				return httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/order/applyLoan/v/processSubmit",map);
			}else if(params.containsKey("tbl_fdd_fddMortgageRelease")){//抵押品出库
				map.putAll(MapUtils.getMap(params, "tbl_fdd_fddMortgageRelease"));
				return httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/process/fddMortgageRelease/v/add",map);
			}else if(params.containsKey("tbl_fdd_release")){//解押
				map.putAll(MapUtils.getMap(params, "tbl_fdd_release"));
				return httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/process/release/v/add",map);
			}else if(params.containsKey("tbl_fdd_editreport")){//出款报备
				map.putAll(MapUtils.getMap(params, "tbl_fdd_editreport"));
				map.put("acceptMemberUid", user.getUid());
				return httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/finance/report/v/insert",map);
			}else if(params.containsKey("tbl_fdd_editPaymentReport")){//回款报备
				map.putAll(MapUtils.getMap(params, "tbl_fdd_editPaymentReport"));
				return httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/finance/paymentreport/v/insert",map);
			}
			
			
			
			//获取订单基本信息
			String pageClass = "";	//获取pageClass
			boolean hasOrderNo = true;
			if(StringUtils.isNotEmpty(MapUtils.getString(params, "orderNo",""))){
				params.putAll(getOrderBaseInfo(MapUtils.getString(params, "orderNo")));
				if(processId!=null){
					params.put("processId", processId);
				}
				if(params.get("tbl_cm_closeOrder")!=null){
					params.put("processId", "closeOrder");
					Map<String,Object> p =(Map<String, Object>) params.get("tbl_cm_closeOrder");
					String closeReason = MapUtils.getString(p, "closeReason");
					params.put("processId", "closeOrder");
					params.put("tblName", "tbl_cm_closeOrder");
					params.put("state", "已关闭");
					params.put("closeReason", closeReason);
					return httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/product/data/list/base/v/close",params);
				}else if(params.containsKey("tbl_cm_repaymentChannelManager")){//指派渠道经理
					Map<String,Object> p =(Map<String, Object>) params.get("tbl_cm_repaymentChannelManager");
					params.put("tblName", "tbl_cm_repaymentChannelManager");
					params.put("state", "待提交评估");
					params.put("channelManagerUid",MapUtils.getString(p, "channelManagerUid",""));
					params.put("channelManagerName",MapUtils.getString(p, "channelManagerName",""));
					params.put("remark",MapUtils.getString(p, "remark",""));
					return httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/product/data/list/base/v/repaymentChannelManager",params);
				}else if(params.containsKey("tbl_cm_assess")){//提交评估
					params.putAll(MapUtils.getMap(params, "tbl_cm_assess",new HashMap<String, Object>()));
				}
				pageClass = setPageClass(params);
			}else{
				hasOrderNo = false;
				if(params.containsKey("tbl_kg_add_order")){
					params.putAll(MapUtils.getMap(params, "tbl_kg_add_order",new HashMap<String, Object>()));
					params.put("processId", "kgAddOrder");
				}else{
					params.putAll(MapUtils.getMap(params, "tbl_add_order",new HashMap<String, Object>()));
				}
				pageClass = setPageClass(params);
				params.put("orderNo", UidUtil.generateOrderId());
			}
			System.out.println("pageClass:"+pageClass);
			PageConfigDto pageConfigDto = (PageConfigDto) RedisOperator.get(pageClass);	//获取pageClass配置
			
			for (PageTabConfigDto pageTabConfigDto : pageConfigDto.getPageTabConfigDtos()) {
				
				if(hasOrderNo){//有订单号提交
					//params.put("tblName", pageTabConfigDto.getTblName());
					params.put("tblName", pageTabConfigDto.getTblName());
					params.put("packageClassMethodName", pageConfigDto.getPackageClassMethodName());
				}else{
					if("10000".equals(MapUtils.getString(params, "productCode",""))){
						if("tbl_add_order".equals(pageTabConfigDto.getTblName())){
							params.put("tblName", "tbl_cm_assess");
							params.put("tbl_cm_assess", MapUtils.getObject(params, "tbl_add_order",""));
							params.put("packageClassMethodName", "com.anjbo.service.cm.impl.AssessServiceImpl.addAssess");
							UserDto u = getUserDtoByRedis(request);
							if("快鸽".equals(u.getSourceFrom())){
								params.put("source", "四海APP");
							}else{
								params.put("source", "四海APP");
							}
						}else if("tbl_kg_add_order".equals(pageTabConfigDto.getTblName())){
							params.put("tblName", "tbl_cm_assess");
							params.put("tbl_cm_assess", MapUtils.getObject(params, "tbl_kg_add_order",""));
							params.put("packageClassMethodName", "com.anjbo.service.cm.impl.AssessServiceImpl.addAssess");
							params.put("source", "快鸽APP");
							//快鸽普通用户提单，没有客户经理手机号等字段
						}else{
							params.put("tblName", pageTabConfigDto.getTblName());
							params.put("packageClassMethodName", pageConfigDto.getPackageClassMethodName());
						}
					}else if("01".equals(MapUtils.getString(params, "productCode","")) || "02".equals(MapUtils.getString(params, "productCode",""))|| "03".equals(MapUtils.getString(params, "productCode",""))
							|| "04".equals(MapUtils.getString(params, "productCode",""))
							|| "05".equals(MapUtils.getString(params, "productCode",""))
							|| "06".equals(MapUtils.getString(params, "productCode",""))
							|| "07".equals(MapUtils.getString(params, "productCode",""))){
						params.put("tblName", "tbl_sl_order");
						params.put("tbl_sl_order", MapUtils.getObject(params, "tbl_add_order",""));
						params.put("packageClassMethodName", pageConfigDto.getPackageClassMethodName());
					}
					params.remove("tbl_add_order");
					params.remove("tbl_kg_add_order");
				}
				Map<String, Object> selectMap = (Map<String, Object>) httpUtil.getData(Constants.LINK_CREDIT, "/credit/product/data/base/v/select", params);
				Map<String, Object> dataTempMap = (Map<String, Object>) MapUtils.getObject(selectMap, "data","");
				Map<String, Object> dataMap = JSONObject.fromObject(MapUtils.getString(dataTempMap, "data",""));
				
				//如果没有数据提交过来。则为最外层提交。需要查询数据校验。 如果有数据。则为内层提交并保存数据
				if(MapUtils.getMap(params, MapUtils.getString(params, "tblName",""),null) == null){
					
					if("10000".equals(MapUtils.getString(params, "productCode",""))){
						//云按揭校验方法。查询数据再次判断。
						if(!pageConfigService.checkPageTabConfigDto(pageTabConfigDto,dataMap)){
							RespHelper.setFailRespStatus(resp, RespStatusEnum.PARAMETER_ERROR.getMsg());
							return resp;
						}
					}else if("01".equals(MapUtils.getString(params, "productCode","")) || "02".equals(MapUtils.getString(params, "productCode",""))){
						//债务置换贷款校验方法。查询表单是否完成。
						
					}
					resp.setCode(RespStatusEnum.SUCCESS.getCode());
				}else{
					dataMap = setDataMap(dataMap, params);
					if("10000".equals(MapUtils.getString(params, "productCode",""))){
						//云按揭校验方法。查询数据再次判断。
						if(!pageConfigService.checkPageTabConfigDto(pageTabConfigDto,dataMap)){
							RespHelper.setFailRespStatus(resp, RespStatusEnum.PARAMETER_ERROR.getMsg());
							return resp;
						}
					}
					resp = saveData(pageTabConfigDto, params, dataMap);
					
					if(!RespStatusEnum.SUCCESS.getCode().equals(resp.getCode())){
						return resp;
					}
				}
			}
			
			if(RespStatusEnum.SUCCESS.getCode().equals(resp.getCode())){
				//提交请求
				resp = httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/product/data/submit/base/v/submit", params);
				if(!hasOrderNo){
					//提单提交失败，删除订单
					if(resp.getCode().equals(RespStatusEnum.FAIL.getCode())){
						Map<String,Object> tempMap = new HashMap<String, Object>();
						tempMap.put("orderNo", MapUtils.getString(params, "orderNo"));
						tempMap.put("tblName", "tbl_cm_assess");
						RespStatus respStatus = httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/product/data/submit/base/v/submitFailDelete", tempMap);
					}
				}
			}
			setOrderBaseInfo(MapUtils.getString(params, "productCode",""),MapUtils.getString(params, "orderNo"));
			
		} catch (Exception e) {
			RespHelper.setFailRespStatus(resp, RespStatusEnum.FAIL.getMsg());
			log.error("提交页面数据失败", e);
		}
		return resp;
	}
	
	/**
	 * App详情
	 * @param request
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/appDetail") 
	public RespDataObject<Map<String, Object>> appDetail(HttpServletRequest request, @RequestBody Map<String,Object> params){
		RespDataObject<Map<String, Object>> resp = new RespDataObject<Map<String,Object>>();
		try {
			String orderNo = MapUtils.getString(params, "orderNo");
			String orderNos = ConfigUtil.getStringValue(Constants.BASE_ORDER_EXAMPLE, ConfigUtil.CONFIG_BASE);
			if(orderNos.contains(orderNo)){
				Map<String,Object> map = exampleOrderDetail(orderNo);
				RespHelper.setSuccessDataObject(resp,map);
				return resp;
			}
			UserDto user = getUserDto(request);
			String productCode = MapUtils.getString(params, "productCode");
			params.putAll(getOrderBaseInfo(MapUtils.getString(params, "orderNo")));
			if("01".equals(productCode)||"02".equals(productCode)||"03".equals(productCode)||"04".equals(productCode)||"05".equals(productCode)||"06".equals(productCode)||"07".equals(productCode)){//债务置换贷款详情
				OrderListDto orderListDto = new OrderListDto();
				orderListDto.setOrderNo(orderNo);
				Map<String, Object> data = (Map<String, Object>) httpUtil.getData(Constants.LINK_CREDIT, "/credit/order/base/v/appDetail", orderListDto);
				RespHelper.setSuccessDataObject(resp,MapUtils.getMap(data, "data"));
				return resp;
			}
			Map<String, Object> retMap = new HashMap<String, Object>();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("pageClass", "tbl_cm_detail_page");
			map.put("regionClass", "cmDetail");
			RespData<Map<String,Object>> respData = httpUtil.getRespData(Constants.LINK_CREDIT, "/credit/config/page/base/v/getPageTabRegionConfigDto", map, Map.class);
			
//			Map<String, Object> p = new HashMap<String, Object>();
//			p.put("productId", MapUtils.getString(params, "cityCode")+MapUtils.getString(params, "productCode"));
//			Map<String, Object> authMap = new HashMap<String, Object>();
//			List<String> authList = new ArrayList<String>();
//			authMap = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/user/authority/v/selectAuthorityByProductId", p, Map.class);
//			authList = CommonDataUtil.getUserDtoByUidAndMobile(getUserDto(request).getUid()).getAuthIds();
			
			//查询流水
			params.put("tblName", "tbl_cm_");
			List<Map<String,Object>> respDat = httpUtil.getList(Constants.LINK_CREDIT, "/credit/product/data/flow/base/v/list", params, Map.class);
			if(respDat!=null&&respDat.size()>0){
				for (Map<String, Object> map2 : respDat) {
					map2.put("createTimeStr", "系统提交时间："+MapUtils.getString(map2, "createTimeStr"));
					// 普通用户订单示例订单也不能点击节点详情
//					if(0!=user.getIsEnable()||orderNos.contains(orderNo)){					
//						map2.put("currentProcessId", "noAuth");
//					}
				}
			}
			List<Map<String,Object>> configlist = respData.getData();
			if(configlist.size()<7){
				RespHelper.setFailDataObject(resp, null, "数据库详情cmDetail配置缺失数量(小于7)");
				return resp;
			}
			RespDataObject<Map<String,Object>> respD = httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/product/data/list/base/v/select", params, Map.class);
			if(!"SUCCESS".equals(respD.getCode())){
				RespHelper.setFailDataObject(resp, null, "列表数据查询失败");
				return resp;
			}
			Map<String,Object> data = (Map<String, Object>) respD.getData();
			Map<String, Object> orderDeatil = new LinkedHashMap<String, Object>();
			String key="";
			orderDeatil.put(MapUtils.getString(configlist.get(0), "title"), "云按揭");
			key+=MapUtils.getString(configlist.get(0), "title")+",";
			orderDeatil.put(MapUtils.getString(configlist.get(1), "title"), data.get("source"));
			key+=MapUtils.getString(configlist.get(1), "title")+",";
			if(data.get("name")!=null){
				orderDeatil.put(MapUtils.getString(configlist.get(2), "title"), data.get("name"));
			}else{
				orderDeatil.put(MapUtils.getString(configlist.get(2), "title"), "-");
			}
			key+=MapUtils.getString(configlist.get(2), "title")+",";
			if(data.get("borrowingAmount")!=null){
				orderDeatil.put(MapUtils.getString(configlist.get(3), "title"), data.get("borrowingAmount")+"万");
			}else{
				orderDeatil.put(MapUtils.getString(configlist.get(3), "title"), "-");
			}
			key+=MapUtils.getString(configlist.get(3), "title")+",";
			if(data.get("loanLimit")!=null){
				orderDeatil.put(MapUtils.getString(configlist.get(4), "title"), data.get("loanLimit")+"月");
			}else{
				orderDeatil.put(MapUtils.getString(configlist.get(4), "title"), "-");
			}
			key+=MapUtils.getString(configlist.get(4), "title")+",";
			orderDeatil.put(MapUtils.getString(configlist.get(5), "title"), data.get("cooperativeAgencyName"));
			key+=MapUtils.getString(configlist.get(5), "title")+",";
			orderDeatil.put(MapUtils.getString(configlist.get(6), "title"), data.get("channelManagerName"));
			key+=MapUtils.getString(configlist.get(6), "title");
			if(data.get("createUid").equals(user.getUid())|| user.getUid().equals(data.get("channelManagerUid"))){
				retMap.put("isAcceptMember", true);
			}else {
				retMap.put("isAcceptMember", false);
			}
			//云按揭详情页是否展示图片上传按钮
			boolean flag = false;
			boolean preFlag = false;
			log.info("cityCode:"+data.get("processId"));
			log.info("processId:"+data.get("processId"));
			log.info("示例订单："+orderNos);
			int productId =Integer.parseInt(MapUtils.getString(data, "cityCode")+productCode); 
			flag = compareProcessAround(productId, "auditSuccess", data.get("processId").toString());
			preFlag = compareProcessAround(productId, "subMortgage", data.get("processId").toString());
			//普通用户订单和示例订单不可查看影像资料
			if(orderNos.contains(orderNo)||0!=user.getIsEnable()){
				preFlag = false;
			}
			if((user.getUid().equals(data.get("createUid"))||user.getUid().equals(data.get("channelManagerUid")))&&!flag&&preFlag){//查看并操作
				retMap.put("operate", true);
				retMap.put("see", true);
			}else if(preFlag){//查看
				retMap.put("operate", false);
				retMap.put("see", true);
			}else{//不显示查看按钮
				retMap.put("operate", false);
				retMap.put("see", false);
			}
			retMap.put("orderFlowList", respDat);
			retMap.put("order", orderDeatil);
			orderDeatil.put("keys", key);
			retMap.put("upper", orderDeatil);
			RespHelper.setSuccessDataObject(resp, retMap);
		} catch (Exception e) {
			e.printStackTrace();
			RespHelper.setFailDataObject(resp, null, RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	private Map<String, Object> setDataMap(Map<String, Object> dataMap,Map<String, Object> params){
		if(dataMap != null && !dataMap.isEmpty()){
			try {
				Map<String, Object> tempMap = (Map<String, Object>) MapUtils.getObject(params, MapUtils.getString(params, "tblName",""));
				for (String key : tempMap.keySet()) {
					dataMap.put(key, MapUtils.getObject(tempMap, key));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			dataMap = (Map<String, Object>) MapUtils.getObject(params, MapUtils.getString(params, "tblName",""));
		}
		return dataMap;
	}
	
	/**
	 * 保存数据
	 * @param pageTabConfigDto
	 * @param params
	 * @param dataMap
	 * @return
	 */
	private RespStatus saveData(PageTabConfigDto pageTabConfigDto,Map<String, Object> params,Map<String, Object> dataMap){
		if("影像资料".equals(pageTabConfigDto.getTitle())){
			return RespHelper.setSuccessRespStatus(new RespStatus());
		}
		Map<String, Object> tempParams = new HashMap<String, Object>();
		tempParams.put("updateUid", MapUtils.getString(params, "uid"));
		tempParams.put("orderNo", MapUtils.getString(params, "orderNo"));
		tempParams.put("productCode", MapUtils.getString(params, "productCode"));
		tempParams.put("cityCode", MapUtils.getString(params, "cityCode"));
		tempParams.put("tblName", MapUtils.getString(params, "tblName",""));
		tempParams.put("source", MapUtils.getString(params, "source",""));
		tempParams.put("addOrderType", params.get("addOrderType"));
		tempParams.put("otherData", MapUtils.getMap(dataMap, "otherTblName"));
		tempParams.put("data", dataMap);
		params.remove(MapUtils.getString(params, "tblName",""));
		return httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/product/data/base/v/save", tempParams);
	}
	
	public ReportDto getReport(OrderListDto orderListDto){
		ReportDto dto = new ReportDto();
		if(StringUtils.isNotBlank(orderListDto.getOrderNo())){
			DocumentsDto doc = new DocumentsDto();
			doc.setOrderNo(orderListDto.getOrderNo());
			doc = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/element/basics/v/detail", doc,DocumentsDto.class);
			dto.setCityCode(orderListDto.getCityCode());
			dto.setCityName(orderListDto.getCityName());
			dto.setAcceptMemberName(orderListDto.getAcceptMemberName());
			dto.setAcceptMemberUid(orderListDto.getAcceptMemberUid());
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
			if(!"请选择".equals(orderListDto.getCustomerTypeName())
					&&StringUtils.isNotBlank(orderListDto.getCustomerTypeName())){
				dto.setCustomerTypeName(orderListDto.getCustomerTypeName());
				dto.setCustomerType(orderListDto.getCustomerType()+"");
			}

		}
		return dto;
	}
	public void configPaymentReport(Map<String,Object> params,PageConfigDto pageConfigDto,String orderNo,OrderListDto orderListDto,UserDto user){
		PaymentReportDto obj = null;
		try {
			log.info("回款款报备orderNo:"+MapUtils.getString(params, "orderNo"));
			Map<String,Object> mm = new HashMap<String, Object>();
			mm.put("orderNo", MapUtils.getString(params, "orderNo"));
			obj = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/finance/paymentreport/v/detailByStatus", mm, PaymentReportDto.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		boolean isReportEdit = (null==obj);
		if(null!=obj){
			if(StringUtils.isNotBlank(obj.getReportEditRecordStr())){
				obj.setEditrecord(obj.getReportEditRecordStr());
			}
			if(StringUtils.isNotBlank(obj.getReportReplyRecordStr())){
				obj.setReplyrecord(obj.getReportReplyRecordStr());
			}
		} else if(null==obj&&StringUtils.isNotBlank(orderNo)){
			obj = getPaymentReport(orderListDto);
		}

		Map<String,Object> map = BeanToMapUtil.beanToMap(obj);
		if(obj!=null&&obj.getEstimateInLoanTime()!=null){
			map.put("estimateInLoanTimeStr", DateUtil.getDateByFmt(obj.getEstimateInLoanTime(), DateUtil.FMT_TYPE11));
		}
		for(PageTabConfigDto pageTabConfigDto : pageConfigDto.getPageTabConfigDtos()) {
			for (PageTabRegionConfigDto pageTabRegionConfigDto : pageTabConfigDto.getPageTabRegionConfigDtos()) {
				setSlData(pageTabRegionConfigDto, map);
			}
		}
		for(PageTabConfigDto pageTabConfigDto : pageConfigDto.getPageTabConfigDtos()) {
			for (PageTabRegionConfigDto pageTabRegionConfigDto : pageTabConfigDto.getPageTabRegionConfigDtos()) {
				List<PageTabRegionFormConfigDto> formList = new ArrayList<PageTabRegionFormConfigDto>();
				for (PageTabRegionFormConfigDto pageTabRegionFormConfigDto  : pageTabRegionConfigDto.getValueList().get(0)) {
						if(!pageTabRegionFormConfigDto.getKey().equals("estimateInLoanTimeStr")
								&&!"editrecord".equals(pageTabRegionFormConfigDto.getKey())
								&&!"replyrecord".equals(pageTabRegionFormConfigDto.getKey())){
							if(("customerTypeName".equals(pageTabRegionFormConfigDto.getKey())
									||"inLoanType".equals(pageTabRegionFormConfigDto.getKey()))
									&&StringUtils.isBlank(pageTabRegionFormConfigDto.getValue())){
							} else if(!pageTabRegionFormConfigDto.getKey().equals("remark")){
								pageTabRegionFormConfigDto.setType(1);
								pageTabRegionFormConfigDto.setIsReadOnly(2);
							}
						}

						//机构渠道经理不可修改,设置为机构渠道经理
						/*
						if(null!=orderListDto&&orderListDto.getAgencyId()>1&&pageTabRegionFormConfigDto.getKey().equals("channelManagerName")){
							pageTabRegionFormConfigDto.setType(1);
							pageTabRegionFormConfigDto.setIsReadOnly(2);
							String agencyChanlManUid = user.getAgencyChanlManUid();
							pageTabRegionFormConfigDto.setSpecialValue(agencyChanlManUid);
							String channelManagerName = CommonDataUtil.getUserDtoByUidAndMobile(agencyChanlManUid).getName();
							log.info("回款报备，取用户所属机构渠道经理Uid："+user.getAgencyChanlManUid());
							log.info("回款报备，取用户所属机构渠道经理："+channelManagerName);
							pageTabRegionFormConfigDto.setValue(channelManagerName);
						}*/
					//修改与回复记录为则不显示
					if((obj==null||StringUtils.isBlank(obj.getEditrecord()))&&"editrecord".equals(pageTabRegionFormConfigDto.getKey())){
						continue;
					}else if((obj==null||StringUtils.isBlank(obj.getReplyrecord()))&&"replyrecord".equals(pageTabRegionFormConfigDto.getKey())){
						continue;
					}else{
						formList.add(pageTabRegionFormConfigDto);
					}
				}
				pageTabRegionConfigDto.getValueList().set(0, formList);
			}
		}

	}
	public PaymentReportDto getPaymentReport(OrderListDto orderListDto){
		PaymentReportDto dto = new PaymentReportDto();
		if(null!=orderListDto&&StringUtils.isNotBlank(orderListDto.getOrderNo())){
			DocumentsDto doc = new DocumentsDto();
			doc.setOrderNo(orderListDto.getOrderNo());
			doc = httpUtil.getObject(Constants.LINK_CREDIT, "/credit/element/basics/v/detail", doc,DocumentsDto.class);
			dto.setCityCode(orderListDto.getCityCode());
			dto.setCityName(orderListDto.getCityName());
			dto.setAcceptMemberName(orderListDto.getAcceptMemberName());
			dto.setAcceptMemberUid(orderListDto.getAcceptMemberUid());
			dto.setProductCode(orderListDto.getProductCode());
			dto.setProductName(orderListDto.getProductName());
			dto.setCooperativeAgencyId(orderListDto.getCooperativeAgencyId());
			dto.setCooperativeAgencyName(orderListDto.getCooperativeAgencyName());
			dto.setChannelManagerName(orderListDto.getChannelManagerName());
			dto.setChannelManagerUid(orderListDto.getChannelManagerUid());
			dto.setCustomerName(orderListDto.getCustomerName());
			dto.setLoanAmount(orderListDto.getBorrowingAmount());
			dto.setBorrowingDays(orderListDto.getBorrowingDay());
			if(null!=doc&&null!=doc.getPaymentType()){
				dto.setInLoanType(doc.getPaymentType().getPaymentMode());
			}
			dto.setProcessId(orderListDto.getProcessId());
			dto.setState(orderListDto.getState());
			if(StringUtils.isNotBlank(orderListDto.getCustomerTypeName())
					&&!orderListDto.getCustomerTypeName().equals("请选择")) {
				dto.setCustomerTypeName(orderListDto.getCustomerTypeName());
				dto.setCustomerType(orderListDto.getCustomerType()+"");
			}
		}
		return dto;
	}
	
	  /**
     * 去除字符開頭和末尾逗號
     * @param img
     * @return
     */
	public String cleanImgUrl(String img){
        if(StringUtils.isBlank(img))return img;
        img = img.replaceAll(";",",");
        if(img.startsWith(",")&&img.length()>1){
            img = img.substring(1,img.length());
        }
        if(img.endsWith(",")&&img.length()>1){
            img = img.substring(0,img.length()-1);
        }
        return img;
    }
	
	//示例订单详情
	public Map<String, Object> exampleOrderDetail(String orderNo){
		Map<String, Object> retMap = new HashMap<String, Object>();
		List<Map<String,Object>> respDat = new ArrayList<Map<String,Object>>();
		Map<String, Object> orderDeatil = new LinkedHashMap<String, Object>();
		
		//交易类
		if(orderNo.equals("2016112415164100017")){
			Map<String,Object> map2 = new HashMap<String, Object>();
			map2.put("createTimeStr", "系统提交时间：2018-01-12 09:22:16");
			map2.put("currentProcessId", "noAuth");
			map2.put("currentProcessName", "提单");
			map2.put("handleName", "何佳");
			respDat.add(map2);
			map2 = new HashMap<String, Object>();
			map2.put("createTimeStr", "系统提交时间：2018-01-12 09:23:51");
			map2.put("currentProcessId", "noAuth");
			map2.put("currentProcessName", "分配订单");
			map2.put("handleName", "陈飞");
			respDat.add(map2);
			map2 = new HashMap<String, Object>();
			map2.put("createTimeStr", "系统提交时间：2018-01-12 09:27:39");
			map2.put("currentProcessId", "noAuth");
			map2.put("currentProcessName", "风控初审");
			map2.put("handleName", "何佳");
			respDat.add(map2);
			map2 = new HashMap<String, Object>();
			map2.put("createTimeStr", "系统提交时间：2018-01-12 09:28:39");
			map2.put("currentProcessId", "noAuth");
			map2.put("currentProcessName", "风控终审");
			map2.put("handleName", "何佳");
			respDat.add(map2);
			map2 = new HashMap<String, Object>();
			map2.put("createTimeStr", "系统提交时间：2018-01-12 09:29:19");
			map2.put("currentProcessId", "noAuth");
			map2.put("currentProcessName", "首席风险官审批");
			map2.put("handleName", "何佳");
			respDat.add(map2);
			map2 = new HashMap<String, Object>();
			map2.put("createTimeStr", "系统提交时间：2018-01-12 09:39:19");
			map2.put("currentProcessId", "noAuth");
			map2.put("currentProcessName", "法务审批");
			map2.put("handleName", "何佳");
			respDat.add(map2);
			map2 = new HashMap<String, Object>();
			map2.put("createTimeStr", "系统提交时间：2018-01-12 09:42:19");
			map2.put("currentProcessId", "noAuth");
			map2.put("currentProcessName", "推送金融机构");
			map2.put("handleName", "何佳");
			respDat.add(map2);
			map2 = new HashMap<String, Object>();
			map2.put("createTimeStr", "系统提交时间：2018-01-12 09:43:19");
			map2.put("currentProcessId", "noAuth");
			map2.put("currentProcessName", "公证");
			map2.put("handleName", "何佳");
			respDat.add(map2);
			map2 = new HashMap<String, Object>();
			map2.put("createTimeStr", "系统提交时间：2018-01-12 09:44:19");
			map2.put("currentProcessId", "noAuth");
			map2.put("currentProcessName", "面签");
			map2.put("handleName", "何佳");
			respDat.add(map2);
			map2 = new HashMap<String, Object>();
			map2.put("createTimeStr", "系统提交时间：2018-01-12 09:45:19");
			map2.put("currentProcessId", "noAuth");
			map2.put("currentProcessName", "资料审核");
			map2.put("handleName", "何佳");
			respDat.add(map2);
			map2 = new HashMap<String, Object>();
			map2.put("createTimeStr", "系统提交时间：2018-01-12 09:46:09");
			map2.put("currentProcessId", "noAuth");
			map2.put("currentProcessName", "资料推送");
			map2.put("handleName", "何佳");
			respDat.add(map2);
			map2 = new HashMap<String, Object>();
			map2.put("createTimeStr", "系统提交时间：2018-01-12 09:47:09");
			map2.put("currentProcessId", "noAuth");
			map2.put("currentProcessName", "资金审批");
			map2.put("handleName", "何佳");
			respDat.add(map2);
			map2 = new HashMap<String, Object>();
			map2.put("createTimeStr", "系统提交时间：2018-01-12 09:48:09");
			map2.put("currentProcessId", "noAuth");
			map2.put("currentProcessName", "指派还款专员");
			map2.put("handleName", "何佳");
			respDat.add(map2);
			map2 = new HashMap<String, Object>();
			map2.put("createTimeStr", "系统提交时间：2018-01-12 09:49:09");
			map2.put("currentProcessId", "noAuth");
			map2.put("currentProcessName", "要件校验");
			map2.put("handleName", "何佳");
			respDat.add(map2);
			map2 = new HashMap<String, Object>();
			map2.put("createTimeStr", "系统提交时间：2018-01-12 09:50:09");
			map2.put("currentProcessId", "noAuth");
			map2.put("currentProcessName", "申请放款");
			map2.put("handleName", "何佳");
			respDat.add(map2);
			map2 = new HashMap<String, Object>();
			map2.put("createTimeStr", "系统提交时间：2018-01-12 09:52:09");
			map2.put("currentProcessId", "noAuth");
			map2.put("currentProcessName", "收利息");
			map2.put("handleName", "何佳");
			respDat.add(map2);
			map2 = new HashMap<String, Object>();
			map2.put("createTimeStr", "系统提交时间：2018-01-12 09:54:09");
			map2.put("currentProcessId", "noAuth");
			map2.put("currentProcessName", "放款");
			map2.put("handleName", "何佳");
			respDat.add(map2);
			map2 = new HashMap<String, Object>();
			map2.put("createTimeStr", "系统提交时间：2018-01-12 09:55:09");
			map2.put("currentProcessId", "noAuth");
			map2.put("currentProcessName", "结清原贷款");
			map2.put("handleName", "何佳");
			respDat.add(map2);
			map2 = new HashMap<String, Object>();
			map2.put("createTimeStr", "系统提交时间：2018-01-12 09:56:09");
			map2.put("currentProcessId", "noAuth");
			map2.put("currentProcessName", "取证");
			map2.put("handleName", "何佳");
			respDat.add(map2);
			map2 = new HashMap<String, Object>();
			map2.put("createTimeStr", "系统提交时间：2018-01-12 09:57:09");
			map2.put("currentProcessId", "noAuth");
			map2.put("currentProcessName", "注销");
			map2.put("handleName", "何佳");
			respDat.add(map2);
			map2 = new HashMap<String, Object>();
			map2.put("createTimeStr", "系统提交时间：2018-01-12 09:58:09");
			map2.put("currentProcessId", "noAuth");
			map2.put("currentProcessName", "过户");
			map2.put("handleName", "何佳");
			respDat.add(map2);
			map2 = new HashMap<String, Object>();
			map2.put("createTimeStr", "系统提交时间：2018-01-12 09:59:09");
			map2.put("currentProcessId", "noAuth");
			map2.put("currentProcessName", "领新证");
			map2.put("handleName", "何佳");
			respDat.add(map2);
			map2 = new HashMap<String, Object>();
			map2.put("createTimeStr", "系统提交时间：2018-01-12 10:02:09");
			map2.put("currentProcessId", "noAuth");
			map2.put("currentProcessName", "抵押");
			map2.put("handleName", "何佳");
			respDat.add(map2);
			map2 = new HashMap<String, Object>();
			map2.put("createTimeStr", "系统提交时间：2018-01-12 10:03:09");
			map2.put("currentProcessId", "noAuth");
			map2.put("currentProcessName", "回款");
			map2.put("handleName", "何佳");
			respDat.add(map2);
			map2 = new HashMap<String, Object>();
			map2.put("createTimeStr", "系统提交时间：2018-01-12 10:04:09");
			map2.put("currentProcessId", "noAuth");
			map2.put("currentProcessName", "要件退还");
			map2.put("handleName", "何佳");
			respDat.add(map2);
			map2 = new HashMap<String, Object>();
			map2.put("createTimeStr", "系统提交时间：2018-01-12 10:05:09");
			map2.put("currentProcessId", "noAuth");
			map2.put("currentProcessName", "返佣");
			map2.put("handleName", "何佳");
			respDat.add(map2);
			map2 = new HashMap<String, Object>();
			map2.put("createTimeStr", "系统提交时间：2018-01-12 10:06:09");
			map2.put("currentProcessId", "noAuth");
			map2.put("currentProcessName", "已完结");
			map2.put("handleName", "何佳");
			respDat.add(map2);
			
			
			
			//交易类
			orderDeatil.put("城市", "深圳市");
			orderDeatil.put("业务类型", "债务置换贷款（交易类）");
			orderDeatil.put("客户姓名", "张立祥");
			orderDeatil.put("借款金额", "450万元");
			orderDeatil.put("借款期限", "25天");
			orderDeatil.put("合作机构", "晒晒机构");
			orderDeatil.put("渠道经理", "何佳");
			orderDeatil.put("结清原贷款时间", "2018-01-12");
			orderDeatil.put("结清原贷款地点", "深圳市龙岗区");
			orderDeatil.put("新贷款地点", "深圳市南山区");
			orderDeatil.put("放款时间", "2018-1-12");
			orderDeatil.put("预计回款时间", "2018-02-05");
			orderDeatil.put("keys", "城市,业务类型,客户姓名,借款金额,借款期限,合作机构,渠道经理,结清原贷款时间,结清原贷款地点,新贷款地点,放款时间,预计回款时间");
		}
		
		//非交易类
		if(orderNo.equals("2016102510124200000")){
			Map<String,Object> map2 = new HashMap<String, Object>();
			map2.put("createTimeStr", "系统提交时间：2018-01-12 09:22:16");
			map2.put("currentProcessId", "noAuth");
			map2.put("currentProcessName", "提单");
			map2.put("handleName", "何佳");
			respDat.add(map2);
			map2 = new HashMap<String, Object>();
			map2.put("createTimeStr", "系统提交时间：2018-01-12 09:23:51");
			map2.put("currentProcessId", "noAuth");
			map2.put("currentProcessName", "分配订单");
			map2.put("handleName", "陈飞");
			respDat.add(map2);
			map2 = new HashMap<String, Object>();
			map2.put("createTimeStr", "系统提交时间：2018-01-12 09:27:39");
			map2.put("currentProcessId", "noAuth");
			map2.put("currentProcessName", "风控初审");
			map2.put("handleName", "何佳");
			respDat.add(map2);
			map2 = new HashMap<String, Object>();
			map2.put("createTimeStr", "系统提交时间：2018-01-12 09:28:39");
			map2.put("currentProcessId", "noAuth");
			map2.put("currentProcessName", "风控终审");
			map2.put("handleName", "何佳");
			respDat.add(map2);
			map2 = new HashMap<String, Object>();
			map2.put("createTimeStr", "系统提交时间：2018-01-12 09:29:19");
			map2.put("currentProcessId", "noAuth");
			map2.put("currentProcessName", "首席风险官审批");
			map2.put("handleName", "何佳");
			respDat.add(map2);
			map2 = new HashMap<String, Object>();
			map2.put("createTimeStr", "系统提交时间：2018-01-12 09:39:19");
			map2.put("currentProcessId", "noAuth");
			map2.put("currentProcessName", "法务审批");
			map2.put("handleName", "何佳");
			respDat.add(map2);
			map2 = new HashMap<String, Object>();
			map2.put("createTimeStr", "系统提交时间：2018-01-12 09:42:19");
			map2.put("currentProcessId", "noAuth");
			map2.put("currentProcessName", "推送金融机构");
			map2.put("handleName", "何佳");
			respDat.add(map2);
			map2 = new HashMap<String, Object>();
			map2.put("createTimeStr", "系统提交时间：2018-01-12 09:43:19");
			map2.put("currentProcessId", "noAuth");
			map2.put("currentProcessName", "公证");
			map2.put("handleName", "何佳");
			respDat.add(map2);
			map2 = new HashMap<String, Object>();
			map2.put("createTimeStr", "系统提交时间：2018-01-12 09:44:19");
			map2.put("currentProcessId", "noAuth");
			map2.put("currentProcessName", "面签");
			map2.put("handleName", "何佳");
			respDat.add(map2);
			map2 = new HashMap<String, Object>();
			map2.put("createTimeStr", "系统提交时间：2018-01-12 09:45:19");
			map2.put("currentProcessId", "noAuth");
			map2.put("currentProcessName", "资料审核");
			map2.put("handleName", "何佳");
			respDat.add(map2);
			map2 = new HashMap<String, Object>();
			map2.put("createTimeStr", "系统提交时间：2018-01-12 09:46:09");
			map2.put("currentProcessId", "noAuth");
			map2.put("currentProcessName", "资料推送");
			map2.put("handleName", "何佳");
			respDat.add(map2);
			map2 = new HashMap<String, Object>();
			map2.put("createTimeStr", "系统提交时间：2018-01-12 09:47:09");
			map2.put("currentProcessId", "noAuth");
			map2.put("currentProcessName", "资金审批");
			map2.put("handleName", "何佳");
			respDat.add(map2);
			map2 = new HashMap<String, Object>();
			map2.put("createTimeStr", "系统提交时间：2018-01-12 09:48:09");
			map2.put("currentProcessId", "noAuth");
			map2.put("currentProcessName", "指派还款专员");
			map2.put("handleName", "何佳");
			respDat.add(map2);
			map2 = new HashMap<String, Object>();
			map2.put("createTimeStr", "系统提交时间：2018-01-12 09:49:09");
			map2.put("currentProcessId", "noAuth");
			map2.put("currentProcessName", "要件校验");
			map2.put("handleName", "何佳");
			respDat.add(map2);
			map2 = new HashMap<String, Object>();
			map2.put("createTimeStr", "系统提交时间：2018-01-12 09:50:09");
			map2.put("currentProcessId", "noAuth");
			map2.put("currentProcessName", "申请放款");
			map2.put("handleName", "何佳");
			respDat.add(map2);
			map2 = new HashMap<String, Object>();
			map2.put("createTimeStr", "系统提交时间：2018-01-12 09:52:09");
			map2.put("currentProcessId", "noAuth");
			map2.put("currentProcessName", "收利息");
			map2.put("handleName", "何佳");
			respDat.add(map2);
			map2 = new HashMap<String, Object>();
			map2.put("createTimeStr", "系统提交时间：2018-01-12 09:54:09");
			map2.put("currentProcessId", "noAuth");
			map2.put("currentProcessName", "放款");
			map2.put("handleName", "何佳");
			respDat.add(map2);
			map2 = new HashMap<String, Object>();
			map2.put("createTimeStr", "系统提交时间：2018-01-12 09:55:09");
			map2.put("currentProcessId", "noAuth");
			map2.put("currentProcessName", "结清原贷款");
			map2.put("handleName", "何佳");
			respDat.add(map2);
			map2 = new HashMap<String, Object>();
			map2.put("createTimeStr", "系统提交时间：2018-01-12 09:56:09");
			map2.put("currentProcessId", "noAuth");
			map2.put("currentProcessName", "取证");
			map2.put("handleName", "何佳");
			respDat.add(map2);
			map2 = new HashMap<String, Object>();
			map2.put("createTimeStr", "系统提交时间：2018-01-12 09:57:09");
			map2.put("currentProcessId", "noAuth");
			map2.put("currentProcessName", "注销");
			map2.put("handleName", "何佳");
			respDat.add(map2);
			map2 = new HashMap<String, Object>();
			map2.put("createTimeStr", "系统提交时间：2018-01-12 09:58:09");
			map2.put("currentProcessId", "noAuth");
			map2.put("currentProcessName", "过户");
			map2.put("handleName", "何佳");
			respDat.add(map2);
			map2 = new HashMap<String, Object>();
			map2.put("createTimeStr", "系统提交时间：2018-01-12 09:59:09");
			map2.put("currentProcessId", "noAuth");
			map2.put("currentProcessName", "领新证");
			map2.put("handleName", "何佳");
			respDat.add(map2);
			map2 = new HashMap<String, Object>();
			map2.put("createTimeStr", "系统提交时间：2018-01-12 10:02:09");
			map2.put("currentProcessId", "noAuth");
			map2.put("currentProcessName", "抵押");
			map2.put("handleName", "何佳");
			respDat.add(map2);
			map2 = new HashMap<String, Object>();
			map2.put("createTimeStr", "系统提交时间：2018-01-12 10:03:09");
			map2.put("currentProcessId", "noAuth");
			map2.put("currentProcessName", "回款");
			map2.put("handleName", "何佳");
			respDat.add(map2);
			map2 = new HashMap<String, Object>();
			map2.put("createTimeStr", "系统提交时间：2018-01-12 10:04:09");
			map2.put("currentProcessId", "noAuth");
			map2.put("currentProcessName", "要件退还");
			map2.put("handleName", "何佳");
			respDat.add(map2);
			map2 = new HashMap<String, Object>();
			map2.put("createTimeStr", "系统提交时间：2018-01-12 10:05:09");
			map2.put("currentProcessId", "noAuth");
			map2.put("currentProcessName", "返佣");
			map2.put("handleName", "何佳");
			respDat.add(map2);
			map2 = new HashMap<String, Object>();
			map2.put("createTimeStr", "系统提交时间：2018-01-12 10:06:09");
			map2.put("currentProcessId", "noAuth");
			map2.put("currentProcessName", "已完结");
			map2.put("handleName", "何佳");
			respDat.add(map2);
			
			
			
			//非交易类
			orderDeatil.put("城市", "深圳市");
			orderDeatil.put("业务类型", "债务置换贷款（非交易类）");
			orderDeatil.put("客户姓名", "秦程");
			orderDeatil.put("借款金额", "400万元");
			orderDeatil.put("借款期限", "45天");
			orderDeatil.put("合作机构", "晒晒机构");
			orderDeatil.put("渠道经理", "何佳");
			orderDeatil.put("结清原贷款时间", "2018-01-12");
			orderDeatil.put("结清原贷款地点", "深圳市龙岗区");
			orderDeatil.put("新贷款地点", "深圳市南山区");
			orderDeatil.put("放款时间", "2018-1-11");
			orderDeatil.put("预计回款时间", "2018-02-24");
			orderDeatil.put("keys", "城市,业务类型,客户姓名,借款金额,借款期限,合作机构,渠道经理,结清原贷款时间,结清原贷款地点,新贷款地点,放款时间,预计回款时间");
		}
		//云按揭
		if(orderNo.equals("2017110714552200049")){
			Map<String,Object> map2 = new HashMap<String, Object>();
			map2.put("createTimeStr", "系统提交时间：2018-01-01 11:11:11");
			map2.put("currentProcessId", "noAuth");
			map2.put("currentProcessName", "指派渠道经理");
			map2.put("handleName", "陈飞");
			respDat.add(map2);
			map2 = new HashMap<String, Object>();
			map2.put("createTimeStr", "系统提交时间：2018-01-01 11:12:11");
			map2.put("currentProcessId", "noAuth");
			map2.put("currentProcessName", "提交评估");
			map2.put("handleName", "陈飞");
			respDat.add(map2);
			map2 = new HashMap<String, Object>();
			map2.put("createTimeStr", "系统提交时间：2018-01-01 11:13:11");
			map2.put("currentProcessId", "noAuth");
			map2.put("currentProcessName", "评估");
			map2.put("handleName", "建行房贷通");
			respDat.add(map2);
			map2 = new HashMap<String, Object>();
			map2.put("createTimeStr", "系统提交时间：2018-01-01 11:14:11");
			map2.put("currentProcessId", "noAuth");
			map2.put("currentProcessName", "买卖双方信息");
			map2.put("handleName", "陈飞");
			respDat.add(map2);
			map2 = new HashMap<String, Object>();
			map2.put("createTimeStr", "系统提交时间：2018-01-01 11:15:11");
			map2.put("currentProcessId", "noAuth");
			map2.put("currentProcessName", "提交申请按揭");
			map2.put("handleName", "陈飞");
			respDat.add(map2);
			map2 = new HashMap<String, Object>();
			map2.put("createTimeStr", "系统提交时间：2018-01-01 11:16:11");
			map2.put("currentProcessId", "noAuth");
			map2.put("currentProcessName", "客户经理审核");
			map2.put("handleName", "建行房贷通");
			respDat.add(map2);
			map2 = new HashMap<String, Object>();
			map2.put("createTimeStr", "系统提交时间：2018-01-01 11:17:11");
			map2.put("currentProcessId", "noAuth");
			map2.put("currentProcessName", "审批前材料准备");
			map2.put("handleName", "建行房贷通");
			respDat.add(map2);
			map2 = new HashMap<String, Object>();
			map2.put("createTimeStr", "系统提交时间：2018-01-01 11:18:11");
			map2.put("currentProcessId", "noAuth");
			map2.put("currentProcessName", "审批通过");
			map2.put("handleName", "建行房贷通");
			respDat.add(map2);
			map2 = new HashMap<String, Object>();
			map2.put("createTimeStr", "系统提交时间：2018-01-01 11:19:11");
			map2.put("currentProcessId", "noAuth");
			map2.put("currentProcessName", "准备房产过户和抵押");
			map2.put("handleName", "建行房贷通");
			respDat.add(map2);
			map2 = new HashMap<String, Object>();
			map2.put("createTimeStr", "系统提交时间：2018-01-01 11:20:11");
			map2.put("currentProcessId", "noAuth");
			map2.put("currentProcessName", "预约抵押登记");
			map2.put("handleName", "陈飞");
			respDat.add(map2);
			map2 = new HashMap<String, Object>();
			map2.put("createTimeStr", "系统提交时间：2018-01-01 11:21:11");
			map2.put("currentProcessId", "noAuth");
			map2.put("currentProcessName", "取证抵押");
			map2.put("handleName", "陈飞");
			respDat.add(map2);
			map2 = new HashMap<String, Object>();
			map2.put("createTimeStr", "系统提交时间：2018-01-05 14:00:00");
			map2.put("currentProcessId", "noAuth");
			map2.put("currentProcessName", "放款");
			map2.put("handleName", "建行房贷通");
			respDat.add(map2);
			
			orderDeatil.put("业务类型", "云按揭");
			orderDeatil.put("订单来源", "四海APP");
			orderDeatil.put("贷款申请人", "陈春燕");
			orderDeatil.put("贷款金额", "300万");
			orderDeatil.put("贷款期限", "250月");
			orderDeatil.put("合作机构", "晒晒机构");
			orderDeatil.put("渠道经理", "陈飞");
			orderDeatil.put("keys", "业务类型,订单来源,贷款申请人,贷款金额,贷款期限,合作机构,渠道经理");
			
		}

		retMap.put("operate", false);
		retMap.put("see", false);
		retMap.put("orderFlowList", respDat);
		retMap.put("order", orderDeatil);
		retMap.put("upper", orderDeatil);
		return retMap;
	}
	
	// 将时间戳转为字符串  
	public String getStrTime(String cc_time) {  
		long lTime = Long.parseLong(cc_time);  //int放不下的，用long
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		String sLastTime=sdf.format(lTime);
		return sLastTime;  
	} 
	
}
