package com.anjbo.dao.app.estatedeal;

import com.anjbo.bean.estatedeal.DealdataAvgPricce;
import com.anjbo.bean.estatedeal.DealdataTrend;
import com.anjbo.bean.estatedeal.SZDealDto;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * Created by Administrator on 2017/3/7.
 */
public interface ShenZhenDealDataMapper {
    int insertShenZhenOneHandDataBatch(List<SZDealDto> list);

    int deleteShenZhenOneHandDataByDate(Date date);

    int insertShenZhenSecondHandDataBatch(List<SZDealDto> shenZhenSecondHandData);

    int deleteShenZhenSecondHandDataByDate(Date date);

    int insertShenZhenSecondHandDetailBatch(List<SZDealDto> shenZhenSecondHandDetail);

    int deleteShenZhenSecondHandDetailByDate(Date date);
    
    int deleteShenZhenSecondHandTrendByDate(@Param("date")Date date,@Param("cityName")String cityName);
    
    int insertShenZhenSecondHandTrend(List<DealdataTrend> list);
    
    int insertShenZhenOneHandAvgPrice(DealdataAvgPricce dt);
    
    int deleteShenZhenOneHandAvgPriceByDate(DealdataAvgPricce dt);
}
