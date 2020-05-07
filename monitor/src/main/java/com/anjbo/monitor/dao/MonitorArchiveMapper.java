package com.anjbo.monitor.dao;

import com.anjbo.monitor.entity.MonitorArchiveDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public abstract interface MonitorArchiveMapper
{
  public abstract int insertMonitorArchive(MonitorArchiveDto paramMonitorArchiveDto);

  public abstract int deleteMonitorArchiveById(@Param("ids") String paramString);

  public abstract List<MonitorArchiveDto> selectArchiveListAll(MonitorArchiveDto paramMonitorArchiveDto);

  public abstract List<MonitorArchiveDto> selectArchiveList(MonitorArchiveDto paramMonitorArchiveDto);

  public abstract int selectArchiveCount(MonitorArchiveDto paramMonitorArchiveDto);

  public abstract MonitorArchiveDto findBymonitor(MonitorArchiveDto paramMonitorArchiveDto);

  public abstract int updateMonitorArchive(MonitorArchiveDto paramMonitorArchiveDto);

  public abstract MonitorArchiveDto selectArchiveByArchiveId(MonitorArchiveDto paramMonitorArchiveDto);

  public abstract MonitorArchiveDto selectArchiveByIdentityNo(MonitorArchiveDto paramMonitorArchiveDto);
}