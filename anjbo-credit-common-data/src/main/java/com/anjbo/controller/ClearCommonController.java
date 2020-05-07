package com.anjbo.controller;

import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.CommonService;

@Controller
@RequestMapping("/credit/common/base")
public class ClearCommonController extends BaseController{
	
	@Resource
	private CommonService commonService;
	
	@ResponseBody
	@RequestMapping(value = "/clearData") 
	public RespStatus initData(){
		try {
			commonService.initData();
			return new RespStatus(RespStatusEnum.SUCCESS.getCode(), RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			return new RespStatus(RespStatusEnum.FAIL.getCode(), RespStatusEnum.FAIL.getMsg());
		}
	}
	
	/**
	 * 清理银行
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/clearBank") 
	public RespStatus clearBank(){
		try {
			commonService.selectBankList();
			return new RespStatus(RespStatusEnum.SUCCESS.getCode(), RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			return new RespStatus(RespStatusEnum.FAIL.getCode(), RespStatusEnum.FAIL.getMsg());
		}
	}
	
	/**
	 * 清理支行
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/clearSubBank") 
	public RespStatus clearSubBank(){
		try {
			commonService.selectSubBankList();
			return new RespStatus(RespStatusEnum.SUCCESS.getCode(), RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			return new RespStatus(RespStatusEnum.FAIL.getCode(), RespStatusEnum.FAIL.getMsg());
		}
	}
	
	/**
	 * 清理字典
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/clearDict") 
	public RespStatus clearDict(){
		try {
			commonService.selectDictList();
			return new RespStatus(RespStatusEnum.SUCCESS.getCode(), RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			return new RespStatus(RespStatusEnum.FAIL.getCode(), RespStatusEnum.FAIL.getMsg());
		}
	}
}
