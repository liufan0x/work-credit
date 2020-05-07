package com.anjbo.dao;

import java.util.List;
import java.util.Map;

import com.anjbo.bean.product.data.ProductBusinfoDto;

public interface ProductBusinfoBaseMapper {

	int insertProductBusinfoBase(ProductBusinfoDto productBusinfoDto);
	
	List<Map<String,Object>> selectProductBusinfoBase(Map<String,Object> map);
	
	/**
	 * 查询同一订单同一影像资料的最大下标
	 * @param map
	 * @return
	 */
	int selectLastIndex(ProductBusinfoDto productBusinfoDto);
	
	int updateProductBusinfo(ProductBusinfoDto productBusinfoDto);
	
	int deleteImgByIds(Map<String,Object> map);

	/**
	 * 批量上传影像资料
	 * @param imags
	 * @return
	 */
	int batchInsertProductBusinfoBase(List<Map<String,Object>> imags);
}
