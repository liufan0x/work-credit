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
import com.anjbo.bean.finance.LendingPayDto;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.finance.ILendingPayController;
import com.anjbo.service.finance.LendingPayService;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 18:34:33
 * @version 1.0
 */
@RestController
public class LendingPayController extends BaseController implements ILendingPayController{

	@Resource private LendingPayService lendingPayService;
	
	@Resource private UserApi userApi;

	/**
	 * 分页查询
	 * @author Generator 
	 */
	@Override
	public RespPageData<LendingPayDto> page(@RequestBody LendingPayDto dto){
		RespPageData<LendingPayDto> resp = new RespPageData<LendingPayDto>();
		try {
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
			resp.setRows(lendingPayService.search(dto));
			resp.setTotal(lendingPayService.count(dto));
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
	public RespData<LendingPayDto> search(@RequestBody LendingPayDto dto){ 
		RespData<LendingPayDto> resp = new RespData<LendingPayDto>();
		try {
			return RespHelper.setSuccessData(resp, lendingPayService.search(dto));
		}catch (Exception e) {
			logger.error("查询异常,参数："+dto.toString(), e);
			return RespHelper.setFailData(resp, new ArrayList<LendingPayDto>(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 查找
	 * @author Generator 
	 */
	@Override
	public RespDataObject<LendingPayDto> find(@RequestBody LendingPayDto dto){ 
		RespDataObject<LendingPayDto> resp = new RespDataObject<LendingPayDto>();
		try {
			return RespHelper.setSuccessDataObject(resp, lendingPayService.find(dto));
		}catch (Exception e) {
			logger.error("查找异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new LendingPayDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 新增
	 * @author Generator 
	 */
	@Override
	public RespDataObject<LendingPayDto> add(@RequestBody LendingPayDto dto){ 
		RespDataObject<LendingPayDto> resp = new RespDataObject<LendingPayDto>();
		try {
			dto.setCreateUid(userApi.getUserDto().getUid());
			return RespHelper.setSuccessDataObject(resp, lendingPayService.insert(dto));
		}catch (Exception e) {
			logger.error("新增异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new LendingPayDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 编辑
	 * @author Generator 
	 */
	@Override
	public RespStatus edit(@RequestBody LendingPayDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			dto.setUpdateUid(userApi.getUserDto().getUid());
			lendingPayService.update(dto);
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
	public RespStatus delete(@RequestBody LendingPayDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			lendingPayService.delete(dto);
			return RespHelper.setSuccessRespStatus(resp);
		}catch (Exception e) {
			logger.error("删除异常,参数："+dto.toString(), e);
			return RespHelper.failRespStatus();
		}
	}
		
}