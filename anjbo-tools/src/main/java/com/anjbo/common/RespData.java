package com.anjbo.common;

import java.util.List;
/**
 * 返回数据类型List
 * @author limh limh@zxsf360.com
 * @date 2016-6-2 下午07:13:07
 */
public class RespData<T> extends RespStatus{
	private static final long serialVersionUID = -9190423359869425621L;
	private List<T> data;
	
	public RespData() {
	}

	public RespData(String code,String msg) {
		setCode(code);
		setMsg(msg);
	}
	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}
	
}
