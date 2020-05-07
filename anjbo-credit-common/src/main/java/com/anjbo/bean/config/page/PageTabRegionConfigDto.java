package com.anjbo.bean.config.page;

import java.util.List;

import com.anjbo.bean.BaseDto;

/**
 * 表单标签区域
 * @author lic
 * @date 2017-8-18
 */
public class PageTabRegionConfigDto extends BaseDto{

	private static final long serialVersionUID = 1L;

	private int id;
	
	/** 
	 *	表单区域类型
	 *	1.普通区域
	 *	2.循环区域
	 */
	private int type;
	
	/** 区域标题 **/
	private String title;
	
	/** 区域key(可以为空） **/
	private String key;
	
	/** 区域表单(普通区域取第一个，循环区域则循环) **/
	private List<List<PageTabRegionFormConfigDto>> valueList;
	
	/** 循环区域第一个是否能删除(1,不能删除，2.可以删除) **/
	private int firstIsDelete;
	
	/** 删除区域url **/
	private String deleteUrl;
	
	/** 排序 **/
	private int sort;
	
	/** 区域归类 **/
	private String regionClass;
	
	/** 包含那些表单归类(用逗号隔开） **/
	private String formClasses;
	
	/** 是否必填(1,非必填，2,必填) **/
	private int isNeed;
	
	/** 详情页是否展示编辑按钮(1,能编辑，2,不能编辑) **/
	private int ableEdit;
	
	/** 当前页是否能编辑(1,能编辑，2,不能编辑) **/
	private int curAbleEdit;
	
	/** 元素是否选中(1:选中，2未选中)*/
	private int isChecked;
	
	/** 元素状态(1未存入2已存入3已借出5超时未还7已申请借出)*/
	private int status;

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

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public List<List<PageTabRegionFormConfigDto>> getValueList() {
		return valueList;
	}

	public void setValueList(List<List<PageTabRegionFormConfigDto>> valueList) {
		this.valueList = valueList;
	}

	public int getFirstIsDelete() {
		return firstIsDelete;
	}

	public void setFirstIsDelete(int firstIsDelete) {
		this.firstIsDelete = firstIsDelete;
	}

	public String getDeleteUrl() {
		return deleteUrl;
	}

	public void setDeleteUrl(String deleteUrl) {
		this.deleteUrl = deleteUrl;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getRegionClass() {
		return regionClass;
	}

	public void setRegionClass(String regionClass) {
		this.regionClass = regionClass;
	}

	public String getFormClasses() {
		return formClasses;
	}

	public void setFormClasses(String formClasses) {
		this.formClasses = formClasses;
	}

	public int getIsNeed() {
		return isNeed;
	}

	public void setIsNeed(int isNeed) {
		this.isNeed = isNeed;
	}

	public int getAbleEdit() {
		return ableEdit;
	}

	public void setAbleEdit(int ableEdit) {
		this.ableEdit = ableEdit;
	}

	public int getCurAbleEdit() {
		return curAbleEdit;
	}

	public void setCurAbleEdit(int curAbleEdit) {
		this.curAbleEdit = curAbleEdit;
	}

	public int getIsChecked() {
		//status为2已存入，设置isChecked为1，已选中
		switch (status) {
		case 2:
			isChecked=1;
			break;
		default:
			break;
		}
		return isChecked;
	}

	public void setIsChecked(int isChecked) {
		this.isChecked = isChecked;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
}
