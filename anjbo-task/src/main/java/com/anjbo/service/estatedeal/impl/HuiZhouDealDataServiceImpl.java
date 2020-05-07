package com.anjbo.service.estatedeal.impl;


import com.anjbo.bean.estatedeal.DealdataTrend;
import com.anjbo.bean.estatedeal.HZDealDto;
import com.anjbo.dao.app.estatedeal.HuiZhouDealDataMapper;
import com.anjbo.service.estatedeal.HuiZhouDealDataService;
import com.anjbo.task.estatedeal.HZDataUtil;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/3/7.
 */
@Transactional
@Service("huiZhouDealDataService")
public class HuiZhouDealDataServiceImpl implements HuiZhouDealDataService {

    @Autowired
    private HuiZhouDealDataMapper huiZhouDealDataMapper;

    @Override
    public Date selectMaxOneHandDate() {
        Date date = huiZhouDealDataMapper.selectMaxOneHandDate();
        if(date==null){
            date= HZDataUtil.DEFAULT_DATE;
        }else {
            date= DateUtils.addDays(date,1);
        }
        return date;
    }

    @Override
    public Date selectMaxSecondHandDate() {
        Date date = huiZhouDealDataMapper.selectMaxSecondHandDate();
        if(date==null){
            date= HZDataUtil.DEFAULT_DATE;
        }else {
            date= DateUtils.addDays(date,1);
        }
        return date;
    }

    @Override
    public int insertHuiZhouOneHandDataBatch(List<HZDealDto> list) {
        int i=0;
        if(list !=null&& list.size()>0){
             i = huiZhouDealDataMapper.insertHuiZhouOneHandDataBatch(list);
        }
        return i;
    }

    @Override
    public int insertHuiZhouSecondHandDataBatch(List<HZDealDto> list) {
        int i=0;
        if(list !=null&& list.size()>0){
            i=huiZhouDealDataMapper.insertHuiZhouSecondHandDataBatch(list);
        }
        return i;
    }

	@Override
	public int deleteHuiZhouSecondHandTrendByDate(Date date,String cityName) {
		
		return huiZhouDealDataMapper.deleteHuiZhouSecondHandTrendByDate(date,cityName);
	}

	@Override
	public int insertHuiZhouSecondHandTrend(List<DealdataTrend> list) {
		
		return huiZhouDealDataMapper.insertHuiZhouSecondHandTrend(list);
	}

	 /**
     * 历史数据抓完后，此方法每次执行更新月数据
     */
	@Override
	public void updateHuiZhouSecondHandTrend(List<DealdataTrend> list) {
		 if(list !=null&& list.size()>0){
			 Date date = list.get(0).getDate();
				if(date!=null){
					deleteHuiZhouSecondHandTrendByDate(date,"惠州");
					insertHuiZhouSecondHandTrend(list);
				 }
	        }
	}
	
	/**
	 * 首次抓取数据，主要是为了获取历史数据
	 */
	/*@Override
	public void updateHuiZhouSecondHandTrend(List<DealdataTrend> list) {
		 if(list !=null&& list.size()>0){
			 insertHuiZhouSecondHandTrend(list);
	        }
	}*/
}
