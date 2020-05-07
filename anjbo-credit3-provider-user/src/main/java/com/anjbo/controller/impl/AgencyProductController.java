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
import com.anjbo.controller.BaseController;
import com.anjbo.bean.AgencyProductDto;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.IAgencyProductController;
import com.anjbo.service.AgencyProductService;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-05-10 16:54:59
 * @version 1.0
 */
@RestController
public class AgencyProductController extends BaseController implements IAgencyProductController{

	@Resource private AgencyProductService agencyProductService;

	/**
	 * 分页查询
	 * @author Generator 
	 */
	@Override
	public RespPageData<AgencyProductDto> page(@RequestBody AgencyProductDto dto){
		RespPageData<AgencyProductDto> resp = new RespPageData<AgencyProductDto>();
		try {
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
			resp.setRows(agencyProductService.search(dto));
			resp.setTotal(agencyProductService.count(dto));
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
	public RespData<AgencyProductDto> search(@RequestBody AgencyProductDto dto){ 
		RespData<AgencyProductDto> resp = new RespData<AgencyProductDto>();
		try {
			return RespHelper.setSuccessData(resp, agencyProductService.search(dto));
		}catch (Exception e) {
			logger.error("查询异常,参数："+dto.toString(), e);
			return RespHelper.setFailData(resp, new ArrayList<AgencyProductDto>(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 查找
	 * @author Generator 
	 */
	@Override
	public RespDataObject<AgencyProductDto> find(@RequestBody AgencyProductDto dto){ 
		RespDataObject<AgencyProductDto> resp = new RespDataObject<AgencyProductDto>();
		try {
			return RespHelper.setSuccessDataObject(resp, agencyProductService.find(dto));
		}catch (Exception e) {
			logger.error("查找异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new AgencyProductDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 新增
	 * @author Generator 
	 */
	@Override
	public RespDataObject<AgencyProductDto> add(@RequestBody AgencyProductDto dto){ 
		RespDataObject<AgencyProductDto> resp = new RespDataObject<AgencyProductDto>();
		try {
			return RespHelper.setSuccessDataObject(resp, agencyProductService.insert(dto));
		}catch (Exception e) {
			logger.error("新增异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new AgencyProductDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 编辑
	 * @author Generator 
	 */
	@Override
	public RespStatus edit(@RequestBody AgencyProductDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			agencyProductService.update(dto);
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
	public RespStatus delete(@RequestBody AgencyProductDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			agencyProductService.delete(dto);
			return RespHelper.setSuccessRespStatus(resp);
		}catch (Exception e) {
			logger.error("删除异常,参数："+dto.toString(), e);
			return RespHelper.failRespStatus();
		}
	}
		
}