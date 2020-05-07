package com.anjbo.service.impl;

import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.dao.AccessFlowMapper;
import com.anjbo.dao.BoxBaseWebMapper;
import com.anjbo.manager.ElementManager;
import com.anjbo.service.BoxBaseWebService;
import com.anjbo.util.BoxUtil;
import com.anjbo.utils.JsonUtil;
import com.anjbo.utils.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lichao on 2017/12/22.
 */
@Service
public class BoxBaseWebServiceImpl implements BoxBaseWebService {

    protected Log logger = LogFactory.getLog(BoxBaseWebServiceImpl.class);

    @Autowired
    private BoxBaseWebMapper boxBaseWebMapper;
    @Autowired
    private AccessFlowMapper accessFlowMapper;

    @Override
    public List<String> selectBoxBaseHaveCityList() {
        return boxBaseWebMapper.selectBoxBaseHaveCityList();
    }

    @Override
    public int selectBoxBaseCount(Map<String, Object> param) {
        return boxBaseWebMapper.selectBoxBaseCount(param);
    }

    @Override
    public List<Map<String, Object>> selectBoxBaseList(Map<String, Object> param) {
        return boxBaseWebMapper.selectBoxBaseList(param);
    }

    @Override
    public int selectOpenBoxBaseCount(Map<String, Object> param) {
        return boxBaseWebMapper.selectOpenBoxBaseCount(param);
    }

    @Override
    public List<Map<String, Object>> selectOpenBoxBaseList(Map<String, Object> param) {
        return boxBaseWebMapper.selectOpenBoxBaseList(param);
    }

    @Override
    public Map<String, Object> getElementBoxDetail(Map<String, Object> param) {
        return boxBaseWebMapper.getElementBoxDetail(param);
    }

    @Override
    public int updateBoxBaseStatus(Map<String, Object> param) {
        return boxBaseWebMapper.updateBoxBaseStatus(param);
    }

    @Override
    public void saveElementBoxLogs(String log, int type) {
        try {
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("log", log);
            param.put("type", type);
            boxBaseWebMapper.saveElementBoxLogs(param);
        } catch (Exception e) {
            logger.error(e);
        }
    }

    @Override
    public void viewOffLineBox() {
        try {
            List<Map<String, Object>> offLineBoxList = boxBaseWebMapper.selectOffLineBoxList();
            if (CollectionUtils.isNotEmpty(offLineBoxList)) {
                for (Map<String, Object> map : offLineBoxList) {
                    String deviceId = MapUtils.getString(map, "deviceId");
                    String lockAddress = MapUtils.getString(map, "lockAddress");
                    if (StringUtil.isNotEmpty(deviceId)&&StringUtil.isNotEmpty(lockAddress)){
                        BoxUtil.getBoxStatus(deviceId, lockAddress);
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e);
        }
    }

    @Override
    public void saveAccessFlowByKey(Map<String, Object> param) {

        try {
            Map<String, Object> boxDetail = new HashMap<String, Object>();
            boxDetail.put("deviceId", MapUtils.getString(param, "dev_id", ""));
            boxDetail.put("lockAddress", MapUtils.getString(param, "ds_id", ""));
            Map<String, Object> elementBox = boxBaseWebMapper.getElementBoxDetail(boxDetail);

            if (elementBox != null) {
                if ("0".equals(MapUtils.getString(elementBox, "deviceStatus"))) {
                    //修改要件箱设备状态为“正常”
                    Map<String, Object> up = new HashMap<String, Object>();
                    up.put("deviceId", MapUtils.getString(param, "dev_id", ""));
                    up.put("deviceStatus", 1);
                    boxBaseWebMapper.updateBoxBaseStatus(up);
                } else if ("3".equals(MapUtils.getString(param, "value", ""))) {
                    Map<String, Object> accessFlow = new HashMap<String, Object>();
                    String orderNo = MapUtils.getString(elementBox, "orderNo");
                    String useStatus = MapUtils.getString(elementBox, "useStatus");
                    if (StringUtil.isNotEmpty(orderNo) && "1".equals(useStatus)) {
                        accessFlow.put("orderNo", orderNo);
                        long time = MapUtils.getLong(param, "at", 0L);
                        accessFlow.put("operationTime", new Date(time));
                        accessFlow.put("operationType", 7);
                        accessFlow.put("currentHandler", "钥匙开箱");
                        accessFlowMapper.insertAccessFlowRecorde(accessFlow);
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e);
        }
    }

    @Override
    public RespDataObject<Map<String, Object>> openElementBox(String boxNo) {
        RespDataObject<Map<String, Object>> resp = new RespDataObject<Map<String, Object>>();

        long beginTime=System.currentTimeMillis();
        
        
        RespHelper.setFailRespStatus(resp, "");
        logger.info("开箱接口调用-start,箱号："+boxNo);
        try {
            if (StringUtil.isEmpty(boxNo)) {
                resp.setMsg("boxNo不能为空");
                return resp;
            }
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("boxNo", boxNo);
            Map<String, Object> boxDetail = boxBaseWebMapper.getElementBoxDetail(param);
            if (boxDetail == null || StringUtil.isEmpty(MapUtils.getString(boxDetail, "deviceId"))
                    || StringUtil.isEmpty(MapUtils.getString(boxDetail, "lockAddress"))) {
                resp.setMsg("boxNo不存在");
                return resp;
            }
            ElementManager elementManager = ElementManager.getInstance();
            //校验是否存在缓存记录
//            boolean flag = elementManager.checkElementById(MapUtils.getString(boxDetail, "id"));
//            if (!flag) {

                //String result  = openBoxHttp(boxDetail);
                String result = BoxUtil.openBox(MapUtils.getString(boxDetail, "deviceId"), MapUtils.getString(boxDetail, "lockAddress"));
                if (StringUtil.isNotEmpty(result)) {
                    Map<String, Object> resultMap = JsonUtil.jsonToMap(result);

                    if ("0".equals(MapUtils.getString(resultMap, "code"))) {
                        resp = elementManager.openBoxByeId(boxDetail);
                    } else {
                        if ("设备不在线".equals(MapUtils.getString(resultMap, "msg"))) {
                            //更新设备离线
                            Map<String, Object> up = new HashMap<String, Object>();
                            up.put("id", MapUtils.getString(boxDetail, "id"));
                            up.put("deviceStatus", 0);
                            boxBaseWebMapper.updateBoxBaseStatus(up);
                        }
                        resp.setMsg(MapUtils.getString(resultMap, "msg"));
                    }
                }
//            } else {
//                resp = elementManager.openBoxByeId(boxDetail);
//            }
            
            long endTime=System.currentTimeMillis();

            logger.info("保存openElementBox方法执行时间:"+(endTime-beginTime));
    
            
        } catch (Exception e) {
            logger.error(e);
        }
        logger.info("开箱接口调用-end,返回结果："+resp);
        return resp;
    }
}
