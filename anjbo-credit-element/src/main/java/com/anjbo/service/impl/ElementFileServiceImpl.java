package com.anjbo.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anjbo.dao.ElementFileMapper;
import com.anjbo.service.ElementFileService;

@Service
public class ElementFileServiceImpl implements ElementFileService{
	@Resource
	private ElementFileMapper elementFileMapper;

	@Override
	public int updateElementFile(List<Map<String, Object>> list) {
		return elementFileMapper.updateElementFile(list);
	}

	@Override
	public void updateStatusByIds(Map<String, Object> param) {
		elementFileMapper.updateStatusByIds(param);
	}

}
