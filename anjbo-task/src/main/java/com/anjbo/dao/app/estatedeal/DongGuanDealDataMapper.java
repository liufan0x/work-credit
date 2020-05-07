package com.anjbo.dao.app.estatedeal;

import com.anjbo.bean.estatedeal.DGDealDto;
import com.anjbo.bean.estatedeal.DealdataTrend;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * Created by Administrator on 2017/3/7.
 */
public interface DongGuanDealDataMapper {
    int insertDongGuanOneHandDataBatch(List<DGDealDto> list);

    int deleteDongGuanOneHandDataByDate(Date date);

    int insertDongGuanSecondHandDataBatch(List<DGDealDto> DongGuanSecondHandData);

    int deleteDongGuanSecondHandDataByDate(Date date);

    int insertDongGuanOneHandDetailBatch(List<DGDealDto> DongGuanSecondHandDetail);

    int deleteDongGuanOneHandDetailByDate(Date date);

    int insertDongGuanSecondHandDetailBatch(List<DGDealDto> DongGuanSecondHandDetail);

    int deleteDongGuanSecondHandDetailByDate(Date date);
    
    
    int deleteDongGuanSecondHandTrendByDate(@Param("date")Date date,@Param("cityName")String cityName);
    
    int insertDongGuanSecondHandTrend(List<DealdataTrend> list);
}
