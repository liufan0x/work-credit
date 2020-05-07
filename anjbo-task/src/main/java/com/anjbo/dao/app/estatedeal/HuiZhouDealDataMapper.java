package com.anjbo.dao.app.estatedeal;

import com.anjbo.bean.estatedeal.DealdataTrend;
import com.anjbo.bean.estatedeal.HZDealDto;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * Created by Administrator on 2017/3/7.
 */
public interface HuiZhouDealDataMapper {

    Date selectMaxOneHandDate();

    Date selectMaxSecondHandDate();

    int insertHuiZhouOneHandDataBatch(List<HZDealDto> huiZhouOneHandData);

    int insertHuiZhouSecondHandDataBatch(List<HZDealDto> huiZhouSecondHandData);
    
    int deleteHuiZhouSecondHandTrendByDate(@Param("date")Date date,@Param("cityName")String cityName);
    
    int insertHuiZhouSecondHandTrend(List<DealdataTrend> list);
}
