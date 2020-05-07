package com.anjbo.bean.risk;

import java.util.List;
import java.util.Map;

import com.anjbo.bean.BaseDto;
/**
 *诉讼信息表
 *
 * This field corresponds to the database column tbl_risk_lawsuit.id
 *
 * @mbggenerated 2017-06-22
 */
public class LawsuitDto extends BaseDto{
	
    private Integer id;

    /**
     *订单号
     *
     * This field corresponds to the database column tbl_risk_lawsuit.orderNo
     *
     * @mbggenerated 2017-06-22
     */
    private String orderNo;

    /**
     *是否有诉讼，0初始化，1没有诉讼，2有诉讼
     *
     * This field corresponds to the database column tbl_risk_lawsuit.isLawsuit
     *
     * @mbggenerated 2017-06-22
     */
    private Integer isLawsuit;

    /**
     *被执行人名称/被执行机构名称
     *
     * This field corresponds to the database column tbl_risk_lawsuit.name
     *
     * @mbggenerated 2017-06-22
     */
    private String name;

    /**
     *身份证号/机构代号
     *
     * This field corresponds to the database column tbl_risk_lawsuit.codeNo
     *
     * @mbggenerated 2017-06-22
     */
    private String codeNo;

    /**
     *0初始化 1查过全国 2查过深圳 3两个都查过
     *
     * This field corresponds to the database column tbl_risk_lawsuit.status
     *
     * @mbggenerated 2017-06-22
     */
    private Integer status;
    
    private List<Map<String,Object>> lawsuitImgs;

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

    public Integer getIsLawsuit() {
        return isLawsuit;
    }

    public void setIsLawsuit(Integer isLawsuit) {
        this.isLawsuit = isLawsuit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCodeNo() {
        return codeNo;
    }

    public void setCodeNo(String codeNo) {
        this.codeNo = codeNo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

	public void setLawsuitImgs(List<Map<String, Object>> lawsuitImgs) {
		this.lawsuitImgs = lawsuitImgs;
	}

	public List<Map<String, Object>> getLawsuitImgs() {
		return lawsuitImgs;
	}
}