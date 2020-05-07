package com.anjbo.service.estatedeal;


import com.anjbo.bean.estatedeal.DealdataTrend;
import com.anjbo.bean.estatedeal.GZDealDto;


import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/3/7.
 */
public interface GuangZhouDealDataService {


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
    int insertGuangZhouOneHandDataBatch(List<GZDealDto> list);
    /**
     * 批量插入广州二手房数据
     * @param list
     * @return
     */
    int insertGuangZhouSecondHandDataBatch(List<GZDealDto> list);
    
    
    
    /**
     * 按时间删除深圳二手房详情
     * @param date
     * @return
     */
    int deleteGuangZhouSecondHandTrendByDate(Date date,String cityName);
    /**
     * 批量插入深圳二手房趋势数据
     * @param list
     * @return
     */
    int insertGuangZhouSecondHandTrend(List<DealdataTrend> list);
    

    
    /**
     * 更新二手房均价趋势图数据
     * @param list
     * @return
     */
	void updateGuangZhouSecondHandTrend(List<DealdataTrend> guangZhouSecondHandDataTrend);
}
