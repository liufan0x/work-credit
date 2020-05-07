 package com.anjbo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.bean.common.DictDto;
import com.anjbo.bean.product.ProductDto;
import com.anjbo.bean.product.ProductProcessDto;
import com.anjbo.bean.user.AuthorityDto;
import com.anjbo.bean.user.RoleDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.Constants;
import com.anjbo.common.RedisOperator;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.AuthorityService;
import com.anjbo.utils.CommonDataUtil;
import com.anjbo.utils.StringUtil;


@Controller
@RequestMapping("/credit/user/authority/v")
public class AuthorityController extends BaseController{

	private Log log = LogFactory.getLog(getClass());
	
	@Resource
	private AuthorityService authorityService;
	

	@ResponseBody
	@RequestMapping(value = "/selectAuthorityByProductId")
	public RespDataObject<Map<String, Object>> selectAuthorityByProductId(@RequestBody Map<String, Object> params){
		RespDataObject<Map<String, Object>> resp = new RespDataObject<Map<String,Object>>();
		try {

			String productId = MapUtils.getString(params, "productId");
			List<ProductProcessDto> productProcessDtos = CommonDataUtil.getProductProcessDto(productId);
			String processIds = "";
			for (ProductProcessDto productProcessDto : productProcessDtos) {
				processIds += productProcessDto.getId()+",";
			}
			if(StringUtils.isNotEmpty(processIds)){
				processIds = processIds.substring(0, processIds.length()-1);
			}
			
			List<AuthorityDto> authorityDtos = authorityService.selectAuthorityByProcessIds(processIds);
			Map<String, Object> retMap = new HashMap<String, Object>();
			for (AuthorityDto authorityDto : authorityDtos) {
				String processId = "";
				
				for (ProductProcessDto productProcessDto : productProcessDtos) {
					if(authorityDto.getProcessId() == productProcessDto.getId()){
						processId = productProcessDto.getProcessId();
						break;
					}
				}
				if(authorityDto.getName().contains("[A]")){
					retMap.put(processId+"[A]", authorityDto.getId());
				}else{
					retMap.put(processId+"[B]", authorityDto.getId());
				}
				
			}
			RespHelper.setSuccessDataObject(resp, retMap);
		} catch (Exception e) {
			log.error(e);
			RespHelper.setFailDataObject(resp, null, RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}
	
	/**
	 * 城市产品权限
	 * @Author LiC<2017年07月06日>
	 * @Author KangLG<2017年11月22日> 新增isAgency参数
	 * @param isAgency 是否机构调用
	 * @return
	 */
	private RespData<Map<String, Object>> loadingCityProductAuthority(HttpServletRequest request, boolean isAgency){
		RespData<Map<String, Object>> resp = new RespData<Map<String, Object>>();
		try {
			List<Map<String, Object>> cityList = new ArrayList<Map<String,Object>>();
			List<ProductDto> productListTemp = getProductDtos();
			List<Map<String, Object>> authorityListTemp = authorityService.selectAuthority();
			// 机构城市产品
			Map<String, String> mapCityProduct = null;
			if(isAgency){
				RespDataObject<Map<String, String>> respData = httpUtil.getRespDataObject(Constants.LINK_CREDIT, "/credit/customer/agencyProduct/searchEnabled_"+this.getUserDto(request).getAgencyId(), null, Map.class);
				if(RespStatusEnum.SUCCESS.getCode().equals(respData.getCode())){
					mapCityProduct = respData.getData();
				}
			}
			
			List<DictDto> cityListTemp = getDictDtoByType("bookingSzAreaOid");
			for (DictDto dictDto : cityListTemp) {
				if(StringUtil.isEmpty(dictDto.getPcode())){
					//机构已开通城市
					if(isAgency && null!=mapCityProduct && !mapCityProduct.containsKey(dictDto.getCode())){
						continue;
					}
					Map<String, Object> cityMap = new HashMap<String, Object>();
					cityMap.put("id", dictDto.getCode());
					cityMap.put("name", dictDto.getName());
					List<Map<String, Object>> productList = new ArrayList<Map<String,Object>>();
					for (ProductDto productDto : productListTemp) {
						if(productDto.getCityCode().equals(dictDto.getCode())){
							//机构已开通城市产品
							if(isAgency && null!=mapCityProduct ){
								String products = String.format(",%s,", MapUtils.getString(mapCityProduct, productDto.getCityCode(), "0"));
								if(!products.contains(String.format(",%s,", productDto.getProductCode()))){
									continue;
								}
							}
							Map<String, Object> productMap = new HashMap<String, Object>();
							productMap.put("id", productDto.getId());
							productMap.put("name", productDto.getProductName());
							List<Map<String, Object>> productProcessList = new ArrayList<Map<String,Object>>();
							for (ProductProcessDto productProcessDto : productDto.getProductProcessDtos()) {
								// 机构产品流程节点
								if(isAgency && 1!=productProcessDto.getShowAgency()){
									continue;
								}									
								Map<String, Object> productProcessMap = new HashMap<String, Object>();
								productProcessMap.put("id", productProcessDto.getId());
								productProcessMap.put("name", productProcessDto.getProcessName());
								List<Map<String, Object>> authorityList = new ArrayList<Map<String,Object>>();
								for (Map<String, Object> authorityMap : authorityListTemp) {
									if(MapUtils.getIntValue(authorityMap, "processId",0) != 0){
										if(productProcessDto.getId() == MapUtils.getIntValue(authorityMap, "processId")){
											authorityList.add(authorityMap);
										}
									}
								}
								productProcessMap.put("authorityList", authorityList);
								productProcessList.add(productProcessMap);
							}
							productMap.put("productProcessList", productProcessList);
							productList.add(productMap);
						}
					}
					cityMap.put("productList", productList);
					cityList.add(cityMap);
				}
			}
			RespHelper.setSuccessData(resp, cityList);
		} catch (Exception e) {
			log.error(e);
			RespHelper.setFailData(resp, null, RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}
	@ResponseBody
	@RequestMapping(value = "/selectCityProductAuthority")
	public RespData<Map<String, Object>> selectCityProductAuthority(HttpServletRequest request){
		return this.loadingCityProductAuthority(request, false);
	}
	@ResponseBody
	@RequestMapping(value = "/selectCityProductAuthorityByAgency")
	public RespData<Map<String, Object>> selectCityProductAuthorityByAgency(HttpServletRequest request){
		return this.loadingCityProductAuthority(request, true);
	}
	
	@ResponseBody
	@RequestMapping(value = "/selectAuthority")
	public RespData<Map<String, Object>> selectAuthority(){
		RespData<Map<String, Object>> resp = new RespData<Map<String, Object>>();
		try {
			List<Map<String, Object>> resourceList = authorityService.selectResource();
			List<Map<String, Object>> authorityList = authorityService.selectAuthority();
			for (Map<String, Object> resourceMap : resourceList) {
				List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
				for (Map<String, Object> authorityMap : authorityList) {
					if(MapUtils.getIntValue(authorityMap, "resourceId",0) != 0){
						if(MapUtils.getIntValue(resourceMap, "id") == MapUtils.getIntValue(authorityMap, "resourceId")){
							list.add(authorityMap);
						}
					}
				}
				if(MapUtils.getString(resourceMap, "resourceName").equals("看单权限配置")){
					Map<String, Object> authorityMap = new HashMap<String, Object>();
					list.add(authorityMap);
				}
				resourceMap.put("authorityList", list);
			}
			RespHelper.setSuccessData(resp, resourceList);
		} catch (Exception e) {
			log.error(e);
			RespHelper.setFailData(resp, null, RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}
	
	@ResponseBody
	@RequestMapping(value = "/selectUserAuthorityByUid") 
	public RespDataObject<String> selectUserAuthorityByUid(@RequestBody UserDto userDto){
		RespDataObject<String> resp = new RespDataObject<String>();
		try {
			Map<String, Object> map = authorityService.selectUserAuthority(userDto.getUid());
			RespHelper.setSuccessDataObject(resp, MapUtils.getString(map, "authorityId"));
		} catch (Exception e) {
			log.error(e);
			RespHelper.setFailDataObject(resp, null, RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}

	@ResponseBody
	@RequestMapping(value = "/selectRoleAuthorityByRoleId")
	public RespDataObject<String> selectRoleAuthorityByRoleId(@RequestBody RoleDto roleDto){
		RespDataObject<String> resp = new RespDataObject<String>();
		try {
			Map<String, Object> map = authorityService.selectRoleAuthority(roleDto.getId());
			RespHelper.setSuccessDataObject(resp, MapUtils.getString(map, "authorityId"));
		} catch (Exception e) {
			log.error(e);
			RespHelper.setFailDataObject(resp, null, RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}
	
	@Resource
	private UserUtilController userUtilController;
	
	@ResponseBody
	@RequestMapping(value = "/updateUserAuthority") 
	public RespDataObject<String> updateUserAuthority(HttpServletRequest request,@RequestBody Map<String, Object> params){
		RespDataObject<String> resp = new RespDataObject<String>();
		RespHelper.setFailRespStatus(resp, RespStatusEnum.FAIL.getMsg());
		try {
			if(authorityService.updateUserAuthority(params)>0){
				RespHelper.setSuccessRespStatus(resp);
				RedisOperator.set("userList", userUtilController.userList().getData());
				RedisOperator.set("updateAuth"+MapUtils.getString(params, "uid"), true);
			}
		} catch (Exception e) {
			log.error(e);
			RespHelper.setFailRespStatus(resp, RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}
	

	@ResponseBody
	@RequestMapping(value = "/updateRoleAuthority") 
	public RespDataObject<String> updateRoleAuthority(@RequestBody Map<String, Object> params){
		RespDataObject<String> resp = new RespDataObject<String>();
		RespHelper.setFailRespStatus(resp, RespStatusEnum.FAIL.getMsg());
		try {
			if(authorityService.updateRoleAuthority(params)>0){
				RespHelper.setSuccessRespStatus(resp);
			}
		} catch (Exception e) {
			log.error(e);
			RespHelper.setFailRespStatus(resp, RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}
	
}
