package com.anjbo.service.estatedeal;


import com.anjbo.bean.estatedeal.DealdataTrend;
import com.anjbo.bean.estatedeal.HZDealDto;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/3/7.
 */
public interface HuiZhouDealDataService {


    /**
     * 查询一手房记录的最大时间
     * @return
     */
    Date selectMaxOneHandDate();
    /**
     * 查询二手房记录的最大时间
     * @return
     */
    Date selectMaxSecondHandDate();

    /**
     * 批量插入广州一手房数据
     * @param list
     * @return
     */
    int insertHuiZhouOneHandDataBatch(List<HZDealDto> list);
    /**
     * 批量插入惠州二手房数据
     * @param list
     * @return
     */
    int insertHuiZhouSecondHandDataBatch(List<HZDealDto> list);
    
    
    /**
     * 按时间删除二手房详情
     * @param date
     * @return
     */
    int deleteHuiZhouSecondHandTrendByDate(Date date,String cityName);
    /**
     * 批量插入二手房趋势数据
     * @param list
     * @return
     */
    int insertHuiZhouSecondHandTrend(List<DealdataTrend> list);
    /**
     * 更新二手房趋势数据
     * @param list
     * @return
     */
	void updateHuiZhouSecondHandTrend(List<DealdataTrend> list);
}
