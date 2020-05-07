package com.anjbo.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.bean.product.UploadInsuranceDto;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.UploadInsuranceService;

@Controller
@RequestMapping("/credit/process/uploadInsurance/v")
public class UploadInsuranceController extends BaseController{

	@Resource UploadInsuranceService uploadInsuranceService;
	
	/**
	 * 详情
	 * @param request
	 * @param uploadInsuranceDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/detail") 
	public RespDataObject<UploadInsuranceDto> detail(HttpServletRequest request,@RequestBody  UploadInsuranceDto uploadInsuranceDto){
		 RespDataObject<UploadInsuranceDto> resp=new RespDataObject<UploadInsuranceDto>();
		 try {
			UploadInsuranceDto dto=uploadInsuranceService.select(uploadInsuranceDto);
			 if(dto==null){
				 dto=new UploadInsuranceDto();
				 dto.setOrderNo(uploadInsuranceDto.getOrderNo());
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
