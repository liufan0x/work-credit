/*
 *Project: anjbo-credit-common
 *File: com.anjbo.bean.vo.PageList.java  <2017年10月24日>
 ****************************************************************
 * 版权所有@2017 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.bean.vo;

import java.util.List;

/**
 * @Author KangLG 
 * @Date 2017年10月24日 下午4:21:59
 * @version 1.0
 */
public class PageList<T> {
	private long total;// 数据行总数
	private List<T> dataList; //当前页的数据集合
	
	public PageList(long total, List<T> dataList){
		this.total = total;
		this.dataList = dataList;
	}
	
	/*
	 * getter - setter
	 */
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}

	public List<T> getDataList() {
		return dataList;
	}

	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}
	

}
