package com.anjbo.monitor.service;

import com.anjbo.monitor.entity.MonitorArchiveAgencyDto;
import java.util.List;

public abstract interface MonitorArchiveAgencyService
{
  public abstract List<MonitorArchiveAgencyDto> selectAll();
}