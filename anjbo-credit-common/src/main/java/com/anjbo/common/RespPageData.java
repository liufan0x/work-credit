package com.anjbo.common;

import java.util.List;

import com.anjbo.bean.vo.PageList;

public class RespPageData<T> extends RespStatus{
	private static final long serialVersionUID = -9190423359869425621L;
	//总条数
	private int total;
	//此页的数据
	private List<T> rows;
	
	public RespPageData(){}
	public RespPageData(PageList<T> pageList){
		if(null != pageList){
			this.total = (int)pageList.getTotal();
			this.rows = pageList.getDataList();
		}
		super.setCode(RespStatusEnum.SUCCESS.getCode());
		super.setMsg(RespStatusEnum.SUCCESS.getMsg());
	}

	/**
	 * 设置分页对象
	 * @Author KangLG<2017年10月24日>
	 * @param pageList
	 */
	public void setPageList(PageList<T> pageList) {
		if(null != pageList){
			this.total = (int)pageList.getTotal();
			this.rows = pageList.getDataList();
		}
	}
	
	/*
	 * getter - setter
	 */
	public List<T> getRows() {
		return rows;
	}
	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}

}
