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

import java.util.List;
import java.util.Map;

/**
 * Created by lichao on 2017/12/21.
 */
@Component("elementMessageAMQP")
public class ElementMessageAMQP implements MessageListener {
    protected Log logger = LogFactory.getLog(ElementMessageAMQP.class);

    @Autowired
    private BoxBaseWebService boxBaseWebService;

    /**
     * 4F1DB5340DAD314F81EA1C07
     * [{"at":"1513580745396","dev_id":"0000000011","ds_id":"0102","type":"1","value":"3"}] 远程开箱
     * [{"at":"1513580815690","dev_id":"0000000011","ds_id":"0101","type":"1","value":"3"}] 手动开箱
     * [{"at":"1513580841023","dev_id":"0000000011","ds_id":"0101","type":"1","value":"1"}] 关箱
     * [{"at":"1513580905859","dev_id":"0000000011","ds_id":"0103","type":"1","value":"3"}] 查询状态
     */
    public void onMessage(Message message) {

        try {
            String result = new String(message.getBody());
            logger.info("rabbitmq回调信息：" + result);
            boxBaseWebService.saveElementBoxLogs(result, 1);
            List<Map<String, Object>> list = (List<Map<String, Object>>) JsonUtil.parseJsonToObj(result, List.class);
            if (list != null && list.size() > 0) {
                for (Map<String, Object> map : list) {
                    if (MapUtils.isNotEmpty(map)) {
                        //type 数据类型（1：数据点数据，2：上下线数据）
                        if ("1".equals(MapUtils.getString(map, "type"))) {

                            ElementManager elementManager = ElementManager.getInstance();
                            Map<String, Object> element = ElementManager.getInstance().getElementByDetail(map);

                            if (element != null) {
                                elementManager.changeStatusByDetail(map);
                            } else {
                                boxBaseWebService.saveAccessFlowByKey(map);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e);
        }
    }
}
