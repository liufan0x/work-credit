package com.anjbo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anjbo.bean.common.DictDto;
import com.anjbo.bean.product.ProductDto;
import com.anjbo.common.RedisOperator;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.ProductBaseService;

@Controller
@RequestMapping("/credit/product/base/v")
public class ProductBaseController extends BaseController{
	
	@Resource
	private ProductBaseService productBaseService;
	
	@ResponseBody
	@RequestMapping(value = "/initData") 
	public RespStatus selectOrderFlowList(HttpServletRequest request){
		RespStatus resp = new RespStatus();
		try {
			List<ProductDto> list = productBaseService.selectProductBaseList(null);
			RedisOperator.set("productList", list);
			Map<Object, Object> productIdTempMap = new HashMap<Object, Object>();
			Map<Object, Object> productCodeTempMap = new HashMap<Object, Object>();
			for (ProductDto productDto : list) {
				productIdTempMap.put(String.valueOf(productDto.getId()), productDto);
				productCodeTempMap.put(productDto.getProductCode(), productDto);
			}
			RedisOperator.putToMap("productListMapByPorductId",productIdTempMap);
			RedisOperator.putToMap("productListMapByproductCode",productCodeTempMap);
			RespHelper.setSuccessRespStatus(resp);
		} catch (Exception e) {
			e.printStackTrace();
			RespHelper.setFailRespStatus(resp, RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}

	@ResponseBody
	@RequestMapping(value = "/list") 
	public RespPageData<ProductDto> list(HttpServletRequest request,@RequestBody ProductDto productDto){
		RespPageData<ProductDto> resp = new RespPageData<ProductDto>();
		try {
			List<ProductDto> list = productBaseService.selectProductBaseList(productDto);
			List<DictDto> dictDtos = getDictDtoByType("bookingSzAreaOid");
			for (ProductDto product : list) {
				for (DictDto dictDto : dictDtos) {
					if(product.getCityCode().equals(dictDto.getCode())){
						product.setCityName(dictDto.getName());
						break;
					}
				}
			}
			resp.setRows(list);
			resp.setTotal(productBaseService.selectProductBaseCount(productDto));
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}
	
	@ResponseBody
	@RequestMapping(value = "/find") 
	public RespDataObject<ProductDto> find(HttpServletRequest request,@RequestBody ProductDto productDto){
		RespDataObject<ProductDto> resp = new RespDataObject<ProductDto>();
		try {
			productDto = productBaseService.findproductDto(productDto);
			List<DictDto> dictDtos = getDictDtoByType("bookingSzAreaOid");
			for (DictDto dictDto : dictDtos) {
				if(productDto.getCityCode().equals(dictDto.getCode())){
					productDto.setCityName(dictDto.getName());
					break;
				}
			}
			resp.setData(productDto);
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}
	
	@ResponseBody
	@RequestMapping(value = "/update") 
	public RespStatus update(HttpServletRequest request,@RequestBody ProductDto productDto){
		RespStatus resp = new RespStatus();
		try {
			if(productBaseService.selectProductBase(productDto) == null){
				if(productBaseService.updateProductBase(productDto)>0){
					resp.setCode(RespStatusEnum.SUCCESS.getCode());
					resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
				}else{
					resp.setCode(RespStatusEnum.FAIL.getCode());
					resp.setMsg(RespStatusEnum.FAIL.getMsg());
				}
			}else{
				resp.setCode(RespStatusEnum.FAIL.getCode());
				resp.setMsg("该城市已有相同的产品名称");
			}
		} catch (Exception e) {
			e.printStackTrace();
			resp.setCode(RespStatusEnum.FAIL.getCode());
			resp.setMsg(RespStatusEnum.FAIL.getMsg());
		}
		return resp;
	}
	
}
