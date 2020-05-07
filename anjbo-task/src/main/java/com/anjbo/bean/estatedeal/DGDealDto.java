package com.anjbo.bean.estatedeal;

import java.util.Date;

/**
 * 东莞交易数据
 */
public class DGDealDto {

    /*镇区*/
    private String name;
    /*住宅成交套数*/
    private Integer houseNum;
    /*成交总套数*/
    private Integer totalNum;
    /*成交金额*/
    private Double money;
    /*成交日期*/
    private Date date;
    /*类型1（一手房） 2（二手房）*/
    private Integer type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
