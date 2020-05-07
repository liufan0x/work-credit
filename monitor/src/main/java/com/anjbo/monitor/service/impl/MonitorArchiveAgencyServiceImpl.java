package com.anjbo.monitor.service.impl;

import com.anjbo.monitor.dao.MonitorArchiveAgencyMapper;
import com.anjbo.monitor.entity.MonitorArchiveAgencyDto;
import com.anjbo.monitor.service.MonitorArchiveAgencyService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MonitorArchiveAgencyServiceImpl
  implements MonitorArchiveAgencyService
{

  @Autowired
  private MonitorArchiveAgencyMapper monitorArchiveAgencyMapper;

  public List<MonitorArchiveAgencyDto> selectAll()
  {
    return this.monitorArchiveAgencyMapper.selectAll();
  }
}