package com.anjbo.controller.impl;

import com.anjbo.bean.data.PageBusinfoDto;
import com.anjbo.bean.data.PageBusinfoTypeDto;
import com.anjbo.common.*;
import com.anjbo.controller.IPageBusinfoController;
import com.anjbo.controller.PageBaseController;
import com.anjbo.controller.api.UserApi;
import com.anjbo.service.PageBusinfoService;

import org.apache.commons.collections.MapUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.*;

@RestController
public class PageBusinfoController extends PageBaseController implements IPageBusinfoController{
	
	@Resource
	private PageBusinfoService pageBusinfoService;
	
	@Resource private UserApi userApi;
	
	@Override
	public RespData<PageBusinfoTypeDto> pageBusinfoConfig(@RequestBody Map<String, Object> params) {
		RespData<PageBusinfoTypeDto> resp = new RespData<PageBusinfoTypeDto>();
		try {
			RespHelper.setSuccessData(resp, pageBusinfoService.pageBusinfoConfig(params));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取"+MapUtils.getString(params, "productCode")+"影像资料配置失败", e);
			RespHelper.setFailData(resp, null, RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}
	

	@Override
	public RespData<PageBusinfoDto> selectPageBusinfo(@RequestBody PageBusinfoDto pageBusinfoDto) {
		try {
			pageBusinfoDto.setCreateUid(userApi.getUserDto().getUid());
			return RespHelper.setSuccessData(new RespData<PageBusinfoDto>(), pageBusinfoService.selectPageBusinfo(pageBusinfoDto));
		} catch (Exception e) {
			this.logger.error("新增影像资料异常", e);
			return RespHelper.setFailData(new RespData<PageBusinfoDto>(), new ArrayList<PageBusinfoDto>(), RespStatusEnum.FAIL.getMsg());
		}
	}
	
	@Override
	public RespStatus pageBusinfoSave(@RequestBody List<PageBusinfoDto> pageBusinfoDtos) {
		try {
			String uid = userApi.getUserDto().getUid();
			for (PageBusinfoDto pageBusinfoDto : pageBusinfoDtos) {
				pageBusinfoDto.setCreateUid(uid);;
			}
			pageBusinfoService.pageBusinfoSave(pageBusinfoDtos);
			return RespHelper.setSuccessRespStatus(new RespStatus());
		} catch (Exception e) {
			this.logger.error("新增影像资料异常", e);
			return RespHelper.failRespStatus();
		}
	}
	
	@Override
	public RespStatus pageBusinfoDelete(@RequestBody PageBusinfoDto pageBusinfoDto) {
		try {
			pageBusinfoDto.setCreateUid(userApi.getUserDto().getUid());
			pageBusinfoService.pageBusinfoDelete(pageBusinfoDto);
			return RespHelper.setSuccessRespStatus(new RespStatus());
		} catch (Exception e) {
			this.logger.error("删除影像资料异常", e);
			return RespHelper.failRespStatus();
		}
	}
	
	@Override
	public RespStatus pageBusinfoMove(@RequestBody PageBusinfoDto pageBusinfoDto) {
		try {
			pageBusinfoDto.setCreateUid(userApi.getUserDto().getUid());
			pageBusinfoService.pageBusinfoUpdate(pageBusinfoDto);
			return RespHelper.setSuccessRespStatus(new RespStatus());
		} catch (Exception e) {
			this.logger.error("移动影像资料异常", e);
			return RespHelper.failRespStatus();
		}
	}
	
}
