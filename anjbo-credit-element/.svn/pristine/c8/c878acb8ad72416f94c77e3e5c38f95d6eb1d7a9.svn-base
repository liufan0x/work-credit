package com.anjbo.manager;

import com.anjbo.service.BoxBaseWebService;
import com.anjbo.utils.JsonUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lichao on 2017/12/21.
 */
@Component("elementStatusAMQP")
public class ElementStatusAMQP implements MessageListener {

    protected Log logger = LogFactory.getLog(ElementMessageAMQP.class);

    @Autowired
    private BoxBaseWebService boxBaseWebService;
    /**
     *
     * STATUS_4F1DB5340DAD314F81EA1C07
     *  [{"at":"1513580643709","dev_id":"0000000011","status":"0","type":"2"}] 离线
     *  [{"at":"1513580696173","dev_id":"0000000011","status":"1","type":"2"}] 上线
     */
    public void onMessage(Message message) {
        try {
            String result = new String(message.getBody());
            logger.info("rabbitmq回调信息："+result);
            boxBaseWebService.saveElementBoxLogs(result, 2);
            List<Map<String,Object>> list = (List<Map<String,Object>>) JsonUtil.parseJsonToObj(result, List.class);
            if (list!=null&&list.size()>0){
                for (Map<String,Object> map:list){
                    //type 数据类型（1：数据点数据，2：上下线数据）
                    if("2".equals(MapUtils.getString(map, "type"))){
                        Map<String,Object> param = new HashMap<String,Object>();
                        param.put("deviceId",MapUtils.getString(map, "dev_id"));
                        param.put("deviceStatus",MapUtils.getString(map, "status"));
                        boxBaseWebService.updateBoxBaseStatus(param);
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e);
        }
    }
}
