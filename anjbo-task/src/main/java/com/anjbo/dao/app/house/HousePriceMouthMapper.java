package com.anjbo.dao.app.house;


import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/3.
 */
public interface HousePriceMouthMapper {
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
     * 统计日流水表SumPrice均价
     * @param params
     * @return
     */
    Map<String,Object> avgHouseSumPriceDay(Map<String, Object> params);
}

