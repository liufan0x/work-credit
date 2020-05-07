package com.anjbo.dao;

import java.util.List;
import java.util.Map;

/**
 * Created by lichao on 2018/1/11.
 */
public interface EleAccessMapper {

    int selectElementAccessCount(Map<String,Object> param);

    List<Map<String,Object>> selectElementAccessList(Map<String,Object> param);

    Map<String,Object> selectElementAccessFlowDetail(Map<String,Object> param);

    List<Map<String, Object>> selectElementAuditFlowListByDbId(Map<String,Object> param);

    Map<String,Object> getElementAuditBaseDetail (Map<String,Object> param);

    List<Map<String,Object>> selectElementFileList(Map<String,Object> param);

    List<Map<String,Object>> selectAllElementFileList();

    Map<String,Object> getElementOrderDetail(Map<String,Object> param);

    int selectElementCountByOrderNo(Map<String,Object> param);

    List<Map<String, Object>> selectElementListByOrderNo(Map<String,Object> param);

}
