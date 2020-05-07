package com.anjbo.controller;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.anjbo.bean.config.list.ListConfigDto;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.PageListConfigService;
import com.anjbo.utils.UidUtil;

@Controller
@RequestMapping("/credit/config/page/list/v")
public class PageListConfigController extends BaseController{

	private Log log = LogFactory.getLog(getClass());
	
	@Resource
	private PageListConfigService pageListConfigService;
	
	@ResponseBody
	@RequestMapping(value = "/pageListConfig") 
	public RespDataObject<ListConfigDto> pageListConfig(@RequestBody ListConfigDto listConfigDto){
		RespDataObject<ListConfigDto> resp = new RespDataObject<ListConfigDto>();
		try {
			listConfigDto = pageListConfigService.selectPageListConfig(listConfigDto);
			RespHelper.setSuccessDataObject(resp, listConfigDto);
			log.info("初始页面配置成功");
		} catch (Exception e) {
			log.error("初始页面配置失败", e);
			RespHelper.setFailRespStatus(resp, RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}
	
	
	/**
	 * 获取新的订单号
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/generateOrderNo")
	public RespDataObject<String> generateOrderNo(HttpServletRequest request){
		return RespHelper.setSuccessDataObject(new RespDataObject<String>(), UidUtil.generateOrderId());
	}
	
}
