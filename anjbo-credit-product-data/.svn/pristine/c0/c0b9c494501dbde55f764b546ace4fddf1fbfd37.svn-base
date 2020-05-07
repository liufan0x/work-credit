package com.anjbo.service;

import java.util.List;
import java.util.Map;

import com.anjbo.bean.order.OrderBusinfoDto;
import com.anjbo.bean.product.data.ProductBusinfoDto;

public interface ProductBusinfoBaseService {
	
	/**
	 * 录入影像资料
	 * @param map
	 * @return
	 */
	int insertProductBusinfoBase(ProductBusinfoDto productBusinfoDto,boolean flag);
	
	/**
	 * 查询订单影像资料
	 * @param map
	 * @return
	 */
	List<Map<String,Object>> selectProductBusinfoBase(Map<String,Object> map);
	
	int updateProductBusinfo(ProductBusinfoDto productBusinfoDto);
	
	int move(Map<String,Object> map);
	
	int deleteImgByIds(Map<String,Object> map);

	/**
	 * 校验必备影像资料
	 * @param map(key=productCode:产品code,key=tblName,key=orderNo)
	 * @return true(校验通过),false(校验失败)
	 */
	boolean verificationImgage(Map<String,Object> map);
}
