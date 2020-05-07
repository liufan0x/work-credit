/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller.impl;

import java.util.ArrayList;
import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.anjbo.controller.BaseController;
import com.anjbo.bean.FundDto;
import com.anjbo.bean.UserDto;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.IFundController;
import com.anjbo.controller.impl.api.UserApiController;
import com.anjbo.dao.FundMapper;
import com.anjbo.service.FundService;
import com.anjbo.service.UserService;


/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-05-10 16:54:59
 * @version 1.0
 */
@RestController
public class FundController extends BaseController implements IFundController{

	@Resource private FundService fundService;
	
	@Resource private UserService userService;
	
	@Resource private UserApiController userControllers;
	
	
	/**
	 * 分页查询
	 * @author Generator 
	 */
	@Override
	public RespPageData<FundDto> page(@RequestBody FundDto dto){
		RespPageData<FundDto> resp = new RespPageData<FundDto>();
		try {
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
			resp.setRows(fundService.search(dto));
			resp.setTotal(fundService.count(dto));
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
	public RespData<FundDto> search(@RequestBody FundDto dto){ 
		RespData<FundDto> resp = new RespData<FundDto>();
		try {
			return RespHelper.setSuccessData(resp, fundService.search(dto));
		}catch (Exception e) {
			logger.error("查询异常,参数："+dto.toString(), e);
			return RespHelper.setFailData(resp, new ArrayList<FundDto>(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 查找
	 * @author Generator 
	 */
	@Override
	public RespDataObject<FundDto> find(@RequestBody FundDto dto){ 
		RespDataObject<FundDto> resp = new RespDataObject<FundDto>();
		try {
			return RespHelper.setSuccessDataObject(resp, fundService.find(dto));
		}catch (Exception e) {
			logger.error("查找异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new FundDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 新增
	 * @author Generator 
	 */
	@Override
	public RespDataObject<FundDto> add(@RequestBody FundDto dto){ 
		RespDataObject<FundDto> resp = new RespDataObject<FundDto>();
		try {
			dto.setStatus(1);
			dto.setCreateUid(userControllers.getUserDto().getUid());
			return RespHelper.setSuccessDataObject(resp, fundService.insert(dto));
		}catch (Exception e) {
			logger.error("新增异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new FundDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 编辑
	 * @author Generator 
	 */
	@Override
	public RespStatus edit(@RequestBody FundDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			dto.setUpdateUid(userControllers.getUserDto().getUid());
			fundService.update(dto);
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
	public RespStatus delete(@RequestBody FundDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			fundService.delete(dto);
			return RespHelper.setSuccessRespStatus(resp);
		}catch (Exception e) {
			logger.error("删除异常,参数："+dto.toString(), e);
			return RespHelper.failRespStatus();
		}
	}
	
	@Override
	public RespStatus changeManagerStatus(@RequestBody FundDto fundDto) {
		try {
			if(StringUtils.isBlank(fundDto.getManagerUid())){
				return RespHelper.setFailRespStatus(new RespStatus(), "资方不存在登录账号信息。");
			}
			UserDto userDto = new UserDto();
			userDto.setUid(fundDto.getManagerUid());
			userDto.setMobile(fundDto.getContactTel());
			userDto.setIsEnable(fundDto.getManagerStatus());
			if(userService.update(userDto) > 0){
				return RespHelper.setFailRespStatus(new RespStatus(), String.format("同步用户状态数据失败(%s-%s)：%s", fundDto.getManagerUid(), fundDto.getContactTel()));
			}
			fundService.update(fundDto);			
			return RespHelper.setSuccessRespStatus(new RespStatus());
		} catch (Exception e) {
			logger.error("修改资方管理人状态资方id:"+fundDto.getId()+"异常",e);
			return RespHelper.failRespStatus();
		}
	}
	
	@Override
	public FundDto selectCustomerFundById(@RequestBody int id) {
		FundDto fundDto = new FundDto();
		fundDto=fundService.selectCustomerFundById(id);
		return fundDto;
	}
}