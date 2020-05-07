package com.anjbo.service.house.impl;

import com.anjbo.bean.house.HouseRpValueDto;
import com.anjbo.dao.app.house.HouseMapper;
import com.anjbo.dao.app.house.HousePriceDayMapper;
import com.anjbo.dao.mort.house.HouseRentPriceMapper;
import com.anjbo.service.house.HousePriceDayService;
import com.anjbo.service.house.HouseService;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/3.
 */


@Service("housePriceDayService")
public class HousePriceDayServiceImpl implements HousePriceDayService {

    @Autowired
    private HouseMapper houseMapper;

    @Autowired
    private HousePriceDayMapper housePriceDayMapper;
    @Autowired
    private HouseRentPriceMapper houseRentPriceMapper;
    /**
     *更新价格信息定时任务（每日）
     *
     * @return
     */
    @Override
    public int updateHouseBaseAndPriceDayTask() {
        int update=0,del=0;
        int temp=0;
        try {
            //得到房产信息记录
            List<Map<String,Object>> list = housePriceDayMapper.selectHouseBase();
            for (int i=0;i<list.size();i++){

                try {
                    Map<String,Object> houseBaseMap=list.get(i);
                    if(MapUtils.getIntValue(houseBaseMap,"state")==0);
                    {
                        Double lostPrice=MapUtils.getDouble(houseBaseMap,"price");
                        if(lostPrice==null){
                            lostPrice=0.0;
                        }
                        //lostPrice=0.0;
                        this.computePriceUpdate(houseBaseMap,lostPrice);//更新计算价格
                        temp=houseMapper.updateHouse(houseBaseMap);
                        houseBaseMap.put("priceDayTime",new Date());
                        //temp=housePriceDayMapper.insertHousePriceDay(houseBaseMap);
                        update++;
                    }
                    if(MapUtils.getIntValue(houseBaseMap,"state")==1){
                        temp=houseMapper.deleteHouseBase(houseBaseMap);
                        del++;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("房产更新任务：共更新数据"+update+"条");
        System.out.println("房产更新任务：共删除数据"+del+"条");
        return temp;

    }

    /**
     * 获取单价（根据纬度来获取单价）
     * @param params
     * @return
     */
    private Map<String,Object> getAvgValue(Map<String,Object> params){
        List<Map<String,Object>> list=new ArrayList();
        list=houseMapper.getHousePrice(params);//获取价格结果集
        Double loatSumPrice=0.0;//所有记录总值
        Double avgPrice=0.0;//均单价
        Double area=MapUtils.getDouble(params,"acreage");//获取面积
        String AreaKey=this.retrunAvgArea(area);//获取面积key
        String bulidName=MapUtils.getString(params,"buildingName");//楼栋
        for(int i=0;i<list.size();i++){
            Map<String,Object> map=list.get(i);
            //判断楼栋相同，面积在范围内返回均价
            String buildingName=MapUtils.getString(map,"buildingName");
            String AreaMapString=MapUtils.getString(map,"area");
            //等值匹配，匹配面积范围，匹配楼栋名称
            if(StringUtils.equals(AreaKey,AreaMapString) && StringUtils.equals(bulidName,buildingName)){
                avgPrice=MapUtils.getDouble(map,"estatePrice");
            }
            //统计总价
            loatSumPrice+=MapUtils.getDouble(map,"estatePrice");
        }
        Map<String,Object> priceMap=new HashedMap();
        if(avgPrice!=null && avgPrice>0){
            priceMap.put("avgPrice",avgPrice);
        }else if(loatSumPrice!=null && loatSumPrice>0){
            int lastCount=list.size();
            loatSumPrice=loatSumPrice/lastCount;
            priceMap.put("loatSumPrice",loatSumPrice*0.95);

        }else{

        }



        return priceMap;



    }
    //返回均单价表相对面积范围
    private String retrunAvgArea(Double area){
        if(area!=null){
            if(area>140){
                return "180-9999";
            }else if(area>=120){
                return "120-140";
            }else if(area>=90){
                return "90-120";
            }else if(area>=70){
                return "70-90";
            }else if(area>=1){
                return "0-70";
            }
        }
        return null;
    }

    public Map<String,Object> computePriceUpdate(Map<String, Object> params,Double unitPrice){
        double sumPrice=0.0;//总价
        double loanablePrice=0.0;//可贷金额
        double appreciation=0.0;//增值
        double price=0.0;//单价
        Double boughtPrice=MapUtils.getDouble(params,"boughtPrice");//获取购买价格参数
        Double loanBalance=MapUtils.getDouble(params,"loanBalance");//获取貸款余额参数
        Double acreage=MapUtils.getDouble(params,"acreage");//获取面积

        Double rp=this.getRpValue(params);//rp值
        //均单价
        Double avgPrice=0.0;
        //物业均单价
        Double loatSumPrice=0.0;

        try {
            rp=this.getRpValue(params);
            Map<String,Object> priceAvgMap=this.getAvgValue(params);
            avgPrice=MapUtils.getDoubleValue(priceAvgMap,"avgPrice",0.0);//匹配纬度获得单价

            loatSumPrice=MapUtils.getDoubleValue(priceAvgMap,"loatSumPrice",0.0);//匹配失败返回物业均价
            if(avgPrice!=null||unitPrice!=null){
                if(unitPrice<avgPrice*0.8){
                    unitPrice=0.0;
                }
                if(avgPrice!=null&&avgPrice>0){
                    //均单价和询价接口同在取两者之和参与计算
                    if(unitPrice!=null && unitPrice>0){
                        Double loadPrice=avgPrice*0.8;//保留平均价格80%
                        Double rpPrice=avgPrice*0.2*rp;//取均价20%参与计算RP值
                        avgPrice=rpPrice+loadPrice+unitPrice;
                        avgPrice=avgPrice/2;
                    }else{
                        //询价接口返回单价为空或小于0时只取均单价参与计算
                        Double loadPrice=avgPrice-avgPrice*0.2;
                        Double rpPrice=avgPrice*0.2*rp;
                        avgPrice=rpPrice+loadPrice;
                    }
                    sumPrice=avgPrice * acreage;//总价
                    appreciation=sumPrice-boughtPrice;
                    loanablePrice= sumPrice * 0.7;
                }else {
                    /**均单价价为空或小于0时取询价接口单价参与计算
                    if(unitPrice!=null && unitPrice>0){
                     Double loadPrice=unitPrice-unitPrice*0.2;
                     Double rpPrice=unitPrice*0.2*rp;
                     avgPrice=rpPrice+loadPrice;
                     }else{
                     //询价接口，均单价皆为空时返回物业均价
                     Double loadPrice=loatSumPrice-loatSumPrice*0.2;
                     Double rpPrice=loatSumPrice*0.2*rp;
                     avgPrice=rpPrice+loadPrice;
                     }
                    */

                    //询价接口，均单价皆为空时返回物业均价
                    Double loadPrice=loatSumPrice-loatSumPrice*0.2;
                    Double rpPrice=loatSumPrice*0.2*rp;
                    avgPrice=rpPrice+loadPrice;
                    sumPrice=avgPrice * acreage;//总价
                    appreciation=sumPrice-boughtPrice;
                    loanablePrice= sumPrice * 0.7;
                }

            }

        } catch (Exception e) {
            avgPrice=0.0;
            e.printStackTrace();
        }


        //租金
        double rentalPrice=0.0;
        try {
            rentalPrice=this.getRentPrice(params);

        } catch (Exception e) {
            rentalPrice=0.0;


            e.printStackTrace();
        }
        if(Double.isNaN(rentalPrice)){
            rentalPrice=0.0;
        }if(Double.isNaN(sumPrice)){
            rentalPrice=0.0;
        }

        params.put("sumPrice",new BigDecimal(sumPrice).setScale(2,BigDecimal.ROUND_HALF_UP));//现总价
        params.put("price",new BigDecimal(avgPrice).setScale(2,BigDecimal.ROUND_HALF_UP));//现单价
        params.put("boughtPrice",new BigDecimal(boughtPrice).setScale(2,BigDecimal.ROUND_HALF_UP));//购买价格
        params.put("appreciation",new BigDecimal(appreciation).setScale(2,BigDecimal.ROUND_HALF_UP));//现增值
        params.put("loanBalance",new BigDecimal(loanBalance).setScale(2,BigDecimal.ROUND_HALF_UP));//贷款余额
        params.put("loanablePrice",new BigDecimal(loanablePrice).setScale(2,BigDecimal.ROUND_HALF_UP));//可贷金额
        params.put("rentalPrice",new BigDecimal(rentalPrice).setScale(2,BigDecimal.ROUND_HALF_UP));//租金


        //rp值
        return params;
    }
    /**
     * 获取租金
     * @param params
     * @return
     */
    private double getRentPrice(Map<String,Object> params){
        List<Map<String,Object>> rentPriceListMap=houseRentPriceMapper.getRentalPrice(params);//获取租金
        //获取输入面积
        Double userArea=MapUtils.getDouble(params,"acreage");
        Double rentPrice=0.0;
        int tempSize=0;
        if(userArea>180){
            //用户匹配输入范围180-9999
            for(int i=0;i<rentPriceListMap.size();i++){
                Map<String,Object> map=rentPriceListMap.get(i);
                Double area=MapUtils.getDouble(map,"area");
                if(area>180){
                    rentPrice+=MapUtils.getDouble(map,"rentalPrice");
                    tempSize++;
                }

            }


        }else if(userArea>140){
            //用户匹配输入范围140-180
            for(int i=0;i<rentPriceListMap.size();i++){
                Map<String,Object> map=rentPriceListMap.get(i);
                Double area=MapUtils.getDouble(map,"area");
                if(area>140 && area<=180){
                    rentPrice+=MapUtils.getDouble(map,"rentalPrice");
                    tempSize++;
                }
            }

        }
        else if(userArea>120){
            //用户匹配输入范围120-140
            for(int i=0;i<rentPriceListMap.size();i++){
                Map<String,Object> map=rentPriceListMap.get(i);
                Double area=MapUtils.getDouble(map,"area");
                if(area>120 && area<=140){
                    rentPrice+=MapUtils.getDouble(map,"rentalPrice");
                    tempSize++;
                }

            }

        }else if(userArea>110){
            //用户匹配输入范围110-120
            for(int i=0;i<rentPriceListMap.size();i++){
                Map<String,Object> map=rentPriceListMap.get(i);
                Double area=MapUtils.getDouble(map,"area");
                if(area>110 && area<=120){
                    rentPrice+=MapUtils.getDouble(map,"rentalPrice");
                    tempSize++;

                }
            }

        }else if(userArea>90){
            //用户匹配输入范围90-110
            for(int i=0;i<rentPriceListMap.size();i++){
                Map<String,Object> map=rentPriceListMap.get(i);
                Double area=MapUtils.getDouble(map,"area");
                if(area>90 && area<=110){
                    rentPrice+=MapUtils.getDouble(map,"rentalPrice");
                    tempSize++;

                }
            }

        }else if(userArea>70){
            //用户匹配输入范围70-90
            for(int i=0;i<rentPriceListMap.size();i++){
                Map<String,Object> map=rentPriceListMap.get(i);
                Double area=MapUtils.getDouble(map,"area");
                if(area>70 && area<=90){
                    rentPrice+=MapUtils.getDouble(map,"rentalPrice");
                    tempSize++;
                }

            }

        }else if(userArea>=1){
            //用户匹配输入范围0-70
            for(int i=0;i<rentPriceListMap.size();i++){
                Map<String,Object> map=rentPriceListMap.get(i);
                Double area=MapUtils.getDouble(map,"area");
                if(area<=70){
                    rentPrice+=MapUtils.getDouble(map,"rentalPrice");
                    tempSize++;
                }

            }

        }

        if(rentPrice>0)
        {
            rentPrice=rentPrice/tempSize;
            return rentPrice*userArea;
        }else{
            for(int i=0;i<rentPriceListMap.size();i++){

                Map<String,Object> mapTemp=rentPriceListMap.get(i);
                rentPrice+=MapUtils.getDouble(mapTemp,"rentalPrice");
                tempSize++;

            }
            rentPrice=rentPrice/tempSize;
            return rentPrice*userArea;
        }
    }
    //获取楼层范围
    private String getFloor(Double f){
        String floorKey="";
        if(f!=null){
            if(f>15){
                floorKey="15层以上";
            }else if(f>=9){
                floorKey="9-15层";
            }else if(f>=7){
                floorKey="7-8层";
            }else if(f>=3){
                floorKey="3-6层";
            }else if(f>=1){
                floorKey= "1-2层";
            }

        }
        return floorKey;


    }
    //获取面积范围
    private String getArea(Double a){
        String areaKey="";
        if(a!=null){
            if(a>146){
                areaKey="146㎡以上";
            }else if(a>=121){
                areaKey="121-145㎡";
            }else if(a>=81){
                areaKey= "81-120㎡";
            }else if(a>=61){
                areaKey="61-80㎡";
            }else if(a>=1){
                areaKey="1-60㎡";
            }

        }
        return areaKey;


    }
    private Double getRpValue(Map<String, Object> params){
        Double RpValue=0.0;
        /*-----------根据输入匹配数据库Rp---key----------------*/
        String houseTypeKey="";//暂无户型配置参数
        String placeTypeKey=MapUtils.getString(params,"placeType");//朝向参数
        String foolerKey=this.getFloor(MapUtils.getDouble(params,"floor"));//通过方法得到rp默认楼层配置key
        String acreageKey=this.getArea(MapUtils.getDouble(params,"acreage"));
      /*-----------取数据库rp值配置信息----------------------*/
        List<HouseRpValueDto> rps = houseMapper.selectRpValue();//获取默认配置rp值参数
        //进行参数匹配得到对应rp值
        Double foolerRp=this.getRpValueByKey(foolerKey,rps);
        Double acreageRp=this.getRpValueByKey(acreageKey,rps);
        Double placeTypeRp=this.getRpValueByKey(placeTypeKey,rps);
        RpValue=foolerRp*acreageRp*placeTypeRp;
        if(RpValue>0)
            return RpValue;
        else
            return 1.0;
    }
    //Rp值参数匹配
    private Double getRpValueByKey(String key, List<HouseRpValueDto> rps){
        for(HouseRpValueDto rp:rps){
            if(rp.getKey().equals(key)){
                return rp.getValue();
            }
        }
        return 1.0;//默认返回1
    }

}
