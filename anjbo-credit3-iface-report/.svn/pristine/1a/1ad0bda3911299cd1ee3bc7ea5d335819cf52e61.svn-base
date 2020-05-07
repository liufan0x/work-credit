package com.anjbo.bean;

import java.io.Serializable;

/**
 * @author Chad-dx
 * @version 1.0
 * @date 2018-7-20
 * @Deprecated 风控退回率统统计查询类
 **/
public class RiskAuditVo implements Serializable {
    private static final long serialVersionUID = -6564451455129182289L;

    public RiskAuditVo() {
    }

    public RiskAuditVo(String cityName, Integer addInformation, Integer refusal, Integer modifySys, Integer other) {
        this.cityName = cityName;
        this.addInformation = addInformation;
        this.refusal = refusal;
        this.modifySys = modifySys;
        this.other = other;
    }

    /**
     *分公司
     */
    private String cityName;
    /**
     *补充资料退单数
     */
    private Integer addInformation;
    /**
     *拒单退单数
     */
    private Integer refusal;
    /**
     *系统修改退单数
     */
    private Integer modifySys;
    /**
     *其他原因退单数
     */
    private Integer other;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Integer getAddInformation() {
        return addInformation;
    }

    public void setAddInformation(Integer addInformation) {
        this.addInformation = addInformation;
    }

    public Integer getRefusal() {
        return refusal;
    }

    public void setRefusal(Integer refusal) {
        this.refusal = refusal;
    }

    public Integer getModifySys() {
        return modifySys;
    }

    public void setModifySys(Integer modifySys) {
        this.modifySys = modifySys;
    }

    public Integer getOther() {
        return other;
    }

    public void setOther(Integer other) {
        this.other = other;
    }
}
