package com.anjbo.bean.risk;

import com.anjbo.bean.BaseDto;

/** 
 * 全国诉讼
 * @ClassName: LawsuitCnDto 
 * @author liuw 
 * @date 2016-10-8 下午07:46:51 
 */ 
public class LawsuitCnDto extends BaseDto{
    private Integer id;
    
    /** 外键诉讼id **/
    private Integer lawsuitId;
    /** 名称 **/
    private String name;
    /** 日期 **/
    private String date;
    /** 案号 **/
    private String caseNo;
    /** 详情id **/
    private String detailId;
    /** 0初始化 1查过全国 2查过深圳 3两个都查过 **/
    private Integer status;
    
    private String orderNo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLawsuitId() {
        return lawsuitId;
    }

    public void setLawsuitId(Integer lawsuitId) {
        this.lawsuitId = lawsuitId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCaseNo() {
        return caseNo;
    }

    public void setCaseNo(String caseNo) {
        this.caseNo = caseNo;
    }

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
}