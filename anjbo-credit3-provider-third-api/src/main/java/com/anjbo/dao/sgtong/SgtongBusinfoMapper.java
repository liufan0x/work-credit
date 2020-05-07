/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.dao.sgtong;

import java.util.List;
import java.util.Map;

import com.anjbo.bean.sgtong.SgtongBusinfoDto;
import com.anjbo.dao.BaseMapper;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-11-21 11:21:31
 * @version 1.0
 */
public interface SgtongBusinfoMapper extends BaseMapper<SgtongBusinfoDto>{

	void batchInsertImg(List<Map<String, Object>> list);

	void update(Map<String, Object> map);
		
}
