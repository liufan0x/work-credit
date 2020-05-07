/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.anjbo.controller.BaseController;
import com.anjbo.bean.AgencyFeescaleDto;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.IAgencyFeescaleController;
import com.anjbo.service.AgencyFeescaleService;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-05-10 16:54:58
 * @version 1.0
 */
@RestController
public class AgencyFeescaleController extends BaseController implements IAgencyFeescaleController{

	@Resource private AgencyFeescaleService agencyFeescaleService;

	/**
	 * 分页查询
	 * @author Generator 
	 */
	@Override
	public RespPageData<AgencyFeescaleDto> page(@RequestBody AgencyFeescaleDto dto){
		RespPageData<AgencyFeescaleDto> resp = new RespPageData<AgencyFeescaleDto>();
		try {
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
			resp.setRows(agencyFeescaleService.search(dto));
			resp.setTotal(agencyFeescaleService.count(dto));
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
	public RespData<AgencyFeescaleDto> search(@RequestBody AgencyFeescaleDto dto){ 
		RespData<AgencyFeescaleDto> resp = new RespData<AgencyFeescaleDto>();
		try {
			List<AgencyFeescaleDto> list = agencyFeescaleService.search(dto);
			AgencyFeescaleDto agencyFeescaleDto = new AgencyFeescaleDto();
			agencyFeescaleDto.setId(0);
			agencyFeescaleDto.setRiskGradeId(0);
			agencyFeescaleDto.setRiskGradeName("其他");
			list.add(agencyFeescaleDto);
			return RespHelper.setSuccessData(resp,list);
		}catch (Exception e) {
			logger.error("查询异常,参数："+dto.toString(), e);
			return RespHelper.setFailData(resp, new ArrayList<AgencyFeescaleDto>(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 查找
	 * @author Generator 
	 */
	@Override
	public RespDataObject<AgencyFeescaleDto> find(@RequestBody AgencyFeescaleDto dto){ 
		RespDataObject<AgencyFeescaleDto> resp = new RespDataObject<AgencyFeescaleDto>();
		try {
			return RespHelper.setSuccessDataObject(resp, agencyFeescaleService.find(dto));
		}catch (Exception e) {
			logger.error("查找异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new AgencyFeescaleDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 新增
	 * @author Generator 
	 */
	@Override
	public RespDataObject<AgencyFeescaleDto> add(@RequestBody AgencyFeescaleDto dto){ 
		RespDataObject<AgencyFeescaleDto> resp = new RespDataObject<AgencyFeescaleDto>();
		try {
			return RespHelper.setSuccessDataObject(resp, agencyFeescaleService.insert(dto));
		}catch (Exception e) {
			logger.error("新增异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new AgencyFeescaleDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 编辑
	 * @author Generator 
	 */
	@Override
	public RespStatus edit(@RequestBody AgencyFeescaleDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			agencyFeescaleService.update(dto);
			return RespHelper.setSuccessRespStatus(resp);
		}catch (Exception e) {
			logger.error("编辑异常,参数："+dto.toString(), e);
			return RespHelper.failRespStatus();
		}
	}
	

	/**
	 * 批量编辑
	 * @author Generator 
	 */
	@Override
	public RespStatus batchUpdate(@RequestBody List<AgencyFeescaleDto> agencyFeescaleDtos){ 
		RespStatus resp = new RespStatus();
		try {
			for (AgencyFeescaleDto agencyFeescaleDto : agencyFeescaleDtos) {
				agencyFeescaleService.delete(agencyFeescaleDto);
			}
			for (AgencyFeescaleDto agencyFeescaleDto : agencyFeescaleDtos) {
				agencyFeescaleService.insert(agencyFeescaleDto);
			}
			return RespHelper.setSuccessRespStatus(resp);
		}catch (Exception e) {
			logger.error("批量编辑异常,参数：", e);
			return RespHelper.failRespStatus();
		}
	}

	/**
	 * 删除
	 * @author Generator 
	 */
	@Override
	public RespStatus delete(@RequestBody AgencyFeescaleDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			agencyFeescaleService.delete(dto);
			return RespHelper.setSuccessRespStatus(resp);
		}catch (Exception e) {
			logger.error("删除异常,参数："+dto.toString(), e);
			return RespHelper.failRespStatus();
		}
	}
		
}