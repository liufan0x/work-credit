/*
 *Project: anjbo-credit-customer
 *File: com.anjbo.controller.AgencyProductController.java  <2017年11月20日>
 ****************************************************************
 * 版权所有@2017 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller;

import com.anjbo.bean.customer.AgencyProductDto;
import com.anjbo.bean.user.UserDto;
import com.anjbo.common.*;
import com.anjbo.service.AgencyProductService;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author KangLG 
 * @Date 2017年11月20日 下午4:26:32
 * @version 1.0
 */
@RestController
@RequestMapping("/credit/customer/agencyProduct")
public class AgencyProductController extends BaseController {
	@Autowired private AgencyProductService agencyProductService;
	
	@RequestMapping("/search")
	public RespData<AgencyProductDto> search(HttpServletRequest request){
		return this.search(this.getUserDto(request).getAgencyId());
	}
	@RequestMapping("/search_{agencyId}")
	public RespData<AgencyProductDto> search(@PathVariable("agencyId")int agencyId){
		AgencyProductDto dto = new AgencyProductDto();
		dto.setAgencyId(agencyId);
		return RespHelper.setSuccessData(new RespData<AgencyProductDto>(), agencyProductService.search(dto));
	}
	/**
	 * 构建启用的城市产品群（用于机构员工权限及提单）
	 * @Author KangLG<2017年12月4日>
	 * @param agencyId
	 * @return Map<城市编码, 产品编码串>
	 */
	@RequestMapping("/searchEnabled_{agencyId}")
	public RespDataObject<Map<String, String>> searchEnabled(@PathVariable("agencyId")int agencyId){
		Map<String, String> mapCityProduct = new HashMap<String, String>();
		
		AgencyProductDto dto = new AgencyProductDto();
		dto.setAgencyId(agencyId);
		dto.setStatus(1);		
		try{
			List<AgencyProductDto> lstAgencyProduct = agencyProductService.search(dto);
			if(null!=lstAgencyProduct && !lstAgencyProduct.isEmpty()){				
				for (AgencyProductDto agencyProductDto : lstAgencyProduct) {
					if(mapCityProduct.containsKey(agencyProductDto.getCityCode())){
						mapCityProduct.put(agencyProductDto.getCityCode(), MapUtils.getString(mapCityProduct, agencyProductDto.getCityCode()) +","+ agencyProductDto.getProductCode());
					}else{
						mapCityProduct.put(agencyProductDto.getCityCode(), agencyProductDto.getProductCode());
					}
				}
			}
		} catch (Exception e){
			e.printStackTrace();
			return RespHelper.setFailDataObject(new RespDataObject<Map<String, String>>(), null, RespStatusEnum.FAIL.getMsg());
		}		
		return RespHelper.setSuccessDataObject(new RespDataObject<Map<String, String>>(), mapCityProduct);
	}
		
	@ResponseBody
	@RequestMapping("/v/update")
	public RespStatus update(HttpServletRequest request,@RequestBody  AgencyProductDto productDto){
		RespStatus result = new RespStatus();
		try{
			if(productDto.getId()==0){
				RespHelper.setFailRespStatus(result, RespStatusEnum.FAIL.getMsg());
				return result;
			}
			UserDto user = getUserDto(request);
			productDto.setUpdateUid(user.getUid());
			agencyProductService.update(productDto);
			RespHelper.setSuccessRespStatus(result);
		} catch (Exception e){
			RespHelper.setFailRespStatus(result, RespStatusEnum.FAIL.getMsg());
			logger.error("更新机构产品信息异常:",e);
		}
		return result;
	}
	
	/**
	 * 构建启用的城市产品群（用于app点击提单检验）
	 * @Author Jiangyq<2018年01月03日>
	 * @param agencyId
	 * @return Map<城市名称, 产品编码串>
	 */
	@RequestMapping("/findProductList_{agencyId}")
	public RespDataObject<Map<String, String>> findProductListByAgencyId(@PathVariable("agencyId") int agencyId){
		Map<String, String> mapCityProduct = new HashMap<String, String>();
		AgencyProductDto dto = new AgencyProductDto();
		dto.setAgencyId(agencyId);
		dto.setStatus(1);		
		try{
			List<AgencyProductDto> lstAgencyProduct = agencyProductService.findAllCityProduct(dto);
			if(null!=lstAgencyProduct && !lstAgencyProduct.isEmpty()){				
				for (AgencyProductDto agencyProductDto : lstAgencyProduct) {
					mapCityProduct.put(agencyProductDto.getCityName(), agencyProductDto.getProductCode());
				}
			}
		} catch (Exception e){
			e.printStackTrace();
			return RespHelper.setFailDataObject(new RespDataObject<Map<String, String>>(), null, RespStatusEnum.FAIL.getMsg());
		}		
		return RespHelper.setSuccessDataObject(new RespDataObject<Map<String, String>>(), mapCityProduct);
	}
}
