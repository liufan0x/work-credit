/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.anjbo.bean.AuthorityDto;
import com.anjbo.bean.DictDto;
import com.anjbo.bean.ProcessDto;
import com.anjbo.bean.ProductDto;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.BaseController;
import com.anjbo.controller.IAuthorityController;
import com.anjbo.controller.api.UserApi;
import com.anjbo.service.AuthorityService;
import com.anjbo.service.DictService;
import com.anjbo.service.ProcessService;
import com.anjbo.service.ProductService;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-05-15 09:25:27
 * @version 1.0
 */
@RestController
public class AuthorityController extends BaseController implements IAuthorityController{

	@Resource private AuthorityService authorityService;
	
	@Resource private DictService dictService;
	
	@Resource private ProductService productService;
	
	@Resource private ProcessService processService;
	
	@Resource private UserApi userApi;

	/**
	 * 分页查询
	 * @author Generator 
	 */
	@Override
	public RespPageData<AuthorityDto> page(@RequestBody AuthorityDto dto){
		RespPageData<AuthorityDto> resp = new RespPageData<AuthorityDto>();
		try {
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
			resp.setRows(authorityService.search(dto));
			resp.setTotal(authorityService.count(dto));
		}catch (Exception e) {
			logger.error("分页异常,参数："+dto.toString(), e);
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}


	/**
	 * 查询
	 * @author Generator 
	 */
	@Override
	public RespData<AuthorityDto> search(@RequestBody AuthorityDto dto){ 
		RespData<AuthorityDto> resp = new RespData<AuthorityDto>();
		try {
			return RespHelper.setSuccessData(resp, authorityService.search(dto));
		}catch (Exception e) {
			logger.error("查询异常,参数："+dto.toString(), e);
			return RespHelper.setFailData(resp, new ArrayList<AuthorityDto>(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 查找
	 * @author Generator 
	 */
	@Override
	public RespDataObject<AuthorityDto> find(@RequestBody AuthorityDto dto){ 
		RespDataObject<AuthorityDto> resp = new RespDataObject<AuthorityDto>();
		try {
			return RespHelper.setSuccessDataObject(resp, authorityService.find(dto));
		}catch (Exception e) {
			logger.error("查找异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new AuthorityDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 新增
	 * @author Generator 
	 */
	@Override
	public RespDataObject<AuthorityDto> add(@RequestBody AuthorityDto dto){ 
		RespDataObject<AuthorityDto> resp = new RespDataObject<AuthorityDto>();
		try {
			dto.setCreateUid(userApi.getUserDto().getUid());
			return RespHelper.setSuccessDataObject(resp, authorityService.insert(dto));
		}catch (Exception e) {
			logger.error("新增异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new AuthorityDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 编辑
	 * @author Generator 
	 */
	@Override
	public RespStatus edit(@RequestBody AuthorityDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			dto.setUpdateUid(userApi.getUserDto().getUid());
			authorityService.update(dto);
			return RespHelper.setSuccessRespStatus(resp);
		}catch (Exception e) {
			logger.error("编辑异常,参数："+dto.toString(), e);
			return RespHelper.failRespStatus();
		}
	}

	/**
	 * 删除
	 * @author Generator 
	 */
	@Override
	public RespStatus delete(@RequestBody AuthorityDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			authorityService.delete(dto);
			return RespHelper.setSuccessRespStatus(resp);
		}catch (Exception e) {
			logger.error("删除异常,参数："+dto.toString(), e);
			return RespHelper.failRespStatus();
		}
	}
	
	@Override
	public RespData<Map<String, Object>> selectCityProductAuthority() {
		RespData<Map<String, Object>> resp = new RespData<Map<String, Object>>();
		try {
			List<Map<String, Object>> cityList = new ArrayList<Map<String,Object>>();
			DictDto dict = new DictDto();
			dict.setType("cityList");
			List<DictDto> cityListTemp = dictService.search(dict);
			List<ProductDto> productListTemp = productService.search(null);
			List<ProcessDto> processListTemp = processService.search(null);
			List<AuthorityDto> authorityListTemp = authorityService.search(null);
			for (DictDto dictDto : cityListTemp) {
				Map<String, Object> cityMap = new HashMap<String, Object>();
				cityMap.put("id", dictDto.getCode());
				cityMap.put("name", dictDto.getName());
				List<Map<String, Object>> productList = new ArrayList<Map<String,Object>>();
				for (ProductDto productDto : productListTemp) {
					if(productDto.getCityCode().equals(dictDto.getCode()) && !productDto.getProductCode().contains("10000")){
						Map<String, Object> productMap = new HashMap<String, Object>();
						productMap.put("id", productDto.getId());
						productMap.put("name", productDto.getProductName());
						List<Map<String, Object>> processList = new ArrayList<Map<String,Object>>();
						for (ProcessDto processDto : processListTemp) {
							if(processDto.getProductId().equals(productDto.getId())) {
								Map<String, Object> processMap = new HashMap<String, Object>();
								processMap.put("id", processDto.getId());
								processMap.put("name", processDto.getProcessName());
								List<AuthorityDto> authorityList = new ArrayList<AuthorityDto>();
								for (AuthorityDto authorityMap : authorityListTemp) {
									if(authorityMap.getProcessId() != null && authorityMap.getProcessId() > 0){
										if(processDto.getId().equals(authorityMap.getProcessId())){
											authorityList.add(authorityMap);
										}
									}
								}
								processMap.put("authorityList", authorityList);
								processList.add(processMap);
							}
						}
						productMap.put("productProcessList", processList);
						productList.add(productMap);
					}
				}
				cityMap.put("productList", productList);
				cityList.add(cityMap);
			}
			RespHelper.setSuccessData(resp, cityList);
		} catch (Exception e) {
			logger.error("加载城市产品权限异常", e);
			RespHelper.setFailData(resp, null, RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}
		
}