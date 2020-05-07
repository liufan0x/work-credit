/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller.impl.order;

import java.util.ArrayList;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.anjbo.controller.api.UserApi;
import com.anjbo.controller.BaseController;
import com.anjbo.bean.order.BaseCustomerGuaranteeDto;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.order.IBaseCustomerGuaranteeController;
import com.anjbo.service.order.BaseCustomerGuaranteeService;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 20:14:45
 * @version 1.0
 */
@RestController
public class BaseCustomerGuaranteeController extends BaseController implements IBaseCustomerGuaranteeController{

	@Resource private BaseCustomerGuaranteeService baseCustomerGuaranteeService;
	
	@Resource private UserApi userApi;

	/**
	 * 分页查询
	 * @author Generator 
	 */
	@Override
	public RespPageData<BaseCustomerGuaranteeDto> page(@RequestBody BaseCustomerGuaranteeDto dto){
		RespPageData<BaseCustomerGuaranteeDto> resp = new RespPageData<BaseCustomerGuaranteeDto>();
		try {
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
			resp.setRows(baseCustomerGuaranteeService.search(dto));
			resp.setTotal(baseCustomerGuaranteeService.count(dto));
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
	public RespData<BaseCustomerGuaranteeDto> search(@RequestBody BaseCustomerGuaranteeDto dto){ 
		RespData<BaseCustomerGuaranteeDto> resp = new RespData<BaseCustomerGuaranteeDto>();
		try {
			return RespHelper.setSuccessData(resp, baseCustomerGuaranteeService.search(dto));
		}catch (Exception e) {
			logger.error("查询异常,参数："+dto.toString(), e);
			return RespHelper.setFailData(resp, new ArrayList<BaseCustomerGuaranteeDto>(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 查找
	 * @author Generator 
	 */
	@Override
	public RespDataObject<BaseCustomerGuaranteeDto> find(@RequestBody BaseCustomerGuaranteeDto dto){ 
		RespDataObject<BaseCustomerGuaranteeDto> resp = new RespDataObject<BaseCustomerGuaranteeDto>();
		try {
			return RespHelper.setSuccessDataObject(resp, baseCustomerGuaranteeService.find(dto));
		}catch (Exception e) {
			logger.error("查找异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new BaseCustomerGuaranteeDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 新增
	 * @author Generator 
	 */
	@Override
	public RespDataObject<BaseCustomerGuaranteeDto> add(@RequestBody BaseCustomerGuaranteeDto dto){ 
		RespDataObject<BaseCustomerGuaranteeDto> resp = new RespDataObject<BaseCustomerGuaranteeDto>();
		try {
			dto.setCreateUid(userApi.getUserDto().getUid());
			return RespHelper.setSuccessDataObject(resp, baseCustomerGuaranteeService.insert(dto));
		}catch (Exception e) {
			logger.error("新增异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new BaseCustomerGuaranteeDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 编辑
	 * @author Generator 
	 */
	@Override
	public RespStatus edit(@RequestBody BaseCustomerGuaranteeDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			dto.setUpdateUid(userApi.getUserDto().getUid());
			baseCustomerGuaranteeService.update(dto);
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
	public RespStatus delete(@RequestBody BaseCustomerGuaranteeDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			baseCustomerGuaranteeService.delete(dto);
			return RespHelper.setSuccessRespStatus(resp);
		}catch (Exception e) {
			logger.error("删除异常,参数："+dto.toString(), e);
			return RespHelper.failRespStatus();
		}
	}
		
}