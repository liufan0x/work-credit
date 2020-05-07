package com.anjbo.service.house;


import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/3.
 */
public interface HousePriceMouthService {




    /**
     * 添加房产价格月流水信息
     * @param params
     * @return
     */
    int insertHousePriceMouth(Map<String, Object> params);
    /**
     * 更新房产价格月流水信息
     * @param params
     * @return
     */
    int updateHousePriceMouth(Map<String, Object> params);
    /**
     * 删除房产价格月流水信息
     * @param params
     * @return
     */
    int deleteHousePriceMouth(Map<String,Object> params);
    /**
     * 查询房产价格月流水信息
     * @param params
     * @return Map
     */
    List<Map<String,Object>> selectHousePriceMouth(Map<String, Object> params);

    /**
     * 更新价格信息定时任务（每月）
     * @param
     * @return
     */
    int updateHouseBaseAndPriceMouthTask();

    /**
     * 更新小区房产定时任务（每月）
     * @return
     */
    int updataMarketBargain();


    int updataMarketBargainTemp();



}

