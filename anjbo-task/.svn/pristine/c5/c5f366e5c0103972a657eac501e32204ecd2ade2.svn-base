package com.anjbo.dao.app.estatedeal;

import com.anjbo.bean.estatedeal.DealdataTrend;
import com.anjbo.bean.estatedeal.GZDealDto;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * Created by Administrator on 2017/3/7.
 */
public interface GuangZhouDealDataMapper {

    Date selectMaxOneHandDate();

    Date selectMaxSecondHandDate();

    int insertGuangZhouOneHandDataBatch(List<GZDealDto> list);

    int insertGuangZhouSecondHandDataBatch(List<GZDealDto> list);
    
    
    int deleteGuangZhouSecondHandTrendByDate(@Param("date")Date date,@Param("cityName")String cityName);
    
    int insertGuangZhouSecondHandTrend(List<DealdataTrend> list);
}
