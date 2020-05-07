package com.anjbo.bean.config.page;

import java.util.List;

import com.anjbo.bean.BaseDto;

/**
 * 表单页面
 * @author lic
 * @date 2017-8-17
 */
public class PageConfigDto extends BaseDto{

	private static final long serialVersionUID = 1L;

	private int id;
	
	/** 
	 * 表单页类型
	 * 1.带标签
	 * 2.不带标签
	 */
	private int type;
	
	/** 页面标题 **/
	private String title;
	
	/** 上一步按钮名称 **/
	private String backButName;
	
	/** 上一步按钮Url **/
	private String backButUrl;
	
	/** 提交按钮名称 **/
	private String submitButName;
	
	/** 特殊业务调用的接口 **/
	private String packageClassMethodName;
	
	/** 页面标签(无标签只有一个) **/
	private List<PageTabConfigDto> pageTabConfigDtos;
	
	/** 排序 **/
	private int sort;
	
	/** 页面归类 **/
	private String pageClass;
	
	/** 页面包含那些标签（用逗号分隔） **/
	private String tabClasses;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBackButName() {
		return backButName;
	}

	public void setBackButName(String backButName) {
		this.backButName = backButName;
	}

	public String getBackButUrl() {
		return backButUrl;
	}

	public void setBackButUrl(String backButUrl) {
		this.backButUrl = backButUrl;
	}

	public String getSubmitButName() {
		return submitButName;
	}

	public void setSubmitButName(String submitButName) {
		this.submitButName = submitButName;
	}

	public String getPackageClassMethodName() {
		return packageClassMethodName;
	}

	public void setPackageClassMethodName(String packageClassMethodName) {
		this.packageClassMethodName = packageClassMethodName;
	}

	public List<PageTabConfigDto> getPageTabConfigDtos() {
		return pageTabConfigDtos;
	}

	public void setPageTabConfigDtos(List<PageTabConfigDto> pageTabConfigDtos) {
		this.pageTabConfigDtos = pageTabConfigDtos;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getPageClass() {
		return pageClass;
	}

	public void setPageClass(String pageClass) {
		this.pageClass = pageClass;
	}

	public String getTabClasses() {
		return tabClasses;
	}

	public void setTabClasses(String tabClasses) {
		this.tabClasses = tabClasses;
	}
	
}
