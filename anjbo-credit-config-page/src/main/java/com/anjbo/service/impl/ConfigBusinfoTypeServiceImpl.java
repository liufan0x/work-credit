package com.anjbo.service.impl;

import com.anjbo.dao.ConfigBusinfoTypeMapper;
import com.anjbo.service.ConfigBusinfoTypeService;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class ConfigBusinfoTypeServiceImpl implements ConfigBusinfoTypeService {

	@Resource
	private ConfigBusinfoTypeMapper configBusinfoTypeMapper; 
	
	@Override
	public List<Map<String,Object>> selectBusinfoParentType(Map<String, Object> map) {
		return configBusinfoTypeMapper.selectBusinfoParentType(map);
	}

	@Override
	public List<Map<String,Object>> selectBusinfoSonType(Map<String, Object> map) {
		return configBusinfoTypeMapper.selectBusinfoSonType(map);
	}

	/**
	 * 根据productCode查询影像资料类型
	 */
	@Override
	public Map<String, Object> selectBusinfoInit(Map<String, Object> map) {
		Map<String, Object> data = new HashMap<String, Object>();
		List<Map<String,Object>> parentTypeList = configBusinfoTypeMapper.selectBusinfoParentType(map);
		List<Map<String,Object>> sonTypeList = configBusinfoTypeMapper.selectBusinfoSonType(map);
		List<Map<String,Object>> sonTypeListTemp = new ArrayList<Map<String,Object>>();
		String tblName = MapUtils.getString(map, "tblName");
		String tblDataName = tblName.substring(0, tblName.indexOf("_",5))+"_businfo";
		map.put("tblDataName", tblDataName);
		int id;
		int pid;
		for(Map<String,Object> m:parentTypeList){
			id=new Integer(m.get("id").toString());
			sonTypeListTemp = new ArrayList<Map<String,Object>>();
			for(Map<String,Object> n:sonTypeList){
				pid = new Integer(n.get("pid").toString());
				if(pid==id){
					sonTypeListTemp.add(n);
				}
			}
			m.put("sonTypeList", sonTypeListTemp);
		}
		data.put("parentTypeList", parentTypeList);
		return data;
	}

	@Override
	public Map<String, Object> getAllType(Map<String, Object> map) {
		List<Map<String, Object>> list = configBusinfoTypeMapper.getAllType(map);
		Map<String, Object> listMap = new HashMap<String, Object>();
		listMap.put("typeList", list);
		return listMap;
	}
	/**
	 * 查询必备影像资料
	 * @param map(key=productCode:产品code)
	 * @return true(校验通过),false(校验失败)
	 */
	public List<Map<String,Object>> selectNecessaryBusinfo(Map<String,Object> map){
		List<Map<String,Object>> listType = configBusinfoTypeMapper.selectNecessaryBusinfo(map);
		return listType;
	}
}
