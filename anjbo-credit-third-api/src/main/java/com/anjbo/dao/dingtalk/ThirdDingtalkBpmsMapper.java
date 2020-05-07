package com.anjbo.dao.dingtalk;

import java.util.List;

import com.anjbo.bean.dingtalk.ThirdDingtalkBpmsDto;
 
 /**
  *  [Dao接口类]
  * @ClassName: ThirdDingtalkBpmsMapper
  * @Description: 
  * @date 2017-10-13 11:37:59
  * @version V3.0
 */
public interface ThirdDingtalkBpmsMapper{
	/**
	 * 获取列表
	 * @param thirdDingtalkBpmsDto
	 * @return list
	 */
	List<ThirdDingtalkBpmsDto> search(ThirdDingtalkBpmsDto thirdDingtalkBpmsDto);
	int searchCount(ThirdDingtalkBpmsDto thirdDingtalkBpmsDto);

	/**
	 * @description:添加 
	 * @param ThirdDingtalkBpmsDto
	 * @return int
	 */
	int add(ThirdDingtalkBpmsDto thirdDingtalkBpmsDto);
	
}