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
import com.anjbo.bean.TblFinanceAfterloanFirstInterestDto;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.ITblFinanceAfterloanFirstInterestController;
import com.anjbo.service.TblFinanceAfterloanFirstInterestService;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-12-04 15:49:30
 * @version 1.0
 */
@RestController
public class TblFinanceAfterloanFirstInterestController extends BaseController implements ITblFinanceAfterloanFirstInterestController{

	@Resource private TblFinanceAfterloanFirstInterestService tblFinanceAfterloanFirstInterestService;
	
	@Resource private UserApi userApi;

	/**
	 * 分页查询
	 * @author Generator 
	 */
	@Override
	public RespPageData<TblFinanceAfterloanFirstInterestDto> page(@RequestBody TblFinanceAfterloanFirstInterestDto dto){
		RespPageData<TblFinanceAfterloanFirstInterestDto> resp = new RespPageData<TblFinanceAfterloanFirstInterestDto>();
		try {
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
			resp.setRows(tblFinanceAfterloanFirstInterestService.search(dto));
			resp.setTotal(tblFinanceAfterloanFirstInterestService.count(dto));
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
	public RespData<TblFinanceAfterloanFirstInterestDto> search(@RequestBody TblFinanceAfterloanFirstInterestDto dto){ 
		RespData<TblFinanceAfterloanFirstInterestDto> resp = new RespData<TblFinanceAfterloanFirstInterestDto>();
		try {
			return RespHelper.setSuccessData(resp, tblFinanceAfterloanFirstInterestService.search(dto));
		}catch (Exception e) {
			logger.error("查询异常,参数："+dto.toString(), e);
			return RespHelper.setFailData(resp, new ArrayList<TblFinanceAfterloanFirstInterestDto>(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 查找
	 * @author Generator 
	 */
	@Override
	public RespDataObject<TblFinanceAfterloanFirstInterestDto> find(@RequestBody TblFinanceAfterloanFirstInterestDto dto){ 
		RespDataObject<TblFinanceAfterloanFirstInterestDto> resp = new RespDataObject<TblFinanceAfterloanFirstInterestDto>();
		try {
			return RespHelper.setSuccessDataObject(resp, tblFinanceAfterloanFirstInterestService.find(dto));
		}catch (Exception e) {
			logger.error("查找异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new TblFinanceAfterloanFirstInterestDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 新增
	 * @author Generator 
	 */
	@Override
	public RespDataObject<TblFinanceAfterloanFirstInterestDto> add(@RequestBody TblFinanceAfterloanFirstInterestDto dto){ 
		RespDataObject<TblFinanceAfterloanFirstInterestDto> resp = new RespDataObject<TblFinanceAfterloanFirstInterestDto>();
		try {
			dto.setCreateUid(userApi.getUserDto().getUid());
			return RespHelper.setSuccessDataObject(resp, tblFinanceAfterloanFirstInterestService.insert(dto));
		}catch (Exception e) {
			logger.error("新增异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new TblFinanceAfterloanFirstInterestDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 编辑
	 * @author Generator 
	 */
	@Override
	public RespStatus edit(@RequestBody TblFinanceAfterloanFirstInterestDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			dto.setUpdateUid(userApi.getUserDto().getUid());
			tblFinanceAfterloanFirstInterestService.update(dto);
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
	public RespStatus delete(@RequestBody TblFinanceAfterloanFirstInterestDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			tblFinanceAfterloanFirstInterestService.delete(dto);
			return RespHelper.setSuccessRespStatus(resp);
		}catch (Exception e) {
			logger.error("删除异常,参数："+dto.toString(), e);
			return RespHelper.failRespStatus();
		}
	}
		
}