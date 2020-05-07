/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller.impl.risk;

import java.util.ArrayList;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.anjbo.controller.api.UserApi;
import com.anjbo.controller.BaseController;
import com.anjbo.bean.risk.AuditFirstForeclosureDto;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.risk.IAuditFirstForeclosureController;
import com.anjbo.service.risk.AuditFirstForeclosureService;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 19:44:32
 * @version 1.0
 */
@RestController
public class AuditFirstForeclosureController extends BaseController implements IAuditFirstForeclosureController{

	@Resource private AuditFirstForeclosureService auditFirstForeclosureService;
	
	@Resource private UserApi userApi;

	/**
	 * 分页查询
	 * @author Generator 
	 */
	@Override
	public RespPageData<AuditFirstForeclosureDto> page(@RequestBody AuditFirstForeclosureDto dto){
		RespPageData<AuditFirstForeclosureDto> resp = new RespPageData<AuditFirstForeclosureDto>();
		try {
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
			resp.setRows(auditFirstForeclosureService.search(dto));
			resp.setTotal(auditFirstForeclosureService.count(dto));
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
	public RespData<AuditFirstForeclosureDto> search(@RequestBody AuditFirstForeclosureDto dto){ 
		RespData<AuditFirstForeclosureDto> resp = new RespData<AuditFirstForeclosureDto>();
		try {
			return RespHelper.setSuccessData(resp, auditFirstForeclosureService.search(dto));
		}catch (Exception e) {
			logger.error("查询异常,参数："+dto.toString(), e);
			return RespHelper.setFailData(resp, new ArrayList<AuditFirstForeclosureDto>(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 查找
	 * @author Generator 
	 */
	@Override
	public RespDataObject<AuditFirstForeclosureDto> find(@RequestBody AuditFirstForeclosureDto dto){ 
		RespDataObject<AuditFirstForeclosureDto> resp = new RespDataObject<AuditFirstForeclosureDto>();
		try {
			return RespHelper.setSuccessDataObject(resp, auditFirstForeclosureService.find(dto));
		}catch (Exception e) {
			logger.error("查找异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new AuditFirstForeclosureDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 新增
	 * @author Generator 
	 */
	@Override
	public RespDataObject<AuditFirstForeclosureDto> add(@RequestBody AuditFirstForeclosureDto dto){ 
		RespDataObject<AuditFirstForeclosureDto> resp = new RespDataObject<AuditFirstForeclosureDto>();
		try {
			dto.setCreateUid(userApi.getUserDto().getUid());
			return RespHelper.setSuccessDataObject(resp, auditFirstForeclosureService.insert(dto));
		}catch (Exception e) {
			logger.error("新增异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new AuditFirstForeclosureDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 编辑
	 * @author Generator 
	 */
	@Override
	public RespStatus edit(@RequestBody AuditFirstForeclosureDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			dto.setUpdateUid(userApi.getUserDto().getUid());
			auditFirstForeclosureService.update(dto);
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
	public RespStatus delete(@RequestBody AuditFirstForeclosureDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			auditFirstForeclosureService.delete(dto);
			return RespHelper.setSuccessRespStatus(resp);
		}catch (Exception e) {
			logger.error("删除异常,参数："+dto.toString(), e);
			return RespHelper.failRespStatus();
		}
	}
		
}