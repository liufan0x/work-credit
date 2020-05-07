package com.anjbo.service.house.impl;

import com.anjbo.dao.app.house.HouseMapper;
import com.anjbo.dao.app.house.HousePriceDayMapper;
import com.anjbo.dao.app.house.HousePriceMouthMapper;
import com.anjbo.dao.mort.house.EnquiryMarketBargainMapper;
import com.anjbo.service.house.HousePriceMouthService;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by zsy on 2017/3/3.
 */
@Service("housePriceMouthService")
public class HousePriceMouthServiceImpl implements HousePriceMouthService {

    @Autowired
    private HousePriceMouthMapper housePriceMouthMapper;

    @Autowired
    private HouseMapper houseMapper;

    @Autowired
    private HousePriceDayMapper housePriceDayMapper;
    @Autowired
    private EnquiryMarketBargainMapper enquiryMarketBargainMapper;

    @Override
    public int insertHousePriceMouth(Map<String, Object> params) {


        return housePriceMouthMapper.insertHousePriceMouth(params);
    }

    @Override
    public int updateHousePriceMouth(Map<String, Object> params) {
        return housePriceMouthMapper.updateHousePriceMouth(params);
    }

    @Override
    public List<Map<String, Object>> selectHousePriceMouth(Map<String, Object> params) {
        return housePriceMouthMapper.selectHousePriceMouth(params);
    }

    @Override
    public int deleteHousePriceMouth(Map<String, Object> params) {
        return housePriceMouthMapper.deleteHousePriceMouth(params);
    }
    /**
     *更新价格信息定时任务（每月）
     *
     * @return
     */
    @Override
    public int updateHouseBaseAndPriceMouthTask() {
        int temp= 0;
        Date date=new Date();
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM") ;
        String dateFormat=df.format(date);
        Date priceMonthTime= null;
        try {
            priceMonthTime = df.parse(dateFormat);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(dateFormat);

        try {
            //得到房产信息记录
            List<Map<String,Object>> list = housePriceDayMapper.selectHouseBase();
            for (int i=0;i<list.size();i++){
                Map<String,Object> houseBaseMap=list.get(i);//获取base表房产信息(单个)
                Map<String,Object> avgDayWaterMap=housePriceMouthMapper.avgHouseSumPriceDay(houseBaseMap);//对所有日表信息求均价返回
                avgDayWaterMap.put("priceMonthTime",priceMonthTime);
                avgDayWaterMap.put("orderNo",MapUtils.getString(houseBaseMap,"orderNo"));
                temp=housePriceMouthMapper.insertHousePriceMouth(avgDayWaterMap);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return temp;

    }
    //定时更新小区房产信息
    @Override
    public int updataMarketBargain() {
        Map<String,Object> map=new HashedMap();
        map.put("signDate",new Date());
        List<Map<String,Object>> list=enquiryMarketBargainMapper.enquiryMarketBarginByDate(map);
        int temp=0;
        for(int i=0;i<list.size();i++){
            Map<String,Object> map1=list.get(i);
            temp=houseMapper.insertEnquiryMarketBargain(map1);
        }
        return temp;
    }

    @Override
    public int updataMarketBargainTemp() {
        List<Map<String,Object>> list=enquiryMarketBargainMapper.enquiryMarketBargin();
        int temp=0;
        for(int i=0;i<list.size();i++){
            Map<String,Object> map1=list.get(i);
            temp=houseMapper.insertEnquiryMarketBargain(map1);
        }
        return temp;
    }

}
