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
import com.anjbo.bean.ModelConfigDto;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.IModelConfigController;
import com.anjbo.service.ModelConfigService;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-06-14 20:42:47
 * @version 1.0
 */
@RestController
public class ModelConfigController extends BaseController implements IModelConfigController{

	@Resource private ModelConfigService modelConfigService;
	
	@Resource private UserApi userApi;

	/**
	 * 分页查询
	 * @author Generator 
	 */
	@Override
	public RespPageData<ModelConfigDto> page(@RequestBody ModelConfigDto dto){
		RespPageData<ModelConfigDto> resp = new RespPageData<ModelConfigDto>();
		try {
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
			resp.setRows(modelConfigService.search(dto));
			resp.setTotal(modelConfigService.count(dto));
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
	public RespData<ModelConfigDto> search(@RequestBody ModelConfigDto dto){ 
		RespData<ModelConfigDto> resp = new RespData<ModelConfigDto>();
		try {
			return RespHelper.setSuccessData(resp, modelConfigService.search(dto));
		}catch (Exception e) {
			logger.error("查询异常,参数："+dto.toString(), e);
			return RespHelper.setFailData(resp, new ArrayList<ModelConfigDto>(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 查找
	 * @author Generator 
	 */
	@Override
	public RespDataObject<ModelConfigDto> find(@RequestBody ModelConfigDto dto){ 
		RespDataObject<ModelConfigDto> resp = new RespDataObject<ModelConfigDto>();
		try {
			return RespHelper.setSuccessDataObject(resp, modelConfigService.find(dto));
		}catch (Exception e) {
			logger.error("查找异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new ModelConfigDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 新增
	 * @author Generator 
	 */
	@Override
	public RespDataObject<ModelConfigDto> add(@RequestBody ModelConfigDto dto){ 
		RespDataObject<ModelConfigDto> resp = new RespDataObject<ModelConfigDto>();
		try {
			dto.setCreateUid(userApi.getUserDto().getUid());
			return RespHelper.setSuccessDataObject(resp, modelConfigService.insert(dto));
		}catch (Exception e) {
			logger.error("新增异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new ModelConfigDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 编辑
	 * @author Generator 
	 */
	@Override
	public RespStatus edit(@RequestBody ModelConfigDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			dto.setUpdateUid(userApi.getUserDto().getUid());
			modelConfigService.update(dto);
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
	public RespStatus delete(@RequestBody ModelConfigDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			modelConfigService.delete(dto);
			return RespHelper.setSuccessRespStatus(resp);
		}catch (Exception e) {
			logger.error("删除异常,参数："+dto.toString(), e);
			return RespHelper.failRespStatus();
		}
	}
	
	/**
	 * 全部禁用
	 * @author Generator 
	 */
	@Override
	public RespStatus updateByFundId(@RequestBody ModelConfigDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			dto.setUpdateUid(userApi.getUserDto().getUid());
			modelConfigService.updateByFundId(dto);
			return RespHelper.setSuccessRespStatus(resp);
		}catch (Exception e) {
			logger.error("全部禁用异常,参数："+dto.toString(), e);
			return RespHelper.failRespStatus();
		}
	}
		
}