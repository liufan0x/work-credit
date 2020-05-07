/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller.impl;

import java.util.ArrayList;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.anjbo.controller.api.ThirdApi;
import com.anjbo.controller.api.UserApi;
import com.anjbo.controller.BaseController;
import com.anjbo.controller.IRisklistController;
import com.anjbo.bean.RisklistDto;
import com.anjbo.bean.baidu.BaiduRiskVo;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.RisklistService;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-06-14 20:21:45
 * @version 1.0
 */
@RestController
public class RisklistController extends BaseController implements IRisklistController{

	@Resource private RisklistService risklistService;
	
	@Resource private ThirdApi thirdApi;
	
	@Resource private UserApi userApi;

	/**
	 * 分页查询
	 * @author Generator 
	 */
	@Override
	public RespPageData<RisklistDto> page(@RequestBody RisklistDto dto){
		RespPageData<RisklistDto> resp = new RespPageData<RisklistDto>();
		try {
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
			resp.setRows(risklistService.search(dto));
			resp.setTotal(risklistService.count(dto));
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
	public RespData<RisklistDto> search(@RequestBody RisklistDto dto){ 
		RespData<RisklistDto> resp = new RespData<RisklistDto>();
		try {
			return RespHelper.setSuccessData(resp, risklistService.search(dto));
		}catch (Exception e) {
			logger.error("查询异常,参数："+dto.toString(), e);
			return RespHelper.setFailData(resp, new ArrayList<RisklistDto>(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 查找
	 * @author Generator 
	 */
	@Override
	public RespDataObject<RisklistDto> find(@RequestBody RisklistDto dto){ 
		RespDataObject<RisklistDto> resp = new RespDataObject<RisklistDto>();
		try {
			return RespHelper.setSuccessDataObject(resp, risklistService.find(dto));
		}catch (Exception e) {
			logger.error("查找异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new RisklistDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 新增
	 * @author Generator 
	 */
	@Override
	public RespDataObject<RisklistDto> add(@RequestBody RisklistDto dto){ 
		RespDataObject<RisklistDto> resp = new RespDataObject<RisklistDto>();
		try {
			dto.setCreateUid(userApi.getUserDto().getUid());
			BaiduRiskVo baiduRiskVo = new BaiduRiskVo();
			baiduRiskVo.setName(dto.getName());
			baiduRiskVo.setIdentity(dto.getIdentity());
			baiduRiskVo.setPhone(dto.getPhone());
			Map<String, Object> result = thirdApi.searchBlacklist(baiduRiskVo);
			if(result.containsKey("retCode")) {
				return RespHelper.setFailDataObject(resp, new RisklistDto(), MapUtils.getString(result, "retMsg"));
			}
			dto.setBlackLevel(MapUtils.getString(result, "blackLevel"));
			dto.setBlackReason(MapUtils.getString(result, "blackReason"));
			dto.setBlackDetails(MapUtils.getString(result, "blackDetails"));
			return RespHelper.setSuccessDataObject(resp, risklistService.insert(dto));
		}catch (Exception e) {
			logger.error("新增异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new RisklistDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 编辑
	 * @author Generator 
	 */
	@Override
	public RespStatus edit(@RequestBody RisklistDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			dto.setUpdateUid(userApi.getUserDto().getUid());
			risklistService.update(dto);
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
	public RespStatus delete(@RequestBody RisklistDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			risklistService.delete(dto);
			return RespHelper.setSuccessRespStatus(resp);
		}catch (Exception e) {
			logger.error("删除异常,参数："+dto.toString(), e);
			return RespHelper.failRespStatus();
		}
	}
		
}