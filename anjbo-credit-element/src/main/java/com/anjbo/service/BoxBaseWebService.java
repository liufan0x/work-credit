package com.anjbo.service;

import com.anjbo.common.RespStatus;

import java.util.List;
import java.util.Map;

/**
 * Created by lichao on 2017/12/22.
 */
public interface BoxBaseWebService {

    Map<String,Object> getElementBoxDetail(Map<String, Object> param);

    RespStatus openElementBox(String boxNo);

    List<String> selectBoxBaseHaveCityList();

    int selectBoxBaseCount(Map<String,Object> param);

    List<Map<String,Object>> selectBoxBaseList(Map<String,Object> param);

    int selectOpenBoxBaseCount(Map<String,Object> param);

    List<Map<String,Object>> selectOpenBoxBaseList(Map<String,Object> param);

    int updateBoxBaseStatus(Map<String,Object> param);

    void saveAccessFlowByKey(Map<String,Object> param);

    void saveElementBoxLogs (String log,int type);

    void viewOffLineBox();
}
