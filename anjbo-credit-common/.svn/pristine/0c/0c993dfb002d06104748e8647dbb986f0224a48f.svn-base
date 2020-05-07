package com.anjbo.bean.element;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.anjbo.bean.BaseDto;
import com.anjbo.common.Constants;

/**
 * 要件退回信息 
 * 
 * @author huanglj
 *
 */
public class DocumentsReturnDto extends BaseDto{

	/** 要件退还 */
	private Integer id;

	
	
	/** 订单编号 */
	private String orderNo;
	
	/** 最后更新时间 */
	private Date lastUpdateTime;

	/** 退回时间 */
	private Date returnTime;
	
	private String returnTimeStr;
	/**
	 * 退回操作人
	 */
	private String returnHandleName;

	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 处理人uid
	 */
	private String handleUid;
	 /**
     * 处理人名称
     */
    private String handleName;
    /**
     * 退还图片
     */
    private String returnImgUrl;
    
    private List<String> imgUrl;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public Date getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(Date returnTime) {
		this.returnTime = returnTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getHandleUid() {
		return handleUid;
	}

	public void setHandleUid(String handleUid) {
		this.handleUid = handleUid;
	}

	public String getHandleName() {
		return handleName;
	}

	public void setHandleName(String handleName) {
		this.handleName = handleName;
	}

	public String getReturnImgUrl() {
		return returnImgUrl;
	}

	public void setReturnImgUrl(String returnImgUrl) {
		this.returnImgUrl = returnImgUrl;
	}

	public String getReturnTimeStr() {
		return returnTimeStr;
	}

	public void setReturnTimeStr(String returnTimeStr) {
		this.returnTimeStr = returnTimeStr;
	}

	public String getReturnHandleName() {
		return returnHandleName;
	}

	public void setReturnHandleName(String returnHandleName) {
		this.returnHandleName = returnHandleName;
	}

	public List<String> getImgUrl() {
		if(StringUtils.isNotBlank(this.returnImgUrl)){
			String tmp = returnImgUrl.replaceAll("_18", "_48").replaceAll(";",",");
			String[] str = tmp.split(Constants.IMG_SEPARATE);
			imgUrl = Arrays.asList(str);
		}
		return imgUrl;
	}

	public void setImgUrl(List<String> imgUrl) {
		this.imgUrl = imgUrl;
	}

}