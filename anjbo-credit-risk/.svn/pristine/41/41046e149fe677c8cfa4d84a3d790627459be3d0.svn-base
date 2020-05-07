package com.anjbo.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anjbo.bean.risk.LawsuitSzDto;
import com.anjbo.dao.LawsuitSzMapper;
import com.anjbo.service.LawsuitSzService;


@Transactional
@Service
public class LawsuitSzServiceImpl implements LawsuitSzService {
	@Resource
	private LawsuitSzMapper lawsuitSzMapper;

	@Override
	public int deleteByPrimaryKey(Integer id) {
		return lawsuitSzMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(LawsuitSzDto record) {
		return lawsuitSzMapper.insert(record);
	}

	@Override
	public int insertSelective(LawsuitSzDto record) {
		return lawsuitSzMapper.insertSelective(record);
	}

	@Override
	public LawsuitSzDto selectByPrimaryKey(Integer id) {
		return lawsuitSzMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(LawsuitSzDto record) {
		return lawsuitSzMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(LawsuitSzDto record) {
		return lawsuitSzMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<Map<String, Object>> getListMap(Map<String, Object> map) {
		return lawsuitSzMapper.getListMap(map);
	}

	@Override
	public int deleteByMap(Map<String, Object> map) {
		return lawsuitSzMapper.deleteByMap(map);
	}
	
}
