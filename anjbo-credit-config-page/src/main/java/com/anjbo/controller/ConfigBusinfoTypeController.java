package com.anjbo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.common.Constants;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.ConfigBusinfoTypeService;
import com.anjbo.utils.JsonUtil;
import com.anjbo.utils.ValidHelper;

@Controller
@RequestMapping("/credit/config/page/businfo/type")
public class ConfigBusinfoTypeController extends BaseController {
	
	Logger log =Logger.getLogger(ConfigBusinfoTypeController.class);
	
	@Resource
	private ConfigBusinfoTypeService configBusinfoTypeService;
	
	/**
	 * 查询影像资料树
	 * @param request
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/query") 
	public RespDataObject<Map<String, Object>> query(HttpServletRequest request,@RequestBody Map<String,Object> map){
		RespDataObject<Map<String, Object>> resp = new 	RespDataObject<Map<String, Object>>();
		try {
			Map<String, Object> data = new HashMap<String, Object>();
			data = configBusinfoTypeService.selectBusinfoInit(map);
			RespHelper.setSuccessDataObject(resp, data);
		} catch (Exception e) {
			e.printStackTrace();
			RespHelper.setFailRespStatus(resp, RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}
	
	/**
	 * 查询影像资料类型
	 * @param request
	 * @param map
	 * @return
	 */
	@ResponseBody 
	@RequestMapping(value="/getAllType")
	public RespDataObject<Map<String, Object>> getAllType(HttpServletRequest request,@RequestBody Map<String,Object> map){
		RespDataObject<Map<String, Object>> respDataObject = RespHelper.failDataObject(null);
		if(!ValidHelper.isNotEmptyByKeys(map, "productCode")){
			respDataObject.setMsg(RespStatusEnum.PARAMETER_ERROR.getMsg());
			return respDataObject;
		}
		try{
			String productCode = MapUtils.getString(map, "productCode");
			if("01".equals(productCode)||"02".equals(productCode)||"03".equals(productCode)||"05".equals(productCode)){//债务置换贷款影像资料
				RespDataObject<Map<String,Object>> respDa = httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/order/businfo/v/getAllType", map, Map.class);
				return respDa;
			}
			Map<String, Object> data = configBusinfoTypeService.getAllType(map);
			respDataObject = RespHelper.setSuccessDataObject(respDataObject, data);
		}catch (Exception e) {
			log.error("getAllType Exception  ->",e);
		}
		return respDataObject;
	}
	
