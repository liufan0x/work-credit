package com.anjbo.bean.config;

import com.anjbo.bean.BaseDto;

import net.sf.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * 表单标签区域表单
 * @author lic
 * @date 2017-8-18
 */
public class PageTabRegionFormConfigDto extends BaseDto implements Cloneable{

	private static final long serialVersionUID = 1L;

	private int id;
	
	/** 类型 
	 * 	1.输入框
	 *  2.自带数据下拉框
	 *  3.请求接口下拉框（带参数）
	 *  4.时间控件
	 *  5.图片上传
	 *  6.备注
	 */
	private int type;
	
	/** 
	 * type依赖参数
	 * type为1:正则表达式
	 * type为2:下拉框type
	 * type为3:请求接口Url
	 * type为4:时间格式
	 **/
	private String typeDepend;
	
	/** 下拉框数据(type为"2") **/
	private List<Map<String,Object>> dataList;
	
	/** 
	 * 显示隐藏参数。示列{show:{productCode:'04'},params:'channelManagerUid'}
	 **/
	private String paramsValues;
	
	/** 
	 * 显示隐藏参数Map
	 **/
	private JSONObject paramsValuesJosn;
	
	
	/** 表单标题 **/
	private String title;
	
	/** 表单key **/
	private String key;
	
	/** 特殊Key **/
	private String specialKey;
	
	/** 表单值 **/
	private String value;
	
	/** 特殊Value **/
	private String specialValue;

	/** 是否必填(1,非必填，2,必填) **/
	private int isNeed;
	
	/** 单位 **/
	private String single;

	/** 表单占几列 **/
	private int col;
	
	/** 占位符 **/
	private String placeholder;
	
	/** 键盘类型(0.正常,1.数字) **/
	private String keyboardType;

	/** 排序 **/
	private int sort;
	
	/** 是否只读(1,否，2,是) **/
	private int isReadOnly;
	
	/** 表单归类 **/
	private String formClass;

	/** 自带数据下拉框的值 （默认字典，1为name,name）  **/
	private int selectType;

	/** 其他表名 **/
	private String otherTblName;
	
    public PageTabRegionFormConfigDto() {
		super();
	}

	public PageTabRegionFormConfigDto(int type, String title, String key,
			String value) {
		super();
		this.type = type;
		this.title = title;
		this.key = key;
		this.value = value;
	}
	
	public PageTabRegionFormConfigDto(int type, String title, String key,
			String value,String specialValue) {
		super();
		this.type = type;
		this.title = title;
		this.key = key;
		this.value = value;
		this.specialValue = specialValue;
	}

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

	public String getTypeDepend() {
		return typeDepend;
	}

	public void setTypeDepend(String typeDepend) {
		this.typeDepend = typeDepend;
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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public List<Map<String, Object>> getDataList() {
		return dataList;
	}

	public void setDataList(List<Map<String, Object>> dataList) {
		this.dataList = dataList;
	}

	public int getIsNeed() {
		return isNeed;
	}

	public void setIsNeed(int isNeed) {
		this.isNeed = isNeed;
	}

	public String getSingle() {
		return single;
	}

	public void setSingle(String single) {
		this.single = single;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public String getPlaceholder() {
		return placeholder;
	}

	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public int getIsReadOnly() {
		return isReadOnly;
	}

	public void setIsReadOnly(int isReadOnly) {
		this.isReadOnly = isReadOnly;
	}

	public String getFormClass() {
		return formClass;
	}

	public void setFormClass(String formClass) {
		this.formClass = formClass;
	}

	public String getSpecialKey() {
		return specialKey;
	}

	public void setSpecialKey(String specialKey) {
		this.specialKey = specialKey;
	}

	public String getSpecialValue() {
		return specialValue;
	}

	public void setSpecialValue(String specialValue) {
		this.specialValue = specialValue;
	}

	public String getOtherTblName() {
		return otherTblName;
	}

	public void setOtherTblName(String otherTblName) {
		this.otherTblName = otherTblName;
	}

	public String getParamsValues() {
		return paramsValues;
	}

	public void setParamsValues(String paramsValues) {
		this.paramsValues = paramsValues;
	}

	public JSONObject getParamsValuesJosn() {
		return paramsValuesJosn;
	}

	public void setParamsValuesJosn(JSONObject paramsValuesJosn) {
		this.paramsValuesJosn = paramsValuesJosn;
	}

	public String getKeyboardType() {
		return keyboardType;
	}

	public void setKeyboardType(String keyboardType) {
		this.keyboardType = keyboardType;
	}
	
    public Object clone() {   
        PageTabRegionFormConfigDto o = null;
        try {
            o = (PageTabRegionFormConfigDto) super.clone();   
        } catch (CloneNotSupportedException e) {   
            e.printStackTrace();   
        }   
        return o;   
    }

	public int getSelectType() {
		return selectType;
	}

	public void setSelectType(int selectType) {
		this.selectType = selectType;
	}
    
}


