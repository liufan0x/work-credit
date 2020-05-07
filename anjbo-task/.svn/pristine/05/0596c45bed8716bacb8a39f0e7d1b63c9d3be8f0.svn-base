package com.anjbo.dao.fc.monitor;

import java.util.List;

import com.anjbo.bean.fc.monitor.MonitorArchiveDto;



public interface MonitorArchiveMapper {
	/**
	 * 更新房产监控记录
	 * @param monitorArchiveDto
	 * @return
	 */
	int updateMonitorArchive(MonitorArchiveDto monitorArchiveDto);
	/**
	 * 查询所有房产监控数据
	 * @param archiveDto
	 * @return
	 */
	public List<MonitorArchiveDto> selectArchiveList(MonitorArchiveDto archiveDto);
	/**
	 * 查询前一次查档产权状态
	 * @param archiveDto
	 * @return
	 */
	public MonitorArchiveDto selectArchiveByArchiveId(MonitorArchiveDto archiveDto);
}
