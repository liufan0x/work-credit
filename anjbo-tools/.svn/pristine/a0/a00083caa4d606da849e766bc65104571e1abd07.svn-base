package com.anjbo.controller.tools;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.bean.enquiry.EnquiryAssessResp;
import com.anjbo.bean.enquiry.EnquiryReport;
import com.anjbo.bean.enquiry.LimitApplyReq;
import com.anjbo.bean.enquiry.ReportApplyReq;
import com.anjbo.bean.enquiry.ReportReq;
import com.anjbo.bean.tools.AssessReportDto;
import com.anjbo.bean.tools.EnquiryAssessDto;
import com.anjbo.bean.tools.EnquiryDto;
import com.anjbo.common.CommEnum;
import com.anjbo.common.Constants;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.common.TZCEnquiryParseHelper;
import com.anjbo.common.TzcReportEnum;
import com.anjbo.service.mort.UserService;
import com.anjbo.service.tools.AssessReportService;
import com.anjbo.service.tools.EnquiryAssessService;
import com.anjbo.service.tools.EnquiryService;
import com.anjbo.service.tools.TZCEnquiryApiService;
import com.anjbo.utils.UidUtil;
import com.anjbo.utils.ConfigUtil;
import com.anjbo.utils.HttpUtil;
import com.anjbo.utils.MD5;
import com.anjbo.utils.MapAndBean;
import com.anjbo.utils.StringUtil;


/**
 * 申请评估
 * 
 * @author Administrator
 * 
 */
@Component
@Controller
@RequestMapping("/tools/enquiryAssess")
public class EnquiryAssessController{

	private static final Log log = LogFactory.getLog(EnquiryAssessController.class);
	@Resource
	private EnquiryAssessService enquiryAssessService;
	@Resource
	private EnquiryService enquiryService;
	@Resource
	private UserService userService; 
	
	@Resource
	private TZCEnquiryApiService tzcEnquiryApiService;
	@Resource
	private AssessReportService assessReportService;
	
