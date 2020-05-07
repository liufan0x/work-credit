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
import com.anjbo.bean.risk.AuditFirstPaymentDto;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.risk.IAuditFirstPaymentController;
import com.anjbo.service.risk.AuditFirstPaymentService;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 19:44:32
 * @version 1.0
 */
@RestController
public class AuditFirstPaymentController extends BaseController implements IAuditFirstPaymentController{

	@Resource private AuditFirstPaymentService auditFirstPaymentService;
	
	@Resource private UserApi userApi;

	/**
	 * 分页查询
	 * @author Generator 
	 */
	@Override
	public RespPageData<AuditFirstPaymentDto> page(@RequestBody AuditFirstPaymentDto dto){
		RespPageData<AuditFirstPaymentDto> resp = new RespPageData<AuditFirstPaymentDto>();
		try {
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
			resp.setRows(auditFirstPaymentService.search(dto));
			resp.setTotal(auditFirstPaymentService.count(dto));
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
	public RespData<AuditFirstPaymentDto> search(@RequestBody AuditFirstPaymentDto dto){ 
		RespData<AuditFirstPaymentDto> resp = new RespData<AuditFirstPaymentDto>();
		try {
			return RespHelper.setSuccessData(resp, auditFirstPaymentService.search(dto));
		}catch (Exception e) {
			logger.error("查询异常,参数："+dto.toString(), e);
			return RespHelper.setFailData(resp, new ArrayList<AuditFirstPaymentDto>(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 查找
	 * @author Generator 
	 */
	@Override
	public RespDataObject<AuditFirstPaymentDto> find(@RequestBody AuditFirstPaymentDto dto){ 
		RespDataObject<AuditFirstPaymentDto> resp = new RespDataObject<AuditFirstPaymentDto>();
		try {
			return RespHelper.setSuccessDataObject(resp, auditFirstPaymentService.find(dto));
		}catch (Exception e) {
			logger.error("查找异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new AuditFirstPaymentDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 新增
	 * @author Generator 
	 */
	@Override
	public RespDataObject<AuditFirstPaymentDto> add(@RequestBody AuditFirstPaymentDto dto){ 
		RespDataObject<AuditFirstPaymentDto> resp = new RespDataObject<AuditFirstPaymentDto>();
		try {
			dto.setCreateUid(userApi.getUserDto().getUid());
			return RespHelper.setSuccessDataObject(resp, auditFirstPaymentService.insert(dto));
		}catch (Exception e) {
			logger.error("新增异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new AuditFirstPaymentDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 编辑
	 * @author Generator 
	 */
	@Override
	public RespStatus edit(@RequestBody AuditFirstPaymentDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			dto.setUpdateUid(userApi.getUserDto().getUid());
			auditFirstPaymentService.update(dto);
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
	public RespStatus delete(@RequestBody AuditFirstPaymentDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			auditFirstPaymentService.delete(dto);
			return RespHelper.setSuccessRespStatus(resp);
		}catch (Exception e) {
			logger.error("删除异常,参数："+dto.toString(), e);
			return RespHelper.failRespStatus();
		}
	}
		
}