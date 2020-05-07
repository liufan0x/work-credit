/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service.sgtong;

import java.util.List;
import java.util.Map;

import com.anjbo.bean.sgtong.SgtongBusinfoDto;
import com.anjbo.service.BaseService;


/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-11-21 11:21:31
 * @version 1.0
 */
public interface SgtongBusinfoService extends BaseService<SgtongBusinfoDto>{

	void batchInsertImg(List<Map<String, Object>> list);

	void stgbatchDeleteImg(Map<String, Object> map);
	
}
