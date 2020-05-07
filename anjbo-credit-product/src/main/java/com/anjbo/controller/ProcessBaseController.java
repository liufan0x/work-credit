package com.anjbo.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.anjbo.bean.product.ProductProcessDto;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.ProductProcessService;

@Controller
@RequestMapping("/credit/product/process/v")
public class ProcessBaseController extends BaseController{
	
	@Resource
	private ProductProcessService productProcessService;
	
	@ResponseBody
	@RequestMapping(value = "/list") 
	public RespPageData<ProductProcessDto> list(HttpServletRequest request,@RequestBody ProductProcessDto productProcessDto){
		RespPageData<ProductProcessDto> resp = new RespPageData<ProductProcessDto>();
		try {
			resp.setRows(productProcessService.selectProductProcessList(productProcessDto));
			resp.setTotal(productProcessService.selectProductProcessCount(productProcessDto));
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}
	
}
