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
import com.anjbo.bean.MonitorArchiveDto;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.IMonitorArchiveController;
import com.anjbo.service.MonitorArchiveService;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-06-14 20:40:22
 * @version 1.0
 */
@RestController
public class MonitorArchiveController extends BaseController implements IMonitorArchiveController{

	@Resource private MonitorArchiveService monitorArchiveService;
	
	@Resource private UserApi userApi;

	/**
	 * 分页查询
	 * @author Generator 
	 */
	@Override
	public RespPageData<MonitorArchiveDto> page(@RequestBody MonitorArchiveDto dto){
		RespPageData<MonitorArchiveDto> resp = new RespPageData<MonitorArchiveDto>();
		try {
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
			resp.setRows(monitorArchiveService.search(dto));
			resp.setTotal(monitorArchiveService.count(dto));
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
	public RespData<MonitorArchiveDto> search(@RequestBody MonitorArchiveDto dto){ 
		RespData<MonitorArchiveDto> resp = new RespData<MonitorArchiveDto>();
		try {
			return RespHelper.setSuccessData(resp, monitorArchiveService.search(dto));
		}catch (Exception e) {
			logger.error("查询异常,参数："+dto.toString(), e);
			return RespHelper.setFailData(resp, new ArrayList<MonitorArchiveDto>(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 查找
	 * @author Generator 
	 */
	@Override
	public RespDataObject<MonitorArchiveDto> find(@RequestBody MonitorArchiveDto dto){ 
		RespDataObject<MonitorArchiveDto> resp = new RespDataObject<MonitorArchiveDto>();
		try {
			return RespHelper.setSuccessDataObject(resp, monitorArchiveService.find(dto));
		}catch (Exception e) {
			logger.error("查找异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new MonitorArchiveDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 新增
	 * @author Generator 
	 */
	@Override
	public RespDataObject<MonitorArchiveDto> add(@RequestBody MonitorArchiveDto dto){ 
		RespDataObject<MonitorArchiveDto> resp = new RespDataObject<MonitorArchiveDto>();
		try {
			dto.setCreateUid(userApi.getUserDto().getUid());
			return RespHelper.setSuccessDataObject(resp, monitorArchiveService.insert(dto));
		}catch (Exception e) {
			logger.error("新增异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new MonitorArchiveDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 编辑
	 * @author Generator 
	 */
	@Override
	public RespStatus edit(@RequestBody MonitorArchiveDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			dto.setUpdateUid(userApi.getUserDto().getUid());
			monitorArchiveService.update(dto);
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
	public RespStatus delete(@RequestBody MonitorArchiveDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			monitorArchiveService.delete(dto);
			return RespHelper.setSuccessRespStatus(resp);
		}catch (Exception e) {
			logger.error("删除异常,参数："+dto.toString(), e);
			return RespHelper.failRespStatus();
		}
	}
		
}