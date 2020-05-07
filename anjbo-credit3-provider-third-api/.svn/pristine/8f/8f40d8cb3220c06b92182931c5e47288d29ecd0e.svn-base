package com.anjbo.service.lineparty.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anjbo.bean.lineparty.PlatformDto;
import com.anjbo.dao.lineparty.PlatformMapper;
import com.anjbo.service.lineparty.PlatformService;

@Service
public class PlatformServiceImpl implements PlatformService{
    @Resource
    private PlatformMapper mapper;
	@Override
	public PlatformDto selectOne(String idCardNumber) {
		// TODO Auto-generated method stub
		return mapper.selectOne(idCardNumber);
	}
	@Override
	public int insertOne(PlatformDto platformDto) {
		// TODO Auto-generated method stub
		return mapper.insertOne(platformDto);
	}

}
