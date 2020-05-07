package com.anjbo.controller;

import com.anjbo.bean.user.UserDto;
import com.anjbo.common.*;
import com.anjbo.service.ProductListBaseService;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/credit/product/data/list/base/v")
public class ProductListBaseController extends BaseController{
	
	@Resource
	private ProductListBaseService productListBaseService;
	
	//查询列表方法 查询tbl_xx_list，需要分页。 刷选条件会通过配置来（刷选条件可暂时不做）
	@RequestMapping("page")
	@ResponseBody
	public RespPageData<Map<String,Object>> page(HttpServletRequest request,@RequestBody Map<String,Object> map){
		RespPageData<Map<String,Object>> result = new RespPageData<Map<String,Object>>();
		try {
			UserDto userDto = getUserDto(request);
			String deptAllUid = "";
			if(MapUtils.getString(map,"tblName").contains("tbl_sm")){
				Map<String,Object> um = getSelectUid(userDto);
				deptAllUid = MapUtils.getString(um,"uids");
			} else {
				//查看全部订单
				if(userDto.getAuthIds().contains("1")){
					//查看部门订单
				}else if(userDto.getAuthIds().contains("2")){
					userDto.setCreateTime(null);
					userDto.setUpdateTime(null);
					RespDataObject<Map<String,String>> respTemp = httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/user/base/v/selectUidsByDeptId", userDto, Map.class);
					deptAllUid = MapUtils.getString(respTemp.getData(), "uids");
					//查看自己订单
				}else{
					deptAllUid = userDto.getUid();
				}
			}
			String tblName = MapUtils.getString(map, "tblName");
			String tblDataName = tblName.substring(0, tblName.indexOf("_",5))+"_list";
			map.put("tblDataName", tblDataName);
			map.put("deptAllUid", deptAllUid);
			map.put("uid", userDto.getUid());
			List<Map<String,Object>> data = productListBaseService.selectProductListBase(map);
			int count = productListBaseService.selectProductListBaseCount(map);
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
			result.setRows(data);
			result.setTotal(count); 
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(RespStatusEnum.FAIL.getCode());
			result.setMsg(RespStatusEnum.FAIL.getMsg());
			return result;
		}
	}
	
	@RequestMapping("save")
	@ResponseBody
	public RespStatus update(HttpServletRequest request,@RequestBody Map<String,Object> map){
		RespStatus respStatus = new RespStatus();
		try {
			UserDto user = getUserDto(request);
			if(user!=null && user.getUid()!=null){
				map.put("createUid", user.getUid());
				map.put("updateUid", user.getUid());
			}
			String tblName = MapUtils.getString(map, "tblName");
			String tblDataName = tblName.substring(0, tblName.indexOf("_",5))+"_list";
			map.put("tblDataName", tblDataName);
			productListBaseService.insertProductListBase(map);
		} catch (Exception e) {
			e.printStackTrace();
			RespHelper.setFailRespStatus(respStatus, "更新"+MapUtils.getString(map, "tblName")+"异常");
			return respStatus;
		}
		RespHelper.setSuccessRespStatus(respStatus);
		return respStatus;
	}
	
	@RequestMapping("close")
	@ResponseBody
	public RespStatus close(HttpServletRequest request,@RequestBody Map<String,Object> map){
		RespStatus respStatus = new RespStatus();
		try {
			UserDto user = getUserDto(request);
			if(user!=null && user.getUid()!=null){
				map.put("createUid", user.getUid());
				map.put("updateUid", user.getUid());
			}
			productListBaseService.close(map);
		} catch (Exception e) {
			e.printStackTrace();
			RespHelper.setFailRespStatus(respStatus, "关闭订单异常");
			return respStatus;
		}
		RespHelper.setSuccessRespStatus(respStatus);
		return respStatus;
	}
	
