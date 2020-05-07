/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller.impl;

import java.util.ArrayList;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.anjbo.controller.api.UserApi;
import com.anjbo.bean.data.PageDataDto;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.IPageDataController;
import com.anjbo.controller.PageBaseController;
import com.anjbo.service.PageDataService;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-06-28 20:20:05
 * @version 1.0
 */
@RestController
public class PageDataController extends PageBaseController implements IPageDataController{

	@Resource private PageDataService pageDataService;
	
	@Resource private UserApi userApi;

	/**
	 * 分页查询
	 * @author Generator 
	 */
	@Override
	public RespPageData<PageDataDto> page(@RequestBody PageDataDto dto){
		RespPageData<PageDataDto> resp = new RespPageData<PageDataDto>();
		try {
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
			resp.setRows(pageDataService.search(dto));
			resp.setTotal(pageDataService.count(dto));
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
	public RespData<PageDataDto> search(@RequestBody PageDataDto dto){ 
		RespData<PageDataDto> resp = new RespData<PageDataDto>();
		try {
			return RespHelper.setSuccessData(resp, pageDataService.search(dto));
		}catch (Exception e) {
			logger.error("查询异常,参数："+dto.toString(), e);
			return RespHelper.setFailData(resp, new ArrayList<PageDataDto>(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 查找
	 * @author Generator 
	 */
	@Override
	public RespDataObject<PageDataDto> find(@RequestBody PageDataDto dto){ 
		RespDataObject<PageDataDto> resp = new RespDataObject<PageDataDto>();
		try {
			return RespHelper.setSuccessDataObject(resp, pageDataService.find(dto));
		}catch (Exception e) {
			logger.error("查找异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new PageDataDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 新增
	 * @author Generator 
	 */
	@Override
	public RespDataObject<PageDataDto> add(@RequestBody PageDataDto dto){ 
		RespDataObject<PageDataDto> resp = new RespDataObject<PageDataDto>();
		try {
			dto.setCreateUid(userApi.getUserDto().getUid());
			return RespHelper.setSuccessDataObject(resp, pageDataService.insert(dto));
		}catch (Exception e) {
			logger.error("新增异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new PageDataDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 编辑
	 * @author Generator 
	 */
	@Override
	public RespStatus edit(@RequestBody PageDataDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			dto.setUpdateUid(userApi.getUserDto().getUid());
			pageDataService.update(dto);
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
	public RespStatus delete(@RequestBody PageDataDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			pageDataService.delete(dto);
			return RespHelper.setSuccessRespStatus(resp);
		}catch (Exception e) {
			logger.error("删除异常,参数："+dto.toString(), e);
			return RespHelper.failRespStatus();
		}
	}
		
	@Override
	public RespStatus savePageTabConfigDto(@RequestBody PageDataDto pageDataDto) {
		RespStatus resp = new RespStatus();
		String pageClass = getTbl(pageDataDto.getProductCode()) + pageDataDto.getProcessId() + "_page";
		try {
			pageDataDto.setUpdateUid(userApi.getUserDto().getUid());
			pageDataService.savePageTabConfigDto(pageDataDto,getPageConfigDto(pageClass));
			return RespHelper.setSuccessRespStatus(resp);
		} catch (Exception e) {
			logger.error("保存标签页失败pageClass:"+pageClass, e);
			return RespHelper.failRespStatus();
		}
	}
	
	@Override
	public RespStatus submitPageConfigDto(@RequestBody PageDataDto pageDataDto) {
		RespStatus resp = new RespStatus();
		String pageClass = getTbl(pageDataDto.getProductCode()) + pageDataDto.getProcessId() + "_page";
		try {
			pageDataDto.setUpdateUid(userApi.getUserDto().getUid());
			pageDataService.savePageTabConfigDto(pageDataDto, getPageConfigDto(pageClass));
			pageDataDto.setTblName(getTbl(pageDataDto.getProductCode()));
			pageDataService.submitPageConfigDto(pageDataDto);
			return RespHelper.setSuccessRespStatus(resp);
		} catch (Exception e) {
			logger.error("页面提交失败pageClass:"+pageClass, e);
			return RespHelper.failRespStatus();
		}
	}
	
}