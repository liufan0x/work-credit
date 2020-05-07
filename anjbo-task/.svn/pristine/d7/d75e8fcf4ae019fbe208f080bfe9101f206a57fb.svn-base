package com.anjbo.task.estateprice;

import com.anjbo.bean.estateprice.NetworkOfferDto;
import com.anjbo.bean.estateprice.SZCFJDto;
import com.anjbo.bean.estateprice.TZCPropertyDto;
import com.anjbo.common.ApplicationContextHolder;
import com.anjbo.common.DateUtil;
import com.anjbo.service.estateprice.CFJService;
import com.anjbo.service.estateprice.NetworkOfferService;
import com.anjbo.service.estateprice.TZCPropertyService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;

import java.util.*;


public class CFJTask {

    private static Log logger = LogFactory.getLog(CFJTask.class);

    public void run2() {
        try {
            logger.info("查房价任务启动");
            ApplicationContext applicationContext = ApplicationContextHolder.getApplicationContext();
            CFJService cfjService = (CFJService) applicationContext.getBean("cfjService");
            TZCPropertyService tzcPropertyService = (TZCPropertyService) applicationContext.getBean("tzcPropertyService");
            NetworkOfferService networkOfferService = (NetworkOfferService) applicationContext.getBean("networkOfferService");

            List<SZCFJDto> cfjDtos = CFJUtil.getLatestSZCFJDtos();
            logger.info("获取查房价数据" + cfjDtos.size() + "条");
            cfjService.updateShenZhenCFJDtoBatch(cfjDtos);
            logger.info("插入查房价数据" + cfjDtos.size() + "条");
            List<SZCFJDto> shenZhenCFJDtos = cfjService.selectShenZhenCFJDtoAllCurrentDay();
            List<NetworkOfferDto> networkOfferDtos = networkOfferService.selectNetworkOfferDtoAll();
            List<TZCPropertyDto> tzcPropertyDtos = tzcPropertyService.selectPropertyMaxAndAVGPrice();
            for (TZCPropertyDto dto : tzcPropertyDtos) {
                int count = 2;
                Double total = 0d;
                total += dto.getMaxPrice();
                total += dto.getAvgPrice();
                final String city = dto.getCity();
                final String propertyName = dto.getPropertyName();
                List<NetworkOfferDto> tmpList = (List<NetworkOfferDto>) CollectionUtils.select(networkOfferDtos,
                        new Predicate() {
                            public boolean evaluate(Object arg0) {
                                NetworkOfferDto networkOfferDto = (NetworkOfferDto) arg0;
                                return city.equals(networkOfferDto.getCity()) && propertyName.equals(networkOfferDto.getPropertyName());
                            }
                        });
                if (tmpList != null && tmpList.size() == 1) {
                    count++;
                    NetworkOfferDto networkOfferDto = tmpList.get(0);
                    total += networkOfferDto.getAvgPrice();
                }
                if (city.equals("深圳")) {
                    List<SZCFJDto> tmpList2 = (List<SZCFJDto>) CollectionUtils.select(shenZhenCFJDtos,
                            new Predicate() {
                                public boolean evaluate(Object arg0) {
                                    SZCFJDto shenZhenCFJDto = (SZCFJDto) arg0;
                                    return propertyName.equals(shenZhenCFJDto.getPropertyName());
                                }
                            });
                    if (tmpList2 != null && tmpList2.size() == 1) {
                        count++;
                        SZCFJDto shenZhenCFJDto = tmpList2.get(0);
                        total += shenZhenCFJDto.getAvgPrice();
                    }
                }
                Double result = total / count;
                dto.setEstatePrice(result);
                dto.setDate(new Date());
            }
            logger.info("更新时间：" + DateUtil.getDateByFmt(new Date(), "yyyy-MM-dd"));
            logger.info("准备更新均价数据" + tzcPropertyDtos.size() + "条");
            cfjService.updateTZCPropertyDtoByDate(tzcPropertyDtos);
            cfjService.insertTZCPropertyDtoByDate(tzcPropertyDtos);
            logger.info("更新均价数据" + tzcPropertyDtos.size() + "条");
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("There was an exception in this update");
        }
    }


