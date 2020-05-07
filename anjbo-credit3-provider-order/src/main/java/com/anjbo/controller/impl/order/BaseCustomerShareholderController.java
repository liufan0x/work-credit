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
import com.anjbo.bean.order.BaseCustomerShareholderDto;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.order.IBaseCustomerShareholderController;
import com.anjbo.service.order.BaseCustomerShareholderService;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 20:14:45
 * @version 1.0
 */
@RestController
public class BaseCustomerShareholderController extends BaseController implements IBaseCustomerShareholderController{

	@Resource private BaseCustomerShareholderService baseCustomerShareholderService;
	
	@Resource private UserApi userApi;

	/**
	 * 分页查询
	 * @author Generator 
	 */
	@Override
	public RespPageData<BaseCustomerShareholderDto> page(@RequestBody BaseCustomerShareholderDto dto){
		RespPageData<BaseCustomerShareholderDto> resp = new RespPageData<BaseCustomerShareholderDto>();
		try {
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
			resp.setRows(baseCustomerShareholderService.search(dto));
			resp.setTotal(baseCustomerShareholderService.count(dto));
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
	public RespData<BaseCustomerShareholderDto> search(@RequestBody BaseCustomerShareholderDto dto){ 
		RespData<BaseCustomerShareholderDto> resp = new RespData<BaseCustomerShareholderDto>();
		try {
			return RespHelper.setSuccessData(resp, baseCustomerShareholderService.search(dto));
		}catch (Exception e) {
			logger.error("查询异常,参数："+dto.toString(), e);
			return RespHelper.setFailData(resp, new ArrayList<BaseCustomerShareholderDto>(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 查找
	 * @author Generator 
	 */
	@Override
	public RespDataObject<BaseCustomerShareholderDto> find(@RequestBody BaseCustomerShareholderDto dto){ 
		RespDataObject<BaseCustomerShareholderDto> resp = new RespDataObject<BaseCustomerShareholderDto>();
		try {
			return RespHelper.setSuccessDataObject(resp, baseCustomerShareholderService.find(dto));
		}catch (Exception e) {
			logger.error("查找异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new BaseCustomerShareholderDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 新增
	 * @author Generator 
	 */
	@Override
	public RespDataObject<BaseCustomerShareholderDto> add(@RequestBody BaseCustomerShareholderDto dto){ 
		RespDataObject<BaseCustomerShareholderDto> resp = new RespDataObject<BaseCustomerShareholderDto>();
		try {
			dto.setCreateUid(userApi.getUserDto().getUid());
			return RespHelper.setSuccessDataObject(resp, baseCustomerShareholderService.insert(dto));
		}catch (Exception e) {
			logger.error("新增异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new BaseCustomerShareholderDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 编辑
	 * @author Generator 
	 */
	@Override
	public RespStatus edit(@RequestBody BaseCustomerShareholderDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			dto.setUpdateUid(userApi.getUserDto().getUid());
			baseCustomerShareholderService.update(dto);
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
	public RespStatus delete(@RequestBody BaseCustomerShareholderDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			baseCustomerShareholderService.delete(dto);
			return RespHelper.setSuccessRespStatus(resp);
		}catch (Exception e) {
			logger.error("删除异常,参数："+dto.toString(), e);
			return RespHelper.failRespStatus();
		}
	}
		
}