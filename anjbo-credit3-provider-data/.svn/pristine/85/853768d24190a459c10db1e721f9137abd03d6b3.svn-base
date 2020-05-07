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
import com.anjbo.bean.BankDto;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.IBankController;
import com.anjbo.service.BankService;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-05-03 15:08:02
 * @version 1.0
 */
@RestController
public class BankController extends BaseController implements IBankController{

	@Resource private BankService bankService;

	/**
	 * 分页查询
	 * @author Generator 
	 */
	@Override
	public RespPageData<BankDto> page(@RequestBody BankDto dto){
		RespPageData<BankDto> resp = new RespPageData<BankDto>();
		try {
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
			resp.setRows(bankService.search(dto));
			resp.setTotal(bankService.count(dto));
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
	public RespData<BankDto> search(@RequestBody BankDto dto){ 
		RespData<BankDto> resp = new RespData<BankDto>();
		try {
			return RespHelper.setSuccessData(resp, bankService.search(dto));
		}catch (Exception e) {
			logger.error("查询异常,参数："+dto.toString(), e);
			return RespHelper.setFailData(resp, new ArrayList<BankDto>(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 查找
	 * @author Generator 
	 */
	@Override
	public RespDataObject<BankDto> find(@RequestBody BankDto dto){ 
		RespDataObject<BankDto> resp = new RespDataObject<BankDto>();
		try {
			return RespHelper.setSuccessDataObject(resp, bankService.find(dto));
		}catch (Exception e) {
			logger.error("查找异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new BankDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 新增
	 * @author Generator 
	 */
	@Override
	public RespDataObject<BankDto> add(@RequestBody BankDto dto){ 
		RespDataObject<BankDto> resp = new RespDataObject<BankDto>();
		try {
			return RespHelper.setSuccessDataObject(resp, bankService.insert(dto));
		}catch (Exception e) {
			logger.error("新增异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new BankDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 编辑
	 * @author Generator 
	 */
	@Override
	public RespStatus edit(@RequestBody BankDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			bankService.update(dto);
			return RespHelper.setSuccessRespStatus(resp);
		}catch (Exception e) {
			logger.error("新增异常,参数："+dto.toString(), e);
			return RespHelper.failRespStatus();
		}
	}
		
}