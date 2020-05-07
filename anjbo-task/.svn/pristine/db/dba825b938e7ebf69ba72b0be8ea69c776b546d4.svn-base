package com.anjbo.dao.app.house;


import com.anjbo.bean.house.HouseRpValueDto;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/3.
 */
public interface HouseMapper {

    /**
     * 更新房产信息
     * @param params
     * @return
     */
    int updateHouse(Map<String,Object> params);

    /**
     * 删除房产信息(list)
     * @param params
     * @return
     */
    int deleteHouseBase(Map<String,Object> params);

    /**
     * 删除房产日流水记录
     * @param params
     * @return
     */
    int deleteHouseDayPrice(Map<String,Object> params);

    /**
     * 删除房产月流水记录
     * @param params
     * @return
     */
    int deleteHouseMouthPrice(Map<String,Object> params);

    /**
     * 获取房产单价信息
     * @param params
     * @return
     */
    List<Map<String,Object>> getHousePrice(Map<String,Object> params);


    /**
     * 增加小区成交价格历史
     * @param params
     * @return
     */
    int insertEnquiryMarketBargain(Map<String,Object> params);


    /**
     * 获取默认rp值配置
     * @return
     */

    List<HouseRpValueDto> selectRpValue();
}

