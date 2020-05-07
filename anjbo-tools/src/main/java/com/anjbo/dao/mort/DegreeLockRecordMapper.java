package com.anjbo.dao.mort;

import java.util.List;

import com.anjbo.bean.tools.DegreeLockRecordDto;

public interface DegreeLockRecordMapper {
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
	 * 
	 * 查询学位锁定查询记录总数
	 * @user Administrator
	 * @date 2018年3月23日 上午9:26:01 
	 * @param degreeLockRecordDto
	 * @return
	 */
	int findLockRecordCount(DegreeLockRecordDto degreeLockRecordDto);
}
