package com.anjbo.bean.product.data;

import java.util.Date;
import java.util.Map;

import com.anjbo.bean.BaseDto;
/**
 * 产品数据表
 * @author liuf
 *
 */
public class ProductDataDto extends BaseDto{
	private static final long serialVersionUID = 1L;
	/** 自增主键 */
	private int id;
	/** 创建人 */
	private String createUid;
	/** 创建时间 */
	private Date createTime;
	/** 更新人 */
	private String updateUid;
	/** 更新时间 */
	private Date updateTime;
	/** 逻辑表名 */
	private String tblName;
	/** 录入的目标表 */
	private String tblDataName;
	/** 订单号 */
	private String orderNo;
	/** 产品code */
	private String productCode;
	/** 城市code */
	private String cityCode;
	/** 1:机构订单，2：普通用户*/
	private int addOrderType;
	
	/** 列表独有字段 start */
	/** 上个处理人Uid */
	private String previousHandlerUid;
	/** 当前处理人Uid */
	private String currentHandlerUid;
	/** 上个处理人 */
	private String previousHandler;
	/** 当前处理人 */
	private String currentHandler;
	/** 来源 */
	private String source;
	/** 列表独有字段  end */
	
	/** 数据 */
	private Map<String,Object> data;
	/** 其他表数据 */
	private Map<String,Object> otherData;
	private String dataStr;
	/** 需要更新到列表的key,用逗号隔开 */
	private String key;
	/**动态录入值如：'1','s'*/
	private String value;
	/** 动态更新值如：a='01',b='ss' */
	private String keyValue;
	/** 无表数据时展示的一句话 */
	private String showText;
	public ProductDataDto() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCreateUid() {
		return createUid;
	}
	public void setCreateUid(String createUid) {
		this.createUid = createUid;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getUpdateUid() {
		return updateUid;
	}
	public void setUpdateUid(String updateUid) {
		this.updateUid = updateUid;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getTblName() {
		return tblName;
	}
	public void setTblName(String tblName) {
		this.tblName = tblName;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getTblDataName() {
		return tblDataName;
	}
	public void setTblDataName(String tblDataName) {
		this.tblDataName = tblDataName;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getKeyValue() {
		return keyValue;
	}
	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}
	public Map<String, Object> getData() {
		return data;
	}
	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	public String getDataStr() {
		return dataStr;
	}
	public void setDataStr(String dataStr) {
		this.dataStr = dataStr;
	}
	public String getShowText() {
		return showText;
	}
	public void setShowText(String showText) {
		this.showText = showText;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public Map<String,Object> getOtherData() {
		return otherData;
	}
	public void setOtherData(Map<String,Object> otherData) {
		this.otherData = otherData;
	}
	public String getPreviousHandlerUid() {
		return previousHandlerUid;
	}
	public void setPreviousHandlerUid(String previousHandlerUid) {
		this.previousHandlerUid = previousHandlerUid;
	}
	public String getCurrentHandlerUid() {
		return currentHandlerUid;
	}
	public void setCurrentHandlerUid(String currentHandlerUid) {
		this.currentHandlerUid = currentHandlerUid;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getPreviousHandler() {
		return previousHandler;
	}
	public void setPreviousHandler(String previousHandler) {
		this.previousHandler = previousHandler;
	}
	public String getCurrentHandler() {
		return currentHandler;
	}
	public void setCurrentHandler(String currentHandler) {
		this.currentHandler = currentHandler;
	}
	public int getAddOrderType() {
		return addOrderType;
	}
	public void setAddOrderType(int addOrderType) {
		this.addOrderType = addOrderType;
	}
	
	
}
