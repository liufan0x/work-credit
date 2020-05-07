package com.anjbo.monitor.entity;

public class BaseDto extends BasePager
{
  protected String sid;
  protected String device;
  protected String deviceId;
  protected String version = "task-1.0";
  protected String startTime;
  protected String endTime;
  private String key;

  public String getKey()
  {
    return this.key;
  }
  public void setKey(String key) {
    this.key = key;
  }
  public String getVersion() {
    return this.version;
  }
  public void setVersion(String version) {
    this.version = version;
  }
  public String getSid() {
    return this.sid;
  }
  public void setSid(String sid) {
    this.sid = sid;
  }
  public String getDevice() {
    return this.device;
  }
  public void setDevice(String device) {
    this.device = device;
  }
  public String getDeviceId() {
    return this.deviceId;
  }
  public void setDeviceId(String deviceId) {
    this.deviceId = deviceId;
  }
  public String getStartTime() {
    return this.startTime;
  }
  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }
  public String getEndTime() {
    return this.endTime;
  }
  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }
}