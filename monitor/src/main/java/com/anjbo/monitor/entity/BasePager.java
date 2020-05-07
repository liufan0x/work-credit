package com.anjbo.monitor.entity;

import java.io.Serializable;

public class BasePager
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private int offset;
  private int limit;
  private int nowPage;
  private String sortName;
  private String sortOrder;

  public int getOffset()
  {
    return this.offset;
  }

  public void setOffset(int offset) {
    this.offset = offset;
  }

  public int getLimit() {
    return this.limit;
  }

  public void setLimit(int limit) {
    this.limit = limit;
  }

  public int getNowPage() {
    return this.nowPage;
  }

  public void setNowPage(int nowPage) {
    this.nowPage = nowPage;
  }

  public String getSortName() {
    return this.sortName;
  }

  public void setSortName(String sortName) {
    this.sortName = sortName;
  }

  public String getSortOrder() {
    return this.sortOrder;
  }

  public void setSortOrder(String sortOrder) {
    this.sortOrder = sortOrder;
  }

  public static long getSerialversionuid() {
    return 1L;
  }
}