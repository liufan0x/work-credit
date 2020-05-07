package com.anjbo.controller.tools;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.bean.tools.EnquiryDto;
import com.anjbo.bean.tools.EnquiryReportDto;
import com.anjbo.common.Constants;
import com.anjbo.common.Enums;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.common.aop.ValidateAnt;
import com.anjbo.service.tools.CrawlerService;
import com.anjbo.service.tools.EnquiryService;
import com.anjbo.utils.DateUtil;
import com.anjbo.utils.MapAndBean;
import com.anjbo.utils.StringUtil;

/**
 * 询价  调用：赎楼，APP。
 * @author lic
 *
 * @date 2016-11-18 上午09:44:22
 */
@Component
@Controller
@RequestMapping("/tools/enquiry/v")
public class EnquiryController {

	private static final Log log = LogFactory.getLog(EnquiryController.class);

	@Resource
	private EnquiryService enquiryService;
	@Resource
	private CrawlerService crawlerService;

	/**
	 * 查询物业
	 * @user lic
	 * @date 2016-6-3 下午03:27:17 
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/getProperty")
	@ResponseBody
	public RespDataObject<List<Map<String, String>>> getProperty(@RequestBody Map<String, String> param){
		RespDataObject<List<Map<String, String>>> resp = new RespDataObject<List<Map<String,String>>>();
		String keyWord = MapUtils.getString(param, "keyWord");
		if(StringUtil.isEmpty(keyWord)){
			resp.setMsg(RespStatusEnum.PARAMETER_ERROR.getMsg());
			resp.setCode(RespStatusEnum.PARAMETER_ERROR.getCode());
			return  resp;
		}
		
		try {
			String cityName = MapUtils.getString(param, "cityName");
			String type = MapUtils.getString(param, "type");
			String cityId = "";
			if(StringUtil.isEmpty(cityName)){
				//设置默认城市
				cityName = Constants.DEFAULT_CITY;
			}
			if(StringUtil.isEmpty(type)){
				//设置默认评估公司
				type = Constants.TZC_ID;
			}
			cityId = Enums.CityNameByType.getCodeBygNameAndType(cityName,type);
			List<Map<String, String>> propertyNameMaps = null;
			if(type.equals(Constants.TZC_ID)){
				propertyNameMaps = enquiryService.getTZCPropertyName(keyWord,cityId);		//同致诚查询物业
			}else if(type.equals(Constants.GJ_ID)){
				propertyNameMaps = enquiryService.getGJPropertyName(keyWord, cityId);		//国策查询物业
			}else if(type.equals(Constants.YPG_ID)){
				propertyNameMaps = enquiryService.getYPGPropertyName(keyWord,cityId);		//云评估查询物业
			}else if(type.equals(Constants.LKPG_ID)){
				propertyNameMaps = enquiryService.getLKPGPropertyName(keyWord,cityName);		//鲁克评估查询物业
			}
			
			resp.setData(propertyNameMaps);

			
//			/**抓取物业信息**/
//			Map<String, Object> crawlerMap = new HashMap<String, Object>();
//			crawlerMap.put("cityName", cityName);
//			crawlerMap.put("type", type);
//			crawlerMap.put("list", propertyNameMaps);
//			crawlerService.addCrawler(crawlerMap);
//			/**抓取物业信息**/
			
//			if(propertyNameMaps != null && propertyNameMaps.size() > 0){
//				Map<Object, Object> tempMaps = RedisOperator.getMapAll("propertyNameMaps");
//				
//				if(tempMaps == null){
//					tempMaps = new HashMap<Object, Object>();
//				}
//				for (Map<String, String> map : propertyNameMaps) {
//					tempMaps.put(map.get("id"), map.get("name"));
//				}
//				
//				RedisOperator.putToMap("propertyNameMaps", tempMaps);
//			}

			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			resp.setData(null);
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(RespStatusEnum.ERRORONE.getMsg());
			log.error("查询物业异常", e);
		}
		return resp;
	}

	/**
	 * 查询楼栋
	 * @user lic
	 * @date 2016-6-3 下午03:27:17 
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/getBuilding")
	@ResponseBody
	public RespDataObject<List<Map<String, Object>>> getBuilding(@RequestBody Map<String, String> param){
		RespDataObject<List<Map<String, Object>>> resp = new RespDataObject<List<Map<String,Object>>>();
		String propertyId = MapUtils.getString(param, "propertyId");
		if(StringUtil.isEmpty(propertyId)){
			resp.setMsg(RespStatusEnum.PARAMETER_ERROR.getMsg());
			resp.setCode(RespStatusEnum.PARAMETER_ERROR.getCode());
			return  resp;
		}
		try {
			String buildingKeyWord = MapUtils.getString(param, "buildingKeyWord");
			String cityId = "";
			String cityName = MapUtils.getString(param, "cityName");
			if(StringUtil.isEmpty(cityName)){
				//设置默认城市
				cityName = Constants.DEFAULT_CITY;
			}
			String type = MapUtils.getString(param, "type");
			if(StringUtil.isEmpty(type)){
				//设置默认评估公司
				type = Constants.TZC_ID;
			}
			cityId = Enums.CityNameByType.getCodeBygNameAndType(cityName,type);
			List<Map<String, Object>> buildingMaps = null;
			if(type.equals(Constants.TZC_ID)){
				buildingMaps = enquiryService.getTZCBuilding(propertyId,buildingKeyWord,cityId);	//同致诚查询楼栋
			}else if(type.equals(Constants.GJ_ID)){
				buildingMaps = enquiryService.getGJBuilding(propertyId,cityId);				//国策查询楼栋
			}else if(type.equals(Constants.YPG_ID)){
				buildingMaps = enquiryService.getYPGBuilding(propertyId,cityId);			//云评估查询楼栋
			}else if(type.equals(Constants.LKPG_ID)){
				buildingMaps = enquiryService.getLKPGBuilding(propertyId,cityName);			//鲁克评估查询楼栋
			}
			
//			/**抓取楼栋**/
//			Map<String, Object> crawlerMap = new HashMap<String, Object>();
//			crawlerMap.put("propertyId", propertyId);
//			crawlerMap.put("list", buildingMaps);
//			crawlerService.addCrawler(crawlerMap);
//			/**抓取楼栋**/
			
			resp.setData(buildingMaps);
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(RespStatusEnum.ERRORONE.getMsg());
			log.error("查询楼栋异常", e);
		}
		return resp;
	}

	/**
	 * 查询房号
	 * @user lic
	 * @date 2016-6-3 下午03:46:23 
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/getHouses")
	@ResponseBody
	public RespDataObject<List<Map<String, String>>> getHouses(@RequestBody Map<String, String> param){
		RespDataObject<List<Map<String, String>>> resp = new RespDataObject<List<Map<String,String>>>();
		String buildingId = MapUtils.getString(param, "buildingId");
		if(StringUtil.isEmpty(buildingId)){
			resp.setMsg(RespStatusEnum.PARAMETER_ERROR.getMsg());
			resp.setCode(RespStatusEnum.PARAMETER_ERROR.getCode());
			return  resp;
		}
		try {
			String housesKeyWord = MapUtils.getString(param, "housesKeyWord");
			String cityId = "";
			String cityName = MapUtils.getString(param, "cityName");
			if(StringUtil.isEmpty(cityName)){
				//设置默认城市
				cityName = Constants.DEFAULT_CITY;
			}
			
			String type = MapUtils.getString(param, "type");
			if(StringUtil.isEmpty(type)){
				//设置默认评估公司
				type = Constants.TZC_ID;
			}
			cityId = Enums.CityNameByType.getCodeBygNameAndType(cityName,type);
			
			List<Map<String, String>> housesMaps = null;
			if(type.equals(Constants.TZC_ID)){
				housesMaps = enquiryService.getTZCHouses(buildingId,housesKeyWord,cityId);		//同致诚查询房号
			}else if(type.equals(Constants.GJ_ID)){
				housesMaps = enquiryService.getGJHouses(buildingId,cityId);						//国策查询房号
			}else if(type.equals(Constants.YPG_ID)){
				housesMaps = enquiryService.getYPGHouses(buildingId,cityId);					//云评估查询房号
			}else if(type.equals(Constants.LKPG_ID)){
				String propertyId = MapUtils.getString(param, "propertyId");
				if(StringUtil.isEmpty(propertyId)){
					resp.setMsg(RespStatusEnum.PARAMETER_ERROR.getMsg());
					resp.setCode(RespStatusEnum.PARAMETER_ERROR.getCode());
					return  resp;
				}
				housesMaps = enquiryService.getLKPGHouses(propertyId, buildingId, cityName);					//鲁克评估查询房号
			}
			
//			/**抓取房号**/
//			Map<String, Object> crawlerMap = new HashMap<String, Object>();
//			crawlerMap.put("buildingId", buildingId);
//			crawlerMap.put("list", housesMaps);
//			crawlerService.addCrawler(crawlerMap);
//			/**抓取房号**/
			
			resp.setData(housesMaps);
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(RespStatusEnum.ERRORONE.getMsg());
			log.error("查询房号异常", e);
		}
		return resp;
	}
	
	/**
	 * 查询房间信息
	 * @user Object
	 * @date 2016-11-10 下午04:39:27 
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/getHousesInfo")
	@ResponseBody
	public RespDataObject<Map<String, String>> getHousesInfo(@RequestBody Map<String, String> param){
		RespDataObject<Map<String, String>> resp = new RespDataObject<Map<String,String>>();
		String houseRoomId = MapUtils.getString(param, "houseRoomId");
		if(StringUtil.isEmpty(houseRoomId)){
			resp.setMsg(RespStatusEnum.PARAMETER_ERROR.getMsg());
			resp.setCode(RespStatusEnum.PARAMETER_ERROR.getCode());
			return  resp;
		}
		try {
			String cityId = "";
			String cityName = MapUtils.getString(param, "cityName");
			if(StringUtil.isEmpty(cityName)){
				//设置默认城市
				cityName = Constants.DEFAULT_CITY;
			}
			String type = MapUtils.getString(param, "type");
			if(StringUtil.isEmpty(type)){
				//设置默认评估公司
				type = Constants.TZC_ID;
			}
			cityId = Enums.CityNameByType.getCodeBygNameAndType(cityName,type);
			Map<String, String> resultMap = enquiryService.getGJHousesData(houseRoomId,cityId);
			
//			/**抓取房号信息**/
//			Map<String, Object> crawlerMap = new HashMap<String, Object>();
//			crawlerMap.put("roomId", houseRoomId);
//			crawlerMap.putAll(resultMap);
//			crawlerService.addCrawler(crawlerMap);
//			/**抓取房号信息**/
			
			resp.setData(resultMap);
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(RespStatusEnum.FAIL.getMsg());
			log.error("查询房间信息异常", e);
		}
		return resp;
	}

	/**
	 * 查询银行
	 * @user lic
	 * @date 2016-6-3 下午04:01:37 
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/getBanks")
	@ResponseBody
	public RespDataObject<List<Map<String, String>>> getBanks(@RequestBody Map<String, String> param){
		RespDataObject<List<Map<String, String>>> resp = new RespDataObject<List<Map<String,String>>>();
		resp.setCode(RespStatusEnum.FAIL.getCode());
		resp.setMsg(RespStatusEnum.FAIL.getMsg());
		try {
			String bankId = MapUtils.getString(param, "bankId");
			resp.setData(enquiryService.getTZCBank(bankId));
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			log.error("查询银行异常", e);
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}

	/**
	 * 查询银行支行
	 * @user lic
	 * @date 2016-6-3 下午04:01:37 
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/getSubBanks")
	@ResponseBody
	public RespDataObject<List<Map<String, String>>> getSubBanks(@RequestBody Map<String, String> param){
		RespDataObject<List<Map<String, String>>> resp = new RespDataObject<List<Map<String,String>>>();
		resp.setCode(RespStatusEnum.FAIL.getCode());
		resp.setMsg(RespStatusEnum.FAIL.getMsg());
		try {
			String bankId = MapUtils.getString(param, "bankId");
			String subBankId = MapUtils.getString(param, "subBankId");
			resp.setData(enquiryService.getTZCSubBank(bankId,subBankId));
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			log.error("查询银行支行异常", e);
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}

	/**
	 * 创建询价
	 * @user lic
	 * @date 2016-6-3 下午04:08:56 
	 * @param param
	 * @return
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws InstantiationException 
	 * @throws IllegalAccessException 
	 */
	@ValidateAnt(name="createEnquiry",nameDesc="询价",limit=true)
	@RequestMapping(value = "/createEnquiry")
	@ResponseBody
	public RespDataObject<Map<String, Object>> createEnquiry(HttpServletRequest request,@RequestBody Map<String, Object> param) 
		throws IllegalAccessException, InstantiationException, InvocationTargetException, IntrospectionException{
		EnquiryDto enquiryDto = new EnquiryDto();
		//enquiryDto = (EnquiryDto) MapAndBean.MapToBean(EnquiryDto.class, param);
		BeanUtils.populate(enquiryDto, param);
		
		RespDataObject<Map<String, Object>> resp = new RespDataObject<Map<String, Object>>();
		resp.setCode(RespStatusEnum.FAIL.getCode());
		resp.setMsg(RespStatusEnum.ERRORTWO.getMsg());
		try {

			//设置默认城市
			if(StringUtil.isEmpty(enquiryDto.getCityName())){
				enquiryDto.setCityName(Constants.DEFAULT_CITY);
			}

			//设置默认评估公司
			if(StringUtil.isEmpty(enquiryDto.getType())){
				enquiryDto.setType(Constants.LKPG_ID);
			}
			
			//获取对应评估公司的城市Id
			enquiryDto.setCityId(Enums.CityNameByType.getCodeBygNameAndType(enquiryDto.getCityName(),enquiryDto.getType()));
			
			int count = enquiryService.selectCountByCondition(enquiryDto);		//查询限制(相同物业，相同楼栋，相同房号一天内不能查询超过Constants.ENQUIRY_COUNT次)
			if(count > Constants.ENQUIRY_COUNT){
				resp.setCode(RespStatusEnum.ENQUIRY_SAME_QUERY.getCode());
				resp.setMsg(String.format(RespStatusEnum.ENQUIRY_SAME_QUERY.getMsg(), Constants.ENQUIRY_COUNT));
				return resp;
			}
			
			enquiryDto.setSerialid(DateUtil.getDateString());					//加入内部流水号(同志诚)
			if ("东莞".equals(enquiryDto.getCityName()) && !Constants.LKPG_ID.equals(enquiryDto.getType())) 
				enquiryService.createEnquiryDongGuan(enquiryDto);				//针对东莞特殊询价
			else
				enquiryService.createEnquiry(enquiryDto);							//询价
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("enquiryid", enquiryDto.getId());
			resp.setData(dataMap);
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			log.error("创建询价异常", e);
			resp.setMsg(RespStatusEnum.ERRORTWO.getMsg());
			resp.setCode(RespStatusEnum.FAIL.getCode());
		}
		return resp;
	}
	

	/**
	 * 同致诚询价结果回调
	 * @user Object
	 * @date 2016-11-14 下午04:53:32 
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/tzcEnquiryReturn")
	public RespStatus tzcEnquiryReturn(@RequestBody Map<String, String> param){
		RespStatus resp = new RespStatus();
		try {
			String serialid = MapUtils.getString(param, "pgsqID");	// 内部流水号
			int enquiryId = enquiryService.selectEnquiryBySerialid(serialid);
			EnquiryReportDto enquiryReportDto = new EnquiryReportDto();
			enquiryReportDto.setEnquiryId(enquiryId);
			enquiryReportDto.setPropertyName(MapUtils.getString(param, "projectName"));
			enquiryReportDto.setBuildingName(MapUtils.getString(param, "buildingName"));
			enquiryReportDto.setRoomName(MapUtils.getString(param, "houseName"));
			enquiryReportDto.setArea(MapUtils.getDoubleValue(param, "buildingArea"));
			enquiryReportDto.setUnitPrice(MapUtils.getDoubleValue(param, "unitPrice"));
			enquiryReportDto.setTotalPrice(MapUtils.getDoubleValue(param, "totalPrice"));
			enquiryReportDto.setTax(MapUtils.getDoubleValue(param, "tax"));
			double netPrice = MapUtils.getDouble(param, "netPrice");
			double sffive = netPrice * 1.05 * 0.7;// 净值上浮5%
			double sften = netPrice * 1.1 * 0.7;// 净值上浮10%
			enquiryReportDto.setSffive(sffive);
			enquiryReportDto.setSften(sften);
			enquiryReportDto.setNetPrice(netPrice);
			enquiryReportDto.setCompany(MapUtils.getString(param, "estimator"));
			enquiryService.updateEnquiryReport(enquiryReportDto);
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			log.info("同致诚回调失败！",e);
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(RespStatusEnum.FAIL.getMsg());
		}
		System.out.println(param);
		return resp;
	}

	/**
	 * 查询询价列表
	 * @user lic
	 * @date 2016-11-14 下午06:32:48 
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getList")
	public RespDataObject<List<Map<String, Object>>> getList(@RequestBody Map<String, Object> param){
		RespDataObject<List<Map<String, Object>>> resp  = new RespDataObject<List<Map<String, Object>>>();
		if(StringUtil.isEmpty(MapUtils.getString(param, "uid"))){
			resp.setMsg(RespStatusEnum.PARAMETER_ERROR.getMsg());
			resp.setCode(RespStatusEnum.PARAMETER_ERROR.getCode());
			return  resp;
		}
		try {
			List<Map<String, Object>> list = enquiryService.getList(param);
			resp.setData(list);
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
		} catch (Exception e) {
			log.error("查询询价列表异常", e);
			resp.setMsg(RespStatusEnum.ERRORONE.getMsg());
			resp.setCode(RespStatusEnum.FAIL.getCode());
		}
		return resp;
	}

	/**
	 * 根据已查id获取询价记录
	 * @return
	 */
	@RequestMapping(value = "/getEnquiryInfo")
	@ResponseBody
	public RespDataObject<Map<String, Object>> getEnquiryInfo(@RequestBody Map<String, String> param) {
		RespDataObject<Map<String, Object>> status = new RespDataObject<Map<String, Object>>();
		if(null==MapUtils.getInteger(param, "enquiryId")){
			status.setMsg(RespStatusEnum.PARAMETER_ERROR.getMsg());
			status.setCode(RespStatusEnum.PARAMETER_ERROR.getCode());
			return status;
		}
		Map<String, Object> retMap = null;
		try {
			Integer id = MapUtils.getInteger(param, "enquiryId");
			EnquiryDto enquiryDto = enquiryService.selectEnquiry(id);
			retMap = MapAndBean.beanToMap(enquiryDto);
			List<Map<String, Object>> enquiryReportList = enquiryService.selectEnquiryReportList(id);
			for (Map<String, Object> map : enquiryReportList) {
				if(MapUtils.getString(map, "company").equals(Constants.LKPG_ID) || Constants.LKPG_ID.equals(enquiryDto.getType())){
					Map<String, Object>  lkpgMap = enquiryService.selectLKPGPropertyImgAndBargainAndNetworkCount(enquiryDto.getPropertyId(), enquiryDto.getCityName());
					if (lkpgMap!=null && !lkpgMap.isEmpty()) {
						retMap.putAll(lkpgMap);
					}
				}
			}
			retMap.put("enquiryReportList", enquiryReportList);
			
			status.setData(retMap);
			status.setMsg(RespStatusEnum.SUCCESS.getMsg());
			status.setCode(RespStatusEnum.SUCCESS.getCode());
		} catch (Exception e) {
			log.error("查询询价异常", e);
			status.setMsg(RespStatusEnum.FAIL.getMsg());
			status.setCode(RespStatusEnum.FAIL.getCode());
		}
		return status;
	}
	
	/**
	 * 删除询价记录
	 * @return
	 */
	@RequestMapping(value = "/delEnquiry")
	public @ResponseBody
	RespDataObject<Map<String,Object>> delEnquiry(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, String> param){
		RespDataObject<Map<String,Object>> status = new RespDataObject<Map<String,Object>>();
		try {
			
			String enquiryId = param.get("enquiryId");
			
			if(StringUtil.isEmpty(enquiryId)){
				status.setCode(RespStatusEnum.FAIL.getCode());
				status.setMsg(RespStatusEnum.FAIL.getCode());
				return status;
			}
			
			String [] enquiryIds=enquiryId.split(",");
			
			for (int i = 0; i < enquiryIds.length; i++) {
				int eid = Integer.parseInt(enquiryIds[i]);
				enquiryService.deleteEnquiry(eid); //删除询价表记录和结果
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
	 * 
	 * @user likf
	 * @date 2018年3月23日 上午11:42:07 
	 * @param params
	 * @return
	 */
	@RequestMapping("enqueryReportListByProperty")
	@ResponseBody
	public RespPageData<Map<String,Object>> enqueryReportListByProperty(@RequestBody Map<String,Object> params){
		RespPageData<Map<String,Object>> page = new RespPageData<Map<String,Object>>();
		page.setCode(RespStatusEnum.FAIL.getCode());
		page.setMsg(RespStatusEnum.FAIL.getMsg());
		try {
			Integer orderMode = MapUtils.getInteger(params, "orderMode",0);
			Integer start = MapUtils.getInteger(params, "start");
			Integer pageSize = MapUtils.getInteger(params, "pageSize");
			String propertyName = MapUtils.getString(params, "propertyName");
			if(start == null){
				params.put("start", 0);
			}
			if(pageSize == null){
				params.put("pageSize", 10);
			}
			if(StringUtils.isBlank(propertyName)){
				return page;
			}
			this.setOrderParam(params, orderMode);
			page = enquiryService.enqueryReportListByProperty(params);
		} catch (Exception e) {
			log.error(e);
		}
		return page;
	}
	//设置排序参数
	private void setOrderParam(Map<String,Object> paramMap,int orderMode){
		switch(orderMode){
			case 1://面积降序
				paramMap.put("order", "DESC");
				paramMap.put("orderColumn", "area");
				break;
			case 2://面积升序
				paramMap.put("order", "ASC");
				paramMap.put("orderColumn", "area");
				break;
			case 3://单价降序
				paramMap.put("order", "DESC");
				paramMap.put("orderColumn", "unitPrice");
				break;
			case 4://单价升序
				paramMap.put("order", "ASC");
				paramMap.put("orderColumn", "unitPrice");
				break;
			case 5://总价降序
				paramMap.put("order", "DESC");
				paramMap.put("orderColumn", "totalPrice");
				break;
			case 6://总价升序
				paramMap.put("order", "ASC");
				paramMap.put("orderColumn", "totalPrice");
				break;
			default://默认时间倒序
				paramMap.put("order", "DESC");
				paramMap.put("orderColumn", "updateTime");
		}
	}
	
	
	/**
	 * 查询小区数量信息
	 * @user Administrator
	 * @date 2018年6月22日 下午3:24:19 
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/selectLKPGPropertyImgAndBargainAndNetworkCount")
	@ResponseBody
	public RespDataObject<Map<String, Object>> selectLKPGPropertyImgAndBargainAndNetworkCount(@RequestBody Map<String, String> param){
		RespDataObject<Map<String, Object>> resp = new RespDataObject<Map<String,Object>>();
		String propertyId = MapUtils.getString(param, "propertyId");
		if(StringUtil.isEmpty(propertyId)){
			resp.setMsg(RespStatusEnum.PARAMETER_ERROR.getMsg());
			resp.setCode(RespStatusEnum.PARAMETER_ERROR.getCode());
			return  resp;
		}
		try {
			String cityName = MapUtils.getString(param, "cityName");
			if(StringUtil.isEmpty(cityName)){
				//设置默认城市
				cityName = Constants.DEFAULT_CITY;
			}
			Map<String, Object> res = enquiryService.selectLKPGPropertyImgAndBargainAndNetworkCount(propertyId, cityName);
			resp.setData(res);
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(RespStatusEnum.ERRORONE.getMsg());
			log.error("查询小区数量信息", e);
		}
		return resp;
	}
	
	/**
	 * 获取鲁克评估小区照片
	 * @user Administrator
	 * @date 2018年6月22日 下午3:25:31 
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/getLKPGPropertyImgs")
	@ResponseBody
	public RespDataObject<List<Map<String, Object>>> getLKPGPropertyImgs(@RequestBody Map<String, String> param){
		RespDataObject<List<Map<String, Object>>> resp = new RespDataObject<List<Map<String, Object>>>();
		String propertyId = MapUtils.getString(param, "propertyId");
		if(StringUtil.isEmpty(propertyId)){
			resp.setMsg(RespStatusEnum.PARAMETER_ERROR.getMsg());
			resp.setCode(RespStatusEnum.PARAMETER_ERROR.getCode());
			return  resp;
		}
		try {
			String cityName = MapUtils.getString(param, "cityName");
			if(StringUtil.isEmpty(cityName)){
				//设置默认城市
				cityName = Constants.DEFAULT_CITY;
			}
			String propertyName = MapUtils.getString(param, "propertyName");
			List<Map<String, Object>> list = enquiryService.getLKPGPropertyImgs(propertyId, propertyName, cityName, 2);
			resp.setData(list);
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(RespStatusEnum.ERRORONE.getMsg());
			log.error("查询楼栋异常", e);
		}
		return resp;
	}
	
	/**
	 * 获取鲁克评估小区市场成交
	 * @user Administrator
	 * @date 2018年6月22日 下午3:25:31 
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/getLKPGMarketBargain")
	@ResponseBody
	public RespDataObject<List<Map<String, Object>>> getLKPGMarketBargain(@RequestBody Map<String, Object> param){
		RespDataObject<List<Map<String, Object>>> resp = new RespDataObject<List<Map<String,Object>>>();
		String propertyId = MapUtils.getString(param, "propertyId");
		if(StringUtil.isEmpty(propertyId)){
			resp.setMsg(RespStatusEnum.PARAMETER_ERROR.getMsg());
			resp.setCode(RespStatusEnum.PARAMETER_ERROR.getCode());
			return  resp;
		}
		try {
			String cityName = MapUtils.getString(param, "cityName");
			if(StringUtil.isEmpty(cityName)){
				//设置默认城市
				cityName = Constants.DEFAULT_CITY;
			}
			String propertyName = MapUtils.getString(param, "propertyName");
			int start = MapUtils.getInteger(param, "start");
			int pageSize = MapUtils.getInteger(param, "pagesize");
			List<Map<String, Object>> list = enquiryService.getLKPGMarketBargain(start, pageSize, -1, propertyId, propertyName, cityName);
			resp.setData(list);
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(RespStatusEnum.ERRORONE.getMsg());
			log.error("查询楼栋异常", e);
		}
		return resp;
	}
	
	/**
	 * 获取鲁克评估小区网络报盘
	 * @user Administrator
	 * @date 2018年6月22日 下午3:25:31 
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/getLKPGNetworkOffer")
	@ResponseBody
	public RespDataObject<List<Map<String, String>>> getLKPGNetworkOffer(@RequestBody Map<String, Object> param){
		RespDataObject<List<Map<String, String>>> resp = new RespDataObject<List<Map<String,String>>>();
		String propertyId = MapUtils.getString(param, "propertyId");
		if(StringUtil.isEmpty(propertyId)){
			resp.setMsg(RespStatusEnum.PARAMETER_ERROR.getMsg());
			resp.setCode(RespStatusEnum.PARAMETER_ERROR.getCode());
			return  resp;
		}
		try {
			String cityName = MapUtils.getString(param, "cityName");
			if(StringUtil.isEmpty(cityName)){
				//设置默认城市
				cityName = Constants.DEFAULT_CITY;
			}
			String propertyName = MapUtils.getString(param, "propertyName");
			int start = MapUtils.getInteger(param, "start");
			int pageSize = MapUtils.getInteger(param, "pagesize");
			List<Map<String, String>> list = enquiryService.getLKPGNetworkOffer(start, pageSize, -1, propertyId, propertyName, cityName);
			resp.setData(list);
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(RespStatusEnum.ERRORONE.getMsg());
			log.error("查询楼栋异常", e);
		}
		return resp;
	}
	
	/**
	 * 鲁克评估询价反馈
	 * @user jiangyq
	 * @date 2018年10月19日 下午2:30:13 
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/lKPGEnquiryFeedback")
	@ResponseBody
	public RespDataObject<Map<String, Object>> lKPGEnquiryFeedback(@RequestBody Map<String, Object> param){
		RespDataObject<Map<String, Object>> resp = new RespDataObject<Map<String,Object>>();
		try {
			RespDataObject<Map<String, Object>> res = enquiryService.lKPGEnquiryFeedback(param);
			return res;
		} catch (Exception e) {
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(RespStatusEnum.ERRORONE.getMsg());
			log.error("鲁克评估询价反馈", e);
		}
		return resp;
	}
	
	/**
	 * 鲁克评估询价反馈
	 * @user jiangyq
	 * @date 2018年10月19日 下午2:30:13 
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/lKPGPropertyFeedback")
	@ResponseBody
	public RespDataObject<Map<String, Object>> lKPGPropertyFeedback(@RequestBody Map<String, Object> param){
		RespDataObject<Map<String, Object>> resp = new RespDataObject<Map<String,Object>>();
		try {
			RespDataObject<Map<String, Object>> res = enquiryService.lKPGPropertyFeedback(param);
			return res;
		} catch (Exception e) {
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(RespStatusEnum.ERRORONE.getMsg());
			log.error("鲁克评估物业信息反馈", e);
		}
		return resp;
	}
	
	/**
	 * 获取鲁克评估token
	 * @user jiangyq
	 * @date 2018年10月22日 上午9:20:56 
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/resetLkpgToken")
	@ResponseBody
	public RespDataObject<Map<String, Object>> resetLkpgToken(@RequestBody Map<String, Object> param){
		RespDataObject<Map<String, Object>> resp = new RespDataObject<Map<String,Object>>();
		try {
			RespDataObject<Map<String, Object>> res = enquiryService.resetLkpgToken(param);
			return res;
		} catch (Exception e) {
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(RespStatusEnum.ERRORONE.getMsg());
			log.error("重新获取鲁克评估token", e);
		}
		return resp;
	}
	
}
