package com.anjbo.common;
import java.util.List;

/**
 * 返回前端消息实体类
 * @param <T> 泛型T
 */
public class RespPageData<T> extends RespStatus{
	private static final long serialVersionUID = -9190423359869425621L;
	//总条数
	private int total;
	//此页的数据
	private List<T> rows;
	public RespPageData(){}

	private static RespPageData respPageData = null;
	static{
		respPageData = new RespPageData();
	}

	/**
	 * 静态工厂方法
	 * @return respPageData对象
	 */
	public static RespPageData getInstance() {
		if(respPageData==null){
			return respPageData;
		}else{ return new RespPageData();}
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
	/**
	 * 返回信息，总计数，所有行，信息，错误代码
	 * @param total 总记录数
	 * @param rows  所有行数
	 * @param msg   消息
	 * @param code  代码
	 * @return
	 */
	public RespPageData<T> getRespPageData(Integer total,List<T> rows,String msg,String code){
		this.total= total;
		this.rows = rows;
		this.setMsg(msg);
		this.setCode(code);
		return this;
	}

	/**
	 * 返回信息，消息和错误代码
	 * @param msg 消息
	 * @param code 失败code
	 * @return
	 */
	public RespPageData<T> getRespPageData(String msg,String code){
		this.setMsg(msg);
		this.setCode(code);
		return this;
	}

}
