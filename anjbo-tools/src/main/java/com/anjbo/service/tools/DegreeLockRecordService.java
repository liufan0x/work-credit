package com.anjbo.service.tools;

import java.util.List;

import com.anjbo.bean.tools.DegreeLockRecordDto;

public interface DegreeLockRecordService {
	/**
	 * 录入学位锁定查询记录
	 * @param degreeLockRecordDto
	 * @return
	 */
	int insertLockRecord(DegreeLockRecordDto degreeLockRecordDto);
	/**
	 * 学位锁定查询记录
	 * @param degreeLockRecordDto
	 * @return
	 */
	List<DegreeLockRecordDto> findLockRecord(DegreeLockRecordDto degreeLockRecordDto);
	/**
	 * 学位锁定查询记录总数
	 * @param degreeLockRecordDto
	 * @return
	 */
	int findLockRecordCount(DegreeLockRecordDto degreeLockRecordDto);
}
