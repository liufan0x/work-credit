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
import com.anjbo.bean.LoanPreparationDto;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.ILoanPreparationController;
import com.anjbo.controller.ToolsBaseController;
import com.anjbo.controller.api.UserApi;
import com.anjbo.service.LoanPreparationService;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-06-25 16:16:13
 * @version 1.0
 */
@RestController
public class LoanPreparationController extends ToolsBaseController implements ILoanPreparationController{

	@Resource private LoanPreparationService loanPreparationService;
	
	@Resource private UserApi userApi;

	/**
	 * 分页查询
	 * @author Generator 
	 */
	@Override
	public RespPageData<LoanPreparationDto> page(@RequestBody LoanPreparationDto dto){
		RespPageData<LoanPreparationDto> resp = new RespPageData<LoanPreparationDto>();
		try {
			dto.setCityCode(getCityCodes(userApi.getUserDto(), dto.getCityCode()));
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
			resp.setRows(loanPreparationService.search(dto));
			resp.setTotal(loanPreparationService.count(dto));
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
	public RespData<LoanPreparationDto> search(@RequestBody LoanPreparationDto dto){ 
		RespData<LoanPreparationDto> resp = new RespData<LoanPreparationDto>();
		try {
			return RespHelper.setSuccessData(resp, loanPreparationService.search(dto));
		}catch (Exception e) {
			logger.error("查询异常,参数："+dto.toString(), e);
			return RespHelper.setFailData(resp, new ArrayList<LoanPreparationDto>(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 查找
	 * @author Generator 
	 */
	@Override
	public RespDataObject<LoanPreparationDto> find(@RequestBody LoanPreparationDto dto){ 
		RespDataObject<LoanPreparationDto> resp = new RespDataObject<LoanPreparationDto>();
		try {
			return RespHelper.setSuccessDataObject(resp, loanPreparationService.find(dto));
		}catch (Exception e) {
			logger.error("查找异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new LoanPreparationDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 新增
	 * @author Generator 
	 */
	@Override
	public RespDataObject<LoanPreparationDto> add(@RequestBody LoanPreparationDto dto){ 
		RespDataObject<LoanPreparationDto> resp = new RespDataObject<LoanPreparationDto>();
		try {
			dto.setCreateUid(userApi.getUserDto().getUid());
			return RespHelper.setSuccessDataObject(resp, loanPreparationService.insert(dto));
		}catch (Exception e) {
			logger.error("新增异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new LoanPreparationDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 编辑
	 * @author Generator 
	 */
	@Override
	public RespStatus edit(@RequestBody LoanPreparationDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			dto.setUpdateUid(userApi.getUserDto().getUid());
			loanPreparationService.update(dto);
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
	public RespStatus delete(@RequestBody LoanPreparationDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			loanPreparationService.delete(dto);
			return RespHelper.setSuccessRespStatus(resp);
		}catch (Exception e) {
			logger.error("删除异常,参数："+dto.toString(), e);
			return RespHelper.failRespStatus();
		}
	}
		
}