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
import com.anjbo.controller.BaseController;
import com.anjbo.bean.TblFinanceAfterloanEqualInterestDto;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.ITblFinanceAfterloanEqualInterestController;
import com.anjbo.service.TblFinanceAfterloanEqualInterestService;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-12-04 15:49:29
 * @version 1.0
 */
@RestController
public class TblFinanceAfterloanEqualInterestController extends BaseController implements ITblFinanceAfterloanEqualInterestController{

	@Resource private TblFinanceAfterloanEqualInterestService tblFinanceAfterloanEqualInterestService;
	
	@Resource private UserApi userApi;

	/**
	 * 分页查询
	 * @author Generator 
	 */
	@Override
	public RespPageData<TblFinanceAfterloanEqualInterestDto> page(@RequestBody TblFinanceAfterloanEqualInterestDto dto){
		RespPageData<TblFinanceAfterloanEqualInterestDto> resp = new RespPageData<TblFinanceAfterloanEqualInterestDto>();
		try {
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
			resp.setRows(tblFinanceAfterloanEqualInterestService.search(dto));
			resp.setTotal(tblFinanceAfterloanEqualInterestService.count(dto));
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
	public RespData<TblFinanceAfterloanEqualInterestDto> search(@RequestBody TblFinanceAfterloanEqualInterestDto dto){ 
		RespData<TblFinanceAfterloanEqualInterestDto> resp = new RespData<TblFinanceAfterloanEqualInterestDto>();
		try {
			return RespHelper.setSuccessData(resp, tblFinanceAfterloanEqualInterestService.search(dto));
		}catch (Exception e) {
			logger.error("查询异常,参数："+dto.toString(), e);
			return RespHelper.setFailData(resp, new ArrayList<TblFinanceAfterloanEqualInterestDto>(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 查找
	 * @author Generator 
	 */
	@Override
	public RespDataObject<TblFinanceAfterloanEqualInterestDto> find(@RequestBody TblFinanceAfterloanEqualInterestDto dto){ 
		RespDataObject<TblFinanceAfterloanEqualInterestDto> resp = new RespDataObject<TblFinanceAfterloanEqualInterestDto>();
		try {
			return RespHelper.setSuccessDataObject(resp, tblFinanceAfterloanEqualInterestService.find(dto));
		}catch (Exception e) {
			logger.error("查找异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new TblFinanceAfterloanEqualInterestDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 新增
	 * @author Generator 
	 */
	@Override
	public RespDataObject<TblFinanceAfterloanEqualInterestDto> add(@RequestBody TblFinanceAfterloanEqualInterestDto dto){ 
		RespDataObject<TblFinanceAfterloanEqualInterestDto> resp = new RespDataObject<TblFinanceAfterloanEqualInterestDto>();
		try {
			dto.setCreateUid(userApi.getUserDto().getUid());
			return RespHelper.setSuccessDataObject(resp, tblFinanceAfterloanEqualInterestService.insert(dto));
		}catch (Exception e) {
			logger.error("新增异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new TblFinanceAfterloanEqualInterestDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 编辑
	 * @author Generator 
	 */
	@Override
	public RespStatus edit(@RequestBody TblFinanceAfterloanEqualInterestDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			dto.setUpdateUid(userApi.getUserDto().getUid());
			tblFinanceAfterloanEqualInterestService.update(dto);
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
	public RespStatus delete(@RequestBody TblFinanceAfterloanEqualInterestDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			tblFinanceAfterloanEqualInterestService.delete(dto);
			return RespHelper.setSuccessRespStatus(resp);
		}catch (Exception e) {
			logger.error("删除异常,参数："+dto.toString(), e);
			return RespHelper.failRespStatus();
		}
	}
		
}