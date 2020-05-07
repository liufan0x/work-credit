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
import com.anjbo.bean.BankSubDto;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.IBankSubController;
import com.anjbo.service.BankSubService;
import com.anjbo.utils.StringUtil;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-05-03 15:08:02
 * @version 1.0
 */
@RestController
public class BankSubController extends BaseController implements IBankSubController{

	@Resource private BankSubService bankSubService;

	/**
	 * 分页查询
	 * @author Generator 
	 */
	@Override
	public RespPageData<BankSubDto> page(@RequestBody BankSubDto dto){
		RespPageData<BankSubDto> resp = new RespPageData<BankSubDto>();
		try {
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
			resp.setRows(bankSubService.search(dto));
			resp.setTotal(bankSubService.count(dto));
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
	public RespData<BankSubDto> searchByPid(@RequestBody BankSubDto dto){ 
		RespData<BankSubDto> resp = new RespData<BankSubDto>();
		try {
			if(dto.getPid() == null) {
				return resp;
			}
			return RespHelper.setSuccessData(resp, bankSubService.search(dto));
		}catch (Exception e) {
			logger.error("查询异常,参数："+dto.toString(), e);
			return RespHelper.setFailData(resp, new ArrayList<BankSubDto>(), RespStatusEnum.FAIL.getMsg());
		}
	}
	
	/**
	 * 查询
	 * @author Generator 
	 */
	@Override
	public RespData<BankSubDto> search(@RequestBody BankSubDto dto){ 
		RespData<BankSubDto> resp = new RespData<BankSubDto>();
		try {
			return RespHelper.setSuccessData(resp, bankSubService.search(dto));
		}catch (Exception e) {
			logger.error("查询异常,参数："+dto.toString(), e);
			return RespHelper.setFailData(resp, new ArrayList<BankSubDto>(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 查找
	 * @author Generator 
	 */
	@Override
	public RespDataObject<BankSubDto> find(@RequestBody BankSubDto dto){ 
		RespDataObject<BankSubDto> resp = new RespDataObject<BankSubDto>();
		try {
			return RespHelper.setSuccessDataObject(resp, bankSubService.find(dto));
		}catch (Exception e) {
			logger.error("查找异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new BankSubDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 新增
	 * @author Generator 
	 */
	@Override
	public RespDataObject<BankSubDto> add(@RequestBody BankSubDto dto){ 
		RespDataObject<BankSubDto> resp = new RespDataObject<BankSubDto>();
		try {
			return RespHelper.setSuccessDataObject(resp, bankSubService.insert(dto));
		}catch (Exception e) {
			logger.error("新增异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new BankSubDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 编辑
	 * @author Generator 
	 */
	@Override
	public RespStatus edit(@RequestBody BankSubDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			bankSubService.update(dto);
			return RespHelper.setSuccessRespStatus(resp);
		}catch (Exception e) {
			logger.error("新增异常,参数："+dto.toString(), e);
			return RespHelper.failRespStatus();
		}
	}
		
}