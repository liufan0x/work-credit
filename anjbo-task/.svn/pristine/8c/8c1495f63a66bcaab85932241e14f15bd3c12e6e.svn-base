package com.anjbo.service.estatedeal;

import com.anjbo.bean.estatedeal.DGDealDto;
import com.anjbo.bean.estatedeal.DealdataTrend;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/3/7.
 */
public interface DongGuanDealDataService {

    /*东莞一手房数据*/
    int insertDongGuanOneHandDataBatch(List<DGDealDto> list);

    int deleteDongGuanOneHandDataByDate(Date date);

    void updateDongGuanOneHandDataBatch(List<DGDealDto> list);

    /*东莞二手房数据*/
    int insertDongGuanSecondHandDataBatch(List<DGDealDto> list);

    int deleteDongGuanSecondHandDataByDate(Date date);

    void updateDongGuanSecondHandDataBatch(List<DGDealDto> list);

    /*东莞一手房详情*/
    int insertDongGuanOneHandDetailBatch(List<DGDealDto> list);

    int deleteDongGuanOneHandDetailByDate(Date date);

    void updateDongGuanOneHandDetailBatch(List<DGDealDto> list);

    /*东莞二手房详情*/
    int insertDongGuanSecondHandDetailBatch(List<DGDealDto> list);

    int deleteDongGuanSecondHandDetailByDate(Date date);

    void updateDongGuanSecondHandDetailBatch(List<DGDealDto> list);

    
    int insertDongGuanSecondHandTrend(List<DealdataTrend> list);
    
    int deleteDongGuanSecondHandTrendByDate(Date date,String cityName);
    
    /*东莞二手房趋势图数据更新方法*/
	void updateDongGuanSecondHandTrend(List<DealdataTrend> dongGuanSecondHandDataTrend);
}
