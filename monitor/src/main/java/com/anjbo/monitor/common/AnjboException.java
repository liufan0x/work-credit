package com.anjbo.monitor.common;

public class AnjboException extends Exception
{
  private static final long serialVersionUID = -3720258570036335132L;
  private String code;
  private String sysMsg;
  private String msg;

  public AnjboException(String code, String msg)
  {
    this.code = code;
    this.msg = msg;
  }

  public AnjboException(String code, String sysMsg, String msg)
  {
    super(sysMsg);
    this.code = code;
    this.msg = msg;
  }

  public AnjboException(String code, String sysMsg, String msg, Exception e)
  {
    super(sysMsg, e);
    this.code = code;
    this.msg = msg;
  }

  public String getCode()
  {
    return this.code;
  }

  public String getSysMsg()
  {
    return this.sysMsg;
  }

  public String getMsg()
  {
    return this.msg;
  }
}