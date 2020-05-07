package com.anjbo.service.fc.monitor.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anjbo.bean.fc.monitor.MonitorArchiveDto;
import com.anjbo.dao.fc.monitor.MonitorArchiveMapper;
import com.anjbo.service.fc.monitor.MonitorArchiveService;
@Service("monitorArchiveService")
@Transactional
public class MonitorArchiveServiceImpl implements MonitorArchiveService{
	Logger log = Logger.getLogger(MonitorArchiveServiceImpl.class);
	@Resource
	private MonitorArchiveMapper monitorArchiveMapper;
	@Override
	public int updateMonitorArchive(MonitorArchiveDto monitorArchiveDto) {
		return monitorArchiveMapper.updateMonitorArchive(monitorArchiveDto);
	}
	@Override
	public List<MonitorArchiveDto> selectArchiveList(
			MonitorArchiveDto archiveDto) {
		return monitorArchiveMapper.selectArchiveList(archiveDto);
	}
	@Override
	public MonitorArchiveDto selectArchiveByArchiveId(
			MonitorArchiveDto archiveDto) {
		return monitorArchiveMapper.selectArchiveByArchiveId(archiveDto);
	} 

}
