/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller.impl.finance;

import java.util.ArrayList;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.anjbo.controller.api.UserApi;
import com.anjbo.controller.BaseController;
import com.anjbo.bean.finance.FinanceLogDto;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.finance.IFinanceLogController;
import com.anjbo.service.finance.FinanceLogService;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 18:34:34
 * @version 1.0
 */
@RestController
public class FinanceLogController extends BaseController implements IFinanceLogController{

	@Resource private FinanceLogService financeLogService;
	
	@Resource private UserApi userApi;

	/**
	 * 分页查询
	 * @author Generator 
	 */
	@Override
	public RespPageData<FinanceLogDto> page(@RequestBody FinanceLogDto dto){
		RespPageData<FinanceLogDto> resp = new RespPageData<FinanceLogDto>();
		try {
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
			resp.setRows(financeLogService.search(dto));
			resp.setTotal(financeLogService.count(dto));
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
	public RespData<FinanceLogDto> search(@RequestBody FinanceLogDto dto){ 
		RespData<FinanceLogDto> resp = new RespData<FinanceLogDto>();
		try {
			return RespHelper.setSuccessData(resp, financeLogService.search(dto));
		}catch (Exception e) {
			logger.error("查询异常,参数："+dto.toString(), e);
			return RespHelper.setFailData(resp, new ArrayList<FinanceLogDto>(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 查找
	 * @author Generator 
	 */
	@Override
	public RespDataObject<FinanceLogDto> find(@RequestBody FinanceLogDto dto){ 
		RespDataObject<FinanceLogDto> resp = new RespDataObject<FinanceLogDto>();
		try {
			return RespHelper.setSuccessDataObject(resp, financeLogService.find(dto));
		}catch (Exception e) {
			logger.error("查找异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new FinanceLogDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 新增
	 * @author Generator 
	 */
	@Override
	public RespDataObject<FinanceLogDto> add(@RequestBody FinanceLogDto dto){ 
		RespDataObject<FinanceLogDto> resp = new RespDataObject<FinanceLogDto>();
		try {
			dto.setCreateUid(userApi.getUserDto().getUid());
			return RespHelper.setSuccessDataObject(resp, financeLogService.insert(dto));
		}catch (Exception e) {
			logger.error("新增异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new FinanceLogDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 编辑
	 * @author Generator 
	 */
	@Override
	public RespStatus edit(@RequestBody FinanceLogDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			dto.setUpdateUid(userApi.getUserDto().getUid());
			financeLogService.update(dto);
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
	public RespStatus delete(@RequestBody FinanceLogDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			financeLogService.delete(dto);
			return RespHelper.setSuccessRespStatus(resp);
		}catch (Exception e) {
			logger.error("删除异常,参数："+dto.toString(), e);
			return RespHelper.failRespStatus();
		}
	}
		
}