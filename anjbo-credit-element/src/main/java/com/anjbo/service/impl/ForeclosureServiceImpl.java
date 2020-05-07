package com.anjbo.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anjbo.dao.ForeclosureTypeMapper;
import com.anjbo.service.ForeclosureTypeService;
@Service
public class ForeclosureServiceImpl implements ForeclosureTypeService {

	@Resource
	private ForeclosureTypeMapper foreclosureTypeMapper;
	@Override
	public Map<String, Object> selectRiskElement(Map<String, Object> param) {
		return foreclosureTypeMapper.selectRiskElement(param);
	}

}
