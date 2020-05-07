package com.anjbo.controller;

import com.anjbo.bean.customer.FundAdminDto;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.FundAdminService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/credit/customer/fund/v")
public class FundAdminController {

	private static final Log log = LogFactory.getLog(FundAdminController.class);
	
	@Resource
	private FundAdminService fundAdminService;
	
	@ResponseBody
	@RequestMapping("/list")
	public RespDataObject<List<FundAdminDto>> list(HttpServletRequest request,@RequestBody FundAdminDto obj){
		RespDataObject<List<FundAdminDto>> result = new RespDataObject<List<FundAdminDto>>();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			List<FundAdminDto> list = fundAdminService.list(obj);
			result.setData(list);
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e){
			log.error("查询资金方列表异常,异常信息为:", e);
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/detail")
	public RespDataObject<FundAdminDto> detail(HttpServletRequest request,@RequestBody FundAdminDto obj){
		RespDataObject<FundAdminDto> result = new RespDataObject<FundAdminDto>();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			if(null==obj.getId()){
				result.setMsg("查询参数不能为空");
				return result;
			}
			obj = fundAdminService.detail(obj);
			result.setData(obj);
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e){
			log.error("查询资金方列表异常,异常信息为:", e);
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/listRelation")
	public RespDataObject<List<FundAdminDto>> listRelation(HttpServletRequest request,@RequestBody FundAdminDto obj){
		RespDataObject<List<FundAdminDto>> result = new RespDataObject<List<FundAdminDto>>();
		result.setCode(RespStatusEnum.FAIL.getCode());
		result.setMsg(RespStatusEnum.FAIL.getMsg());
		try{
			List<FundAdminDto> list = fundAdminService.listRelation(obj);
			result.setData(list);
			result.setCode(RespStatusEnum.SUCCESS.getCode());
			result.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e){
			log.error("查询资金方列表异常,异常信息为:", e);
		}
		return result;
	}
}
