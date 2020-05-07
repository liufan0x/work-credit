package com.anjbo.monitor.common;

public enum RespStatusEnum
{
  SUCCESS("SUCCESS", "操作成功"), 
  FAIL("FAIL", "操作失败，请稍后再试"), 
  LOGIN_OVER_TIME("LOGIN_OVER_TIME", "登录超时"), 
  VERIFYCODE_ERROR("VERIFYCODE_ERROR", "验证码不正确"), 
  ACCOUNT_NO_FIND("ACCOUNT_NO_FIND", "账号不存在"), 
  PASSWORD_ERROR("PASSWORD_ERROR", "密码错误"), 
  PARAMETER_ERROR("PARAMETER_ERROR", "参数异常"), 
  REQUEST_TIMEOUT("REQUEST_TIMEOUT", "请求超时,请稍后重试"), 
  SMS_DAY_THREE("SMS_DAY_THREE", "一天只能发送3次短信"), 
  SMS_MONTH_FIVE("SMS_MONTH_FIVE", "一个月只能发送5次短信"), 
  SYSTEM_ERROR("SYSTEM_ERROR", "系统内部错误，请重试!"), 
  THIRD_ERROR("THIRD_ERROR", "请求第三方接口%s异常，请联系管理员"), 
  THIRD_PARAM_ERROR("THIRD_PARAM_ERROR", "调用第三方接口%s失败，请检查输入信息");

  private String code;
  private String msg;

  public String getCode() { return this.code; }

  public void setCode(String code) {
    this.code = code;
  }
  public String getMsg() {
    return this.msg;
  }
  public void setMsg(String msg) {
    this.msg = msg;
  }
  private RespStatusEnum(String code, String msg) {
    this.code = code;
    this.msg = msg;
  }
}