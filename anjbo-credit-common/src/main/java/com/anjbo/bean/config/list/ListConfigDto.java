package com.anjbo.bean.config.list;

import java.util.List;
import java.util.Map;

public class ListConfigDto {
	
	/** 表主键 */
	private int id;

	/** 请求数据url **/
	private String postUrl;
	
	/** 列表名称标示 */
	private String name;
	
	/** 产品code */
	private String productCode;
	
	/** 默认分页参数，默认刷选条件参数，json字符串 **/
	private String page;
	
	/** 是否能提单 **/
	private boolean hasPlaceOrder;
	
	/** 默认展示列 **/
	private Map<String, Object> columnSwitch;
	
	/** 列数据 **/
	private List<PageListColumnsConfigDto> pageListColumnsConfigDtos;

	public ListConfigDto() {
		super();
	}

	public String getPostUrl() {
		return postUrl;
	}

	public void setPostUrl(String postUrl) {
		this.postUrl = postUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public boolean isHasPlaceOrder() {
		return hasPlaceOrder;
	}

	public void setHasPlaceOrder(boolean hasPlaceOrder) {
		this.hasPlaceOrder = hasPlaceOrder;
	}

	public Map<String, Object> getColumnSwitch() {
		return columnSwitch;
	}

	public void setColumnSwitch(Map<String, Object> columnSwitch) {
		this.columnSwitch = columnSwitch;
	}

	public List<PageListColumnsConfigDto> getPageListColumnsConfigDtos() {
		return pageListColumnsConfigDtos;
	}

	public void setPageListColumnsConfigDtos(
			List<PageListColumnsConfigDto> pageListColumnsConfigDtos) {
		this.pageListColumnsConfigDtos = pageListColumnsConfigDtos;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	
}
