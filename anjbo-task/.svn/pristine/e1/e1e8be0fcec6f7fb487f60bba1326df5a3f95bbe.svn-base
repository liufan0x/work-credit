package com.anjbo.service.estatedeal.impl;


import com.anjbo.bean.estatedeal.DealdataTrend;
import com.anjbo.bean.estatedeal.GZDealDto;
import com.anjbo.dao.app.estatedeal.GuangZhouDealDataMapper;
import com.anjbo.service.estatedeal.GuangZhouDealDataService;
import com.anjbo.task.estatedeal.GZDataUtil;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/3/8.
 */
@Transactional
@Service("guangZhouDealDataService")
public class GuangZhouDealDataServiceImpl implements GuangZhouDealDataService {

    @Autowired
    private GuangZhouDealDataMapper guangZhouDealDataMapper;

    @Override
    public Date selectMaxOneHandDate() {
        Date date = guangZhouDealDataMapper.selectMaxOneHandDate();
        if (date == null) {
            date = GZDataUtil.DEFAULT_DATE;
        } else {
            date = DateUtils.addDays(date, 1);
        }
        return date;
    }

    @Override
    public Date selectMaxSecondHandDate() {
        Date date = guangZhouDealDataMapper.selectMaxSecondHandDate();
        if (date == null) {
            date = GZDataUtil.DEFAULT_DATE;
        } else {
            date = DateUtils.addDays(date, 1);
        }
        return date;
    }

    @Override
    public int insertGuangZhouOneHandDataBatch(List<GZDealDto> list) {
        int i = 0;
        if (list != null && list.size() > 0) {
            i = guangZhouDealDataMapper.insertGuangZhouOneHandDataBatch(list);
        }
        return i;
    }

    @Override
    public int insertGuangZhouSecondHandDataBatch(List<GZDealDto> list) {
        int i = 0;
        if (list != null && list.size() > 0) {
            i = guangZhouDealDataMapper.insertGuangZhouSecondHandDataBatch(list);
        }
        return i;
    }

	@Override
	public int deleteGuangZhouSecondHandTrendByDate(Date date,String cityName) {

		return guangZhouDealDataMapper.deleteGuangZhouSecondHandTrendByDate(date,cityName);
	}

	@Override
	public int insertGuangZhouSecondHandTrend(List<DealdataTrend> list) {
	
		return guangZhouDealDataMapper.insertGuangZhouSecondHandTrend(list);
	}
	 /**
     * 历史数据抓完后，此方法每次执行更新月数据
     */
	@Override
	public void updateGuangZhouSecondHandTrend(List<DealdataTrend> list) {
		 if(list !=null&& list.size()>0){
			 Date date = list.get(0).getDate();
				if(date!=null){
					deleteGuangZhouSecondHandTrendByDate(date,"广州");
					insertGuangZhouSecondHandTrend(list);
				 }
	        }
	}
	
	/**
	 * 首次抓取数据，主要是为了获取历史数据
	 */
	/*public void updateGuangZhouSecondHandTrend(List<DealdataTrend> list) {
		if(list !=null&& list.size()>0){
			insertGuangZhouSecondHandTrend(list);
		}
	}*/
	
	
}
