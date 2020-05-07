package com.anjbo.service.sm;

import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespStatus;

import java.util.Map;

/**
 * Created by Administrator on 2017/11/9.
 */
public interface AgencyService {

    /**
     * PC新增机构
     * @param map
     * @return
     */
    RespDataObject<Map<String,Object>> addAgency(Map<String,Object> map);

    /**
     * 分配
     * @param map
     * @return
     */
    RespDataObject<Map<String,Object>> distribution(Map<String,Object> map);

    /**
     *立项
     * @param map
     * @return
     */
    RespDataObject<Map<String,Object>> confirm(Map<String,Object> map);

    /**
     * 尽调
     * @param map
     * @return
     */
    RespDataObject<Map<String,Object>> investigation(Map<String,Object> map);

    /**
     * 审核
     * @param map
     * @return
     */
    RespDataObject<Map<String,Object>> toexamine(Map<String,Object> map);

    /**
     * 签约
     * @param map
     * @return
     */
    RespDataObject<Map<String,Object>> sign(Map<String,Object> map);

    /**
     * 取消
     * @param map
     * @return
     */
    RespStatus cancel(Map<String,Object> map);

    /**
     * 撤回
     * @param map
     * @return
     */
    RespStatus withdraw(Map<String,Object> map);

    /**
     * 重启机构审批
     * @param map
     * @return
     */
    RespStatus restart(Map<String,Object> map);

    /**
     * 查询机构申请信息
     * @param map
     * @return
     */
    Map<String,Object> getAgencyApplyDate(Map<String,Object> map);

    /**
     * 获取指定节点的上一节点
     * @param map(key=processId:当前节点,key=cityCode:城市code,key=productCode:产品)
     * @param key(有则根据传入的key名称获取Map数据)
     * @return key=preProcessId(上一节点),key=state(上一节点名称)
     */
    Map<String,Object> getPreProcessId(Map<String,Object> map,String key);

}