	@RequestMapping("reopen")
	@ResponseBody
	public RespStatus reopen(HttpServletRequest request,@RequestBody Map<String,Object> map){
		RespStatus respStatus = new RespStatus();
		try {
			UserDto user = getUserDto(request);
			if(user!=null && user.getUid()!=null){
				map.put("createUid", user.getUid());
				map.put("updateUid", user.getUid());
				map.put("currentHandlerUid", user.getUid());
				map.put("currentHandler", user.getName());
			}
			productListBaseService.reopen(map);
		} catch (Exception e) {
			e.printStackTrace();
			RespHelper.setFailRespStatus(respStatus, "重新开启订单异常");
			return respStatus;
		}
		RespHelper.setSuccessRespStatus(respStatus);
		return respStatus;
	}
	
	/**
	 * 指派渠道经理撤回
	 */
	@RequestMapping("withdraw")
	@ResponseBody
	public RespStatus withdraw(HttpServletRequest request,@RequestBody Map<String,Object> map){
		RespStatus respStatus = new RespStatus();
		try {
			UserDto user = getUserDto(request);
			if(user!=null && user.getUid()!=null){
				map.put("createUid", user.getUid());
				map.put("updateUid", user.getUid());
				map.put("currentHandlerUid", user.getUid());
				map.put("currentHandler", user.getName());
			}
			productListBaseService.withdraw(map);
		} catch (Exception e) {
			e.printStackTrace();
			RespHelper.setFailRespStatus(respStatus, "撤回订单失败");
			return respStatus;
		}
		RespHelper.setSuccessRespStatus(respStatus);
		return respStatus;
	}
	/**
	 * 指派渠道经理
	 */
	@RequestMapping("repaymentChannelManager")
	@ResponseBody
	public RespStatus repaymentChannelManager(HttpServletRequest request,@RequestBody Map<String,Object> map){
		RespStatus respStatus = new RespStatus();
		try {
			UserDto user = getUserDto(request);
			if(user!=null && user.getUid()!=null){
				map.put("createUid", user.getUid());
				map.put("updateUid", user.getUid());
			}
			productListBaseService.repaymentChannelManager(map);
		} catch (Exception e) {
			e.printStackTrace();
			RespHelper.setFailRespStatus(respStatus, "指派渠道经理失败");
			return respStatus;
		}
		RespHelper.setSuccessRespStatus(respStatus);
		return respStatus;
	}
	
	@RequestMapping("select")
	@ResponseBody
	public RespDataObject<Map<String,Object>> select(HttpServletRequest request,@RequestBody Map<String,Object> params){
		RespDataObject<Map<String,Object>> resp = new RespDataObject<Map<String,Object>>();
		try {
			Map<String,Object> data = productListBaseService.selectOrderList(params);
			RespHelper.setSuccessDataObject(resp, data);
		} catch (Exception e) {
			e.printStackTrace();
			RespHelper.setFailRespStatus(resp, "查询订单列表数据失败");
			return resp;
		}
		return resp;
	}
	
	@ResponseBody
	@RequestMapping(value = "/selectAllOrder") 
	public RespDataObject<List<Map<String, Object>>> selectAllOrder(HttpServletRequest request,@RequestBody Map<String,Object> map){
		RespDataObject<List<Map<String, Object>>> resp = new RespDataObject<List<Map<String, Object>>>();
		try {
			List<Map<String, Object>> list = productListBaseService.selectAllOrder(map);
			RespHelper.setSuccessDataObject(resp, list);
		} catch (Exception e) {
			e.printStackTrace();
			RespHelper.setFailDataObject(resp, null, RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}
	/**
	 * 获取用户查询权限uid
	 * @param userDto
	 * @return java.util.Map key=uids(关联的uid),key=ids(如果是受理员则会返回该字段)
	 */
	public Map<String,Object> getSelectUid(UserDto userDto){
		Map<String,Object> selectMap = new HashMap<String,Object>();
		String deptAllUid = "";
		//查看全部订单
		if(userDto.getAuthIds().contains("102")){
			//查看部门订单
		}else if(userDto.getAuthIds().contains("103")){
			userDto.setCreateTime(null);
			userDto.setUpdateTime(null);
			RespDataObject<Map<String,String>> respTemp = httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/user/base/v/selectUidsByDeptId",userDto , Map.class);
			deptAllUid = MapUtils.getString(respTemp.getData(), "uids");
			//查看自己订单
		}else{
			deptAllUid = userDto.getUid();
		}
		selectMap.put("uids",deptAllUid);
		return  selectMap;
	}
}
