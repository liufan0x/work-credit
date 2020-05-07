package com.anjbo.service.tools.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anjbo.bean.tools.DegreeBADto;
import com.anjbo.dao.mort.DegreeBAMapper;
import com.anjbo.service.tools.DegreeBAService;
@Service
public class DegreeBAServiceImpl implements DegreeBAService{
	@Resource
	private DegreeBAMapper degreeBAMapper;
	@Override
	public List<DegreeBADto> queryBaoAnDegree(DegreeBADto begreeBADto) {
		return degreeBAMapper.queryBaoAnDegree(begreeBADto);
	}

	@Override
	public List<DegreeBADto> queryPropertyRoomList(DegreeBADto begreeBADto) {
		return degreeBAMapper.queryPropertyRoomList(begreeBADto);
	}

}
