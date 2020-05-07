package com.anjbo.monitor.common;

import java.io.Serializable;

public class RespStatus
  implements Serializable
{
  private static final long serialVersionUID = 5852117168069614127L;
  private String msg;
  private String code;

  public RespStatus()
  {
  }

  public RespStatus(String code, String msg)
  {
    this.code = code;
    this.msg = msg;
  }

  public String getMsg() {
    return this.msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public String getCode() {
    return this.code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String toString()
  {
    return "RespStatus [msg=" + this.msg + ", code=" + this.code + "]";
  }
}