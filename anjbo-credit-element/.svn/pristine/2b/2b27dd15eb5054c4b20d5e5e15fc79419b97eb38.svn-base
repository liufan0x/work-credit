package com.anjbo.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anjbo.dao.BoxBaseMapper;
import com.anjbo.dao.XAuditBaseMapper;
import com.anjbo.service.BoxBaseService;
import com.anjbo.service.BoxBaseWebService;
@Service
public class BoxBaseServiceImpl implements BoxBaseService{
	@Resource
	private BoxBaseMapper boxBaseMapper;

	@Override
	//分配箱子
	public Map<String, Object> randomBox(Map<String, Object> params) {
		
		List<Map<String, Object>> box_list=boxBaseMapper.selectBoxBaseList(params);
		
		if(box_list==null||box_list.size()==0) {
			return null;//若为空表示箱子已满
		}
		
		//int num=(int) (Math.random()*(box_list.size()-1));  
		
		Map<String, Object> box_map=box_list.get(0);
		
		return box_map;
	}

	@Override
	public List<Map<String, Object>> selectBoxBaseByBoxNo(Map<String, Object> params) {
		
		return boxBaseMapper.selectBoxBaseByBoxNo(params);
	}

	@Override
	public Map<String, Object> selectBoxByOperationAuthority(Map<String, Object> map) {
		return boxBaseMapper.selectBoxByOperationAuthority(map);
	}

	@Override
	public Map<String, Object> selectBoxBaseByOrderNo(Map<String, Object> map) {
		return boxBaseMapper.selectBoxBaseByOrderNo(map);
	}

}
