package com.anjbo.bean;

import java.io.Serializable;

/**
 * 字典
 * @author lic
 * @date 2017-6-1
 */
public class DictDto implements Serializable{
	
	/**  **/
	private static final long serialVersionUID = 1L;

	/** Id **/
	private int id;
	
	/** 编号 **/
	private String code;
	
	/** 父Code **/
	private String pcode;
	
	/** 名称 **/
	private String name;
	
	/** 类型 **/
	private String type;
	
	/** 排序 **/
	private int sort;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPcode() {
		return pcode;
	}

	public void setPcode(String pcode) {
		this.pcode = pcode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}
	
}
