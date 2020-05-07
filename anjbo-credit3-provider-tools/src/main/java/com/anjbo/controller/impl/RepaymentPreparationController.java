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

import com.anjbo.bean.RepaymentPreparationDto;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.IRepaymentPreparationController;
import com.anjbo.controller.ToolsBaseController;
import com.anjbo.controller.api.UserApi;
import com.anjbo.service.RepaymentPreparationService;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-06-25 16:16:15
 * @version 1.0
 */
@RestController
public class RepaymentPreparationController extends ToolsBaseController implements IRepaymentPreparationController{

	@Resource private RepaymentPreparationService repaymentPreparationService;
	
	@Resource private UserApi userApi;

	/**
	 * 分页查询
	 * @author Generator 
	 */
	@Override
	public RespPageData<RepaymentPreparationDto> page(@RequestBody RepaymentPreparationDto dto){
		RespPageData<RepaymentPreparationDto> resp = new RespPageData<RepaymentPreparationDto>();
		try {
			dto.setCityCode(getCityCodes(userApi.getUserDto(), dto.getCityCode()));
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
			resp.setRows(repaymentPreparationService.search(dto));
			resp.setTotal(repaymentPreparationService.count(dto));
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
	public RespData<RepaymentPreparationDto> search(@RequestBody RepaymentPreparationDto dto){ 
		RespData<RepaymentPreparationDto> resp = new RespData<RepaymentPreparationDto>();
		try {
			return RespHelper.setSuccessData(resp, repaymentPreparationService.search(dto));
		}catch (Exception e) {
			logger.error("查询异常,参数："+dto.toString(), e);
			return RespHelper.setFailData(resp, new ArrayList<RepaymentPreparationDto>(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 查找
	 * @author Generator 
	 */
	@Override
	public RespDataObject<RepaymentPreparationDto> find(@RequestBody RepaymentPreparationDto dto){ 
		RespDataObject<RepaymentPreparationDto> resp = new RespDataObject<RepaymentPreparationDto>();
		try {
			return RespHelper.setSuccessDataObject(resp, repaymentPreparationService.find(dto));
		}catch (Exception e) {
			logger.error("查找异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new RepaymentPreparationDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 新增
	 * @author Generator 
	 */
	@Override
	public RespDataObject<RepaymentPreparationDto> add(@RequestBody RepaymentPreparationDto dto){ 
		RespDataObject<RepaymentPreparationDto> resp = new RespDataObject<RepaymentPreparationDto>();
		try {
			dto.setCreateUid(userApi.getUserDto().getUid());
			return RespHelper.setSuccessDataObject(resp, repaymentPreparationService.insert(dto));
		}catch (Exception e) {
			logger.error("新增异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new RepaymentPreparationDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 编辑
	 * @author Generator 
	 */
	@Override
	public RespStatus edit(@RequestBody RepaymentPreparationDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			dto.setUpdateUid(userApi.getUserDto().getUid());
			repaymentPreparationService.update(dto);
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
	public RespStatus delete(@RequestBody RepaymentPreparationDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			repaymentPreparationService.delete(dto);
			return RespHelper.setSuccessRespStatus(resp);
		}catch (Exception e) {
			logger.error("删除异常,参数："+dto.toString(), e);
			return RespHelper.failRespStatus();
		}
	}
		
}