package com.anjbo.service;

import java.util.List;
import java.util.Map;

public interface RiskBaseService {

	/**
	 * 根据类型查询图片
	 * @param map key{imgType:(archive:查档,enquiry:询价,lawsuit:诉讼),orderNo:订单编号}
	 * @return
	 */
	List<Map<String,Object>> listImg(Map<String,Object> map);
	/**
	 * 根据id删除图片
	 * @param map key{id:图片id(可以为id集合)}
	 * @return
	 */
	List<Map<String,Object>> deleteImgById(Map<String,Object> map);
	/**
	 * 新增图片
	 * @param map key{imgType:图片类型(archive:查档,enquiry:询价,lawsuit:诉讼),orderNo:订单编号,imgUrl:图片链接}
	 * @return
	 */
	List<Map<String,Object>> insertImg(Map<String,Object> map);
	
}
