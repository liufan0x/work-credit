package com.anjbo.manager;

import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 开箱
 * Created by lichao on 2018/1/8.
 */
public class ElementManager {

    protected static final Log logger = LogFactory.getLog(ElementManager.class);

    private final static int times = 20;//开箱等待 尝试次数
    private final static int waitTime = 1000;//开箱等待 每次等待时间
    private final static int effectiveTime = 1000 * 60 * 5;//重新开箱的有效时间


    private static ElementManager elementManager;
    private List<Map<String,Object>> elements;

    public static synchronized ElementManager getInstance() {
        if (elementManager == null) {
            elementManager = new ElementManager();
        }
        return elementManager;
    }

    private ElementManager(){
        elements = new CopyOnWriteArrayList<Map<String,Object>>(
                new LinkedList<Map<String,Object>>());
    }

    private void addElement(Map<String,Object> element) {
        long time = new Date().getTime();
        for (Map<String,Object> map : elements) {
            if (MapUtils.getString(map,"id").equals(MapUtils.getString(element,"id"))
                    && (time - MapUtils.getLong(element,"time")) < effectiveTime) {
                logger.info("数据已存在缓存中，不能重复添加id=" + MapUtils.getString(element,"id"));
                return;
            }
        }
        element.put("time",time);
        elements.add(element);
    }

    /**
     * 查档入口
     *
     * @return
     * lazyLoadingTime遍历次数
     */
    @SuppressWarnings("static-access")
    public RespDataObject<Map<String,Object>> openBoxByeId(Map<String,Object> element) {
        RespDataObject<Map<String,Object>> result = new RespDataObject<Map<String,Object>>();
        RespHelper.setFailRespStatus(result,"");
        try {
            Map<String,Object> map = getElementById(MapUtils.getString(element,"id"));
            if (map == null) {
                /*
                * status 0:等待，1:关箱，2:失败，3:开箱
                */
                element.put("status","0");
                addElement(element);
                logger.info("新增开箱。id=" + MapUtils.getString(element,"id")+"，boxNo=" + MapUtils.getString(element,"boxNo"));
            }else if("2".equals(MapUtils.getString(element,"status"))){
                elements.remove(map);
                logger.info("已取缓存结果数据。id=" + MapUtils.getString(element,"id")+"，boxNo="+MapUtils.getString(element,"boxNo"));
                result.setData(map);
                RespHelper.setSuccessRespStatus(result);
                return result;
            }

            int i = 0;
            while (i < times) {
                Thread.sleep(waitTime);
                Map<String,Object> newElement = getElementById(MapUtils.getString(element,"id"));
                if (newElement != null) {
                    if ("2".equals(MapUtils.getString(newElement,"status"))||"3".equals(MapUtils.getString(newElement,"status"))) {
                        logger.info("已取新开箱结果数据。id=" + MapUtils.getString(element,"id")+"，boxNo=" + MapUtils.getString(element,"boxNo"));
                        elements.remove(newElement);
                        result.setData(newElement);
                        if("3".equals(MapUtils.getString(newElement,"status"))){
                            //开箱成功
                            RespHelper.setSuccessRespStatus(result);
                        }
                        return result;
                    }
                }
                i++;
            }
            if(result.getData()==null){
                result.setMsg("开箱超时");
            }
            logger.info("openBoxByeId-end:i" + i);
        }catch (Exception e){
            logger.error(e);
        }
        return result;
    }


    public synchronized void changeStatusByDetail(Map<String,Object> detail) {
        //{"at":"1513580905859","dev_id":"0000000011","ds_id":"0103","type":"1","value":"3"}

        long time = MapUtils.getLong(detail,"at",0L);
        String deviceId = MapUtils.getString(detail, "dev_id","");
        String lockAddress = MapUtils.getString(detail, "ds_id","");
        String value = MapUtils.getString(detail, "value","");

        for (Map<String,Object> map : elements) {
            if (deviceId.equals(MapUtils.getString(map,"deviceId"))
                    &&lockAddress.equals(MapUtils.getString(map,"lockAddress"))){
                if ("1".equals(value)){
                    map.put("status",1);
                }else if("3".equals(value)){
                    map.put("status",3);
                }else{
                    map.put("status",2);
                }
                map.put("operationTime",time);
                map.put("value",value);
            }
        }
    }

    public boolean checkElementById(String id){
        boolean flag = true;
        Map<String,Object> element = getElementById(id);
        long time = new Date().getTime();
        if (element == null||(time - MapUtils.getLong(element,"time")) >= effectiveTime) {
            flag = false;
        }else if("1".equals(MapUtils.getString(element,"status"))){
            elements.remove(element);
        }
        return flag;
    }

    public Map<String,Object> getElementById(String id) {
        for (Map<String,Object> map : elements) {
            if (id.equals(MapUtils.getString(map,"id"))) {
                return map;
            }
        }
        return null;
    }

    public Map<String,Object> getElementByDetail(Map<String,Object> detail) {
        String deviceId = MapUtils.getString(detail, "dev_id","");
        String lockAddress = MapUtils.getString(detail, "ds_id","");
        for (Map<String,Object> map : elements) {
            if (deviceId.equals(MapUtils.getString(map,"deviceId"))
                    &&lockAddress.equals(MapUtils.getString(map,"lockAddress"))){
                return map;
            }
        }
        return null;
    }

    public void checkElementEffective() {
        if (CollectionUtils.isNotEmpty(elements)){
            long time = new Date().getTime();
            Iterator<Map<String, Object>> iterator = elements.iterator();
            while (iterator.hasNext()){
                Map<String, Object> next = iterator.next();
                if ((time - MapUtils.getLong(next,"time")) >= effectiveTime){
                    iterator.remove();
                }
            }
            logger.info("checkElement :"+elements);
        }
    }

}
