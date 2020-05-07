package com.anjbo.bean;

import java.io.Serializable;

public class RespBean<T> implements Serializable {

	private static final long serialVersionUID = 5852117168069614127L;
	
	private T data;
	
	private String msg = "操作成功";

	private String code = "SUCCESS";

	public RespBean() {
		super();
	}

	public RespBean(T data) {
		super();
		this.data = data;
	}
	
	public RespBean(Throwable e) {
		super();
		this.msg = e.toString();
		this.code = "FAIL";
	}

	@Override
	public String toString() {
		return "RespStatus [msg=" + msg + ", code=" + code + "]";
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
