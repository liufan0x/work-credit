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
import com.anjbo.bean.order.BaseHousePurchaserDto;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.order.IBaseHousePurchaserController;
import com.anjbo.service.order.BaseHousePurchaserService;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 20:14:46
 * @version 1.0
 */
@RestController
public class BaseHousePurchaserController extends BaseController implements IBaseHousePurchaserController{

	@Resource private BaseHousePurchaserService baseHousePurchaserService;
	
	@Resource private UserApi userApi;

	/**
	 * 分页查询
	 * @author Generator 
	 */
	@Override
	public RespPageData<BaseHousePurchaserDto> page(@RequestBody BaseHousePurchaserDto dto){
		RespPageData<BaseHousePurchaserDto> resp = new RespPageData<BaseHousePurchaserDto>();
		try {
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
			resp.setRows(baseHousePurchaserService.search(dto));
			resp.setTotal(baseHousePurchaserService.count(dto));
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
	public RespData<BaseHousePurchaserDto> search(@RequestBody BaseHousePurchaserDto dto){ 
		RespData<BaseHousePurchaserDto> resp = new RespData<BaseHousePurchaserDto>();
		try {
			return RespHelper.setSuccessData(resp, baseHousePurchaserService.search(dto));
		}catch (Exception e) {
			logger.error("查询异常,参数："+dto.toString(), e);
			return RespHelper.setFailData(resp, new ArrayList<BaseHousePurchaserDto>(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 查找
	 * @author Generator 
	 */
	@Override
	public RespDataObject<BaseHousePurchaserDto> find(@RequestBody BaseHousePurchaserDto dto){ 
		RespDataObject<BaseHousePurchaserDto> resp = new RespDataObject<BaseHousePurchaserDto>();
		try {
			return RespHelper.setSuccessDataObject(resp, baseHousePurchaserService.find(dto));
		}catch (Exception e) {
			logger.error("查找异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new BaseHousePurchaserDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 新增
	 * @author Generator 
	 */
	@Override
	public RespDataObject<BaseHousePurchaserDto> add(@RequestBody BaseHousePurchaserDto dto){ 
		RespDataObject<BaseHousePurchaserDto> resp = new RespDataObject<BaseHousePurchaserDto>();
		try {
			dto.setCreateUid(userApi.getUserDto().getUid());
			return RespHelper.setSuccessDataObject(resp, baseHousePurchaserService.insert(dto));
		}catch (Exception e) {
			logger.error("新增异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new BaseHousePurchaserDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 编辑
	 * @author Generator 
	 */
	@Override
	public RespStatus edit(@RequestBody BaseHousePurchaserDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			dto.setUpdateUid(userApi.getUserDto().getUid());
			baseHousePurchaserService.update(dto);
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
	public RespStatus delete(@RequestBody BaseHousePurchaserDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			baseHousePurchaserService.delete(dto);
			return RespHelper.setSuccessRespStatus(resp);
		}catch (Exception e) {
			logger.error("删除异常,参数："+dto.toString(), e);
			return RespHelper.failRespStatus();
		}
	}
		
}