package com.anjbo.common;

public enum RespStatusEnum {
	SUCCESS("SUCCESS","操作成功"),
	FAIL("FAIL","操作失败，请稍后再试"),
	LOGIN_OVER_TIME("LOGIN_OVER_TIME","登录超时"),
	VERIFYCODE_ERROR("VERIFYCODE_ERROR","验证码不正确"),
	ACCOUNT_NO_FIND("ACCOUNT_NO_FIND","账号不存在"),
	PASSWORD_ERROR("PASSWORD_ERROR","密码错误"),
	LOGIN_PLATFORM_CASE("LOGIN_PLATFORM_CASE","当前登录系统与您所在的机构不匹配"),
	LOGIN_ENABLE_ORG("LOGIN_ENABLE_ORG","该账号已被冻结，如果问题，请联系快鸽管理员."),
	LOGIN_ENABLE("LOGIN_ENABLE","您的账号已被冻结，暂时无法登陆，如有问题，请联系管理员"),
	LOGIN_ENABLE_INDATE("LOGIN_ENABLE_INDATE","您的账号已超过使用期，请联系管理员"),
	PARAMETER_ERROR("PARAMETER_ERROR","参数异常"),
	NOWITHDRAW_ERROR("NOWITHDRAW_ERROR","该订单已经被处理，您无法操作撤回"),
	NOADOPT_ERROR("NOADOPT_ERROR","该订单已被撤回，您无法操作下一步"),
	REQUEST_TIMEOUT("REQUEST_TIMEOUT","请求超时,请稍后重试"),
	SMS_DAY_THREE("SMS_DAY_THREE","一天只能发送3次短信"),
	SMS_MONTH_FIVE("SMS_MONTH_FIVE","一个月只能发送5次短信"),
	SYSTEM_ERROR("SYSTEM_ERROR","系统内部错误，请重试!"),
	THIRD_ERROR("THIRD_ERROR","请求第三方接口%s异常，请联系管理员"),
	THIRD_PARAM_ERROR("THIRD_PARAM_ERROR","调用第三方接口%s失败，请检查输入信息"),
	THIRD_SERVER_ERROR("THIRD_SERVER_ERROR","第三方服务器异常，请稍后再试"),
	MOBILE_REPEAT("MOBILE_REPEAT", "手机号重复"),
	/**未找到*/AGENCY_UNFIND("AGENCY_UNFIND", "未找到匹配的机构信息"),
	/**未启用*/AGENCY_UNENABLED("AGENCY_UNENABLED", "当前机构未启用"),
	/**未签约*/AGENCY_UNSIGN("AGENCY_UNSIGN", "当前机构未正式签约"),
	APPLYLOAN_SUCCESS("APPLYLOAN_SUCCESS","业务合作正常开展中，为避免余额不足影响业务合作，请及时补充保证金"),
	APPLYLOAN_FAIL("APPLYLOAN_FAIL","由于贷款余额＞授信额度，业务合作已暂停，请及时补充保证金。");
	private String code;
	private String msg;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	private RespStatusEnum(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
}
