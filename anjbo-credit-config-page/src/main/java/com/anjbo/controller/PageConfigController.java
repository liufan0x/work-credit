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

import com.anjbo.bean.config.page.PageConfigDto;
import com.anjbo.bean.config.page.PageTabConfigDto;
import com.anjbo.bean.config.page.PageTabRegionConfigDto;
import com.anjbo.bean.config.page.PageTabRegionFormConfigDto;
import com.anjbo.common.Constants;
import com.anjbo.common.RedisKey;
import com.anjbo.common.RedisOperator;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.PageConfigService;

@Controller
@RequestMapping("/credit/config/page/base/v")
public class PageConfigController extends BaseController{

	private Log log = LogFactory.getLog(getClass());

	@Resource
	private PageConfigService pageConfigService;

	/**
	 * 初始化页面配置
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/initPageConfig") 
	public RespStatus initPageConfig(){
		RespStatus resp = new RespStatus();
		try {
			pageConfigService.initPageConfig();
			RespHelper.setSuccessRespStatus(resp);
			log.info("初始页面配置成功");
		} catch (Exception e) {
			log.error("初始页面配置失败", e);
			RespHelper.setFailRespStatus(resp, RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}


	/**
	 * 初始表单下拉框配置
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/initSelectConfig") 
	public RespStatus initSelectConfig(){
		RespStatus resp = new RespStatus();
		try {
			pageConfigService.initSelectConfig();
			RespHelper.setSuccessRespStatus(resp);
			log.info("初始表单下拉框配置成功");
		} catch (Exception e) {
			log.error("初始表单下拉框配置失败",e);
			RespHelper.setFailRespStatus(resp, RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}
	
	/**
	 * 初始订单基本信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/initOrderBaseInfo") 
	public RespStatus initOrderBaseInfo(){
		RespStatus resp = new RespStatus();
		try {
			Map<String,Object> map = new HashMap<String, Object>();
			List<Map<String,Object>> crOrder = httpUtil.getList(Constants.LINK_CREDIT, "/credit/order/base/v/selectAllOrder", map, Map.class);
			List<Map<String,Object>> cmOrder = httpUtil.getList(Constants.LINK_CREDIT, "/credit/product/data/list/base/v/selectAllOrder", map, Map.class);
			if(cmOrder==null) {
				cmOrder = new ArrayList<Map<String,Object>>();
			}
			cmOrder.addAll(crOrder);
			for (Map<String, Object> map2 : cmOrder) {
				RedisOperator.set(RedisKey.PREFIX.ANJBO_CREDIT_LOGININFO+MapUtils.getString(map2, "orderNo"),map2);
			}
			RespHelper.setSuccessRespStatus(resp);
			log.info("初始订单基本信息成功");
		} catch (Exception e) {
			log.error("初始订单基本信息失败",e);
			RespHelper.setFailRespStatus(resp, RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}

	/**
	 * 获取页面配置
	 * @param request
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/pageConfig") 
	public RespDataObject<PageConfigDto> pageConfig(HttpServletRequest request,@RequestBody Map<String, Object> params){
		RespDataObject<PageConfigDto> resp = new RespDataObject<PageConfigDto>();
		
		try {
			//测试代码开始
//			initPageConfig();
//			initSelectConfig();
			//测试代码结束
			
			PageConfigDto pageConfigDto = pageConfigService.pageConfig(params);
			RespHelper.setSuccessDataObject(resp, pageConfigDto);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("获取"+MapUtils.getString(params, "pageClass")+"页面配置失败", e);
			RespHelper.setFailDataObject(resp, null, RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}
	
	/**
	 * 获取页面tbl_config_page_tab_region要件项目配置
	 * @param request
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/pageTabRegionConfig") 
	public RespDataObject<Map<String,Object>> pageTabRegionConfig(HttpServletRequest request,@RequestBody Map<String, Object> params){
		RespDataObject<Map<String,Object>> resp = new RespDataObject<Map<String,Object>>();
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			PageConfigDto pageConfigDto = pageConfigService.pageConfig(params);
			for (PageTabConfigDto pageTabConfigDto : pageConfigDto.getPageTabConfigDtos()) {
				for (PageTabRegionConfigDto pageTabRegionConfigDto: pageTabConfigDto.getPageTabRegionConfigDtos()) {
					map.put(pageTabRegionConfigDto.getTitle(), pageTabRegionConfigDto.getRegionClass());
					for (PageTabRegionFormConfigDto pageTabRegionFormConfigDto: pageTabRegionConfigDto.getValueList().get(0)) {
						map.put(pageTabRegionFormConfigDto.getTitle(), pageTabRegionFormConfigDto.getFormClass());
					}
				}
			}
			RespHelper.setSuccessDataObject(resp, map);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("获取"+MapUtils.getString(params, "pageClass")+"页面配置失败", e);
			RespHelper.setFailDataObject(resp, null, RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}


	/**
	 * 获取区域表单
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getPageTabRegionConfigDto") 
	public RespData<PageTabRegionFormConfigDto> getPageTabRegionConfigDto(@RequestBody Map<String, Object> params){
		RespData<PageTabRegionFormConfigDto> resp = new RespData<PageTabRegionFormConfigDto>();
		try {
			RespHelper.setSuccessData(resp, pageConfigService.getPageTabRegionConfigDto(params));
		} catch (Exception e) {
			RespHelper.setFailData(resp, null, RespStatusEnum.FAIL.getMsg());
			e.printStackTrace();
			log.error("获取"+MapUtils.getString(params, "pageClass")+"-"+MapUtils.getString(params, "regionClass")+"页面区域配置失败", e);
		}
		return resp;
	}
	
	/**
	 * 获取区域表单
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getPageTabRegionConfigMap") 
	public RespDataObject<Map<String,Object>> getPageTabRegionConfigMap(@RequestBody Map<String, Object> params){
		RespDataObject<Map<String,Object>> resp = new RespDataObject<Map<String,Object>>();
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			PageConfigDto pageConfigDto = pageConfigService.pageConfig(params);
			for (PageTabConfigDto pageTabConfigDto : pageConfigDto.getPageTabConfigDtos()) {
				for (PageTabRegionConfigDto pageTabRegionConfigDto: pageTabConfigDto.getPageTabRegionConfigDtos()) {
					map.put(pageTabRegionConfigDto.getRegionClass(), pageTabRegionConfigDto.getValueList().get(0));
				}
			}
			RespHelper.setSuccessDataObject(resp, map);
		} catch (Exception e) {
			RespHelper.setFailDataObject(resp, null, RespStatusEnum.FAIL.getMsg());
			e.printStackTrace();
			log.error("获取"+MapUtils.getString(params, "pageClass")+"-"+MapUtils.getString(params, "regionClass")+"页面区域配置失败", e);
		}
		return resp;
	}

	/**
	 * 保存标签
	 * @param request
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/savePageTabConfigDto")
	public RespStatus savePageTabConfigDto(HttpServletRequest request,@RequestBody Map<String, Object> params){
		RespStatus resp = new RespStatus(); 
		try {
			String pageClass = MapUtils.getString(params, "pageClass");
			String tabClass = MapUtils.getString(params, "tabClass");
			PageConfigDto pageConfigDto = (PageConfigDto) RedisOperator.get(pageClass);
			params.put("packageClassMethodName", pageConfigDto.getPackageClassMethodName());
			for (PageTabConfigDto pageTabConfigDto : pageConfigDto.getPageTabConfigDtos()){
				if(StringUtils.isNotEmpty(tabClass) && !tabClass.equals(pageTabConfigDto.getTabClass())){
					continue;
				}
				if(!pageConfigService.checkPageTabConfigDto(pageTabConfigDto,params)){
					RespHelper.setFailRespStatus(resp, RespStatusEnum.PARAMETER_ERROR.getMsg());
					return resp;
				}else{
					//保存请求
					Map<String, Object> tempParams = new HashMap<String, Object>();
					tempParams.put("orderNo", MapUtils.getString(params, "orderNo"));
					tempParams.put("productCode", MapUtils.getString(params, "productCode"));
					tempParams.put("cityCode", MapUtils.getString(params, "cityCode"));
					tempParams.put("tblName", pageTabConfigDto.getTblName());
					tempParams.put("updateUid", getUserDto(request).getUid());
//					tempParams.put("key", MapUtils.getString(params, "key",""));
					tempParams.put("otherData", MapUtils.getMap(params, "otherTblName",null));
					tempParams.put("data", MapUtils.getObject(params, pageTabConfigDto.getTblName()));
					//将数据传入提交参数
					params.put("data", MapUtils.getObject(params, pageTabConfigDto.getTblName()));
					params.remove(pageTabConfigDto.getTblName());
					if("机构影像资料".equals(pageTabConfigDto.getTitle())
							||"影像资料".equals(pageTabConfigDto.getTitle())){
						continue;
					}
					System.out.println(tempParams);
					resp = httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/product/data/base/v/save", tempParams);
				}
			}
			
		} catch (Exception e) {
			RespHelper.setFailRespStatus(resp, RespStatusEnum.FAIL.getMsg());
			e.printStackTrace();
			log.error("保存"+MapUtils.getString(params, "pageClass")+"-"+MapUtils.getString(params, "tabClass")+"页面标签数据失败", e);
		}
		return resp;
	}

	/**
	 * 提交页面(即保存页面下所有标签数据)
	 * @param request
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/submitPageConfigDto")
	public RespStatus submitPageConfigDto(HttpServletRequest request,@RequestBody Map<String, Object> params){
		RespStatus resp = new RespStatus(); 
		String pageClass = MapUtils.getString(params, "pageClass");
		try {
			resp = savePageTabConfigDto(request,params);
			if(resp.getCode().equals(RespStatusEnum.SUCCESS.getCode())){
				//提交请求
				params.put("updateUid", getUserDto(request).getUid());
				resp = httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/product/data/submit/base/v/submit", params);
			}
		} catch (Exception e) {
			RespHelper.setFailRespStatus(resp, RespStatusEnum.FAIL.getMsg());
			e.printStackTrace();
			log.error("提交"+pageClass+"页面数据失败", e);
		}
		return resp;
	}
	
	/**
	 * 获取页面详情
	 * @param request
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/pageConfigDetail")
	public RespStatus pageConfigDetail(HttpServletRequest request,@RequestBody Map<String, Object> params){
		RespStatus resp = new RespStatus();
		String pageClass = MapUtils.getString(params, "pageClass");
		try {
			resp = savePageTabConfigDto(request,params);
			if(resp.getCode().equals(RespStatusEnum.SUCCESS.getCode())){
				//提交请求
				resp = httpUtil.getRespStatus(Constants.LINK_CREDIT, "/credit/product/data/base/v/", params);
			}
		} catch (Exception e) {
			RespHelper.setFailRespStatus(resp, RespStatusEnum.FAIL.getMsg());
			e.printStackTrace();
			log.error("提交"+pageClass+"页面数据失败", e);
		}
		return resp;
	}

}