	/**
	 * 创建评估记录
	 * 
	 * @user jiangyq
	 * @date 2017年10月31日 上午9:08:18 
	 * @param param
	 * @return
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws InstantiationException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping(value = "/v/addEnquiryAccess")
	@ResponseBody
	public RespDataObject<Map<String, Object>> addEnquiryAccess(@RequestBody Map<String, Object> param) throws IllegalAccessException, InstantiationException, InvocationTargetException, IntrospectionException {
		EnquiryAssessDto assessDto = new EnquiryAssessDto();
		assessDto = (EnquiryAssessDto) MapAndBean.MapToBean(EnquiryAssessDto.class, param);
		RespDataObject<Map<String, Object>> resp = new RespDataObject<Map<String, Object>>();
		resp.setCode(RespStatusEnum.FAIL.getCode());
		resp.setMsg(RespStatusEnum.ERRORTWO.getMsg());
		try {
			Map<String, Object> retMap = null;
			EnquiryDto enquiryDto = enquiryService.selectEnquiry(assessDto.getEnquiryId());
			retMap = MapAndBean.beanToMap(enquiryDto);
			List<Map<String, Object>> enquiryReportList = enquiryService.selectEnquiryReportList(assessDto.getEnquiryId());
			for (Map<String, Object> map : enquiryReportList) {
				if(MapUtils.getString(map, "company").equals(Constants.SL_ID)){
					map.put("company", "世联");
				}else if(MapUtils.getString(map, "company").equals(Constants.TZC_ID)){
					map.put("company", "同致诚");
				}else if(MapUtils.getString(map, "company").equals(Constants.GJ_ID)){
					map.put("company", "国策");
				}else if(MapUtils.getString(map, "company").equals(Constants.YPG_ID)){
					map.put("company", "云评估");
				}
			}
			
			Map<String, Object> map1 = enquiryReportList.get(0);
			Double area = (Double) retMap.get("area");
			Double totalPrice = ((BigDecimal) map1.get("totalPrice")).doubleValue();//评估总价
			assessDto.setEnquiryTotalPrice(totalPrice==null?0:totalPrice);
			assessDto.setSerialId(enquiryDto.getSerialid());
			assessDto.setArea(area==null?0:area);
			assessDto.setPropertyName((String)map1.get("propertyName")+(String)map1.get("buildingName")+(String)map1.get("roomName"));
			if(assessDto.getIsEnquiryManager()){
				assessDto.setDistrict("");
			}else{
				assessDto.setDistrict(StringUtils.isBlank(enquiryDto.getDistrict())?"未知":enquiryDto.getDistrict());
			}
			String id = UidUtil.getUuid();
			assessDto.setId(id);
			assessDto.setStatus(CommEnum.ASSESS_STATUS_0.getCode());
			assessDto.setApplyTime(new Date());//免费申请时间即创建时间
			assessDto.setEnable(1);
			assessDto.setCreateTime(new Date());
			
			enquiryAssessService.addEnquiryAccess(assessDto);
			
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("id", assessDto.getId());
			dataMap.put("userid", assessDto.getUserid());
			dataMap.put("city", assessDto.getCity());
			dataMap.put("district", assessDto.getDistrict());
			dataMap.put("propertyName", assessDto.getPropertyName());
			dataMap.put("bankName", assessDto.getBankName());
			dataMap.put("area", assessDto.getArea());
			dataMap.put("dealAmount", assessDto.getDealAmount());
			dataMap.put("userName", assessDto.getUserName());
			dataMap.put("loanAmount", assessDto.getLoanAmount());
			dataMap.put("enquiryTotalPrice", assessDto.getEnquiryTotalPrice());
			dataMap.put("type", assessDto.getType());
			dataMap.put("mobile", assessDto.getMobile());
			dataMap.put("status", assessDto.getStatus());
			resp.setData(dataMap);
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			log.error("创建评估记录异常", e);
			if (UncategorizedSQLException.class.getName().equals(e.getClass().getName())) {
				resp.setMsg("请输入正确的姓名");
				resp.setCode(RespStatusEnum.FAIL.getCode());
			} else {
				resp.setMsg(RespStatusEnum.ERRORTWO.getMsg());
				resp.setCode(RespStatusEnum.FAIL.getCode());
			}
		}
		return resp;
	}
	
	/**
	 * 渠道经理提交评估
	 * 
	 * @user jiangyq
	 * @date 2017年10月31日 上午9:08:18 
	 * @param param
	 * @return
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws InstantiationException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping(value = "/v/submitEnquiryAssess")
	@ResponseBody
	public RespDataObject<Map<String, Object>> submitEnquiryAssess(@RequestBody Map<String, Object> param) throws IllegalAccessException, InstantiationException, InvocationTargetException, IntrospectionException {
		RespDataObject<Map<String, Object>> resp = new RespDataObject<Map<String, Object>>();
		resp.setCode(RespStatusEnum.FAIL.getCode());
		resp.setMsg(RespStatusEnum.ERRORTWO.getMsg());
		try {
			
			String enquiryAssessId = MapUtils.getString(param, "enquiryAssessId");
			double dealAmount = MapUtils.getDoubleValue(param, "dealAmount");
			double loanAmount = MapUtils.getDoubleValue(param, "loanAmount");
			
			EnquiryAssessDto assess = enquiryAssessService.findById(enquiryAssessId);
			assess.setId(assess.getSerialId());//申请评估要传对应询价的流水号
			assess.setDealAmount(dealAmount);
			assess.setLoanAmount(loanAmount);
			RespStatus respObj = enquiryAssessService.tzcEnquiryAccess(assess);
			log.info("essess resp:"+respObj.getMsg());
			int code = CommEnum.ASSESS_STATUS_5.getCode();
			if(RespStatusEnum.SUCCESS.getCode().equals(respObj.getCode())){//返回true说明申请成功
				code= CommEnum.ASSESS_STATUS_1.getCode();
			}
			//enquiryAssessService.updateApplyTime(enquiryAssessId);
			//enquiryAssessService.updateStatus(enquiryAssessId, code,respObj.getMsg());
			enquiryAssessService.updateAssessByLimitApply(enquiryAssessId, code, respObj.getMsg(), dealAmount, loanAmount);
			
			Map<String, Object> dataMap = new HashMap<String, Object>();
			//dataMap.put("id", id);
			resp.setData(dataMap);
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			log.error("创建评估记录异常", e);
			resp.setMsg(RespStatusEnum.ERRORTWO.getMsg());
			resp.setCode(RespStatusEnum.FAIL.getCode());
		}
		return resp;
	}
	
	/**
	 * 查询评估记录列表
	 * 
	 * @user jiangyq
	 * @date 2017年10月31日 上午10:25:27 
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/v/selEnquiryAssess")
	public RespDataObject<List<Map<String, Object>>> selEnquiryAssess(@RequestBody Map<String, Object> param){
		RespDataObject<List<Map<String, Object>>> resp  = new RespDataObject<List<Map<String, Object>>>();
		if(StringUtil.isEmpty(MapUtils.getString(param, "uid"))){
			resp.setMsg(RespStatusEnum.PARAMETER_ERROR.getMsg());
			resp.setCode(RespStatusEnum.PARAMETER_ERROR.getCode());
			return  resp;
		}
		String uid = MapUtils.getString(param, "uid");
		String district = MapUtils.getString(param, "district");
		Integer pagesize = MapUtils.getInteger(param, "pagesize");
		Integer pageindex = MapUtils.getInteger(param, "pageindex");
		Boolean isEnquiryManager = MapUtils.getBoolean(param, "isEnquiryManager");
		
		try {
			List<EnquiryAssessDto> list = new ArrayList<EnquiryAssessDto>();
			if (isEnquiryManager) {
				String [] districts = district.split(",");
				//获取渠道经理对应的区域
				list = enquiryAssessService.selEnquiryAssessByDistrict(uid, districts, pagesize, pageindex);
			}else{
				list = enquiryAssessService.selEnquiryAssessAll(uid, pagesize, pageindex);   
			}
			
			List<Map<String, Object>> listMap = new ArrayList<Map<String,Object>>(); 
			for (EnquiryAssessDto enquiryAssessDto : list) {
				//只显示最大成交价
				double tzcAssessAmount = enquiryAssessDto.getAssessAmount();
				double slAssessAmount = enquiryAssessDto.getsLAssessAmount();
				double ddAssessAmount = enquiryAssessDto.getdDAssessAmount();
				double max = tzcAssessAmount>slAssessAmount?tzcAssessAmount:slAssessAmount;
				max = max>ddAssessAmount?max:ddAssessAmount;
				enquiryAssessDto.setAssessAmount(max);
				enquiryAssessDto.setIsEnquiryManager(isEnquiryManager);
				listMap.add(MapAndBean.beanToMap(enquiryAssessDto));
			}
			
			resp.setData(listMap);
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
		} catch (Exception e) {
			log.error("查询评估列表异常", e);
			resp.setMsg(RespStatusEnum.ERRORONE.getMsg());
			resp.setCode(RespStatusEnum.FAIL.getCode());
		}
		return resp;
	}
	
	/**
	 * 查询评估记录详情
	 * 
	 * @user jiangyq
	 * @date 2017年10月31日 上午10:42:09 
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/v/selEnquiryAssessDetail")
	@ResponseBody
	public RespDataObject<Map<String, Object>> selEnquiryAssessDetail(@RequestBody Map<String, String> param) {
		RespDataObject<Map<String, Object>> status = new RespDataObject<Map<String, Object>>();
		if(StringUtils.isBlank(MapUtils.getString(param, "enquiryAssessId"))){
			status.setMsg(RespStatusEnum.PARAMETER_ERROR.getMsg());
			status.setCode(RespStatusEnum.PARAMETER_ERROR.getCode());
			return status;
		}
		
		Map<String, Object> retMap = null;
		try {
			String id = MapUtils.getString(param, "enquiryAssessId");
			
			EnquiryAssessDto dto = enquiryAssessService
					.selEnquirAssessDetail(id);
			if(dto.getIsRead()==0){
				this.enquiryAssessService.updateAssessIsRead(id, 1);
			}
			
			Double amount = MapUtils.getDouble(param, "amount");//PriceCache.getPricemap().get(PriceCache.PRICE_ASSESSREPORT);
			dto.setAmount(amount);
			String discount = MapUtils.getString(param, "discount");//ConfigUtil.getStringValue(Enums.GLOBAL_CONFIG_KEY.ASSESSAPPLY_DISCOUNT.toString());
			dto.setDiscount(discount);
			if(StringUtils.isNotEmpty(dto.getContent())){
				dto.setContent(dto.getContent().replace("0OK","").replace("OK", ""));
			}else{
				dto.setContent("");
			}
			
			retMap = MapAndBean.beanToMap(dto);
			status.setData(retMap);
			status.setMsg(RespStatusEnum.SUCCESS.getMsg());
			status.setCode(RespStatusEnum.SUCCESS.getCode());
		} catch (Exception e) {
			log.error("查询评估记录详情异常", e);
			status.setMsg(RespStatusEnum.FAIL.getMsg());
			status.setCode(RespStatusEnum.FAIL.getCode());
		}
		return status;
	}
	
	
	/**
	 * 申请评估报告
	 * 
	 * @user jiangyq
	 * @date 2017年10月31日 上午11:01:29 
	 * @param request
	 * @param response
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/v/report")
	public @ResponseBody
	RespDataObject<Map<String,Object>> report(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> param){
		
		RespDataObject<Map<String,Object>> status = new RespDataObject<Map<String,Object>>();
		try {
			
			String enquiryAssessId = MapUtils.getString(param, "orderNo");
			
			if(StringUtil.isEmpty(enquiryAssessId)){
				status.setCode(RespStatusEnum.FAIL.getCode());
				status.setMsg(RespStatusEnum.FAIL.getCode());
				return status;
			}
			
			EnquiryAssessDto assess = enquiryAssessService.findById(enquiryAssessId);
			if (assess == null) {
				log.info("enquiryAssess-->assess--id:" + enquiryAssessId + "找不到记录");
				status.setCode(RespStatusEnum.FAIL.getCode());
				status.setMsg(RespStatusEnum.FAIL.getCode());
				return status;
			}
			
			RespDataObject<AssessReportDto> resp = assessReportService.insertAssessReport(param);
			
			if (!RespStatusEnum.SUCCESS.getCode().equals(resp.getCode())) {
				status.setCode(resp.getCode());
				status.setMsg(resp.getCode());
				return status;
			}
			
			AssessReportDto assessReportDto = resp.getData();
			
			Map<String, String> params = new HashMap<String, String>();
			params.put("bankName", assessReportDto.getBankName());
			params.put("serialId", assess.getSerialId());
			params.put("clientManager", assessReportDto.getClientManager());
			params.put("clientManagerTel", assessReportDto.getClientManagerTel());
			params.put("photographPerson", assessReportDto.getPhotographPerson());
			params.put("photographPersonTel", assessReportDto.getPhotographPersonTel());
			params.put("enquiryAssessId", enquiryAssessId);
			params.put("expressAddr", assessReportDto.getAddress());
			params.put("bookFile", assessReportDto.getHouseCard());
			
			
			RespStatus rdo = enquiryAssessService.tzcWebReportApply(params);
			if (RespStatusEnum.SUCCESS.getCode().equals(rdo.getCode())) {// 申请成功更新数据状态
				this.enquiryAssessService.updateApplyReportTime(enquiryAssessId);
				this.enquiryAssessService.updateStatus(enquiryAssessId, CommEnum.ASSESS_STATUS_3.getCode(), TzcReportEnum.ASSESS_REPORT_STATUS_1.getName());
				status.setCode(RespStatusEnum.SUCCESS.getCode());
				status.setMsg(RespStatusEnum.SUCCESS.getMsg());
			} else {// 失败情况记录状态
				this.enquiryAssessService.updateStatus(enquiryAssessId, CommEnum.ASSESS_STATUS_6.getCode(), rdo.getMsg());
				return status;
			}
			// 发邮件
			//sendEmailAndMsg(assess, "您的评估报告申请已收到,请耐心等待结果通知.", "申请评估报告通知");

			
			/*this.enquiryAssessService.updateApplyReportTime(enquiryAssessId);
			this.enquiryAssessService.updateStatus(enquiryAssessId, CommEnum.ASSESS_STATUS_3.getCode(),"");*/
			
