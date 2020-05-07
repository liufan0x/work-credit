package com.anjbo.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anjbo.bean.risk.LawsuitCnDto;
import com.anjbo.dao.LawsuitCnMapper;
import com.anjbo.service.LawsuitCnService;

@Transactional
@Service
public class LawsuitCnServiceImpl implements LawsuitCnService {
	@Resource
	private LawsuitCnMapper lawsuitCnMapper;
	@Override
	public int deleteByPrimaryKey(Integer id) {
		return lawsuitCnMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(LawsuitCnDto record) {
		return lawsuitCnMapper.insert(record);
	}

	@Override
	public int insertSelective(LawsuitCnDto record) {
		return lawsuitCnMapper.insertSelective(record);
	}

	@Override
	public LawsuitCnDto selectByPrimaryKey(Integer id) {
		return lawsuitCnMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(LawsuitCnDto record) {
		return lawsuitCnMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(LawsuitCnDto record) {
		return lawsuitCnMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<Map<String, Object>> getListMap(Map<String, Object> map) {
		return lawsuitCnMapper.getListMap(map);
	}

	@Override
	public int deleteByMap(Map<String, Object> map) {
		return lawsuitCnMapper.deleteByMap(map);
	}
}
