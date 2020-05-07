package com.anjbo.bean.estatedeal;

import java.util.Date;

/**
 * Created by zhangl on 2017/3/4.
 */
public class HZDealDto {

    /*区域名称*/
    private String areaName;
    /*住宅成交套数*/
    private Integer houseNum;
    /*成交总套数*/
    private Integer totalNum;
    /*成交日期*/
    private Date date;
    /*类型 1（一手房） 2（二手房）*/
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
