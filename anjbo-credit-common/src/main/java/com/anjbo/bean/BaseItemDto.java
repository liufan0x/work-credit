package com.anjbo.bean;

import java.util.List;
import java.util.Map;

public class BaseItemDto {


	public BaseItemDto(){}

	public BaseItemDto(int type,String title,String key,List<Map<String, String>> content,String contentUrl,String params,boolean isNeed,String value,String single,String placeholder) {
		this.type = type;
		this.title = title;
		this.key = key;
		this.content = content;
		this.contentUrl = contentUrl;
		this.params = params;
		this.isNeed = isNeed;
		this.value = value;
		this.single = single;
		this.placeholder = placeholder;
	}
	
//	public static BaseItemDto baseItemDtoType1(String title,String key,boolean isNeed,String value,String single,String placeholder){
//		return BaseItemDto(1,title,key,null,null,null,isNeed,value,single,placeholder);
//	}
	

	/** 类型(
	 * 1.输入框,
	 * 2.选择框,
	 * 3.时间选择器,
	 * 4.下一页选择器
	 * 5.上传图片
	 * 6.只读文本框
	 * 7.备注框
	 * 8.按钮
	 * 9.开关
	 * 10.计算
	 */
	private int type;

	/** 标题 **/
	private String title;

	/** 传输key **/
	private String key;

	/** 前置参数 **/
	private String contentType;
	
	/** 选择框内容 **/
	private List<Map<String, String>> content;

	/** 下一页选择器Url **/
	private String contentUrl;

	/** 选择器所需参数 **/
	private String params;

	/** 是否必填 **/
	private boolean isNeed;

	/** 默认值 **/
	private String value;

	/** 单位 **/
	private String single;

	/** 占位符 **/
	private String placeholder;

	/** 键盘类型
	 * 0.正常
	 * 1.数字
	 *  **/
	private int keyboardType;
	
	private int isEnd;
	
	/** 类型 **/
	private String pageType;
	
	/** 前置参数Type(1,清空 2,显示隐藏) **/
	private String preArgumentType;
	
	/** 需要显示的参数 **/
	private String showParams;
	
	/** 什么值隐藏 **/
	private String showValues;
	
	/** 时间格式 **/
	private String dataFmt;
	/**后置参数:当当前字段值改变时初始化设置字段的值,多个用,分割*/
	private String postParams;
	/**1:只读2：可操作*/
	private int readOnly;
	
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

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public List<Map<String, String>> getContent() {
		return content;
	}

	public void setContent(List<Map<String, String>> content) {
		this.content = content;
	}

	public String getContentUrl() {
		return contentUrl;
	}

	public void setContentUrl(String contentUrl) {
		this.contentUrl = contentUrl;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public boolean getIsNeed() {
		return isNeed;
	}

	public void setIsNeed(boolean isNeed) {
		this.isNeed = isNeed;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getSingle() {
		return single;
	}

	public void setSingle(String single) {
		this.single = single;
	}

	public String getPlaceholder() {
		return placeholder;
	}

	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
	}

	public int getKeyboardType() {
		return keyboardType;
	}

	public void setKeyboardType(int keyboardType) {
		this.keyboardType = keyboardType;
	}

	public int getIsEnd() {
		return isEnd;
	}

	public void setIsEnd(int isEnd) {
		this.isEnd = isEnd;
	}

	public String getPageType() {
		return pageType;
	}

	public void setPageType(String pageType) {
		this.pageType = pageType;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getShowParams() {
		return showParams;
	}

	public void setShowParams(String showParams) {
		this.showParams = showParams;
	}

	public String getShowValues() {
		return showValues;
	}

	public void setShowValues(String showValues) {
		this.showValues = showValues;
	}

	public String getPreArgumentType() {
		return preArgumentType;
	}

	public void setPreArgumentType(String preArgumentType) {
		this.preArgumentType = preArgumentType;
	}

	public String getDataFmt() {
		return dataFmt;
	}

	public void setDataFmt(String dataFmt) {
		this.dataFmt = dataFmt;
	}

	public String getPostParams() {
		return postParams;
	}

	public void setPostParams(String postParams) {
		this.postParams = postParams;
	}

	public int getReadOnly() {
		return readOnly;
	}

	public void setReadOnly(int readOnly) {
		this.readOnly = readOnly;
	}
	
}
