/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller.impl.order;

import java.util.ArrayList;
import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.anjbo.controller.api.UserApi;
import com.anjbo.controller.BaseController;
import com.anjbo.bean.UserDto;
import com.anjbo.bean.order.BaseBorrowDto;
import com.anjbo.bean.order.BaseListDto;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.order.IBaseBorrowController;
import com.anjbo.service.order.BaseBorrowService;
import com.anjbo.service.order.BaseListService;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-07-03 20:14:44
 * @version 1.0
 */
@RestController
public class BaseBorrowController extends BaseController implements IBaseBorrowController{

	@Resource private BaseBorrowService baseBorrowService;
	
	@Resource private BaseListService baseListService;
	
	@Resource private UserApi userApi;

	/**
	 * 分页查询
	 * @author Generator 
	 */
	@Override
	public RespPageData<BaseBorrowDto> page(@RequestBody BaseBorrowDto dto){
		RespPageData<BaseBorrowDto> resp = new RespPageData<BaseBorrowDto>();
		try {
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
			resp.setRows(baseBorrowService.search(dto));
			resp.setTotal(baseBorrowService.count(dto));
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
	public RespData<BaseBorrowDto> search(@RequestBody BaseBorrowDto dto){ 
		RespData<BaseBorrowDto> resp = new RespData<BaseBorrowDto>();
		try {
			return RespHelper.setSuccessData(resp, baseBorrowService.search(dto));
		}catch (Exception e) {
			logger.error("查询异常,参数："+dto.toString(), e);
			return RespHelper.setFailData(resp, new ArrayList<BaseBorrowDto>(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 查找
	 * @author Generator 
	 */
	@Override
	public RespDataObject<BaseBorrowDto> find(@RequestBody BaseBorrowDto dto){ 
		RespDataObject<BaseBorrowDto> resp = new RespDataObject<BaseBorrowDto>();
		try {
			return RespHelper.setSuccessDataObject(resp, baseBorrowService.find(dto));
		}catch (Exception e) {
			logger.error("查找异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new BaseBorrowDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 新增
	 * @author Generator 
	 */
	@Override
	public RespDataObject<BaseBorrowDto> add(@RequestBody BaseBorrowDto dto){ 
		RespDataObject<BaseBorrowDto> resp = new RespDataObject<BaseBorrowDto>();
		try {
			if(dto.getLoanAmount() != null && dto.getLoanAmount() >= 2000){
				return RespHelper.setFailDataObject(resp, null, "超过2000万的订单请线下处理");
			}
			UserDto userDto = userApi.getUserDto();
			dto.setCreateUid(userDto.getUid());
			dto.setAgencyId(userDto.getAgencyId());
			dto.setUpdateUid(userDto.getUid());
			
//			if(userDto.getAgencyId() > 1) {
//				dto.setChannelManagerUid(userDto.getAgencyChanlManUid());
//				dto.setCooperativeAgencyId(userDto.getAgencyId());
//			}else if(1 == userDto.getAgencyId() && dto.getCooperativeAgencyId() < 1 && StringUtils.isNotEmpty(dto.getRelationOrderNo())) {
//				BaseListDto baseListDto = new BaseListDto();
//				baseListDto.setOrderNo(dto.getRelationOrderNo());
//				baseListDto = baseListService.find(baseListDto);
//				dto.setCooperativeAgencyId(baseListDto.getCooperativeAgencyId());
//				dto.setChannelManagerUid(baseListDto.getChannelManagerUid());
//			}
			return RespHelper.setSuccessDataObject(resp, baseBorrowService.insert(dto));
		}catch (Exception e) {
			logger.error("新增异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new BaseBorrowDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 编辑
	 * @author Generator 
	 */
	@Override
	public RespStatus edit(@RequestBody BaseBorrowDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			dto.setUpdateUid(userApi.getUserDto().getUid());
			baseBorrowService.update(dto);
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
	public RespStatus delete(@RequestBody BaseBorrowDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			baseBorrowService.delete(dto);
			return RespHelper.setSuccessRespStatus(resp);
		}catch (Exception e) {
			logger.error("删除异常,参数："+dto.toString(), e);
			return RespHelper.failRespStatus();
		}
	}
		
}