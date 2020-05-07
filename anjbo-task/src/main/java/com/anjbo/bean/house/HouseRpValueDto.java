package com.anjbo.bean.house;

/**
 * Created by zsy on 2017/3/10.
 * Bean Rp值实体类
 */

public class HouseRpValueDto {


    private String name;//rp编号 用于关联
    private String key;//具体参数名称，默认是default 其他例如：楼层：6-8层
    private Double value;//系数值

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
