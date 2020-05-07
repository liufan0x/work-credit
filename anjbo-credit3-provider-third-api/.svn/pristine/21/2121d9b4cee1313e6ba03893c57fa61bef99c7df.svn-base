/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller.impl.sgtong;

import java.util.ArrayList;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.anjbo.controller.api.UserApi;
import com.anjbo.controller.sgtong.ISgtongContractInformationController;
import com.anjbo.service.sgtong.SgtongContractInformationService;
import com.anjbo.controller.BaseController;
import com.anjbo.common.RespHelper;
import com.anjbo.bean.sgtong.SgtongContractInformationDto;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-11-21 11:21:31
 * @version 1.0
 */
@RestController
public class SgtongContractInformationController extends BaseController implements ISgtongContractInformationController{

	@Resource private SgtongContractInformationService sgtongContractInformationService;
	
	@Resource private UserApi userApi;

	/**
	 * 分页查询
	 * @author Generator 
	 */
	@Override
	public RespPageData<SgtongContractInformationDto> page(@RequestBody SgtongContractInformationDto dto){
		RespPageData<SgtongContractInformationDto> resp = new RespPageData<SgtongContractInformationDto>();
		try {
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
			resp.setRows(sgtongContractInformationService.search(dto));
			resp.setTotal(sgtongContractInformationService.count(dto));
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
	public RespData<SgtongContractInformationDto> search(@RequestBody SgtongContractInformationDto dto){ 
		RespData<SgtongContractInformationDto> resp = new RespData<SgtongContractInformationDto>();
		try {
			return RespHelper.setSuccessData(resp, sgtongContractInformationService.search(dto));
		}catch (Exception e) {
			logger.error("查询异常,参数："+dto.toString(), e);
			return RespHelper.setFailData(resp, new ArrayList<SgtongContractInformationDto>(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 查找
	 * @author Generator 
	 */
	@Override
	public RespDataObject<SgtongContractInformationDto> find(@RequestBody SgtongContractInformationDto dto){ 
		RespDataObject<SgtongContractInformationDto> resp = new RespDataObject<SgtongContractInformationDto>();
		try {
			return RespHelper.setSuccessDataObject(resp, sgtongContractInformationService.find(dto));
		}catch (Exception e) {
			logger.error("查找异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new SgtongContractInformationDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 新增
	 * @author Generator 
	 */
	@Override
	public RespDataObject<SgtongContractInformationDto> add(@RequestBody SgtongContractInformationDto dto){ 
		RespDataObject<SgtongContractInformationDto> resp = new RespDataObject<SgtongContractInformationDto>();
		try {
			dto.setCreateUid(userApi.getUserDto().getUid());
			return RespHelper.setSuccessDataObject(resp, sgtongContractInformationService.insert(dto));
		}catch (Exception e) {
			logger.error("新增异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new SgtongContractInformationDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 编辑
	 * @author Generator 
	 */
	@Override
	public RespStatus edit(@RequestBody SgtongContractInformationDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			dto.setUpdateUid(userApi.getUserDto().getUid());
			sgtongContractInformationService.update(dto);
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
	public RespStatus delete(@RequestBody SgtongContractInformationDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			sgtongContractInformationService.delete(dto);
			return RespHelper.setSuccessRespStatus(resp);
		}catch (Exception e) {
			logger.error("删除异常,参数："+dto.toString(), e);
			return RespHelper.failRespStatus();
		}
	}
		
}