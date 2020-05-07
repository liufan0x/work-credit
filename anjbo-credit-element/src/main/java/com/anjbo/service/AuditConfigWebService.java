package com.anjbo.service;

import java.util.List;
import java.util.Map;

/**
 * Created by lichao on 2017/12/21.
 */
public interface AuditConfigWebService {
    int save(Map<String,Object> param);

    int edit(Map<String,Object> param);

    Map<String,Object> selectAuditConfigDetail(Map<String,Object> param);

    List<String> selectAuditConfigHaveCityList();

    List<Integer> selectAuditConfigTypeList(Map<String,Object> param);

    int selectAuditConfigCount(Map<String,Object> param);

    List<Map<String,Object>> selectAuditConfigList(Map<String,Object> param);
    
    void editState(Map<String,Object> param);
    
    List<Map<String,Object>> selectAllCityType();
}
