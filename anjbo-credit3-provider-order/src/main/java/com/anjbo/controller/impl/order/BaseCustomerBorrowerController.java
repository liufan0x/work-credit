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
import com.anjbo.bean.order.BaseCustomerBorrowerDto;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.order.IBaseCustomerBorrowerController;
import com.anjbo.service.order.BaseCustomerBorrowerService;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 20:14:45
 * @version 1.0
 */
@RestController
public class BaseCustomerBorrowerController extends BaseController implements IBaseCustomerBorrowerController{

	@Resource private BaseCustomerBorrowerService baseCustomerBorrowerService;
	
	@Resource private UserApi userApi;

	/**
	 * 分页查询
	 * @author Generator 
	 */
	@Override
	public RespPageData<BaseCustomerBorrowerDto> page(@RequestBody BaseCustomerBorrowerDto dto){
		RespPageData<BaseCustomerBorrowerDto> resp = new RespPageData<BaseCustomerBorrowerDto>();
		try {
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
			resp.setRows(baseCustomerBorrowerService.search(dto));
			resp.setTotal(baseCustomerBorrowerService.count(dto));
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
	public RespData<BaseCustomerBorrowerDto> search(@RequestBody BaseCustomerBorrowerDto dto){ 
		RespData<BaseCustomerBorrowerDto> resp = new RespData<BaseCustomerBorrowerDto>();
		try {
			return RespHelper.setSuccessData(resp, baseCustomerBorrowerService.search(dto));
		}catch (Exception e) {
			logger.error("查询异常,参数："+dto.toString(), e);
			return RespHelper.setFailData(resp, new ArrayList<BaseCustomerBorrowerDto>(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 查找
	 * @author Generator 
	 */
	@Override
	public RespDataObject<BaseCustomerBorrowerDto> find(@RequestBody BaseCustomerBorrowerDto dto){ 
		RespDataObject<BaseCustomerBorrowerDto> resp = new RespDataObject<BaseCustomerBorrowerDto>();
		try {
			return RespHelper.setSuccessDataObject(resp, baseCustomerBorrowerService.find(dto));
		}catch (Exception e) {
			logger.error("查找异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new BaseCustomerBorrowerDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 新增
	 * @author Generator 
	 */
	@Override
	public RespDataObject<BaseCustomerBorrowerDto> add(@RequestBody BaseCustomerBorrowerDto dto){ 
		RespDataObject<BaseCustomerBorrowerDto> resp = new RespDataObject<BaseCustomerBorrowerDto>();
		try {
			dto.setCreateUid(userApi.getUserDto().getUid());
			return RespHelper.setSuccessDataObject(resp, baseCustomerBorrowerService.insert(dto));
		}catch (Exception e) {
			logger.error("新增异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new BaseCustomerBorrowerDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 编辑
	 * @author Generator 
	 */
	@Override
	public RespStatus edit(@RequestBody BaseCustomerBorrowerDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			dto.setUpdateUid(userApi.getUserDto().getUid());
			baseCustomerBorrowerService.update(dto);
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
	public RespStatus delete(@RequestBody BaseCustomerBorrowerDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			baseCustomerBorrowerService.delete(dto);
			return RespHelper.setSuccessRespStatus(resp);
		}catch (Exception e) {
			logger.error("删除异常,参数："+dto.toString(), e);
			return RespHelper.failRespStatus();
		}
	}
		
}