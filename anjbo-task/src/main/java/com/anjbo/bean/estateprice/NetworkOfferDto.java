package com.anjbo.bean.estateprice;

/**
 * Created by zhangl on 2017/3/3.
 */
public class NetworkOfferDto {

    /*网络报盘数据*/
    private Integer id;
    /*物业名称*/
    private String propertyName;
    /*城市名称*/
    private String city;
    /*物业均价，已分组求平均*/
    private Double avgPrice;

    /*面积范围*/
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Double getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(Double avgPrice) {
        this.avgPrice = avgPrice;
    }

    public String getAreaRange() {
        return areaRange;
    }

    public void setAreaRange(String areaRange) {
        this.areaRange = areaRange;
    }
}
