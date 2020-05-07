package com.anjbo.service;

import java.util.List;
import java.util.Map;

import com.anjbo.bean.risk.LawsuitSzDto;

public interface LawsuitSzService {
    int deleteByPrimaryKey(Integer id);

    int insert(LawsuitSzDto record);

    int insertSelective(LawsuitSzDto record);

    LawsuitSzDto selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LawsuitSzDto record);

    int updateByPrimaryKey(LawsuitSzDto record);
    
    /**
     * 组合查询深圳诉讼记录集合
     * @param map
     * @return
     */
    List<Map<String, Object>> getListMap(Map<String, Object> map);
    
    int deleteByMap(Map<String, Object> map);
}
