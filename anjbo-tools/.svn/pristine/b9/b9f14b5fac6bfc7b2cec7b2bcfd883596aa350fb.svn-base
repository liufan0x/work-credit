package com.anjbo.service.mort.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;

import com.anjbo.dao.mort.DictMapper;
import com.anjbo.service.mort.DictService;

/**
 * 用户信息
 * @author limh limh@zxsf360.com
 * @date 2016-6-1 下午03:16:49
 */

@Service
public class DictServiceImpl implements DictService {

	@Resource
	private DictMapper dictMapper;

	@Override
	public Map<String, String> getDictMap(String type) {
		Map<String, String> dictMap = new LinkedHashMap<String, String>();
		List<Map<String,Object>> list = dictMapper.selectDict(type);
		for (Map<String, Object> map : list) {
			String code = map.get("code").toString();
			String name = map.get("name").toString();
			dictMap.put(code,name);
		}
		return dictMap;
	}

	@Override
	public int updateDict(Map<String, Object> param) {
		return dictMapper.updateDict(param);
	}

	@Override
	public int addDict(Map<String, Object> param) {
		return dictMapper.addDict(param);
	}

	@Override
	public int getMaxSort(String type) {
		List<Map<String, Object>> list = dictMapper.selectDict(type);
		if(list.size()>0){
			return MapUtils.getIntValue(list.get(list.size()-1),"sort");
		}
		return 0;
	}
	
}
