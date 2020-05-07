/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.anjbo.controller.BaseController;
import com.anjbo.bean.DictDto;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.IDictController;
import com.anjbo.service.DictService;
import com.anjbo.utils.StringUtil;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-05-03 15:08:03
 * @version 1.0
 */
@RestController
public class DictController extends BaseController implements IDictController{

	@Resource private DictService dictService;

	/**
	 * 分页查询
	 * @author Generator 
	 */
	@Override
	public RespPageData<DictDto> page(@RequestBody DictDto dto){
		RespPageData<DictDto> resp = new RespPageData<DictDto>();
		try {
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
			resp.setRows(dictService.search(dto));
			resp.setTotal(dictService.count(dto));
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
	public RespData<DictDto> search(@RequestBody DictDto dto){ 
		RespData<DictDto> resp = new RespData<DictDto>();
		try {
			return RespHelper.setSuccessData(resp, dictService.search(dto));
		}catch (Exception e) {
			logger.error("查询异常,参数："+dto.toString(), e);
			return RespHelper.setFailData(resp, new ArrayList<DictDto>(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 查询Map
	 * @author Generator 
	 */
	@Override
	public RespData<Map<String, Object>> searchMap(@RequestBody DictDto dto){ 
		RespData<Map<String, Object>> resp = new RespData<Map<String, Object>>();
		try {
			List<DictDto> dictDtos = dictService.search(dto);
			List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
			for (DictDto dictDto : dictDtos) {
				if("province".equals(dto.getType())&&StringUtil.isBlank(dto.getPcode())) {
					if(StringUtil.isBlank(dictDto.getPcode())) {
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("id", dictDto.getCode());
						map.put("name", dictDto.getName());
						listMap.add(map);
					}
				}else {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("id", dictDto.getCode());
					map.put("name", dictDto.getName());
					listMap.add(map);
				}
			}
			return RespHelper.setSuccessData(resp, listMap);
		}catch (Exception e) {
			logger.error("查询异常,参数："+dto.toString(), e);
			return RespHelper.setFailData(resp, new ArrayList<Map<String, Object>>(), RespStatusEnum.FAIL.getMsg());
		}
	}
	
	/**
	 * 查找
	 * @author Generator 
	 */
	@Override
	public RespDataObject<DictDto> find(@RequestBody DictDto dto){ 
		RespDataObject<DictDto> resp = new RespDataObject<DictDto>();
		try {
			return RespHelper.setSuccessDataObject(resp, dictService.find(dto));
		}catch (Exception e) {
			logger.error("查找异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new DictDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 新增
	 * @author Generator 
	 */
	@Override
	public RespDataObject<DictDto> add(@RequestBody DictDto dto){ 
		RespDataObject<DictDto> resp = new RespDataObject<DictDto>();
		try {
			return RespHelper.setSuccessDataObject(resp, dictService.insert(dto));
		}catch (Exception e) {
			logger.error("新增异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new DictDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 编辑
	 * @author Generator 
	 */
	@Override
	public RespStatus edit(@RequestBody DictDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			dictService.update(dto);
			return RespHelper.setSuccessRespStatus(resp);
		}catch (Exception e) {
			logger.error("新增异常,参数："+dto.toString(), e);
			return RespHelper.failRespStatus();
		}
	}
		
}