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
import com.anjbo.bean.FundCostDiscountDto;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.IFundCostDiscountController;
import com.anjbo.service.FundCostDiscountService;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-05-10 16:55:00
 * @version 1.0
 */
@RestController
public class FundCostDiscountController extends BaseController implements IFundCostDiscountController{

	@Resource private FundCostDiscountService fundCostDiscountService;

	/**
	 * 分页查询
	 * @author Generator 
	 */
	@Override
	public RespPageData<FundCostDiscountDto> page(@RequestBody FundCostDiscountDto dto){
		RespPageData<FundCostDiscountDto> resp = new RespPageData<FundCostDiscountDto>();
		try {
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
			resp.setRows(fundCostDiscountService.search(dto));
			resp.setTotal(fundCostDiscountService.count(dto));
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
	public RespData<FundCostDiscountDto> search(@RequestBody FundCostDiscountDto dto){ 
		RespData<FundCostDiscountDto> resp = new RespData<FundCostDiscountDto>();
		try {
			return RespHelper.setSuccessData(resp, fundCostDiscountService.search(dto));
		}catch (Exception e) {
			logger.error("查询异常,参数："+dto.toString(), e);
			return RespHelper.setFailData(resp, new ArrayList<FundCostDiscountDto>(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 查找
	 * @author Generator 
	 */
	@Override
	public RespDataObject<FundCostDiscountDto> find(@RequestBody FundCostDiscountDto dto){ 
		RespDataObject<FundCostDiscountDto> resp = new RespDataObject<FundCostDiscountDto>();
		try {
			return RespHelper.setSuccessDataObject(resp, fundCostDiscountService.find(dto));
		}catch (Exception e) {
			logger.error("查找异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new FundCostDiscountDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 新增
	 * @author Generator 
	 */
	@Override
	public RespDataObject<FundCostDiscountDto> add(@RequestBody FundCostDiscountDto dto){ 
		RespDataObject<FundCostDiscountDto> resp = new RespDataObject<FundCostDiscountDto>();
		try {
			return RespHelper.setSuccessDataObject(resp, fundCostDiscountService.insert(dto));
		}catch (Exception e) {
			logger.error("新增异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new FundCostDiscountDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 编辑
	 * @author Generator 
	 */
	@Override
	public RespStatus edit(@RequestBody FundCostDiscountDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			fundCostDiscountService.update(dto);
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
	public RespStatus delete(@RequestBody FundCostDiscountDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			fundCostDiscountService.delete(dto);
			return RespHelper.setSuccessRespStatus(resp);
		}catch (Exception e) {
			logger.error("删除异常,参数："+dto.toString(), e);
			return RespHelper.failRespStatus();
		}
	}
		
}