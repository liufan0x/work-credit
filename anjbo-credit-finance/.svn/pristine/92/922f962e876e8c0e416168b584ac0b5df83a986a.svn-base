package com.anjbo.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anjbo.dao.EgMapper;
import com.anjbo.service.EgService;

@Service
public class EgServiceImpl implements EgService {
	
	@Resource
	private EgMapper egMapper;
	
	@Override
	public List<Map<String, Object>> test() {
		return egMapper.test();
	}
	
}
