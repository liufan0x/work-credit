package com.anjbo.common.push.umeng;

public class ReqIosUMDto {
	private String imSecret;// ios密码
	private String amSecret;// android密码
	private String tokens;// 发送的用户
	private String msg;// 发送的消息
	private String title;
	private String code;// 打开的url
	private String params;// 消息中需要打开链接的参数

	public ReqIosUMDto() {
	}

	public ReqIosUMDto(String title, String msg) {
		this.msg = msg;
		this.title = title;
	}
	/**
	 * 
	 * @param title
	 *            推送标题
	 * @param msg
	 *            推送消息
	 * @param tokens
	 *            推送人
	 * @param url
	 *            推送消息打开url
	 */
	public ReqIosUMDto(String title, String msg, String tokens, String code,String params) {
		this.setImSecret(UMengConst.IOS_AMS);
		this.setAmSecret(UMengConst.ANDROID_AMS);
		this.tokens = tokens;
		this.msg = msg;
		this.title = title;
		this.code = code;
		this.params = params;
	}

	public String getImSecret() {
		return imSecret;
	}

	public void setImSecret(String imSecret) {
		this.imSecret = imSecret;
	}

	public String getAmSecret() {
		return amSecret;
	}

	public void setAmSecret(String amSecret) {
		this.amSecret = amSecret;
	}

	public String getTokens() {
		return tokens;
	}

	public void setTokens(String tokens) {
		this.tokens = tokens;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

}
