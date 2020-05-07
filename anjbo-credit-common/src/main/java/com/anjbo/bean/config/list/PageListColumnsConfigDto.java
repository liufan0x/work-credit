package com.anjbo.bean.config.list;

public class PageListColumnsConfigDto {
	
	/** 主键 */
	private int id;
	
	/** 关联列表配置表列表名称 */
	private String listName;

	/** 标题(支持html标签) **/
	private String title;
	
	/** 属性名 **/
	private String field;
	
	/** 左右排列 **/
	private String align;
	
	/** 上下排列 **/
	private String valign;
	
	/** 排序 **/
	private boolean sortable;
	
	/** 默认是否展示  **/
	private boolean visible;
	
	/** 操作项 **/
	private String formatter;

	public PageListColumnsConfigDto() {
		super();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getAlign() {
		return align;
	}

	public void setAlign(String align) {
		this.align = align;
	}

	public String getValign() {
		return valign;
	}

	public void setValign(String valign) {
		this.valign = valign;
	}

	public boolean isSortable() {
		return sortable;
	}

	public void setSortable(boolean sortable) {
		this.sortable = sortable;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public String getFormatter() {
		return formatter;
	}

	public void setFormatter(String formatter) {
		this.formatter = formatter;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getListName() {
		return listName;
	}

	public void setListName(String listName) {
		this.listName = listName;
	}
	
	
}
