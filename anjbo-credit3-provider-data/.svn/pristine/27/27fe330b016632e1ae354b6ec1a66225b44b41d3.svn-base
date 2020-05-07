/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.controller.impl;

import java.util.ArrayList;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.anjbo.controller.BaseController;
import com.anjbo.bean.ProductDto;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespData;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespPageData;
import com.anjbo.common.RespStatus;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.controller.IProductController;
import com.anjbo.service.ProductService;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-05-03 15:02:46
 * @version 1.0
 */
@RestController
public class ProductController extends BaseController implements IProductController{

	@Resource private ProductService productService;

	/**
	 * 分页查询
	 * @author Generator 
	 */
	@Override
	public RespPageData<ProductDto> page(@RequestBody ProductDto dto){
		RespPageData<ProductDto> resp = new RespPageData<ProductDto>();
		try {
			resp.setCode(RespStatusEnum.SUCCESS.getCode());
			resp.setMsg(RespStatusEnum.SUCCESS.getMsg());
			resp.setRows(productService.search(dto));
			resp.setTotal(productService.count(dto));
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
	public RespData<ProductDto> search(@RequestBody ProductDto dto){ 
		RespData<ProductDto> resp = new RespData<ProductDto>();
		try {
			return RespHelper.setSuccessData(resp, productService.search(dto));
		}catch (Exception e) {
			logger.error("查询异常,参数："+dto.toString(), e);
			return RespHelper.setFailData(resp, new ArrayList<ProductDto>(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 查找
	 * @author Generator 
	 */
	@Override
	public RespDataObject<ProductDto> find(@RequestBody ProductDto dto){ 
		RespDataObject<ProductDto> resp = new RespDataObject<ProductDto>();
		try {
			return RespHelper.setSuccessDataObject(resp, productService.find(dto));
		}catch (Exception e) {
			logger.error("查找异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new ProductDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 新增
	 * @author Generator 
	 */
	@Override
	public RespDataObject<ProductDto> add(@RequestBody ProductDto dto){ 
		RespDataObject<ProductDto> resp = new RespDataObject<ProductDto>();
		try {
			return RespHelper.setSuccessDataObject(resp, productService.insert(dto));
		}catch (Exception e) {
			logger.error("新增异常,参数："+dto.toString(), e);
			return RespHelper.setFailDataObject(resp, new ProductDto(), RespStatusEnum.FAIL.getMsg());
		}
	}

	/**
	 * 编辑
	 * @author Generator 
	 */
	@Override
	public RespStatus edit(@RequestBody ProductDto dto){ 
		RespStatus resp = new RespStatus();
		try {
			productService.update(dto);
			return RespHelper.setSuccessRespStatus(resp);
		}catch (Exception e) {
			logger.error("新增异常,参数："+dto.toString(), e);
			return RespHelper.failRespStatus();
		}
	}
		
}