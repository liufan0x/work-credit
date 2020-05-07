package com.anjbo.bean.risk;

import com.anjbo.bean.BaseDto;

/** 
 * 深圳诉讼
 * @ClassName: LawsuitSzDto 
 * @author liuw 
 * @date 2016-10-8 下午07:48:02 
 */ 
public class LawsuitSzDto extends BaseDto{
    private Integer id;
    /** 外键诉讼id **/
    private Integer lawsuitId;
    /** 案号 **/
    private String caseNo;
    /** 法官or助理 **/
    private String judge;
    /** 当事人 **/
    private String litigant;
    /** 立案时间 **/
    private String registerDate;
    /** 开庭时间 **/
    private String openCourtDate;
    /** 结案时间 **/
    private String closeCourtDate;
    /** 案件状态 **/
    private String status;
    
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

    public String getCaseNo() {
        return caseNo;
    }

    public void setCaseNo(String caseNo) {
        this.caseNo = caseNo;
    }

    public String getJudge() {
        return judge;
    }

    public void setJudge(String judge) {
        this.judge = judge;
    }

    public String getLitigant() {
        return litigant;
    }

    public void setLitigant(String litigant) {
        this.litigant = litigant;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public String getOpenCourtDate() {
        return openCourtDate;
    }

    public void setOpenCourtDate(String openCourtDate) {
        this.openCourtDate = openCourtDate;
    }

    public String getCloseCourtDate() {
        return closeCourtDate;
    }

    public void setCloseCourtDate(String closeCourtDate) {
        this.closeCourtDate = closeCourtDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
}