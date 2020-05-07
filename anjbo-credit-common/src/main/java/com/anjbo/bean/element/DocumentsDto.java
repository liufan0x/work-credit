package com.anjbo.bean.element;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.anjbo.bean.BaseDto;
import com.anjbo.common.Constants;

/**
 * 订单要件管理
 * @author huanglj
 *
 */
public class DocumentsDto extends BaseDto{
	
    private Integer id;

    /**
     *订单编号
     *
     * This field corresponds to the database column tbl_element_documents.orderNo
     *
     * @mbggenerated 2017-06-08
     */
    private String orderNo;

    /**
     *状态  1初始状态 2.通过钉钉特批，3通过系统校验
     *
     * This field corresponds to the database column tbl_element_documents.status
     *
     * @mbggenerated 2017-06-08
     */
    private Integer status;

    /**
     * 特批要件上传图片URL
     */
    private String greenStatusImgUrl;
    
    /**
     * 要件图片
     */
    private String elementUrl;
    
    /**
     * 备注
     */
    private String remark;
    /**
     * 将要件特批上传的图片按;切割
     */
    private List<String> imgUrl;
    /**
     * 处理人uid
     */
    private String handleUid;
    /**
     * 处理人名称
     */
    private String handleName;
    /**
     * 赎楼方式
     */
    private ForeclosureTypeDto foreclosureType;
    /**
     * 回款方式
     */
    private PaymentTypeDto paymentType;
    /**
     * 下一节点处理人
     */
    private String nextHandleUid;
    
    
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
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

	public String getGreenStatusImgUrl() {
		return greenStatusImgUrl;
	}

	public void setGreenStatusImgUrl(String greenStatusImgUrl) {
		this.greenStatusImgUrl = greenStatusImgUrl;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getElementUrl() {
		return elementUrl;
	}

	public void setElementUrl(String elementUrl) {
		this.elementUrl = elementUrl;
	}

	public List<String> getImgUrl() {
		if(StringUtils.isNotBlank(this.greenStatusImgUrl)){
			String tmp = greenStatusImgUrl.replaceAll("_18", "_48").replaceAll(";", ",");
			String[] str = tmp.split(Constants.IMG_SEPARATE);
			imgUrl = Arrays.asList(str);
		}
		return imgUrl;
	}

	public void setImgUrl(List<String> imgUrl) {
		this.imgUrl = imgUrl;
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

	public ForeclosureTypeDto getForeclosureType() {
		return foreclosureType;
	}

	public void setForeclosureType(ForeclosureTypeDto foreclosureType) {
		this.foreclosureType = foreclosureType;
	}

	public PaymentTypeDto getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentTypeDto paymentType) {
		this.paymentType = paymentType;
	}

	public String getNextHandleUid() {
		return nextHandleUid;
	}

	public void setNextHandleUid(String nextHandleUid) {
		this.nextHandleUid = nextHandleUid;
	}
}