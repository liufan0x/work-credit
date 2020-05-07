package com.anjbo.common;


public class RespDataObject<T> extends RespStatus{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9190423359869425621L;
	private T data;

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	public RespDataObject(T data,String code,String msg) {
		setCode(code);
		setMsg(msg);
		this.data=data;
	}
	public RespDataObject(){}
}
