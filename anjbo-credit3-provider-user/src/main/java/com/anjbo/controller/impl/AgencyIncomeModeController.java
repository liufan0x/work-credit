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
import com.anjbo.bean.AgencyIncomeModeDto;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.IAgencyIncomeModeController;
import com.anjbo.service.AgencyIncomeModeService;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-05-10 16:54:59
 * @version 1.0
 */
@RestController
public class AgencyIncomeModeController extends BaseController implements IAgencyIncomeModeController{

	@Resource private AgencyIncomeModeService agencyIncomeModeService;

	/**
	 * 分页查询
	 * @author Generator 
	 */
	@Override
	public RespPageData<AgencyIncomeModeDto> page(@RequestBody AgencyIncomeModeDto dto){
		RespPageData<AgencyIncomeModeDto> resp = new RespPageData<AgencyIncomeModeDto>();
		try {
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
			resp.setRows(agencyIncomeModeService.search(dto));
			resp.setTotal(agencyIncomeModeService.count(dto));
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
	public RespData<AgencyIncomeModeDto> search(@RequestBody AgencyIncomeModeDto dto){ 
		RespData<AgencyIncomeModeDto> resp = new RespData<AgencyIncomeModeDto>();
		try {
			return RespHelper.setSuccessData(resp, agencyIncomeModeService.search(dto));
		}catch (Exception e) {
			logger.error("查询异常,参数："+dto.toString(), e);
			return RespHelper.setFailData(resp, new ArrayList<AgencyIncomeModeDto>(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 查找
	 * @author Generator 
	 */
	@Override
	public RespDataObject<AgencyIncomeModeDto> find(@RequestBody AgencyIncomeModeDto dto){ 
		RespDataObject<AgencyIncomeModeDto> resp = new RespDataObject<AgencyIncomeModeDto>();
		try {
			return RespHelper.setSuccessDataObject(resp, agencyIncomeModeService.find(dto));
		}catch (Exception e) {
			logger.error("查找异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new AgencyIncomeModeDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 新增
	 * @author Generator 
	 */
	@Override
	public RespDataObject<AgencyIncomeModeDto> add(@RequestBody AgencyIncomeModeDto dto){ 
		RespDataObject<AgencyIncomeModeDto> resp = new RespDataObject<AgencyIncomeModeDto>();
		try {
			return RespHelper.setSuccessDataObject(resp, agencyIncomeModeService.insert(dto));
		}catch (Exception e) {
			logger.error("新增异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new AgencyIncomeModeDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 编辑
	 * @author Generator 
	 */
	@Override
	public RespStatus edit(@RequestBody AgencyIncomeModeDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			agencyIncomeModeService.update(dto);
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
	public RespStatus delete(@RequestBody AgencyIncomeModeDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			agencyIncomeModeService.delete(dto);
			return RespHelper.setSuccessRespStatus(resp);
		}catch (Exception e) {
			logger.error("删除异常,参数："+dto.toString(), e);
			return RespHelper.failRespStatus();
		}
	}
		
}