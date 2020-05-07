/*
 *Project: anjbo-credit-common
 *File: com.anjbo.bean.vo.ChartVo.java  <2017年11月10日>
 ****************************************************************
 * 版权所有@2017 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.bean.vo;

import java.io.Serializable;
import java.util.Date;

import com.anjbo.utils.DateUtils;

/**
 * 多维报表
 * @Author KangLG 
 * @Date 2017年11月10日 下午4:02:17
 * @version 1.0
 */
public class ChartVo implements Serializable {	
	private static final long serialVersionUID = 1L;	
	// 报表坐标名
	private String[] names;
	private String[] names2;	
	// 报表坐标值
	private Object[] values;
	private Object[] values2;
	
	public ChartVo(){}
	// 最近X月报表(月、金额、订单数)
	public ChartVo(int xMonth){
		this.names = new String[xMonth];
		this.values = new Double[xMonth];
		this.values2 = new Integer[xMonth];
		// 报表X轴(月份)
		Date curDate = new Date();
		for (int i = 0; i < xMonth; i++) {
			this.names[i] = DateUtils.dateToString(org.apache.commons.lang.time.DateUtils.addMonths(curDate, (i-xMonth+1)), "MM月");
			this.values[i] = 0.0;
			this.values2[i] = 0;		
		}
	}
	
	/*
	 * getter - setter
	 */
	public String[] getNames() {
		return names;
	}
	public void setNames(String[] names) {
		this.names = names;
	}
	public String[] getNames2() {
		return names2;
	}
	public void setNames2(String[] names2) {
		this.names2 = names2;
	}
	public Object[] getValues() {
		return values;
	}
	public void setValues(Object[] values) {
		this.values = values;
	}
	public Object[] getValues2() {
		return values2;
	}
	public void setValues2(Object[] values2) {
		this.values2 = values2;
	}
	
	

}
