package com.anjbo.controller;

import com.anjbo.bean.risk.*;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.*;
import com.anjbo.service.impl.CountryLawsuitClient;
import com.anjbo.service.impl.SZLawsuitClient;
import com.anjbo.utils.ValidHelper;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 诉讼
 * @author huanglj
 *
 */
@Controller
@RequestMapping("/credit/risk/lawsuit/v")
public class LawsuitController extends BaseController{

	private static final Log log = LogFactory.getLog(LawsuitController.class);
	
	@Resource
	private LawsuitService lawsuitService;
	@Resource
	private LawsuitCnService lawsuitCnService;
	@Resource
	private LawsuitSzService lawsuitSzService;
	@Resource
	private ArchiveService archiveService;
	@Resource
	private EnquiryService enquiryService;
	/**
	 * 加载询价/查档/诉讼 数据
	 * @param request
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/loadInquiryAndArchiveInit")
	public RespDataObject<Map<String,Object>> loadInquiryAndArchiveInit(HttpServletRequest request,@RequestBody Map<String,Object> map){
		RespDataObject<Map<String,Object>> result = new RespDataObject<Map<String,Object>>();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			String orderNo = MapUtils.getString(map, "orderNo");
			
			if(StringUtils.isBlank(orderNo)){
				result.setCode(RespStatusEnum.SUCCESS.getCode());
				result.setMsg(RespStatusEnum.SUCCESS.getMsg());
				
				return result;
			}
			orderNo = AllocationFundController.getOrderNo(orderNo);
			//详情已有查档记录
			List<ArchiveDto> archiveMap = archiveService.listArchive(orderNo);
			if(null==archiveMap||archiveMap.size()<=0){
				archiveMap = new ArrayList<ArchiveDto>();
			}
			dataMap.put("archiveData", archiveMap);
			//详情已有询价记录
			List<EnquiryDto> enquiryMap = enquiryService.listEnquiry(orderNo);
			if(null==enquiryMap||enquiryMap.size()<=0){
				enquiryMap = new ArrayList<EnquiryDto>();
			}
			dataMap.put("enquiryData", enquiryMap);
			
			
			//详情诉讼记录
			LawsuitDto lawsuitDto = lawsuitService.selectByOrderNo(orderNo);
			dataMap.put("lawsuitData",lawsuitDto);
			
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
			result.setData(dataMap);
		} catch (Exception e){
			log.error("加载询价/查档/诉讼 数据失败==>",e);
		}
		return result;
	}
	
	/**
	 * 获取深圳诉讼网站的验证码
	 * @param request
	 * @param params
	 * @return
	 */
	@ResponseBody 
	@RequestMapping(value="/getSZAuthCode")
	public Map<String, Object> getSZAuthCode(HttpServletRequest request,Map<String, Object> params){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			SZLawsuitClient.getSessionCookies();
			String authCode = SZLawsuitClient.getAuthCode(request);
			map.put("SZAuthCode", authCode);
			map.put("code", RespStatusEnum.SUCCESS.getCode());
			map.put("msg", RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			log.error("getSZAuthCode Exception  ->",e);
			map.put("code", RespStatusEnum.FAIL.getCode());
			map.put("msg", "验证码获取失败");
		}
		return map;
	}
	
