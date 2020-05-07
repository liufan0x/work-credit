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
import com.anjbo.bean.FundCostDto;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.IFundCostController;
import com.anjbo.service.FundCostService;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-05-10 16:55:00
 * @version 1.0
 */
@RestController
public class FundCostController extends BaseController implements IFundCostController{

	@Resource private FundCostService fundCostService;

	/**
	 * 分页查询
	 * @author Generator 
	 */
	@Override
	public RespPageData<FundCostDto> page(@RequestBody FundCostDto dto){
		RespPageData<FundCostDto> resp = new RespPageData<FundCostDto>();
		try {
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
			resp.setRows(fundCostService.search(dto));
			resp.setTotal(fundCostService.count(dto));
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
	public RespData<FundCostDto> search(@RequestBody FundCostDto dto){ 
		RespData<FundCostDto> resp = new RespData<FundCostDto>();
		try {
			return RespHelper.setSuccessData(resp, fundCostService.search(dto));
		}catch (Exception e) {
			logger.error("查询异常,参数："+dto.toString(), e);
			return RespHelper.setFailData(resp, new ArrayList<FundCostDto>(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 查找
	 * @author Generator 
	 */
	@Override
	public RespDataObject<FundCostDto> find(@RequestBody FundCostDto dto){ 
		RespDataObject<FundCostDto> resp = new RespDataObject<FundCostDto>();
		try {
			return RespHelper.setSuccessDataObject(resp, fundCostService.find(dto));
		}catch (Exception e) {
			logger.error("查找异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new FundCostDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 新增
	 * @author Generator 
	 */
	@Override
	public RespDataObject<FundCostDto> add(@RequestBody FundCostDto dto){ 
		RespDataObject<FundCostDto> resp = new RespDataObject<FundCostDto>();
		try {
			return RespHelper.setSuccessDataObject(resp, fundCostService.insert(dto));
		}catch (Exception e) {
			logger.error("新增异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new FundCostDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 编辑
	 * @author Generator 
	 */
	@Override
	public RespStatus edit(@RequestBody FundCostDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			fundCostService.update(dto);
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
	public RespStatus delete(@RequestBody FundCostDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			fundCostService.delete(dto);
			return RespHelper.setSuccessRespStatus(resp);
		}catch (Exception e) {
			logger.error("删除异常,参数："+dto.toString(), e);
			return RespHelper.failRespStatus();
		}
	}
		
}