package com.anjbo.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.bean.product.SignInsuranceDto;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.SignInsuranceService;

@Controller
@RequestMapping("/credit/process/signInsurance/v")
public class SignInsuranceController extends BaseController{

	@Resource SignInsuranceService signInsuranceService;
	
	/**
	 * 详情
	 * @param request
	 * @param signInsuranceDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/detail") 
	public RespDataObject<SignInsuranceDto> detail(HttpServletRequest request,@RequestBody  SignInsuranceDto signInsuranceDto){
		 RespDataObject<SignInsuranceDto> resp=new RespDataObject<SignInsuranceDto>();
		 try {
			SignInsuranceDto dto=signInsuranceService.select(signInsuranceDto);
			 if(dto==null){
				 dto=new SignInsuranceDto();
				 dto.setOrderNo(signInsuranceDto.getOrderNo());
			 }
			 resp.setData(dto);
			 resp.setCode(RespStatusEnum.SUCCESS.getCode());
			 resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
		}
		 return resp;
	}
	
}
