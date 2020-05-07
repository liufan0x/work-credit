package com.anjbo.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.anjbo.bean.risk.MonitorArchiveDto;

@Service
public interface MonitorArchiveService {
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
	
	public Map<String, Object> getArchiveId(Map<String, Object> params,MonitorArchiveDto monitorDao) ;
	
	/**
	 * 修改监控任务
	 * @param monitorArchiveDto
	 * @return
	 */
	public int updateMonitorArchive(MonitorArchiveDto monitorArchiveDto);
}
