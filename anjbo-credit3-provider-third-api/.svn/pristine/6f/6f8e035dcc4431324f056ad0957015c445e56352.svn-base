/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller.impl.sgtong;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.anjbo.controller.api.OrderApi;
import com.anjbo.controller.api.UserApi;
import com.anjbo.controller.sgtong.ISgtongBusinfoController;
import com.anjbo.service.sgtong.SgtongBusinfoService;
import com.anjbo.controller.BaseController;
import com.anjbo.common.RespHelper;
import com.anjbo.bean.UserDto;
import com.anjbo.bean.sgtong.SgtongBusinfoDto;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-11-21 11:21:31
 * @version 1.0
 */
@RestController
public class SgtongBusinfoController extends BaseController implements ISgtongBusinfoController{

	@Resource private SgtongBusinfoService sgtongBusinfoService;
	
	@Resource private UserApi userApi;
	@Resource
	private OrderApi orderApi;
	/**
	 * 分页查询
	 * @author Generator 
	 */
	@Override
	public RespPageData<SgtongBusinfoDto> page(@RequestBody SgtongBusinfoDto dto){
		RespPageData<SgtongBusinfoDto> resp = new RespPageData<SgtongBusinfoDto>();
		try {
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
			resp.setRows(sgtongBusinfoService.search(dto));
			resp.setTotal(sgtongBusinfoService.count(dto));
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
	public RespData<SgtongBusinfoDto> search(@RequestBody SgtongBusinfoDto dto){ 
		RespData<SgtongBusinfoDto> resp = new RespData<SgtongBusinfoDto>();
		try {
			List<SgtongBusinfoDto> dtos=sgtongBusinfoService.search(dto);
			
			return RespHelper.setSuccessData(resp, sgtongBusinfoService.search(dto));
		}catch (Exception e) {
			logger.error("查询异常,参数："+dto.toString(), e);
			return RespHelper.setFailData(resp, new ArrayList<SgtongBusinfoDto>(), RespStatusEnum.FAIL.getMsg());
		}
	}

	@Override
	public RespDataObject<List<Map<String, Object>>> busInfoTypes(@RequestBody Map<String, Object> map){ 
		RespDataObject<List<Map<String, Object>>> resp = new RespDataObject<List<Map<String, Object>>>();
		try {
			
			List<Map<String, Object>> parentList = orderApi.getParentBusInfoTypes(map);
			 SgtongBusinfoDto dto =new SgtongBusinfoDto();
			 dto.setOrderNo(String.valueOf(map.get("orderNo")));
			 dto.setIsDelete(2);
			List<SgtongBusinfoDto> dtos=sgtongBusinfoService.search(dto);
			
			//组装每个类型的文件数据
			List<Map<String, Object>> sgtbusinfodata =new ArrayList<Map<String, Object>>();
			for (Map<String, Object> parent : parentList) {
				Map<String, Object> typeMap = new HashMap<>();
				typeMap.put("typeId", parent.get("id"));
				typeMap.put("type", parent.get("name"));
				typeMap.put("remark", parent.get("remark"));
				List<SgtongBusinfoDto> sgtbusinfo = new ArrayList<SgtongBusinfoDto>();

				for (SgtongBusinfoDto sgtongBusinfoDto : dtos) {

					if (sgtongBusinfoDto.getTypeId().equals(parent.get("id"))) {
						sgtbusinfo.add(sgtongBusinfoDto);
					}
				}
			typeMap.put("img",sgtbusinfo );
			sgtbusinfodata.add(typeMap);
			
			}
			
			
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
			return RespHelper.setSuccessDataObject(resp, sgtbusinfodata);
		}catch (Exception e) {
			return RespHelper.setFailDataObject(resp,null, RespStatusEnum.FAIL.getMsg());
		}
	}

	
	
	/**
	 * 查找
	 * @author Generator 
	 */
	@Override
	public RespDataObject<SgtongBusinfoDto> find(@RequestBody SgtongBusinfoDto dto){ 
		RespDataObject<SgtongBusinfoDto> resp = new RespDataObject<SgtongBusinfoDto>();
		try {
			return RespHelper.setSuccessDataObject(resp, sgtongBusinfoService.find(dto));
		}catch (Exception e) {
			logger.error("查找异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new SgtongBusinfoDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 新增
	 * @author Generator 
	 */
	@Override
	public RespDataObject<SgtongBusinfoDto> add(@RequestBody SgtongBusinfoDto dto){ 
		RespDataObject<SgtongBusinfoDto> resp = new RespDataObject<SgtongBusinfoDto>();
		try {
			dto.setCreateUid(userApi.getUserDto().getUid());
			return RespHelper.setSuccessDataObject(resp, sgtongBusinfoService.insert(dto));
		}catch (Exception e) {
			logger.error("新增异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new SgtongBusinfoDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 编辑
	 * @author Generator 
	 */
	@Override
	public RespStatus edit(@RequestBody SgtongBusinfoDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			dto.setUpdateUid(userApi.getUserDto().getUid());
			sgtongBusinfoService.update(dto);
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
	public RespStatus delete(@RequestBody SgtongBusinfoDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			sgtongBusinfoService.delete(dto);
			return RespHelper.setSuccessRespStatus(resp);
		}catch (Exception e) {
			logger.error("删除异常,参数："+dto.toString(), e);
			return RespHelper.failRespStatus();
		}
	}


	@Override
	public RespStatus batchAddImage(@RequestBody List<Map<String, Object>> list) {
		RespStatus respStatus = new RespStatus();
		try {
			UserDto user = userApi.getUserDto();
			for (Map<String, Object> m : list) {
				m.put("createUid", user.getUid());
				m.put("isPlus", 2);
			}
			sgtongBusinfoService.batchInsertImg(list);
			RespHelper.setSuccessRespStatus(respStatus);
		} catch (Exception e) {
			logger.error("批量保存陕国投信托影像资料异常:", e);
			RespHelper.setFailRespStatus(respStatus, RespStatusEnum.FAIL.getMsg());
		}
		return respStatus;
	}


	@Override
	public RespStatus stgbatchDeleteImg(@RequestBody Map<String, Object> map) {
		RespStatus result = new RespStatus();
		try {
			if (!map.containsKey("id") || null == MapUtils.getObject(map, "id")) {
				RespHelper.setFailRespStatus(result, RespStatusEnum.PARAMETER_ERROR.getMsg());
				return result;
			}
			UserDto user = userApi.getUserDto();
			map.put("updateUid", user.getUid());
			sgtongBusinfoService.stgbatchDeleteImg(map);
			RespHelper.setSuccessRespStatus(result);
		} catch (Exception e) {
			RespHelper.setFailRespStatus(result, RespStatusEnum.FAIL.getMsg());
			logger.error("批量删除陕国投信托影像资料异常:", e);
		}
		return result;
	}
		
}