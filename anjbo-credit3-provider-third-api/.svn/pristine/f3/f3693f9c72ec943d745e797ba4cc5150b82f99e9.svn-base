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
import com.anjbo.controller.sgtong.ISgtongMortgagorInformationController;
import com.anjbo.service.sgtong.SgtongMortgagorInformationService;
import com.anjbo.controller.BaseController;
import com.anjbo.common.RespHelper;
import com.anjbo.bean.sgtong.SgtongMortgagorInformationDto;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-11-21 11:21:32
 * @version 1.0
 */
@RestController
public class SgtongMortgagorInformationController extends BaseController implements ISgtongMortgagorInformationController{

	@Resource private SgtongMortgagorInformationService sgtongMortgagorInformationService;
	
	@Resource private UserApi userApi;

	/**
	 * 分页查询
	 * @author Generator 
	 */
	@Override
	public RespPageData<SgtongMortgagorInformationDto> page(@RequestBody SgtongMortgagorInformationDto dto){
		RespPageData<SgtongMortgagorInformationDto> resp = new RespPageData<SgtongMortgagorInformationDto>();
		try {
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
			resp.setRows(sgtongMortgagorInformationService.search(dto));
			resp.setTotal(sgtongMortgagorInformationService.count(dto));
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
	public RespData<SgtongMortgagorInformationDto> search(@RequestBody SgtongMortgagorInformationDto dto){ 
		RespData<SgtongMortgagorInformationDto> resp = new RespData<SgtongMortgagorInformationDto>();
		try {
			return RespHelper.setSuccessData(resp, sgtongMortgagorInformationService.search(dto));
		}catch (Exception e) {
			logger.error("查询异常,参数："+dto.toString(), e);
			return RespHelper.setFailData(resp, new ArrayList<SgtongMortgagorInformationDto>(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 查找
	 * @author Generator 
	 */
	@Override
	public RespDataObject<SgtongMortgagorInformationDto> find(@RequestBody SgtongMortgagorInformationDto dto){ 
		RespDataObject<SgtongMortgagorInformationDto> resp = new RespDataObject<SgtongMortgagorInformationDto>();
		try {
			return RespHelper.setSuccessDataObject(resp, sgtongMortgagorInformationService.find(dto));
		}catch (Exception e) {
			logger.error("查找异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new SgtongMortgagorInformationDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 新增
	 * @author Generator 
	 */
	@Override
	public RespDataObject<SgtongMortgagorInformationDto> add(@RequestBody SgtongMortgagorInformationDto dto){ 
		RespDataObject<SgtongMortgagorInformationDto> resp = new RespDataObject<SgtongMortgagorInformationDto>();
		try {
			dto.setCreateUid(userApi.getUserDto().getUid());
			return RespHelper.setSuccessDataObject(resp, sgtongMortgagorInformationService.insert(dto));
		}catch (Exception e) {
			logger.error("新增异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new SgtongMortgagorInformationDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 编辑
	 * @author Generator 
	 */
	@Override
	public RespStatus edit(@RequestBody SgtongMortgagorInformationDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			dto.setUpdateUid(userApi.getUserDto().getUid());
			sgtongMortgagorInformationService.update(dto);
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
	public RespStatus delete(@RequestBody SgtongMortgagorInformationDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			sgtongMortgagorInformationService.delete(dto);
			return RespHelper.setSuccessRespStatus(resp);
		}catch (Exception e) {
			logger.error("删除异常,参数："+dto.toString(), e);
			return RespHelper.failRespStatus();
		}
	}
		
}