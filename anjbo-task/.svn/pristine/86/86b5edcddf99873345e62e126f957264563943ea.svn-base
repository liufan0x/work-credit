package com.anjbo.task.house;


import com.anjbo.common.ApplicationContextHolder;

import com.anjbo.service.house.HousePriceMouthService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by zsy on 2017/3/8.
 */
public class HouseMarketBargainTask {

    private static Log logger= LogFactory.getLog(HouseMarketBargainTask.class);

    public void run(){

        ApplicationContext applicationContext = ApplicationContextHolder.getApplicationContext();

        HousePriceMouthService housePriceMouthService = (HousePriceMouthService) applicationContext.getBean("housePriceMouthService");
        int state=0;
        try {
            state=housePriceMouthService.updataMarketBargain();
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
            Date d=new Date();
            String dateTime=sdf.format(d);
            logger.info("==========>小区成交月数据更新成功---("+state+")=====:"+dateTime+"=======>");
        } catch (Exception e) {
            logger.info("==========>小区成交月数据更新失败---("+state+")============>");
            e.printStackTrace();
        }


    }

}
