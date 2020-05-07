package com.anjbo.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;

import com.anjbo.dao.OrderContractTypeMapper;
import com.anjbo.service.OrderContractTypeService;

@Service
public class OrderContractTypeServiceImpl implements OrderContractTypeService {

	@Resource
	private OrderContractTypeMapper orderContractTypeMapper;
	
	@Override
	public List<Map<String, Object>> getParentContractTypes() {
		return orderContractTypeMapper.getParentContractTypes();
	}

	@Override
	public List<Map<String, Object>> getSonContractTypes() {
		return orderContractTypeMapper.getSonContractTypes();
	}

	@Override
	public Map<String, Object> list() {
		Map<String,Object> map = new HashMap<String, Object>();
		List<Map<String,Object>> parentTypes = getParentContractTypes(); 
		List<Map<String,Object>> sonList = getSonContractTypes(); 
		List<Map<String, Object>> sonTypes = new ArrayList<Map<String,Object>>();
		for (Map<String, Object> parentType : parentTypes) {
			int ppid = MapUtils.getIntValue(parentType, "id");
			sonTypes = new ArrayList<Map<String,Object>>();
			for (Map<String, Object> sonTypesTemp : sonList) {
				int pid = MapUtils.getIntValue(sonTypesTemp, "pid");
				if(ppid==pid){
					sonTypes.add(sonTypesTemp);
				}
			}
			parentType.put("sonTypes", sonTypes);
		}
		map.put("parentTypes", parentTypes);
		return map;
	}

}
