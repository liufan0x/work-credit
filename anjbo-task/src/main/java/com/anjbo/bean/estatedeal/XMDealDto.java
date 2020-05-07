package com.anjbo.bean.estatedeal;

import java.util.Date;

/**
 * Created by zhangl on 2017/3/6.
 */
public class XMDealDto {

    /*所属地区*/
    private String areaName;
    /*住宅套数*/
    private Integer houseNum;
    /*总套数*/
    private Integer totalNum;
    /*开始日期*/
    private Date startDate;
    /*结束日期*/
    private Date endDate;

    /*插入日期*/
    private Date date;
    /*住宅类型*/
    private Integer type;

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Integer getHouseNum() {
        return houseNum;
    }

    public void setHouseNum(Integer houseNum) {
        this.houseNum = houseNum;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
