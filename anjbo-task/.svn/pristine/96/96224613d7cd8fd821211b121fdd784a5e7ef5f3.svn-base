package com.anjbo.service.estatedeal;

import com.anjbo.bean.estatedeal.DealdataAvgPricce;
import com.anjbo.bean.estatedeal.DealdataTrend;
import com.anjbo.bean.estatedeal.SZDealDto;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/7.
 */
public interface ShenZhenDealDataService {

    /**
     * 批量插入深圳一手房数据
     * @param list
     * @return
     */
    int insertShenZhenOneHandDataBatch(List<SZDealDto> list);

    /**
     * 按时间删除深圳一手房数据
     * @param date
     * @return
     */
    int deleteShenZhenOneHandDataByDate(Date date);

    /**
     * 批量更新深圳一手房数据
     * @param list
     * @return
     */
    void updateShenZhenOneHandDataBatch(List<SZDealDto> list);

    /**
     * 批量插入深圳二手房数据
     * @param list
     * @return
     */
    int insertShenZhenSecondHandDataBatch(List<SZDealDto> list);

    /**
     * 按时间删除深圳二手房数据
     * @param date
     * @return
     */
    int deleteShenZhenSecondHandDataByDate(Date date);
    /**
     * 批量更新深圳一手房数据
     * @param list
     * @return
     */
    void updateShenZhenSecondHandDataBatch(List<SZDealDto> list);

    /**
     * 批量插入深圳二手房详情
     * @param list
     * @return
     */
    int insertShenZhenSecondHandDetailBatch(List<SZDealDto> list);

    /**
     * 按时间删除深圳二手房详情
     * @param date
     * @return
     */
    int deleteShenZhenSecondHandDetailByDate(Date date);

    /**
     * 批量更新深圳二手房数据
     * @param list
     * @return
     */
    void updateShenZhenSecondHandDetailBatch(List<SZDealDto> list);
    
    /**
     * 按时间删除深圳二手房详情
     * @param date
     * @return
     */
    int deleteShenZhenSecondHandTrendByDate(Date date,String cityName);
    /**
     * 批量插入深圳二手房趋势数据
     * @param list
     * @return
     */
    int insertShenZhenSecondHandTrend(List<DealdataTrend> list);
    
    /**
     * 批量更新深圳二手房数据趋势图数据
     * @param list
     * @return
     */
    void updateShenZhenSecondHandTrend(List<DealdataTrend> list);
    /**
     * 插入深圳一手房均价数据
     * @param list
     * @return
     */
    int insertShenZhenOneHandAvgPrice(DealdataAvgPricce dt);
    /**
     * 删除深圳一手房均价数据
     * @param list
     * @return
     */
    int deleteShenZhenOneHandAvgPriceByDate(DealdataAvgPricce dt);
    
    /**
     * 更新深圳一手房均价数据
     * @param list
     * @return
     */
    void updateShenZhenOneHandAvgPrice(DealdataAvgPricce dt);
}
