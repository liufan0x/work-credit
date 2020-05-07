package com.anjbo.monitor.dao;

import com.anjbo.monitor.entity.MonitorArchiveAgencyDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public abstract interface MonitorArchiveAgencyMapper
{
  public abstract List<MonitorArchiveAgencyDto> selectAll();
}