package com.anjbo.task.estatedeal;


import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;

import com.anjbo.bean.estatedeal.DGDealDto;
import com.anjbo.bean.estatedeal.DealdataAvgPricce;
import com.anjbo.bean.estatedeal.GZDealDto;
import com.anjbo.bean.estatedeal.HZDealDto;
import com.anjbo.bean.estatedeal.SZDealDto;
import com.anjbo.bean.estatedeal.XMDealDto;
import com.anjbo.common.AnjboException;
import com.anjbo.common.ApplicationContextHolder;
import com.anjbo.common.Constants;
import com.anjbo.common.DateUtil;
import com.anjbo.common.Enums;
import com.anjbo.service.estatedeal.DongGuanDealDataService;
import com.anjbo.service.estatedeal.GuangZhouDealDataService;
import com.anjbo.service.estatedeal.HuiZhouDealDataService;
import com.anjbo.service.estatedeal.ShenZhenDealDataService;
import com.anjbo.service.estatedeal.XiaMenDealDataService;
import com.anjbo.utils.AmsUtil;
import com.anjbo.utils.ConfigUtil;
import com.anjbo.utils.HttpUtil;
import com.anjbo.utils.TimestampToDateMorpher;

/**
 * Created by Administrator on 2017/3/8.
 */
public class DealDataTask {

    private static final Log logger= LogFactory.getLog(DealDataTask.class);

