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
import com.anjbo.bean.order.BaseHouseCreditDto;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.order.IBaseHouseCreditController;
import com.anjbo.service.order.BaseHouseCreditService;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 20:14:46
 * @version 1.0
 */
@RestController
public class BaseHouseCreditController extends BaseController implements IBaseHouseCreditController{

	@Resource private BaseHouseCreditService baseHouseCreditService;
	
	@Resource private UserApi userApi;

	/**
	 * 分页查询
	 * @author Generator 
	 */
	@Override
	public RespPageData<BaseHouseCreditDto> page(@RequestBody BaseHouseCreditDto dto){
		RespPageData<BaseHouseCreditDto> resp = new RespPageData<BaseHouseCreditDto>();
		try {
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
			resp.setRows(baseHouseCreditService.search(dto));
			resp.setTotal(baseHouseCreditService.count(dto));
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
	public RespData<BaseHouseCreditDto> search(@RequestBody BaseHouseCreditDto dto){ 
		RespData<BaseHouseCreditDto> resp = new RespData<BaseHouseCreditDto>();
		try {
			return RespHelper.setSuccessData(resp, baseHouseCreditService.search(dto));
		}catch (Exception e) {
			logger.error("查询异常,参数："+dto.toString(), e);
			return RespHelper.setFailData(resp, new ArrayList<BaseHouseCreditDto>(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 查找
	 * @author Generator 
	 */
	@Override
	public RespDataObject<BaseHouseCreditDto> find(@RequestBody BaseHouseCreditDto dto){ 
		RespDataObject<BaseHouseCreditDto> resp = new RespDataObject<BaseHouseCreditDto>();
		try {
			return RespHelper.setSuccessDataObject(resp, baseHouseCreditService.find(dto));
		}catch (Exception e) {
			logger.error("查找异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new BaseHouseCreditDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 新增
	 * @author Generator 
	 */
	@Override
	public RespDataObject<BaseHouseCreditDto> add(@RequestBody BaseHouseCreditDto dto){ 
		RespDataObject<BaseHouseCreditDto> resp = new RespDataObject<BaseHouseCreditDto>();
		try {
			dto.setCreateUid(userApi.getUserDto().getUid());
			return RespHelper.setSuccessDataObject(resp, baseHouseCreditService.insert(dto));
		}catch (Exception e) {
			logger.error("新增异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new BaseHouseCreditDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 编辑
	 * @author Generator 
	 */
	@Override
	public RespStatus edit(@RequestBody BaseHouseCreditDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			dto.setUpdateUid(userApi.getUserDto().getUid());
			baseHouseCreditService.update(dto);
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
	public RespStatus delete(@RequestBody BaseHouseCreditDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			baseHouseCreditService.delete(dto);
			return RespHelper.setSuccessRespStatus(resp);
		}catch (Exception e) {
			logger.error("删除异常,参数："+dto.toString(), e);
			return RespHelper.failRespStatus();
		}
	}
		
}