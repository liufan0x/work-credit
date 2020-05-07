/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.anjbo.bean.UserDto;
import com.anjbo.bean.data.PageListDto;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.IPageListController;
import com.anjbo.controller.PageBaseController;
import com.anjbo.controller.api.UserApi;
import com.anjbo.service.PageListService;
import com.anjbo.utils.UidUtils;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-05-15 09:25:27
 * @version 1.0
 */
@RestController
public class PageListController extends PageBaseController implements IPageListController{

	@Resource private UserApi userApi;

	@Resource private PageListService pageListService;
	
	@Override
	public RespDataObject<String> generateOrderNo() {
		return RespHelper.setSuccessDataObject(new RespDataObject<String>(), UidUtils.generateOrderId());
	}
	
	@Override
	public RespPageData<Map<String,Object>> pageListData(@RequestBody Map<String,Object> map){
		RespPageData<Map<String,Object>> resp = new RespPageData<Map<String,Object>>();
		try {
			UserDto userDto = userApi.getUserDto();
			String deptAllUid = userDto.getUid();
			//默认看单权限
			String lookAll = "1";
			String lookDept = "2";
			//修改成看机构列表权限
			if(MapUtils.getString(map,"tblName").contains("tbl_sm")){
				lookAll = "102";
				lookDept = "103";
			}
			//查看全部订单
			if(userDto.getAuthIds().contains(lookAll)){
				deptAllUid = "";
				//查看部门订单
			}else if(userDto.getAuthIds().contains(lookDept)){
				deptAllUid = userApi.selectUidsByDeptId(userDto);
				//查看自己订单
			}
			map.put("deptAllUid", deptAllUid);
			map.put("uid", userDto.getUid());
			resp.setRows(pageListService.pageList(map));
			resp.setTotal(pageListService.pageCount(map)); 
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
			return resp;
		} catch (Exception e) {
			logger.error("获取配置列表异常tblName:" + MapUtils.getString(map, "tblName"), e);
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(RespStatusEnum.FAIL.getMsg());
			return resp;
		}
	}
	
	@Override
	public RespStatus withdraw(@RequestBody PageListDto pageListDto) {
		RespStatus resp = new RespStatus();
		try {
			if(StringUtils.isEmpty(pageListDto.getOrderNo()) || StringUtils.isEmpty(pageListDto.getProcessId())) {
				return RespHelper.setFailRespStatus(resp, RespStatusEnum.PARAMETER_ERROR.getMsg());
			}
			UserDto userDto = userApi.getUserDto();
			pageListDto.setUpdateUid(userDto.getUid());
			pageListDto.setCurrentHandler(userDto.getName());
			pageListDto.setCurrentHandlerUid(userDto.getUid());
			pageListDto.setTblDataName(getTbl(pageListDto.getProductCode())+"list");
			pageListService.withdraw(pageListDto);
			return RespHelper.setSuccessRespStatus(resp);
		} catch (Exception e) {
			logger.error("撤回订单异常", e);
			return RespHelper.failRespStatus();
		}
	}
	
	
	@Override
	public RespStatus cancel(@RequestBody PageListDto pageListDto) {
		RespStatus resp = new RespStatus();
		try {
			if(StringUtils.isEmpty(pageListDto.getOrderNo())) {
				return RespHelper.setFailRespStatus(resp, RespStatusEnum.PARAMETER_ERROR.getMsg());
			}
			UserDto userDto = userApi.getUserDto();
			pageListDto.setUpdateUid(userDto.getUid());
			pageListDto.setCurrentHandler("-");
			pageListDto.setCurrentHandlerUid("-");
			pageListDto.setState("已取消");
			pageListDto.setTblDataName(getTbl(pageListDto.getProductCode())+"list");
			pageListService.update(pageListDto);
			return RespHelper.setSuccessRespStatus(resp);
		} catch (Exception e) {
			logger.error("取消订单异常", e);
			return RespHelper.failRespStatus();
		}
	}
	
}