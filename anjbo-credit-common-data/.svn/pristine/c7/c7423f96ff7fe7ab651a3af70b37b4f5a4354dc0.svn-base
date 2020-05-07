/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anjbo.bean.order.OrderUpDto;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.service.OrderContractUpService;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-03-07 14:16:11
 * @version 1.0
 */
@RestController
@RequestMapping("/credit/common/contract/orderUp/v")
public class OrderContractUpController extends BaseController{
	@Resource private OrderContractUpService orderContractUpService;

	/**
	 * 查询
	 * @author Generator 
	 */
	@RequestMapping(value = "/page")
	public RespPageData<OrderUpDto> page(HttpServletRequest request,@RequestBody OrderUpDto dto){
		return new RespPageData<OrderUpDto>(orderContractUpService.searchPage(dto));
	}
	@RequestMapping(value = "/get")
	public RespStatus get(HttpServletRequest request, long id){ 
		try {
			return RespHelper.setSuccessDataObject(new RespDataObject<OrderUpDto>(), orderContractUpService.getEntity(id));
		} catch (Exception e) {
			logger.error("加载出错...：", e);		
			return RespHelper.failRespStatus();
		}
		
	}
	
	/**
	 * 编辑
	 * @author Generator
	 * @return
	 */
	@RequestMapping(value = "/edit")
	public RespStatus edit(HttpServletRequest request, @RequestBody OrderUpDto dto){ 
		try {
			dto.setCreateUid(getUserDto(request).getUid());
			orderContractUpService.insert(dto);
			return RespHelper.setSuccessRespStatus(new RespStatus());
		} catch (Exception e) {
			logger.error("编辑失败，异常信息：", e);			
		}
		return RespHelper.failRespStatus();
	}
	
	@RequestMapping(value = "/delete")
	public RespStatus delete(HttpServletRequest request, @RequestBody OrderUpDto dto){ 
		try {
			dto.setCreateUid(getUserDto(request).getUid());
			if(orderContractUpService.delete(dto) > 0){
				return RespHelper.setSuccessRespStatus(new RespStatus());
			}			
		} catch (Exception e) {
			logger.error("编辑失败，异常信息：", e);			
		}
		return RespHelper.failRespStatus();
	}
		
}