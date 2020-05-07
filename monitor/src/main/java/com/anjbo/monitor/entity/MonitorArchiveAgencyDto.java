package com.anjbo.monitor.entity;

public class MonitorArchiveAgencyDto extends BasePager
{
  private Integer id;
  private String agencyId;
  private String name;

  public Integer getId()
  {
    return this.id;
  }
  public void setId(Integer id) {
    this.id = id;
  }
  public String getAgencyId() {
    return this.agencyId;
  }
  public void setAgencyId(String agencyId) {
    this.agencyId = agencyId;
  }
  public String getName() {
    return this.name;
  }
  public void setName(String name) {
    this.name = name;
  }
}