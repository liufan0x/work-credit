package com.anjbo.service.cm.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anjbo.common.Enums;
import com.anjbo.dao.cm.ProgressMapper;
import com.anjbo.service.cm.ProgressService;

/**
 * 实现
 * @author chenzm    
 * @date 2017-08-24
 */

@Service
public class ProgressServiceImpl implements ProgressService {
	
	@Resource private ProgressMapper progressMapper;


	@Override
	public int addOrderProgressFlow(String orderNo, int progressNo) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("orderNo", orderNo);
		param.put("progressNo",progressNo);
		param.put("progressName", Enums.CMOrderProgressEnum.getNameByCode(progressNo));
		int r = progressMapper.addOrderProgressFlow(param);
		return r;
	}


	

}
