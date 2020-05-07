package com.anjbo.controller;

import com.anjbo.bean.config.PageConfigDto;
import com.anjbo.common.RedisOperator;
import com.anjbo.controller.BaseController;

public class PageBaseController extends BaseController {	

	public String getTbl(String productCode) {
		if("100".equals(productCode)) {
			return "tbl_sm_";
		}else if("10000".equals(productCode)) {
			return "tbl_cm_";
		}
		return "tbl_sl_";
	}
	
	public String getProcessId(String productCode) {
		if("100".equals(productCode)) {
			return "waitSignAgency";
		}else if("100".equals(productCode)) {
			return "assess";
		}
		return "";
	}
	
	public PageConfigDto getPageConfigDto(String pageClass) {
		PageConfigDto pageConfigDto = (PageConfigDto) RedisOperator.get(pageClass);
		if (pageConfigDto == null) {
			pageConfigDto = (PageConfigDto) RedisOperator.get(pageClass);
		}
		return pageConfigDto;
	}
	
}