			assess.setStatus(CommEnum.ASSESS_STATUS_3.getCode());
			
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("id", enquiryAssessId);
			dataMap.put("userid", assess.getUserid());
			dataMap.put("city", assess.getCity());
			dataMap.put("district", assess.getDistrict());
			dataMap.put("propertyName", assess.getPropertyName());
			dataMap.put("bankName", assess.getBankName());
			dataMap.put("area", assess.getArea());
			dataMap.put("dealAmount", assess.getDealAmount());
			dataMap.put("userName", assess.getUserName());
			dataMap.put("loanAmount", assess.getLoanAmount());
			dataMap.put("enquiryTotalPrice", assess.getEnquiryTotalPrice());
			dataMap.put("type", assess.getType());
			dataMap.put("mobile", assess.getMobile());
			dataMap.put("status", assess.getStatus());
			status.setData(dataMap);
			status.setCode(RespStatusEnum.SUCCESS.getCode());
			status.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			// e.printStackTrace();
			log.info(e.getMessage());
			status.setCode(RespStatusEnum.FAIL.getCode());
			status.setMsg(RespStatusEnum.FAIL.getMsg());
		}
		
		return status;
	}

	/**
	 * 评估结果服务端接口
	 * 
	 * @return
	 */
	@RequestMapping(value = "/kgLimitApplyResult")
	@ResponseBody
	public RespStatus kgLimitApplyResult(HttpServletRequest request, HttpServletResponse response,@RequestBody LimitApplyReq req) {
		RespStatus resp = new RespStatus();
		resp.setCode(RespStatusEnum.FAIL.getCode());
		resp.setMsg(RespStatusEnum.FAIL.getMsg());
		String pgsqId = req.getPgsqId();
		String result = req.getResult();
		String origin = req.getOrigin();
		try {
			log.info("kgLimitApplyResult:pgsqId=" + pgsqId + ",result="
					+ result + ",origin=" + origin);
			String realRs = StringUtil.getFromBASE64(result, "utf-8");
			log.info("kgReportApply:realRs=" + realRs);
			String originTemp = Constants.TZC_SECRET_KEY + realRs;
			String rs = MD5.MD5Encode(originTemp);
			log.info("kgReportApply:md5=" + rs.toUpperCase());
			if (!rs.toUpperCase().equals(origin)) {
				resp.setMsg("身份校验失败!");
				return resp;
			}
			EnquiryAssessDto ea = enquiryAssessService.findBySerialid(pgsqId);
			if(ea==null){
				resp.setMsg("pgsqId="+pgsqId+"数据已不存在");
				return resp;
			}
//			String id = "";
			EnquiryAssessResp assResp = TZCEnquiryParseHelper
					.parseEnquiryAssessResp(realRs);
			log.info("kgLimitApplyResult-" + realRs);
			if (assResp != null) {
				if (assResp.getErrCode() == 0) {// 成功说明评估结果已出,调用询价轮询接口获取新的评估价格
					ReportReq report = new ReportReq("1", pgsqId);
					List<EnquiryReport> enquiryReports = tzcEnquiryApiService
							.queryEnquiryReport(report);
					if (CollectionUtils.isNotEmpty(enquiryReports)) {
						log.info("enquiryReports.size:"+enquiryReports.size()+",==pgsqId:"+pgsqId+",返回金额:"+enquiryReports.get(0).getTotalPrice());
						for (EnquiryReport rep : enquiryReports) {
							if(rep.getTotalPrice()>0){
								ea.setAssessAmount(rep.getTotalPrice());
								ea.setTzcNetPrice(rep.getNetPrice());
								break;
							}
						}
					}else{
						return resp;
					}
				} else {
					
					enquiryAssessService.updateStatus(ea.getId(), CommEnum.ASSESS_STATUS_5.getCode(), assResp.getErrMsg());
					
					resp.setCode(RespStatusEnum.SUCCESS.getCode());
					resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
					return resp;
				}

//				ea.setStatus(CommEnum.ASSESS_STATUS_2.getCode());
				/**2015-12-28开始人工干预估价 limh**/
				//ea.setStatus(CommEnum.ASSESS_STATUS_1.getCode());
				/**2015-12-28开始人工干预估价 limh**/
//				int enquiryId = this.enquiryAssessService
//						.findEnquiryIdBySerialid(pgsqId);
//				id = this.enquiryAssessService.findIdByEnquiryId(enquiryId);
//				if (StringUtil.isEmpty(id) || "0".equals(id)) {
//					log.info("kgLimitApplyResult:pgsqId=" + pgsqId + "对应的询价id"
//							+ enquiryId + "不存在");
//					return resp;
//				}
				ea.setContent(assResp.getErrMsg());
//				ea.setId(id);
				ea.setTzcAssessStatus(2);//已评估
				ea.setStatus(CommEnum.ASSESS_STATUS_2.getCode());
				this.enquiryAssessService.updateAccessByApply(ea);
				resp.setCode(RespStatusEnum.SUCCESS.getCode());
				resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
			}
			
			String url = ConfigUtil.getStringValue("ENQUIRY_ASSESS_NOTIFY_URL")+"/mortgage/enquiryAssess/toolsLimitApplyResultNotify";
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("id", ea.getId());
			dataMap.put("userid", ea.getUserid());
			dataMap.put("city", ea.getCity());
			dataMap.put("district", ea.getDistrict());
			dataMap.put("propertyName", ea.getPropertyName());
			dataMap.put("bankName", ea.getBankName());
			dataMap.put("area", ea.getArea());
			dataMap.put("dealAmount", ea.getDealAmount());
			dataMap.put("userName", ea.getUserName());
			dataMap.put("loanAmount", ea.getLoanAmount());
			dataMap.put("enquiryTotalPrice", ea.getEnquiryTotalPrice());
			dataMap.put("type", ea.getType());
			dataMap.put("mobile", ea.getMobile());
			dataMap.put("status", ea.getStatus());
			String res = HttpUtil.jsonPost(url, dataMap);
			log.info("评估回调通知ENQUIRY_ASSESS结果:" + res);
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
	
	
	/**
	 * 申请评估报告结果服务端接口
	 * 
	 * @return
	 */
	@RequestMapping(value = "/kgReportApply")
	@ResponseBody
	public RespStatus kgReportApply(HttpServletRequest request, HttpServletResponse response,@RequestBody ReportApplyReq req) {
		RespStatus resp = new RespStatus();
		resp.setCode(RespStatusEnum.FAIL.getCode());
		resp.setMsg(RespStatusEnum.FAIL.getMsg());
		try {
			String pgsqId = req.getPgsqId();
			String enquiryAssessId = req.getEnquiryAssessId();
			String progressId = req.getProgressId();
			String expressOrderNO = req.getExpressOrderNO();
			String expressCompany = req.getExpressCompany();
			String origin = req.getOrigin();//身份校验码
			
			//身份校验码=约定MD5加密key+评估报告Id+流程Id+快递单号+快递公司+当天时间 并用md5加密后结果转大写
			StringBuffer param = new StringBuffer(Constants.TZC_SECRET_KEY);
			param.append(pgsqId);
			param.append(enquiryAssessId);
			param.append(progressId);
			param.append(expressOrderNO);
			param.append(expressCompany);
			param.append(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			String tempOrigin = MD5.MD5EncodeUTF(param.toString());//身份校验码进行MD5加密
			tempOrigin = tempOrigin.toUpperCase();//结果转大写
			if(!tempOrigin.equals(origin)){
				resp.setMsg("身份校验失败!");
				return resp;
			}
			
			EnquiryAssessDto ea = new EnquiryAssessDto();
			Map<String, Object> dataMap = new HashMap<String, Object>();
			String msg = TzcReportEnum.getNameByCode(progressId);
			if(StringUtils.isBlank(msg)){
				return resp;
			}
			
			if("4".equals(progressId)){
				
				ea.setStatus(CommEnum.ASSESS_STATUS_4.getCode());
				EnquiryAssessDto ea2 = this.enquiryAssessService.findById(enquiryAssessId);
				
				if (ea2 == null) {
					log.info("kgReportApply:enquiryAssessId=" + enquiryAssessId + "对应的评估记录不存在");
					return resp;
				}
				
				this.enquiryAssessService.updateStatus(enquiryAssessId, ea.getStatus(), msg);
				this.enquiryAssessService.updateAssessIsRead(enquiryAssessId, 0);
				resp.setCode(RespStatusEnum.SUCCESS.getCode());
				resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
				
				dataMap.put("id", enquiryAssessId);
				dataMap.put("userid", ea.getUserid());
				dataMap.put("city", ea.getCity());
				dataMap.put("district", ea.getDistrict());
				dataMap.put("propertyName", ea.getPropertyName());
				dataMap.put("bankName", ea.getBankName());
				dataMap.put("area", ea.getArea());
				dataMap.put("dealAmount", ea.getDealAmount());
				dataMap.put("userName", ea.getUserName());
				dataMap.put("loanAmount", ea.getLoanAmount());
				dataMap.put("enquiryTotalPrice", ea.getEnquiryTotalPrice());
				dataMap.put("type", ea.getType());
				dataMap.put("mobile", ea.getMobile());
				dataMap.put("status", ea.getStatus());
				
			} else {
				
				enquiryAssessService.updateStatus(enquiryAssessId, CommEnum.ASSESS_STATUS_4.getCode(), msg);
				resp.setCode(RespStatusEnum.SUCCESS.getCode());
				resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
				return resp;
			}
			
			assessReportService.updateAssessReportProgressId(enquiryAssessId, Integer.parseInt(progressId));
			
			String url = ConfigUtil.getStringValue("ENQUIRY_ASSESS_NOTIFY_URL")+"/mortgage/enquiryAssess/toolsReportApplyNotify";
			String res = HttpUtil.jsonPost(url, dataMap);
			log.info("评估报告回调通知ENQUIRY_ASSESS结果:" + res);
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
	
	/**
	 * 删除评估记录
	 * 
	 * @user jiangyq
	 * @date 2017年10月31日 上午11:32:00 
	 * @param request
	 * @param response
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/v/delEnquiryAssess")
	public @ResponseBody
	RespDataObject<Map<String,Object>> delEnquiryAssess(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, String> param){
		RespDataObject<Map<String,Object>> status = new RespDataObject<Map<String,Object>>();
		try {
			
			String enquiryAssessId = param.get("enquiryAssessId");
			
			if(StringUtil.isEmpty(enquiryAssessId)){
				status.setCode(RespStatusEnum.FAIL.getCode());
				status.setMsg(RespStatusEnum.FAIL.getCode());
				return status;
			}
			
			String [] enquiryAssessIds = enquiryAssessId.split(",");
			for (int i = 0; i < enquiryAssessIds.length; i++) {
				String id=enquiryAssessIds[i];
				enquiryAssessService.delAssessById(id);
			}
			
			status.setCode(RespStatusEnum.SUCCESS.getCode());
			status.setMsg(RespStatusEnum.SUCCESS.getMsg());
			
		} catch (Exception e) {
			// e.printStackTrace();
			log.info(e.getMessage());
			status.setCode(RespStatusEnum.FAIL.getCode());
			status.setMsg(RespStatusEnum.FAIL.getMsg());
		}
		
		return status;
	}
	
	/**
	 * 关闭评估记录
	 * 
	 * @user jiangyq
	 * @date 2017年10月31日 上午11:32:00 
	 * @param request
	 * @param response
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/v/closeEnquiryAssess")
	public @ResponseBody
	RespDataObject<Map<String,Object>> closeEnquiryAssess(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, String> param){
		RespDataObject<Map<String,Object>> status = new RespDataObject<Map<String,Object>>();
		try {
			
			String enquiryAssessId = param.get("enquiryAssessId");
			
			if(StringUtil.isEmpty(enquiryAssessId)){
				status.setCode(RespStatusEnum.FAIL.getCode());
				status.setMsg(RespStatusEnum.FAIL.getCode());
				return status;
			}
			
			String [] enquiryAssessIds = enquiryAssessId.split(",");
			for (int i = 0; i < enquiryAssessIds.length; i++) {
				String id = enquiryAssessIds[i];
				//this.enquiryAssessService.updateStatus(id, CommEnum.ASSESS_STATUS_7.getCode(), "");
				this.enquiryAssessService.updateIsClose(id, CommEnum.ASSESS_CLOSE.getCode());
			}
			
			status.setCode(RespStatusEnum.SUCCESS.getCode());
			status.setMsg(RespStatusEnum.SUCCESS.getMsg());
			
		} catch (Exception e) {
			// e.printStackTrace();
			log.info(e.getMessage());
			status.setCode(RespStatusEnum.FAIL.getCode());
			status.setMsg(RespStatusEnum.FAIL.getMsg());
		}
		
		return status;
	}
	
	/**
	 * 启用评估记录
	 * 
	 * @user jiangyq
	 * @date 2017年11月06日 上午11:32:00 
	 * @param request
	 * @param response
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/v/useEnquiryAssess")
	public @ResponseBody
	RespDataObject<Map<String,Object>> useEnquiryAssess(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, String> param){
		RespDataObject<Map<String,Object>> status = new RespDataObject<Map<String,Object>>();
		try {
			
			String enquiryAssessId = param.get("enquiryAssessId");
			
			if(StringUtil.isEmpty(enquiryAssessId)){
				status.setCode(RespStatusEnum.FAIL.getCode());
				status.setMsg(RespStatusEnum.FAIL.getCode());
				return status;
			}
			
			String [] enquiryAssessIds = enquiryAssessId.split(",");
			for (int i = 0; i < enquiryAssessIds.length; i++) {
				String id = enquiryAssessIds[i];
				//this.enquiryAssessService.updateStatus(id, CommEnum.ASSESS_STATUS_7.getCode(), "");
				this.enquiryAssessService.updateIsClose(id, CommEnum.ASSESS_USE.getCode());
			}
			
			status.setCode(RespStatusEnum.SUCCESS.getCode());
			status.setMsg(RespStatusEnum.SUCCESS.getMsg());
			
		} catch (Exception e) {
			// e.printStackTrace();
			log.info(e.getMessage());
			status.setCode(RespStatusEnum.FAIL.getCode());
			status.setMsg(RespStatusEnum.FAIL.getMsg());
		}
		
		return status;
	}
	
	/**
	 * 查询评估报告详情
	 * 
	 * @user jiangyq
	 * @date 2017年10月31日 上午10:42:09 
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/v/selAssessReportDetail")
	@ResponseBody
	public RespDataObject<Map<String, Object>> selAssessReportDetail(@RequestBody Map<String, String> param) {
		RespDataObject<Map<String, Object>> status = new RespDataObject<Map<String, Object>>();
		if(StringUtils.isBlank(MapUtils.getString(param, "enquiryAssessId"))){
			status.setMsg(RespStatusEnum.PARAMETER_ERROR.getMsg());
			status.setCode(RespStatusEnum.PARAMETER_ERROR.getCode());
			return status;
		}
		
		Map<String, Object> retMap = null;
		try {
			String orderNo = MapUtils.getString(param, "enquiryAssessId");
			AssessReportDto assessReportDto = assessReportService.findAssessReportDto(orderNo);
			
			retMap = MapAndBean.beanToMap(assessReportDto);
			status.setData(retMap);
			status.setMsg(RespStatusEnum.SUCCESS.getMsg());
			status.setCode(RespStatusEnum.SUCCESS.getCode());
		} catch (Exception e) {
			log.error("查询评估报告详情异常", e);
			status.setMsg(RespStatusEnum.FAIL.getMsg());
			status.setCode(RespStatusEnum.FAIL.getCode());
		}
		return status;
	}
}
