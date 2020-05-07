package com.anjbo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONNull;
import net.sf.json.JsonConfig;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.anjbo.bean.risk.ArchiveDto;
import com.anjbo.bean.risk.EnquiryDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.Constants;
import com.anjbo.common.Enums;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.EnquiryService;
import com.anjbo.utils.ConfigUtil;
import com.anjbo.utils.HttpUtil;

/**
 * 询价
 * @author huanglj
 *
 */
@Controller
@RequestMapping("/credit/risk/enquiry/v")
public class EnquiryController extends BaseController{

	private static final Log log = LogFactory.getLog(EnquiryController.class);
	@Resource
	private EnquiryService enquiryService;
	/**
	 * 发起房产询价
	 * 
	 * @Title: create
	 * @param request
	 * @param response
	 * @param propertyname
	 *            物业类型
	 * @param buildingnum
	 *            楼栋
	 * @param roomnum
	 *            房号
	 * @param area
	 *            建筑面积
	 * @param price
	 *            登记价格
	 * @param obligee
	 *            权利人
	 * @param propertyuse
	 *            物业用途
	 * @param consumerloans
	 *            消费贷款
	 * @param range
	 *            购房期限
	 * @param explain
	 *            补充说明
	 * @param handlerid
	 *            处理人uid
	 * @return RespStatus enquiryid
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/createEnquiry")
	public RespDataObject<Map<String, Object>> createEnquiry(HttpServletRequest request,@RequestBody Map<String,Object> param) {
		RespDataObject<Map<String, Object>> result = new RespDataObject<Map<String, Object>>();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			if(!param.containsKey("orderNo")
					&&StringUtils.isBlank(MapUtils.getString(param, "orderNo", ""))){
				result.setMsg("参数缺失");
				return result;
			}
			UserDto userDto = getUserDto(request);
			param.put("uid", userDto.getUid());
			param.put("type", 2);
			String toolsUrl = ConfigUtil.getStringValue(Constants.LINK_ANJBO_TOOl_URL, ConfigUtil.CONFIG_LINK)+"/tools/enquiry/v/createEnquiry";
			String resultStr = HttpUtil.jsonPost(toolsUrl, param);
			result = JSON.parseObject(resultStr, RespDataObject.class);
			/*
			HttpUtil http = new HttpUtil();
			result = http.getRespDataObject(Constants.LINK_ANJBO_TOOl_URL, "/tools/enquiry/v/createEnquiry", param, Map.class);
			*/
			if(RespStatusEnum.SUCCESS.getCode().equals(result.getCode())){
				String oldEnquiryId = MapUtils.getString(param, "enquiryId", "");
				param.putAll(result.getData());
				param.put("oldEnquiryId", oldEnquiryId);
				param.put("createUid", userDto.getUid());
				param.put("updateUid", userDto.getUid());
				enquiryService.insertByMap(param);
			}
			
		} catch (Exception e){
			result.setCode(RespStatusEnum.FAIL.getCode());
			result.setMsg(RespStatusEnum.FAIL.getMsg());
			log.error("查询房产询价信息异常,异常信息为:",e);
		}
		return result;
	}
	
	/**
	 * 根据数据查询已存在的询价记录
	 * @param request
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getEnquiryById")
	public RespDataObject<Map<String, Object>> getEnquiryById(HttpServletRequest request,@RequestBody Map<String,Object> param){
		RespDataObject<Map<String, Object>> result = new RespDataObject<Map<String, Object>>();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			if(!param.containsKey("enquiryId")
					&&StringUtils.isBlank(MapUtils.getString(param, "enquiryId", ""))){
				result.setMsg("参数缺失");
				return result;
			}
			String enquiryId = MapUtils.getString(param, "enquiryId");
			HttpUtil http = new HttpUtil();
			result = http.getRespDataObject(Constants.LINK_ANJBO_TOOl_URL, "/tools/enquiry/v/getEnquiryInfo", param, Map.class);
			if(null==result.getData()){
				result.setCode(RespStatusEnum.FAIL.getCode());
				result.setMsg("没有查到数据");
				return result;
			}
			Map<String,Object> tmp = result.getData();
			calculationMaxLoanPrice(tmp);
			nullToString(tmp);
			tmp.put("orderNo", MapUtils.getString(param, "orderNo"));
			tmp.put("enquiryId", enquiryId);
			enquiryService.update(tmp);
			result.setData(tmp);
		} catch (Exception e){
			log.error("根据数据查询已存在的询价记录异常,异常信息为:",e);
		}
		return result;
	}
	/**
	 * 通过关键字查询楼盘
	 * 
	 * @param request
	 * @param response
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/queryConstruction")
	@ResponseBody
	public RespData<Map<String, Object>> queryConstruction(HttpServletRequest request,String keyword) {
		RespData<Map<String, Object>> result = new RespData<Map<String, Object>>();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			if(StringUtils.isBlank(keyword)){
				result.setMsg("房产名称不能为空");
				return result;
			}
			Map<String,Object> params = new HashMap<String, Object>();
			HttpUtil http = new HttpUtil();
			//data.replaceAll("id", "constructionId").replaceAll("name", "constructionName");
			params.put("keyWord", keyword);
			result = http.getRespData(Constants.LINK_ANJBO_TOOl_URL, "/tools/enquiry/v/getProperty", params,Map.class);
		} catch (Exception e){
			log.error("查询楼盘信息异常,异常信息为:",e);
		}
		return result;
	}
	
	/**
	 * 通过楼盘查询楼栋
	 * 
	 * @param request
	 * @param response
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryBuilding")
	public  RespData<Map<String, Object>> queryBuilding(HttpServletRequest request, @RequestBody Map<String, Object> param) {
		RespData<Map<String, Object>> result = new RespData<Map<String, Object>>();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			if(!param.containsKey("propertyId")||"".equals(MapUtils.getString(param, "propertyId", ""))){
				result.setMsg("请选择房产名称");
				return result;
			}
			
			UserDto userDto=getUserDto(request);
			param.put("uid", userDto.getUid());
			 
			//result.replaceAll("id", "buildingId").replaceAll("name", "buildingName");
			HttpUtil http = new HttpUtil();
			result = http.getRespData(Constants.LINK_ANJBO_TOOl_URL, "/tools/enquiry/v/getBuilding", param, Map.class);
		} catch (Exception e){
			log.error("查询楼盘信息异常,异常信息为:",e);
		}
		return result;
	}
	
	/**
	 * 通过楼栋检索房号(世联)
	 * 
	 * @param request
	 * @param response
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryHouse")
	public  RespData<Map<String, Object>> queryHouse(HttpServletRequest request,@RequestBody Map<String, Object> param) {
		RespData<Map<String, Object>> result = new RespData<Map<String, Object>>();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			if(!param.containsKey("buildingId")||"".equals(MapUtils.getString(param, "buildingId", ""))){
				result.setMsg("请选择楼栋");
				return result;
			}
			UserDto userDto=getUserDto(request);
			param.put("uid", userDto.getUid());
			//result.replaceAll("id", "houseId").replaceAll("name", "houseName").replaceAll("area", "BuildArea");
			HttpUtil http = new HttpUtil();
//			param.put("buildingId", 1773);
			result = http.getRespData(Constants.LINK_ANJBO_TOOl_URL, "/tools/enquiry/v/getHouses", param, Map.class);
		} catch (Exception e){
			log.error("查询楼盘信息异常,异常信息为:",e);
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public RespStatus delete(HttpServletRequest request,@RequestBody EnquiryDto obj){
		RespStatus result = new RespStatus();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			if(StringUtils.isBlank(obj.getOrderNo())||StringUtils.isBlank(obj.getEnquiryId())){
				result.setMsg("删除失败,删除条件不能为空!");
				return result;
			}
			enquiryService.delete(obj);
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e){
			log.error("删除异常,异常信息为:",e);
		}
		return result;
	}
	
	public static void main(String[] args) {
		HttpUtil http = new HttpUtil();
		Map<String,Object> param = new HashMap<String,Object>();
		/*
		param.put("area", 139.07);
		param.put("userid", "123456");
		param.put("buildingId", "800");
		param.put("buildingName", "10栋");
		param.put("consumerloans", 0);
		param.put("isGetNetPriceTax", 0);
		param.put("obligee", "个人");
		param.put("orderNo", "2016102015535400002");
		param.put("propertyId", "162");
		param.put("propertyName", "碧海富通城");
		param.put("range", "0");
		param.put("registerPrice",123123);
		param.put("roomId", "2566438");
		param.put("roomName", "1001");
		param.put("source", "3");
		param.put("enquiryid", "86653");
		*/
		param.put("id", "85ce3deffa04454b86d0e6b043381e7a");
		RespDataObject<Map<String,Object>> result = http.getRespDataObject(Constants.LINK_ANJBO_TOOl_URL, "/tools/archive/v/getArchiveById", param, Map.class);
		System.out.println(result.getData());
	}
	
	public void calculationMaxLoanPrice(Map<String,Object> map){
		List<Map<String, Object>> list = JSONArray.toList(JSONArray.fromObject(map.get("enquiryReportList")),new HashMap<String, Object>(),new JsonConfig());
		for (Map<String, Object> map2 : list) {
			if(NumberUtils.toInt(map.get("isGetNetPriceTax")+"") == 0){
				map2.put("maxLoanPrice", (int)(MapUtils.getDoubleValue(map2, "netPrice")*0.7/10000));//计算最高可贷
			}else if(NumberUtils.toInt(map.get("isGetNetPriceTax")+"") == 1){
				map2.put("maxLoanPrice", (int)(MapUtils.getDoubleValue(map2, "totalPrice")*0.7/10000));//计算最高可贷
			}
			map.put("totalPrice", map2.get("totalPrice"));
			map.put("netPrice", map2.get("netPrice"));
		}
		int isGetNetPriceTax = MapUtils.getIntValue(map, "isGetNetPriceTax");
		Double netPrice = MapUtils.getDouble(map, "netPrice");
		int maxLoanPrice = 0;
		if(isGetNetPriceTax == 0){
			maxLoanPrice=((int) (netPrice*0.7/10000));//计算最高可贷
		}else if(isGetNetPriceTax == 1){
			maxLoanPrice=((int) (netPrice*0.7/10000));//计算最高可贷
		}
		map.put("maxLoanPrice", maxLoanPrice);
		map.put("enquiryReportList", list);
	}
	
	public static void nullToString(Map<String,Object> map){
		if(null==map)return;
		
		Set<String> keys = map.keySet();
		for(String key:keys){
			if("null".equals(map.get(key)+"")||JSONNull.getInstance().equals(map.get(key))){
				map.put(key, "null");
			}
		}
		
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public RespStatus update(HttpServletRequest request,@RequestBody  EnquiryDto obj){
		RespStatus result = new RespStatus();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			if(StringUtils.isBlank(obj.getOrderNo())){
				result.setMsg("更新失败,更新条件不能为空!");
				return result;
			}
			enquiryService.updateByOrderNo(obj);
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e){
			log.error("更新异常,异常信息为:",e);
		}
		return result;
	}
	@ResponseBody
	@RequestMapping("/detail")
	public RespDataObject<List<EnquiryDto>> detail(HttpServletRequest request,@RequestBody EnquiryDto obj){
		RespDataObject<List<EnquiryDto>> result = new RespDataObject<List<EnquiryDto>>();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			if(StringUtils.isBlank(obj.getOrderNo())){
				result.setMsg("获取查档信息参数不能为空");
				return result;
			}
			List<EnquiryDto> list = enquiryService.detailByOrderNo(obj.getOrderNo());
			result.setData(list);
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e){
			log.error("查询查档详情异常:",e);
		}
		return result;
	}
}