	/**
	 * 初始化影像资料
	 * 
	 * @param request
	 * @param orderDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/appBusInfo")
	public RespDataObject<Map<String, Object>> loadBusInfo(HttpServletRequest request,@RequestBody Map<String,Object> map) {
		RespDataObject<Map<String, Object>> resp = new RespDataObject<Map<String, Object>>();
		try {
			System.out.println("初始化影像资料参数:"+map.toString());
			String productCode = MapUtils.getString(map, "productCode");
			if("01".equals(productCode)||"02".equals(productCode)||"03".equals(productCode)||"04".equals(productCode)||"05".equals(productCode)||"06".equals(productCode)||"07".equals(productCode)){//债务置换贷款影像资料
				Map<String,Object> data = (Map<String,Object>)httpUtil.getData(Constants.LINK_CREDIT, "/credit/order/businfo/v/query", map);
				RespHelper.setSuccessDataObject(resp, MapUtils.getMap(data, "data"));
				return resp;
			}
			Map<String, Object> resultData = new HashMap<String, Object>();
			Map<String, Object> data = new HashMap<String, Object>();
			Map<String,Object> params = new HashMap<String, Object>();
			params.put("tblName", "tbl_cm_");
			params.putAll(map);
			data = configBusinfoTypeService.selectBusinfoInit(params);
			RespDataObject<Map<String,Object>> respD = httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/product/data/businfo/base/select", params, Map.class);
			List<Map<String, Object>> parentTypes = (List<Map<String, Object>>) data.get("parentTypeList");
			List<Map<String, Object>> listMap = new ArrayList<Map<String,Object>>();
			Map<String, Object> mapTemp = new HashMap<String,Object>();
			if("SUCCESS".equals(respD.getCode())&&null!=respD.getData()){
				listMap = (List<Map<String, Object>>) respD.getData().get("businfo");
			}
			List<Map<String, Object>> sonTypes = new ArrayList<Map<String,Object>>();
			for (Map<String, Object> parentType : parentTypes) {
				sonTypes = (List<Map<String, Object>>) parentType.get("sonTypeList");
				for (int i = 0; i < sonTypes.size(); i++) {
					int ids = Integer
							.parseInt(sonTypes.get(i).get("id").toString());
					List<Map<String, Object>> imgMap = new ArrayList<Map<String, Object>>();
					if(listMap!=null&&listMap.size()>0){
						for (int j = 0; j < listMap.size(); j++) {
							String jsonStr = JsonUtil.BeanToJson(listMap.get(j));
							mapTemp = JsonUtil.jsonToMap(jsonStr);
							String url = MapUtils.getIntValue(mapTemp, "typeId") + "";
							url = url.replace("_18", "_48");
							mapTemp.put("url", url);
							int id = MapUtils.getIntValue(mapTemp, "typeId");
							if (id == ids) {
								imgMap.add(mapTemp);
							}
						}
					}
					sonTypes.get(i).put("listMap", imgMap);
					sonTypes.get(i).put("docCount", imgMap.size());
				}
				parentType.put("sonTypes", sonTypes);
			}
			resultData.put("parentTypes", parentTypes);
			//判断是否可上传
			//设置是否要操作,是否可删除
			//查询订单列表节点
			String orderNo = MapUtils.getString(map, "orderNo");
			Map<String,Object> tMap = new HashMap<String, Object>();
			tMap.put("orderNo", orderNo);
			RespDataObject<Map<String,Object>> res = httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/product/data/list/base/v/select", tMap, Map.class);
			if(!"SUCCESS".equals(res.getCode())){
				RespHelper.setFailDataObject(resp, null, "列表数据查询失败");
				return resp;
			}
			Map<String,Object> maps = (Map<String, Object>) res.getData();
			String channelManagerUid = MapUtils.getString(maps, "channelManagerUid");
			String createUid = MapUtils.getString(maps, "createUid");
			String uid = getUserDto(request).getUid();
			String cityCode = MapUtils.getString(maps, "cityCode");
			int productId = Integer.parseInt(cityCode+productCode);
			boolean flag = compareProcessAround(productId,"auditSuccess",MapUtils.getString(maps, "processId"));
			if((createUid.equals(uid)||channelManagerUid.equals(uid))&&!flag){
				resultData.put("operate", true);
			}else{
				resultData.put("operate", false);
			}
			RespHelper.setSuccessDataObject(resp, resultData);
		} catch (Exception e) {
			log.info("loadBusInfo Exception ==>", e);
		}
		return resp;
	}
	
	/**
	 * 返回债务置换贷款订单和畅贷集合
	 * @param request
	 * @param map
	 * @return
	 */
	@ResponseBody 
	@RequestMapping(value="/orderList")
	public RespDataObject<List<Map<String, Object>>> orderList(HttpServletRequest request,@RequestBody Map<String,Object> map){
		RespDataObject<List<Map<String, Object>>> respDataObject = RespHelper.failDataObject(null);
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		if(!ValidHelper.isNotEmptyByKeys(map,"orderNo")){
			respDataObject.setMsg(RespStatusEnum.PARAMETER_ERROR.getMsg());
			return respDataObject;
		}
		try{
			Map<String,Object> maps = new HashMap<String, Object>();
			String productCode = MapUtils.getString(map, "productCode");
			if("01".equals(productCode)||"02".equals(productCode)||"03".equals(productCode)||"04".equals(productCode)||"05".equals(productCode)||"06".equals(productCode)||"07".equals(productCode)){
				list = httpUtil.getList(Constants.LINK_CREDIT, "/credit/order/businfo/v/orderList", map, Map.class);
			}else{
				if("10000".equals(productCode)){
					maps = new HashMap<String, Object>();
					maps.put("orderNo", MapUtils.getString(map, "orderNo"));
					maps.put("productName", "云按揭资料");
					maps.put("productCode", "10000");
					maps.put("isChangLoan", "2");
					list.add(maps);
				}
			}
			respDataObject = RespHelper.setSuccessDataObject(respDataObject, list);
		}catch (Exception e) {
			log.error("orderList Exception  ->",e);
		}
		return respDataObject;
	}

	/**
	 * 查询影像资料必备类型
	 * @param request
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping("selectNecessaryBusinfo")
	public RespDataObject<List<Map<String,Object>>> selectNecessaryBusinfo(HttpServletRequest request,@RequestBody Map<String,Object> map){
		RespDataObject<List<Map<String,Object>>> result = new RespDataObject<List<Map<String,Object>>>();
		try{
			List<Map<String,Object>> list = configBusinfoTypeService.selectNecessaryBusinfo(map);
			RespHelper.setSuccessDataObject(result,list);
		} catch (Exception e){
			RespHelper.setFailDataObject(result,null,"查询影像资料必备类型异常");
			log.error("查询影像资料必备类型异常:",e);
		}
		return result;
	}
}