	/**
	 * 查询深圳诉讼，返回id,存数据到数据库
	 * @param request
	 * @param map
	 * @return
	 */
	@ResponseBody 
	@RequestMapping(value="/searchSZ")
	public RespDataObject<Map<String, Object>> searchSZ(HttpServletRequest request,@RequestBody Map<String,Object> map){
		RespDataObject<Map<String, Object>> respDataObject = new RespDataObject<Map<String, Object>>();

		if(!ValidHelper.isNotEmptyByKeys(map,"appliers","code","orderNo")){
			respDataObject.setMsg(RespStatusEnum.PARAMETER_ERROR.getMsg());
			return respDataObject;
		}
		LawsuitDto lawsuitDto = lawsuitService.selectByOrderNo(MapUtils.getString(map, "orderNo"),false);
		UserDto user = getUserDto(request);
		try{
			Map<String, Object> data = SZLawsuitClient.getResult(map);
			if(null!=data){
				List<Map<String, Object>> listMap = (List<Map<String, Object>>)data.get("listMap");
				if(listMap!=null&&listMap.size()>10){
					respDataObject.setCode(RespStatusEnum.FAIL.getCode());
					respDataObject.setMsg("验证码错误");
					return respDataObject;
				}
				if(null==lawsuitDto){
					lawsuitDto = new LawsuitDto();
					lawsuitDto.setUpdateUid(user.getUid());
					lawsuitDto.setCreateUid(user.getUid());
					lawsuitDto.setOrderNo(MapUtils.getString(map, "orderNo"));
					lawsuitDto.setIsLawsuit(0);
					lawsuitDto.setName(MapUtils.getString(map, "appliers"));
					lawsuitDto.setCodeNo(MapUtils.getString(map, "cardNum"));
					lawsuitDto.setStatus(2);
					lawsuitDto.setCreateTime(new Date());
					lawsuitService.insertSelective(lawsuitDto);
				}else{
					if(1==lawsuitDto.getStatus()){
						lawsuitDto.setStatus(3);
						lawsuitService.updateByPrimaryKeySelective(lawsuitDto);
					}
				}
				Map<String,Object> paramMap = new HashMap<String, Object>();
				paramMap.put("lawsuitId", lawsuitDto.getId());
				lawsuitSzService.deleteByMap(paramMap);
				if(listMap.size()>0){
					for(Map<String, Object> rowMap:listMap){
						LawsuitSzDto lawsuitSzDto = new LawsuitSzDto();
						lawsuitSzDto.setLawsuitId(lawsuitDto.getId());
						lawsuitSzDto.setCreateUid(getUserDto(request).getUid());
						lawsuitSzDto.setUpdateUid(getUserDto(request).getUid());
						lawsuitSzDto.setCreateTime(new Date());
						lawsuitSzDto.setOrderNo(MapUtils.getString(map, "orderNo"));
						lawsuitSzDto.setCaseNo(MapUtils.getString(rowMap, "caseNo"));
						lawsuitSzDto.setJudge(MapUtils.getString(rowMap, "judge"));
						lawsuitSzDto.setLitigant(MapUtils.getString(rowMap, "litigant"));
						lawsuitSzDto.setRegisterDate(MapUtils.getString(rowMap, "registerDate"));
						lawsuitSzDto.setOpenCourtDate(MapUtils.getString(rowMap, "openCourtDate"));
						lawsuitSzDto.setCloseCourtDate(MapUtils.getString(rowMap, "closeCourtDate"));
						lawsuitSzDto.setStatus(MapUtils.getString(rowMap, "status"));
						lawsuitSzService.insertSelective(lawsuitSzDto);
					}
				}
				data.clear();
				data.put("lawsuitId", lawsuitDto.getId());
				respDataObject = RespHelper.setSuccessDataObject(respDataObject, data);
			}
		}catch (Exception e) {
			log.error("searchSZ Exception  ->",e);
		}
		return respDataObject;
	}
	
