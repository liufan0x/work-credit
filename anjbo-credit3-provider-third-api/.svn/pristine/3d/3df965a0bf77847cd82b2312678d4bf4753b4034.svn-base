/*
 *Project: ANJBO Generator
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.service.sgtong.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anjbo.bean.sgtong.SgtongBusinfoDto;
import com.anjbo.dao.sgtong.SgtongBusinfoMapper;
import com.anjbo.service.impl.BaseServiceImpl;
import com.anjbo.service.sgtong.SgtongBusinfoService;

/**
 * 
 * @Author ANJBO Generator 
 * @Date 2018-11-21 11:21:31
 * @version 1.0
 */
@Service
public class SgtongBusinfoServiceImpl extends BaseServiceImpl<SgtongBusinfoDto>  implements SgtongBusinfoService {
	@Autowired private SgtongBusinfoMapper sgtongBusinfoMapper;

	@Override
	public void batchInsertImg(List<Map<String, Object>> list) {
		sgtongBusinfoMapper.batchInsertImg(list);
		
	}

	@Override
	public void stgbatchDeleteImg(Map<String, Object> map) {
		  map.put("isDelete",1);
		  sgtongBusinfoMapper.update(map);
	}

}
