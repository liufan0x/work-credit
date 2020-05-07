/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service.order;

import java.util.List;
import java.util.Map;
import com.anjbo.bean.UserDto;
import com.anjbo.bean.order.BaseListDto;
import com.anjbo.service.BaseService;


/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-05-11 10:57:21
 * @version 1.0
 */
public interface BaseListService extends BaseService<BaseListDto>{
	
	BaseListDto selectDetail(String orderNo);

	/**
	 * @Author 
	 * @Rewrite KangLG<2018年1月12日>   新增hasYAJ参数
	 * @param user
	 * @param hasYAJ  下拉是否包含云按揭
	 * @return
	 */
	Map<String, Object> assembleBrush(UserDto userDto, boolean hasYAJ);
	
	
	/**
	 * 组装app列表显示页
	 * @param baseListDto
	 * @return
	 */
	List<Map<String, Object>> assembleLis(List<BaseListDto> orderListDtos,UserDto userDto);

	/**
	 * 查询列表集合数
	 * @param orderListDto
	 * @return
	 */
	int searchPageListCount(BaseListDto orderListDto);
	
	/**
	 * 查询列表集合
	 * @param orderListDto
	 * @return
	 */
	List<BaseListDto> searchPageList(BaseListDto orderListDto);

	/**
	 * 查询关联订单信息
	 * @param baseListDto
	 * @return
	 */
	List<BaseListDto> selectAbleRelationOrder(BaseListDto baseListDto);
}
