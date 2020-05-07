package com.anjbo.service.estatedeal.impl;


import com.anjbo.bean.estatedeal.DGDealDto;
import com.anjbo.bean.estatedeal.DealdataTrend;
import com.anjbo.dao.app.estatedeal.DongGuanDealDataMapper;
import com.anjbo.service.estatedeal.DongGuanDealDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/3/7.
 */
@Transactional
@Service("dongGuanDealDataService")
public class DongGuanDealDataServiceImpl implements DongGuanDealDataService {

    @Autowired
    private DongGuanDealDataMapper dongGuanDealDataMapper;

    /*东莞一手房数据*/
    @Override
    public int insertDongGuanOneHandDataBatch(List<DGDealDto> list) {
        return dongGuanDealDataMapper.insertDongGuanOneHandDataBatch(list);
    }

    @Override
    public int deleteDongGuanOneHandDataByDate(Date date) {
        return dongGuanDealDataMapper.deleteDongGuanOneHandDataByDate(date);
    }

    @Override
    public void updateDongGuanOneHandDataBatch(List<DGDealDto> list) {
        if(list !=null&& list.size()>0){
            Date date = list.get(0).getDate();
            deleteDongGuanOneHandDataByDate(date);
            insertDongGuanOneHandDataBatch(list);
        }
    }

    /*东莞二手房数据*/
    @Override
    public int insertDongGuanSecondHandDataBatch(List<DGDealDto> list) {
        return dongGuanDealDataMapper.insertDongGuanSecondHandDataBatch(list);
    }

    @Override
    public int deleteDongGuanSecondHandDataByDate(Date date) {
        return dongGuanDealDataMapper.deleteDongGuanSecondHandDataByDate(date);
    }

    @Override
    public void updateDongGuanSecondHandDataBatch(List<DGDealDto> list) {
        if(list !=null&& list.size()>0){
            Date date = list.get(0).getDate();
            deleteDongGuanSecondHandDataByDate(date);
            insertDongGuanSecondHandDataBatch(list);
        }
    }

    /*东莞一手房详情*/
    @Override
    public int insertDongGuanOneHandDetailBatch(List<DGDealDto> list) {
        return dongGuanDealDataMapper.insertDongGuanOneHandDetailBatch(list);
    }

    @Override
    public int deleteDongGuanOneHandDetailByDate(Date date) {
        return dongGuanDealDataMapper.deleteDongGuanOneHandDetailByDate(date);
    }

    @Override
    public void updateDongGuanOneHandDetailBatch(List<DGDealDto> list) {
        if(list !=null&& list.size()>0){
            Date date = list.get(0).getDate();
            deleteDongGuanOneHandDetailByDate(date);
            insertDongGuanOneHandDetailBatch(list);
        }
    }

    /*东莞二手房详情*/
    @Override
    public int insertDongGuanSecondHandDetailBatch(List<DGDealDto> list) {
        return dongGuanDealDataMapper.insertDongGuanSecondHandDetailBatch(list);
    }

    @Override
    public int deleteDongGuanSecondHandDetailByDate(Date date) {
        return dongGuanDealDataMapper.deleteDongGuanSecondHandDetailByDate(date);
    }

    @Override
    public void updateDongGuanSecondHandDetailBatch(List<DGDealDto> list) {
        if(list !=null&& list.size()>0){
            Date date = list.get(0).getDate();
            deleteDongGuanSecondHandDetailByDate(date);
            insertDongGuanSecondHandDetailBatch(list);
        }
    }

	@Override
	public int insertDongGuanSecondHandTrend(List<DealdataTrend> list) {		
		return dongGuanDealDataMapper.insertDongGuanSecondHandTrend(list);
	}

	@Override
	public int deleteDongGuanSecondHandTrendByDate(Date date,String cityName) {
		return dongGuanDealDataMapper.deleteDongGuanSecondHandTrendByDate(date,cityName);
	}

	 /**
     * 历史数据抓完后，此方法每次执行更新月数据
     */
	@Override
	public void updateDongGuanSecondHandTrend(List<DealdataTrend> list) {
		 if(list !=null&& list.size()>0){
			 Date date = list.get(0).getDate();
				if(date!=null){
					deleteDongGuanSecondHandTrendByDate(date,"东莞");
					insertDongGuanSecondHandTrend(list);
				 }
	        }
	}
	
	/**
	 * 首次抓取数据，主要是为了获取历史数据
	 */
	/*@Override
	public void updateDongGuanSecondHandTrend(List<DealdataTrend> list) {
		 if(list !=null&& list.size()>0){
			 insertDongGuanSecondHandTrend(list);
	        }
	}*/

}
