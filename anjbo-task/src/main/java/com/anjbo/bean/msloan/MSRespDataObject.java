package com.anjbo.bean.msloan;

import java.util.Date;

public class MSRespDataObject {

    /*响应代码0：成功其它表异常*/
    private Integer resultCode;
    /*异常描述*/
    private String resultDesc;
    /*获取到的凭证*/
    private String token;
    /*结果集*/
    private Object resultData;
    /*评估金额（double）单位：万元*/
    private Date quota;
    /*工单状态*/
    private String state;
    /*工单id*/
    private String workorderId;
    /*审批意见*/
    private String opinion;
    /*评估金额（double）单位：万元*/
    private Double evaluation;
    /*获取到的凭证*/
    private String access_token;

    public Integer getResultCode() {
        return resultCode;
    }

    public void setResultCode(Integer resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultDesc() {
        return resultDesc;
    }

    public void setResultDesc(String resultDesc) {
        this.resultDesc = resultDesc;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Object getResultData() {
        return resultData;
    }

    public void setResultData(Object resultData) {
        this.resultData = resultData;
    }

    public Date getQuota() {
        return quota;
    }

    public void setQuota(Date quota) {
        this.quota = quota;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getWorkorderId() {
        return workorderId;
    }

    public void setWorkorderId(String workorderId) {
        this.workorderId = workorderId;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public Double getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(Double evaluation) {
        this.evaluation = evaluation;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }
}