    public void run() throws Exception {
        logger.info("均价计算任务开始");
        try {
            ApplicationContext applicationContext = ApplicationContextHolder.getApplicationContext();
            CFJService cfjService = (CFJService) applicationContext.getBean("cfjService");
            TZCPropertyService tzcPropertyService = (TZCPropertyService) applicationContext.getBean("tzcPropertyService");
            NetworkOfferService networkOfferService = (NetworkOfferService) applicationContext.getBean("networkOfferService");

            List<NetworkOfferDto> networkOfferDtos = networkOfferService.selectNetworkOfferDtoAll();
            List<SZCFJDto> cfjDtos = CFJUtil.getLatestSZCFJDtos();
            cfjService.updateShenZhenCFJDtoBatch(cfjDtos);
            cfjService.deleteTZCPropertyDtoAll();
            HashMap<Integer, Integer> areas = new HashMap<Integer, Integer>();
            areas.put(0, 70);
            areas.put(70, 90);
            areas.put(90, 120);
            areas.put(120, 140);
            areas.put(140, 180);
            areas.put(180, 9999);
            //以同志诚数据为基础计算
            for (Map.Entry<Integer, Integer> entry : areas.entrySet()) {
                List<TZCPropertyDto> tzcPropertyDtos = tzcPropertyService.selectPropertyMaxAndAVGPriceByArea(entry.getKey(), entry.getValue());
                for (TZCPropertyDto dto : tzcPropertyDtos) {
                    int count = 2;
                    Double total = 0d;
                    total += dto.getMaxPrice();
                    total += dto.getAvgPrice();
                    final String city = dto.getCity();
                    final String propertyName = dto.getPropertyName();
                    List<NetworkOfferDto> tmpList = (List<NetworkOfferDto>) CollectionUtils.select(networkOfferDtos,
                            new Predicate() {
                                public boolean evaluate(Object arg0) {
                                    NetworkOfferDto networkOfferDto = (NetworkOfferDto) arg0;
                                    return city.equals(networkOfferDto.getCity()) && propertyName.equals(networkOfferDto.getPropertyName());
                                }
                            });
                    if (tmpList != null && tmpList.size() == 1) {
                        count++;
                        NetworkOfferDto networkOfferDto = tmpList.get(0);
                        total += networkOfferDto.getAvgPrice();
                    }
                    if (city.equals("深圳")) {
                        List<SZCFJDto> tmpList2 = (List<SZCFJDto>) CollectionUtils.select(cfjDtos,
                                new Predicate() {
                                    public boolean evaluate(Object arg0) {
                                        SZCFJDto shenZhenCFJDto = (SZCFJDto) arg0;
                                        return propertyName.equals(shenZhenCFJDto.getPropertyName());
                                    }
                                });
                        if (tmpList2 != null && tmpList2.size() == 1) {
                            count++;
                            SZCFJDto shenZhenCFJDto = tmpList2.get(0);
                            total += shenZhenCFJDto.getAvgPrice();
                        }
                    }
                    Double result = total / count;
                    dto.setEstatePrice(result);
                    dto.setDate(new Date());
                    dto.setAreaRange(entry.getKey() + "-" + entry.getValue());
                }

                cfjService.insertTZCPropertyDtoBatch(tzcPropertyDtos);
                cfjService.insertTZCPropertyDtoByDate(tzcPropertyDtos);
            }

            //以网络报盘为基础进行计算
           /* for (Map.Entry<Integer, Integer> entry : areas.entrySet()) {
                List<NetworkOfferDto> networkDtos =networkOfferService.selectNetworkOfferDtoAllByArea(entry.getKey(),entry.getValue());
                for(NetworkOfferDto dto:networkDtos){
                    TZCPropertyDto tzcPropertyDto = new TZCPropertyDto();
                    tzcPropertyDto.setPropertyName(dto.getPropertyName());
                    tzcPropertyDto.setCity(dto.getCity());
                    tzcPropertyDto.setEstatePrice(dto.getAvgPrice());
                    tzcPropertyDto.setAreaRange(entry.getKey()+"-"+entry.getValue());
                    tzcPropertyDto.setDate(new Date());
                    Integer count=cfjService.selectTZCPropertyDtoCount(tzcPropertyDto);
                    if(count==0){
                        ArrayList<TZCPropertyDto> tzcPropertyDtos = new ArrayList<TZCPropertyDto>();
                        tzcPropertyDtos.add(tzcPropertyDto);
                        cfjService.insertTZCPropertyDtoBatch(tzcPropertyDtos);
                        cfjService.insertTZCPropertyDtoByDate(tzcPropertyDtos);
                    }
                }
            }*/
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("均价计算任务异常");
        }
        logger.info("均价计算任务完成");
    }

}
