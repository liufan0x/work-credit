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
import com.anjbo.bean.TblOrderBaseHouseLendingDto;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.ITblOrderBaseHouseLendingController;
import com.anjbo.service.TblOrderBaseHouseLendingService;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-11-29 09:37:27
 * @version 1.0
 */
@RestController
public class TblOrderBaseHouseLendingController extends BaseController implements ITblOrderBaseHouseLendingController{

	@Resource private TblOrderBaseHouseLendingService tblOrderBaseHouseLendingService;
	
	@Resource private UserApi userApi;

	/**
	 * 分页查询
	 * @author Generator 
	 */
	@Override
	public RespPageData<TblOrderBaseHouseLendingDto> page(@RequestBody TblOrderBaseHouseLendingDto dto){
		RespPageData<TblOrderBaseHouseLendingDto> resp = new RespPageData<TblOrderBaseHouseLendingDto>();
		try {
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
			resp.setRows(tblOrderBaseHouseLendingService.search(dto));
			resp.setTotal(tblOrderBaseHouseLendingService.count(dto));
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
	public RespData<TblOrderBaseHouseLendingDto> search(@RequestBody TblOrderBaseHouseLendingDto dto){ 
		RespData<TblOrderBaseHouseLendingDto> resp = new RespData<TblOrderBaseHouseLendingDto>();
		try {
			return RespHelper.setSuccessData(resp, tblOrderBaseHouseLendingService.search(dto));
		}catch (Exception e) {
			logger.error("查询异常,参数："+dto.toString(), e);
			return RespHelper.setFailData(resp, new ArrayList<TblOrderBaseHouseLendingDto>(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 查找
	 * @author Generator 
	 */
	@Override
	public RespDataObject<TblOrderBaseHouseLendingDto> find(@RequestBody TblOrderBaseHouseLendingDto dto){ 
		RespDataObject<TblOrderBaseHouseLendingDto> resp = new RespDataObject<TblOrderBaseHouseLendingDto>();
		try {
			return RespHelper.setSuccessDataObject(resp, tblOrderBaseHouseLendingService.find(dto));
		}catch (Exception e) {
			logger.error("查找异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new TblOrderBaseHouseLendingDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 新增
	 * @author Generator 
	 */
	@Override
	public RespDataObject<TblOrderBaseHouseLendingDto> add(@RequestBody TblOrderBaseHouseLendingDto dto){ 
		RespDataObject<TblOrderBaseHouseLendingDto> resp = new RespDataObject<TblOrderBaseHouseLendingDto>();
		try {
			dto.setCreateUid(userApi.getUserDto().getUid());
			return RespHelper.setSuccessDataObject(resp, tblOrderBaseHouseLendingService.insert(dto));
		}catch (Exception e) {
			logger.error("新增异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new TblOrderBaseHouseLendingDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 编辑
	 * @author Generator 
	 */
	@Override
	public RespStatus edit(@RequestBody TblOrderBaseHouseLendingDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			dto.setUpdateUid(userApi.getUserDto().getUid());
			tblOrderBaseHouseLendingService.update(dto);
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
	public RespStatus delete(@RequestBody TblOrderBaseHouseLendingDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			tblOrderBaseHouseLendingService.delete(dto);
			return RespHelper.setSuccessRespStatus(resp);
		}catch (Exception e) {
			logger.error("删除异常,参数："+dto.toString(), e);
			return RespHelper.failRespStatus();
		}
	}
		
}