package com.anjbo.bean.estateprice;

import java.util.Date;

/**
 * Created by zhangl on 2017/3/3.
 */
public class TZCPropertyDto {

    private Integer id;
    /*物业名称*/
    private String propertyName;
    /*楼栋名称*/
    private String buildingName;
    /*城市名称*/
    private String city;
    /*物业楼栋下，房号的最高单价*/
    private Double maxPrice;
    /*物业楼栋下，物业的均价*/
    private Double avgPrice;
    /*avg(快鸽同致诚数据物业均价+快鸽同致诚数据物业最高单价+网络报盘均价+查房价均价)*/
    private Double estatePrice;
    /*插入日期*/
    private Date date;

    /*面积区间*/
    private String areaRange;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Double getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(Double avgPrice) {
        this.avgPrice = avgPrice;
    }

    public Double getEstatePrice() {
        return estatePrice;
    }

    public void setEstatePrice(Double estatePrice) {
        this.estatePrice = estatePrice;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAreaRange() {
        return areaRange;
    }

    public void setAreaRange(String areaRange) {
        this.areaRange = areaRange;
    }
}
