package com.anjbo.service.estatedeal.impl;

import com.anjbo.bean.estatedeal.DealdataAvgPricce;
import com.anjbo.bean.estatedeal.DealdataTrend;
import com.anjbo.bean.estatedeal.SZDealDto;
import com.anjbo.dao.app.estatedeal.ShenZhenDealDataMapper;
import com.anjbo.service.estatedeal.ShenZhenDealDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/7.
 */
@Transactional
@Service("shenZhenDealDataService")
public class ShenZhenDealDataServiceImpl implements ShenZhenDealDataService {

    @Autowired
    private ShenZhenDealDataMapper shenZhenDealDataMapper;

    @Override
    public int insertShenZhenOneHandDataBatch(List<SZDealDto> list) {
        return shenZhenDealDataMapper.insertShenZhenOneHandDataBatch(list);
    }

    @Override
    public int deleteShenZhenOneHandDataByDate(Date date) {
        return shenZhenDealDataMapper.deleteShenZhenOneHandDataByDate(date);
    }

    @Override
    public void updateShenZhenOneHandDataBatch(List<SZDealDto> list) {
        if(list !=null&& list.size()>0){
            Date date = list.get(0).getDate();
            if(date!=null){
                deleteShenZhenOneHandDataByDate(date);
                insertShenZhenOneHandDataBatch(list);
            }
        }
    }

    @Override
    public int insertShenZhenSecondHandDataBatch(List<SZDealDto> list) {
        return shenZhenDealDataMapper.insertShenZhenSecondHandDataBatch(list);
    }

    @Override
    public int deleteShenZhenSecondHandDataByDate(Date date) {
        return shenZhenDealDataMapper.deleteShenZhenSecondHandDataByDate(date);
    }

    @Override
    public void updateShenZhenSecondHandDataBatch(List<SZDealDto> list) {
        if(list !=null&& list.size()>0){
            Date date = list.get(0).getDate();
            if(date!=null){
                deleteShenZhenSecondHandDataByDate(date);
                insertShenZhenSecondHandDataBatch(list);
            }
        }
    }

    @Override
    public int insertShenZhenSecondHandDetailBatch(List<SZDealDto> list) {
        return shenZhenDealDataMapper.insertShenZhenSecondHandDetailBatch(list);
    }

    @Override
    public int deleteShenZhenSecondHandDetailByDate(Date date) {
        return shenZhenDealDataMapper.deleteShenZhenSecondHandDetailByDate(date);
    }

    @Override
    public void updateShenZhenSecondHandDetailBatch(List<SZDealDto> list) {
        if(list !=null&& list.size()>0){
            Date date = list.get(0).getDate();
            if(date!=null){
                deleteShenZhenSecondHandDetailByDate(date);
                insertShenZhenSecondHandDetailBatch(list);
            }
        }
    }

    
    @Override
	public int insertShenZhenSecondHandTrend(List<DealdataTrend> list) {
		
		return shenZhenDealDataMapper.insertShenZhenSecondHandTrend(list);
	}
    
	@Override
	public int deleteShenZhenSecondHandTrendByDate(Date date,String cityName) {
		
		return shenZhenDealDataMapper.deleteShenZhenSecondHandTrendByDate(date,cityName);
	}

    /**
     * 历史数据抓完后，此方法每次执行更新月数据
     */
	@Override
	public void updateShenZhenSecondHandTrend(List<DealdataTrend> list) {
		 if(list !=null&& list.size()>0){
			 Date date = list.get(0).getDate();
				if(date!=null){
					deleteShenZhenSecondHandTrendByDate(date,"深圳");
					insertShenZhenSecondHandTrend(list);
				 }
	        }
	}
	/**
	 * 首次抓取数据，主要是为了获取历史数据
	 */
/*	@Override
	public void updateShenZhenSecondHandTrend(List<DealdataTrend> list) {
		 if(list !=null&& list.size()>0){
					 insertShenZhenSecondHandTrend(list);
	        }
	}*/

	@Override
	public int insertShenZhenOneHandAvgPrice(DealdataAvgPricce dt) {
		
		return shenZhenDealDataMapper.insertShenZhenOneHandAvgPrice(dt);
	}

	@Override
	public int deleteShenZhenOneHandAvgPriceByDate(DealdataAvgPricce dt) {
		
		return shenZhenDealDataMapper.deleteShenZhenOneHandAvgPriceByDate(dt);
	}

	@Override
	public void updateShenZhenOneHandAvgPrice(DealdataAvgPricce dt) {
		 if(dt.getDate()!=null){
			 deleteShenZhenOneHandAvgPriceByDate(dt);
			 insertShenZhenOneHandAvgPrice(dt);
		 }
		
	}
}
