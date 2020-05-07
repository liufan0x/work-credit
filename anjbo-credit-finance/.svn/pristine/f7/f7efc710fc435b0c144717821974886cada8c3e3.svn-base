package com.anjbo.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.common.Constants;
import com.anjbo.common.Enums;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
/**
 * 资金代扣
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/credit/finance/capital/v")
public class CapitalController extends BaseController{
	
	String appUrl=Constants.LINK_ANJBO_APP_URL;
	
	/**
	 * 查询余额
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("queryBalance")
	@ResponseBody
	public  RespDataObject<Map<String, Object>> queryBalance(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("uid", getUserDto(request).getUid());
		return httpUtil.getRespDataObject(appUrl, "mortgage/realname/v/queryBalance", paramMap,Map.class);
	}
	/**
	 * @param request
	 * @param paramMap
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/list") 
	public RespPageData<Map<String, Object>> list(HttpServletRequest request, @RequestBody Map<String,Object> paramMap){
		RespPageData<Map<String, Object>> resp = new RespPageData<Map<String, Object>>();
		resp.setCode(RespStatusEnum.FAIL.getCode());
		resp.setMsg(RespStatusEnum.FAIL.getMsg());
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			
			
			if("desc".equals(MapUtils.getString(paramMap, "sortOrder"))){
				if("withholdMoney".equals(MapUtils.getString(paramMap, "sortName"))){
					params.put("withholdMoney",2);
				}else if("withholdDate".equals(MapUtils.getString(paramMap, "sortName"))){
					params.put("withholdDate","2");
				}
			}else if("asc".equals(MapUtils.getString(paramMap, "sortOrder"))){
				if("withholdMoney".equals(MapUtils.getString(paramMap, "sortName"))){
					params.put("withholdMoney",1);
				}else if("withholdDate".equals(MapUtils.getString(paramMap, "sortName"))){
					params.put("withholdDate","1");
				}
			}
			
			params.put("name",MapUtils.getString(paramMap, "name"));
			params.put("idCard",MapUtils.getString(paramMap, "idCard"));
			params.put("phone",MapUtils.getString(paramMap, "phone"));
			params.put("withholdState",MapUtils.getString(paramMap, "withholdState"));
			params.put("start", MapUtils.getIntValue(paramMap, "start"));
			params.put("pagesize",MapUtils.getIntValue(paramMap, "pageSize"));
			Integer total = httpUtil.getObject(appUrl, "mortgage/myFinance/queryCount", params,Integer.class);
			resp.setTotal(total);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
			RespData<Map<String, Object>> respList = httpUtil.getRespData(appUrl, "mortgage/myFinance/query", params,Map.class);
			for (Map<String, Object> map : respList.getData()) {
				map.put("createTime", MapUtils.getString(map, "createTime"));
				map.put("appPayPwd", MapUtils.getString(map, "appPayPwd"));
				map.put("remarks", MapUtils.getString(map, "remarks"));
				map.put("bindTime", MapUtils.getString(map, "bindTime").equals("null") ? "" : MapUtils.getString(map, "bindTime"));
				map.put("withholdDate", MapUtils.getString(map, "withholdDate").equals("null") ? "" : MapUtils.getString(map, "withholdDate"));
				map.put("customerNo", MapUtils.getString(map, "customerNo").equals("null") ? "" : MapUtils.getString(map, "customerNo"));
				Date date = new Date();	
				if(null!=map.get("withholdDate") && ""!=map.get("withholdDate")){
					 date.setTime(Long.parseLong(map.get("withholdDate")+""));
					 map.put("withholdDate",sdf.format(date));
				}
				String updataUid=MapUtils.getString(map, "updateUid");
				map.put("updateUid", updataUid.equals("null") ? null : MapUtils.getString(map, "updateUid"));
			}
			resp.setRows(respList.getData());
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}
	
	/**
	 * 详情
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/queryDetails")
	@ResponseBody
	public RespPageData<Map<String, Object>> queryDetails(HttpServletRequest request,@RequestBody Map<String,Object> paramMap){
		RespPageData<Map<String, Object>> resp= new RespPageData<Map<String, Object>>(); 
		//参数flowNo流水号
		RespData<Map<String, Object>> respList = httpUtil.getRespData(appUrl, "mortgage/myFinance/queryDetails", paramMap,Map.class);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
		for (Map<String, Object> map : respList.getData()) {
			map.put("createTime", MapUtils.getString(map, "createTime").equals("null") ? "-" : MapUtils.getString(map, "createTime"));
			map.put("appPayPwd", MapUtils.getString(map, "appPayPwd").equals("null") ? "-" : MapUtils.getString(map, "appPayPwd"));
			map.put("remarks", MapUtils.getString(map, "remarks").equals("null") ? "" : MapUtils.getString(map, "appPayPwd"));
			map.put("withholdDate", MapUtils.getString(map, "withholdDate").equals("null") ? "" : MapUtils.getString(map, "withholdDate"));
			Date date = new Date();	
			if(null!=map.get("withholdDate") && ""!=map.get("withholdDate")){
				 date.setTime(Long.parseLong(map.get("withholdDate")+""));
				 map.put("withholdDate",sdf.format(date));
			}
			map.put("updateUid", MapUtils.getString(map, "updateUid").equals("null") ? "" : MapUtils.getString(map, "updateUid"));
			map.put("uid", MapUtils.getString(map, "uid").equals("null") ? "" : MapUtils.getString(map, "uid"));
			map.put("bindTime", MapUtils.getString(map, "bindTime").equals("null") ? "" : MapUtils.getString(map, "bindTime"));
		}
		resp.setRows(respList.getData());
		resp.setCode(RespStatusEnum.SUCCESS.getCode());
		resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		return resp;
	}
	
	/**
	 * 提现
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/withdrawals")
	@ResponseBody
	public RespDataObject<Map<String,Object>> withdrawals(HttpServletRequest request,@RequestBody Map<String,String> paramMap){
		paramMap.put("uid", getUserDto(request).getUid());
		paramMap.put("outCapitalType", "1");
		return httpUtil.getRespDataObject(appUrl, "mortgage/realname/v/outCapital", paramMap,Map.class);
	}
	
	/**
	 * 代扣
	 * @param request  
	 *   bindState绑定状态(0:初始化,1:已绑定,2:已解绑)
	 *   withholdState(0:初始化,1:未代扣,2:代扣中,3:已成功,4:失败,5重新代扣)
	 * @return
	 */
	@RequestMapping(value="/inCapital")
	@ResponseBody
	public RespDataObject<Map<String,Object>> inCapital(HttpServletRequest request,@RequestBody Map<String,String> paramMap){
		paramMap.put("uid", getUserDto(request).getUid());
		return httpUtil.getRespDataObject(appUrl, "mortgage/realname/v/inCapital", paramMap,Map.class);
	}
	
	/**
	 * 解绑
	 * @param request  
	 * unbind 是否能解绑(1可以,2禁止)
	 * @return
	 */
	@RequestMapping(value="/unbind")
	@ResponseBody
	public RespStatus  unbind(HttpServletRequest request,@RequestBody Map<String,String> paramMap){
		paramMap.put("updateUid", getUserDto(request).getUid());
		return httpUtil.getRespStatus(appUrl, "mortgage/myFinance/updateBindState", paramMap);
	}
}
