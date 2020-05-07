package com.anjbo.stream.bo;

import java.io.Serializable;

public class CommonBo implements Serializable{
	
	private static final long serialVersionUID = -2164058270260403154L;
	
	public CommonBo() {}
	
	public CommonBo(String message) {
		this.message = message;
	}
	
	
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "message:"+this.message;
	}
	
}
