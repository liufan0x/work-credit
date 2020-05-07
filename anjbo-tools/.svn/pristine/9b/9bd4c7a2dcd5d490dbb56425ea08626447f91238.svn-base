package com.anjbo.service.tools.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anjbo.bean.tools.DegreeLockRecordDto;
import com.anjbo.dao.mort.DegreeLockRecordMapper;
import com.anjbo.service.tools.DegreeLockRecordService;
@Service
public class DegreeLockRecordServiceImpl implements DegreeLockRecordService{
	@Resource
	private DegreeLockRecordMapper degreeLockRecordMapper;
	@Override
	public int insertLockRecord(DegreeLockRecordDto degreeLockRecordDto) {
		return degreeLockRecordMapper.insertLockRecord(degreeLockRecordDto);
	}

	@Override
	public List<DegreeLockRecordDto> findLockRecord(
			DegreeLockRecordDto degreeLockRecordDto) {
		return degreeLockRecordMapper.findLockRecord(degreeLockRecordDto);
	}

	@Override
	public int findLockRecordCount(DegreeLockRecordDto degreeLockRecordDto) {
		return degreeLockRecordMapper.findLockRecordCount(degreeLockRecordDto);
	}

}
