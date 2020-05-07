package com.anjbo.monitor.entity;

import java.util.Date;

public class MonitorArchiveDto extends BasePager
{
  private Integer id;
  private String createUid;
  private Date createTime;
  private String createTimeStr;
  private String updateUid;
  private Date updateTime;
  private Integer type;
  private Integer estateType;
  private String estateTypeStr;
  private String yearNo;
  private String estateNo;
  private String identityNo;
  private String startTime;
  private String endTime;
  private String startTimeStr;
  private String endTimeStr;
  private Integer queryFrequency;
  private String phone;
  private String archiveId;
  private String message;
  private String propertyStatus;
  private String changeRecord;
  private String sign;
  private String agencyId;
  private String SectionTime;
  private String name;

  public Integer getId()
  {
    return this.id;
  }
  public void setId(Integer id) {
    this.id = id;
  }
  public String getCreateUid() {
    return this.createUid;
  }
  public void setCreateUid(String createUid) {
    this.createUid = createUid;
  }
  public Date getCreateTime() {
    return this.createTime;
  }
  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }
  public String getUpdateUid() {
    return this.updateUid;
  }
  public void setUpdateUid(String updateUid) {
    this.updateUid = updateUid;
  }
  public Date getUpdateTime() {
    return this.updateTime;
  }
  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }
  public Integer getType() {
    return this.type;
  }
  public void setType(Integer type) {
    this.type = type;
  }
  public Integer getEstateType() {
    return this.estateType;
  }
  public void setEstateType(Integer estateType) {
    this.estateType = estateType;
  }
  public String getYearNo() {
    return this.yearNo;
  }
  public void setYearNo(String yearNo) {
    this.yearNo = yearNo;
  }
  public String getEstateNo() {
    return this.estateNo;
  }
  public void setEstateNo(String estateNo) {
    this.estateNo = estateNo;
  }
  public String getIdentityNo() {
    return this.identityNo;
  }
  public void setIdentityNo(String identityNo) {
    this.identityNo = identityNo;
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
  public Integer getQueryFrequency() {
    return this.queryFrequency;
  }
  public void setQueryFrequency(Integer queryFrequency) {
    this.queryFrequency = queryFrequency;
  }
  public String getPhone() {
    return this.phone;
  }
  public void setPhone(String phone) {
    this.phone = phone;
  }
  public String getArchiveId() {
    return this.archiveId;
  }
  public void setArchiveId(String archiveId) {
    this.archiveId = archiveId;
  }
  public String getMessage() {
    return this.message;
  }
  public void setMessage(String message) {
    this.message = message;
  }
  public String getPropertyStatus() {
    return this.propertyStatus;
  }
  public void setPropertyStatus(String propertyStatus) {
    this.propertyStatus = propertyStatus;
  }
  public String getChangeRecord() {
    return this.changeRecord;
  }
  public void setChangeRecord(String changeRecord) {
    this.changeRecord = changeRecord;
  }
  public String getName() {
    return this.name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getSign() {
    return this.sign;
  }
  public void setSign(String sign) {
    this.sign = sign;
  }
  public String getAgencyId() {
    return this.agencyId;
  }
  public void setAgencyId(String agencyId) {
    this.agencyId = agencyId;
  }
  public String getSectionTime() {
    return this.SectionTime;
  }
  public void setSectionTime(String sectionTime) {
    this.SectionTime = sectionTime;
  }
  public String getEstateTypeStr() {
    return this.estateTypeStr;
  }
  public void setEstateTypeStr(String estateTypeStr) {
    this.estateTypeStr = estateTypeStr;
  }
  public String getEndTimeStr() {
    return this.endTimeStr;
  }
  public void setEndTimeStr(String endTimeStr) {
    this.endTimeStr = endTimeStr;
  }
  public String getStartTimeStr() {
    return this.startTimeStr;
  }
  public void setStartTimeStr(String startTimeStr) {
    this.startTimeStr = startTimeStr;
  }
  public String getCreateTimeStr() {
    return this.createTimeStr;
  }
  public void setCreateTimeStr(String createTimeStr) {
    this.createTimeStr = createTimeStr;
  }
}