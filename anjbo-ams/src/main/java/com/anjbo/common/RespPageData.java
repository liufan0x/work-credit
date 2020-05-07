package com.anjbo.common;

import java.util.List;

public class RespPageData<T> extends RespStatus{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9190423359869425621L;
	//总条数
	private int count;
	//此页的数据
	private List<T> data;
	private int myAllCount;
	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getMyAllCount() {
		return myAllCount;
	}

	public void setMyAllCount(int myAllCount) {
		this.myAllCount = myAllCount;
	}
	
}
