package com.anjbo.monitor.service.impl;

import com.anjbo.monitor.dao.MonitorArchiveMapper;
import com.anjbo.monitor.entity.MonitorArchiveDto;
import com.anjbo.monitor.service.MonitorArchiveService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MonitorArchiveServiceImpl
  implements MonitorArchiveService
{

  @Autowired
  private MonitorArchiveMapper monitorArchiveMapper;

  public int insertMonitorArchive(MonitorArchiveDto monitorArchiveDto)
  {
    return this.monitorArchiveMapper.insertMonitorArchive(monitorArchiveDto);
  }

  public List<MonitorArchiveDto> selectArchiveList(MonitorArchiveDto monitorArchiveDto)
  {
    return this.monitorArchiveMapper.selectArchiveList(monitorArchiveDto);
  }

  public MonitorArchiveDto selectArchiveByArchiveId(MonitorArchiveDto monitorArchiveDto)
  {
    return this.monitorArchiveMapper.selectArchiveByArchiveId(monitorArchiveDto);
  }

  public int updateMonitorArchive(MonitorArchiveDto monitorArchiveDto)
  {
    return this.monitorArchiveMapper.updateMonitorArchive(monitorArchiveDto);
  }

  public MonitorArchiveDto selectArchiveByIdentityNo(MonitorArchiveDto monitorArchiveDto)
  {
    return this.monitorArchiveMapper.selectArchiveByIdentityNo(monitorArchiveDto);
  }

  public int selectArchiveCount(MonitorArchiveDto archiveDto)
  {
    return this.monitorArchiveMapper.selectArchiveCount(archiveDto);
  }

  public int deleteMonitorArchiveById(String ids)
  {
    return this.monitorArchiveMapper.deleteMonitorArchiveById(ids);
  }

  public List<MonitorArchiveDto> selectArchiveListAll(MonitorArchiveDto archiveDto)
  {
    return this.monitorArchiveMapper.selectArchiveListAll(archiveDto);
  }
}