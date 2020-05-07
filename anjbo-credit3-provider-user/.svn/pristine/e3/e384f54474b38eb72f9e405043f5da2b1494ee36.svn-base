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
import com.anjbo.bean.FundAuthDto;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.IFundAuthController;
import com.anjbo.service.FundAuthService;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-05-10 16:55:00
 * @version 1.0
 */
@RestController
public class FundAuthController extends BaseController implements IFundAuthController{

	@Resource private FundAuthService fundAuthService;

	/**
	 * 分页查询
	 * @author Generator 
	 */
	@Override
	public RespPageData<FundAuthDto> page(@RequestBody FundAuthDto dto){
		RespPageData<FundAuthDto> resp = new RespPageData<FundAuthDto>();
		try {
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
			resp.setRows(fundAuthService.search(dto));
			resp.setTotal(fundAuthService.count(dto));
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
	public RespData<FundAuthDto> search(@RequestBody FundAuthDto dto){ 
		RespData<FundAuthDto> resp = new RespData<FundAuthDto>();
		try {
			return RespHelper.setSuccessData(resp, fundAuthService.search(dto));
		}catch (Exception e) {
			logger.error("查询异常,参数："+dto.toString(), e);
			return RespHelper.setFailData(resp, new ArrayList<FundAuthDto>(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 查找
	 * @author Generator 
	 */
	@Override
	public RespDataObject<FundAuthDto> find(@RequestBody FundAuthDto dto){ 
		RespDataObject<FundAuthDto> resp = new RespDataObject<FundAuthDto>();
		try {
			return RespHelper.setSuccessDataObject(resp, fundAuthService.find(dto));
		}catch (Exception e) {
			logger.error("查找异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new FundAuthDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 新增
	 * @author Generator 
	 */
	@Override
	public RespDataObject<FundAuthDto> add(@RequestBody FundAuthDto dto){ 
		RespDataObject<FundAuthDto> resp = new RespDataObject<FundAuthDto>();
		try {
			return RespHelper.setSuccessDataObject(resp, fundAuthService.insert(dto));
		}catch (Exception e) {
			logger.error("新增异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new FundAuthDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 编辑
	 * @author Generator 
	 */
	@Override
	public RespStatus edit(@RequestBody FundAuthDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			fundAuthService.update(dto);
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
	public RespStatus delete(@RequestBody FundAuthDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			fundAuthService.delete(dto);
			return RespHelper.setSuccessRespStatus(resp);
		}catch (Exception e) {
			logger.error("删除异常,参数："+dto.toString(), e);
			return RespHelper.failRespStatus();
		}
	}
		
}