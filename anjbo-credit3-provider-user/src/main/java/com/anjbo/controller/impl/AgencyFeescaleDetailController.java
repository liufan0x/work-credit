/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller.impl;

import java.util.ArrayList;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.anjbo.controller.BaseController;
import com.anjbo.bean.AgencyFeescaleDetailDto;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.IAgencyFeescaleDetailController;
import com.anjbo.service.AgencyFeescaleDetailService;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-05-10 16:54:58
 * @version 1.0
 */
@RestController
public class AgencyFeescaleDetailController extends BaseController implements IAgencyFeescaleDetailController{

	@Resource private AgencyFeescaleDetailService agencyFeescaleDetailService;

	/**
	 * 分页查询
	 * @author Generator 
	 */
	@Override
	public RespPageData<AgencyFeescaleDetailDto> page(@RequestBody AgencyFeescaleDetailDto dto){
		RespPageData<AgencyFeescaleDetailDto> resp = new RespPageData<AgencyFeescaleDetailDto>();
		try {
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
			resp.setRows(agencyFeescaleDetailService.search(dto));
			resp.setTotal(agencyFeescaleDetailService.count(dto));
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
	public RespData<AgencyFeescaleDetailDto> search(@RequestBody AgencyFeescaleDetailDto dto){ 
		RespData<AgencyFeescaleDetailDto> resp = new RespData<AgencyFeescaleDetailDto>();
		try {
			return RespHelper.setSuccessData(resp, agencyFeescaleDetailService.search(dto));
		}catch (Exception e) {
			logger.error("查询异常,参数："+dto.toString(), e);
			return RespHelper.setFailData(resp, new ArrayList<AgencyFeescaleDetailDto>(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 查找
	 * @author Generator 
	 */
	@Override
	public RespDataObject<AgencyFeescaleDetailDto> find(@RequestBody AgencyFeescaleDetailDto dto){ 
		RespDataObject<AgencyFeescaleDetailDto> resp = new RespDataObject<AgencyFeescaleDetailDto>();
		try {
			return RespHelper.setSuccessDataObject(resp, agencyFeescaleDetailService.find(dto));
		}catch (Exception e) {
			logger.error("查找异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new AgencyFeescaleDetailDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 新增
	 * @author Generator 
	 */
	@Override
	public RespDataObject<AgencyFeescaleDetailDto> add(@RequestBody AgencyFeescaleDetailDto dto){ 
		RespDataObject<AgencyFeescaleDetailDto> resp = new RespDataObject<AgencyFeescaleDetailDto>();
		try {
			return RespHelper.setSuccessDataObject(resp, agencyFeescaleDetailService.insert(dto));
		}catch (Exception e) {
			logger.error("新增异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new AgencyFeescaleDetailDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 编辑
	 * @author Generator 
	 */
	@Override
	public RespStatus edit(@RequestBody AgencyFeescaleDetailDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			agencyFeescaleDetailService.update(dto);
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
	public RespStatus delete(@RequestBody AgencyFeescaleDetailDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			agencyFeescaleDetailService.delete(dto);
			return RespHelper.setSuccessRespStatus(resp);
		}catch (Exception e) {
			logger.error("删除异常,参数："+dto.toString(), e);
			return RespHelper.failRespStatus();
		}
	}
		
}