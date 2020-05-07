/*
 *Project: anjbo-credit-third-api
 *File: com.anjbo.service.dingtalk.impl.BpmsTempService.java  <2017年10月16日>
 ****************************************************************
 * 版权所有@2017 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service.dingtalk;

import com.anjbo.bean.dingtalk.ThirdDingtalkBpmsTempDto;

import java.util.List;

/**
 * @Author KangLG 
 * @Date 2017年10月16日 下午3:57:50
 * @version 1.0
 */
public interface BpmsTempService {

	public abstract List<ThirdDingtalkBpmsTempDto> search(
            ThirdDingtalkBpmsTempDto thirdDingtalkBpmsTempDto);

	/**
	 * 获取实体
	 * @Author KangLG<2017年10月16日>
	 * @param id
	 * @return
	 */
	ThirdDingtalkBpmsTempDto getEntity(long id);
	
	ThirdDingtalkBpmsTempDto getEntityByCode(String code);
	
	public abstract int edit(ThirdDingtalkBpmsTempDto thirdDingtalkBpmsTempDto);

}