	/**
	 * 根据诉讼id获取深圳诉讼信息集合
	 * @param request
	 * @param params
	 * @return
	 */
	@ResponseBody 
	@RequestMapping(value="/getSZListByLawsuitId")
	public Map<String, Object> getSZListByLawsuitId(HttpServletRequest request,@RequestBody Map<String,Object> params){
		Map<String, Object> map = new HashMap<String, Object>();

		if(!ValidHelper.isNotEmptyByKeys(params,"lawsuitId")){
			map.put("code", "FAIL");
			map.put("msg", "获取深圳诉讼信息集合参数异常");
			return map;
		}
		try {
			List<Map<String, Object>> listMap = lawsuitSzService.getListMap(params);
			map.put("data", listMap);
			LawsuitDto lawsuitDto = lawsuitService.selectByOrderNo(MapUtils.getString(params, "orderNo"));
			map.put("lawsuit", lawsuitDto);
			map.put("code", RespStatusEnum.SUCCESS.getCode());
			map.put("msg", RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			log.error("getQGListByLawsuitId Exception  ->",e);
			map.put("code", RespStatusEnum.FAIL.getCode());
			map.put("msg", "查询深圳诉讼记录列表失败");
		}
		return map;
	}
	
	@ResponseBody 
	@RequestMapping(value="/isVisitLimit")
	public Map<String, Object> isVisitLimit(){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Boolean isVisitLimit = CountryLawsuitClient.isVisitLimit();
			map.put("isVisitLimit", isVisitLimit);
			map.put("code", RespStatusEnum.SUCCESS.getCode());
			map.put("msg", RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			log.error("isVisitLimit Exception  ->",e);
			map.put("code", RespStatusEnum.FAIL.getCode());
			map.put("msg", "判断是否有访问限制失败");
		}
		return map;
	}
	
	@ResponseBody 
	@RequestMapping(value="/getLimitCode")
	public Map<String, Object> getLimitCode(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String authCode = CountryLawsuitClient.getLimitAuthCode(request);
			map.put("QGAuthCode", authCode);
			map.put("code", RespStatusEnum.SUCCESS.getCode());
			map.put("msg", RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			log.error("getLimitCode Exception  ->",e);
			map.put("code", RespStatusEnum.FAIL.getCode());
			map.put("msg", "限制访问验证码获取失败");
		}
		return map;
	}
	
	/**
	 * 获取全国诉讼验证码
	 * @param request
	 * @return
	 */
	@ResponseBody 
	@RequestMapping(value="/getQGAuthCode")
	public Map<String, Object> getQGAuthCode(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String authCode = CountryLawsuitClient.getAuthCodeAndCookiesNew(request);
			map.put("QGAuthCode", authCode);
			map.put("code", RespStatusEnum.SUCCESS.getCode());
			map.put("msg", RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			log.error("getQGAuthCode Exception  ->",e);
			map.put("code", RespStatusEnum.FAIL.getCode());
			map.put("msg", "验证码获取失败");
		}
		return map;
	}
	
	/**
	 * 全国诉讼查询获取id
	 * @param request
	 * @param params
	 * @return
	 */
	@ResponseBody 
	@RequestMapping(value="/getLawsuitId")
	public Map<String, Object> getLawsuitId(HttpServletRequest request,@RequestBody Map<String,Object> params){
		Map<String, Object> map = new HashMap<String, Object>();

		if(StringUtils.isBlank(MapUtils.getString(params,"code",""))
				||StringUtils.isBlank(MapUtils.getString(params,"orderNo",""))){
			map.put("code", "FAIL");map.put("msg", "全国诉讼查询参数异常");
			return map;
		}

		LawsuitDto lawsuitDto = lawsuitService.selectByOrderNo(MapUtils.getString(params, "orderNo"),false);
		try {
			Map<String, Object> data = CountryLawsuitClient.getResult(MapUtils.getString(params, "pname"), 
					MapUtils.getString(params, "cardNum"), MapUtils.getString(params, "code"),MapUtils.getString(params, "captchaId"));
			if(null==data||data.size()<=0){
				map.put("code", RespStatusEnum.FAIL.getCode());
				map.put("msg", "验证码错误,请重新输入");
				return map;
			}
			if(null==lawsuitDto){
				lawsuitDto = new LawsuitDto();
				lawsuitDto.setCreateUid(getUserDto(request).getUid());
				lawsuitDto.setUpdateUid(getUserDto(request).getUid());
				lawsuitDto.setOrderNo(MapUtils.getString(params, "orderNo"));
				lawsuitDto.setIsLawsuit(0);
				lawsuitDto.setName(MapUtils.getString(params, "pname"));
				lawsuitDto.setCodeNo(MapUtils.getString(params, "cardNum"));
				lawsuitDto.setStatus(1);
				lawsuitDto.setCreateTime(new Date());
				lawsuitService.insertSelective(lawsuitDto);
			}else{
				if(2==lawsuitDto.getStatus()){
					lawsuitDto.setStatus(3);
					lawsuitService.updateByPrimaryKeySelective(lawsuitDto);
				}
				
			}
			Map<String,Object> paramMap = new HashMap<String, Object>();
			paramMap.put("lawsuitId", lawsuitDto.getId());
			lawsuitCnService.deleteByMap(paramMap);
			if(null!=data.get("listMap")){
				List<Map<String, Object>> listMap = (List<Map<String, Object>>)data.get("listMap");
				if(listMap.size()>0){
					lawsuitDto.setIsLawsuit(2);
					lawsuitService.updateByPrimaryKeySelective(lawsuitDto);		
					for(Map<String, Object> cnMap:listMap){
						LawsuitCnDto lawsuitCnDto = new LawsuitCnDto();
						lawsuitCnDto.setCreateUid(getUserDto(request).getUid());
						lawsuitCnDto.setUpdateUid(getUserDto(request).getUid());
						lawsuitCnDto.setCreateTime(new Date());
						lawsuitCnDto.setOrderNo(MapUtils.getString(params, "orderNo"));
						lawsuitCnDto.setLawsuitId(lawsuitDto.getId());
						lawsuitCnDto.setName(MapUtils.getString(cnMap, "name"));
						lawsuitCnDto.setDate(MapUtils.getString(cnMap, "date"));
						lawsuitCnDto.setCaseNo(MapUtils.getString(cnMap, "caseNo"));
						lawsuitCnDto.setDetailId(MapUtils.getString(cnMap, "detailId"));
						lawsuitCnService.insertSelective(lawsuitCnDto);
					}
				}else{		
					lawsuitDto.setIsLawsuit(1);
					lawsuitService.updateByPrimaryKeySelective(lawsuitDto);
				}
				map.put("lawsuitId", lawsuitDto.getId());
			}
			
			map.put("code", RespStatusEnum.SUCCESS.getCode());
			map.put("msg", RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			log.error("getLawsuitId Exception  ->",e);
			map.put("code", RespStatusEnum.FAIL.getCode());
			map.put("msg", "查询诉讼记录失败");
		}
		return map;
	}
	
	/**
	 * 根据诉讼id获取全国诉讼记录集合
	 * @param request
	 * @param params
	 * @return
	 */
	@ResponseBody 
	@RequestMapping(value="/getQGListByLawsuitId")
	public Map<String, Object> getQGListByLawsuitId(HttpServletRequest request,@RequestBody Map<String,Object> params){
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(!ValidHelper.isNotEmptyByKeys(params,"lawsuitId","orderNo")){
			map.put("code", "FAIL");map.put("msg", "获取全国诉讼记录集合参数异常");
			return map;
		}
		try {
			List<Map<String, Object>> listMap = lawsuitCnService.getListMap(params);
			LawsuitDto lawsuitDto = lawsuitService.selectByOrderNo(MapUtils.getString(params, "orderNo"));
			map.put("data", listMap);
			map.put("lawsuit", lawsuitDto);		
			map.put("code", RespStatusEnum.SUCCESS.getCode());
			map.put("msg", RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			log.error("getQGListByLawsuitId Exception  ->",e);
			map.put("code", RespStatusEnum.FAIL.getCode());
			map.put("msg", "查询全国诉讼记录列表失败");
		}
		return map;
	}
	
	/**
	 * 根据验证码和全国诉讼的id获取详情
	 * @param request
	 * @param params
	 * @return
	 */
	@ResponseBody 
	@RequestMapping(value="/getQGDetail")
	public Map<String, Object> getQGDetail(HttpServletRequest request,@RequestBody Map<String,Object> params){
		Map<String, Object> map = new HashMap<String, Object>();

		if(!ValidHelper.isNotEmptyByKeys(params,"id","j_captcha","captchaId")){
			map.put("code", "FAIL");map.put("msg", "全国诉讼获取详情参数异常");
			return map;
		}
		try {
			Map<String, Object> data = CountryLawsuitClient.getDetail(params);
			if(null==data||data.size()<=0){
				map.put("data", data);
				map.put("code", RespStatusEnum.FAIL.getCode());
				map.put("msg", "第三方没有返回数据!");
				return map;
			}
			map.put("data", data);
			map.put("code", RespStatusEnum.SUCCESS.getCode());
			map.put("msg", RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			log.error("getQGDetail Exception  ->",e);
			map.put("code", RespStatusEnum.FAIL.getCode());
			map.put("msg", "查询诉讼详情失败");
		}
		return map;
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public RespStatus update(HttpServletRequest request,@RequestBody  LawsuitDto obj){
		RespStatus result = new RespStatus();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			if(StringUtils.isBlank(obj.getOrderNo())){
				result.setMsg("更新失败,更新条件不能为空!");
				return result;
			}
			lawsuitService.updateByOrderNo(obj);
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e){
			log.error("更新异常,异常信息为:",e);
		}
		return result;
	}
}
