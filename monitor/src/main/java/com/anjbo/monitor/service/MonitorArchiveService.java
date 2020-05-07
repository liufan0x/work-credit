package com.anjbo.monitor.service;

import com.anjbo.monitor.entity.MonitorArchiveDto;
import java.util.List;

public abstract interface MonitorArchiveService
{
  public abstract int insertMonitorArchive(MonitorArchiveDto paramMonitorArchiveDto);

  public abstract List<MonitorArchiveDto> selectArchiveListAll(MonitorArchiveDto paramMonitorArchiveDto);

  public abstract List<MonitorArchiveDto> selectArchiveList(MonitorArchiveDto paramMonitorArchiveDto);

  public abstract MonitorArchiveDto selectArchiveByArchiveId(MonitorArchiveDto paramMonitorArchiveDto);

  public abstract int updateMonitorArchive(MonitorArchiveDto paramMonitorArchiveDto);

  public abstract MonitorArchiveDto selectArchiveByIdentityNo(MonitorArchiveDto paramMonitorArchiveDto);

  public abstract int selectArchiveCount(MonitorArchiveDto paramMonitorArchiveDto);

  public abstract int deleteMonitorArchiveById(String paramString);
}