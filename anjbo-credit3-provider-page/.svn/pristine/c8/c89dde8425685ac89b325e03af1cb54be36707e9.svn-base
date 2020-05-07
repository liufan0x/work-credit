/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller.impl;

import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.collections.MapUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.anjbo.bean.UserDto;
import com.anjbo.bean.config.PageConfigDto;
import com.anjbo.bean.config.PageListConfigDto;
import com.anjbo.bean.config.PageTabRegionFormConfigDto;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.PageBaseController;
import com.anjbo.controller.api.UserApi;
import com.anjbo.controller.IPageConfigController;
import com.anjbo.service.PageConfigService;
import com.anjbo.service.PageDataService;
import com.anjbo.service.PageListService;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-05-15 09:25:27
 * @version 1.0
 */
@RestController
public class PageConfigController extends PageBaseController implements IPageConfigController{
	
	@Resource private PageConfigService pageConfigService;
	
	@Resource private PageDataService pageDataService;
	
	@Resource private PageListService pageListService;
	
	@Resource private UserApi userApi;
	
	private void getTblDataName(Map<String, Object> params) {
		String productCode = MapUtils.getString(params, "productCode","");
		String processId = MapUtils.getString(params, "processId",getProcessId(productCode));
		String tblName = getTbl(productCode);
		UserDto userDto = userApi.getUserDto();
		params.put("tblName", tblName);
		params.put("userUid", userDto.getUid());
		params.put("userName", userDto.getName());
		params.put("pageClass", tblName + processId + "_page");
	}
	
	@Override
	public RespDataObject<PageListConfigDto> pageListConfig(@RequestBody PageListConfigDto pageListConfigDto) {
		RespDataObject<PageListConfigDto> resp = new RespDataObject<PageListConfigDto>();
		try {
			pageListConfigDto = pageConfigService.selectPageListConfig(pageListConfigDto);
			RespHelper.setSuccessDataObject(resp, pageListConfigDto);
			logger.info("初始页面配置成功");
		} catch (Exception e) {
			logger.error("初始页面配置失败", e);
			RespHelper.setFailRespStatus(resp, RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}
	
	@Override
	public RespDataObject<PageConfigDto> pageConfig(@RequestBody Map<String, Object> params) {
		RespDataObject<PageConfigDto> resp = new RespDataObject<PageConfigDto>();
		try {
			pageConfigService.initPageConfig();
			pageConfigService.initSelectConfig();
			getTblDataName(params);
			PageConfigDto pageConfigDto = pageConfigService.pageConfig(params);
			RespHelper.setSuccessDataObject(resp, pageConfigDto);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取"+MapUtils.getString(params, "pageClass")+"页面配置失败", e);
			RespHelper.setFailDataObject(resp, null, RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}
	
	@Override
	public RespData<PageTabRegionFormConfigDto> pageTabRegionConfigDto(@RequestBody Map<String, Object> params) {
		RespData<PageTabRegionFormConfigDto> resp = new RespData<PageTabRegionFormConfigDto>();
		try {
			getTblDataName(params);
			RespHelper.setSuccessData(resp,pageConfigService.getPageTabRegionConfigDto(params));
		} catch (Exception e) {
			RespHelper.setFailData(resp, null, RespStatusEnum.FAIL.getMsg());
			logger.error("获取"+MapUtils.getString(params, "pageClass")+"-"+MapUtils.getString(params, "regionClass")+"页面区域配置失败", e);
		}
		return resp;
	}
}