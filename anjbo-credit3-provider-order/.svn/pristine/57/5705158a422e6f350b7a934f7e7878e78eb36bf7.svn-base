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
import com.anjbo.bean.order.BusinfoTypeDto;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.order.IBusinfoTypeController;
import com.anjbo.service.order.BusinfoTypeService;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 20:14:47
 * @version 1.0
 */
@RestController
public class BusinfoTypeController extends BaseController implements IBusinfoTypeController{

	@Resource private BusinfoTypeService businfoTypeService;
	
	@Resource private UserApi userApi;

	/**
	 * 分页查询
	 * @author Generator 
	 */
	@Override
	public RespPageData<BusinfoTypeDto> page(@RequestBody BusinfoTypeDto dto){
		RespPageData<BusinfoTypeDto> resp = new RespPageData<BusinfoTypeDto>();
		try {
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
			resp.setRows(businfoTypeService.search(dto));
			resp.setTotal(businfoTypeService.count(dto));
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
	public RespData<BusinfoTypeDto> search(@RequestBody BusinfoTypeDto dto){ 
		RespData<BusinfoTypeDto> resp = new RespData<BusinfoTypeDto>();
		try {
			return RespHelper.setSuccessData(resp, businfoTypeService.search(dto));
		}catch (Exception e) {
			logger.error("查询异常,参数："+dto.toString(), e);
			return RespHelper.setFailData(resp, new ArrayList<BusinfoTypeDto>(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 查找
	 * @author Generator 
	 */
	@Override
	public RespDataObject<BusinfoTypeDto> find(@RequestBody BusinfoTypeDto dto){ 
		RespDataObject<BusinfoTypeDto> resp = new RespDataObject<BusinfoTypeDto>();
		try {
			return RespHelper.setSuccessDataObject(resp, businfoTypeService.find(dto));
		}catch (Exception e) {
			logger.error("查找异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new BusinfoTypeDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 新增
	 * @author Generator 
	 */
	@Override
	public RespDataObject<BusinfoTypeDto> add(@RequestBody BusinfoTypeDto dto){ 
		RespDataObject<BusinfoTypeDto> resp = new RespDataObject<BusinfoTypeDto>();
		try {
			dto.setCreateUid(userApi.getUserDto().getUid());
			return RespHelper.setSuccessDataObject(resp, businfoTypeService.insert(dto));
		}catch (Exception e) {
			logger.error("新增异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new BusinfoTypeDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 编辑
	 * @author Generator 
	 */
	@Override
	public RespStatus edit(@RequestBody BusinfoTypeDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			dto.setUpdateUid(userApi.getUserDto().getUid());
			businfoTypeService.update(dto);
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
	public RespStatus delete(@RequestBody BusinfoTypeDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			businfoTypeService.delete(dto);
			return RespHelper.setSuccessRespStatus(resp);
		}catch (Exception e) {
			logger.error("删除异常,参数："+dto.toString(), e);
			return RespHelper.failRespStatus();
		}
	}
		
}