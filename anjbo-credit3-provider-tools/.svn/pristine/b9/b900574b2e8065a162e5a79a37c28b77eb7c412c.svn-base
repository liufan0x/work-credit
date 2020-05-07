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
import com.anjbo.bean.TblFinanceAfterloanListDto;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.ITblFinanceAfterloanListController;
import com.anjbo.service.TblFinanceAfterloanListService;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-12-04 15:49:31
 * @version 1.0
 */
@RestController
public class TblFinanceAfterloanListController extends BaseController implements ITblFinanceAfterloanListController{

	@Resource private TblFinanceAfterloanListService tblFinanceAfterloanListService;
	
	@Resource private UserApi userApi;

	/**
	 * 分页查询
	 * @author Generator 
	 */
	@Override
	public RespPageData<TblFinanceAfterloanListDto> page(@RequestBody TblFinanceAfterloanListDto dto){
		RespPageData<TblFinanceAfterloanListDto> resp = new RespPageData<TblFinanceAfterloanListDto>();
		try {
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
			resp.setRows(tblFinanceAfterloanListService.search(dto));
			resp.setTotal(tblFinanceAfterloanListService.count(dto));
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
	public RespData<TblFinanceAfterloanListDto> search(@RequestBody TblFinanceAfterloanListDto dto){ 
		RespData<TblFinanceAfterloanListDto> resp = new RespData<TblFinanceAfterloanListDto>();
		try {
			return RespHelper.setSuccessData(resp, tblFinanceAfterloanListService.search(dto));
		}catch (Exception e) {
			logger.error("查询异常,参数："+dto.toString(), e);
			return RespHelper.setFailData(resp, new ArrayList<TblFinanceAfterloanListDto>(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 查找
	 * @author Generator 
	 */
	@Override
	public RespDataObject<TblFinanceAfterloanListDto> find(@RequestBody TblFinanceAfterloanListDto dto){ 
		RespDataObject<TblFinanceAfterloanListDto> resp = new RespDataObject<TblFinanceAfterloanListDto>();
		try {
			return RespHelper.setSuccessDataObject(resp, tblFinanceAfterloanListService.find(dto));
		}catch (Exception e) {
			logger.error("查找异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new TblFinanceAfterloanListDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 新增
	 * @author Generator 
	 */
	@Override
	public RespDataObject<TblFinanceAfterloanListDto> add(@RequestBody TblFinanceAfterloanListDto dto){ 
		RespDataObject<TblFinanceAfterloanListDto> resp = new RespDataObject<TblFinanceAfterloanListDto>();
		try {
			dto.setCreateUid(userApi.getUserDto().getUid());
			return RespHelper.setSuccessDataObject(resp, tblFinanceAfterloanListService.insert(dto));
		}catch (Exception e) {
			logger.error("新增异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new TblFinanceAfterloanListDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 编辑
	 * @author Generator 
	 */
	@Override
	public RespStatus edit(@RequestBody TblFinanceAfterloanListDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			dto.setUpdateUid(userApi.getUserDto().getUid());
			tblFinanceAfterloanListService.update(dto);
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
	public RespStatus delete(@RequestBody TblFinanceAfterloanListDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			tblFinanceAfterloanListService.delete(dto);
			return RespHelper.setSuccessRespStatus(resp);
		}catch (Exception e) {
			logger.error("删除异常,参数："+dto.toString(), e);
			return RespHelper.failRespStatus();
		}
	}
		
}