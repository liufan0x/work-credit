package com.anjbo.dao;

import java.util.List;

import com.anjbo.bean.risk.MonitorArchiveDto;


public interface MonitorArchiveMapper {
	/**
	 * 录入查档信息
	 * @param monitorArchiveDto
	 * @return
	 */
	int insertMonitorArchive(MonitorArchiveDto monitorArchiveDto);
	/**
	 * 删除房产监控
	 * @param monitorArchiveDto
	 * @return
	 */
	int deleteMonitorArchiveById(MonitorArchiveDto monitorArchiveDto);
	/**
	 * 查询列表
	 * @param archiveDto
	 * @return
	 */
	public List<MonitorArchiveDto> selectArchiveList(MonitorArchiveDto archiveDto);
	
	public int selectArchiveCount(MonitorArchiveDto archiveDto);
	
	/**
	 * 查询信息
	 * @param archiveDto
	 * @return
	 */
	public MonitorArchiveDto findBymonitor(MonitorArchiveDto archiveDto);
	/**
	 * 修改监控任务
	 * @param monitorArchiveDto
	 * @return
	 */
	public int updateMonitorArchive(MonitorArchiveDto monitorArchiveDto);
	/**
	 * 根据查档id查询监控
	 * @param monitorArchiveDto
	 * @return
	 */
	public MonitorArchiveDto selectArchiveByArchiveId(MonitorArchiveDto monitorArchiveDto);
}