    public void run() {
    	logger.info("启动按揭圈各地区数据抓取任务线程start");
    	Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					DealData();
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
				
			}
		});
    	t.start();
    	
		try {
			int i = 0;
			//设置线程超时时间10分钟
			while(t.isAlive() && i < 600){
				Thread.sleep(1000);
				i++;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			if (t.isAlive()) {
				//如果超时，则强制结束线程并发送通知
				t.stop();
				logger.info("数据抓取超时，线程强制结束");
				sendMsg();
			}
		}
    	logger.info("按揭圈各地区数据抓取任务线程end");
    	
    }
    
    public void DealData(){
    	if("true".equals(ConfigUtil.getStringValue("DEALDATATASKCLOSE"))){
    		logger.info("按揭圈各地区数据抓取任务已关闭（默认关闭）");
    		return;
    	}
        logger.info("数据抓取任务启动");
        ApplicationContext applicationContext = ApplicationContextHolder.getApplicationContext();
        ShenZhenDealDataService shenZhenDealDataService = (ShenZhenDealDataService) applicationContext.getBean("shenZhenDealDataService");
        DongGuanDealDataService dongGuanDealDataService= (DongGuanDealDataService) applicationContext.getBean("dongGuanDealDataService");
        XiaMenDealDataService xiaMenDealDataService= (XiaMenDealDataService) applicationContext.getBean("xiaMenDealDataService");
        HuiZhouDealDataService huiZhouDealDataService= (HuiZhouDealDataService) applicationContext.getBean("huiZhouDealDataService");
        GuangZhouDealDataService guangZhouDealDataService= (GuangZhouDealDataService) applicationContext.getBean("guangZhouDealDataService");

        //深圳数据更新
        try {
            logger.info("深圳数据更新开始");
            String result = HttpUtil.get("http://182.254.149.92:8106/task/system/eg/v/getSzData", "UTF-8", 60000, null, null);
            logger.info("请求92服务器数据结果："+result);
            JSONUtils.getMorpherRegistry().registerMorpher(new TimestampToDateMorpher());  	
            JSONObject jsonResult = JSONObject.fromObject(result);
			@SuppressWarnings({ "unchecked", "deprecation" })
			List<SZDealDto> shenZhenSecondHandData =(List<SZDealDto>) JSONArray.toList(jsonResult.getJSONArray("shenZhenSecondHandData"), SZDealDto.class);
			@SuppressWarnings({ "unchecked", "deprecation" })
			List<SZDealDto> shenZhenSecondHandDetail =(List<SZDealDto>) JSONArray.toList(jsonResult.getJSONArray("shenZhenSecondHandDetail"), SZDealDto.class);
			@SuppressWarnings({ "unchecked", "deprecation" })
			List<SZDealDto> shenZhenOneHandData =(List<SZDealDto>) JSONArray.toList(jsonResult.getJSONArray("shenZhenOneHandData"), SZDealDto.class);
//			@SuppressWarnings({ "unchecked", "deprecation" })
//			List<DealdataTrend> shenZhenSecondHandDataTrend =(List<DealdataTrend>) JSONArray.toList(jsonResult.getJSONArray("shenZhenSecondHandDataTrend"), DealdataTrend.class);
            DealdataAvgPricce avgPrcie =(DealdataAvgPricce) JSONObject.toBean(jsonResult.getJSONObject("avgPrcie"), DealdataAvgPricce.class);
//            List<SZDealDto> shenZhenSecondHandData = SZDataUtil.getSZSecondHandData();
//            List<SZDealDto> shenZhenSecondHandDetail = SZDataUtil.getSZSecondHandDetail();
//            List<SZDealDto> shenZhenOneHandData = SZDataUtil.getSZOneHandData();
//            List<DealdataTrend> shenZhenSecondHandDataTrend = SZDataUtil.getSZSecondHandTrend();

            shenZhenDealDataService.updateShenZhenSecondHandDataBatch(shenZhenSecondHandData);
            shenZhenDealDataService.updateShenZhenSecondHandDetailBatch(shenZhenSecondHandDetail);
            shenZhenDealDataService.updateShenZhenOneHandDataBatch(shenZhenOneHandData);
//            shenZhenDealDataService.updateShenZhenSecondHandTrend(shenZhenSecondHandDataTrend);
            
//            DealdataAvgPricce avgPrcie=SZDataUtil.getSZOneHandAvgPrice();
            shenZhenDealDataService.updateShenZhenOneHandAvgPrice(avgPrcie);
            
            
            logger.info("深圳数据更新完成");
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("深圳数据更新异常");
            sendMsg();
        }

        //东莞数据更新
        try {
            logger.info("东莞数据更新开始");
            List<DGDealDto> dongGuanSecondHandData = null;
            List<DGDealDto> dongGuanSecondHandDetail = null;
            try {
                dongGuanSecondHandData=DGDataUtil.getDGSecondHandData();
                dongGuanSecondHandDetail=DGDataUtil.getDGSecondHandDetail();
            } catch (Exception e) {
                int count=3;
                while (dongGuanSecondHandData==null&&dongGuanSecondHandDetail==null){
                    try {
                        if(count<0){
                            break;
                        }
                        dongGuanSecondHandData=DGDataUtil.getDGSecondHandData();
                        dongGuanSecondHandDetail=DGDataUtil.getDGSecondHandDetail();
                        count--;
                    } catch (Exception e1) {
                        //do nothing
                    }
                }
            }
            List<DGDealDto> dongGuanOneHandData = DGDataUtil.getDGOneHandData();
            List<DGDealDto> dongGuanOneHandDetail = DGDataUtil.getDGOneHandDetail();
          
            dongGuanDealDataService.updateDongGuanOneHandDataBatch(dongGuanOneHandData);
            dongGuanDealDataService.updateDongGuanOneHandDetailBatch(dongGuanOneHandDetail);
            dongGuanDealDataService.updateDongGuanSecondHandDataBatch(dongGuanSecondHandData);
            dongGuanDealDataService.updateDongGuanSecondHandDetailBatch(dongGuanSecondHandDetail);
            
//            List<DealdataTrend> dongGuanSecondHandDataTrend = DGDataUtil.getDGSecondHandTrend();
//
//            dongGuanDealDataService.updateDongGuanSecondHandTrend(dongGuanSecondHandDataTrend);
            logger.info("东莞数据更新完成");
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("东莞数据更新异常");
            sendMsg();
        }

        //广州数据更新
        try {
            logger.info("广州数据更新开始");
            Date date = new Date();
            Date oneHandDate = guangZhouDealDataService.selectMaxOneHandDate();
            List<GZDealDto> guangZhouOneHandData = GZDataUtil.getGZOneHandData(DateUtil.getDateByFmt(oneHandDate, "yyyy-MM-dd"), DateUtil.getDateByFmt(date, "yyyy-MM-dd"));
            guangZhouDealDataService.insertGuangZhouOneHandDataBatch(guangZhouOneHandData);

            Date secondHandDate = guangZhouDealDataService.selectMaxSecondHandDate();
            List<GZDealDto> guangZhouSecondHandData = GZDataUtil.getGZSecondHandData(DateUtil.getDateByFmt(secondHandDate, "yyyy-MM-dd"), DateUtil.getDateByFmt(date, "yyyy-MM-dd"));
            guangZhouDealDataService.insertGuangZhouSecondHandDataBatch(guangZhouSecondHandData);
            
//            List<DealdataTrend> guangZhouSecondHandDataTrend = GZDataUtil.getGZSecondHandTrend();
//
//            guangZhouDealDataService.updateGuangZhouSecondHandTrend(guangZhouSecondHandDataTrend);
            
            
            
            logger.info("广州数据更新完成");
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("广州数据更新异常");
            sendMsg();
        }

       //厦门数据更新
        try {
            logger.info("厦门数据更新开始");
            List<XMDealDto> xmData = XMDataUtil.getXMData();
            xiaMenDealDataService.updateXMDataBatch(xmData);
            logger.info("厦门数据更新完成");
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("厦门数据更新异常");
            sendMsg();
        }

        //惠州数据更新
        try {
            logger.info("惠州数据更新开始");
            Date date = new Date();
            Date oneHandDate2 = huiZhouDealDataService.selectMaxOneHandDate();
            List<HZDealDto> huiZhouOneHandData = HZDataUtil.getHZOneHandData(DateUtil.getDateByFmt(oneHandDate2,"yyyy-MM-dd"), DateUtil.getDateByFmt(date,"yyyy-MM-dd"));
            huiZhouDealDataService.insertHuiZhouOneHandDataBatch(huiZhouOneHandData);

            Date secondHandDate2 = huiZhouDealDataService.selectMaxSecondHandDate();
            List<HZDealDto> huiZhouSecondHandData = HZDataUtil.getHZSecondHandData(DateUtil.getDateByFmt(secondHandDate2,"yyyy-MM-dd"), DateUtil.getDateByFmt(date,"yyyy-MM-dd"));
            huiZhouDealDataService.insertHuiZhouSecondHandDataBatch(huiZhouSecondHandData);

//            List<DealdataTrend> huiZhouSecondHandDataTrend = HZDataUtil.getHZSecondHandTrend();
//
//            huiZhouDealDataService.updateHuiZhouSecondHandTrend(huiZhouSecondHandDataTrend);
            
            logger.info("惠州数据更新完成");
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("惠州数据更新异常");
            sendMsg();
        }
    }
    public static void main(String[] args) {
		sendMsg();
	}
    public static void sendMsg(){
    	String phoneStr = ConfigUtil.getStringValue("ANJBO_APP_DATA_TASK_PHONE");
    	if(StringUtils.isNotEmpty(phoneStr)){
    		String[] phones = phoneStr.split(",");
    		String content = "按揭圈各地区数据抓取异常，请处理并排查问题";
    		String ipWhite = ConfigUtil.getStringValue(Enums.GLOBAL_CONFIG_KEY.AMS_SMS_IPWHITE.toString());
    		for (String phone : phones) {
    			try {
    				AmsUtil.smsSend(phone, ipWhite,content , Constants.SMSCOMEFROM);
    			} catch (AnjboException e1) {
    				e1.printStackTrace();
    				logger.error("发送短信失败："+content);
    			}
    		}
    	}
    }
    
}
