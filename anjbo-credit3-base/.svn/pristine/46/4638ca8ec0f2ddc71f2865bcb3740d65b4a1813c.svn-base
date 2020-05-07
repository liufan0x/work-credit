package com.anjbo.service.impl;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.anjbo.dao.BaseMapper;
import com.anjbo.service.BaseService;

public class BaseServiceImpl<T> implements BaseService<T> {
	public Log logger = LogFactory.getLog(this.getClass());
	@Autowired BaseMapper<T> baseMapper;

	@Override
	public List<T> search(T dto) {
		return baseMapper.search(dto);
	}

	@Override
	public T find(T dto) {
		return baseMapper.find(dto);
	}
	
	@Override
	public int count(T dto) {
		return baseMapper.count(dto);
	}

	@Override
	public T insert(T dto) {
		baseMapper.insert(dto);
		return dto;
	}

	@Override
	public int delete(T dto) {
		return baseMapper.delete(dto);
	}

	@Override
	public int update(T dto) {
		return baseMapper.update(dto);
	}

	@Override
	public int batchInsert(List<T> list) {
		return baseMapper.batchInsert(list);
	}

	@Override
	public int batchDelete(List<T> list) {
		return baseMapper.batchDelete(list);
	}

	@Override
	public int batchUpdate(List<T> list) {
		return baseMapper.batchUpdate(list);
	}

}